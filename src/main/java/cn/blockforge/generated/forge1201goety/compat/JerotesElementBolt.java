package cn.blockforge.generated.goetysupreme.compat;

import java.lang.reflect.Constructor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesElementBolt;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;

public final class JerotesElementBolt {

    private static final String OPTIONS_CLASS =
            "com.jerotes.jerotes.client.particle.ElementBoltParticle$BoltParticleOptions";

    private static boolean probed;
    private static Constructor<?> optionsCtor;

    private JerotesElementBolt() {
    }

    public static void spawnFan(Level level, LivingEntity owner, float range, float spellAngleDeg,
            int colorI, int colorII, int colorIII, int totalParticles, RandomSource random) {
        if (owner == null) {
            return;
        }
        Vec3 casterPos = owner.position();
        Vec3 forward = owner.getViewVector(1.0F);
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
        Vec3 origin = casterPos.add(0.0D, owner.getBbHeight() * 0.7D, 0.0D).add(forward.scale(0.4D));
        float baseRadius = range * (float) Math.tan(Math.toRadians(spellAngleDeg * 0.5F));
        for (int i = 0; i < totalParticles; ++i) {
            float depth = 0.25F + random.nextFloat() * 0.75F;
            float actualDist = depth * range;
            float radiusAtDepth = baseRadius * depth;
            float angleRad = random.nextFloat() * 2.0F * (float) Math.PI;
            float distFromCenter = radiusAtDepth * (float) Math.sqrt(random.nextFloat());
            Vec3 radialDir = right.scale(Mth.cos(angleRad)).add(up.scale(Mth.sin(angleRad)));
            Vec3 target = origin.add(forward.scale(actualDist)).add(radialDir.scale(distFromCenter));
            if (!hasLineOfSightPos(level, target, owner)) {
                continue;
            }
            float[] rgb = JerotesParticleFx.gradientColor(colorI, colorII, colorIII, depth);
            float scale = 0.85F + depth * 0.35F;
            Vec3 delta = target.subtract(origin);
            ParticleOptions options = boltOptions(rgb[0], rgb[1], rgb[2], scale, delta.x, delta.y, delta.z);
            if (options == null) {
                return;
            }
            level.addParticle(options, origin.x, origin.y, origin.z, 0.0D, 0.0D, 0.0D);
        }
    }

    private static boolean hasLineOfSightPos(Level level, Vec3 target, LivingEntity self) {
        Vec3 from = new Vec3(self.getX(), self.getEyeY(), self.getZ());
        return level.clip(new ClipContext(from, target, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, self)).getType() == HitResult.Type.MISS;
    }

    private static ParticleOptions boltOptions(float r, float g, float b, float scale,
            double dx, double dy, double dz) {
        Constructor<?> ctor = optionsConstructor();
        if (ctor == null) {
            return null;
        }
        try {
            return (ParticleOptions) ctor.newInstance(r, g, b, scale,
                    (float) dx, (float) dy, (float) dz);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static synchronized Constructor<?> optionsConstructor() {
        if (!probed) {
            probed = true;
            if (JerotesCoreBridge.isLoaded()) {
                try {
                    Class<?> type = Class.forName(OPTIONS_CLASS);
                    optionsCtor = type.getConstructor(float.class, float.class, float.class,
                            float.class, float.class, float.class, float.class);
                } catch (Throwable ignored) {
                    optionsCtor = null;
                }
            }
        }
        return optionsCtor;
    }
}
