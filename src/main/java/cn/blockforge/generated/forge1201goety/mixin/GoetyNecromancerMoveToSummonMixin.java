package cn.blockforge.generated.goetysupreme.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSummonApproachPurgeMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSummoningSpellMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Pseudo
@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$MoveToSummonGoal",
        remap = false)
public abstract class GoetyNecromancerMoveToSummonMixin {

    @Inject(method = "m_8036_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$denyMoveCloserToSummon(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(false);
    }
}
