package cn.blockforge.generated.goetysupreme.compat;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;

public final class OminousCompat {

    public static final String[] NAMESPACES = {"goetyominous", "goetyominus", "someillagerservants"};

    private static final String[] MARKER_PATHS = {
            "sunken_necromancer_servant", "storm_necromancer_servant", "sunken_necromancer",
            "storm_necromancer", "spider_focus", "urbhadhach_focus", "crimson_spider_servant"};

    private static Boolean loaded;
    private static String namespace;
    private static final Map<String, ResourceLocation> ENTITY_CACHE = new HashMap<>();
    private static final Map<String, ResourceLocation> ITEM_CACHE = new HashMap<>();

    private OminousCompat() {
    }

    public static String namespace() {
        if (namespace == null) {
            namespace = NAMESPACES[0];
            for (String modId : NAMESPACES) {
                if (ModList.get().isLoaded(modId)) {
                    namespace = modId;
                    break;
                }
            }
        }
        return namespace;
    }

    public static boolean loaded() {
        if (loaded == null) {
            loaded = detect();
        }
        return loaded;
    }

    private static boolean detect() {
        for (String modId : NAMESPACES) {
            if (ModList.get().isLoaded(modId)) {
                return true;
            }
        }
        for (ResourceLocation key : ForgeRegistries.ENTITY_TYPES.getKeys()) {
            if (isMarker(key.getPath())) {
                return true;
            }
        }
        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            if (isMarker(key.getPath())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMarker(String path) {
        for (String marker : MARKER_PATHS) {
            if (marker.equals(path)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOminousNamespace(String namespace) {
        for (String modId : NAMESPACES) {
            if (modId.equals(namespace)) {
                return true;
            }
        }
        return false;
    }

    public static ResourceLocation entity(String path) {
        ResourceLocation cached = ENTITY_CACHE.get(path);
        if (cached != null) {
            return cached;
        }
        ResourceLocation found = null;
        for (String namespace : NAMESPACES) {
            ResourceLocation candidate = ResourceLocation.fromNamespaceAndPath(namespace, path);
            if (ForgeRegistries.ENTITY_TYPES.containsKey(candidate)) {
                found = candidate;
                break;
            }
        }
        if (found == null) {
            for (ResourceLocation key : ForgeRegistries.ENTITY_TYPES.getKeys()) {
                if (key.getPath().equals(path)) {
                    found = key;
                    break;
                }
            }
        }
        if (found != null) {
            ENTITY_CACHE.put(path, found);
        }
        return found;
    }

    public static ResourceLocation item(String path) {
        ResourceLocation cached = ITEM_CACHE.get(path);
        if (cached != null) {
            return cached;
        }
        ResourceLocation found = null;
        for (String namespace : NAMESPACES) {
            ResourceLocation candidate = ResourceLocation.fromNamespaceAndPath(namespace, path);
            if (ForgeRegistries.ITEMS.containsKey(candidate)) {
                found = candidate;
                break;
            }
        }
        if (found == null) {
            for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
                if (key.getPath().equals(path)) {
                    found = key;
                    break;
                }
            }
        }
        if (found != null) {
            ITEM_CACHE.put(path, found);
        }
        return found;
    }

    public static boolean isOminousPath(String path) {
        return isMarker(path);
    }
}
