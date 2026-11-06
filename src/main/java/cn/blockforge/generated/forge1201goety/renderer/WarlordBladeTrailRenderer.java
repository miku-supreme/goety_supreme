package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WarlordBladeTrail;
import cn.blockforge.generated.goetysupreme.renderer.WarlordBladeTrailRenderer;
import cn.blockforge.generated.goetysupreme.entity.WarlordSaberTrailLayer;

public class WarlordBladeTrailRenderer extends EntityRenderer<WarlordBladeTrail> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/warlord_blade_trail.png");

    private static final ResourceLocation CORE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/warlord_blade_trail_glow.png");

    private static final float EXPAND = 1.45F;

    private static final float HALO_SCALE = 1.32F;

    private static final float CORE_SCALE = 0.72F;

    private static final float HALO_STRENGTH = 1.05F;

    private static final float CORE_STRENGTH = 0.9F;

    private static final int SEGMENTS = 8;

    private static final float TAIL = 0.45F;

    public WarlordBladeTrailRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WarlordBladeTrail trail, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        ++trail.animationTime;
        float age = trail.tickCount + partialTick;
        float life = trail.getLife();
        float progress = Mth.clamp(age / life, 0.0F, 1.0F);
        if (progress >= 1.0F) {
            return;
        }
        float globalFade = (float) Math.pow(1.0F - progress, 1.15D);
        if (globalFade <= 0.01F) {
            return;
        }
        float scale = trail.getTrailScale() * Mth.lerp(progress, 1.0F, EXPAND);
        float haloBreath = 0.5F + 0.5F * Mth.sin(age * 1.7F);
        float coreBreath = 0.5F + 0.5F * Mth.sin(age * 3.1F + 1.3F);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-trail.getTrailYaw() - 90.0F));
        float sway = Mth.sin(age * 0.9F) * 3.0F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(trail.getTrailRoll() + sway));
        PoseStack.Pose pose = poseStack.last();

        float haloSize = scale * HALO_SCALE * (1.0F + 0.05F * haloBreath);
        float coreSize = scale * CORE_SCALE * (1.0F + 0.06F * coreBreath);
        float halo = HALO_STRENGTH * (0.78F + 0.22F * haloBreath) * globalFade;
        float core = CORE_STRENGTH * (0.72F + 0.28F * coreBreath) * globalFade;

        VertexConsumer haloBuffer = bufferSource.getBuffer(RenderType.eyes(TEXTURE));
        drawLayer(haloBuffer, pose, haloSize, progress, halo, 1.0F, 0.42F, 0.16F, true);

        VertexConsumer body = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        drawLayer(body, pose, scale, progress, globalFade, 1.0F, 1.0F, 1.0F, false);

        VertexConsumer coreBuffer = bufferSource.getBuffer(RenderType.eyes(CORE_TEXTURE));
        drawLayer(coreBuffer, pose, coreSize, progress, core, 1.0F, 0.88F, 0.66F, true);

        poseStack.popPose();
        super.render(trail, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void drawLayer(VertexConsumer consumer, PoseStack.Pose pose, float size,
            float progress, float alpha, float r, float g, float b, boolean doubleSided) {
        for (int i = 0; i < SEGMENTS; i++) {
            float segStart = (float) i / SEGMENTS;
            float reveal = Mth.clamp((progress * (1.0F + TAIL) - segStart) / TAIL, 0.0F, 1.0F);
            if (reveal <= 0.001F) {
                continue;
            }
            float a = alpha * reveal;
            float z0 = -size + 2.0F * size * i / SEGMENTS;
            float z1 = -size + 2.0F * size * (i + 1) / SEGMENTS;
            float v0 = 1.0F - (float) i / SEGMENTS;
            float v1 = 1.0F - (float) (i + 1) / SEGMENTS;
            drawSegment(consumer, pose, size, z0, z1, v0, v1, r, g, b, a, false);
            if (doubleSided) {
                drawSegment(consumer, pose, size, z0, z1, v0, v1, r, g, b, a, true);
            }
        }
    }

    private static void drawSegment(VertexConsumer consumer, PoseStack.Pose pose, float size,
            float z0, float z1, float v0, float v1, float r, float g, float b, float a, boolean reversed) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        int light = 15728880;
        float ny = reversed ? -1.0F : 1.0F;
        if (reversed) {
            vertex(consumer, matrix, normal, size, 0.0F, z0, 1.0F, v0, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, -size, 0.0F, z0, 0.0F, v0, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, -size, 0.0F, z1, 0.0F, v1, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, size, 0.0F, z1, 1.0F, v1, light, r, g, b, a, 0.0F, ny, 0.0F);
        } else {
            vertex(consumer, matrix, normal, -size, 0.0F, z0, 0.0F, v0, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, size, 0.0F, z0, 1.0F, v0, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, size, 0.0F, z1, 1.0F, v1, light, r, g, b, a, 0.0F, ny, 0.0F);
            vertex(consumer, matrix, normal, -size, 0.0F, z1, 0.0F, v1, light, r, g, b, a, 0.0F, ny, 0.0F);
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float y, float z, float u, float v, int packedLight,
            float r, float g, float b, float a, float nx, float ny, float nz) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(WarlordBladeTrail trail) {
        return TEXTURE;
    }
}
