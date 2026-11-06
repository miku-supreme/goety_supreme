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
import cn.blockforge.generated.goetysupreme.entity.PrismarineBladeSlashEntity;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineBladeSlashRenderer;

public class PrismarineBladeSlashRenderer extends EntityRenderer<PrismarineBladeSlashEntity> {
    private static final ResourceLocation[] SLASH_FRAMES = {
            slashTexture("1.png"), slashTexture("2.png"), slashTexture("3.png"), slashTexture("4.png"),
    };

    public PrismarineBladeSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private static ResourceLocation slashTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/entity/projectiles/slash/" + name);
    }

    @Override
    public void render(PrismarineBladeSlashEntity slash, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        ++slash.animationTime;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, slash.yRotO, slash.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(-Mth.lerp(partialTick, slash.xRotO, slash.getXRot())));
        float previousRadius = slash.previousRadius;
        float radius = previousRadius + (slash.getRadius() - previousRadius) * Math.min(partialTick, 1.0F);
        PoseStack.Pose pose = poseStack.last();
        ResourceLocation bodyFrame = SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
        ResourceLocation glowFrame = SLASH_FRAMES[(slash.animationTime / 6 + 1) % SLASH_FRAMES.length];
        drawSlashQuad(bufferSource.getBuffer(RenderType.entityTranslucent(bodyFrame)), pose, radius, packedLight, 1.0F);
        VertexConsumer glow = bufferSource.getBuffer(RenderType.eyes(glowFrame));
        drawSlashQuad(glow, pose, radius, 15728880, 1.0F);
        drawSlashQuad(glow, pose, radius, 15728880, 1.0F, true);
        poseStack.popPose();
        super.render(slash, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void drawSlashQuad(VertexConsumer consumer, PoseStack.Pose pose, float radius,
            int packedLight, float alpha) {
        drawSlashQuad(consumer, pose, radius, packedLight, alpha, false);
    }

    private static void drawSlashQuad(VertexConsumer consumer, PoseStack.Pose pose, float radius,
            int packedLight, float alpha, boolean reversed) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        float r = 0.35F, g = 0.9F, b = 1.0F;
        float s = radius;
        if (reversed) {
            vertex(consumer, matrix, normal, 0.0F, s, s, 1.0F, 0.0F, packedLight, alpha, r, g, b, -1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, s, -s, 1.0F, 1.0F, packedLight, alpha, r, g, b, -1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, -s, -s, 0.0F, 1.0F, packedLight, alpha, r, g, b, -1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, -s, s, 0.0F, 0.0F, packedLight, alpha, r, g, b, -1.0F, 0.0F, 0.0F);
        } else {
            vertex(consumer, matrix, normal, 0.0F, -s, s, 0.0F, 0.0F, packedLight, alpha, r, g, b, 1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, -s, -s, 0.0F, 1.0F, packedLight, alpha, r, g, b, 1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, s, -s, 1.0F, 1.0F, packedLight, alpha, r, g, b, 1.0F, 0.0F, 0.0F);
            vertex(consumer, matrix, normal, 0.0F, s, s, 1.0F, 0.0F, packedLight, alpha, r, g, b, 1.0F, 0.0F, 0.0F);
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float y, float z, float u, float v, int packedLight, float alpha, float r, float g, float b,
            float nx, float ny, float nz) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(PrismarineBladeSlashEntity slash) {
        return SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
    }
}
