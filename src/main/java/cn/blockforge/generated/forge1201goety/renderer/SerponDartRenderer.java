package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SerponDartEntity;
import cn.blockforge.generated.goetysupreme.renderer.SerponDartRenderer;

public class SerponDartRenderer extends ArrowRenderer<SerponDartEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/serpon_dart.png");

    public SerponDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SerponDartEntity entity) {
        return TEXTURE;
    }
}
