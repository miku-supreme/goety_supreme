package cn.blockforge.generated.goetysupreme.compat;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;

public final class SakuraGearCompat {

    public static final String SAKURA_MOD_ID = "sakura_sword";

    private static final String PACKAGE = "cn.blockforge.generated.sakurasword.";
    private static final Logger LOGGER = LoggerFactory.getLogger("goety_supreme/sakura");

    public static final int BOW_OVERCHARGE_TICKS = 30;

    public static final int TRIDENT_CHARGE_TICKS = 20;

    private static Boolean loaded = null;

    private static final Map<String, Class<?>> CLASSES = new ConcurrentHashMap<>();
    private static final Set<String> WARNED = ConcurrentHashMap.newKeySet();

    private SakuraGearCompat() {
    }

    public static boolean isLoaded() {
        if (loaded == null) {
            loaded = ModList.get().isLoaded(SAKURA_MOD_ID);
        }
        return loaded;
    }

    public static String path(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return key != null && SAKURA_MOD_ID.equals(key.getNamespace()) ? key.getPath() : null;
    }

    public static boolean isCrystalBow(ItemStack stack) {
        return "crystal_bow".equals(path(stack));
    }

    public static boolean isCrystalCrossbow(ItemStack stack) {
        return "crystal_crossbow".equals(path(stack));
    }

    public static boolean isCrystalTrident(ItemStack stack) {
        return "crystal_trident".equals(path(stack));
    }

    public static boolean isHeavyWeapon(ItemStack stack) {
        String path = path(stack);
        return "crystal_battleblade".equals(path) || "crystal_greatsword".equals(path)
                || "crystal_battleaxe".equals(path) || "crystal_greataxe".equals(path);
    }

    public static boolean isQiSword(ItemStack stack) {
        String path = path(stack);
        return "crystal_battleblade".equals(path) || "crystal_greatsword".equals(path);
    }

    public static boolean isHeavyEmpowerReady(LivingEntity entity, ItemStack stack) {
        String simple = heavyWeaponClass(stack);
        if (simple == null) {
            return false;
        }
        Object result = invokeStatic(simple, "isEmpowerReady",
                new Class<?>[] {LivingEntity.class, ItemStack.class}, entity, stack);
        return result instanceof Boolean ready && ready;
    }

    public static boolean tryEmpowerHeavy(LivingEntity entity, ItemStack stack) {
        String simple = heavyWeaponClass(stack);
        if (simple == null) {
            return false;
        }
        Object result = invokeStatic(simple, "tryEmpower",
                new Class<?>[] {LivingEntity.class, ItemStack.class}, entity, stack);
        return result instanceof Boolean fired && fired;
    }

    public static boolean isQiReady(LivingEntity entity) {
        Object result = invokeStatic("SakuraSwordQi", "isQiReady",
                new Class<?>[] {LivingEntity.class}, entity);
        return result instanceof Boolean ready && ready;
    }

    public static boolean tryFireQi(LivingEntity entity) {
        Object result = invokeStatic("SakuraSwordQi", "tryFireFor",
                new Class<?>[] {LivingEntity.class}, entity);
        return result instanceof Boolean fired && fired;
    }

    public static boolean shootCrystalCrossbow(LivingEntity user, InteractionHand hand, ItemStack stack) {
        if (!isLoaded() || !isCrystalCrossbow(stack)) {
            return false;
        }
        Class<?> type = sakuraClass("CrystalCrossbowItem");
        if (type == null) {
            return false;
        }
        try {
            type.getMethod("performCrystalShooting", Level.class, LivingEntity.class,
                    InteractionHand.class, ItemStack.class)
                    .invoke(stack.getItem(), user.level(), user, hand, stack);
            return true;
        } catch (Throwable throwable) {
            warnOnce("crossbow", "樱璃弩发射入口调用失败", throwable);
            return false;
        }
    }

    private static String heavyWeaponClass(ItemStack stack) {
        if (!isLoaded()) {
            return null;
        }
        return switch (path(stack)) {
            case "crystal_battleblade" -> "CrystalBattlebladeItem";
            case "crystal_greatsword" -> "CrystalGreatswordItem";
            case "crystal_battleaxe" -> "CrystalBattleaxeItem";
            case "crystal_greataxe" -> "CrystalGreataxeItem";
            default -> null;
        };
    }

    private static Object invokeStatic(String simple, String method, Class<?>[] signature, Object... args) {
        if (!isLoaded()) {
            return null;
        }
        Class<?> type = sakuraClass(simple);
        if (type == null) {
            return null;
        }
        try {
            return type.getMethod(method, signature).invoke(null, args);
        } catch (Throwable throwable) {
            warnOnce(simple + "#" + method, "樱璃触发入口调用失败", throwable);
            return null;
        }
    }

    private static Class<?> sakuraClass(String simple) {
        return CLASSES.computeIfAbsent(simple, name -> {
            String qualified = PACKAGE + name;
            try {
                return Class.forName(qualified);
            } catch (Throwable first) {
                ClassLoader context = Thread.currentThread().getContextClassLoader();
                if (context != null) {
                    try {
                        return Class.forName(qualified, false, context);
                    } catch (Throwable second) {
                        first.addSuppressed(second);
                    }
                }
                warnOnce(name, "找不到樱璃类 " + qualified, first);
                return null;
            }
        });
    }

    private static void warnOnce(String key, String message, Throwable throwable) {
        if (WARNED.add(key)) {
            LOGGER.warn("{}（{}）", message, throwable.toString());
        }
    }
}
