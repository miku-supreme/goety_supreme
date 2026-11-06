package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.FellSpearEvents;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.network.FellSpearStabPacket;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.mixin.MinecraftFellSpearAttackMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class FellSpearEvents {

    private FellSpearEvents() {
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof FellSpearItem)) {
            return;
        }
        event.setCanceled(true);
        FellSpearItem.stabAttack(player, stack);
    }
}
