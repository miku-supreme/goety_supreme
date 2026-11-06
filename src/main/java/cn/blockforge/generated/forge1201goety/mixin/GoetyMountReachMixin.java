package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.util.MountReachBonus;
import com.Polarice3.Goety.common.entities.ai.ModMeleeAttackGoal;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.SerponIntruder;

@Mixin(value = ModMeleeAttackGoal.class, remap = false)
public abstract class GoetyMountReachMixin {
    @Inject(method = "getAttackReachSqr", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$mountedReach(LivingEntity target, CallbackInfoReturnable<Double> callback) {
        Entity vehicle = ((ModMeleeAttackGoal) (Object) this).mob.getVehicle();
        double bonus = MountReachBonus.bonusFor(vehicle);
        if (bonus <= 0.0D) {
            return;
        }
        double reach = Math.sqrt(Math.max(0.0D, callback.getReturnValueD())) + bonus;
        callback.setReturnValue(reach * reach);
    }

    @Inject(method = "getAttackReachSqr", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$jerotesSpearReach(LivingEntity target, CallbackInfoReturnable<Double> callback) {
        PathfinderMob mob = ((ModMeleeAttackGoal) (Object) this).mob;
        if (!(mob instanceof WitherSkeletonServant)) {
            return;
        }
        ItemStack main = mob.getMainHandItem();
        if (!JerotesItems.isSpear(main) && !JerotesItems.isPike(main)
                && !FellSpearItem.isSpear(main)) {
            return;
        }
        callback.setReturnValue(Math.max(callback.getReturnValueD(), 9.0D));
    }
}
