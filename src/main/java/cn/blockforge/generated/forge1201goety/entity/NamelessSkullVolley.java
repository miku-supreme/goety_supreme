package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class NamelessSkullVolley {

    public static final float DAMAGE = 8.0F;
    public static final int ROW_COUNT = 2;
    public static final int PER_ROW = 8;

    public static final double COLUMN_SPREAD = 0.075D;
    public static final double ROW_OFFSET = 0.25D;
    public static final double LAUNCH_SPEED = 0.6D;

    private NamelessSkullVolley() {
    }

    public static void launch(ServerLevel server, LivingEntity shooter, LivingEntity target) {
        if (target == null) {
            return;
        }
        Vec3 base = new Vec3(target.getX() - shooter.getX(), target.getEyeY() - shooter.getEyeY(),
                target.getZ() - shooter.getZ()).normalize();
        for (int row = 0; row < ROW_COUNT; row++) {
            double y = shooter.getEyeY() + (row == 0 ? ROW_OFFSET : -ROW_OFFSET);
            for (int column = 0; column < PER_ROW; column++) {
                double spread = (column - (PER_ROW - 1) / 2.0D) * COLUMN_SPREAD;
                double dx = base.x * Math.cos(spread) - base.z * Math.sin(spread);
                double dz = base.x * Math.sin(spread) + base.z * Math.cos(spread);
                HauntedSkullProjectile skull = new HauntedSkullProjectile(shooter,
                        dx * LAUNCH_SPEED, base.y * LAUNCH_SPEED, dz * LAUNCH_SPEED, server);
                skull.setPos(shooter.getX() + dx * 0.8D, y, shooter.getZ() + dz * 0.8D);
                if (GoetyCompat.hasSkullSetDamage()) {
                    skull.setDamage(DAMAGE);
                }
                server.addFreshEntity(skull);
                server.sendParticles(ParticleTypes.WHITE_ASH, skull.getX(), skull.getY(), skull.getZ(),
                        8, 0.2D, 0.2D, 0.2D, 0.02D);
            }
        }
    }
}
