package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.SummonPotencyAccess;
import com.Polarice3.Goety.common.magic.SummonSpell;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SummonSpell.class, remap = false)
public abstract class GoetySummonSpellPotencyMixin {

    @Inject(method = "buffSummon", at = @At("HEAD"), require = 0)
    private void goetySupreme$storeSummonPotency(LivingEntity caster, LivingEntity summoned,
            int potency, CallbackInfo callback) {
        if (summoned instanceof SummonPotencyAccess access) {
            access.goetySupreme$setPotency(potency);
        }
    }
}
