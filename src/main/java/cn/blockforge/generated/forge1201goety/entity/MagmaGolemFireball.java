package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolemFireball;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class MagmaGolemFireball extends SmallFireball {

    public static final double ACCELERATION_POWER = 0.1D;

    public static final double INERTIA = 0.95D;

    private static final int HIT_IGNITE_SECONDS = 5;

    public MagmaGolemFireball(EntityType<? extends SmallFireball> type, Level level) {
        super(type, level);
    }

    public MagmaGolemFireball(Level level, LivingEntity owner, double dx, double dy, double dz) {
        this(ModEntityTypes.MAGMA_FIREBALL.get(), level);
        setOwner(owner);
        Vec3 origin = owner.position().add(0.0D, owner.getBbHeight() * 0.66D, 0.0D);
        setPosRaw(origin.x, origin.y, origin.z);
        setDeltaMovement(Vec3.ZERO);
        xPower = dx * ACCELERATION_POWER;
        yPower = dy * ACCELERATION_POWER;
        zPower = dz * ACCELERATION_POWER;
        setRot(owner.getYRot(), owner.getXRot());
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (level().isClientSide) {
            return;
        }
        Entity target = result.getEntity();
        float damage = GolemServantConfig.MAGMA.FIREBALL_DAMAGE.get().floatValue();
        int previousFireTicks = target.getRemainingFireTicks();
        target.setSecondsOnFire(HIT_IGNITE_SECONDS);
        boolean hit = target.hurt(damageSources().fireball(this, getOwner()), damage);
        if (hit) {
            if (getOwner() instanceof LivingEntity living) {
                doEnchantDamageEffects(living, target);
            }
            if (target instanceof LivingEntity living) {
                living.invulnerableTime = 0;
            }
        } else {
            target.setRemainingFireTicks(previousFireTicks);
        }
        detonate();
        discard();
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        if (!level().isClientSide) {
            detonate();
            discard();
        }
    }

    private void detonate() {
        float radius = GolemServantConfig.MAGMA.FIREBALL_EXPLOSION_RADIUS.get().floatValue();
        if (radius > 0.0F) {
            level().explode(this, getX(), getY(), getZ(), radius, false, Level.ExplosionInteraction.NONE);
        }
    }
}
