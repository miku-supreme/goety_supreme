package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.render.model.ZombieServantModel;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.ZombieServantOuterLayer;

public final class ZombieServantOuterLayer<T extends ZombieServant, M extends ZombieServantModel<T>>
        extends RenderLayer<T, M> {

    private static final ResourceLocation OUTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/zombie_servant_butcher_outer.png");

    public ZombieServantOuterLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (!(entity instanceof GoetyZombieServant zombie)
                || zombie.hasBerserkTexture()
                || entity.isInvisible()) {
            return;
        }
        if (!(entity instanceof RagingStarServant star) || !star.isRagingStarred()) {
            return;
        }
        this.getParentModel().renderToBuffer(poseStack,
                bufferSource.getBuffer(RenderType.entityCutoutNoCull(OUTER_TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
