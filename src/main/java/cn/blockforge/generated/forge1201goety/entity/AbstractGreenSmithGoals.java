package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmithGoals;

public final class AbstractGreenSmithGoals {

    private AbstractGreenSmithGoals() {
    }

    public static final class CombatGoal extends Goal {

        private final AbstractGreenSmith mob;

        CombatGoal(AbstractGreenSmith mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.mob.isDeadOrDying() || this.mob.isClone()) {
                return false;
            }
            LivingEntity target = this.mob.getTarget();
            return target != null && target.isAlive() && !this.mob.isCasting();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void tick() {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return;
            }
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distSqr = this.mob.distanceToSqr(target);
            if (!this.mob.isFieldActive()) {
                if (distSqr > AbstractGreenSmith.MELEE_RANGE_SQR) {
                    this.mob.getNavigation().moveTo(target, 1.0D);
                } else {
                    this.mob.getNavigation().stop();
                }
            } else {
                this.mob.getNavigation().stop();
            }
            if (this.mob.attackCooldown <= 0 && !this.mob.isCasting() && this.mob.hasLineOfSight(target)
                    && distSqr <= AbstractGreenSmith.ATTACK_RANGE_SQR) {
                this.mob.startCast(AbstractGreenSmith.CAST_ATTACK);
                this.mob.attackCooldown = this.mob.scaledAttackCooldown(AbstractGreenSmith.ATTACK_COOLDOWN);
            }
        }

        @Override
        public void stop() {
            this.mob.getNavigation().stop();
        }
    }

    public static final class SpellGoal extends Goal {

        private final AbstractGreenSmith mob;

        SpellGoal(AbstractGreenSmith mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.mob.isDeadOrDying() || this.mob.isCasting() || this.mob.getTarget() == null
                    || this.mob.spellPickCooldown > 0 || this.mob.isFieldActive() || this.mob.isClone()) {
                return false;
            }
            LivingEntity target = this.mob.getTarget();
            if (!target.isAlive()
                    || this.mob.distanceToSqr(target) > AbstractGreenSmith.FOLLOW_RANGE * AbstractGreenSmith.FOLLOW_RANGE) {
                return false;
            }
            this.mob.spellPickCooldown = 5;
            this.mob.pendingSpell = this.mob.pickWeightedSpell();
            return this.mob.pendingSpell != AbstractGreenSmith.SPELL_NONE;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            int spell = this.mob.pendingSpell;
            this.mob.pendingSpell = AbstractGreenSmith.SPELL_NONE;
            if (spell == AbstractGreenSmith.SPELL_NONE || this.mob.isCasting()) {
                return;
            }
            switch (spell) {
                case AbstractGreenSmith.SPELL_SUMMON -> {
                    this.mob.summonCooldown = AbstractGreenSmith.SUMMON_COOLDOWN;
                    this.mob.startCast(AbstractGreenSmith.CAST_SUMMON);
                }
                case AbstractGreenSmith.SPELL_BORROW -> {
                    this.mob.borrowCooldown = AbstractGreenSmith.BORROW_COOLDOWN;
                    this.mob.borrowedSkill = this.mob.getRandom().nextInt(AbstractGreenSmith.BORROW_SKILLS);
                    this.mob.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_BORROW, false);
                    this.mob.castImpact = this.mob.scaledCharge(AbstractGreenSmith.BORROW_CHARGE_TICKS);
                }
                case AbstractGreenSmith.SPELL_LASER -> {
                    this.mob.laserCooldown = AbstractGreenSmith.LASER_COOLDOWN;
                    this.mob.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_LASER_CHARGE, false);
                    this.mob.castImpact = this.mob.scaledCharge(AbstractGreenSmith.LASER_CHARGE_TICKS);
                }
                case AbstractGreenSmith.SPELL_WIND -> {
                    this.mob.windCooldown = AbstractGreenSmith.WIND_COOLDOWN;
                    this.mob.startCast(AbstractGreenSmith.CAST_WIND);
                }
                case AbstractGreenSmith.SPELL_CLOUD -> {
                    this.mob.cloudCooldown = AbstractGreenSmith.CLOUD_COOLDOWN;
                    this.mob.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_CLOUD, false);
                }
                case AbstractGreenSmith.SPELL_RALLY -> {
                    this.mob.rallyCooldown = AbstractGreenSmith.RALLY_COOLDOWN;
                    this.mob.retreatHealTicks = 0;
                    this.mob.startCast(AbstractGreenSmith.CAST_RALLY);
                }
                case AbstractGreenSmith.SPELL_FIELD -> {
                    this.mob.fieldUsedAtHalf = true;
                    this.mob.startCast(AbstractGreenSmith.CAST_FIELD, true);
                }
                default -> {
                }
            }
        }
    }
}
