package cn.blockforge.generated.goetysupreme.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.PerformanceClientEvents;
import cn.blockforge.generated.goetysupreme.event.PlayerMobServantSkinEvents;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinFolder;
import cn.blockforge.generated.goetysupreme.event.WitherServantClientEvents;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class PlayerMobServantSkinEvents {

    private PlayerMobServantSkinEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        try {
            PlayerMobServantSkinFolder.autoScan();
        } catch (Throwable ignored) {
        }
    }
}
