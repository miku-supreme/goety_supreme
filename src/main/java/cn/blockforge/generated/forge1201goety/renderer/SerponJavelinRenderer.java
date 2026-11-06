package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;
import cn.blockforge.generated.goetysupreme.model.SerponJavelinModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponJavelinRenderer;

public final class SerponJavelinRenderer extends EntityRenderer<SerponJavelinEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/item/serpon_javelin_model.png");

    private final SerponJavelinModel model;

    public SerponJavelinRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SerponJavelinModel(context.bakeLayer(SerponJavelinModel.LAYER));
    }

    @Override
    public void render(SerponJavelinEntity entity, float entityYaw, float partialTick,
            PoseStack pose, MultiBufferSource buffer, int packedLight) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(
                Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        pose.mulPose(Axis.ZP.rotationDegrees(
                90.0F + Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        this.model.renderToBuffer(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        pose.popPose();
        super.render(entity, entityYaw, partialTick, pose, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SerponJavelinEntity entity) {
        return TEXTURE;
    }
}
