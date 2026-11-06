package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.EliteServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentEyesLayer;
import cn.blockforge.generated.goetysupreme.renderer.SedimentLordServantRenderer;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SedimentServantRenderer;

public class SedimentServantRenderer<T extends AbstractSedimentServant>
        extends HumanoidMobRenderer<T, SedimentServantModel<T>> {

    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/resurrecter_eye.png");

    public SedimentServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SedimentServantModel<>(context.bakeLayer(SedimentServantModel.LAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new SedimentEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", entity.servantTexturePath());
    }

    @Override
    public void scale(T entity, PoseStack pose, float partialTicks) {
        pose.scale(1.05F, 1.05F, 1.05F);
        super.scale(entity, pose, partialTicks);
    }

    @Override
    public float getFlipDegrees(T entity) {
        return 0.0F;
    }

    @Override
    public boolean isShaking(T entity) {
        return (entity.deathTime > 0 && entity.deathTime <= 20
                && entity instanceof EliteServant) || super.isShaking(entity);
    }

    @Override
    public void setupRotations(T entity, PoseStack pose, float ageInTicks, float rotationYaw,
            float partialTicks) {
        super.setupRotations(entity, pose, ageInTicks, rotationYaw, partialTicks);
        if (entity.walkAnimation.speed() < 0.01F) {
            return;
        }
        float sway = entity.walkAnimation.speed(partialTicks) + 6.0F;
        float normalized = (Math.abs(sway % 13.0F - 6.5F) - 3.25F) / 3.25F;
        pose.mulPose(Axis.ZP.rotationDegrees(3.5F * normalized));
    }
}
