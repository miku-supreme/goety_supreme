package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetyForgeRingArmorMixin;

@Mixin(targets = "com.Polarice3.Goety.common.entities.ally.illager.raider.RaiderServant", remap = false)
public abstract class GoetyRaiderServantRingArmorMixin {

    @Inject(method = "spawnArmor", at = @At("HEAD"), cancellable = true, require = 1)
    private void goetySupreme$rollRingArmorForRaider(RandomSource random, CallbackInfo callback) {
        Summoned self = (Summoned) (Object) this;
        if (RingServantGear.handles(RingServantGear.ringOf(self))) {
            RingServantGear.rollRingArmorOnly(self, random);
            callback.cancel();
        }
    }
}
