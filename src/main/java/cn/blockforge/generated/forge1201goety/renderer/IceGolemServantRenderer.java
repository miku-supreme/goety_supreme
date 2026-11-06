package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.BoneParticleFx;
import cn.blockforge.generated.goetysupreme.entity.BoneParticleLayer;
import cn.blockforge.generated.goetysupreme.entity.FrostGolemPumpkinLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.model.IceGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IceGolemServantRenderer;

public final class IceGolemServantRenderer
        extends MobRenderer<IceGolemServant, IceGolemServantModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/ice_golem.png");

    private static final double HAND_LOCAL_Y = 1.72D;

    public IceGolemServantRenderer(EntityRendererProvider.Context context) {
        super(context, new IceGolemServantModel(context.bakeLayer(IceGolemServantModel.LAYER_LOCATION)), 0.7F);
        addLayer(new FrostGolemPumpkinLayer<>(this, context));
        addLayer(new BoneParticleLayer<>(this, IceGolemServantModel::applyLeftHandTransform,
                IceGolemServant::isWindmillSpinAnimating,
                1, 3, 0.11D, 0.0D, HAND_LOCAL_Y, ParticleTypes.SNOWFLAKE));
        addLayer(new BoneParticleLayer<>(this, IceGolemServantModel::applyRightHandTransform,
                IceGolemServant::isWindmillSpinAnimating,
                2, 3, 0.11D, 0.0D, HAND_LOCAL_Y, ParticleTypes.SNOWFLAKE));
    }

    @Override
    public ResourceLocation getTextureLocation(IceGolemServant servant) {
        return TEXTURE;
    }

    @Override
    public void render(IceGolemServant entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        BoneParticleFx.captureFrameBase(entity.getId(), poseStack.last().pose());
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
