package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import cn.blockforge.generated.goetysupreme.renderer.JerotesSpearAnimation;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.renderer.JvillageSpearPose;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearAttackMixin;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;

public final class ServantSpearPose {

    private ServantSpearPose() {
    }

    public static boolean apply(LivingEntity living, HumanoidModel<?> model,
            float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (FellSpearPose.holds(living)) {
            if (model.attackTime <= 0.0F) {
                FellSpearPose.apply(living, model, ageInTicks);
            }
            return true;
        }
        if (JerotesVillageCompat.isJerotesSpearGoalLoadable()
                && JvillageSpearPose.apply(living, model.head, model.rightArm, model.leftArm, model.body,
                        limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)) {
            return true;
        }
        JerotesSpearAnimation.specialAnim(living, model, model.head, model.rightArm, model.leftArm, model.body);
        return false;
    }
}
