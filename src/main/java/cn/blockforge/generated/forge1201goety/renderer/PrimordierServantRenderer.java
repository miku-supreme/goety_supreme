package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.PrimordierServant;
import cn.blockforge.generated.goetysupreme.model.PrimordierServantModel;
import cn.blockforge.generated.goetysupreme.renderer.PrimordierServantRenderer;

public class PrimordierServantRenderer
        extends MobRenderer<PrimordierServant, PrimordierServantModel> {

    public PrimordierServantRenderer(EntityRendererProvider.Context context) {
        super(context, new PrimordierServantModel(context.bakeLayer(PrimordierServantModel.LAYER_LOCATION)), 2.5F);
    }

    @Override
    public float getFlipDegrees(PrimordierServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(PrimordierServant entity) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", entity.servantTexturePath());
    }
}
