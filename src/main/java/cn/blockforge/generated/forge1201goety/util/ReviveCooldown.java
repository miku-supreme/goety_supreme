package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;

public final class ReviveCooldown {

    public static final int REVIVE_TICKS = MathHelper.secondsToTicks(5);

    private ReviveCooldown() {
    }

    public static int deliverySeconds() {
        return ItemConfig.ReviveSecondsCool.get();
    }

    public static void grantOnDelivery(Player player, ItemStack stack) {
        if (player == null || player.level().isClientSide || stack.isEmpty()) {
            return;
        }
        int seconds = deliverySeconds();
        if (seconds <= 0) {
            return;
        }
        SEHelper.addSpecificCooldown(player, stack, MathHelper.secondsToTicks(seconds));
    }

    public static void grantAfterRevive(Player player, ItemStack stack) {
        if (player.level().isClientSide || stack.isEmpty()) {
            return;
        }
        SEHelper.addCooldown(player, stack.getItem(), REVIVE_TICKS);
    }

    public static boolean isOnCooldown(Player player, ItemStack stack) {
        return !stack.isEmpty() && SEHelper.isOnCooldown(player, stack);
    }
}
