package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;

public final class WitheringSkeletonFollowGoal extends Goal {
    public static final double FOLLOW_RANGE = 32.0D;
    public static final double START_DISTANCE = 24.0D;
    private final WitherSkeletonServant minion;
    private LivingEntity owner;
    private boolean chasing;
    private double stopDistance;
    private int recalc;
    private int wanderTicks;

    public WitheringSkeletonFollowGoal(WitherSkeletonServant minion) {
        this.minion = minion;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        owner = WitheringMinionControl.summoner(minion);
        return owner != null && owner.isAlive() && minion.isFollowing()
                && !minion.isCommanded() && minion.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() { return true; }

    @Override
    public void start() {
        stopDistance = 4.0D + minion.getRandom().nextDouble() * 4.0D;
        chasing = horizontalDistanceSqr() >= START_DISTANCE * START_DISTANCE;
        recalc = 0;
        wanderTicks = 20;
    }

    @Override
    public void stop() {
        owner = null;
        minion.getNavigation().stop();
    }

    private double horizontalDistanceSqr() {
        return minion.distanceToSqr(owner.getX(), minion.getY(), owner.getZ());
    }

    @Override
    public void tick() {
        if (!(minion.level() instanceof ServerLevel server) || owner == null) return;
        double distance = horizontalDistanceSqr();
        if (distance >= START_DISTANCE * START_DISTANCE) chasing = true;
        if (chasing && distance <= stopDistance * stopDistance) {
            chasing = false;
            minion.getNavigation().stop();
            wanderTicks = 20;
        }
        if (chasing) {
            if (--recalc > 0) return;
            recalc = 10;
            if (distance > FOLLOW_RANGE * FOLLOW_RANGE && moveNearOwner(server, true)) {
                chasing = false;
                wanderTicks = 20;
                return;
            }
            double y = MinionGroundPlacement.groundFeetY(server, owner.getX(), owner.getZ(),
                    minion.getY(), MinionGroundPlacement.SKELETON_CLEARANCE);
            minion.getNavigation().moveTo(owner.getX(), y, owner.getZ(), 1.0D);
        } else if (distance <= 8.0D * 8.0D && --wanderTicks <= 0) {
            moveNearOwner(server, false);
            wanderTicks = 60 + minion.getRandom().nextInt(60);
        }
    }

    private boolean moveNearOwner(ServerLevel server, boolean teleport) {
        for (int attempt = 0; attempt < 8; attempt++) {
            double angle = minion.getRandom().nextDouble() * Math.PI * 2.0D;
            double radius = 4.0D + minion.getRandom().nextDouble() * 4.0D;
            double x = owner.getX() + Math.cos(angle) * radius;
            double z = owner.getZ() + Math.sin(angle) * radius;
            int y = (int) Math.floor(MinionGroundPlacement.groundFeetY(server, x, z,
                    minion.getY(), MinionGroundPlacement.SKELETON_CLEARANCE));
            if (!MinionGroundPlacement.isStandable(server, x, z, y,
                    MinionGroundPlacement.SKELETON_CLEARANCE)) continue;
            if (teleport) {
                minion.teleportTo(x, y, z);
                minion.getNavigation().stop();
                minion.resetFallDistance();
                return true;
            }
            if (minion.getNavigation().moveTo(x, y, z, 0.8D)) return true;
        }
        return false;
    }
}
