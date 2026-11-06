package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class ThunderBowGoal extends Goal {

    private final PrismarineSoldier soldier;

    ThunderBowGoal(PrismarineSoldier soldier) {
        this.soldier = soldier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.soldier.holdsBow()
                && this.soldier.getTarget() != null
                && this.soldier.getThunderCooldown() <= 0
                && this.soldier.onGround()
                && !this.soldier.isUsingItem()
                && !this.soldier.isAttackAnimating();
    }

    @Override
    public void start() {
        this.soldier.startAttackAnimation(PrismarineSoldier.ATTACK_THUNDER, PrismarineSoldier.THUNDER_TICKS);
        this.soldier.startThunderJump();
        this.soldier.playBowDraw();
        this.soldier.setDeltaMovement(this.soldier.getDeltaMovement()
                .add(0.0D, PrismarineSoldier.THUNDER_JUMP_VELOCITY, 0.0D));
    }

    @Override
    public boolean canContinueToUse() {
        return this.soldier.getAttackAnimation() == PrismarineSoldier.ATTACK_THUNDER && this.soldier.isAttackAnimating();
    }

    @Override
    public void stop() {
        this.soldier.stopAttackAnimation();
        this.soldier.cancelThunderHover();
        this.soldier.setThunderCooldown(PrismarineSoldier.THUNDER_COOLDOWN_TICKS);
    }

    @Override
    public void tick() {
        LivingEntity target = this.soldier.getTarget();
        if (target != null) {
            this.soldier.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }
}
