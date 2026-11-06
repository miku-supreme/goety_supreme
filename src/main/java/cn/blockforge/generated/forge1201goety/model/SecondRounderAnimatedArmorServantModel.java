package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.model.SecondRounderAnimatedArmorServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public class SecondRounderAnimatedArmorServantModel<T extends SecondRounderAnimatedArmorServant>
        extends HumanoidModel<T> {

    public SecondRounderAnimatedArmorServantModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.deathTime > 0) {
            setAllVisible(true);
            ServantDeathPose.humanoid(this, entity, ageInTicks, -0.95F, 2.2F, 0.25F);
            return;
        }
        if (entity.isInSittingPose()) {
            this.head.setRotation(0.0F, 0.0F, 0.0F);
            this.hat.setRotation(0.0F, 0.0F, 0.0F);
            this.body.setRotation(0.0F, 0.0F, 0.0F);
            this.rightArm.setRotation(0.0F, 0.0F, 0.0F);
            this.leftArm.setRotation(0.0F, 0.0F, 0.0F);
            this.rightLeg.setRotation(0.0F, 0.0F, 0.0F);
            this.leftLeg.setRotation(0.0F, 0.0F, 0.0F);
            setAllVisible(false);
        } else {
            setAllVisible(true);
            applyBlockPose(entity);
        }
    }

    private void applyBlockPose(T entity) {
        if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof ShieldItem) {
            boolean leftHand = entity.getUsedItemHand() == InteractionHand.OFF_HAND;
            poseBlockingArm(leftHand ? this.leftArm : this.rightArm);
            return;
        }
        ItemStack main = entity.getMainHandItem();
        if (main.isEmpty() || main.getItem() instanceof ShieldItem
                || main.getUseAnimation() != UseAnim.BLOCK
                || !entity.getOffhandItem().isEmpty()
                || !entity.isUsingItem()
                || entity.getUseItem().getUseAnimation() != UseAnim.BLOCK
                || entity.attackAnim > 0.0F) {
            return;
        }
        poseBlockingArm(entity.getMainArm() == HumanoidArm.RIGHT ? this.rightArm : this.leftArm);
    }

    private void poseBlockingArm(ModelPart arm) {
        if (arm == this.leftArm) {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = 30.0F * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        } else {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = -30.0F * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        }
    }
}
