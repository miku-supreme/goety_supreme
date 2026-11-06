package cn.blockforge.generated.goetysupreme.renderer;

import java.util.ArrayList;
import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.renderer.LightningBoltBeamRenderer;

public class LightningBoltBeamRenderer<T extends AbstractHurtingProjectile> extends EntityRenderer<T> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotes", "textures/entity/beam/ray.png");

    private static final int COLOR_I = 0x3296ff;
    private static final int COLOR_II = 0x8ac2ff;

    private static final int STRANDS = 5;

    private static final float STRAND_HALF_WIDTH = 0.006F;

    private final int maxLife;
    private final float beamScale;

    public LightningBoltBeamRenderer(EntityRendererProvider.Context context, int maxLife, float beamScale) {
        super(context);
        this.maxLife = Math.max(1, maxLife);
        this.beamScale = beamScale;
    }

    @Override
    public int getBlockLightLevel(T entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        float life = entity.tickCount + partialTick;
        float half = this.maxLife / 2.0F;
        float fade = 1.0F - Mth.clamp((life - half) / half, 0.0F, 1.0F);
        if (fade > 0.01F) {
            this.renderBeam(entity, partialTick, life, fade, poseStack, buffer);
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderBeam(T entity, float partialTick, float life, float fade,
            PoseStack poseStack, MultiBufferSource buffer) {
        Vec3 motion = entity.getDeltaMovement();
        Vec3 forward = motion.lengthSqr() > 1.0E-6D ? motion.normalize() : new Vec3(0.0D, 0.0D, 1.0D);
        Vec3 startLocal = forward.scale(-1.0D).add(0.0D, -0.15D, 0.0D);
        Vec3 endLocal = forward.scale(1.4D).add(0.0D, 0.15D, 0.0D);

        float amplitude = 0.8F * fade * this.beamScale;
        int timeSeed = entity.tickCount / 2;
        int uuidHash = entity.getUUID().hashCode();
        int segments = 10 + entity.level().getRandom().nextInt(4);
        int lineAlpha = (int) (230.0F * fade);

        List<Vec3> startAnchors = new ArrayList<>(5);
        List<Vec3> endAnchors = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            double angle = (i * 2) * Math.PI / 5.0D + timeSeed * 0.02D + uuidHash * 0.001D;
            double radius = 0.2D + 0.08D * Math.sin(timeSeed * 0.03D + i * 1.7D + uuidHash * 5.0E-4D);
            startAnchors.add(startLocal.add(radius * Math.cos(angle),
                    radius * 0.3D * Math.sin(angle * 1.3D + uuidHash * 0.001D),
                    radius * Math.sin(angle)));
            endAnchors.add(endLocal.add(
                    radius * Math.cos(angle + 1.2D + uuidHash * 5.0E-4D),
                    radius * 0.3D * Math.sin(angle * 1.3D + 0.8D + uuidHash * 0.001D),
                    radius * Math.sin(angle + 1.2D + uuidHash * 5.0E-4D)));
        }

        VertexConsumer consumer = buffer.getBuffer(RenderType.lightning());
        Matrix4f matrix = poseStack.last().pose();

        for (int strand = 0; strand < STRANDS; ++strand) {
            Vec3 startPoint = startAnchors.get(strand % 5);
            Vec3 endPoint = endAnchors.get(strand % 5);
            long seed = (long) uuidHash + (long) timeSeed * 137L + (long) segments * 31L + (long) strand * 7L;
            RandomSource strandRandom = RandomSource.create(seed);

            List<Vec3> points = new ArrayList<>(segments + 1);
            points.add(startPoint);
            for (int i = 1; i < segments; ++i) {
                float t = (float) i / (float) segments;
                Vec3 basePos = startPoint.lerp(endPoint, t);
                float envelope = (float) Math.sin(Math.PI * t);
                double noiseX = (strandRandom.nextDouble() - 0.5D) * 2.0D * amplitude * envelope;
                double noiseY = (strandRandom.nextDouble() - 0.5D) * 1.8D * amplitude * envelope;
                double noiseZ = (strandRandom.nextDouble() - 0.5D) * 2.0D * amplitude * envelope;
                double timeOffset = timeSeed * 0.02D;
                double wave1 = Math.sin(t * 18.0D + strand * 1.7D + timeOffset + uuidHash * 3.0E-4D)
                        * 0.35D * amplitude * envelope;
                double wave2 = Math.cos(t * 15.0D + strand * 2.3D + timeOffset * 1.2D + uuidHash * 4.0E-4D)
                        * 0.35D * amplitude * envelope;
                double wave3 = Math.sin(t * 20.0D + strand * 3.1D + timeOffset * 0.8D + uuidHash * 2.0E-4D)
                        * 0.3D * amplitude * envelope;
                points.add(basePos.add(noiseX + wave1, noiseY + wave2 * 0.6D, noiseZ + wave3));
            }
            points.add(endPoint);

            RandomSource colorRandom = RandomSource.create((long) strand * 137L + (long) timeSeed * 7L);
            float shift = colorRandom.nextFloat();
            int r = Mth.clamp((int) (channel(COLOR_I, 16) * shift + channel(COLOR_II, 16) * (1.5F - shift)), 0, 255);
            int g = Mth.clamp((int) (channel(COLOR_I, 8) * shift + channel(COLOR_II, 8) * (1.5F - shift)), 0, 255);
            int b = Mth.clamp((int) (channel(COLOR_I, 0) * shift + channel(COLOR_II, 0) * (1.5F - shift)), 0, 255);
            float brightFactor = 0.7F + 0.3F * (strand % 3) / 2.0F;
            r = (int) (r * brightFactor);
            g = (int) (g * brightFactor);
            b = (int) (b * brightFactor);

            for (int segIdx = 0; segIdx < points.size() - 1; ++segIdx) {
                Vec3 p0 = points.get(segIdx);
                Vec3 p1 = points.get(segIdx + 1);
                this.renderSegment(consumer, matrix, p0, p1, STRAND_HALF_WIDTH * 6.0F,
                        r, g, b, (int) (lineAlpha * 0.2F));
                this.renderSegment(consumer, matrix, p0, p1, STRAND_HALF_WIDTH, r, g, b, lineAlpha);
            }
        }
    }

    private void renderSegment(VertexConsumer consumer, org.joml.Matrix4f matrix,
            Vec3 p0, Vec3 p1, float halfWidth, int r, int g, int b, int alpha) {
        Vec3 dir = p1.subtract(p0);
        if (dir.lengthSqr() < 1.0E-10D) {
            return;
        }
        dir = dir.normalize();
        Vec3 sideA = dir.cross(new Vec3(0.0D, 1.0D, 0.0D));
        if (sideA.lengthSqr() < 1.0E-6D) {
            sideA = new Vec3(1.0D, 0.0D, 0.0D);
        }
        sideA = sideA.normalize();
        Vec3 sideB = dir.cross(sideA).normalize();
        this.renderQuad(consumer, matrix, p0, p1, sideA, halfWidth, r, g, b, alpha);
        this.renderQuad(consumer, matrix, p0, p1, sideB, halfWidth, r, g, b, alpha);
    }

    private void renderQuad(VertexConsumer consumer, org.joml.Matrix4f matrix,
            Vec3 p0, Vec3 p1, Vec3 side, float halfWidth, int r, int g, int b, int alpha) {
        Vec3 a0 = p0.subtract(side.scale(halfWidth));
        Vec3 b0 = p0.add(side.scale(halfWidth));
        Vec3 a1 = p1.subtract(side.scale(halfWidth));
        Vec3 b1 = p1.add(side.scale(halfWidth));
        consumer.vertex(matrix, (float) a0.x, (float) a0.y, (float) a0.z).color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, (float) b0.x, (float) b0.y, (float) b0.z).color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, (float) b1.x, (float) b1.y, (float) b1.z).color(r, g, b, alpha).endVertex();
        consumer.vertex(matrix, (float) a1.x, (float) a1.y, (float) a1.z).color(r, g, b, alpha).endVertex();
    }

    private static int channel(int color, int shift) {
        return color >> shift & 0xFF;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
