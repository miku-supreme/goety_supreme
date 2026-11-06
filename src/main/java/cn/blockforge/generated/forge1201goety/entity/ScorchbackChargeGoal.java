package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.util.ScorchbackCombatMath;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class ScorchbackChargeGoal extends Goal {
    private final Scorchback mob;

    ScorchbackChargeGoal(Scorchback mob) {
        this.mob = mob;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isBusy() || this.mob.chargeCooldown > 0) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double distance = this.mob.distanceTo(target);
        return distance >= 5.0D && distance <= 22.0D
                && this.mob.getSensing().hasLineOfSight(target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getAction() == Action.CHARGE;
    }

    @Override
    public void start() {
        this.mob.startAction(Action.CHARGE);
        this.mob.chargeCooldown = Scorchback.CHARGE_TICKS + ScorchbackCombatMath.CHARGE_COOLDOWN;
    }
}
