package cn.blockforge.generated.goetysupreme.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.entity.SerpenperthMeteorEntity;

public class SerpenperthMeteorEntity extends Entity {

    private static final EntityDataAccessor<Integer> DATA_LIFE =
            SynchedEntityData.defineId(SerpenperthMeteorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_LIFE_BOMB =
            SynchedEntityData.defineId(SerpenperthMeteorEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_NOW_X =
            SynchedEntityData.defineId(SerpenperthMeteorEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_NOW_Y =
            SynchedEntityData.defineId(SerpenperthMeteorEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_NOW_Z =
            SynchedEntityData.defineId(SerpenperthMeteorEntity.class, EntityDataSerializers.FLOAT);

    private int spellLevelDamage = 6;

    private float damageOverride = -1.0F;

    private double radiusBonus;
    private int order;
    private Vec3 startPos = Vec3.ZERO;

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUuid;

    public SerpenperthMeteorEntity(EntityType<? extends SerpenperthMeteorEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public void setSpellLevelDamage(int value) {
        this.spellLevelDamage = value;
    }

    public void setDamageOverride(float damage) {
        this.damageOverride = damage;
    }

    public void setRadiusBonus(double radiusBonus) {
        this.radiusBonus = Math.max(0.0D, radiusBonus);
    }

    public int getSpellLevelDamage() {
        return this.spellLevelDamage;
    }

    public void setOrder(int value) {
        this.order = value;
    }

    private boolean noBlockFire;

    public void setNoBlockFire(boolean noBlockFire) {
        this.noBlockFire = noBlockFire;
    }

    public static BlockPos findSpawnPositionNear(ServerLevel level, BlockPos base, int n) {
        BlockPos fallback = base.above(3);
        int spread = Math.max(1, n);
        for (int i = 0; i < 128; ++i) {
            int dx = level.random.nextInt(spread * 2 + 1) - spread;
            int dy = level.random.nextInt(spread);
            int dz = level.random.nextInt(spread * 2 + 1) - spread;
            BlockPos candidate = new BlockPos(base.getX() + dx, base.getY() + dy, base.getZ() + dz).above(3);
            if (level.getBlockState(candidate).isAir()) {
                return candidate;
            }
        }
        return fallback;
    }

    public void setStartPos(Vec3 pos) {
        this.startPos = pos;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUuid != null && level() instanceof ServerLevel server) {
            Entity entity = server.getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity living) {
                this.owner = living;
            }
        }
        return this.owner;
    }

    public int getLife() {
        return entityData.get(DATA_LIFE);
    }

    private void setLife(int value) {
        entityData.set(DATA_LIFE, value);
    }

    public int getLifeBomb() {
        return entityData.get(DATA_LIFE_BOMB);
    }

    private void setLifeBomb(int value) {
        entityData.set(DATA_LIFE_BOMB, value);
    }

    @Override
    public void defineSynchedData() {
        entityData.define(DATA_LIFE, 0);
        entityData.define(DATA_LIFE_BOMB, 0);
        entityData.define(DATA_NOW_X, 0.0F);
        entityData.define(DATA_NOW_Y, 0.0F);
        entityData.define(DATA_NOW_Z, 0.0F);
    }

    public static final class TrailPoint {
        public final Vec3 prevPosition;
        public final Vec3 position;
        public final int createdAt;

        public TrailPoint(Vec3 prevPosition, Vec3 position, int tick) {
            this.prevPosition = prevPosition;
            this.position = position;
            this.createdAt = tick;
        }

        public Vec3 getPosition(float partialTick) {
            return this.prevPosition.lerp(this.position, partialTick);
        }
    }

    private final java.util.List<TrailPoint> trailPoints = new java.util.ArrayList<>();

    private Vec3 lastTrailPos;

    public java.util.List<TrailPoint> getTrailPoints() {
        return this.trailPoints;
    }

    public int trailMaxLife() {
        return 12;
    }

    public int trailMaxPoints() {
        return 12;
    }

    public int beamLightI() {
        return 0x644647;
    }

    public int beamLightII() {
        return 0x96716b;
    }

    public float getNowX() {
        return this.entityData.get(DATA_NOW_X);
    }

    public float getNowY() {
        return this.entityData.get(DATA_NOW_Y);
    }

    public float getNowZ() {
        return this.entityData.get(DATA_NOW_Z);
    }

    private void setNowPos(Vec3 pos) {
        this.entityData.set(DATA_NOW_X, (float) pos.x);
        this.entityData.set(DATA_NOW_Y, (float) pos.y);
        this.entityData.set(DATA_NOW_Z, (float) pos.z);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (getLifeBomb() <= 0 && getLife() >= 5) {
                Vec3 current = new Vec3(getNowX(), getNowY(), getNowZ());
                Vec3 prev = this.lastTrailPos != null ? this.lastTrailPos : current;
                this.trailPoints.add(new TrailPoint(prev, current, getLife()));
                this.trailPoints.removeIf(point -> getLife() - point.createdAt > trailMaxLife());
                while (this.trailPoints.size() > trailMaxPoints()) {
                    this.trailPoints.remove(0);
                }
                this.lastTrailPos = current;
            }
            return;
        }
        int life = getLife() + 1;
        setLife(life);
        float startLife = (life - 5) * 10.0F;
        if (startLife < 0.0F) {
            return;
        }
        Vec3 target = position();
        double distance = target.distanceTo(startPos);
        float startCount = (float) (distance * (10.0D + 2.0D * order));
        if (startCount <= 0.0F) {
            startCount = 1.0F;
        }
        float progress = Mth.clamp(startLife / startCount, 0.0F, 1.0F);
        Vec3 now = target.scale(progress).add(startPos.scale(1.0F - progress));
        setNowPos(now);
        if (startLife >= startCount) {
            setLifeBomb(getLifeBomb() + 1);
        }
        int bomb = getLifeBomb();
        if (bomb == 1) {
            specialExplode();
        }
        if (bomb == 8) {
            spawnDebris();
        }
        if (bomb == 10) {
            spawnAftermath();
        }
        if (bomb >= 60) {
            discard();
        }
    }

    private void spawnTrail(Vec3 now) {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(ParticleTypes.FLAME, now.x, now.y, now.z, 3, 0.25D, 0.25D, 0.25D, 0.01D);
        server.sendParticles(ParticleTypes.LARGE_SMOKE, now.x, now.y, now.z, 2, 0.3D, 0.3D, 0.3D, 0.005D);
    }

    private void specialExplode() {
        float radius = 3.0F + (float) radiusBonus;
        DamageSource source = damageSources().explosion(this, getOwner());
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE,
                getSoundSource(), 15.0F, (float) (0.8D + Math.random() * 0.4D));
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.FIRECHARGE_USE,
                getSoundSource(), 15.0F, (float) (0.8D + Math.random() * 0.4D));
        for (Entity hurt : level().getEntities(this, getBoundingBox().inflate(radius + 2.0F))) {
            if (hurt.distanceTo(this) > radius) {
                continue;
            }
            float strength = (12.0F - hurt.distanceTo(this)) / 6.0F;
            if (hurt instanceof LivingEntity living && getOwner() instanceof Mob mob && !mob.canAttack(living)) {
                continue;
            }
            if (getOwner() != null && getOwner().isAlliedTo(hurt)) {
                continue;
            }
            // 陨石是"召唤物"：主人的仆从（含其它模组的队友召唤物）不应被自己的陨石炸到。
            if (hurt instanceof LivingEntity living && getOwner() != null) {
                if (living == getOwner()) {
                    continue;
                }
                if (living instanceof com.Polarice3.Goety.api.entities.IOwned owned) {
                    LivingEntity trueOwner = owned.getTrueOwner();
                    if (trueOwner != null && (trueOwner == getOwner() || trueOwner.isAlliedTo(getOwner())
                            || getOwner().isAlliedTo(trueOwner))) {
                        continue;
                    }
                }
            }
            if (!(hurt instanceof net.minecraft.world.entity.item.ItemEntity)) {
                hurt.setDeltaMovement(0.0D, strength / 5.0D, 0.0D);
            }
            if (hurt instanceof LivingEntity living) {
                living.invulnerableTime = 0;
                boolean hit = living.hurt(source, this.damageOverride >= 0.0F ? this.damageOverride
                        : rollDice(6 + (spellLevelDamage - 5), 6));
                if (hit) {
                    living.setRemainingFireTicks(6 + spellLevelDamage * 3);
                }
                double knock = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double factor = Math.max(0.2D, 1.2D - knock);
                living.setDeltaMovement(living.getDeltaMovement().add(
                        -(getX() - living.getX()) * factor / 3.0D,
                        -(getY() - living.getY()) * factor / 4.0D,
                        -(getZ() - living.getZ()) * factor / 3.0D));
            }
        }
        setDeltaMovement(Vec3.ZERO);
        level().explode(this, getX(), getY(), getZ(),
                Math.min(spellLevelDamage / 3.0F, 1.73F), !this.noBlockFire, Level.ExplosionInteraction.NONE);
        if (level() instanceof ServerLevel server) {
            this.firstExplodeParticles(server);
        }
    }

    private void firstExplodeParticles(ServerLevel server) {
        for (int i = 0; i < 10; ++i) {
            JerotesParticleFx.sendBallParticles(server, this, ParticleTypes.FLAME, 3.0F, 0.4F);
            for (int j = 0; j < 6; ++j) {
                float angle = (float) (j / 6.0D * Math.PI * 2.0D);
                float radius = i / 8.0F + this.random.nextFloat() * 2.0F;
                double px = getRandomX((this.random.nextDouble() - 0.5D) * 3.0D)
                        + (double) radius * Math.cos(angle);
                double pz = getRandomZ((this.random.nextDouble() - 0.5D) * 3.0D)
                        + (double) radius * Math.sin(angle);
                server.sendParticles(ParticleTypes.FLASH, px, getY() + 0.5D, pz,
                        0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        server.sendParticles(AbstractJerotesServant.jerotesParticle(
                        "serpenperth_meteor_shockwave", ParticleTypes.EXPLOSION),
                getX(), getY() + 0.1D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void spawnDebris() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        for (int i = 0; i < 16; ++i) {
            double angle = i / 16.0D * Math.PI * 2.0D;
            server.sendParticles(ParticleTypes.LAVA,
                    getX() + 2.0D * Math.cos(angle), getY() + 2.5D, getZ() + 2.0D * Math.sin(angle),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        BlockPos center = blockPosition();
        int reach = 2;
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-(reach * 2 + 1), -reach, -(reach * 2 + 1)),
                center.offset(reach * 2 + 1, reach, reach * 2 + 1))) {
            double distance = center.distSqr(pos);
            if (distance > (double) reach * reach) {
                continue;
            }
            BlockState state = server.getBlockState(pos);
            if (state.isAir() || state.hasBlockEntity() || !state.isSolidRender(server, pos)) {
                continue;
            }
            FlungBlockEntity debris = new FlungBlockEntity(server,
                    pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state,
                    0.4F + random.nextFloat() * 0.6F);
            server.addFreshEntity(debris);
        }
    }

    private void spawnAftermath() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        for (int ring = 0; ring < 2; ++ring) {
            for (int i = 0; i < 18; ++i) {
                double angle = i / 18.0D * Math.PI * 2.0D;
                server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        getX() + (random.nextDouble() - 0.5D) * 3.0D + 2.0D * Math.cos(angle),
                        getY() + 0.5D,
                        getZ() + (random.nextDouble() - 0.5D) * 3.0D + 2.0D * Math.sin(angle),
                        1, 0.0D, ring * 0.2D * random.nextDouble(), 0.0D, 0.3D);
            }
        }
    }

    private float rollDice(int count, int sides) {
        if (count <= 0 || sides <= 0) {
            return 0.0F;
        }
        int sum = 0;
        for (int i = 0; i < count; ++i) {
            sum += random.nextInt(sides) + 1;
        }
        return sum;
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
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Life", getLife());
        tag.putInt("LifeBomb", getLifeBomb());
        tag.putInt("Order", order);
        tag.putInt("SpellLevelDamage", spellLevelDamage);
        tag.putDouble("RadiusBonus", radiusBonus);
        tag.putDouble("StartX", startPos.x);
        tag.putDouble("StartY", startPos.y);
        tag.putDouble("StartZ", startPos.z);
        if (ownerUuid != null) {
            tag.putUUID("Owner", ownerUuid);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        setLife(tag.getInt("Life"));
        setLifeBomb(tag.getInt("LifeBomb"));
        this.order = tag.getInt("Order");
        this.spellLevelDamage = tag.getInt("SpellLevelDamage");
        this.radiusBonus = tag.getDouble("RadiusBonus");
        this.startPos = new Vec3(tag.getDouble("StartX"), tag.getDouble("StartY"), tag.getDouble("StartZ"));
        if (tag.hasUUID("Owner")) {
            this.ownerUuid = tag.getUUID("Owner");
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
