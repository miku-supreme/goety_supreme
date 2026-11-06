package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballFireLayer;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballOuterLayer;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.model.FlyingFireballServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FlyingFireballServantRenderer;

public final class FlyingFireballServantRenderer
        extends MobRenderer<FlyingFireballServant, FlyingFireballServantModel<FlyingFireballServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/flying_fireball.png");

    public FlyingFireballServantRenderer(EntityRendererProvider.Context context) {
        super(context, new FlyingFireballServantModel<>(context.bakeLayer(FlyingFireballServantModel.LAYER)), 0.0F);
        this.addLayer(new FlyingFireballOuterLayer<>(this,
                new FlyingFireballServantModel<>(context.bakeLayer(FlyingFireballServantModel.LAYER)),
                FlyingFireballServantModel.OUT_TEXTURE));
        this.addLayer(new FlyingFireballFireLayer<>(this,
                new FlyingFireballServantModel<>(context.bakeLayer(FlyingFireballServantModel.LAYER)),
                FlyingFireballServantModel.FIRE_TEXTURE));
    }

    @Override
    public void scale(FlyingFireballServant entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(0.5F, 0.5F, 0.5F);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public int getBlockLightLevel(FlyingFireballServant entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingFireballServant entity) {
        return TEXTURE;
    }
}
