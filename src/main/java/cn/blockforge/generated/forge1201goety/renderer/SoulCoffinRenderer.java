package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.model.SoulCoffinModel;
import cn.blockforge.generated.goetysupreme.renderer.SoulCoffinRenderer;

public class SoulCoffinRenderer extends MobRenderer<SoulCoffin, SoulCoffinModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/soul_coffin.png");

    public SoulCoffinRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulCoffinModel(context.bakeLayer(SoulCoffinModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulCoffin entity) {
        return TEXTURE;
    }
}
