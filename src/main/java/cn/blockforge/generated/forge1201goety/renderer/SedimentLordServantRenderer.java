package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SedimentEyesLayer;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordItemInHandLayer;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.renderer.SedimentLordServantRenderer;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SedimentServantRenderer;

public class SedimentLordServantRenderer
        extends MobRenderer<SedimentLordServant, SedimentServantModel<SedimentLordServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/sediment_lord.png");
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/sediment_lord_eye.png");

    public SedimentLordServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SedimentServantModel<>(
                context.bakeLayer(SedimentServantModel.LAYER), true), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new SedimentLordItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new SedimentEyesLayer<>(this, EYES));
    }

    @Override
    public void scale(SedimentLordServant entity, PoseStack pose, float partialTicks) {
        pose.scale(1.05F, 1.05F, 1.05F);
        super.scale(entity, pose, partialTicks);
    }

    @Override
    public float getFlipDegrees(SedimentLordServant entity) {
        return 0.0F;
    }

    @Override
    public void setupRotations(SedimentLordServant entity, PoseStack pose, float ageInTicks,
            float rotationYaw, float partialTicks) {
        super.setupRotations(entity, pose, ageInTicks, rotationYaw, partialTicks);
        if (entity.walkAnimation.speed() < 0.01F) {
            return;
        }
        float sway = entity.walkAnimation.speed(partialTicks) + 6.0F;
        float normalized = (Math.abs(sway % 13.0F - 6.5F) - 3.25F) / 3.25F;
        pose.mulPose(Axis.ZP.rotationDegrees(1.5F * normalized));
    }

    @Override
    public ResourceLocation getTextureLocation(SedimentLordServant entity) {
        return TEXTURE;
    }
}
