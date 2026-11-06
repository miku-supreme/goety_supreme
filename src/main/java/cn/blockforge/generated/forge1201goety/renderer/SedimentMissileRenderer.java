package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;
import cn.blockforge.generated.goetysupreme.renderer.SedimentMissileRenderer;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;

public class SedimentMissileRenderer extends EntityRenderer<SedimentMissileEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/projectiles/sediment_missile.png");

    private static final ResourceLocation RAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotes", "textures/entity/beam/ray.png");

    private static final int COLOR_I = 6924236;
    private static final int COLOR_II = 9029580;
    private static final int COLOR_III = 24472;

    private static final float CORE_HALF = 0.125F;

    private static final float TRAIL_HALF_WIDTH = 0.12F;

    private static final float TRAIL_MAX_ALPHA = 0.6F;

    private static final float TRAIL_AGE_TICKS = 20.0F;

    private static final float TRAIL_BASE_LIFE = 120.0F;

    private static final float TRAIL_ALPHA_SCALE = 200.0F;

    public SedimentMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SedimentMissileEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SedimentMissileEntity entity, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        renderCore(poseStack, buffer);
        renderTrail(entity, partialTicks, poseStack, buffer);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void renderCore(PoseStack poseStack, MultiBufferSource buffer) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        float s = CORE_HALF;
        face(consumer, matrix, normal, 0.0F, 0.0F, -1.0F,
                -s, -s, -s, s, -s, -s, s, s, -s, -s, s, -s);
        face(consumer, matrix, normal, 0.0F, 0.0F, 1.0F,
                -s, -s, s, -s, s, s, s, s, s, s, -s, s);
        face(consumer, matrix, normal, -1.0F, 0.0F, 0.0F,
                -s, -s, -s, -s, s, -s, -s, s, s, -s, -s, s);
        face(consumer, matrix, normal, 1.0F, 0.0F, 0.0F,
                s, -s, -s, s, -s, s, s, s, s, s, s, -s);
        face(consumer, matrix, normal, 0.0F, -1.0F, 0.0F,
                -s, -s, -s, -s, -s, s, s, -s, s, s, -s, -s);
        face(consumer, matrix, normal, 0.0F, 1.0F, 0.0F,
                -s, s, -s, s, s, -s, s, s, s, -s, s, s);
    }

    private void renderTrail(SedimentMissileEntity entity, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer) {
        List<SedimentMissileEntity.TrailPoint> points = entity.getTrailPoints();
        if (points.size() < 2) {
            return;
        }
        Vec3 origin = new Vec3(
                Mth.lerp(partialTicks, entity.xOld, entity.getX()),
                Mth.lerp(partialTicks, entity.yOld, entity.getY()),
                Mth.lerp(partialTicks, entity.zOld, entity.getZ()));
        int n = points.size();

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(RAY_TEXTURE));

        float[] widths = new float[n];
        float[] alphas = new float[n];
        for (int i = 0; i < n; i++) {
            float t = (float) i / (n - 1);
            float age = entity.tickCount - points.get(i).createdAt;
            float ageFade = 1.0F - Math.min(1.0F, age / TRAIL_AGE_TICKS);
            float overallFade = 1.0F - entity.tickCount / TRAIL_BASE_LIFE;
            alphas[i] = Math.max(0.0F,
                    Math.min(TRAIL_MAX_ALPHA, 0.8F * ageFade * (1.0F - overallFade * 0.7F)));
            widths[i] = TRAIL_HALF_WIDTH * ageFade * (0.3F + 0.7F * t);
        }

        for (int i = 0; i < n - 1; i++) {
            Vector3f p0 = toLocal(points.get(i).getPosition(partialTicks), origin);
            Vector3f p1 = toLocal(points.get(i + 1).getPosition(partialTicks), origin);
            Vector3f dir = new Vector3f(p1).sub(p0);
            if (dir.lengthSquared() < 1.0E-8F) {
                continue;
            }
            dir.normalize();
            Vector3f refUp = Math.abs(dir.y) < 0.9F ? new Vector3f(0.0F, 1.0F, 0.0F) : new Vector3f(1.0F, 0.0F, 0.0F);
            Vector3f right = new Vector3f(dir).cross(refUp).normalize();
            Vector3f up = new Vector3f(right).cross(dir).normalize();
            float t0 = (float) i / (n - 1);
            float t1 = (float) (i + 1) / (n - 1);
            int c0 = lerpColor(t0);
            int c1 = lerpColor(t1);
            ribbon(consumer, matrix, normal, p0, p1, right,
                    widths[i], widths[i + 1], alphas[i], alphas[i + 1], c0, c1);
            ribbon(consumer, matrix, normal, p0, p1, up,
                    widths[i], widths[i + 1], alphas[i], alphas[i + 1], c0, c1);
        }
    }

    private static Vector3f toLocal(Vec3 world, Vec3 origin) {
        return new Vector3f((float) (world.x - origin.x), (float) (world.y - origin.y),
                (float) (world.z - origin.z));
    }

    private static void ribbon(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            Vector3f p0, Vector3f p1, Vector3f axis, float w0, float w1,
            float a0, float a1, int c0, int c1) {
        Vector3f l0 = new Vector3f(axis).mul(-w0).add(p0);
        Vector3f r0 = new Vector3f(axis).mul(w0).add(p0);
        Vector3f l1 = new Vector3f(axis).mul(-w1).add(p1);
        Vector3f r1 = new Vector3f(axis).mul(w1).add(p1);
        trailVertex(consumer, matrix, normal, l0, c0, a0, 0.0F, 1.0F);
        trailVertex(consumer, matrix, normal, r0, c0, a0, 1.0F, 1.0F);
        trailVertex(consumer, matrix, normal, r1, c1, a1, 1.0F, 0.0F);
        trailVertex(consumer, matrix, normal, l1, c1, a1, 0.0F, 0.0F);
        trailVertex(consumer, matrix, normal, l1, c1, a1, 0.0F, 0.0F);
        trailVertex(consumer, matrix, normal, r1, c1, a1, 1.0F, 0.0F);
        trailVertex(consumer, matrix, normal, r0, c0, a0, 1.0F, 1.0F);
        trailVertex(consumer, matrix, normal, l0, c0, a0, 0.0F, 1.0F);
    }

    private static void trailVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            Vector3f pos, int color, float alpha, float u, float v) {
        consumer.vertex(matrix, pos.x, pos.y, pos.z)
                .color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF,
                        (int) (alpha * TRAIL_ALPHA_SCALE))
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static int lerpColor(float t) {
        int from = t < 0.5F ? COLOR_I : COLOR_II;
        int to = t < 0.5F ? COLOR_II : COLOR_III;
        float u = t < 0.5F ? t / 0.5F : (t - 0.5F) / 0.5F;
        int r = (int) Mth.lerp(u, (from >> 16) & 0xFF, (to >> 16) & 0xFF);
        int g = (int) Mth.lerp(u, (from >> 8) & 0xFF, (to >> 8) & 0xFF);
        int b = (int) Mth.lerp(u, from & 0xFF, to & 0xFF);
        return r << 16 | g << 8 | b;
    }

    private static void face(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float nx, float ny, float nz,
            float ax, float ay, float az, float bx, float by, float bz,
            float cx, float cy, float cz, float dx, float dy, float dz) {
        int light = LightTexture.FULL_BRIGHT;
        vertex(consumer, matrix, normal, ax, ay, az, nx, ny, nz, light, 0.0F, 1.0F);
        vertex(consumer, matrix, normal, bx, by, bz, nx, ny, nz, light, 1.0F, 1.0F);
        vertex(consumer, matrix, normal, cx, cy, cz, nx, ny, nz, light, 1.0F, 0.0F);
        vertex(consumer, matrix, normal, dx, dy, dz, nx, ny, nz, light, 0.0F, 0.0F);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float y, float z, float nx, float ny, float nz, int light, float u, float v) {
        consumer.vertex(matrix, x, y, z).color(255, 255, 255, 255)
                .uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, nx, ny, nz).endVertex();
    }
}
