package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldierOtherLayer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierOtherModel;

public final class SerponColonialSoldierOtherLayer<T extends AbstractSerpon>
        extends RenderLayer<T, SerponColonialSoldierModel<T>> {

    private final SerponColonialSoldierOtherModel<T> otherModel;
    private final ResourceLocation texture;

    public SerponColonialSoldierOtherLayer(RenderLayerParent<T, SerponColonialSoldierModel<T>> parent,
            SerponColonialSoldierOtherModel<T> otherModel, ResourceLocation texture) {
        super(parent);
        this.otherModel = otherModel;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, T entity, float limbSwing,
            float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.otherModel);
        this.otherModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        this.otherModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.texture));
        this.otherModel.renderToBuffer(pose, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
