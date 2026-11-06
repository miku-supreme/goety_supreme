package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.model.PrisonGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.PrisonGolemRenderer;

public final class PrisonGolemRenderer extends MobRenderer<PrisonGolem, PrisonGolemModel> {
    private static final ResourceLocation RED =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prison_golem_red.png");
    private static final ResourceLocation PINK =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prison_golem_pink.png");

    private static final ResourceLocation RED_GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prison_golem_red_glow.png");
    private static final ResourceLocation PINK_GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prison_golem_pink_glow.png");

    private static final double RISE_HEIGHT = 3.2D;

    public PrisonGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new PrisonGolemModel(context.bakeLayer(PrisonGolemModel.LAYER)), 0.7F);
        this.addLayer(new SummonProjectionLayer(this));
        this.addLayer(new EntityEmissiveMaskLayer<>(this,
                entity -> entity.isPink() ? PINK_GLOW : RED_GLOW));
    }

    @Override
    public void scale(PrisonGolem entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(PrisonGolem.SCALE, PrisonGolem.SCALE, PrisonGolem.SCALE);
        super.scale(entity, poseStack, partialTickTime);
    }

    @Override
    public void render(PrisonGolem entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        float rise = entity.summonProgress(partialTicks);
        if (rise < 1.0F) {
            poseStack.pushPose();
            poseStack.translate(0.0D, -(1.0D - rise) * RISE_HEIGHT, 0.0D);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            poseStack.popPose();
        } else {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(PrisonGolem entity) {
        return entity.isPink() ? PINK : RED;
    }

    private final class SummonProjectionLayer extends RenderLayer<PrisonGolem, PrisonGolemModel> {
        private SummonProjectionLayer(PrisonGolemRenderer renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                PrisonGolem entity, float limbSwing, float limbSwingAmount, float partialTick,
                float ageInTicks, float netHeadYaw, float headPitch) {
            float rise = entity.summonProgress(partialTick);
            if (rise >= 1.0F) {
                return;
            }
            float alpha = 0.1F + 0.8F * (1.0F - rise);
            VertexConsumer consumer = buffer.getBuffer(
                    RenderType.entityTranslucent(PrisonGolemRenderer.this.getTextureLocation(entity)));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight,
                    OverlayTexture.NO_OVERLAY, 0.35F, 0.8F, 1.0F, alpha);
        }
    }
}
