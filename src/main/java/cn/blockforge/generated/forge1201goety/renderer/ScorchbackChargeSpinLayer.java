package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackChargeSpinLayer;
import cn.blockforge.generated.goetysupreme.model.ScorchbackModel;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackRenderer;

@OnlyIn(Dist.CLIENT)
public class ScorchbackChargeSpinLayer extends RenderLayer<Scorchback, ScorchbackModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/scorchback/scorchback_charge.png");

    private static final int RINGS = 4;

    private static final float RING_SCALE = 0.75F;

    private static final float RING_SPIN = 52.0F;

    private static final float RING_RISE = 0.6F;

    private final ModelPart box;

    public ScorchbackChargeSpinLayer(RenderLayerParent<Scorchback, ScorchbackModel> parent,
            EntityModelSet modelSet) {
        super(parent);
        this.box = modelSet.bakeLayer(ModelLayers.PLAYER_SPIN_ATTACK).getChild("box");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            Scorchback entity, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isChargeDashing()) {
            return;
        }
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        for (int ring = 0; ring < RINGS; ring++) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * -(RING_SPIN + ring * 6.0F)));
            float scale = RING_SCALE * (ring + 1);
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0F, -0.2F + RING_RISE * ring, 0.0F);
            this.box.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
