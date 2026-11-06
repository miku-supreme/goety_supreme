package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public final class CombatGoal extends Goal {

    private final AbstractSoulReaper mob;

    private int moveRecalc = 10;

    CombatGoal(AbstractSoulReaper mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive() && !this.mob.isCasting();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.moveRecalc = 0;
    }

    @Override
    public void tick() {
        AbstractSoulReaper mob = this.mob;
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        double distSqr = mob.distanceToSqr(target);
        mob.tryGapClose(target, distSqr);
        if (distSqr > AbstractSoulReaper.MELEE_APPROACH_SQR) {
            if (--this.moveRecalc <= 0 || mob.getNavigation().isDone()) {
                this.moveRecalc = 10;
                mob.getNavigation().moveTo(target, 1.0D);
            }
        } else {
            mob.getNavigation().stop();
        }
        if (distSqr <= AbstractSoulReaper.MELEE_RANGE_SQR && mob.attackCooldown <= 0
                && !mob.isCasting() && mob.hasLineOfSight(target)) {
            mob.startCast(AbstractSoulReaper.CAST_ATTACK);
            mob.attackCooldown = AbstractSoulReaper.ATTACK_COOLDOWN;
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }
}
