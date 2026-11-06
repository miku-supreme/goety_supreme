package cn.blockforge.generated.goetysupreme.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.InfernalMaskClientTick;
import cn.blockforge.generated.goetysupreme.network.InfernalMaskNetwork;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class InfernalMaskClientTick {

    private static final int DOUBLE_TAP_TICKS = 7;
    private static boolean leftDown;
    private static boolean rightDown;
    private static int tick;
    private static int lastLeftTap;
    private static int lastRightTap;

    private InfernalMaskClientTick() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen != null) {
            return;
        }
        while (ClientModEvents.INFERNAL_MASK_SUMMON.consumeClick()) {
            InfernalMaskNetwork.send(0, 0);
        }
        ++tick;
        boolean left = minecraft.options.keyLeft.isDown();
        boolean right = minecraft.options.keyRight.isDown();
        if (left && !leftDown) {
            if (lastLeftTap > 0 && tick - lastLeftTap <= DOUBLE_TAP_TICKS) {
                com.mojang.logging.LogUtils.getLogger().info("[infernal_mask] double-tap LEFT detected");
                InfernalMaskNetwork.send(1, 0);
            }
            lastLeftTap = tick;
        }
        if (right && !rightDown) {
            if (lastRightTap > 0 && tick - lastRightTap <= DOUBLE_TAP_TICKS) {
                com.mojang.logging.LogUtils.getLogger().info("[infernal_mask] double-tap RIGHT detected");
                InfernalMaskNetwork.send(1, 1);
            }
            lastRightTap = tick;
        }
        leftDown = left;
        rightDown = right;
    }
}
