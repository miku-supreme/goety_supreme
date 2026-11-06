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
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithBossBarOverlay;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class GreenSmithBossBarOverlay {

    private static final ResourceLocation FRAME =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/gui/green_smith_bar_frame.png");
    private static final ResourceLocation FILL =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/gui/green_smith_bar_fill.png");

    private static final int FRAME_W = 200;
    private static final int FRAME_H = 16;
    private static final int FILL_OFF_X = 5;
    private static final int FILL_OFF_Y = 4;
    private static final int FILL_W = 190;
    private static final int FILL_H = 8;

    private static final int NAME_COLOR = 16777215;
    private static final int NAME_COLOR_PHASE2 = 0x9BFF6B;

    private static final float BLOCKED_R = 0.34F;
    private static final float BLOCKED_G = 0.38F;
    private static final float BLOCKED_B = 0.32F;

    private GreenSmithBossBarOverlay() {
    }

    @SubscribeEvent
    public static void onBossBarProgress(CustomizeGuiOverlayEvent.BossEventProgress event) {
        LerpingBossEvent boss = event.getBossEvent();
        GreenSmith entity = findGreenSmith(boss.getId());
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
        boolean phase2 = entity.isSecondPhase();

        if (filled > 0) {
            int fillX = x + FILL_OFF_X;
            int fillY = y + FILL_OFF_Y;
            int antiTicks = entity.getAntiHealTicks();
            if (antiTicks > 0) {
                tint(BLOCKED_R, BLOCKED_G, BLOCKED_B, 1.0F);
                blitFill(graphics, fillX, fillY, filled);
                float total = Math.max(1.0F, entity.getAntiHealTotal());
                float smite = 1.0F - antiTicks / total;
                int regen = Math.round(smite * filled);
                if (regen > 0) {
                    tint(1.0F, 1.0F, 1.0F, 1.0F);
                    blitFill(graphics, fillX, fillY, regen);
                }
            } else {
                tint(1.0F, 1.0F, 1.0F, 1.0F);
                blitFill(graphics, fillX, fillY, filled);
            }

            tint(1.0F, 1.0F, 1.0F, 1.0F);
            int span = 34;
            int travel = (int) ((entity.tickCount + event.getPartialTick()) * 2.0F) % (filled + span);
            int bandStart = fillX + travel - span;
            int bandEnd = Math.min(fillX + filled, bandStart + span);
            int bandBegin = Math.max(fillX, bandStart);
            if (bandEnd > bandBegin) {
                graphics.fill(bandBegin, fillY, bandEnd, fillY + FILL_H, 0x2EEFFFF0);
            }

            if (entity.hurtTime >= 5) {
                int shake = entity.getRandom().nextInt(entity.hurtTime);
                int jitter = entity.getRandom().nextInt(entity.hurtTime);
                int hx = Math.max(fillX, fillX + shake - 3);
                int hw = Math.min(filled, filled - jitter + 3);
                if (hw > 0) {
                    graphics.fill(hx, fillY, hx + hw, fillY + FILL_H, 0x4C101008);
                }
            }
        }

        if (phase2) {
            tint(0.62F, 1.0F, 0.62F, 1.0F);
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

    private static GreenSmith findGreenSmith(UUID bossId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return null;
        }
        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof GreenSmith smith && smith.getBossBarId().filter(bossId::equals).isPresent()) {
                return smith;
            }
        }
        return null;
    }
}
