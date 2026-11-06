package cn.blockforge.generated.goetysupreme.client.particles;

import com.Polarice3.Goety.client.particles.NecroVFXPalette;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class SupremeSoulVFX {
    public static final int STREAM_TICKS = 28;
    private static final float[][] SOUL_SMOKE = {{0.0F, 0.6F, 1.0F}, {0.1F, 0.8F, 1.0F}, {0.2F, 1.0F, 1.0F}};

    private SupremeSoulVFX() {
    }

    public static void emitter(ServerLevel level, Vec3 pos, Emitter emitter, float yawDeg, boolean alternate) {
        level.sendParticles(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_EMITTER.get(), 1.0F, emitter.ordinal(), yawDeg, 0.0F, 0.0F, alternate), pos.x, pos.y, pos.z, 0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void emitter(ServerLevel level, Vec3 pos, Emitter emitter, float yawDeg) {
        emitter(level, pos, emitter, yawDeg, false);
    }

    public static void emitter(ServerLevel level, Vec3 pos, Emitter emitter) {
        emitter(level, pos, emitter, 0.0F, false);
    }

    public static float emitterTick(Level level, Vec3 origin, float yawDeg, Emitter emitter, int age, float remainder, boolean alternate) {
        RandomSource random = level.getRandom();
        switch (emitter) {
            case CUBE_SPAWN -> {
                if (age == 0) {
                    int souls = 2 + random.nextInt(2);
                    for (int i = 0; i < souls; i++) {
                        bombSoul(level, origin, random, alternate);
                    }
                    int floating = 12 + random.nextInt(9);
                    for (int i = 0; i < floating; i++) {
                        cubeFloatingSpark(level, origin, random, alternate);
                    }
                    int dust = 12 + random.nextInt(7);
                    for (int i = 0; i < dust; i++) {
                        cubeDust(level, origin, random, alternate);
                    }
                }
                if (age == 1) {
                    int sparks = 24 + random.nextInt(7);
                    for (int i = 0; i < sparks; i++) {
                        soulStreak(level, origin, random, alternate);
                    }
                    int lava = 12 + random.nextInt(7);
                    for (int i = 0; i < lava; i++) {
                        soulLava(level, origin, random, alternate);
                    }
                }
                if (age < 10) {
                    remainder += 18.0F * 0.05F;
                    while (remainder >= 1.0F) {
                        soulLava(level, origin, random, alternate);
                        remainder -= 1.0F;
                    }
                }
            }
            case SOUL_BOMB -> {
                if (age == 0) {
                    for (int i = 0; i < 6; i++) {
                        bombSoul(level, origin, random, alternate);
                    }
                }
            }
        }
        return remainder;
    }

    public static float emitterTick(Level level, Vec3 origin, float yawDeg, Emitter emitter, int age, float remainder) {
        return emitterTick(level, origin, yawDeg, emitter, age, remainder, false);
    }

    public static void soulStreak(Level level, Vec3 origin, RandomSource random, boolean alternate) {
        Vec3 vec3 = cone(random, 0.0D, 75.0D).scale(0.9D + random.nextDouble() * 1.8D);
        level.addParticle(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_STREAK.get(), 0.13F + random.nextFloat() * 0.02F, 4 + random.nextInt(5), 0.5F + random.nextFloat() * 0.5F, 0.0F, 0.0F, alternate), origin.x, origin.y, origin.z, vec3.x, vec3.y, vec3.z);
    }

    public static void soulLava(Level level, Vec3 origin, RandomSource random, boolean alternate) {
        double halfAngle = random.nextDouble() * 45.0D;
        Vec3 vec3 = cone(random, 0.0D, halfAngle).scale(0.9D + random.nextDouble() * 0.9D);
        float k = 0.2F + random.nextFloat() * 0.8F;
        float[] hue = alternate ? NecroVFXPalette.greenHue(k) : new float[]{0.0F, k, 1.0F};
        level.addParticle(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_LAVA.get(), 0.13F + random.nextFloat() * 0.02F, 20 + random.nextInt(21), hue[0], hue[1], hue[2], alternate), origin.x, origin.y, origin.z, vec3.x, vec3.y, vec3.z);
    }

    public static void cubeFloatingSpark(Level level, Vec3 origin, RandomSource random, boolean alternate) {
        Vec3 vec3 = cone(random, 0.0D, 67.5D).scale(0.9D + random.nextDouble() * 0.9D);
        float blue = 5.0F + random.nextFloat() * 5.0F;
        float green = 1.0F + random.nextFloat() * 4.0F;
        float gravity = -(50.0F + random.nextFloat() * 100.0F) / 40000.0F;
        level.addParticle(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_SPARK.get(), 0.06F + random.nextFloat() * 0.06F, 20 + random.nextInt(21), green / blue, blue, gravity, alternate), origin.x, origin.y, origin.z, vec3.x, vec3.y, vec3.z);
    }

    public static void cubeDust(Level level, Vec3 origin, RandomSource random, boolean alternate) {
        double inner = (0.7D + random.nextDouble() * 0.3D) * 70.0D;
        Vec3 vec3 = cone(random, inner, 85.0D).scale(1.0D + random.nextDouble() * 0.5D);
        float[] colour = alternate
                ? NecroVFXPalette.smokeColour(random.nextInt(NecroVFXPalette.smokeColours()))
                : SOUL_SMOKE[random.nextInt(SOUL_SMOKE.length)];
        level.addParticle(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_DUST.get(), 1.0F + random.nextFloat(), 16 + random.nextInt(45), colour[0], colour[1], colour[2], alternate), origin.x, origin.y + 0.1D, origin.z, vec3.x, vec3.y, vec3.z);
    }

    public static void bombSoul(Level level, Vec3 origin, RandomSource random, boolean alternate) {
        double r = Math.sqrt(random.nextDouble());
        double angle = random.nextDouble() * Math.PI * 2.0D;
        double ox = Math.cos(angle) * r, oz = Math.sin(angle) * r;
        double out = 0.25D + random.nextDouble() * 0.25D;
        double h = Math.sqrt(ox * ox + oz * oz);
        double dirX, dirZ;
        if (h > 1.0E-4D) {
            dirX = ox / h;
            dirZ = oz / h;
        } else {
            double a = random.nextDouble() * Math.PI * 2.0D;
            dirX = Math.cos(a);
            dirZ = Math.sin(a);
        }
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_BURST.get(), 0.55F, 30 + random.nextInt(21), (float) -ox, 1.0F, (float) -oz, alternate), origin.x + ox, origin.y, origin.z + oz, 0, dirX * out, 0.05D + random.nextDouble() * 0.95D, dirZ * out, 1.0F);
        } else {
            level.addParticle(new SupremeEffectOption(GeneratedMod.SUPREME_SOUL_BURST.get(), 0.55F, 30 + random.nextInt(21), (float) -ox, 1.0F, (float) -oz, alternate), origin.x + ox, origin.y, origin.z + oz, dirX * out, 0.05D + random.nextDouble() * 0.95D, dirZ * out);
        }
    }

    private static Vec3 cone(RandomSource randomSource, double minDeg, double maxDeg) {
        double cosOuter = Math.cos(Math.toRadians(maxDeg));
        double cosInner = Math.cos(Math.toRadians(minDeg));
        double cos = Mth.lerp(randomSource.nextDouble(), cosOuter, cosInner);
        double sin = Math.sqrt(Math.max(0.0D, 1.0D - cos * cos));
        double phi = randomSource.nextDouble() * Math.PI * 2.0D;
        return new Vec3(Math.cos(phi) * sin, cos, Math.sin(phi) * sin);
    }

    public enum Emitter {
        CUBE_SPAWN(11),
        SOUL_BOMB(1);

        public final int lifetime;

        Emitter(int lifetime) {
            this.lifetime = lifetime;
        }
    }
}
