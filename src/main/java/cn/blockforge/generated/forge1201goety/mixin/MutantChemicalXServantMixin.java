package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.compat.MutantPotionServantCompatibility;
import fuzs.mutantmonsters.world.entity.SkullSpirit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "fuzs.mutantmonsters.world.effect.ChemicalXMobEffect", remap = false)
public abstract class MutantChemicalXServantMixin {
    @Inject(method = "m_6742_", at = @At("HEAD"), cancellable = true, require = 1)
    private void goetySupreme$startServantMutation(LivingEntity target, int amplifier, CallbackInfo callback) {
        if (!(target instanceof Mob mob) || target.level().isClientSide
                || !MutantPotionServantCompatibility.isCompatibleSource(target)) {
            return;
        }
        SkullSpirit spirit = new SkullSpirit(target.level(), mob);
        spirit.setPos(mob.getX(), mob.getY(), mob.getZ());
        target.level().addFreshEntity(spirit);
        callback.cancel();
    }

    @Inject(method = "getMutantOf", at = @At("HEAD"), cancellable = true, require = 1)
    private static void goetySupreme$servantMutantType(LivingEntity target,
            CallbackInfoReturnable<EntityType<?>> callback) {
        EntityType<?> mutant = MutantPotionServantCompatibility.mutantFor(target);
        if (mutant != null) {
            callback.setReturnValue(mutant);
        }
    }
}
