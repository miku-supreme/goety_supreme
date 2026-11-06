package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.client.init.ClientModRegistry;
import fuzs.mutantmonsters.client.model.MutantArrowModel;
import fuzs.mutantmonsters.client.model.MutantCrossbowModel;
import fuzs.mutantmonsters.client.model.MutantSkeletonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantCrossbowLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSkeletonServantModel;

public final class MutantSkeletonServantCrossbowLayer
        extends RenderLayer<MutantSkeletonServant, MutantSkeletonServantModel> {
    private static final ResourceLocation CROSSBOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "textures/entity/mutant_crossbow.png");
    private static final ResourceLocation GILDED_CROSSBOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_crossbow_gilded.png");

    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "textures/entity/mutant_arrow.png");
    private static final ResourceLocation GILDED_ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_arrow_gilded.png");

    private final MutantArrowModel arrowModel;

    public MutantSkeletonServantCrossbowLayer(
            RenderLayerParent<MutantSkeletonServant, MutantSkeletonServantModel> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.arrowModel = new MutantArrowModel(context.bakeLayer(ClientModRegistry.MUTANT_ARROW));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            MutantSkeletonServant servant, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        MutantSkeletonModel model = getParentModel().getDelegate();
        boolean leftHanded = servant.isLeftHanded();

        poseStack.pushPose();
        model.translateHand(leftHanded, poseStack);
        if (leftHanded) {
            poseStack.scale(-1.0F, 1.0F, -1.0F);
        }
        MutantCrossbowModel crossbow = model.getCrossbow();
        ResourceLocation crossbowTexture = servant.hasHornedAppearance()
                ? GILDED_CROSSBOW_TEXTURE : CROSSBOW_TEXTURE;
        crossbow.renderToBuffer(poseStack,
                bufferSource.getBuffer(crossbow.renderType(crossbowTexture)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        Animation animation = servant.getAnimation();
        int tick = servant.getAnimationTick();
        boolean nocked = (animation == MutantSkeletonServant.SHOOT_ANIMATION && tick > 10 && tick < 26)
                || (animation == MutantSkeletonServant.MULTI_SHOT_ANIMATION && tick > 16 && tick < 24);
        if (nocked && !servant.isInvisible()) {
            poseStack.pushPose();
            model.translateHand(leftHanded, poseStack);
            poseStack.translate(leftHanded ? 0.2D : -0.2D, 0.4D, -1.8D);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.scale(1.2F, 1.2F, 1.2F);
            ResourceLocation arrowTexture = servant.hasHornedAppearance()
                    ? GILDED_ARROW_TEXTURE : ARROW_TEXTURE;
            arrowModel.renderToBuffer(poseStack,
                    bufferSource.getBuffer(arrowModel.renderType(arrowTexture)),
                    packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
