package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
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

import java.util.List;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.model.ReaperNecromancerModel;
import cn.blockforge.generated.goetysupreme.renderer.ReaperNecromancerRenderer;
import cn.blockforge.generated.goetysupreme.model.SoulReaperModel;

public class ReaperNecromancerModel extends HierarchicalModel<AbstractReaperNecromancer> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath((String)"goety_supreme", (String)"reaper_necromancer"), "main");

    private final ModelPart root;
    private final ModelPart head;

    private final ModelPart chainSkeleton;
    private final ModelPart chainUpperBody;
    private final ModelPart chainBodyNoHead;
    private final ModelPart chainRightHand;
    private final ModelPart chainRightHand2;
    private final ModelPart chainScythe;
    private final ModelPart chainScytheHead;

    public ReaperNecromancerModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("skeleton").getChild("上半身").getChild("头");
        this.chainSkeleton = root.getChild("skeleton");
        this.chainUpperBody = this.chainSkeleton.getChild("上半身");
        this.chainBodyNoHead = this.chainUpperBody.getChild("除了头");
        this.chainRightHand = this.chainBodyNoHead.getChild("右手");
        this.chainRightHand2 = this.chainRightHand.getChild("右手2");
        this.chainScythe = this.chainRightHand2.getChild("镰刀");
        this.chainScytheHead = this.chainScythe.getChild("group").getChild("镰刀头");
    }

    public void applyScytheTransform(PoseStack poseStack) {
        this.chainSkeleton.translateAndRotate(poseStack);
        this.chainUpperBody.translateAndRotate(poseStack);
        this.chainBodyNoHead.translateAndRotate(poseStack);
        this.chainRightHand.translateAndRotate(poseStack);
        this.chainRightHand2.translateAndRotate(poseStack);
        this.chainScythe.translateAndRotate(poseStack);
        this.chainScytheHead.translateAndRotate(poseStack);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

        public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition rootDef = mesh.getRoot();
        PartDefinition b0 = rootDef.addOrReplaceChild("skeleton", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)24.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b1 = b0.addOrReplaceChild("\u4e0a\u534a\u8eab", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-12.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b2 = b1.addOrReplaceChild("\u5934", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-12.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b2.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 0).addBox(-3.98f, -8.0f, -3.98f, 7.96f, 8.0f, 7.96f), PartPose.ZERO);
        PartDefinition b3 = b2.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b3.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.75f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.ZERO);
        PartDefinition b4 = b3.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)24.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b4.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(24, 92).addBox(-9.0f, -37.0f, -0.02f, 18.0f, 8.0f, 0.04f), PartPose.ZERO);
        PartDefinition b5 = b2.addOrReplaceChild("\u59342", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-4.0f, (float)2.33333f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b5.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(27, 112).addBox(-3.97334f, -3.97714f, -6.31047f, 7.94667f, 7.95429f, 7.95429f, new CubeDeformation(-0.99333f, -0.49714f, -0.49714f)), PartPose.ZERO);
        b5.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(104, 116).addBox(-4.0f, -4.0f, 1.66667f, 8.0f, 8.0f, 4.0f, new CubeDeformation(-1.0f, -0.5f, -0.25f)), PartPose.ZERO);
        b5.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(96, 82).addBox(-3.94666f, -3.95429f, -6.28762f, 7.89333f, 7.90857f, 7.90857f, new CubeDeformation(-0.98667f, -0.49429f, -0.49429f)), PartPose.ZERO);
        b5.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(96, 99).addBox(-4.0f, -4.0f, -6.33333f, 8.0f, 8.0f, 8.0f, new CubeDeformation(-1.0f, -0.5f, -0.5f)), PartPose.ZERO);
        PartDefinition b6 = b1.addOrReplaceChild("\u9664\u4e86\u5934", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)-12.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b7 = b6.addOrReplaceChild("\u53f3\u624b", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-5.0f, (float)2.0f, (float)0.0f, (float)((float)Math.toRadians(-80.0)), (float)((float)Math.toRadians(15.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b8 = b7.addOrReplaceChild("right_pauldron", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)1.0f, (float)((float)Math.toRadians(60.0)), (float)((float)Math.toRadians(-5.0)), (float)((float)Math.toRadians(-15.0))));
        b8.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 48).addBox(-4.0f, -4.0f, -3.0f, 5.0f, 5.0f, 6.0f), PartPose.ZERO);
        PartDefinition b9 = b7.addOrReplaceChild("\u53f3\u624b2", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(-2.5)), (float)((float)Math.toRadians(-0.0))));
        b9.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(79, 113).addBox(-2.0f, -2.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(-0.25f, 0.0f, -0.25f)), PartPose.ZERO);
        b9.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(66, 102).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, new CubeDeformation(-0.125f, 0.0f, -0.125f)), PartPose.ZERO);
        PartDefinition b10 = b9.addOrReplaceChild("\u9570\u5200", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.05386f, (float)9.78037f, (float)-0.32502f, (float)((float)Math.toRadians(82.5)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b11 = b10.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-1.05386f, (float)6.83997f, (float)19.1077f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b11_p0 = b11.addOrReplaceChild("b11_pivot0", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.0f, (float)-4.0f, (float)-19.5f, (float)((float)Math.toRadians(2.5)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b11_p0.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(65, 32).addBox(-0.5f, -6.2f, 0.2f, 1.0f, 23.0f, 1.0f), PartPose.ZERO);
        PartDefinition b11_p1 = b11.addOrReplaceChild("b11_pivot1", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.0f, (float)-22.0f, (float)-18.5f, (float)((float)Math.toRadians(-2.5)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b11_p1.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(60, 39).addBox(-0.5f, 6.0f, -0.5f, 1.0f, 6.0f, 1.0f), PartPose.ZERO);
        PartDefinition b11_p2 = b11.addOrReplaceChild("b11_pivot2", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.0f, (float)-10.0f, (float)-7.5f, (float)((float)Math.toRadians(-90.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b11_p2.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(70, 39).addBox(-0.5f, 7.0f, -0.5f, 1.0f, 5.0f, 1.0f), PartPose.ZERO);
        PartDefinition b12 = b10.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-1.05386f, (float)6.83997f, (float)19.1077f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b12.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(56, 47).addBox(-1.5f, -22.0f, -17.0f, 1.0f, 2.0f, 1.0f), PartPose.ZERO);
        b12.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(56, 47).addBox(2.5f, -22.0f, -21.0f, 1.0f, 2.0f, 1.0f), PartPose.ZERO);
        b12.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(56, 47).addBox(-1.5f, -22.0f, -21.0f, 1.0f, 2.0f, 1.0f), PartPose.ZERO);
        b12.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(56, 47).addBox(2.5f, -22.0f, -17.0f, 1.0f, 2.0f, 1.0f), PartPose.ZERO);
        b12.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(65, 51).addBox(-1.5f, -17.0f, -21.0f, 5.0f, 1.0f, 5.0f), PartPose.ZERO);
        b12.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(65, 58).addBox(-1.5f, -20.7f, -21.0f, 5.0f, 4.0f, 5.0f, new CubeDeformation(-0.3125f, -0.25f, -0.3125f)), PartPose.ZERO);
        PartDefinition b13 = b12.addOrReplaceChild("\u9570\u5200\u5934", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.0f, (float)-13.36954f, (float)-30.76186f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b13.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(80, 26).addBox(-0.5f, -6.71429f, 6.92857f, 1.0f, 6.0f, 5.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(115, 26).addBox(-0.5f, -5.71429f, 3.92857f, 1.0f, 6.0f, 3.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(105, 9).addBox(-0.5f, -4.71429f, 0.92857f, 1.0f, 6.0f, 3.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(91, 14).addBox(-0.5f, -2.71429f, -2.07143f, 1.0f, 5.0f, 3.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(100, 30).addBox(-0.5f, -0.71429f, -5.07143f, 1.0f, 4.0f, 3.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(118, 46).addBox(-0.5f, 1.28571f, -7.07143f, 1.0f, 3.0f, 2.0f), PartPose.ZERO);
        b13.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(106, 47).addBox(-0.5f, 3.28571f, -8.07143f, 1.0f, 2.0f, 2.0f), PartPose.ZERO);
        PartDefinition b14 = b10.addOrReplaceChild("staffhead", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-1.05386f, (float)6.83997f, (float)19.1077f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b14.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(48, 50).addBox(-0.5f, -20.0f, -20.0f, 3.0f, 3.0f, 3.0f), PartPose.ZERO);
        PartDefinition b15 = b6.addOrReplaceChild("\u5de6\u80a9", CubeListBuilder.create(), PartPose.offsetAndRotation((float)5.0f, (float)2.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-5.0))));
        PartDefinition b16 = b15.addOrReplaceChild("leftItem", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.0f, (float)7.0f, (float)1.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        PartDefinition b17 = b15.addOrReplaceChild("left_pauldron", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)1.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b17.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0f, -4.0f, -3.0f, 5.0f, 5.0f, 5.94f), PartPose.ZERO);
        PartDefinition b18 = b15.addOrReplaceChild("\u5de6\u624b", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b18.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(79, 113).addBox(-2.0f, -2.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(-0.25f, 0.0f, -0.25f)), PartPose.ZERO);
        b18.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(66, 102).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, new CubeDeformation(-0.125f, 0.0f, -0.125f)), PartPose.ZERO);
        PartDefinition b19 = b6.addOrReplaceChild("\u62ab\u98ce", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)2.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b19.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(24, 64).addBox(-8.0f, 0.0f, -2.0f, 16.0f, 24.0f, 4.0f), PartPose.ZERO);
        PartDefinition b20 = b6.addOrReplaceChild("\u8eab", CubeListBuilder.create(), PartPose.offsetAndRotation((float)3.0f, (float)24.0f, (float)0.0f, (float)((float)Math.toRadians(-0.0)), (float)((float)Math.toRadians(0.0)), (float)((float)Math.toRadians(-0.0))));
        b20.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(99, 57).addBox(-7.0f, -23.98f, -1.0f, 8.0f, 19.98f, 4.0f, new CubeDeformation(0.25f, 0.25f, 0.25f)), PartPose.ZERO);
        b20.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(76, 97).addBox(-6.5f, -23.96f, 0.02f, 7.0f, 6.96f, 2.96f), PartPose.ZERO);
        b20.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(16, 16).addBox(-7.0f, -25.5f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f, 0.75f, 0.25f)), PartPose.ZERO);
        return LayerDefinition.create((MeshDefinition)mesh, (int)128, (int)128);
    }

    private static AnimationDefinition createIdle() {
        return AnimationDefinition.Builder.withLength(2.00000F).looping()
                .addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, 2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-66.59580F, 66.32280F, -85.24050F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-51.15470F, 64.71260F, -73.76000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-66.59580F, 66.32280F, -85.24050F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-82.47170F, -4.95750F, 4.34580F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(17.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75000F, KeyframeAnimations.degreeVec(-5.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-14.97380F, 2.40680F, 1.51020F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(7.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(30.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createAttack() {
        return AnimationDefinition.Builder.withLength(1.00000F)
                .addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-55.00000F, -45.00000F, -50.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-55.00000F, -45.00000F, -50.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-10.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-65.00000F, -30.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-65.00000F, -30.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(45.00000F, 25.00000F, -25.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.posVec(-0.12921F, -3.61118F, -6.77810F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(-0.12921F, -3.61118F, -6.77810F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-15.00000F, 60.00000F, -15.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-15.00000F, 60.00000F, -15.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(30.00000F, -10.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(15.00000F, -60.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(15.00000F, -60.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(16.50000F, 13.50000F, 12.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(10.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(70.00000F, 10.00000F, -5.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(50.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(50.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(57.27960F, 54.89170F, -27.74590F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createSummon() {
        return AnimationDefinition.Builder.withLength(1.79167F)
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-0.00000F, -15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-8.94261F, 14.96852F, 7.11386F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(-8.94261F, 14.96852F, 7.11386F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.degreeVec(-10.00000F, -15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-10.00000F, -15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-32.52090F, 15.73373F, 5.62731F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(-32.52090F, 15.73373F, 5.62731F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.degreeVec(-105.00000F, 0.00000F, -85.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-105.00000F, 0.00000F, -85.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-50.00000F, 0.00000F, -32.50000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(-50.00000F, 0.00000F, -32.50000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.degreeVec(7.50000F, -90.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(7.50000F, -90.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(7.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.08333F, KeyframeAnimations.degreeVec(7.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.degreeVec(80.00000F, 15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(80.00000F, 15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-12.50000F, -15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(-12.50000F, -15.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(10.00000F, 0.00000F, -45.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(10.00000F, 0.00000F, -45.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(90.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(70.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04167F, KeyframeAnimations.degreeVec(77.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(40.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16667F, KeyframeAnimations.degreeVec(52.74765F, -15.76270F, 12.84264F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.degreeVec(180.00000F, -80.00000F, -115.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.degreeVec(180.00000F, -80.00000F, -115.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(30.00000F, -15.00000F, 70.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(30.00000F, -15.00000F, 70.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -1.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.58333F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -2.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createPunish() {
        return AnimationDefinition.Builder.withLength(2.25000F)
                .addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 4.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-20.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.degreeVec(27.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(27.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(18.75070F, -1.11050F, 6.71900F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.degreeVec(57.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(57.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-41.30410F, -0.93130F, 48.36760F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.degreeVec(-19.35210F, 4.99220F, -0.90680F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(-19.35210F, 4.99220F, -0.90680F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-104.74300F, -20.62370F, 10.43990F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.degreeVec(-89.99990F, -5.00040F, 5.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(-92.50940F, -4.99560F, 5.21870F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.posVec(-0.35902F, 4.10358F, 5.74733F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.posVec(-0.35902F, 4.10358F, 5.74733F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.00000F, 20.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createSpin() {
        return AnimationDefinition.Builder.withLength(3.50000F)
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.20833F, KeyframeAnimations.degreeVec(-15.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.70833F, KeyframeAnimations.degreeVec(5.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(-0.33710F, 20.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.70833F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(0.02780F, -22.50000F, -0.13970F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(74.50320F, -14.37030F, -10.70890F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.degreeVec(12.45280F, 18.85800F, -92.06590F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(12.45280F, 18.85800F, -92.06590F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.83333F, KeyframeAnimations.degreeVec(-2441.93590F, -22.88930F, -67.19140F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.25000F, KeyframeAnimations.degreeVec(-2678.13860F, 25.04810F, -126.74320F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.33333F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.29167F, KeyframeAnimations.posVec(-0.02757F, -0.18551F, -8.72826F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.posVec(0.98790F, 3.68948F, -5.68703F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.70833F, KeyframeAnimations.posVec(-0.20376F, -0.80131F, -1.78365F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.posVec(-0.29105F, -1.12818F, -2.21077F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.33333F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(2.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.58333F, KeyframeAnimations.degreeVec(26.00930F, -3.42340F, -9.16280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.66667F, KeyframeAnimations.degreeVec(16.46130F, -2.88260F, -5.66310F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75000F, KeyframeAnimations.degreeVec(26.00930F, -3.42340F, -9.16280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.83333F, KeyframeAnimations.degreeVec(16.46130F, -2.88260F, -5.66310F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.91667F, KeyframeAnimations.degreeVec(26.00930F, -3.42340F, -9.16280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(16.46130F, -2.88260F, -5.66310F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(2.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-74.03220F, 14.18140F, -10.91020F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.70833F, KeyframeAnimations.degreeVec(-18.81840F, 4.84650F, -0.87770F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.degreeVec(-29.09160F, 7.30260F, -1.97850F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(-29.09160F, 7.30260F, -1.97850F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(-21.91360F, 17.17020F, 73.39610F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-21.91360F, 17.17020F, 73.39610F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.62500F, KeyframeAnimations.degreeVec(-291.86040F, -20.71690F, 329.85750F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(-291.86040F, -20.71690F, 329.85750F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.20833F, KeyframeAnimations.degreeVec(-28.23770F, 41.21160F, 33.07440F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(-75.60050F, -20.95520F, -17.06540F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.04167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.33333F, KeyframeAnimations.degreeVec(30.14050F, -28.58140F, 4.44240F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createSnap() {
        return AnimationDefinition.Builder.withLength(1.50000F)
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.66667F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -20.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(30.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-10.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -155.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -155.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -135.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -155.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.37500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -155.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createRest() {
        return AnimationDefinition.Builder.withLength(2.00000F).looping()
                .addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(20.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(12.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-13.75000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createDeath() {
        return AnimationDefinition.Builder.withLength(1.75000F)
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, -4.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -4.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, 57.50000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, 57.50000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-20.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-20.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("头", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe[]{new Keyframe(1.25000F, KeyframeAnimations.scaleVec(1.00000F, 1.00000F, 1.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75000F, KeyframeAnimations.scaleVec(0.00000F, 0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-75.65150F, -69.40950F, -13.46620F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-75.65150F, -69.40950F, -13.46620F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(22.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(22.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe[]{new Keyframe(0.75000F, KeyframeAnimations.scaleVec(1.00000F, 1.00000F, 1.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.00000F, KeyframeAnimations.scaleVec(0.00000F, 0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .build();
    }
    public static final AnimationDefinition FLOAT = ReaperNecromancerModel.createIdle();
    public static final AnimationDefinition IDLE = FLOAT;
    public static final AnimationDefinition ATTACK = ReaperNecromancerModel.createAttack();
    public static final AnimationDefinition SUMMON = ReaperNecromancerModel.createSummon();
    public static final AnimationDefinition PUNISH = ReaperNecromancerModel.createPunish();
    public static final AnimationDefinition SPIN = ReaperNecromancerModel.createSpin();
    public static final AnimationDefinition SNAP = ReaperNecromancerModel.createSnap();
    public static final AnimationDefinition REST = ReaperNecromancerModel.createRest();
    public static final AnimationDefinition DEATH = ReaperNecromancerModel.createDeath();
    public static final AnimationDefinition TAKEOFF = ReaperNecromancerModel.createTakeoff();
    public static final AnimationDefinition LANDING = ReaperNecromancerModel.createLanding();

    private static final float BLEND_SECONDS = 0.18F;

    private List<ModelPart> poseParts;
    private float[][] idlePose;
    private float[][] restPose;
    private float[][] castHandoff;

    @Override
    public void setupAnim(AbstractReaperNecromancer entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.ensurePoseBuffers();
        this.resetAllPoses();

        if (entity.deathTime > 0) {
            this.animate(entity.deathAnimationState, DEATH, ageInTicks);
            return;
        }

        if (entity.getCastAnimation() == AbstractReaperNecromancer.CAST_NONE
                && entity.getPostCastBlendTicks() > 0) {
            this.animateCast(entity, entity.getLastCastAnimation(), ageInTicks);
            this.capturePose(this.castHandoff);
            this.resetAllPoses();
            this.animate(entity.restAnimationState, REST, ageInTicks);
            this.capturePose(this.restPose);
            float t = 1.0F - entity.getPostCastBlendTicks()
                    / (float) AbstractReaperNecromancer.POST_CAST_BLEND_TICKS;
            this.applyBlend(this.castHandoff, this.restPose, t);
        } else if (entity.isReaperTransitioning()) {
            boolean takeoff = entity.isReaperTransitionTakeoff();
            entity.reaperFloatBlend = takeoff ? 1.0F : 0.0F;
            entity.reaperFloatBlendAge = ageInTicks;
            this.animate(takeoff ? entity.takeoffAnimationState : entity.landingAnimationState,
                    takeoff ? TAKEOFF : LANDING, ageInTicks);
        } else {
            float target = entity.isMovingForAnimation() ? 1.0F : 0.0F;
            entity.reaperFloatBlend = this.smoothBlend(
                    entity.reaperFloatBlend, target, entity.reaperFloatBlendAge, ageInTicks);
            entity.reaperFloatBlendAge = ageInTicks;
            float floatBlend = entity.reaperFloatBlend;

            if (entity.getCastAnimation() == AbstractReaperNecromancer.CAST_NONE) {
                this.animate(entity.idleAnimationState, IDLE, ageInTicks);
                this.capturePose(this.idlePose);
                this.resetAllPoses();
                this.animate(entity.restAnimationState, REST, ageInTicks);
                this.capturePose(this.restPose);
                this.applyBlend(this.restPose, this.idlePose, floatBlend);
            }
        }
        this.animateCast(entity, entity.getCastAnimation(), ageInTicks);
        if (this.head != null) {
            if (entity.cantDo > 0) {
                this.head.zRot = 0.3F * Mth.sin(0.45F * ageInTicks);
                this.head.xRot = 0.4F;
            } else {
                this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
                this.head.xRot = headPitch * (float) (Math.PI / 180.0);
            }
        }
    }

    private void animateCast(AbstractReaperNecromancer entity, int cast, float ageInTicks) {
        switch (cast) {
            case AbstractReaperNecromancer.CAST_ATTACK ->
                    this.animate(entity.castAnimationState, ATTACK, ageInTicks, entity.getAttackSpeed());
            case AbstractReaperNecromancer.CAST_SUMMON ->
                    this.animate(entity.castAnimationState, SUMMON, ageInTicks);
            case AbstractReaperNecromancer.CAST_PUNISH ->
                    this.animate(entity.castAnimationState, PUNISH, ageInTicks);
            case AbstractReaperNecromancer.CAST_SPIN ->
                    this.animate(entity.castAnimationState, SPIN, ageInTicks);
            case AbstractReaperNecromancer.CAST_SNAP ->
                    this.animate(entity.castAnimationState, SNAP, ageInTicks);
            default -> {
            }
        }
    }

    private void ensurePoseBuffers() {
        if (this.poseParts != null) {
            return;
        }
        this.poseParts = this.root().getAllParts().toList();
        int n = this.poseParts.size();
        this.idlePose = new float[n][6];
        this.restPose = new float[n][6];
        this.castHandoff = new float[n][6];
        for (int i = 0; i < n; i++) {
            this.idlePose[i] = new float[6];
            this.restPose[i] = new float[6];
            this.castHandoff[i] = new float[6];
        }
    }

    private void resetAllPoses() {
        for (int i = 0; i < this.poseParts.size(); i++) {
            this.poseParts.get(i).resetPose();
        }
    }

    private void capturePose(float[][] dst) {
        for (int i = 0; i < dst.length; i++) {
            ModelPart part = this.poseParts.get(i);
            float[] v = dst[i];
            v[0] = part.x;
            v[1] = part.y;
            v[2] = part.z;
            v[3] = part.xRot;
            v[4] = part.yRot;
            v[5] = part.zRot;
        }
    }

    private void applyBlend(float[][] a, float[][] b, float t) {
        for (int i = 0; i < a.length; i++) {
            ModelPart part = this.poseParts.get(i);
            float[] va = a[i];
            float[] vb = b[i];
            part.x = Mth.lerp(t, va[0], vb[0]);
            part.y = Mth.lerp(t, va[1], vb[1]);
            part.z = Mth.lerp(t, va[2], vb[2]);
            part.xRot = Mth.lerp(t, va[3], vb[3]);
            part.yRot = Mth.lerp(t, va[4], vb[4]);
            part.zRot = Mth.lerp(t, va[5], vb[5]);
        }
    }

    private float smoothBlend(float current, float target, float previousAge, float ageInTicks) {
        float delta = ageInTicks - previousAge;
        if (Float.isNaN(delta)) {
            return target;
        }
        if (delta <= 0.0F) {
            return current;
        }
        if (delta > 1.0F) {
            return target;
        }
        float k = 1.0F - (float) Math.exp(-delta / BLEND_SECONDS);
        return current + (target - current) * k;
    }
    private static AnimationDefinition createTakeoff() {
        return AnimationDefinition.Builder.withLength(0.50000F)
                .addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-95.37810F, 68.76620F, -86.62770F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-66.59580F, 66.32280F, -85.24050F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(0.02830F, -4.95750F, 4.34580F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-82.47170F, -4.95750F, 4.34580F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, -90.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-14.97380F, 2.40680F, 1.51020F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(30.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }
    private static AnimationDefinition createLanding() {
        return AnimationDefinition.Builder.withLength(0.50000F)
                .addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-66.59580F, 66.32280F, -85.24050F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-95.37810F, 68.76620F, -86.62770F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-82.47170F, -4.95750F, 4.34580F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(0.02830F, -4.95750F, 4.34580F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-14.97380F, 2.40680F, 1.51020F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, -90.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(30.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }
}
