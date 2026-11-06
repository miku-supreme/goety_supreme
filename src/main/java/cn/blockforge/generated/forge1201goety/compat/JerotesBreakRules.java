package cn.blockforge.generated.goetysupreme.compat;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;

public final class JerotesBreakRules {

    private static final String RULES_CLASS = "com.jerotes.jerotes.init.JerotesGameRules";

    private static final Object LOCK = new Object();

    private static volatile boolean resolved;
    private static final Map<Rule, GameRules.Key<GameRules.BooleanValue>> KEYS =
            new EnumMap<>(Rule.class);

    private JerotesBreakRules() {
    }

    private enum Rule {
        SHIELD("JEROTES_SHIELD_CAN_BREAK"),
        ARMOR("JEROTES_ARMOR_CAN_BREAK"),
        MELEE("JEROTES_MELEE_CAN_BREAK"),
        RANGE("JEROTES_RANGE_CAN_BREAK"),
        MAGIC("JEROTES_MAGIC_CAN_BREAK");

        private final String field;

        Rule(String field) {
            this.field = field;
        }
    }

    public static boolean meleeCanBreak(Level level) {
        return get(level, Rule.MELEE);
    }

    public static boolean rangeCanBreak(Level level) {
        return get(level, Rule.RANGE);
    }

    public static boolean magicCanBreak(Level level) {
        return get(level, Rule.MAGIC);
    }

    public static boolean shieldCanBreak(Level level) {
        return get(level, Rule.SHIELD);
    }

    public static boolean armorCanBreak(Level level) {
        return get(level, Rule.ARMOR);
    }

    private static boolean get(Level level, Rule rule) {
        if (level == null) {
            return false;
        }
        GameRules.Key<GameRules.BooleanValue> key = key(rule);
        return key != null && level.getGameRules().getBoolean(key);
    }

    @SuppressWarnings("unchecked")
    private static GameRules.Key<GameRules.BooleanValue> key(Rule rule) {
        if (!resolved) {
            synchronized (LOCK) {
                if (!resolved) {
                    resolve();
                    resolved = true;
                }
            }
        }
        return KEYS.get(rule);
    }

    private static void resolve() {
        try {
            Class<?> clazz = Class.forName(RULES_CLASS);
            for (Rule rule : Rule.values()) {
                try {
                    Field field = clazz.getField(rule.field);
                    Object value = field.get(null);
                    if (value instanceof GameRules.Key<?> key) {
                        KEYS.put(rule, (GameRules.Key<GameRules.BooleanValue>) key);
                    }
                } catch (ReflectiveOperationException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
                }
            }
        } catch (ClassNotFoundException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
    }
}
