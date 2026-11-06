package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SupportType;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;

public final class PlayerMobCrossbowAttackGoal extends Goal {
    private enum State {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK
    }

    private static final float BACK_UP_ON_LAND = -3.0F;
    private static final float BACK_UP_IN_WATER = -0.5F;

    private static final double SPRINT_RANGE = 20.0D;

    private final PlayerMobServant mob;
    private final double speedModifier;
    private final float attackRadius;
    private State state = State.UNCHARGED;
    private int seeTime;
    private int attackDelay;
    private int updatePathDelay;

    public PlayerMobCrossbowAttackGoal(PlayerMobServant mob, double speedModifier, float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackRadius = attackRadius;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return isValidTarget() && isHoldingCrossbow();
    }

    @Override
    public boolean canContinueToUse() {
        return isValidTarget() && isHoldingCrossbow()
                && (canUse() || !mob.getNavigation().isDone());
    }

    @Override
    public void start() {
        state = CrossbowItem.isCharged(mob.getMainHandItem()) ? State.CHARGED : State.UNCHARGED;
        seeTime = 0;
        attackDelay = 0;
        updatePathDelay = 0;
        mob.setChargingCrossbow(false);
    }

    @Override
    public void stop() {
        super.stop();
        if (mob.isUsingItem()) {
            ItemStack useItem = mob.getUseItem();
            mob.stopUsingItem();
            if (useItem.getItem() instanceof CrossbowItem) {
                CrossbowItem.setCharged(useItem, false);
            }
        }
        mob.setChargingCrossbow(false);
        state = State.UNCHARGED;
        seeTime = 0;
        attackDelay = 0;
        updatePathDelay = 0;
        mob.setCombatChaseSprint(false);
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

        double distance = Math.sqrt(mob.distanceToSqr(target));
        boolean tooFar = distance > attackRadius;
        if (!tooFar && standingOnSolidGround()) {
            mob.getMoveControl().strafe(mob.isInWater() ? BACK_UP_IN_WATER : BACK_UP_ON_LAND, 0.0F);
        }
        boolean positioning = tooFar || seeTime < 5;
        if (positioning && state != State.CHARGED) {
            if (--updatePathDelay <= 0) {
                double speed = state == State.UNCHARGED ? speedModifier : speedModifier * 0.5D;
                mob.getNavigation().moveTo(target, speed);
                updatePathDelay = 20;
            }
            mob.setCombatChaseSprint(distance > SPRINT_RANGE && !mob.isUsingItem());
        } else {
            updatePathDelay = 0;
            mob.getNavigation().stop();
            mob.setCombatChaseSprint(false);
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        switch (state) {
            case UNCHARGED -> {
                if (canSee && isHoldingCrossbow() && !CrossbowItem.isCharged(mob.getMainHandItem())) {
                    mob.startUsingItem(InteractionHand.MAIN_HAND);
                    mob.setChargingCrossbow(true);
                    state = State.CHARGING;
                }
            }
            case CHARGING -> {
                if (!mob.isUsingItem()) {
                    mob.setChargingCrossbow(false);
                    state = State.UNCHARGED;
                    return;
                }

                ItemStack crossbow = mob.getUseItem();
                if (!(crossbow.getItem() instanceof CrossbowItem)) {
                    mob.stopUsingItem();
                    mob.setChargingCrossbow(false);
                    state = State.UNCHARGED;
                    return;
                }

                if (mob.getTicksUsingItem() >= CrossbowItem.getChargeDuration(crossbow)) {
                    mob.releaseUsingItem();
                    mob.setChargingCrossbow(false);
                    if (CrossbowItem.isCharged(crossbow)) {
                        state = State.CHARGED;
                        attackDelay = 5;
                    } else {
                        state = State.UNCHARGED;
                    }
                }
            }
            case CHARGED -> {
                if (!CrossbowItem.isCharged(mob.getMainHandItem())) {
                    state = State.UNCHARGED;
                    return;
                }
                if (attackDelay > 0) {
                    attackDelay--;
                }
                if (attackDelay <= 0) {
                    state = State.READY_TO_ATTACK;
                }
            }
            case READY_TO_ATTACK -> {
                if (!CrossbowItem.isCharged(mob.getMainHandItem())) {
                    state = State.UNCHARGED;
                    return;
                }
                if (!canSee) {
                    return;
                }
                ItemStack crossbow = mob.getMainHandItem();
                if (SakuraGearCompat.isCrystalCrossbow(crossbow)) {
                    SakuraGearCompat.shootCrystalCrossbow(mob, InteractionHand.MAIN_HAND, crossbow);
                } else {
                    mob.performRangedAttack(target, 1.0F);
                }
                CrossbowItem.setCharged(crossbow, false);
                state = State.UNCHARGED;
            }
        }
    }

    private boolean standingOnSolidGround() {
        return mob.getBlockStateOn().isFaceSturdy(mob.level(), mob.blockPosition().above(),
                Direction.UP, SupportType.CENTER);
    }

    private boolean isHoldingCrossbow() {
        return mob.getMainHandItem().getItem() instanceof CrossbowItem;
    }

    private boolean isValidTarget() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && mob.isValidCombatTargetForGoal(target);
    }
}
