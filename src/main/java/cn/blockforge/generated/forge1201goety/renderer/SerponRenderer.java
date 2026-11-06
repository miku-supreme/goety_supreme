package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.entity.EliteServant;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldierGlowLayer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldierOtherLayer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierOtherModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponRenderer;

public final class SerponRenderer<T extends AbstractSerpon>
        extends HumanoidMobRenderer<T, SerponColonialSoldierModel<T>> {

    public SerponRenderer(EntityRendererProvider.Context context,
            ResourceLocation otherTexture, ResourceLocation glowTexture) {
        super(context, new SerponColonialSoldierModel<>(
                context.bakeLayer(SerponColonialSoldierModel.LAYER)), 0.55F);
        this.addLayer(new SerponColonialSoldierGlowLayer<>(this, glowTexture));
        this.addLayer(new SerponColonialSoldierOtherLayer<>(this,
                new SerponColonialSoldierOtherModel<>(
                        context.bakeLayer(SerponColonialSoldierOtherModel.LAYER)), otherTexture));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", entity.servantTexturePath());
    }

    @Override
    public float getFlipDegrees(T entity) {
        return 0.0F;
    }

    @Override
    public boolean isShaking(T entity) {
        return entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteServant
                || super.isShaking(entity);
    }
}
