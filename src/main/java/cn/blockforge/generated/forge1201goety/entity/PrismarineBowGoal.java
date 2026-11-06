package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

final class PrismarineBowGoal extends Goal {

    private final PrismarineSoldier soldier;
    @Nullable
    private LivingEntity target;

    private int drawTicks;

    PrismarineBowGoal(PrismarineSoldier soldier) {
        this.soldier = soldier;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.soldier.holdsBow() || this.soldier.getBowGapCooldown() > 0) {
            return false;
        }
        LivingEntity candidate = this.soldier.getTarget();
        if (candidate == null || !candidate.isAlive()) {
            return false;
        }
        if (this.soldier.distanceToSqr(candidate) > PrismarineSoldier.BOW_ATTACK_RADIUS * PrismarineSoldier.BOW_ATTACK_RADIUS) {
            return false;
        }
        if (!this.soldier.getSensing().hasLineOfSight(candidate)) {
            return false;
        }
        this.target = candidate;
        return true;
    }

    @Override
    public void start() {
        this.soldier.getNavigation().stop();
        this.drawTicks = 0;
        this.soldier.playBowDraw();
        if (!this.soldier.isUsingItem()) {
            this.soldier.startUsingItem(InteractionHand.MAIN_HAND);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.drawTicks < PrismarineSoldier.BOW_DRAW_TICKS && this.soldier.holdsBow() && this.target != null
                && this.target.isAlive()
                && this.soldier.distanceToSqr(this.target) <= PrismarineSoldier.BOW_ATTACK_RADIUS * PrismarineSoldier.BOW_ATTACK_RADIUS;
    }

    @Override
    public void tick() {
        if (this.target == null || !this.soldier.holdsBow()) {
            return;
        }
        this.soldier.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        this.soldier.getNavigation().stop();
        this.drawTicks++;
        if (this.drawTicks == PrismarineSoldier.BOW_DRAW_TICKS / 2) {
            this.soldier.playBowDrawMid();
        }
        if (this.drawTicks >= PrismarineSoldier.BOW_DRAW_TICKS && this.soldier.getSensing().hasLineOfSight(this.target)) {
            this.soldier.performRangedAttack(this.target, 1.0F);
        }
    }

    @Override
    public void stop() {
        this.soldier.stopUsingItem();
        this.target = null;
        this.drawTicks = 0;
        this.soldier.setBowGapCooldown(PrismarineSoldier.BOW_GAP_TICKS);
    }
}
