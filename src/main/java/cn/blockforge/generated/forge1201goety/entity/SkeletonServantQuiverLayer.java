package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ally.undead.skeleton.AbstractSkeletonServant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.SkeletonServantQuiverLayer;
import cn.blockforge.generated.goetysupreme.entity.ZombieServantOuterLayer;

public final class SkeletonServantQuiverLayer<T extends AbstractSkeletonServant, M extends SkeletonModel<T>>
        extends RenderLayer<T, M> {

    private static final ResourceLocation GEAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/skeleton_servant_quiver.png");

    public SkeletonServantQuiverLayer(RenderLayerParent<T, M> parent) {
        super(parent);
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
                bufferSource.getBuffer(RenderType.entityCutoutNoCull(GEAR_TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
