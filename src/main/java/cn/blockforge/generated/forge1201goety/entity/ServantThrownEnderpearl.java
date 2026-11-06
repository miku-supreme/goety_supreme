package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownEnderpearl;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownTrident;

public final class ServantThrownEnderpearl extends ThrownEnderpearl {

    @Nullable
    private Vec3 pearlLanding;

    public ServantThrownEnderpearl(Level level, LivingEntity owner) {
        super(level, owner);
    }

    public void setPearlLanding(@Nullable Vec3 landing) {
        this.pearlLanding = landing;
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (getOwner() instanceof PlayerMobServant servant && servant.isAlliedTo(target)) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    public void onHit(HitResult result) {
        for (int i = 0; i < 32; i++) {
            level().addParticle(ParticleTypes.PORTAL,
                    getX(), getY() + random.nextDouble() * 2.0D, getZ(),
                    random.nextGaussian(), 0.0D, random.nextGaussian());
        }
        if (!level().isClientSide) {
            Entity owner = getOwner();
            if (owner instanceof PlayerMobServant servant && servant.isAlive()) {
                servant.arriveByEnderPearl(pearlLanding != null ? pearlLanding : position());
            } else if (owner != null) {
                owner.teleportTo(getX(), getY(), getZ());
                owner.resetFallDistance();
            }
            discard();
        }
    }
}
