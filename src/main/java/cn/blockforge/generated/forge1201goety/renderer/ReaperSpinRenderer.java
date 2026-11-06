package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBladeRenderer;
import cn.blockforge.generated.goetysupreme.model.ReaperScytheModel;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.renderer.ReaperSpinRenderer;

public class ReaperSpinRenderer extends EntityRenderer<ReaperSpinEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/reaper_necromancer.png");

    private static final float ALPHA = 0.4F;

    private static final float GLOW_R = 0.45F;
    private static final float GLOW_G = 0.9F;
    private static final float GLOW_B = 1.0F;

    private static final float ROOT_X = 0.309F;
    private static final float ROOT_Y = 0.736F;
    private static final float ROOT_Z = -0.020F;

    private static final float PIVOT_X = 0.00337F;
    private static final float PIVOT_Y = 0.44796F;
    private static final float PIVOT_Z = 0.13524F;

    private static final float FLATTEN_DEGREES = 90.0F;

    private final ReaperScytheModel model;

    public ReaperSpinRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ReaperScytheModel(context.bakeLayer(ReaperScytheModel.LAYER_LOCATION));
    }

    @Override
    public void render(ReaperSpinEntity spin, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float yaw = 180.0F - Mth.lerp(partialTick, spin.yRotO, spin.getYRot());
        float pitch = Mth.lerp(partialTick, spin.xRotO, spin.getXRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-ROOT_X, -ROOT_Y, -ROOT_Z);
        poseStack.translate(-PIVOT_X, -PIVOT_Y, -PIVOT_Z);
        poseStack.mulPose(Axis.YP.rotationDegrees(spin.spinDegrees(partialTick)));
        poseStack.mulPose(Axis.ZP.rotationDegrees(FLATTEN_DEGREES));
        float widthScale = spin.getWidthScale();
        if (widthScale != 1.0F) {
            poseStack.scale(widthScale, 1.0F, widthScale);
        }
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE));
        this.model.setupAnim(spin, 0.0F, 0.0F, spin.tickCount + partialTick, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, ALPHA);
        VertexConsumer glow = bufferSource.getBuffer(RenderType.eyes(TEXTURE));
        this.model.renderToBuffer(poseStack, glow, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                GLOW_R, GLOW_G, GLOW_B, 1.0F);
        poseStack.popPose();
        super.render(spin, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ReaperSpinEntity spin) {
        return TEXTURE;
    }
}
