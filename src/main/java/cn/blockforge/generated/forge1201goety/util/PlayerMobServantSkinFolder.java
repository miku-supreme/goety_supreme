package cn.blockforge.generated.goetysupreme.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.debug.PlayerMobServantSkinCommand;
import cn.blockforge.generated.goetysupreme.renderer.PlayerMobServantRenderer;
import cn.blockforge.generated.goetysupreme.event.PlayerMobServantSkinEvents;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinFolder;

@OnlyIn(Dist.CLIENT)
public final class PlayerMobServantSkinFolder {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String FOLDER_NAME = "player_mob_servant_skins";

    private static final String NAMESPACE = "player_mob_servant_skins";

    private static final long RESCAN_INTERVAL_MILLIS = 2000L;

    private static final String DEFAULT_KEY = "default";

    private static final Map<String, ResourceLocation> BY_NAME = new ConcurrentHashMap<>();

    private static final Map<String, Long> STAMPS = new ConcurrentHashMap<>();

    private static final Set<ResourceLocation> REGISTERED = ConcurrentHashMap.newKeySet();

    private static volatile long lastScanMillis;

    private static volatile boolean autoScanDone;

    private PlayerMobServantSkinFolder() {
    }

    public static Path folder() {
        return FMLPaths.CONFIGDIR.get().resolve(FOLDER_NAME);
    }

    public static void ensureFolder() {
        Path dir = folder();
        try {
            Files.createDirectories(dir);
            Path readme = dir.resolve("README.txt");
            if (!Files.exists(readme)) {
                Files.writeString(readme, readmeText());
            }
        } catch (IOException e) {
            LOGGER.warn("[goety_supreme] 创建玩家生物仆从皮肤文件夹失败：{}（{}）", dir, e.toString());
        }
    }

    public static void openFolder() {
        ensureFolder();
        Path dir = folder();
        try {
            Util.getPlatform().openFile(dir.toFile());
        } catch (Throwable t) {
            LOGGER.warn("[goety_supreme] 打开玩家生物仆从皮肤文件夹失败：{}（{}）", dir, t.toString());
        }
    }

    public static ResourceLocation textureFor(String name) {
        String key = name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
        if (key.isEmpty()) {
            return null;
        }
        refresh(false);
        return BY_NAME.get(key);
    }

    public static ResourceLocation defaultTexture() {
        refresh(false);
        return BY_NAME.get(DEFAULT_KEY);
    }

    public static int skinCount() {
        refresh(false);
        return BY_NAME.size();
    }

    public static List<String> skinNames() {
        refresh(false);
        List<String> names = new ArrayList<>(BY_NAME.keySet());
        names.sort(Comparator.naturalOrder());
        return names;
    }

    public static void forceRescan() {
        refresh(true);
    }

    public static void autoScan() {
        if (autoScanDone) {
            return;
        }
        autoScanDone = true;
        refresh(true);
    }

    public static void resetAutoScanFlag() {
        autoScanDone = false;
    }

    private static String readmeText() {
        return """
                玩家生物仆从 · 皮肤文件夹
                ================================

                把 PNG 皮肤直接扔进这个文件夹就能用，不需要做资源包、也不用重启游戏：
                每次进游戏（启动客户端）时会自动扫描一次这个文件夹；
                之后再改图 / 加图 / 删图，敲 /servant_skins rescan 立刻重扫即可。

                命名规则
                --------
                文件名（去掉 .png）就是仆从名，要和配置里的名单对得上：
                    mireyni.png        -> 名字为 mireyni 的仆从
                    baicha_yoyo.png    -> 名字为 baicha_yoyo 的仆从
                    default.png        -> 名字没对上时的兜底皮肤（可选）

                名单在 config/goety_supreme/servants.toml 的 "Mob Names" 里。
                游戏里敲指令 /servant_skins 就能直接打开这个文件夹
                （/servant_skins rescan 立刻重扫，/servant_skins path 只看路径）。

                贴图规格
                --------
                与原版玩家皮肤一致：64x64（细手臂也支持 64x32 的旧格式）。
                第二层（帽子 / 外套 / 袖子 / 裤腿）照常画在同一张图上即可。

                优先级
                ------
                主人皮肤（copy_owner_skin 打开时）> 本文件夹里的对应名字 > 本文件夹的 default.png
                > 资源包里的皮肤 > 配置项 skin 指定的默认皮肤。
                """;
    }

    private static void refresh(boolean force) {
        long now = System.currentTimeMillis();
        long sinceLastScan = now - lastScanMillis;
        if (!force && lastScanMillis != 0L && sinceLastScan >= 0L
                && sinceLastScan < RESCAN_INTERVAL_MILLIS) {
            return;
        }
        lastScanMillis = now;

        Path dir = folder();
        if (!Files.isDirectory(dir)) {
            ensureFolder();
        }
        if (!Files.isDirectory(dir) || Minecraft.getInstance().getTextureManager() == null) {
            return;
        }

        List<Path> files;
        try (Stream<Path> list = Files.list(dir)) {
            files = list.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".png"))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .toList();
        } catch (IOException e) {
            LOGGER.warn("[goety_supreme] 扫描玩家生物仆从皮肤文件夹失败：{}（{}）", dir, e.toString());
            return;
        }

        Map<String, ResourceLocation> found = new LinkedHashMap<>();
        for (Path file : files) {
            String fileName = file.getFileName().toString();
            String key = fileName.substring(0, fileName.length() - 4).toLowerCase(Locale.ROOT);
            if (!ResourceLocation.isValidPath(key)) {
                LOGGER.warn("[goety_supreme] 皮肤文件名 {} 不能当作仆从名（只能用小写字母、数字、_ . -），已跳过。",
                        fileName);
                continue;
            }
            long stamp;
            try {
                stamp = Files.getLastModifiedTime(file).toMillis();
            } catch (IOException e) {
                continue;
            }
            ResourceLocation cached = BY_NAME.get(key);
            if (cached != null && STAMPS.get(key) != null && STAMPS.get(key) == stamp) {
                found.put(key, cached);
                continue;
            }
            ResourceLocation loaded = load(file, key, stamp);
            if (loaded != null) {
                found.put(key, loaded);
            } else if (cached != null) {
                found.put(key, cached);
            }
        }

        TextureManager textures = Minecraft.getInstance().getTextureManager();
        for (Map.Entry<String, ResourceLocation> entry : BY_NAME.entrySet()) {
            if (found.containsKey(entry.getKey())) {
                continue;
            }
            STAMPS.remove(entry.getKey());
            if (REGISTERED.remove(entry.getValue())) {
                textures.release(entry.getValue());
            }
            LOGGER.info("[goety_supreme] 外置皮肤 {} 已移除。", entry.getKey());
        }

        BY_NAME.clear();
        BY_NAME.putAll(found);
    }

    private static ResourceLocation load(Path file, String key, long stamp) {
        NativeImage image;
        try (InputStream in = Files.newInputStream(file)) {
            image = NativeImage.read(in);
        } catch (IOException e) {
            LOGGER.warn("[goety_supreme] 读取皮肤 {} 失败：{}", file, e.toString());
            return null;
        }

        ResourceLocation location =
                ResourceLocation.fromNamespaceAndPath(NAMESPACE, key + "_" + stamp);
        ResourceLocation previous = BY_NAME.get(key);
        TextureManager textures = Minecraft.getInstance().getTextureManager();
        try {
            DynamicTexture texture = new DynamicTexture(image);
            texture.setFilter(false, false);
            if (previous != null && REGISTERED.remove(previous)) {
                textures.release(previous);
            }
            if (REGISTERED.remove(location)) {
                textures.release(location);
            }
            textures.register(location, texture);
            REGISTERED.add(location);
            STAMPS.put(key, stamp);
            LOGGER.info("[goety_supreme] 已从外置文件夹载入皮肤 {}（{}x{}）-> {}",
                    file.getFileName(), image.getWidth(), image.getHeight(), location);
            return location;
        } catch (Throwable t) {
            image.close();
            LOGGER.warn("[goety_supreme] 注册皮肤 {} 失败：{}", file, t.toString());
            return null;
        }
    }
}
