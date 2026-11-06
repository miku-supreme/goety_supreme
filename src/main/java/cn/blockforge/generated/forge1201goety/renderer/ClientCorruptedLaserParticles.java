package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.renderer.ClientCorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserParticle;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserTrailRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;

@OnlyIn(Dist.CLIENT)
public final class ClientCorruptedLaserParticles {

    private static final double MAX_LENGTH = 48.0D;

    private static final double MIN_LENGTH = 0.4D;

    private static final double SPARK_RING_SPACING = 3.0D;
    private static final int SPARK_MAX_RINGS = 16;

    private static final double SPARK_RADIUS = 0.44D;
    private static final double SPARK_RADIUS_JITTER = 0.12D;

    private static final double SPARK_SPIN = 0.30D;

    private static final double SPARK_FORWARD = 0.80D;

    private static final double TRAIL_RING_SPACING = 4.0D;
    private static final int TRAIL_MAX_RINGS = 12;

    private static final double TRAIL_RADIUS = 0.54D;
    private static final double TRAIL_RADIUS_JITTER = 0.14D;

    private static final double TRAIL_SPIN = -0.26D;

    public static final double VOID_SHOCK_FORWARD = 1.15D;

    private static final double TRAIL_FORWARD = VOID_SHOCK_FORWARD;

    private static final double TRAIL_PHASE = 1.6D;

    private static final double TWIST_PER_BLOCK = 1.05D;

    private static final int SPLASH_PERIOD = 2;
    private static final int SPLASH_SPARKS = 3;
    private static final int SPLASH_GLOWS = 2;

    private static final double SPLASH_NORMAL_SPEED = 0.14D;
    private static final double SPLASH_SPREAD = 0.16D;

    public static void tickClient(CorruptedBeam beam) {
        if (!(beam.level() instanceof ClientLevel level)) {
            return;
        }
        Vec3 start = beam.position();
        Vec3 dir = beam.getViewVector(1.0F);
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        dir = dir.normalize();

        HitResult hit = beam.beamTraceResult(64.0D, 1.0F, true);
        boolean hitBlock = hit.getType() == HitResult.Type.BLOCK;
        Vec3 hitPos = hit.getLocation();
        double length = Mth.clamp(start.distanceTo(hitPos), 0.0D, MAX_LENGTH);
        RandomSource random = level.random;

        if (hitBlock && beam.tickCount % SPLASH_PERIOD == 0) {
            spawnImpactSplash(level, hitPos, ((BlockHitResult) hit).getDirection(), random,
                    beam.getOwner() instanceof NamelessWither);
        }

        if (length < MIN_LENGTH) {
            return;
        }

        int sparkRings = Mth.clamp((int) Math.round(length / SPARK_RING_SPACING), 1, SPARK_MAX_RINGS);
        for (int i = 0; i < sparkRings; i++) {
            double along = length * (i + 0.5D) / sparkRings;
            Vec3 center = start.add(dir.scale(along));
            double angle = along * TWIST_PER_BLOCK + beam.tickCount * SPARK_SPIN;
            double radius = SPARK_RADIUS + (random.nextDouble() - 0.5D) * SPARK_RADIUS_JITTER;
            CorruptedLaserParticle.spawnOrbit(level, center, dir, radius, angle,
                    SPARK_SPIN, SPARK_FORWARD, length - along, false);
        }

        int trailRings = Mth.clamp((int) Math.round(length / TRAIL_RING_SPACING), 1, TRAIL_MAX_RINGS);
        for (int i = 0; i < trailRings; i++) {
            double along = length * (i + 0.5D) / trailRings;
            Vec3 center = start.add(dir.scale(along));
            double angle = along * TWIST_PER_BLOCK + TRAIL_PHASE + beam.tickCount * TRAIL_SPIN;
            double radius = TRAIL_RADIUS + (random.nextDouble() - 0.5D) * TRAIL_RADIUS_JITTER;
            CorruptedLaserParticle.spawnOrbit(level, center, dir, radius, angle,
                    TRAIL_SPIN, TRAIL_FORWARD, length - along, true);
        }
    }

    private static void spawnImpactSplash(ClientLevel level, Vec3 hitPos, Direction face,
            RandomSource random, boolean nameless) {
        Vec3 normal = Vec3.atLowerCornerOf(face.getNormal());
        for (int i = 0; i < SPLASH_SPARKS; i++) {
            double sx = (random.nextDouble() - 0.5D) * SPLASH_SPREAD;
            double sy = (random.nextDouble() - 0.5D) * SPLASH_SPREAD;
            double sz = (random.nextDouble() - 0.5D) * SPLASH_SPREAD;
            Vec3 velocity = new Vec3(sx, sy, sz)
                    .add(normal.scale(SPLASH_NORMAL_SPEED * (0.5D + random.nextDouble())));
            level.addParticle(GeneratedMod.CORRUPTED_LASER_SPARK.get(),
                    hitPos.x + normal.x * 0.05D, hitPos.y + normal.y * 0.05D, hitPos.z + normal.z * 0.05D,
                    velocity.x, velocity.y, velocity.z);
        }
        for (int i = 0; i < SPLASH_GLOWS; i++) {
            double ox = (random.nextDouble() - 0.5D) * 0.4D;
            double oy = (random.nextDouble() - 0.5D) * 0.4D;
            double oz = (random.nextDouble() - 0.5D) * 0.4D;
            level.addParticle(nameless ? GeneratedMod.NAMELESS_LASER_GLOW.get()
                            : GeneratedMod.CORRUPTED_LASER_GLOW.get(),
                    hitPos.x + normal.x * 0.06D + ox,
                    hitPos.y + normal.y * 0.06D + oy,
                    hitPos.z + normal.z * 0.06D + oz,
                    normal.x * 0.05D, normal.y * 0.05D, normal.z * 0.05D);
        }
    }

    private ClientCorruptedLaserParticles() {
    }
}
