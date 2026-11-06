package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

final class PlayerMobServantThreat {

    private static final int FOCUS_FIRE_LONG_WINDOW_TICKS = 40;
    private static final float PANIC_ESCAPE_HP_FRACTION = 0.25F;
    private static final int FOCUS_FIRE_COOLDOWN_TICKS = 60;
    private static final int PARTING_SHOT_COOLDOWN_TICKS = 40;
    private static final double ATTACKER_SCAN_RADIUS = 16.0D;

    private final PlayerMobServant mob;

    PlayerMobServantThreat(PlayerMobServant mob) {
        this.mob = mob;
    }

    public boolean isHostile() {
        return false;
    }

    public void setHostile(boolean hostile) {
    }

    public void checkHostility() {
    }

    public boolean isValidCombatTargetForGoal(Entity entity) {
        if (!(entity instanceof LivingEntity living) || living == mob || !living.isAlive()) {
            return false;
        }
        if (living == mob.getTrueOwner() || mob.isAllyWith(living)) {
            return false;
        }
        if (living instanceof Player player && player.isCreative()) {
            return false;
        }
        return !mob.isAlliedTo(living);
    }

    public boolean isHostileTowardServant(LivingEntity entity) {
        if (entity == null || entity == mob || !entity.isAlive()
                || !this.isValidCombatTargetForGoal(entity)) {
            return false;
        }
        if (entity instanceof Mob mobEntity && mobEntity.getTarget() == mob) {
            return true;
        }
        return entity instanceof NeutralMob neutral && neutral.isAngryAt(mob);
    }

    public List<LivingEntity> nearbyAttackers(double radius) {
        if (mob.tickCount != mob.attackerCacheTick) {
            mob.attackerCacheTick = mob.tickCount;
            List<LivingEntity> scanned = new ArrayList<>();
            for (LivingEntity entity : mob.level().getEntitiesOfClass(LivingEntity.class,
                    mob.getBoundingBox().inflate(ATTACKER_SCAN_RADIUS))) {
                if (this.isHostileTowardServant(entity)) {
                    scanned.add(entity);
                }
            }
            scanned.sort(java.util.Comparator.comparingDouble(mob::distanceTo));
            mob.attackerCache = scanned;
        }
        if (radius >= ATTACKER_SCAN_RADIUS) {
            return mob.attackerCache;
        }
        double sqr = radius * radius;
        List<LivingEntity> filtered = new ArrayList<>();
        for (LivingEntity entity : mob.attackerCache) {
            if (mob.distanceToSqr(entity) <= sqr) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    @Nullable
    public LivingEntity nearestThreat(@Nullable LivingEntity target, double radius) {
        LivingEntity nearest = target != null && target.isAlive() ? target : null;
        double best = nearest == null ? Double.MAX_VALUE : mob.distanceTo(nearest);
        for (LivingEntity attacker : this.nearbyAttackers(radius)) {
            double distance = mob.distanceTo(attacker);
            if (distance < best) {
                best = distance;
                nearest = attacker;
            }
        }
        return nearest;
    }

    void recordHurt(float damage) {
        mob.recentHurtTicks.addLast(mob.tickCount);
        if (damage > 0.0F) {
            mob.hurtDamageSinceEscape += damage;
        }
        this.pruneHurtTicks();
    }

    private void pruneHurtTicks() {
        while (!mob.recentHurtTicks.isEmpty()
                && mob.tickCount - mob.recentHurtTicks.peekFirst() > FOCUS_FIRE_LONG_WINDOW_TICKS) {
            mob.recentHurtTicks.pollFirst();
        }
    }

    public boolean isUnderFocusFire() {
        return this.isUnderPanicEscape() || this.hurtCountWithin(FOCUS_FIRE_LONG_WINDOW_TICKS) >= 4;
    }

    public boolean isUnderPanicEscape() {
        return !mob.panicEscapeLatched
                && mob.hurtDamageSinceEscape >= mob.getMaxHealth() * PANIC_ESCAPE_HP_FRACTION;
    }

    private int hurtCountWithin(int windowTicks) {
        this.pruneHurtTicks();
        int count = 0;
        for (int tick : mob.recentHurtTicks) {
            if (mob.tickCount - tick <= windowTicks) {
                count++;
            }
        }
        return count;
    }

    public boolean isFocusFireEscapeReady() {
        return mob.focusFireEscapeCooldown <= 0;
    }

    public void markFocusFireEscaped() {
        mob.panicEscapeLatched = mob.hurtDamageSinceEscape >= mob.getMaxHealth() * PANIC_ESCAPE_HP_FRACTION;
        mob.focusFireEscapeCooldown = FOCUS_FIRE_COOLDOWN_TICKS;
        mob.recentHurtTicks.clear();
        mob.hurtDamageSinceEscape = 0.0F;
    }

    public boolean isPartingShotReady() {
        return mob.partingShotCooldown <= 0;
    }

    public void markPartingShotUsed() {
        mob.partingShotCooldown = PARTING_SHOT_COOLDOWN_TICKS;
    }
}
