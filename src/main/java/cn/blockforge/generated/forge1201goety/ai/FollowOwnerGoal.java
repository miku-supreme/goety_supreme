package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.api.entities.ally.IServant;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;

public final class FollowOwnerGoal extends Goal {

    private static final int RECALC_INTERVAL = 10;

    private final PathfinderMob mob;
    private final IServant servant;
    private final double speed;
    private final float startDistance;
    private final float stopDistance;
    private LivingEntity owner;
    private int timeToRecalcPath;

    private float oldWaterCost;

    public FollowOwnerGoal(PathfinderMob mob, double speed, float startDistance, float stopDistance) {
        this.mob = mob;
        this.servant = (IServant) Objects.requireNonNull(mob);
        this.speed = speed;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.owner = this.servant.getTrueOwner();
        return this.owner != null && this.servant.isFollowing() && !this.servant.isCommanded()
                && this.mob.getTarget() == null
                && this.mob.distanceToSqr(this.owner) > (double) (this.startDistance * this.startDistance);
    }

    @Override
    public boolean canContinueToUse() {
        return this.owner != null && this.servant.isFollowing() && !this.servant.isCommanded()
                && this.mob.getTarget() == null
                && this.mob.distanceToSqr(this.owner) > (double) (this.stopDistance * this.stopDistance);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
        this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.owner, 10.0F, this.mob.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = RECALC_INTERVAL;
            if (!this.mob.isLeashed() && !this.mob.isPassenger()) {
                this.mob.getNavigation().moveTo(this.owner, this.speed);
            }
        }
    }
}
