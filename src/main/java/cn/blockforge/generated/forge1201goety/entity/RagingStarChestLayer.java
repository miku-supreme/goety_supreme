package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.entity.RagingStarChestLayer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantStarLayer;

public final class RagingStarChestLayer<T extends LivingEntity, M extends HumanoidModel<T>>
        extends RenderLayer<T, M> {

    private static final ResourceLocation STAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/item/raging_star.png");

    private static final float CHEST_CENTER_Y_PX = 4.5F;

    private static final float CHEST_FRONT_Z_PX = -3.25F;

    public static final float DEFAULT_STAR_HALF_PX = 2.9F;
    private static final double PX = 1.0D / 16.0D;

    private final float starHalfPx;

    public RagingStarChestLayer(RenderLayerParent<T, M> parent) {
        this(parent, DEFAULT_STAR_HALF_PX);
    }

    public RagingStarChestLayer(RenderLayerParent<T, M> parent, float starHalfPx) {
        super(parent);
        this.starHalfPx = starHalfPx;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (!(entity instanceof RagingStarServant servant) || !servant.isRagingStarred()) {
            return;
        }
        float pulse = 1.0F + 0.08F * Mth.sin((entity.tickCount + partialTick) * 0.12F);
        poseStack.pushPose();
        if (this.getParentModel().young) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0F, 1.5F, 0.0F);
        }
        this.getParentModel().body.translateAndRotate(poseStack);
        float half = (float) (this.starHalfPx * PX) * pulse;
        float centerY = (float) (CHEST_CENTER_Y_PX * PX);
        float frontZ = (float) (CHEST_FRONT_Z_PX * PX);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(STAR_TEXTURE));
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        put(consumer, pose, normal, -half, centerY - half, frontZ, 0.0F, 0.0F, 0.0F, 0.0F, -1.0F);
        put(consumer, pose, normal, half, centerY - half, frontZ, 1.0F, 0.0F, 0.0F, 0.0F, -1.0F);
        put(consumer, pose, normal, half, centerY + half, frontZ, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        put(consumer, pose, normal, -half, centerY + half, frontZ, 0.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        poseStack.popPose();
    }

    private static void put(VertexConsumer consumer, Matrix4f pose, Matrix3f normal,
            float x, float y, float z, float u, float v, float nx, float ny, float nz) {
        consumer.vertex(pose, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(240)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }
}
