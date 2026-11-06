package cn.blockforge.generated.goetysupreme.util;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;
import cn.blockforge.generated.goetysupreme.entity.SkinProbePlayer;

public final class PlayerMobServantSkinLayers {

    private static final String CLASS_SKIN_UTIL = "dev.tr7zw.skinlayers.SkinUtil";
    private static final String CLASS_PLAYER_SETTINGS = "dev.tr7zw.skinlayers.accessor.PlayerSettings";
    private static final String CLASS_MODEL_PART_INJECTOR = "dev.tr7zw.skinlayers.accessor.ModelPartInjector";
    private static final String CLASS_MESH = "dev.tr7zw.skinlayers.api.Mesh";
    private static final String CLASS_OFFSET_PROVIDER = "dev.tr7zw.skinlayers.api.OffsetProvider";
    private static final String CLASS_MOD_BASE = "dev.tr7zw.skinlayers.SkinLayersModBase";
    private static final String CLASS_CONFIG = "dev.tr7zw.skinlayers.versionless.config.Config";

    private static final String CLASS_XAERO_TRACER =
            "xaero.hud.minimap.radar.icon.creator.render.trace.EntityRenderTracer";

    private static final int PROBE_CACHE_SIZE = 16;

    private static final UUID PROBE_UUID =
            UUID.nameUUIDFromBytes("goety_supreme:player_mob_servant_skin_probe"
                    .getBytes(StandardCharsets.UTF_8));
    private static final String PROBE_NAME = "PlayerMobServantProbe";

    private static boolean available;
    private static Method setup3dLayers;
    private static Method setInjectedMesh;
    private static Method getHeadMesh;
    private static Method getTorsoMesh;
    private static Method getLeftArmMesh;
    private static Method getRightArmMesh;
    private static Method getLeftLegMesh;
    private static Method getRightLegMesh;
    private static Object offsetHead;
    private static Object offsetBody;
    private static Object offsetLeftArm;
    private static Object offsetRightArm;
    private static Object offsetLeftArmSlim;
    private static Object offsetRightArmSlim;
    private static Object offsetLeftLeg;
    private static Object offsetRightLeg;

    private static Object modConfig;
    private static Field renderDistanceLod;

    private static Field xaeroTracingModelRenders;

    private static final Map<String, SkinProbePlayer> PROBES =
            new LinkedHashMap<>(PROBE_CACHE_SIZE, 0.75F, true) {
                @Override
                public boolean removeEldestEntry(Map.Entry<String, SkinProbePlayer> eldest) {
                    return size() > PROBE_CACHE_SIZE;
                }
            };

    static {
        try {
            Class<?> skinUtil = resolveClass(CLASS_SKIN_UTIL);
            Class<?> playerSettings = resolveClass(CLASS_PLAYER_SETTINGS);
            Class<?> injector = resolveClass(CLASS_MODEL_PART_INJECTOR);
            Class<?> mesh = resolveClass(CLASS_MESH);
            Class<?> offsetProvider = resolveClass(CLASS_OFFSET_PROVIDER);
            if (skinUtil != null && playerSettings != null && injector != null
                    && mesh != null && offsetProvider != null) {
                setup3dLayers = skinUtil.getMethod("setup3dLayers",
                        AbstractClientPlayer.class, playerSettings, boolean.class);
                setInjectedMesh = injector.getMethod("setInjectedMesh", mesh, offsetProvider);
                getHeadMesh = playerSettings.getMethod("getHeadMesh");
                getTorsoMesh = playerSettings.getMethod("getTorsoMesh");
                getLeftArmMesh = playerSettings.getMethod("getLeftArmMesh");
                getRightArmMesh = playerSettings.getMethod("getRightArmMesh");
                getLeftLegMesh = playerSettings.getMethod("getLeftLegMesh");
                getRightLegMesh = playerSettings.getMethod("getRightLegMesh");
                offsetHead = offsetProvider.getField("HEAD").get(null);
                offsetBody = offsetProvider.getField("BODY").get(null);
                offsetLeftArm = offsetProvider.getField("LEFT_ARM").get(null);
                offsetRightArm = offsetProvider.getField("RIGHT_ARM").get(null);
                offsetLeftArmSlim = offsetProvider.getField("LEFT_ARM_SLIM").get(null);
                offsetRightArmSlim = offsetProvider.getField("RIGHT_ARM_SLIM").get(null);
                offsetLeftLeg = offsetProvider.getField("LEFT_LEG").get(null);
                offsetRightLeg = offsetProvider.getField("RIGHT_LEG").get(null);
                try {
                    Class<?> modBase = resolveClass(CLASS_MOD_BASE);
                    Class<?> configClass = resolveClass(CLASS_CONFIG);
                    if (modBase != null && configClass != null) {
                        modConfig = modBase.getField("config").get(null);
                        renderDistanceLod = configClass.getField("renderDistanceLOD");
                    }
                } catch (Throwable ignored) {
                    modConfig = null;
                    renderDistanceLod = null;
                }
                try {
                    Class<?> xaeroTracer = resolveClass(CLASS_XAERO_TRACER);
                    if (xaeroTracer != null) {
                        Field field = xaeroTracer.getDeclaredField("TRACING_MODEL_RENDERS");
                        field.setAccessible(true);
                        xaeroTracingModelRenders = field;
                    }
                } catch (Throwable ignored) {
                    xaeroTracingModelRenders = null;
                }
                available = true;
            }
        } catch (Throwable ignored) {
            available = false;
        }
    }

    private PlayerMobServantSkinLayers() {
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void apply(PlayerModel<?> model, PlayerMobServant servant, ResourceLocation skin,
            boolean slim) {
        apply(model, servant, skin, slim, false);
    }

    public static void apply(PlayerModel<?> model, PlayerMobServant servant, ResourceLocation skin,
            boolean slim, boolean dedicatedModel) {
        if (!available || skin == null) {
            return;
        }
        if (!dedicatedModel && isPreviewLike(servant)) {
            return;
        }
        try {
            clearAll(model);

            if (servant.isDeadOrDying() || servant.deathTime > 0) {
                return;
            }

            if (xaeroTracingModelRenders()) {
                return;
            }

            if (beyondRenderDistance(servant)) {
                return;
            }

            SkinProbePlayer probe = probeFor(servant, skin, slim);
            if (probe == null) {
                return;
            }
            Object built = setup3dLayers.invoke(null, probe, probe, slim);
            if (!Boolean.TRUE.equals(built)) {
                return;
            }
            inject(model.hat, getHeadMesh.invoke(probe), offsetHead);
            inject(model.jacket, getTorsoMesh.invoke(probe), offsetBody);
            inject(model.leftSleeve, getLeftArmMesh.invoke(probe),
                    slim ? offsetLeftArmSlim : offsetLeftArm);
            inject(model.rightSleeve, getRightArmMesh.invoke(probe),
                    slim ? offsetRightArmSlim : offsetRightArm);
            inject(model.leftPants, getLeftLegMesh.invoke(probe), offsetLeftLeg);
            inject(model.rightPants, getRightLegMesh.invoke(probe), offsetRightLeg);
        } catch (Throwable ignored) {
        }
    }

    public static void clear(PlayerModel<?> model) {
        if (!available) {
            return;
        }
        try {
            clearAll(model);
        } catch (Throwable ignored) {
        }
    }

    private static void clearAll(PlayerModel<?> model) throws ReflectiveOperationException {
        clearInjected(model.hat);
        clearInjected(model.jacket);
        clearInjected(model.leftSleeve);
        clearInjected(model.rightSleeve);
        clearInjected(model.leftPants);
        clearInjected(model.rightPants);
    }

    private static boolean xaeroTracingModelRenders() {
        Field field = xaeroTracingModelRenders;
        if (field == null) {
            return false;
        }
        try {
            return field.getBoolean(null);
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static SkinProbePlayer probeFor(PlayerMobServant servant, ResourceLocation skin,
            boolean slim) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        String key = skin + (slim ? "#slim" : "#classic");
        SkinProbePlayer probe = PROBES.get(key);
        if (probe == null || probe.level() != level) {
            probe = new SkinProbePlayer(level, new GameProfile(PROBE_UUID, PROBE_NAME));
            probe.setSkinTexture(skin);
            PROBES.put(key, probe);
        }
        if (!isPreviewLike(servant)) {
            probe.setPos(servant.getX(), servant.getY(), servant.getZ());
        } else {
            Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            probe.setPos(camera.x, camera.y, camera.z);
        }
        return probe;
    }

    private static void clearInjected(ModelPart part) throws ReflectiveOperationException {
        setInjectedMesh.invoke(part, null, null);
    }

    private static void inject(ModelPart part, Object mesh, Object offset)
            throws ReflectiveOperationException {
        if (mesh == null || offset == null) {
            return;
        }
        setInjectedMesh.invoke(part, mesh, offset);
    }

    private static boolean beyondRenderDistance(PlayerMobServant servant) {
        if (modConfig == null || renderDistanceLod == null) {
            return false;
        }
        if (isPreviewLike(servant)) {
            return false;
        }
        try {
            int lod = renderDistanceLod.getInt(modConfig);
            if (lod <= 0) {
                return false;
            }
            Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            return servant.distanceToSqr(camera) > (double) lod * (double) lod;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static boolean isInWorld(PlayerMobServant servant) {
        Level level = servant.level();
        return level != null && level.getEntity(servant.getId()) == servant;
    }

    public static boolean isPreviewLike(PlayerMobServant servant) {
        return !isInWorld(servant) || Minecraft.getInstance().screen != null;
    }

    private static Class<?> resolveClass(String name) {
        try {
            return Class.forName(name, false, PlayerMobServantSkinLayers.class.getClassLoader());
        } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
        AtomicReference<Class<?>> found = new AtomicReference<>();
        ModList.get().forEachModContainer((id, container) -> {
            if (found.get() != null) {
                return;
            }
            try {
                Object mod = container.getMod();
                if (mod != null) {
                    found.set(Class.forName(name, false, mod.getClass().getClassLoader()));
                }
            } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            }
        });
        return found.get();
    }
}
