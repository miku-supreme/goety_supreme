package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;

public final class WitherServantArmorLayer extends EnergySwirlLayer<WitherServant, WitherServantModel> {
    private static final ResourceLocation WITHER_ARMOR_LOCATION =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/wither/wither_armor.png");
    private final WitherServantModel armorModel;

    public WitherServantArmorLayer(RenderLayerParent<WitherServant, WitherServantModel> parent,
            EntityModelSet modelSet) {
        super(parent);
        this.armorModel = new WitherServantModel(modelSet.bakeLayer(ModelLayers.WITHER_ARMOR));
    }

    @Override
    public float xOffset(float ageInTicks) {
        return Mth.cos(ageInTicks * 0.02F) * 3.0F;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return WITHER_ARMOR_LOCATION;
    }

    @Override
    public EntityModel<WitherServant> model() {
        return armorModel;
    }
}
