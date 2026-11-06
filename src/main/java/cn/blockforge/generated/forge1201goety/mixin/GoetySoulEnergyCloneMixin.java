package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import com.Polarice3.Goety.common.events.SoulEnergyEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SoulEnergyEvents.class, remap = false)
public abstract class GoetySoulEnergyCloneMixin {

    @Inject(method = "onLivingDeathEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void goetySupreme$skipGreenSmithClone(LivingDeathEvent event, CallbackInfo ci) {
        if (event.getEntity() instanceof AbstractGreenSmith smith && smith.isClone()) {
            ci.cancel();
        }
    }
}
