package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBombEntity;
import cn.blockforge.generated.goetysupreme.renderer.MalignasaurBombRenderer;

public class MalignasaurBombRenderer extends EntityRenderer<GemstoneMalignasaurBombEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/projectiles/malignasaur_bomb.png");

    private static final float HALF = 0.125F;

    private static final float CENTER_Y = 0.3125F;

    private static final int GLOW_R = 0x26;
    private static final int GLOW_G = 0xD3;
    private static final int GLOW_B = 0xFF;

    public MalignasaurBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(GemstoneMalignasaurBombEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(GemstoneMalignasaurBombEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float life = entity.tickCount + partialTick;
        float alpha = Mth.clamp((life - 10.0F) / 10.0F, 0.0F, 1.0F);
        if (alpha > 0.01F) {
            int a = (int) (255.0F * alpha);
            Matrix4f matrix = poseStack.last().pose();
            VertexConsumer base = buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
            this.renderTexturedCube(base, matrix, HALF, 255, 255, 255, a);
            VertexConsumer glow = buffer.getBuffer(RenderType.lightning());
            this.renderColoredCube(glow, matrix, HALF * 1.18F, GLOW_R, GLOW_G, GLOW_B,
                    (int) (a * 0.35F));
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderTexturedCube(VertexConsumer consumer, Matrix4f matrix, float h,
            int r, int g, int b, int a) {
        this.face(consumer, matrix, r, g, b, a,
                -h, -h, -h, -h, -h, h, -h, h, h, -h, h, -h);
        this.face(consumer, matrix, r, g, b, a,
                h, -h, h, h, -h, -h, h, h, -h, h, h, h);
        this.face(consumer, matrix, r, g, b, a,
                -h, -h, -h, h, -h, -h, h, -h, h, -h, -h, h);
        this.face(consumer, matrix, r, g, b, a,
                -h, h, h, h, h, h, h, h, -h, -h, h, -h);
        this.face(consumer, matrix, r, g, b, a,
                h, -h, -h, -h, -h, -h, -h, h, -h, h, h, -h);
        this.face(consumer, matrix, r, g, b, a,
                -h, -h, h, h, -h, h, h, h, h, -h, h, h);
    }

    private void face(VertexConsumer consumer, Matrix4f matrix,
            int r, int g, int b, int a,
            float x0, float y0, float z0, float x1, float y1, float z1,
            float x2, float y2, float z2, float x3, float y3, float z3) {
        float cy = CENTER_Y;
        consumer.vertex(matrix, x0, y0 + cy, z0).color(r, g, b, a).uv(0.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x1, y1 + cy, z1).color(r, g, b, a).uv(1.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x2, y2 + cy, z2).color(r, g, b, a).uv(1.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x3, y3 + cy, z3).color(r, g, b, a).uv(0.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0.0F, 1.0F, 0.0F).endVertex();
    }

    private void renderColoredCube(VertexConsumer consumer, Matrix4f matrix, float h,
            int r, int g, int b, int a) {
        this.faceColor(consumer, matrix, r, g, b, a,
                -h, -h, -h, -h, -h, h, -h, h, h, -h, h, -h);
        this.faceColor(consumer, matrix, r, g, b, a,
                h, -h, h, h, -h, -h, h, h, -h, h, h, h);
        this.faceColor(consumer, matrix, r, g, b, a,
                -h, -h, -h, h, -h, -h, h, -h, h, -h, -h, h);
        this.faceColor(consumer, matrix, r, g, b, a,
                -h, h, h, h, h, h, h, h, -h, -h, h, -h);
        this.faceColor(consumer, matrix, r, g, b, a,
                h, -h, -h, -h, -h, -h, -h, h, -h, h, h, -h);
        this.faceColor(consumer, matrix, r, g, b, a,
                -h, -h, h, h, -h, h, h, h, h, -h, h, h);
    }

    private void faceColor(VertexConsumer consumer, Matrix4f matrix, int r, int g, int b, int a,
            float x0, float y0, float z0, float x1, float y1, float z1,
            float x2, float y2, float z2, float x3, float y3, float z3) {
        float cy = CENTER_Y;
        consumer.vertex(matrix, x0, y0 + cy, z0).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, x1, y1 + cy, z1).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, x2, y2 + cy, z2).color(r, g, b, a).endVertex();
        consumer.vertex(matrix, x3, y3 + cy, z3).color(r, g, b, a).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GemstoneMalignasaurBombEntity entity) {
        return TEXTURE;
    }
}
