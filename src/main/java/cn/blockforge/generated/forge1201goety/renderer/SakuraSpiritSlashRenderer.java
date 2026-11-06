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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritSlashEntity;
import cn.blockforge.generated.goetysupreme.renderer.SakuraSpiritSlashRenderer;

public class SakuraSpiritSlashRenderer extends EntityRenderer<SakuraSpiritSlashEntity> {
    private static final ResourceLocation[] SLASH_FRAMES = {
            slashTexture("1.png"), slashTexture("2.png"), slashTexture("3.png"), slashTexture("4.png"),
    };

    public SakuraSpiritSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private static ResourceLocation slashTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/entity/projectiles/slash/" + name);
    }

    @Override
    public void render(SakuraSpiritSlashEntity slash, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        ++slash.animationTime;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, slash.yRotO, slash.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F - Mth.lerp(partialTick, slash.xRotO, slash.getXRot())));
        float previousWidth = slash.previousWidth;
        float width = previousWidth + (slash.getBbWidth() - previousWidth) * Math.min(partialTick, 1.0F);
        float radius = width * 0.5F;
        PoseStack.Pose pose = poseStack.last();
        ResourceLocation bodyFrame = SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
        ResourceLocation glowFrame = SLASH_FRAMES[(slash.animationTime / 6 + 1) % SLASH_FRAMES.length];
        drawSlashQuad(bufferSource.getBuffer(RenderType.entityTranslucent(bodyFrame)), pose, radius, packedLight, 1.0F);
        drawSlashQuad(bufferSource.getBuffer(RenderType.eyes(glowFrame)), pose, radius, 15728880, 1.0F);
        poseStack.popPose();
        super.render(slash, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void drawSlashQuad(VertexConsumer consumer, PoseStack.Pose pose, float radius,
            int packedLight, float alpha) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        vertex(consumer, matrix, normal, -radius, -radius, 0.0F, 0.0F, packedLight, alpha);
        vertex(consumer, matrix, normal, radius, -radius, 1.0F, 0.0F, packedLight, alpha);
        vertex(consumer, matrix, normal, radius, radius, 1.0F, 1.0F, packedLight, alpha);
        vertex(consumer, matrix, normal, -radius, radius, 0.0F, 1.0F, packedLight, alpha);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float z, float u, float v, int packedLight, float alpha) {
        consumer.vertex(matrix, x, -0.1F, z)
                .color(1.0F, 0.5F, 0.6F, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(SakuraSpiritSlashEntity slash) {
        return SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
    }
}
