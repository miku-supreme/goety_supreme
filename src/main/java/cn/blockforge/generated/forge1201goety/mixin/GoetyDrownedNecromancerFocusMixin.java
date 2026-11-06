package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerFocusMixin;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.DrownedNecromancer", remap = false)
public abstract class GoetyDrownedNecromancerFocusMixin {

    @Inject(method = "m_6071_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$grantDrownedDeathFocus(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> callback) {
        AbstractNecromancer self = (AbstractNecromancer) (Object) this;
        ItemStack stack = player.getItemInHand(hand);
        if (NecromancerSummonExtras.tryGrantFocus(self, player, stack)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
