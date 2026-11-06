package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.HorseServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.renderer.WitherSkeletonHorseServantRenderer;

public final class WitherSkeletonHorseServantRenderer
        extends MobRenderer<WitherSkeletonHorseServant,
                HorseServantModel<WitherSkeletonHorseServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/wither_skeleton_horse_servant.png");

    public WitherSkeletonHorseServantRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseServantModel<>(context.bakeLayer(HorseServantModel.LAYER)), 0.9F);
        this.addLayer(new HorseServantArmorLayer<>(this, context.getModelSet()));
    }

    @Override
    public void scale(WitherSkeletonHorseServant entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(WitherSkeletonHorseServant.RENDER_SCALE, WitherSkeletonHorseServant.RENDER_SCALE,
                WitherSkeletonHorseServant.RENDER_SCALE);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherSkeletonHorseServant entity) {
        return TEXTURE;
    }
}
