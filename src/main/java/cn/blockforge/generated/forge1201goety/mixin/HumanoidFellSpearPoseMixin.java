package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HumanoidModel.class, remap = false)
public abstract class HumanoidFellSpearPoseMixin {

    @Inject(method = {"m_102875_(Lnet/minecraft/world/entity/LivingEntity;)V",
                      "poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V"},
            at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$poseFellSpearRightArm(LivingEntity entity, CallbackInfo callback) {
        if (!FellSpearPose.armHolds(entity, HumanoidArm.RIGHT)) {
            return;
        }
        FellSpearPose.applyArm((HumanoidModel<?>) (Object) this, entity, HumanoidArm.RIGHT,
                FellSpearPose.isUsingSpear(entity));
        callback.cancel();
    }

    @Inject(method = {"m_102878_(Lnet/minecraft/world/entity/LivingEntity;)V",
                      "poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V"},
            at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$poseFellSpearLeftArm(LivingEntity entity, CallbackInfo callback) {
        if (!FellSpearPose.armHolds(entity, HumanoidArm.LEFT)) {
            return;
        }
        FellSpearPose.applyArm((HumanoidModel<?>) (Object) this, entity, HumanoidArm.LEFT,
                FellSpearPose.isUsingSpear(entity));
        callback.cancel();
    }
}
