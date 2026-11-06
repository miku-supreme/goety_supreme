package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.IronGolemServantCrackinessLayer;
import cn.blockforge.generated.goetysupreme.model.IronGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IronGolemServantRenderer;

public final class IronGolemServantRenderer extends MobRenderer<IronGolemServant, IronGolemServantModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public IronGolemServantRenderer(EntityRendererProvider.Context context) {
        super(context, new IronGolemServantModel(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
        this.addLayer(new IronGolemServantCrackinessLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(IronGolemServant servant) {
        return TEXTURE;
    }

    @Override
    public void setupRotations(IronGolemServant servant, PoseStack poseStack, float ageInTicks,
            float rotationYaw, float partialTick) {
        super.setupRotations(servant, poseStack, ageInTicks, rotationYaw, partialTick);
        if (servant.walkAnimation.speed() >= 0.01F) {
            float period = 13.0F;
            float phase = servant.walkAnimation.position(partialTick) + 6.0F;
            float sway = (Math.abs(phase % period - 6.5F) - 3.25F) / 3.25F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * sway));
        }
    }
}
