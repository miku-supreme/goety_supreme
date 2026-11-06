package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordArmorLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

public final class WitherWarlordArmorLayer<T extends AbstractWitherWarlord,
        M extends WitherWarlordModel<T>> extends EnergySwirlLayer<T, M> {

    private static final ResourceLocation WITHER_ARMOR_LOCATION =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/wither/wither_armor.png");

    private final WitherWarlordModel<T> armorModel;

    public WitherWarlordArmorLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.armorModel = new WitherWarlordModel<>(modelSet.bakeLayer(WitherWarlordModel.LAYER));
        this.armorModel.setHelmetEnabled(false);
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
    public EntityModel<T> model() {
        return this.armorModel;
    }
}
