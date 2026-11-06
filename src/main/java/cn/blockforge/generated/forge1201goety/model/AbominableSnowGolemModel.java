package cn.blockforge.generated.goetysupreme.model;

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
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;
import cn.blockforge.generated.goetysupreme.model.EngenderGolemServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.api.HeadProvider;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemClips;

public class AbominableSnowGolemModel extends EntityModel<AbominableSnowGolemServant>
        implements HeadProvider {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "abominable_snow_golem"), "main");

    private static final float WALK_STRIDE = 6.2831855F;

    private static final float IDLE_THRESHOLD = 0.05F;

    private final ModelPart root;
    private final ModelPart upperBody;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public AbominableSnowGolemModel(ModelPart root) {
        this.root = root;
        this.upperBody = SafeModelParts.find(root, "upper_body");
        this.body = SafeModelParts.findPath(root, "upper_body", "body");
        this.head = SafeModelParts.findPath(root, "upper_body", "head");
        this.rightArm = SafeModelParts.findPath(root, "upper_body", "right_arm");
        this.leftArm = SafeModelParts.findPath(root, "upper_body", "left_arm");
        this.rightLeg = SafeModelParts.find(root, "right_leg");
        this.leftLeg = SafeModelParts.find(root, "left_leg");
    }

    @Override
    public ModelPart getHead() {
        return head;
    }

    @Override
    public void translateHead(PoseStack poseStack) {
        this.upperBody.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition upper = root.addOrReplaceChild("upper_body", CubeListBuilder.create(),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        upper.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-9.0F, -17.0F, -6.0F, 18.0F, 12.0F, 11.0F)
                        .texOffs(0, 70).addBox(-4.5F, -5.0F, -3.0F, 9.0F, 5.0F, 6.0F,
                                new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));
        upper.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, -17.0F, -3.5F));
        upper.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(60, 58).addBox(0.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(9.0F, -16.0F, 0.0F));
        upper.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(60, 21).addBox(-3.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F),
                PartPose.offset(-10.0F, -16.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(60, 0).mirror().addBox(-2.5F, -1.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(4.0F, 9.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(37, 0).addBox(-3.5F, -1.0F, -3.0F, 6.0F, 16.0F, 5.0F),
                PartPose.offset(-4.0F, 9.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    public ModelPart resolveBone(String name) {
        return switch (name) {
            case "upper_body" -> upperBody;
            case "body" -> body;
            case "head" -> head;
            case "left_arm" -> leftArm;
            case "right_arm" -> rightArm;
            case "left_leg" -> leftLeg;
            case "right_leg" -> rightLeg;
            default -> SafeModelParts.dummy();
        };
    }

    @Override
    public void setupAnim(AbominableSnowGolemServant servant, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        resetToRestPose();
        int attack = servant.getAttackAnimationTick();
        int attackTicks = servant.getAttackAnimationTicks();
        int animation = servant.getAttackAnimation();
        boolean attackPlaying = servant.isAttackAnimating() && attackTicks > 0;
        if (attackPlaying) {
            GolemClip clip = attackClip(animation);
            float tick = attackTicks - attack + Mth.frac(ageInTicks);
            if (clip.lengthTicks > 0.0F && Math.abs(clip.lengthTicks - attackTicks) > 0.001F) {
                tick *= clip.lengthTicks / attackTicks;
            }
            clip.apply(this::resolveBone, tick);
            blendWalkingLegs(limbSwing, limbSwingAmount);
        } else {
            float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
            if (amount <= IDLE_THRESHOLD) {
                float idleTick = ageInTicks % SnowGolemClips.IDLE_LENGTH;
                SnowGolemClips.idle().apply(this::resolveBone, idleTick);
            } else {
                float phase = limbSwing % WALK_STRIDE;
                if (phase < 0.0F) {
                    phase += WALK_STRIDE;
                }
                float tick = phase * SnowGolemClips.WALK_LENGTH / WALK_STRIDE;
                GolemClip walk = SnowGolemClips.walk();
                if (amount >= 0.999F) {
                    walk.apply(this::resolveBone, tick);
                } else {
                    applyScaled(walk, tick, amount);
                }
            }
        }
        boolean clipControlsHead = attackPlaying
                && (animation == AbstractGolemServant.ATTACK_SNOW_THROW_BLOCK
                || animation == AbstractGolemServant.ATTACK_SNOW_THROW_BALL
                || animation == AbstractGolemServant.ATTACK_SNOW_THROW_BALL_FINISH);
        boolean breathing = attackPlaying && animation == AbstractGolemServant.ATTACK_FROST_BREATH;
        if (!clipControlsHead) {
            if (!breathing) {
                this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
            }
            this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        }
    }

    private static GolemClip attackClip(int animation) {
        return switch (animation) {
            case AbstractGolemServant.ATTACK_SNOW_SLAM -> SnowGolemClips.attack();
            case AbstractGolemServant.ATTACK_SNOW_THROW_BLOCK -> SnowGolemClips.throwBlock();
            case AbstractGolemServant.ATTACK_SNOW_THROW_BALL -> SnowGolemClips.throwSnowball();
            case AbstractGolemServant.ATTACK_SNOW_THROW_BALL_SPIN -> SnowGolemClips.spinLoop();
            case AbstractGolemServant.ATTACK_SNOW_THROW_BALL_FINISH -> SnowGolemClips.snowballFinish();
            case AbstractGolemServant.ATTACK_FROST_BREATH -> SnowGolemClips.frostBreath();
            default -> SnowGolemClips.attack();
        };
    }

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

    private void blendWalkingLegs(float limbSwing, float limbSwingAmount) {
        float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        if (amount <= 0.0F) {
            return;
        }
        float phase = limbSwing % WALK_STRIDE;
        if (phase < 0.0F) {
            phase += WALK_STRIDE;
        }
        float tick = phase * SnowGolemClips.WALK_LENGTH / WALK_STRIDE;

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

        SnowGolemClips.walk().apply(this::legBone, tick);

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
        for (ModelPart part : new ModelPart[] {this.upperBody, this.body, this.head, this.leftArm,
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
