package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;

@Mixin(value = SkeletonModel.class, remap = false)
public abstract class SkeletonModelSpearPoseMixin {

    @Inject(method = {"m_6973_(Lnet/minecraft/world/entity/Mob;FFFFF)V",
                      "setupAnim(Lnet/minecraft/world/entity/Mob;FFFFF)V"},
            at = @At("TAIL"), require = 0)
    private void goetySupreme$poseSpearForSkeleton(Mob entity, float limbSwing,
            float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
            CallbackInfo callback) {
        ServantSpearPose.apply(entity, (HumanoidModel<?>) (Object) this,
                limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
