package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanHandGlowLayer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.ServantEmissiveGlowLayer;

public final class ServantEmissiveGlowLayer<T extends Entity, M extends EntityModel<T>>
        extends RenderLayer<T, M> {

    private final ResourceLocation maskTexture;

    public ServantEmissiveGlowLayer(RenderLayerParent<T, M> parent, ResourceLocation maskTexture) {
        super(parent);
        this.maskTexture = maskTexture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        if (!(entity instanceof RagingStarServant servant) || !servant.isRagingStarred()) {
            return;
        }
        this.getParentModel().renderToBuffer(poseStack,
                bufferSource.getBuffer(RenderType.eyes(this.maskTexture)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
