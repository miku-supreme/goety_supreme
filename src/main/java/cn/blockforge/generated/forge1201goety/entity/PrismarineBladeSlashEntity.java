package cn.blockforge.generated.goetysupreme.entity;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
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
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PrismarineBladeSlashEntity;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineBladeSlashRenderer;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;

public class PrismarineBladeSlashEntity extends Projectile {
    private static final EntityDataAccessor<Float> DATA_RADIUS =
            SynchedEntityData.defineId(PrismarineBladeSlashEntity.class, EntityDataSerializers.FLOAT);

    private static final float INITIAL_RADIUS = 0.9F;
    private static final float MAX_RADIUS = 3.0F;
    private static final float RADIUS_GROWTH = 0.12F;

    private static final float BLADE_WIDTH = 0.5F;
    private static final double MAX_TRAVEL = 10.0D;
    private static final int MAX_AGE = 46;

    public float previousRadius;
    public int animationTime;

    @Nullable
    private LivingEntity shooter;
    private double travelled;
    private float damage = 0.0F;
    private float maxRadius = MAX_RADIUS;
    private int maxLifeSpan = MAX_AGE;
    private final Set<Entity> victims = new HashSet<>();

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    public void markPreVictim(Entity entity) {
        if (entity != null) {
            this.victims.add(entity);
        }
    }

    public PrismarineBladeSlashEntity(EntityType<? extends PrismarineBladeSlashEntity> type, Level level) {
        super(type, level);
        this.setRadius(INITIAL_RADIUS);
        this.previousRadius = this.getRadius();
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public PrismarineBladeSlashEntity(Level level, @Nullable LivingEntity shooter) {
        super(ModEntityTypes.PRISMARINE_BLADE_SLASH.get(), level);
        this.shooter = shooter;
        this.setRadius(INITIAL_RADIUS);
        this.setOwner(shooter);
        if (shooter != null) {
            this.setYRot(shooter.getYRot());
            this.setXRot(shooter.getXRot());
            this.setPos(shooter.getX(), shooter.getEyeY() - 0.3D, shooter.getZ());
        }
        this.noPhysics = true;
        this.setNoGravity(true);
        this.previousRadius = this.getRadius();
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_RADIUS, INITIAL_RADIUS);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Radius")) {
            this.setRadius(tag.getFloat("Radius"));
        }
        if (tag.contains("Damage")) {
            this.damage = tag.getFloat("Damage");
        }
        if (tag.contains("MaxLifeSpan")) {
            this.maxLifeSpan = tag.getInt("MaxLifeSpan");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Radius", this.getRadius());
        tag.putFloat("Damage", this.damage);
        tag.putInt("MaxLifeSpan", this.maxLifeSpan);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_RADIUS.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    public float getRadius() {
        return this.entityData.get(DATA_RADIUS);
    }

    public void setRadius(float radius) {
        this.entityData.set(DATA_RADIUS, Mth.clamp(radius, 0.0F, this.maxRadius));
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(BLADE_WIDTH, this.getRadius() * 2.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.travelled += this.getDeltaMovement().length();
            if (this.travelled > MAX_TRAVEL || this.tickCount > this.maxLifeSpan) {
                this.discard();
                return;
            }
        }
        this.previousRadius = this.getRadius();
        this.setRadius(this.getRadius() + RADIUS_GROWTH);
        if (!this.level().isClientSide) {
            if (!this.level().getBlockState(this.blockPosition()).isAir()) {
                this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
                this.discard();
                return;
            }
            if (this.shooter == null) {
                this.shooter = this.getOwner() instanceof LivingEntity owner ? owner : null;
            }
            AABB box = this.getBoundingBox();
            for (Entity entity : this.level().getEntities(this, box)) {
                if (!entity.isAlive() || entity == this.shooter || this.shooter == null) {
                    continue;
                }
                if (this.victims.contains(entity)) {
                    continue;
                }
                if (this.shooter.isAlliedTo(entity)) {
                    continue;
                }
                float damageValue = this.damage > 0.0F
                        ? this.damage
                        : (float) this.shooter.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
                this.victims.add(entity);
                entity.hurt(this.damageSources().mobAttack(this.shooter), damageValue);
            }
        }
        this.setPos(this.position().add(this.getDeltaMovement()));
        if (this.level().isClientSide) {
            Vec3 motion = this.getDeltaMovement();
            if (motion.lengthSqr() > 0.01D) {
                Vec3 forward = motion.normalize();
                Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);
                Vec3 side = forward.cross(up);
                if (side.lengthSqr() < 0.001D) {
                    side = new Vec3(1.0D, 0.0D, 0.0D);
                }
                side = side.normalize();
                for (int i = 0; i < 3; i++) {
                    double angle = i * (Math.PI * 2.0D / 3.0D);
                    double r = 0.35D;
                    double px = side.x * Math.cos(angle) * r;
                    double py = up.y * Math.sin(angle) * r;
                    double pz = side.z * Math.cos(angle) * r;
                    this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                            this.getX() + px, this.getY() + py, this.getZ() + pz,
                            0.0D, 0.01D, 0.0D);
                }
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}
