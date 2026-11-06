package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.ReviveServantItemSoulJarGuardMixin;

@Mixin(value = AbstractNecromancer.class, remap = false)
public abstract class GoetyNecromancerSoulJarMixin {

    @Inject(method = "soulJar", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$denyGreenSmithSummonSoulJar(CallbackInfo callback) {
        if (GreenSmithSoulJarGuard.shouldBlockSoulJar((LivingEntity) (Object) this)) {
            callback.cancel();
        }
    }

    @Redirect(method = "m_6667_",
            at = @At(value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/common/entities/neutral/AbstractNecromancer;soulJar()V"),
            require = 0)
    private void goetySupreme$denySoulJarOnDeath(AbstractNecromancer self) {
        if (!GreenSmithSoulJarGuard.shouldBlockSoulJar(self)) {
            self.soulJar();
        }
    }
}
