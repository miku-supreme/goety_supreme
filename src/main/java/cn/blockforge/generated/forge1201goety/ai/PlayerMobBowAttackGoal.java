package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;

public final class PlayerMobBowAttackGoal extends Goal {

    private static final int MIN_REDRAW_GAP_TICKS = 1;

    private static final double STRAFE_STANDOFF = 16.0D;

    private static final double STRAFE_DEAD_BAND = 1.5D;

    private static final float STRAFE_DRIVE = 1.0F;

    private static final float STRAFE_SIDE = 0.6F;
    private final PlayerMobServant mob;
    private final double speedModifier;
    private int attackIntervalMin;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private int strafingTime = -1;

    public PlayerMobBowAttackGoal(PlayerMobServant mob, double speedModifier, int attackIntervalMin,
            float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackIntervalMin = attackIntervalMin;
        this.attackRadiusSqr = attackRadius * attackRadius;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return isHoldingBow() && target != null && target.isAlive()
                && mob.isValidCombatTargetForGoal(target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        return isHoldingBow() && target != null && target.isAlive()
                && mob.isValidCombatTargetForGoal(target)
                && (canUse() || !mob.getNavigation().isDone());
    }

    @Override
    public void start() {
        super.start();
        mob.setAggressive(true);
        attackTime = -1;
        strafingTime = -1;
    }

    @Override
    public void stop() {
        super.stop();
        mob.setAggressive(false);
        mob.getNavigation().stop();
        mob.stopUsingItem();
        mob.setCombatChaseSprint(false);
        attackTime = -1;
        strafingTime = -1;
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

        double distance = mob.distanceToSqr(target);
        boolean canSee = mob.getSensing().hasLineOfSight(target);
        boolean sawTarget = seeTime > 0;
        if (canSee != sawTarget) {
            seeTime = 0;
        }
        seeTime += canSee ? 1 : -1;

        if (distance <= attackRadiusSqr && seeTime >= 20) {
            mob.getNavigation().stop();
            strafingTime++;
            mob.setCombatChaseSprint(false);
        } else {
            mob.getNavigation().moveTo(target, speedModifier);
            strafingTime = -1;
            mob.setCombatChaseSprint(!mob.isUsingItem());
        }

        if (strafingTime >= 20) {
            if (mob.getRandom().nextFloat() < 0.3F) {
                strafingClockwise = !strafingClockwise;
            }
            strafingTime = 0;
        }

        if (strafingTime > -1) {
            double range = Math.sqrt(distance);
            float forward;
            if (range < STRAFE_STANDOFF - STRAFE_DEAD_BAND) {
                forward = -STRAFE_DRIVE;
            } else if (range > STRAFE_STANDOFF + STRAFE_DEAD_BAND) {
                forward = STRAFE_DRIVE;
            } else {
                forward = 0.0F;
            }
            float side = forward == 0.0F ? STRAFE_DRIVE : STRAFE_SIDE;
            mob.getMoveControl().strafe(forward, strafingClockwise ? side : -side);
            Entity vehicle = mob.getControlledVehicle();
            if (vehicle instanceof net.minecraft.world.entity.Mob vehicleMob) {
                vehicleMob.lookAt(target, 30.0F, 30.0F);
            }
            mob.lookAt(target, 30.0F, 30.0F);
        } else {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        if (mob.isUsingItem()) {
            if (!canSee && seeTime < -60) {
                mob.stopUsingItem();
                return;
            }
            if (!canSee) {
                return;
            }

            int ticksUsingItem = mob.getTicksUsingItem();
            boolean crystalBow = SakuraGearCompat.isCrystalBow(mob.getMainHandItem());
            int releaseTicks = crystalBow ? SakuraGearCompat.BOW_OVERCHARGE_TICKS : 20;
            if (ticksUsingItem >= releaseTicks) {
                if (crystalBow) {
                    mob.releaseUsingItem();
                } else {
                    mob.stopUsingItem();
                    mob.performRangedAttack(target, BowItem.getPowerForTime(ticksUsingItem));
                }
                attackTime = Math.max(MIN_REDRAW_GAP_TICKS, attackIntervalMin - releaseTicks);
            }
        } else if (--attackTime <= 0 && seeTime >= -60) {
            InteractionHand hand = bowHand();
            if (hand != null) {
                mob.startUsingItem(hand);
            }
        }
    }

    private boolean isHoldingBow() {
        return mob.getMainHandItem().getItem() instanceof BowItem;
    }

    private InteractionHand bowHand() {
        return isHoldingBow() ? InteractionHand.MAIN_HAND : null;
    }
}
