package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;

@Mixin(value = AbstractZombieModel.class, remap = false)
public abstract class AbstractZombieModelSpearPoseMixin {

    @Inject(method = {"m_6973_(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V",
                      "setupAnim(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V"},
            at = @At("TAIL"), require = 0)
    private void goetySupreme$poseSpearForZombie(Monster entity, float limbSwing,
            float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
            CallbackInfo callback) {
        ServantSpearPose.apply(entity, (HumanoidModel<?>) (Object) this,
                limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
