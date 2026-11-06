package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.AbominableSnowGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.FrostGolemPumpkinLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class AbominableSnowGolemServantRenderer
        extends MobRenderer<AbominableSnowGolemServant,
                AbominableSnowGolemModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/abominable_snowman.png");

    private static final float SCALE = 2.0F;

    public AbominableSnowGolemServantRenderer(EntityRendererProvider.Context context) {
        super(context, new AbominableSnowGolemModel(context.bakeLayer(AbominableSnowGolemModel.LAYER)),
                1.75F);
        addLayer(new FrostGolemPumpkinLayer<>(this, context));
    }

    @Override
    public void scale(AbominableSnowGolemServant entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(SCALE, SCALE, SCALE);
        super.scale(entity, poseStack, partialTickTime);
    }

    @Override
    public ResourceLocation getTextureLocation(AbominableSnowGolemServant servant) {
        return TEXTURE;
    }
}
