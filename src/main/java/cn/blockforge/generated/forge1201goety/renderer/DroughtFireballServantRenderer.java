package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.model.DroughtFireballModel;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.renderer.DroughtFireballServantRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ScrollUvVertexConsumer;

public class DroughtFireballServantRenderer extends EntityRenderer<DroughtFireballServantEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
            "textures/entity/projectiles/drought_fireball.png");

    private static final float UV_SCROLL_PER_TICK = 0.03F;

    private final DroughtFireballModel<DroughtFireballServantEntity> model;

    public DroughtFireballServantRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DroughtFireballModel<>(context.bakeLayer(DroughtFireballModel.LAYER_LOCATION));
    }

    @Override
    public void render(DroughtFireballServantEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0D, -1.625D, 0.0D);
        float ageInTicks = entity.tickCount + partialTick;
        Vec3 motion = entity.getDeltaMovement();
        float yaw;
        float pitch;
        if (motion.lengthSqr() > 1.0E-6D) {
            yaw = (float) (Mth.atan2(motion.x, motion.z) * (180.0D / Math.PI));
            pitch = (float) (Mth.atan2(motion.y, motion.horizontalDistance()) * (180.0D / Math.PI));
        } else {
            yaw = entity.getYRot();
            pitch = entity.getXRot();
        }
        this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, yaw, pitch);
        VertexConsumer base = buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        VertexConsumer consumer = new ScrollUvVertexConsumer(base,
                Mth.frac(ageInTicks * UV_SCROLL_PER_TICK));
        this.model.renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DroughtFireballServantEntity entity) {
        return TEXTURE;
    }
}
