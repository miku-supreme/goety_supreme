package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.mojang.logging.LogUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;

public final class SupremeChargeAura {

    public enum Theme {
        REDSTONE,
        WITHER,
        MAGMA
    }

    private static final int AIRBORNE_GRACE_TICKS = 20;

    private static final Logger LOGGER = LogUtils.getLogger();

    private static boolean reflectionReady;
    private static Constructor<?> settingsConstructor;
    private static Constructor<?> auraConstructor;
    private static Method tickMethod;
    private static boolean failureLogged;

    private final Theme theme;
    private Object aura;
    private int airborneTicks;
    private boolean suppressed;

    public SupremeChargeAura(Theme theme) {
        this.theme = theme;
        ensureReflection();
        if (!reflectionReady) {
            this.aura = null;
            return;
        }
        try {
            this.aura = auraConstructor.newInstance(this.buildSettings());
        } catch (Throwable throwable) {
            this.aura = null;
            reportFailure(throwable);
        }
    }

    public static SupremeChargeAura of(Theme theme) {
        return new SupremeChargeAura(theme);
    }

    public Object goetyAura() {
        return this.aura;
    }

    public boolean isSuppressed() {
        return this.suppressed || this.aura == null;
    }

    public void tick(LivingEntity entity, boolean active) {
        boolean flying = this.updateFlying(entity);
        this.suppressed = flying;
        if (this.aura == null) {
            return;
        }
        try {
            tickMethod.invoke(this.aura, entity, active && !flying);
        } catch (Throwable throwable) {
            this.aura = null;
            reportFailure(throwable);
        }
    }

    private boolean updateFlying(LivingEntity entity) {
        if (entity instanceof FlyingMob) {
            return true;
        }
        if (entity.onGround()) {
            this.airborneTicks = 0;
            return false;
        }
        this.airborneTicks++;
        return this.airborneTicks > AIRBORNE_GRACE_TICKS;
    }

    private Object buildSettings() throws Exception {
        return switch (this.theme) {
            case REDSTONE -> newSettings(1.0F, 0.60F, 0.13F, 0.03F, 0.60F, 0.01F, 0.0275F,
                    0xFF2D00, 0xD02000, ModParticleTypes.BIG_FIRE.get());
            case WITHER -> newSettings(0.8F, 0.35F, 0.45F, 0.02F, 0.60F, 0.65F, 0.75F,
                    0x9B4DFF, 0x6A2FB0, ModParticleTypes.END_FIRE.get());
            case MAGMA -> newSettings(0.45F, 0.65F, 0.90F, 0.30F, 0.60F, 0.02F, 0.05F,
                    0xFF8A00, 0xD25A00, ModParticleTypes.BIG_FIRE.get());
        };
    }

    private static Object newSettings(float scale, float tintRMin, float tintRMax, float tintGMin,
            float tintGMax, float tintBMin, float tintBMax, int colorA, int colorB,
            ParticleOptions flame) throws Exception {
        float size = Math.max(0.35F, scale);
        return settingsConstructor.newInstance(
                5.616F * size, 7.488F * size, 0.6F * size, 0.5F,
                tintRMin, tintRMax, tintGMin, tintGMax, tintBMin, tintBMax,
                0.3F, colorA, colorB, flame);
    }

    private static synchronized void ensureReflection() {
        if (reflectionReady || settingsConstructor != null) {
            return;
        }
        if (!GoetyCompat.isGoety60()) {
            return;
        }
        try {
            Class<?> settingsClass = Class.forName(
                    "com.Polarice3.Goety.common.entities.util.ChargeAura$Settings");
            Class<?> auraClass = Class.forName(
                    "com.Polarice3.Goety.common.entities.util.ChargeAura");
            settingsConstructor = settingsClass.getConstructor(
                    float.class, float.class, float.class, float.class,
                    float.class, float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    int.class, int.class, ParticleOptions.class);
            auraConstructor = auraClass.getConstructor(settingsClass);
            tickMethod = auraClass.getMethod("tick", LivingEntity.class, boolean.class);
            reflectionReady = true;
            LOGGER.info("[goety_supreme] 冲锋特效直接使用 Goety 原版 ChargeAura"
                    + "（ChargeAura + ChargeAura$Settings + ChargeAuraRenderer）");
        } catch (Throwable throwable) {
            reflectionReady = false;
            settingsConstructor = null;
            auraConstructor = null;
            tickMethod = null;
            reportFailure(throwable);
        }
    }

    public static synchronized void reportFailure(Throwable throwable) {
        if (failureLogged) {
            return;
        }
        failureLogged = true;
        LOGGER.error("[goety_supreme] 无法调用 Goety 原版 ChargeAura（2.5.60.0），"
                + "冲锋特效将不显示", throwable);
    }
}
