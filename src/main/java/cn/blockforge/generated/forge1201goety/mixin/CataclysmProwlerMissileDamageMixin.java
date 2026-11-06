package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.config.CataclysmConfigValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.ally.factory.ProwlerServant",
        remap = false)
public abstract class CataclysmProwlerMissileDamageMixin {

    @ModifyArg(
            method = "MissileLaunch(FFLnet/minecraft/world/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/goety_cataclysm/common/entities/projectiles/"
                            + "WitherHomingMissile;<init>(Lnet/minecraft/world/entity/LivingEntity;"
                            + "Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;F"
                            + "Lnet/minecraft/world/entity/LivingEntity;)V",
                    remap = false),
            index = 3,
            remap = false,
            require = 0)
    private float goetySupreme$configuredProwlerMissileDamage(float vanilla) {
        Double configured = CataclysmConfigValues.value("ProwlerMissileDamage");
        return configured == null ? vanilla : (float) configured.doubleValue();
    }
}
