package cn.blockforge.generated.goetysupreme.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.effect.MalignasaurAsphyxiaMobEffect;

public class MalignasaurAsphyxiaMobEffect extends MobEffect {
    public MalignasaurAsphyxiaMobEffect() {
        super(MobEffectCategory.HARMFUL, -13865258);
    }

    @Override
    public String getDescriptionId() {
        return "effect.goety_supreme.malignasaur_asphyxia";
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int effectLevel = amplifier + 1;
        if (entity.getHealth() <= 0.0F) {
            return;
        }
        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(AbstractJerotesServant.jerotesParticle(
                            "gemstone_bubble", ParticleTypes.BUBBLE),
                    entity.getRandomX(0.5D), entity.getRandomY(), entity.getRandomZ(0.5D),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (entity.getMobType() != MobType.WATER
                && !entity.hasEffect(MobEffects.WATER_BREATHING)
                && !entity.hasEffect(MobEffects.CONDUIT_POWER)) {
            entity.hurt(entity.damageSources().drown(), (float) effectLevel * 2.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
