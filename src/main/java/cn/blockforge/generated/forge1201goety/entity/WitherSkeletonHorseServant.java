package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;

public class WitherSkeletonHorseServant extends SkeletonHorseServant {

    public static final float RENDER_SCALE = 1.2F;

    public static final int WITHER_TICKS = 3 * 20;

    public static final int WITHER_AMPLIFIER = 0;

    public static final double NETHERITE_ARMOR_KNOCKBACK_RESISTANCE = 0.6D;

    public WitherSkeletonHorseServant(EntityType<? extends WitherSkeletonHorseServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 8;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public double netheriteHorseArmorKnockbackResistance() {
        return NETHERITE_ARMOR_KNOCKBACK_RESISTANCE;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, WITHER_AMPLIFIER), this);
        }
        return hit;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.WITHER && super.canBeAffected(effect);
    }

    private static final float VOICE_PITCH = 0.8F;

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * VOICE_PITCH;
    }
}
