package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;

public class PlayerMobMeleeAttackGoal extends Goal {

    private static final long CAN_USE_CHECK_INTERVAL = 20L;

    private static final float SPACING_BACKSTEP = -0.6F;

    private static final double CLOSE_CHASE_SQR = 16.0D;

    private static final double CHASE_DETOUR_STEP = 4.0D;

    public final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;

    public PlayerMobMeleeAttackGoal(PathfinderMob mob, double speedModifier,
            boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long now = this.mob.level().getGameTime();
        if (now - this.lastCanUseCheck < CAN_USE_CHECK_INTERVAL) {
            return false;
        }
        this.lastCanUseCheck = now;
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        this.path = this.mob.getNavigation().createPath(target, 0);
        if (this.path != null) {
            return true;
        }
        return this.getAttackReachSqr(target) >= gapDistanceSqr(this.mob, target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        }
        if (!this.mob.isWithinRestriction(target.blockPosition())) {
            return false;
        }
        return !(target instanceof Player player) || !player.isSpectator() && !player.isCreative();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
        this.lastCanUseCheck = this.mob.level().getGameTime() - CAN_USE_CHECK_INTERVAL;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        double distance = gapDistanceSqr(this.mob, target);
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        if (!tickSpacingHold(target, distance)) {
            tickChase(target, distance);
        }
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        this.checkAndPerformAttack(target, distance);
    }

    private void tickChase(LivingEntity target, double distanceSqr) {
        boolean canSee = this.mob.getSensing().hasLineOfSight(target);
        if (!this.followingTargetEvenIfNotSeen && !canSee) {
            return;
        }
        Vec3 detour = ServantMineAvoidance.chaseWaypoint(this.mob, target.position(), CHASE_DETOUR_STEP);
        if (this.ticksUntilNextPathRecalculation <= 0
                && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
                        || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D
                        || this.mob.getRandom().nextFloat() < 0.05F)) {
            this.pathedTargetX = target.getX();
            this.pathedTargetY = target.getY();
            this.pathedTargetZ = target.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
            if (distanceSqr > 1024.0D) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (distanceSqr > 256.0D) {
                this.ticksUntilNextPathRecalculation += 5;
            }
            boolean pathed = detour != null
                    ? this.mob.getNavigation().moveTo(detour.x, detour.y, detour.z, this.speedModifier)
                    : this.mob.getNavigation().moveTo(target, this.speedModifier);
            if (!pathed) {
                if (isAirborne()) {
                    this.ticksUntilNextPathRecalculation = 0;
                    this.pathedTargetX = 0.0D;
                    this.pathedTargetY = 0.0D;
                    this.pathedTargetZ = 0.0D;
                } else {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }
            this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
        }
        if (this.mob.getNavigation().isDone() && canSee) {
            if (detour != null) {
                this.mob.getMoveControl().setWantedPosition(detour.x, detour.y, detour.z,
                        this.speedModifier);
            } else if (distanceSqr <= CLOSE_CHASE_SQR) {
                this.mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(),
                        this.speedModifier);
            }
        }
    }

    private boolean isAirborne() {
        return !this.mob.onGround() && !this.mob.isInWater() && !this.mob.isPassenger();
    }

    private boolean tickSpacingHold(LivingEntity target, double distanceSqr) {
        double bandMax = spacingBandMax(target);
        if (bandMax <= 0.0D || distanceSqr > bandMax * bandMax) {
            return false;
        }
        if (!this.mob.getSensing().hasLineOfSight(target)) {
            return false;
        }
        this.mob.getNavigation().stop();
        this.ticksUntilNextPathRecalculation = 0;
        this.pathedTargetX = 0.0D;
        this.pathedTargetY = 0.0D;
        this.pathedTargetZ = 0.0D;
        double bandMin = spacingBandMin(bandMax);
        if (distanceSqr < bandMin * bandMin) {
            this.mob.getMoveControl().strafe(SPACING_BACKSTEP, 0.0F);
        } else {
            this.mob.getMoveControl().strafe(0.0F, 0.0F);
        }
        return true;
    }

    private double spacingBandMax(LivingEntity target) {
        double reach = Math.sqrt(Math.max(0.0D, this.getAttackReachSqr(target)));
        double max = PlayerMobServantConfig.MELEE_KEEP_DISTANCE_MAX.get();
        return Math.max(0.0D, Math.min(max, reach));
    }

    private double spacingBandMin(double bandMax) {
        double min = PlayerMobServantConfig.MELEE_KEEP_DISTANCE_MIN.get();
        return Math.max(0.0D, Math.min(min, bandMax));
    }

    public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
        double reachSqr = this.getAttackReachSqr(target);
        if (distanceSqr <= reachSqr && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }
    }

    public void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    public boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    public int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    public int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }

    public double getAttackReachSqr(LivingEntity target) {
        if (this.mob instanceof PlayerMobServant servant) {
            return servant.meleeAttackReachSqr();
        }
        double reach = Math.max(0.5D, PlayerMobServantConfig.MELEE_ATTACK_RANGE.get());
        return reach * reach;
    }

    public static double gapDistanceSqr(Entity attacker, Entity target) {
        return gapDistanceSqr(attacker.getBoundingBox(), target.getBoundingBox());
    }

    public static double gapDistanceSqr(AABB own, AABB other) {
        double dx = Math.max(Math.max(own.minX - other.maxX, other.minX - own.maxX), 0.0D);
        double dy = Math.max(Math.max(own.minY - other.maxY, other.minY - own.maxY), 0.0D);
        double dz = Math.max(Math.max(own.minZ - other.maxZ, other.minZ - own.maxZ), 0.0D);
        return dx * dx + dy * dy + dz * dz;
    }
}
