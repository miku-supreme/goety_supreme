package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.BoneParticleFx;
import cn.blockforge.generated.goetysupreme.entity.BoneParticleLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GeneratedRenderTypes;
import cn.blockforge.generated.goetysupreme.renderer.ReaperNecromancerRenderer;
import cn.blockforge.generated.goetysupreme.model.SoulReaperModel;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperRenderer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

public class SoulReaperRenderer extends MobRenderer<AbstractSoulReaper, SoulReaperModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/soul_reaper.png");

    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/soul_reaper_glow.png");

    private static final float BASE_SIZE_SCALE = 1.14F;

    private static final float MIRROR_X = 1.0F; // 负缩放会引发移动时明暗跳变，故不镜像

    public SoulReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulReaperModel(context.bakeLayer(SoulReaperModel.LAYER_LOCATION)), 0.6F);
        this.addLayer(new SoulReaperGlowLayer(this));
        this.addLayer(new BoneParticleLayer<>(this, SoulReaperModel::applyScytheTransform,
                entity -> entity.getCastAnimation() == AbstractSoulReaper.CAST_ATTACK,
                0, 2, 0.12D, 0.01D, 0.0D,
                ParticleTypes.SOUL, ParticleTypes.SOUL_FIRE_FLAME));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSoulReaper entity) {
        return TEXTURE;
    }

    @Override
    public void render(AbstractSoulReaper entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        BoneParticleFx.captureFrameBase(entity.getId(), poseStack.last().pose());
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public float getFlipDegrees(AbstractSoulReaper entity) {
        return 0.0F;
    }

    @Override
    public void scale(AbstractSoulReaper entity, PoseStack poseStack, float partialTick) {
        float scale = BASE_SIZE_SCALE * entity.growthSizeScale();
        poseStack.scale(MIRROR_X * scale, scale, scale);
    }

    private static final class SoulReaperGlowLayer
            extends EyesLayer<AbstractSoulReaper, SoulReaperModel> {

        private SoulReaperGlowLayer(RenderLayerParent<AbstractSoulReaper, SoulReaperModel> parent) {
            super(parent);
        }

        @Override
        public RenderType renderType() {
            return GeneratedRenderTypes.wraith(GLOW_TEXTURE);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                AbstractSoulReaper entity, float limbSwing, float limbSwingAmount, float partialTick,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.deathTime < 12) {
                super.render(poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount,
                        partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }
    }
}
