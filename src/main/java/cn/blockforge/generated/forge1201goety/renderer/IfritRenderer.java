package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.renderer.ClientIfritAfterimage;
import cn.blockforge.generated.goetysupreme.renderer.ClientIfritClawTrail;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GeneratedRenderTypes;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.renderer.IfritClawTrailLayer;
import cn.blockforge.generated.goetysupreme.model.IfritModel;
import cn.blockforge.generated.goetysupreme.renderer.IfritRenderer;

public class IfritRenderer extends MobRenderer<Ifrit, IfritModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/ifrit/ifrit.png");

    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/ifrit/ifrit_glow.png");

    private final IfritModel shadowModel;

    public IfritRenderer(EntityRendererProvider.Context context) {
        super(context, new IfritModel(context.bakeLayer(IfritModel.LAYER)), 0.9F);
        this.shadowModel = new IfritModel(context.bakeLayer(IfritModel.LAYER));
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW_TEXTURE,
                entity -> entity.deathTime < 12, GeneratedRenderTypes::wraith));
        this.addLayer(new IfritClawTrailLayer(this));
        this.addLayer(new IfritHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public void render(Ifrit entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        ClientIfritClawTrail.captureFrameBase(entity.getId(), poseStack.last().pose());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        ClientIfritAfterimage.render(entity, this.getModel(), this.shadowModel, poseStack, buffer,
                packedLight, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(Ifrit entity) {
        return TEXTURE;
    }

    @Override
    public float getFlipDegrees(Ifrit entity) {
        return 0.0F;
    }

    private static final class IfritHeldItemLayer extends RenderLayer<Ifrit, IfritModel> {

        private final ItemInHandRenderer itemRenderer;

        private IfritHeldItemLayer(RenderLayerParent<Ifrit, IfritModel> parent,
                ItemInHandRenderer itemRenderer) {
            super(parent);
            this.itemRenderer = itemRenderer;
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                Ifrit entity, float limbSwing, float limbSwingAmount, float partialTick,
                float ageInTicks, float netHeadYaw, float headPitch) {
            ItemStack stack = entity.getMainHandItem();
            if (stack.isEmpty()) {
                return;
            }
            poseStack.pushPose();
            this.getParentModel().applyRightHandTransform(poseStack);
            this.itemRenderer.renderItem(entity, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    false, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        }
    }
}
