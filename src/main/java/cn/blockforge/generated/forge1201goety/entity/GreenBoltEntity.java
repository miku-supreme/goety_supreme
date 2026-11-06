package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenBoltEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class GreenBoltEntity extends Projectile {

    private static final int LIFETIME = 60;
    private static final double MAX_TRAVEL = 18.0D;

    private float damage = AbstractGreenSmith.BOLT_DAMAGE;
    private double travelled;
    private boolean spent;

    public GreenBoltEntity(EntityType<? extends GreenBoltEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public GreenBoltEntity(Level level, @Nullable LivingEntity shooter) {
        this(ModEntityTypes.GREEN_BOLT.get(), level);
        this.setOwner(shooter);
        if (shooter != null) {
            this.setYRot(shooter.getYRot());
            this.setXRot(shooter.getXRot());
        }
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    @Override
    public void defineSynchedData() {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.damage);
        tag.putDouble("Travelled", this.travelled);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getFloat("Damage");
        this.travelled = tag.getDouble("Travelled");
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        Vec3 dir = new Vec3(x, y, z).normalize();
        this.setDeltaMovement(dir.scale(velocity));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.spent) {
            return;
        }
        Vec3 delta = this.getDeltaMovement();
        Vec3 next = this.position().add(delta);
        this.setPos(next.x, next.y, next.z);
        this.travelled += delta.length();
        if (this.level().isClientSide) {
            return;
        }
        BlockPos pos = this.blockPosition();
        if (!this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty()) {
            this.puff();
            if (this.getOwner() instanceof AbstractGreenSmith smith
                    && this.level() instanceof ServerLevel server) {
                smith.spawnGreenFire(server, this.getX(), this.getY(), this.getZ());
            }
            this.discard();
            return;
        }
        AABB box = this.getBoundingBox().inflate(0.4D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!this.canHit(victim)) {
                continue;
            }
            victim.hurt(this.damageSources().indirectMagic(this, this.getOwner()), this.damage);
            this.puff();
            this.discard();
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(
                    GreenSmithFx.necroFire(),
                    this.getX(), this.getY(), this.getZ(), 2, 0.03D, 0.03D, 0.03D, 0.0D);
        }
        if (this.tickCount > LIFETIME || this.travelled > MAX_TRAVEL) {
            this.puff();
            this.discard();
        }
    }

    private void puff() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(
                    GreenSmithFx.necroFire(),
                    this.getX(), this.getY(), this.getZ(), 8, 0.15D, 0.15D, 0.15D, 0.02D);
            this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5F, 1.4F);
        }
        this.spent = true;
    }

    private boolean canHit(LivingEntity victim) {
        if (!victim.isAlive() || victim.isSpectator()) {
            return false;
        }
        Entity owner = this.getOwner();
        if (owner == null) {
            return true;
        }
        if (victim == owner) {
            return false;
        }
        if (owner instanceof AbstractGreenSmith smith && smith.isMyAlly(victim)) {
            return false;
        }
        return !victim.isAlliedTo(owner);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4096.0D;
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}
