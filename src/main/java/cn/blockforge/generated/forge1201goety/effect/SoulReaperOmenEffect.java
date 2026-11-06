package cn.blockforge.generated.goetysupreme.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperFogClient;
import cn.blockforge.generated.goetysupreme.effect.SoulReaperOmenEffect;

public final class SoulReaperOmenEffect extends MobEffect {

    public static final int PHASE_INTRO = 0;

    public static final int PHASE_ARRIVAL = 1;

    public SoulReaperOmenEffect() {
        super(MobEffectCategory.NEUTRAL, 0x101418);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
    }
}
