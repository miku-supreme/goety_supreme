package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;

final class MutantRoarGoal extends Goal {
    private final MutantZombieServant servant;

    MutantRoarGoal(MutantZombieServant servant) {
        this.servant = servant;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!MutantZombieServantConfig.SUMMON_ZOMBIE_SERVANTS.get()
                || servant.getTrueOwner() == null
                || servant.tickCount < servant.nextRoarSummonTick) {
            return false;
        }
        LivingEntity target = servant.getTarget();
        boolean base = target != null
                && servant.onGround()
                && !servant.isAnimationPlaying()
                && servant.resurrectionList.isEmpty()
                && (servant.soulJarUpgrade || servant.countZombieServantsForOwner()
                        < MutantZombieServantConfig.SUMMON_SERVANT_CAP.get());
        if (base && servant.isGuaranteedRoarDue()) {
            return true;
        }
        return base && servant.tickCount % 3 == 0
                && (servant.soulJarUpgrade || servant.distanceToSqr(target) > 16.0D
                        || servant.getHealth() < servant.getMaxHealth() * 0.5F)
                && servant.getRandom().nextFloat() * 100.0F < (servant.soulJarUpgrade
                        ? MutantZombieServantConfig.SOUL_JAR_ROAR_CHANCE_PERCENT.get() : 2.2D);
    }

    @Override
    public void start() {
        servant.animationTarget = servant.getTarget();
        servant.roarSummonScheduled = 0;
        servant.fulfillGuaranteedRoar();
        servant.nextRoarSummonTick = (long) servant.tickCount
                + MutantZombieServantConfig.SUMMON_SERVANT_COOLDOWN_SECONDS.get() * 20L;
        servant.startAnimation(MutantZombieServant.ROAR_ANIMATION);
    }

    @Override
    public boolean canContinueToUse() {
        return servant.getAnimation() == MutantZombieServant.ROAR_ANIMATION;
    }

    @Override
    public void tick() {
        servant.getNavigation().stop();
        int tick = servant.getAnimationTick();
        LivingEntity target = servant.getTarget();
        if (tick < 75 && target != null) {
            servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }
}
