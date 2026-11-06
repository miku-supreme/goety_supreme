package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.ForgeRingArmorNormalizer;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Summoned.class, remap = false)
public abstract class GoetyForgeRingArmorMixin {

    @Inject(method = "canSpawnArmor", at = @At("RETURN"), cancellable = true, require = 1)
    private void goetySupreme$allowRingArmor(CallbackInfoReturnable<Boolean> callback) {
        if (Boolean.TRUE.equals(callback.getReturnValue())) {
            return;
        }
        Summoned self = (Summoned) (Object) this;
        if (self.getSpawnType() == net.minecraft.world.entity.MobSpawnType.CONVERSION) {
            return;
        }
        if (self.getTrueOwner() == null) {
            return;
        }
        if (RingServantGear.handles(RingServantGear.ringOf(self))) {
            callback.setReturnValue(true);
        }
    }

    @Inject(method = "spawnArmor", at = @At("HEAD"), cancellable = true, require = 1)
    private void goetySupreme$rollRingArmor(RandomSource random, CallbackInfo callback) {
        Summoned self = (Summoned) (Object) this;
        if (RingServantGear.handles(RingServantGear.ringOf(self))) {
            RingServantGear.rollRingGear(self, random);
            callback.cancel();
        }
    }

    @Inject(method = "spawnArmor", at = @At("RETURN"), require = 1)
    private void goetySupreme$normalizeForgeRingArmor(RandomSource random, CallbackInfo callback) {
        Summoned self = (Summoned) (Object) this;
        ForgeRingArmorNormalizer.normalize(self);
    }
}
