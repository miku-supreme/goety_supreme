package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.renderer.SerponCombatExecuterGlowLayer;
import cn.blockforge.generated.goetysupreme.model.SerponCombatExecuterModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponCombatExecuterRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponRenderer;
import cn.blockforge.generated.goetysupreme.entity.WeaponAnchorItemInHandLayer;

public class SerponCombatExecuterRenderer
        extends MobRenderer<SerponCombatExecuter, SerponCombatExecuterModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/serpon/serpon_combat_executer.png");
    private static final ResourceLocation GLOW = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/serpon/serpon_combat_executer_glow.png");

    public SerponCombatExecuterRenderer(EntityRendererProvider.Context context) {
        super(context, new SerponCombatExecuterModel(
                context.bakeLayer(SerponCombatExecuterModel.LAYER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new WeaponAnchorItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new SerponCombatExecuterGlowLayer(this, GLOW));
    }

    @Override
    public void scale(SerponCombatExecuter entity, PoseStack pose, float partialTicks) {
        pose.scale(1.05F, 1.05F, 1.05F);
        super.scale(entity, pose, partialTicks);
    }

    @Override
    public float getFlipDegrees(SerponCombatExecuter entity) {
        return 0.0F;
    }

    @Override
    public int getBlockLightLevel(SerponCombatExecuter entity, BlockPos pos) {
        return 9;
    }

    @Override
    public ResourceLocation getTextureLocation(SerponCombatExecuter entity) {
        return TEXTURE;
    }
}
