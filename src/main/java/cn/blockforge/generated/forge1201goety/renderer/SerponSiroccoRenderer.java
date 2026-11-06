package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.entity.SerponSiroccoEntity;
import cn.blockforge.generated.goetysupreme.renderer.SerponSiroccoRenderer;

public final class SerponSiroccoRenderer extends EntityRenderer<SerponSiroccoEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "goety_supreme", "textures/entity/serpon_sirocco.png");

    private static final int SEGMENTS = 20;
    private static final int POINTS_PER_SEGMENT = 20;
    private static final float MAX_RADIUS = 1.0F;
    private static final float MAX_HEIGHT = 4.0F;
    private static final float BASE_WIDTH = 0.08F;
    private static final int BEGIN_COLOR = 0x5C311F;
    private static final int TARGET_COLOR = 0xA17541;

    public SerponSiroccoRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SerponSiroccoEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SerponSiroccoEntity entity, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        renderBeam(entity, partialTicks * 2.0F, poseStack, buffer);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderBeam(SerponSiroccoEntity entity, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer) {
        int life = entity.getLife();
        float totalAlpha = 1.0F;
        if (life < 5) {
            totalAlpha = life / 5.0F;
        } else if (life > 30) {
            totalAlpha = 1.0F - (life - 30) / 10.0F;
        }
        totalAlpha = Mth.clamp(totalAlpha, 0.0F, 1.0F);
        if (totalAlpha < 0.01F) {
            return;
        }

        VertexConsumer consumer = buffer.getBuffer(RenderType.lightning());
        Matrix4f matrix = poseStack.last().pose();

        float riseOffset = (life + partialTick) * 0.025F;
        float baseRotation = (life + partialTick) * 0.12F;

        float beginR = ((BEGIN_COLOR >> 16) & 0xFF) / 255.0F;
        float beginG = ((BEGIN_COLOR >> 8) & 0xFF) / 255.0F;
        float beginB = (BEGIN_COLOR & 0xFF) / 255.0F;
        float targetR = ((TARGET_COLOR >> 16) & 0xFF) / 255.0F;
        float targetG = ((TARGET_COLOR >> 8) & 0xFF) / 255.0F;
        float targetB = (TARGET_COLOR & 0xFF) / 255.0F;

        for (int s = 0; s < SEGMENTS; ++s) {
            Random random = new Random(entity.getId() * 31L + 17L + s * 103L);
            float fade = (random.nextFloat() + totalAlpha) / 2.0F;
            float r = fade * beginR + targetR * (1.0F - fade);
            float g = fade * beginG + targetG * (1.0F - fade);
            float b = fade * beginB + targetB * (1.0F - fade);
            int cr = (int) (r * 255.0F);
            int cg = (int) (g * 255.0F);
            int cb = (int) (b * 255.0F);

            float radiusBase = (0.5F + 1.8F * random.nextFloat()) * MAX_RADIUS;
            float segHeight = (0.5F + 0.5F * random.nextFloat()) * MAX_HEIGHT;
            float angleOffset = random.nextFloat() * 2.0F * (float) Math.PI;
            float turns = 1.5F + 2.5F * random.nextFloat();
            float speedFactor = 5.0F + 4.0F * random.nextFloat();

            float[] xs = new float[POINTS_PER_SEGMENT + 1];
            float[] ys = new float[POINTS_PER_SEGMENT + 1];
            float[] zs = new float[POINTS_PER_SEGMENT + 1];
            for (int i = 0; i <= POINTS_PER_SEGMENT; ++i) {
                float progress = (float) i / (float) POINTS_PER_SEGMENT;
                float radius = radiusBase * progress;
                float y = progress * segHeight + riseOffset;
                float angle = angleOffset + progress * turns * 2.0F * (float) Math.PI
                        + baseRotation * speedFactor;
                xs[i] = radius * Mth.cos(angle);
                ys[i] = y;
                zs[i] = radius * Mth.sin(angle);
            }

            for (int i = 0; i < POINTS_PER_SEGMENT; ++i) {
                float y0 = ys[i];
                float y1 = ys[i + 1];
                if (y0 < 0.0F && y1 < 0.0F) {
                    continue;
                }
                if (y0 > MAX_HEIGHT && y1 > MAX_HEIGHT) {
                    continue;
                }

                float h0 = Mth.clamp(y0 / MAX_HEIGHT, 0.0F, 1.0F);
                float h1 = Mth.clamp(y1 / MAX_HEIGHT, 0.0F, 1.0F);

                float edgeFade0 = 1.0F;
                if (y0 < 0.1F * MAX_HEIGHT) {
                    edgeFade0 = y0 / (0.1F * MAX_HEIGHT);
                }
                if (y0 > 0.9F * MAX_HEIGHT) {
                    edgeFade0 = (MAX_HEIGHT - y0) / (0.1F * MAX_HEIGHT);
                }
                float edgeFade1 = 1.0F;
                if (y1 < 0.1F * MAX_HEIGHT) {
                    edgeFade1 = y1 / (0.1F * MAX_HEIGHT);
                }
                if (y1 > 0.9F * MAX_HEIGHT) {
                    edgeFade1 = (MAX_HEIGHT - y1) / (0.1F * MAX_HEIGHT);
                }

                float alpha0 = totalAlpha * h0 * edgeFade0;
                float alpha1 = totalAlpha * h1 * edgeFade1;
                if (alpha0 < 0.005F && alpha1 < 0.005F) {
                    continue;
                }

                float w0 = BASE_WIDTH * (0.2F + 0.8F * h0);
                float w1 = BASE_WIDTH * (0.2F + 0.8F * h1);
                int a0 = (int) (255.0F * alpha0);
                int a1 = (int) (255.0F * alpha1);

                consumer.vertex(matrix, xs[i], y0 - w0, zs[i]).color(cr, cg, cb, a0).endVertex();
                consumer.vertex(matrix, xs[i], y0 + w0, zs[i]).color(cr, cg, cb, a0).endVertex();
                consumer.vertex(matrix, xs[i + 1], y1 + w1, zs[i + 1]).color(cr, cg, cb, a1).endVertex();
                consumer.vertex(matrix, xs[i + 1], y1 - w1, zs[i + 1]).color(cr, cg, cb, a1).endVertex();
            }
        }
    }
}
