package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSunImmunity;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GreenSmithSunImmunity {

    private static final String SUN_IMMUNE_UNTIL = "goety_supreme:sun_immune_until";

    private GreenSmithSunImmunity() {
    }

    public static void grant(LivingEntity entity, int ticks) {
        if (entity.level().isClientSide() || ticks <= 0) {
            return;
        }
        entity.getPersistentData().putLong(SUN_IMMUNE_UNTIL,
                entity.level().getGameTime() + ticks);
    }

    private static boolean isImmune(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        return data.contains(SUN_IMMUNE_UNTIL)
                && entity.level().getGameTime() < data.getLong(SUN_IMMUNE_UNTIL);
    }

    private static boolean inSunlight(LivingEntity entity) {
        return entity.level().isDay()
                && entity.level().canSeeSky(entity.blockPosition())
                && !entity.isInWaterOrRain();
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || !isImmune(entity)) {
            return;
        }
        if (entity.isOnFire() && inSunlight(entity)) {
            entity.clearFire();
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || !isImmune(entity)) {
            return;
        }
        if (inSunlight(entity) && event.getSource().is(DamageTypes.ON_FIRE)) {
            event.setCanceled(true);
        }
    }
}
