package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import cn.blockforge.generated.goetysupreme.config.PerformanceConfig;
import cn.blockforge.generated.goetysupreme.renderer.SkullCloudSmokeParticle;

public final class SkullCloudSmokeParticle extends SmokeParticle {

    private SkullCloudSmokeParticle(ClientLevel level, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, sprites);
        if (PerformanceConfig.SKULL_CLOUD_SMOKE_NO_COLLISION.get()) {
            this.hasPhysics = false;
        }
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SkullCloudSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
