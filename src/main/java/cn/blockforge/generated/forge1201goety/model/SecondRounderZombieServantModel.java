package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.model.SecondRounderZombieServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;

public class SecondRounderZombieServantModel extends HumanoidModel<SecondRounderZombieServant> {

    public SecondRounderZombieServantModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(SecondRounderZombieServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.deathTime > 0) {
            ServantDeathPose.humanoid(this, entity, ageInTicks, 1.30F, 2.8F, 0.30F);
            return;
        }
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm,
                entity.isAggressive(), this.attackTime, ageInTicks);
        ServantSpearPose.apply(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
