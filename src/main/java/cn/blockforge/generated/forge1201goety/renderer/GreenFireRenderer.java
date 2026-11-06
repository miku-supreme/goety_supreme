package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ModModelLayer;
import com.Polarice3.Goety.client.render.ModRenderType;
import com.Polarice3.Goety.client.render.model.IceBouquetModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.renderer.GreenFireRenderer;

public class GreenFireRenderer extends EntityRenderer<GreenFireEntity> {

    private static final String FRAME_PATH = "textures/entity/projectiles/green_fire/";

    private static final long FRAME_COUNT = 31L;

    private final IceBouquetModel<GreenFireEntity> model;

    public GreenFireRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceBouquetModel<>(context.bakeLayer(ModModelLayer.ICE_BOUQUET));
    }

    @Override
    public void render(GreenFireEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        float animation = entity.getAnimationProgress(partialTick);
        if (animation != 0.0F) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
            poseStack.scale(-animation, -(animation + 0.25F), animation);
            poseStack.translate(0.0D, -1.45D, 0.0D);
            ResourceLocation texture = this.getTextureLocation(entity);
            VertexConsumer sheet = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
            this.model.setupAnim(entity, 0.0F, 0.0F, partialTick / 10.0F, 0.0F, 0.0F);
            this.model.renderToBuffer(poseStack, sheet, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 0.15F);
            VertexConsumer glow = buffer.getBuffer(ModRenderType.wraith(texture));
            this.model.renderToBuffer(poseStack, glow, 15728640, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        }
    }

    @Override
    public int getBlockLightLevel(GreenFireEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(GreenFireEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                FRAME_PATH + entity.level().getGameTime() % FRAME_COUNT + ".png");
    }
}
