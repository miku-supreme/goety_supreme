package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PrismarineArrowEntity;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;

public class PrismarineArrowEntity extends Arrow {

    private static final int STRIKE_TRAP_DURATION = 10;

    private static final float STRIKE_TRAP_DAMAGE = 5.0F;

    private static final float STRIKE_TRAP_RADIUS = 2.0F;

    private static final int EXPLOSION_FUSE_TICKS = 40;

    private static final float EXPLOSION_RADIUS = 2.0F;

    private static final int SPASM_DURATION = 100;

    private static final double STORM_SHIFT = 3.0D;

    private static final double EMBED_DEPTH = 0.45D;

    private static final EntityDataAccessor<Integer> DATA_FUSE =
            SynchedEntityData.defineId(PrismarineArrowEntity.class, EntityDataSerializers.INT);

    private boolean thunder;

    private boolean finalStrike;

    private boolean ignoreInvulnerability;

    private boolean explosive;

    @Nullable
    private Entity stuckEntity;
    private int stuckEntityId = -1;
    private double stuckOffsetX;
    private double stuckOffsetY;
    private double stuckOffsetZ;

    public PrismarineArrowEntity(EntityType<? extends PrismarineArrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PrismarineArrowEntity(Level level, @Nullable LivingEntity shooter) {
        this(level, shooter, false);
    }

    public PrismarineArrowEntity(Level level, @Nullable LivingEntity shooter, boolean thunder) {
        super(ModEntityTypes.PRISMARINE_ARROW.get(), level);
        this.thunder = thunder;
        if (shooter != null) {
            this.setOwner(shooter);
            this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
        }
    }

    public boolean isThunder() {
        return this.thunder;
    }

    public void setFinalStrike(boolean finalStrike) {
        this.finalStrike = finalStrike;
    }

    public boolean isFinalStrike() {
        return this.finalStrike;
    }

    public void setExplosive(boolean explosive) {
        this.explosive = explosive;
    }

    public boolean isExplosive() {
        return this.explosive;
    }

    public void setIgnoreInvulnerability(boolean ignoreInvulnerability) {
        this.ignoreInvulnerability = ignoreInvulnerability;
    }

    public boolean isIgnoreInvulnerability() {
        return this.ignoreInvulnerability;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FUSE, 0);
    }

    private int getFuse() {
        return this.entityData.get(DATA_FUSE);
    }

    private void armFuse() {
        if (this.level().isClientSide) {
            return;
        }
        this.entityData.set(DATA_FUSE, EXPLOSION_FUSE_TICKS);
        this.setPierceLevel((byte) 0);
    }

    private void freeze() {
        this.setDeltaMovement(Vec3.ZERO);
        this.setNoGravity(true);
    }

    private void stickTo(Entity entity, Vec3 impactDirection) {
        Vec3 position = this.position();
        Vec3 center = new Vec3(entity.getX(),
                entity.getY() + entity.getBbHeight() * 0.5D,
                entity.getZ());
        Vec3 toCenter = center.subtract(position);
        Vec3 direction = impactDirection.lengthSqr() > 1.0E-6D
                ? impactDirection.normalize()
                : toCenter.normalize();
        double depth = Math.max(0.0D, Math.min(toCenter.dot(direction), EMBED_DEPTH));
        if (depth > 0.0D) {
            Vec3 embedded = position.add(direction.scale(depth));
            this.setPos(embedded.x, embedded.y, embedded.z);
        }
        this.stuckEntity = entity;
        this.stuckEntityId = entity.getId();
        this.stuckOffsetX = this.getX() - entity.getX();
        this.stuckOffsetY = this.getY() - entity.getY();
        this.stuckOffsetZ = this.getZ() - entity.getZ();
    }

    private void snapTo(Vec3 location) {
        this.setPos(location.x, location.y, location.z);
    }

    private static Vec3 effectAnchor(Entity entity) {
        return new Vec3(entity.getX(),
                entity.getY() + entity.getBbHeight() * 0.5D,
                entity.getZ());
    }

    private void followStuckEntity() {
        if (this.stuckEntity == null || !this.stuckEntity.isAlive()) {
            if (this.stuckEntityId != -1 && this.stuckEntity == null
                    && !this.level().isClientSide) {
                this.stuckEntity = this.level().getEntity(this.stuckEntityId);
            }
            if (this.stuckEntity == null || !this.stuckEntity.isAlive()) {
                return;
            }
        }
        this.setPos(this.stuckEntity.getX() + this.stuckOffsetX,
                this.stuckEntity.getY() + this.stuckOffsetY,
                this.stuckEntity.getZ() + this.stuckOffsetZ);
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.getFuse() > 0
                && reason != RemovalReason.UNLOADED_TO_CHUNK
                && reason != RemovalReason.UNLOADED_WITH_PLAYER
                && reason != RemovalReason.CHANGED_DIMENSION) {
            return;
        }
        super.remove(reason);
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(GeneratedMod.PRISMARINE_ARROW.get());
    }

    @Override
    public void tick() {
        int fuse = this.getFuse();
        if (fuse > 0) {
            this.xo = this.getX();
            this.yo = this.getY();
            this.zo = this.getZ();
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
            this.followStuckEntity();
            if (!this.level().isClientSide) {
                if (fuse <= 1) {
                    this.entityData.set(DATA_FUSE, 0);
                    this.explodeNow();
                    this.discard();
                    return;
                }
                this.entityData.set(DATA_FUSE, fuse - 1);
            }
            this.level().addParticle(ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.1D, this.getZ(),
                    0.0D, 0.02D, 0.0D);
            return;
        }
        super.tick();
        if (this.thunder && !this.level().isClientSide) {
            if (this.level().getRandom().nextInt(3) == 0) {
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        this.getX(), this.getY(), this.getZ(),
                        (this.random.nextDouble() - 0.5D) * 0.1D,
                        (this.random.nextDouble() - 0.5D) * 0.1D,
                        (this.random.nextDouble() - 0.5D) * 0.1D);
            }
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Vec3 impactDirection = this.getDeltaMovement();
        Vec3 impactLocation = result.getLocation();
        if (!this.level().isClientSide && this.ignoreInvulnerability
                && result.getEntity() instanceof LivingEntity hitEntity) {
            hitEntity.invulnerableTime = 0;
        }
        if (!this.level().isClientSide && this.explosive) {
            this.armFuse();
        }
        super.onHitEntity(result);
        if (!this.level().isClientSide && this.explosive) {
            this.snapTo(impactLocation);
            this.freeze();
            this.stickTo(result.getEntity(), impactDirection);
        }
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity victim && this.isEmpoweredShooter()) {
                victim.addEffect(new MobEffectInstance(GoetyEffects.SPASMS.get(), SPASM_DURATION, 0,
                        false, true, true));
            }
            this.handleSpecialHit(effectAnchor(result.getEntity()));
        }
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide && this.explosive) {
            this.armFuse();
        }
        super.onHitBlock(result);
        if (!this.level().isClientSide && this.explosive) {
            this.snapTo(result.getLocation());
            this.freeze();
        }
        if (!this.level().isClientSide) {
            this.handleSpecialHit(result.getLocation());
        }
    }

    private void handleSpecialHit(Vec3 at) {
        boolean empowered = this.isEmpoweredShooter();
        if (this.thunder && empowered) {
            this.strike(at);
        }
        if (this.finalStrike) {
            this.finalStrike = false;
            if (empowered) {
                this.summonTridentStorm(at);
            }
        }
    }

    private boolean isEmpoweredShooter() {
        return this.getOwner() instanceof PrismarineSoldier soldier && soldier.isLightningEmpowered();
    }

    private void summonTridentStorm(Vec3 at) {
        LivingEntity shooter = this.getOwner() instanceof LivingEntity living ? living : null;
        if (shooter == null) {
            return;
        }
        WandUtil.summonTridentStorm(shooter, at,
                (this.random.nextDouble() - 0.5D) * STORM_SHIFT,
                (this.random.nextDouble() - 0.5D) * STORM_SHIFT, 10, 1);
    }

    private void explodeNow() {
        LivingEntity shooter = this.getOwner() instanceof LivingEntity living ? living : null;
        DamageSource source = shooter instanceof PrismarineSoldier soldier
                ? ModDamageSource.summonAttack(soldier, soldier.getTrueOwner())
                : this.damageSources().explosion(this, this);
        this.level().explode(this, source, null,
                this.getX(), this.getY(), this.getZ(), EXPLOSION_RADIUS, false,
                Level.ExplosionInteraction.NONE);
    }

    private void strike(Vec3 at) {
        LivingEntity shooter = this.getOwner() instanceof LivingEntity living ? living : null;
        MagicLightningTrap trap = new MagicLightningTrap(this.level(), at.x, at.y, at.z);
        trap.setOwner(shooter);
        trap.setDuration(STRIKE_TRAP_DURATION);
        trap.setRadius(STRIKE_TRAP_RADIUS);
        trap.setDamage(STRIKE_TRAP_DAMAGE);
        this.level().addFreshEntity(trap);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Thunder")) {
            this.thunder = tag.getBoolean("Thunder");
        }
        if (tag.contains("Explosive")) {
            this.explosive = tag.getBoolean("Explosive");
        }
        if (tag.contains("FinalStrike")) {
            this.finalStrike = tag.getBoolean("FinalStrike");
        }
        if (tag.contains("IgnoreInvulnerability")) {
            this.ignoreInvulnerability = tag.getBoolean("IgnoreInvulnerability");
        }
        if (tag.contains("Fuse")) {
            this.entityData.set(DATA_FUSE, tag.getInt("Fuse"));
        }
        if (tag.contains("StuckEntity")) {
            this.stuckEntityId = tag.getInt("StuckEntity");
            this.stuckOffsetX = tag.getDouble("StuckOffX");
            this.stuckOffsetY = tag.getDouble("StuckOffY");
            this.stuckOffsetZ = tag.getDouble("StuckOffZ");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Thunder", this.thunder);
        tag.putBoolean("Explosive", this.explosive);
        tag.putBoolean("FinalStrike", this.finalStrike);
        tag.putBoolean("IgnoreInvulnerability", this.ignoreInvulnerability);
        tag.putInt("Fuse", this.getFuse());
        if (this.stuckEntityId != -1) {
            tag.putInt("StuckEntity", this.stuckEntityId);
            tag.putDouble("StuckOffX", this.stuckOffsetX);
            tag.putDouble("StuckOffY", this.stuckOffsetY);
            tag.putDouble("StuckOffZ", this.stuckOffsetZ);
        }
    }
}
