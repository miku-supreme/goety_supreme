package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import cn.blockforge.generated.goetysupreme.util.TintedTotemEffectOption;
import cn.blockforge.generated.goetysupreme.renderer.TintedTotemEffectParticle;

public class TintedTotemEffectParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    public TintedTotemEffectParticle(ClientLevel level, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites,
            float red, float green, float blue) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.sprites = sprites;
        this.setColor(red, green, blue);
        this.quadSize *= 0.75F;
        this.lifetime = (int) (8.0D / (this.random.nextDouble() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 0.96F;
        this.gravity = 0.0F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.pickSprite(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    public static class Provider implements ParticleProvider<TintedTotemEffectOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(TintedTotemEffectOption option, ClientLevel level,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new TintedTotemEffectParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    this.sprites, option.red(), option.green(), option.blue());
        }
    }
}
