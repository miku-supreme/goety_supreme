package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyCryptAttackWindupStaticMixin;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$NecromancerRangedGoal",
        remap = false)
public abstract class GoetyCryptAttackWindupMixin {
    @Shadow @Final private AbstractNecromancer mob;

    @ModifyConstant(method = "m_8037_", constant = @Constant(intValue = 5, ordinal = 1), require = 0)
    private int goetySupreme$cryptWindup(int original) {
        return this.mob instanceof CryptNecromancerServant crypt
                ? crypt.getCryptAttackWindupTicks() : original;
    }
}
