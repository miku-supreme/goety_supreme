package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballOuterLayer;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.model.FlyingFireballServantModel;
import cn.blockforge.generated.goetysupreme.util.ScrollUvVertexConsumer;

public final class FlyingFireballOuterLayer<T extends FlyingFireballServant,
        M extends FlyingFireballServantModel<T>> extends RenderLayer<T, M> {

    private final FlyingFireballServantModel<T> model;
    private final ResourceLocation texture;

    public FlyingFireballOuterLayer(RenderLayerParent<T, M> parent,
            FlyingFireballServantModel<T> model, ResourceLocation texture) {
        super(parent);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float scroll = ((entity.tickCount + partialTicks) * 0.01F) % 1.0F;
        VertexConsumer consumer = new ScrollUvVertexConsumer(
                buffer.getBuffer(RenderType.entityTranslucentEmissive(this.texture)), 0.0F, scroll);
        this.model.renderToBuffer(poseStack, consumer, packedLight,
                LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
