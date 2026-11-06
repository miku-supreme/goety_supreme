package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;

@Mixin(value = LocalPlayer.class, remap = false)
public class LocalPlayerFellSpearSpeedMixin {

    @ModifyConstant(method = {"m_8107_", "aiStep"}, constant = @Constant(floatValue = 0.2F), require = 0)
    private float goetySupreme$fellSpearNoSlowdown(float original) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (self.isUsingItem() && self.getUseItem().getItem() instanceof FellSpearItem) {
            return 1.0F;
        }
        return original;
    }

    @Inject(method = {"m_8107_", "aiStep"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$fellSpearCanSprint(CallbackInfo callback) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (self.isUsingItem() && self.getUseItem().getItem() instanceof FellSpearItem
                && !self.isPassenger() && !self.isShiftKeyDown()
                && self.input.forwardImpulse > 0.0F
                && Minecraft.getInstance().options.keySprint.isDown()) {
            self.setSprinting(true);
        }
    }
}
