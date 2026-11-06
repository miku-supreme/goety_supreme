package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.renderer.ClientCorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserParticle;

public final class CorruptedLaserParticle extends TextureSheetParticle {

    private static final float FADE_START = 0.45F;

    private static final float SHRINK = 0.55F;

    private static SpriteSet sparkSprites;
    private static SpriteSet glowSprites;

    private Vec3 orbitCenter;

    private final Vec3 orbitAxis;

    private final Vec3 orbitSide;
    private final Vec3 orbitUp;
    private final double orbitRadius;
    private double orbitAngle;
    private final double orbitAngularSpeed;
    private final double orbitForward;

    private final double orbitLimit;

    private double orbitAdvanced;

    private final float startSize;

    private CorruptedLaserParticle(ClientLevel level, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, int lifetime, float size) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.lifetime = lifetime;
        this.quadSize = size;
        this.startSize = size;
        this.pickSprite(sprites);
        this.orbitCenter = null;
        this.orbitAxis = null;
        this.orbitSide = null;
        this.orbitUp = null;
        this.orbitRadius = 0.0D;
        this.orbitAngle = 0.0D;
        this.orbitAngularSpeed = 0.0D;
        this.orbitForward = 0.0D;
        this.orbitLimit = 0.0D;
    }

    private CorruptedLaserParticle(ClientLevel level, Vec3 center, Vec3 axis, Vec3 side, Vec3 up,
            double radius, double angle, double angularSpeed, double forward, double limit,
            SpriteSet sprites, int lifetime, float size) {
        super(level, center.x, center.y, center.z);
        this.orbitCenter = center;
        this.orbitAxis = axis;
        this.orbitSide = side;
        this.orbitUp = up;
        this.orbitRadius = radius;
        this.orbitAngle = angle;
        this.orbitAngularSpeed = angularSpeed;
        this.orbitForward = forward;
        this.orbitLimit = limit;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.lifetime = lifetime;
        this.quadSize = size;
        this.startSize = size;
        this.pickSprite(sprites);
        this.orbitPlace();
    }

    private void orbitPlace() {
        double cos = Math.cos(this.orbitAngle);
        double sin = Math.sin(this.orbitAngle);
        this.setPos(
                this.orbitCenter.x + (this.orbitSide.x * cos + this.orbitUp.x * sin) * this.orbitRadius,
                this.orbitCenter.y + (this.orbitSide.y * cos + this.orbitUp.y * sin) * this.orbitRadius,
                this.orbitCenter.z + (this.orbitSide.z * cos + this.orbitUp.z * sin) * this.orbitRadius);
    }

    @Override
    public void tick() {
        boolean orbiting = this.orbitAxis != null;
        if (orbiting) {
            this.orbitAngle += this.orbitAngularSpeed;
            this.orbitCenter = this.orbitCenter.add(this.orbitAxis.scale(this.orbitForward));
            this.orbitAdvanced += this.orbitForward;
        }
        super.tick();
        if (this.removed) {
            return;
        }
        if (orbiting) {
            if (this.orbitLimit > 0.0D && this.orbitAdvanced >= this.orbitLimit) {
                this.remove();
                return;
            }
            this.orbitPlace();
        }
        float life = this.lifetime <= 0 ? 1.0F
                : Math.max(0.0F, Math.min(1.0F, (float) this.age / (float) this.lifetime));
        float alpha = life <= FADE_START ? 1.0F : 1.0F - (life - FADE_START) / (1.0F - FADE_START);
        this.setAlpha(Math.max(0.0F, Math.min(1.0F, alpha)));
        this.quadSize = this.startSize * (1.0F - SHRINK * life);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static void spawnOrbit(ClientLevel level, Vec3 center, Vec3 axis, double radius,
            double angle, double angularSpeed, double forward, double limit, boolean glow) {
        SpriteSet sprites = glow ? glowSprites : sparkSprites;
        if (level == null || sprites == null || axis.lengthSqr() < 1.0E-6D) {
            return;
        }
        Vec3 dir = axis.normalize();
        Vec3 reference = Math.abs(dir.y) > 0.9D ? new Vec3(1.0D, 0.0D, 0.0D) : new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 side = dir.cross(reference).normalize();
        Vec3 up = side.cross(dir).normalize();
        int lifetime = glow ? GlowProvider.LIFETIME : SparkProvider.LIFETIME;
        float size = glow ? GlowProvider.SIZE : SparkProvider.SIZE;
        Particle particle = new CorruptedLaserParticle(level, center, dir, side, up, radius, angle,
                angularSpeed, forward, limit, sprites, lifetime, size);
        Minecraft.getInstance().particleEngine.add(particle);
    }

    public static final class SparkProvider implements ParticleProvider<SimpleParticleType> {
        private static final int LIFETIME = 8;
        private static final float SIZE = 0.18F;

        private final SpriteSet sprites;

        public SparkProvider(SpriteSet sprites) {
            this.sprites = sprites;
            sparkSprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new CorruptedLaserParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    this.sprites, LIFETIME, SIZE);
        }
    }

    public static final class GlowProvider implements ParticleProvider<SimpleParticleType> {
        private static final int LIFETIME = 10;
        private static final float SIZE = 0.30F;

        private final SpriteSet sprites;

        public GlowProvider(SpriteSet sprites) {
            this.sprites = sprites;
            glowSprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new CorruptedLaserParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    this.sprites, LIFETIME, SIZE);
        }
    }
}
