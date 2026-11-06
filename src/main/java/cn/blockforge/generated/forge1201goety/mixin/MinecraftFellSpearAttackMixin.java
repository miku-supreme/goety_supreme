package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.network.FellSpearStabPacket;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.network.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.event.FellSpearEvents;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftFellSpearAttackMixin {

    @Inject(method = {"m_202354_", "startAttack"}, at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$fellSpearStab(CallbackInfoReturnable<Boolean> callback) {
        Minecraft self = (Minecraft) (Object) this;
        LocalPlayer player = self.player;
        HitResult hit = self.hitResult;
        if (player == null || hit == null) {
            return;
        }
        if (hit.getType() == HitResult.Type.BLOCK) {
            return;
        }
        if (player.isUsingItem()) {
            return;
        }
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof FellSpearItem)) {
            return;
        }
        if (player.getAttackStrengthScale(0.0F) < 1.0F) {
            callback.setReturnValue(false);
            return;
        }
        player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                GeneratedMod.FELL_SPEAR_ATTACK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        player.resetAttackStrengthTicker();
        ModNetwork.CHANNEL.sendToServer(FellSpearStabPacket.INSTANCE);
        player.swing(InteractionHand.MAIN_HAND);
        callback.setReturnValue(true);
    }
}
