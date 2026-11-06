package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.EntityFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.MutantSnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.compat.GoetyFrostNovaHelper;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.block.MutantSnowGolemServantIceBlock;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantJackOLanternLayer;
import cn.blockforge.generated.goetysupreme.util.MutantThrowStandIns;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.data.SnowServantRoster;

public class MutantSnowGolemServant extends AbstractMutantServant implements IServant, Shearable,
        net.minecraft.world.entity.OwnableEntity, RangedAttackMob {

    public static final int THROW_DURATION_TICKS = 20;

    public static final int THROW_RELEASE_TICK = 7;

    private static final float THROW_TURN_DEGREES_PER_TICK = 12.0F;
    private static final int SUMMON_DURATION_TICKS = 15;

    private int summonTicks;
    private int summonCooldown;
    private final List<SnowGolemServant> pendingSummons = new ArrayList<>();

    private static final int THROWING_BIT = 1;

    private static final int JACK_O_LANTERN_BIT = 1;

    private static final EntityDataAccessor<Byte> THROWING_STATE =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> THROWING_TICK =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> THROW_YAW =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> PUMPKIN_STATE =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Boolean> FULLY_UPGRADED =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> SOUL_JAR_UPGRADED =
            SynchedEntityData.defineId(MutantSnowGolemServant.class, EntityDataSerializers.BOOLEAN);

    private boolean frostNovaUpgrade;
    private boolean soulJarUpgrade;
    private boolean chillingUpgrade;
    private static final UUID SOUL_JAR_HEALTH_UUID =
            UUID.fromString("c3d2aaf2-56cc-44ac-8d85-7ef1a951a40e");

    private static final UUID FULL_UPGRADE_ARMOR_UUID =
            UUID.fromString("0d4f6b21-7c38-4e59-a2b6-81c93d5e7f02");

    private static final float FULL_UPGRADE_ICE_DIRECT_BONUS = 2.0F;

    private static final float FULL_UPGRADE_ICE_SPLASH_BONUS = 2.0F;

    private LivingEntity throwTarget;

    public MutantSnowGolemServant(EntityType<? extends MutantSnowGolemServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MutantSnowGolemServantConfig.MAX_HEALTH.get())
                .add(Attributes.FOLLOW_RANGE, MutantSnowGolemServantConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, MutantSnowGolemServantConfig.MOVEMENT_SPEED.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, MutantSnowGolemServantConfig.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.ARMOR, MutantSnowGolemServantConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, MutantSnowGolemServantConfig.ARMOR_TOUGHNESS.get());
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return registerAttributes();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), MutantSnowGolemServantConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), MutantSnowGolemServantConfig.MOVEMENT_SPEED.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                MutantSnowGolemServantConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), MutantSnowGolemServantConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), MutantSnowGolemServantConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), MutantSnowGolemServantConfig.FOLLOW_RANGE.get());
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
    public void registerGoals() {
        super.registerGoals();

        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.removeAllGoals(goal -> true);

        this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new ServantHurtByTargetGoal(this));
        if (MutantSnowGolemServantConfig.ACTIVE_TARGETING.get()) {
            this.targetSelector.addGoal(3, new SummonTargetGoal(this));
        }

        this.goalSelector.addGoal(0, new SummoningGoal(this));
        this.goalSelector.addGoal(1, new IceThrowGoal());
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new CommandPositionGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new ServantWanderGoal(this, 1.0D, 0.001F));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SERVANT_FLAGS, (byte) 0);
        this.entityData.define(THROWING_STATE, (byte) 0);
        this.entityData.define(THROWING_TICK, 0);
        this.entityData.define(THROW_YAW, 0.0F);
        this.entityData.define(PUMPKIN_STATE, (byte) JACK_O_LANTERN_BIT);
        this.entityData.define(FULLY_UPGRADED, false);
        this.entityData.define(SOUL_JAR_UPGRADED, false);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return mutantSound("entity.mutant_snow_golem.hurt", SoundEvents.SNOW_GOLEM_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return mutantSound("entity.mutant_snow_golem.death", SoundEvents.SNOW_GOLEM_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        playSound(SoundEvents.SNOW_STEP, 0.15F, 1.0F);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader reader) {
        if (reader.getBiome(pos).is(net.minecraft.tags.BiomeTags.SNOW_GOLEM_MELTS)) {
            return -10.0F;
        }
        if (reader.getBlockState(pos).getBlock() == Blocks.SNOW) {
            return 10.0F;
        }
        return super.getWalkTargetValue(pos, reader);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
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
        tag.putBoolean("JackOLantern", hasJackOLantern());
        tag.putBoolean("FrostNovaUpgrade", frostNovaUpgrade);
        tag.putBoolean("SoulJarUpgrade", soulJarUpgrade);
        tag.putBoolean("ChillingUpgrade", chillingUpgrade);
        tag.putInt("SnowServantSummonCooldown", summonCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        } else if (tag.hasUUID("GoetyOwner")) {
            setOwnerId(tag.getUUID("GoetyOwner"));
        }
        if (tag.contains("wandering")) {
            setWandering(tag.getBoolean("wandering"));
        }
        if (tag.contains("staying")) {
            setStaying(tag.getBoolean("staying"));
        }
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
        if (tag.contains("Pumpkin") || tag.contains("JackOLantern")) {
            setJackOLantern(tag.getBoolean("Pumpkin") || tag.getBoolean("JackOLantern"));
        }
        frostNovaUpgrade = tag.getBoolean("FrostNovaUpgrade");
        soulJarUpgrade = tag.getBoolean("SoulJarUpgrade");
        chillingUpgrade = tag.getBoolean("ChillingUpgrade");
        summonCooldown = Mth.clamp(tag.getInt("SnowServantSummonCooldown"), 0, MutantSnowGolemServantConfig.SUMMON_COOLDOWN_TICKS.get());
        summonTicks = 0;
        pendingSummons.clear();
        refreshUpgradeEffects();
    }

    public boolean hasFullyUpgradedTexture() {
        return entityData.hasItem(FULLY_UPGRADED) && entityData.get(FULLY_UPGRADED);
    }

    public boolean hasSoulJarUpgrade() {
        return entityData.hasItem(SOUL_JAR_UPGRADED) && entityData.get(SOUL_JAR_UPGRADED);
    }

    public boolean isFullyUpgraded() {
        return frostNovaUpgrade && soulJarUpgrade && chillingUpgrade;
    }

    public float iceDirectDamageBonus() {
        return isFullyUpgraded() ? FULL_UPGRADE_ICE_DIRECT_BONUS : 0.0F;
    }

    public float iceSplashDamageBonus() {
        return isFullyUpgraded() ? FULL_UPGRADE_ICE_SPLASH_BONUS : 0.0F;
    }

    private void playUpgradeSound(SoundEvent normal, float pitch, boolean wasFullyUpgraded) {
        if (!wasFullyUpgraded && isFullyUpgraded()) {
            playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.0F);
            return;
        }
        if (normal != null) {
            playSound(normal, 1.0F, pitch);
        }
    }

    private void refreshUpgradeEffects() {
        if (!level().isClientSide()) {
            entityData.set(FULLY_UPGRADED, isFullyUpgraded());
            entityData.set(SOUL_JAR_UPGRADED, soulJarUpgrade);
        }
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double bonus = soulJarUpgrade ? MutantSnowGolemServantConfig.SOUL_JAR_HEALTH.get() : 0.0D;
            net.minecraft.world.entity.ai.attributes.AttributeModifier modifier =
                    maxHealth.getModifier(SOUL_JAR_HEALTH_UUID);
            if (modifier != null && modifier.getAmount() != bonus) {
                maxHealth.removePermanentModifier(SOUL_JAR_HEALTH_UUID);
            }
            if (bonus > 0.0D && maxHealth.getModifier(SOUL_JAR_HEALTH_UUID) == null) {
                maxHealth.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        SOUL_JAR_HEALTH_UUID, "Cairn soul jar", bonus,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
            }
        }
        AttributeInstance armor = getAttribute(Attributes.ARMOR);
        if (armor != null) {
            boolean fullyUpgraded = isFullyUpgraded();
            double armorBonus = fullyUpgraded ? MutantSnowGolemServantConfig.FULL_UPGRADE_ARMOR.get() : 0.0D;
            net.minecraft.world.entity.ai.attributes.AttributeModifier armorModifier =
                    armor.getModifier(FULL_UPGRADE_ARMOR_UUID);
            if (armorModifier != null && armorModifier.getAmount() != armorBonus) {
                armor.removePermanentModifier(FULL_UPGRADE_ARMOR_UUID);
            }
            if (armorBonus > 0.0D && armor.getModifier(FULL_UPGRADE_ARMOR_UUID) == null) {
                armor.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        FULL_UPGRADE_ARMOR_UUID, "Fully upgraded armor", armorBonus,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
            }
        }
        if (soulJarUpgrade && GoetyEffects.FROSTY_AURA != null) {
            addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    GoetyEffects.FROSTY_AURA.get(), 80, MutantSnowGolemServantConfig.FROSTY_AURA_LEVEL.get() - 1, false, false, true));
        }
        if (chillingUpgrade && GoetyEffects.CHILL_HIDE != null) {
            addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    GoetyEffects.CHILL_HIDE.get(), 80, MutantSnowGolemServantConfig.CHILL_HIDE_LEVEL.get() - 1, false, false, true));
        }
    }

    private boolean applyGoetyUpgrade(Player player, InteractionHand hand, ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId == null) {
            return false;
        }
        boolean wasFullyUpgraded = isFullyUpgraded();
        if (MutantSnowGolemServantConfig.FROST_NOVA_ENABLED.get() && !frostNovaUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "frost_nova_focus").equals(itemId)) {
            frostNovaUpgrade = true;
            refreshUpgradeEffects();
            consumeUpgradeItem(player, hand, stack);
            playUpgradeSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.25F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.SNOWFLAKE);
            return true;
        }
        if (MutantSnowGolemServantConfig.SOUL_JAR_ENABLED.get() && !soulJarUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "soul_jar").equals(itemId)
                && SoulJar.isCairn(stack)) {
            soulJarUpgrade = true;
            float oldHealth = getHealth();
            refreshUpgradeEffects();
            setHealth(Math.min(getMaxHealth(), oldHealth + MutantSnowGolemServantConfig.SOUL_JAR_HEALTH.get().floatValue()));
            consumeUpgradeItem(player, hand, stack);
            playUpgradeSound(SoundEvents.BEACON_ACTIVATE, 0.75F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.SNOWFLAKE);
            return true;
        }
        if (MutantSnowGolemServantConfig.CHILLING_ENABLED.get() && !chillingUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "chilling_focus").equals(itemId)) {
            chillingUpgrade = true;
            refreshUpgradeEffects();
            applyNearbyFrostServantChillHide();
            consumeUpgradeItem(player, hand, stack);
            playUpgradeSound(SoundEvents.GLASS_PLACE, 0.8F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.SNOWFLAKE);
            return true;
        }
        return false;
    }

    private void consumeUpgradeItem(Player player, InteractionHand hand, ItemStack stack) {
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

    private static final TagKey<net.minecraft.world.entity.EntityType<?>> FROST_SERVANT_TAG =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "frost_heal"));

    private void applyNearbyFrostServantChillHide() {
        if (!(level() instanceof ServerLevel serverLevel) || GoetyEffects.CHILL_HIDE == null) {
            return;
        }
        AABB area = getBoundingBox().inflate(MutantSnowGolemServantConfig.ALLY_CHILL_HIDE_RADIUS.get());
        for (LivingEntity nearby : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
            if (nearby == this || !nearby.isAlive()
                    || !(nearby instanceof SnowGolemServant || nearby.getType().is(FROST_SERVANT_TAG))
                    || distanceToSqr(nearby) > Math.pow(MutantSnowGolemServantConfig.ALLY_CHILL_HIDE_RADIUS.get(), 2) || !ServantFaction.isSameFaction(this, nearby)) {
                continue;
            }
            nearby.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    GoetyEffects.CHILL_HIDE.get(), MutantSnowGolemServantConfig.ALLY_CHILL_HIDE_TICKS.get(), MutantSnowGolemServantConfig.ALLY_CHILL_HIDE_LEVEL.get() - 1, false, true, true));
        }
    }

    public void applyIceBlockEffect(LivingEntity target) {
        if (target == null || !target.isAlive() || isAlliedTo(target)) {
            return;
        }
        LivingEntity owner = getTrueOwner();
        net.minecraft.world.effect.MobEffect effect = owner != null && CuriosFinder.hasFrostRobes(owner)
                ? GoetyEffects.FREEZING.get() : net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;
        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(effect, 100, 0), this);
    }

    private int summonLifespanTicks() {
        int lifespan = ServantLimits.SUMMONED_LIFESPAN_TICKS;
        if (frostNovaUpgrade) {
            lifespan += 30 * 20;
        }
        if (chillingUpgrade) {
            lifespan += 30 * 20;
        }
        return lifespan;
    }

    public boolean hasFrostNovaUpgrade() {
        return frostNovaUpgrade;
    }

    public void triggerFrostNovaAt(Vec3 position) {
        if (frostNovaUpgrade && random.nextFloat() < MutantSnowGolemServantConfig.FROST_NOVA_CHANCE.get().floatValue() && level() instanceof ServerLevel serverLevel) {
            GoetyFrostNovaHelper.castAt(serverLevel, this, position);
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
        hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        spawnAtLocation(new ItemStack(Blocks.SNOW_BLOCK), 0.3F);
        spawnAtLocation(new ItemStack(Blocks.SNOW_BLOCK), 0.3F);
        spawnAtLocation(new ItemStack(Blocks.SNOW_BLOCK), 0.3F);
        spawnAtLocation(new ItemStack(Blocks.CARVED_PUMPKIN), 0.3F);
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) {
            return true;
        }
        LivingEntity owner = getTrueOwner();
        if (ServantFaction.isSameFaction(this, entity)) {
            return true;
        }
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
        if (!level().isClientSide()) updateSummoning();
        super.tick();
        updateThrowing();
        updateCommand();
        updateGuardPosition();
        if (!level().isClientSide()) {
            updateSnowAura();
            if (isAlive()) {
                tickVanillaMelt();
            }
            if (soulJarUpgrade || chillingUpgrade) {
                refreshUpgradeEffects();
            }
            if (chillingUpgrade && isAlive() && tickCount % 20 == 0) {
                applyNearbyFrostServantChillHide();
            }
        }
        if (isStaying() && getTarget() == null) {
            getNavigation().stop();
        }
        ServantTeleportHelper.tick(this);
    }

    private void tickVanillaMelt() {
        if (ownerWearsFrostRobes()) {
            return;
        }
        if (isInWaterRainOrBubble()) {
            hurt(damageSources().drown(), 1.0F);
        } else if (level().getBiome(blockPosition()).value().getBaseTemperature() > 1.0F) {
            hurt(damageSources().onFire(), 1.0F);
        }
    }

    private boolean ownerWearsFrostRobes() {
        LivingEntity owner = getTrueOwner();
        return owner != null && CuriosFinder.hasFrostRobes(owner);
    }

    private final class IceThrowGoal extends Goal {
        private LivingEntity goalTarget;
        private int attackTime = -1;

        @Override
        public EnumSet<Flag> getFlags() {
            return EnumSet.of(Flag.MOVE, Flag.LOOK);
        }

        @Override
        public boolean canUse() {
            LivingEntity target = getTarget();
            if (target == null || !target.isAlive() || isSummoning() || !canAttack(target)) {
                return false;
            }
            goalTarget = target;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return !isSummoning() && goalTarget != null && goalTarget.isAlive()
                    && getTarget() == goalTarget && canAttack(goalTarget);
        }

        @Override
        public void start() {
            attackTime = -1;
        }

        @Override
        public void stop() {
            goalTarget = null;
            attackTime = -1;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = goalTarget;
            if (target == null) {
                return;
            }
            double range = Math.max(2.0D, MutantSnowGolemServantConfig.THROW_RANGE.get().doubleValue());
            double distanceSqr = distanceToSqr(target);
            boolean seesTarget = getSensing().hasLineOfSight(target);
            if (isThrowing()) {
                getNavigation().stop();
            } else if (distanceSqr > range * range || !seesTarget) {
                getNavigation().moveTo(target, 1.1D);
            } else {
                getNavigation().stop();
            }
            getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (attackTime > -1) {
                --attackTime;
            }
            if (attackTime <= 0 && distanceSqr <= range * range && seesTarget && !isThrowing()) {
                performRangedAttack(target, (float) (Math.sqrt(distanceSqr) / range));
                int interval = MutantSnowGolemServantConfig.THROW_INTERVAL_TICKS.get();
                if (hasFrostNovaUpgrade()) {
                    interval = Math.max(1, (int) Math.round(interval * (2.0D / 3.0D)));
                }
                attackTime = Math.max(5, interval);
            }
        }
    }

    public boolean isThrowing() {
        return getThrowFlag(THROWING_BIT);
    }

    public int getThrowingTick() {
        return entityData.hasItem(THROWING_TICK) ? entityData.get(THROWING_TICK) : 0;
    }

    public void setThrowingTick(int tick) {
        if (entityData.hasItem(THROWING_TICK)) {
            entityData.set(THROWING_TICK, Math.max(0, tick));
        }
    }

    private void startThrowing() {
        if (!level().isClientSide()) {
            this.yRotO = this.getYRot();
            this.yBodyRotO = this.yBodyRot;
            this.yHeadRotO = this.yHeadRot;
            this.entityData.set(THROW_YAW, throwYawTowardsTarget(this.getYRot(), true));
        }
        setThrowFlag(THROWING_BIT, true);
        setThrowingTick(0);
    }

    private void stopThrowing() {
        setThrowFlag(THROWING_BIT, false);
        setThrowingTick(0);
        throwTarget = null;
    }

    public float getThrowYaw() {
        return this.entityData.hasItem(THROW_YAW) ? this.entityData.get(THROW_YAW) : this.getYRot();
    }

    private float throwYawTowardsTarget(float current, boolean snap) {
        LivingEntity target = this.throwTarget;
        if (target == null || !target.isAlive()) {
            return current;
        }
        float wanted = (float) (Mth.atan2(target.getZ() - this.getZ(), target.getX() - this.getX())
                * 180.0D / Math.PI) - 90.0F;
        float yaw = snap ? wanted
                : Mth.approachDegrees(current, wanted, THROW_TURN_DEGREES_PER_TICK);
        return Mth.wrapDegrees(yaw);
    }

    private void updateThrowFacing() {
        if (!level().isClientSide()) {
            float yaw = throwYawTowardsTarget(getThrowYaw(), false);
            if (yaw != getThrowYaw()) {
                this.entityData.set(THROW_YAW, yaw);
            }
        }
        applyThrowFacing();
    }

    private void applyThrowFacing() {
        float yaw = getThrowYaw();
        this.setYRot(yaw);
        this.yBodyRot = yaw;
        this.yHeadRot = yaw;
        this.yHeadRotO = yaw;
        this.yBodyRotO = yaw;
    }

    @Override
    public float tickHeadTurn(float yaw, float speed) {
        if (isThrowing()) {
            applyThrowFacing();
            return speed;
        }
        return super.tickHeadTurn(yaw, speed);
    }

    private void updateThrowing() {
        if (isSummoning()) {
            stopThrowing();
            return;
        }
        if (!isThrowing()) {
            return;
        }
        int next = getThrowingTick() + 1;
        if (!level().isClientSide()) {
            if (next == THROW_RELEASE_TICK) {
                launchIceBlock();
            }
            if (next >= THROW_DURATION_TICKS) {
                stopThrowing();
                return;
            }
        }
        setThrowingTick(next);
        updateThrowFacing();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceModifier) {
        if (level().isClientSide() || !isAlive() || target == null || !target.isAlive()
                || !canAttack(target) || isSummoning() || isThrowing()) {
            return;
        }
        double range = Math.max(2.0D, MutantSnowGolemServantConfig.THROW_RANGE.get().doubleValue());
        if (distanceToSqr(target) > range * range) {
            return;
        }
        if (soulJarUpgrade && summonCooldown == 0 && random.nextFloat() < MutantSnowGolemServantConfig.SUMMON_CHANCE.get().floatValue()
                && startSummoning()) {
            return;
        }
        throwTarget = target;
        startThrowing();
    }

    private boolean isSummoning() {
        return summonTicks > 0;
    }

    private boolean startSummoning() {
        if (!(level() instanceof ServerLevel serverLevel)) return false;
        int available = MutantSnowGolemServantConfig.SUMMON_CAP.get() - countOwnedSnowServants(serverLevel);
        if (available <= 0) return false;
        pendingSummons.clear();
        int minimum = MutantSnowGolemServantConfig.SUMMON_COUNT_MIN.get();
        int maximum = Math.max(minimum, MutantSnowGolemServantConfig.SUMMON_COUNT_MAX.get());
        int count = Math.min(available, minimum + random.nextInt(maximum - minimum + 1));
        for (int i = 0; i < count; i++) {
            SnowGolemServant summoned = ModEntityTypes.SNOW_GOLEM_SERVANT.get().create(serverLevel);
            if (summoned != null && findSummonPosition(serverLevel, summoned)) {
                pendingSummons.add(summoned);
            }
        }
        if (pendingSummons.isEmpty()) return false;
        summonTicks = SUMMON_DURATION_TICKS;
        summonCooldown = MutantSnowGolemServantConfig.SUMMON_COOLDOWN_TICKS.get();
        stopThrowing();
        holdSummoningPosition();
        spawnSummoningParticles(serverLevel);
        return true;
    }

    private int countOwnedSnowServants(ServerLevel level) {
        SnowServantRoster roster = SnowServantRoster.get(level);
        for (ServerLevel dimension : level.getServer().getAllLevels()) {
            for (Entity entity : dimension.getAllEntities()) {
                if (entity instanceof SnowGolemServant servant) servant.updateSummonRoster();
            }
        }
        return roster.count(getUUID());
    }

    private boolean findSummonPosition(ServerLevel level, SnowGolemServant summoned) {
        double startAngle = random.nextDouble() * Math.PI * 2.0D;
        for (int i = 0; i < 12; i++) {
            double angle = startAngle + i * Math.PI / 6.0D;
            double radius = 2.0D + (i % 3) * 0.5D;
            double x = getX() + Math.cos(angle) * radius;
            double z = getZ() + Math.sin(angle) * radius;
            if (placeSummonAt(level, summoned, x, getY(), z)) return true;
        }
        return false;
    }

    private boolean placeSummonAt(ServerLevel level, SnowGolemServant summoned, double x, double y, double z) {
        if (!level.hasChunkAt(BlockPos.containing(x, y, z))) return false;
        net.minecraft.world.phys.BlockHitResult ground = level.clip(new net.minecraft.world.level.ClipContext(
                new Vec3(x, y + 1.5D, z), new Vec3(x, y - 2.0D, z),
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE, this));
        if (ground.getType() != net.minecraft.world.phys.HitResult.Type.BLOCK
                || ground.getDirection() != net.minecraft.core.Direction.UP) return false;
        summoned.moveTo(x, ground.getLocation().y + 0.01D, z, getYRot(), 0.0F);
        return canPlaceSummon(level, summoned);
    }

    private boolean canPlaceSummon(ServerLevel level, SnowGolemServant summoned) {
        AABB body = summoned.getBoundingBox();
        return level.hasChunkAt(summoned.blockPosition()) && level.getWorldBorder().isWithinBounds(body)
                && level.noCollision(summoned) && !level.containsAnyLiquid(body)
                && pendingSummons.stream().noneMatch(other -> other != summoned
                        && other.getBoundingBox().intersects(body))
                && !level.noCollision(summoned, body.move(0.0D, -0.1D, 0.0D));
    }

    private void holdSummoningPosition() {
        getNavigation().stop();
        setXxa(0.0F);
        setZza(0.0F);
        setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
    }

    private void updateSummoning() {
        if (summonCooldown > 0) summonCooldown--;
        if (!isSummoning()) return;
        if (!isAlive() || pendingSummons.isEmpty()) {
            summonTicks = 0;
            pendingSummons.clear();
            return;
        }
        holdSummoningPosition();
        ServerLevel serverLevel = (ServerLevel) level();
        if (--summonTicks > 0) {
            spawnSummoningParticles(serverLevel);
            return;
        }
        List<SnowGolemServant> summons = new ArrayList<>(pendingSummons);
        pendingSummons.clear();
        boolean spawned = false;
        for (SnowGolemServant summoned : summons) {
            if (countOwnedSnowServants(serverLevel) >= MutantSnowGolemServantConfig.SUMMON_CAP.get()) break;
            if (!placeSummonAt(serverLevel, summoned, summoned.getX(), summoned.getY(), summoned.getZ())) continue;
            summoned.setTrueOwner(this);
            summoned.setWandering(false);
            summoned.setStaying(false);
            summoned.setLimitedLife(summonLifespanTicks());
            summoned.setPersistenceRequired();
            summoned.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(summoned.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            summoned.setTarget(getTarget());
            if (serverLevel.addFreshEntity(summoned)) {
                summoned.updateSummonRoster();
                spawned = true;
                GoetySummonFx.play(serverLevel, summoned);
                serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, summoned.getX(),
                        summoned.getY() + 0.9D, summoned.getZ(), 48,
                        0.2D, 0.45D, 0.2D, 0.18D);
                serverLevel.playSound(null, summoned.getX(), summoned.getY(), summoned.getZ(),
                        SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
        if (spawned && chillingUpgrade) applyNearbyFrostServantChillHide();
    }

    private void spawnSummoningParticles(ServerLevel level) {
        double phase = (SUMMON_DURATION_TICKS - summonTicks) * Math.PI / 5.0D;
        spawnSnowRing(level, position(), getBbWidth() * 0.65D, getBbHeight(), phase);
        for (SnowGolemServant summoned : pendingSummons) {
            spawnSnowRing(level, summoned.position(), 0.65D, summoned.getBbHeight(), -phase);
        }
    }

    private void spawnSnowRing(ServerLevel level, Vec3 center, double radius, double height, double phase) {
        for (int i = 0; i < 6; i++) {
            double angle = phase + i * Math.PI / 3.0D;
            double y = 0.2D + height * (0.4D + 0.3D * Math.sin(angle + phase * 0.5D));
            level.sendParticles(ParticleTypes.SNOWFLAKE, center.x + Math.cos(angle) * radius,
                    center.y + y, center.z + Math.sin(angle) * radius, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void launchIceBlock() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        LivingEntity target = throwTarget;
        if (target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        MutantSnowGolem standIn = MutantThrowStandIns.forLevel(serverLevel);
        if (standIn == null) {
            return;
        }
        standIn.setPos(getX(), getY(), getZ());
        standIn.setYRot(getYRot());
        standIn.yHeadRot = getYHeadRot();
        standIn.yBodyRot = getYRot();

        MutantSnowGolemServantIceBlock ice = new MutantSnowGolemServantIceBlock(standIn, this);
        float inaccuracy = MutantSnowGolemServantConfig.THROW_INACCURACY.get().floatValue();
        if (MutantSnowGolemServantConfig.AIM_CORRECTION.get()) {
            Vec3 from = ice.position();
            Vec3 center = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
            double directRange = Math.max(1.0D,
                    MutantSnowGolemServantConfig.DIRECT_THROW_RANGE.get().doubleValue());
            if (distanceToSqr(target) <= directRange * directRange) {
                Vec3 dir = center.subtract(from);
                double len = dir.length();
                if (len < 1.0E-4D) {
                    return;
                }
                double speed = Math.max(0.5D,
                        MutantSnowGolemServantConfig.DIRECT_THROW_SPEED.get().doubleValue());
                double flightTicks = Math.max(2.0D, len / speed);
                double gravityComp = 0.5D * 0.06D * flightTicks * flightTicks * 0.99D;
                Vec3 velocity = dir.scale(speed / len).add(0.0D, gravityComp, 0.0D);
                ice.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), inaccuracy);
            } else {
                BallisticAim.Solution aim = BallisticAim.solveFast(from, center, 0.06D, 0.99D, 0.5D, 1.8D);
                Vec3 motion = target.getDeltaMovement();
                Vec3 lead = BallisticAim.leadOffset(motion, aim.ticks());
                aim = BallisticAim.solveFast(from, center.add(lead), 0.06D, 0.99D, 0.5D, 1.8D);
                Vec3 velocity = aim.velocity();
                ice.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), inaccuracy);
            }
        } else {
            double dx = target.getX() - ice.getX();
            double dy = target.getY() - ice.getY();
            double dz = target.getZ() - ice.getZ();
            double xz = Math.sqrt(dx * dx + dz * dz);
            ice.shoot(dx, dy + xz * 0.4D, dz, 0.9F, 1.0F);
        }
        playSound(SoundEvents.SNOWBALL_THROW, 1.0F, 0.8F + this.random.nextFloat() * 0.2F);
        serverLevel.addFreshEntity(ice);
    }

    private void updateSnowAura() {
        if (!MutantSnowGolemServantConfig.SNOW_TRAIL.get() || !onGround() || isBaby()) {
            return;
        }
        if (!level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (level().getBiome(blockPosition()).is(net.minecraft.tags.BiomeTags.SNOW_GOLEM_MELTS)) {
            return;
        }
        Level level = level();
        int baseX = Mth.floor(getX());
        int baseY = Mth.floor(getBoundingBox().minY);
        int baseZ = Mth.floor(getZ());
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) == 2 && Math.abs(dz) == 2) {
                    continue; // The original skips the four far corners.
                }
                BlockPos pos = new BlockPos(baseX + dx, baseY, baseZ + dz);
                BlockPos above = pos.above();
                BlockPos below = pos.below();
                boolean snowSpot = level.isEmptyBlock(pos)
                        && Blocks.SNOW.defaultBlockState().canSurvive(level, pos);
                boolean liquidAbove = level.getBlockState(above).getBlock() instanceof net.minecraft.world.level.block.LiquidBlock
                        && !level.getFluidState(above).isEmpty();
                boolean liquidBelow = level.getBlockState(below).getBlock() instanceof net.minecraft.world.level.block.LiquidBlock
                        && !level.getFluidState(below).isEmpty();
                if (level.getFluidState(pos).getType() == Fluids.WATER) {
                    level.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
                }
                if (level.getFluidState(below).getType() == Fluids.WATER) {
                    level.setBlock(below, Blocks.ICE.defaultBlockState(), 3);
                }
                if (snowSpot && (Math.abs(dx) == 2 || Math.abs(dz) == 2) && random.nextInt(20) != 0) {
                    snowSpot = false;
                }
                if (snowSpot && (Math.abs(dx) == 1 || Math.abs(dz) == 1) && random.nextInt(10) != 0) {
                    snowSpot = false;
                }
                if ((liquidAbove || liquidBelow) && (Math.abs(dx) == 2 || Math.abs(dz) == 2)
                        && random.nextInt(14) != 0) {
                    snowSpot = false;
                }
                if ((liquidAbove || liquidBelow) && (Math.abs(dx) == 1 || Math.abs(dz) == 1)
                        && random.nextInt(6) != 0) {
                    snowSpot = false;
                }
                if (snowSpot) {
                    level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 3);
                }
                if (liquidAbove) {
                    level.setBlock(above, Blocks.ICE.defaultBlockState(), 3);
                }
            }
        }
    }

    public boolean hasJackOLantern() {
        return getPumpkinFlag(JACK_O_LANTERN_BIT);
    }

    public void setJackOLantern(boolean worn) {
        setPumpkinFlag(JACK_O_LANTERN_BIT, worn);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!level().isClientSide() && player == getTrueOwner() && applyGoetyUpgrade(player, hand, held)) {
            return InteractionResult.SUCCESS;
        }
        if (player == getTrueOwner() && isAlive() && getHealth() < getMaxHealth()
                && MutantSnowGolemServantConfig.SNOW_BLOCK_HEAL.get() > 0.0D
                && held.is(Blocks.SNOW_BLOCK.asItem())) {
            if (!level().isClientSide()) {
                heal((float) (double) MutantSnowGolemServantConfig.SNOW_BLOCK_HEAL.get());
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
        boolean pumpkinHand = held.is(Blocks.CARVED_PUMPKIN.asItem());
        if ((!pumpkinHand && !held.is(Items.SHEARS)) || !canAdjustPumpkin(player)) {
            return super.mobInteract(player, hand);
        }
        if (held.is(Items.SHEARS)) {
            if (!readyForShearing()) {
                return InteractionResult.PASS;
            }
            if (!level().isClientSide()) {
                shear(player.getSoundSource());
                held.hurtAndBreak(1, player, broken -> broken.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (hasJackOLantern()) {
            return InteractionResult.PASS;
        }
        if (!level().isClientSide()) {
            setJackOLantern(true);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            spawnHeadPuff();
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private boolean canAdjustPumpkin(Player player) {
        LivingEntity owner = getTrueOwner();
        return owner == null || owner == player;
    }

    @Override
    public void shear(SoundSource category) {
        level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, category, 1.0F, 1.0F);
        if (!level().isClientSide()) {
            setJackOLantern(false);
            spawnAtLocation(new ItemStack(Blocks.CARVED_PUMPKIN), 1.7F);
        }
    }

    @Override
    public boolean readyForShearing() {
        return isAlive() && hasJackOLantern();
    }

    private void spawnHeadPuff() {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, getX(),
                    getY() + getBbHeight() * 0.85D, getZ(), 8,
                    getBbWidth() * 0.45D, 0.12D, getBbWidth() * 0.45D, 0.01D);
        }
    }

    private boolean getPumpkinFlag(int flag) {
        return entityData.hasItem(PUMPKIN_STATE) && (entityData.get(PUMPKIN_STATE) & flag) != 0;
    }

    private void setPumpkinFlag(int flag, boolean value) {
        if (!entityData.hasItem(PUMPKIN_STATE)) {
            return;
        }
        byte state = entityData.get(PUMPKIN_STATE);
        entityData.set(PUMPKIN_STATE, value ? (byte) (state | flag) : (byte) (state & ~flag));
    }

    public void feedSnowball() {
        feedSnowball(1.0F);
    }

    public void feedSnowball(float amount) {
        if (!isAlive()) {
            return;
        }
        if (getHealth() < getMaxHealth()) {
            heal(amount);
        }
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 3; i++) {
                serverLevel.sendParticles(ParticleTypes.HEART,
                        getX() + (random.nextDouble() - 0.5D) * getBbWidth(),
                        getY() + 0.6D + random.nextDouble() * getBbHeight() * 0.6D,
                        getZ() + (random.nextDouble() - 0.5D) * getBbWidth(),
                        1, 0.0D, 0.05D, 0.0D, 0.0D);
            }
        }
    }

    private void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) {
            return;
        }
        if (getTarget().distanceToSqr(boundPos.getX() + 0.5D, boundPos.getY(), boundPos.getZ() + 0.5D)
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) {
            setTarget(null);
        }
    }

    private boolean getThrowFlag(int flag) {
        return entityData.hasItem(THROWING_STATE) && (entityData.get(THROWING_STATE) & flag) != 0;
    }

    private void setThrowFlag(int flag, boolean value) {
        if (!entityData.hasItem(THROWING_STATE)) {
            return;
        }
        byte state = entityData.get(THROWING_STATE);
        entityData.set(THROWING_STATE, value ? (byte) (state | flag) : (byte) (state & ~flag));
    }

    public boolean canVolleyHit(Entity entity) {
        return entity instanceof LivingEntity living && living.isAlive() && canAttack(living);
    }

    private static final class SummoningGoal extends Goal {
        private final MutantSnowGolemServant servant;

        private SummoningGoal(MutantSnowGolemServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override public boolean canUse() { return servant.isSummoning(); }
        @Override public boolean canContinueToUse() { return servant.isSummoning(); }
        @Override public boolean requiresUpdateEveryTick() { return true; }
        @Override public void tick() { servant.holdSummoningPosition(); }
    }

}
