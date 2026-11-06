package cn.blockforge.generated.goetysupreme.renderer;

import fuzs.mutantmonsters.client.init.ClientModRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantArmorLayer;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantCrossbowLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSkeletonServantModel;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantRenderer;

public final class MutantSkeletonServantRenderer
        extends MobRenderer<MutantSkeletonServant, MutantSkeletonServantModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "textures/entity/mutant_skeleton.png");
    private static final ResourceLocation HORNED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_skeleton_horned.png");

    public MutantSkeletonServantRenderer(EntityRendererProvider.Context context) {
        super(context,
                new MutantSkeletonServantModel(
                        context.bakeLayer(ClientModRegistry.MUTANT_SKELETON),
                        context.bakeLayer(ClientModRegistry.MUTANT_CROSSBOW)),
                0.6F);
        addLayer(new MutantSkeletonServantCrossbowLayer(this, context));
        addLayer(new MutantSkeletonServantArmorLayer(this, context));
    }

    @Override
    public float getFlipDegrees(MutantSkeletonServant servant) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(MutantSkeletonServant servant) {
        return servant.hasHornedAppearance() ? HORNED_TEXTURE : TEXTURE;
    }
}
