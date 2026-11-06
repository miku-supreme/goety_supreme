package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownTrident;
import cn.blockforge.generated.goetysupreme.renderer.ServantTridentRenderer;

public final class ServantTridentRenderer extends EntityRenderer<ThrownTrident> {

    private static final ResourceLocation VANILLA_TEXTURE =
            net.minecraft.client.renderer.entity.ThrownTridentRenderer.TRIDENT_LOCATION;
    private final TridentModel vanillaModel;
    private final ItemRenderer itemRenderer;

    public ServantTridentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.vanillaModel = new TridentModel(context.bakeLayer(ModelLayers.TRIDENT));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ThrownTrident trident, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffers, int packedLight) {
        float yaw = Mth.rotLerp(partialTick, trident.yRotO, trident.getYRot());
        float pitch = Mth.lerp(partialTick, trident.xRotO, trident.getXRot());
        ItemStack item = trident instanceof ServantThrownTrident servantTrident
                ? servantTrident.getRenderItem()
                : ItemStack.EMPTY;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(pitch + 90.0F));
        if (item.isEmpty() || item.is(Items.TRIDENT)) {
            renderVanillaTrident(item, poseStack, buffers, packedLight);
        } else {
            BakedModel model = itemRenderer.getModel(item, trident.level(), null, trident.getId());
            if (model.isCustomRenderer()) {
                itemRenderer.render(item, ItemDisplayContext.FIXED, false, poseStack, buffers, packedLight,
                        OverlayTexture.NO_OVERLAY, model);
            } else if (!model.isGui3d()) {
                renderVanillaTrident(item, poseStack, buffers, packedLight);
            } else {
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                itemRenderer.render(item, ItemDisplayContext.NONE, false, poseStack, buffers, packedLight,
                        OverlayTexture.NO_OVERLAY, model);
            }
        }
        poseStack.popPose();
    }

    private void renderVanillaTrident(ItemStack item, PoseStack poseStack, MultiBufferSource buffers,
            int packedLight) {
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffers,
                RenderType.entityCutoutNoCull(VANILLA_TEXTURE), false, !item.isEmpty() && item.hasFoil());
        vanillaModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownTrident trident) {
        return VANILLA_TEXTURE;
    }
}
