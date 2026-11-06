package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.compat.GoetyFrostNovaHelper;
import com.Polarice3.Goety.common.magic.spells.frost.FrostNovaSpell;
import com.Polarice3.Goety.utils.ModDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FrostNovaSpell.class, remap = false)
public abstract class GoetyFrostNovaDamageMixin {
    @Redirect(method = "SpellResult", at = @At(value = "INVOKE",
            target = "Lcom/Polarice3/Goety/utils/ModDamageSource;directFreeze(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/damagesource/DamageSource;"),
            require = 1)
    private DamageSource goetySupreme$extraNovaDamage(LivingEntity caster) {
        DamageSource original = ModDamageSource.directFreeze(caster);
        if ((Object) this instanceof GoetyFrostNovaHelper.ExtraFrostNovaSpell) {
            return GoetyFrostNovaHelper.extraDamageSource(original);
        }
        return original;
    }
}
