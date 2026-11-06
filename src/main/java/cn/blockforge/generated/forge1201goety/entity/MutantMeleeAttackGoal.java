package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

final class MutantMeleeAttackGoal extends MeleeAttackGoal {
    private final MutantZombieServant servant;

    MutantMeleeAttackGoal(MutantZombieServant servant, double speed) {
        super(servant, speed, true);
        this.servant = servant;
    }

    @Override
    public boolean canUse() {
        return !servant.isAnimationPlaying() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !servant.isAnimationPlaying() && super.canContinueToUse();
    }

    @Override
    public void checkAndPerformAttack(LivingEntity enemy, double distSqr) {
        if (distSqr <= getAttackReachSqr(enemy)
                || servant.getBoundingBox().intersects(enemy.getBoundingBox())) {
            servant.doHurtTarget(enemy);
        }
    }
}
