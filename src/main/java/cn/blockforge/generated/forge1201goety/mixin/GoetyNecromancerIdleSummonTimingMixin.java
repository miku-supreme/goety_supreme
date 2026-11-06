package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$SummonUndeadGoal",
        remap = false)
public abstract class GoetyNecromancerIdleSummonTimingMixin {

    private static final int GOETY_SUPREME_IDLE_SUMMON_COOLDOWN = 100;

    @Inject(method = {"m_8056_", "start"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$cooldownAtStart(CallbackInfo callback) {
        NecromancerSummonExtras.forceSpellCooldown(
                NecromancerSummonExtras.outerNecromancer(this), GOETY_SUPREME_IDLE_SUMMON_COOLDOWN);
    }

    @Redirect(method = {"m_8041_", "stop"},
            at = @At(value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/common/entities/neutral/AbstractNecromancer;setSpellCooldown(I)V"),
            require = 0)
    private void goetySupreme$noCooldownOnStop(AbstractNecromancer necro, int ticks) {
    }
}
