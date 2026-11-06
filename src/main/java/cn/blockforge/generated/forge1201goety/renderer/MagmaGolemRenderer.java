package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.model.MagmaGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.MagmaGolemRenderer;

public final class MagmaGolemRenderer extends MobRenderer<MagmaGolem, MagmaGolemModel> {
    private static final ResourceLocation LAVA =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/magma_golem.png");
    private static final ResourceLocation STONE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/magma_golem_stone.png");

    private static final ResourceLocation GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/magma_golem_glow.png");

    public MagmaGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new MagmaGolemModel(context.bakeLayer(MagmaGolemModel.LAYER)), 0.7F);
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW,
                entity -> !entity.isPetrified() && !entity.isInWater()));
    }

    @Override
    public ResourceLocation getTextureLocation(MagmaGolem entity) {
        return entity.isPetrified() || entity.isInWater() ? STONE : LAVA;
    }
}
