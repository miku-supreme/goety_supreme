package cn.blockforge.generated.goetysupreme.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractWitherNecromancer$SummonServantSpell",
        remap = false)
public abstract class GoetyWitherNecromancerCastTimingMixin {

    @Inject(method = "timeToCast", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$castAtTen(CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(10);
    }
}
