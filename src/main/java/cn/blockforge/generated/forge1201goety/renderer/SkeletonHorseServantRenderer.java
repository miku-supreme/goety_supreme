package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.HorseServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.renderer.SkeletonHorseServantRenderer;

public class SkeletonHorseServantRenderer
        extends MobRenderer<SkeletonHorseServant, HorseServantModel<SkeletonHorseServant>> {

    public static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_skeleton.png");

    public SkeletonHorseServantRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseServantModel<>(context.bakeLayer(HorseServantModel.LAYER)), 0.75F);
        this.addLayer(new HorseServantArmorLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonHorseServant entity) {
        return TEXTURE;
    }
}
