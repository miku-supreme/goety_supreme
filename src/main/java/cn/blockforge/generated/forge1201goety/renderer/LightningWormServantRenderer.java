package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.LightningWormServant;
import cn.blockforge.generated.goetysupreme.model.LightningWormServantModel;
import cn.blockforge.generated.goetysupreme.renderer.LightningWormServantRenderer;

public class LightningWormServantRenderer extends
        MobRenderer<LightningWormServant, LightningWormServantModel<LightningWormServant>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/lightning_worm.png");

    public LightningWormServantRenderer(EntityRendererProvider.Context context) {
        super(context, new LightningWormServantModel<>(
                context.bakeLayer(LightningWormServantModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public void scale(LightningWormServant entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(2.0F, 2.0F, 2.0F);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public float getFlipDegrees(LightningWormServant entity) {
        return 0.0F;
    }

    @Override
    public int getBlockLightLevel(LightningWormServant entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(LightningWormServant entity) {
        return LOCATION;
    }
}
