package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.AllianceRingGuard;
import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.Owned", remap = false)
public abstract class OwnedAllianceRingGuardMixin {

    @Inject(method = "m_7307_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$breakOwnerRing(Entity entity, CallbackInfoReturnable<Boolean> callback) {
        Boolean safe = AllianceRingGuard.safeAllianceAnswer((IOwned) (Object) this, entity);
        if (safe != null) {
            callback.setReturnValue(safe);
        }
    }
}
