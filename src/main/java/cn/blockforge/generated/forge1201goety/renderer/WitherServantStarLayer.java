package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantStarLayer;

public final class WitherServantStarLayer extends RenderLayer<WitherServant, WitherServantModel> {
    private static final ResourceLocation STAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/item/raging_star.png");

    private static final float BONE_CENTER_Y_PX = 5.4F;
    private static final float BONE_FRONT_Z_PX = -0.5F;

    private static final float STAR_HALF_PX = 4.4F;
    private static final double PX = 1.0D / 16.0D;

    public WitherServantStarLayer(RenderLayerParent<WitherServant, WitherServantModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            WitherServant servant, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!servant.isStarEmbedded()) {
            return;
        }
        float pulse = 1.0F + 0.08F * Mth.sin((servant.tickCount + partialTick) * 0.12F);
        poseStack.pushPose();
        this.getParentModel().applyRootTransform(poseStack);
        this.getParentModel().shouldersPart().translateAndRotate(poseStack);
        float half = (float) (STAR_HALF_PX * PX) * pulse;
        float centerY = (float) (BONE_CENTER_Y_PX * PX);
        float frontZ = (float) ((BONE_FRONT_Z_PX - 0.35D) * PX);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(STAR_TEXTURE));
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        drawQuad(consumer, pose, normal, half, centerY, frontZ, false);
        drawQuad(consumer, pose, normal, half, centerY,
                (float) ((BONE_FRONT_Z_PX + 3.35D) * PX), true);
        poseStack.popPose();
    }

    private static void drawQuad(VertexConsumer consumer, Matrix4f pose, Matrix3f normal,
            float half, float centerY, float z, boolean flip) {
        float emissive = 1.0F;
        if (!flip) {
            put(consumer, pose, normal, -half, centerY - half, z, 0.0F, 0.0F, 0.0F, 0.0F, -1.0F, emissive);
            put(consumer, pose, normal, half, centerY - half, z, 1.0F, 0.0F, 0.0F, 0.0F, -1.0F, emissive);
            put(consumer, pose, normal, half, centerY + half, z, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F, emissive);
            put(consumer, pose, normal, -half, centerY + half, z, 0.0F, 1.0F, 0.0F, 0.0F, -1.0F, emissive);
        } else {
            put(consumer, pose, normal, half, centerY - half, z, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, emissive);
            put(consumer, pose, normal, -half, centerY - half, z, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, emissive);
            put(consumer, pose, normal, -half, centerY + half, z, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, emissive);
            put(consumer, pose, normal, half, centerY + half, z, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, emissive);
        }
    }

    private static void put(VertexConsumer consumer, Matrix4f pose, Matrix3f normal,
            float x, float y, float z, float u, float v, float nx, float ny, float nz, float shade) {
        consumer.vertex(pose, x, y, z)
                .color((int) (255 * shade), (int) (255 * shade), (int) (255 * shade), 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(240)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }
}
