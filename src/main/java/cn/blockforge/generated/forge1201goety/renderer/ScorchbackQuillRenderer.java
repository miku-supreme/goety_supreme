package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ModModelLayer;
import com.Polarice3.Goety.client.render.model.PoisonQuillModel;
import com.Polarice3.Goety.client.render.visual.TrailRenderer;
import com.Polarice3.Goety.utils.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackQuill;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackQuillRenderer;

@OnlyIn(Dist.CLIENT)
public class ScorchbackQuillRenderer extends EntityRenderer<ScorchbackQuill> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
            "textures/entity/scorchback/rain_spike.png");
    private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "goety", "textures/entity/projectiles/trail.png");
    private final PoisonQuillModel model;

    public ScorchbackQuillRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PoisonQuillModel(context.bakeLayer(ModModelLayer.POISON_QUILL));
    }

    @Override
    public void render(ScorchbackQuill entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        this.renderTrail(entity, partialTick, poseStack, buffer);
        poseStack.pushPose();
        float yaw = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        float pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch - 90.0F));
        if (entity.isEmbedded()) {
            float elapsed = ScorchbackQuill.FUSE_TICKS - entity.getFuse() + partialTick;
            float wobble = Math.max(0.0F, 5.0F - elapsed) * Mth.sin(elapsed * 2.8F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(wobble));
        }
        poseStack.scale(0.85F, 0.85F, 0.85F);
        poseStack.translate(0.0D, 5.0D / 16.0D, 0.0D);
        this.model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (entity.isEmbedded()) {
            float elapsed = ScorchbackQuill.FUSE_TICKS - entity.getFuse() + partialTick;
            float pulse = (Mth.sin(elapsed * (0.35F + elapsed * 0.055F)) + 1.0F) * 0.5F;
            this.model.renderToBuffer(poseStack,
                    buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE)),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 0.15F + pulse * 0.65F);
        }
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderTrail(ScorchbackQuill entity, float partialTick, PoseStack poses,
            MultiBufferSource buffer) {
        List<Vec3> history = entity.trailPositions();
        if (entity.isEmbedded() || history.size() < 2) {
            return;
        }
        Vec3 head = new Vec3(Mth.lerp(partialTick, entity.xo, entity.getX()),
                Mth.lerp(partialTick, entity.yo, entity.getY()),
                Mth.lerp(partialTick, entity.zo, entity.getZ()));
        TrailEffect trail = new TrailEffect(0.22F, 3.5F);
        for (Vec3 point : history) {
            trail.update(point);
        }
        trail.prepareRender(head, partialTick);
        poses.pushPose();
        poses.translate(-head.x, -head.y, -head.z);
        TrailRenderer.render(trail, buffer.getBuffer(RenderType.entityTranslucentEmissive(TRAIL_TEXTURE)),
                poses, TrailEffect.TrailOffsetFunction.FACE_CAMERA, false,
                1.0F, 0.24F, 0.055F, 0.95F, LightTexture.FULL_BRIGHT);
        poses.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ScorchbackQuill entity) {
        return TEXTURE;
    }
}
