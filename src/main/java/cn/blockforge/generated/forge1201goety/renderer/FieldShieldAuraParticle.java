package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FieldShieldAuraProvider;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class FieldShieldAuraParticle extends TextureSheetParticle {

    private static final int LIFETIME = 20;

    private static final float SIZE = 2.8875F * 1.15F;

    private static final int GREEN_RGB = 0x7CCB5B;

    private final float rotSpeed;

    public FieldShieldAuraParticle(ClientLevel level, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.hasPhysics = false;
        this.quadSize = SIZE;
        this.lifetime = LIFETIME;
        this.pickSprite(sprites);
        this.rotSpeed = (this.random.nextFloat() - 0.5F) * 0.1F;
        this.roll = this.random.nextFloat() * ((float) Math.PI * 2.0F);
        this.setColor(((GREEN_RGB >> 16) & 0xFF) / 255.0F,
                ((GREEN_RGB >> 8) & 0xFF) / 255.0F,
                (GREEN_RGB & 0xFF) / 255.0F);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        ++this.age;
        if (this.age >= this.lifetime) {
            this.remove();
        }
        this.oRoll = this.roll;
        this.roll += (float) Math.PI * this.rotSpeed * 2.0F;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
