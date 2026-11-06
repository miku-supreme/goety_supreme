package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AncientSecondRounderArrowEntity;
import cn.blockforge.generated.goetysupreme.renderer.AncientSecondRounderArrowRenderer;

public class AncientSecondRounderArrowRenderer extends ArrowRenderer<AncientSecondRounderArrowEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/projectiles/ancient_second_rounder_arrow.png");

    public AncientSecondRounderArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AncientSecondRounderArrowEntity entity) {
        return TEXTURE;
    }
}
