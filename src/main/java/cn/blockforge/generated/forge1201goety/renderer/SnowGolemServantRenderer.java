package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemServantHeadLayer;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemServantRenderer;

public final class SnowGolemServantRenderer extends MobRenderer<SnowGolemServant, SnowGolemModel<SnowGolemServant>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/snow_golem.png");

    public SnowGolemServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowGolemModel<>(context.bakeLayer(ModelLayers.SNOW_GOLEM)), 0.5F);
        addLayer(new SnowGolemServantHeadLayer(this, context));
    }

    @Override
    public ResourceLocation getTextureLocation(SnowGolemServant servant) {
        return TEXTURE;
    }
}
