package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public final class FlyingWanderGoal extends Goal {

    private final AbstractSoulReaper mob;

    @Nullable
    private Vec3 targetPos;

    FlyingWanderGoal(AbstractSoulReaper mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        AbstractSoulReaper mob = this.mob;
        if (mob.getTarget() != null || mob.isCombatLocked()
                || mob.getNavigation().isInProgress()) {
            return false;
        }
        if (mob.getRandom().nextInt(60) != 0) {
            return false;
        }
        double range = 16.0D;
        double x = mob.getX() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
        double y = mob.getY() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
        double z = mob.getZ() + (mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
        this.targetPos = new Vec3(x,
                Mth.clamp(y, mob.level().getMinBuildHeight() + 2.0D,
                        mob.level().getMaxBuildHeight() - 2.0D),
                z);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        AbstractSoulReaper mob = this.mob;
        return this.targetPos != null && mob.getTarget() == null && !mob.isCasting()
                && !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        if (this.targetPos != null) {
            this.mob.getNavigation()
                    .moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, 0.8D);
        }
    }

    @Override
    public void stop() {
        this.targetPos = null;
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.targetPos != null && this.mob.getNavigation().isDone()) {
            this.stop();
        }
    }
}
