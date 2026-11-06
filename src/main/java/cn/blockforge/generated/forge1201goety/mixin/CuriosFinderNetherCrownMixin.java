package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.WitherWarlordHelmetEffects;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.CuriosFinderForgeRingMixin;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class CuriosFinderNetherCrownMixin {

    @Inject(
            method = "hasNetherCrown(Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void goetySupreme$helmCountsAsNetherCrown(LivingEntity entity,
            CallbackInfoReturnable<Boolean> callback) {
        if (WitherWarlordHelmetEffects.countsAsNetherCrown(entity)) {
            callback.setReturnValue(true);
        }
    }
}
