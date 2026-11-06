package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordHelmetLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

public final class WitherWarlordHelmetLayer<T extends AbstractWitherWarlord,
        M extends WitherWarlordModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/models/armor/wither_warlord_layer_1.png");

    private final WitherWarlordHelmetModel model;

    public WitherWarlordHelmetLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new WitherWarlordHelmetModel(modelSet.bakeLayer(WitherWarlordHelmetModel.LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.isInvisible()
                || !entity.getItemBySlot(EquipmentSlot.HEAD).is(GeneratedMod.WITHER_WARLORD_HELMET.get())) {
            return;
        }
        poseStack.pushPose();
        this.getParentModel().applyHeadTransform(poseStack);
        poseStack.translate(0.0D, 2.0D / 16.0D, 0.0D);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.head.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
