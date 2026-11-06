package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public final class ControlGoal extends Goal {

    private final AbstractSoulReaper mob;

    ControlGoal(AbstractSoulReaper mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        AbstractSoulReaper mob = this.mob;
        LivingEntity target = mob.getTarget();
        if (mob.isCasting() || target == null || !target.isAlive() || mob.controlCooldown > 0) {
            return false;
        }
        if (!mob.hasCoffin() || mob.distanceToSqr(target) > AbstractSoulReaper.CONTROL_RANGE_SQR) {
            return false;
        }
        SoulCoffin coffin = mob.getCoffin();
        return coffin != null && coffin.distanceToSqr(target) <= 144.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        AbstractSoulReaper mob = this.mob;
        mob.controlCooldown = AbstractSoulReaper.CONTROL_COOLDOWN;
        SoulCoffin coffin = mob.getCoffin();
        mob.startCast(coffin != null && coffin.isDowned() ? AbstractSoulReaper.CAST_CONTROL_REVIVE : AbstractSoulReaper.CAST_CONTROL_ATTACK);
    }
}
