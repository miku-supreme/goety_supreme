package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSunkenNecromancerFocusMixin;

@Pseudo
@Mixin(targets = {"com.qiuyue.goetyominous.common.entities.ally.neutral.AbstractSunkenNecromancer",
        "com.qiuyue.goetyominus.common.entities.ally.neutral.AbstractSunkenNecromancer"}, remap = false)
public abstract class GoetyOminousSunkenNecromancerFocusMixin {

    @Inject(method = "m_6071_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$feedUniversalSoulJar(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> callback) {
        AbstractNecromancer self = (AbstractNecromancer) (Object) this;
        ItemStack stack = player.getItemInHand(hand);
        if (UniversalSoulJarItem.tryFeed(self, player, stack, hand)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
            return;
        }
        if (NecromancerSummonExtras.tryGrantFocus(self, player, stack)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "soulJar", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$denyGreenSmithSoulJar(CallbackInfo callback) {
        if (GreenSmithSoulJarGuard.shouldBlockSoulJar((LivingEntity) (Object) this)) {
            callback.cancel();
        }
    }
}
