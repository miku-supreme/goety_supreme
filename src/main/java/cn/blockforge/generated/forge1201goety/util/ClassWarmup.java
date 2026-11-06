package cn.blockforge.generated.goetysupreme.util;

import com.mojang.logging.LogUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.config.PerformanceConfig;
import cn.blockforge.generated.goetysupreme.util.ClassWarmup;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class ClassWarmup {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final long MENU_BUDGET_NANOS = 40_000_000L;

    private static final long LOADING_BUDGET_NANOS = 15_000_000L;

    private static final int JARS_PER_STEP = 8;

    private static final String MIXIN_PACKAGE_PREFIX = "cn.blockforge.generated.goetysupreme.mixin.";

    private static final List<String> BUILTIN_MODS = List.of(
            "goety", "goety_supreme", "jerotes", "jerotesvillage",
            "mutantmonsters", "mutantmore", "cataclysm", "citadel");

    private static final String[] TRUSTED_PREFIXES = {
            "java.", "javax.", "jdk.", "sun.", "com.sun.",
            "net.minecraft.", "net.minecraftforge.", "com.mojang.",
            "cpw.mods.", "org.slf4j.", "org.apache.", "com.google.",
            "it.unimi.dsi.", "org.jetbrains.", "org.objectweb.",
            "io.netty.", "org.lwjgl.", "oshi.", "com.electronwill.",
            "org.spongepowered.", "net.fabricmc.", "org.joml.",
    };

    private static final Set<String> KNOWN_CLASSES = new HashSet<>();

    private static final Map<String, Boolean> LOADABLE = new HashMap<>();

    private static int skippedByDeps;

    private static int phase;

    private static boolean configured;

    private static Set<String> requestIds = Set.of();
    private static final List<Batch> BATCHES = new ArrayList<>();
    private static int planCursor;
    private static int batchIndex;
    private static int nameIndex;
    private static int totalNames;
    private static int loadedNames;
    private static int failedNames;
    private static long beginMillis;

    private ClassWarmup() {
    }

    private record Batch(ClassLoader loader, String[] names) {
    }

    public static void tick(boolean gameplayStarted, boolean levelLoaded) {
        if (phase == 3) {
            return;
        }
        if (!configured && !readConfig()) {
            return; // 配置还没就绪：下一Tick再试
        }
        if (phase == 3) {
            return; // 配置里关掉了
        }
        if (gameplayStarted) {
            stopForGameplay();
            return;
        }
        if (phase == 0) {
            return; // 关掉了
        }
        final long budget = levelLoaded ? LOADING_BUDGET_NANOS : MENU_BUDGET_NANOS;
        long tickStart = System.nanoTime();
        try {
            if (phase == 1 && !buildPlan(tickStart, budget)) {
                return;
            }
            loadSome(tickStart, budget);
        } catch (Throwable failure) {
            phase = 3;
            LOGGER.warn("[goety_supreme] 类预热提前结束：{}", failure.toString());
        }
    }

    private static void stopForGameplay() {
        phase = 3;
        long elapsed = beginMillis == 0L ? 0L : System.currentTimeMillis() - beginMillis;
        LOGGER.info("[goety_supreme] 已进入游戏，类预热停手：成功 {} / 共 {} 个类，用时 {}ms",
                loadedNames, totalNames, elapsed);
    }

    private static boolean readConfig() {
        boolean enabled;
        List<? extends String> extra;
        boolean goetyFamily;
        try {
            enabled = PerformanceConfig.CLASS_WARMUP_ENABLED.get();
            goetyFamily = PerformanceConfig.CLASS_WARMUP_GOETY_FAMILY.get();
            extra = PerformanceConfig.CLASS_WARMUP_MODS.get();
        } catch (Throwable notLoadedYet) {
            return false;
        }
        configured = true;
        if (!enabled) {
            phase = 3;
            return true;
        }
        requestIds = collectModIds(extra, goetyFamily);
        phase = 1;
        planCursor = 0;
        beginMillis = System.currentTimeMillis();
        LOGGER.info("[goety_supreme] 菜单期类预热开始，目标模组：{}", requestIds);
        return true;
    }

    private static Set<String> collectModIds(List<? extends String> configured, boolean goetyFamily) {
        Set<String> ids = new LinkedHashSet<>();
        ids.add(GeneratedMod.MOD_ID.toLowerCase(Locale.ROOT));
        ids.addAll(BUILTIN_MODS);
        if (configured != null) {
            for (String id : configured) {
                if (id != null && !id.isBlank()) {
                    ids.add(id.trim().toLowerCase(Locale.ROOT));
                }
            }
        }
        if (goetyFamily) {
            try {
                for (IModInfo info : ModList.get().getMods()) {
                    String id = info.getModId();
                    if (id == null) {
                        continue;
                    }
                    String lower = id.toLowerCase(Locale.ROOT);
                    if (lower.contains("goety") || lower.contains("goetic")) {
                        ids.add(lower);
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return ids;
    }

    private static boolean buildPlan(long tickStart, long budget) {
        List<String> ids = new ArrayList<>(requestIds);
        int done = 0;
        while (planCursor < ids.size() && done < JARS_PER_STEP) {
            String modId = ids.get(planCursor);
            ++planCursor;
            ++done;
            Batch batch = readMod(modId);
            if (batch != null && batch.names().length > 0) {
                BATCHES.add(batch);
                totalNames += batch.names().length;
            }
            if (System.nanoTime() - tickStart >= budget) {
                return false;
            }
        }
        if (planCursor < ids.size()) {
            return false;
        }
        phase = 2;
        LOGGER.info("[goety_supreme] 类预热计划就绪：{} 个 jar、{} 个类", BATCHES.size(), totalNames);
        return true;
    }

    private static Batch readMod(String modId) {
        try {
            ModContainer container = ModList.get().getModContainerById(modId).orElse(null);
            if (container == null) {
                return null;
            }
            Path path = container.getModInfo().getOwningFile().getFile().getFilePath();
            if (path == null || !Files.exists(path)) {
                return null;
            }
            List<String> names = new ArrayList<>(2048);
            if (Files.isDirectory(path)) {
                try (Stream<Path> walk = Files.walk(path)) {
                    walk.filter(entry -> entry.toString().endsWith(".class"))
                            .forEach(entry -> addName(names, binaryName(path.relativize(entry))));
                }
            } else {
                try (ZipFile zip = new ZipFile(path.toFile())) {
                    Enumeration<? extends ZipEntry> entries = zip.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        if (entry.isDirectory()) {
                            continue;
                        }
                        String name = entry.getName();
                        if (!name.endsWith(".class")) {
                            continue;
                        }
                        addName(names, name.substring(0, name.length() - ".class".length()).replace('/', '.'));
                    }
                }
            }
            ClassLoader loader = null;
            try {
                Object mod = container.getMod();
                if (mod != null) {
                    loader = mod.getClass().getClassLoader();
                }
            } catch (Throwable ignored) {
            }
            KNOWN_CLASSES.addAll(names);
            return new Batch(loader, names.toArray(new String[0]));
        } catch (Throwable ignored) {
            return null; // 读不了这个模组就当它不存在
        }
    }

    private static String binaryName(Path relative) {
        String text = relative.toString().replace('\\', '/');
        return text.substring(0, text.length() - ".class".length()).replace('/', '.');
    }

    private static void addName(List<String> names, String binary) {
        if (binary.startsWith("META-INF.") || binary.startsWith(MIXIN_PACKAGE_PREFIX)
                || binary.endsWith("module-info") || binary.endsWith("package-info")) {
            return;
        }
        names.add(binary);
    }

    private static void loadSome(long tickStart, long budget) {
        while (batchIndex < BATCHES.size()) {
            Batch batch = BATCHES.get(batchIndex);
            ClassLoader loader = batch.loader() != null
                    ? batch.loader() : ClassWarmup.class.getClassLoader();
            String[] names = batch.names();
            while (nameIndex < names.length) {
                String name = names[nameIndex];
                ++nameIndex;
                if (!canLoad(name, loader)) {
                    ++skippedByDeps;
                } else {
                    try {
                        Class.forName(name, false, loader);
                        ++loadedNames;
                    } catch (Throwable ignored) {
                        ++failedNames;
                    }
                }
                if (System.nanoTime() - tickStart >= budget) {
                    return;
                }
            }
            nameIndex = 0;
            ++batchIndex;
        }
        phase = 3;
        long elapsed = System.currentTimeMillis() - beginMillis;
        LOGGER.info("[goety_supreme] 菜单期类预热结束：成功 {} / 失败 {} / 依赖缺失跳过 {} / 共 {} 个类，用时 {}ms",
                loadedNames, failedNames, skippedByDeps, totalNames, elapsed);
    }

    private static boolean canLoad(String name, ClassLoader loader) {
        if (name == null || name.equals("java.lang.Object") || trusted(name)) {
            return true;
        }
        Boolean cached = LOADABLE.get(name);
        if (cached != null) {
            return cached;
        }
        LOADABLE.put(name, Boolean.TRUE);
        boolean result = true;
        byte[] bytes = readClassBytes(name, loader);
        if (bytes == null) {
            result = KNOWN_CLASSES.contains(name);
        } else {
            String[] related = parseDirectTypes(bytes);
            if (related != null) {
                for (String type : related) {
                    if (type != null && !canLoad(type, loader)) {
                        result = false;
                        break;
                    }
                }
            }
        }
        LOADABLE.put(name, result);
        return result;
    }

    private static boolean trusted(String name) {
        for (String prefix : TRUSTED_PREFIXES) {
            if (name.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] readClassBytes(String binaryName, ClassLoader loader) {
        ClassLoader effective = loader != null ? loader : ClassWarmup.class.getClassLoader();
        String path = binaryName.replace('.', '/') + ".class";
        try (InputStream in = effective.getResourceAsStream(path)) {
            return in == null ? null : in.readAllBytes();
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String[] parseDirectTypes(byte[] bytes) {
        try {
            if (bytes == null || bytes.length < 10
                    || bytes[0] != (byte) 0xCA || bytes[1] != (byte) 0xFE
                    || bytes[2] != (byte) 0xBA || bytes[3] != (byte) 0xBE) {
                return null;
            }
            int cursor = 8;
            int poolCount = u2(bytes, cursor);
            cursor += 2;
            String[] utf8 = new String[poolCount];
            int[] classRef = new int[poolCount];
            for (int i = 1; i < poolCount; i++) {
                int tag = bytes[cursor++] & 0xFF;
                switch (tag) {
                    case 1 -> { // Utf8
                        int length = u2(bytes, cursor);
                        cursor += 2;
                        utf8[i] = new String(bytes, cursor, length, StandardCharsets.UTF_8);
                        cursor += length;
                    }
                    case 7 -> { // Class：指向一个 Utf8 名字
                        classRef[i] = u2(bytes, cursor);
                        cursor += 2;
                    }
                    case 8, 16, 19, 20 -> cursor += 2; // String / MethodType / Module / Package
                    case 15 -> cursor += 3;            // MethodHandle
                    case 3, 4, 9, 10, 11, 12, 17, 18 -> cursor += 4;
                    case 5, 6 -> {                     // Long / Double 占两个槽位
                        cursor += 8;
                        i++;
                    }
                    default -> {
                        return null;
                    }
                }
            }
            cursor += 2; // access_flags
            cursor += 2; // this_class
            int superIndex = u2(bytes, cursor);
            cursor += 2;
            int interfaceCount = u2(bytes, cursor);
            cursor += 2;
            String[] result = new String[interfaceCount + 1];
            result[0] = poolTypeName(utf8, classRef, superIndex);
            for (int i = 0; i < interfaceCount; i++) {
                result[i + 1] = poolTypeName(utf8, classRef, u2(bytes, cursor));
                cursor += 2;
            }
            return result;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String poolTypeName(String[] utf8, int[] classRef, int classIndex) {
        if (classIndex <= 0 || classIndex >= classRef.length) {
            return null;
        }
        int nameIndex = classRef[classIndex];
        if (nameIndex <= 0 || nameIndex >= utf8.length) {
            return null;
        }
        String raw = utf8[nameIndex];
        return raw == null ? null : raw.replace('/', '.');
    }

    private static int u2(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 8) | (bytes[offset + 1] & 0xFF);
    }

}
