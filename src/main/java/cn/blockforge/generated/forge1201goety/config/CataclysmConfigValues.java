package cn.blockforge.generated.goetysupreme.config;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmCindariaSpearDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrSoulArrowDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmProwlerMissileDamageMixin;

public final class CataclysmConfigValues {

    private static final String CONFIG_CLASS =
            "com.Polarice3.goety_cataclysm.config.GCAttributesConfig";

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();
    private static final Object MISSING = new Object();

    public static Double value(String fieldName) {
        Object resolved = CACHE.computeIfAbsent(fieldName, CataclysmConfigValues::resolve);
        if (!(resolved instanceof Field field)) {
            return null;
        }
        try {
            Object raw = field.get(null);
            if (raw instanceof ForgeConfigSpec.ConfigValue<?> configValue) {
                Object current = configValue.get();
                if (current instanceof Number number) {
                    return number.doubleValue();
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static Object resolve(String fieldName) {
        try {
            Class<?> config = Class.forName(CONFIG_CLASS);
            return config.getField(fieldName);
        } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            return MISSING;
        }
    }

    private CataclysmConfigValues() {
    }
}
