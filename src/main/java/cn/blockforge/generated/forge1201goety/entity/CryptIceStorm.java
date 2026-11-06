package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.IceSpike;
import com.Polarice3.Goety.common.entities.projectiles.IceStorm;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.CryptIceStorm;

public class CryptIceStorm extends IceStorm {

    private static final int RING_INTERVAL = 16;

    private static final int MIN_SPIKES = 5;
    private static final int MAX_SPIKES = 8;

    private static final float SPIKE_SPEED = 1.35F;

    public CryptIceStorm(EntityType<? extends CryptIceStorm> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.isRemoved()) {
            return;
        }
        if (this.level() instanceof ServerLevel server && this.tickCount % 2 == 0) {
            server.sendParticles(ParticleTypes.SNOWFLAKE,
                    this.getX(), this.getY() + 0.5D, this.getZ(),
                    10, this.getSize() * 0.4D, this.getSize() * 0.3D, this.getSize() * 0.4D, 0.04D);
        }
        if (this.tickCount % RING_INTERVAL == 0) {
            this.fireRing();
        }
    }

    private void fireRing() {
        if (!(this.getOwner() instanceof LivingEntity owner)) {
            return;
        }
        int count = MIN_SPIKES + this.random.nextInt(MAX_SPIKES - MIN_SPIKES + 1);
        double angleStep = Math.PI * 2.0D / count;
        for (int i = 0; i < count; ++i) {
            double angle = angleStep * i;
            double dx = Math.cos(angle);
            double dz = Math.sin(angle);
            IceSpike spike = new IceSpike(owner, this.level());
            spike.setPos(this.getX() + dx * 0.5D, this.getY() + 0.4D, this.getZ() + dz * 0.5D);
            spike.shoot(dx, 0.0D, dz, SPIKE_SPEED, 2.0F);
            this.level().addFreshEntity(spike);
        }
        this.playSound(com.Polarice3.Goety.init.ModSounds.ICE_SPIKE_CAST.get(), 1.2F, 1.4F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SNOWFLAKE,
                    this.getX(), this.getY() + 0.4D, this.getZ(), 16, 0.7D, 0.3D, 0.7D, 0.08D);
        }
    }
}
