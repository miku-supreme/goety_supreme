package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.IronGolemServantCrackinessLayer;
import cn.blockforge.generated.goetysupreme.model.IronGolemServantModel;

@OnlyIn(Dist.CLIENT)
public final class IronGolemServantCrackinessLayer
        extends RenderLayer<IronGolemServant, IronGolemServantModel> {

    private static final ResourceLocation LOW =
            ResourceLocation.fromNamespaceAndPath("minecraft",
                    "textures/entity/iron_golem/iron_golem_crackiness_low.png");
    private static final ResourceLocation MEDIUM =
            ResourceLocation.fromNamespaceAndPath("minecraft",
                    "textures/entity/iron_golem/iron_golem_crackiness_medium.png");
    private static final ResourceLocation HIGH =
            ResourceLocation.fromNamespaceAndPath("minecraft",
                    "textures/entity/iron_golem/iron_golem_crackiness_high.png");

    public IronGolemServantCrackinessLayer(
            RenderLayerParent<IronGolemServant, IronGolemServantModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
            IronGolemServant servant, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (servant.isInvisible()) {
            return;
        }
        float health = servant.getHealth() / servant.getMaxHealth();
        ResourceLocation texture = health < 0.25F ? HIGH : (health < 0.5F ? MEDIUM
                : (health < 0.75F ? LOW : null));
        if (texture == null) {
            return;
        }
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight,
                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
