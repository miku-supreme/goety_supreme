package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.config.CataclysmConfigValues;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.projectiles.PhantomArrow",
        remap = false)
public abstract class CataclysmDraugrSoulArrowDamageMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/world/level/Level;"
                    + "Lnet/minecraft/world/entity/LivingEntity;"
                    + "Lnet/minecraft/world/entity/LivingEntity;)V",
            at = @At("RETURN"),
            remap = false,
            require = 0)
    private void goetySupreme$configuredSoulArrowDamage(Level level, LivingEntity shooter,
            LivingEntity finalTarget, CallbackInfo callback) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        Entity owner = ((Projectile) (Object) this).getOwner();
        if (owner == null || !owner.getClass().getName().contains("DraugrNecromancer")) {
            return;
        }
        Double configured = CataclysmConfigValues.value("DraugrNecromancerDamage");
        if (configured != null) {
            self.setBaseDamage(configured.doubleValue());
        }
    }
}
