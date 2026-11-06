package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.util.CorrosiverRiderGuard;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithDamageGuard;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorSetBonus;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class CorrosiverRiderGuard {

    private static final float RIDDEN_DAMAGE_MULTIPLIER = 0.8F;

    private CorrosiverRiderGuard() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide()) {
            return;
        }
        if (!(player.getVehicle() instanceof CorrosiverServant corrosiver)) {
            return;
        }
        if (corrosiver.getTrueOwner() != player) {
            return;
        }
        if (event.getSource().is(DamageTypeTags.IS_FREEZING)) {
            event.setCanceled(true);
            return;
        }
        event.setAmount(event.getAmount() * RIDDEN_DAMAGE_MULTIPLIER);
    }
}
