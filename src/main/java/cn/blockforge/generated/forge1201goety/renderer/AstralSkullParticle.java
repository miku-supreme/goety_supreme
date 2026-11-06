package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import cn.blockforge.generated.goetysupreme.renderer.AstralSkullParticle;

public final class AstralSkullParticle extends TextureSheetParticle {

    private static final float FADE_START = 0.35F;

    private static final float SHRINK = 0.7F;

    private static final float MIN_SIZE = 0.14F;
    private static final float SIZE_SPAN = 0.08F;

    private final float startSize;

    private AstralSkullParticle(ClientLevel level, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.friction = 0.82F;
        this.lifetime = 6 + this.random.nextInt(4);
        this.startSize = MIN_SIZE + this.random.nextFloat() * SIZE_SPAN;
        this.quadSize = this.startSize;
        this.pickSprite(sprites);
        if (this.random.nextBoolean()) {
            this.setColor(0.76F, 0.58F, 1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.removed) {
            return;
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

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AstralSkullParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
