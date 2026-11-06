package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.items.WaystoneItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.MobilePrisonBindingEvents;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class MobilePrisonBindingEvents {

    private MobilePrisonBindingEvents() {
    }

    @SubscribeEvent
    public static void onWaystoneBind(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty() || !(stack.getItem() instanceof WaystoneItem)) {
            return;
        }
        if (!(event.getTarget() instanceof PrisonGolem golem)) {
            return;
        }
        Player player = event.getEntity();
        GlobalPos stored = WaystoneItem.getPosition(stack);
        if (stored == null || !stored.dimension().equals(player.level().dimension())) {
            return; // 没记录坐标 / 跨维度：Goety 默认本来也不会绑，不用管
        }
        LivingEntity owner = golem.getTrueOwner();
        if (owner != player) {
            return; // 只处理"给自己的傀儡绑定"，别人的傀儡 Goety 默认直接 PASS
        }
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }
        BlockPos anchor = MobilePrisonBlockEntity.findAnchor(level, stored.pos());
        if (anchor == null) {
            event.setCanceled(true);
            player.displayClientMessage(
                    Component.translatable("info.goety_supreme.prison.bind_not_prison"), true);
            return;
        }
        golem.setBoundPos(anchor);
        golem.setWandering(false);
        golem.setStaying(false);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 1.0F, 0.45F);
        player.swing(event.getHand(), true);
        event.setCanceled(true);
    }
}
