package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.SphereExplodeParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public final class NamelessSphereShell {

    private static final int SPEED = 1;

    private NamelessSphereShell() {
    }

    public static void spawn(ServerLevel level, LivingEntity caster) {
        spawnAt(level, caster.getX(), caster.getY() + caster.getBbHeight() * 0.5D, caster.getZ(), wrapRadius(caster));
    }

    public static void spawnAt(ServerLevel level, double x, double y, double z, float size) {
        level.sendParticles(
                new SphereExplodeParticleOption(color(16), color(8), color(0), size, SPEED),
                x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static float wrapRadius(LivingEntity caster) {
        double halfHeight = caster.getBbHeight() * 0.5D;
        double halfWidth = caster.getBbWidth() * 0.5D;
        return (float) Math.sqrt(halfHeight * halfHeight + halfWidth * halfWidth);
    }

    private static float color(int shift) {
        return ((NAMELESS_COLOR >> shift) & 0xFF) / 255.0F;
    }

    private static final int NAMELESS_COLOR = 0x36dc0b;
}
