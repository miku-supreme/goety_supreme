package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetySummonedSpearGoalsMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class FellSpearAttackGoal extends Goal {

    private static final double ENGAGE_RANGE = 10.0D;
    private static final double ENGAGE_RANGE_SQR = ENGAGE_RANGE * ENGAGE_RANGE;

    private static final double CHARGE_SPEED = 1.0D;

    private static final double REPOSITION_SPEED = 1.0D;

    private static final double TARGET_IN_RANGE = 2.0D;
    private static final double TARGET_IN_RANGE_SQR = TARGET_IN_RANGE * TARGET_IN_RANGE;

    private static final double LOST_MARGIN = 6.0D;

    private static final int MAX_FLEEING_TICKS = 100;

    private static final int AWAY_VERTICAL = 7;

    private static final double AWAY_MID_FAR = 7.0D;

    private static final double AWAY_END_FAR = 11.0D;

    private static final int ATTACH_PRIORITY = 0;

    private final Mob mob;

    private int engageTime = -1;

    private int fleeingTime = -1;

    @Nullable
    private Vec3 awayPos;

    private boolean done;

    public FellSpearAttackGoal(Mob mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!holdsSpear()) {
            return false;
        }
        if (mob instanceof PlayerMobServant servant
                && (servant.isThrowEngagement() || servant.isEnderPearlEngagement())) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive() || target.isSpectator()) {
            return false;
        }
        return !mob.isUsingItem();
    }

    @Override
    public boolean canContinueToUse() {
        if (done || !holdsSpear()) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return mob.distanceTo(target) <= ENGAGE_RANGE + LOST_MARGIN;
    }

    @Override
    public void start() {
        mob.setAggressive(true);
        engageTime = -1;
        fleeingTime = -1;
        awayPos = null;
        done = false;
    }

    @Override
    public void stop() {
        if (mob.isUsingItem()) {
            mob.stopUsingItem();
        }
        mob.getNavigation().stop();
        mob.setAggressive(false);
        updateSprint(false);
        engageTime = -1;
        fleeingTime = -1;
        awayPos = null;
        done = false;
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
        double distanceSqr = mob.distanceToSqr(target);
        double distance = Math.sqrt(distanceSqr);
        int mounted = mob.isPassenger() ? 2 : 0;
        mob.lookAt(target, 30.0F, 30.0F);
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (engageTime < 0) {
            if (distanceSqr > ENGAGE_RANGE_SQR) {
                updateSprint(true);
                mob.getNavigation().moveTo(target, REPOSITION_SPEED);
                return;
            }
            engageTime = spearEngageTicks();
            mob.startUsingItem(InteractionHand.MAIN_HAND);
            FellSpearItem.playUseSound(mob);
            updateSprint(true);
        }

        if (engageTime > 0) {
            --engageTime;
            if (engageTime == 0) {
                mob.stopUsingItem();
                updateSprint(false);
                awayPos = pickAwayPosition(target, AWAY_END_FAR + mounted - distance);
                fleeingTime = 1;
            }
        }

        if (fleeingTime > 0) {
            ++fleeingTime;
            if (fleeingTime > MAX_FLEEING_TICKS) {
                done = true;
                return;
            }
        }

        if (awayPos != null) {
            updateSprint(false);
            mob.getNavigation().moveTo(awayPos.x, awayPos.y, awayPos.z, REPOSITION_SPEED);
            if (mob.getNavigation().isDone()) {
                if (fleeingTime > 0) {
                    done = true;
                    return;
                }
                awayPos = null;
            }
        } else {
            updateSprint(true);
            mob.getNavigation().moveTo(target, CHARGE_SPEED);
            if (distanceSqr < TARGET_IN_RANGE_SQR || mob.getNavigation().isDone()) {
                awayPos = pickAwayPosition(target, AWAY_MID_FAR + mounted - distance);
            }
        }
    }

    private static int spearEngageTicks() {
        return FellSpearItem.chargeDelayTicks() + FellSpearItem.damageWindowTicks();
    }

    @Nullable
    private Vec3 pickAwayPosition(LivingEntity target, double farDistance) {
        double horizontal = Math.max(1.0D, farDistance);
        if (mob instanceof PathfinderMob pathfinder) {
            Vec3 pos = LandRandomPos.getPosAway(pathfinder, (int) Math.round(horizontal),
                    AWAY_VERTICAL, target.position());
            if (pos != null) {
                return pos;
            }
        }
        Vec3 away = mob.position().subtract(target.position());
        Vec3 flat = new Vec3(away.x, 0.0D, away.z);
        if (flat.lengthSqr() < 1.0E-4D) {
            return null;
        }
        return mob.position().add(flat.normalize().scale(horizontal));
    }

    private void updateSprint(boolean wantSprint) {
        if (mob instanceof PlayerMobServant servant) {
            servant.setCombatChaseSprint(wantSprint);
        }
    }

    private boolean holdsSpear() {
        return FellSpearItem.isSpear(mob.getMainHandItem());
    }

    public static boolean isAttached(GoalSelector selector) {
        for (WrappedGoal wrapped : selector.getAvailableGoals()) {
            if (wrapped.getGoal() instanceof FellSpearAttackGoal) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }
        if (!isAttached(mob.goalSelector)) {
            mob.goalSelector.addGoal(ATTACH_PRIORITY, new FellSpearAttackGoal(mob));
        }
    }
}
