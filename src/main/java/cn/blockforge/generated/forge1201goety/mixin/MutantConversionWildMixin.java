package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.compat.MutantPotionServantCompatibility;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "fuzs.mutantmonsters.util.EntityUtil", remap = false)
public abstract class MutantConversionWildMixin {
    @Inject(method = "convertMobWithNBT", at = @At("RETURN"), require = 1)
    private static void goetySupreme$clearConvertedOwner(LivingEntity source, EntityType<?> targetType,
            boolean copyEquipment, CallbackInfoReturnable<Entity> callback) {
        if (!MutantPotionServantCompatibility.isCompatibleSource(source)) {
            return;
        }
        if (callback.getReturnValue() instanceof MutantSnowGolem snowGolem) {
            snowGolem.setOwnerId(null);
        }
    }
}
