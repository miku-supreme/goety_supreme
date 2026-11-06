package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.LightningWormBodyModel;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;
import cn.blockforge.generated.goetysupreme.renderer.LightningWormPartRenderer;
import cn.blockforge.generated.goetysupreme.renderer.LightningWormServantRenderer;

public class LightningWormPartRenderer
        extends LivingEntityRenderer<LightningWormPartEntity, LightningWormBodyModel<LightningWormPartEntity>> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/lightning_worm.png");

    public LightningWormPartRenderer(EntityRendererProvider.Context context) {
        super(context, new LightningWormBodyModel<>(context.bakeLayer(LightningWormBodyModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public float getFlipDegrees(LightningWormPartEntity entity) {
        return 0.0F;
    }

    @Override
    public void scale(LightningWormPartEntity entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(2.0F, 2.0F, 2.0F);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public boolean shouldShowName(LightningWormPartEntity entity) {
        return super.shouldShowName(entity)
                && (entity.hasCustomName() || entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public int getBlockLightLevel(LightningWormPartEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(LightningWormPartEntity entity) {
        return LOCATION;
    }
}
