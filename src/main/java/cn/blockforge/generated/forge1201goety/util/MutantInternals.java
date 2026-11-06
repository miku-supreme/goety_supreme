package cn.blockforge.generated.goetysupreme.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Mob;
import cn.blockforge.generated.goetysupreme.util.MutantInternals;

public final class MutantInternals {

    public static final int TICK_UNAVAILABLE = Integer.MIN_VALUE;

    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();
    private static final Map<Class<?>, Field> RESURRECTION_LIST_FIELDS = new ConcurrentHashMap<>();
    private static final Set<Class<?>> RESURRECTION_LIST_MISSING = ConcurrentHashMap.newKeySet();
    private static final Map<Class<?>, Field> TICK_FIELDS = new ConcurrentHashMap<>();
    private static final Set<Class<?>> TICK_FIELDS_MISSING = ConcurrentHashMap.newKeySet();

    private MutantInternals() {
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static List<Object> resurrectionListOf(Mob zombie) {
        Class<?> start = zombie.getClass();
        while (start != null && !start.getName().startsWith("fuzs.")) {
            start = start.getSuperclass();
        }
        if (start == null) {
            return null;
        }
        Field field = RESURRECTION_LIST_FIELDS.get(start);
        if (field == null) {
            if (RESURRECTION_LIST_MISSING.contains(start)) {
                return null;
            }
            field = findResurrectionListField(start);
            if (field == null) {
                RESURRECTION_LIST_MISSING.add(start);
                warnOnce(start, "no 'resurrectionList' field");
                return null;
            }
            RESURRECTION_LIST_FIELDS.put(start, field);
        }
        try {
            return (List<Object>) field.get(zombie);
        } catch (ReflectiveOperationException | RuntimeException e) {
            return null;
        }
    }

    public static int decrementResurrectionTick(Object resurrection) {
        Class<?> type = resurrection.getClass();
        Field field = TICK_FIELDS.get(type);
        if (field == null) {
            if (TICK_FIELDS_MISSING.contains(type)) {
                return TICK_UNAVAILABLE;
            }
            field = findTickField(type);
            if (field == null) {
                TICK_FIELDS_MISSING.add(type);
                warnOnce(type, "no int 'tick' field");
                return TICK_UNAVAILABLE;
            }
            TICK_FIELDS.put(type, field);
        }
        try {
            int value = field.getInt(resurrection) - 1;
            field.setInt(resurrection, value);
            return value;
        } catch (ReflectiveOperationException | RuntimeException e) {
            return TICK_UNAVAILABLE;
        }
    }

    private static Field findResurrectionListField(Class<?> zombieType) {
        for (Class<?> c = zombieType; isInMutantMod(c); c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && List.class.isAssignableFrom(f.getType())
                        && f.getName().equals("resurrectionList")) {
                    return open(f);
                }
            }
            for (Field f : c.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()) || !List.class.isAssignableFrom(f.getType())) {
                    continue;
                }
                Type generic = f.getGenericType();
                if (generic.toString().contains("Resurrection")) {
                    return open(f);
                }
            }
        }
        return null;
    }

    private static Field findTickField(Class<?> type) {
        for (Class<?> c = type; isInMutantMod(c); c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && f.getType() == int.class
                        && f.getName().equals("tick")) {
                    return open(f);
                }
            }
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && f.getType() == int.class
                        && f.getName().toLowerCase().contains("tick")) {
                    return open(f);
                }
            }
        }
        return null;
    }

    private static boolean isInMutantMod(@Nullable Class<?> c) {
        if (c == null || c == Object.class) {
            return false;
        }
        String name = c.getName();
        return !name.startsWith("net.minecraft.") && !name.startsWith("java.") && !name.startsWith("jdk.");
    }

    private static Field open(Field f) {
        f.setAccessible(true);
        return f;
    }

    private static void warnOnce(Class<?> type, String what) {
        LOGGER.warn("[goety_supreme] Mutant Monsters class {} is not in the expected shape ({}); "
                        + "provided by {}. The dependent compatibility feature is disabled until the "
                        + "conflicting mod is removed or updated.",
                type.getName(), what, codeSource(type));
    }

    private static String codeSource(Class<?> type) {
        try {
            return String.valueOf(type.getProtectionDomain().getCodeSource().getLocation());
        } catch (RuntimeException e) {
            return "unknown";
        }
    }
}
