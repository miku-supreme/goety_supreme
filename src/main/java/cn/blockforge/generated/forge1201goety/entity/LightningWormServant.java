package cn.blockforge.generated.goetysupreme.entity;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageSpellBridge;
import cn.blockforge.generated.goetysupreme.entity.LightningWormChainLightningEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormServant;
import cn.blockforge.generated.goetysupreme.entity.LightningWormThunderstormEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class LightningWormServant extends AbstractJerotesServant implements RangedAttackMob {

    public static final int SEGMENTS = 10;
    private static final float PART_DAMAGE_MULTIPLIER = 0.8F;

    private static final double OWNER_FOLLOW_START = 10.0D;
    private static final double OWNER_FOLLOW_STOP = 3.0D;
    private static final int OWNER_FOLLOW_RECALC_TICKS = 10;
    private static final double OWNER_FOLLOW_STANDOFF = 2.5D;
    private static final double OWNER_FOLLOW_SPEED = 1.0D;

    private boolean ownerFollowing;
    private Vec3 ownerFollowTarget;
    private int ownerFollowRecalc;

    private static final EntityDataAccessor<Integer> SHOOT_TICK =
            SynchedEntityData.defineId(LightningWormServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIGHTNING_TICK =
            SynchedEntityData.defineId(LightningWormServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHARGING_TICK =
            SynchedEntityData.defineId(LightningWormServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID =
            SynchedEntityData.defineId(LightningWormServant.class, EntityDataSerializers.OPTIONAL_UUID);

    private int spellLevel = 3;

    public LightningWormServant(EntityType<? extends LightningWormServant> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.6F);
        this.setNoGravity(true);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.navigation = new FlyingPathNavigation(this, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.MAX_HEALTH, 135.0D)
                .add(Attributes.ARMOR, 11.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        return navigation;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/lightning_worm.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.LIGHTNING_WORM;
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 50;
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("lightning_worm_ambient", SoundEvents.BLAZE_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("lightning_worm_hurt", SoundEvents.BLAZE_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("lightning_worm_death", SoundEvents.BLAZE_DEATH);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT_TICK, 0);
        this.entityData.define(LIGHTNING_TICK, 0);
        this.entityData.define(CHARGING_TICK, 0);
        this.entityData.define(CHILD_UUID, Optional.empty());
    }

    public int getShootTick() {
        return this.entityData.get(SHOOT_TICK);
    }

    public void setShootTick(int ticks) {
        this.entityData.set(SHOOT_TICK, ticks);
    }

    public int getLightningTick() {
        return this.entityData.get(LIGHTNING_TICK);
    }

    public void setLightningTick(int ticks) {
        this.entityData.set(LIGHTNING_TICK, ticks);
    }

    public int getChargingTick() {
        return this.entityData.get(CHARGING_TICK);
    }

    public void setChargingTick(int ticks) {
        this.entityData.set(CHARGING_TICK, ticks);
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(@Nullable UUID id) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(id));
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
            this.heal(3.0F);
        }
        this.clearFire();
        if (this.level().isClientSide()) {
            this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                    this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
        if (!this.level().isClientSide()) {
            this.ensureParts();
            this.setShootTick(Math.max(0, this.getShootTick() - 1));
            this.setLightningTick(Math.max(0, this.getLightningTick() - 1));
            this.setChargingTick(Math.max(0, this.getChargingTick() - 1));
        }
        if (this.getTarget() != null && this.getChargingTick() <= 0 && this.getLightningTick() <= 0) {
            if (this.distanceTo(this.getTarget()) <= 32.0F && this.random.nextInt(20) == 0) {
                this.setChargingTick(40);
                if (!this.isSilent()) {
                    this.playSound(jerotesSound("lightning_worm_charging", SoundEvents.LIGHTNING_BOLT_IMPACT),
                            15.0F, 0.8F + this.random.nextFloat() * 0.4F);
                }
                if (this.level().isClientSide()) {
                    LivingEntity chargeTarget = this.getTarget();
                    for (int i = 0; i < 20; ++i) {
                        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                                chargeTarget.getRandomX(0.5D), chargeTarget.getRandomY(),
                                chargeTarget.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        if (this.getChargingTick() == 5 && !this.level().isClientSide() && this.getTarget() != null) {
            this.setLightningTick(120);
            if (JerotesVillageSpellBridge.isLoaded()) {
                JerotesVillageSpellBridge.chaoticDischarge(this.spellLevel, this, this.getTarget());
            } else {
                this.spawnChainLightning(this.getTarget());
            }
        }
        if (this.random.nextInt(200) == 1 && !this.level().isClientSide()) {
            BlockPos rod = this.findLightningTargetAround(this.blockPosition());
            if (rod != null) {
                this.spawnChainLightningAt(rod);
            }
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.tickAirborneOwnerFollow();
    }

    private void tickAirborneOwnerFollow() {
        LivingEntity owner = getTrueOwner();
        boolean eligible = owner != null && owner.isAlive() && !owner.isSpectator()
                && owner.level() == level() && isFollowing() && !isCommanded()
                && getTarget() == null && !isPassenger() && !isLeashed()
                && isNoGravity() && getNavigation().isDone();
        if (!eligible) {
            if (ownerFollowing) {
                ownerFollowing = false;
                ownerFollowTarget = null;
            }
            return;
        }
        double distanceSqr = distanceToSqr(owner);
        if (ownerFollowing) {
            if (distanceSqr <= OWNER_FOLLOW_STOP * OWNER_FOLLOW_STOP) {
                ownerFollowing = false;
            }
        } else if (distanceSqr > OWNER_FOLLOW_START * OWNER_FOLLOW_START) {
            ownerFollowing = true;
            ownerFollowRecalc = 0;
        }
        if (!ownerFollowing) {
            if (ownerFollowTarget != null) {
                ownerFollowTarget = null;
                getMoveControl().setWantedPosition(getX(), getY(), getZ(), 0.0D);
            }
            return;
        }
        if (--ownerFollowRecalc <= 0 || ownerFollowTarget == null) {
            ownerFollowRecalc = OWNER_FOLLOW_RECALC_TICKS;
            ownerFollowTarget = ownerHoverPoint(owner);
        }
        getMoveControl().setWantedPosition(ownerFollowTarget.x, ownerFollowTarget.y,
                ownerFollowTarget.z, OWNER_FOLLOW_SPEED);
        getLookControl().setLookAt(owner, 10.0F, getMaxHeadXRot());
    }

    private Vec3 ownerHoverPoint(LivingEntity owner) {
        double dx = getX() - owner.getX();
        double dz = getZ() - owner.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-3D) {
            float yaw = owner.getYRot() * ((float) Math.PI / 180.0F);
            dx = Mth.sin(yaw) * OWNER_FOLLOW_STANDOFF;
            dz = -Mth.cos(yaw) * OWNER_FOLLOW_STANDOFF;
        } else {
            dx = dx / horizontal * OWNER_FOLLOW_STANDOFF;
            dz = dz / horizontal * OWNER_FOLLOW_STANDOFF;
        }
        double wave = (Math.sin((tickCount + (getId() & 0xFF) * 7) * 0.05D) + 1.0D) * 0.5D;
        return new Vec3(owner.getX() + dx, owner.getY() + 2.0D + wave * 1.0D, owner.getZ() + dz);
    }

    private void ensureParts() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        UUID childId = getChildId();
        if (childId != null && server.getEntity(childId) != null) {
            return;
        }
        LivingEntity parentPart = this;
        PlayerTeam team = (PlayerTeam) getTeam();
        for (int i = 0; i < SEGMENTS; ++i) {
            LightningWormPartEntity part = new LightningWormPartEntity(
                    ModEntityTypes.LIGHTNING_WORM_PART.get(), parentPart, 1.1F, 180.0F, 0.0F);
            part.setBodyIndex(i);
            part.damageMultiplier = PART_DAMAGE_MULTIPLIER;
            if (parentPart == this) {
                setChildId(part.getUUID());
            }
            part.setInitialPartPos(this);
            parentPart = part;
            server.addFreshEntity(part);
            if (team != null) {
                server.getScoreboard().addPlayerToTeam(part.getStringUUID(), team);
            }
        }
    }

    private void spawnChainLightning(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LightningWormChainLightningEntity bolt = new LightningWormChainLightningEntity(
                server, this, target, this.spellLevel);
        bolt.setPos(this.getX(), this.getEyeY(), this.getZ());
        server.addFreshEntity(bolt);
    }

    private void spawnChainLightningAt(BlockPos pos) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LightningWormChainLightningEntity bolt = new LightningWormChainLightningEntity(
                server, this, this, this.spellLevel);
        bolt.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        server.addFreshEntity(bolt);
    }

    private Optional<BlockPos> findLightningRod(BlockPos pos) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return Optional.empty();
        }
        PoiManager poiManager = serverLevel.getPoiManager();
        Optional<BlockPos> rod = poiManager.findClosest(
                holder -> holder.is(PoiTypes.LIGHTNING_ROD),
                p -> p.getY() == serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, p.getX(), p.getZ()) - 1,
                pos, 128, PoiManager.Occupancy.ANY);
        return rod.map(BlockPos::above);
    }

    private BlockPos findLightningTargetAround(BlockPos pos) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return null;
        }
        BlockPos heightPos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
        Optional<BlockPos> rod = this.findLightningRod(heightPos);
        if (rod.isPresent() && pos.closerThan(heightPos.above(), 32.0D)) {
            return rod.get();
        }
        return null;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (this.level().isClientSide()) {
            return;
        }
        this.setShootTick(240);
        if (JerotesVillageSpellBridge.isLoaded()) {
            JerotesVillageSpellBridge.wormThunderstorm(this.spellLevel, this, target);
        } else {
            this.castFeedback("magic_worm_thunderstorm", SoundEvents.LIGHTNING_BOLT_THUNDER,
                    "worm_thunderstorm_display", ParticleTypes.ELECTRIC_SPARK);
            this.spawnThunderstorm(target);
        }
    }

    private void spawnThunderstorm(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LightningWormThunderstormEntity storm = new LightningWormThunderstormEntity(
                server, this, target, this.spellLevel);
        storm.setPos(this.getX(), this.getEyeY(), this.getZ());
        server.addFreshEntity(storm);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.random.nextInt(3) == 0 && entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(this.damageSources().lightningBolt(), 1.0F);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_LIGHTNING) || source.is(DamageTypes.LIGHTNING_BOLT)) {
            this.heal(amount * 0.8F);
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_LIGHTNING) || source.is(DamageTypes.LIGHTNING_BOLT)
                || source.is(DamageTypes.FALL)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpellLevel", this.spellLevel);
        tag.putInt("LightningTick", this.getLightningTick());
        tag.putInt("ChargingTick", this.getChargingTick());
        tag.putInt("ShootTick", this.getShootTick());
        if (this.getChildId() != null) {
            tag.putUUID("ChildUUID", this.getChildId());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spellLevel = tag.contains("SpellLevel") ? tag.getInt("SpellLevel") : 3;
        this.setLightningTick(tag.getInt("LightningTick"));
        this.setChargingTick(tag.getInt("ChargingTick"));
        this.setShootTick(tag.getInt("ShootTick"));
        if (tag.hasUUID("ChildUUID")) {
            this.setChildId(tag.getUUID("ChildUUID"));
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        this.spellLevel = 3;
        return result;
    }
}
