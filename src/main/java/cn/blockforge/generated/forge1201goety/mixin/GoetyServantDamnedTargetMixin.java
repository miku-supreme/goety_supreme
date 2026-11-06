package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import com.Polarice3.Goety.api.entities.ally.IServant;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.Polarice3.Goety.utils.MobUtil", remap = false)
public abstract class GoetyServantDamnedTargetMixin {
    @Inject(method = "isOwnedTargetable", at = @At("HEAD"), cancellable = true, require = 1)
    private static void goetySupreme$skipDamned(Entity attacker, LivingEntity target,
            CallbackInfoReturnable<Boolean> cir) {
        if (attacker instanceof IServant && ServantTargetRules.isDamned(target)) {
            cir.setReturnValue(false);
        }
    }
}
