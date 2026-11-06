package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.util.AnnihilationDamage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class AnnihilationProbe {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final String FILE_NAME = "goety_supreme-annihilation.log";

    private static final int RING_MAX = 200;

    private static final int LINE_CAP = 20000;

    private static final Deque<String> RING = new ArrayDeque<>();
    private static final Map<UUID, String> GATE_SNAPSHOT = new HashMap<>();
    private static final Map<Class<?>, Boolean> GATE_CLASS_CACHE = new HashMap<>();
    private static final Object LOCK = new Object();

    private static final String[] GATE_FIELDS = {
            "moddedInvul", "obsidianInvul", "hitTimes", "antiRegen", "antiRegenTotal",
            "deathTime", "coolDown", "monolithCoolDown", "toTeleportTime"};

    private static final String[] GATE_METHODS = {
            "isSettingUpSecond", "isSecondPhase", "isMonolithPower", "monolithWeakened",
            "getHitTimes", "hitTimeTeleport", "getCoolDown", "coolDownLimit",
            "isCasting", "isInNether", "isSmited", "getMonolithCoolDown"};

    private static volatile boolean enabled;
    private static volatile long deadlineMillis;
    private static volatile int written;

    private AnnihilationProbe() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static int written() {
        return written;
    }

    public static long remainingSeconds() {
        if (!enabled) {
            return 0L;
        }
        return Math.max(0L, (deadlineMillis - System.currentTimeMillis()) / 1000L);
    }

    public static void setEnabled(boolean value, long seconds) {
        enabled = value;
        deadlineMillis = value ? System.currentTimeMillis() + Math.max(1L, seconds) * 1000L : 0L;
        if (value) {
            written = 0;
        }
        record(value ? "==== 检测开启（最多 " + Math.max(1L, seconds) + " 秒）===="
                : "==== 检测关闭 ====");
    }

    public static void expireIfNeeded() {
        if (enabled && deadlineMillis > 0L && System.currentTimeMillis() > deadlineMillis) {
            enabled = false;
            record("==== 到时自动关闭 ====");
        }
    }

    public static void record(String message) {
        String line = "[" + LocalTime.now().format(TIME) + "] " + message;
        synchronized (LOCK) {
            RING.addLast(line);
            while (RING.size() > RING_MAX) {
                RING.removeFirst();
            }
            written++;
            if (written >= LINE_CAP && enabled) {
                enabled = false;
                LOGGER.warn("[AnnihilationProbe] 日志达到 {} 行，检测自动关闭。", LINE_CAP);
            }
        }
        LOGGER.debug("[AnnihilationProbe] {}", message);
        Path path = logFile();
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.warn("[AnnihilationProbe] 写日志失败：{}", e.toString());
        }
    }

    public static void recordAt(Level level, Vec3 at, String message) {
        record(message);
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : serverLevel.players()) {
                if (player.distanceToSqr(at) <= 96.0D * 96.0D) {
                    player.sendSystemMessage(Component.literal("[灭绝检测] " + message));
                }
            }
        }
    }

    public static Path logFile() {
        return FMLPaths.GAMEDIR.get().resolve("logs").resolve(FILE_NAME);
    }

    public static String logPathText() {
        return logFile().toAbsolutePath().toString();
    }

    public static List<String> tail(int count) {
        synchronized (LOCK) {
            List<String> all = new ArrayList<>(RING);
            int from = Math.max(0, all.size() - count);
            return List.copyOf(all.subList(from, all.size()));
        }
    }

    public static void clearLog() {
        synchronized (LOCK) {
            RING.clear();
            GATE_SNAPSHOT.clear();
            written = 0;
        }
        try {
            Files.deleteIfExists(logFile());
        } catch (IOException ignored) {
        }
    }

    public static String entity(@Nullable LivingEntity entity) {
        if (entity == null) {
            return "null";
        }
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return (id == null ? "unregistered" : id.toString())
                + "#" + entity.getId()
                + "(hp=" + trim(entity.getHealth()) + "/" + trim(entity.getMaxHealth())
                + ",cls=" + entity.getClass().getSimpleName() + ")";
    }

    public static String state(LivingEntity entity) {
        return "alive=" + entity.isAlive()
                + " invulnerable=" + entity.isInvulnerable()
                + " invulTime=" + entity.invulnerableTime;
    }

    public static String damageType(@Nullable DamageSource source) {
        if (source == null) {
            return "source=null";
        }
        String type;
        try {
            type = source.typeHolder().unwrapKey().map(key -> key.location().toString()).orElse("unregistered");
        } catch (Throwable t) {
            type = "?";
        }
        return "type=" + type + " msg=" + source.getMsgId()
                + " tag[invuln=" + safely(source, DamageTypeTags.BYPASSES_INVULNERABILITY)
                + " armor=" + safely(source, DamageTypeTags.BYPASSES_ARMOR)
                + " cooldown=" + safely(source, DamageTypeTags.BYPASSES_COOLDOWN)
                + " shield=" + safely(source, DamageTypeTags.BYPASSES_SHIELD) + "]";
    }

    private static boolean safely(DamageSource source, net.minecraft.tags.TagKey<DamageType> tag) {
        try {
            return source.is(tag);
        } catch (Throwable t) {
            return false;
        }
    }

    public static String chain(@Nullable DamageSource source) {
        if (source == null) {
            return "direct=null cause=null";
        }
        return "direct=" + name(source.getDirectEntity()) + " cause=" + name(source.getEntity());
    }

    private static String name(@Nullable Entity entity) {
        if (entity == null) {
            return "null";
        }
        return entity.getClass().getSimpleName() + "#" + entity.getId();
    }

    public static String trim(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }

    public static boolean hasGoetyGates(@Nullable LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        return GATE_CLASS_CACHE.computeIfAbsent(entity.getClass(),
                cls -> readField(entity, "moddedInvul") != null || readField(entity, "obsidianInvul") != null);
    }

    public static String goetyGates(@Nullable LivingEntity entity) {
        if (entity == null || !hasGoetyGates(entity)) {
            return "非Goety免伤实体";
        }
        List<String> parts = new ArrayList<>();
        for (String field : GATE_FIELDS) {
            Object value = readField(entity, field);
            if (value != null) {
                parts.add(field + "=" + value);
            }
        }
        for (String method : GATE_METHODS) {
            Object value = invoke(entity, method);
            if (value != null) {
                parts.add(method + "()=" + value);
            }
        }
        return parts.isEmpty() ? "（反射未读到任何字段）" : String.join(" ", parts);
    }

    public static void watchGates(LivingEntity entity) {
        if (!hasGoetyGates(entity) || !nearWitherServant(entity)) {
            return;
        }
        String snapshot = goetyGates(entity);
        String previous = GATE_SNAPSHOT.put(entity.getUUID(), snapshot);
        if (!snapshot.equals(previous)) {
            record("[GATE t=" + entity.tickCount + "] " + entity(entity) + " " + snapshot);
        }
    }

    public static void forget(UUID id) {
        GATE_SNAPSHOT.remove(id);
    }

    private static Object readField(Object target, String name) {
        Class<?> cls = target.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException missing) {
                cls = cls.getSuperclass();
            } catch (Throwable t) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", t.toString());
                return null;
            }
        }
        return null;
    }

    private static Object invoke(Object target, String name) {
        Class<?> cls = target.getClass();
        while (cls != null) {
            try {
                Method method = cls.getDeclaredMethod(name);
                method.setAccessible(true);
                return method.invoke(target);
            } catch (NoSuchMethodException missing) {
                cls = cls.getSuperclass();
            } catch (Throwable t) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", t.toString());
                return null;
            }
        }
        return null;
    }

    public static String goetyConfig() {
        List<String> parts = new ArrayList<>();
        addConfig(parts, "com.Polarice3.Goety.config.AttributesConfig", "ApostleDamageCap");
        addConfig(parts, "com.Polarice3.Goety.config.MobsConfig", "BossInvulnerabilityTime");
        addConfig(parts, "com.Polarice3.Goety.config.MobsConfig", "ApostleNetherDamageReduction");
        addConfig(parts, "com.Polarice3.Goety.config.MobsConfig", "ApostleDelayedTeleport");
        return parts.isEmpty() ? "（未读到 Goety 配置）" : String.join(" ", parts);
    }

    private static void addConfig(List<String> out, String className, String fieldName) {
        Object value = staticConfig(className, fieldName);
        if (value != null) {
            out.add(fieldName + "=" + value);
        }
    }

    private static Object staticConfig(String className, String fieldName) {
        try {
            Class<?> cls = Class.forName(className);
            Field field = cls.getField(fieldName);
            Object spec = field.get(null);
            if (spec == null) {
                return null;
            }
            Method get = spec.getClass().getMethod("get");
            return get.invoke(spec);
        } catch (Throwable t) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", t.toString());
            return null;
        }
    }

    public static boolean isRelevant(LivingEntity victim, @Nullable DamageSource source) {
        if (hasGoetyGates(victim)) {
            return true;
        }
        if (source != null) {
            Optional<ResourceKey<DamageType>> key = source.typeHolder().unwrapKey();
            if (key.isPresent() && GeneratedMod.MOD_ID.equals(key.get().location().getNamespace())) {
                return true;
            }
            if (source.getEntity() instanceof WitherServant || source.getDirectEntity() instanceof WitherServant) {
                return true;
            }
        }
        return nearWitherServant(victim);
    }

    public static boolean nearWitherServant(LivingEntity entity) {
        if (!(entity.level() instanceof ServerLevel level)) {
            return false;
        }
        return !level.getEntitiesOfClass(WitherServant.class,
                entity.getBoundingBox().inflate(48.0D)).isEmpty();
    }
}
