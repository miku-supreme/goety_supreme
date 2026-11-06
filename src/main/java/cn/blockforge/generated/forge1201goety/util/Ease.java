package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.util.Ease;

public final class Ease {

    private Ease() {
    }

    public static float inQuad(float f) {
        return f * f;
    }

    public static float inCirc(float f) {
        return (float) (-Math.sqrt(1.0F - f * f)) + 1.0F;
    }

    public static float outCirc(float f) {
        return (float) Math.sqrt(1.0F - Mth.square(f - 1.0F));
    }

    public static float outCubic(float f) {
        return 1.0F - cube(1.0F - f);
    }

    public static float inOutSine(float f) {
        return -(Mth.cos((float) Math.PI * f) - 1.0F) / 2.0F;
    }

    public static float inOutExpo(float f) {
        if (f < 0.5F) {
            return f == 0.0F ? 0.0F : (float) (Math.pow(2.0D, 20.0D * f - 10.0D) / 2.0D);
        }
        return f == 1.0F ? 1.0F : (float) ((2.0D - Math.pow(2.0D, -20.0D * f + 10.0D)) / 2.0D);
    }

    public static float inOutElastic(float f) {
        if (f == 0.0F) {
            return 0.0F;
        }
        if (f == 1.0F) {
            return 1.0F;
        }
        double d = Math.sin((20.0D * f - 11.125D) * 1.3962634801864624D);
        if (f < 0.5F) {
            return (float) (-(Math.pow(2.0D, 20.0D * f - 10.0D) * d) / 2.0D);
        }
        return (float) (Math.pow(2.0D, -20.0D * f + 10.0D) * d / 2.0D + 1.0D);
    }

    private static float cube(float f) {
        return f * f * f;
    }
}
