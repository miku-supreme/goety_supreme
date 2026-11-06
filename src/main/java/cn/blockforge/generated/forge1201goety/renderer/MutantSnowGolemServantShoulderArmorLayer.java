package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantShoulderArmorLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemShoulderArmorModel;

public final class MutantSnowGolemServantShoulderArmorLayer
        extends RenderLayer<MutantSnowGolemServant, MutantSnowGolemServantModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/mutant_snow_shoulder_armor.png");
    private final MutantSnowGolemShoulderArmorModel armorModel;

    public MutantSnowGolemServantShoulderArmorLayer(
            RenderLayerParent<MutantSnowGolemServant, MutantSnowGolemServantModel> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.armorModel = new MutantSnowGolemShoulderArmorModel(
                context.bakeLayer(MutantSnowGolemShoulderArmorModel.LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
            MutantSnowGolemServant servant, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!servant.hasFullyUpgradedTexture() || servant.isInvisible()) {
            return;
        }
        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        getParentModel().renderShoulderArmor(poseStack, armorModel, consumer,
                packedLight, OverlayTexture.NO_OVERLAY);
    }
}
