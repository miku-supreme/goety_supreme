package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.renderer.BulgeServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.model.DroughtWildfireServantModel;
import cn.blockforge.generated.goetysupreme.renderer.DroughtWildfireServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.SedimentEyesLayer;

public final class DroughtWildfireServantRenderer
        extends MobRenderer<DroughtWildfireServant, DroughtWildfireServantModel<DroughtWildfireServant>> {

    private static final ResourceLocation GLOW_LOCATION =
            BulgeServantRenderer.jerotesTexture("textures/entity/elemental/drought_wildfire_glow.png");

    public DroughtWildfireServantRenderer(EntityRendererProvider.Context context) {
        super(context, new DroughtWildfireServantModel<>(context.bakeLayer(DroughtWildfireServantModel.LAYER)),
                0.6F);
        this.addLayer(new DroughtWildfireGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(DroughtWildfireServant entity) {
        return BulgeServantRenderer.jerotesTexture(entity.servantTexturePath());
    }

    @Override
    public float getFlipDegrees(DroughtWildfireServant entity) {
        return 0.0F;
    }

    @Override
    public void scale(DroughtWildfireServant entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.25F, 1.25F, 1.25F);
        super.scale(entity, poseStack, partialTick);
    }

    @Override
    public int getBlockLightLevel(DroughtWildfireServant entity, net.minecraft.core.BlockPos pos) {
        return 15;
    }

    @Override
    public void render(DroughtWildfireServant entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        entity.thisTickRenderTime += 1.0F;
        float idleAnim = entity.getIdleAnim();
        float rollAnim = entity.getRollAnim();
        float frames = entity.lastTickRenderTime + 1.0F;
        float smoothFactor = 1.0F / frames;
        entity.idleAnimProgress = Math.min(10.0F, Mth.lerp(smoothFactor, entity.idleAnimProgress, idleAnim));
        if (!entity.isSpecialAction() && entity.getAttackTick() <= 0 && entity.isAlive()) {
            entity.rollAnimProgress = Mth.lerp(smoothFactor, entity.rollAnimProgress, rollAnim);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static final class DroughtWildfireGlowLayer
            extends net.minecraft.client.renderer.entity.layers.EyesLayer<DroughtWildfireServant,
                    DroughtWildfireServantModel<DroughtWildfireServant>> {

        private DroughtWildfireGlowLayer(
                RenderLayerParent<DroughtWildfireServant, DroughtWildfireServantModel<DroughtWildfireServant>> parent) {
            super(parent);
        }

        @Override
        public RenderType renderType() {
            return RenderType.eyes(GLOW_LOCATION);
        }
    }
}
