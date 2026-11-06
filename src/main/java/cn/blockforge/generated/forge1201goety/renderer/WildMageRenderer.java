package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.WildMageModel;
import cn.blockforge.generated.goetysupreme.renderer.WildMageRenderer;

public class WildMageRenderer extends MobRenderer<AbstractWildMage, WildMageModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/wild_mage.png");

    public WildMageRenderer(EntityRendererProvider.Context context) {
        super(context, new WildMageModel(context.bakeLayer(WildMageModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractWildMage entity) {
        return TEXTURE;
    }

    @Override
    public void scale(AbstractWildMage entity, PoseStack poseStack, float partialTick) {
        float scale = entity.soulJarSizeScale();
        if (scale != 1.0F) {
            poseStack.scale(scale, scale, scale);
        }
    }
}
