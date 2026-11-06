package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.common.entities.hostile.Wight;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperDreadOverlay;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperFogClient;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SoulReaperDreadOverlay {

    public static final IGuiOverlay OVERLAY = SoulReaperDreadOverlay::drawOverlay;

    private static final ResourceLocation[] FRAMES = {
            texture("soul_dread_overlay_0"),
            texture("soul_dread_overlay_1"),
            texture("soul_dread_overlay_2"),
            texture("soul_dread_overlay_3"),
    };

    private static final double RANGE = 64.0D;

    private static final float FADE_DISTANCE = 48.0F;

    private SoulReaperDreadOverlay() {
    }

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("soul_reaper_dread", OVERLAY);
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/gui/soul_dread/" + name + ".png");
    }

    private static void drawOverlay(ForgeGui gui, GuiGraphics graphics, float partialTick,
            int screenWidth, int screenHeight) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.isCreative() || player.isSpectator()) {
            return;
        }
        double distance = nearestUnseen(player);
        if (distance < 0.0D) {
            return;
        }
        float alpha = 1.0F - Math.min(1.0F, (float) distance / FADE_DISTANCE);
        if (alpha <= 0.0F) {
            return;
        }
        ResourceLocation frame = FRAMES[(gui.getGuiTicks() / 4) % FRAMES.length];
        drawQuad(frame, alpha, screenWidth, screenHeight);
    }

    private static double nearestUnseen(LocalPlayer player) {
        double best = Double.MAX_VALUE;

        for (Wight wight : player.level().getEntitiesOfClass(Wight.class,
                player.getBoundingBox().inflate(RANGE),
                wight -> wight.isAlive() && !wight.isAggressive() && !wight.isInvisible()
                        && !wight.isHallucination())) {
            best = Math.min(best, effectiveDistance(player, wight));
        }

        return best == Double.MAX_VALUE ? -1.0D : best;
    }

    private static double effectiveDistance(LocalPlayer player, Entity target) {
        double distance = target.distanceTo(player);
        if (!player.hasLineOfSight(target)) {
            distance *= 1.5D;
        }
        return distance;
    }

    private static void drawQuad(ResourceLocation texture, float alpha, int screenWidth,
            int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, texture);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(0.0D, (double) screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        buffer.vertex((double) screenWidth, (double) screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        buffer.vertex((double) screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        buffer.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
