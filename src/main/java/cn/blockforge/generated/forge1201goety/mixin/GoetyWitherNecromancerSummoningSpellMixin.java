package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractWitherNecromancer$SummonServantSpell",
        remap = false)
public abstract class GoetyWitherNecromancerSummoningSpellMixin {

    @Inject(method = "m_8037_", at = @At(value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/common/entities/neutral/AbstractWitherNecromancer$SummonServantSpell;castSpell()V",
                    shift = At.Shift.AFTER),
            require = 0)
    private void goetySupreme$extraWitherCombatSummon(CallbackInfo callback) {
        AbstractNecromancer necro = NecromancerSummonExtras.outerNecromancer(this);
        if (necro != null && necro.level() instanceof ServerLevel level) {
            NecromancerSummonExtras.afterCombatSummon(necro, level);
        }
    }
}
