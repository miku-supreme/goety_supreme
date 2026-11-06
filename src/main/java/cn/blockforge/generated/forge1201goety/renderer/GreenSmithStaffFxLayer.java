package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.GreenSmithModel;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithStaffFxLayer;

public class GreenSmithStaffFxLayer extends RenderLayer<AbstractGreenSmith, GreenSmithModel> {

    private static final ResourceLocation GATHER_TEX =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/green_smith_gather.png");

    private static final float STAFF_TIP_X = -1.0F;
    private static final float STAFF_TIP_Y = -18.5F;
    private static final float STAFF_TIP_Z = -18.0F;

    private static final int MOTE_COUNT = 14;

    private static final float MOTE_CYCLE_TICKS = 16.0F;

    private static final float MOTE_RADIUS_OUTER = 3.4F;

    private static final float MOTE_RADIUS_INNER = 0.35F;

    private static final float GOLDEN_ANGLE = 2.3999632F;

    public GreenSmithStaffFxLayer(RenderLayerParent<AbstractGreenSmith, GreenSmithModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            AbstractGreenSmith entity, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isCasting()) {
            entity.staffFxReady = false;
            return;
        }
        GreenSmithModel model = this.getParentModel();
        ModelPart skeleton = model.root().getChild("skeleton");
        ModelPart upperBody = skeleton.getChild("上半身");
        ModelPart rightHand = upperBody.getChild("右手");
        ModelPart rightArm = rightHand.getChild("右胳膊");
        ModelPart wand = rightArm.getChild("魔杖");
        ModelPart group = wand.hasChild("group") ? wand.getChild("group") : null;
        ModelPart anchor = group != null ? group
                : wand.hasChild("绿豆本体") ? wand.getChild("绿豆本体")
                : wand.hasChild("handle") ? wand.getChild("handle") : wand;

        poseStack.pushPose();
        skeleton.translateAndRotate(poseStack);
        upperBody.translateAndRotate(poseStack);
        rightHand.translateAndRotate(poseStack);
        rightArm.translateAndRotate(poseStack);
        wand.translateAndRotate(poseStack);
        if (anchor != wand) {
            anchor.translateAndRotate(poseStack);
        }
        if (group != null) {
            poseStack.translate(STAFF_TIP_X / 16.0F, STAFF_TIP_Y / 16.0F, STAFF_TIP_Z / 16.0F);
        }
        renderGather(poseStack, bufferSource, packedLight, ageInTicks);

        Matrix4f matrix = poseStack.last().pose();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vector3f offset = new Vector3f(-matrix.m30(), matrix.m31(), -matrix.m32());
        camera.rotation().transform(offset);
        Vec3 cameraPos = camera.getPosition();
        entity.staffFxX = cameraPos.x + offset.x;
        entity.staffFxY = cameraPos.y + offset.y;
        entity.staffFxZ = cameraPos.z + offset.z;
        entity.staffFxReady = true;
        poseStack.popPose();

        entity.spawnStaffGatherFx();
    }

    private void renderGather(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            float ageInTicks) {
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.eyes(GATHER_TEX));

        Matrix4f core = poseStack.last().pose();
        float pulse = 0.90F + 0.10F * Mth.sin(ageInTicks * 0.7F);
        crossGlow(buffer, core, 0.26F * pulse, packedLight, 0.42F, 0.55F, 0.90F, 0.22F);
        crossGlow(buffer, core, 0.17F * pulse, packedLight, 0.62F, 0.72F, 1.00F, 0.30F);
        crossGlow(buffer, core, 0.10F * pulse, packedLight, 0.80F, 1.00F, 1.00F, 0.72F);

        for (int i = 0; i < MOTE_COUNT; i++) {
            float phase = (ageInTicks / MOTE_CYCLE_TICKS + (float) i / MOTE_COUNT) % 1.0F;
            float alpha = 0.55F * Mth.sin(phase * (float) Math.PI);
            if (alpha <= 0.02F) {
                continue;
            }
            float radius = Mth.lerp(phase, MOTE_RADIUS_OUTER, MOTE_RADIUS_INNER);
            float theta = i * GOLDEN_ANGLE + ageInTicks * 0.18F;
            float phi = (float) Math.acos(1.0F - 2.0F * ((i * 0.618034F) % 1.0F));
            float ox = radius * Mth.sin(phi) * Mth.cos(theta);
            float oy = radius * Mth.cos(phi);
            float oz = radius * Mth.sin(phi) * Mth.sin(theta);
            float half = Mth.lerp(phase, 0.105F, 0.055F);
            poseStack.pushPose();
            poseStack.translate(ox / 16.0F, oy / 16.0F, oz / 16.0F);
            crossGlow(buffer, poseStack.last().pose(), half, packedLight, 0.50F, 0.58F, 0.92F, 0.26F);
            poseStack.popPose();
        }
    }

    private static void crossGlow(VertexConsumer buffer, Matrix4f matrix, float half, int packedLight,
            float alpha, float r, float g, float b) {
        quadXY(buffer, matrix, half, packedLight, alpha, r, g, b);
        quadXZ(buffer, matrix, half, packedLight, alpha, r, g, b);
        quadYZ(buffer, matrix, half, packedLight, alpha, r, g, b);
    }

    private static void quadXY(VertexConsumer buffer, Matrix4f m, float h, int light,
            float a, float r, float g, float b) {
        vertex(buffer, m, -h, -h, 0.0F, 0.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, h, -h, 0.0F, 1.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, h, h, 0.0F, 1.0F, 0.0F, light, a, r, g, b);
        vertex(buffer, m, -h, h, 0.0F, 0.0F, 0.0F, light, a, r, g, b);
    }

    private static void quadXZ(VertexConsumer buffer, Matrix4f m, float h, int light,
            float a, float r, float g, float b) {
        vertex(buffer, m, -h, 0.0F, h, 0.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, h, 0.0F, h, 1.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, h, 0.0F, -h, 1.0F, 0.0F, light, a, r, g, b);
        vertex(buffer, m, -h, 0.0F, -h, 0.0F, 0.0F, light, a, r, g, b);
    }

    private static void quadYZ(VertexConsumer buffer, Matrix4f m, float h, int light,
            float a, float r, float g, float b) {
        vertex(buffer, m, 0.0F, -h, h, 0.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, 0.0F, -h, -h, 1.0F, 1.0F, light, a, r, g, b);
        vertex(buffer, m, 0.0F, h, -h, 1.0F, 0.0F, light, a, r, g, b);
        vertex(buffer, m, 0.0F, h, h, 0.0F, 0.0F, light, a, r, g, b);
    }

    private static void vertex(VertexConsumer buffer, Matrix4f matrix,
            float x, float y, float z, float u, float v, int light, float alpha,
            float r, float g, float b) {
        buffer.vertex(matrix, x, y, z)
                .color(r, g, b, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0.0F, 0.0F, 1.0F)
                .endVertex();
    }
}
