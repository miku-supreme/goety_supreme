package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemServantHeadLayer;

public final class SnowGolemServantHeadLayer
        extends RenderLayer<SnowGolemServant, SnowGolemModel<SnowGolemServant>> {
    private final BlockRenderDispatcher blockRenderer;
    private final ItemRenderer itemRenderer;

    public SnowGolemServantHeadLayer(RenderLayerParent<SnowGolemServant, SnowGolemModel<SnowGolemServant>> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        blockRenderer = context.getBlockRenderDispatcher();
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int light, SnowGolemServant servant,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float headYaw,
            float headPitch) {
        if (!servant.hasPumpkin()) return;
        boolean outline = servant.isInvisible() && Minecraft.getInstance().shouldEntityAppearGlowing(servant);
        if (servant.isInvisible() && !outline) return;

        poseStack.pushPose();
        getParentModel().getHead().translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.34375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.625F, -0.625F, -0.625F);
        int overlay = LivingEntityRenderer.getOverlayCoords(servant, 0.0F);
        if (outline) {
            BlockState pumpkin = Blocks.CARVED_PUMPKIN.defaultBlockState();
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            blockRenderer.getModelRenderer().renderModel(poseStack.last(),
                    buffers.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)),
                    pumpkin, blockRenderer.getBlockModel(pumpkin), 0.0F, 0.0F, 0.0F, light, overlay);
        } else {
            itemRenderer.renderStatic(servant, new ItemStack(Blocks.CARVED_PUMPKIN), ItemDisplayContext.HEAD,
                    false, poseStack, buffers, servant.level(), light, overlay, servant.getId());
        }
        poseStack.popPose();
    }
}
