package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.util.ServantArmorTrims;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.api.SupremeChargeAuraHolder;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;
import cn.blockforge.generated.goetysupreme.data.WitheringMinionLedger;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public abstract class AbstractWitherServantBase extends Summoned
        implements OwnableEntity, RangedAttackMob, PowerableMob, SupremeChargeAuraHolder {

    protected AbstractWitherServantBase(EntityType<? extends Summoned> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.setHostile(false);
        this.setNoGravity(true);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }
    protected static final double TARGET_RANGE = 48.0D;

    protected static final double TARGET_RANGE_SQR = TARGET_RANGE * TARGET_RANGE;

    protected static final int TARGET_SIGHT_GRACE_TICKS = 20;

    protected static final double OWNER_FOLLOW_STANDOFF = 4.0D;

    protected static final double OWNER_FOLLOW_MIN_OFFSET = -2.0D;

    protected static final double OWNER_FOLLOW_MAX_OFFSET = 4.0D;

    protected static final double OWNER_HOVER_MAX_HEIGHT = 6.0D;

    protected static final double GIANT_OWNER_FOLLOW_MIN_OFFSET = -16.0D;

    protected static final double GIANT_OWNER_FOLLOW_MAX_OFFSET = 16.0D;

    protected static final double GIANT_FOLLOW_RANGE = 96.0D;

    protected static final double GIANT_ORBIT_ANGULAR_SPEED = 0.018D;

    protected static final double GIANT_ORBIT_EXTRA_RADIUS = 16.0D;

    protected static final double GIANT_ORBIT_RESYNC_RADIUS = 12.0D;

    protected static final double OWNER_FOLLOW_START_DISTANCE = 10.0D;

    protected static final double OWNER_FOLLOW_STOP_DISTANCE = 5.0D;

    protected static final int OWNER_FOLLOW_RECALC_TICKS = 10;

    protected static final double OWNER_FOLLOW_SPEED = 0.75D;

    protected static final double GIANT_FOLLOW_SPEED = 1.0D;

    protected static final float OWNER_HOVER_FACE_YAW = 6.0F;

    protected static final float OWNER_HOVER_FACE_PITCH = 9.0F;

    protected static final double PEACEFUL_MOVE_SPEED = 0.45D;

    protected int targetSightTicks;

    protected static final double STATION_RANGE_SQR = 576.0D;

    protected static final double STATION_ABANDON_SQR = 4096.0D;

    protected static final float FACE_SNAP_YAW = 180.0F;

    protected static final float FACE_SNAP_PITCH = 40.0F;

    protected static final float COBBLESTONE_HARDNESS = 2.0F;

    protected static final double SKULL_INERTIA = 0.95D;

    protected static final double DANGEROUS_SKULL_INERTIA = 0.73D;

    protected static final int MAIN_HEAD_FIRE_MIN = 10;

    protected static final int MAIN_HEAD_FIRE_SPAN = 10;

    protected static final int[] BURIED_OFFSETS = {1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, -1, 0, 1, 0, 0, -1, 0};

    protected static final int RAM_TOTAL = 200;

    static final double RAM_DIVE_SINK = 0.1D;

    static final double RAM_DIVE_SPEED = 1.35D;

    protected static final float RAM_DAMAGE_MULTIPLIER = 3.0F;

    protected static final int RAM_TRAIL_SEGMENTS = 4;

    protected static final int RAM_TRAIL_ASH_PER_SEGMENT = 16;

    protected static final double RAM_TRAIL_SPREAD = 0.75D;

    protected static final int RAM_TRAIL_SMOKE = 8;

    protected static final int MINION_FIXED_COUNT = 4;

    protected static final int HALF_HEALTH_RESISTANCE_REFRESH = 20;

    public static final double ANNIHILATION_KNOCKBACK_RESISTANCE = 1.0D;

    protected static final int HALF_HEALTH_RESISTANCE_DURATION = 100;

    @Nullable
    protected MobEffectInstance halfHealthResistance;

    protected static final List<EntityDataAccessor<Integer>> HEAD_TARGETS = List.of(
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.INT),
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.INT),
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.INT));

    protected static final EntityDataAccessor<Integer> INVULNERABLE_TICKS =
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> RAGING =
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> CHARGING =
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> STAR_EMBEDDED =
            SynchedEntityData.defineId(AbstractWitherServantBase.class, EntityDataSerializers.BOOLEAN);

    protected final float[] headYaw = new float[2];

    protected final float[] headPitch = new float[2];

    protected final int[] sideAttackCooldown = {20, 40};

    protected final int[] sideRetargetCooldown = {0, 0};

    protected int mainLockedTargetId;

    protected int mainLockTicks;

    protected boolean sideAssistUnlocked;

    protected double hoverX;

    protected double hoverY;

    protected double hoverZ;

    protected int travelTicks;

    protected boolean isHoveringAtStation() {
        return this.travelTicks == 0;
    }

    protected boolean ownerFollowing;

    protected int ownerFollowRecalc;

    @Nullable
    protected Vec3 ownerFollowTarget;

    protected double orbitAngle;

    protected boolean orbitInitialized;

    protected boolean stationValid;

    protected int stationHoldTicks;

    protected boolean armorSynced;

    protected int mainHeadCooldown;

    protected int tailCooldown = 120;

    protected int roarCooldown;

    protected int ramTicks;

    protected int ramCooldown = 100;

    protected Vec3 chargeDir = Vec3.ZERO;

    protected final Set<UUID> ramHits = new HashSet<>();

    protected boolean forceRamNext;

    protected boolean spawnSequenceActive;

    protected boolean spawnExplosionDone;

    protected static final int SPAWN_INVULNERABLE_TICKS = 220;

    public static AttributeSupplier.Builder createServantAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.FOLLOW_RANGE, TARGET_RANGE)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D)
                .add(Attributes.FLYING_SPEED, 0.6D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType,
            @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
        if (!level().isClientSide && !spawnSequenceActive && !spawnExplosionDone) {
            beginSpawnSequence();
        }
        return data;
    }

    protected void beginSpawnSequence() {
        spawnSequenceActive = true;
        spawnExplosionDone = false;
        setInvulnerableTicks(SPAWN_INVULNERABLE_TICKS);
        setHealth(getMaxHealth() * 0.5F);
        setDeltaMovement(Vec3.ZERO);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(1.4D), getZ(),
                    24, 0.9D, 1.1D, 0.9D, 0.02D);
            serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY(0.9D), getZ(),
                    16, 1.0D, 0.8D, 1.0D, 0.02D);
        }
    }

    protected void updateSpawnSequence() {
        if (!spawnSequenceActive || level().isClientSide) {
            return;
        }
        if (getInvulnerableTicks() > 0) {
            setHealth(Math.min(getMaxHealth(), getHealth() + getMaxHealth() / SPAWN_INVULNERABLE_TICKS));
            return;
        }
        if (!spawnExplosionDone) {
            spawnExplosionDone = true;
            spawnSequenceActive = false;
            setHealth(getMaxHealth());
            detonateSpawnBlast();
        }
    }

    protected void detonateSpawnBlast() {
        double radius = 7.0D;
        level().globalLevelEvent(1023, blockPosition(), 0);
        playSound(SoundEvents.GENERIC_EXPLODE, 6.0F, 0.5F);
        playSound(SoundEvents.WITHER_SPAWN, 4.0F, 1.0F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(1.4D), getZ(),
                    3, 1.2D, 1.0D, 1.2D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY(1.6D), getZ(),
                    80, 2.6D, 2.2D, 2.6D, 0.05D);
            serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(1.6D), getZ(),
                    48, 2.2D, 1.8D, 2.2D, 0.03D);
        }
        AABB area = getBoundingBox().inflate(radius, radius, radius);
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area,
                entity -> entity != this && entity.isAlive() && !isAlliedTo(entity))) {
            double distance = Math.sqrt(victim.distanceToSqr(this));
            if (distance > radius) {
                continue;
            }
            double falloff = 1.0D - distance / radius;
            float damage = (float) (falloff * 40.0D);
            if (damage < 0.5F) {
                continue;
            }
            victim.invulnerableTime = 0;
            victim.hurt(pierceUndeadImmunity(damageSources().explosion(this, this), victim), damage);
            Vec3 push = victim.position().subtract(position());
            if (push.lengthSqr() > 1.0E-6D) {
                push = push.normalize().scale(0.6D * falloff);
                victim.push(push.x, 0.35D * falloff, push.z);
            }
        }
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(false);
        return navigation;
    }

    @Override
    public boolean isPowered() {
        return getHealth() <= getMaxHealth() * 0.5F;
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return getOwnerId();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public int getInvulnerableTicks() {
        return entityData.get(INVULNERABLE_TICKS);
    }

    public void setInvulnerableTicks(int ticks) {
        entityData.set(INVULNERABLE_TICKS, Math.max(0, ticks));
    }

    public boolean isRaging() {
        return Boolean.TRUE.equals(entityData.get(RAGING));
    }

    public boolean isCharging() {
        return Boolean.TRUE.equals(entityData.get(CHARGING));
    }

    protected SupremeChargeAura supremeChargeAura;

    @Override
    public SupremeChargeAura supremeChargeAura() {
        if (this.supremeChargeAura == null) {
            this.supremeChargeAura = SupremeChargeAura.of(SupremeChargeAura.Theme.WITHER);
        }
        return this.supremeChargeAura;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            this.supremeChargeAura().tick(this, this.isCharging());
        }
    }

    public int getAlternativeTarget(int head) {
        return head >= 0 && head < 3 ? entityData.get(HEAD_TARGETS.get(head)) : 0;
    }

    public void setAlternativeTarget(int head, int targetId) {
        if (head >= 0 && head < 3) entityData.set(HEAD_TARGETS.get(head), targetId);
    }

    public float getHeadYRot(int head) {
        return head >= 0 && head < 2 ? headYaw[head] : 0.0F;
    }

    public float getHeadXRot(int head) {
        return head >= 0 && head < 2 ? headPitch[head] : 0.0F;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> true);
        this.targetSelector.removeAllGoals(goal -> goal instanceof SummonTargetGoal);
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 10, true, false,
                candidate -> isMainHeadCandidate(candidate)) {

            @Override
            public AABB getTargetSearchArea(double range) {
                return AbstractWitherServantBase.this.getBoundingBox()
                        .inflate(range, Math.max(24.0D, range * 0.75D), range);
            }
        }.setUnseenMemoryTicks(TARGET_SIGHT_GRACE_TICKS));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    protected void spawnAmbientParticles() {
        float jitter = 0.3F * getScale();
        for (int head = 0; head < 3; ++head) {
            level().addParticle(ParticleTypes.SMOKE,
                    getHeadX(head) + random.nextGaussian() * (double) jitter,
                    getHeadY(head) + random.nextGaussian() * (double) jitter,
                    getHeadZ(head) + random.nextGaussian() * (double) jitter,
                    0.0D, 0.0D, 0.0D);
        }
        if (isStarEmbedded() && random.nextInt(2) == 0) {
            level().addParticle(ParticleTypes.FIREWORK,
                    getRandomX(0.7D), getRandomY(), getRandomZ(0.7D),
                    random.nextGaussian() * 0.02D, 0.1D, random.nextGaussian() * 0.02D);
        }
    }

    protected void updateInvulnerability() {
        if (getInvulnerableTicks() > 0) {
            setInvulnerableTicks(getInvulnerableTicks() - 1);
        }
    }

    protected void updateHeadAngles() {
        for (int head = 0; head < 2; ++head) {
            int targetSlot = head + 1;
            Entity watched = level().getEntity(getAlternativeTarget(targetSlot));
            if (watched instanceof LivingEntity living && living.isAlive() && !isAlliedTo(living)) {
                Vec3 dir = living.getEyePosition().subtract(headPosition(targetSlot));
                double horizontal = dir.horizontalDistance();
                float wantedYaw = Mth.wrapDegrees((float) Math.toDegrees(Mth.atan2(dir.z, dir.x)) - 90.0F);
                float wantedPitch = (float) -Math.toDegrees(Mth.atan2(dir.y, horizontal));
                headYaw[head] = rotlerp(headYaw[head], wantedYaw, 10.0F);
                headPitch[head] = rotlerp(headPitch[head], wantedPitch, 40.0F);
            } else {
                headYaw[head] = rotlerp(headYaw[head], this.yBodyRot, 10.0F);
            }
        }
    }

    protected static float rotlerp(float source, float target, float maxDelta) {
        float delta = Mth.wrapDegrees(target - source);
        return source + Mth.clamp(delta, -maxDelta, maxDelta);
    }

    protected void faceTowards(Vec3 point, float maxYawPerTick, float maxPitchPerTick) {
        Vec3 dir = point.subtract(position());
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        float wantedYaw = Mth.wrapDegrees((float) Mth.atan2(-dir.x, dir.z) * Mth.RAD_TO_DEG);
        setYRot(rotlerp(getYRot(), wantedYaw, maxYawPerTick));
        setYBodyRot(getYRot()); // 头颅挂点与身体渲染基准一起到位，避免发射点拖在后面
        getLookControl().setLookAt(point.x, point.y, point.z, maxYawPerTick, maxPitchPerTick);
    }

    protected void faceDirectionOfTravel(float maxDegreesPerTick) {
        Vec3 dv = getDeltaMovement();
        if (dv.horizontalDistanceSqr() > 1.0E-4D) {
            float wantedYaw = Mth.wrapDegrees((float) Mth.atan2(-dv.x, dv.z) * Mth.RAD_TO_DEG);
            setYRot(rotlerp(getYRot(), wantedYaw, maxDegreesPerTick));
        }
        setXRot(rotlerp(getXRot(), 0.0F, maxDegreesPerTick));
    }

    protected Vec3 headPosition(int head) {
        return new Vec3(getHeadX(head), getHeadY(head), getHeadZ(head));
    }

    protected boolean isMainHeadCandidate(LivingEntity candidate) {
        return candidate instanceof Enemy && canAttack(candidate);
    }

    protected void holdMainTarget() {
        if (getTarget() != null || getInvulnerableTicks() > 0) {
            return; // 有目标（或生成保护期）都不插手，还击/指哪打哪逻辑照常管
        }
        AABB scan = getBoundingBox().inflate(TARGET_RANGE, Math.max(24.0D, TARGET_RANGE * 0.75D), TARGET_RANGE);
        LivingEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : level().getEntitiesOfClass(LivingEntity.class, scan,
                candidate -> isMainHeadCandidate(candidate) && guardAllows(candidate)
                        && hasLineOfSight(candidate))) {
            double distance = distanceToSqr(candidate);
            if (distance < nearestDistance) {
                nearest = candidate;
                nearestDistance = distance;
            }
        }
        if (nearest != null) {
            setTarget(nearest);
            setAlternativeTarget(0, nearest.getId()); // 与参考实现同款：主头监视目标=攻击目标
        }
    }

    protected boolean guardAllows(LivingEntity candidate) {
        if (!isGuardingArea() || boundPos == null) {
            return true;
        }
        double limit = (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D;
        return candidate.distanceToSqr(Vec3.atCenterOf(boundPos)) <= limit;
    }

    protected void landAt(double x, double z) {
        moveToward(x, groundYAt(x, z), z, PEACEFUL_MOVE_SPEED);
        faceDirectionOfTravel(8.0F);
    }

    protected double groundYAt(double x, double z) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(x, getY() + 1.0D, z);
        int floor = level().getMinBuildHeight();
        while (cursor.getY() > floor) {
            cursor.setY(cursor.getY() - 1);
            if (level().getBlockState(cursor).blocksMotion()) {
                return cursor.getY() + 1.0D;
            }
        }
        return getY();
    }

    protected boolean needsNewStation(LivingEntity target) {
        if (!stationValid) {
            return true;
        }
        Vec3 station = new Vec3(hoverX, hoverY, hoverZ);
        if (target.position().subtract(station).lengthSqr() > STATION_RANGE_SQR) {
            return true; // 目标在射程之外：立即换点
        }
        return distanceToSqr(station) > STATION_ABANDON_SQR;
    }

    @Nullable
    protected LivingEntity headTarget(int head) {
        Entity watched = level().getEntity(getAlternativeTarget(head));
        return watched instanceof LivingEntity living && isValidEnemy(living, TARGET_RANGE_SQR) ? living : null;
    }

    protected void onSkullLaunched() {
    }

    protected boolean isTargetBuried(LivingEntity target) {
        BlockPos middle = BlockPos.containing(target.getX(), target.getY(target.getBbHeight() * 0.5D), target.getZ());
        int weak = 0;
        for (int index = 0; index < BURIED_OFFSETS.length; index += 3) {
            BlockPos probe = middle.offset(BURIED_OFFSETS[index], BURIED_OFFSETS[index + 1], BURIED_OFFSETS[index + 2]);
            BlockState state = level().getBlockState(probe);
            if (state.isAir() || !state.blocksMotion()) {
                continue;
            }
            if (state.getDestroySpeed(level(), probe) <= COBBLESTONE_HARDNESS) {
                ++weak;
            }
        }
        return weak >= 4;
    }

    public void enterRagingForm() {
        if (level().isClientSide || isRaging()) {
            return;
        }
        entityData.set(RAGING, true);
        applyRagingAttributes();
        playSound(SoundEvents.WITHER_AMBIENT, 4.0F, 0.65F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT, getX(), getY(1.5D), getZ(),
                    60, 1.4D, 1.6D, 1.4D, 0.55D);
            serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(2.2D), getZ(),
                    24, 0.9D, 0.7D, 0.9D, 0.05D);
        }
        ramTicks = 0;
        ramCooldown = 60;
    }

    protected void applyRagingAttributes() {
        applyKnockbackResistance();
    }

    protected void applyKnockbackResistance() {
        var knockback = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (knockback == null) {
            return;
        }
        double wanted;
        if (isStarEmbedded()) {
            wanted = ANNIHILATION_KNOCKBACK_RESISTANCE;
        } else if (isRaging()) {
            wanted = WitherServantConfig.RAGING_KNOCKBACK_RESISTANCE.get();
        } else {
            wanted = 0.0D;
        }
        if (Math.abs(knockback.getBaseValue() - wanted) > 1.0E-4D) {
            knockback.setBaseValue(wanted);
        }
    }

    protected void syncStarArmor() {
        if (armorSynced) {
            return;
        }
        if (tickCount < 20) {
            return;
        }
        armorSynced = true;
        applyStarArmor();
    }

    protected void applyStarArmor() {
        boolean starred = isStarEmbedded();
        setBaseIfDifferent(Attributes.ARMOR, starred ? WitherServantConfig.RAGING_ARMOR.get() : 0.0D);
        setBaseIfDifferent(Attributes.ARMOR_TOUGHNESS, starred ? WitherServantConfig.RAGING_TOUGHNESS.get() : 0.0D);
        applyKnockbackResistance();
    }

    protected void setBaseIfDifferent(Attribute attribute, double wanted) {
        var instance = getAttribute(attribute);
        if (instance != null && Math.abs(instance.getBaseValue() - wanted) > 1.0E-4D) {
            instance.setBaseValue(wanted);
        }
    }

    protected void updateRagingRegen() {
        if (getInvulnerableTicks() <= 0 && tickCount % 20 == 0 && getHealth() < getMaxHealth()) {
            heal((float) (double) WitherServantConfig.RAGING_REGEN_PER_SECOND.get());
        }
    }

    protected void updateHalfHealthResistance() {
        MobEffectInstance current = getEffect(MobEffects.DAMAGE_RESISTANCE);
        if (current != halfHealthResistance) {
            halfHealthResistance = null;
        }
        if (isStarEmbedded() && isPowered()) {
            if (tickCount % HALF_HEALTH_RESISTANCE_REFRESH == 0
                    && (current == null || current == halfHealthResistance)) {
                if (super.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                        HALF_HEALTH_RESISTANCE_DURATION, 0, false, false), this)) {
                    halfHealthResistance = getEffect(MobEffects.DAMAGE_RESISTANCE);
                }
            }
        } else if (halfHealthResistance != null) {
            if (removeEffect(MobEffects.DAMAGE_RESISTANCE)) {
                halfHealthResistance = null;
            }
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance instance, @Nullable Entity source) {
        if (instance.getEffect() == MobEffects.DAMAGE_RESISTANCE
                && halfHealthResistance != null
                && getEffect(MobEffects.DAMAGE_RESISTANCE) == halfHealthResistance) {
            if (!canBeAffected(instance)) {
                return false; // 外部拒绝施加时，不动已有被动。
            }
            removeEffect(MobEffects.DAMAGE_RESISTANCE);
            halfHealthResistance = null;
        }
        return super.addEffect(instance, source);
    }

    public boolean unlockRagingForm() {
        boolean already = isStarEmbedded();
        markStarEmbedded();
        return !already;
    }

    public boolean isStarEmbedded() {
        return Boolean.TRUE.equals(entityData.get(STAR_EMBEDDED));
    }

    public void markStarEmbedded() {
        if (!level().isClientSide) {
            entityData.set(STAR_EMBEDDED, true);
            armorSynced = true;
            applyStarArmor(); // 镶星当场获得护甲值/韧性（狂暴之星的专属加成）
            beginSpawnSequence();
        }
    }

    protected void summonWitheringMinions() {
        if (level().isClientSide || !WitherServantConfig.MINIONS_ENABLED.get()
                || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int count = Math.min(MINION_FIXED_COUNT,
                WitheringMinionControl.LIMIT - WitheringMinionControl.count(this, serverLevel));
        if (count <= 0) return;
        for (int index = 0; index < count; ++index) {
            WitherSkeletonServant minion = new WitherSkeletonServant(ModEntityType.WITHER_SKELETON_SERVANT.get(), serverLevel);
            double angle = index * Mth.TWO_PI / count + random.nextDouble() * 0.8D;
            double spawnX = getX() + Mth.cos((float) angle) * 2.2D;
            double spawnZ = getZ() + Mth.sin((float) angle) * 2.2D;
            minion.moveTo(spawnX, MinionGroundPlacement.groundFeetY(serverLevel, spawnX, spawnZ,
                    getY(), MinionGroundPlacement.SKELETON_CLEARANCE), spawnZ, 0.0F, 0.0F);
            minion.setTrueOwner(this); // 以凋灵为主人
            minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
            minion.setPersistenceRequired();
            minion.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(minion.blockPosition()),
                    MobSpawnType.TRIGGERED, null, null);
            equipMinion(minion, index);
            WitheringMinionControl.initialize(minion, this);
            if (minion.isRemoved()) continue;
            if (!serverLevel.addFreshEntity(minion)) {
                WitheringMinionLedger.get(serverLevel).release(minion.getUUID());
                continue;
            }
            GoetySummonFx.play(serverLevel, minion);
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, minion.getX(), minion.getY(1.0D), minion.getZ(),
                    12, 0.5D, 0.8D, 0.5D, 0.05D);
        }
        playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.7F);
    }

    protected static void equipMinion(WitherSkeletonServant minion, int index) {
        minion.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
        minion.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
        minion.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
        minion.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));
        minion.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(index % 2 == 0 ? Items.NETHERITE_SWORD : Items.NETHERITE_AXE));
        ServantArmorTrims.applyWitherMinionTrim(minion);
    }

    protected void setCharging(boolean charging) {
        entityData.set(CHARGING, charging);
    }

    protected void onRamHit(LivingEntity victim) {
        if (victim.hurt(pierceUndeadImmunity(damageSources().mobAttack(this), victim),
                (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * RAM_DAMAGE_MULTIPLIER)) {
            float ramYaw = (float) Mth.atan2(victim.getDeltaMovement().z, victim.getDeltaMovement().x)
                    * (180.0F / (float) Math.PI) - 90.0F;
            victim.setYRot(ramYaw);
            victim.setYBodyRot(ramYaw);
            victim.setYHeadRot(ramYaw);
            victim.setDeltaMovement(victim.getDeltaMovement()
                    .add(chargeDir.x * 1.1D, 0.62D, chargeDir.z * 1.1D));
            victim.hurtMarked = true;
        }
    }

    protected boolean shouldRamDestroyBlocks() {
        return true;
    }

    protected boolean isFacingAhead(BlockPos pos) {
        Vec3 to = Vec3.atCenterOf(pos).subtract(position());
        return to.lengthSqr() < 1.0E-6D || to.normalize().dot(chargeDir) > 0.5D;
    }

    protected void faceDir(Vec3 dir) {
        float yaw = Mth.wrapDegrees((float) Math.toDegrees(Mth.atan2(-dir.x, dir.z)));
        double horizontal = Math.sqrt(dir.x * dir.x + dir.z * dir.z);
        float pitch = (float) -Math.toDegrees(Mth.atan2(dir.y, horizontal));
        setYRot(yaw);
        setXRot(pitch);
        setYHeadRot(yaw);
        setYBodyRot(yaw);
    }

    protected static DamageSource pierceUndeadImmunity(DamageSource source, Entity victim) {
        if (!(victim instanceof WitherBoss)) {
            return source;
        }
        return new DamageSource(source.typeHolder(), source.getDirectEntity(), null);
    }

    protected double getHeadX(int head) {
        if (head <= 0) {
            return this.getX();
        }
        float f = (this.yBodyRot + 180.0F * (head - 1)) * Mth.DEG_TO_RAD;
        return this.getX() + Mth.cos(f) * 1.3D;
    }

    protected double getHeadY(int head) {
        return head <= 0 ? this.getY() + 3.0D : this.getY() + 2.2D;
    }

    protected double getHeadZ(int head) {
        if (head <= 0) {
            return this.getZ();
        }
        float f = (this.yBodyRot + 180.0F * (head - 1)) * Mth.DEG_TO_RAD;
        return this.getZ() + Mth.sin(f) * 1.3D;
    }

    protected void moveToward(double x, double y, double z, double maxSpeed) {
        Vec3 offset = new Vec3(x - getX(), y - getY(), z - getZ());
        double distance = offset.length();
        if (distance < 0.25D) {
            setDeltaMovement(getDeltaMovement().scale(0.55D));
            return;
        }
        Vec3 desired = offset.scale(Math.min(maxSpeed, distance * 0.12D) / distance);
        setDeltaMovement(getDeltaMovement().scale(0.72D).add(desired.scale(0.28D)));
    }

    protected void hoverAtWaypoint() {
        Vec3 correction = new Vec3(hoverX - getX(), hoverY - getY(), hoverZ - getZ());
        if (correction.lengthSqr() > 9.0D) {
            moveToward(hoverX, hoverY, hoverZ, 0.36D);
        } else {
            setDeltaMovement(getDeltaMovement().scale(0.58D).add(correction.scale(0.025D)));
        }
    }

    protected void followOwner(LivingEntity owner) {
        ownerFollowing = ownerFollowTick(owner, ownerFollowing,
                OWNER_FOLLOW_START_DISTANCE, OWNER_FOLLOW_STOP_DISTANCE,
                OWNER_FOLLOW_MIN_OFFSET, OWNER_FOLLOW_MAX_OFFSET, OWNER_FOLLOW_SPEED);
    }

    protected void clearOwnerFollow() {
        ownerFollowing = false;
        ownerFollowTarget = null;
        orbitInitialized = false;
        haltMoveControl();
    }

    protected void haltMoveControl() {
        getMoveControl().setWantedPosition(getX(), getY(), getZ(), 0.0D);
    }

    protected boolean ownerFollowTick(LivingEntity owner, boolean following, double startDistance,
            double stopDistance, double minOffset, double maxOffset, double speed) {
        double distanceSqr = distanceToSqr(owner);
        if (following) {
            if (distanceSqr <= stopDistance * stopDistance) {
                following = false; // 追到位：收手悬停（迟滞下沿）
            }
        } else if (distanceSqr > startDistance * startDistance) {
            following = true; // 主人走远：起步追（迟滞上沿）
            ownerFollowRecalc = 0;
        }
        if (!following) {
            faceTowards(owner.position().add(0.0D, owner.getBbHeight() * 0.5D, 0.0D),
                    OWNER_HOVER_FACE_YAW, OWNER_HOVER_FACE_PITCH);
            return false;
        }
        if (--ownerFollowRecalc <= 0 || ownerFollowTarget == null) {
            ownerFollowRecalc = OWNER_FOLLOW_RECALC_TICKS;
            ownerFollowTarget = ownerHoverPoint(owner, minOffset, maxOffset);
        }
        getMoveControl().setWantedPosition(ownerFollowTarget.x, ownerFollowTarget.y,
                ownerFollowTarget.z, speed);
        getLookControl().setLookAt(owner, 10.0F, getMaxHeadXRot());
        return true;
    }

    protected Vec3 ownerHoverPoint(LivingEntity owner, double minOffset, double maxOffset) {
        double standoff = ownerStandoff(owner);
        double dx = getX() - owner.getX();
        double dz = getZ() - owner.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-3D) {
            float yaw = owner.getYRot() * Mth.DEG_TO_RAD;
            dx = Mth.sin(yaw) * standoff;
            dz = -Mth.cos(yaw) * standoff;
        } else {
            dx = dx / horizontal * standoff;
            dz = dz / horizontal * standoff;
        }
        return new Vec3(owner.getX() + dx, owner.getY() + hoverOffset(minOffset, maxOffset),
                owner.getZ() + dz);
    }

    protected double ownerStandoff(LivingEntity owner) {
        return Math.max(OWNER_FOLLOW_STANDOFF, owner.getBbWidth() * 0.5D + 2.0D);
    }

    protected double hoverOffset(double minOffset, double maxOffset) {
        double phase = (tickCount + (getId() & 0xFF) * 7) * 0.05D;
        double wave = (Math.sin(phase) + 1.0D) * 0.5D;
        return minOffset + (maxOffset - minOffset) * wave;
    }

    protected void maintainGiantOwnerFollow(LivingEntity owner) {
        double distanceSqr = distanceToSqr(owner);
        if (distanceSqr > GIANT_FOLLOW_RANGE * GIANT_FOLLOW_RANGE
                && teleportNearGiantOwner(owner)) {
            ownerFollowTarget = null;
            orbitInitialized = false;
            setDeltaMovement(Vec3.ZERO);
            return;
        }
        orbitGiantOwner(owner);
    }

    protected void orbitGiantOwner(LivingEntity owner) {
        double dx = getX() - owner.getX();
        double dz = getZ() - owner.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double radius = giantOrbitRadius(owner);
        if (!orbitInitialized
                || Math.abs(horizontal - radius) > GIANT_ORBIT_RESYNC_RADIUS) {
            orbitAngle = Math.atan2(dz, dx);
            orbitInitialized = true;
        }
        orbitAngle += GIANT_ORBIT_ANGULAR_SPEED;
        if (orbitAngle < 0.0D) {
            orbitAngle += Mth.TWO_PI;
        } else if (orbitAngle >= Mth.TWO_PI) {
            orbitAngle -= Mth.TWO_PI;
        }
        double orbitY = owner.getY()
                + hoverOffset(GIANT_OWNER_FOLLOW_MIN_OFFSET, GIANT_OWNER_FOLLOW_MAX_OFFSET);
        ownerFollowTarget = new Vec3(owner.getX() + Math.cos(orbitAngle) * radius, orbitY,
                owner.getZ() + Math.sin(orbitAngle) * radius);
        getMoveControl().setWantedPosition(ownerFollowTarget.x, ownerFollowTarget.y,
                ownerFollowTarget.z, GIANT_FOLLOW_SPEED);
        getLookControl().setLookAt(owner, 10.0F, getMaxHeadXRot());
    }

    protected double giantOrbitRadius(LivingEntity owner) {
        return ownerStandoff(owner) + GIANT_ORBIT_EXTRA_RADIUS;
    }

    protected boolean teleportNearGiantOwner(LivingEntity owner) {
        double ringRadius = giantOrbitRadius(owner);
        for (int attempt = 0; attempt < 8; ++attempt) {
            double angle = random.nextDouble() * Mth.TWO_PI;
            double radius = ringRadius + random.nextDouble() * 4.0D;
            double x = owner.getX() + Math.cos(angle) * radius;
            double z = owner.getZ() + Math.sin(angle) * radius;
            double y = owner.getY()
                    + hoverOffset(GIANT_OWNER_FOLLOW_MIN_OFFSET, GIANT_OWNER_FOLLOW_MAX_OFFSET);
            AABB box = getBoundingBox().move(x - getX(), y - getY(), z - getZ());
            if (!level().noCollision(this, box) || level().containsAnyLiquid(box)) {
                continue;
            }
            getNavigation().stop();
            teleportTo(x, y, z);
            fallDistance = 0.0F;
            return true;
        }
        return false;
    }

    @Nullable
    protected Vec3 peacefulDestination() {
        if (commandPosEntity != null && commandPosEntity.isAlive()) {
            return commandPosEntity.position().add(0.0D, commandPosEntity.getBbHeight() + 2.0D, 0.0D);
        }
        if (commandPos != null) {
            return Vec3.atCenterOf(commandPos).add(0.0D, 2.0D, 0.0D);
        }
        LivingEntity owner = getTrueOwner();
        if (isGuardingArea() && boundPos != null && distanceToSqr(Vec3.atCenterOf(boundPos)) > 144.0D) {
            return Vec3.atCenterOf(boundPos).add(0.0D, 4.0D, 0.0D);
        }
        if (isWandering() && owner != null && tickCount % 100 == 0) {
            double angle = random.nextDouble() * Mth.TWO_PI;
            double radius = 4.0D + random.nextDouble() * 6.0D;
            return owner.position().add(Mth.cos((float) angle) * radius,
                    owner.getBbHeight() + 2.0D + random.nextDouble() * 3.0D,
                    Mth.sin((float) angle) * radius);
        }
        return null;
    }

    protected void updateTailAttack() {
        if (--tailCooldown > 0 || getTarget() == null || isCharging()) {
            return;
        }
        Vec3 look = getLookAngle();
        Vec3 horizontalLook = new Vec3(look.x, 0.0D, look.z).normalize();
        AABB sweepArea = getBoundingBox().inflate(4.5D, 2.5D, 4.5D);
        List<LivingEntity> rearTargets = level().getEntitiesOfClass(LivingEntity.class, sweepArea, candidate -> {
            if (!isValidEnemy(candidate, 30.25D)) return false;
            Vec3 toTarget = candidate.position().subtract(position());
            Vec3 horizontal = new Vec3(toTarget.x, 0.0D, toTarget.z).normalize();
            return horizontal.dot(horizontalLook) < -0.15D;
        });
        if (rearTargets.isEmpty()) {
            tailCooldown = 35 + random.nextInt(31);
            return;
        }

        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.25F;
        for (LivingEntity victim : rearTargets) {
            if (victim.hurt(pierceUndeadImmunity(damageSources().mobAttack(this), victim), damage)) {
                double dx = getX() - victim.getX();
                double dz = getZ() - victim.getZ();
                victim.knockback(1.6D, dx, dz);
            }
        }
        playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.65F);
        if (level() instanceof ServerLevel serverLevel) {
            Vec3 rear = position().subtract(horizontalLook.scale(2.2D)).add(0.0D, 1.0D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, rear.x, rear.y, rear.z,
                    18, 1.8D, 0.7D, 1.8D, 0.08D);
        }
        tailCooldown = 140 + random.nextInt(121);
    }

    protected void sanitizeTargets() {
        LivingEntity target = getTarget();
        if (!isValidEnemy(target, TARGET_RANGE_SQR * 2.25D)) {
            super.setTarget(null);
            setAlternativeTarget(0, 0);
        } else {
            setAlternativeTarget(0, target.getId());
        }
        for (int head = 1; head <= 2; ++head) {
            Entity watched = level().getEntity(getAlternativeTarget(head));
            if (!(watched instanceof LivingEntity living) || !isSideHeadCandidate(living, getTarget())) {
                setAlternativeTarget(head, 0);
            }
        }
    }

    protected void updateHeadTargets() {
        LivingEntity mainTarget = getTarget();
        trackMainLock(mainTarget);
        boolean needScan = sideRetargetCooldown[0] <= 0 || sideRetargetCooldown[1] <= 0;
        if (!needScan) {
            --sideRetargetCooldown[0];
            --sideRetargetCooldown[1];
            return;
        }
        List<LivingEntity> candidates = level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(TARGET_RANGE, 24.0D, TARGET_RANGE),
                candidate -> isSideHeadCandidate(candidate, mainTarget) && hasLineOfSight(candidate));
        if (candidates.isEmpty()) {
            if (mainTarget == null) {
                setAlternativeTarget(1, 0);
                setAlternativeTarget(2, 0);
                return;
            }
            candidates.add(mainTarget);
        }
        for (int index = 0; index < 2; ++index) {
            if (sideRetargetCooldown[index] > 0) {
                --sideRetargetCooldown[index];
                continue;
            }
            int head = index + 1;
            LivingEntity selected = selectSideHeadTarget(candidates, mainTarget, head);
            setAlternativeTarget(head, selected == null ? 0 : selected.getId());
            sideRetargetCooldown[index] = 30 + random.nextInt(31);
        }
    }

    protected static final int SIDE_HEAD_ASSIST_AFTER_TICKS = 100;

    protected void trackMainLock(@Nullable LivingEntity target) {
        int id = target == null ? 0 : target.getId();
        if (id != mainLockedTargetId) {
            mainLockedTargetId = id;
            mainLockTicks = 0;
        } else if (id != 0) {
            ++mainLockTicks;
        }
        boolean unlocked = id != 0 && mainLockTicks > SIDE_HEAD_ASSIST_AFTER_TICKS;
        if (unlocked && !sideAssistUnlocked) {
            sideRetargetCooldown[0] = 0;
            sideRetargetCooldown[1] = 0;
        }
        sideAssistUnlocked = unlocked;
    }

    @Nullable
    protected LivingEntity selectSideHeadTarget(List<LivingEntity> candidates, @Nullable LivingEntity mainTarget,
            int head) {
        if (mainTarget != null && sideAssistUnlocked) {
            return mainTarget;
        }
        if (candidates.isEmpty()) {
            return mainTarget;
        }
        List<LivingEntity> preferred = new ArrayList<>();
        int otherHeadId = getAlternativeTarget(head == 1 ? 2 : 1);
        for (LivingEntity candidate : candidates) {
            if (candidate != mainTarget && candidate.getId() != otherHeadId) {
                preferred.add(candidate);
            }
        }
        List<LivingEntity> pool = preferred.isEmpty() ? candidates : preferred;
        return pool.get(random.nextInt(pool.size()));
    }

    protected boolean isValidEnemy(@Nullable LivingEntity candidate, double rangeSqr) {
        return candidate != null && candidate != this && candidate.isAlive() && !candidate.isSpectator()
                && canAttack(candidate) && distanceToSqr(candidate) <= rangeSqr;
    }

    protected boolean isSideHeadCandidate(@Nullable LivingEntity candidate, @Nullable LivingEntity mainTarget) {
        if (!isValidEnemy(candidate, TARGET_RANGE_SQR)) {
            return false;
        }
        return candidate == mainTarget || (candidate instanceof Enemy && canAttack(candidate));
    }

    protected void updateCommand() {
        if (!isCommanded() || getTarget() != null) return;
        if (commandTick != Integer.MAX_VALUE && --commandTick <= 0) {
            commandPos = null;
            commandPosEntity = null;
            return;
        }
        if (commandPosEntity != null) {
            if (!commandPosEntity.isAlive()) {
                commandPosEntity = null;
                commandPos = null;
            } else {
                commandPos = commandPosEntity.blockPosition();
                if (distanceToSqr(commandPosEntity) <= 9.0D) {
                    commandPosEntity = null;
                    commandPos = null;
                }
            }
        } else if (commandPos != null && distanceToSqr(Vec3.atCenterOf(commandPos)) <= 9.0D) {
            commandPos = null;
        }
    }

    protected void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) return;
        double limit = (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D;
        if (getTarget().distanceToSqr(Vec3.atCenterOf(boundPos)) > limit) {
            setTarget(null);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target != null && target != this && !ServantTargetRules.isTechnicalEntity(target)
                && target.canBeSeenAsEnemy() && !isAlliedTo(target) && super.canAttack(target);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target != null && (getControllingPassenger() != null || !canAttack(target))) {
            target = null; // 骑乘操控中主人接管索敌；其余情况过滤掉打不过的目标
        }
        super.setTarget(target);
    }

    @Override
    public void tryKill(Player player) {
        if (level().isClientSide()) {
            return;
        }
        if (getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(ModDamageSource.DISMISSED)) {
            return super.hurt(source, amount);
        }
        if (ExecutionDamage.isExecution(source, amount)) {
            return super.hurt(source, Float.MAX_VALUE);
        }
        if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.WITHER_IMMUNE_TO)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        Entity attacker = source.getEntity();
        Entity direct = source.getDirectEntity();
        if (getInvulnerableTicks() > 0
                || (attacker != null && isAlliedTo(attacker))
                || (direct != null && isAlliedTo(direct))) {
            return false;
        }
        if (isStarEmbedded() && RagingStarSupport.isElementReducedSource(source)) {
            amount *= RagingStarSupport.ELEMENT_DAMAGE_MULTIPLIER;
        }
        if (isPowered() && source.is(DamageTypeTags.IS_PROJECTILE)) {
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, getX(), getY(1.8D), getZ(),
                        6, 0.5D, 0.6D, 0.5D, 0.02D);
            }
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        if (onGround) {
            fallDistance = 0.0F;
        }
    }

    @Override
    public void playBlockFallSound() {
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        MobEffect effect = instance.getEffect();
        if (effect == MobEffects.WITHER || effect == MobEffects.POISON
                || effect == MobEffects.HEAL) { // 瞬间治疗对亡灵是打击，也挡（1.20.1 常量名 HEAL）
            return false;
        }
        if (effect != null && effect.getCategory() == MobEffectCategory.HARMFUL) {
            return false; // 一切“有害”分类（含魔改模组自加的凋零类效果）
        }
        return super.canBeAffected(instance);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) return true;
        if (ServantFaction.isSameFaction(this, entity)) return true;
        LivingEntity owner = getTrueOwner();
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) return true;
        if (entity instanceof IOwned owned) {
            if (this.getUUID().equals(owned.getOwnerId()) || owned.getTrueOwner() == this) return true;
            if (getOwnerId() != null && getOwnerId().equals(owned.getOwnerId())) return true;
        }
        if (entity instanceof OwnableEntity ownable && owner != null && ownable.getOwner() == owner) return true;
        return super.isAlliedTo(entity);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Nullable
    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITHER_HURT;
    }

    @Nullable
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override public boolean canUpdateMove() { return true; }

    @Override
    public void updateMoveMode(Player player) {
        ServantMoveModes.cycle(this, player);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        if (getPersistentData().getBoolean("goety_storm:no_boon")) {
            return;
        }
        OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.RAGING_STAR.get()));
        OwnerBoundLoot.deliver(this, new ItemStack(Items.NETHER_STAR));
    }

}
