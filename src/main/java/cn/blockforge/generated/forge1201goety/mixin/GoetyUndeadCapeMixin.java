package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class GoetyUndeadCapeMixin {
    @Inject(method = "hasUndeadCape", at = @At("HEAD"), cancellable = true, require = 0)
    private static void goetySupreme$undeadCapeTag(LivingEntity entity,
            CallbackInfoReturnable<Boolean> callback) {
        if (entity instanceof UndeadCapeBearer) {
            callback.setReturnValue(true);
        }
    }
}
