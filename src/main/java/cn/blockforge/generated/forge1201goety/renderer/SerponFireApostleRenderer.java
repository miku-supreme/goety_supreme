package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireApostleAnimation;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireApostleGlowLayer;
import cn.blockforge.generated.goetysupreme.model.SerponFireApostleModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireApostleRenderer;
import cn.blockforge.generated.goetysupreme.entity.WeaponAnchorItemInHandLayer;

public class SerponFireApostleRenderer
        extends MobRenderer<SerponFireApostle, SerponFireApostleModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/serpon/serpon_fire_apostle.png");
    private static final ResourceLocation GLOW = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/serpon/serpon_fire_apostle_glow.png");

    public SerponFireApostleRenderer(EntityRendererProvider.Context context) {
        super(context, new SerponFireApostleModel(
                context.bakeLayer(SerponFireApostleModel.LAYER)), 0.5F);
        this.addLayer(new SerponFireApostleGlowLayer(this, GLOW));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new WeaponAnchorItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public void render(SerponFireApostle entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        entity.thisTickRenderTime += 1.0F;
        float smooth = 1.0F / (entity.lastTickRenderTime + 1.0F);
        entity.attackAnimProgress = Mth.lerp(smooth, entity.attackAnimProgress, entity.getAttackAnim());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public float getFlipDegrees(SerponFireApostle entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(SerponFireApostle entity) {
        return TEXTURE;
    }
}
