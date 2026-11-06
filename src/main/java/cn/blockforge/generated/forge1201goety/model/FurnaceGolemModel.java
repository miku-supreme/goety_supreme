package cn.blockforge.generated.goetysupreme.model;

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
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.model.FurnaceGolemModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.VanillaGolemServantModel;

public final class FurnaceGolemModel extends VanillaGolemServantModel<FurnaceGolem> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "furnace_golem"), "main");

    private float ignitePhase;

    private static final float[] BASE_HEAD = {0.0F, -7.0F, -2.0F};
    private static final float[] BASE_BODY = {0.0F, -7.0F, 0.0F};
    private static final float[] BASE_ARM = {0.0F, -7.0F, 0.0F};
    private static final float[] BASE_LEG_R = {-4.0F, 11.0F, 0.0F};
    private static final float[] BASE_LEG_L = {5.0F, 11.0F, 0.0F};

    private static final float UPPER_PIVOT_Y = 8.0F;
    private static final float UPPER_PIVOT_Z = 0.0F;

    private static final float WALK_LENGTH = 1.0F;
    private static final float QUICK_LENGTH = 0.5F;

    private static final float ATTACK_LENGTH = 1.5F;

    private static final float FIRE_BREATH_LENGTH = 4.5F;

    private static final float LIMB_PERIOD = 13.0F;

    private final ModelPart body;

    public FurnaceGolemModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
    }

    private record Kf(float t, float x, float y, float z) {}

    private record Track(String bone, boolean rotation, Kf... keys) {}

    private static Kf K(float t, float x, float y, float z) {
        return new Kf(t, x, y, z);
    }

    private static final Track[] WALK = {
        new Track("head", true, K(0F, -5F, 0F, -0F), K(0.25F, -0F, 0F, -0F), K(0.5F, -5F, 0F, -0F), K(0.75F, -0F, 0F, -0F), K(1F, -5F, 0F, -0F)),
        new Track("rightArm", true, K(0F, -100F, 0F, -0F), K(0.20833F, -70F, 0F, -0F), K(0.5F, 60F, 0F, -0F), K(0.79167F, -70F, 0F, -0F), K(1F, -100F, 0F, -0F)),
        new Track("leftArm", true, K(0F, 60F, 0F, -0F), K(0.29167F, -70F, 0F, -0F), K(0.5F, -100F, 0F, -0F), K(0.70833F, -70F, 0F, -0F), K(1F, 60F, 0F, -0F)),
        new Track("rightLeg", false, K(0F, 0F, -0F, 3F), K(0.25F, 0F, -3F, 0F), K(0.5F, 0F, -0F, -3F), K(1F, 0F, -0F, 3F)),
        new Track("leftLeg", false, K(0F, 0F, -0F, -3F), K(0.5F, 0F, -0F, 3F), K(0.75F, 0F, -3F, 0F), K(1F, 0F, -0F, -3F)),
        new Track("upperBody", true, K(0F, -7.5F, 0F, -0F), K(0.16667F, -4F, 0F, -0F), K(0.25F, -2.5F, 0F, -0F), K(0.5F, -7.5F, 0F, -0F), K(0.66667F, -4F, 0F, -0F), K(0.75F, -2.5F, 0F, -0F), K(1F, -7.5F, 0F, -0F)),
        new Track("upperBody", false, K(0F, 0F, -0F, 0F), K(0.25F, 0F, -1F, 0F), K(0.41667F, 0F, 1F, 0F), K(0.5F, 0F, -0F, 0F), K(0.75F, 0F, -1F, 0F), K(0.91667F, 0F, 1F, 0F), K(1F, 0F, -0F, 0F)),
    };

    private static final Track[] BURNING_WALK = {
        new Track("head", false, K(0F, 0F, -2F, 0F), K(0.125F, 0F, 1F, 0F), K(0.20833F, 0F, -0F, 0F), K(0.25F, 0F, -2F, 0F), K(0.375F, 0F, 1F, 0F), K(0.45833F, 0F, -0F, 0F), K(0.5F, 0F, -1F, 0F)),
        new Track("rightArm", true, K(0F, -29.6217F, -4.9809F, -8.6822F), K(0.25F, 30F, 0F, -0F), K(0.41667F, -20.0347F, -1.5991F, -0.1776F), K(0.5F, -29.6217F, -4.9809F, -8.6822F)),
        new Track("leftArm", true, K(0F, 30F, 0F, -0F), K(0.16667F, -20.0492F, 0.5577F, -1.6332F), K(0.25F, -29.7873F, 3.7419F, 6.5045F), K(0.5F, 30F, 0F, -0F)),
        new Track("rightLeg", false, K(0F, 0F, -0F, 3F), K(0.125F, 0F, -3F, 0F), K(0.25F, 0F, -0F, -3F), K(0.5F, 0F, -0F, 3F)),
        new Track("leftLeg", false, K(0F, 0F, -0F, -3F), K(0.25F, 0F, -0F, 3F), K(0.375F, 0F, -3F, 0F), K(0.5F, 0F, -0F, -3F)),
        new Track("upperBody", false, K(0F, 0F, -1F, 0F), K(0.125F, 0F, 2F, 0F), K(0.20833F, 0F, -0F, 0F), K(0.25F, 0F, -1F, 0F), K(0.375F, 0F, 2F, 0F), K(0.5F, 0F, -0F, 0F)),
    };

    private static final Track[] CHARGE = {
        new Track("head", false, K(0F, 0F, 1F, 0F), K(0.125F, 0F, -1F, 0F), K(0.25F, 0F, -0F, 0F), K(0.375F, 0F, -1F, 0F), K(0.5F, 0F, 1F, 0F)),
        new Track("rightArm", true, K(0F, 32.5F, 0F, -0F), K(0.125F, 22.5F, 0F, -0F), K(0.25F, 32.5F, 0F, -0F), K(0.375F, 22.5F, 0F, -0F), K(0.5F, 32.5F, 0F, -0F)),
        new Track("leftArm", true, K(0F, 32.5F, 0F, -0F), K(0.125F, 22.5F, 0F, -0F), K(0.25F, 32.5F, 0F, -0F), K(0.375F, 22.5F, 0F, -0F), K(0.5F, 32.5F, 0F, -0F)),
        new Track("rightLeg", true, K(0F, -0F, 0F, -0F), K(0.25F, 10F, 0F, -0F), K(0.5F, -0F, 0F, -0F)),
        new Track("rightLeg", false, K(0F, 0F, -0F, 3F), K(0.125F, 0F, -3F, 0F), K(0.25F, 0F, -0F, -6F), K(0.5F, 0F, -0F, 3F)),
        new Track("leftLeg", true, K(0F, 10F, 0F, -0F), K(0.25F, -0F, 0F, -0F), K(0.5F, 10F, 0F, -0F)),
        new Track("leftLeg", false, K(0F, 0F, -0F, -6F), K(0.25F, 0F, -0F, 3F), K(0.375F, 0F, -3F, 0F), K(0.5F, 0F, -0F, -6F)),
        new Track("upperBody", false, K(0F, 0F, -0F, 0F), K(0.125F, 0F, -1F, 0F), K(0.20833F, 0F, 1F, 0F), K(0.25F, 0F, -0F, 0F), K(0.375F, 0F, -1F, 0F), K(0.45833F, 0F, 1F, 0F), K(0.5F, 0F, -0F, 0F)),
    };

    private static final Track[] ATTACK = {
        new Track("head", true, K(0F, -0F, 0F, -0F), K(0.91667F, -0F, 0F, -0F), K(1F, -12.5F, 0F, -0F), K(1.5F, -0F, 0F, -0F)),
        new Track("rightArm", true, K(0F, -0F, 0F, -0F), K(0.125F, -15F, 0F, -0F), K(0.5F, 45F, 0F, -0F), K(0.91667F, 45F, 0F, -0F), K(1F, -130F, 0F, -0F), K(1.5F, -0F, 0F, -0F)),
        new Track("leftArm", true, K(0F, -0F, 0F, -0F), K(0.125F, -15F, 0F, -0F), K(0.5F, 45F, 0F, -0F), K(0.91667F, 45F, 0F, -0F), K(1F, -130F, 0F, -0F), K(1.5F, -0F, 0F, -0F)),
        new Track("rightLeg", true, K(0F, -0F, 0F, -0F), K(0.5F, 20F, 0F, -0F), K(1.5F, -0F, 0F, -0F)),
        new Track("rightLeg", false, K(0F, 0F, -0F, 0F), K(0.25F, 0F, -4F, 0F), K(0.5F, 0F, -1F, 2F), K(1.5F, 0F, -1F, 0F)),
        new Track("upperBody", true, K(0F, -0F, 0F, -0F), K(0.5F, 15F, 0F, -0F), K(0.83333F, 15F, 0F, -0F), K(1F, -12.5F, 0F, -0F), K(1.5F, -0F, 0F, -0F)),
        new Track("upperBody", false, K(0F, 0F, -0F, 0F), K(0.25F, 0F, -2F, 1.5F), K(0.41667F, 0F, 1.33F, 3.61F), K(0.5F, 0F, -0F, 3F), K(0.83333F, 0F, -0F, 3F), K(1F, 0F, -2F, -3F), K(1.5F, 0F, -0F, 0F)),
    };

    private static final Track[] FIRE_BREATH = {
        new Track("head", true, K(1.5F, -0F, 0F, -0F), K(1.75F, -0F, 0F, -0F), K(2.25F, -0F, 7.5F, -0F), K(2.75F, -0F, 0F, -0F), K(3.25F, -0F, 7.5F, -0F), K(3.75F, -0F, 0F, -0F), K(4.25F, -0F, 7.5F, -0F)),
        new Track("rightArm", true, K(0F, -0F, 0F, -0F), K(0.25F, -39.1041F, -18.177F, -20.9969F), K(0.45833F, -39.1041F, -18.177F, -20.9969F), K(0.75F, -195.088F, -2.0882F, -31.1874F), K(1F, -195.088F, -2.0882F, -31.1874F), K(1.75F, -200.088F, -2.0882F, -31.1874F), K(2.25F, -195.088F, -2.0882F, -31.1874F), K(2.75F, -200.088F, -2.0882F, -31.1874F), K(3.25F, -195.088F, -2.0882F, -31.1874F), K(3.75F, -200.088F, -2.0882F, -31.1874F), K(4.25F, -195.088F, -2.0882F, -31.1874F), K(4.5F, -0F, 0F, -0F)),
        new Track("leftArm", true, K(0F, -0F, 0F, -0F), K(0.25F, -40.8934F, 20.7048F, 22.2076F), K(0.45833F, -40.8934F, 20.7048F, 22.2076F), K(0.75F, -196.461F, 0.2972F, 27.4928F), K(1F, -206.461F, 0.2972F, 27.4928F), K(1.75F, -196.461F, 0.2972F, 27.4928F), K(2.25F, -206.461F, 0.2972F, 27.4928F), K(2.75F, -196.461F, 0.2972F, 27.4928F), K(3.25F, -206.461F, 0.2972F, 27.4928F), K(3.75F, -196.461F, 0.2972F, 27.4928F), K(4.25F, -206.461F, 0.2972F, 27.4928F), K(4.5F, -0F, 0F, -0F)),
        new Track("rightLeg", false, K(0F, 0F, -0F, 0F), K(0.41667F, 0F, -0F, 0F), K(0.625F, 0F, -3F, 2.5F), K(0.75F, 0F, -0F, 4F), K(4.5F, 0F, -0F, 0F)),
        new Track("upperBody", true, K(0F, -0F, 0F, -0F), K(0.25F, 42.5F, 0F, -0F), K(0.45833F, 42.5F, 0F, -0F), K(0.75F, -22.5F, 0F, -0F), K(4.375F, -22.5F, 0F, -0F), K(4.5F, -0F, 0F, -0F)),
        new Track("upperBody", false, K(0.70833F, 0F, -0F, 0F), K(0.75F, 0F, -1F, 0F), K(0.79167F, -1F, -1F, 0F), K(0.83333F, -1F, -0F, 0F), K(0.875F, 0F, -0F, 0F), K(0.91667F, 0F, -1F, 0F), K(0.95833F, -1F, -1F, 0F), K(1F, -1F, -0F, 0F), K(1.04167F, 0F, -0F, 0F), K(1.08333F, 0F, -1F, 0F), K(1.125F, -1F, -1F, 0F), K(1.16667F, -1F, -0F, 0F), K(1.20833F, 0F, -0F, 0F), K(1.25F, 0F, -1F, 0F), K(1.29167F, -1F, -1F, 0F), K(1.33333F, -1F, -0F, 0F), K(1.375F, 0F, -0F, 0F), K(1.41667F, 0F, -1F, 0F), K(1.45833F, -1F, -1F, 0F), K(1.5F, -1F, -0F, 0F), K(1.54167F, 0F, -0F, 0F), K(1.58333F, 0F, -1F, 0F), K(1.625F, -1F, -1F, 0F), K(1.66667F, -1F, -0F, 0F), K(1.70833F, 0F, -0F, 0F), K(1.75F, 0F, -1F, 0F), K(1.79167F, -1F, -1F, 0F), K(1.83333F, -1F, -0F, 0F), K(1.875F, 0F, -0F, 0F), K(1.91667F, 0F, -1F, 0F), K(1.95833F, -1F, -1F, 0F), K(2F, -1F, -0F, 0F), K(2.04167F, 0F, -0F, 0F), K(2.08333F, 0F, -1F, 0F), K(2.125F, -1F, -1F, 0F), K(2.16667F, -1F, -0F, 0F), K(2.20833F, 0F, -0F, 0F), K(2.25F, 0F, -1F, 0F), K(2.29167F, -1F, -1F, 0F), K(2.33333F, -1F, -0F, 0F), K(2.375F, 0F, -0F, 0F), K(2.41667F, 0F, -1F, 0F), K(2.45833F, -1F, -1F, 0F), K(2.5F, -1F, -0F, 0F), K(2.54167F, 0F, -0F, 0F), K(2.58333F, 0F, -1F, 0F), K(2.625F, -1F, -1F, 0F), K(2.66667F, -1F, -0F, 0F), K(2.70833F, 0F, -0F, 0F), K(2.75F, 0F, -1F, 0F), K(2.79167F, -1F, -1F, 0F), K(2.83333F, -1F, -0F, 0F), K(2.875F, 0F, -0F, 0F), K(2.91667F, 0F, -1F, 0F), K(2.95833F, -1F, -1F, 0F), K(3F, -1F, -0F, 0F), K(3.04167F, 0F, -0F, 0F), K(3.08333F, 0F, -1F, 0F), K(3.125F, -1F, -1F, 0F), K(3.16667F, -1F, -0F, 0F), K(3.20833F, 0F, -0F, 0F), K(3.25F, 0F, -1F, 0F), K(3.29167F, -1F, -1F, 0F), K(3.33333F, -1F, -0F, 0F), K(3.375F, 0F, -0F, 0F), K(3.41667F, 0F, -1F, 0F), K(3.45833F, -1F, -1F, 0F), K(3.5F, -1F, -0F, 0F), K(3.54167F, 0F, -0F, 0F), K(3.58333F, 0F, -1F, 0F), K(3.625F, -1F, -1F, 0F), K(3.66667F, -1F, -0F, 0F), K(3.70833F, 0F, -0F, 0F), K(3.75F, 0F, -1F, 0F), K(3.79167F, -1F, -1F, 0F), K(3.83333F, -1F, -0F, 0F), K(3.875F, 0F, -0F, 0F), K(3.91667F, 0F, -1F, 0F), K(3.95833F, -1F, -1F, 0F), K(4F, -1F, -0F, 0F), K(4.04167F, 0F, -0F, 0F), K(4.08333F, 0F, -1F, 0F), K(4.125F, -1F, -1F, 0F), K(4.16667F, -1F, -0F, 0F), K(4.20833F, 0F, -0F, 0F), K(4.25F, 0F, -1F, 0F), K(4.29167F, -1F, -1F, 0F), K(4.33333F, -1F, -0F, 0F), K(4.375F, 0F, -0F, 0F)),
    };

    private static final Track[] IGNITE_FRONT = {
        new Track("leftArm", true, K(0F, -0F, 0F, -0F), K(0.25F, -40F, 0F, -0F), K(0.75F, -40F, 0F, -0F), K(1F, -0F, 0F, -0F)),
        new Track("upperBody", true, K(0F, -0F, -0F, 0F), K(0.25F, -0F, 17.5F, -0F), K(0.75F, -0F, 17.5F, -0F), K(1F, -0F, -0F, 0F)),
    };

    private static final Track[] IGNITE_BACK = {
        new Track("rightArm", true, K(0F, -0F, 0F, -0F), K(0.08333F, -59.6189F, 8.6492F, 5.0384F), K(0.5F, -59.6189F, 8.6492F, 5.0384F), K(0.75F, -0F, 0F, -0F)),
        new Track("leftArm", true, K(0F, -0F, 0F, -0F), K(0.08333F, -59.6189F, -8.6492F, -5.0384F), K(0.5F, -59.6189F, -8.6492F, -5.0384F), K(0.75F, -0F, 0F, -0F)),
        new Track("upperBody", true, K(0F, -0F, 0F, -0F), K(0.08333F, -20F, 0F, -0F), K(0.5F, -20F, 0F, -0F), K(0.75F, -0F, 0F, -0F)),
    };

    @Override
    public void prepareMobModel(FurnaceGolem servant, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(servant, limbSwing, limbSwingAmount, partialTick);
        int ignite = servant.getIgniteAnimationTick();
        if (ignite <= 0 || servant.getAttackAnimationTick() > 0) {
            this.ignitePhase = -1.0F;
            return;
        }
        float elapsed = servant.getIgniteAnimationTicks() - ignite + partialTick;
        this.ignitePhase = Math.max(0.0F, elapsed / 20.0F);
    }

    private boolean isIgniting() {
        return this.ignitePhase >= 0.0F;
    }

    @Override
    public void setupAnim(FurnaceGolem servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        Track[] clip;
        float phase;
        float weight;
        int attackTicks = servant.getAttackAnimationTick();
        if (isIgniting()) {
            clip = servant.getIgniteSegment() == FurnaceGolem.IGNITE_SEGMENT_BACK
                    ? IGNITE_BACK : IGNITE_FRONT;
            phase = this.ignitePhase;
            weight = 1.0F;
        } else if (attackTicks > 0) {
            if (servant.getAttackAnimation() == AbstractGolemServant.ATTACK_FIRE_BREATH) {
                clip = FIRE_BREATH;
                int total = Math.max(1, servant.getAttackAnimationTicks());
                float progress = Mth.clamp((total - attackTicks) / (float) total, 0.0F, 1.0F);
                phase = progress * FIRE_BREATH_LENGTH;
                weight = 1.0F;
            } else {
                clip = ATTACK;
                int total = Math.max(1, servant.getAttackAnimationTicks());
                phase = meleePhase(total, total - attackTicks, servant.recoveryImpactTicks());
                weight = 1.0F;
            }
        } else if (servant.isBurning() && servant.isBurningAlert()) {
            clip = CHARGE;
            phase = walkPhase(limbSwing, QUICK_LENGTH);
            weight = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        } else if (servant.isBurning()) {
            clip = BURNING_WALK;
            phase = walkPhase(limbSwing, QUICK_LENGTH);
            weight = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        } else {
            clip = WALK;
            phase = walkPhase(limbSwing, WALK_LENGTH);
            weight = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        }

        float upperX = sampleTrack(clip, "upperBody", true, phase)[0] * weight;
        float upperY = sampleTrack(clip, "upperBody", true, phase)[1] * weight;
        float[] upperPos = sampleTrack(clip, "upperBody", false, phase);

        apply(clip, "head", phase, weight, BASE_HEAD, true, upperX, upperY, upperPos);
        apply(clip, "body", phase, weight, BASE_BODY, true, upperX, upperY, upperPos);
        apply(clip, "rightArm", phase, weight, BASE_ARM, true, upperX, upperY, upperPos);
        apply(clip, "leftArm", phase, weight, BASE_ARM, true, upperX, upperY, upperPos);
        apply(clip, "rightLeg", phase, weight, BASE_LEG_R, false, 0.0F, 0.0F, upperPos);
        apply(clip, "leftLeg", phase, weight, BASE_LEG_L, false, 0.0F, 0.0F, upperPos);

        if (attackTicks > 0) {
            blendWalkingLegs(servant, limbSwing, limbSwingAmount);
        }

        if (attackTicks > 0 && servant.getAttackAnimation() == AbstractGolemServant.ATTACK_FIRE_BREATH) {
            this.head.xRot += headPitch * Mth.DEG_TO_RAD;
        } else {
            this.head.yRot = (netHeadYaw + upperY) * Mth.DEG_TO_RAD;
            this.head.xRot += headPitch * Mth.DEG_TO_RAD;
        }
    }

    private static float meleePhase(int totalTicks, float elapsedTicks, float[] recovery) {
        if (recovery == null || recovery.length < 2 || recovery[0] <= 0.0F || recovery[1] <= 0.0F) {
            return Mth.clamp(elapsedTicks / (float) Math.max(1, totalTicks), 0.0F, 1.0F) * ATTACK_LENGTH;
        }
        float impactAction = recovery[0];
        float impactSeconds = recovery[1] / 20.0F;
        if (elapsedTicks <= impactAction) {
            return elapsedTicks * (impactSeconds / impactAction);
        }
        return Math.min(ATTACK_LENGTH, impactSeconds + (elapsedTicks - impactAction) / 20.0F);
    }

    private static float walkPhase(float limbSwing, float clipLength) {
        float cycles = limbSwing / LIMB_PERIOD * (WALK_LENGTH / clipLength);
        cycles %= 1.0F;
        if (cycles < 0.0F) {
            cycles += 1.0F;
        }
        return cycles * clipLength;
    }

    private void blendWalkingLegs(FurnaceGolem servant, float limbSwing, float limbSwingAmount) {
        float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        if (amount <= 0.0F) {
            return;
        }
        boolean quick = servant.isBurning();
        Track[] legClip = quick ? BURNING_WALK : WALK;
        float legLength = quick ? QUICK_LENGTH : WALK_LENGTH;
        float phase = walkPhase(limbSwing, legLength);
        blendLeg(legClip, "rightLeg", phase, amount, BASE_LEG_R, this.rightLeg);
        blendLeg(legClip, "leftLeg", phase, amount, BASE_LEG_L, this.leftLeg);
    }

    private void blendLeg(Track[] clip, String bone, float phase, float weight, float[] base,
            ModelPart part) {
        float x = part.x;
        float y = part.y;
        float z = part.z;
        float rotX = part.xRot;
        float rotY = part.yRot;
        float rotZ = part.zRot;
        apply(clip, bone, phase, 1.0F, base, false, 0.0F, 0.0F, ZERO);
        part.x = lerp(x, part.x, weight);
        part.y = lerp(y, part.y, weight);
        part.z = lerp(z, part.z, weight);
        part.xRot = lerp(rotX, part.xRot, weight);
        part.yRot = lerp(rotY, part.yRot, weight);
        part.zRot = lerp(rotZ, part.zRot, weight);
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    private void apply(Track[] clip, String bone, float phase, float weight, float[] base,
            boolean linkedToUpper, float upperRotX, float upperRotY, float[] upperPos) {
        float[] rot = sampleTrack(clip, bone, true, phase);
        float[] pos = sampleTrack(clip, bone, false, phase);
        float rx = rot[0] * weight + (linkedToUpper ? upperRotX : 0.0F);
        float ry = rot[1] * weight + (linkedToUpper ? upperRotY : 0.0F);
        float rz = rot[2] * weight;
        float ox = base[0] + pos[0] * weight + (linkedToUpper ? upperPos[0] * weight : 0.0F);
        float oy = base[1] + pos[1] * weight + (linkedToUpper ? upperPos[1] * weight : 0.0F);
        float oz = base[2] + pos[2] * weight + (linkedToUpper ? upperPos[2] * weight : 0.0F);
        if (linkedToUpper && upperRotX != 0.0F) {
            float rad = upperRotX * Mth.DEG_TO_RAD;
            float dy = base[1] - UPPER_PIVOT_Y;
            float dz = base[2] - UPPER_PIVOT_Z;
            float cos = Mth.cos(rad);
            float sin = Mth.sin(rad);
            oy = UPPER_PIVOT_Y + dy * cos - dz * sin + pos[1] * weight + upperPos[1] * weight;
            oz = UPPER_PIVOT_Z + dy * sin + dz * cos + pos[2] * weight + upperPos[2] * weight;
        }
        if (linkedToUpper && upperRotY != 0.0F) {
            float rad = upperRotY * Mth.DEG_TO_RAD;
            float cos = Mth.cos(rad);
            float sin = Mth.sin(rad);
            float nx = ox * cos + oz * sin;
            float nz = -ox * sin + oz * cos;
            ox = nx;
            oz = nz;
        }
        ModelPart part = part(bone);
        part.x = ox;
        part.y = oy;
        part.z = oz;
        part.xRot = rx * Mth.DEG_TO_RAD;
        part.yRot = ry * Mth.DEG_TO_RAD;
        part.zRot = rz * Mth.DEG_TO_RAD;
    }

    private ModelPart part(String bone) {
        return switch (bone) {
            case "head" -> this.head;
            case "body" -> this.body;
            case "rightArm" -> this.rightArm;
            case "leftArm" -> this.leftArm;
            case "rightLeg" -> this.rightLeg;
            default -> this.leftLeg;
        };
    }

    private static float[] sampleTrack(Track[] clip, String bone, boolean rotation, float t) {
        for (Track track : clip) {
            if (track.rotation() == rotation && track.bone().equals(bone)) {
                return sample(track, t, isLooping(clip));
            }
        }
        return ZERO;
    }

    private static boolean isLooping(Track[] clip) {
        return clip == WALK || clip == BURNING_WALK || clip == CHARGE;
    }

    private static final float[] ZERO = {0.0F, 0.0F, 0.0F};

    private static float[] sample(Track track, float t, boolean loop) {
        Kf[] k = track.keys();
        int n = k.length;
        if (n == 0) return ZERO;
        if (n == 1 || t <= k[0].t()) return vec(k[0]);
        if (t >= k[n - 1].t()) return vec(k[n - 1]);
        int i = 0;
        while (i < n - 2 && k[i + 1].t() <= t) {
            ++i;
        }
        float span = k[i + 1].t() - k[i].t();
        float u = span <= 0.0F ? 0.0F : (t - k[i].t()) / span;
        Kf p0 = i > 0 ? k[i - 1] : (loop ? k[n - 2] : k[i]);
        Kf p1 = k[i];
        Kf p2 = k[i + 1];
        Kf p3 = i + 2 < n ? k[i + 2] : (loop ? k[1] : k[i + 1]);
        return new float[] {
                catmull(p0.x(), p1.x(), p2.x(), p3.x(), u),
                catmull(p0.y(), p1.y(), p2.y(), p3.y(), u),
                catmull(p0.z(), p1.z(), p2.z(), p3.z(), u)};
    }

    private static float[] vec(Kf k) {
        return new float[] {k.x(), k.y(), k.z()};
    }

    private static float catmull(float p0, float p1, float p2, float p3, float u) {
        float u2 = u * u;
        float u3 = u2 * u;
        return 0.5F * (2.0F * p1 + (-p0 + p2) * u
                + (2.0F * p0 - 5.0F * p1 + 4.0F * p2 - p3) * u2
                + (-p0 + 3.0F * p1 - 3.0F * p2 + p3) * u3);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F)
                        .texOffs(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F)
                        .texOffs(84, 0).addBox(-4.0F, -12.0F, -7.5F, 8.0F, 5.0F, 2.0F),
                PartPose.offset(0.0F, -7.0F, -2.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F)
                        .texOffs(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F,
                                new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -7.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(0.0F, -7.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(0.0F, -7.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(-4.0F, 11.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(60, 0).mirror()
                        .addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(5.0F, 11.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }
}
