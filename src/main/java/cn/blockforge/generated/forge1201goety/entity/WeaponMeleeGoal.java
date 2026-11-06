package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

final class WeaponMeleeGoal extends MeleeAttackGoal {

    private final PrismarineSoldier soldier;

    WeaponMeleeGoal(PrismarineSoldier soldier, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(soldier, speedModifier, followingTargetEvenIfNotSeen);
        this.soldier = soldier;
    }

    @Override
    public boolean canUse() {
        return !this.soldier.holdsBow() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.soldier.holdsBow() && super.canContinueToUse();
    }

    @Override
    public void checkAndPerformAttack(LivingEntity target, double distToTargetSqr) {
        if (this.soldier.isAttackAnimating() || this.soldier.getMeleeCooldown() > 0) {
            return;
        }
        if (PlayerMobMeleeAttackGoal.gapDistanceSqr(this.soldier, target)
                <= this.soldier.meleeAnticipatedReachSqr(target) && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }
    }
}
