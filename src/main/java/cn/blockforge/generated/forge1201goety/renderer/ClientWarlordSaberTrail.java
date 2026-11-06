package cn.blockforge.generated.goetysupreme.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WarlordBladeTrail;
import cn.blockforge.generated.goetysupreme.entity.WarlordSaberTrailLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

@OnlyIn(Dist.CLIENT)
public final class ClientWarlordSaberTrail {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/warlord_saber_trail.png");

    private static final float SABER_Y = -0.0544F;

    private static final float HILT_Z = -0.125F;

    private static final float TIP_Z = -1.4375F;

    private static final float V_TIP = 0.345F;

    private static final float V_HILT = 0.725F;

    private static final double SWING_SPEED = 5.5D;

    private static final double JUMP_SPEED = 280.0D;

    private static final double PARTICLE_SPACING = 0.16D;

    private static final int MAX_FLAME_POINTS = 14;

    private static final double FADE_SECONDS = 0.36D;

    private static final int MAX_SAMPLES = 56;

    private static final long STALE_NANOS = 250_000_000L;

    private static final Map<Integer, Trail> TRAILS = new HashMap<>();

    private static int pruneCounter;
    private static final int PRUNE_EVERY = 200;

    private ClientWarlordSaberTrail() {
    }

    public static void captureFrameBase(int entityId, Matrix4f base) {
        Trail trail = TRAILS.computeIfAbsent(entityId, id -> new Trail());
        trail.baseInverse.set(base).invert();
        trail.viewRotation.set(base);
        trail.viewRotation.setTranslation(0.0F, 0.0F, 0.0F);
        trail.hasBase = true;
    }

    public static void render(AbstractWitherWarlord warlord, WitherWarlordModel<?> model,
            PoseStack poseStack, MultiBufferSource buffer, float partialTick) {
        long now = System.nanoTime();
        Trail trail = TRAILS.computeIfAbsent(warlord.getId(), id -> new Trail());
        trail.lastSeenNanos = now;
        if (!trail.hasBase) {
            return;
        }

        poseStack.pushPose();
        Vector4f rootView = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F).mul(poseStack.last().pose());
        model.applySaberTransform(poseStack);
        Matrix4f saber = poseStack.last().pose();
        Vector4f hiltView = new Vector4f(0.0F, SABER_Y, HILT_Z, 1.0F).mul(saber);
        Vector4f tipView = new Vector4f(0.0F, SABER_Y, TIP_Z, 1.0F).mul(saber);
        poseStack.popPose();
        Vec3 entityPos = warlord.getPosition(partialTick);
        Vec3 hilt = toWorld(trail, hiltView, entityPos);
        Vec3 tip = toWorld(trail, tipView, entityPos);
        Vec3 root = toWorld(trail, rootView, entityPos);
        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        double dt = (now - trail.lastSampleNanos) / 1.0E9D;
        trail.lastSampleNanos = now;

        Vec3 relTip = tip.subtract(root);

        double speed = 0.0D;
        if (trail.frameRelTip != null && dt > 1.0E-4D && dt < 0.4D) {
            speed = trail.frameRelTip.distanceTo(relTip) / dt;
        }
        boolean jumping = dt > 0.4D || speed > JUMP_SPEED
                || (trail.tail() != null && trail.tail().tip.distanceToSqr(tip) > 64.0D);
        boolean slashing = !jumping && !warlord.isInvisible()
                && warlord.isSaberAttackActive() && speed >= SWING_SPEED;
        trail.frameTip = tip;
        trail.frameHilt = hilt;
        trail.frameRelTip = relTip;
        trail.emitting = slashing;

        if (jumping) {
            trail.samples.clear();
        }
        if (slashing) {
            Sample last = trail.tail();
            if (last == null || last.tip.distanceToSqr(tip) > 1.0E-4D) {
                trail.samples.add(new Sample(hilt, tip, now));
                while (trail.samples.size() > MAX_SAMPLES) {
                    trail.samples.remove(0);
                }
            }
        }

        long fadeNanos = (long) (FADE_SECONDS * 1.0E9D);
        while (!trail.samples.isEmpty() && now - trail.samples.get(0).nanos > fadeNanos) {
            trail.samples.remove(0);
        }

        if (trail.samples.size() >= 2) {
            drawTrail(trail, buffer, cam, now, fadeNanos);
        }

        if (++pruneCounter >= PRUNE_EVERY) {
            pruneCounter = 0;
            pruneStale(now);
        }
    }

    private static Vec3 toWorld(Trail trail, Vector4f viewPoint, Vec3 entityPos) {
        Vector4f local = new Vector4f(viewPoint.x, viewPoint.y, viewPoint.z, 1.0F).mul(trail.baseInverse);
        return entityPos.add(local.x, local.y, local.z);
    }

    private static void drawTrail(Trail trail, MultiBufferSource buffer, Vec3 cam, long now, long fadeNanos) {
        PoseStack fx = new PoseStack();
        fx.last().pose().set(trail.viewRotation);
        fx.translate(-cam.x, -cam.y, -cam.z);
        Matrix4f matrix = fx.last().pose();
        List<Sample> samples = trail.samples;

        VertexConsumer body = buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        VertexConsumer glow = buffer.getBuffer(RenderType.eyes(TEXTURE));

        drawBand(body, matrix, samples, now, fadeNanos,
                new Band(0.0F, 1.0F, 0.0F, 0.0F,
                        0.42F, 0.03F, 0.01F, 1.00F, 0.52F, 0.14F,
                        0.40F, 0.96F, V_HILT, V_TIP, false));
        drawBand(glow, matrix, samples, now, fadeNanos,
                new Band(0.0F, 1.0F, 0.30F, 0.62F,
                        0.90F, 0.06F, 0.02F, 1.00F, 0.32F, 0.08F,
                        0.10F, 0.30F, V_HILT, V_TIP, true));
        drawBand(glow, matrix, samples, now, fadeNanos,
                new Band(0.58F, 1.0F, 0.0F, 0.14F,
                        1.00F, 0.52F, 0.20F, 1.00F, 0.97F, 0.86F,
                        0.22F, 0.88F, 0.45F, 0.30F, true));
        drawBand(glow, matrix, samples, now, fadeNanos,
                new Band(0.0F, 0.42F, 0.12F, 0.0F,
                        0.28F, 0.02F, 0.01F, 0.72F, 0.10F, 0.02F,
                        0.18F, 0.55F, V_HILT, 0.50F, true));
    }

    private record Band(float innerFrac, float outerFrac, float innerExtend, float outerExtend,
            float rT, float gT, float bT, float rH, float gH, float bH,
            float aT, float aH, float vIn, float vOut, boolean doubleSided) {

        public float centerFrac() {
            return 0.5F * (this.innerFrac + this.outerFrac);
        }
    }

    private static void drawBand(VertexConsumer consumer, Matrix4f matrix, List<Sample> samples,
            long now, long fadeNanos, Band band) {
        int n = samples.size();
        float time = (now % 1_000_000_000L) * 1.0E-9F;
        for (int i = 0; i + 1 < n; i++) {
            Sample s0 = samples.get(i);
            Sample s1 = samples.get(i + 1);
            float life0 = life(s0, now, fadeNanos);
            float life1 = life(s1, now, fadeNanos);
            float u0 = (float) i / (float) (n - 1);
            float u1 = (float) (i + 1) / (float) (n - 1);
            float jitter0 = 0.84F + 0.16F * Mth.sin(i * 2.31F + time * 5.0F);
            float jitter1 = 0.84F + 0.16F * Mth.sin((i + 1) * 2.31F + time * 5.0F);
            float a0 = Mth.lerp(u0, band.aT(), band.aH()) * life0 * jitter0;
            float a1 = Mth.lerp(u1, band.aT(), band.aH()) * life1 * jitter1;
            if (a0 <= 0.004F && a1 <= 0.004F) {
                continue;
            }
            float r0 = Mth.lerp(u0, band.rT(), band.rH());
            float g0 = Mth.lerp(u0, band.gT(), band.gH());
            float b0 = Mth.lerp(u0, band.bT(), band.bH());
            float r1 = Mth.lerp(u1, band.rT(), band.rH());
            float g1 = Mth.lerp(u1, band.gT(), band.gH());
            float b1 = Mth.lerp(u1, band.bT(), band.bH());

            Vec3 in0 = rail(s0.hilt, s0.tip, band.innerFrac(), band.innerExtend(), life0, band.centerFrac());
            Vec3 out0 = rail(s0.hilt, s0.tip, band.outerFrac(), band.outerExtend(), life0, band.centerFrac());
            Vec3 in1 = rail(s1.hilt, s1.tip, band.innerFrac(), band.innerExtend(), life1, band.centerFrac());
            Vec3 out1 = rail(s1.hilt, s1.tip, band.outerFrac(), band.outerExtend(), life1, band.centerFrac());

            quad(consumer, matrix, in0, out0, out1, in1,
                    u0, u1, band.vIn(), band.vOut(), r0, g0, b0, a0, r1, g1, b1, a1);
            if (band.doubleSided()) {
                quad(consumer, matrix, in1, out1, out0, in0,
                        u1, u0, band.vIn(), band.vOut(), r1, g1, b1, a1, r0, g0, b0, a0);
            }
        }
    }

    private static Vec3 rail(Vec3 hilt, Vec3 tip, float frac, float extend, float life, float centerFrac) {
        Vec3 axis = tip.subtract(hilt);
        float shrink = 0.35F + 0.65F * Mth.clamp(life, 0.0F, 1.0F);
        float along = centerFrac + (frac - centerFrac) * shrink;
        Vec3 point = hilt.add(axis.scale(along));
        if (extend != 0.0F) {
            point = point.add(axis.normalize().scale(extend * shrink));
        }
        return point;
    }

    private static void quad(VertexConsumer consumer, Matrix4f matrix,
            Vec3 a, Vec3 b, Vec3 c, Vec3 d,
            float uA, float uB, float vIn, float vOut,
            float ra, float ga, float ba, float aa,
            float rb, float gb, float bb, float ab) {
        vertex(consumer, matrix, a, uA, vIn, ra, ga, ba, aa);
        vertex(consumer, matrix, b, uA, vOut, ra, ga, ba, aa);
        vertex(consumer, matrix, c, uB, vOut, rb, gb, bb, ab);
        vertex(consumer, matrix, d, uB, vIn, rb, gb, bb, ab);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Vec3 p, float u, float v,
            float r, float g, float b, float a) {
        consumer.vertex(matrix, (float) p.x, (float) p.y, (float) p.z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static float life(Sample sample, long now, long fadeNanos) {
        float t = (float) ((now - sample.nanos) / (double) fadeNanos);
        return Mth.clamp(1.0F - t, 0.0F, 1.0F);
    }

    public static void clientTick(AbstractWitherWarlord warlord) {
        long now = System.nanoTime();
        Trail trail = TRAILS.get(warlord.getId());
        if (trail == null) {
            return;
        }
        if (now - trail.lastSeenNanos > STALE_NANOS || !trail.emitting
                || trail.frameTip == null || trail.frameHilt == null) {
            trail.tickTip = null;
            trail.tickHilt = null;
            trail.lastTickNanos = now;
            return;
        }
        if (trail.tickTip == null || trail.tickHilt == null || trail.lastTickNanos <= 0L) {
            trail.tickTip = trail.frameTip;
            trail.tickHilt = trail.frameHilt;
            trail.lastTickNanos = now;
            return;
        }

        List<Sample> path = new ArrayList<>();
        path.add(new Sample(trail.tickHilt, trail.tickTip, trail.lastTickNanos));
        for (int i = 0; i < trail.samples.size(); i++) {
            Sample s = trail.samples.get(i);
            if (s.nanos() > trail.lastTickNanos) {
                path.add(s);
            }
        }
        trail.lastTickNanos = now;

        if (path.size() >= 2) {
            spawnTrailFlames(warlord, path);
        }
        trail.tickTip = trail.frameTip;
        trail.tickHilt = trail.frameHilt;
    }

    private static void spawnTrailFlames(AbstractWitherWarlord warlord, List<Sample> path) {
        double total = 0.0D;
        for (int i = 0; i + 1 < path.size(); i++) {
            total += path.get(i).tip().distanceTo(path.get(i + 1).tip());
        }
        if (total < 0.03D) {
            return;
        }
        int count = Mth.clamp((int) Math.ceil(total / PARTICLE_SPACING), 1, MAX_FLAME_POINTS);
        double step = total / (double) count;

        ParticleOptions flame = warlord.isPowered()
                ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
        RandomSource rnd = warlord.getRandom();

        double acc = 0.0D;
        double nextAt = step * 0.5D;
        int spawned = 0;
        for (int i = 0; i + 1 < path.size() && spawned < count; i++) {
            Vec3 h0 = path.get(i).hilt();
            Vec3 t0 = path.get(i).tip();
            Vec3 h1 = path.get(i + 1).hilt();
            Vec3 t1 = path.get(i + 1).tip();
            double seg = t0.distanceTo(t1);
            if (seg < 1.0E-5D) {
                continue;
            }
            while (spawned < count && nextAt <= acc + seg) {
                double f = (nextAt - acc) / seg;
                Vec3 h = new Vec3(Mth.lerp(f, h0.x, h1.x), Mth.lerp(f, h0.y, h1.y), Mth.lerp(f, h0.z, h1.z));
                Vec3 t = new Vec3(Mth.lerp(f, t0.x, t1.x), Mth.lerp(f, t0.y, t1.y), Mth.lerp(f, t0.z, t1.z));
                double bladeFrac = 0.40D + 0.60D * rnd.nextDouble();
                Vec3 p = h.lerp(t, bladeFrac);
                double ox = (rnd.nextDouble() - 0.5D) * 0.10D;
                double oy = (rnd.nextDouble() - 0.5D) * 0.10D;
                double oz = (rnd.nextDouble() - 0.5D) * 0.10D;
                warlord.level().addParticle(flame, p.x + ox, p.y + oy, p.z + oz, 0.0D, 0.0D, 0.0D);
                if (rnd.nextFloat() < 0.22F) {
                    warlord.level().addParticle(ParticleTypes.SMALL_FLAME, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
                }
                if (rnd.nextFloat() < 0.06F) {
                    warlord.level().addParticle(ParticleTypes.LAVA, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
                }
                spawned++;
                nextAt += step;
            }
            acc += seg;
        }
    }

    private static void pruneStale(long now) {
        Iterator<Map.Entry<Integer, Trail>> it = TRAILS.entrySet().iterator();
        while (it.hasNext()) {
            if (now - it.next().getValue().lastSeenNanos > 2_000_000_000L) {
                it.remove();
            }
        }
    }

    private static final class Trail {
        final List<Sample> samples = new ArrayList<>();

        final Matrix4f baseInverse = new Matrix4f();

        final Matrix4f viewRotation = new Matrix4f();
        boolean hasBase;
        Vec3 frameTip;
        Vec3 frameHilt;

        Vec3 frameRelTip;
        Vec3 tickTip;
        Vec3 tickHilt;

        long lastTickNanos;
        long lastSampleNanos;
        long lastSeenNanos;
        boolean emitting;

        public Sample tail() {
            return this.samples.isEmpty() ? null : this.samples.get(this.samples.size() - 1);
        }
    }

    private record Sample(Vec3 hilt, Vec3 tip, long nanos) {
    }
}
