package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class ScorchbackApproachGoal extends Goal {
    private final Scorchback mob;
    private int repath;

    ScorchbackApproachGoal(Scorchback mob) {
        this.mob = mob;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.validChaseTarget();
        if (target == null) {
            return false;
        }
        return this.mob.distanceToSqr(target) > Scorchback.MELEE_REACH * Scorchback.MELEE_REACH * 0.64D
                || !this.mob.getSensing().hasLineOfSight(target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.validChaseTarget();
        if (target == null) {
            return false;
        }
        return this.mob.distanceToSqr(target) > Scorchback.MELEE_REACH * Scorchback.MELEE_REACH * 0.49D
                || !this.mob.getSensing().hasLineOfSight(target);
    }

    @Override
    public void start() {
        this.repath = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (--this.repath <= 0) {
            this.repath = 8;
            this.mob.getNavigation().moveTo(target, Scorchback.CHASE_SPEED);
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }
}
