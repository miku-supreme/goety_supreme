package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.model.SecondRounderAnimatedArmorServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderAnimatedArmorServantRenderer;

public class SecondRounderAnimatedArmorServantRenderer<T extends SecondRounderAnimatedArmorServant>
        extends HumanoidMobRenderer<T, SecondRounderAnimatedArmorServantModel<T>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_animated_armor.png");

    public SecondRounderAnimatedArmorServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SecondRounderAnimatedArmorServantModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.isInSittingPose() ? 0.0F : 0.5F;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public float getFlipDegrees(T entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
