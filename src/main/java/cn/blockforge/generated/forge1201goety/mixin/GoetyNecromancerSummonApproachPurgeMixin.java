package cn.blockforge.generated.goetysupreme.mixin;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerMoveToSummonMixin;

@Mixin(value = AbstractNecromancer.class, remap = false)
public abstract class GoetyNecromancerSummonApproachPurgeMixin {

    private static final org.slf4j.Logger goetySupreme$LOGGER =
            com.mojang.logging.LogUtils.getLogger();
    @Unique private static boolean goetySupreme$purgeLogged;

    @Inject(method = "m_8099_", at = @At("TAIL"), require = 0)
    private void goetySupreme$purgeMoveCloserToSummonGoals(CallbackInfo callback) {
        Mob self = (Mob) (Object) this;
        List<WrappedGoal> doomed = new ArrayList<>();
        for (WrappedGoal wrapped : self.goalSelector.getAvailableGoals()) {
            Goal goal = wrapped.getGoal();
            if (goal.getClass().getName().contains("MoveToSummon")) {
                doomed.add(wrapped);
            }
        }
        for (WrappedGoal wrapped : doomed) {
            self.goalSelector.removeGoal(wrapped.getGoal());
            if (!goetySupreme$purgeLogged) {
                goetySupreme$purgeLogged = true;
                goetySupreme$LOGGER.info(
                        "[goety_supreme] 已从 {} 的 goalSelector 摘掉 {}，恢复走近前就能开打",
                        self.getClass().getSimpleName(), goalName(wrapped.getGoal()));
            }
        }
    }

    @Unique
    private static String goalName(Goal goal) {
        String name = goal.getClass().getName();
        int dot = name.lastIndexOf('.');
        return dot < 0 ? name : name.substring(dot + 1);
    }
}
