package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class LivingEntityFellSpearSwingMixin {

    @Inject(method = {"m_21304_", "getCurrentSwingDuration"},
            at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$fellSpearSwingDuration(CallbackInfoReturnable<Integer> callback) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self.getMainHandItem().getItem() instanceof FellSpearItem)) {
            return;
        }
        int base = FellSpearItem.swingDurationTicks();
        MobEffectInstance digSpeed = self.getEffect(MobEffects.DIG_SPEED);
        if (digSpeed != null) {
            callback.setReturnValue(base - (1 + digSpeed.getAmplifier()));
            return;
        }
        MobEffectInstance digSlow = self.getEffect(MobEffects.DIG_SLOWDOWN);
        callback.setReturnValue(digSlow != null ? base + (1 + digSlow.getAmplifier()) * 2 : base);
    }
}
