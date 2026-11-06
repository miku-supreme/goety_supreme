package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.HellfireSaberEvents;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class HellfireSaberEvents {

    private HellfireSaberEvents() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide()) {
            return;
        }
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof HellfireSaberItem
                && HellfireSaberItem.isBlazeInvulnerable(player, stack)) {
            event.setCanceled(true);
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() == null && source.getDirectEntity() == null) {
            return;
        }
        if (!(stack.getItem() instanceof HellfireSaberItem) || !HellfireSaberItem.isGuarding(player, stack)) {
            return;
        }
        event.setCanceled(true);
        HellfireSaberItem.onParried(player, stack);
    }
}
