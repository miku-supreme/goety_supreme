package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.HorseServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;

public final class HorseServantArmorLayer<T extends SkeletonHorseServant,
        M extends HorseServantModel<T>> extends RenderLayer<T, M> {

    private final HorseServantModel<T> armorModel;

    private static final ResourceLocation NETHERITE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/netherite_horse_armor.png");

    public HorseServantArmorLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.armorModel = new HorseServantModel<>(modelSet.bakeLayer(ModelLayers.HORSE_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getHorseArmor();
        boolean netherite = entity.hasNetheriteArmor();
        if (!netherite && !(stack.getItem() instanceof HorseArmorItem)) {
            return;
        }
        this.armorModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (netherite) {
            VertexConsumer netheriteConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(NETHERITE_TEXTURE));
            this.armorModel.renderToBuffer(poseStack, netheriteConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        HorseArmorItem armorItem = (HorseArmorItem) stack.getItem();
        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;
        if (armorItem instanceof DyeableHorseArmorItem dyeable) {
            int color = dyeable.getColor(stack);
            red = (color >> 16 & 0xFF) / 255.0F;
            green = (color >> 8 & 0xFF) / 255.0F;
            blue = (color & 0xFF) / 255.0F;
        }
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(armorItem.getTexture()));
        this.armorModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                red, green, blue, 1.0F);
    }
}
