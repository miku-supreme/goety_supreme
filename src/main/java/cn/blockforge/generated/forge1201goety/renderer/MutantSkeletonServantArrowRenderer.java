package cn.blockforge.generated.goetysupreme.renderer;

import fuzs.mutantmonsters.client.renderer.entity.MutantArrowRenderer;
import fuzs.mutantmonsters.world.entity.projectile.MutantArrow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantArrowRenderer;

public final class MutantSkeletonServantArrowRenderer extends MutantArrowRenderer {
    private static final ResourceLocation GILDED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_arrow_gilded.png");

    public MutantSkeletonServantArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MutantArrow arrow) {
        return arrow instanceof MutantSkeletonServantArrow servantArrow && servantArrow.isGilded()
                ? GILDED_TEXTURE : super.getTextureLocation(arrow);
    }
}
