package cn.blockforge.generated.goetysupreme.renderer;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBossBarOverlay;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class ReaperBossBarOverlay {

    private static final ResourceLocation VANILLA_BARS =
            ResourceLocation.parse("textures/gui/bars.png");

    private static final ResourceLocation REAPER_TRIM =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/gui/reaper_boss_bar_trim.png");

    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;

    private static final float PROGRESS_SCALE = 183.0F;

    private static final int NAME_COLOR = 16777215;

    private ReaperBossBarOverlay() {
    }

    @SubscribeEvent
    public static void onBossBarProgress(CustomizeGuiOverlayEvent.BossEventProgress event) {
        LerpingBossEvent boss = event.getBossEvent();
        if (!isReaperNecromancer(boss.getId())) {
            return;
        }
        event.setCanceled(true);

        GuiGraphics graphics = event.getGuiGraphics();
        Minecraft minecraft = Minecraft.getInstance();
        int x = event.getX();
        int y = event.getY();

        int backgroundV = boss.getColor().ordinal() * (BAR_HEIGHT * 2);
        int progressV = backgroundV + BAR_HEIGHT;
        int progressWidth = (int) (boss.getProgress() * PROGRESS_SCALE);

        graphics.blit(VANILLA_BARS, x, y, 0, backgroundV, BAR_WIDTH, BAR_HEIGHT);
        if (progressWidth > 0) {
            graphics.blit(VANILLA_BARS, x, y, 0, progressV, progressWidth, BAR_HEIGHT);
        }
        graphics.blit(REAPER_TRIM, x, y, 0, 0, BAR_WIDTH, BAR_HEIGHT);

        Component name = boss.getName();
        graphics.drawCenteredString(minecraft.font, name,
                graphics.guiWidth() / 2, y - 9, NAME_COLOR);
    }

    private static boolean isReaperNecromancer(UUID bossId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return false;
        }
        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof ReaperNecromancer reaper
                    && reaper.getBossBarId().filter(bossId::equals).isPresent()) {
                return true;
            }
        }
        return false;
    }
}
