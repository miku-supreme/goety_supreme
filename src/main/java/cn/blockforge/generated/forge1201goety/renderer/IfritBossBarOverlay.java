package cn.blockforge.generated.goetysupreme.renderer;

import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithBossBarOverlay;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.renderer.IfritBossBarOverlay;
import cn.blockforge.generated.goetysupreme.renderer.WitherWarlordBossBarOverlay;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class IfritBossBarOverlay {

    private static final ResourceLocation FRAME =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/gui/ifrit_bar_frame.png");
    private static final ResourceLocation FILL =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/gui/ifrit_bar_fill.png");

    private static final int FRAME_W = 200;
    private static final int FRAME_H = 16;

    private static final int FILL_OFF_X = 5;
    private static final int FILL_OFF_Y = 6;
    private static final int FILL_W = 190;
    private static final int FILL_H = 5;

    private static final int TROUGH_TINT = 0xFF1B1009;

    private static final int NAME_COLOR = 16777215;
    private static final int NAME_COLOR_PHASE2 = 0xFFB060;

    private static final int HURT_TINT = 0x661A0600;

    private static final int GLOSS_TINT = 0x33FFE0B0;

    private static final float PHASE2_R = 1.0F;
    private static final float PHASE2_G = 0.72F;
    private static final float PHASE2_B = 0.6F;

    private IfritBossBarOverlay() {
    }

    @SubscribeEvent
    public static void onBossBarProgress(CustomizeGuiOverlayEvent.BossEventProgress event) {
        LerpingBossEvent boss = event.getBossEvent();
        Ifrit entity = findIfrit(boss.getId());
        if (entity == null) {
            return;
        }
        event.setCanceled(true);

        GuiGraphics graphics = event.getGuiGraphics();
        Minecraft minecraft = Minecraft.getInstance();
        int x = graphics.guiWidth() / 2 - FRAME_W / 2;
        int y = event.getY();
        float progress = Math.max(0.0F, Math.min(1.0F, boss.getProgress()));
        int filled = Math.round(progress * FILL_W);
        boolean phase2 = entity.isPhaseTwo();

        graphics.fill(x + FILL_OFF_X, y + FILL_OFF_Y,
                x + FILL_OFF_X + FILL_W, y + FILL_OFF_Y + FILL_H, TROUGH_TINT);

        if (filled > 0) {
            int fillX = x + FILL_OFF_X;
            int fillY = y + FILL_OFF_Y;
            tint(1.0F, 1.0F, 1.0F, 1.0F);
            blitFill(graphics, fillX, fillY, filled);

            int span = 34;
            int travel = (int) ((entity.tickCount + event.getPartialTick()) * 2.0F) % (filled + span);
            int bandStart = fillX + travel - span;
            int bandEnd = Math.min(fillX + filled, bandStart + span);
            int bandBegin = Math.max(fillX, bandStart);
            if (bandEnd > bandBegin) {
                graphics.fill(bandBegin, fillY, bandEnd, fillY + FILL_H, GLOSS_TINT);
            }

            if (entity.hurtTime >= 5) {
                int shake = entity.getRandom().nextInt(entity.hurtTime);
                int jitter = entity.getRandom().nextInt(entity.hurtTime);
                int hx = Math.max(fillX, fillX + shake - 3);
                int hw = Math.min(filled, filled - jitter + 3);
                if (hw > 0) {
                    graphics.fill(hx, fillY, hx + hw, fillY + FILL_H, HURT_TINT);
                }
            }
        }

        if (phase2) {
            tint(PHASE2_R, PHASE2_G, PHASE2_B, 1.0F);
        } else {
            tint(1.0F, 1.0F, 1.0F, 1.0F);
        }
        graphics.blit(FRAME, x, y, 0, 0.0F, 0.0F, FRAME_W, FRAME_H, FRAME_W, FRAME_H);
        tint(1.0F, 1.0F, 1.0F, 1.0F);

        Component name = boss.getName();
        graphics.drawCenteredString(minecraft.font, name, graphics.guiWidth() / 2, y - 9,
                phase2 ? NAME_COLOR_PHASE2 : NAME_COLOR);

        event.setIncrement(FRAME_H + 12);
    }

    private static void blitFill(GuiGraphics graphics, int x, int y, int width) {
        graphics.blit(FILL, x, y, 0, 0.0F, 0.0F, width, FILL_H, FILL_W, FILL_H);
    }

    private static void tint(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
    }

    private static Ifrit findIfrit(UUID bossId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return null;
        }
        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof Ifrit ifrit && !ifrit.isClone()
                    && ifrit.getBossBarId().filter(bossId::equals).isPresent()) {
                return ifrit;
            }
        }
        return null;
    }
}
