package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PrismarineArrowEntity;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineArrowRenderer;

public class PrismarineArrowRenderer extends ArrowRenderer<PrismarineArrowEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prismarine_arrow.png");

    private static final ResourceLocation ORB_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety", "textures/entity/projectiles/scatter_bolt.png");

    private static final float[] ORB_ALONG = {-0.42F, 0.02F, 0.46F};
    private static final float[] ORB_SIDE = {0.11F, -0.13F, 0.09F};
    private static final float[] ORB_UP = {0.10F, 0.08F, -0.12F};
    private static final float[] ORB_SIZE = {0.11F, 0.14F, 0.10F};

    private static final float ARROW_SCALE = 1.7F;

    public PrismarineArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PrismarineArrowEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(PrismarineArrowEntity entity, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(ARROW_SCALE, ARROW_SCALE, ARROW_SCALE);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (!entity.isThunder()) {
            poseStack.popPose();
            return;
        }
        Vec3 forward = entity.getViewVector(partialTicks);
        Vec3 side = forward.cross(new Vec3(0.0D, 1.0D, 0.0D));
        if (side.lengthSqr() < 1.0E-4D) {
            side = new Vec3(1.0D, 0.0D, 0.0D);
        } else {
            side = side.normalize();
        }
        Vec3 realUp = side.cross(forward).normalize();
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(ORB_TEXTURE));
        float age = entity.tickCount + partialTicks;
        for (int index = 0; index < ORB_ALONG.length; ++index) {
            Vec3 offset = forward.scale(ORB_ALONG[index])
                    .add(side.scale(ORB_SIDE[index]))
                    .add(realUp.scale(ORB_UP[index]));
            float spin = age * (14.0F + index * 6.0F) + index * 120.0F;
            poseStack.pushPose();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(new Quaternionf(new AxisAngle4f(
                    (float) Math.toRadians(spin), 0.0F, 0.0F, 1.0F)));
            quad(consumer, poseStack.last(), ORB_SIZE[index]);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    private static void quad(VertexConsumer consumer, PoseStack.Pose pose, float size) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        int light = LightTexture.FULL_BRIGHT;
        consumer.vertex(matrix, -size, -size, 0.0F).color(255, 255, 255, 255)
                .uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, 0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, size, -size, 0.0F).color(255, 255, 255, 255)
                .uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, 0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, size, size, 0.0F).color(255, 255, 255, 255)
                .uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, 0.0F, 0.0F, 1.0F).endVertex();
        consumer.vertex(matrix, -size, size, 0.0F).color(255, 255, 255, 255)
                .uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, 0.0F, 0.0F, 1.0F).endVertex();
    }
}
