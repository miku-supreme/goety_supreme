package cn.blockforge.generated.goetysupreme.mixin;

import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;

@Mixin(value = MutantEnderman.class, remap = false)
public abstract class MutantEndermanVoidHealMixin {

    private static final float GOETY_SUPREME_RAID_HEAL = 1.0F;

    private static final float GOETY_SUPREME_CLONE_HEAL = 5.0F;

    private static final int GOETY_SUPREME_CLONE_HEAL_TICK = 1;

    @Inject(method = "teleportAttack", at = @At("HEAD"), require = 0)
    private static void goetySupreme$healOnTeleportRaid(LivingEntity enderman, CallbackInfo callback) {
        if (!(enderman instanceof MutantEnderman mutant) || mutant.level().isClientSide() || mutant.isClone()) {
            return;
        }
        mutant.heal(GOETY_SUPREME_RAID_HEAL);
    }

    @Inject(method = "m_8107_", at = @At("HEAD"), require = 0)
    private void goetySupreme$healOnCloneSkill(CallbackInfo callback) {
        MutantEnderman self = (MutantEnderman) (Object) this;
        if (self.level().isClientSide() || self.isClone()) {
            return;
        }
        if (self.getAnimation() != MutantEnderman.CLONE_ANIMATION
                || self.getAnimationTick() != GOETY_SUPREME_CLONE_HEAL_TICK) {
            return;
        }
        self.heal(GOETY_SUPREME_CLONE_HEAL);
    }
}
