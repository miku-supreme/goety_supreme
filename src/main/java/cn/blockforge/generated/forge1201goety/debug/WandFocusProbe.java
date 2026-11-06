package cn.blockforge.generated.goetysupreme.debug;

import com.Polarice3.Goety.api.magic.ISpell;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;

public final class WandFocusProbe {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final String FILE_NAME = "goety_supreme-wand.log";

    private static final List<String> DEFAULT_WATCHED = List.of("ThunderRageSpell", "IceStormSpell");

    private static final int RING_MAX = 300;

    private static final int LINE_CAP = 20000;

    private static final long DEFAULT_SECONDS = 1800L;

    private static final Set<String> WATCHED = ConcurrentHashMap.newKeySet();
    private static final Deque<String> RING = new ArrayDeque<>();
    private static final Object LOCK = new Object();

    static {
        WATCHED.addAll(DEFAULT_WATCHED);
    }

    private static volatile boolean enabled = true;
    private static volatile long deadlineMillis = System.currentTimeMillis() + DEFAULT_SECONDS * 1000L;
    private static volatile int written;

    private WandFocusProbe() {
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
        write(value ? "==== 检测开启（最多 " + Math.max(1L, seconds) + " 秒，关注=" + watchedText() + "）===="
                : "==== 检测关闭 ====");
    }

    public static boolean isWatched(@Nullable ISpell spell) {
        return spell != null && isWatchedName(spell.getClass().getSimpleName());
    }

    public static boolean isWatchedName(@Nullable String spellName) {
        return spellName != null && !spellName.isBlank() && WATCHED.contains(spellName);
    }

    public static boolean watch(String spellName) {
        return spellName != null && !spellName.isBlank() && WATCHED.add(spellName.trim());
    }

    public static boolean unwatch(String spellName) {
        return spellName != null && !spellName.isBlank() && WATCHED.remove(spellName.trim());
    }

    public static String watchedText() {
        return WATCHED.isEmpty() ? "（无）" : String.join(", ", new java.util.TreeSet<>(WATCHED));
    }

    public static void record(String message) {
        expireIfNeeded();
        write(message);
    }

    private static void expireIfNeeded() {
        if (enabled && deadlineMillis > 0L && System.currentTimeMillis() > deadlineMillis) {
            enabled = false;
            write("==== 到时自动关闭 ====");
        }
    }

    private static void write(String message) {
        String line = "[" + LocalTime.now().format(TIME) + "] " + message;
        synchronized (LOCK) {
            RING.addLast(line);
            while (RING.size() > RING_MAX) {
                RING.removeFirst();
            }
            written++;
            if (written >= LINE_CAP && enabled) {
                enabled = false;
                LOGGER.warn("[WandFocusProbe] 日志达到 {} 行，检测自动关闭。", LINE_CAP);
            }
        }
        LOGGER.debug("[WandFocusProbe] {}", message);
        Path path = logFile();
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.warn("[WandFocusProbe] 写日志失败：{}", e.toString());
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
            written = 0;
        }
        try {
            Files.deleteIfExists(logFile());
        } catch (IOException ignored) {
        }
    }

    public static String entity(@Nullable Entity entity) {
        if (entity == null) {
            return "null";
        }
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        String name = id == null ? "unregistered" : id.toString();
        if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
            return name + "#" + entity.getId() + "(hp=" + trim(living.getHealth())
                    + "/" + trim(living.getMaxHealth()) + ")";
        }
        return name + "#" + entity.getId();
    }

    public static String trim(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}
