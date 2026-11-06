package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyForgeRingArmorMixin;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class CuriosFinderForgeRingMixin {

    @Inject(
            method = "hasCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void goetySupreme$treatNewRingsAsForgeRing(LivingEntity entity, Item item,
            CallbackInfoReturnable<Boolean> callback) {
        if (RingServantGear.newRingCountsAsForgeRing(entity, item)) {
            callback.setReturnValue(true);
        }
    }
}
