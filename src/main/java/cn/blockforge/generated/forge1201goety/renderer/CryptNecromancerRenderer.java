package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.CryptNecromancerModel;
import cn.blockforge.generated.goetysupreme.renderer.CryptNecromancerRenderer;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public class CryptNecromancerRenderer extends MobRenderer<CryptNecromancerServant, CryptNecromancerModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/crypt_necromancer.png");

    private static final ResourceLocation GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/crypt_necromancer_glow.png");

    private static final float GLOW_BOOST = 0.35F;

    private static final float BASE_SCALE = 1.45F;

    private static final float SIZE_PER_LEVEL = 0.15F;

    public CryptNecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new CryptNecromancerModel(context.bakeLayer(CryptNecromancerModel.LAYER_LOCATION)), 0.6F);
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW, entity -> true,
                RenderType::entityTranslucentEmissive));
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW, entity -> true,
                RenderType::eyes, GLOW_BOOST, GLOW_BOOST, GLOW_BOOST, 1.0F));
    }

    @Override
    public void scale(CryptNecromancerServant entity, PoseStack poseStack, float partialTickTime) {
        float scale = BASE_SCALE + SIZE_PER_LEVEL * Math.max(0, entity.getNecroLevel());
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(CryptNecromancerServant entity) {
        return TEXTURE;
    }
}
