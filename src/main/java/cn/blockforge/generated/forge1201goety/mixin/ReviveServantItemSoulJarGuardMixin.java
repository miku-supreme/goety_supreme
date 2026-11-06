package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSoulJarMixin;

@Mixin(targets = "com.Polarice3.Goety.common.items.revive.ReviveServantItem", remap = false)
public abstract class ReviveServantItemSoulJarGuardMixin {

    @Inject(method = "setSummon", at = @At("HEAD"), cancellable = true, require = 0)
    private static void goetySupreme$neutralizeGreenSmithJar(Entity entity, ItemStack stack,
            CallbackInfo callback) {
        if (GreenSmithSoulJarGuard.neutralizeJar(entity, stack)) {
            callback.cancel();
        }
    }
}
