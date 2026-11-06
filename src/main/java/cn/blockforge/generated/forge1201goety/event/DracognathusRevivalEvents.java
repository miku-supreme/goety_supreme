package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.DracognathusRevivalEvents;
import cn.blockforge.generated.goetysupreme.item.DracognathusRevivalHornItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class DracognathusRevivalEvents {

    private DracognathusRevivalEvents() {
    }

    @SubscribeEvent
    public static void onInteractDracognathus(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof DracognathusRevivalHornItem)) {
            return;
        }
        Player player = event.getEntity();
        if (ReviveCooldown.isOnCooldown(player, stack)) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }
        if (!(event.getTarget() instanceof LivingEntity target)
                || !DracognathusRevivalHornItem.isOwnDracognathus(target, player)) {
            return;
        }
        InteractionHand hand = event.getHand();
        if (player.level().isClientSide) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            return;
        }
        if (!(player.level() instanceof ServerLevel server) || player.isSpectator()) {
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        DracognathusRevivalHornItem.revive(server, player, stack, target, hand);
    }
}
