package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class PlayerMobThrowAttackGoal extends Goal {

    private static final double THROW_RANGE = 12.0D;
    private static final double THROW_RANGE_SQR = THROW_RANGE * THROW_RANGE;

    private static final double MAX_THROW_RANGE_SQR = 20.0D * 20.0D;

    private static final double ESCAPE_RANGE = 4.0D;
    private static final double ESCAPE_RANGE_SQR = ESCAPE_RANGE * ESCAPE_RANGE;

    private static final int THROW_INTERVAL_TICKS = 20;

    private static final int FLEE_REPATH_TICKS = 20;

    private final PlayerMobServant mob;
    private int throwCooldown;
    private int seeTime;
    private int fleeRepath;
    @Nullable
    private Vec3 fleePos;

    public PlayerMobThrowAttackGoal(PlayerMobServant mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.isThrowEngagement() && !mob.isEnderPearlEngagement() && hasValidTarget();
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isThrowEngagement() && !mob.isEnderPearlEngagement()
                && (hasValidTarget() || !mob.getNavigation().isDone());
    }

    private boolean hasValidTarget() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && mob.isValidCombatTargetForGoal(target);
    }

    @Override
    public void start() {
        mob.setAggressive(true);
        throwCooldown = 10;
        seeTime = 0;
        fleeRepath = 0;
        fleePos = null;
    }

    @Override
    public void stop() {
        mob.setAggressive(false);
        mob.getNavigation().stop();
        mob.setCombatChaseSprint(false);
        seeTime = 0;
        fleeRepath = 0;
        fleePos = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }
        boolean canSee = mob.getSensing().hasLineOfSight(target);
        seeTime = canSee ? seeTime + 1 : 0;
        double distanceSqr = mob.distanceToSqr(target);
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (mob.isThrowEscapeEngagement()) {
            tickEscape(target, distanceSqr);
        } else {
            tickMeleeSideThrow(target, distanceSqr);
        }

        if (throwCooldown > 0) {
            throwCooldown--;
        }
        if (canSee && throwCooldown <= 0 && distanceSqr <= MAX_THROW_RANGE_SQR) {
            if (mob.performThrowAttack(target)) {
                throwCooldown = THROW_INTERVAL_TICKS;
            } else {
                throwCooldown = 5;
            }
        }
    }

    private void tickEscape(LivingEntity target, double distanceSqr) {
        if (distanceSqr < ESCAPE_RANGE_SQR) {
            if (fleePos == null || --fleeRepath <= 0) {
                fleePos = LandRandomPos.getPosAway(mob, 16, 7, target.position());
                fleeRepath = FLEE_REPATH_TICKS;
            }
            mob.setCombatChaseSprint(true);
            if (fleePos != null) {
                mob.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.2D);
            } else {
                mob.getNavigation().stop();
            }
        } else if (distanceSqr > THROW_RANGE_SQR) {
            fleePos = null;
            mob.setCombatChaseSprint(true);
            mob.getNavigation().moveTo(target, 1.0D);
        } else {
            fleePos = null;
            mob.setCombatChaseSprint(false);
            mob.getNavigation().stop();
        }
    }

    private void tickMeleeSideThrow(LivingEntity target, double distanceSqr) {
        if (distanceSqr > THROW_RANGE_SQR) {
            mob.setCombatChaseSprint(true);
            mob.getNavigation().moveTo(target, 1.0D);
        } else {
            mob.setCombatChaseSprint(false);
            mob.getNavigation().stop();
        }
    }
}
