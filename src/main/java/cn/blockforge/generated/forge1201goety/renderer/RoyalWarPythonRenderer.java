package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.RoyalWarPythonRenderer;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.model.WarPythonModel;

public class RoyalWarPythonRenderer
        extends MobRenderer<RoyalWarPythonServant, WarPythonModel<RoyalWarPythonServant>> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/royal_war_python.png");

    public RoyalWarPythonRenderer(EntityRendererProvider.Context context) {
        super(context, new WarPythonModel<>(context.bakeLayer(WarPythonModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    public void scale(RoyalWarPythonServant entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
        super.scale(entity, poseStack, partialTick);
    }

    @Override
    public float getFlipDegrees(RoyalWarPythonServant entity) {
        return 0.0F;
    }

    @Override
    public int getBlockLightLevel(RoyalWarPythonServant entity, BlockPos pos) {
        return 8;
    }

    @Override
    public ResourceLocation getTextureLocation(RoyalWarPythonServant entity) {
        return LOCATION;
    }
}
