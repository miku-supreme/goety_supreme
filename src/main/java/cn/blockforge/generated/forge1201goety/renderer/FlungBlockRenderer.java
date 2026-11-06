package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.renderer.FlungBlockRenderer;

public class FlungBlockRenderer extends EntityRenderer<FlungBlockEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public FlungBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(FlungBlockEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        BlockState state = entity.getBlockState();
        if (state.isAir()) {
            return;
        }
        float animY = Mth.lerp(partialTick, entity.prevAnimY, entity.animY);
        poseStack.pushPose();
        poseStack.translate(0.0D, animY, 0.0D);
        RenderType renderType = ItemBlockRenderTypes.getChunkRenderType(state);
        if (renderType == null) {
            renderType = RenderType.solid();
        }
        this.blockRenderer.renderSingleBlock(state, poseStack, buffer,
                packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FlungBlockEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
