package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.FellHammerEvents;
import cn.blockforge.generated.goetysupreme.item.FellHammerItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class FellHammerEvents {

    private static final int BUSTED_TICKS = 100;

    private FellHammerEvents() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getAmount() <= 0.0F) {
            return;
        }
        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide() || !victim.isAlive()) {
            return;
        }
        DamageSource source = event.getSource();
        if (!(source.getDirectEntity() instanceof LivingEntity attacker)) {
            return;
        }
        if (attacker == victim) {
            return;
        }
        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.isEmpty() || !weapon.is(GeneratedMod.FELL_HAMMER.get())) {
            return;
        }
        victim.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), attacker);
    }
}
