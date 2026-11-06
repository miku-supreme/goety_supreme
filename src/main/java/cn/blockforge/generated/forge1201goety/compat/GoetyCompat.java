package cn.blockforge.generated.goetysupreme.compat;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;

public final class GoetyCompat {

    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();

    private static final int[] V_FROST_PARTICLE = {2, 5, 59};
    private static final int[] V_SKULL_SET_DAMAGE = {2, 5, 59};
    private static final int[] V_SURGING_ORB_ORANGE = {2, 5, 59};
    private static final int[] V_CHARGE_AURA = {2, 5, 60};

    private static boolean resolved;
    private static String versionText = "unknown";
    private static int[] version = {0, 0, 0};

    private GoetyCompat() {
    }

    private static void resolve() {
        if (resolved) {
            return;
        }
        resolved = true;
        try {
            versionText = ModList.get().getModContainerById("goety")
                    .map(container -> container.getModInfo().getVersion().toString())
                    .orElse("missing");
            version = parse(versionText);
            LOGGER.info("[goety_supreme] 检测到 Goety {}（兼容门限：SMALL_FROST/skull.setDamage/setOrange ≥2.5.59，"
                    + "ChargeAura ≥2.5.60）", versionText);
        } catch (Throwable ignored) {
            versionText = "unknown";
            version = new int[]{0, 0, 0};
        }
    }

    private static int[] parse(String text) {
        int[] out = new int[]{0, 0, 0};
        if (text == null) {
            return out;
        }
        String[] parts = text.split("[.\\-+]");
        for (int i = 0; i < out.length && i < parts.length; i++) {
            StringBuilder digits = new StringBuilder();
            for (char c : parts[i].toCharArray()) {
                if (c >= '0' && c <= '9') {
                    digits.append(c);
                } else {
                    break;
                }
            }
            if (digits.length() == 0) {
                break;
            }
            out[i] = Integer.parseInt(digits.toString());
        }
        return out;
    }

    private static boolean atLeast(int[] required) {
        resolve();
        for (int i = 0; i < required.length; i++) {
            if (version[i] != required[i]) {
                return version[i] > required[i];
            }
        }
        return true;
    }

    public static String versionText() {
        resolve();
        return versionText;
    }

    public static boolean isGoety60() {
        return atLeast(V_CHARGE_AURA);
    }

    public static boolean hasSmallFrost() {
        return atLeast(V_FROST_PARTICLE);
    }

    public static boolean hasSkullSetDamage() {
        return atLeast(V_SKULL_SET_DAMAGE);
    }

    public static boolean hasSurgingOrbOrange() {
        return atLeast(V_SURGING_ORB_ORANGE);
    }

    public static ParticleOptions frostParticle() {
        return hasSmallFrost() ? ModParticleTypes.SMALL_FROST.get() : ParticleTypes.SNOWFLAKE;
    }
}
