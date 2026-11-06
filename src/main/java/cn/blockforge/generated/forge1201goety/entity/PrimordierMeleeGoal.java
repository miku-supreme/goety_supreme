package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

final class PrimordierMeleeGoal extends Goal {

    private final PrimordierServant mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    @Nullable
    private net.minecraft.world.level.pathfinder.Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;

    PrimordierMeleeGoal(PrimordierServant mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long now = this.mob.level().getGameTime();
        if (now - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = now;
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        this.path = this.mob.getNavigation().createPath(target, 0);
        return this.path != null || this.mob.getAttackBoundingBox().intersects(target.getBoundingBox());
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
        return !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative());
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
        LivingEntity target = this.mob.getTarget();
        if (target != null && !net.minecraft.world.entity.EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.mob.setTarget(null);
        }
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
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
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        boolean seen = this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target);
        if (seen && this.ticksUntilNextPathRecalculation <= 0
                && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
                        || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D
                        || this.mob.getRandom().nextFloat() < 0.05F)) {
            this.pathedTargetX = target.getX();
            this.pathedTargetY = target.getY();
            this.pathedTargetZ = target.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
            double distance = this.mob.distanceToSqr(target);
            if (distance > 1024.0D) {
                this.ticksUntilNextPathRecalculation += 20;
            } else if (distance > 256.0D) {
                this.ticksUntilNextPathRecalculation += 5;
            }
            if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }
            this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
        }
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        if (this.ticksUntilNextAttack <= 0 && this.mob.isWithinMeleeAttackRange(target)
                && this.mob.getSensing().hasLineOfSight(target) && !this.mob.specialAction()) {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
            this.mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }
    }
}
