package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.visual.TrailRenderer;
import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import com.Polarice3.Goety.utils.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.renderer.ClientCorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserTrailRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;

@OnlyIn(Dist.CLIENT)
public final class CorruptedLaserTrailRenderer {

    private static final ResourceLocation TRAIL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety", "textures/entity/projectiles/trail.png");

    private static final ResourceLocation HEAD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/particle/laser_glow.png");

    private static final ResourceLocation NAMELESS_HEAD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/particle/nameless_laser_glow.png");

    private static final float WIDTH = 0.16F;

    private static final float LENGTH = 1.60F;

    private static final double RADIUS = 0.68D;

    private static final double TWIST = 0.50D;

    private static final double SPIN = 0.10D;

    private static final double FORWARD = ClientCorruptedLaserParticles.VOID_SHOCK_FORWARD;

    private static final double TAIL_SPAN = 1.00D;

    private static final double TAIL_BACK = 1.40D;

    private static final double RING_SPACING = 2.4D;

    private static final double START_OFFSET = 1.80D;

    private static final int MAX_RINGS = 22;

    private static final int TAIL_SAMPLES = 63;

    private static final float HEAD_SIZE = 0.15F;

    private static final float TRAIL_R = 0.62F, TRAIL_G = 0.26F, TRAIL_B = 1.00F, TRAIL_A = 0.90F;

    private static final float HEAD_R = 0.78F, HEAD_G = 0.46F, HEAD_B = 1.00F, HEAD_A = 0.95F;

    private static final Map<Entity, List<TrailEffect>> TRAILS = new WeakHashMap<>();
    private static final Map<Entity, Integer> LAST_TICK = new WeakHashMap<>();

    private CorruptedLaserTrailRenderer() {
    }

    public static void render(CorruptedBeam beam, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, double distance) {
        if (distance <= START_OFFSET) {
            return;
        }
        boolean nameless = beam.getOwner() instanceof NamelessWither;
        float trailR = nameless ? 0.65F : TRAIL_R;
        float trailG = nameless ? 1.00F : TRAIL_G;
        float trailB = nameless ? 0.24F : TRAIL_B;
        float headR = nameless ? 0.80F : HEAD_R;
        float headG = nameless ? 1.00F : HEAD_G;
        float headB = nameless ? 0.55F : HEAD_B;
        float yaw = Mth.rotLerp(partialTick, beam.yRotO, beam.getYRot());
        float pitch = Mth.rotLerp(partialTick, beam.xRotO, beam.getXRot());
        double x = Mth.lerp(partialTick, beam.xo, beam.getX());
        double y = Mth.lerp(partialTick, beam.yo, beam.getY()) + beam.getBbHeight() * 0.5D;
        double z = Mth.lerp(partialTick, beam.zo, beam.getZ());

        Vec3 start = new Vec3(x, y, z);
        Vec3 dir = Vec3.directionFromRotation(pitch, yaw);
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        dir = dir.normalize();
        Vec3 reference = Math.abs(dir.y) > 0.9D
                ? new Vec3(1.0D, 0.0D, 0.0D) : new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 side = dir.cross(reference).normalize();
        Vec3 up = side.cross(dir).normalize();

        int rings = Mth.clamp((int) Math.round((distance - START_OFFSET) / RING_SPACING) + 1,
                1, MAX_RINGS);
        double stream = RING_SPACING * rings;
        double drift = (beam.tickCount * FORWARD) % stream;

        List<TrailEffect> trails = TRAILS.computeIfAbsent(beam, e -> new ArrayList<>());
        while (trails.size() < rings) {
            trails.add(new TrailEffect(WIDTH, LENGTH));
        }

        Integer last = LAST_TICK.get(beam);
        if (last == null || last != beam.tickCount) {
            LAST_TICK.put(beam, beam.tickCount);
            for (int k = 0; k < rings; k++) {
                double along = ringAlong(k, drift, stream);
                if (along > distance) {
                    continue;
                }
                double headAngle = headAngle(k, beam.tickCount);
                TrailEffect trail = trails.get(k);
                for (int j = 0; j <= TAIL_SAMPLES; j++) {
                    double f = j / (double) TAIL_SAMPLES;
                    double angle = headAngle - (1.0D - f) * TAIL_SPAN;
                    double tailAlong = along - (1.0D - f) * TAIL_BACK;
                    trail.update(start
                            .add(dir.scale(tailAlong))
                            .add(side.scale(Math.cos(angle) * RADIUS))
                            .add(up.scale(Math.sin(angle) * RADIUS)));
                }
            }
        }

        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));
        for (int k = 0; k < rings; k++) {
            double along = ringAlong(k, drift, stream);
            if (along > distance) {
                continue;
            }
            TrailEffect trail = trails.get(k);
            trail.prepareRender(headPoint(start, dir, side, up, along, headAngle(k, beam.tickCount)),
                    partialTick);
            TrailRenderer.render(trail, consumer, poseStack, TrailEffect.TrailOffsetFunction.FACE_CAMERA,
                    false, trailR, trailG, trailB, TRAIL_A, 15728880);
        }
        VertexConsumer headConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(
                nameless ? NAMELESS_HEAD_TEXTURE : HEAD_TEXTURE));
        PoseStack.Pose pose = poseStack.last();
        for (int k = 0; k < rings; k++) {
            double along = ringAlong(k, drift, stream);
            if (along > distance) {
                continue;
            }
            renderHead(headConsumer, pose, camera,
                    headPoint(start, dir, side, up, along, headAngle(k, beam.tickCount)), HEAD_SIZE,
                    headR, headG, headB);
        }
        poseStack.popPose();
    }

    private static double ringAlongBase(int k) {
        return START_OFFSET + RING_SPACING * k;
    }

    private static double ringAlong(int k, double drift, double stream) {
        double shifted = ringAlongBase(k) - START_OFFSET + drift;
        return START_OFFSET + shifted - stream * Math.floor(shifted / stream);
    }

    private static double headAngle(int k, int tickCount) {
        return ringAlongBase(k) * TWIST + tickCount * SPIN;
    }

    private static Vec3 headPoint(Vec3 start, Vec3 dir, Vec3 side, Vec3 up,
            double along, double angle) {
        return start
                .add(dir.scale(along))
                .add(side.scale(Math.cos(angle) * RADIUS))
                .add(up.scale(Math.sin(angle) * RADIUS));
    }

    private static void renderHead(VertexConsumer consumer, PoseStack.Pose pose, Vec3 camera,
            Vec3 center, float size, float colR, float colG, float colB) {
        Vec3 toCamera = camera.subtract(center);
        if (toCamera.lengthSqr() < 1.0E-6D) {
            return;
        }
        Vec3 forward = toCamera.normalize();
        Vec3 reference = Math.abs(forward.y) > 0.99D
                ? new Vec3(1.0D, 0.0D, 0.0D) : new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(reference);
        if (right.lengthSqr() < 1.0E-6D) {
            return;
        }
        right = right.normalize();
        Vec3 faceUp = right.cross(forward).normalize();
        double half = size * 0.5D;
        Vec3 a = center.add(right.scale(-half)).add(faceUp.scale(-half));
        Vec3 b = center.add(right.scale(half)).add(faceUp.scale(-half));
        Vec3 c = center.add(right.scale(half)).add(faceUp.scale(half));
        Vec3 d = center.add(right.scale(-half)).add(faceUp.scale(half));
        headVertex(consumer, pose, a, 0.0F, 1.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, b, 1.0F, 1.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, c, 1.0F, 0.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, d, 0.0F, 0.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, d, 0.0F, 0.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, c, 1.0F, 0.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, b, 1.0F, 1.0F, forward, colR, colG, colB);
        headVertex(consumer, pose, a, 0.0F, 1.0F, forward, colR, colG, colB);
    }

    private static void headVertex(VertexConsumer consumer, PoseStack.Pose pose, Vec3 pos,
            float u, float v, Vec3 normal, float r, float g, float b) {
        consumer.vertex(pose.pose(), (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, HEAD_A)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(pose.normal(), (float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
    }
}
