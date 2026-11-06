package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.renderer.LightningBoltBeamRenderer;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;

public final class JerotesParticleFx {

    private JerotesParticleFx() {
    }

    public static void hitBall(ServerLevel level, double x, double y, double z, int beginColor, int targetColor) {
        RandomSource random = level.getRandom();
        for (int i = 0; i < 12; ++i) {
            double theta = random.nextDouble() * Math.PI * 2.0D;
            double phi = Math.acos(2.0D * random.nextDouble() - 1.0D);
            double radius = 0.1D + random.nextDouble() * 0.1D;
            double px = x + radius * Math.sin(phi) * Math.cos(theta);
            double py = y + radius * Math.cos(phi);
            double pz = z + radius * Math.sin(phi) * Math.sin(theta);
            int color = lerpColor(beginColor, targetColor, random.nextFloat());
            level.sendParticles(new DustParticleOptions(colorToVector(color), 0.5F),
                    px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        level.sendParticles(new DustParticleOptions(colorToVector(targetColor), 0.9F),
                x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void sendBallParticles(ServerLevel level, Entity entity, ParticleOptions particle,
            float radius, float speed) {
        Vec3 center = new Vec3(entity.getX(), entity.getY(0.5D), entity.getZ());
        RandomSource random = level.getRandom();
        for (int i = 0; i < 40; ++i) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double inclination = random.nextDouble() * Math.PI;
            double ox = (double) radius * Math.sin(inclination) * Math.cos(angle);
            double oy = (double) radius * Math.cos(inclination);
            double oz = (double) radius * Math.sin(inclination) * Math.sin(angle);
            Vec3 pos = center.add(ox, oy, oz);
            Vec3 dir = center.subtract(pos);
            Vec3 velocity = dir.lengthSqr() < 1.0E-8D ? Vec3.ZERO : dir.normalize().scale(speed);
            level.sendParticles(particle, pos.x, pos.y, pos.z, 0,
                    velocity.x, velocity.y, velocity.z, 1.0D);
        }
    }

    public static void sprayBoltFan(ServerLevel level, LivingEntity caster, float range, float spellAngleDeg,
            int colorI, int colorII, int colorIII) {
        Vec3 casterPos = caster.position();
        Vec3 forward = caster.getViewVector(1.0F);
        if (forward.lengthSqr() < 1.0E-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }
        forward = forward.normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        Vec3 origin = casterPos.add(0.0D, caster.getBbHeight() * 0.7D, 0.0D)
                .add(forward.scale(0.4D));
        float baseRadius = range * (float) Math.tan(Math.toRadians(spellAngleDeg * 0.5F));
        RandomSource random = level.getRandom();
        for (int i = 0; i < 4; ++i) {
            float depth = 0.25F + random.nextFloat() * 0.75F;
            float actualDist = depth * range;
            float radiusAtDepth = baseRadius * depth;
            float angleRad = random.nextFloat() * 2.0F * (float) Math.PI;
            float distFromCenter = radiusAtDepth * (float) Math.sqrt(random.nextFloat());
            Vec3 radialDir = right.scale(Mth.cos(angleRad)).add(up.scale(Mth.sin(angleRad)));
            Vec3 target = origin.add(forward.scale(actualDist)).add(radialDir.scale(distFromCenter));
            float[] rgb = gradientColor(colorI, colorII, colorIII, depth);
            float scale = 0.85F + depth * 0.35F;
            Vec3 delta = target.subtract(origin);
            int samples = 10;
            for (int s = 1; s <= samples; ++s) {
                float t = (float) s / (float) samples;
                Vec3 point = origin.add(delta.scale(t));
                int color = ((int) (rgb[0] * 255.0F) << 16)
                        | ((int) (rgb[1] * 255.0F) << 8) | (int) (rgb[2] * 255.0F);
                level.sendParticles(new DustParticleOptions(colorToVector(color), scale * 0.5F),
                        point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                if ((s & 1) == 0) {
                    level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            point.x, point.y, point.z, 1, 0.02D, 0.02D, 0.02D, 0.01D);
                }
            }
        }
    }

    public static void rayTrail(ServerLevel level, Entity ray, ParticleOptions particle) {
        if (particle == null) {
            return;
        }
        Vec3 speed = ray.getDeltaMovement().scale(2.0D);
        double half = (double) ray.getBbHeight() / 2.0D;
        for (int i = -1; i < 5; ++i) {
            level.sendParticles(particle,
                    ray.getX() + speed.x * (double) i * 0.2D,
                    ray.getY() + speed.y * (double) i * 0.2D + half,
                    ray.getZ() + speed.z * (double) i * 0.2D,
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        level.sendParticles(particle, ray.getX(), ray.getY() + half, ray.getZ(),
                0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void rayHitBurst(ServerLevel level, Vec3 center, Vec3 direction, ParticleOptions particle) {
        if (particle == null) {
            return;
        }
        hemisphereBurst(level, center, direction, particle, 0.25F, 120);
        hemisphereBurst(level, center, direction, particle, 0.15F, 100);
        hemisphereBurst(level, center, direction, particle, 0.05F, 80);
    }

    private static void hemisphereBurst(ServerLevel level, Vec3 center, Vec3 direction,
            ParticleOptions particle, float speed, int count) {
        if (direction.lengthSqr() < 1.0E-6D) {
            return;
        }
        Vec3 axis = direction.normalize();
        double maxAngle = 0.35D * Math.PI;
        double radius = 0.005D;
        RandomSource random = level.getRandom();
        for (int i = 0; i < count; ++i) {
            double theta = random.nextDouble() * 2.0D * Math.PI;
            double phi = random.nextDouble() * Math.PI;
            double ox = radius * Math.sin(phi) * Math.cos(theta);
            double oy = radius * Math.cos(phi);
            double oz = radius * Math.sin(phi) * Math.sin(theta);
            Vec3 worldDir = new Vec3(ox, oy, oz).normalize();
            double angle = Math.acos(Mth.clamp(worldDir.dot(axis), -1.0D, 1.0D));
            if (angle > maxAngle) {
                continue;
            }
            level.sendParticles(particle, center.x - ox, center.y - oy, center.z - oz, 0,
                    -worldDir.x * (double) speed, -worldDir.y * (double) speed,
                    -worldDir.z * (double) speed, 1.0D);
        }
    }

    public static void fogBurst(ServerLevel level, double x, double y, double z,
            ParticleOptions particle, int count) {
        level.sendParticles(particle, x, y, z, count, 0.8D, 0.5D, 0.8D, 0.01D);
    }

    public static float[] gradientColor(int c1, int c2, int c3, float t) {
        int r;
        int g;
        int b;
        if (t < 0.5F) {
            float lt = t / 0.5F;
            r = (int) Mth.lerp(lt, (float) (c1 >> 16 & 0xFF), (float) (c2 >> 16 & 0xFF));
            g = (int) Mth.lerp(lt, (float) (c1 >> 8 & 0xFF), (float) (c2 >> 8 & 0xFF));
            b = (int) Mth.lerp(lt, (float) (c1 & 0xFF), (float) (c2 & 0xFF));
        } else {
            float lt = (t - 0.5F) / 0.5F;
            r = (int) Mth.lerp(lt, (float) (c2 >> 16 & 0xFF), (float) (c3 >> 16 & 0xFF));
            g = (int) Mth.lerp(lt, (float) (c2 >> 8 & 0xFF), (float) (c3 >> 8 & 0xFF));
            b = (int) Mth.lerp(lt, (float) (c2 & 0xFF), (float) (c3 & 0xFF));
        }
        return new float[] { (float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F };
    }

    public static int lerpColor(int from, int to, float t) {
        int r = (int) Mth.lerp(t, (float) (from >> 16 & 0xFF), (float) (to >> 16 & 0xFF));
        int g = (int) Mth.lerp(t, (float) (from >> 8 & 0xFF), (float) (to >> 8 & 0xFF));
        int b = (int) Mth.lerp(t, (float) (from & 0xFF), (float) (to & 0xFF));
        return r << 16 | g << 8 | b;
    }

    private static Vector3f colorToVector(int color) {
        return new Vector3f(((color >> 16) & 0xFF) / 255.0F,
                ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
    }
}
