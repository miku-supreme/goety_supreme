package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public abstract class AbstractGolemServantModel<T extends AbstractGolemServant> extends EntityModel<T> {

    public static final float LAVA_UPPER_PITCH = 0.34906584F;

    public static final float ARM_LIFT = -0.34906584F;

    private static final float WALK_STRIDE = 6.2831855F;

    public final ModelPart root;

    public final ModelPart upperBody;
    public final ModelPart head;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public AbstractGolemServantModel(ModelPart root) {
        this.root = root;
        this.upperBody = SafeModelParts.find(root, "upper_body");
        this.head = SafeModelParts.findPath(root, "upper_body", "head");
        this.rightArm = SafeModelParts.findPath(root, "upper_body", "right_arm");
        this.leftArm = SafeModelParts.findPath(root, "upper_body", "left_arm");
        this.rightLeg = SafeModelParts.find(root, "right_leg");
        this.leftLeg = SafeModelParts.find(root, "left_leg");
    }

    public ModelPart resolveBone(String name) {
        ModelPart direct = SafeModelParts.findOrNull(this.root, name);
        if (direct != null) {
            return direct;
        }
        ModelPart nested = SafeModelParts.findOrNull(this.upperBody, name);
        return nested != null ? nested : SafeModelParts.find(this.root, name);
    }

    public static LayerDefinition createBodyLayer(float upperPitch, float armPitch) {
        return createBodyLayer(upperPitch, armPitch, armPitch);
    }

    public static LayerDefinition createBodyLayer(float upperPitch, float leftArmPitch,
            float rightArmPitch) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition upper = root.addOrReplaceChild("upper_body", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, upperPitch, 0.0F, 0.0F));
        upper.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F)
                        .texOffs(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F,
                                new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -14.0F, 0.0F));
        upper.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -11.0F, -5.5F, 8.0F, 10.0F, 8.0F)
                        .texOffs(24, 0).addBox(-1.0F, -4.0F, -7.5F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -15.0F, -2.0F));
        upper.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(60, 58).addBox(0.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offsetAndRotation(9.0F, -14.0F, 0.0F, leftArmPitch, 0.0F, 0.0F));
        upper.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(60, 21).addBox(-4.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offsetAndRotation(-9.0F, -14.0F, 0.0F, rightArmPitch, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(37, 0).addBox(-2.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(4.0F, 11.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(60, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(-5.0F, 11.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        resetToRestPose();
        int attack = servant.getAttackAnimationTick();
        int attackTicks = servant.getAttackAnimationTicks();
        if (servant.isAttackAnimating() && attackTicks > 0) {
            GolemClip clip = switch (servant.getAttackAnimation()) {
                case AbstractGolemServant.ATTACK_SMASH -> smashClip();
                case AbstractGolemServant.ATTACK_FIREBALL -> fireballClip();
                default -> sweepClip();
            };
            clip.apply(this::resolveBone,
                    clipTick(servant, clip, attackTicks, attackTicks - attack + Mth.frac(ageInTicks)));
            blendWalkingLegs(servant.isCharging(), limbSwing, limbSwingAmount);
        } else {
            float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
            boolean charging = servant.isCharging();
            GolemClip cycle = charging ? chargeClip() : walkClip();
            float length = charging ? chargeLengthTicks() : walkLengthTicks();
            float phase = limbSwing % WALK_STRIDE;
            if (phase < 0.0F) {
                phase += WALK_STRIDE;
            }
            float tick = phase * length / WALK_STRIDE;
            if (charging || amount >= 0.999F) {
                cycle.apply(this::resolveBone, tick);
            } else {
                applyScaled(cycle, tick, amount);
            }
        }
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
    }

    public float clipTick(T servant, GolemClip clip, int attackTicks, float elapsedTicks) {
        float[] impact = servant.recoveryImpactTicks();
        if (impact != null && impact.length >= 2 && impact[0] > 0.0F && impact[1] > 0.0F
                && clip.lengthTicks > impact[1]) {
            if (elapsedTicks <= impact[0]) {
                return elapsedTicks * (impact[1] / impact[0]);
            }
            return Math.min(clip.lengthTicks, impact[1] + (elapsedTicks - impact[0]));
        }
        return clip.lengthTicks > 0.0F && attackTicks > 0
                ? elapsedTicks * clip.lengthTicks / attackTicks
                : elapsedTicks;
    }

    public abstract float walkLengthTicks();

    public abstract GolemClip walkClip();

    public abstract float chargeLengthTicks();

    public abstract GolemClip chargeClip();

    public abstract GolemClip sweepClip();

    public abstract GolemClip smashClip();

    public abstract GolemClip fireballClip();

    private void applyScaled(GolemClip clip, float tick, float amount) {
        PartPose ubBase = this.upperBody.getInitialPose();
        PartPose laBase = this.leftArm.getInitialPose();
        PartPose raBase = this.rightArm.getInitialPose();
        PartPose llBase = this.leftLeg.getInitialPose();
        PartPose rlBase = this.rightLeg.getInitialPose();
        float ubX = ubBase.xRot;
        float ubY = ubBase.yRot;
        float ubZ = ubBase.zRot;
        float ubPosY = ubBase.y;
        float laX = laBase.xRot;
        float raX = raBase.xRot;
        float laY = laBase.yRot;
        float raY = raBase.yRot;
        float laZ = laBase.zRot;
        float raZ = raBase.zRot;
        float llX = llBase.xRot;
        float rlX = rlBase.xRot;
        float llZ = llBase.z;
        float rlZ = rlBase.z;
        float llY = llBase.y;
        float rlY = rlBase.y;

        clip.apply(this::resolveBone, tick);

        this.upperBody.xRot = lerp(ubX, this.upperBody.xRot, amount);
        this.upperBody.yRot = lerp(ubY, this.upperBody.yRot, amount);
        this.upperBody.zRot = lerp(ubZ, this.upperBody.zRot, amount);
        this.upperBody.y = lerp(ubPosY, this.upperBody.y, amount);
        this.leftArm.xRot = lerp(laX, this.leftArm.xRot, amount);
        this.rightArm.xRot = lerp(raX, this.rightArm.xRot, amount);
        this.leftArm.yRot = lerp(laY, this.leftArm.yRot, amount);
        this.rightArm.yRot = lerp(raY, this.rightArm.yRot, amount);
        this.leftArm.zRot = lerp(laZ, this.leftArm.zRot, amount);
        this.rightArm.zRot = lerp(raZ, this.rightArm.zRot, amount);
        this.leftLeg.xRot = lerp(llX, this.leftLeg.xRot, amount);
        this.rightLeg.xRot = lerp(rlX, this.rightLeg.xRot, amount);
        this.leftLeg.z = lerp(llZ, this.leftLeg.z, amount);
        this.rightLeg.z = lerp(rlZ, this.rightLeg.z, amount);
        this.leftLeg.y = lerp(llY, this.leftLeg.y, amount);
        this.rightLeg.y = lerp(rlY, this.rightLeg.y, amount);
    }

    private void blendWalkingLegs(boolean charging, float limbSwing, float limbSwingAmount) {
        float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        if (amount <= 0.0F) {
            return;
        }
        GolemClip cycle = charging ? chargeClip() : walkClip();
        float length = charging ? chargeLengthTicks() : walkLengthTicks();
        float phase = limbSwing % WALK_STRIDE;
        if (phase < 0.0F) {
            phase += WALK_STRIDE;
        }
        float tick = phase * length / WALK_STRIDE;

        float llRotX = this.leftLeg.xRot;
        float llRotY = this.leftLeg.yRot;
        float llRotZ = this.leftLeg.zRot;
        float llX = this.leftLeg.x;
        float llY = this.leftLeg.y;
        float llZ = this.leftLeg.z;
        float rlRotX = this.rightLeg.xRot;
        float rlRotY = this.rightLeg.yRot;
        float rlRotZ = this.rightLeg.zRot;
        float rlX = this.rightLeg.x;
        float rlY = this.rightLeg.y;
        float rlZ = this.rightLeg.z;

        cycle.apply(this::legBone, tick);

        this.leftLeg.xRot = lerp(llRotX, this.leftLeg.xRot, amount);
        this.leftLeg.yRot = lerp(llRotY, this.leftLeg.yRot, amount);
        this.leftLeg.zRot = lerp(llRotZ, this.leftLeg.zRot, amount);
        this.leftLeg.x = lerp(llX, this.leftLeg.x, amount);
        this.leftLeg.y = lerp(llY, this.leftLeg.y, amount);
        this.leftLeg.z = lerp(llZ, this.leftLeg.z, amount);
        this.rightLeg.xRot = lerp(rlRotX, this.rightLeg.xRot, amount);
        this.rightLeg.yRot = lerp(rlRotY, this.rightLeg.yRot, amount);
        this.rightLeg.zRot = lerp(rlRotZ, this.rightLeg.zRot, amount);
        this.rightLeg.x = lerp(rlX, this.rightLeg.x, amount);
        this.rightLeg.y = lerp(rlY, this.rightLeg.y, amount);
        this.rightLeg.z = lerp(rlZ, this.rightLeg.z, amount);
    }

    private ModelPart legBone(String name) {
        return switch (name) {
            case "left_leg" -> this.leftLeg;
            case "right_leg" -> this.rightLeg;
            default -> SafeModelParts.dummy();
        };
    }

    private void resetToRestPose() {
        for (ModelPart part : new ModelPart[] {this.upperBody, this.head, this.leftArm,
                this.rightArm, this.leftLeg, this.rightLeg}) {
            part.resetPose();
        }
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
