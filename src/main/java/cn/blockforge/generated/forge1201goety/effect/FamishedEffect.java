package cn.blockforge.generated.goetysupreme.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.effect.FamishedEffect;

public final class FamishedEffect extends MobEffect {

    private static final int BASE_INTERVAL_TICKS = 80;
    private static final String TICK_COUNTER = "GoetySupremeFamishedTick";

    public FamishedEffect() {
        super(MobEffectCategory.HARMFUL, 0x8A5A2B);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living instanceof Player player) {
            player.causeFoodExhaustion(1.0F * (amplifier + 1));
        }

        int interval = Math.max(1, BASE_INTERVAL_TICKS / (1 << Math.min(amplifier, 6)));
        int counter = living.getPersistentData().getInt(TICK_COUNTER) + 20;
        if (counter >= interval) {
            counter = 0;
            if (living instanceof Player player) {
                if (player.getFoodData().getSaturationLevel() <= 0.0F) {
                    player.hurt(player.damageSources().starve(), 1.0F);
                }
            } else {
                living.hurt(living.damageSources().starve(), 1.0F);
            }
        }
        living.getPersistentData().putInt(TICK_COUNTER, counter);
    }
}
