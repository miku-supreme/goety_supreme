package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.Polarice3.Goety.client.render.visual.TrailRenderer;
import com.Polarice3.Goety.utils.TrailEffect;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineBladeSlashRenderer;
import cn.blockforge.generated.goetysupreme.entity.ReaperBladeEntity;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBladeRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SakuraSpiritSlashRenderer;

public class ReaperBladeRenderer extends EntityRenderer<ReaperBladeEntity> {

    private static final ResourceLocation SLASH_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/projectiles/slash/1.png");

    private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/projectiles/pointed_trail.png");

    public ReaperBladeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ReaperBladeEntity blade, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        if (blade.tickCount < 5) {
            super.render(blade, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            return;
        }
        float yaw = -Mth.lerp(partialTick, blade.yRotO, blade.getYRot()) + 180.0F;
        float pitch = Mth.lerp(partialTick, blade.xRotO, blade.getXRot());
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        PoseStack.Pose pose = poseStack.last();
        float radius = (0.85F + AbstractReaperNecromancer.BLADE_DAMAGE * 0.03F) * blade.getWidthScale();
        drawSlash(bufferSource.getBuffer(RenderType.entityTranslucent(SLASH_TEXTURE)),
                pose, radius, packedLight, 0.5F, 0.34F, 1.0F, 1.0F);
        VertexConsumer glow = bufferSource.getBuffer(RenderType.eyes(SLASH_TEXTURE));
        drawSlash(glow, pose, radius * 1.15F, LightTexture.FULL_BRIGHT, 1.0F, 0.34F, 0.9F, 1.0F);
        drawSlash(glow, pose, radius * 1.15F, LightTexture.FULL_BRIGHT, 1.0F, 0.34F, 0.9F, 1.0F, true);
        poseStack.popPose();
        this.renderTrails(blade, yaw, pitch, partialTick, poseStack, bufferSource, packedLight);
        super.render(blade, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderTrails(ReaperBladeEntity blade, float yaw, float pitch, float partialTick,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float x = (float) Mth.lerp(partialTick, blade.xo, blade.getX());
        float y = (float) Mth.lerp(partialTick, blade.yo, blade.getY());
        float z = (float) Mth.lerp(partialTick, blade.zo, blade.getZ());
        float age = blade.tickCount + partialTick;
        Matrix4f transform = new Matrix4f();
        transform.rotate(Axis.YP.rotationDegrees(yaw));
        transform.rotate(Axis.XP.rotationDegrees(pitch));
        Vector4f left = transform.transform(new Vector4f(1.0F, Mth.sin(age * 0.4F) * 0.2F, 0.0F, 1.0F));
        Vector4f right = transform.transform(new Vector4f(-1.0F, Mth.cos(age * 0.4F) * 0.2F, 0.0F, 1.0F));
        Vec3 base = new Vec3(x, y, z);
        blade.leftTrail.prepareRender(base.add(left.x(), left.y(), left.z()), partialTick);
        blade.rightTrail.prepareRender(base.add(right.x(), right.y(), right.z()), partialTick);
        poseStack.translate(-x, -y, -z);
        VertexConsumer trailBuffer = bufferSource.getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));
        TrailRenderer.render(blade.leftTrail, trailBuffer, poseStack,
                TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, 0.34F, 1.0F, 1.0F, 1.0F, packedLight);
        TrailRenderer.render(blade.rightTrail, trailBuffer, poseStack,
                TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, 0.34F, 1.0F, 1.0F, 1.0F, packedLight);
        poseStack.popPose();
    }

    private static void drawSlash(VertexConsumer consumer, PoseStack.Pose pose, float halfWidth,
            int packedLight, float alpha, float r, float g, float b) {
        drawSlash(consumer, pose, halfWidth, packedLight, alpha, r, g, b, false);
    }

    private static void drawSlash(VertexConsumer consumer, PoseStack.Pose pose, float halfWidth,
            int packedLight, float alpha, float r, float g, float b, boolean reversed) {
        Matrix4f matrix = pose.pose();
        Matrix3f normal = pose.normal();
        float y = -0.1F;
        if (reversed) {
            vertex(consumer, matrix, normal, -halfWidth, y, halfWidth, 0.0F, 0.0F, packedLight, alpha, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, halfWidth, y, halfWidth, 1.0F, 0.0F, packedLight, alpha, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, halfWidth, y, -halfWidth, 1.0F, 1.0F, packedLight, alpha, r, g, b, 0.0F, -1.0F, 0.0F);
            vertex(consumer, matrix, normal, -halfWidth, y, -halfWidth, 0.0F, 1.0F, packedLight, alpha, r, g, b, 0.0F, -1.0F, 0.0F);
        } else {
            vertex(consumer, matrix, normal, -halfWidth, y, -halfWidth, 0.0F, 1.0F, packedLight, alpha, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, halfWidth, y, -halfWidth, 1.0F, 1.0F, packedLight, alpha, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, halfWidth, y, halfWidth, 1.0F, 0.0F, packedLight, alpha, r, g, b, 0.0F, 1.0F, 0.0F);
            vertex(consumer, matrix, normal, -halfWidth, y, halfWidth, 0.0F, 0.0F, packedLight, alpha, r, g, b, 0.0F, 1.0F, 0.0F);
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal,
            float x, float y, float z, float u, float v, int packedLight, float alpha, float r, float g, float b,
            float nx, float ny, float nz) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(ReaperBladeEntity blade) {
        return SLASH_TEXTURE;
    }
}
