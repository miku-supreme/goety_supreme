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
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.renderer.WarlordHellfireSlashRenderer;

public class WarlordHellfireSlashRenderer extends EntityRenderer<WarlordHellfireSlash> {

    private static final ResourceLocation[] SLASH_FRAMES = {
            slashTexture("1.png"), slashTexture("2.png"), slashTexture("3.png"), slashTexture("4.png"),
    };

    private static final float BODY_R = 0.045F;
    private static final float BODY_G = 0.002F;
    private static final float BODY_B = 0.008F;

    private static final float GLOW_R = 0.34F;
    private static final float GLOW_G = 0.008F;
    private static final float GLOW_B = 0.024F;

    public WarlordHellfireSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private static ResourceLocation slashTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/entity/projectiles/slash/" + name);
    }

    @Override
    public void render(WarlordHellfireSlash slash, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        ++slash.animationTime;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, slash.yRotO, slash.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(-Mth.lerp(partialTick, slash.xRotO, slash.getXRot())));
        float radius = slash.getRadius();
        PoseStack.Pose pose = poseStack.last();
        ResourceLocation bodyFrame = SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
        ResourceLocation glowFrame = SLASH_FRAMES[(slash.animationTime / 6 + 1) % SLASH_FRAMES.length];
        VertexConsumer body = bufferSource.getBuffer(RenderType.entityTranslucent(bodyFrame));
        drawQuad(body, pose, radius, packedLight, BODY_R, BODY_G, BODY_B, false);
        drawQuad(body, pose, radius, packedLight, BODY_R, BODY_G, BODY_B, false);
        VertexConsumer glow = bufferSource.getBuffer(RenderType.entityTranslucent(glowFrame));
        drawQuad(glow, pose, radius, 15728880, GLOW_R, GLOW_G, GLOW_B, false);
        poseStack.popPose();
        super.render(slash, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void drawQuad(VertexConsumer consumer, PoseStack.Pose pose, float radius, int packedLight,
            float r, float g, float b, boolean reversed) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        float s = radius;
        if (reversed) {
            vertex(consumer, matrix, normal, -s, -0.1F, s, 0.0F, 0.0F, packedLight, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, s, -0.1F, s, 1.0F, 0.0F, packedLight, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, s, -0.1F, -s, 1.0F, 1.0F, packedLight, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, -s, -0.1F, -s, 0.0F, 1.0F, packedLight, r, g, b, 0.0F, -1.0F, 0.0F);
        } else {
            vertex(consumer, matrix, normal, -s, -0.1F, -s, 0.0F, 1.0F, packedLight, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, s, -0.1F, -s, 1.0F, 1.0F, packedLight, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, s, -0.1F, s, 1.0F, 0.0F, packedLight, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, -s, -0.1F, s, 0.0F, 0.0F, packedLight, r, g, b, 0.0F, 1.0F, 0.0F);
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float y, float z, float u, float v, int packedLight, float r, float g, float b,
            float nx, float ny, float nz) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, 1.0F)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(WarlordHellfireSlash slash) {
        return SLASH_FRAMES[slash.animationTime / 4 % SLASH_FRAMES.length];
    }
}
