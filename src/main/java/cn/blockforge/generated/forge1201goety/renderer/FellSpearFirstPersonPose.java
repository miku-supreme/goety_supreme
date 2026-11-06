package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.entity.HumanoidArm;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearFirstPersonPose;
import cn.blockforge.generated.goetysupreme.util.FellSpearLancing;

public final class FellSpearFirstPersonPose {

    private FellSpearFirstPersonPose() {
    }

    public static void applyIdle(PoseStack poseStack, HumanoidArm arm, float equipProcess) {
        applyItemArmTransform(poseStack, arm, equipProcess);
    }

    public static void applyUse(PoseStack poseStack, HumanoidArm arm, float ticks, float equipProcess,
            float hitFeedback) {
        int sign = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate((double) ((float) sign * 0.56F), -0.52D, -0.72D);

        FellSpearLancing.Values lv = FellSpearLancing.of(ticks);
        poseStack.translate(
                (double) ((float) sign * (lv.raiseProgress() * 0.15F + lv.raiseProgressEnd() * -0.05F
                        + lv.swayProgress() * -0.1F + lv.swayScaleSlow() * 0.005F)),
                (double) (lv.raiseProgress() * -0.075F + lv.raiseProgressMiddle() * 0.075F
                        + lv.swayScaleFast() * 0.01F),
                (double) lv.raiseProgressStart() * 0.05D + (double) lv.raiseProgressEnd() * -0.05D
                        + (double) (lv.swayScaleSlow() * 0.005F));
        poseStack.rotateAround(
                Axis.XP.rotationDegrees(-65.0F * FellSpearLancing.inOutBack(lv.raiseProgress())
                        - 35.0F * lv.lowerProgress() + 100.0F * lv.raiseBackProgress()
                        + -0.5F * lv.swayScaleFast()),
                0.0F, 0.1F, 0.0F);
        poseStack.rotateAround(
                Axis.YN.rotationDegrees((float) sign
                        * (-90.0F * FellSpearLancing.progress(lv.raiseProgress(), 0.5F, 0.55F)
                                + 90.0F * lv.swayProgress() + 2.0F * lv.swayScaleSlow())),
                (float) sign * 0.15F, 0.0F, 0.0F);
        poseStack.translate(0.0D, -FellSpearLancing.hitFeedbackAmount(hitFeedback), 0.0D);
    }

    public static void applyStabSwing(PoseStack poseStack, HumanoidArm arm, float swing) {
        applyItemArmTransform(poseStack, arm, 0.0F);
        int sign = arm == HumanoidArm.RIGHT ? 1 : -1;
        float g = FellSpearLancing.inOutSine(FellSpearLancing.progress(swing, 0.0F, 0.05F));
        float h = FellSpearLancing.outBack(FellSpearLancing.progress(swing, 0.05F, 0.2F));
        float j = FellSpearLancing.inOutExpo(FellSpearLancing.progress(swing, 0.4F, 1.0F));
        poseStack.translate((float) sign * 0.1F * (g - h), -0.075F * (g - j), 0.65F * (g - h));
        poseStack.mulPose(Axis.XP.rotationDegrees(-70.0F * (g - j)));
        poseStack.translate(0.0D, 0.0D, -0.25D * (double) (j - h));
    }

    private static void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float equipProcess) {
        int sign = arm == HumanoidArm.RIGHT ? 1 : -1;
        poseStack.translate((float) sign * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
    }
}
