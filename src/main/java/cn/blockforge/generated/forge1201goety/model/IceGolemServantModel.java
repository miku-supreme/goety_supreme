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
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.api.HeadProvider;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.model.IceGolemServantModel;

public class IceGolemServantModel extends HierarchicalModel<IceGolemServant>
        implements HeadProvider {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "ice_golem_servant"), "main");

    private final ModelPart root;
    private final ModelPart upperBody;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public IceGolemServantModel(ModelPart root) {
        this.root = root;
        this.upperBody = root.getChild("上半身");
        this.head = this.upperBody.getChild("head");
        this.rightArm = this.upperBody.getChild("right_arm");
        this.leftArm = this.upperBody.getChild("left_arm");
    }

    public void applyRightHandTransform(PoseStack poseStack) {
        this.upperBody.translateAndRotate(poseStack);
        this.rightArm.translateAndRotate(poseStack);
    }

    public void applyLeftHandTransform(PoseStack poseStack) {
        this.upperBody.translateAndRotate(poseStack);
        this.leftArm.translateAndRotate(poseStack);
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    @Override
    public void translateHead(PoseStack poseStack) {
        this.upperBody.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition rootDef = mesh.getRoot();
        PartDefinition b0 = rootDef.addOrReplaceChild("上半身", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 9F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        PartDefinition b1 = b0.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -16F, -3.50000F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b1.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F), PartPose.ZERO);
        PartDefinition b2 = b0.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -16F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b2.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 40).addBox(-9F, -2F, -6F, 18F, 12F, 11F), PartPose.ZERO);
        b2.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(0, 70).addBox(-4.50000F, 10F, -3F, 9F, 5F, 6F), PartPose.ZERO);
        PartDefinition b3 = b0.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-9F, -16F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b3.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(60, 21).addBox(-4F, -2.50000F, -3F, 4F, 30F, 6F), PartPose.ZERO);
        PartDefinition b4 = b0.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(9F, -16F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b4.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(60, 58).addBox(0F, -2.50000F, -3F, 4F, 30F, 6F), PartPose.ZERO);
        PartDefinition b5 = rootDef.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-4F, 11F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b5.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(37, 0).addBox(-3.50000F, -3F, -3F, 6F, 16F, 5F), PartPose.ZERO);
        PartDefinition b6 = rootDef.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(5F, 11F, 0F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b6.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(60, 0).addBox(-3.50000F, -3F, -3F, 6F, 16F, 5F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static AnimationDefinition createIdle() {
        return AnimationDefinition.Builder.withLength(3.00000F).looping()
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, -4.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-0.00000F, 4.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(2.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-2.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(0.77880F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(1.84210F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(-0.77880F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(-1.84210F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(0.77880F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createWalk() {
        return AnimationDefinition.Builder.withLength(2.00000F).looping()
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(-45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(45.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 3.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -3.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 3.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -3.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 3.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -3.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.posVec(0.00000F, 2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.62500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.posVec(0.00000F, 2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.62500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createAttack() {
        return AnimationDefinition.Builder.withLength(2.50000F)
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.45833F, KeyframeAnimations.degreeVec(-7.87880F, -1.91140F, 22.42290F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.62500F, KeyframeAnimations.degreeVec(5.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(5.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.20833F, KeyframeAnimations.degreeVec(-63.25980F, 78.83120F, 26.30230F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.33333F, KeyframeAnimations.degreeVec(-37.65100F, -22.12600F, -7.79270F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.70833F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.degreeVec(43.21990F, -62.00920F, -136.78120F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.41667F, KeyframeAnimations.degreeVec(13.07200F, -8.94390F, -91.94500F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.62500F, KeyframeAnimations.degreeVec(-69.11150F, 16.41370F, 6.15480F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(-69.11150F, 16.41370F, 6.15480F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.20833F, KeyframeAnimations.degreeVec(-8.33000F, 6.74470F, -18.55720F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.33333F, KeyframeAnimations.degreeVec(6.10970F, 6.24100F, -18.55570F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.70833F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.20833F, KeyframeAnimations.posVec(0.00000F, -2.00000F, -2.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -4.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.29167F, KeyframeAnimations.posVec(0.00000F, -0.00000F, -4.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.58333F, KeyframeAnimations.posVec(0.00000F, -3.00000F, -2.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.87500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-4.07490F, -2.52450F, -14.25420F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.54167F, KeyframeAnimations.degreeVec(46.72540F, 31.48550F, 16.76520F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.degreeVec(46.72540F, 31.48550F, 16.76520F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08333F, KeyframeAnimations.degreeVec(-1.81280F, 11.43460F, 13.84880F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(32.56590F, -12.21320F, -10.81270F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.70833F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.45833F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createWindmill() {
        return AnimationDefinition.Builder.withLength(3.50000F).looping()
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-180.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-180.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(3780.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.25000F, KeyframeAnimations.degreeVec(3690.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.50000F, KeyframeAnimations.degreeVec(3600.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-182.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-182.50000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(3600.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-25.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08333F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16667F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.33333F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.41667F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.50000F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.58333F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.66667F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75000F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.83333F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.91667F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.00000F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08333F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.16667F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.25000F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.33333F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.41667F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.50000F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.58333F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.66667F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75000F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.83333F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.87500F, KeyframeAnimations.degreeVec(0.47650F, -0.04170F, 4.99980F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.95833F, KeyframeAnimations.degreeVec(0.47430F, 0.04150F, -5.02280F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createWindmillSpin() {
        return AnimationDefinition.Builder.withLength(2.0F).looping()
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{
                                new Keyframe(0.00000F,
                                        KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2.00000F,
                                        KeyframeAnimations.degreeVec(3600.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.LINEAR)}))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{
                                new Keyframe(0.00000F,
                                        KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2.00000F,
                                        KeyframeAnimations.degreeVec(3600.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.LINEAR)}))
                .build();
    }

    public static final AnimationDefinition IDLE = createIdle();
    public static final AnimationDefinition WALK = createWalk();
    public static final AnimationDefinition ATTACK = createAttack();
    public static final AnimationDefinition WINDMILL = createWindmill();

    public static final AnimationDefinition WINDMILL_SPIN = createWindmillSpin();

    @Override
    public void setupAnim(IceGolemServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, IDLE, ageInTicks);
        this.animateWalk(WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        if (entity.isWindmillSpinAnimating()) {
            this.animate(entity.windmillSpinAnimationState, WINDMILL_SPIN, ageInTicks);
        } else if (entity.isMeleeAnimating()) {
            this.animate(entity.attackAnimationState, ATTACK, ageInTicks, IceGolemServant.ATTACK_ANIM_SPEED);
        }
        if (this.head != null) {
            this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
            this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        }
    }
}
