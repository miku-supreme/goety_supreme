package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.model.BulgeServantModel;
import cn.blockforge.generated.goetysupreme.renderer.BulgeServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.model.SeaWaveServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SedimentServantRenderer;

public final class BulgeServantRenderer {

    private BulgeServantRenderer() {
    }

    public static ResourceLocation jerotesTexture(String path) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", path);
    }

    public static final class Bulge extends MobRenderer<BulgeServant, BulgeServantModel<BulgeServant>> {
        public Bulge(EntityRendererProvider.Context context) {
            super(context, new BulgeServantModel<>(context.bakeLayer(BulgeServantModel.LAYER)), 0.5F);
        }

        @Override
        public float getFlipDegrees(BulgeServant entity) {
            return 0.0F;
        }

        @Override
        public ResourceLocation getTextureLocation(BulgeServant entity) {
            return jerotesTexture(entity.servantTexturePath());
        }
    }

    public static final class SeaWave extends MobRenderer<SeaWaveServant, SeaWaveServantModel<SeaWaveServant>> {
        public SeaWave(EntityRendererProvider.Context context) {
            super(context, new SeaWaveServantModel<>(context.bakeLayer(SeaWaveServantModel.LAYER)), 0.6F);
        }

        @Override
        public float getFlipDegrees(SeaWaveServant entity) {
            return 0.0F;
        }

        @Override
        public void scale(SeaWaveServant entity, com.mojang.blaze3d.vertex.PoseStack poseStack,
                float partialTick) {
            poseStack.scale(1.25F, 1.25F, 1.25F);
            super.scale(entity, poseStack, partialTick);
        }

        @Override
        public void render(SeaWaveServant entity, float entityYaw, float partialTick,
                com.mojang.blaze3d.vertex.PoseStack poseStack,
                net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
            entity.thisTickRenderTime += 1.0F;
            float idleAnim = entity.getIdleAnim();
            float rollAnim = entity.getRollAnim();
            float frames = entity.lastTickRenderTime + 1.0F;
            float smoothFactor = 1.0F / frames;
            entity.idleAnimProgress = Math.min(10.0F,
                    net.minecraft.util.Mth.lerp(smoothFactor, entity.idleAnimProgress, idleAnim));
            if (!entity.specialAction() && entity.getAttackTick() <= 0 && entity.isAlive()) {
                entity.rollAnimProgress = net.minecraft.util.Mth.lerp(smoothFactor,
                        entity.rollAnimProgress, rollAnim);
            }
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }

        @Override
        public ResourceLocation getTextureLocation(SeaWaveServant entity) {
            return jerotesTexture(entity.servantTexturePath());
        }
    }
}
