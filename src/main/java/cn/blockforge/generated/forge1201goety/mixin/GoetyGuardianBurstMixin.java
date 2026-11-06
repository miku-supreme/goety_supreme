package cn.blockforge.generated.goetysupreme.mixin;

import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.GuardianServant;
import java.lang.reflect.Field;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyGuardianAttackRhythmMixin;

@Mixin(value = GuardianServant.class, remap = false)
public abstract class GoetyGuardianBurstMixin {

    @Inject(method = "targetSelectGoal", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$legacyTargetSelect(CallbackInfo callback) {
        Mob self = (Mob) (Object) this;
        self.targetSelector.addGoal(1, new SummonTargetGoal(self));
        callback.cancel();
    }

    @Inject(method = "targetRetaliateGoal", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$legacyRetaliate(CallbackInfo callback) {
        Mob self = (Mob) (Object) this;
        if (self instanceof PathfinderMob pathfinder) {
            pathfinder.targetSelector.addGoal(1, new ServantHurtByTargetGoal(pathfinder));
        }
        callback.cancel();
    }
}
