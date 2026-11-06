package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.Polarice3.Goety.utils.TrailEffect;
import com.mojang.math.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ReaperBladeEntity;

public class ReaperBladeEntity extends Projectile {

    private static final int LIFETIME = 60;
    private static final double MAX_TRAVEL = 24.0D;

    private static final EntityDataAccessor<Float> DATA_WIDTH_SCALE =
            SynchedEntityData.defineId(ReaperBladeEntity.class, EntityDataSerializers.FLOAT);

    private float damage = AbstractReaperNecromancer.BLADE_DAMAGE;

    private final List<UUID> hitTargets = new ArrayList<>();
    private double travelled;

    public final TrailEffect leftTrail = new TrailEffect(0.15F, 6.0F);
    public final TrailEffect rightTrail = new TrailEffect(0.15F, 6.0F);

    public ReaperBladeEntity(EntityType<? extends ReaperBladeEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public ReaperBladeEntity(Level level, @Nullable LivingEntity shooter) {
        super(ModEntityTypes.REAPER_BLADE.get(), level);
        this.noPhysics = true;
        this.setOwner(shooter);
        if (shooter != null) {
            this.setYRot(shooter.getYRot());
            this.setXRot(shooter.getXRot());
        }
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setWidthScale(float scale) {
        this.entityData.set(DATA_WIDTH_SCALE, scale);
        this.refreshDimensions();
    }

    public float getWidthScale() {
        return this.entityData.get(DATA_WIDTH_SCALE);
    }

    public float getDamage() {
        return this.damage;
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_WIDTH_SCALE, 1.0F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_WIDTH_SCALE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions base = super.getDimensions(pose);
        float scale = this.getWidthScale();
        return scale == 1.0F ? base : base.scale(scale, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Damage", this.damage);
        tag.putFloat("WidthScale", this.getWidthScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.damage = tag.getFloat("Damage");
        this.setWidthScale(tag.contains("WidthScale") ? tag.getFloat("WidthScale") : 1.0F);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4096.0D;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 delta = this.getDeltaMovement();
        Vec3 oldPos = this.position();
        this.setPos(this.getX() + delta.x, this.getY() + delta.y, this.getZ() + delta.z);
        this.travelled += delta.length();
        this.setYRot((float) (Mth.atan2(delta.z, delta.x) * (180.0F / (float) Math.PI) + 90.0D));
        this.setXRot((float) (Mth.atan2(Math.sqrt(delta.x * delta.x + delta.z * delta.z), delta.y)
                * (180.0F / (float) Math.PI) - 90.0D));

        if (this.level().isClientSide) {
            this.updateTrails(oldPos);
            return;
        }
        AABB box = this.getBoundingBox().inflate(0.5D * this.getWidthScale());
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!this.canHit(victim) || this.hitTargets.contains(victim.getUUID())) {
                continue;
            }
            this.hitTargets.add(victim.getUUID());
            Entity owner = this.getOwner();
            if (victim.hurt(this.damageSources().indirectMagic(this, owner), this.damage)) {
                if (owner instanceof AbstractReaperNecromancer caster) {
                    caster.harvestSoul(victim);
                    caster.applySoulJarSapped(victim);
                }
            }
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(),
                    2, 0.05D, 0.05D, 0.05D, 0.0D);
        }
        if (this.tickCount > LIFETIME || this.travelled > MAX_TRAVEL) {
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(),
                        6, 0.2D, 0.2D, 0.2D, 0.01D);
                server.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 0.4F, 1.4F);
            }
            this.discard();
        }
    }

    private void updateTrails(Vec3 oldPos) {
        if (this.tickCount <= 5) {
            return;
        }
        float age = this.tickCount + 1.0F;
        Matrix4f transform = new Matrix4f();
        transform.rotate(Axis.YP.rotationDegrees(-this.getYRot() + 180.0F));
        transform.rotate(Axis.XP.rotationDegrees(this.getXRot()));
        Vector4f left = transform.transform(new Vector4f(1.0F, Mth.sin(age * 0.4F) * 0.2F, 0.0F, 1.0F));
        Vector4f right = transform.transform(new Vector4f(-1.0F, Mth.cos(age * 0.4F) * 0.2F, 0.0F, 1.0F));
        this.leftTrail.update(oldPos.add(left.x(), left.y(), left.z()));
        this.rightTrail.update(oldPos.add(right.x(), right.y(), right.z()));
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
        if (owner instanceof AbstractReaperNecromancer caster && caster.isMyAlly(victim)) {
            return false;
        }
        return !victim.isAlliedTo(owner);
    }
}
