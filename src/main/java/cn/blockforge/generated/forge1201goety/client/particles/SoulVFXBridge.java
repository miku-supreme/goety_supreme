package cn.blockforge.generated.goetysupreme.client.particles;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Bridges the (possibly removed) Goety {@code SoulSummonVFX.emitter} call.
 *
 * <p>No Goety type may appear in this class: Goety is reachable only through
 * {@link Class#forName(String)} and {@link java.lang.reflect.Method#invoke}, so a Goety build that
 * dropped {@code SoulSummonVFX} cannot take the game down with {@code NoClassDefFoundError}.
 * When the reflection path fails we fall back to our own {@link SupremeSoulVFX} copy.</p>
 */
public final class SoulVFXBridge {

    private static final String VFX_CLASS = "com.Polarice3.Goety.client.particles.SoulSummonVFX";
    private static final String EMITTER_CLASS = "com.Polarice3.Goety.client.particles.SoulSummonVFX$Emitter";
    private static final String EMITTER_CONSTANT = "CUBE_SPAWN";

    private SoulVFXBridge() {
    }

    public static void cubeSpawn(Level level, Vec3 pos) {
        cubeSpawn(level, pos, false);
    }

    public static void cubeSpawn(Level level, Vec3 pos, boolean alternate) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }
        if (tryGoetyEmitter(server, pos, alternate)) {
            return;
        }
        SupremeSoulVFX.emitter(server, pos, SupremeSoulVFX.Emitter.CUBE_SPAWN, 0.0F, alternate);
    }

    private static boolean tryGoetyEmitter(ServerLevel level, Vec3 pos, boolean alternate) {
        try {
            Class<?> vfxClass = Class.forName(VFX_CLASS);
            Class<?> emitterClass = Class.forName(EMITTER_CLASS);
            Object emitter = null;
            for (Object constant : emitterClass.getEnumConstants()) {
                if (EMITTER_CONSTANT.equals(((Enum<?>) constant).name())) {
                    emitter = constant;
                    break;
                }
            }
            if (emitter == null) {
                return false;
            }
            try {
                vfxClass.getMethod("emitter", ServerLevel.class, Vec3.class, emitterClass, float.class, boolean.class)
                        .invoke(null, level, pos, emitter, 0.0F, alternate);
                return true;
            } catch (NoSuchMethodException no5) {
                try {
                    vfxClass.getMethod("emitter", ServerLevel.class, Vec3.class, emitterClass, float.class)
                            .invoke(null, level, pos, emitter, 0.0F);
                    return true;
                } catch (NoSuchMethodException no4) {
                    try {
                        vfxClass.getMethod("emitter", ServerLevel.class, Vec3.class, emitterClass)
                                .invoke(null, level, pos, emitter);
                        return true;
                    } catch (Throwable ignored) {
                        return false;
                    }
                } catch (Throwable ignored) {
                    return false;
                }
            } catch (Throwable ignored) {
                return false;
            }
        } catch (Throwable ignored) {
            return false;
        }
    }
}
