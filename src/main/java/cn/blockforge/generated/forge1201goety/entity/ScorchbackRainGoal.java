package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class ScorchbackRainGoal extends Goal {
    private final Scorchback mob;

    ScorchbackRainGoal(Scorchback mob) {
        this.mob = mob;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isBusy() || this.mob.rainCooldown > 0) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double distance = this.mob.distanceTo(target);
        return distance >= 5.0D && distance <= 20.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getAction() == Action.RAIN;
    }

    @Override
    public void start() {
        this.mob.startAction(Action.RAIN);
        this.mob.rainCooldown = Scorchback.RAIN_TICKS + 100;
    }
}
