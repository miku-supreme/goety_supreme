package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import com.Polarice3.Goety.common.items.magic.EsotericTesseract;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EsotericTesseract.class, remap = false)
public abstract class EsotericTesseractStarSizeMixin {

    @Inject(method = "isMedium", at = @At("HEAD"), cancellable = true, require = 0)
    private static void goetySupreme$starredOccupiesFour(Mob mob, CallbackInfoReturnable<Boolean> callback) {
        if (mob instanceof RagingStarServant servant && servant.isRagingStarred()) {
            callback.setReturnValue(true);
        }
        if (mob instanceof PlayerMobServant servant && servant.hasArcaneScroll()) {
            callback.setReturnValue(true);
        }
    }
}
