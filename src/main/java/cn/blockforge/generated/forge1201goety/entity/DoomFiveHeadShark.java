package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.Gnasher;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class DoomFiveHeadShark extends Gnasher {

    public static final double BITE_REACH = 4.5D;

    private static final double BITE_VERTICAL = 2.5D;

    public DoomFiveHeadShark(EntityType<? extends Summoned> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void setConfigurableAttributes() {
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.goalSelector.addGoal(1, new FiveHeadBiteGoal(this, 1.35D, false, BITE_REACH));
    }

    @Override
    public void setUpgraded(boolean upgraded) {
        super.setUpgraded(upgraded);
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
        AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance knockback = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (health == null || armor == null || attack == null || knockback == null) {
            return;
        }
        if (upgraded) {
            health.setBaseValue(90.0D * 1.5D);
            armor.setBaseValue(10.0D);
            attack.setBaseValue(16.0D);
            knockback.setBaseValue(1.0D);
        } else {
            health.setBaseValue(90.0D);
            armor.setBaseValue(8.0D);
            attack.setBaseValue(15.0D);
            knockback.setBaseValue(0.5D);
        }
        this.setHealth(this.getMaxHealth());
    }

    private static final class FiveHeadBiteGoal extends MeleeAttackGoal {
        private final double reach;

        private FiveHeadBiteGoal(PathfinderMob mob, double speedModifier, boolean followTargetEvenIfNotSeen,
                double reach) {
            super(mob, speedModifier, followTargetEvenIfNotSeen);
            this.reach = reach;
        }

        @Override
        public double getAttackReachSqr(LivingEntity target) {
            return this.reach * this.reach;
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceToTargetSqr) {
            if (distanceToTargetSqr > this.getAttackReachSqr(target) || !this.isTimeToAttack()) {
                return;
            }
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
            this.biteSemicircle(target);
        }

        private void biteSemicircle(LivingEntity primary) {
            LivingEntity owner = this.mob instanceof IOwned owned ? owned.getTrueOwner() : null;
            Vec3 look = this.mob.getLookAngle();
            if (look.lengthSqr() > 1.0E-6D) {
                look = look.normalize();
            }
            AABB area = this.mob.getBoundingBox().inflate(this.reach, BITE_VERTICAL, this.reach);
            for (LivingEntity other : this.mob.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (other == this.mob || other == primary || !other.isAlive()) {
                    continue;
                }
                if (owner != null && (other == owner
                        || (other instanceof IOwned otherOwned && otherOwned.getTrueOwner() == owner))) {
                    continue;
                }
                if (!this.mob.canAttack(other) || this.mob.isAlliedTo(other) || other.isAlliedTo(this.mob)) {
                    continue;
                }
                Vec3 offset = other.position().subtract(this.mob.position());
                double distanceSqr = offset.lengthSqr();
                if (distanceSqr > this.reach * this.reach) {
                    continue;
                }
                if (distanceSqr > 0.25D && offset.normalize().dot(look) < 0.0D) {
                    continue;
                }
                this.mob.doHurtTarget(other);
            }
        }
    }
}
