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
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.renderer.BoneParticleFx;
import cn.blockforge.generated.goetysupreme.entity.BoneParticleLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GeneratedRenderTypes;
import cn.blockforge.generated.goetysupreme.model.ReaperNecromancerModel;
import cn.blockforge.generated.goetysupreme.renderer.ReaperNecromancerRenderer;

public class ReaperNecromancerRenderer extends MobRenderer<AbstractReaperNecromancer, ReaperNecromancerModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/reaper_necromancer.png");

    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/reaper_necromancer_glow.png");

    private static final float BASE_SIZE_SCALE = 1.20F;

    private static final float MIRROR_X = 1.0F;

    public ReaperNecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new ReaperNecromancerModel(context.bakeLayer(ReaperNecromancerModel.LAYER_LOCATION)), 0.6F);
        this.addLayer(new ReaperGlowLayer(this));
        this.addLayer(new BoneParticleLayer<>(this, ReaperNecromancerModel::applyScytheTransform,
                entity -> entity.getCastAnimation() == AbstractReaperNecromancer.CAST_ATTACK,
                0, 2, 0.12D, 0.01D, 0.0D,
                ParticleTypes.SOUL, ParticleTypes.SOUL_FIRE_FLAME));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractReaperNecromancer entity) {
        return TEXTURE;
    }

    @Override
    public void render(AbstractReaperNecromancer entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        BoneParticleFx.captureFrameBase(entity.getId(), poseStack.last().pose());
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public float getFlipDegrees(AbstractReaperNecromancer entity) {
        return 0.0F;
    }

    @Override
    public void scale(AbstractReaperNecromancer entity, PoseStack poseStack, float partialTick) {
        float scale = BASE_SIZE_SCALE + (entity.soulJarSizeScale() - 1.0F);
        poseStack.scale(MIRROR_X * scale, scale, scale);
    }

    private static final class ReaperGlowLayer
            extends EyesLayer<AbstractReaperNecromancer, ReaperNecromancerModel> {

        private ReaperGlowLayer(RenderLayerParent<AbstractReaperNecromancer, ReaperNecromancerModel> parent) {
            super(parent);
        }

        @Override
        public RenderType renderType() {
            return GeneratedRenderTypes.wraith(GLOW_TEXTURE);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                AbstractReaperNecromancer entity, float limbSwing, float limbSwingAmount, float partialTick,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.deathTime < 12) {
                super.render(poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount,
                        partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }
    }
}
