package cn.blockforge.generated.goetysupreme.model;

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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.model.SoulCoffinModel;
import cn.blockforge.generated.goetysupreme.model.SoulReaperModel;

public class SoulCoffinModel extends HierarchicalModel<SoulCoffin> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "soul_coffin"), "main");

    private final ModelPart root;

    public SoulCoffinModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition rootDef = mesh.getRoot();
        PartDefinition b0 = rootDef.addOrReplaceChild("棺材", CubeListBuilder.create(), PartPose.offsetAndRotation(0.53750F, 8.49142F, 2.04375F, ((float)Math.toRadians(-90.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(180.00000F))));
        b0.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 122).addBox(-2.53750F, 14.50858F, -2.24375F, 5F, 1F, 5F), PartPose.ZERO);
        b0.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(0, 115).addBox(-4.03750F, -11.49142F, -2.24375F, 8F, 1F, 5F), PartPose.ZERO);
        b0.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(3, 24).addBox(-3.53750F, -10.49142F, -2.99375F, 7F, 25F, 1F), PartPose.ZERO);
        PartDefinition b0_p3 = b0.addOrReplaceChild("b0_pivot3", CubeListBuilder.create(), PartPose.offsetAndRotation(2.86250F, 15.50858F, -0.04375F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(8.00000F))));
        b0_p3.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(67, 72).addBox(-0.50000F, -17F, -2.50000F, 1F, 17F, 5F), PartPose.ZERO);
        PartDefinition b0_p4 = b0.addOrReplaceChild("b0_pivot4", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.93750F, 15.50858F, -0.04375F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-8.00000F))));
        b0_p4.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(67, 72).addBox(-0.50000F, -17F, -2.50000F, 1F, 17F, 5F), PartPose.ZERO);
        PartDefinition b0_p5 = b0.addOrReplaceChild("b0_pivot5", CubeListBuilder.create(), PartPose.offsetAndRotation(5.26250F, -1.19142F, -0.04375F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-8.00000F))));
        b0_p5.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(53, 102).addBox(-0.50000F, -9.98000F, -2.50000F, 1F, 9.96000F, 5F), PartPose.ZERO);
        PartDefinition b0_p6 = b0.addOrReplaceChild("b0_pivot6", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.33750F, -1.19142F, -0.04375F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(8.00000F))));
        b0_p6.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(53, 102).addBox(-0.50000F, -9.98000F, -2.50000F, 1F, 9.96000F, 5F), PartPose.ZERO);
        b0.addOrReplaceChild("cube7", CubeListBuilder.create().texOffs(0, 93).addBox(3.26250F, -10.49142F, -2.84375F, 1F, 19F, 0.98000F), PartPose.ZERO);
        b0.addOrReplaceChild("cube8", CubeListBuilder.create().texOffs(0, 93).addBox(-4.23750F, -10.49142F, -2.84375F, 1F, 19F, 0.98000F), PartPose.ZERO);
        b0.addOrReplaceChild("cube9", CubeListBuilder.create().texOffs(6, 102).addBox(-4.93750F, -5.47142F, -2.84375F, 1F, 7.96000F, 1F), PartPose.ZERO);
        b0.addOrReplaceChild("cube10", CubeListBuilder.create().texOffs(6, 102).addBox(4.06250F, -5.47142F, -2.84375F, 1F, 7.96000F, 1F), PartPose.ZERO);
        PartDefinition b1 = b0.addOrReplaceChild("棺材盖", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.53750F, -1.49142F, 1.75625F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b1.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(1, 65).addBox(1F, -9F, 0.25000F, 7F, 25F, 1F), PartPose.ZERO);
        b1.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(0, 93).addBox(7.80000F, -9F, 0F, 1F, 19F, 0.98000F), PartPose.ZERO);
        b1.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(0, 93).addBox(0.30000F, -9F, 0F, 1F, 19F, 0.98000F), PartPose.ZERO);
        b1.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(6, 102).addBox(-0.40000F, -3.98000F, 0F, 1F, 7.96000F, 1F), PartPose.ZERO);
        b1.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(6, 102).addBox(8.60000F, -3.98000F, 0F, 1F, 7.96000F, 1F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static AnimationDefinition createLand() {
        return AnimationDefinition.Builder.withLength(0.25000F)
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -7.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16667F, KeyframeAnimations.posVec(0.00000F, 1.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材盖", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.04167F, KeyframeAnimations.degreeVec(-0.00000F, -45.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16667F, KeyframeAnimations.degreeVec(-0.00000F, 12.50000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createDrain() {
        return AnimationDefinition.Builder.withLength(1.25000F).looping()
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.posVec(0.00000F, -2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, 1.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.62500F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.12500F, KeyframeAnimations.posVec(0.00000F, 1.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材盖", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.degreeVec(-0.00000F, -30.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.41667F, KeyframeAnimations.degreeVec(-0.00000F, -30.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.degreeVec(-0.00000F, -30.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16667F, KeyframeAnimations.degreeVec(-0.00000F, -30.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材盖", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 2.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.41667F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.50000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 2.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.16667F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.LINEAR)}))
                .build();
    }

    private static AnimationDefinition createExplode() {
        return AnimationDefinition.Builder.withLength(1.08333F)
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16667F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.degreeVec(-10.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.50000F, KeyframeAnimations.degreeVec(-10.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.33333F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.66667F, KeyframeAnimations.posVec(0.00000F, -3.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.posVec(0.00000F, 1.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.95833F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材盖", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25000F, KeyframeAnimations.degreeVec(-0.00000F, 7.50000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.45833F, KeyframeAnimations.degreeVec(-0.00000F, -142.50000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.66667F, KeyframeAnimations.degreeVec(-0.00000F, -142.50000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    private static AnimationDefinition createRevive() {
        return AnimationDefinition.Builder.withLength(1.50000F)
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12500F, KeyframeAnimations.degreeVec(-0.00000F, -5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.20833F, KeyframeAnimations.degreeVec(-0.00000F, 5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.29167F, KeyframeAnimations.degreeVec(-0.00000F, -5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.37500F, KeyframeAnimations.degreeVec(-0.00000F, 5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.45833F, KeyframeAnimations.degreeVec(-0.00000F, -5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.54167F, KeyframeAnimations.degreeVec(-0.00000F, 5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.62500F, KeyframeAnimations.degreeVec(-0.00000F, -5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.70833F, KeyframeAnimations.degreeVec(-0.00000F, 5.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.79167F, KeyframeAnimations.posVec(0.00000F, -2.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.00000F, KeyframeAnimations.posVec(0.00000F, 1.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08333F, KeyframeAnimations.posVec(0.00000F, -0.00000F, 0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .addAnimation("棺材盖", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe[]{new Keyframe(0.00000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75000F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.87500F, KeyframeAnimations.degreeVec(-0.00000F, -17.50000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.04167F, KeyframeAnimations.degreeVec(-0.00000F, 4.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.08333F, KeyframeAnimations.degreeVec(-0.00000F, 0.00000F, -0.00000F), AnimationChannel.Interpolations.CATMULLROM)}))
                .build();
    }

    public static final AnimationDefinition LAND = createLand();
    public static final AnimationDefinition DRAIN = createDrain();
    public static final AnimationDefinition EXPLODE = createExplode();
    public static final AnimationDefinition REVIVE = createRevive();

    @Override
    public void setupAnim(SoulCoffin entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        int action = entity.getAction();
        if (action == SoulCoffin.ACTION_NONE) {
            this.animate(entity.drainAnimationState, DRAIN, ageInTicks);
        } else {
            switch (action) {
                case SoulCoffin.ACTION_LAND ->
                        this.animate(entity.landAnimationState, LAND, ageInTicks);
                case SoulCoffin.ACTION_EXPLODE ->
                        this.animate(entity.explodeAnimationState, EXPLODE, ageInTicks);
                case SoulCoffin.ACTION_REVIVE ->
                        this.animate(entity.reviveAnimationState, REVIVE, ageInTicks);
                default -> {
                }
            }
        }
    }
}
