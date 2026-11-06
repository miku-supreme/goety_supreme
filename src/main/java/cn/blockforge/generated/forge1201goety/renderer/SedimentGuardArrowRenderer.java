package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardArrow;
import cn.blockforge.generated.goetysupreme.renderer.SedimentGuardArrowRenderer;

public class SedimentGuardArrowRenderer extends ArrowRenderer<SedimentGuardArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/projectiles/arrow.png");

    public SedimentGuardArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SedimentGuardArrow entity) {
        return TEXTURE;
    }
}
