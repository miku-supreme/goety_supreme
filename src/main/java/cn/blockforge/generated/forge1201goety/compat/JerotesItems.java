package cn.blockforge.generated.goetysupreme.compat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.renderer.JerotesSpearAnimation;

public final class JerotesItems {

    private static final String TWO_HANDED = "com.jerotes.jerotes.item.Interface.ItemTwoHanded";
    private static final String DAGGER = "com.jerotes.jerotes.item.Tool.ItemToolBaseDagger";
    private static final String SPEAR = "com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase";
    private static final String PIKE = "com.jerotes.jerotes.item.Tool.ItemToolBasePike";

    private static final int TWO_HANDED_BIT = 1;
    private static final int DAGGER_BIT = 1 << 1;
    private static final int SPEAR_BIT = 1 << 2;
    private static final int PIKE_BIT = 1 << 3;

    private static final Map<Class<?>, Integer> CACHE = new ConcurrentHashMap<>();

    private JerotesItems() {
    }

    public static boolean isTwoHanded(ItemStack stack) {
        return (flags(stack) & TWO_HANDED_BIT) != 0;
    }

    public static boolean isDagger(ItemStack stack) {
        return (flags(stack) & DAGGER_BIT) != 0;
    }

    public static boolean isSpear(ItemStack stack) {
        return (flags(stack) & SPEAR_BIT) != 0;
    }

    public static boolean isPike(ItemStack stack) {
        return (flags(stack) & PIKE_BIT) != 0;
    }

    private static int flags(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return 0;
        }
        Item item = stack.getItem();
        if (item == null) {
            return 0;
        }
        return CACHE.computeIfAbsent(item.getClass(), JerotesItems::scan);
    }

    private static int scan(Class<?> type) {
        int flags = 0;
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            flags |= match(current.getName());
            for (Class<?> iface : current.getInterfaces()) {
                flags |= scanInterface(iface);
            }
        }
        return flags;
    }

    private static int scanInterface(Class<?> iface) {
        int flags = match(iface.getName());
        for (Class<?> parent : iface.getInterfaces()) {
            flags |= scanInterface(parent);
        }
        return flags;
    }

    private static int match(String className) {
        return switch (className) {
            case TWO_HANDED -> TWO_HANDED_BIT;
            case DAGGER -> DAGGER_BIT;
            case SPEAR -> SPEAR_BIT;
            case PIKE -> PIKE_BIT;
            default -> 0;
        };
    }
}
