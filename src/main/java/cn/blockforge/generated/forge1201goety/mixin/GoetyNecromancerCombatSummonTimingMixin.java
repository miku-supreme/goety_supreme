package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$SummoningSpellGoal",
        remap = false)
public abstract class GoetyNecromancerCombatSummonTimingMixin {

    @Shadow
    public abstract int getCastingInterval();

    @Inject(method = {"m_8056_", "start"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$cooldownAtStart(CallbackInfo callback) {
        NecromancerSummonExtras.forceSpellCooldown(
                NecromancerSummonExtras.outerNecromancer(this), this.getCastingInterval());
    }

    @Redirect(method = {"m_8041_", "stop"},
            at = @At(value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/common/entities/neutral/AbstractNecromancer;setSpellCooldown(I)V"),
            require = 0)
    private void goetySupreme$noCooldownOnStop(AbstractNecromancer necro, int ticks) {
    }

    @Inject(method = "timeToCast", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$castAtTen(CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(10);
    }
}
