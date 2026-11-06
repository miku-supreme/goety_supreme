package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.FellShieldEvents;
import cn.blockforge.generated.goetysupreme.item.FellShieldItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class FellShieldEvents {

    public static final float REFLECT_DAMAGE = 2.0F;

    private static final int BUSTED_TICKS = 100;

    private static final ThreadLocal<Boolean> REFLECTING = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private FellShieldEvents() {
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide || REFLECTING.get()) {
            return;
        }
        ItemStack blocking = victim.getUseItem();
        if (blocking.isEmpty() || !blocking.is(GeneratedMod.FELL_SHIELD.get())) {
            return;
        }
        Entity sourceEntity = event.getDamageSource().getEntity();
        if (!(sourceEntity instanceof LivingEntity attacker) || attacker == victim || !attacker.isAlive()) {
            return;
        }
        REFLECTING.set(Boolean.TRUE);
        try {
            attacker.hurt(victim.damageSources().thorns(victim), REFLECT_DAMAGE);
            if (attacker.isAlive()) {
                attacker.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), victim);
            }
        } finally {
            REFLECTING.set(Boolean.FALSE);
        }
    }
}
