package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.RedstoneMonstrosityRenderer;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import com.Polarice3.Goety.config.MobsConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.model.RagingMonstrosityModel;
import cn.blockforge.generated.goetysupreme.renderer.RagingMonstrosityRenderer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.api.SupremeChargeAuraHolder;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;

public final class RagingMonstrosityRenderer
        extends MobRenderer<RedstoneMonstrosity, RagingMonstrosityModel> {

    private static final String GOETY_MONSTROSITY =
            "textures/entity/servants/redstone_monstrosity/";

    private static final ResourceLocation TEXTURE = mod("redstone_monstrosity.png");

    private static final ResourceLocation GLOW = goety("redstone_monstrosity_glow.png");

    private static final ResourceLocation ACTIVE = goety("redstone_monstrosity_active.png");

    private static final ResourceLocation LINES = goety("redstone_monstrosity_lines.png");

    private static final ResourceLocation EYES = goety("redstone_monstrosity_eyes.png");

    private static final ResourceLocation BANDS = goety("redstone_monstrosity_bands.png");

    private final RedstoneMonstrosityRenderer<RedstoneMonstrosity> delegate;

    public RagingMonstrosityRenderer(EntityRendererProvider.Context context) {
        super(context, new RagingMonstrosityModel(context.bakeLayer(RagingMonstrosityModel.LAYER)), 2.0F);
        this.delegate = new RedstoneMonstrosityRenderer<>(context);
        this.addLayer(new LinesLayer(this));
        this.addLayer(new EmissiveLayer(this, GLOW,
                entity -> !entity.isDeadOrDying() && entity.getBigGlow() > 0.0F
                        ? entity.getBigGlow() : 0.0F));
        this.addLayer(new EmissiveLayer(this, ACTIVE,
                entity -> !entity.isActivating() && !entity.isDeadOrDying() && entity.getBigGlow() <= 0.0F
                        ? entity.getMinorGlow() : 0.0F));
        this.addLayer(new EyesLayer(this));
        this.addLayer(new BandsLayer(this));
    }

    private static ResourceLocation goety(String file) {
        return ResourceLocation.fromNamespaceAndPath("goety", GOETY_MONSTROSITY + file);
    }

    private static ResourceLocation mod(String file) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/entity/raging_monstrosity/" + file);
    }

    @Override
    public void render(RedstoneMonstrosity entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        if (!(entity instanceof RagingStarServant servant) || !servant.isRagingStarred()
                || usesGoetyIdlePose(entity)) {
            this.delegate.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            renderChargeAura(entity, partialTicks, poseStack, buffer);
            return;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        renderChargeAura(entity, partialTicks, poseStack, buffer);
    }

    private static void renderChargeAura(RedstoneMonstrosity entity, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer) {
        if (entity instanceof SupremeChargeAuraHolder holder) {
            SupremeChargeAuraRenderer.render(entity, holder.supremeChargeAura(), partialTicks,
                    poseStack, buffer);
        }
    }

    private static boolean usesGoetyIdlePose(RedstoneMonstrosity entity) {
        return entity.isCurrentAnimation(RedstoneMonstrosity.ACTIVATE)
                || entity.isCurrentAnimation(RedstoneMonstrosity.TO_SIT)
                || entity.isCurrentAnimation(RedstoneMonstrosity.SIT)
                || entity.isCurrentAnimation(RedstoneMonstrosity.TO_STAND);
    }

    @Override
    public ResourceLocation getTextureLocation(RedstoneMonstrosity entity) {
        return TEXTURE;
    }

    private static final class LinesLayer extends RenderLayer<RedstoneMonstrosity, RagingMonstrosityModel> {

        LinesLayer(RenderLayerParent<RedstoneMonstrosity, RagingMonstrosityModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                RedstoneMonstrosity entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isInvisible() || entity.isActivating() || entity.isDeadOrDying()) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LINES));
            this.getParentModel().renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static final class EmissiveLayer extends RenderLayer<RedstoneMonstrosity, RagingMonstrosityModel> {

        private interface Alpha {
            float get(RedstoneMonstrosity entity);
        }

        private final ResourceLocation texture;
        private final Alpha alpha;

        EmissiveLayer(RenderLayerParent<RedstoneMonstrosity, RagingMonstrosityModel> parent,
                ResourceLocation texture, Alpha alpha) {
            super(parent);
            this.texture = texture;
            this.alpha = alpha;
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                RedstoneMonstrosity entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isInvisible()) {
                return;
            }
            float value = Mth.clamp(this.alpha.get(entity), 0.0F, 1.0F);
            if (value <= 0.01F) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight,
                    LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, value);
        }
    }

    private static final class EyesLayer extends RenderLayer<RedstoneMonstrosity, RagingMonstrosityModel> {

        EyesLayer(RenderLayerParent<RedstoneMonstrosity, RagingMonstrosityModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                RedstoneMonstrosity entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isInvisible()) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(EYES));
            this.getParentModel().renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static final class BandsLayer extends RenderLayer<RedstoneMonstrosity, RagingMonstrosityModel> {

        BandsLayer(RenderLayerParent<RedstoneMonstrosity, RagingMonstrosityModel> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                RedstoneMonstrosity entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isInvisible() || entity.isHostile()
                    || !MobsConfig.RedstoneMonstrosityTexture.get()) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(BANDS));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
