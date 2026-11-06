package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.model.CorrosiverServantModel;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverServantRenderer;

public class CorrosiverServantRenderer extends
        MobRenderer<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/corrosiver/corrosiver.png");
    private static final ResourceLocation ANGRY = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/corrosiver/corrosiver_angry.png");
    private static final ResourceLocation WIND = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/corrosiver/corrosiver_wind.png");
    private static final ResourceLocation HURT = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/corrosiver/corrosiver_hurt.png");

    private static final ResourceLocation TOUGH_GLINT = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/tough_glint_corrosiver.png");

    public CorrosiverServantRenderer(EntityRendererProvider.Context context) {
        super(context, new CorrosiverServantModel<>(
                context.bakeLayer(CorrosiverServantModel.LAYER_LOCATION)), 1.3F);
        this.addLayer(new AngryLayer(this, context.getModelSet()));
        this.addLayer(new WindLayer(this, context.getModelSet()));
        this.addLayer(new HurtLayer(this, context.getModelSet()));
    }

    @Override
    public void render(CorrosiverServant entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        entity.thisTickRenderTime += 1.0F;
        float smooth = 1.0F / (entity.lastTickRenderTime + 1.0F);
        entity.windAnimProgress = Mth.lerp(smooth, entity.windAnimProgress, entity.getWindAnim());
        entity.angryAnimProgress = Mth.lerp(smooth, entity.angryAnimProgress, entity.getAngryAnim());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public void setupRotations(CorrosiverServant entity, PoseStack poseStack, float ageInTicks,
            float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (entity.walkAnimation.speed() < 0.01F) {
            return;
        }
        float f5 = entity.walkAnimation.position(partialTicks) + 6.0F;
        float f6 = (Math.abs(f5 % 13.0F - 6.5F) - 3.25F) / 3.25F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.5F * f6));
    }

    @Override
    public float getFlipDegrees(CorrosiverServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(CorrosiverServant entity) {
        return TEXTURE;
    }

    private static class AngryLayer
            extends RenderLayer<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> {
        private final CorrosiverServantModel<CorrosiverServant> model;

        AngryLayer(RenderLayerParent<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> parent,
                EntityModelSet modelSet) {
            super(parent);
            this.model = new CorrosiverServantModel<>(modelSet.bakeLayer(CorrosiverServantModel.LAYER_LOCATION));
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CorrosiverServant entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (entity.getWindAnim() <= 0) {
                return;
            }
            float alpha = Mth.clamp(entity.angryAnimProgress / 10.0F, 0.0F, 1.0F);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(ANGRY));
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, alpha);
        }
    }

    private static class WindLayer
            extends RenderLayer<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> {
        private final CorrosiverServantModel<CorrosiverServant> model;

        WindLayer(RenderLayerParent<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> parent,
                EntityModelSet modelSet) {
            super(parent);
            this.model = new CorrosiverServantModel<>(modelSet.bakeLayer(CorrosiverServantModel.LAYER_LOCATION));
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CorrosiverServant entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (entity.getWindAnim() <= 0) {
                return;
            }
            float alpha = Mth.clamp(entity.windAnimProgress / 10.0F, 0.0F, 1.0F);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(WIND));
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, alpha);
        }
    }

    private static class HurtLayer
            extends RenderLayer<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> {
        private final CorrosiverServantModel<CorrosiverServant> model;

        HurtLayer(RenderLayerParent<CorrosiverServant, CorrosiverServantModel<CorrosiverServant>> parent,
                EntityModelSet modelSet) {
            super(parent);
            this.model = new CorrosiverServantModel<>(modelSet.bakeLayer(CorrosiverServantModel.LAYER_LOCATION));
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CorrosiverServant entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (!entity.isBeastTough()) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(HURT));
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
            VertexConsumer glint = buffer.getBuffer(RenderType.entityTranslucentEmissive(TOUGH_GLINT));
            this.model.renderToBuffer(poseStack, glint, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 0.0F, 1.0F, 0.1F);
        }
    }
}
