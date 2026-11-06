package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import cn.blockforge.generated.goetysupreme.mixin.GoetyWitherSkeletonRingWeaponMixin;

@Mixin(value = Summoned.class, remap = false)
public abstract class GoetySummonedSpearGoalsMixin {

    private static final int FELL_SPEAR_GOAL_PRIORITY = 0;

    @Inject(method = {"m_8099_", "registerGoals"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$attachJerotesSpearGoals(CallbackInfo callback) {
        if (JerotesVillageCompat.isJerotesSpearGoalLoadable()) {
            Summoned self = (Summoned) (Object) this;
            JvillageSpearGoals.attachIfEligible(self.goalSelector, self);
        }
    }

    @Inject(method = {"m_8099_", "registerGoals"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$attachFellSpearGoal(CallbackInfo callback) {
        Summoned self = (Summoned) (Object) this;
        if (!FellSpearAttackGoal.isAttached(self.goalSelector)) {
            self.goalSelector.addGoal(FELL_SPEAR_GOAL_PRIORITY, new FellSpearAttackGoal(self));
        }
    }
}
