package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.util.Ease;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearFirstPersonPose;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.util.FellSpearLancing;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;

public final class FellSpearLancing {

    private static final float DEG = (float) Math.PI / 180.0F;

    private FellSpearLancing() {
    }

    public static Values of(float ticks) {
        int delay = FellSpearItem.chargeDelayTicks();
        int dismountEnd = delay + FellSpearItem.dismountWindowTicks();
        int dismountStart = dismountEnd - 20;
        int knockbackEnd = delay + FellSpearItem.knockbackWindowTicks();
        int knockbackStart = knockbackEnd - 40;
        int damageEnd = delay + FellSpearItem.damageWindowTicks();

        float raise = progress(ticks, 0.0F, delay);
        float raiseStart = progress(raise, 0.0F, 0.5F);
        float raiseMiddle = progress(raise, 0.5F, 0.8F);
        float raiseEnd = progress(raise, 0.8F, 1.0F);
        float sway = progress(ticks, dismountStart, knockbackStart);
        float lower = outCubic(inOutElastic(progress(ticks - 20.0F, knockbackStart, knockbackEnd)));
        float raiseBack = progress(ticks, damageEnd - 5.0F, damageEnd);
        float intensity = 2.0F * outCirc(sway) - 2.0F * inCirc(raiseBack);
        float swayScaleSlow = Mth.sin(ticks * 19.0F * DEG) * intensity;
        float swayScaleFast = Mth.sin(ticks * 30.0F * DEG) * intensity;
        return new Values(raise, raiseStart, raiseMiddle, raiseEnd, sway, lower, raiseBack,
                intensity, swayScaleSlow, swayScaleFast);
    }

    public static float progress(float value, float start, float end) {
        return Mth.clamp(Mth.inverseLerp(value, start, end), 0.0F, 1.0F);
    }

    public static float inQuad(float t) {
        return t * t;
    }

    public static float inCirc(float t) {
        return (float) (-Math.sqrt(1.0F - t * t)) + 1.0F;
    }

    public static float outCirc(float t) {
        return (float) Math.sqrt(1.0F - (t - 1.0F) * (t - 1.0F));
    }

    public static float outCubic(float t) {
        return 1.0F - (1.0F - t) * (1.0F - t) * (1.0F - t);
    }

    public static float outBack(float t) {
        return 1.0F + 2.70158F * (t - 1.0F) * (t - 1.0F) * (t - 1.0F)
                + 1.70158F * (t - 1.0F) * (t - 1.0F);
    }

    public static float inOutSine(float t) {
        return -(Mth.cos((float) Math.PI * t) - 1.0F) / 2.0F;
    }

    public static float inOutExpo(float t) {
        if (t < 0.5F) {
            return t == 0.0F ? 0.0F : (float) (Math.pow(2.0D, 20.0D * t - 10.0D) / 2.0D);
        }
        return t == 1.0F ? 1.0F : (float) ((2.0D - Math.pow(2.0D, -20.0D * t + 10.0D)) / 2.0D);
    }

    public static float inOutBack(float t) {
        if (t < 0.5F) {
            return 4.0F * t * t * (7.189819F * t - 2.5949094F) / 2.0F;
        }
        float u = 2.0F * t - 2.0F;
        return (u * u * (3.5949094F * u + 2.5949094F) + 2.0F) / 2.0F;
    }

    public static float inOutElastic(float t) {
        if (t == 0.0F) {
            return 0.0F;
        }
        if (t == 1.0F) {
            return 1.0F;
        }
        double sine = Math.sin((20.0D * t - 11.125D) * (float) Math.PI * 4.0F / 9.0F);
        return t < 0.5F
                ? (float) (-(Math.pow(2.0D, 20.0D * t - 10.0D) * sine) / 2.0D)
                : (float) (Math.pow(2.0D, -20.0D * t + 10.0D) * sine / 2.0D + 1.0D);
    }

    public static float outQuart(float t) {
        float u = 1.0F - t;
        return 1.0F - u * u * u * u;
    }

    public static float hitFeedbackAmount(float ticks) {
        return 0.4F * (outQuart(progress(ticks, 1.0F, 3.0F))
                - inOutSine(progress(ticks, 3.0F, 10.0F)));
    }

    public record Values(float raiseProgress, float raiseProgressStart, float raiseProgressMiddle,
            float raiseProgressEnd, float swayProgress, float lowerProgress, float raiseBackProgress,
            float swayIntensity, float swayScaleSlow, float swayScaleFast) {
    }
}
