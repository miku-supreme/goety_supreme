package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.items.magic.IWand;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;

public final class GoetyStaffGuard {

    public static boolean isGoetyStaff(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.getItem() instanceof IWand;
    }

    private GoetyStaffGuard() {
    }
}
