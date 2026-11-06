package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.renderer.WindBlastFx;

public final class WindBlastFx {

    private WindBlastFx() {
    }

    public static void cast(ServerLevel level, @Nullable LivingEntity caster,
            double x, double y, double z, double range, double knock) {
        for (int i = 0; i < 16; i++) {
            int width = 1 + level.random.nextInt(4);
            float height = level.random.nextFloat() * 0.5F;
            level.sendParticles(GreenSmithFx.windBlow(width, height),
                    x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        AABB area = new AABB(x - range, y - range * 0.5D, z - range,
                x + range, y + range * 0.5D, z + range);
        if (caster != null) {
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
                if (entity == caster || caster.isAlliedTo(entity)) {
                    continue;
                }
                MobUtil.knockBack(entity, caster, 2.0D * knock, 0.2D * knock, 2.0D * knock);
            }
        }
        for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area)) {
            if (caster != null) {
                Entity shooter = projectile.getOwner();
                if (shooter != null && (shooter == caster || caster.isAlliedTo(shooter))) {
                    continue;
                }
            }
            level.sendParticles(ParticleTypes.EXPLOSION, projectile.getX(), projectile.getY(),
                    projectile.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            projectile.discard();
        }
    }
}
