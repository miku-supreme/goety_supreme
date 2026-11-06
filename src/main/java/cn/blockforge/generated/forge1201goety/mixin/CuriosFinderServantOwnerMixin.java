package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.ServantOwnerGear;
import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class CuriosFinderServantOwnerMixin {

    @Inject(
            method = "hasCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void goetySupreme$servantInheritsOwnerCurio(LivingEntity entity,
            Predicate<ItemStack> filter, CallbackInfoReturnable<Boolean> callback) {
        if (ServantOwnerGear.inheritsOwnerGear(entity)
                && ServantOwnerGear.ownerHasCurio(entity, filter)) {
            callback.setReturnValue(true);
        }
    }

    @Inject(
            method = "hasCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void goetySupreme$servantInheritsOwnerCurioItem(LivingEntity entity,
            Item item, CallbackInfoReturnable<Boolean> callback) {
        if (ServantOwnerGear.inheritsOwnerGear(entity)
                && ServantOwnerGear.ownerHasCurio(entity, item)) {
            callback.setReturnValue(true);
        }
    }
}
