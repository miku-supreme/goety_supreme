package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.utils.SEHelper;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.model.ReaperScytheModel;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.spell.ShockwaveFocus;

public class ReaperSpinEntity extends Projectile {

    private static final int CONTACT_INTERVAL_TICKS = 5;

    private static final int SPIN_SOUL_PER_HIT = 10;

    private static final int OUTBOUND_TICKS = 60;

    private static final int MAX_LIFETIME = 170;

    private static final int RETURN_LIFETIME_MARGIN = 60;

    private static final double RETURN_REACH_SQR = 2.25D;

    private static final double TURN_RATE = 0.28D;

    private static final float SPIN_DEGREES_PER_TICK = 72.0F;

    private static final int SHOCKWAVE_POTENCY = 1;
    private static final int SHOCKWAVE_RADIUS = 4;

    private static final EntityDataAccessor<Float> DATA_WIDTH_SCALE =
            SynchedEntityData.defineId(ReaperSpinEntity.class, EntityDataSerializers.FLOAT);

    private float damage = AbstractReaperNecromancer.SPIN_DAMAGE;

    private int contactCooldown;

    private double speed = AbstractReaperNecromancer.SPIN_SPEED;

    private boolean returning;

    private int outboundTicks = OUTBOUND_TICKS;

    private boolean homingLost;
    private double closestDistSqr = Double.MAX_VALUE;
    private LivingEntity trackedTarget;

    public ReaperSpinEntity(EntityType<? extends ReaperSpinEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public ReaperSpinEntity(Level level, @Nullable LivingEntity shooter) {
        super(ModEntityTypes.REAPER_SPIN.get(), level);
        this.noPhysics = true;
        this.setOwner(shooter);
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setOutboundTicks(int ticks) {
        this.outboundTicks = Math.max(1, ticks);
    }

    public int getOutboundTicks() {
        return this.outboundTicks;
    }

    public boolean isOwnedBy(Entity entity) {
        return this.getOwner() == entity;
    }

    public boolean isReturning() {
        return this.returning;
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
        tag.putBoolean("Returning", this.returning);
        tag.putInt("OutboundTicks", this.outboundTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.damage = tag.getFloat("Damage");
        this.setWidthScale(tag.contains("WidthScale") ? tag.getFloat("WidthScale") : 1.0F);
        this.returning = tag.getBoolean("Returning");
        if (tag.contains("OutboundTicks")) {
            this.setOutboundTicks(tag.getInt("OutboundTicks"));
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4096.0D;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.contactCooldown > 0) {
            --this.contactCooldown;
        }
        if (this.level().isClientSide) {
            return;
        }
        this.tickFlight();
        if (this.isRemoved()) {
            return;
        }
        this.tickContact();
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.6D, this.getZ(),
                    2, 0.3D, 0.3D, 0.3D, 0.0D);
        }
        this.tickSpinRing();
        int lifetime = Math.max(MAX_LIFETIME, this.outboundTicks + RETURN_LIFETIME_MARGIN + OUTBOUND_TICKS);
        if (this.tickCount > lifetime) {
            this.fadeOut();
        }
    }

    private void tickSpinRing() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double phase = Math.toRadians(this.tickCount * SPIN_DEGREES_PER_TICK);
        int points = 6;
        double radius = 1.1D * this.getWidthScale();
        for (int i = 0; i < points; i++) {
            double angle = phase + i * (Math.PI * 2.0D / points);
            double px = this.getX() + Math.cos(angle) * radius;
            double pz = this.getZ() + Math.sin(angle) * radius;
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, this.getY() + 0.5D, pz,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void tickFlight() {
        if (this.returning) {
            Entity owner = this.getOwner();
            if (owner != null && this.distanceToSqr(owner) < RETURN_REACH_SQR) {
                this.fadeOut();
                return;
            }
        }
        if (!this.returning && this.tickCount >= this.outboundTicks) {
            this.returning = true;
        }

        Vec3 next = this.steer(this.getDeltaMovement());
        this.setDeltaMovement(next);

        if (next.lengthSqr() > 1.0E-6D) {
            double horiz = Math.sqrt(next.x * next.x + next.z * next.z);
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
            this.setYRot((float) (Mth.atan2(next.z, next.x) * (180.0D / Math.PI) + 90.0D));
            this.setXRot((float) (Mth.atan2(horiz, next.y) * (180.0D / Math.PI) - 90.0D));
        }

        Vec3 from = this.position();
        Vec3 to = from.add(next);
        BlockHitResult hit = this.level().clip(new ClipContext(from, to,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hit.getType() == HitResult.Type.BLOCK) {
            if (!this.returning) {
                this.returning = true;
                return;
            }
        }
        this.setPos(to.x, to.y, to.z);
    }

    private Vec3 steer(Vec3 current) {
        Vec3 aim = this.aimPoint();
        if (aim == null) {
            if (current.lengthSqr() > 1.0E-6D) {
                return current.normalize().scale(this.speed);
            }
            return this.getLookAngle().scale(this.speed);
        }
        Vec3 desired = aim.subtract(this.position());
        if (desired.lengthSqr() < 1.0E-6D) {
            return current.lengthSqr() > 1.0E-6D ? current.normalize().scale(this.speed)
                    : this.getLookAngle().scale(this.speed);
        }
        desired = desired.normalize().scale(this.speed);
        Vec3 blended;
        if (current.lengthSqr() < 1.0E-6D) {
            blended = desired;
        } else {
            blended = current.normalize().scale(1.0D - TURN_RATE).add(desired.scale(TURN_RATE));
            blended = blended.lengthSqr() < 1.0E-6D ? desired : blended.normalize();
        }
        return blended.scale(this.speed);
    }

    @Nullable
    private Vec3 aimPoint() {
        if (this.returning) {
            Entity owner = this.getOwner();
            if (owner instanceof LivingEntity living && living.isAlive()) {
                return living.position().add(0.0D, living.getBbHeight() * 0.5D, 0.0D);
            }
            return null;
        }
        LivingEntity target = this.resolveTarget();
        if (target == null) {
            return null;
        }
        if (target != this.trackedTarget) {
            this.trackedTarget = target;
            this.closestDistSqr = Double.MAX_VALUE;
        }
        double distSqr = this.distanceToSqr(target);
        double threshold = target.getBbWidth();
        if (this.closestDistSqr < Double.MAX_VALUE && distSqr > this.closestDistSqr
                && this.closestDistSqr < threshold * threshold) {
            this.homingLost = true;
            return null;
        }
        this.closestDistSqr = Math.min(this.closestDistSqr, distSqr);
        return target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
    }

    @Nullable
    private LivingEntity resolveTarget() {
        if (this.homingLost) {
            return null;
        }
        Entity owner = this.getOwner();
        if (owner instanceof AbstractReaperNecromancer caster) {
            LivingEntity target = caster.getTarget();
            if (target != null && target.isAlive() && !caster.isMyAlly(target)) {
                return target;
            }
            return null;
        }
        if (owner instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive() && target != owner
                    && !owner.isAlliedTo(target) && !target.isAlliedTo(owner)) {
                return target;
            }
        }
        if (owner instanceof LivingEntity living) {
            LivingEntity target = living.getLastHurtMob();
            if (target != null && target.isAlive() && target != owner
                    && !owner.isAlliedTo(target) && !target.isAlliedTo(owner)) {
                return target;
            }
        }
        return null;
    }

    private void tickContact() {
        AABB box = this.getBoundingBox().inflate(1.0D * this.getWidthScale());
        List<LivingEntity> victims = this.level().getEntitiesOfClass(LivingEntity.class, box,
                this::canHit);
        if (!victims.isEmpty() && this.contactCooldown <= 0) {
            this.contactCooldown = CONTACT_INTERVAL_TICKS;
            Entity owner = this.getOwner();
            for (LivingEntity victim : victims) {
                if (this.hurtIgnoringInvulnerability(victim)) {
                    this.homingLost = true;
                    if (owner instanceof AbstractReaperNecromancer caster) {
                        caster.harvestSoul(victim);
                        caster.applySoulJarSapped(victim);
                    } else if (owner instanceof Player player) {
                        SEHelper.increaseSouls(player, SPIN_SOUL_PER_HIT);
                    }
                }
            }
        }
    }

    private boolean hurtIgnoringInvulnerability(LivingEntity victim) {
        Entity owner = this.getOwner();
        int invulnerable = victim.invulnerableTime;
        victim.invulnerableTime = 0;
        boolean landed = victim.hurt(this.damageSources().indirectMagic(this, owner), this.damage);
        victim.invulnerableTime = invulnerable;
        return landed;
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
        if (owner.isAlliedTo(victim)) {
            return false;
        }
        return !victim.isAlliedTo(owner);
    }

    public void detonate() {
        if (!(this.level() instanceof ServerLevel server)) {
            this.discard();
            return;
        }
        Entity owner = this.getOwner();
        LivingEntity caster = owner instanceof LivingEntity living ? living : null;
        double x = this.getX();
        double y = this.getY() + 0.5D;
        double z = this.getZ();
        ShockwaveFocus.cast(server, caster, x, y, z, SHOCKWAVE_RADIUS, SHOCKWAVE_POTENCY);
        if (caster instanceof AbstractReaperNecromancer reaper) {
            reaper.applySoulJarSappedAround(server, x, y, z, SHOCKWAVE_RADIUS + 1.0D);
        }
        this.discard();
    }

    private void fadeOut() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.6D, this.getZ(),
                    12, 0.4D, 0.4D, 0.4D, 0.02D);
        }
        this.discard();
    }

    public float spinDegrees(float partialTick) {
        return (this.tickCount + partialTick) * SPIN_DEGREES_PER_TICK;
    }
}
