package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.WitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.EnhancedWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.ServantWitherSkullRenderer;

public final class EnhancedWitherSkullRenderer extends WitherSkullRenderer {

    private static final ResourceLocation WITHER_ARMOR_LOCATION = ResourceLocation.fromNamespaceAndPath(
            "minecraft", "textures/entity/wither/wither_armor.png");
    private static final int FULL_BRIGHT = 15728880;
    private final SkullModel swirlModel;

    public EnhancedWitherSkullRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.swirlModel = new SkullModel(context.bakeLayer(ModelLayers.WITHER_SKULL));
    }

    @Override
    public ResourceLocation getTextureLocation(WitherSkull skull) {
        return ServantWitherSkullRenderer.usesAstralTexture(skull)
                ? ServantWitherSkullRenderer.ASTRAL_TEXTURE
                : super.getTextureLocation(skull);
    }

    @Override
    public void render(WitherSkull skull, float entityYaw, float partialTicks, PoseStack stack,
            MultiBufferSource buffers, int packedLight) {
        super.render(skull, entityYaw, partialTicks, stack, buffers, packedLight);
        stack.pushPose();
        stack.scale(-1.06F, -1.06F, 1.06F);
        float yaw = Mth.rotLerp(partialTicks, skull.yRotO, skull.getYRot());
        float pitch = Mth.lerp(partialTicks, skull.xRotO, skull.getXRot());
        float time = (float) skull.tickCount + partialTicks;
        VertexConsumer consumer = buffers.getBuffer(RenderType.energySwirl(WITHER_ARMOR_LOCATION,
                Mth.cos(time * 0.02F) * 3.0F / 64.0F, 0.0F));
        swirlModel.setupAnim(0.0F, yaw, pitch);
        swirlModel.renderToBuffer(stack, consumer, FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
    }
}
