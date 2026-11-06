package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class ScorchbackCombatGoal extends Goal {
    private final Scorchback mob;

    private int ticksUntilNextPathRecalculation;

    ScorchbackCombatGoal(Scorchback mob) {
        this.mob = mob;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isBusy() || this.mob.comboCooldown > 0 || this.mob.slamCooldown > 0) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (this.mob.distanceToSqr(target) > Scorchback.MELEE_REACH * Scorchback.MELEE_REACH) {
            return false;
        }
        if (--this.ticksUntilNextPathRecalculation <= 0) {
            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
            this.mob.getNavigation().moveTo(target, 1.2D);
        }
        return this.mob.getSensing().hasLineOfSight(target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getAction() == Action.COMBO || this.mob.getAction() == Action.SLAM;
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
        if (this.mob.getRandom().nextFloat() < 0.7F) {
            this.mob.startAction(Action.COMBO);
            this.mob.comboCooldown = 50;
        } else {
            this.mob.startAction(Action.SLAM);
            this.mob.slamCooldown = 60;
        }
    }
}
