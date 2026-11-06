package cn.blockforge.generated.goetysupreme.model;

import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.model.RagingMonstrosityModel;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public final class RagingMonstrosityModel extends EntityModel<RedstoneMonstrosity> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "raging_monstrosity"), "main");

    private final ModelPart root;

    public RagingMonstrosityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition root = parts.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 23.0F, 0.0F, 1.5708F, -0.0F, 0.0F));
        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.0F, 28.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition right_leg = hip.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-19.0F, -0.0F, -3.0F, -3.1416F, -0.0F, 0.0F));
        PartDefinition h_right_leg_0 = right_leg.addOrReplaceChild("h_right_leg_0", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, -0.465F, 10.5F, 1.5708F, -0.0F, 0.0F));
        h_right_leg_0.addOrReplaceChild("c_h_right_leg_0", CubeListBuilder.create().texOffs(0, 171).addBox(-13.5F, -13.5F, -9.5F, 24.0F, 29.0F, 19.0F), PartPose.ZERO);
        PartDefinition left_leg = hip.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(19.0F, -0.0F, -3.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_leg_0 = left_leg.addOrReplaceChild("h_left_leg_0", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.535F, -10.5F, -1.5708F, -0.0F, 0.0F));
        h_left_leg_0.addOrReplaceChild("c_h_left_leg_0", CubeListBuilder.create().texOffs(86, 171).addBox(-13.5F, -14.5F, -9.5F, 24.0F, 29.0F, 19.0F), PartPose.ZERO);
        PartDefinition pelvis = hip.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.0F, -3.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_pelvis_0 = pelvis.addOrReplaceChild("h_pelvis_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -0.7F, 5.525F, -1.5708F, -0.0F, 0.0F));
        h_pelvis_0.addOrReplaceChild("c_h_pelvis_0", CubeListBuilder.create().texOffs(0, 139).addBox(-13.5F, -5.5F, -8.6F, 28.0F, 11.0F, 21.0F), PartPose.ZERO);
        PartDefinition upper = pelvis.addOrReplaceChild("upper", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.0F, 11.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_upper_0 = upper.addOrReplaceChild("h_upper_0", CubeListBuilder.create(), PartPose.offsetAndRotation(0.525F, -0.035F, 28.0F, -1.5708F, -0.0F, 0.0F));
        h_upper_0.addOrReplaceChild("c_h_upper_0", CubeListBuilder.create().texOffs(179, 41).addBox(-16.0F, -22.0F, 13.5F, 28.0F, 16.0F, 11.0F), PartPose.ZERO);
        PartDefinition h_upper_1 = upper.addOrReplaceChild("h_upper_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.525F, -0.035F, 28.0F, -1.5708F, -0.0F, 0.0F));
        h_upper_1.addOrReplaceChild("c_h_upper_1", CubeListBuilder.create().texOffs(1, 52).addBox(-38.5F, -29.0F, -13.5F, 74.0F, 57.0F, 30.0F), PartPose.ZERO);
        PartDefinition left_arm = upper.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(40.0F, -2.0F, 36.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_arm_0 = left_arm.addOrReplaceChild("h_left_arm_0", CubeListBuilder.create(), PartPose.offsetAndRotation(14.7F, 0.475F, -0.5F, -1.5708F, -0.0F, 0.0F));
        h_left_arm_0.addOrReplaceChild("c_h_left_arm_0", CubeListBuilder.create().texOffs(285, 78).addBox(-18.3F, -11.5F, -12.0F, 37.0F, 23.0F, 27.0F), PartPose.ZERO);
        PartDefinition h_left_arm_1 = left_arm.addOrReplaceChild("h_left_arm_1", CubeListBuilder.create(), PartPose.offsetAndRotation(16.025F, 4.975F, 22.5F, -1.5708F, -0.0F, 3.1416F));
        h_left_arm_1.addOrReplaceChild("c_h_left_arm_1", CubeListBuilder.create().texOffs(386, 50).addBox(-0.425F, -11.5F, -10.5F, 20.0F, 23.0F, 27.0F), PartPose.ZERO);
        PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create(), PartPose.offsetAndRotation(16.0F, 2.955F, -12.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_shoulder_0 = left_shoulder.addOrReplaceChild("h_left_shoulder_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 3.995F, -6.0F, -1.5708F, -0.0F, 0.0F));
        h_left_shoulder_0.addOrReplaceChild("c_h_left_shoulder_0", CubeListBuilder.create().texOffs(209, 78).mirror().addBox(-12.0F, -11.0F, -13.5F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition left_hand = left_shoulder.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.955F, -17.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_hand_0 = left_hand.addOrReplaceChild("h_left_hand_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 4.2F, -9.5F, -1.5708F, -0.0F, 0.0F));
        h_left_hand_0.addOrReplaceChild("c_h_left_hand_0", CubeListBuilder.create().texOffs(294, 128).addBox(-14.5F, -9.5F, -18.0F, 29.0F, 20.0F, 29.0F, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition left_finger_back = left_hand.addOrReplaceChild("left_finger_back", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, 4.85F, -21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_finger_back_0 = left_finger_back.addOrReplaceChild("h_left_finger_back_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.065F, -3.975F, -1.5708F, -0.0F, 3.1416F));
        h_left_finger_back_0.addOrReplaceChild("c_h_left_finger_back_0", CubeListBuilder.create().texOffs(182, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition left_thumb = left_hand.addOrReplaceChild("left_thumb", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 1.0F, -21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_thumb_0 = left_thumb.addOrReplaceChild("h_left_thumb_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.715F, -2.975F, -1.5708F, -0.0F, 3.1416F));
        h_left_thumb_0.addOrReplaceChild("c_h_left_thumb_0", CubeListBuilder.create().texOffs(198, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition left_finger_front = left_hand.addOrReplaceChild("left_finger_front", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, -6.31F, -21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_left_finger_front_0 = left_finger_front.addOrReplaceChild("h_left_finger_front_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 0.095F, -3.975F, -1.5708F, -0.0F, 3.1416F));
        h_left_finger_front_0.addOrReplaceChild("c_h_left_finger_front_0", CubeListBuilder.create().texOffs(182, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.ZERO);
        PartDefinition right_arm = upper.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-39.0F, -1.045F, 36.0F, -3.1416F, -0.0F, 0.0F));
        PartDefinition h_right_arm_0 = right_arm.addOrReplaceChild("h_right_arm_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.6F, 0.47F, 0.5F, 1.5708F, -0.0F, 0.0F));
        h_right_arm_0.addOrReplaceChild("c_h_right_arm_0", CubeListBuilder.create().texOffs(257, 28).addBox(-18.3F, -11.5F, -12.0F, 37.0F, 23.0F, 27.0F), PartPose.ZERO);
        PartDefinition h_right_arm_1 = right_arm.addOrReplaceChild("h_right_arm_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.6F, -2.53F, -22.5F, 1.5708F, -0.0F, 0.0F));
        h_right_arm_1.addOrReplaceChild("c_h_right_arm_1", CubeListBuilder.create().texOffs(358, 0).addBox(-2.4F, -11.5F, -15.0F, 20.1F, 23.0F, 27.0F), PartPose.ZERO);
        PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.0F, -2.0F, 12.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_right_shoulder_0 = right_shoulder.addOrReplaceChild("h_right_shoulder_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -4.005F, 6.0F, 1.5708F, -0.0F, 0.0F));
        h_right_shoulder_0.addOrReplaceChild("c_h_right_shoulder_0", CubeListBuilder.create().texOffs(209, 78).addBox(-12.0F, -11.0F, -13.5F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition right_hand = right_shoulder.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 0.954F, 17.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_right_hand_0 = right_hand.addOrReplaceChild("h_right_hand_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -4.209F, 9.5F, 1.5708F, -0.0F, 0.0F));
        h_right_hand_0.addOrReplaceChild("c_h_right_hand_0", CubeListBuilder.create().texOffs(182, 116).addBox(-15.0F, -9.5F, -18.2F, 29.0F, 20.0F, 29.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition right_finger_back = right_hand.addOrReplaceChild("right_finger_back", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, -4.85F, 21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_right_finger_back_0 = right_finger_back.addOrReplaceChild("h_right_finger_back_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 0.076F, 4.025F, 1.5708F, -0.0F, 3.1416F));
        h_right_finger_back_0.addOrReplaceChild("c_h_right_finger_back_0", CubeListBuilder.create().texOffs(182, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition right_thumb = right_hand.addOrReplaceChild("right_thumb", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, -1.0F, 21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_right_thumb_0 = right_thumb.addOrReplaceChild("h_right_thumb_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 0.726F, 4.025F, 1.5708F, -0.0F, 3.1416F));
        h_right_thumb_0.addOrReplaceChild("c_h_right_thumb_0", CubeListBuilder.create().texOffs(198, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition right_finger_front = right_hand.addOrReplaceChild("right_finger_front", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 6.31F, 21.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_right_finger_front_0 = right_finger_front.addOrReplaceChild("h_right_finger_front_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.184F, 4.025F, 1.5708F, -0.0F, 3.1416F));
        h_right_finger_front_0.addOrReplaceChild("c_h_right_finger_front_0", CubeListBuilder.create().texOffs(182, 163).addBox(-1.5F, -5.0F, -2.25F, 3.0F, 10.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition neck = upper.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.387F, -15.019F, 31.307F, 0.0F, -0.0F, 0.023F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -0.0F, 6.094F, -0.0051F, -0.0F, -0.0258F));
        PartDefinition h_head_0 = head.addOrReplaceChild("h_head_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.175F, -31.0F, 20.0F, -1.5708F, -0.0F, 0.0F));
        h_head_0.addOrReplaceChild("c_h_head_0", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -1.0F, 13.5F, 28.0F, 31.0F, 21.0F), PartPose.ZERO);
        PartDefinition h_head_1 = head.addOrReplaceChild("h_head_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.675F, -7.8625F, -20.775F, -1.5708F, -0.0F, 0.0F));
        h_head_1.addOrReplaceChild("c_h_head_1", CubeListBuilder.create().texOffs(77, 31).addBox(-13.5F, -16.0F, -9.3625F, 27.0F, 0.0F, 21.0F, new CubeDeformation(-0.01F)), PartPose.ZERO);
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -1.982F, -21.657F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_jaw_0 = jaw.addOrReplaceChild("h_jaw_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.675F, -4.8805F, 2.882F, -1.5708F, -0.0F, 0.0F));
        h_jaw_0.addOrReplaceChild("c_h_jaw_0", CubeListBuilder.create().texOffs(98, 0).addBox(-13.5F, -5.125F, -10.3625F, 27.0F, 10.0F, 21.0F), PartPose.ZERO);
        PartDefinition h_jaw_1 = jaw.addOrReplaceChild("h_jaw_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.675F, -5.8805F, 0.882F, -1.5708F, -0.0F, 0.0F));
        h_jaw_1.addOrReplaceChild("c_h_jaw_1", CubeListBuilder.create().texOffs(77, 31).addBox(-13.5F, -0.0F, -9.3625F, 27.0F, 0.0F, 21.0F, new CubeDeformation(-0.01F)), PartPose.ZERO);
        PartDefinition eye_middle = head.addOrReplaceChild("eye_middle", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, -17.572F, 2.0F, -0.0F, -0.0F, 0.0F));
        eye_middle.addOrReplaceChild("c_eye_middle_0", CubeListBuilder.create().texOffs(-3, 18).addBox(-2.587F, -0.0F, 0.0F, 5.8F, 0.0F, 3.0F), PartPose.ZERO);
        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create(), PartPose.offsetAndRotation(11.0F, -17.572F, -2.0F, -0.0F, -0.0F, 0.0F));
        eye_left.addOrReplaceChild("c_eye_left_0", CubeListBuilder.create().texOffs(-4, 14).addBox(-1.987F, -0.0F, 0.0F, 3.8F, 0.0F, 4.0F), PartPose.ZERO);
        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, -17.572F, -2.0F, -0.0F, -0.0F, 0.0F));
        eye_right.addOrReplaceChild("c_eye_right_0", CubeListBuilder.create().texOffs(-4, 14).addBox(-2.137F, -0.0F, 0.0F, 3.8F, 0.0F, 4.0F), PartPose.ZERO);
        PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0F, 15.964F, -68.0F, -0.0F, -0.0F, 0.0F));
        PartDefinition h_horns_0 = horns.addOrReplaceChild("h_horns_0", CubeListBuilder.create(), PartPose.offsetAndRotation(-24.2F, -24.0F, 80.5F, -1.5708F, -0.0F, 0.0F));
        h_horns_0.addOrReplaceChild("c_h_horns_0", CubeListBuilder.create().texOffs(196, 0).addBox(-10.0F, -6.5F, -5.5F, 20.0F, 13.0F, 13.0F), PartPose.ZERO);
        PartDefinition h_horns_1 = horns.addOrReplaceChild("h_horns_1", CubeListBuilder.create(), PartPose.offsetAndRotation(22.8F, -24.0F, 80.5F, -1.5708F, -0.0F, 0.0F));
        h_horns_1.addOrReplaceChild("c_h_horns_1", CubeListBuilder.create().texOffs(446, 0).addBox(-10.0F, -6.5F, -5.5F, 20.0F, 13.0F, 13.0F), PartPose.ZERO);
        PartDefinition h_horns_2 = horns.addOrReplaceChild("h_horns_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-24.2F, -24.0F, 96.5F, -1.5708F, -0.0F, 0.0F));
        h_horns_2.addOrReplaceChild("c_h_horns_2", CubeListBuilder.create().texOffs(262, 0).addBox(-10.0F, -5.5F, -5.5F, 9.0F, 15.0F, 13.0F), PartPose.ZERO);
        PartDefinition h_horns_3 = horns.addOrReplaceChild("h_horns_3", CubeListBuilder.create(), PartPose.offsetAndRotation(33.8F, -24.0F, 96.5F, -1.5708F, -0.0F, 0.0F));
        h_horns_3.addOrReplaceChild("c_h_horns_3", CubeListBuilder.create().texOffs(306, 0).addBox(-10.0F, -5.5F, -5.5F, 9.0F, 15.0F, 13.0F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 512, 256);
    }

    public ModelPart root() {
        return this.root;
    }

    private java.util.Map<String, ModelPart> parts;

    public ModelPart part(String name) {
        if (this.parts == null) {
            this.parts = new java.util.HashMap<>();
            this.parts.put("root", child(this.root, "root"));
            this.parts.put("hip", child(this.root, "root", "hip"));
            this.parts.put("right_leg", child(this.root, "root", "hip", "right_leg"));
            this.parts.put("left_leg", child(this.root, "root", "hip", "left_leg"));
            this.parts.put("pelvis", child(this.root, "root", "hip", "pelvis"));
            this.parts.put("upper", child(this.root, "root", "hip", "pelvis", "upper"));
            this.parts.put("left_arm", child(this.root, "root", "hip", "pelvis", "upper", "left_arm"));
            this.parts.put("left_shoulder", child(this.root, "root", "hip", "pelvis", "upper", "left_arm", "left_shoulder"));
            this.parts.put("left_hand", child(this.root, "root", "hip", "pelvis", "upper", "left_arm", "left_shoulder", "left_hand"));
            this.parts.put("left_finger_back", child(this.root, "root", "hip", "pelvis", "upper", "left_arm", "left_shoulder", "left_hand", "left_finger_back"));
            this.parts.put("left_thumb", child(this.root, "root", "hip", "pelvis", "upper", "left_arm", "left_shoulder", "left_hand", "left_thumb"));
            this.parts.put("left_finger_front", child(this.root, "root", "hip", "pelvis", "upper", "left_arm", "left_shoulder", "left_hand", "left_finger_front"));
            this.parts.put("right_arm", child(this.root, "root", "hip", "pelvis", "upper", "right_arm"));
            this.parts.put("right_shoulder", child(this.root, "root", "hip", "pelvis", "upper", "right_arm", "right_shoulder"));
            this.parts.put("right_hand", child(this.root, "root", "hip", "pelvis", "upper", "right_arm", "right_shoulder", "right_hand"));
            this.parts.put("right_finger_back", child(this.root, "root", "hip", "pelvis", "upper", "right_arm", "right_shoulder", "right_hand", "right_finger_back"));
            this.parts.put("right_thumb", child(this.root, "root", "hip", "pelvis", "upper", "right_arm", "right_shoulder", "right_hand", "right_thumb"));
            this.parts.put("right_finger_front", child(this.root, "root", "hip", "pelvis", "upper", "right_arm", "right_shoulder", "right_hand", "right_finger_front"));
            this.parts.put("neck", child(this.root, "root", "hip", "pelvis", "upper", "neck"));
            this.parts.put("head", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head"));
            this.parts.put("jaw", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head", "jaw"));
            this.parts.put("eye_middle", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head", "eye_middle"));
            this.parts.put("eye_left", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head", "eye_left"));
            this.parts.put("eye_right", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head", "eye_right"));
            this.parts.put("horns", child(this.root, "root", "hip", "pelvis", "upper", "neck", "head", "horns"));
        }
        return this.parts.getOrDefault(name, SafeModelParts.dummy());
    }

    private static ModelPart child(ModelPart parent, String... path) {
        ModelPart part = parent;
        for (String step : path) {
            if (part == null) {
                return SafeModelParts.dummy();
            }
            part = part.getChild(step);
        }
        return part == null ? SafeModelParts.dummy() : part;
    }

    @Override
    public void setupAnim(RedstoneMonstrosity entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        RagingMonstrosityAnimator.setup(this, entity, limbSwing, limbSwingAmount, ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
