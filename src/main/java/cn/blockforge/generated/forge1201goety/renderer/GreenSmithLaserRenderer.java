package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserTrailRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;

public class GreenSmithLaserRenderer extends EntityRenderer<GreenSmithLaserBeam> {

    private static final ResourceLocation BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/green_smith_laser_beam.png");

    private static final ResourceLocation NAMELESS_BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/nameless_laser_beam.png");

    private static final float GLOW_R = 0.55F, GLOW_G = 0.10F, GLOW_B = 1.00F;
    private static final float GLOW_A = 0.50F, GLOW_W = 0.34F;

    private static final float MAIN_R = 0.20F, MAIN_G = 0.35F, MAIN_B = 1.00F;
    private static final float MAIN_A = 0.80F, MAIN_W = 0.20F;

    private static final float CORE_R = 0.55F, CORE_G = 0.55F, CORE_B = 1.00F;
    private static final float CORE_A = 0.90F, CORE_W = 0.11F;

    public GreenSmithLaserRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GreenSmithLaserBeam beam, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        double distance = beam.beamTraceDistance(64.0D, partialTick, true);
        if (distance <= 0.05D) {
            return;
        }
        float yaw = Mth.rotLerp(partialTick, beam.yRotO, beam.getYRot());
        float pitch = Mth.rotLerp(partialTick, beam.xRotO, beam.getXRot());
        float scroll = (beam.level().getGameTime() + partialTick) * 0.45F;
        boolean nameless = beam.getOwner() instanceof NamelessWither;

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        PoseStack.Pose pose = poseStack.last();

        VertexConsumer glow = bufferSource.getBuffer(RenderType.eyes(
                nameless ? NAMELESS_BEAM_TEXTURE : BEAM_TEXTURE));
        drawTube(glow, pose, GLOW_W, distance, scroll,
                nameless ? 1.0F : GLOW_R,
                nameless ? 1.0F : GLOW_G,
                nameless ? 1.0F : GLOW_B, GLOW_A);
        VertexConsumer main = bufferSource.getBuffer(RenderType.eyes(
                nameless ? NAMELESS_BEAM_TEXTURE : BEAM_TEXTURE));
        drawTube(main, pose, MAIN_W, distance, scroll,
                nameless ? 1.0F : MAIN_R,
                nameless ? 1.0F : MAIN_G,
                nameless ? 1.0F : MAIN_B, MAIN_A);
        VertexConsumer core = bufferSource.getBuffer(RenderType.eyes(
                nameless ? NAMELESS_BEAM_TEXTURE : BEAM_TEXTURE));
        drawTube(core, pose, CORE_W, distance, scroll,
                nameless ? 1.0F : CORE_R,
                nameless ? 1.0F : CORE_G,
                nameless ? 1.0F : CORE_B, CORE_A);

        poseStack.popPose();
        CorruptedLaserTrailRenderer.render(beam, partialTick, poseStack, bufferSource, distance);
        super.render(beam, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void drawTube(VertexConsumer consumer, PoseStack.Pose pose, float halfWidth,
            double distance, float scroll, float r, float g, float b, float a) {
        float v0 = scroll;
        float v1 = (float) (distance / 1.5D) + scroll;
        side(consumer, pose, halfWidth, -halfWidth, halfWidth, halfWidth, distance, v0, v1, r, g, b, a);
        side(consumer, pose, -halfWidth, halfWidth, -halfWidth, -halfWidth, distance, v0, v1, r, g, b, a);
        side(consumer, pose, -halfWidth, halfWidth, halfWidth, halfWidth, distance, v0, v1, r, g, b, a);
        side(consumer, pose, halfWidth, -halfWidth, -halfWidth, -halfWidth, distance, v0, v1, r, g, b, a);
    }

    private static void side(VertexConsumer consumer, PoseStack.Pose pose, float ax, float ay, float bx, float by,
            double distance, float v0, float v1, float r, float g, float b, float a) {
        float z = (float) distance;
        vertex(consumer, pose, ax, ay, 0.0F, 0.0F, v0, r, g, b, a);
        vertex(consumer, pose, bx, by, 0.0F, 1.0F, v0, r, g, b, a);
        vertex(consumer, pose, bx, by, z, 1.0F, v1, r, g, b, a);
        vertex(consumer, pose, ax, ay, z, 0.0F, v1, r, g, b, a);
        vertex(consumer, pose, bx, by, 0.0F, 1.0F, v0, r, g, b, a);
        vertex(consumer, pose, ax, ay, 0.0F, 0.0F, v0, r, g, b, a);
        vertex(consumer, pose, ax, ay, z, 0.0F, v1, r, g, b, a);
        vertex(consumer, pose, bx, by, z, 1.0F, v1, r, g, b, a);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z,
            float u, float v, float r, float g, float b, float a) {
        consumer.vertex(pose.pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(pose.normal(), 0.0F, 0.0F, 1.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GreenSmithLaserBeam beam) {
        return BEAM_TEXTURE;
    }
}
