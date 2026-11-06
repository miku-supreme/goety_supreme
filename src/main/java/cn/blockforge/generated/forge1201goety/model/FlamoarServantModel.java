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
import net.minecraft.world.entity.HumanoidArm;
import cn.blockforge.generated.goetysupreme.renderer.FlamoarLadyServantAnimation;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.renderer.FlamoarServantAnimation;
import cn.blockforge.generated.goetysupreme.model.FlamoarServantModel;

public class FlamoarServantModel extends HierarchicalModel<FlamoarServant> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "flamoar_servant"), "main");
    public static final ModelLayerLocation LADY_LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "flamoar_lady_servant"), "main");

    private final ModelPart root;
    private final ModelPart all_body;
    private final ModelPart head;
    private final ModelPart face;
    private final ModelPart horn;
    private final ModelPart left_horn;
    private final ModelPart right_horn;
    private final ModelPart body;
    private final ModelPart cloak;
    private final ModelPart leg;
    private final ModelPart leg2;
    private final ModelPart left_arm;
    private final ModelPart left_fire;
    private final ModelPart left_hand;
    private final ModelPart left_weapon;
    private final ModelPart right_arm;
    private final ModelPart right_fire;
    private final ModelPart right_hand;
    private final ModelPart right_weapon;

    public FlamoarServantModel(ModelPart root) {
        this.root = root;
        this.all_body = this.root.getChild("all_body");
        this.head = this.all_body.getChild("head");
        this.face = this.head.getChild("face");
        this.horn = this.head.getChild("horn");
        this.left_horn = this.horn.getChild("left_horn");
        this.right_horn = this.horn.getChild("right_horn");
        this.body = this.all_body.getChild("body");
        this.cloak = this.body.getChild("cloak");
        this.leg = this.body.getChild("leg");
        this.leg2 = this.leg.getChild("leg2");
        this.left_arm = this.all_body.getChild("left_arm");
        this.left_fire = this.left_arm.getChild("left_fire");
        this.left_hand = this.left_arm.getChild("left_hand");
        this.left_weapon = this.left_hand.getChild("left_weapon");
        this.right_arm = this.all_body.getChild("right_arm");
        this.right_fire = this.right_arm.getChild("right_fire");
        this.right_hand = this.right_arm.getChild("right_hand");
        this.right_weapon = this.right_hand.getChild("right_weapon");
    }

    public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            PartDefinition all_body = partdefinition.addOrReplaceChild("all_body", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)5.0f, (float)2.25f));
            PartDefinition head = all_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(32, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)).texOffs(64, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.75f)).texOffs(50, 74).addBox(-5.0f, -8.1875f, -3.945f, 10.0f, 10.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-13.0625f, (float)-1.065f));
            PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(56, 58).addBox(-7.6f, -4.7f, -0.8901f, 16.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-0.4f, (float)-5.2375f, (float)-2.545f));
            PartDefinition horn_r1 = face.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(68, 58).addBox(0.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)4.4f, (float)0.8f, (float)-0.8901f, (float)0.0f, (float)1.4835f, (float)0.0f));
            PartDefinition horn_r2 = face.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(68, 58).addBox(0.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)4.4f, (float)-1.2f, (float)2.1099f, (float)0.0f, (float)-0.6545f, (float)0.0f));
            PartDefinition horn_r3 = face.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-4.6f, (float)-1.2f, (float)2.1099f, (float)0.0f, (float)0.6545f, (float)0.0f));
            PartDefinition horn_r4 = face.addOrReplaceChild("horn_r4", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-4.6f, (float)0.8f, (float)-0.8901f, (float)0.0f, (float)-1.4835f, (float)0.0f));
            PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-7.9375f, (float)-0.185f));
            PartDefinition left_horn = horn.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5f, 1.7f, -4.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 2.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, 0.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -0.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -0.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -0.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -5.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -1.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -1.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -2.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -1.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 0.7f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -2.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -3.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -4.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset((float)4.5f, (float)-0.7f, (float)2.35f));
            PartDefinition right_horn = horn.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5f, 1.7f, -4.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 2.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, 0.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -0.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -0.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -0.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -5.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -1.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -1.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -2.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -1.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 0.7f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -2.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -3.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -4.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-4.5f, (float)-0.7f, (float)2.35f));
            PartDefinition body = all_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0f, -0.7f, -4.1f, 16.0f, 9.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(48, 16).addBox(-8.0f, -0.7f, -4.1f, 16.0f, 9.0f, 8.0f, new CubeDeformation(0.15f)).texOffs(0, 68).addBox(-8.0f, -0.7f, -4.6f, 16.0f, 13.0f, 9.0f, new CubeDeformation(0.25f)).texOffs(0, 33).addBox(-7.0f, 8.3f, -3.35f, 14.0f, 5.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(42, 33).addBox(-7.0f, 8.3f, -3.35f, 14.0f, 5.0f, 7.0f, new CubeDeformation(0.15f)), PartPose.offset((float)0.0f, (float)-12.3f, (float)-0.15f));
            PartDefinition cloak = body.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(100, 87).addBox(-7.0f, -0.5f, 0.0f, 14.0f, 17.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)8.8f, (float)4.4f));
            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0f, 1.0f, -3.5f, 10.0f, 6.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(34, 45).addBox(-5.0f, 1.0f, -3.5f, 10.0f, 6.0f, 7.0f, new CubeDeformation(0.15f)), PartPose.offset((float)0.0f, (float)12.3f, (float)0.15f));
            PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0f, 0.5f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(26, 58).addBox(-4.0f, 0.5f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.15f)), PartPose.offset((float)0.0f, (float)6.5f, (float)0.25f));
            PartDefinition left_arm = all_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(102, 26).mirror().addBox(0.0f, -1.0f, -2.0f, 6.0f, 13.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(100, 12).mirror().addBox(-0.5f, -2.0f, -2.0f, 7.0f, 7.0f, 7.0f, new CubeDeformation(0.5f)).mirror(false).texOffs(104, 0).mirror().addBox(-1.5f, -3.5f, -1.0f, 7.0f, 7.0f, 5.0f, new CubeDeformation(0.55f)).mirror(false), PartPose.offset((float)8.0f, (float)-11.0f, (float)-2.25f));
            PartDefinition left_fire = left_arm.addOrReplaceChild("left_fire", CubeListBuilder.create(), PartPose.offset((float)-3.5f, (float)16.5f, (float)-1.0f));
            PartDefinition fire_r1 = left_fire.addOrReplaceChild("fire_r1", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)4.0f, (float)-22.5f, (float)2.5f, (float)0.0f, (float)0.1309f, (float)0.0f));
            PartDefinition fire_r2 = left_fire.addOrReplaceChild("fire_r2", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)6.0f, (float)-22.5f, (float)2.5f, (float)0.0f, (float)-0.2618f, (float)0.0f));
            PartDefinition fire_r3 = left_fire.addOrReplaceChild("fire_r3", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)8.0f, (float)-22.5f, (float)2.5f, (float)-0.0479f, (float)-0.2129f, (float)0.2233f));
            PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(102, 67).mirror().addBox(-3.0f, 0.0f, -2.0f, 6.0f, 13.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(102, 46).mirror().addBox(-3.0f, -5.0f, -2.0f, 6.0f, 14.0f, 7.0f, new CubeDeformation(0.75f)).mirror(false), PartPose.offset((float)3.0f, (float)12.0f, (float)0.0f));
            PartDefinition left_weapon = left_hand.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset((float)0.5f, (float)9.5f, (float)-1.5f));
            PartDefinition right_arm = all_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(102, 26).addBox(-6.0f, -1.0f, -2.0f, 6.0f, 13.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(100, 12).addBox(-6.5f, -2.0f, -2.0f, 7.0f, 7.0f, 7.0f, new CubeDeformation(0.5f)).texOffs(104, 0).addBox(-5.5f, -3.5f, -1.0f, 7.0f, 7.0f, 5.0f, new CubeDeformation(0.55f)), PartPose.offset((float)-8.0f, (float)-11.0f, (float)-2.25f));
            PartDefinition right_fire = right_arm.addOrReplaceChild("right_fire", CubeListBuilder.create(), PartPose.offset((float)-8.5f, (float)16.5f, (float)-1.0f));
            PartDefinition fire_r4 = right_fire.addOrReplaceChild("fire_r4", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)4.0f, (float)-22.5f, (float)2.5f, (float)-0.0479f, (float)0.2129f, (float)-0.2233f));
            PartDefinition fire_r5 = right_fire.addOrReplaceChild("fire_r5", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)6.0f, (float)-22.5f, (float)2.5f, (float)0.0f, (float)0.2618f, (float)0.0f));
            PartDefinition fire_r6 = right_fire.addOrReplaceChild("fire_r6", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)8.0f, (float)-22.5f, (float)2.5f, (float)0.0f, (float)-0.1309f, (float)0.0f));
            PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(102, 67).addBox(-3.0f, 0.0f, -2.0f, 6.0f, 13.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(102, 46).addBox(-3.0f, -5.0f, -2.0f, 6.0f, 14.0f, 7.0f, new CubeDeformation(0.75f)), PartPose.offset((float)-3.0f, (float)12.0f, (float)0.0f));
            PartDefinition right_weapon = right_hand.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset((float)-0.5f, (float)9.5f, (float)-1.5f));
            return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
        }

    public static LayerDefinition createLadyBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            PartDefinition all_body = partdefinition.addOrReplaceChild("all_body", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)5.0f, (float)2.25f));
            PartDefinition head = all_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(32, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)).texOffs(64, 0).addBox(-4.0f, -6.9375f, -3.185f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.75f)).texOffs(50, 74).addBox(-5.0f, -8.1875f, -3.945f, 10.0f, 10.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-13.0625f, (float)-1.065f));
            PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(56, 58).addBox(-7.6f, -4.7f, -0.8901f, 16.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-0.4f, (float)-5.2375f, (float)-2.545f));
            PartDefinition horn_r1 = face.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(68, 58).addBox(0.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)4.4f, (float)0.8f, (float)-0.8901f, (float)0.0f, (float)-0.5236f, (float)0.0f));
            PartDefinition horn_r2 = face.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(68, 58).addBox(0.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)4.4f, (float)-1.2f, (float)2.1099f, (float)0.0f, (float)-0.6545f, (float)0.0f));
            PartDefinition horn_r3 = face.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-4.6f, (float)-1.2f, (float)2.1099f, (float)0.0f, (float)0.6545f, (float)0.0f));
            PartDefinition horn_r4 = face.addOrReplaceChild("horn_r4", CubeListBuilder.create().texOffs(52, 58).mirror().addBox(-4.0f, -5.5f, 0.0f, 4.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-4.6f, (float)0.8f, (float)-0.8901f, (float)0.0f, (float)0.5236f, (float)0.0f));
            PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-7.9375f, (float)-0.185f));
            PartDefinition left_horn = horn.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5f, 1.7f, -4.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 2.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, 0.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -0.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -0.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -0.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -1.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -1.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -2.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -1.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 0.7f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -2.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -3.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -3.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset((float)4.5f, (float)-0.7f, (float)2.35f));
            PartDefinition right_horn = horn.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5f, 1.7f, -4.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 1.7f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 2.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, 0.7f, -2.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, 0.7f, -3.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -0.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -0.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -0.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-0.5f, -1.3f, -1.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -1.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 2).addBox(-0.5f, -2.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, -1.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 4).addBox(-0.5f, 0.7f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -2.3f, -0.1f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 6).addBox(-0.5f, -3.3f, 0.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(4, 0).addBox(-0.5f, -3.3f, 1.9f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-4.5f, (float)-0.7f, (float)2.35f));
            PartDefinition body = all_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(5, 18).addBox(-5.0f, 0.3f, -3.1f, 10.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(53, 19).addBox(-5.0f, 0.3f, -3.1f, 10.0f, 7.0f, 6.0f, new CubeDeformation(0.15f)).texOffs(5, 70).addBox(-5.0f, 0.3f, -3.6f, 10.0f, 8.0f, 7.0f, new CubeDeformation(0.25f)), PartPose.offset((float)0.0f, (float)-12.3f, (float)-0.15f));
            PartDefinition chest_r1 = body.addOrReplaceChild("chest_r1", CubeListBuilder.create().texOffs(8, 85).addBox(-4.0f, -2.5f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation((float)0.0f, (float)3.8f, (float)-3.0f, (float)0.829f, (float)0.0f, (float)0.0f));
            PartDefinition chest_r2 = body.addOrReplaceChild("chest_r2", CubeListBuilder.create().texOffs(26, 58).addBox(-4.0f, -2.5f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.15f)).texOffs(0, 58).addBox(-4.0f, -2.5f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)3.8f, (float)-3.0f, (float)-0.7418f, (float)0.0f, (float)0.0f));
            PartDefinition cloak = body.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)4.8f, (float)4.4f));
            PartDefinition cloak_r1 = cloak.addOrReplaceChild("cloak_r1", CubeListBuilder.create().texOffs(103, 87).addBox(-4.0f, -0.5f, 0.0f, 8.0f, 17.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)-1.0f, (float)0.2182f, (float)0.0f, (float)0.0f));
            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(4, 32).addBox(-4.0f, 0.0f, -2.5f, 8.0f, 8.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(49, 32).addBox(-4.0f, 0.0f, -2.5f, 8.0f, 8.0f, 5.0f, new CubeDeformation(0.15f)), PartPose.offset((float)0.0f, (float)6.3f, (float)0.15f));
            PartDefinition leg2 = leg.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0f, 0.25f, -3.75f, 10.0f, 6.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(34, 45).addBox(-5.0f, 0.25f, -3.75f, 10.0f, 6.0f, 7.0f, new CubeDeformation(0.15f)).texOffs(0, 58).addBox(-4.0f, 3.25f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(26, 58).addBox(-4.0f, 3.25f, -2.5f, 8.0f, 5.0f, 5.0f, new CubeDeformation(0.15f)), PartPose.offset((float)0.0f, (float)4.75f, (float)0.25f));
            PartDefinition left_arm = all_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(106, 29).mirror().addBox(0.0f, -1.0f, -2.0f, 4.0f, 9.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(110, 13).mirror().addBox(0.0f, 0.0f, -2.5f, 4.0f, 5.0f, 5.0f, new CubeDeformation(0.5f)).mirror(false).texOffs(112, 0).mirror().addBox(-0.5f, -0.75f, -2.0f, 4.0f, 5.0f, 4.0f, new CubeDeformation(0.55f)).mirror(false), PartPose.offset((float)5.0f, (float)-11.0f, (float)-0.25f));
            PartDefinition left_fire = left_arm.addOrReplaceChild("left_fire", CubeListBuilder.create(), PartPose.offset((float)2.5f, (float)-3.0f, (float)-0.5f));
            PartDefinition fire_r1 = left_fire.addOrReplaceChild("fire_r1", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-2.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.1309f, (float)0.0f));
            PartDefinition fire_r2 = left_fire.addOrReplaceChild("fire_r2", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.2618f, (float)0.0f));
            PartDefinition fire_r3 = left_fire.addOrReplaceChild("fire_r3", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)2.0f, (float)0.0f, (float)0.0f, (float)-0.0479f, (float)-0.2129f, (float)0.2233f));
            PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(106, 70).mirror().addBox(-3.0f, 0.0f, -1.0f, 4.0f, 9.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(107, 49).mirror().addBox(-3.0f, 0.0f, -1.0f, 4.0f, 7.0f, 4.0f, new CubeDeformation(0.75f)).mirror(false), PartPose.offset((float)3.0f, (float)8.0f, (float)-1.0f));
            PartDefinition left_weapon = left_hand.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset((float)-1.0f, (float)7.5f, (float)1.0f));
            PartDefinition right_arm = all_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(106, 29).addBox(-4.0f, -1.0f, -2.0f, 4.0f, 9.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(110, 13).addBox(-4.0f, 0.0f, -2.5f, 4.0f, 5.0f, 5.0f, new CubeDeformation(0.5f)).texOffs(112, 0).addBox(-3.5f, -0.75f, -2.0f, 4.0f, 5.0f, 4.0f, new CubeDeformation(0.55f)), PartPose.offset((float)-5.0f, (float)-11.0f, (float)-0.25f));
            PartDefinition right_fire = right_arm.addOrReplaceChild("right_fire", CubeListBuilder.create(), PartPose.offset((float)-2.5f, (float)-3.0f, (float)-0.5f));
            PartDefinition fire_r4 = right_fire.addOrReplaceChild("fire_r4", CubeListBuilder.create().texOffs(81, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-2.0f, (float)0.0f, (float)0.0f, (float)-0.0479f, (float)0.2129f, (float)-0.2233f));
            PartDefinition fire_r5 = right_fire.addOrReplaceChild("fire_r5", CubeListBuilder.create().texOffs(71, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.2618f, (float)0.0f));
            PartDefinition fire_r6 = right_fire.addOrReplaceChild("fire_r6", CubeListBuilder.create().texOffs(61, 40).mirror().addBox(0.0f, -2.5f, -2.5f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)2.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.1309f, (float)0.0f));
            PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(106, 70).addBox(-1.0f, 0.0f, -1.0f, 4.0f, 9.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(107, 49).addBox(-1.0f, 0.0f, -1.0f, 4.0f, 7.0f, 4.0f, new CubeDeformation(0.75f)), PartPose.offset((float)-3.0f, (float)8.0f, (float)-1.0f));
            PartDefinition right_weapon = right_hand.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset((float)1.0f, (float)7.5f, (float)1.0f));
            return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
        }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(FlamoarServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        boolean lady = entity.isLady();
        this.animateWalk(lady ? FlamoarLadyServantAnimation.ATTACKWALK : FlamoarServantAnimation.ATTACKWALK,
                limbSwing, limbSwingAmount * Math.min(1.0F, entity.attackAnimProgress / 10.0F), 1.5F, 2.0F);
        this.animateWalk(lady ? FlamoarLadyServantAnimation.WALK : FlamoarServantAnimation.WALK,
                limbSwing, limbSwingAmount * Math.min(1.0F, 1.0F - entity.attackAnimProgress / 10.0F), 1.5F, 2.0F);
        this.animate(entity.idleAnimationState, lady ? FlamoarLadyServantAnimation.IDLE : FlamoarServantAnimation.IDLE, ageInTicks);
        this.animate(entity.attack1AnimationState, lady ? FlamoarLadyServantAnimation.ATTACK1 : FlamoarServantAnimation.ATTACK1, ageInTicks);
        this.animate(entity.attack2AnimationState, lady ? FlamoarLadyServantAnimation.ATTACK2 : FlamoarServantAnimation.ATTACK2, ageInTicks);
        this.animate(entity.attack3AnimationState, lady ? FlamoarLadyServantAnimation.ATTACK3 : FlamoarServantAnimation.ATTACK3, ageInTicks);
        this.animate(entity.attack4AnimationState, lady ? FlamoarLadyServantAnimation.ATTACK4 : FlamoarServantAnimation.ATTACK4, ageInTicks);
        this.animate(entity.deadAnimationState, lady ? FlamoarLadyServantAnimation.DEATH : FlamoarServantAnimation.DEATH, ageInTicks);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.all_body.translateAndRotate(poseStack);
        this.getArm(arm).translateAndRotate(poseStack);
        this.getHand(arm).translateAndRotate(poseStack);
        this.getWeapon(arm).translateAndRotate(poseStack);
    }

    public ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    public ModelPart getHand(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_hand : this.right_hand;
    }

    public ModelPart getWeapon(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.left_weapon : this.right_weapon;
    }
}
