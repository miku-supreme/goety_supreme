package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.FrostYetiServant;
import cn.blockforge.generated.goetysupreme.model.FrostYetiServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FrostYetiServantRenderer;

public class FrostYetiServantRenderer
        extends MobRenderer<FrostYetiServant, FrostYetiServantModel> {

    private static final String ANGRY = "textures/entity/frost_yeti_angry.png";

    public FrostYetiServantRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostYetiServantModel(context.bakeLayer(FrostYetiServantModel.LAYER_LOCATION)), 1.1F);
    }

    @Override
    public void scale(FrostYetiServant entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
        super.scale(entity, poseStack, partialTick);
    }

    @Override
    public float getFlipDegrees(FrostYetiServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(FrostYetiServant entity) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage",
                entity.isAggressive() ? ANGRY : entity.servantTexturePath());
    }
}
