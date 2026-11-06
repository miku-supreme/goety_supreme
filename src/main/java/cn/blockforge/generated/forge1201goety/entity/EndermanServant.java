package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantOwnerTargetGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;

public class EndermanServant extends Summoned implements IServant, OwnableEntity, RagingStarServant {
    private static final int WANDERING_FLAG = 1;
    private static final int STAYING_FLAG = 2;
    private static final int NATURAL_FLAG = 4;

    private static final int DODGE_TELEPORT_COOLDOWN = 20;
    private static final int APPROACH_TELEPORT_COOLDOWN = 60;

    private static final double APPROACH_DISTANCE_SQR = 144.0D;

    private static final double STAR_MAX_HEALTH = 80.0D;

    private static final double STAR_ATTACK_DAMAGE = 14.0D;

    private static final double STAR_ARMOR = 15.0D;
    private static final double STAR_ARMOR_TOUGHNESS = 8.0D;

    private static final double STAR_KNOCKBACK_RESISTANCE = 0.8D;

    private static final double STAR_REACH_BONUS = 1.0D;

    private static final float STAR_SIZE_SCALE = 1.15F;
    private static final float STAR_WIDTH = 0.6F * STAR_SIZE_SCALE;
    private static final float STAR_HEIGHT = 2.9F * STAR_SIZE_SCALE;

    private static final float BASE_WIDTH = 0.6F;
    private static final float BASE_HEIGHT = 2.9F;

    private static final float VOID_TOUCHED_CHANCE = 0.25F;
    private static final int VOID_TOUCHED_SECONDS = 5;

    private static final double COMBO_RANGE_SQR = 512.0D;

    private static final int COMBO_COOLDOWN = 1200;

    private static final double COMBO_SEARCH_RADIUS = 64.0D;

    private static final int COMBO_RESCAN_WINDOW = 20;

    private static final int COMBO_RESCAN_INTERVAL = 5;

    private static final int COMBO_STRIKE_INTERVAL = 4;

    private static final int COMBO_EXTRA_MAX = 16;

    private static final int GAZE_DURATION = 60;

    private static final int GAZE_SLOWNESS_AMPLIFIER = 9;

    private static final float GAZE_MAX_HEIGHT = 2.25F;

    private static final UUID STAR_HEALTH_UUID = UUID.fromString("d7c6e9f8-1b3d-4f5a-8e2c-9a0b1c2d3e4f");
    private static final UUID STAR_KNOCKBACK_UUID = UUID.fromString("e8d7f0a9-2c4e-4a6b-9f3d-0b1c2d3e4f50");

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID =
            UUID.fromString("020e0dfb-87ae-4653-9556-831010e291a0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(
            SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15D,
            AttributeModifier.Operation.ADDITION);

    private static final EntityDataAccessor<Byte> SERVANT_FLAGS =
            SynchedEntityData.defineId(EndermanServant.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Boolean> STARRED =
            SynchedEntityData.defineId(EndermanServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> CREEPY =
            SynchedEntityData.defineId(EndermanServant.class, EntityDataSerializers.BOOLEAN);

    private int lastStareSound = -400;

    private boolean hostile;
    private int teleportCooldown;

    private int comboCooldown;

    private int comboRescanTicks;

    private int comboExtraHits;

    private final Set<UUID> comboHitIds = new HashSet<>();

    private final List<LivingEntity> comboQueue = new ArrayList<>();

    private int comboStrikeTicks;

    private boolean comboTrailActive;

    private Vec3 comboOrigin;

    private String comboOriginDim;

    private boolean comboActive;

    public EndermanServant(EntityType<? extends EndermanServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        setMaxUpStep(1.0F);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), 40.0D);
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), 7.0D);
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), 0.3D);
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), 64.0D);
    }

    private static void setBaseAttribute(AttributeInstance attribute, double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        setConfigurableAttributes();
        setHealth(getMaxHealth());
        return result;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SERVANT_FLAGS, (byte) 0);
        entityData.define(STARRED, false);
        entityData.define(CREEPY, false);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.removeAllGoals(goal -> true);

        this.targetSelector.addGoal(0, new ServantOwnerTargetGoal.HurtBy<>(this));
        this.targetSelector.addGoal(1, new ServantOwnerTargetGoal.Hurt<>(this));
        this.targetSelector.addGoal(2, new ServantHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new SummonTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false,
                candidate -> candidate instanceof Enemy && !isAlliedTo(candidate)));

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TeleportTowardsTargetGoal(this));
        this.goalSelector.addGoal(2, new StarredReachAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new CommandPositionGoal(this, 1.25D));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) tag.putUUID("Owner", owner);
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
        tag.putInt("commandTick", commandTick);
        tag.putBoolean(RagingStarSupport.NBT_KEY, isRagingStarred());
        if (commandPos != null) tag.put("commandPos", NbtUtils.writeBlockPos(commandPos));
        if (commandPosEntity != null) tag.putUUID("commandPosEntity", commandPosEntity.getUUID());
        if (boundPos != null) {
            tag.put("boundPos", NbtUtils.writeBlockPos(boundPos));
            tag.putString("boundDim", boundDim);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        float savedHealth = tag.contains("Health") ? tag.getFloat("Health") : getMaxHealth();
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        } else if (tag.hasUUID("GoetyOwner")) {
            setOwnerId(tag.getUUID("GoetyOwner"));
        }
        setWandering(tag.getBoolean("wandering"));
        setStaying(tag.getBoolean("staying"));
        setConfigurableAttributes();
        boolean starred = tag.getBoolean(RagingStarSupport.NBT_KEY);
        if (starred) {
            entityData.set(STARRED, true);
            getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, true);
            applyStarAttributes();
        }
        setHealth(Math.min(savedHealth, getMaxHealth()));
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        if (tag.contains("commandPos", 10)) {
            commandPos = NbtUtils.readBlockPos(tag.getCompound("commandPos")).immutable();
        } else if (tag.contains("GoetyCommandX")) {
            commandPos = new BlockPos(tag.getInt("GoetyCommandX"), tag.getInt("GoetyCommandY"),
                    tag.getInt("GoetyCommandZ"));
        }
        if (tag.contains("boundPos", 10)) {
            boundPos = NbtUtils.readBlockPos(tag.getCompound("boundPos")).immutable();
            boundDim = tag.contains("boundDim") ? tag.getString("boundDim")
                    : level().dimension().location().toString();
        }
    }

    @Override
    public UUID getOwnerUUID() {
        return getOwnerId();
    }

    @Override
    public void checkHostility() {
        hostile = false;
    }

    @Override
    public void setHostile(boolean hostile) {
        this.hostile = false;
    }

    @Override
    public boolean isHostile() {
        return hostile;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target != null && target != this && !isAlliedTo(target) && super.canAttack(target);
    }

    @Override
    public void setTarget(LivingEntity target) {
        LivingEntity valid = target != null && canAttack(target) ? target : null;
        super.setTarget(valid);
        setCreepy(valid != null);
        updateAttackingSpeedBoost(valid != null);
    }

    private void updateAttackingSpeedBoost(boolean attacking) {
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }
        if (attacking) {
            if (!speed.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                speed.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        } else {
            speed.removeModifier(SPEED_MODIFIER_ATTACKING);
        }
    }

    public boolean isCreepy() {
        return entityData.get(CREEPY);
    }

    private void setCreepy(boolean creepy) {
        if (isCreepy() != creepy) {
            entityData.set(CREEPY, creepy);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (CREEPY.equals(accessor) && isCreepy() && level().isClientSide()) {
            playStareSound();
        }
    }

    private void playStareSound() {
        if (tickCount >= lastStareSound + 400) {
            lastStareSound = tickCount;
            if (!isSilent()) {
                level().playLocalSound(getX(), getEyeY(), getZ(), SoundEvents.ENDERMAN_STARE,
                        getSoundSource(), 2.5F, 1.0F, false);
            }
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) return true;
        LivingEntity owner = getTrueOwner();
        if (ServantFaction.isSameFaction(this, entity)) return true;
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) return true;
        if (entity instanceof com.Polarice3.Goety.api.entities.IOwned other
                && getOwnerId() != null && getOwnerId().equals(other.getOwnerId())) return true;
        if (entity instanceof OwnableEntity ownable && owner != null && ownable.getOwner() == owner) return true;
        return super.isAlliedTo(entity);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * (2.55F / BASE_HEIGHT);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENDERMAN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isRagingStarred() && amount > 0.0F) {
            amount *= RagingStarSupport.DAMAGE_MULTIPLIER;
            if (RagingStarSupport.isElementReducedSource(source)) {
                amount *= RagingStarSupport.ELEMENT_DAMAGE_MULTIPLIER;
            }
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt && !level().isClientSide() && source.getDirectEntity() instanceof Projectile) {
            if (servantTeleportRandomly()) {
                teleportCooldown = DODGE_TELEPORT_COOLDOWN;
            }
        }
        return hurt;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && isRagingStarred() && !level().isClientSide()
                && target instanceof LivingEntity living && living.isAlive()
                && !RagingStarSupport.isFriendlyUnit(this, living)
                && random.nextFloat() < VOID_TOUCHED_CHANCE) {
            living.addEffect(new MobEffectInstance(GoetyEffects.VOID_TOUCHED.get(),
                    VOID_TOUCHED_SECONDS * 20, 0), this);
        }
        return hit;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            spawnPortalParticles();
            RagingStarSupport.tickStarParticles(this);
            return;
        }
        if (teleportCooldown > 0) {
            teleportCooldown--;
        }
        updateCommand();
        updateGuardPosition();
        if (isStaying() && getTarget() == null) {
            getNavigation().stop();
        }
        ServantTeleportHelper.tick(this);
        RagingStarSupport.tickStarProtection(this);
        tickRagingStar();
    }

    @Override
    public boolean isRagingStarred() {
        return entityData.hasItem(STARRED) && Boolean.TRUE.equals(entityData.get(STARRED));
    }

    @Override
    public void setRagingStarred(boolean starred) {
        if (starred == isRagingStarred()) {
            return;
        }
        entityData.set(STARRED, starred);
        if (level().isClientSide()) {
            return;
        }
        getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, starred);
        if (starred) {
            applyStarAttributes();
            refreshDimensions();
            setHealth(getMaxHealth());
        } else if (comboActive) {
            finishTeleportCombo();
        }
    }

    private void applyStarAttributes() {
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        if (health != null && health.getModifier(STAR_HEALTH_UUID) == null) {
            double delta = STAR_MAX_HEALTH - health.getBaseValue();
            if (Math.abs(delta) > 1.0E-4D) {
                health.addPermanentModifier(new AttributeModifier(STAR_HEALTH_UUID,
                        "Raging star health", delta, AttributeModifier.Operation.ADDITION));
            }
        }
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && Math.abs(attack.getBaseValue() - STAR_ATTACK_DAMAGE) > 1.0E-4D) {
            attack.setBaseValue(STAR_ATTACK_DAMAGE);
        }
        setBaseIfDifferent(Attributes.ARMOR, STAR_ARMOR);
        setBaseIfDifferent(Attributes.ARMOR_TOUGHNESS, STAR_ARMOR_TOUGHNESS);
        AttributeInstance knockback = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (knockback != null && knockback.getModifier(STAR_KNOCKBACK_UUID) == null) {
            knockback.addPermanentModifier(new AttributeModifier(STAR_KNOCKBACK_UUID,
                    "Raging star knockback resistance", STAR_KNOCKBACK_RESISTANCE,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    private void setBaseIfDifferent(net.minecraft.world.entity.ai.attributes.Attribute attribute, double wanted) {
        AttributeInstance instance = getAttribute(attribute);
        if (instance != null && Math.abs(instance.getBaseValue() - wanted) > 1.0E-4D) {
            instance.setBaseValue(wanted);
        }
    }

    private void tickRagingStar() {
        if (!isRagingStarred()) {
            return;
        }
        if (tickCount % 20 == 0 && getHealth() < getMaxHealth()) {
            heal(1.0F);
        }
        if (tickCount % 20 == 1) {
            applyStarAttributes();
        }
        LivingEntity target = getTarget();
        applyGazeSuppression(target);
        tickTeleportCombo(target);
    }

    private void applyGazeSuppression(LivingEntity target) {
        if (target == null || !target.isAlive() || RagingStarSupport.isFriendlyUnit(this, target)) {
            return;
        }
        if (target.getBbHeight() > GAZE_MAX_HEIGHT || RagingStarSupport.isBossLike(target)) {
            return;
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, GAZE_DURATION,
                GAZE_SLOWNESS_AMPLIFIER));
        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, GAZE_DURATION, 0));
        if (target instanceof Mob mob) {
            mob.setTarget(null);
        }
    }

    private void tickTeleportCombo(LivingEntity target) {
        if (comboCooldown > 0) {
            --comboCooldown;
        }
        if (!comboQueue.isEmpty()) {
            if (comboStrikeTicks > 0) {
                --comboStrikeTicks;
                return;
            }
            comboStrikeTicks = COMBO_STRIKE_INTERVAL;
            strikeComboVictim(comboQueue.remove(0));
            if (comboQueue.isEmpty()) {
                comboRescanTicks = COMBO_RESCAN_WINDOW;
            }
            return;
        }
        if (comboRescanTicks > 0) {
            --comboRescanTicks;
            if (comboExtraHits < COMBO_EXTRA_MAX && comboRescanTicks % COMBO_RESCAN_INTERVAL == 0) {
                List<LivingEntity> fresh = findFreshComboVictims(COMBO_EXTRA_MAX - comboExtraHits);
                if (!fresh.isEmpty()) {
                    comboExtraHits += fresh.size();
                    comboQueue.addAll(fresh);
                    comboStrikeTicks = 0;
                    comboRescanTicks = 0;
                }
            }
            return;
        }
        if (comboActive) {
            finishTeleportCombo();
            return;
        }
        if (comboCooldown > 0) {
            return;
        }
        if (target == null || !target.isAlive() || distanceToSqr(target) > COMBO_RANGE_SQR) {
            return;
        }
        comboExtraHits = 0;
        comboHitIds.clear();
        comboQueue.addAll(findFreshComboVictims(Integer.MAX_VALUE));
        comboStrikeTicks = 0;
        comboCooldown = COMBO_COOLDOWN;
        comboRescanTicks = 0;
        if (!comboQueue.isEmpty()) {
            comboOrigin = position();
            comboOriginDim = level().dimension().location().toString();
            comboActive = true;
        }
    }

    private void finishTeleportCombo() {
        comboActive = false;
        comboQueue.clear();
        comboRescanTicks = 0;
        comboStrikeTicks = 0;
        Vec3 origin = comboOrigin;
        String originDim = comboOriginDim;
        comboOrigin = null;
        comboOriginDim = null;
        if (level().isClientSide() || !isAlive()) {
            return;
        }
        comboTrailActive = true;
        try {
            if (returnToOwner()) {
                return;
            }
            if (origin != null && level().dimension().location().toString().equals(originDim)) {
                servantTeleport(origin.x, origin.y, origin.z);
            }
        } finally {
            comboTrailActive = false;
        }
    }

    private boolean returnToOwner() {
        if (!isFollowing()) {
            return false;
        }
        LivingEntity owner = getTrueOwner();
        if (owner == null || !owner.isAlive() || owner.level() != level()) {
            return false;
        }
        if (distanceToSqr(owner) > COMBO_SEARCH_RADIUS * COMBO_SEARCH_RADIUS) {
            return false;
        }
        return servantTeleportNear(owner);
    }

    private List<LivingEntity> findFreshComboVictims(int limit) {
        if (!(level() instanceof ServerLevel server)) {
            return List.of();
        }
        List<LivingEntity> victims = server.getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(COMBO_SEARCH_RADIUS),
                candidate -> candidate.isAlive()
                        && !comboHitIds.contains(candidate.getUUID())
                        && !RagingStarSupport.isFriendlyUnit(this, candidate));
        victims.sort(Comparator.comparingDouble(this::distanceToSqr));
        if (victims.size() > limit) {
            return victims.subList(0, limit);
        }
        return victims;
    }

    private void strikeComboVictim(LivingEntity victim) {
        if (!(level() instanceof ServerLevel) || victim == null || !victim.isAlive()
                || victim.level() != level() || RagingStarSupport.isFriendlyUnit(this, victim)) {
            return;
        }
        comboHitIds.add(victim.getUUID());
        comboTrailActive = true;
        try {
            servantTeleportToEntity(victim);
            servantTeleportRandomly();
        } finally {
            comboTrailActive = false;
        }
        doHurtTarget(victim);
        swing(InteractionHand.MAIN_HAND);
    }

    private boolean servantTeleportToEntity(LivingEntity target) {
        if (level().isClientSide() || target == null || !target.isAlive()) {
            return false;
        }
        if (servantTeleport(target.getX(), target.getY(), target.getZ())) {
            return true;
        }
        return servantTeleportNear(target);
    }

    private void spawnPortalParticles() {
        for (int i = 0; i < 2; i++) {
            level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D),
                    (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(),
                    (random.nextDouble() - 0.5D) * 2.0D);
        }
    }

    private boolean servantTeleportRandomly() {
        if (level().isClientSide() || !isAlive()) {
            return false;
        }
        double x = getX() + (random.nextDouble() - 0.5D) * 32.0D;
        double y = getY() + (double) (random.nextInt(64) - 32);
        double z = getZ() + (random.nextDouble() - 0.5D) * 32.0D;
        return servantTeleport(x, y, z);
    }

    public boolean servantTeleportNear(LivingEntity target) {
        if (!(level() instanceof ServerLevel server) || target == null || !target.isAlive()) {
            return false;
        }
        BlockPos landing = ServantTeleportHelper.findLanding(this, target, 2.0D, 4.0D,
                target.blockPosition().getY(), 3);
        if (landing == null) {
            return false;
        }
        double x = landing.getX() + 0.5D;
        double y = landing.getY();
        double z = landing.getZ() + 0.5D;
        Vec3 origin = position();
        boolean moved = teleportTo(server, x, y, z, Set.of(), getYRot(), getXRot());
        if (moved) {
            if (comboTrailActive) {
                spawnTeleportTrail(origin, position());
            }
            playTeleportSound();
        }
        return moved;
    }

    private boolean servantTeleport(double x, double y, double z) {
        if (level().isClientSide()) {
            return false;
        }
        Vec3 origin = position();
        boolean moved = randomTeleport(x, y, z, true);
        if (moved) {
            if (comboTrailActive) {
                spawnTeleportTrail(origin, position());
            }
            playTeleportSound();
        }
        return moved;
    }

    private void spawnTeleportTrail(Vec3 from, Vec3 to) {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        double dist = from.distanceTo(to);
        if (dist < 1.5D) {
            return;
        }
        int samples = (int) Math.min(48.0D, Math.max(8.0D, dist / 0.75D));
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dz = to.z - from.z;
        double lift = getBbHeight() * 0.5D;
        for (int i = 0; i <= samples; i++) {
            double t = (double) i / samples;
            double px = from.x + dx * t + (random.nextDouble() - 0.5D) * 0.3D;
            double py = from.y + dy * t + lift + (random.nextDouble() - 0.5D) * 0.3D;
            double pz = from.z + dz * t + (random.nextDouble() - 0.5D) * 0.3D;
            server.sendParticles(ParticleTypes.PORTAL, px, py, pz, 2, 0.12D, 0.12D, 0.12D, 0.0D);
            if (i % 4 == 0) {
                server.sendParticles(ParticleTypes.END_ROD, px, py, pz, 1, 0.08D, 0.08D, 0.08D, 0.01D);
            }
        }
    }

    private void playTeleportSound() {
        playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    private void updateCommand() {
        if (!isCommanded() || getTarget() != null) return;
        if (commandTick != Integer.MAX_VALUE && --commandTick <= 0) {
            commandPos = null;
            commandPosEntity = null;
            return;
        }
        if (commandPosEntity != null) {
            commandPos = commandPosEntity.blockPosition();
            if (distanceToSqr(commandPosEntity) <= 4.0D) {
                commandPos = null;
                commandPosEntity = null;
            }
        }
    }

    private void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) return;
        if (getTarget().distanceToSqr(boundPos.getX() + 0.5D, boundPos.getY(), boundPos.getZ() + 0.5D)
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) setTarget(null);
    }

    @Override public void setNatural(boolean natural) { setFlag(NATURAL_FLAG, natural); }
    @Override public boolean isNatural() { return getFlag(NATURAL_FLAG); }
    @Override public boolean isWandering() { return getFlag(WANDERING_FLAG); }
    @Override public void setWandering(boolean value) { setFlag(WANDERING_FLAG, value); }
    @Override public boolean isStaying() { return getFlag(STAYING_FLAG) && !isCommanded(); }
    @Override public void setStaying(boolean value) { setFlag(STAYING_FLAG, value); if (value) getNavigation().stop(); }
    @Override public boolean canUpdateMove() { return true; }

    @Override
    public void updateMoveMode(Player player) {
        if (!isWandering() && !isStaying() && !isGuardingArea()) {
            setBoundPos(null); setWandering(true); setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.wander", getDisplayName()), true);
        } else if (!isStaying() && !isGuardingArea()) {
            setBoundPos(null); setWandering(false); setStaying(true);
            player.displayClientMessage(Component.translatable("info.goety.servant.staying", getDisplayName()), true);
        } else if (!isGuardingArea()) {
            setBoundPos(blockPosition()); setWandering(false); setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.guard", getDisplayName()), true);
        } else {
            setBoundPos(null); setWandering(false); setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.follow", getDisplayName()), true);
        }
        playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
    }

    @Override public void setCommandPos(BlockPos pos) { setCommandPos(pos, true); }
    @Override public void setCommandPos(BlockPos pos, boolean removeEntity) { if (removeEntity) commandPosEntity = null; commandPos = pos == null ? null : pos.immutable(); commandTick = pos == null ? 0 : (removeEntity ? 200 : Integer.MAX_VALUE); setStaying(false); }
    @Override public BlockPos getCommandPos() { return commandPos; }
    @Override public void setCommandPosEntity(LivingEntity entity) { commandPosEntity = entity; commandPos = entity == null ? null : entity.blockPosition(); commandTick = entity == null ? 0 : Integer.MAX_VALUE; setStaying(false); }
    @Override public LivingEntity getCommandPosEntity() { return commandPosEntity; }
    @Override public int getCommandTick() { return commandTick; }
    @Override public void setCommandTick(int ticks) { commandTick = ticks; }
    @Override public boolean isCommanded() { return commandPos != null || commandPosEntity != null; }
    @Override public BlockPos getBoundPos() { return boundPos; }
    @Override public void setBoundPos(BlockPos pos) { boundPos = pos == null ? null : pos.immutable(); if (boundPos != null) boundDim = level().dimension().location().toString(); }
    @Override public String getBoundDim() { return boundDim; }
    @Override public void setBoundDim(String dimension) { boundDim = dimension == null ? level().dimension().location().toString() : dimension; }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (isRagingStarred() && RagingStarSupport.isVanillaHarmful(effect)) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public void tryKill(Player player) {
        if (isRagingStarred() && getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        if (!level().isClientSide) {
            hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
        }
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean hitByPlayer) {
        super.dropCustomDeathLoot(source, looting, hitByPlayer);
        if (isRagingStarred()) {
            OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.RAGING_STAR.get()));
        }
    }

    @Override
    public Component getName() {
        if (isRagingStarred() && !hasCustomName()) {
            return Component.translatable("entity.goety_supreme.void_shadow_guard");
        }
        return super.getName();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return isRagingStarred()
                ? EntityDimensions.scalable(STAR_WIDTH, STAR_HEIGHT)
                : super.getDimensions(pose);
    }

    public static float starWidthScale() {
        return STAR_WIDTH / BASE_WIDTH;
    }

    public static float starHeightScale() {
        return STAR_HEIGHT / BASE_HEIGHT;
    }

    private boolean getFlag(int flag) { return (entityData.get(SERVANT_FLAGS) & flag) != 0; }
    private void setFlag(int flag, boolean value) { byte state = entityData.get(SERVANT_FLAGS); entityData.set(SERVANT_FLAGS, value ? (byte) (state | flag) : (byte) (state & ~flag)); }

    private static final class TeleportTowardsTargetGoal extends Goal {
        private final EndermanServant servant;
        private LivingEntity target;

        TeleportTowardsTargetGoal(EndermanServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            target = servant.getTarget();
            return target != null && target.isAlive() && !servant.isAlliedTo(target)
                    && servant.teleportCooldown <= 0
                    && servant.distanceToSqr(target) > APPROACH_DISTANCE_SQR;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            servant.teleportCooldown = servant.servantTeleportNear(target)
                    ? APPROACH_TELEPORT_COOLDOWN : DODGE_TELEPORT_COOLDOWN;
            target = null;
        }
    }

    private static final class StarredReachAttackGoal extends MeleeAttackGoal {

        private final EndermanServant servant;

        StarredReachAttackGoal(EndermanServant servant, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(servant, speedModifier, followingTargetEvenIfNotSeen);
            this.servant = servant;
        }

        @Override
        public double getAttackReachSqr(LivingEntity target) {
            double base = super.getAttackReachSqr(target);
            if (!servant.isRagingStarred()) {
                return base;
            }
            double reach = Math.sqrt(base) + STAR_REACH_BONUS;
            return reach * reach;
        }
    }
}
