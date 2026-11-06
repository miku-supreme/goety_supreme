package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.SummonPotencyAccess;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import cn.blockforge.generated.goetysupreme.mixin.GoetySummonSpellPotencyMixin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.ally.undead.draugr.EliteDraugrServant",
        remap = false)
public abstract class CataclysmEliteDraugrServantPotencyMixin implements SummonPotencyAccess {

    @Unique
    private int goetySupreme$potency;

    @Override
    public int goetySupreme$potency() {
        return this.goetySupreme$potency;
    }

    @Override
    public void goetySupreme$setPotency(int potency) {
        this.goetySupreme$potency = potency;
    }

    @Redirect(method = "m_5811_", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;m_36781_(D)V"),
            require = 0)
    private void goetySupreme$addPotencyToArrow(AbstractArrow arrow, double baseDamage) {
        arrow.setBaseDamage(baseDamage + this.goetySupreme$potency);
    }
}
