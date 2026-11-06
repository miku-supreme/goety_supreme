package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public final class BurstGoal extends Goal {

    private final AbstractSoulReaper mob;

    private boolean coffinSlamFirst;

    BurstGoal(AbstractSoulReaper mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        AbstractSoulReaper mob = this.mob;
        LivingEntity target = mob.getTarget();
        if (mob.getCastAnimation() != AbstractSoulReaper.CAST_NONE && mob.getCastAnimation() != AbstractSoulReaper.CAST_ATTACK) {
            return false;
        }
        if (target == null || !target.isAlive() || mob.burstCooldown > 0) {
            return false;
        }
        if (!this.hasEnemyInRadius(mob.position(), AbstractSoulReaper.BURST_TRIGGER_RADIUS)) {
            SoulCoffin coffin = mob.getCoffin();
            if (coffin == null || !this.hasEnemyInRadius(coffin.position(), AbstractSoulReaper.BURST_TRIGGER_RADIUS)) {
                return false;
            }
        }
        boolean hurt = mob.getHealth() <= mob.getMaxHealth() * 0.5F;
        this.coffinSlamFirst = false;
        if (hurt && !mob.halfHealthBurstUsed) {
            mob.halfHealthBurstUsed = true;
            SoulCoffin coffin = mob.getCoffin();
            if (coffin != null && !coffin.isDowned()) {
                mob.pendingBurstAfterCoffin = true;
                this.coffinSlamFirst = true;
            }
            return true;
        }
        return mob.getRandom().nextInt(hurt ? 10 : 20) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    private boolean hasEnemyInRadius(Vec3 center, double radius) {
        AbstractSoulReaper mob = this.mob;
        if (!(mob.level() instanceof ServerLevel server)) {
            return false;
        }
        net.minecraft.world.phys.AABB area = new net.minecraft.world.phys.AABB(
                center.x - radius, center.y - radius, center.z - radius,
                center.x + radius, center.y + radius, center.z + radius);
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive() && area.contains(target.position())) {
            return true;
        }
        for (LivingEntity entity : server.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity == mob || !entity.isAlive()
                    || entity.getType() == mob.getType()
                    || com.Polarice3.Goety.utils.MobUtil.areAllies(mob, entity)) {
                continue;
            }
            if (entity instanceof net.minecraft.world.entity.player.Player player
                    && (player.isCreative() || player.isSpectator())) {
                continue;
            }
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        AbstractSoulReaper mob = this.mob;
        com.mojang.logging.LogUtils.getLogger().info("[goety_supreme][soul-reaper] detonation");
        mob.burstCooldown = AbstractSoulReaper.BURST_COOLDOWN;
        if (this.coffinSlamFirst) {
            mob.controlCooldown = AbstractSoulReaper.CONTROL_COOLDOWN;
            mob.forceStartCast(AbstractSoulReaper.CAST_CONTROL_ATTACK);
        } else {
            mob.forceStartCast(AbstractSoulReaper.CAST_BURST);
        }
    }
}
