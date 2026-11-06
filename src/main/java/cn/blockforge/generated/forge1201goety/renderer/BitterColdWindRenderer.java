package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.BitterColdWindModel;
import cn.blockforge.generated.goetysupreme.renderer.BitterColdWindRenderer;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;

public class BitterColdWindRenderer
        extends MobRenderer<BitterColdWindServant, BitterColdWindModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/bitter_cold_wind.png");
    private static final ResourceLocation BODY = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/bitter_cold_wind_body.png");

    public BitterColdWindRenderer(EntityRendererProvider.Context context) {
        super(context, new BitterColdWindModel(
                context.bakeLayer(BitterColdWindModel.LAYER)), 0.4F);
        this.addLayer(new WindBodyLayer(this));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public void scale(BitterColdWindServant entity, PoseStack pose, float partialTicks) {
        pose.scale(1.75F, 1.75F, 1.75F);
        super.scale(entity, pose, partialTicks);
    }

    @Override
    public float getFlipDegrees(BitterColdWindServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(BitterColdWindServant entity) {
        return TEXTURE;
    }

    private static class WindBodyLayer
            extends RenderLayer<BitterColdWindServant, BitterColdWindModel> {

        WindBodyLayer(RenderLayerParent<BitterColdWindServant, BitterColdWindModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                BitterColdWindServant entity, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(BODY));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
        }
    }
}
