package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import cn.blockforge.generated.goetysupreme.model.SecondRounderSkeletonServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;

public class SecondRounderSkeletonServantModel<T extends Mob> extends HumanoidModel<T> {

    public SecondRounderSkeletonServantModel(ModelPart root) {
        super(root);
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        if (entity.isAggressive() && entity.getMainHandItem().getItem() instanceof BowItem) {
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.deathTime > 0) {
            ServantDeathPose.humanoid(this, entity, ageInTicks, 1.40F, 3.0F, 0.50F);
            return;
        }
        ServantSpearPose.apply(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
