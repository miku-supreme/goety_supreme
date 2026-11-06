package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.WarPythonModel;
import cn.blockforge.generated.goetysupreme.renderer.WarPythonRenderer;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;

public class WarPythonRenderer
        extends MobRenderer<WarPythonServant, WarPythonModel<WarPythonServant>> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/war_python.png");

    public WarPythonRenderer(EntityRendererProvider.Context context) {
        super(context, new WarPythonModel<>(context.bakeLayer(WarPythonModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public float getFlipDegrees(WarPythonServant entity) {
        return 0.0F;
    }

    @Override
    public int getBlockLightLevel(WarPythonServant entity, BlockPos pos) {
        return 8;
    }

    @Override
    public ResourceLocation getTextureLocation(WarPythonServant entity) {
        return LOCATION;
    }
}
