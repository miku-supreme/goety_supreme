package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant",
        remap = false)
public abstract class GoetyWitherSkeletonRingWeaponMixin {

    @Inject(method = "m_213945_", at = @At("RETURN"), require = 0)
    private void goetySupreme$ringWitherSkeletonWeapon(RandomSource random, DifficultyInstance difficulty,
            CallbackInfo callback) {
        RingServantGear.applyWitherSkeletonWeapon((Summoned) (Object) this, random);
    }
}
