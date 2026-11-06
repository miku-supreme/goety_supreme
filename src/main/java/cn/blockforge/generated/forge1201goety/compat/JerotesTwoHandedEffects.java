package cn.blockforge.generated.goetysupreme.compat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.compat.JerotesTwoHandedEffects;

public final class JerotesTwoHandedEffects {

    private static final Map<Class<?>, Method> CACHE = new HashMap<>();

    private JerotesTwoHandedEffects() {
    }

    public static void blockUse(Entity self, ItemStack weapon, Entity attacker, DamageSource source) {
        if (self == null || attacker == null || source == null || weapon == null || weapon.isEmpty()) {
            return;
        }
        Method method = methodFor(weapon.getItem().getClass());
        if (method == null) {
            return;
        }
        try {
            method.invoke(weapon.getItem(), self, attacker, source);
        } catch (Throwable ignored) {
        }
    }

    private static Method methodFor(Class<?> itemClass) {
        if (CACHE.containsKey(itemClass)) {
            return CACHE.get(itemClass);
        }
        Method found = null;
        try {
            found = itemClass.getMethod("blockUse", Entity.class, Entity.class, DamageSource.class);
        } catch (NoSuchMethodException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
        CACHE.put(itemClass, found);
        return found;
    }
}
