package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import cn.blockforge.generated.goetysupreme.renderer.RoarWaveParticle;

public final class RoarWaveParticle extends TextureSheetParticle {

    private static final int LIFETIME = 10;

    private static final float START_SIZE = 1.0F;

    private static final float GROWTH = 0.8F;

    private static final float MAX_SIZE = 9.0F;

    private static final float START_ALPHA = 0.55F;

    private static final float RED = 1.0F;
    private static final float GREEN = 0.62F;
    private static final float BLUE = 0.50F;

    public RoarWaveParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSize(1.0F, 1.0F);
        this.pickSprite(sprites);
        this.quadSize = START_SIZE;
        this.lifetime = LIFETIME;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.friction = 1.0F;
        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
        this.setColor(RED, GREEN, BLUE);
        this.alpha = START_ALPHA;
        this.roll = this.random.nextFloat() * ((float) Math.PI * 2.0F);
    }

    @Override
    public void tick() {
        this.oRoll = this.roll;
        super.tick();
        if (this.removed) {
            return;
        }
        this.quadSize = Math.min(this.quadSize + GROWTH, MAX_SIZE);
        this.alpha = START_ALPHA * (1.0F - this.age / (float) this.lifetime);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RoarWaveParticle(level, x, y, z, this.sprites);
        }
    }
}
