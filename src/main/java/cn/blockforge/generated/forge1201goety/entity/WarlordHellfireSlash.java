package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.Hellfire;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.renderer.WarlordHellfireSlashRenderer;

public class WarlordHellfireSlash extends Projectile {

    private static final EntityDataAccessor<Float> DATA_RADIUS =
            SynchedEntityData.defineId(WarlordHellfireSlash.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> DATA_WIDTH_SCALE =
            SynchedEntityData.defineId(WarlordHellfireSlash.class, EntityDataSerializers.FLOAT);

    public static final float BASE_RADIUS = 1.2F;

    public static final float RADIUS_GROWTH = 0.24F;

    public static final float MAX_RADIUS = 6.0F;

    public static final double SPEED = 0.375D;

    public static final float DAMAGE = 15.0F;

    public static final double MAX_TRAVEL = 15.0D;

    private static final int MAX_AGE = 80;

    private static final double CROSS_ARM = 2.0D;

    private static final int CROSS_SEGMENTS = 2;

    private static final int HELLFIRE_LIFETIME = 140;

    public int animationTime;

    private static final double HOMING_TURN_RATE = 0.14D;

    private static final double HOMING_MAX_RANGE_SQR = 20.0D * 20.0D;

    @Nullable
    private LivingEntity shooter;

    @Nullable
    private LivingEntity trackTarget;
    @Nullable
    private java.util.UUID trackTargetId;
    private double travelled;
    private boolean homingLost;
    private double closestDistSqr = Double.MAX_VALUE;
    private final Set<Entity> victims = new HashSet<>();

    public WarlordHellfireSlash(EntityType<? extends WarlordHellfireSlash> type, Level level) {
        super(type, level);
        this.setRadius(BASE_RADIUS);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public WarlordHellfireSlash(Level level, @Nullable LivingEntity shooter) {
        super(ModEntityTypes.WARLORD_HELLFIRE_SLASH.get(), level);
        this.shooter = shooter;
        this.setOwner(shooter);
        this.setRadius(BASE_RADIUS);
        this.noPhysics = true;
        this.setNoGravity(true);
        if (shooter != null) {
            this.setYRot(shooter.getYRot());
            this.setXRot(shooter.getXRot());
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_RADIUS, BASE_RADIUS);
        this.entityData.define(DATA_WIDTH_SCALE, 1.0F);
    }

    public float getRadius() {
        return this.entityData.get(DATA_RADIUS);
    }

    public float getWidthScale() {
        return this.entityData.get(DATA_WIDTH_SCALE);
    }

    public void setWidthScale(float scale) {
        float clamped = Mth.clamp(scale, 0.1F, 1.0F);
        this.entityData.set(DATA_WIDTH_SCALE, clamped);
        this.setRadius(BASE_RADIUS * clamped);
    }

    private void setRadius(float radius) {
        this.entityData.set(DATA_RADIUS, Mth.clamp(radius, 0.0F, MAX_RADIUS * this.getWidthScale()));
    }

    public void setTrackingTarget(@Nullable LivingEntity target) {
        this.trackTarget = target;
        this.trackTargetId = target == null ? null : target.getUUID();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("WidthScale")) {
            this.setWidthScale(tag.getFloat("WidthScale"));
        }
        if (tag.contains("Radius")) {
            this.setRadius(tag.getFloat("Radius"));
        }
        this.travelled = tag.getDouble("Travelled");
        if (tag.hasUUID("TrackTarget")) {
            this.trackTargetId = tag.getUUID("TrackTarget");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("WidthScale", this.getWidthScale());
        tag.putFloat("Radius", this.getRadius());
        tag.putDouble("Travelled", this.travelled);
        if (this.trackTargetId != null) {
            tag.putUUID("TrackTarget", this.trackTargetId);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_RADIUS.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(this.getRadius() * 2.0F, 0.5F);
    }

    @Override
    public void tick() {
        super.tick();
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        if (this.level().isClientSide) {
            this.spawnFlightFlames();
        } else {
            this.applyHoming();
            this.travelled += this.getDeltaMovement().length();
            this.updateFacing();
            this.hitEntities();
            BlockPos pos = this.blockPosition();
            if (!this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty()) {
                this.shatter();
                return;
            }
            if (this.travelled >= MAX_TRAVEL || this.tickCount > MAX_AGE) {
                this.shatter();
                return;
            }
        }

        float scale = this.getWidthScale();
        if (this.getRadius() < MAX_RADIUS * scale) {
            this.setRadius(this.getRadius() + RADIUS_GROWTH * scale);
        }
        this.setPos(this.position().add(this.getDeltaMovement()));
    }

    private void applyHoming() {
        if (this.homingLost) {
            return;
        }
        if (this.trackTarget == null || !this.trackTarget.isAlive()) {
            this.trackTarget = resolveTrackTarget();
        }
        LivingEntity target = this.trackTarget;
        if (target == null || target.distanceToSqr(this) > HOMING_MAX_RANGE_SQR) {
            return;
        }
        double distSqr = this.distanceToSqr(target);
        double threshold = this.getRadius();
        if (this.closestDistSqr < Double.MAX_VALUE && distSqr > this.closestDistSqr
                && this.closestDistSqr < threshold * threshold) {
            this.homingLost = true;
            return;
        }
        this.closestDistSqr = Math.min(this.closestDistSqr, distSqr);
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() < 1.0E-8D) {
            return;
        }
        Vec3 wanted = target.getEyePosition().subtract(this.position());
        if (wanted.lengthSqr() < 1.0E-8D) {
            return;
        }
        Vec3 blended = motion.normalize().add(wanted.normalize().scale(HOMING_TURN_RATE))
                .normalize().scale(SPEED);
        this.setDeltaMovement(blended);
    }

    @Nullable
    private LivingEntity resolveTrackTarget() {
        if (this.trackTargetId == null || this.level().isClientSide
                || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        if (server.getEntity(this.trackTargetId) instanceof LivingEntity living && living.isAlive()) {
            this.trackTarget = living;
        }
        return this.trackTarget;
    }

    private void updateFacing() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() < 1.0E-6D) {
            return;
        }
        double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        this.setYRot((float) (Mth.atan2(motion.x, motion.z) * (180.0D / Math.PI)));
        this.setXRot((float) (Mth.atan2(motion.y, horizontal) * (180.0D / Math.PI)));
    }

    private void hitEntities() {
        if (this.shooter == null) {
            this.shooter = this.getOwner() instanceof LivingEntity owner ? owner : null;
        }
        AABB box = this.getBoundingBox();
        for (Entity entity : this.level().getEntities(this, box)) {
            if (!(entity instanceof LivingEntity living) || !living.isAlive() || living == this.shooter) {
                continue;
            }
            if (this.shooter != null && this.shooter.isAlliedTo(living)) {
                continue;
            }
            if (this.victims.contains(living)) {
                continue;
            }
            this.victims.add(living);
            this.homingLost = true;
            if (this.shooter instanceof AbstractWitherWarlord warlord) {
                warlord.applyHellfireSlashDamage(living);
            } else if (this.shooter instanceof Player player) {
                living.hurt(this.damageSources().playerAttack(player), DAMAGE);
            } else if (this.shooter != null) {
                living.hurt(this.damageSources().mobAttack(this.shooter), DAMAGE);
            } else {
                living.hurt(this.damageSources().generic(), DAMAGE);
            }
        }
    }

    private void shatter() {
        if (this.level() instanceof ServerLevel server) {
            Vec3 at = this.groundImpactPoint(this.position());
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, at.x, at.y, at.z, 1,
                    0.0D, 0.0D, 0.0D, 0.0D);
            server.sendParticles(ParticleTypes.FLAME, at.x, at.y, at.z, 40,
                    1.4D, 0.8D, 1.4D, 0.05D);
            Vec3 motion = this.getDeltaMovement();
            Vec3 dir = new Vec3(motion.x, 0.0D, motion.z);
            if (dir.lengthSqr() < 1.0E-6D) {
                dir = new Vec3(0.0D, 0.0D, 1.0D);
            }
            dir = dir.normalize();
            Vec3 side = new Vec3(-dir.z, 0.0D, dir.x);
            if (side.lengthSqr() < 1.0E-6D) {
                side = new Vec3(1.0D, 0.0D, 0.0D);
            }
            side = side.normalize();
            spawnHellfire(server, at);
            for (int arm = 1; arm <= CROSS_SEGMENTS; arm++) {
                double d = CROSS_ARM * arm / CROSS_SEGMENTS;
                spawnHellfire(server, at.add(side.scale(d)));
                spawnHellfire(server, at.add(side.scale(-d)));
                spawnHellfire(server, at.add(dir.scale(d)));
                spawnHellfire(server, at.add(dir.scale(-d)));
            }
        }
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.5F, 0.7F);
        this.playSound(goetySound("hell_blast_impact"), 1.8F, 0.9F);
        this.discard();
    }

    private Vec3 groundImpactPoint(Vec3 at) {
        BlockPos pos = BlockPos.containing(at);
        int minY = this.level().getMinBuildHeight();
        while (pos.getY() > minY
                && this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty()) {
            pos = pos.below();
        }
        return new Vec3(at.x, pos.getY() + 1.0D, at.z);
    }

    @Nullable
    private static net.minecraft.sounds.SoundEvent goetySound(String path) {
        return net.minecraftforge.registries.ForgeRegistries.SOUND_EVENTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    private void spawnHellfire(ServerLevel server, Vec3 at) {
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        Hellfire fire = new Hellfire(server, at, owner);
        fire.lifeTicks = HELLFIRE_LIFETIME;
        server.addFreshEntity(fire);
    }

    private static final float FLAME_ARC_RADIUS = 1.083F;
    private static final float FLAME_ARC_CENTER_Z = -0.804F;
    private static final float FLAME_ARC_THICK = 0.24F;

    private static final float FLAME_ARC_HALF_ANGLE = 1.05F;

    private static final float FLAME_LOCAL_Y = -0.10F;

    private void spawnFlightFlames() {
        float radius = this.getRadius();
        if (radius <= 0.02F) {
            return;
        }
        float yaw = this.getYRot() * (float) (Math.PI / 180.0);
        float pitch = this.getXRot() * (float) (Math.PI / 180.0);
        float cy = Mth.cos(yaw);
        float sy = Mth.sin(yaw);
        float cp = Mth.cos(pitch);
        float sp = Mth.sin(pitch);
        Vec3 right = new Vec3(cy, 0.0D, -sy);
        Vec3 up = new Vec3(-sy * sp, cp, -cy * sp);
        Vec3 forward = new Vec3(sy * cp, sp, cy * cp);
        Vec3 origin = this.position();

        RandomSource rnd = this.random;
        int count = Mth.clamp((int) (radius * 2.0F), 4, 16);
        for (int i = 0; i < count; i++) {
            double phi = (rnd.nextDouble() * 2.0D - 1.0D) * FLAME_ARC_HALF_ANGLE;
            double thick = FLAME_ARC_THICK * rnd.nextDouble() * (0.30D + 0.70D * Math.cos(phi));
            double rad = (FLAME_ARC_RADIUS - thick) * radius;
            double lx = rad * Math.sin(phi);
            double lz = FLAME_ARC_CENTER_Z * radius + rad * Math.cos(phi);
            double ly = FLAME_LOCAL_Y + (rnd.nextDouble() - 0.5D) * 0.22D;
            Vec3 p = origin
                    .add(right.scale(lx))
                    .add(up.scale(ly))
                    .add(forward.scale(lz));
            this.level().addParticle(ParticleTypes.FLAME, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
            if (rnd.nextFloat() < 0.25F) {
                this.level().addParticle(ParticleTypes.SMALL_FLAME, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
            }
            if (rnd.nextFloat() < 0.06F) {
                this.level().addParticle(ParticleTypes.LAVA, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
            }
        }
        int wake = Mth.clamp((int) (radius * 0.8F), 1, 5);
        for (int i = 0; i < wake; i++) {
            double back = radius * (0.20D + rnd.nextDouble() * 0.60D);
            double side = (rnd.nextDouble() - 0.5D) * radius * 0.9D;
            double lift = (rnd.nextDouble() - 0.2D) * 0.5D;
            Vec3 p = origin
                    .add(forward.scale(-back))
                    .add(right.scale(side))
                    .add(up.scale(lift));
            this.level().addParticle(ParticleTypes.FLAME, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}
