package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.SummonPotencyAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmCindariaSpearDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetySummonSpellPotencyMixin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.ally.acropolis.CindariaServant",
        remap = false)
public abstract class CataclysmCindariaServantPotencyMixin implements SummonPotencyAccess {

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
}
