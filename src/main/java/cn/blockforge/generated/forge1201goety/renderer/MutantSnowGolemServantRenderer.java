package cn.blockforge.generated.goetysupreme.renderer;

import fuzs.mutantmonsters.client.init.ClientModRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantJackOLanternLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantShoulderArmorLayer;

public final class MutantSnowGolemServantRenderer
        extends MobRenderer<MutantSnowGolemServant, MutantSnowGolemServantModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "textures/entity/mutant_snow_golem/mutant_snow_golem.png");

    private static final ResourceLocation UPGRADED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/mutant_snow_golem_upgraded.png");

    public MutantSnowGolemServantRenderer(EntityRendererProvider.Context context) {
        super(context,
                new MutantSnowGolemServantModel(context.bakeLayer(ClientModRegistry.MUTANT_SNOW_GOLEM)),
                0.7F);
        addLayer(new MutantSnowGolemServantShoulderArmorLayer(this, context));
        addLayer(new MutantSnowGolemServantJackOLanternLayer(this, context));
    }

    @Override
    public ResourceLocation getTextureLocation(MutantSnowGolemServant servant) {
        return servant.hasFullyUpgradedTexture() ? UPGRADED_TEXTURE : TEXTURE;
    }
}
