package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ModModelLayer;
import com.Polarice3.Goety.client.render.model.SummonCircleModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.ScaledSummonCircle;
import cn.blockforge.generated.goetysupreme.renderer.ScaledSummonCircleRenderer;

public class ScaledSummonCircleRenderer extends EntityRenderer<ScaledSummonCircle> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety", "textures/entity/summon_circle.png");
    private final SummonCircleModel<ScaledSummonCircle> model;

    public ScaledSummonCircleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SummonCircleModel<>(context.bakeLayer(ModModelLayer.SUMMON_CIRCLE));
    }

    @Override
    public void render(ScaledSummonCircle entity, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        float spin = entity.noSpin ? 0.0F : partialTicks / 10.0F;
        this.model.setupAnim(entity, 0.0F, 0.0F, spin, entity.getYRot(), entity.getXRot());
        float scale = entity.visualScale();
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0D, 1.6D, 0.0D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 0.15F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ScaledSummonCircle entity) {
        return TEXTURE;
    }
}
