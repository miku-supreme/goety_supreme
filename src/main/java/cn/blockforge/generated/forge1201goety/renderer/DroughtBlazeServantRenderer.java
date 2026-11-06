package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.BulgeServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.renderer.DroughtBlazeServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class DroughtBlazeServantRenderer
        extends MobRenderer<DroughtBlazeServant, BlazeModel<DroughtBlazeServant>> {

    public DroughtBlazeServantRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeModel<>(context.bakeLayer(ModelLayers.BLAZE)), 0.5F);
    }

    @Override
    public void scale(DroughtBlazeServant entity, PoseStack pose, float partialTick) {
        float t = ServantDeathPose.progress(entity, partialTick);
        float shrink = 1.0F - 0.6F * t;
        pose.scale(shrink, shrink, shrink);
        pose.translate(0.0D, -0.35D * t, 0.0D);
        super.scale(entity, pose, partialTick);
    }

    @Override
    public float getFlipDegrees(DroughtBlazeServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(DroughtBlazeServant entity) {
        return BulgeServantRenderer.jerotesTexture(entity.servantTexturePath());
    }

    @Override
    public int getBlockLightLevel(DroughtBlazeServant entity, net.minecraft.core.BlockPos pos) {
        return 15;
    }
}
