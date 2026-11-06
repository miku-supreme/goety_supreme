package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.WarlordBladeTrail;
import cn.blockforge.generated.goetysupreme.renderer.WarlordBladeTrailRenderer;

public class WarlordBladeTrail extends Entity {

    private static final int FLAME_COUNT = 3;

    public static final int LIFETIME = 16;

    private static final EntityDataAccessor<Float> DATA_YAW =
            SynchedEntityData.defineId(WarlordBladeTrail.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> DATA_ROLL =
            SynchedEntityData.defineId(WarlordBladeTrail.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> DATA_SCALE =
            SynchedEntityData.defineId(WarlordBladeTrail.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> DATA_LIFE =
            SynchedEntityData.defineId(WarlordBladeTrail.class, EntityDataSerializers.INT);

    public int animationTime;

    public WarlordBladeTrail(EntityType<? extends WarlordBladeTrail> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public WarlordBladeTrail(Level level, Vec3 pos, float yaw, float roll, float scale, int life) {
        super(ModEntityTypes.WARLORD_BLADE_TRAIL.get(), level);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setPos(pos.x, pos.y, pos.z);
        this.setYRot(yaw);
        this.yRotO = yaw;
        this.setTrailYaw(yaw);
        this.setTrailRoll(roll);
        this.setTrailScale(scale);
        this.setLife(life);
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_YAW, 0.0F);
        this.entityData.define(DATA_ROLL, 0.0F);
        this.entityData.define(DATA_SCALE, 2.5F);
        this.entityData.define(DATA_LIFE, 10);
    }

    public float getTrailYaw() {
        return this.entityData.get(DATA_YAW);
    }

    private void setTrailYaw(float yaw) {
        this.entityData.set(DATA_YAW, yaw);
    }

    public float getTrailRoll() {
        return this.entityData.get(DATA_ROLL);
    }

    private void setTrailRoll(float roll) {
        this.entityData.set(DATA_ROLL, roll);
    }

    public float getTrailScale() {
        return this.entityData.get(DATA_SCALE);
    }

    private void setTrailScale(float scale) {
        this.entityData.set(DATA_SCALE, scale);
    }

    public int getLife() {
        return Math.max(1, this.entityData.get(DATA_LIFE));
    }

    private void setLife(int life) {
        this.entityData.set(DATA_LIFE, life);
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(Vec3.ZERO);
        if (this.level().isClientSide) {
            this.spawnFlames();
        } else if (this.tickCount >= this.getLife()) {
            this.discard();
        }
    }

    private void spawnFlames() {
        float s = this.getTrailScale();
        double yawRad = Math.toRadians(-this.getTrailYaw() - 90.0D);
        double rollRad = Math.toRadians(this.getTrailRoll());
        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);
        double cosRoll = Math.cos(rollRad);
        double sinRoll = Math.sin(rollRad);
        Vec3 base = this.position();
        float progress = Mth.clamp((this.tickCount + 0.5F) / this.getLife(), 0.0F, 1.0F);
        float revealed = Math.min(1.0F, progress * 1.45F);
        for (int i = 0; i < FLAME_COUNT; i++) {
            float t = 0.04F + this.random.nextFloat() * 0.92F * Math.max(0.12F, revealed);
            double arc = Math.PI * (double) t;
            double lx = (-0.35D + 1.10D * Math.sin(arc)) * (double) s;
            double lz = 0.82D * Math.cos(arc) * (double) s;
            lx += (this.random.nextDouble() - 0.5D) * 0.18D * (double) s;
            lz += (this.random.nextDouble() - 0.5D) * 0.18D * (double) s;
            double rx = lx * cosRoll;
            double ry = lx * sinRoll;
            double wx = rx * cosYaw + lz * sinYaw;
            double wz = -rx * sinYaw + lz * cosYaw;
            double px = base.x + wx;
            double py = base.y + ry + (this.random.nextDouble() - 0.5D) * 0.14D;
            double pz = base.z + wz;
            double vx = (this.random.nextDouble() - 0.5D) * 0.02D;
            double vy = this.random.nextDouble() * 0.035D;
            double vz = (this.random.nextDouble() - 0.5D) * 0.02D;
            this.level().addParticle(ParticleTypes.FLAME, px, py, pz, vx, vy, vz);
            if (this.random.nextFloat() < 0.4F) {
                this.level().addParticle(ParticleTypes.SMALL_FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
            }
            if (this.random.nextFloat() < 0.14F) {
                this.level().addParticle(ParticleTypes.LAVA, px, py, pz, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.setTrailYaw(tag.getFloat("TrailYaw"));
        this.setTrailRoll(tag.getFloat("TrailRoll"));
        this.setTrailScale(tag.getFloat("TrailScale"));
        this.setLife(tag.getInt("TrailLife"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("TrailYaw", this.getTrailYaw());
        tag.putFloat("TrailRoll", this.getTrailRoll());
        tag.putFloat("TrailScale", this.getTrailScale());
        tag.putInt("TrailLife", this.getLife());
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distanceSqr) {
        return distanceSqr < 128.0D * 128.0D;
    }
}
