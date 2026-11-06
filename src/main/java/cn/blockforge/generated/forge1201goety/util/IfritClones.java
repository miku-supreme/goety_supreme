package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

final class IfritClones {

    static final int MODE_NONE = 0;
    static final int MODE_COMBO = 1;
    static final int MODE_ECHO = 2;
    static final int MODE_DANCE = 3;

    static final int ECHO_DELAY = 20;
    static final int BALLROOM_COOLDOWN_TICKS = 375;
    static final int BALLROOM_CLONES = 6;
    static final int BALLROOM_SPAWN_INTERVAL = 10;

    private static final float HEALTH = 80.0F;
    private static final int FREE_LIFETIME_TICKS = 300;
    private static final int COOLDOWN_TICKS = 750;
    private static final int DANCE_LOCK_TICKS = 20;

    private IfritClones() {
    }

    static List<Ifrit> ownedLightDanceClones(Ifrit mob) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return List.of();
        }
        List<Ifrit> clones = new ArrayList<>();
        for (Ifrit other : server.getEntitiesOfClass(Ifrit.class,
                mob.getBoundingBox().inflate(64.0D))) {
            if (other != mob && other.isAlive() && other.isClone()
                    && other.cloneMode != MODE_DANCE
                    && mob.getUUID().equals(other.cloneOwnerUuid)) {
                clones.add(other);
            }
        }
        return clones;
    }

    static boolean hasLivingLightDanceClone(Ifrit mob) {
        return !ownedLightDanceClones(mob).isEmpty();
    }

    static boolean mirrorsMaster(Ifrit mob) {
        return mob.isClone() && mob.cloneMode != MODE_DANCE && !mob.isPhaseTwo();
    }

    static boolean hasFreeWill(Ifrit mob) {
        return mob.isClone() && mob.cloneMode == MODE_COMBO
                && mob.comboFinished && mob.isPhaseTwo();
    }

    static void spawn(Ifrit mob) {
        if (!(mob.level() instanceof ServerLevel server) || mob.isClone()) {
            return;
        }
        if (hasLivingLightDanceClone(mob)) {
            return;
        }
        Ifrit clone = ModEntityTypes.IFRIT.get().create(server);
        if (clone == null) {
            return;
        }
        mob.openingClonePending = false;
        mob.openingCloneUsed = true;
        mob.cloneCooldown = COOLDOWN_TICKS;
        boolean echo = !mob.isPhaseTwo();
        double angle = mob.getRandom().nextDouble() * Math.PI * 2.0D;
        double x = mob.getX() + Math.cos(angle) * 3.0D;
        double z = mob.getZ() + Math.sin(angle) * 3.0D;
        spawnAt(mob, server, clone, x, z, echo ? MODE_ECHO : MODE_COMBO);
        if (echo) {
            clone.setEchoAction(mob.lastSkillAction);
            clone.mirroredAction = mob.lastSkillAction;
            clone.startEchoDelay();
        }
    }

    static void spawnDanceClone(Ifrit mob, int index) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        Ifrit clone = ModEntityTypes.IFRIT.get().create(server);
        if (clone == null) {
            return;
        }
        Vec3 look = mob.getLookAngle().multiply(1.0D, 0.0D, 1.0D);
        double base = look.lengthSqr() < 1.0E-6D ? 0.0D : Math.atan2(look.z, look.x);
        double angle = base + index * (Math.PI / 3.0D);
        double x = mob.getX() + Math.cos(angle) * 3.0D;
        double z = mob.getZ() + Math.sin(angle) * 3.0D;
        spawnAt(mob, server, clone, x, z, MODE_DANCE);
        clone.danceLockTicks = DANCE_LOCK_TICKS;
    }

    private static void spawnAt(Ifrit mob, ServerLevel server, Ifrit clone, double x, double z, int mode) {
        clone.moveTo(x, mob.getY(), z, mob.getYRot(), 0.0F);
        clone.setClone(true);
        clone.setCloneOwner(mob);
        clone.setCloneMode(mode);
        AttributeInstance maxHealth = clone.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(HEALTH);
        }
        AttributeInstance armor = clone.getAttribute(Attributes.ARMOR);
        if (armor != null) {
            armor.setBaseValue(0.0D);
        }
        AttributeInstance toughness = clone.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughness != null) {
            toughness.setBaseValue(0.0D);
        }
        clone.setHealth(HEALTH);
        clone.setGlowingTag(true);
        clone.setPersistenceRequired();
        if (mob.getTarget() != null) {
            clone.setTarget(mob.getTarget());
        }
        server.addFreshEntity(clone);
        server.sendParticles(ParticleTypes.FLAME, x, mob.getY() + 1.0D, z,
                30, 0.4D, 0.8D, 0.4D, 0.06D);
        mob.playSound(SoundEvents.BLAZE_SHOOT, 1.4F, 0.6F);
    }

    static void poof(Ifrit mob) {
        mob.burst(ParticleTypes.FLAME, 30, 0.08D);
        mob.discard();
    }

    static void poofAllClones(Ifrit master) {
        if (!(master.level() instanceof ServerLevel server)) {
            return;
        }
        for (Ifrit other : server.getEntitiesOfClass(Ifrit.class,
                master.getBoundingBox().inflate(128.0D))) {
            if (other != master && other.isClone()
                    && master.getUUID().equals(other.cloneOwnerUuid)) {
                poof(other);
            }
        }
    }

    static void tick(Ifrit mob) {
        if (mob.cloneMode == MODE_DANCE) {
            tickDanceClone(mob);
            return;
        }
        if (mirrorsMaster(mob)) {
            tickMirrorMaster(mob);
            return;
        }
        if (mob.cloneMode == MODE_COMBO) {
            if (mob.comboFinished && mob.getAction() == IfritCombo.Action.NONE && mob.cloneFreeTicks <= 0) {
                mob.cloneFreeTicks = FREE_LIFETIME_TICKS;
            }
            if (mob.cloneFreeTicks > 0) {
                mob.cloneFreeTicks--;
                if (mob.cloneFreeTicks <= 0) {
                    poof(mob);
                    return;
                }
            }
            if (!mob.scriptedComboStarted && mob.getAction() == IfritCombo.Action.NONE) {
                mob.scriptedComboStarted = true;
                IfritCombo.startAction(mob, IfritCombo.Action.COMBO_START);
            }
        } else if (mob.cloneMode == MODE_ECHO) {
            tickMirrorMaster(mob);
        }
    }

    static void tickMirrorMaster(Ifrit mob) {
        Ifrit owner = mob.resolveCloneOwner();
        if (owner != null && owner.isAlive() && owner.lastSkillAction != mob.mirroredAction) {
            mob.mirroredAction = owner.lastSkillAction;
            mob.echoAction = owner.lastSkillAction;
            mob.echoDelayTicks = ECHO_DELAY;
            mob.echoReleased = false;
        }
        if (mob.echoDelayTicks > 0) {
            mob.echoDelayTicks--;
        }
        if (mob.echoDelayTicks <= 0 && !mob.echoReleased && mob.getAction() == IfritCombo.Action.NONE) {
            mob.echoReleased = true;
            IfritCombo.startAction(mob, mob.echoAction == null ? IfritCombo.Action.MELEE : mob.echoAction);
        }
    }

    static void tickDanceClone(Ifrit mob) {
        if (mob.getAction() == IfritCombo.Action.DASH) {
            mob.danceDashing = true;
            return;
        }
        if (mob.danceDashing) {
            poof(mob);
            return;
        }
        if (mob.danceLockTicks > 0) {
            mob.danceLockTicks--;
            mob.getNavigation().stop();
            IfritMovement.holdStill(mob);
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive()) {
                mob.getLookControl().setLookAt(target, 60.0F, 60.0F);
            }
            if (mob.danceLockTicks == 0 && !mob.danceDashing) {
                // 到点无条件开 DASH：被闪避/其它动作占用也会在动作结束后重新走到这里，
                // 不会因为 getAction()!=NONE 错过触发导致永不消失
                IfritCombo.startAction(mob, IfritCombo.Action.DASH);
            }
        }
        // 硬寿命兜底：舞会分身最多存活 220 刻，无论如何都会消失
        if (++mob.danceAge > 220) {
            poof(mob);
        }
    }

    static void tickOpeningClone(Ifrit mob) {
        if (mob.isClone() || mob.openingCloneUsed) {
            return;
        }
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive()) {
            mob.openingClonePending = true;
        }
        if (!mob.openingClonePending) {
            return;
        }
        if (mob.cloneCooldown > 0) {
            return;
        }
        if (hasLivingLightDanceClone(mob)) {
            return;
        }
        if (mob.getAction() != IfritCombo.Action.NONE) {
            return;
        }
        IfritCombo.startAction(mob, IfritCombo.Action.CLONE);
    }
}
