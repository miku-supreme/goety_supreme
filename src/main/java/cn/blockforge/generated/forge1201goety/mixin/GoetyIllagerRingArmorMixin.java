package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetyForgeRingArmorMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyIllagerTrainArmorMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyRaiderServantRingArmorMixin;

@Mixin(targets = {
        "com.Polarice3.Goety.common.entities.ally.illager.VindicatorServant",
        "com.Polarice3.Goety.common.entities.ally.illager.PillagerServant",
        "com.Polarice3.Goety.common.entities.ally.illager.MountaineerServant",
        "com.Polarice3.Goety.common.entities.ally.illager.cultist.MaverickServant"
}, remap = false)
public abstract class GoetyIllagerRingArmorMixin {

    @Inject(method = "m_213945_", at = @At("TAIL"), require = 1)
    private void goetySupreme$rollRingArmorForIllager(RandomSource random,
            DifficultyInstance difficulty, CallbackInfo callback) {
        Summoned self = (Summoned) (Object) this;
        if (self.getSpawnType() == MobSpawnType.CONVERSION) {
            return;
        }
        RingServantGear.rollRingArmorOnly(self, random);
    }
}
