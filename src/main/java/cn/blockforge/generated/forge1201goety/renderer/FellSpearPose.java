package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.api.FellSpearHitFeedback;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.util.FellSpearLancing;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearAttackMixin;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;
import cn.blockforge.generated.goetysupreme.mixin.ItemInHandLayerFellSpearMixin;
import cn.blockforge.generated.goetysupreme.mixin.LivingEntityFellSpearHitFeedbackMixin;

public final class FellSpearPose {

    private static final float DEG = (float) Math.PI / 180.0F;

    private static final float RAD = 180.0F / (float) Math.PI;

    private static final float BASE_ARM_YAW = -0.1F;

    private static final float BASE_ARM_PITCH = (float) (-Math.PI / 2.0D) + 0.8F;

    public static final float FORWARD_MOVEMENT = 0.38F;

    private static final float FALL_FLYING_PITCH = 0.9599311F;

    private static final float YAW_CLAMP = 60.0F;

    private static final float MIN_PITCH_CLAMP = -120.0F;

    private static final float MAX_PITCH_CLAMP = 30.0F;

    private static final float ATTACK_BODY_TWIST = 0.2F;

    private static final float ATTACK_ARM_OFFSET = 5.0F;

    private FellSpearPose() {
    }

    public static boolean holds(LivingEntity living) {
        return spearHand(living) != null;
    }

    public static InteractionHand spearHand(LivingEntity living) {
        if (living.isUsingItem() && living.getUseItem().getItem() instanceof FellSpearItem) {
            return living.getUsedItemHand();
        }
        if (living.getMainHandItem().getItem() instanceof FellSpearItem) {
            return InteractionHand.MAIN_HAND;
        }
        if (living.getOffhandItem().getItem() instanceof FellSpearItem) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }

    public static boolean isUsingSpear(LivingEntity living) {
        return living.isUsingItem() && living.getUseItem().getItem() instanceof FellSpearItem;
    }

    public static boolean armHolds(LivingEntity living, HumanoidArm arm) {
        InteractionHand hand = spearHand(living);
        if (hand == null) {
            return false;
        }
        return spearArm(living, hand) == arm;
    }

    public static HumanoidArm handArm(LivingEntity living) {
        InteractionHand hand = spearHand(living);
        return hand == null ? living.getMainArm() : spearArm(living, hand);
    }

    private static HumanoidArm spearArm(LivingEntity living, InteractionHand hand) {
        boolean rightHand = (living.getMainArm() == HumanoidArm.RIGHT) == (hand == InteractionHand.MAIN_HAND);
        return rightHand ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
    }

    public static void apply(LivingEntity living, HumanoidModel<?> model, float ageInTicks) {
        if (!holds(living)) {
            return;
        }
        applyArm(model, living, handArm(living), isUsingSpear(living));
    }

    public static void applyArm(HumanoidModel<?> model, LivingEntity living, HumanoidArm armSide, boolean using) {
        ModelPart arm = armSide == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        int sign = armSide == HumanoidArm.RIGHT ? 1 : -1;

        arm.yRot = BASE_ARM_YAW * (float) sign + model.head.yRot;
        arm.xRot = BASE_ARM_PITCH + model.head.xRot;
        if (living.isFallFlying() || model.swimAmount > 0.0F) {
            arm.xRot -= FALL_FLYING_PITCH;
        }
        arm.yRot = DEG * Mth.clamp(RAD * arm.yRot, -YAW_CLAMP, YAW_CLAMP);
        arm.xRot = DEG * Mth.clamp(RAD * arm.xRot, MIN_PITCH_CLAMP, MAX_PITCH_CLAMP);

        if (using) {
            FellSpearLancing.Values lv = FellSpearLancing.of(living.getTicksUsingItem());
            arm.yRot += (float) (-sign) * lv.swayScaleFast() * DEG * lv.swayIntensity();
            arm.zRot += (float) (-sign) * lv.swayScaleSlow() * DEG * lv.swayIntensity() * 0.5F;
            arm.xRot += DEG * (-40.0F * lv.raiseProgressStart()
                    + 30.0F * lv.raiseProgressMiddle()
                    - 20.0F * lv.raiseProgressEnd()
                    + 20.0F * lv.lowerProgress()
                    + 10.0F * lv.raiseBackProgress()
                    + 0.6F * lv.swayScaleSlow() * lv.swayIntensity());
        }
    }

    public static void applyAttack(HumanoidModel<?> model, LivingEntity living) {
        float attack = model.attackTime;
        if (attack <= 0.0F || !holds(living)) {
            return;
        }
        HumanoidArm mainArm = living.getMainArm();
        model.body.yRot = Mth.sin(Mth.sqrt(attack) * (float) (Math.PI * 2.0)) * ATTACK_BODY_TWIST;
        if (mainArm == HumanoidArm.LEFT) {
            model.body.yRot *= -1.0F;
        }
        model.rightArm.z = Mth.sin(model.body.yRot) * ATTACK_ARM_OFFSET;
        model.rightArm.x = -Mth.cos(model.body.yRot) * ATTACK_ARM_OFFSET;
        model.leftArm.z = -Mth.sin(model.body.yRot) * ATTACK_ARM_OFFSET;
        model.leftArm.x = Mth.cos(model.body.yRot) * ATTACK_ARM_OFFSET;

        ModelPart arm = handArm(living) == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        float start = FellSpearLancing.inOutSine(FellSpearLancing.progress(attack, 0.0F, 0.05F));
        float mid = FellSpearLancing.inQuad(FellSpearLancing.progress(attack, 0.05F, 0.2F));
        float end = FellSpearLancing.inOutExpo(FellSpearLancing.progress(attack, 0.4F, 1.0F));
        arm.xRot += DEG * (90.0F * start - 120.0F * mid + 30.0F * end);
    }

    public static void applyThirdPersonItem(PoseStack pose, LivingEntity living, HumanoidArm arm,
            float attackTime) {
        if (!armHolds(living, arm)) {
            return;
        }
        if (attackTime > 0.0F) {
            float mid = FellSpearLancing.inQuad(FellSpearLancing.progress(attackTime, 0.05F, 0.2F));
            float end = FellSpearLancing.inOutExpo(FellSpearLancing.progress(attackTime, 0.4F, 1.0F));
            pivotRotate(pose, Axis.XN, 70.0F * (mid - end), -0.125F, 0.125F);
            pose.translate(0.0F, FORWARD_MOVEMENT * (mid - end), 0.0F);
            return;
        }
        if (!isUsingSpear(living)) {
            return;
        }
        float ticks = living.getTicksUsingItem();
        if (ticks <= 0.0F) {
            return;
        }
        FellSpearLancing.Values lv = FellSpearLancing.of(ticks);
        int sign = arm == HumanoidArm.RIGHT ? 1 : -1;
        float raise = lv.raiseProgress();
        float early = FellSpearLancing.inQuad(FellSpearLancing.progress(ticks, 0.05F, 0.2F));
        float late = FellSpearLancing.inOutExpo(FellSpearLancing.progress(ticks, 0.4F, 1.0F));
        float raised = 1.0F - FellSpearLancing.outBack(1.0F - raise);
        float kick = living instanceof FellSpearHitFeedback feedback
                ? FellSpearLancing.hitFeedbackAmount(feedback.goetySupreme$ticksSinceKineticHit(0.0F))
                : 0.0F;
        pose.translate(0.0D, -(double) (kick * 0.4F),
                -(double) (FORWARD_MOVEMENT * (raised - lv.raiseBackProgress())) + (double) kick);
        pivotRotate(pose, Axis.XN, 70.0F * (raise - lv.raiseBackProgress())
                - 40.0F * (early - late), -0.03125F, 0.125F);
        pivotRotate(pose, Axis.YP, (float) sign * 90.0F
                * (raise - lv.swayProgress() + 3.0F * late + early), 0.0F, 0.125F);
    }

    private static void pivotRotate(PoseStack pose, Axis axis, float degrees, float y, float z) {
        pose.translate(0.0F, y, z);
        pose.mulPose(axis.rotationDegrees(degrees));
        pose.translate(0.0F, -y, -z);
    }
}
