package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public final class AbstractReaperNecromancerGoals {

    private AbstractReaperNecromancerGoals() {
    }

    public static final class CombatGoal extends Goal {

        private final AbstractReaperNecromancer mob;

        CombatGoal(AbstractReaperNecromancer mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            return target != null && target.isAlive()
                    && !this.mob.isCasting();
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
            if (distSqr > AbstractReaperNecromancer.MELEE_RANGE_SQR) {
                this.mob.getNavigation().moveTo(target, 1.0D);
            } else {
                this.mob.getNavigation().stop();
            }
            if (this.mob.attackCooldown <= 0 && !this.mob.isCasting() && this.mob.hasLineOfSight(target)
                    && distSqr <= AbstractReaperNecromancer.ATTACK_RANGE_SQR) {
                this.mob.startCast(AbstractReaperNecromancer.CAST_ATTACK,
                        distSqr <= AbstractReaperNecromancer.MELEE_RANGE_SQR);
                this.mob.attackCooldown = this.mob.scaledAttackCooldown(AbstractReaperNecromancer.ATTACK_COOLDOWN);
            }
        }

        @Override
        public void stop() {
            this.mob.getNavigation().stop();
        }
    }

    public static final class FlyingWanderGoal extends Goal {

        private final AbstractReaperNecromancer mob;

        @Nullable
        private Vec3 targetPos;

        FlyingWanderGoal(AbstractReaperNecromancer mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.mob.getTarget() != null || this.mob.isCasting() || this.mob.getNavigation().isInProgress()) {
                return false;
            }
            if (this.mob.getRandom().nextInt(60) != 0) {
                return false;
            }
            double range = 16.0D;
            double x = this.mob.getX() + (this.mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
            double y = this.mob.getY() + (this.mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
            double z = this.mob.getZ() + (this.mob.getRandom().nextDouble() * 2.0D - 1.0D) * range;
            this.targetPos = new Vec3(x,
                    Mth.clamp(y, this.mob.level().getMinBuildHeight() + 2.0D,
                            this.mob.level().getMaxBuildHeight() - 2.0D),
                    z);
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.targetPos != null && this.mob.getTarget() == null && !this.mob.isCasting()
                    && !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            if (this.targetPos != null) {
                this.mob.getNavigation()
                        .moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, 0.8D);
            }
        }

        @Override
        public void stop() {
            this.targetPos = null;
            this.mob.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (this.targetPos != null && this.mob.getNavigation().isDone()) {
                this.stop();
            }
        }
    }

    public static final class SpellGoal extends Goal {

        private final AbstractReaperNecromancer mob;

        SpellGoal(AbstractReaperNecromancer mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.mob.isCasting() || this.mob.getTarget() == null || this.mob.spellPickCooldown > 0) {
                return false;
            }
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()
                    || this.mob.distanceToSqr(target) > AbstractReaperNecromancer.FOLLOW_RANGE
                            * AbstractReaperNecromancer.FOLLOW_RANGE) {
                return false;
            }
            this.mob.spellPickCooldown = 5;
            this.mob.pendingSpell = this.mob.pickWeightedSpell();
            return this.mob.pendingSpell != AbstractReaperNecromancer.CAST_NONE;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            int spell = this.mob.pendingSpell;
            this.mob.pendingSpell = AbstractReaperNecromancer.CAST_NONE;
            if (spell == AbstractReaperNecromancer.CAST_NONE || this.mob.isCasting()) {
                return;
            }
            switch (spell) {
                case AbstractReaperNecromancer.CAST_SUMMON ->
                        this.mob.summonCooldown = AbstractReaperNecromancer.SUMMON_COOLDOWN;
                case AbstractReaperNecromancer.CAST_SPIN ->
                        this.mob.spinCooldown = AbstractReaperNecromancer.SPIN_COOLDOWN;
                case AbstractReaperNecromancer.CAST_PUNISH ->
                        this.mob.punishCooldown = AbstractReaperNecromancer.PUNISH_COOLDOWN;
                case AbstractReaperNecromancer.CAST_SNAP ->
                        this.mob.snapCooldown = AbstractReaperNecromancer.SNAP_COOLDOWN;
                default -> {
                }
            }
            this.mob.startCast(spell, false);
        }
    }
}
