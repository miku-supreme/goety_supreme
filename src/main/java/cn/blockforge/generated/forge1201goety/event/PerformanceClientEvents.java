package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.util.ClassWarmup;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.PerformanceClientEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class PerformanceClientEvents {

    private static boolean gameplayStarted;

    private PerformanceClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        try {
            Minecraft minecraft = Minecraft.getInstance();
            if (!gameplayStarted && minecraft.player != null && minecraft.screen == null) {
                gameplayStarted = true;
            }
            if (Boolean.getBoolean("goety_supreme.classWarmup")) {
                ClassWarmup.tick(gameplayStarted, minecraft.level != null);
            }
        } catch (Throwable ignored) {
        }
    }
}
