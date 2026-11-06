package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
final class IfritCombo {

    public enum Action {
        NONE, SPAWN, MELEE, WALTZ,
        COMBO_START, COMBO_DODGE_START, COMBO1, COMBO2, COMBO3, COMBO4,
        COMBO_BLOCK, COMBO_CHASE, COMBO_REST,
        DASH, RUSH, DASH_REC, CLONE, BALLROOM, DODGE_L, DODGE_R,

        GRAND_CURTAIN,

        DODGE_SLASH
    }

    public static final class Def {
        final String clip;
        final int ticks;
        final int hit;
        final Action next;

        Def(String clip, int ticks, int hit, Action next) {
            this.clip = clip;
            this.ticks = ticks;
            this.hit = hit;
            this.next = next;
        }
    }

    static final Def[] DEFS = new Def[Action.values().length];
    static final Action[] ACTIONS = Action.values();

    private static final float SKILL_DAMAGE_MULTIPLIER = 0.5F;
    private static final float CURTAIN_DAMAGE_MULTIPLIER = 0.1F;

    static {
        DEFS[Action.NONE.ordinal()] = new Def("idle", 0, -1, Action.NONE);
        DEFS[Action.SPAWN.ordinal()] = new Def("spawn", 70, -1, Action.NONE);
        DEFS[Action.MELEE.ordinal()] = new Def("melee", 27, 4, Action.NONE);
        DEFS[Action.WALTZ.ordinal()] = new Def("waltz", 56, 24, Action.NONE);
        DEFS[Action.COMBO_START.ordinal()] = new Def("combo_start", 24, -1, Action.COMBO1);
        DEFS[Action.COMBO_DODGE_START.ordinal()] =
                new Def("combo_start_dodge", 26, -1, Action.COMBO1);
        DEFS[Action.COMBO1.ordinal()] = new Def("combo1", 12, 4, Action.COMBO2);
        DEFS[Action.COMBO2.ordinal()] = new Def("combo2", 15, 10, Action.COMBO3);
        DEFS[Action.COMBO3.ordinal()] = new Def("combo3", 32, 23, Action.COMBO4);
        DEFS[Action.COMBO4.ordinal()] = new Def("combo4", 38, 24, Action.COMBO_REST);
        DEFS[Action.COMBO_BLOCK.ordinal()] =
                new Def("combo_block", Ifrit.BLOCK_MAX_TICKS, -1, Action.COMBO_REST);
        DEFS[Action.COMBO_CHASE.ordinal()] = new Def("rush", 30, 24, Action.DASH_REC);
        DEFS[Action.COMBO_REST.ordinal()] = new Def("combo_rest", 28, -1, Action.NONE);
        DEFS[Action.DASH.ordinal()] = new Def("dash", 30, 26, Action.DASH_REC);
        DEFS[Action.RUSH.ordinal()] = new Def("rush", 33, 24, Action.DASH_REC);
        DEFS[Action.DASH_REC.ordinal()] = new Def("dash_recovery", 18, -1, Action.NONE);
        DEFS[Action.CLONE.ordinal()] = new Def("clone", 76, 28, Action.NONE);
        DEFS[Action.BALLROOM.ordinal()] = new Def("fire_rain", 64, -1, Action.NONE);
        DEFS[Action.DODGE_L.ordinal()] = new Def("dodge_left", 22, -1, Action.NONE);
        DEFS[Action.DODGE_R.ordinal()] = new Def("dodge_right", 22, -1, Action.NONE);
        DEFS[Action.GRAND_CURTAIN.ordinal()] = new Def("fire_rain", 64, -1, Action.NONE);
        DEFS[Action.DODGE_SLASH.ordinal()] = new Def("melee", 22, 9, Action.NONE);
    }

    private IfritCombo() {
    }

    static void startAction(Ifrit mob, Action action) {
        if (action != Action.DODGE_L && action != Action.DODGE_R && action != Action.DODGE_SLASH) {
            mob.dodgeStreak = 0;
        }
        mob.getEntityData().set(Ifrit.DATA_ACTION, action.ordinal());
        mob.getEntityData().set(Ifrit.DATA_ACTION_TICK, 0);
        onActionStart(mob, action);
    }

    static void endAction(Ifrit mob) {
        mob.getEntityData().set(Ifrit.DATA_ACTION, Action.NONE.ordinal());
        mob.getEntityData().set(Ifrit.DATA_ACTION_TICK, 0);
        mob.hellfireBonus = 0.0F;
        mob.dashHitVictims.clear();
    }

    static boolean isSkillAction(Action action) {
        return switch (action) {
            case MELEE, WALTZ, DASH, RUSH, COMBO_START, COMBO_DODGE_START, BALLROOM -> true;
            default -> false;
        };
    }

    static float skillDamageMultiplier(Ifrit mob) {
        return switch (mob.getAction()) {
            case BALLROOM, CLONE -> SKILL_DAMAGE_MULTIPLIER;
            case GRAND_CURTAIN -> CURTAIN_DAMAGE_MULTIPLIER;
            default -> 1.0F;
        };
    }

    static boolean isHitTick(Action action, int tick) {
        return switch (action) {
            case COMBO_START -> tick == Ifrit.COMBO_START_HIT_1 || tick == Ifrit.COMBO_START_HIT_2;
            case COMBO2 -> tick == 5 || tick == 10;
            case COMBO3 -> tick == 8 || tick == 15 || tick == 23;
            case COMBO4 -> tick == 8 || tick == 12 || tick == 24;
            case COMBO_CHASE -> tick == 10 || tick == 24;
            case DODGE_SLASH -> tick == 9;
            case DASH -> tick >= Ifrit.DASH_SWEEP_FIRST && tick <= Ifrit.DASH_LAND_TICK;
            case RUSH -> tick >= IfritMovement.RUSH_SWEEP_FIRST && tick <= Ifrit.RUSH_SWEEP_LAST;
            default -> DEFS[action.ordinal()].hit == tick;
        };
    }

    static Action nextAction(Ifrit mob, Action finished) {
        return switch (finished) {
            case COMBO1, COMBO2, COMBO3, COMBO4 -> advanceCombo(mob, finished);
            case DODGE_L, DODGE_R -> finishDodge(mob, finished);
            case COMBO_BLOCK, COMBO_REST -> {
                mob.comboHits = 0;
                mob.lastStageHit = false;
                mob.comboResetPending = false;
                yield Action.NONE;
            }
            default -> DEFS[finished.ordinal()].next;
        };
    }

    static int comboStageOf(Action action) {
        return switch (action) {
            case COMBO1 -> 1;
            case COMBO2 -> 2;
            case COMBO3 -> 3;
            case COMBO4 -> 4;
            default -> 0;
        };
    }

    static Action advanceCombo(Ifrit mob, Action finished) {
        int stage = comboStageOf(finished);
        if (mob.lastStageHit) {
            mob.comboHits++;
        }
        if (stage >= 2 && !mob.lastStageHit && !mob.scriptedCombo) {
            cancelCombo(mob);
            return Action.COMBO_REST;
        }
        if (stage >= 4) {
            if (mob.scriptedCombo) {
                mob.comboFinished = true;
            }
            mob.comboResetPending = true;
            return Action.COMBO_REST;
        }
        return switch (stage) {
            case 1 -> Action.COMBO2;
            case 2 -> Action.COMBO3;
            default -> mob.scriptedCombo || mob.getRandom().nextBoolean()
                    ? Action.COMBO4 : Action.COMBO_BLOCK;
        };
    }

    static void cancelCombo(Ifrit mob) {
        mob.comboResetPending = true;
        mob.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.2F, 1.1F);
    }

    static Action finishDodge(Ifrit mob, Action finished) {
        mob.dodgeStreak++;
        if (mob.dodgeStreak >= IfritDodge.STREAK_FOR_COMBO) {
            mob.dodgeStreak = IfritDodge.STREAK_FOR_COMBO;
            mob.comboDodgeArmed = true;
            mob.approachDodgeSpent = true;
        }
        if (finished == Action.DODGE_R && mob.getRandom().nextFloat() < IfritDodge.CUT_CHANCE
                && dodgeSlashInRange(mob)) {
            return Action.DODGE_SLASH;
        }
        return Action.NONE;
    }

    static boolean dodgeSlashInRange(Ifrit mob) {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && target.distanceTo(mob) <= IfritDodge.SLASH_RANGE;
    }
    static void onActionStart(Ifrit mob, Action action) {
        mob.rushEarlyFinish = false;
        mob.waltzLeapDir = Vec3.ZERO;
        if (action == Action.COMBO_START || action == Action.COMBO_DODGE_START
                || action == Action.COMBO3 || action == Action.COMBO4) {
            mob.comboAdvanceOrigin = null;
        }
        LivingEntity face = mob.chaseTarget != null ? mob.chaseTarget : mob.getTarget();
        switch (action) {
            case MELEE, WALTZ, COMBO_START, COMBO_DODGE_START, COMBO1, COMBO2, COMBO3, COMBO4,
                    DASH, RUSH, CLONE, BALLROOM, GRAND_CURTAIN, COMBO_CHASE, DODGE_SLASH -> {
                if (face != null) {
                    mob.faceInstantly(face);
                }
            }
            default -> {
            }
        }
        switch (action) {
            case COMBO_START, COMBO_DODGE_START -> {
                mob.comboHits = 0;
                mob.lastStageHit = false;
                mob.comboResetPending = false;
                mob.comboDodgeArmed = false;
                mob.approachDodgeSpent = false;
                IfritSounds.playSwordSaberSound(mob);
            }
            case COMBO1 -> {
                mob.lastStageHit = false;
                IfritSounds.playBasicAttackSound(mob);
            }
            case COMBO2, COMBO3, COMBO4 -> {
                mob.lastStageHit = false;
                IfritSounds.playSwordSaberSound(mob);
            }
            case MELEE, DODGE_SLASH, WALTZ -> IfritSounds.playSwordSaberSound(mob);
            case COMBO_BLOCK -> {
                mob.comboHits = 0;
                mob.comboResetPending = false;
                mob.getNavigation().stop();
            }
            case DASH, RUSH, COMBO_CHASE -> {
                if (action != Action.COMBO_CHASE) {
                    mob.chaseTarget = null;
                    mob.dashHitVictims.clear();
                }
            }
            case CLONE, BALLROOM, GRAND_CURTAIN -> {
                mob.getNavigation().stop();
                mob.chaseTarget = null;
                IfritMovement.holdStill(mob);
            }
            default -> {
            }
        }
        if (IfritCombo.isSkillAction(action)) {
            mob.lastSkillAction = action;
        }
    }


    static void tickAction(Ifrit mob) {
        Action action = mob.getAction();
        if (action == Action.NONE || mob.isDeadOrDying()) {
            return;
        }
        IfritCombo.Def def = IfritCombo.DEFS[action.ordinal()];
        int tick = mob.getEntityData().get(Ifrit.DATA_ACTION_TICK) + 1;
        mob.getEntityData().set(Ifrit.DATA_ACTION_TICK, tick);
        switch (action) {
            case WALTZ -> {
                if (tick >= Ifrit.WALTZ_LEAP_FIRST && tick <= Ifrit.WALTZ_LEAP_LAST) {
                    IfritMovement.waltzLeapStep(mob, tick == Ifrit.WALTZ_LEAP_TICK);
                }
            }
            case MELEE, DODGE_SLASH -> {
                if (tick == 5) {
                    Vec3 look = mob.getLookAngle();
                    mob.setDeltaMovement(look.x * 0.5D, 0.45D, look.z * 0.5D);
                    mob.hasImpulse = true;
                }
            }
            case DASH -> {
                if (tick == 10) {
                    IfritMovement.leapOff(mob);
                    IfritSounds.playDashSound(mob);
                }
                if (tick >= 10 && tick <= 25) {
                    IfritMovement.dashStep(mob, 1.15D, ParticleTypes.FLAME);
                }
            }
            case RUSH -> {
                if (tick == 12) {
                    IfritMovement.leapOff(mob);
                    IfritSounds.playDashSound(mob);
                }
                if (mob.rushEarlyFinish) {
                    IfritMovement.holdStill(mob);
                } else if (tick >= 12 && tick <= 27) {
                    IfritMovement.dashStep(mob, 1.05D, ParticleTypes.SOUL_FIRE_FLAME);
                }
                if (IfritMovement.tryEarlyRushFinish(mob, tick)) {
                    return;
                }
                if (mob.rushEarlyFinish && tick >= Ifrit.RUSH_EARLY_END_TICK) {
                    IfritCombo.startAction(mob, Action.DASH_REC);
                    return;
                }
            }
            case COMBO_CHASE -> {
                if (tick == 11) {
                    IfritSounds.playDashSound(mob);
                }
                if (tick == 12) {
                    IfritMovement.leapOff(mob);
                }
                if (tick >= 11 && tick <= 27) {
                    IfritMovement.chaseStep(mob);
                }
            }
            case COMBO_START -> {
                if (tick >= Ifrit.COMBO_START_ADVANCE_FIRST && tick <= Ifrit.COMBO_START_ADVANCE_LAST) {
                    IfritMovement.comboAdvance(mob);
                }
            }
            case COMBO1 -> {
                if (tick == 3) {
                    launchCombo1(mob);
                }
            }
            case COMBO2 -> {
                if (tick == 4) {
                    launchCombo2(mob);
                }
                if (tick >= Ifrit.COMBO2_DIVE_FIRST && tick <= Ifrit.COMBO2_DIVE_LAST) {
                    combo2Dive(mob);
                }
            }
            case COMBO3 -> {
                if (tick >= Ifrit.COMBO3_ADVANCE_FIRST && tick <= Ifrit.COMBO3_ADVANCE_LAST) {
                    IfritMovement.comboAdvance(mob);
                }
            }
            case COMBO4 -> {
                if (tick >= Ifrit.COMBO4_ADVANCE_FIRST && tick <= Ifrit.COMBO4_ADVANCE_LAST) {
                    IfritMovement.comboAdvance(mob);
                }
            }
            case COMBO_BLOCK -> IfritMovement.holdStill(mob);
            case DODGE_L -> {
                if (tick >= IfritDodge.MOVE_FIRST_TICK && tick <= IfritDodge.MOVE_LAST_TICK) {
                    IfritMovement.sideStep(mob, 1.0D);
                }
            }
            case DODGE_R -> {
                if (tick >= IfritDodge.MOVE_FIRST_TICK && tick <= IfritDodge.MOVE_LAST_TICK) {
                    IfritMovement.sideStep(mob, -1.0D);
                }
            }
            case CLONE -> {
                IfritMovement.holdStill(mob);
            }
            case BALLROOM -> {
                IfritMovement.holdStill(mob);
                if (tick >= 8 && tick <= 58 && (tick - 8) % IfritClones.BALLROOM_SPAWN_INTERVAL == 0) {
                    IfritClones.spawnDanceClone(mob, (tick - 8) / IfritClones.BALLROOM_SPAWN_INTERVAL);
                }
            }
            case GRAND_CURTAIN -> {
                IfritMovement.holdStill(mob);
            }
            default -> {
            }
        }
        if (IfritCombo.isHitTick(action, tick)) {
            onHitFrame(mob, action, tick);
        }
        if (tick >= def.ticks) {
            Action next = IfritCombo.nextAction(mob, action);
            if (next != Action.NONE) {
                IfritCombo.startAction(mob, next);
            } else {
                IfritCombo.endAction(mob);
            }
        }
    }

    static void onHitFrame(Ifrit mob, Action action, int tick) {
        switch (action) {
            case MELEE -> IfritCombat.hitCone(mob, 4.5D, Ifrit.DMG_MELEE, 0.0D, 0.45D);
            case WALTZ -> {
                mob.shieldBreakStrike = true;
                mob.hellfireBonus = Ifrit.HELLFIRE_WALTZ;
                int hits = IfritCombat.hitAoe(mob, 5.4D, Ifrit.DMG_WALTZ);
                mob.hellfireBonus = 0.0F;
                mob.shieldBreakStrike = false;
                IfritCombat.healFromTargets(mob, hits, Ifrit.WALTZ_HEAL_PER_TARGET);
            }
            case COMBO_START -> {
                mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                int hits = IfritCombat.hitAoe(mob, 5.0D, Ifrit.DMG_COMBO_START);
                mob.hellfireBonus = 0.0F;
                IfritCombat.healFromTargets(mob, hits, Ifrit.COMBO_HEAL_PER_TARGET);
            }
            case DODGE_SLASH -> {
                mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                IfritCombat.hitCone(mob, 4.5D, Ifrit.DMG_DODGE_CUT, 0.0D, 0.4D);
                mob.hellfireBonus = 0.0F;
            }
            case COMBO1 -> {
                mob.hellfireBonus = Ifrit.HELLFIRE_COMBO1;
                int hits = IfritCombat.hitSideSectors(mob, 5.0D, Ifrit.DMG_COMBO1);
                mob.hellfireBonus = 0.0F;
                if (hits > 0) {
                    mob.lastStageHit = true;
                }
            }
            case COMBO2 -> {
                if (tick == 5) {
                    mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                    int hits = IfritCombat.hitAoe(mob, 5.0D, Ifrit.DMG_COMBO2_DROP);
                    mob.hellfireBonus = 0.0F;
                    if (IfritCombat.healFromTargets(mob, hits, Ifrit.COMBO_HEAL_PER_TARGET) > 0) {
                        mob.lastStageHit = true;
                    }
                }
                if (tick == 10) {
                    mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                    int hits = IfritCombat.hitCone(mob, 5.0D, Ifrit.DMG_COMBO2_WING, 0.0D, 0.5D);
                    mob.hellfireBonus = 0.0F;
                    if (IfritCombat.healFromTargets(mob, hits, Ifrit.COMBO_HEAL_PER_TARGET) > 0) {
                        mob.lastStageHit = true;
                    }
                }
            }
            case COMBO3 -> {
                mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                int hits = IfritCombat.hitAoe(mob, 5.0D, Ifrit.DMG_COMBO3);
                mob.hellfireBonus = 0.0F;
                if (IfritCombat.healFromTargets(mob, hits, Ifrit.COMBO3_HEAL_PER_TARGET) > 0) {
                    mob.lastStageHit = true;
                }
            }
            case COMBO4 -> {
                mob.shieldBreakStrike = true;
                if (tick == 8 || tick == 12) {
                    mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                    int hits = IfritCombat.hitAoe(mob, 5.2D, Ifrit.DMG_COMBO4_TURN);
                    mob.hellfireBonus = 0.0F;
                    if (IfritCombat.healFromTargets(mob, hits, IfritCombat.COMBO4_HEAL_PER_TARGET) > 0) {
                        mob.lastStageHit = true;
                    }
                }
                if (tick == 24) {
                    IfritCombat.slamShockwave(mob);
                    mob.lastStageHit = true;
                }
                mob.shieldBreakStrike = false;
            }
            case COMBO_CHASE -> {
                if (tick == 10) {
                    mob.hellfireBonus = Ifrit.HELLFIRE_CHASE;
                    IfritCombat.hitAoe(mob, 3.0D, Ifrit.DMG_CHASE);
                    mob.hellfireBonus = 0.0F;
                }
                if (tick == 24) {
                    mob.hellfireBonus = Ifrit.HELLFIRE_CHASE;
                    chaseCut(mob);
                    mob.hellfireBonus = 0.0F;
                }
            }
            case DASH -> {
                mob.shieldBreakStrike = true;
                mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                IfritMovement.dashStrike(mob, tick, Ifrit.DASH_SWEEP_FIRST, Ifrit.DASH_SWEEP_LAST, Ifrit.DASH_LAND_TICK, 0.0F);
                mob.hellfireBonus = 0.0F;
                mob.shieldBreakStrike = false;
            }
            case RUSH -> {
                mob.hellfireBonus = Ifrit.HELLFIRE_BASIC;
                IfritMovement.dashStrike(mob, tick, IfritMovement.RUSH_SWEEP_FIRST, Ifrit.RUSH_SWEEP_LAST,
                        IfritCombo.DEFS[Action.RUSH.ordinal()].hit, Ifrit.RUSH_CUT_HEAL_PER_TARGET);
                mob.hellfireBonus = 0.0F;
            }
            case CLONE -> IfritClones.spawn(mob);
            default -> {
            }
        }
    }

    static void launchCombo1(Ifrit mob) {
        Vec3 back = mob.getLookAngle().scale(-0.55D);
        mob.setDeltaMovement(back.x, 0.62D, back.z);
        mob.hasImpulse = true;
        mob.playSound(SoundEvents.BLAZE_SHOOT, 1.2F, 0.7F);
    }

    static void launchCombo2(Ifrit mob) {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            mob.setDeltaMovement(0.0D, -0.55D, 0.0D);
            mob.hasImpulse = true;
            return;
        }
        mob.setDeltaMovement(0.0D, -0.55D, 0.0D);
        mob.hasImpulse = true;
        combo2Dive(mob);
    }

    static void combo2Dive(Ifrit mob) {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double dx = target.getX() - mob.getX();
        double dz = target.getZ() - mob.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        Vec3 movement = mob.getDeltaMovement();
        if (horizontal <= Ifrit.COMBO2_DIVE_STOP_RANGE) {
            mob.setDeltaMovement(0.0D, movement.y, 0.0D);
            mob.hasImpulse = true;
            return;
        }
        double speed;
        if (mob.onGround()) {
            mob.getNavigation().stop();
            speed = Math.min(horizontal / Ifrit.COMBO2_DIVE_TICKS, Ifrit.COMBO2_CHASE_SPEED);
        } else {
            speed = Mth.clamp(horizontal / Ifrit.COMBO2_DIVE_TICKS,
                    Ifrit.COMBO2_DIVE_MIN_SPEED, Ifrit.COMBO2_DIVE_MAX_SPEED);
        }
        mob.setDeltaMovement(dx / horizontal * speed, movement.y, dz / horizontal * speed);
        mob.hasImpulse = true;
    }

    static void chaseCut(Ifrit mob) {
        LivingEntity target = mob.chaseTarget;
        if (target == null || !target.isAlive()) {
            target = mob.getTarget();
        }
        if (target == null || !target.isAlive() || target.distanceTo(mob) > 4.5D) {
            return;
        }
        mob.faceInstantly(target);
        IfritCombat.hitVictim(mob, target, Ifrit.DMG_CHASE_CUT);
        target.push(0.0D, 0.5D * IfritCombat.KNOCKBACK_SCALE, 0.0D);
        mob.burst(ParticleTypes.SOUL_FIRE_FLAME, 24, 0.08D);
        IfritSounds.playImpactSound(mob);
    }


    static void tickSignatureParticles(Ifrit mob) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        if (mob.tickCount % 2 == 0) {
            emitAshRing(mob, server, ParticleTypes.ASH, Ifrit.ASH_BODY_RADIUS, Ifrit.ASH_BODY_MIN_Y,
                    Ifrit.ASH_BODY_MAX_Y, 4, 0.01D);
        }
        if (mob.isPhaseTwo()) {
            emitAshRing(mob, server, ParticleTypes.ASH, Ifrit.ASH_PHASE_TWO_RADIUS, Ifrit.ASH_BODY_MIN_Y - 0.2D,
                    Ifrit.ASH_BODY_MAX_Y + 0.5D, 6, 0.02D);
            emitAshRing(mob, server, ParticleTypes.SMOKE, Ifrit.ASH_PHASE_TWO_RADIUS, 2.0D,
                    Ifrit.ASH_BODY_MAX_Y + 0.3D, 2, 0.0D);
            if (mob.tickCount % 5 == 0) {
                emitAshRing(mob, server, ParticleTypes.ASH, Ifrit.ASH_PHASE_TWO_OUTER_RADIUS, 0.2D,
                        Ifrit.ASH_BODY_MAX_Y + 1.0D, 14, 0.005D);
            }
        }
        Action action = mob.getAction();
        if (action == Action.CLONE) {
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, mob.getX(), mob.getY() + 1.1D,
                    mob.getZ(), 5, 0.5D, 0.85D, 0.5D, 0.01D);
            server.sendParticles(ParticleTypes.SOUL, mob.getX(), mob.getY() + 1.3D, mob.getZ(),
                    3, 0.45D, 0.8D, 0.45D, 0.01D);
        } else if (action == Action.BALLROOM || action == Action.GRAND_CURTAIN) {
            server.sendParticles(ParticleTypes.FLAME, mob.getX(), mob.getY() + 1.1D, mob.getZ(),
                    5, 0.5D, 0.85D, 0.5D, 0.015D);
        }
    }

    static void emitAshRing(Ifrit mob, ServerLevel server, ParticleOptions particle, double radius,
            double minY, double maxY, int count, double speed) {
        double start = mob.getRandom().nextDouble() * Mth.TWO_PI;
        double height = Math.max(0.0D, maxY - minY);
        for (int i = 0; i < count; i++) {
            double angle = start + Mth.TWO_PI * i / count
                    + (mob.getRandom().nextDouble() - 0.5D) * 0.5D;
            double ringRadius = radius * (0.85D + mob.getRandom().nextDouble() * 0.3D);
            server.sendParticles(particle,
                    mob.getX() + Math.cos(angle) * ringRadius,
                    mob.getY() + minY + mob.getRandom().nextDouble() * height,
                    mob.getZ() + Math.sin(angle) * ringRadius,
                    1, 0.12D, 0.2D, 0.12D, speed);
        }
    }

}
