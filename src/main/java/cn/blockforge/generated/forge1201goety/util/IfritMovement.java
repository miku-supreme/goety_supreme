package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;

final class IfritMovement {

    static final int RUSH_SWEEP_FIRST = 12;

    private static final float DMG_COMBO4_WAVE = 25.0F;
    private static final double WALTZ_LEAP_SPEED = 0.46D;
    private static final double COMBO_ADVANCE_SPEED = 0.42D;
    private static final double COMBO_ADVANCE_MAX_DISTANCE = 9.0D;
    private static final double COMBO_ADVANCE_STOP_RANGE = 2.0D;
    private static final double QUAKE_RADIUS = 10.0D;
    private static final double QUAKE_START = 0.4D;
    private static final int QUAKE_SAMPLES = 16;
    private static final float DMG_DASH = 13.0F;
    private static final double DASH_SWEEP_RADIUS = 2.6D;
    private static final double DASH_FINISH_RADIUS = 5.0D;
    private static final double RUSH_EARLY_FINISH_RADIUS = DASH_FINISH_RADIUS;
    private static final int RUSH_EARLY_SEEK_TICK = 21;

    private IfritMovement() {
    }

    static void leapOff(Ifrit mob) {
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(movement.x, 0.6D, movement.z);
        mob.hasImpulse = true;
    }

    static void waltzLeapStep(Ifrit mob, boolean takeOff) {
        mob.getNavigation().stop();
        if (takeOff) {
            leapOff(mob);
        }
        if (mob.waltzLeapDir == Vec3.ZERO) {
            Vec3 look = mob.getLookAngle();
            mob.waltzLeapDir = new Vec3(look.x, 0.0D, look.z).normalize();
        }
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(mob.waltzLeapDir.x * WALTZ_LEAP_SPEED, movement.y,
                mob.waltzLeapDir.z * WALTZ_LEAP_SPEED);
        mob.hasImpulse = true;
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME, mob.getX(), mob.getY() + 0.4D, mob.getZ(),
                    5, 0.4D, 0.2D, 0.4D, 0.02D);
        }
    }

    static void dashStep(Ifrit mob, double strength, SimpleParticleType trail) {
        mob.getNavigation().stop();
        Vec3 look = mob.getLookAngle();
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(look.x * strength, movement.y, look.z * strength);
        mob.hasImpulse = true;
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(trail, mob.getX(), mob.getY() + 1.2D, mob.getZ(),
                    4, 0.3D, 0.5D, 0.3D, 0.02D);
        }
    }

    static void chaseStep(Ifrit mob) {
        LivingEntity target = mob.chaseTarget;
        if (target == null || !target.isAlive()) {
            return;
        }
        mob.getNavigation().stop();
        mob.getLookControl().setLookAt(target, 60.0F, 60.0F);
        Vec3 delta = target.position().subtract(mob.position());
        double length = delta.length();
        if (length < 2.0D) {
            mob.setDeltaMovement(0.0D, mob.getDeltaMovement().y, 0.0D);
            return;
        }
        Vec3 dir = delta.normalize();
        mob.setDeltaMovement(dir.x * 1.15D, Math.max(mob.getDeltaMovement().y, 0.0D), dir.z * 1.15D);
        mob.hasImpulse = true;
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME, mob.getX(), mob.getY() + 1.2D, mob.getZ(),
                    6, 0.3D, 0.5D, 0.3D, 0.04D);
        }
    }

    static void sideStep(Ifrit mob, double side) {
        if (mob.dodgeOrigin == Vec3.ZERO) {
            mob.dodgeOrigin = mob.position();
        }
        mob.dodgeTravelled = mob.position().distanceTo(mob.dodgeOrigin);
        if (mob.dodgeTravelled >= IfritDodge.MAX_DISTANCE) {
            mob.setDeltaMovement(0.0D, mob.getDeltaMovement().y, 0.0D);
            return;
        }
        mob.getNavigation().stop();
        Vec3 look = mob.getLookAngle();
        double sx = look.z * side;
        double sz = -look.x * side;
        float progress = Mth.clamp((mob.getActionTick() - IfritDodge.MOVE_FIRST_TICK)
                / (float) (IfritDodge.MOVE_LAST_TICK - IfritDodge.MOVE_FIRST_TICK), 0.0F, 1.0F);
        double strength = IfritDodge.STEP_STRENGTH * (1.15D - 0.6D * progress);
        mob.setDeltaMovement(sx * strength, mob.getDeltaMovement().y,
                sz * strength);
        mob.hasImpulse = true;
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SMOKE, mob.getX(), mob.getY() + 1.0D, mob.getZ(),
                    8, 0.3D, 0.4D, 0.3D, 0.02D);
        }
    }

    static void holdStill(Ifrit mob) {
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(0.0D, movement.y, 0.0D);
        mob.hasImpulse = true;
    }

    static void comboAdvance(Ifrit mob) {
        if (mob.getTarget() != null && mob.getTarget().isAlive()
                && mob.distanceTo(mob.getTarget()) <= COMBO_ADVANCE_STOP_RANGE) {
            holdStill(mob);
            return;
        }
        if (mob.comboAdvanceOrigin == null) {
            mob.comboAdvanceOrigin = mob.position();
        }
        double dx = mob.getX() - mob.comboAdvanceOrigin.x;
        double dz = mob.getZ() - mob.comboAdvanceOrigin.z;
        if (dx * dx + dz * dz >= COMBO_ADVANCE_MAX_DISTANCE * COMBO_ADVANCE_MAX_DISTANCE) {
            holdStill(mob);
            return;
        }
        mob.getNavigation().stop();
        Vec3 look = mob.getLookAngle();
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(look.x * COMBO_ADVANCE_SPEED, movement.y, look.z * COMBO_ADVANCE_SPEED);
        mob.hasImpulse = true;
    }

    static void dashStrike(Ifrit mob, int tick, int sweepFirst, int sweepLast, int finishTick,
            float finishHealPerTarget) {
        if (tick >= sweepFirst && tick <= sweepLast) {
            int swept = IfritCombat.hitDashSweep(mob, DASH_SWEEP_RADIUS, DMG_DASH);
            if (finishHealPerTarget > 0.0F && tick == finishTick) {
                IfritCombat.healFromTargets(mob, swept, finishHealPerTarget);
            }
        }
        if (tick == finishTick) {
            int cut = IfritCombat.hitDashSweep(mob, DASH_FINISH_RADIUS, DMG_DASH);
            if (finishHealPerTarget > 0.0F) {
                IfritCombat.healFromTargets(mob, cut, finishHealPerTarget);
            }
            mob.burst(ParticleTypes.FLAME, 22, 0.06D);
            mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.5F, 0.7F);
        }
    }

    static boolean tryEarlyRushFinish(Ifrit mob, int tick) {
        if (mob.rushEarlyFinish) {
            return false;
        }
        if (tick < RUSH_SWEEP_FIRST || tick >= IfritCombo.DEFS[IfritCombo.Action.RUSH.ordinal()].hit) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive() || !IfritCombat.canHit(mob, target)) {
            return false;
        }
        double dx = target.getX() - mob.getX();
        double dz = target.getZ() - mob.getZ();
        if (dx * dx + dz * dz > RUSH_EARLY_FINISH_RADIUS * RUSH_EARLY_FINISH_RADIUS) {
            return false;
        }
        mob.rushEarlyFinish = true;
        mob.getNavigation().stop();
        holdStill(mob);
        if (tick < RUSH_EARLY_SEEK_TICK - 1) {
            mob.getEntityData().set(Ifrit.DATA_ACTION_TICK, RUSH_EARLY_SEEK_TICK - 1);
        }
        return true;
    }

    static void tickWipeQuake(Ifrit mob) {
        if (mob.quakeTicks <= 0) {
            return;
        }
        mob.quakeTicks--;
        if (!(mob.level() instanceof ServerLevel server)) {
            mob.quakeTicks = 0;
            return;
        }
        Vec3 dir = mob.quakeDir.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 0.0D, 1.0D) : mob.quakeDir;
        Vec3 side = new Vec3(-dir.z, 0.0D, dir.x);
        double step = (QUAKE_RADIUS - QUAKE_START) / Math.max(1, IfritCombat.QUAKE_TICKS - 1);
        double front = Math.min(QUAKE_RADIUS, QUAKE_START + step * mob.quakeAge);
        float damage = DMG_COMBO4_WAVE;

        for (int i = 0; i < QUAKE_SAMPLES; i++) {
            double ratio = QUAKE_SAMPLES <= 1 ? 0.5D : (double) i / (QUAKE_SAMPLES - 1);
            double angle = -Math.PI / 2.0D + ratio * Math.PI;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double px = mob.quakeOriginX + (dir.x * cos + side.x * sin) * front;
            double pz = mob.quakeOriginZ + (dir.z * cos + side.z * sin) * front;
            double ground = groundAt(mob, px, pz, mob.quakeRefY + 1.5D);
            BlockPos pos = BlockPos.containing(px, ground - 0.15D, pz);
            BlockState state = mob.level().getBlockState(pos);
            if (state.isAir()) {
                state = mob.level().getBlockState(pos.below());
            }
            if (state.isAir()) {
                continue;
            }
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    px, ground + 0.2D, pz, 5, 0.28D, 0.18D, 0.28D, 0.08D);
            server.sendParticles(ParticleTypes.CLOUD, px, ground + 0.15D, pz,
                    1, 0.18D, 0.06D, 0.18D, 0.02D);
            server.sendParticles(ParticleTypes.LAVA, px, ground + 0.3D, pz,
                    3, 0.25D, 0.10D, 0.25D, 0.0D);
            if (mob.getRandom().nextInt(2) == 0) {
                server.addFreshEntity(new FlungBlockEntity(mob.level(), px, ground + 0.1D, pz,
                        state, 0.35F + mob.getRandom().nextFloat() * 0.4F));
            }
        }

        mob.shieldBreakStrike = true;
        AABB box = new AABB(mob.quakeOriginX - front - 1.0D, mob.quakeRefY - 4.0D,
                mob.quakeOriginZ - front - 1.0D, mob.quakeOriginX + front + 1.0D,
                mob.quakeRefY + 5.0D, mob.quakeOriginZ + front + 1.0D);
        boolean hit = false;
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, box)) {
            if (!IfritCombat.canHit(mob, victim) || mob.quakeHits.contains(victim.getId())) {
                continue;
            }
            double dx = victim.getX() - mob.quakeOriginX;
            double dz = victim.getZ() - mob.quakeOriginZ;
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance > front || (dx * dir.x + dz * dir.z) < 0.0D) {
                continue;
            }
            double ground = groundAt(mob, victim.getX(), victim.getZ(), mob.quakeRefY + 1.5D);
            if (victim.getY() < ground - 2.0D || victim.getY() > ground + 3.0D) {
                continue;
            }
            if (!mob.quakeHits.add(victim.getId())) {
                continue;
            }
            if (!IfritCombat.hitVictim(mob, victim,
                    damage + victim.getMaxHealth() * IfritCombat.COMBO4_SLAM_HEALTH_RATIO)) {
                mob.quakeHits.remove(victim.getId());
                continue;
            }
            IfritCombat.stun(mob, victim);
            Vec3 push = distance < 1.0E-4D ? dir : new Vec3(dx / distance, 0.0D, dz / distance);
            victim.push(push.x * 1.1D * IfritCombat.KNOCKBACK_SCALE, 0.5D * IfritCombat.KNOCKBACK_SCALE,
                    push.z * 1.1D * IfritCombat.KNOCKBACK_SCALE);
            hit = true;
        }
        mob.shieldBreakStrike = false;
        if (hit) {
            IfritSounds.playImpactSound(mob);
        }
        mob.quakeAge++;
    }

    static double groundAt(Ifrit mob, double x, double z, double refY) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        pos.set(Mth.floor(x), Mth.floor(refY), Mth.floor(z));
        for (int i = 0; i < 8; i++) {
            if (!mob.level().getBlockState(pos).isAir()) {
                return pos.getY() + 1.0D;
            }
            pos.move(0, -1, 0);
        }
        return refY;
    }
}
