package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.AmphibiousGroundNavigation;

public class AmphibiousGroundNavigation extends GroundPathNavigation {

    public AmphibiousGroundNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    public boolean canUpdatePath() {
        return true;
    }

    @Override
    public Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    }

    @Override
    public double getGroundY(Vec3 pos) {
        return pos.y;
    }

    @Override
    public boolean canMoveDirectly(Vec3 pos, Vec3 target) {
        return isInLiquid() && isClearForMovementBetween(this.mob, pos, target, false);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return !this.level.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean hasValidPathType(BlockPathTypes pathType) {
        return pathType == BlockPathTypes.WATER
                || pathType == BlockPathTypes.LAVA
                || pathType == BlockPathTypes.OPEN
                || super.hasValidPathType(pathType);
    }

    @Override
    public void setCanFloat(boolean canFloat) {
    }
}
