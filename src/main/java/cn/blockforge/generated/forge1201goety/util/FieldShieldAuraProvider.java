package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FieldShieldAuraProvider;

public final class FieldShieldAuraProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet sprites;

    public FieldShieldAuraProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level,
            double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new FieldShieldAuraParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
    }
}
