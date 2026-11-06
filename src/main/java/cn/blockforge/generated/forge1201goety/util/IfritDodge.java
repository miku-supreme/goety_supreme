package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.utils.ModDamageSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

final class IfritDodge {

    static final double MAX_DISTANCE = 10.0D;
    static final double STEP_STRENGTH = 0.95D;
    static final int MOVE_FIRST_TICK = 1;
    static final int MOVE_LAST_TICK = 12;
    static final double APPROACH_RANGE = 8.0D;
    static final int APPROACH_DASH_COOLDOWN = 60;
    static final float CUT_CHANCE = 0.5F;
    static final double SLASH_RANGE = 4.5D;

    private static final int COMBAT_COOLDOWN = 10;
    static final int STREAK_FOR_COMBO = 2;
    private static final double ENDER_WATCHER_TRIGGER_RANGE = 6.0D;
    private static final int ENDER_WATCHER_TRIGGER_COOLDOWN = 200;

    private IfritDodge() {
    }

    static boolean tryDodge(Ifrit mob, DamageSource source, float amount) {
        if (mob.level().isClientSide || mob.isClone()) {
            return false;
        }
        if (mob.ifritInvulTicks > 0) {
            return false;
        }
        if (!isDodgeable(source)) {
            return false;
        }
        if (mob.isDodging()) {
            return true;
        }
        if (!canStartDodge(mob)) {
            return false;
        }
        startSideDodge(mob);
        return true;
    }

    static void tryEnderWatcherDodge(Ifrit mob) {
        if (mob.level().isClientSide || mob.isClone() || mob.enderWatcherCooldown > 0) {
            return;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        if (mob.distanceTo(target) >= ENDER_WATCHER_TRIGGER_RANGE) {
            return;
        }
        if (mob.getAction() != IfritCombo.Action.NONE) {
            return;
        }
        mob.enderWatcherCooldown = ENDER_WATCHER_TRIGGER_COOLDOWN;
        startSideDodge(mob);
    }

    static void startSideDodge(Ifrit mob) {
        mob.dodgeCooldown = mob.getTarget() != null ? COMBAT_COOLDOWN : 0;
        mob.dodgeOrigin = mob.position();
        mob.dodgeTravelled = 0.0D;
        if (mob.comboDodgeArmed || mob.dodgeStreak >= STREAK_FOR_COMBO) {
            mob.comboDodgeArmed = true;
            IfritCombo.Action armed = resolveArmedDodge(mob);
            if (armed == IfritCombo.Action.COMBO_DODGE_START) {
                IfritCombo.startAction(mob, IfritCombo.Action.COMBO_DODGE_START);
            } else {
                IfritCombo.startAction(mob, armed);
            }
            mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.4F);
            return;
        }
        IfritCombo.startAction(mob, mob.getRandom().nextBoolean() ? IfritCombo.Action.DODGE_R : IfritCombo.Action.DODGE_L);
        mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.4F);
    }

    private static IfritCombo.Action resolveArmedDodge(Ifrit mob) {
        LivingEntity target = mob.getTarget();
        boolean far = target != null && target.isAlive()
                && mob.distanceTo(target) > APPROACH_RANGE;
        if (!far) {
            return IfritCombo.Action.COMBO_DODGE_START;
        }
        if (!mob.approachDodgeSpent) {
            mob.approachDodgeSpent = true;
            return mob.getRandom().nextBoolean() ? IfritCombo.Action.DODGE_R : IfritCombo.Action.DODGE_L;
        }
        if (mob.approachDashCooldown <= 0) {
            mob.approachDodgeSpent = false;
            mob.approachDashCooldown = APPROACH_DASH_COOLDOWN;
            return mob.getRandom().nextBoolean() ? IfritCombo.Action.DASH : IfritCombo.Action.RUSH;
        }
        return IfritCombo.Action.COMBO_DODGE_START;
    }

    static boolean canStartDodge(Ifrit mob) {
        IfritCombo.Action action = mob.getAction();
        if (action == IfritCombo.Action.SPAWN) {
            return false;
        }
        if (action == IfritCombo.Action.NONE) {
            return true;
        }
        return switch (action) {
            case COMBO_START, COMBO_DODGE_START, COMBO1, COMBO2, COMBO3, COMBO4,
                    COMBO_BLOCK, COMBO_CHASE, COMBO_REST -> false;
            case DASH, RUSH, DASH_REC -> false;
            case CLONE, BALLROOM, GRAND_CURTAIN -> false;
            default -> mob.dodgeCooldown <= 0;
        };
    }

    static boolean isDashInvulnerable(Ifrit mob) {
        IfritCombo.Action action = mob.getAction();
        int tick = mob.getActionTick();
        if (action == IfritCombo.Action.DASH) {
            return tick >= 1 && tick <= 24;
        }
        if (action == IfritCombo.Action.RUSH) {
            return tick >= 1 && tick <= 26;
        }
        return false;
    }

    private static boolean isDodgeable(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_PROJECTILE)
                || source.getDirectEntity() instanceof Projectile
                || source.getDirectEntity() instanceof AbstractArrow) {
            return true;
        }
        if (ModDamageSource.hellfireAttacks(source)) {
            return true;
        }
        String id = damageTypePath(source);
        return id.contains("laser") || id.contains("corrupt") || id.contains("beam")
                || id.contains("cloud") || id.contains("brew") || id.contains("potion")
                || id.contains("gas") || id.contains("acid") || id.contains("venom")
                || id.contains("hellfire");
    }

    private static String damageTypePath(DamageSource source) {
        net.minecraft.core.Holder<?> holder = source.typeHolder();
        return holder.unwrapKey()
                .map(key -> key.location().getPath().toLowerCase(java.util.Locale.ROOT))
                .orElse("");
    }
}
