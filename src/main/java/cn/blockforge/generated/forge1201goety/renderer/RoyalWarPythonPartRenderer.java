package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.RoyalWarPythonPartRenderer;
import cn.blockforge.generated.goetysupreme.model.WarPythonBodyModel;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;

public class RoyalWarPythonPartRenderer
        extends LivingEntityRenderer<WarPythonPartEntity, WarPythonBodyModel<WarPythonPartEntity>> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/royal_war_python.png");

    public RoyalWarPythonPartRenderer(EntityRendererProvider.Context context) {
        super(context, new WarPythonBodyModel<>(context.bakeLayer(WarPythonBodyModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    public void scale(WarPythonPartEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
        super.scale(entity, poseStack, partialTick);
    }

    @Override
    public float getFlipDegrees(WarPythonPartEntity entity) {
        return 0.0F;
    }

    @Override
    public boolean shouldShowName(WarPythonPartEntity entity) {
        return super.shouldShowName(entity)
                && (entity.hasCustomName() || entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public int getBlockLightLevel(WarPythonPartEntity entity, BlockPos pos) {
        return 8;
    }

    @Override
    public ResourceLocation getTextureLocation(WarPythonPartEntity entity) {
        return LOCATION;
    }
}
