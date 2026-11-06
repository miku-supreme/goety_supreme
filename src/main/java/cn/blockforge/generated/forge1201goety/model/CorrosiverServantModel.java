
package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
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
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverAnimation;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.model.CorrosiverServantModel;

public class CorrosiverServantModel<T extends LivingEntity>
extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("goety_supreme", "corrosiver_servant"), "main");
    private final ModelPart root;
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart head;

    public CorrosiverServantModel(ModelPart root) {
        super(0.5f, 24.0f);
        this.root = root;
        this.main = root.getChild("main");
        this.body = this.main.getChild("body");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-1.0f, (float)-33.0f, (float)10.0f, (float)0.2182f, (float)0.0f, (float)0.0f));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 66).addBox(-5.0f, -10.7657f, -25.7939f, 10.0f, 10.0f, 20.0f, new CubeDeformation(0.005f)).texOffs(154, 62).addBox(-4.0f, -0.7657f, -24.7939f, 8.0f, 1.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0f, (float)-16.0f, (float)-17.0f, (float)-0.2182f, (float)0.0f, (float)0.0f));
        PartDefinition neck_hair_r1 = head.addOrReplaceChild("neck_hair_r1", CubeListBuilder.create().texOffs(140, -12).addBox(0.0f, -14.0f, -6.0f, 0.0f, 8.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(84, 0).addBox(-4.0f, -6.0f, -6.0f, 8.0f, 12.0f, 12.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-1.5425f, (float)-4.7207f, (float)-0.5236f, (float)0.0f, (float)0.0f));
        PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-5.48f, (float)-15.6028f, (float)-12.5796f, (float)0.6227f, (float)0.0198f, (float)-0.2531f));
        PartDefinition right_horn_r1 = right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(148, 44).addBox(0.0f, -6.0f, -8.0f, 2.0f, 4.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(128, 44).addBox(0.0f, -2.0f, -4.0f, 2.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-1.0f, (float)1.9176f, (float)-0.7568f, (float)-1.7017f, (float)0.0f, (float)0.0f));
        PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation((float)5.48f, (float)-15.6028f, (float)-12.5796f, (float)0.6227f, (float)-0.0198f, (float)0.2531f));
        PartDefinition left_horn_r1 = left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(148, 44).mirror().addBox(-2.0f, -6.0f, -8.0f, 2.0f, 4.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(128, 44).mirror().addBox(-2.0f, -2.0f, -4.0f, 2.0f, 10.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)1.0f, (float)1.9176f, (float)-0.7568f, (float)-1.7017f, (float)0.0f, (float)0.0f));
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(114, 62).addBox(-5.0f, 0.2343f, -15.7939f, 10.0f, 4.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(154, 62).addBox(-4.0f, -0.7657f, -14.2939f, 8.0f, 1.0f, 11.0f, new CubeDeformation(0.0f)).texOffs(144, 24).addBox(0.0f, 2.2343f, -11.7939f, 0.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-1.0f, (float)-10.0f));
        PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-5.0f, (float)-6.0f));
        PartDefinition body1_hair_r1 = body1.addOrReplaceChild("body1_hair_r1", CubeListBuilder.create().texOffs(140, -14).addBox(0.0f, -16.0f, -14.0f, 0.0f, 6.0f, 22.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0f, (float)-1.5858f, (float)-3.3825f, (float)-0.7418f, (float)0.0f, (float)0.0f));
        PartDefinition body1_r1 = body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0f, -9.0f, -10.0f, 22.0f, 18.0f, 20.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0f, (float)-3.6743f, (float)-4.1814f, (float)-0.7418f, (float)0.0f, (float)0.0f));
        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset((float)1.0f, (float)3.0f, (float)4.0f));
        PartDefinition body2_hair_r1 = body2.addOrReplaceChild("body2_hair_r1", CubeListBuilder.create().texOffs(140, 0).addBox(0.0f, -18.0f, -13.0f, 0.0f, 6.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)5.4879f, (float)-1.816f, (float)-0.829f, (float)0.0f, (float)0.0f));
        PartDefinition body2_r1 = body2.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-9.0f, -8.0f, -7.0f, 18.0f, 16.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-1.6381f, (float)-2.9204f, (float)-0.829f, (float)0.0f, (float)0.0f));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)3.0f, (float)8.0f, (float)-0.5672f, (float)0.0f, (float)0.0f));
        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(140, 10).addBox(-0.005f, -14.005f, 3.505f, 0.0f, 4.0f, 10.0f, new CubeDeformation(-0.005f)), PartPose.offsetAndRotation((float)1.0f, (float)5.4879f, (float)-5.816f, (float)-0.2618f, (float)0.0f, (float)0.0f));
        PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 68).addBox(-3.05f, -4.2545f, 4.6029f, 6.0f, 8.0f, 10.0f, new CubeDeformation(0.15f)), PartPose.offsetAndRotation((float)1.0f, (float)-0.5121f, (float)-5.816f, (float)-0.2618f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset((float)0.962f, (float)2.2732f, (float)7.415f));
        PartDefinition tail_1_r1 = tail_1.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(32, 69).addBox(-3.075f, -4.575f, -8.425f, 6.0f, 6.0f, 9.0f, new CubeDeformation(0.125f)), PartPose.offsetAndRotation((float)0.038f, (float)1.4163f, (float)8.3637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r2 = tail_1.addOrReplaceChild("tail_1_r2", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001f, -7.501f, -4.499f, 0.0f, 4.0f, 6.0f, new CubeDeformation(-0.001f)), PartPose.offsetAndRotation((float)0.038f, (float)1.4163f, (float)5.3637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-0.3f, (float)8.0f, (float)0.2618f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r3 = tail_2.addOrReplaceChild("tail_1_r3", CubeListBuilder.create().texOffs(32, 69).addBox(-3.075f, -4.575f, -8.425f, 6.0f, 6.0f, 9.0f, new CubeDeformation(0.025f)), PartPose.offsetAndRotation((float)0.038f, (float)1.4163f, (float)8.3637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r4 = tail_2.addOrReplaceChild("tail_1_r4", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001f, -7.501f, -4.499f, 0.0f, 4.0f, 6.0f, new CubeDeformation(-0.001f)), PartPose.offsetAndRotation((float)0.038f, (float)1.4163f, (float)5.3637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-0.4f, (float)8.7f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r5 = tail_3.addOrReplaceChild("tail_1_r5", CubeListBuilder.create().texOffs(140, -4).addBox(0.0f, -5.0f, -5.0f, 0.0f, 6.0f, 32.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.038f, (float)1.7163f, (float)8.6637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r6 = tail_3.addOrReplaceChild("tail_1_r6", CubeListBuilder.create().texOffs(226, 0).addBox(-3.0f, -3.0f, -4.5f, 6.0f, 6.0f, 9.0f, new CubeDeformation(-0.075f)), PartPose.offsetAndRotation((float)-0.037f, (float)0.014f, (float)4.3738f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition tail_1_r7 = tail_3.addOrReplaceChild("tail_1_r7", CubeListBuilder.create().texOffs(140, 18).addBox(-0.001f, -7.501f, -4.499f, 0.0f, 4.0f, 6.0f, new CubeDeformation(-0.001f)), PartPose.offsetAndRotation((float)0.038f, (float)1.4163f, (float)5.3637f, (float)0.0436f, (float)0.0f, (float)0.0f));
        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-14.0f, (float)-12.865f, (float)-15.0945f, (float)-0.3927f, (float)0.0f, (float)0.0f));
        PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(128, 40).addBox(-4.0f, -2.0f, 0.0f, 8.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)3.1004f, (float)-5.834f, (float)-1.5973f, (float)-0.1309f, (float)0.0f, (float)0.2618f));
        PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(128, 0).addBox(-3.0f, -10.0f, 0.0f, 6.0f, 20.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-6.6933f, (float)3.6706f, (float)1.0f, (float)0.2317f, (float)0.3405f, (float)0.3404f));
        PartDefinition right_arm_r3 = right_arm.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(184, 64).addBox(-4.0f, -10.0f, -5.0f, 8.0f, 10.0f, 10.0f, new CubeDeformation(0.7f)).texOffs(184, 34).addBox(-4.0f, -10.0f, -5.0f, 8.0f, 20.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0681f, (float)5.4824f, (float)1.0f, (float)0.2182f, (float)0.0f, (float)0.2618f));
        PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(220, 28).addBox(-4.6f, -3.6f, -5.4f, 8.0f, 26.0f, 10.0f, new CubeDeformation(0.4f)), PartPose.offset((float)-2.0f, (float)17.0f, (float)3.0f));
        PartDefinition right_claw = right_hand.addOrReplaceChild("right_claw", CubeListBuilder.create(), PartPose.offset((float)-3.0f, (float)22.0f, (float)-1.4f));
        PartDefinition right_claw_r1 = right_claw.addOrReplaceChild("right_claw_r1", CubeListBuilder.create().texOffs(222, 66).addBox(-3.0f, -3.0f, -4.0f, 6.0f, 6.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0f, (float)1.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.7854f));
        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation((float)16.0f, (float)-12.865f, (float)-15.0945f, (float)-0.3927f, (float)0.0f, (float)0.0f));
        PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(128, 40).mirror().addBox(-4.0f, -2.0f, 0.0f, 8.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-3.1004f, (float)-5.834f, (float)-1.5973f, (float)-0.1309f, (float)0.0f, (float)-0.2618f));
        PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(128, 0).mirror().addBox(-3.0f, -10.0f, 0.0f, 6.0f, 20.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)6.6933f, (float)3.6706f, (float)1.0f, (float)0.2317f, (float)-0.3405f, (float)-0.3404f));
        PartDefinition left_arm_r3 = left_arm.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(184, 64).mirror().addBox(-4.0f, -10.0f, -5.0f, 8.0f, 10.0f, 10.0f, new CubeDeformation(0.7f)).mirror(false).texOffs(184, 34).mirror().addBox(-4.0f, -10.0f, -5.0f, 8.0f, 20.0f, 10.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-0.0681f, (float)5.4824f, (float)1.0f, (float)0.2182f, (float)0.0f, (float)-0.2618f));
        PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(220, 28).mirror().addBox(-1.6f, -3.6f, -5.4f, 8.0f, 26.0f, 10.0f, new CubeDeformation(0.4f)).mirror(false), PartPose.offset((float)0.0f, (float)17.0f, (float)3.0f));
        PartDefinition left_claw = left_hand.addOrReplaceChild("left_claw", CubeListBuilder.create(), PartPose.offset((float)5.0f, (float)22.0f, (float)-1.4f));
        PartDefinition left_claw_r1 = left_claw.addOrReplaceChild("left_claw_r1", CubeListBuilder.create().texOffs(222, 66).mirror().addBox(-3.0f, -3.0f, -4.0f, 6.0f, 6.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-1.0f, (float)1.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.7854f));
        PartDefinition right_leg = main.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset((float)-9.0f, (float)-27.0f, (float)12.0f));
        PartDefinition right_hind_leg_r1 = right_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(84, 24).addBox(-4.0f, -13.0f, -4.0f, 8.0f, 22.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0f, (float)5.0f, (float)0.0f, (float)-0.2618f, (float)0.0f, (float)0.0f));
        PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(64, 58).addBox(-3.0f, -0.8958f, -5.2118f, 6.0f, 19.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset((float)1.0f, (float)9.1958f, (float)0.2118f));
        PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset((float)9.0f, (float)-27.0f, (float)12.0f));
        PartDefinition left_hind_leg_r1 = left_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(84, 24).mirror().addBox(-4.0f, -12.0f, -4.0f, 8.0f, 22.0f, 10.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-1.0f, (float)4.0f, (float)0.0f, (float)-0.2618f, (float)0.0f, (float)0.0f));
        PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(64, 58).mirror().addBox(-3.0f, -0.8721f, -5.4282f, 6.0f, 19.0f, 9.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset((float)-1.0f, (float)9.1721f, (float)0.4282f));
        PartDefinition wind = partdefinition.addOrReplaceChild("wind", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition top = wind.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 97).addBox(-26.0f, -5.25f, -26.0f, 52.0f, 16.0f, 52.0f, new CubeDeformation(2.75f)).texOffs(-1, 101).addBox(-24.0f, 8.0f, -24.0f, 48.0f, 16.0f, 48.0f, new CubeDeformation(1.0f)), PartPose.offset((float)0.0f, (float)-56.0f, (float)0.0f));
        PartDefinition middle = wind.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 172).addBox(-20.0f, -5.0f, -20.0f, 40.0f, 12.0f, 40.0f, new CubeDeformation(2.0f)).texOffs(0, 180).addBox(-16.0f, 5.0f, -16.0f, 32.0f, 12.0f, 32.0f, new CubeDeformation(1.0f)), PartPose.offset((float)0.0f, (float)-25.5f, (float)0.0f));
        PartDefinition bottom = wind.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 232).addBox(-37.0f, -8.0f, -37.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(2.0f)).texOffs(0, 232).addBox(-37.0f, -3.0f, -37.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(1.0f)).texOffs(0, 232).addBox(27.0f, -3.0f, -37.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(1.0f)).texOffs(0, 232).addBox(27.0f, -8.0f, -37.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(2.0f)).texOffs(0, 232).addBox(-37.0f, -8.0f, 27.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(2.0f)).texOffs(0, 232).addBox(-37.0f, -3.0f, 27.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(1.0f)).texOffs(0, 232).addBox(27.0f, -8.0f, 27.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(2.0f)).texOffs(0, 232).addBox(27.0f, -3.0f, 27.0f, 10.0f, 14.0f, 10.0f, new CubeDeformation(1.0f)), PartPose.offset((float)0.0f, (float)-12.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)256, (int)256);
    }

    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (t instanceof CorrosiverServant) {
            CorrosiverServant corrosiverEntity = (CorrosiverServant)((Object)t);
            float f6 = f3 * (float)Math.PI * -0.1f;
            float f7 = f3 * 0.1f;
            this.head.xRot = f4 * ((float)Math.PI / 180);
            this.head.yRot = f5 * ((float)Math.PI / 180);
            KeyframeAnimations.animate((HierarchicalModel)this, (AnimationDefinition)CorrosiverAnimation.WINDANIM, (long)0L, (float)(t.isAlive() ? Mth.clamp((float)(1.0f - corrosiverEntity.windAnimProgress / 10.0f), (float)0.0f, (float)1.0f) : 0.0f), (Vector3f)new Vector3f());
            if (corrosiverEntity.isPassenger() || corrosiverEntity.getControllingPassenger() != null) {
                this.animateWalk(CorrosiverAnimation.RUSH, f, f2, 1.0f, 2.0f);
            } else if (corrosiverEntity.isAggressive()) {
                this.animateWalk(CorrosiverAnimation.RUSH, f, f2, 2.0f, 2.0f);
            } else {
                this.animateWalk(CorrosiverAnimation.WALK, f, f2, 1.5f, 2.0f);
            }
            this.animate(corrosiverEntity.idleAnimationState, CorrosiverAnimation.IDLE, f3);
            this.animate(corrosiverEntity.idleWindAnimationState, CorrosiverAnimation.IDLEWIND, f3);
            this.animate(corrosiverEntity.attack1AnimationState, CorrosiverAnimation.ATTACK1, f3);
            this.animate(corrosiverEntity.attack2AnimationState, CorrosiverAnimation.ATTACK2, f3);
            this.animate(corrosiverEntity.attack3AnimationState, CorrosiverAnimation.ATTACK3, f3);
            this.animate(corrosiverEntity.attack4AnimationState, CorrosiverAnimation.ATTACK4, f3);
            this.animate(corrosiverEntity.attack5AnimationState, CorrosiverAnimation.ATTACK5, f3);
            this.animate(corrosiverEntity.attack6AnimationState, CorrosiverAnimation.ATTACK6, f3);
            this.animate(corrosiverEntity.attack7AnimationState, CorrosiverAnimation.ATTACK7, f3);
            this.animate(corrosiverEntity.breathAnimationState, CorrosiverAnimation.BREATH, f3);
            this.animate(corrosiverEntity.windAnimationState, CorrosiverAnimation.WIND, f3);
            this.animate(corrosiverEntity.snowAnimationState, CorrosiverAnimation.SNOW, f3);
            this.animate(corrosiverEntity.iceAnimationState, CorrosiverAnimation.ICE, f3);
            this.animate(corrosiverEntity.teleportAnimationState, CorrosiverAnimation.TELEPORT, f3);
            this.animate(corrosiverEntity.startAnimationState, CorrosiverAnimation.START, f3);
            this.animate(corrosiverEntity.deadAnimationState, CorrosiverAnimation.DEAD, f3);
        }
    }

    public ModelPart root() {
        return this.root;
    }
}

