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
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolemFireball;

public class FurnaceGolemFireball extends SmallFireball {

    public static final double ACCELERATION_POWER = 0.1D;

    public FurnaceGolemFireball(EntityType<? extends SmallFireball> type, Level level) {
        super(type, level);
    }

    public FurnaceGolemFireball(Level level, LivingEntity owner, double dx, double dy, double dz) {
        this(EntityType.SMALL_FIREBALL, level);
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
        if (target == getOwner() || (getOwner() != null && getOwner().isAlliedTo(target))) {
            discard();
            return;
        }
        int invulnerable = target.invulnerableTime;
        target.invulnerableTime = 0;
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        target.setSecondsOnFire(furnace.FIRE_BREATH_BURNING.get() * 5);
        if (target.hurt(damageSources().fireball(this, getOwner()),
                furnace.FIRE_BREATH_FIREBALL_DAMAGE.get().floatValue())) {
            if (getOwner() instanceof LivingEntity living) {
                doEnchantDamageEffects(living, target);
            }
        }
        target.invulnerableTime = Math.max(invulnerable, target.invulnerableTime);
        discard();
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        if (!level().isClientSide) {
            discard();
        }
    }
}
