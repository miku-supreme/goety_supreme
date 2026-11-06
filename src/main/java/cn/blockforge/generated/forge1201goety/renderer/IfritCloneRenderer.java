package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GeneratedRenderTypes;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.model.IfritCloneModel;
import cn.blockforge.generated.goetysupreme.renderer.IfritCloneRenderer;

public class IfritCloneRenderer extends MobRenderer<IfritClone, IfritCloneModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/ifrit/ifrit.png");
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/ifrit/ifrit_glow.png");

    public IfritCloneRenderer(EntityRendererProvider.Context context) {
        super(context, new IfritCloneModel(context.bakeLayer(IfritCloneModel.LAYER)), 0.9F);
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW_TEXTURE,
                entity -> true, GeneratedRenderTypes::wraith));
    }

    @Override
    public ResourceLocation getTextureLocation(IfritClone entity) {
        return TEXTURE;
    }
}
