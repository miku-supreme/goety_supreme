package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import cn.blockforge.generated.goetysupreme.entity.FrostYetiServant;
import cn.blockforge.generated.goetysupreme.renderer.FrostYetiServantAnimation;
import cn.blockforge.generated.goetysupreme.model.FrostYetiServantModel;

public class FrostYetiServantModel extends HierarchicalModel<FrostYetiServant> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "frost_yeti_servant"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart left_hand;
    private final ModelPart right_arm;
    private final ModelPart right_hand;

    public FrostYetiServantModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.left_hand = this.left_arm.getChild("left_hand");
        this.right_arm = root.getChild("right_arm");
        this.right_hand = this.right_arm.getChild("right_hand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(24, 24).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -4.0F, -5.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -9.6302F, -0.8218F));
        body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(34, 10).addBox(-5.0F, -6.0F, -1.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6302F, 0.8218F, 0.2182F, 0.0F, 0.0F));
        body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -15.0F, 0.0F, 14.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.6302F, 0.8218F, 0.3927F, 0.0F, 0.0F));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(8.0F, -9.0F, 0.0F));
        left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(42, 37).mirror().addBox(-2.4132F, -6.5076F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false).texOffs(0, 32).mirror().addBox(-2.4132F, -6.5076F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.4132F, 6.5076F, -1.0F, 0.1745F, 0.0F, -0.1745F));
        PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(2.5F, 11.0F, -1.0F));
        left_hand.addOrReplaceChild("left_hand_r1", CubeListBuilder.create().texOffs(42, 37).mirror().addBox(-2.5F, -6.5F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false).texOffs(0, 51).mirror().addBox(-2.5F, -6.5F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, -0.0873F, 0.0F, 0.0F));
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-8.0F, -9.0F, 0.0F));
        right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(42, 37).addBox(-2.5868F, -6.5076F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).texOffs(0, 32).addBox(-2.5868F, -6.5076F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.4132F, 6.5076F, -1.0F, 0.1745F, 0.0F, 0.1745F));
        PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-2.5F, 11.0F, -1.0F));
        right_hand.addOrReplaceChild("right_hand_r1", CubeListBuilder.create().texOffs(42, 37).addBox(-2.5F, -6.5F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).texOffs(0, 51).addBox(-2.5F, -6.5F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.5F, -0.0873F, 0.0F, 0.0F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 6.0F, 0.0F));
        left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(22, 37).mirror().addBox(-2.5F, -5.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 4.5F, 0.5F, -0.3054F, 0.0F, -0.0873F));
        left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(22, 53).mirror().addBox(-2.5F, -0.5F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false).texOffs(40, 0).mirror().addBox(-2.5F, 5.5F, -2.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.5F, 7.5F, 0.0F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 6.0F, 0.0F));
        right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(22, 37).addBox(-2.5F, -5.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.5F, -0.3054F, 0.0F, 0.0873F));
        right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(22, 53).addBox(-2.5F, -0.5F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.25F)).texOffs(40, 0).addBox(-2.5F, 5.5F, -2.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-0.5F, 7.5F, 0.0F));
        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(FrostYetiServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = Mth.clamp(headPitch, -30.0F, 30.0F) * ((float) Math.PI / 180.0F);
        this.head.yRot = Mth.clamp(netHeadYaw, -25.0F, 25.0F) * ((float) Math.PI / 180.0F);
        if (entity.isYetiRush()) {
            this.animateWalk(FrostYetiServantAnimation.RUSH, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        } else if (entity.isAggressive()) {
            this.animateWalk(FrostYetiServantAnimation.RUN, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        } else {
            this.animateWalk(FrostYetiServantAnimation.WALK, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        }
        this.animate(entity.attack1AnimationState, FrostYetiServantAnimation.ATTACK1, ageInTicks);
        this.animate(entity.attack2AnimationState, FrostYetiServantAnimation.ATTACK2, ageInTicks);
        this.animate(entity.attack3AnimationState, FrostYetiServantAnimation.ATTACK3, ageInTicks);
        if (!entity.isLeftHanded()) {
            this.animate(entity.throwAnimationState, FrostYetiServantAnimation.THROW1, ageInTicks);
        } else {
            this.animate(entity.throwAnimationState, FrostYetiServantAnimation.THROW2, ageInTicks);
        }
        this.animate(entity.idleAnimationState, FrostYetiServantAnimation.IDLE, ageInTicks);
        this.animate(entity.deterAnimationState, FrostYetiServantAnimation.DETER, ageInTicks);
        this.animate(entity.deadAnimationState, FrostYetiServantAnimation.DEAD, ageInTicks);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.getArm(arm).translateAndRotate(poseStack);
        this.getHand(arm).translateAndRotate(poseStack);
    }

    private ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    private ModelPart getHand(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_hand : this.right_hand;
    }
}
