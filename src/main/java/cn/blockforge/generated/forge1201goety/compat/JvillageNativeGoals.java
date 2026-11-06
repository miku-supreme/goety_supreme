package cn.blockforge.generated.goetysupreme.compat;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.WizardEntity;
import com.jerotes.jerotes.goal.JerotesAddSpellAttackGoal;
import com.jerotes.jerotes.goal.JerotesFlyingRandomStrollGoal;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMainSpellAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedBowAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedJavelinAttackGoal;
import com.jerotes.jerotes.goal.JerotesRangedThrowAttackGoal;
import java.lang.reflect.Method;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import cn.blockforge.generated.goetysupreme.ai.SerponFlyingStrollGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;

public final class JvillageNativeGoals {

    private static final int RANGED_PRIORITY = 0;

    private static final int HELP_PRIORITY = 1;

    private static final int SPELL_PRIORITY = 1;

    private static final float DEFAULT_REACH = 15.0F;

    private static Method meleeOrRangeMethod;

    private JvillageNativeGoals() {
    }

    public static boolean attachRanged(GoalSelector goalSelector, Mob mob) {
        if (!(mob instanceof RangedAttackMob)) {
            return false;
        }
        RangedAttackMob shooter = (RangedAttackMob) mob;
        Mob shooterMob = (Mob & RangedAttackMob) mob;
        float reach = meleeOrRangeDistance(mob);
        if (!hasGoalOfType(goalSelector, JerotesRangedBowAttackGoal.class)) {
            goalSelector.addGoal(RANGED_PRIORITY,
                    new JerotesRangedBowAttackGoal(shooterMob, 0.5D, 20, 15.0F));
        }
        if (!hasGoalOfType(goalSelector, JerotesRangedJavelinAttackGoal.class)) {
            goalSelector.addGoal(RANGED_PRIORITY,
                    new JerotesRangedJavelinAttackGoal(shooter, 1.0D, 60, 12.0F, reach));
        }
        if (!hasGoalOfType(goalSelector, JerotesRangedThrowAttackGoal.class)) {
            goalSelector.addGoal(RANGED_PRIORITY,
                    new JerotesRangedThrowAttackGoal(shooter, 0.4D, 40, 12.0F));
        }
        return true;
    }

    public static boolean attachBow(GoalSelector goalSelector, Mob mob) {
        if (!(mob instanceof RangedAttackMob)) {
            return false;
        }
        if (!hasGoalOfType(goalSelector, JerotesRangedBowAttackGoal.class)) {
            Mob shooterMob = (Mob & RangedAttackMob) mob;
            goalSelector.addGoal(RANGED_PRIORITY,
                    new JerotesRangedBowAttackGoal(shooterMob, 0.5D, 20, 15.0F));
        }
        return true;
    }

    public static void attachSpellAttack(GoalSelector goalSelector, Mob mob, int spellLevel) {
        if (!(mob instanceof WizardEntity)) {
            return;
        }
        if (!hasGoalOfType(goalSelector, JerotesMainSpellAttackGoal.class)) {
            goalSelector.addGoal(SPELL_PRIORITY,
                    new JerotesMainSpellAttackGoal(mob, spellLevel, 60, 240, 0.5F));
        }
        if (!hasGoalOfType(goalSelector, JerotesAddSpellAttackGoal.class)) {
            goalSelector.addGoal(SPELL_PRIORITY,
                    new JerotesAddSpellAttackGoal(mob, spellLevel, 180, 240, 0.5F));
        }
    }

    public static void attachFactionHelp(GoalSelector targetSelector, Mob mob) {
        if (!hasGoalOfType(targetSelector, JerotesHelpSameFactionGoal.class)) {
            targetSelector.addGoal(HELP_PRIORITY, new JerotesHelpSameFactionGoal(
                    mob, LivingEntity.class, false, false,
                    livingEntity -> livingEntity instanceof LivingEntity));
        }
        if (!hasGoalOfType(targetSelector, JerotesHelpAlliesGoal.class)) {
            targetSelector.addGoal(HELP_PRIORITY, new JerotesHelpAlliesGoal(
                    mob, LivingEntity.class, false, false,
                    livingEntity -> livingEntity instanceof LivingEntity));
        }
    }

    public static Goal flyingStroll(AbstractJerotesServant servant, double speed, int interval) {
        return new GuardedGoal(new JerotesFlyingRandomStrollGoal(servant, speed, interval)) {
            @Override
            public boolean guardAllows() {
                return servant.isNoGravity()
                        && servant.getTarget() == null
                        && !servant.isStaying()
                        && !servant.isCommanded();
            }
        };
    }

    private abstract static class GuardedGoal extends Goal {

        private final Goal delegate;

        @SuppressWarnings({"unchecked", "rawtypes"})
        public GuardedGoal(Goal delegate) {
            this.delegate = delegate;
            this.setFlags(delegate.getFlags());
        }

        public abstract boolean guardAllows();

        @Override
        public final boolean canUse() {
            return guardAllows() && delegate.canUse();
        }

        @Override
        public final boolean canContinueToUse() {
            return guardAllows() && delegate.canContinueToUse();
        }

        @Override
        public final boolean isInterruptable() {
            return delegate.isInterruptable();
        }

        @Override
        public final void start() {
            delegate.start();
        }

        @Override
        public final void stop() {
            delegate.stop();
        }

        @Override
        public final boolean requiresUpdateEveryTick() {
            return delegate.requiresUpdateEveryTick();
        }

        @Override
        public final void tick() {
            delegate.tick();
        }
    }

    private static float meleeOrRangeDistance(Mob mob) {
        if (!(mob instanceof InventoryEntity)) {
            return DEFAULT_REACH;
        }
        try {
            if (meleeOrRangeMethod == null) {
                meleeOrRangeMethod = InventoryEntity.class.getMethod("meleeOrRangeDistance");
            }
            Object value = meleeOrRangeMethod.invoke(mob);
            return value instanceof Float ? (Float) value : DEFAULT_REACH;
        } catch (ReflectiveOperationException | RuntimeException | LinkageError fallback) {
            return DEFAULT_REACH;
        }
    }

    private static boolean hasGoalOfType(GoalSelector goalSelector, Class<? extends Goal> type) {
        for (WrappedGoal wrapped : goalSelector.getAvailableGoals()) {
            if (type.isInstance(wrapped.getGoal())) {
                return true;
            }
        }
        return false;
    }
}
