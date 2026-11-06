package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.NecroBehaviorProbeEvents;

public final class NecroBehaviorProbe {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final String BASE_NAME = "goety_supreme-necro-behavior";
    private static final String FILE_NAME = BASE_NAME + ".log";
    private static final String ARCHIVE_SUFFIX = ".bak.log";
    private static final int ARCHIVE_KEEP = 5;

    private static final int PROBE_VERSION = 3;

    private static final int DEFAULT_INTERVAL_TICKS = 10;

    private static final double DEFAULT_RADIUS = 64.0D;
    private static final int RING_MAX = 300;
    private static final int LINE_CAP = 40000;

    private static final Deque<String> RING = new ArrayDeque<>();
    private static final Map<UUID, String> LAST_SIGNATURE = new HashMap<>();
    private static final Map<UUID, String> LAST_EXTRA = new HashMap<>();
    private static final Map<Class<?>, List<Field>> GOAL_SELECTOR_FIELDS = new HashMap<>();
    private static final Map<Class<?>, Map<String, Method>> METHOD_CACHE = new HashMap<>();
    private static final Object LOCK = new Object();
    private static final AtomicBoolean SHUTDOWN_HOOK = new AtomicBoolean();

    private static volatile String sessionId = newSessionId();
    private static final long SESSION_START_MILLIS = System.currentTimeMillis();

    private static volatile boolean headerWritten;

    private static volatile boolean enabled;
    private static volatile long deadlineMillis;
    private static volatile int written;
    private static volatile int intervalTicks = DEFAULT_INTERVAL_TICKS;
    private static volatile double radius = DEFAULT_RADIUS;

    private NecroBehaviorProbe() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static int written() {
        return written;
    }

    public static int intervalTicks() {
        return intervalTicks;
    }

    public static void setIntervalTicks(int value) {
        intervalTicks = Math.max(1, Math.min(200, value));
    }

    public static double radius() {
        return radius;
    }

    public static void setRadius(double value) {
        radius = Math.max(8.0D, Math.min(256.0D, value));
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
            synchronized (LOCK) {
                LAST_SIGNATURE.clear();
                LAST_EXTRA.clear();
            }
        }
        record("==== 死法行为检测" + (value ? "开启（最多 " + Math.max(1L, seconds) + " 秒）" : "关闭")
                + " interval=" + intervalTicks + "t radius=" + (int) radius + " ====");
    }

    public static void expireIfNeeded() {
        if (enabled && deadlineMillis > 0L && System.currentTimeMillis() > deadlineMillis) {
            enabled = false;
            record("==== 到时自动关闭 ====");
        }
    }

    public static void forget(UUID uuid) {
        synchronized (LOCK) {
            LAST_SIGNATURE.remove(uuid);
            LAST_EXTRA.remove(uuid);
        }
    }

    public static void record(String message) {
        String line = "[" + LocalTime.now().format(TIME) + "] " + message;
        synchronized (LOCK) {
            ensureSessionHeaderLocked();
            RING.addLast(line);
            while (RING.size() > RING_MAX) {
                RING.removeFirst();
            }
            written++;
            if (written >= LINE_CAP && enabled) {
                enabled = false;
                LOGGER.warn("[NecroProbe] 日志达到 {} 行，检测自动关闭。", LINE_CAP);
            }
        }
        LOGGER.debug("[NecroProbe] {}", message);
        appendLine(line);
    }

    private static void appendLine(String line) {
        Path path = logFile();
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.warn("[NecroProbe] 写日志失败：{}", e.toString());
        }
    }

    private static void ensureSessionHeaderLocked() {
        if (headerWritten) {
            return;
        }
        headerWritten = true;
        installShutdownHook();
        Path path = logFile();
        StringBuilder header = new StringBuilder("==== SESSION ").append(sessionId)
                .append(" START ").append(LocalDateTime.now().format(ISO))
                .append(" probe=").append(PROBE_VERSION)
                .append(" build=").append(modVersion())
                .append(" mc=1.20.1")
                .append(" goety=").append(goetyVersion())
                .append(" sid=").append(sessionId)
                .append(" jar=").append(jarLabel())
                .append(" ====");
        try {
            Files.createDirectories(path.getParent());
            Path archived = archivePrevious(path);
            if (archived != null) {
                header.append(System.lineSeparator())
                        .append("==== SESSION ").append(sessionId)
                        .append(" 上一局日志已归档为 logs/").append(archived.getFileName()).append(" ====");
            }
            Files.writeString(path, header + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            LOGGER.debug("[NecroProbe] {}", header.toString().replace(System.lineSeparator(), " | "));
        } catch (IOException e) {
            LOGGER.warn("[NecroProbe] 写会话头失败：{}", e.toString());
        }
    }

    @Nullable
    private static Path archivePrevious(Path path) throws IOException {
        if (!Files.exists(path) || Files.size(path) == 0L) {
            return null;
        }
        String stamp;
        try {
            stamp = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(),
                    ZoneId.systemDefault()).format(STAMP);
        } catch (IOException e) {
            stamp = LocalDateTime.now().format(STAMP);
        }
        Path dir = path.getParent();
        Path target = dir.resolve(BASE_NAME + "." + stamp + ARCHIVE_SUFFIX);
        for (int suffix = 2; Files.exists(target) && suffix < 100; suffix++) {
            target = dir.resolve(BASE_NAME + "." + stamp + "-" + suffix + ARCHIVE_SUFFIX);
        }
        Files.move(path, target, StandardCopyOption.REPLACE_EXISTING);
        pruneArchives(dir);
        return target;
    }

    private static void pruneArchives(Path dir) {
        List<Path> archives = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
                BASE_NAME + ".*" + ARCHIVE_SUFFIX)) {
            for (Path candidate : stream) {
                archives.add(candidate);
            }
        } catch (IOException ignored) {
            return;
        }
        if (archives.size() <= ARCHIVE_KEEP) {
            return;
        }
        archives.sort((a, b) -> b.getFileName().toString().compareTo(a.getFileName().toString()));
        for (int index = ARCHIVE_KEEP; index < archives.size(); index++) {
            try {
                Files.deleteIfExists(archives.get(index));
            } catch (IOException ignored) {
            }
        }
    }

    private static void installShutdownHook() {
        if (!SHUTDOWN_HOOK.compareAndSet(false, true)) {
            return;
        }
        try {
            Runtime.getRuntime().addShutdownHook(
                    new Thread(NecroBehaviorProbe::writeSessionEnd, "necro-probe-session-end"));
        } catch (Throwable ignored) {
        }
    }

    private static void writeSessionEnd() {
        if (!headerWritten) {
            return;
        }
        String line = "==== SESSION " + sessionId + " END "
                + LocalDateTime.now().format(ISO) + " lines=" + written + " ====";
        try {
            Path path = logFile();
            Files.createDirectories(path.getParent());
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Throwable ignored) {
        }
    }

    private static String newSessionId() {
        return LocalDateTime.now().format(STAMP) + "-"
                + String.format(Locale.ROOT, "%04x", ThreadLocalRandom.current().nextInt(0x10000));
    }

    private static String modVersion() {
        try {
            return ModList.get().getModContainerById(GeneratedMod.MOD_ID)
                    .map(container -> container.getModInfo().getVersion().toString())
                    .orElse("unknown");
        } catch (Throwable t) {
            return "unknown";
        }
    }

    private static String goetyVersion() {
        try {
            return ModList.get().getModContainerById("goety")
                    .map(container -> container.getModInfo().getVersion().toString())
                    .orElse("missing");
        } catch (Throwable t) {
            return "unknown";
        }
    }

    private static String jarLabel() {
        try {
            Path jar = ModList.get().getModContainerById(GeneratedMod.MOD_ID)
                    .map(container -> container.getModInfo().getOwningFile().getFile().getFilePath())
                    .orElse(null);
            if (jar == null) {
                return "unknown";
            }
            long size = Files.size(jar);
            String mtime = LocalDateTime.ofInstant(Files.getLastModifiedTime(jar).toInstant(),
                    ZoneId.systemDefault()).format(STAMP);
            return jar.getFileName() + ":" + size + ":" + mtime;
        } catch (Throwable t) {
            return "unknown";
        }
    }

    public static Path logFile() {
        return FMLPaths.GAMEDIR.get().resolve("logs").resolve(FILE_NAME);
    }

    public static String logPathText() {
        return logFile().toAbsolutePath().toString();
    }

    public static String sessionId() {
        return sessionId;
    }

    public static String sessionStartedAt() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(SESSION_START_MILLIS),
                ZoneId.systemDefault()).format(ISO);
    }

    public static String sessionSummary() {
        return "会话=" + sessionId + "，启动于 " + sessionStartedAt()
                + "，probe=" + PROBE_VERSION + "，build=" + modVersion()
                + "，goety=" + goetyVersion();
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
            LAST_SIGNATURE.clear();
            LAST_EXTRA.clear();
            written = 0;
            sessionId = newSessionId();
            headerWritten = false;
        }
        try {
            Files.deleteIfExists(logFile());
        } catch (IOException ignored) {
        }
    }

    public static void tickEntity(LivingEntity entity) {
        if (!enabled) {
            return;
        }
        if (!(entity instanceof Mob mob)) {
            return;
        }
        if (entity.level().isClientSide()) {
            return;
        }
        String typeName = typeName(entity);
        if (typeName == null) {
            return;
        }
        if (!entity.isAlive()) {
            forget(entity.getUUID());
            return;
        }
        if (!(entity.level() instanceof ServerLevel level)) {
            return;
        }
        double near = radius;
        if (level.getNearestPlayer(entity, near) == null) {
            return;
        }
        long gameTime = level.getGameTime();
        if (gameTime % intervalTicks != 0L) {
            String sig = signature(mob, entity);
            String prev;
            synchronized (LOCK) {
                prev = LAST_SIGNATURE.get(entity.getUUID());
            }
            if (sig.equals(prev)) {
                return;
            }
            sample(mob, entity, typeName, true, gameTime);
            return;
        }
        sample(mob, entity, typeName, false, gameTime);
    }

    public static int snapNow(ServerLevel level, net.minecraft.world.phys.AABB box) {
        int count = 0;
        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (!(living instanceof Mob mob)) {
                continue;
            }
            String typeName = typeName(living);
            if (typeName == null || !living.isAlive()) {
                continue;
            }
            sample(mob, living, typeName, true, level.getGameTime());
            count++;
        }
        return count;
    }

    public static List<String> describeNear(ServerLevel level, net.minecraft.world.phys.AABB box) {
        List<String> out = new ArrayList<>();
        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (typeName(living) == null || !living.isAlive()) {
                continue;
            }
            out.add(brief(living));
        }
        return out;
    }

    private static void sample(Mob mob, LivingEntity entity, String typeName, boolean changed,
                               long gameTime) {
        StringBuilder out = new StringBuilder(320);
        out.append("[NECRO] t=").append(gameTime)
                .append(" chg=").append(changed ? 1 : 0)
                .append(" kind=").append(typeName.contains("necromancer_servant") ? "servant" : "hostile")
                .append(" type=").append(fullTypeName(entity))
                .append(" eid=").append(entity.getId())
                .append(" pos=").append(fmt(entity.getX())).append(',')
                .append(fmt(entity.getY())).append(',')
                .append(fmt(entity.getZ()))
                .append(" hp=").append(fmt(entity.getHealth())).append('/').append(fmt(entity.getMaxHealth()));

        LivingEntity target = mob.getTarget();
        if (target == null) {
            out.append(" target=null dist=-1 los=-1");
        } else {
            out.append(" target=").append(target.getClass().getSimpleName()).append('#').append(target.getId());
            double dist = Math.sqrt(entity.distanceToSqr(target));
            out.append(" dist=").append(fmt(dist));
            out.append(" los=").append(entity.hasLineOfSight(target) ? 1 : 0);
        }
        out.append(" canAtk=").append(target == null ? -1 : (mob.canAttack(target) ? 1 : 0));

        out.append(" run=").append(runningGoals(mob));
        out.append(" tgt=").append(runningTargetGoals(mob));
        out.append(" goals=").append(goalCount(mob));

        PathNavigation nav = mob.getNavigation();
        net.minecraft.world.level.pathfinder.Path path = nav.getPath();
        out.append(" nav=").append(nav.isInProgress() ? 1 : 0);
        out.append(" path=").append(path == null ? -1 : path.getNodeCount());
        if (path != null) {
            BlockPos destination = path.getTarget();
            out.append(" dest=").append(destination.getX()).append(',')
                    .append(destination.getY()).append(',')
                    .append(destination.getZ());
        } else {
            BlockPos destination = nav.getTargetPos();
            out.append(" dest=").append(destination == null ? "none" : destination.toShortString());
        }
        out.append(" stuck=").append(nav.isStuck() ? 1 : 0);

        out.append(" yaw=").append(fmt(entity.getYRot()));
        out.append(" yhead=").append(fmt(entity.getYHeadRot()));
        out.append(" lookErr=").append(fmt(lookError(entity, target)));

        out.append(" spell=").append(probe(mob, "isSpellCasting"));
        out.append(" shoot=").append(probe(mob, "isShooting"));
        out.append(" atk=").append(probe(mob, "getAttackSpeed"));
        out.append(" cool=").append(probe(mob, "getSpellCooldown"));
        out.append(" cant=").append(probeField(mob, "cantDo"));
        out.append(" lvl=").append(probe(mob, "getNecroLevel"));
        out.append(" anim=").append(probe(mob, "getCurrentAnimation"));
        out.append(" summons=").append(summonCount(mob));
        out.append(" frange=").append(fmt(mob.getAttributeValue(
                net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE)));

        String line = out.toString();
        String sig = signature(mob, entity);
        String extra = "spell=" + probe(mob, "isSpellCasting");
        synchronized (LOCK) {
            LAST_SIGNATURE.put(entity.getUUID(), sig);
            LAST_EXTRA.put(entity.getUUID(), extra);
        }
        record(line);
    }

    private static String signature(Mob mob, LivingEntity entity) {
        LivingEntity target = mob.getTarget();
        PathNavigation nav = mob.getNavigation();
        double dist = target == null ? -1.0D : Math.sqrt(entity.distanceToSqr(target));
        return runningGoals(mob)
                + "|" + (target == null ? "null" : target.getId())
                + "|" + (int) dist
                + "|" + (nav.isInProgress() ? 1 : 0)
                + "|" + (nav.isStuck() ? 1 : 0)
                + "|" + probe(mob, "isSpellCasting")
                + "|" + probe(mob, "isShooting")
                + "|" + ((int) (entity.getYHeadRot() / 15.0F));
    }

    private static String runningGoals(Mob mob) {
        return runningGoals(goalSelectorOf(mob));
    }

    private static String runningTargetGoals(Mob mob) {
        return runningGoals(targetSelectorOf(mob));
    }

    private static String runningGoals(@Nullable GoalSelector selector) {
        if (selector == null) {
            return "[]";
        }
        StringBuilder out = new StringBuilder("[");
        boolean first = true;
        try {
            for (Object raw : selector.getAvailableGoals()) {
                if (!(raw instanceof WrappedGoal wrapped) || !wrapped.isRunning()) {
                    continue;
                }
                if (!first) {
                    out.append(',');
                }
                first = false;
                out.append(wrapped.getPriority()).append(':').append(shortGoalName(wrapped.getGoal()));
            }
        } catch (Throwable t) {
            return "[na]";
        }
        return out.append(']').toString();
    }

    private static int goalCount(Mob mob) {
        GoalSelector selector = goalSelectorOf(mob);
        if (selector == null) {
            return -1;
        }
        try {
            int count = 0;
            for (Object ignored : selector.getAvailableGoals()) {
                count++;
            }
            return count;
        } catch (Throwable t) {
            return -1;
        }
    }

    private static GoalSelector goalSelectorOf(Mob mob) {
        List<Field> fields = GOAL_SELECTOR_FIELDS.computeIfAbsent(mob.getClass(),
                NecroBehaviorProbe::findGoalSelectorFields);
        GoalSelector fallback = null;
        for (Field field : fields) {
            try {
                if (!(field.get(mob) instanceof GoalSelector selector)) {
                    continue;
                }
                if (fallback == null) {
                    fallback = selector;
                }
                for (Object raw : selector.getAvailableGoals()) {
                    if (raw instanceof WrappedGoal wrapped
                            && !(wrapped.getGoal() instanceof net.minecraft.world.entity.ai.goal.target.TargetGoal)) {
                        return selector;
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return fallback;
    }

    @Nullable
    private static GoalSelector targetSelectorOf(Mob mob) {
        List<Field> fields = GOAL_SELECTOR_FIELDS.computeIfAbsent(mob.getClass(),
                NecroBehaviorProbe::findGoalSelectorFields);
        for (Field field : fields) {
            try {
                if (!(field.get(mob) instanceof GoalSelector selector)) {
                    continue;
                }
                for (Object raw : selector.getAvailableGoals()) {
                    if (raw instanceof WrappedGoal wrapped
                            && wrapped.getGoal() instanceof net.minecraft.world.entity.ai.goal.target.TargetGoal) {
                        return selector;
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    private static List<Field> findGoalSelectorFields(Class<?> type) {
        List<Field> found = new ArrayList<>(2);
        for (Class<?> current = type; current != null && current != Object.class;
                current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (!GoalSelector.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    found.add(field);
                } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
                }
            }
            if (!found.isEmpty()) {
                break;
            }
        }
        return found;
    }

    private static String shortGoalName(Goal goal) {
        String name = goal.getClass().getSimpleName();
        return name.isEmpty() ? goal.getClass().getName() : name;
    }

    private static double lookError(LivingEntity entity, @Nullable LivingEntity target) {
        if (target == null) {
            return -1.0D;
        }
        double dx = target.getX() - entity.getX();
        double dz = target.getZ() - entity.getZ();
        if (dx == 0.0D && dz == 0.0D) {
            return 0.0D;
        }
        float bearing = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        return Math.abs(Mth.wrapDegrees(entity.getYHeadRot() - bearing));
    }

    public static boolean isTracked(net.minecraft.world.entity.Entity entity) {
        return entity instanceof LivingEntity living && typeName(living) != null;
    }

    public static String entityTag(net.minecraft.world.entity.Entity entity) {
        if (entity == null) {
            return "null";
        }
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return (id == null ? "unregistered" : id.toString()) + "#" + entity.getId();
    }

    @Nullable
    private static String typeName(LivingEntity entity) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (id == null) {
            return null;
        }
        String path = id.getPath();
        return path.contains("necromancer") ? path : null;
    }

    private static String fullTypeName(LivingEntity entity) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return id == null ? "unregistered" : id.toString();
    }

    private static String summonCount(Mob mob) {
        try {
            Method method = findMethod(mob.getClass(), "getSummonList");
            if (method == null) {
                return "na";
            }
            Object value = method.invoke(mob);
            return value instanceof List<?> list ? Integer.toString(list.size()) : "na";
        } catch (Throwable t) {
            return "na";
        }
    }

    private static String probe(Object target, String methodName) {
        try {
            Method method = findMethod(target.getClass(), methodName);
            if (method == null) {
                return "na";
            }
            Object value = method.invoke(target);
            if (value instanceof Boolean flag) {
                return flag ? "1" : "0";
            }
            if (value instanceof Float number) {
                return fmt(number);
            }
            if (value instanceof Double number) {
                return fmt(number);
            }
            if (value instanceof Number number) {
                return Integer.toString(number.intValue());
            }
            return value == null ? "null" : String.valueOf(value);
        } catch (Throwable t) {
            return "na";
        }
    }

    private static String probeField(Object target, String fieldName) {
        try {
            for (Class<?> current = target.getClass(); current != null && current != Object.class;
                    current = current.getSuperclass()) {
                for (Field field : current.getDeclaredFields()) {
                    if (!field.getName().equals(fieldName)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Object value = field.get(target);
                    return value == null ? "null"
                            : (value instanceof Number number ? Integer.toString(number.intValue())
                                    : String.valueOf(value));
                }
            }
            return "na";
        } catch (Throwable t) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", t.toString());
            return "na";
        }
    }

    @Nullable
    private static Method findMethod(Class<?> type, String name) {
        Map<String, Method> cache = METHOD_CACHE.computeIfAbsent(type, key -> new HashMap<>());
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        Method found = null;
        for (Class<?> current = type; current != null && current != Object.class;
                current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (!method.getName().equals(name) || method.getParameterCount() != 0) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
                    continue;
                }
                found = method;
                break;
            }
            if (found != null) {
                break;
            }
        }
        cache.put(name, found);
        return found;
    }

    public static String brief(LivingEntity entity) {
        if (!(entity instanceof Mob mob)) {
            return fullTypeName(entity) + " 不是 Mob";
        }
        LivingEntity target = mob.getTarget();
        return fullTypeName(entity) + "#" + entity.getId()
                + " hp=" + fmt(entity.getHealth()) + "/" + fmt(entity.getMaxHealth())
                + " target=" + (target == null ? "null"
                        : target.getClass().getSimpleName() + '#' + target.getId()
                                + " dist=" + fmt(Math.sqrt(entity.distanceToSqr(target))))
                + " run=" + runningGoals(mob)
                + " nav=" + (mob.getNavigation().isInProgress() ? 1 : 0)
                + " stuck=" + (mob.getNavigation().isStuck() ? 1 : 0)
                + " spell=" + probe(mob, "isSpellCasting")
                + " lookErr=" + fmt(lookError(entity, target));
    }

    public static void recordAt(ServerLevel level, Vec3 at, String message) {
        record(message);
        for (var player : level.players()) {
            if (player.distanceToSqr(at) <= 96.0D * 96.0D) {
                player.sendSystemMessage(Component.literal("[死法检测] " + message));
            }
        }
    }

    public static String fmt(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}
