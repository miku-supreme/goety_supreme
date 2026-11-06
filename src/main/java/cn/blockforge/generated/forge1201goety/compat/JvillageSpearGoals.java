package cn.blockforge.generated.goetysupreme.compat;

import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.jerotes.jerotes.goal.JerotesPikeUseGoal;
import com.jerotes.jerotes.goal.JerotesSpearUseGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;

public final class JvillageSpearGoals {

    private static final int PRIORITY = 0;

    private JvillageSpearGoals() {
    }

    public static void attachIfEligible(GoalSelector goalSelector, PathfinderMob mob) {
        if (!canHoldJerotesWeapon(mob)) {
            return;
        }
        boolean yieldToThrow = mob instanceof PlayerMobServant;
        if (!hasGoalOfType(goalSelector, JerotesSpearUseGoal.class)) {
            Goal spear = new JerotesSpearUseGoal<>(mob, 1.0D, 1.0D, 10.0F, 2.0F);
            goalSelector.addGoal(PRIORITY,
                    yieldToThrow ? new ThrowYieldGoal((PlayerMobServant) mob, spear) : spear);
        }
        if (!hasGoalOfType(goalSelector, JerotesPikeUseGoal.class)) {
            Goal pike = new JerotesPikeUseGoal(mob, 1.2D, true);
            goalSelector.addGoal(PRIORITY,
                    yieldToThrow ? new ThrowYieldGoal((PlayerMobServant) mob, pike) : pike);
        }
    }

    private static final class ThrowYieldGoal extends Goal {

        private final PlayerMobServant servant;
        private final Goal delegate;

        private ThrowYieldGoal(PlayerMobServant servant, Goal delegate) {
            this.servant = servant;
            this.delegate = delegate;
            setFlags(delegate.getFlags());
        }

        @Override
        public boolean canUse() {
            return !servant.isThrowEngagement() && !servant.isEnderPearlEngagement()
                    && delegate.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !servant.isThrowEngagement() && !servant.isEnderPearlEngagement()
                    && delegate.canContinueToUse();
        }

        @Override
        public boolean isInterruptable() {
            return delegate.isInterruptable();
        }

        @Override
        public void start() {
            delegate.start();
            servant.setCombatChaseSprint(servant.isSpearChasing());
        }

        @Override
        public void stop() {
            delegate.stop();
            servant.setCombatChaseSprint(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return delegate.requiresUpdateEveryTick();
        }

        @Override
        public void tick() {
            servant.setCombatChaseSprint(servant.isSpearChasing());
            delegate.tick();
        }
    }

    private static boolean canHoldJerotesWeapon(PathfinderMob mob) {
        if (mob instanceof WitherSkeletonServant) {
            return true;
        }
        if (mob instanceof PlayerMobServant
                || mob instanceof SecondRounderZombieServant
                || mob instanceof SecondRounderSkeletonServant
                || mob instanceof GoetyZombieServant
                || mob instanceof GoetySkeletonServant) {
            return true;
        }
        if (mob instanceof AbstractSerpon && !(mob instanceof SerponCombatExecuter)) {
            return true;
        }
        return mob instanceof AbstractSedimentServant
                && !(mob instanceof SedimentLordServant);
    }

    private static boolean hasGoalOfType(GoalSelector goalSelector, Class<?> goalType) {
        for (WrappedGoal wrapped : goalSelector.getAvailableGoals()) {
            Goal goal = wrapped.getGoal();
            if (goalType.isInstance(goal)
                    || (goal instanceof ThrowYieldGoal guard && goalType.isInstance(guard.delegate))) {
                return true;
            }
        }
        return false;
    }
}
