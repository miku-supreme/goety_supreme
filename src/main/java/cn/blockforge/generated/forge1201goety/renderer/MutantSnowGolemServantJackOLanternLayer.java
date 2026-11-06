package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.mutantmonsters.client.init.ClientModRegistry;
import fuzs.mutantmonsters.client.model.MutantSnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantJackOLanternLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemServantModel;

public final class MutantSnowGolemServantJackOLanternLayer
        extends RenderLayer<MutantSnowGolemServant, MutantSnowGolemServantModel> {
    private static final ResourceLocation JACK_O_LANTERN_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "mutantmonsters", "textures/entity/mutant_snow_golem/jack_o_lantern.png");
    private static final ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "mutantmonsters", "textures/entity/mutant_snow_golem/glow.png");

    private static final RenderType GLOW_RENDER_TYPE = RenderType.eyes(GLOW_TEXTURE);
    private static final int FULL_BRIGHTNESS = 15728640;

    private final MutantSnowGolemModel headModel;

    public MutantSnowGolemServantJackOLanternLayer(
            RenderLayerParent<MutantSnowGolemServant, MutantSnowGolemServantModel> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.headModel = new MutantSnowGolemModel(context.bakeLayer(ClientModRegistry.MUTANT_SNOW_GOLEM_HEAD))
                .setRenderHeadOnly();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            MutantSnowGolemServant servant, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!servant.hasJackOLantern()) {
            return;
        }
        if (!servant.isInvisible()) {
            getParentModel().copyPropertiesTo(headModel);
            VertexConsumer pumpkinConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(JACK_O_LANTERN_TEXTURE));
            headModel.renderToBuffer(poseStack, pumpkinConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(GLOW_RENDER_TYPE);
        float red;
        float green;
        float blue;
        if (servant.hasSoulJarUpgrade()) {
            red = Math.max(0.0F, 0.45F + 0.05F * Mth.cos(ageInTicks * 0.15F));
            green = Math.max(0.0F, 0.75F + 0.05F * Mth.cos(ageInTicks * 0.15F));
            blue = 1.0F;
        } else {
            red = 1.0F;
            green = Math.max(0.0F, 0.8F + 0.05F * Mth.cos(ageInTicks * 0.15F));
            blue = Math.max(0.0F, 0.2F + 0.15F * Mth.cos(ageInTicks * 0.1F));
        }
        getParentModel().renderToBuffer(poseStack, vertexConsumer, FULL_BRIGHTNESS, OverlayTexture.NO_OVERLAY,
                red, green, blue, 1.0F);
    }
}
