package cn.blockforge.generated.goetysupreme.ai;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;

public class FollowSerponCombatExecuterGoal extends Goal {

    private static final double SEARCH_XZ = 64.0D;
    private static final double SEARCH_Y = 32.0D;

    private static final double MIN_START_DISTANCE_SQR = 128.0D;

    private static final double MIN_FOLLOW_DISTANCE = 16.0D;
    private static final double MAX_FOLLOW_DISTANCE = 1024.0D;

    private static final int SEARCH_INTERVAL = 10;

    private final Mob follower;
    private final double speedModifier;
    @Nullable
    private LivingEntity executer;
    private int timeToRecalcPath;

    public FollowSerponCombatExecuterGoal(Mob follower, double speedModifier) {
        this.follower = follower;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof SerponCombatExecuter || follower.isPassenger()) {
            return false;
        }
        if (SEARCH_INTERVAL > 1 && follower.getRandom().nextInt(SEARCH_INTERVAL) != 0) {
            return false;
        }
        AABB area = follower.getBoundingBox().inflate(SEARCH_XZ, SEARCH_Y, SEARCH_XZ);
        LivingEntity nearest = null;
        double nearestDistanceSqr = Double.MAX_VALUE;
        for (LivingEntity candidate : follower.level().getEntitiesOfClass(
                LivingEntity.class, area, AbstractSerpon::isSerponCombatExecuter)) {
            double distanceSqr = follower.distanceToSqr(candidate);
            if (distanceSqr < nearestDistanceSqr) {
                nearestDistanceSqr = distanceSqr;
                nearest = candidate;
            }
        }
        if (nearest == null || nearestDistanceSqr < MIN_START_DISTANCE_SQR) {
            return false;
        }
        this.executer = nearest;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (executer == null || !executer.isAlive() || follower.getTarget() != null) {
            return false;
        }
        double distance = follower.distanceTo(executer);
        return distance >= MIN_FOLLOW_DISTANCE && distance <= MAX_FOLLOW_DISTANCE;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.executer = null;
    }

    @Override
    public void tick() {
        if (executer == null) {
            return;
        }
        if (--this.timeToRecalcPath > 0) {
            return;
        }
        this.timeToRecalcPath = this.adjustedTickDelay(10);
        this.follower.getNavigation().moveTo(executer, this.speedModifier);
    }
}
