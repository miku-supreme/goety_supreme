package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import com.Polarice3.Goety.client.render.model.HauntedArmorModel;
import com.Polarice3.Goety.common.entities.neutral.AbstractHauntedArmor;
import net.minecraft.client.model.HumanoidModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import cn.blockforge.generated.goetysupreme.util.HauntedArmorDressing;
import cn.blockforge.generated.goetysupreme.mixin.AbstractZombieModelSpearPoseMixin;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;
import cn.blockforge.generated.goetysupreme.mixin.SkeletonModelSpearPoseMixin;

@Mixin(value = HauntedArmorModel.class, remap = false)
public abstract class HauntedArmorSpearPoseMixin {

    @Inject(
            method = "setupAnim(Lcom/Polarice3/Goety/common/entities/neutral/AbstractHauntedArmor;FFFFF)V",
            at = @At("TAIL"), require = 0)
    private void goetySupreme$poseFellSpearForHauntedArmor(AbstractHauntedArmor entity,
            float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch, CallbackInfo callback) {
        ServantSpearPose.apply(entity, (HumanoidModel<?>) (Object) this,
                limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
