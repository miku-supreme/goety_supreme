package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritSlashEntity;

public class SakuraSpiritSlashEntity extends Projectile {
    private static final EntityDataAccessor<Float> RADIUS =
            SynchedEntityData.defineId(SakuraSpiritSlashEntity.class, EntityDataSerializers.FLOAT);

    private static final float MAX_RADIUS = 3.0F;
    private static final float RADIUS_GROWTH = 0.12F;
    private static final double MAX_TRAVEL = 12.0D;
    private static final int MAX_AGE = 60;

    private static final float FALLBACK_DAMAGE = 6.0F;

    public float previousWidth;
    public int animationTime;

    @Nullable
    private LivingEntity shooter;
    private double travelled;
    private float damage = 0.0F;

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public SakuraSpiritSlashEntity(EntityType<? extends SakuraSpiritSlashEntity> type, Level level) {
        super(type, level);
        this.setRadius(0.6F);
        this.previousWidth = this.getDimensions(Pose.STANDING).width;
        this.noPhysics = true;
    }

    public SakuraSpiritSlashEntity(Level level, @Nullable LivingEntity shooter) {
        super(ModEntityTypes.SAKURA_SPIRIT_SLASH.get(), level);
        this.shooter = shooter;
        this.setRadius(0.6F);
        this.setOwner(shooter);
        if (shooter != null) {
            this.setYRot(shooter.getYRot());
            this.setXRot(shooter.getXRot());
            this.setPos(shooter.getX(), shooter.getEyeY() - 0.3D, shooter.getZ());
        }
        this.noPhysics = true;
        this.previousWidth = this.getDimensions(Pose.STANDING).width;
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(RADIUS, 0.6F);
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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Radius", this.getRadius());
        tag.putFloat("Damage", this.damage);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (RADIUS.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    public float getRadius() {
        return this.entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        this.entityData.set(RADIUS, Mth.clamp(radius, 0.0F, MAX_RADIUS));
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(this.getRadius() * 2.0F, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.travelled += this.getDeltaMovement().length();
            if (this.travelled > MAX_TRAVEL || this.tickCount > MAX_AGE) {
                this.discard();
                return;
            }
        }
        this.previousWidth = this.getDimensions(Pose.STANDING).width;
        this.setRadius(this.getRadius() + RADIUS_GROWTH);
        if (!this.level().isClientSide) {
            if (!this.level().getBlockState(this.blockPosition()).isAir()) {
                this.playSound(SoundEvents.CHERRY_LEAVES_BREAK);
                this.discard();
                return;
            }
            if (this.shooter == null) {
                this.shooter = this.getOwner() instanceof LivingEntity owner ? owner : null;
            }
            for (Entity entity : this.level().getEntities(this, this.getBoundingBox())) {
                if (!entity.isAlive() || entity == this.shooter || this.shooter == null) {
                    continue;
                }
                float damageValue = this.damage;
                if (damageValue <= 0.0F) {
                    damageValue = (float) this.shooter.getAttributeValue(Attributes.ATTACK_DAMAGE);
                }
                if (damageValue <= 0.0F) {
                    damageValue = FALLBACK_DAMAGE;
                }
                entity.hurt(this.damageSources().mobAttack(this.shooter), damageValue);
                float selfHeal = SakuraSpiritConfig.SLASH_SELF_HEAL.get().floatValue();
                if (selfHeal > 0.0F && this.shooter.getHealth() < this.shooter.getMaxHealth() * 0.6F) {
                    this.shooter.heal(selfHeal);
                }
                if (entity instanceof LivingEntity living) {
                    this.applyGoetySapped(living);
                }
                SoundEvent hitSound = ForgeRegistries.SOUND_EVENTS.getValue(
                        ResourceLocation.fromNamespaceAndPath("goety", "scythe_hit"));
                if (hitSound == null) {
                    hitSound = SoundEvents.PLAYER_ATTACK_SWEEP;
                }
                this.level().playSound((Player) null, entity.blockPosition(), hitSound, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
        this.setPos(this.position().add(this.getDeltaMovement()));
        if (this.level().isClientSide) {
            Vec3 motion = this.getDeltaMovement();
            if (motion.lengthSqr() > 0.01D) {
                Vec3 forward = motion.normalize();
                Vec3 side = perpendicular(forward);
                for (int i = 0; i < 2; i++) {
                    double angle = i * Math.PI;
                    double px = (side.x * Math.cos(angle) + forward.x * Math.sin(angle)) * 0.3D;
                    double pz = (side.z * Math.cos(angle) + forward.z * Math.sin(angle)) * 0.3D;
                    this.level().addParticle(ParticleTypes.CHERRY_LEAVES,
                            this.getX() + px, this.getY(), this.getZ() + pz, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    private void applyGoetySapped(LivingEntity victim) {
        if (this.shooter == null) {
            return;
        }
        MobEffect buff = goetyEffect("buff");
        MobEffect sapped = goetyEffect("sapped");
        if (sapped == null) {
            return;
        }
        int buffLevel = 0;
        if (buff != null) {
            MobEffectInstance instance = this.shooter.getEffect(buff);
            if (instance != null) {
                buffLevel = instance.getAmplifier() + 1;
            }
        }
        int duration = 60 + buffLevel / 2 * 20;
        int amplifier = buffLevel / 3;
        victim.addEffect(new MobEffectInstance(sapped, duration, amplifier));
    }

    @Nullable
    private static MobEffect goetyEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    public static Vec3 perpendicular(Vec3 forward) {
        Vec3 cross = forward.cross(new Vec3(0.0D, 1.0D, 0.0D));
        return cross.lengthSqr() < 0.001D ? new Vec3(1.0D, 0.0D, 0.0D) : cross.normalize();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}
