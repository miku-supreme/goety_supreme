package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ICustomAttributes;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.EntityFinder;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.core.SeismicWave;
import fuzs.mutantmonsters.core.ZombieResurrection;
import fuzs.mutantmonsters.util.EntityUtil;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.MutantZombieServantModel;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.util.MutantZombieSlamArea;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieSlamFx;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.util.ZombieServantVariants;

public class MutantZombieServant extends AbstractMutantServant implements IServant, net.minecraft.world.entity.OwnableEntity {
    public static final Animation SLAM_GROUND_ANIMATION = MutantZombie.SLAM_GROUND_ANIMATION;
    public static final Animation THROW_ANIMATION = MutantZombie.THROW_ANIMATION;
    public static final Animation ROAR_ANIMATION = MutantZombie.ROAR_ANIMATION;
    private static final Animation[] ANIMATIONS = {SLAM_GROUND_ANIMATION, THROW_ANIMATION, ROAR_ANIMATION};

    private static final EntityDataAccessor<Integer> ANIMATION_ID =
            net.minecraft.network.syncher.SynchedEntityData.defineId(
                    MutantZombieServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_TICK =
            net.minecraft.network.syncher.SynchedEntityData.defineId(
                    MutantZombieServant.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Byte> THROW_ATTACK_STATE =
            net.minecraft.network.syncher.SynchedEntityData.defineId(
                    MutantZombieServant.class, EntityDataSerializers.BYTE);

    private static final int THROW_HIT_BIT = 1;
    private static final int THROW_FINISHED_BIT = 2;

    private static final EntityDataAccessor<Integer> LIVES =
            net.minecraft.network.syncher.SynchedEntityData.defineId(
                    MutantZombieServant.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> BERSERK_ACTIVE =
            net.minecraft.network.syncher.SynchedEntityData.defineId(
                    MutantZombieServant.class, EntityDataSerializers.BOOLEAN);

    private static final int MAX_DEATH_TIME = 140;
    private static final int MAX_VANISH_TIME = 100;

    private static final float RESURRECTION_HEALTH_DIVISOR = 4.0F;

    private static final int THROW_MAX_EXTRA_TICKS = 80;
    private static final int THROW_LANDING_TICKS = 10;

    private static final double ROAR_OFFHAND_BONUS_SCALE = 0.25D;

    private boolean howlingSoulUpgrade;
    boolean soulJarUpgrade;
    private int berserkTicks;

    private boolean berserkBoon;
    LivingEntity animationTarget;

    private static final String HOWLING_SOUL_TAG = "HowlingSoulUpgrade";
    private static final String SOUL_JAR_TAG = "SoulJarUpgrade";
    private static final String BERSERK_TICKS_TAG = "BerserkTicks";
    private static final String BERSERK_BOON_TAG = "BerserkBoon";

    private static final int SOUL_JAR_SERVANT_LIFE_BONUS = 30 * 20;

    private static final int BERSERK_SERVANT_LIFE_BONUS = 60 * 20;

    private static final float BERSERK_SIZE_SCALE = 1.10F;
    private static final UUID HOWLING_SOUL_HEALTH_UUID =
            UUID.fromString("11d8ae2f-e8df-4e20-b746-4d7f5b5f2c10");
    private static final UUID SOUL_JAR_HEALTH_UUID =
            UUID.fromString("8d3fb9ea-df86-40d5-93bd-cd6e1ee8c531");
    private static final UUID BERSERK_HEALTH_UUID =
            UUID.fromString("c1e9d10d-37d4-4fc6-9c9f-0b4f20b9ce31");
    private static final UUID BERSERK_ARMOR_UUID =
            UUID.fromString("3a8eb8bd-d263-45c5-8f8c-e203cf62f11f");
    private static final UUID BERSERK_TOUGHNESS_UUID =
            UUID.fromString("e4d7ca65-6c1e-4b31-9c0f-95abcbcfb2c8");
    private static final UUID BERSERK_ATTACK_UUID =
            UUID.fromString("f1d7c7db-142d-48c7-b6b9-1f727ecf6c3b");

    private static final UUID BERSERK_SPEED_UUID =
            UUID.fromString("b7c4a1e2-58d3-4a90-8f16-7c2d4e9a5f21");

    private static final UUID SUMMONED_KNOCKBACK_UUID =
            UUID.fromString("2f6b9d10-4c8a-4e73-a5d1-9b0c7e3f8a44");
    private static final TagKey<EntityType<?>> ZOMBIE_SERVANT_TAG = TagKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "zombie_servants"));
    private final List<SeismicWave> seismicWaveList = new ArrayList<>();
    final List<ServantResurrection> resurrectionList = new ArrayList<>();

    long nextRoarSummonTick;

    int roarSummonScheduled;

    private LivingEntity roarGuardTarget;
    private int guaranteedRoarTick = -1;
    private boolean roarGuaranteeSpent;

    private int throwFinishTimer = -1;

    private DamageSource deathCause;
    private int vanishTime;

    private int throwHitTick = -1;
    private int throwFinishTick = -1;

    public MutantZombieServant(EntityType<? extends MutantZombieServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        setMaxUpStep(1.0F);
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MutantZombieServantConfig.MAX_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, MutantZombieServantConfig.ATTACK_DAMAGE.get())
                .add(Attributes.FOLLOW_RANGE, MutantZombieServantConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, MutantZombieServantConfig.MOVEMENT_SPEED.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, MutantZombieServantConfig.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.ARMOR, MutantZombieServantConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, MutantZombieServantConfig.ARMOR_TOUGHNESS.get());
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return registerAttributes();
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), MutantZombieServantConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), MutantZombieServantConfig.ATTACK_DAMAGE.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), MutantZombieServantConfig.MOVEMENT_SPEED.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                MutantZombieServantConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), MutantZombieServantConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), MutantZombieServantConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), MutantZombieServantConfig.FOLLOW_RANGE.get());
        refreshUpgradeAttributes();
    }

    private void refreshUpgradeAttributes() {
        replaceModifier(getAttribute(Attributes.MAX_HEALTH), HOWLING_SOUL_HEALTH_UUID,
                "Howling soul health", howlingSoulUpgrade ? MutantZombieServantConfig.HOWLING_SOUL_HEALTH.get() : 0.0D);
        replaceModifier(getAttribute(Attributes.MAX_HEALTH), SOUL_JAR_HEALTH_UUID,
                "Soul jar health", soulJarUpgrade ? MutantZombieServantConfig.SOUL_JAR_HEALTH.get() : 0.0D);
        replaceModifier(getAttribute(Attributes.MAX_HEALTH), BERSERK_HEALTH_UUID,
                "Berserk essence health", isBerserk() ? MutantZombieServantConfig.BERSERK_HEALTH.get() : 0.0D);
        replaceModifier(getAttribute(Attributes.ARMOR), BERSERK_ARMOR_UUID,
                "Berserk essence armor", isBerserk() ? MutantZombieServantConfig.BERSERK_ARMOR.get() : 0.0D);
        replaceModifier(getAttribute(Attributes.ARMOR_TOUGHNESS), BERSERK_TOUGHNESS_UUID,
                "Berserk essence toughness", isBerserk() ? MutantZombieServantConfig.BERSERK_TOUGHNESS.get() : 0.0D);
        replaceModifier(getAttribute(Attributes.ATTACK_DAMAGE), BERSERK_ATTACK_UUID,
                "Berserk essence attack", isBerserk() ? MutantZombieServantConfig.BERSERK_ATTACK_BONUS.get() : 0.0D,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        replaceModifier(getAttribute(Attributes.MOVEMENT_SPEED), BERSERK_SPEED_UUID,
                "Berserk essence speed", isBerserk() ? MutantZombieServantConfig.BERSERK_SPEED_BONUS.get() : 0.0D,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private static void replaceModifier(AttributeInstance attribute, UUID id, String name, double amount) {
        replaceModifier(attribute, id, name, amount, AttributeModifier.Operation.ADDITION);
    }

    private static void replaceModifier(AttributeInstance attribute, UUID id, String name, double amount,
            AttributeModifier.Operation operation) {
        if (attribute == null) {
            return;
        }
        if (attribute.getModifier(id) != null) {
            attribute.removePermanentModifier(id);
        }
        if (amount != 0.0D) {
            attribute.addPermanentModifier(new AttributeModifier(id, name, amount, operation));
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
    public void convertNewEquipment(Entity original) {
        super.convertNewEquipment(original);
        setConfigurableAttributes();
        setHealth(getMaxHealth());
        if (getTrueOwner() instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, this);
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.removeAllGoals(goal -> true);

        this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new ServantHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new SummonTargetGoal(this));

        this.goalSelector.addGoal(0, new MutantRoarGoal(this));
        this.goalSelector.addGoal(1, new MutantMeleeAttackGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new CommandPositionGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new ServantWanderGoal(this, 1.0D, 0.001F));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SERVANT_FLAGS, (byte) 0);
        this.entityData.define(ANIMATION_ID, -1);
        this.entityData.define(ANIMATION_TICK, 0);
        this.entityData.define(THROW_ATTACK_STATE, (byte) 0);
        this.entityData.define(LIVES, Math.max(0, MutantZombieServantConfig.RESURRECTION_LIVES.get()));
        this.entityData.define(BERSERK_ACTIVE, false);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return mutantSound("entity.mutant_zombie.ambient", SoundEvents.ZOMBIE_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return mutantSound("entity.mutant_zombie.hurt", SoundEvents.ZOMBIE_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return mutantSound("entity.mutant_zombie.death", SoundEvents.ZOMBIE_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        if (deathTime == 0) {
            playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }
        tag.putInt("MutantAnimation", animationIndex(getAnimation()));
        tag.putInt("MutantAnimationTick", getAnimationTick());
        tag.putInt("Lives", getLives());
        tag.putShort("VanishTime", (short) vanishTime);
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
        tag.putLong("RoarSummonCooldown", Math.max(0L, nextRoarSummonTick - tickCount));
        tag.putInt("GuaranteedRoarTick", guaranteedRoarTick < 0 ? -1 : guaranteedRoarTick - tickCount);
        tag.putBoolean("RoarGuaranteeSpent", roarGuaranteeSpent);
        tag.putBoolean(HOWLING_SOUL_TAG, howlingSoulUpgrade);
        tag.putBoolean(SOUL_JAR_TAG, soulJarUpgrade);
        tag.putInt(BERSERK_TICKS_TAG, berserkTicks);
        tag.putBoolean(BERSERK_BOON_TAG, berserkBoon);
        tag.putInt("commandTick", commandTick);
        if (commandPos != null) {
            tag.put("commandPos", NbtUtils.writeBlockPos(commandPos));
        }
        if (commandPosEntity != null) {
            tag.putUUID("commandPosEntity", commandPosEntity.getUUID());
        }
        if (boundPos != null) {
            tag.put("boundPos", NbtUtils.writeBlockPos(boundPos));
            tag.putString("boundDim", boundDim);
        }
        if (!resurrectionList.isEmpty()) {
            ListTag list = new ListTag();
            for (ServantResurrection resurrection : resurrectionList) {
                CompoundTag entry = NbtUtils.writeBlockPos(resurrection.pos);
                entry.putInt("Tick", resurrection.tick);
                list.add(entry);
            }
            tag.put("Resurrections", list);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        } else if (tag.hasUUID("GoetyOwner")) {
            setOwnerId(tag.getUUID("GoetyOwner"));
        }

        if (tag.contains("MutantAnimation")) {
            int id = tag.getInt("MutantAnimation");
            setAnimation(id >= 0 && id < ANIMATIONS.length ? ANIMATIONS[id] : Animation.NONE);
            setAnimationTick(tag.contains("MutantAnimationTick") ? tag.getInt("MutantAnimationTick") : 0);
        }
        if (tag.contains("Lives")) {
            setLives(tag.getInt("Lives"));
        }
        vanishTime = tag.getShort("VanishTime");
        if (tag.contains("wandering")) {
            setWandering(tag.getBoolean("wandering"));
        }
        if (tag.contains("staying")) {
            setStaying(tag.getBoolean("staying"));
        }
        nextRoarSummonTick = tickCount + Math.max(0L, tag.getLong("RoarSummonCooldown"));
        int guaranteedRemaining = tag.getInt("GuaranteedRoarTick");
        guaranteedRoarTick = tag.contains("GuaranteedRoarTick") && guaranteedRemaining >= 0
                ? tickCount + guaranteedRemaining : -1;
        roarGuaranteeSpent = tag.getBoolean("RoarGuaranteeSpent");
        howlingSoulUpgrade = tag.getBoolean(HOWLING_SOUL_TAG);
        soulJarUpgrade = tag.getBoolean(SOUL_JAR_TAG);
        berserkTicks = Math.max(0, Math.min((MutantZombieServantConfig.BERSERK_DURATION_SECONDS.get() * 20), tag.getInt(BERSERK_TICKS_TAG)));
        berserkBoon = tag.getBoolean(BERSERK_BOON_TAG);
        syncBerserkState();
        refreshUpgradeAttributes();
        setHealth(Math.min(getHealth(), getMaxHealth()));
        if (tag.contains("commandPos", 10)) {
            commandPos = NbtUtils.readBlockPos(tag.getCompound("commandPos")).immutable();
        } else if (tag.contains("GoetyCommandX")) {
            commandPos = new BlockPos(tag.getInt("GoetyCommandX"), tag.getInt("GoetyCommandY"),
                    tag.getInt("GoetyCommandZ"));
        }
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        if (tag.hasUUID("commandPosEntity")) {
            commandPosEntity = EntityFinder.getLivingEntityByUuiD(tag.getUUID("commandPosEntity"));
        }
        if (tag.contains("boundPos", 10)) {
            boundPos = NbtUtils.readBlockPos(tag.getCompound("boundPos")).immutable();
            boundDim = tag.contains("boundDim") ? tag.getString("boundDim") : level().dimension().location().toString();
        }
        resurrectionList.clear();
        ListTag resurrections = tag.getList("Resurrections", Tag.TAG_COMPOUND);
        for (int i = 0; i < resurrections.size(); i++) {
            CompoundTag entry = resurrections.getCompound(i);
            resurrectionList.add(new ServantResurrection(NbtUtils.readBlockPos(entry), entry.getInt("Tick")));
        }
    }

    @Override
    public void updateMoveMode(Player player) {
        ServantMoveModes.cycle(this, player);
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
        setLives(0);
        hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    public boolean canRide(LivingEntity entity) {
        return false;
    }

    @Override
    public float getStandingEyeHeight(net.minecraft.world.entity.Pose pose,
            net.minecraft.world.entity.EntityDimensions dimensions) {
        return 2.8F * (hasBerserkTexture() ? BERSERK_SIZE_SCALE : 1.0F);
    }

    @Override
    public net.minecraft.world.entity.EntityDimensions getDimensions(net.minecraft.world.entity.Pose pose) {
        net.minecraft.world.entity.EntityDimensions base = super.getDimensions(pose);
        return hasBerserkTexture() ? base.scale(BERSERK_SIZE_SCALE) : base;
    }

    @Override
    public net.minecraft.world.phys.AABB getBoundingBoxForCulling() {
        return getBoundingBox().inflate(1.0D);
    }

    @Override
    public float getPickRadius() {
        return 0.4F;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) {
            return true;
        }
        LivingEntity owner = getTrueOwner();
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) {
            return true;
        }
        if (entity instanceof com.Polarice3.Goety.api.entities.IOwned other
                && getOwnerId() != null && getOwnerId().equals(other.getOwnerId())) {
            return true;
        }
        if (entity instanceof net.minecraft.world.entity.OwnableEntity ownable && owner != null
                && ownable.getOwner() == owner) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void tick() {
        super.tick();
        if (getAnimation() == THROW_ANIMATION) {
            fallDistance = 0.0F;
        }
        updateAnimation();
        updateCommand();
        updateGuardPosition();
        if (!level().isClientSide()) {
            processSeismicWaves();
            updateResurrections();
            updateBerserkState();
            updateRoarGuarantee();
        } else {
            updateThrowClientTicks();
        }
        if (isStaying() && getTarget() == null) {
            getNavigation().stop();
        }
        ServantTeleportHelper.tick(this);
        if (getHealth() > 0.0F) {
            deathTime = 0;
            vanishTime = 0;
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        if (getAnimation() == THROW_ANIMATION || throwFinishTimer >= 0) {
            fallDistance = 0.0F;
            return false;
        }
        return super.causeFallDamage(distance, multiplier, source);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity target = getTarget();
        if (!isAnimationPlaying() && target != null
                && Math.abs(getY() - target.getY()) <= 1.0D
                && distanceToSqr(target) <= 49.0D
                && random.nextInt(20) == 0) {
            animationTarget = target;
            startAnimation(SLAM_GROUND_ANIMATION);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (getAnimation() == THROW_ANIMATION && !hasThrowAttackHit()
                && source.getEntity() != null && source.getEntity() == getTarget()) {
            return false;
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (soulJarUpgrade && getAnimation() == ROAR_ANIMATION) {
                amount *= MutantZombieServantConfig.SOUL_JAR_DAMAGE_MULTIPLIER.get();
            }
            if (isBerserk()) {
                amount *= MutantZombieServantConfig.BERSERK_DAMAGE_MULTIPLIER.get();
            }
        }
        return super.hurt(source, amount);
    }

    private void updateBerserkState() {
        if (berserkTicks <= 0) {
            syncBerserkState();
            return;
        }
        syncBerserkState();
        if (--berserkTicks == 0) {
            syncBerserkState();
            refreshUpgradeAttributes();
            setHealth(Math.min(getHealth(), getMaxHealth()));
        } else {
            refreshBerserkEffects();
        }
    }

    private void syncBerserkState() {
        if (!entityData.hasItem(BERSERK_ACTIVE)) {
            return;
        }
        boolean active = berserkTicks > 0;
        if (entityData.get(BERSERK_ACTIVE) != active) {
            entityData.set(BERSERK_ACTIVE, active);
            refreshDimensions();
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (BERSERK_ACTIVE.equals(key)) {
            refreshDimensions();
        }
    }

    public static float berserkSizeScale() {
        return BERSERK_SIZE_SCALE;
    }

    private void refreshBerserkEffects() {
        if (!isBerserk() || !isAlive()) {
            return;
        }
        addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, berserkTicks, 0, false, true, true));
        addEffect(new MobEffectInstance(GoetyEffects.RALLYING.get(), berserkTicks, 0, false, true, true));
    }

    public boolean isBerserk() {
        return this.level().isClientSide()
                ? this.entityData.get(BERSERK_ACTIVE)
                : this.berserkTicks > 0;
    }

    public boolean hasBerserkBoon() {
        return berserkBoon;
    }

    public boolean hasBerserkTexture() {
        return level().isClientSide() && entityData.hasItem(BERSERK_ACTIVE)
                ? entityData.get(BERSERK_ACTIVE) : isBerserk();
    }

    public boolean hasHowlingSoulUpgrade() {
        return howlingSoulUpgrade;
    }

    public boolean hasSoulJarUpgrade() {
        return soulJarUpgrade;
    }

    private void applyAttackEffects(LivingEntity target) {
        if (!howlingSoulUpgrade || target == null || !target.isAlive()) {
            return;
        }
        int duration = MutantZombieServantConfig.HOWLING_SOUL_EFFECT_SECONDS.get() * 20;
        target.addEffect(new MobEffectInstance(GoetyEffects.CURSED.get(), duration, 0), this);
        target.addEffect(new MobEffectInstance(GoetyEffects.DOOM.get(), duration, 0), this);
    }

    private void applyRoarEffects(LivingEntity entity) {
        if (!soulJarUpgrade || entity == null || !entity.isAlive()) {
            return;
        }
        entity.addEffect(new MobEffectInstance(GoetyEffects.NECROSIS.get(), (MutantZombieServantConfig.SOUL_JAR_BUFF_SECONDS.get() * 20), 0,
                false, true, true), this);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, (MutantZombieServantConfig.SOUL_JAR_BUFF_SECONDS.get() * 20), 0,
                false, false, true), this);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (MutantZombieServantConfig.SOUL_JAR_BUFF_SECONDS.get() * 20), 0,
                false, false, true), this);
    }

    private boolean isZombieServant(Entity entity) {
        return entity instanceof LivingEntity living && living.isAlive() && entity instanceof IServant
                && (entity instanceof MutantZombieServant || living.getType().is(ZOMBIE_SERVANT_TAG))
                && distanceToSqr(entity) <= 16.0D * 16.0D
                && ServantFaction.isSameFaction(this, entity);
    }

    public int getLives() {
        return entityData.hasItem(LIVES) ? entityData.get(LIVES) : 0;
    }

    public void setLives(int lives) {
        if (entityData.hasItem(LIVES)) {
            entityData.set(LIVES, Math.max(0, lives));
        }
    }

    public int getVanishTime() {
        return vanishTime;
    }

    @Override
    public void die(DamageSource source) {
        if (level().isClientSide()) {
            return;
        }
        if (MutantZombieServantConfig.RESURRECTION_LIVES.get() <= 0) {
            super.die(source);
            return;
        }
        if (isOnFire()) {
            setLives(0);
        }
        deathCause = source;
        goalSelector.getRunningGoals().forEach(Goal::stop);
        setLastHurtMob(getLastHurtByMob());
        level().broadcastEntityEvent(this, (byte) 3);
        if (lastHurtByPlayerTime > 0) {
            lastHurtByPlayerTime += MAX_DEATH_TIME;
        }
    }

    @Override
    public void setSecondsOnFire(int seconds) {
        super.setSecondsOnFire(seconds);
        if (level() instanceof ServerLevel && isDeadOrDying()) {
            setLives(0);
        }
    }

    @Override
    public void tickDeath() {
        if (getLives() > 0) {
            if (++deathTime >= MAX_DEATH_TIME) {
                deathTime = 0;
                vanishTime = 0;
                deathCause = null;
                setHealth(getMaxHealth() / RESURRECTION_HEALTH_DIVISOR);
                setLives(getLives() - 1);
                if (getLastHurtMob() != null) {
                    setLastHurtByMob(getLastHurtMob());
                }
            }
        } else {
            if (deathTime > 100) {
                --deathTime;
            } else if (deathTime < 100) {
                ++deathTime;
            }
            if (++vanishTime >= MAX_VANISH_TIME) {
                DamageSource killedBy = deathCause;
                super.die(killedBy != null ? killedBy : level().damageSources().generic());
                super.tickDeath();
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 60) {
            for (int i = 0; i < 30; ++i) {
                level().addParticle(isOnFire() ? ParticleTypes.FLAME : ParticleTypes.POOF,
                        getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D),
                        random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D,
                        random.nextGaussian() * 0.02D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void kill() {
        super.kill();
        setLives(0);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        net.minecraft.world.item.Item hammer =
                BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mutantmonsters", "hulk_hammer"));
        if (hammer == null || hammer == net.minecraft.world.item.Items.AIR) {
            return;
        }
        OwnerBoundLoot.deliver(this, new ItemStack(hammer));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        ItemStack before = held.copy();
        InteractionResult result = burnFallenServant(player, hand);
        if (result.consumesAction()) {
            if (!level().isClientSide()) {
                ItemStack current = player.getItemInHand(hand);
                if (player.getAbilities().instabuild && current == held
                        && current.getCount() < before.getCount()) {
                    current.setCount(before.getCount());
                }
                if (current.isEmpty() && !player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }
                gameEvent(GameEvent.ENTITY_INTERACT);
            }
            return result;
        }
        if (isAlive() && player == getTrueOwner() && applyUpgrade(player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (isAlive() && player == getTrueOwner()
                && held.is(net.minecraft.world.item.Items.ROTTEN_FLESH)
                && getHealth() < getMaxHealth()
                && MutantZombieServantConfig.ROTTEN_FLESH_HEAL.get() > 0.0D) {
            if (!level().isClientSide()) {
                heal((float) (double) MutantZombieServantConfig.ROTTEN_FLESH_HEAL.get());
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            getX(), getY(1.6D), getZ(), 7, 0.3D, 0.4D, 0.3D, 0.0D);
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (player == getTrueOwner() && equipArmor(player, hand, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (player == getTrueOwner() && equipWeapon(player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private boolean equipArmor(Player player, InteractionHand hand, ItemStack held) {
        EquipmentSlot slot = armorSlotFor(held);
        if (slot == null || !isAlive()) {
            return false;
        }
        if (level().isClientSide()) {
            return true;
        }

        ItemStack previous = getItemBySlot(slot).copy();
        float previousDropChance = getEquipmentDropChance(slot);
        ItemStack equipped = held.copyWithCount(1);
        setItemSlot(slot, equipped);
        setGuaranteedDrop(slot);
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
        RingServantGear.returnReplaced(player, previous, previousDropChance);

        Equipable equipable = Equipable.get(equipped);
        playSound(equipable == null ? SoundEvents.ARMOR_EQUIP_GENERIC : equipable.getEquipSound(),
                1.0F, 1.0F);
        return true;
    }

    private static EquipmentSlot armorSlotFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Equipable equipable = Equipable.get(stack);
        if (equipable == null) {
            return null;
        }
        EquipmentSlot slot = equipable.getEquipmentSlot();
        return slot.getType() == EquipmentSlot.Type.ARMOR ? slot : null;
    }

    private boolean equipWeapon(Player player, ItemStack held) {
        if (!isAlive() || !isMeleeWeapon(held)) {
            return false;
        }
        EquipmentSlot slot = player.isShiftKeyDown() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
        if (level().isClientSide()) {
            return true;
        }
        ItemStack previous = getItemBySlot(slot).copy();
        float previousDropChance = getEquipmentDropChance(slot);
        setItemSlot(slot, held.copyWithCount(1));
        setGuaranteedDrop(slot);
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
        RingServantGear.returnReplaced(player, previous, previousDropChance);
        Equipable equipable = Equipable.get(held);
        playSound(equipable == null ? SoundEvents.ARMOR_EQUIP_GENERIC : equipable.getEquipSound(),
                1.0F, 1.0F);
        return true;
    }

    private static boolean isMeleeWeapon(ItemStack stack) {
        if (stack.isEmpty() || GoetyStaffGuard.isGoetyStaff(stack)
                || JerotesRangedWeaponGoal.isRangedWeapon(stack)) {
            return false;
        }
        return stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)
                || !stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(Attributes.ATTACK_DAMAGE).isEmpty();
    }

    private double weaponAttackBonus(EquipmentSlot slot) {
        ItemStack stack = getItemBySlot(slot);
        if (stack.isEmpty()) {
            return 0.0D;
        }
        double bonus = 0.0D;
        for (AttributeModifier modifier : stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                bonus += modifier.getAmount();
            }
        }
        return bonus;
    }

    private double mainHandWeaponBonus() {
        return MutantZombieServantConfig.MAINHAND_WEAPON_DAMAGE_ENABLED.get()
                ? weaponAttackBonus(EquipmentSlot.MAINHAND) : 0.0D;
    }

    private double offHandWeaponBonus() {
        return MutantZombieServantConfig.OFFHAND_WEAPON_SHOCKWAVE_ENABLED.get()
                ? weaponAttackBonus(EquipmentSlot.OFFHAND) : 0.0D;
    }

    private boolean weaponEnchantEnabled(EquipmentSlot slot) {
        return slot == EquipmentSlot.OFFHAND
                ? MutantZombieServantConfig.OFFHAND_WEAPON_ENCHANTMENT_ENABLED.get()
                : MutantZombieServantConfig.MAINHAND_WEAPON_ENCHANTMENT_ENABLED.get();
    }

    private int weaponEnchantLevel(EquipmentSlot slot, Enchantment enchantment) {
        if (!weaponEnchantEnabled(slot)) {
            return 0;
        }
        ItemStack stack = getItemBySlot(slot);
        return stack.isEmpty() ? 0 : EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    private double weaponEnchantDamageBonus(EquipmentSlot slot, LivingEntity target) {
        if (!weaponEnchantEnabled(slot) || target == null) {
            return 0.0D;
        }
        ItemStack stack = getItemBySlot(slot);
        return stack.isEmpty() ? 0.0D : EnchantmentHelper.getDamageBonus(stack, target.getMobType());
    }

    private void applyWeaponOnHitEnchantments(EquipmentSlot slot, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        int fireAspect = weaponEnchantLevel(slot, Enchantments.FIRE_ASPECT);
        if (fireAspect > 0) {
            target.setSecondsOnFire(fireAspect * 4);
        }
    }

    private double weaponEnchantKnockbackBonus(EquipmentSlot slot) {
        return weaponEnchantLevel(slot, Enchantments.KNOCKBACK) * 0.5D;
    }

    private boolean applyUpgrade(Player player, ItemStack stack) {
        if (stack.isEmpty() || !isAlive()) {
            return false;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId == null) {
            return false;
        }
        if (MutantZombieServantConfig.HOWLING_SOUL_ENABLED.get() && !howlingSoulUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "howling_soul").equals(itemId)) {
            if (level().isClientSide()) {
                return true;
            }
            howlingSoulUpgrade = true;
            float oldHealth = getHealth();
            refreshUpgradeAttributes();
            setHealth(Math.min(getMaxHealth(), oldHealth + MutantZombieServantConfig.HOWLING_SOUL_HEALTH.get().floatValue()));
            consumeUpgradeItem(player, stack);
            playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.7F);
            spawnUpgradeParticles(ParticleTypes.SOUL);
            return true;
        }
        if (MutantZombieServantConfig.SOUL_JAR_ENABLED.get() && !soulJarUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "soul_jar").equals(itemId)
                && !SoulJar.isCairn(stack) && !SoulJar.isMossy(stack)
                && !SoulJar.isDrowned(stack) && !SoulJar.isWither(stack)) {
            if (level().isClientSide()) {
                return true;
            }
            soulJarUpgrade = true;
            float oldHealth = getHealth();
            refreshUpgradeAttributes();
            setHealth(Math.min(getMaxHealth(), oldHealth + MutantZombieServantConfig.SOUL_JAR_HEALTH.get().floatValue()));
            consumeUpgradeItem(player, stack);
            playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.8F);
            spawnUpgradeParticles(ParticleTypes.SOUL);
            return true;
        }
        if (MutantZombieServantConfig.BERSERK_ENABLED.get() && ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "berserk_essence").equals(itemId)) {
            if (level().isClientSide()) {
                return true;
            }
            boolean wasBerserk = isBerserk();
            berserkTicks = (MutantZombieServantConfig.BERSERK_DURATION_SECONDS.get() * 20);
            berserkBoon = true;
            syncBerserkState();
            float oldHealth = getHealth();
            refreshUpgradeAttributes();
            setHealth(Math.min(getMaxHealth(), oldHealth + (wasBerserk ? 0.0F : MutantZombieServantConfig.BERSERK_HEALTH.get().floatValue())));
            refreshBerserkEffects();
            consumeUpgradeItem(player, stack);
            setLives((int) Math.min(Integer.MAX_VALUE, (long) getLives() + MutantZombieServantConfig.BERSERK_EXTRA_LIVES.get()));
            if (MutantZombieServantConfig.BERSERK_IMMEDIATE_ROAR.get()) startImmediateRoar();
            playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 0.7F);
            spawnUpgradeParticles(ParticleTypes.ANGRY_VILLAGER);
            return true;
        }
        return false;
    }

    private static void consumeUpgradeItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private void spawnUpgradeParticles(net.minecraft.core.particles.SimpleParticleType particle) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particle, getX(), getY() + getBbHeight() * 0.55D, getZ(),
                    18, getBbWidth() * 0.55D, getBbHeight() * 0.45D, getBbWidth() * 0.55D, 0.03D);
        }
    }

    private InteractionResult burnFallenServant(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(ItemTags.CREEPER_IGNITERS) || isAlive() || isOnFire() || isInWaterOrRain()) {
            return InteractionResult.PASS;
        }
        level().playSound(player, getX(), getY(), getZ(), SoundEvents.FLINTANDSTEEL_USE,
                getSoundSource(), 1.0F, random.nextFloat() * 0.4F + 0.8F);
        if (!level().isClientSide()) {
            setSecondsOnFire(8);
            if (!stack.isDamageableItem()) {
                stack.shrink(1);
            } else {
                stack.hurtAndBreak(1, player, target -> target.broadcastBreakEvent(hand));
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!(target instanceof LivingEntity living) || !canAttack(living) || isAnimationPlaying()) {
            return false;
        }

        animationTarget = living;
        setLastHurtMob(living);
        boolean throwAttack = living.getVehicle() == this || random.nextInt(5) == 0;
        startAnimation(throwAttack ? THROW_ANIMATION : SLAM_GROUND_ANIMATION);
        return true;
    }

    public Animation getAnimation() {
        if (!entityData.hasItem(ANIMATION_ID)) {
            return Animation.NONE;
        }
        int id = entityData.get(ANIMATION_ID);
        return id >= 0 && id < ANIMATIONS.length ? ANIMATIONS[id] : Animation.NONE;
    }

    public void setAnimation(Animation animation) {
        if (!entityData.hasItem(ANIMATION_ID)) {
            return;
        }
        entityData.set(ANIMATION_ID, animationIndex(animation));
        if (animation == null || animation == Animation.NONE) {
            entityData.set(ANIMATION_TICK, 0);
        }
    }

    public int getAnimationTick() {
        return entityData.hasItem(ANIMATION_TICK) ? entityData.get(ANIMATION_TICK) : 0;
    }

    public void setAnimationTick(int tick) {
        if (entityData.hasItem(ANIMATION_TICK)) {
            entityData.set(ANIMATION_TICK, Math.max(0, tick));
        }
    }

    public Animation[] getAnimations() {
        return ANIMATIONS.clone();
    }

    public boolean isAnimationPlaying() {
        return getAnimation() != Animation.NONE;
    }

    void startAnimation(Animation animation) {
        setAnimation(animation);
        setAnimationTick(0);
        if (animation == SLAM_GROUND_ANIMATION) {
            ambientSoundTime = -getAmbientSoundInterval();
            playSound(mutantSound("entity.mutant_zombie.attack", SoundEvents.ZOMBIE_ATTACK_IRON_DOOR),
                    0.3F, 0.8F + random.nextFloat() * 0.4F);
        } else if (animation == THROW_ANIMATION) {
            throwFinishTimer = -1;
            setThrowFlag(THROW_HIT_BIT, false);
            setThrowFlag(THROW_FINISHED_BIT, false);
            if (animationTarget != null) {
                animationTarget.stopRiding();
                double x = animationTarget.getX() - getX();
                double z = animationTarget.getZ() - getZ();
                double length = Math.sqrt(x * x + z * z);
                if (length < 1.0E-4D) {
                    length = 1.0D;
                }
                animationTarget.setDeltaMovement(x / length * 0.8D, 1.6D, z / length * 0.8D);
                EntityUtil.sendPlayerVelocityPacket(animationTarget);
            }
        } else if (animation == ROAR_ANIMATION) {
            ambientSoundTime = -getAmbientSoundInterval();
            invulnerableTime = 20;
        }
    }

    private void updateAnimation() {
        Animation animation = getAnimation();
        if (animation == Animation.NONE) {
            return;
        }

        int currentTick = getAnimationTick();
        if (!level().isClientSide()) {
            if (animation == SLAM_GROUND_ANIMATION && currentTick == 12) {
                performSlamImpact();
            } else if (animation == THROW_ANIMATION) {
                updateThrowLunge(currentTick, animation.duration());
            } else if (animation == ROAR_ANIMATION) {
                if (currentTick == 10) {
                    performRoarShockwave();
                }
                if (currentTick >= 20 && currentTick < 80 && currentTick % 10 == 0) {
                    scheduleRoarResurrection();
                }
            }
        }

        int nextTick = currentTick + 1;
        boolean finished;
        if (animation == THROW_ANIMATION) {
            finished = throwFinishTimer >= THROW_LANDING_TICKS
                    || nextTick >= animation.duration() + THROW_MAX_EXTRA_TICKS;
        } else {
            finished = nextTick >= animation.duration();
        }
        if (finished) {
            setAnimation(Animation.NONE);
            animationTarget = null;
            throwFinishTimer = -1;
            setThrowFlag(THROW_HIT_BIT, false);
            setThrowFlag(THROW_FINISHED_BIT, false);
        } else {
            setAnimationTick(nextTick);
        }
    }

    private void updateThrowClientTicks() {
        if (getAnimation() == THROW_ANIMATION) {
            if (hasThrowAttackHit()) {
                if (throwHitTick == -1) {
                    throwHitTick = 0;
                }
                throwHitTick++;
            }
            if (isThrowAttackFinished()) {
                if (throwFinishTick == -1) {
                    throwFinishTick = 0;
                }
                throwFinishTick++;
            }
        } else {
            throwHitTick = -1;
            throwFinishTick = -1;
        }
    }

    private void updateThrowLunge(int currentTick, int duration) {
        LivingEntity target = animationTarget;
        if (target != null && target.isAlive()) {
            getNavigation().stop();
            getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
        if (currentTick == duration) {
            this.stuckSpeedMultiplier = Vec3.ZERO;
            double x;
            double y;
            double z;
            if (target != null) {
                x = target.getX() - getX();
                y = target.getY() - getY();
                z = target.getZ() - getZ();
            } else {
                x = -Mth.sin(getYRot() * ((float) Math.PI / 180F)) * 3.0D;
                y = 1.0D;
                z = Mth.cos(getYRot() * ((float) Math.PI / 180F)) * 3.0D;
            }
            double length = Math.sqrt(x * x + y * y + z * z);
            if (length < 1.0E-4D) {
                length = 1.0D;
            }
            setDeltaMovement(x / length * 3.4D, y / length * 1.4D, z / length * 3.4D);
        } else if (currentTick > duration && target != null && canSpecialAttack(target)) {
            double reach = getBbWidth() * 2.0D;
            double distance = distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
            if (distance < reach * reach || getBoundingBox().intersects(target.getBoundingBox())) {
                if (!hasThrowAttackHit()) {
                    setThrowFlag(THROW_HIT_BIT, true);
                    double pounceDamage = getAttributeValue(Attributes.ATTACK_DAMAGE) + mainHandWeaponBonus()
                            + weaponEnchantDamageBonus(EquipmentSlot.MAINHAND, target);
                    if (target.hurt(ModDamageSource.summonAttack(this, getTrueOwner()),
                            (float) pounceDamage)) {
                        applyAttackEffects(target);
                        applyWeaponOnHitEnchantments(EquipmentSlot.MAINHAND, target);
                    } else {
                        EntityUtil.disableShield(target, 150);
                    }
                    double x = target.getX() - getX();
                    double z = target.getZ() - getZ();
                    double length = Math.sqrt(x * x + z * z);
                    if (length < 1.0E-4D) {
                        length = 1.0D;
                    }
                    double push = 0.6D + weaponEnchantKnockbackBonus(EquipmentSlot.MAINHAND);
                    target.setDeltaMovement(x / length * push, -1.2D, z / length * push);
                    target.invulnerableTime = 10;
                    target.hurtMarked = true;
                    EntityUtil.sendPlayerVelocityPacket(target);
                    EntityUtil.stunRavager(target);
                    playSound(mutantSound("entity.mutant_zombie.grunt", SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR),
                            0.3F, 0.8F + random.nextFloat() * 0.4F);
                }
            }
        }
        if (currentTick > duration && throwFinishTimer < 0 && isOnGroundLike() && !isThrowAttackFinished()) {
            throwFinishTimer = 0;
            setThrowFlag(THROW_FINISHED_BIT, true);
            Vec3 landing = getDeltaMovement();
            setDeltaMovement(0.0D, Math.min(landing.y, 0.0D), 0.0D);
        }
        if (throwFinishTimer >= 0) {
            throwFinishTimer++;
        }
    }

    private boolean isOnGroundLike() {
        return onGround() || !getFeetBlockState().getFluidState().isEmpty();
    }

    private void performSlamImpact() {
        double x;
        double z;
        LivingEntity target = animationTarget != null ? animationTarget : getTarget();
        if (target != null) {
            x = target.getX() - getX();
            z = target.getZ() - getZ();
        } else {
            x = -Mth.sin(getYRot() * ((float) Math.PI / 180F));
            z = Mth.cos(getYRot() * ((float) Math.PI / 180F));
        }
        double length = Math.sqrt(x * x + z * z);
        if (length < 1.0E-4D) {
            length = 1.0D;
        }
        x /= length;
        z /= length;
        int startX = Mth.floor(getX() + x * 2.0D);
        int startZ = Mth.floor(getZ() + z * 2.0D);
        double range = isBerserk() ? MutantZombieServantConfig.BERSERK_SLAM_RANGE.get() : 8.0D;
        int endX = Mth.floor(getX() + x * range);
        int endZ = Mth.floor(getZ() + z * range);
        int y = Mth.floor(getBoundingBox().minY);
        applySlamImpactArea(startX, startZ, y);
        SeismicWave.createWaves(level(), seismicWaveList, startX, startZ, endX, endZ, y);
        playSound(SoundEvents.GENERIC_EXPLODE, 0.5F, 0.8F + random.nextFloat() * 0.4F);
        if (level() instanceof ServerLevel serverLevel) {
            MutantZombieSlamFx.impactBurst(serverLevel, startX + 0.5D, y + 0.1D, startZ + 0.5D,
                    MutantZombieSlamArea.RADIUS);
        }
    }

    private void processSeismicWaves() {
        if (seismicWaveList.isEmpty()) {
            return;
        }
        SeismicWave wave = seismicWaveList.remove(0);
        wave.affectBlocks(level(), this);
        AABB box = new AABB(wave.getX(), wave.getY() + 1.0D, wave.getZ(),
                wave.getX() + 1.0D, wave.getY() + 2.0D, wave.getZ() + 1.0D);
        if (wave.isFirst()) {
            double addScale = random.nextDouble() * 0.75D;
            box = box.inflate(0.25D + addScale, 0.25D + addScale * 0.5D, 0.25D + addScale);
        }
        DamageSource damageSource = ModDamageSource.summonAttack(this, getTrueOwner());
        for (Entity entity : level().getEntities(this, box, EntitySelector.NO_SPECTATORS.and(this::canSpecialAttack))) {
            if (entity instanceof LivingEntity living) {
                slamHit(living, wave.isFirst(), damageSource);
            }
            pushFromCenter(entity, 0.3D + weaponEnchantKnockbackBonus(EquipmentSlot.OFFHAND), 0.04D);
        }
    }

    private void slamHit(LivingEntity living, boolean first, DamageSource damageSource) {
        double damage = first
                ? MutantZombieServantConfig.randomBetween(MutantZombieServantConfig.SLAM_GROUND_DAMAGE_FIRST_MIN,
                        MutantZombieServantConfig.SLAM_GROUND_DAMAGE_FIRST_MAX, random)
                : MutantZombieServantConfig.randomBetween(MutantZombieServantConfig.SLAM_GROUND_DAMAGE_WAVE_MIN,
                        MutantZombieServantConfig.SLAM_GROUND_DAMAGE_WAVE_MAX, random);
        damage += offHandWeaponBonus() + weaponEnchantDamageBonus(EquipmentSlot.OFFHAND, living);
        if (isBerserk()) {
            damage *= MutantZombieServantConfig.BERSERK_SLAM_MULTIPLIER.get();
        }
        if (living.hurt(damageSource, (float) damage)) {
            applyAttackEffects(living);
            applyWeaponOnHitEnchantments(EquipmentSlot.OFFHAND, living);
            if (random.nextInt(5) == 0) {
                MobEffect hunger = getTrueOwner() != null && CuriosFinder.hasWildSet(getTrueOwner())
                        ? GeneratedMod.FAMISHED.get() : MobEffects.HUNGER;
                living.addEffect(new MobEffectInstance(hunger, 160, 1));
            }
        }
    }

    private void applySlamImpactArea(int startX, int startZ, int y) {
        double centerX = startX + 0.5D;
        double centerZ = startZ + 0.5D;
        DamageSource damageSource = ModDamageSource.summonAttack(this, getTrueOwner());
        for (LivingEntity victim : MutantZombieSlamArea.victims(this, centerX, centerZ, y,
                this::canSpecialAttack)) {
            slamHit(victim, true, damageSource);
            MutantZombieSlamArea.pushAway(victim, getX(), getZ(),
                    0.3D + weaponEnchantKnockbackBonus(EquipmentSlot.OFFHAND), 0.04D);
        }
    }

    private void updateRoarGuarantee() {
        LivingEntity target = getTarget();
        if (target != roarGuardTarget) {
            roarGuardTarget = target;
            roarGuaranteeSpent = false;
            guaranteedRoarTick = -1;
        }
        if (target == null || roarGuaranteeSpent || guaranteedRoarTick >= 0
                || !MutantZombieServantConfig.FIRST_ROAR_GUARANTEE.get()
                || tickCount < nextRoarSummonTick) {
            return;
        }
        guaranteedRoarTick = tickCount + MutantZombieServantConfig.firstRoarDelayTicks(random);
    }

    boolean isGuaranteedRoarDue() {
        LivingEntity target = getTarget();
        return guaranteedRoarTick >= 0 && tickCount >= guaranteedRoarTick
                && MutantZombieServantConfig.FIRST_ROAR_GUARANTEE.get()
                && target != null && target.isAlive()
                && !isAnimationPlaying() && resurrectionList.isEmpty();
    }

    void fulfillGuaranteedRoar() {
        if (guaranteedRoarTick >= 0) {
            roarGuaranteeSpent = true;
            guaranteedRoarTick = -1;
        }
    }

    private void startImmediateRoar() {
        if (level().isClientSide()) {
            return;
        }
        animationTarget = getTarget();
        roarSummonScheduled = 0;
        fulfillGuaranteedRoar();
        nextRoarSummonTick = (long) tickCount
                + MutantZombieServantConfig.SUMMON_SERVANT_COOLDOWN_SECONDS.get() * 20L;
        startAnimation(ROAR_ANIMATION);
    }

    private void performRoarShockwave() {
        if (soulJarUpgrade) {
            applyRoarEffects(this);
            for (Entity entity : level().getEntities(this, getBoundingBox().inflate(16.0D), this::isZombieServant)) {
                applyRoarEffects((LivingEntity) entity);
            }
        }
        ambientSoundTime = -getAmbientSoundInterval();
        playSound(mutantSound("entity.mutant_zombie.roar", SoundEvents.ZOMBIE_AMBIENT),
                3.0F, 0.7F + random.nextFloat() * 0.2F);
        DamageSource damageSource = ModDamageSource.summonAttack(this, getTrueOwner());
        for (Entity entity : level().getEntities(this, getBoundingBox().inflate(12.0D, 8.0D, 12.0D),
                EntitySelector.NO_SPECTATORS.and(this::canSpecialAttack))) {
            if (distanceToSqr(entity) > 196.0D) {
                continue;
            }
            double x = entity.getX() - getX();
            double z = entity.getZ() - getZ();
            double length = Math.sqrt(x * x + z * z);
            if (length < 1.0E-4D) {
                length = 1.0D;
            }
            entity.setDeltaMovement(x / length * 0.7D, 0.3D, z / length * 0.7D);
            entity.hurtMarked = true;
            if (entity instanceof LivingEntity living) {
                float roarDamage = 2.0F + random.nextInt(2)
                        + (float) (offHandWeaponBonus() * ROAR_OFFHAND_BONUS_SCALE)
                        + (howlingSoulUpgrade ? MutantZombieServantConfig.HOWLING_SOUL_ROAR_DAMAGE.get().floatValue() : 0.0F);
                if (living.hurt(damageSource, roarDamage)) {
                    applyAttackEffects(living);
                    if (howlingSoulUpgrade) {
                        living.addEffect(new MobEffectInstance(GoetyEffects.STUNNED.get(), MutantZombieServantConfig.HOWLING_SOUL_STUN_TICKS.get(), 0), this);
                        living.addEffect(new MobEffectInstance(GoetyEffects.WANE.get(), MutantZombieServantConfig.HOWLING_SOUL_WANE_TICKS.get(), 0), this);
                    }
                }
            }
            EntityUtil.sendPlayerVelocityPacket(entity);
        }
    }

    private void scheduleRoarResurrection() {
        if (roarSummonScheduled >= MutantZombieServantConfig.SUMMON_SERVANT_COUNT.get()) {
            return;
        }
        int x = Mth.floor(getX());
        int z = Mth.floor(getZ());
        x += (1 + random.nextInt(8)) * (random.nextBoolean() ? 1 : -1);
        z += (1 + random.nextInt(8)) * (random.nextBoolean() ? 1 : -1);
        int y = ZombieResurrection.getSuitableGround(level(), x, Mth.floor(getBoundingBox().minY) - 1, z);
        if (y != -1) {
            roarSummonScheduled++;
            resurrectionList.add(new ServantResurrection(new BlockPos(x, y, z), 40 + random.nextInt(20)));
        }
    }

    private void updateResurrections() {
        if (resurrectionsComplete()) {
            return;
        }
        Level level = level();
        for (int i = resurrectionList.size() - 1; i >= 0; i--) {
            ServantResurrection resurrection = resurrectionList.get(i);
            BlockPos above = resurrection.pos.above();
            if (level.isEmptyBlock(resurrection.pos) || !level.isEmptyBlock(above)) {
                resurrectionList.remove(i);
                continue;
            }
            if (random.nextInt(15) == 0 && level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,
                        level.getBlockState(resurrection.pos)),
                        above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D,
                        3, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            if (--resurrection.tick > 0) {
                continue;
            }
            spawnRoarReinforcement(above, resurrection.pos);
            resurrectionList.remove(i);
        }
    }

    private boolean resurrectionsComplete() {
        return level().isClientSide() || resurrectionList.isEmpty();
    }

    private void spawnRoarReinforcement(BlockPos above, BlockPos ground) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!MutantZombieServantConfig.SUMMON_ZOMBIE_SERVANTS.get()) {
            return;
        }
        LivingEntity owner = getTrueOwner();
        if (owner == null) {
            return;
        }
        if (countZombieServantsForOwner() >= MutantZombieServantConfig.SUMMON_SERVANT_CAP.get()) {
            return;
        }
        Mob reinforcement = hasBerserkBoon()
                ? createBerserkReinforcement(serverLevel)
                : ZombieServantVariants.createFor(serverLevel, above);
        if (reinforcement == null) {
            return;
        }
        reinforcement.moveTo(above.getX() + 0.5D, above.getY(), above.getZ() + 0.5D, getYRot(), 0.0F);
        if (reinforcement instanceof IOwned owned) {
            owned.setTrueOwner(this);
            owned.setNatural(false);
            owned.setHostile(false);
        }
        if (reinforcement instanceof IServant servantMode) {
            servantMode.setWandering(false);
            servantMode.setStaying(false);
        }
        if (reinforcement instanceof ICustomAttributes configurable) {
            configurable.setConfigurableAttributes();
        }
        reinforcement.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(above),
                MobSpawnType.MOB_SUMMONED, null, null);
        reinforcement.setHealth(reinforcement.getMaxHealth());
        applySummonedZombieKnockbackResistance(reinforcement);
        if (reinforcement instanceof IOwned owned) {
            owned.setLimitedLife(summonLifespanTicks());
        }
        reinforcement.setPersistenceRequired();
        reinforcement.setTarget(getTarget());
        applyRoarEffects(reinforcement);
        reinforcement.playAmbientSound();
        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(ground)),
                above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D, 12, 0.1D, 0.05D, 0.1D, 0.1D);
        serverLevel.sendParticles(ParticleTypes.POOF,
                above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D, 8, 0.15D, 0.1D, 0.15D, 0.02D);
        serverLevel.addFreshEntity(reinforcement);
        GoetySummonFx.play(serverLevel, reinforcement);
    }

    private static Mob createBerserkReinforcement(ServerLevel serverLevel) {
        GoetyZombieServant servant = ModEntityTypes.GOETY_ZOMBIE_SERVANT.get().create(serverLevel);
        return servant;
    }

    private int summonLifespanTicks() {
        int lifespan = ServantLimits.SUMMONED_LIFESPAN_TICKS;
        if (soulJarUpgrade) {
            lifespan += SOUL_JAR_SERVANT_LIFE_BONUS;
        }
        if (berserkBoon) {
            lifespan += BERSERK_SERVANT_LIFE_BONUS;
        }
        return lifespan;
    }

    private static void applySummonedZombieKnockbackResistance(Mob reinforcement) {
        double resistance = MutantZombieServantConfig.SUMMON_SERVANT_KNOCKBACK_RESISTANCE.get();
        if (resistance == 0.0D) {
            return;
        }
        AttributeInstance knockback = reinforcement.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (knockback == null) {
            return;
        }
        if (knockback.getModifier(SUMMONED_KNOCKBACK_UUID) != null) {
            knockback.removeModifier(SUMMONED_KNOCKBACK_UUID);
        }
        knockback.addPermanentModifier(new AttributeModifier(SUMMONED_KNOCKBACK_UUID,
                "Mutant zombie roar summon knockback", resistance, AttributeModifier.Operation.ADDITION));
    }

    int countZombieServantsForOwner() {
        UUID ownerId = ServantFaction.ownerId(this);
        if (ownerId == null) {
            return 0;
        }
        AABB area = getBoundingBox().inflate(48.0D, 24.0D, 48.0D);
        int count = 0;
        for (Entity entity : level().getEntities(this, area)) {
            if (!(entity instanceof LivingEntity living) || !living.isAlive()) {
                continue;
            }
            boolean zombieServant = entity instanceof GoetyZombieServant
                    || living.getType().is(ZOMBIE_SERVANT_TAG);
            if (zombieServant && ownerId.equals(ServantFaction.ownerId(entity))) {
                count++;
            }
        }
        return count;
    }

    private boolean hasThrowAttackHit() {
        return getThrowFlag(THROW_HIT_BIT);
    }

    private boolean isThrowAttackFinished() {
        return getThrowFlag(THROW_FINISHED_BIT);
    }

    private boolean getThrowFlag(int flag) {
        return entityData.hasItem(THROW_ATTACK_STATE) && (entityData.get(THROW_ATTACK_STATE) & flag) != 0;
    }

    private void setThrowFlag(int flag, boolean value) {
        if (!entityData.hasItem(THROW_ATTACK_STATE)) {
            return;
        }
        byte state = entityData.get(THROW_ATTACK_STATE);
        entityData.set(THROW_ATTACK_STATE, value ? (byte) (state | flag) : (byte) (state & ~flag));
    }

    public int getThrowHitTick() {
        return throwHitTick;
    }

    public int getThrowFinishTick() {
        return throwFinishTick;
    }

    public List<ServantResurrection> getResurrectionList() {
        return resurrectionList;
    }

    private boolean canSpecialAttack(Entity entity) {
        return entity instanceof LivingEntity living && living.isAlive() && canAttack(living);
    }

    private void pushFromCenter(Entity entity, double horizontal, double vertical) {
        double x = entity.getX() - getX();
        double z = entity.getZ() - getZ();
        double length = Math.sqrt(x * x + z * z);
        if (length < 1.0E-4D) {
            length = 1.0D;
        }
        entity.setDeltaMovement(x / length * horizontal, vertical, z / length * horizontal);
        entity.hurtMarked = true;
    }

    private void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) {
            return;
        }
        if (getTarget().distanceToSqr(Vec3Helper.x(boundPos), Vec3Helper.y(boundPos), Vec3Helper.z(boundPos))
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) {
            setTarget(null);
        }
    }

    private static int animationIndex(Animation animation) {
        if (animation == SLAM_GROUND_ANIMATION) {
            return 0;
        }
        if (animation == THROW_ANIMATION) {
            return 1;
        }
        if (animation == ROAR_ANIMATION) {
            return 2;
        }
        return -1;
    }
}
