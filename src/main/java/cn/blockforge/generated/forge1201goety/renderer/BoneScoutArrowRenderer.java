package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.BoneScoutArrow;
import cn.blockforge.generated.goetysupreme.renderer.BoneScoutArrowRenderer;

public class BoneScoutArrowRenderer extends ArrowRenderer<BoneScoutArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/projectiles/arrow.png");

    public BoneScoutArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BoneScoutArrow entity) {
        return TEXTURE;
    }
}
