package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.model.IronTeethServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IronTeethServantRenderer;

public final class IronTeethServantRenderer
        extends MobRenderer<IronTeethServant, IronTeethServantModel<IronTeethServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/iron_teeth.png");

    public IronTeethServantRenderer(EntityRendererProvider.Context context) {
        super(context, new IronTeethServantModel<>(context.bakeLayer(IronTeethServantModel.LAYER)), 0.4F);
    }

    @Override
    public void scale(IronTeethServant entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(0.75F, 0.75F, 0.75F);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public float getFlipDegrees(IronTeethServant entity) {
        return 0.0F;
    }

    @Override
    public void setupRotations(IronTeethServant entity, PoseStack poseStack, float ageInTicks,
            float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (entity.walkAnimation.speed() < 0.01F) {
            return;
        }
        float phase = entity.walkAnimation.position(partialTicks) + 6.0F;
        float wobble = (Math.abs(phase % 13.0F - 6.5F) - 3.25F) / 3.25F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * wobble));
    }

    @Override
    public ResourceLocation getTextureLocation(IronTeethServant entity) {
        return TEXTURE;
    }
}
