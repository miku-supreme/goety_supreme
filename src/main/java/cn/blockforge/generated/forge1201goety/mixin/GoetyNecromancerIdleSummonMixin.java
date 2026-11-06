package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetyDrownedNecromancerIdleSummonMixin;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$SummonUndeadGoal",
        remap = false)
public abstract class GoetyNecromancerIdleSummonMixin {

    @Inject(method = "m_8037_", at = @At("TAIL"), require = 0)
    private void goetySupreme$extraIdleSummon(CallbackInfo callback) {
        if (!NecromancerSummonExtras.goalJustSummoned(this)) {
            return;
        }
        AbstractNecromancer necro = NecromancerSummonExtras.outerNecromancer(this);
        if (necro != null && necro.level() instanceof ServerLevel level) {
            NecromancerSummonExtras.afterIdleSummon(necro, level);
        }
    }
}
