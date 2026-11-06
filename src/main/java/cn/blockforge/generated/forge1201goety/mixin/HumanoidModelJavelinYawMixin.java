package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.JavelinThrowPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;

@Mixin(value = HumanoidModel.class, remap = false)
public abstract class HumanoidModelJavelinYawMixin {

    @Inject(method = {"m_102875_(Lnet/minecraft/world/entity/LivingEntity;)V",
                      "poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V"},
            at = @At("TAIL"), require = 0)
    private void goetySupreme$javelinYawRight(LivingEntity entity, CallbackInfo callback) {
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        boolean throwSpearPose = model.rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR;
        if (!JavelinThrowPose.isJavelinForArm(entity, HumanoidArm.RIGHT, throwSpearPose)) {
            return;
        }
        JavelinThrowPose.followHeadYaw(model, HumanoidArm.RIGHT);
    }

    @Inject(method = {"m_102878_(Lnet/minecraft/world/entity/LivingEntity;)V",
                      "poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V"},
            at = @At("TAIL"), require = 0)
    private void goetySupreme$javelinYawLeft(LivingEntity entity, CallbackInfo callback) {
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        boolean throwSpearPose = model.leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR;
        if (!JavelinThrowPose.isJavelinForArm(entity, HumanoidArm.LEFT, throwSpearPose)) {
            return;
        }
        JavelinThrowPose.followHeadYaw(model, HumanoidArm.LEFT);
    }
}
