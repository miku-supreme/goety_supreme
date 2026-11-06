package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.GreenSmithSoulJarEvents;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GreenSmithSoulJarEvents {

    private GreenSmithSoulJarEvents() {
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            return;
        }
        if (entity.getHealth() - event.getAmount() > 0.0F) {
            return;
        }
        if (!GreenSmithSoulJarGuard.isGreenSmithSummoned(entity)) {
            return;
        }
        if (entity instanceof IOwned owned && owned.getTrueOwner() instanceof Player) {
            owned.removeTrueOwner();
        }
    }
}
