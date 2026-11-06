package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.EntityFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.util.EntityUtil;
import fuzs.mutantmonsters.world.entity.MutantSkeletonBodyPart;
import fuzs.mutantmonsters.world.entity.mutant.MutantSkeleton;
import fuzs.mutantmonsters.world.entity.projectile.MutantArrow;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.MutantSkeletonServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;

public class MutantSkeletonServant extends AbstractMutantServant implements IServant,
        net.minecraft.world.entity.OwnableEntity, RangedAttackMob {

    public static final Animation MELEE_ANIMATION = MutantSkeleton.MELEE_ANIMATION;
    public static final Animation CONSTRICT_RIBS_ANIMATION = MutantSkeleton.CONSTRICT_RIBS_ANIMATION;
    public static final Animation SHOOT_ANIMATION = MutantSkeleton.SHOOT_ANIMATION;
    public static final Animation MULTI_SHOT_ANIMATION = MutantSkeleton.MULTI_SHOT_ANIMATION;
    private static final Animation[] ANIMATIONS =
            {MELEE_ANIMATION, CONSTRICT_RIBS_ANIMATION, SHOOT_ANIMATION, MULTI_SHOT_ANIMATION};

    private static final int SHOOT_RELEASE_TICK = 26;

    private static final int MULTI_SHOT_BARRAGE_START = 24;
    private static final int MULTI_SHOT_BARRAGE_TICKS = 4;
    private static final int MULTI_SHOT_PER_TICK = 6;

    private static final EntityDataAccessor<Integer> ANIMATION_ID =
            SynchedEntityData.defineId(MutantSkeletonServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_TICK =
            SynchedEntityData.defineId(MutantSkeletonServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_ELAPSED =
            SynchedEntityData.defineId(MutantSkeletonServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_DURATION =
            SynchedEntityData.defineId(MutantSkeletonServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HORNED_APPEARANCE =
            SynchedEntityData.defineId(MutantSkeletonServant.class, EntityDataSerializers.BOOLEAN);

    private boolean soulJarUpgrade;
    private boolean maleficHelmUpgrade;
    private int concentratedBoneUpgrades;

    private static final String SOUL_JAR_TAG = "SoulJarUpgrade";
    private static final String MALEFIC_HELM_TAG = "MaleficHelmUpgrade";
    private static final String CONCENTRATED_BONE_TAG = "ConcentratedBoneUpgrades";
    private static final UUID SOUL_JAR_HEALTH_UUID =
            UUID.fromString("2f7c1f3d-0a6f-4d32-9a74-6d2ce03b7b11");
    private static final UUID BONE_ATTACK_UUID =
            UUID.fromString("74d61c05-3f93-4b3c-bc8a-2c51e6efc810");
    private static final UUID BONE_HEALTH_UUID =
            UUID.fromString("e0bd7a7e-241c-4b6c-a0f0-7c4d254d39be");
    private static final UUID BONE_ARMOR_UUID =
            UUID.fromString("b3f99d25-2cc3-4b5d-b6fb-fdcb9a5f4af0");
    private static final UUID BONE_TOUGHNESS_UUID =
            UUID.fromString("f276cb57-57c9-4c0a-8e11-e68a4b2ac9c9");

    private LivingEntity shotTarget;

    private boolean sweepResolved;

    private int pendingTripleShots;
    private float tripleShotInaccuracy;

    private int consecutiveMeleeAttacks;
    private LivingEntity meleeTarget;
    private boolean retreatJump;
    private boolean skillJumpActive;

    public MutantSkeletonServant(EntityType<? extends MutantSkeletonServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MutantSkeletonServantConfig.MAX_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, MutantSkeletonServantConfig.ATTACK_DAMAGE.get())
                .add(Attributes.FOLLOW_RANGE, MutantSkeletonServantConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, MutantSkeletonServantConfig.MOVEMENT_SPEED.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, MutantSkeletonServantConfig.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.ARMOR, MutantSkeletonServantConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, MutantSkeletonServantConfig.ARMOR_TOUGHNESS.get());
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return registerAttributes();
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), MutantSkeletonServantConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), MutantSkeletonServantConfig.ATTACK_DAMAGE.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), MutantSkeletonServantConfig.MOVEMENT_SPEED.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                MutantSkeletonServantConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), MutantSkeletonServantConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), MutantSkeletonServantConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), MutantSkeletonServantConfig.FOLLOW_RANGE.get());
        refreshUpgradeEffects();
    }

    private void refreshUpgradeEffects() {
        refreshHornedAppearance();
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            replaceModifier(maxHealth, SOUL_JAR_HEALTH_UUID, "Soul jar", soulJarUpgrade ? MutantSkeletonServantConfig.SOUL_JAR_HEALTH.get() : 0.0D);
            replaceModifier(maxHealth, BONE_HEALTH_UUID, "Concentrated bone", concentratedBoneUpgrades * MutantSkeletonServantConfig.BONE_HEALTH.get());
        }
        replaceModifier(getAttribute(Attributes.ATTACK_DAMAGE), BONE_ATTACK_UUID,
                "Concentrated bone", concentratedBoneUpgrades * MutantSkeletonServantConfig.BONE_MELEE_DAMAGE.get());
        replaceModifier(getAttribute(Attributes.ARMOR), BONE_ARMOR_UUID,
                "Concentrated bone", concentratedBoneUpgrades * MutantSkeletonServantConfig.BONE_ARMOR.get());
        replaceModifier(getAttribute(Attributes.ARMOR_TOUGHNESS), BONE_TOUGHNESS_UUID,
                "Concentrated bone", concentratedBoneUpgrades * MutantSkeletonServantConfig.BONE_TOUGHNESS.get());
    }

    private static void replaceModifier(AttributeInstance attribute, UUID id, String name, double amount) {
        if (attribute == null) {
            return;
        }
        if (attribute.getModifier(id) != null) {
            attribute.removePermanentModifier(id);
        }
        if (amount != 0.0D) {
            attribute.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    id, name, amount,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
        }
    }

    public boolean hasSoulJarUpgrade() {
        return soulJarUpgrade;
    }

    public boolean hasMaleficHelmUpgrade() {
        return maleficHelmUpgrade;
    }

    public int getConcentratedBoneUpgrades() {
        return concentratedBoneUpgrades;
    }

    public boolean isFullyUpgraded() {
        return soulJarUpgrade && maleficHelmUpgrade
                && concentratedBoneUpgrades >= MutantSkeletonServantConfig.BONE_MAX_UPGRADES.get();
    }

    private void playUpgradeSound(SoundEvent normal, float pitch, boolean wasFullyUpgraded) {
        if (!wasFullyUpgraded && isFullyUpgraded()) {
            playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.0F);
            awardFullyUpgradedAdvancement();
            return;
        }
        if (normal != null) {
            playSound(normal, 1.0F, pitch);
        }
    }

    private void awardFullyUpgradedAdvancement() {
        if (!(getTrueOwner() instanceof ServerPlayer serverPlayer)) {
            return;
        }
        var server = serverPlayer.getServer();
        if (server == null) {
            return;
        }
        var advancement = server.getAdvancements().getAdvancement(
                ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "bone_overgrowth_plus"));
        if (advancement != null) {
            serverPlayer.getAdvancements().award(advancement, "bone_overgrowth_plus");
        }
    }

    public boolean hasHornedAppearance() {
        return entityData.get(HORNED_APPEARANCE);
    }

    private void refreshHornedAppearance() {
        if (!level().isClientSide() && entityData.hasItem(HORNED_APPEARANCE)) {
            entityData.set(HORNED_APPEARANCE, concentratedBoneUpgrades >= 3);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        setConfigurableAttributes();
        refreshUpgradeEffects();
        setHealth(getMaxHealth());
        return result;
    }

    @Override
    public void convertNewEquipment(Entity original) {
        super.convertNewEquipment(original);
        setConfigurableAttributes();
        refreshUpgradeEffects();
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

        this.goalSelector.addGoal(1, new ServantShootGoal(this));
        this.goalSelector.addGoal(2, new MeleeArtsGoal(this));
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
        this.entityData.define(ANIMATION_ELAPSED, 0);
        this.entityData.define(ANIMATION_DURATION, 1);
        this.entityData.define(HORNED_APPEARANCE, false);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return mutantSound("entity.mutant_skeleton.ambient", SoundEvents.SKELETON_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return mutantSound("entity.mutant_skeleton.hurt", SoundEvents.SKELETON_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return mutantSound("entity.mutant_skeleton.death", SoundEvents.SKELETON_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        if (deathTime == 0) {
            playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
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
        tag.putBoolean(SOUL_JAR_TAG, soulJarUpgrade);
        tag.putBoolean(MALEFIC_HELM_TAG, maleficHelmUpgrade);
        tag.putInt(CONCENTRATED_BONE_TAG, concentratedBoneUpgrades);
        tag.putBoolean("SkillJumpActive", skillJumpActive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        } else if (tag.hasUUID("GoetyOwner")) {
            setOwnerId(tag.getUUID("GoetyOwner"));
        }
        soulJarUpgrade = tag.getBoolean(SOUL_JAR_TAG);
        maleficHelmUpgrade = tag.getBoolean(MALEFIC_HELM_TAG);
        concentratedBoneUpgrades = Math.max(0, Math.min(64, tag.getInt(CONCENTRATED_BONE_TAG)));
        if (tag.contains("MutantAnimation")) {
            int id = tag.getInt("MutantAnimation");
            setAnimation(id >= 0 && id < ANIMATIONS.length ? ANIMATIONS[id] : Animation.NONE);
            setAnimationTick(tag.contains("MutantAnimationTick") ? tag.getInt("MutantAnimationTick") : 0);
            entityData.set(ANIMATION_ELAPSED, (int) ((long) getAnimationTick()
                    * entityData.get(ANIMATION_DURATION) / Math.max(1, getAnimation().duration())));
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
        skillJumpActive = tag.getBoolean("SkillJumpActive");
        refreshUpgradeEffects();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (!level().isClientSide() && player == getTrueOwner() && applyUpgrade(player, hand, held)) {
            return InteractionResult.SUCCESS;
        }
        if (player == getTrueOwner() && isAlive() && getHealth() < getMaxHealth()
                && MutantSkeletonServantConfig.FEED_BONE_HEAL.get() > 0.0D) {
            double amount = 0.0D;
            boolean boneBlock = false;
            if (held.is(Items.BONE)) {
                amount = MutantSkeletonServantConfig.FEED_BONE_HEAL.get();
            } else if (held.is(net.minecraft.world.level.block.Blocks.BONE_BLOCK.asItem())) {
                amount = MutantSkeletonServantConfig.FEED_BONE_HEAL.get()
                        * MutantSkeletonServantConfig.FEED_BONE_BLOCK_MULTIPLIER.get();
                boneBlock = true;
            }
            if (amount > 0.0D) {
                if (!level().isClientSide()) {
                    heal((float) amount);
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                    playSound(boneBlock ? SoundEvents.BONE_BLOCK_PLACE : SoundEvents.SKELETON_AMBIENT,
                            1.0F, 1.0F);
                    if (level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                getX(), getY(1.9D), getZ(), 7, 0.3D, 0.5D, 0.3D, 0.0D);
                    }
                }
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        if (player == getTrueOwner() && equipArmor(player, hand, held)) {
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

    private boolean applyUpgrade(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId == null) {
            return false;
        }
        boolean wasFullyUpgraded = isFullyUpgraded();
        if (MutantSkeletonServantConfig.SOUL_JAR_ENABLED.get() && !soulJarUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "soul_jar").equals(itemId)
                && !SoulJar.isCairn(stack) && !SoulJar.isMossy(stack)
                && !SoulJar.isDrowned(stack) && !SoulJar.isWither(stack)) {
            soulJarUpgrade = true;
            float oldHealth = getHealth();
            refreshUpgradeEffects();
            setHealth(Math.min(getMaxHealth(), oldHealth + MutantSkeletonServantConfig.SOUL_JAR_HEALTH.get().floatValue()));
            consumeUpgradeItem(player, stack);
            playUpgradeSound(SoundEvents.SOUL_ESCAPE, 0.8F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.SOUL);
            return true;
        }
        if (MutantSkeletonServantConfig.MALEFIC_HELM_ENABLED.get() && !maleficHelmUpgrade && ResourceLocation.fromNamespaceAndPath("goety", "malefic_helm").equals(itemId)) {
            maleficHelmUpgrade = true;
            consumeUpgradeItem(player, stack);
            playUpgradeSound(SoundEvents.ARMOR_EQUIP_CHAIN, 0.8F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.POOF);
            return true;
        }
        if (concentratedBoneUpgrades < MutantSkeletonServantConfig.BONE_MAX_UPGRADES.get()
                && ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "concentrated_bone_chunk").equals(itemId)) {
            concentratedBoneUpgrades++;
            float oldHealth = getHealth();
            refreshUpgradeEffects();
            setHealth(Math.min(getMaxHealth(), oldHealth + MutantSkeletonServantConfig.BONE_HEALTH.get().floatValue()));
            consumeUpgradeItem(player, stack);
            playUpgradeSound(SoundEvents.BONE_BLOCK_PLACE, 0.7F, wasFullyUpgraded);
            spawnUpgradeParticles(ParticleTypes.ITEM_SLIME);
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

    @Override
    public void updateMoveMode(Player player) {
        if (!isWandering() && !isStaying() && !isGuardingArea()) {
            setBoundPos(null);
            setWandering(true);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.wander", getDisplayName()), true);
        } else if (!isStaying() && !isGuardingArea()) {
            setBoundPos(null);
            setWandering(false);
            setStaying(true);
            player.displayClientMessage(Component.translatable("info.goety.servant.staying", getDisplayName()), true);
        } else if (!isGuardingArea()) {
            setBoundPos(blockPosition());
            setWandering(false);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.guard", getDisplayName()), true);
        } else {
            setBoundPos(null);
            setWandering(false);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.follow", getDisplayName()), true);
        }
        playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
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
    public void die(DamageSource source) {
        super.die(source);
        if (!level().isClientSide()) {
            for (int i = 0; i < 18; ++i) {
                int partIndex = i;
                if (partIndex >= 3) {
                    partIndex += 1;
                }
                if (partIndex >= 4) {
                    partIndex += 1;
                }
                if (partIndex >= 5) {
                    partIndex += 1;
                }
                if (partIndex >= 6) {
                    partIndex += 1;
                }
                if (partIndex >= 9) {
                    partIndex += 1;
                }
                if (partIndex >= 10) {
                    partIndex += 1;
                }
                if (partIndex >= 11) {
                    partIndex += 1;
                }
                if (partIndex >= 12) {
                    partIndex += 1;
                }
                if (partIndex >= 15) {
                    partIndex += 1;
                }
                if (partIndex >= 16) {
                    partIndex += 1;
                }
                if (partIndex >= 17) {
                    partIndex += 1;
                }
                if (partIndex >= 18) {
                    partIndex += 1;
                }
                if (partIndex >= 20) {
                    partIndex += 1;
                }
                MutantSkeletonBodyPart bodyPart = new MutantSkeletonBodyPart(level(), this, partIndex);
                bodyPart.setDeltaMovement(bodyPart.getDeltaMovement().add(
                        this.random.nextFloat() * 0.8F * 2.0F - 0.8F,
                        this.random.nextFloat() * 0.25F + 0.1F,
                        this.random.nextFloat() * 0.8F * 2.0F - 0.8F));
                level().addFreshEntity(bodyPart);
            }
        }
        this.deathTime = 19;
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
        updateAnimation();
        updateCommand();
        updateGuardPosition();
        if (skillJumpActive) {
            fallDistance = 0.0F;
            if (onGround() || isInWaterOrBubble() || isInLava()) {
                skillJumpActive = false;
            }
        }
        if (!level().isClientSide()) {
            if (getTarget() == null || getTarget() != meleeTarget) {
                consecutiveMeleeAttacks = 0;
                meleeTarget = getTarget();
            }
        }
        if (isStaying() && getTarget() == null) {
            getNavigation().stop();
        }
        ServantTeleportHelper.tick(this);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        if (skillJumpActive) {
            fallDistance = 0.0F;
            return false;
        }
        return super.causeFallDamage(distance, multiplier, source);
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

    @Override
    public void performRangedAttack(LivingEntity target, float distanceModifier) {
        if (level().isClientSide() || target == null || !canAttack(target) || isAnimationPlaying()) {
            return;
        }
        shotTarget = target;
        sweepResolved = false;
        int chance = MutantSkeletonServantConfig.MULTI_SHOT_CHANCE_PERCENT.get();
        boolean volley = chance > 0 && random.nextInt(100) < chance;
        startAnimation(volley ? MULTI_SHOT_ANIMATION : SHOOT_ANIMATION);
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
        entityData.set(ANIMATION_ELAPSED, 0);
        boolean ranged = animation == SHOOT_ANIMATION || animation == MULTI_SHOT_ANIMATION;
        int duration = animation == null ? 1 : Math.max(1, animation.duration());
        if (ranged && maleficHelmUpgrade) {
            duration = Math.max(1, (int) Math.round(duration
                    * MutantSkeletonServantConfig.MALEFIC_INTERVAL_MULTIPLIER.get()));
        }
        entityData.set(ANIMATION_DURATION, duration);
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

    private int animationIndex(Animation animation) {
        for (int i = 0; i < ANIMATIONS.length; i++) {
            if (ANIMATIONS[i] == animation) {
                return i;
            }
        }
        return -1;
    }

    private void startAnimation(Animation animation) {
        if (animation != MULTI_SHOT_ANIMATION && animation != SHOOT_ANIMATION) {
            pendingTripleShots = 0;
            tripleShotInaccuracy = 0.0F;
        }
        setAnimation(animation);
        setAnimationTick(0);
        if (animation == SHOOT_ANIMATION || animation == MULTI_SHOT_ANIMATION) {
            LivingEntity target = shotTarget;
            if (target != null) {
                getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        } else if (animation == MELEE_ANIMATION || animation == CONSTRICT_RIBS_ANIMATION) {
            ambientSoundTime = -getAmbientSoundInterval();
        }
    }

    private void updateAnimation() {
        Animation animation = getAnimation();
        if (animation == Animation.NONE) {
            return;
        }
        int tick = getAnimationTick();
        int elapsed = entityData.get(ANIMATION_ELAPSED) + 1;
        int duration = Math.max(1, entityData.get(ANIMATION_DURATION));
        int next = Math.max(tick + 1, Math.min(animation.duration(),
                (int) ((long) elapsed * animation.duration() / duration)));
        if (!level().isClientSide()) {
            for (int eventTick = tick; eventTick < next; eventTick++) {
                advanceAnimationEffects(animation, eventTick);
            }
        }
        entityData.set(ANIMATION_ELAPSED, elapsed);
        boolean volleyTail = animation == MULTI_SHOT_ANIMATION
                && next >= MULTI_SHOT_BARRAGE_START && next < MULTI_SHOT_BARRAGE_START + MULTI_SHOT_BARRAGE_TICKS;
        boolean tripleTail = animation == SHOOT_ANIMATION && pendingTripleShots > 0
                && next <= SHOOT_RELEASE_TICK + pendingTripleShots * 2;
        if (next >= animation.duration() && !volleyTail && !tripleTail && pendingTripleShots == 0) {
            setAnimation(Animation.NONE);
            shotTarget = null;
            sweepResolved = false;
            retreatJump = false;
        } else {
            setAnimationTick(next);
        }
    }

    private void advanceAnimationEffects(Animation animation, int tick) {
        if (animation == SHOOT_ANIMATION) {
            if (tick == 5) {
                playSound(SoundEvents.CROSSBOW_QUICK_CHARGE_2, 1.0F, 1.0F);
            } else if (tick == 20) {
                playSound(SoundEvents.CROSSBOW_LOADING_END, 1.0F,
                        1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
            } else if (tick == SHOOT_RELEASE_TICK) {
                releaseSingleShot();
            } else if (pendingTripleShots > 0 && tick > SHOOT_RELEASE_TICK
                    && (tick - SHOOT_RELEASE_TICK) % 2 == 0) {
                releaseTripleShot();
            }
        } else if (animation == MULTI_SHOT_ANIMATION) {
            if (tick == 10) {
                leapForVolley();
            } else if (tick == 15) {
                playSound(SoundEvents.CROSSBOW_QUICK_CHARGE_3, 1.0F, 1.0F);
            } else if (tick == 20) {
                playSound(SoundEvents.CROSSBOW_LOADING_END, 1.0F,
                        1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
            } else if (tick >= MULTI_SHOT_BARRAGE_START && tick < MULTI_SHOT_BARRAGE_START + MULTI_SHOT_BARRAGE_TICKS) {
                int arrowsPerTick = MULTI_SHOT_PER_TICK * (maleficHelmUpgrade ? MutantSkeletonServantConfig.MALEFIC_VOLLEY_MULTIPLIER.get() : 1);
                for (int i = 0; i < arrowsPerTick; i++) {
                    launchArrow(shotTarget, 2.0F - random.nextFloat() * 0.1F, 3.0F, false);
                }
                playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F,
                        1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.25F);
            }
        } else if (animation == MELEE_ANIMATION && tick == 3) {
            resolveBoneSweep();
            playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 1.2F));
        } else if (animation == CONSTRICT_RIBS_ANIMATION && tick == 6) {
            resolveConstrictRibs();
        }
    }

    private void leapForVolley() {
        LivingEntity target = shotTarget;
        if (target == null || !target.isAlive()) {
            return;
        }
        this.stopRiding();
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double strength = 0.06D + this.random.nextFloat() * 0.03D;
        if (this.distanceToSqr(target) < 16.0D) {
            dx = -dx;
            dz = -dz;
            strength *= 5.0D;
        }
        this.stuckSpeedMultiplier = Vec3.ZERO;
        this.setDeltaMovement(dx * strength, 1.1D * this.getBlockJumpFactor(), dz * strength);
        this.skillJumpActive = true;
        this.fallDistance = 0.0F;
        this.setOnGround(false);
        this.hasImpulse = true;
    }

    private void releaseSingleShot() {
        LivingEntity target = shotTarget;
        if (target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        float inaccuracy = hasLineOfSight(target) ? 0.0F : 0.5F + random.nextFloat();
        boolean tripleShot = maleficHelmUpgrade
                && random.nextInt(100) < MutantSkeletonServantConfig.MALEFIC_TRIPLE_SHOT_CHANCE_PERCENT.get();
        pendingTripleShots = tripleShot ? 2 : 0;
        tripleShotInaccuracy = tripleShot ? inaccuracy + 1.0F : inaccuracy;
        launchArrow(target, 2.4F, tripleShotInaccuracy,
                MutantSkeletonServantConfig.ARROW_EFFECTS.get(), true);
        playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.25F);
        if (!tripleShot) {
            shotTarget = null;
        }
    }

    private void releaseTripleShot() {
        LivingEntity target = shotTarget;
        if (target == null || !target.isAlive() || !canAttack(target)) {
            pendingTripleShots = 0;
            shotTarget = null;
            return;
        }
        launchArrow(target, 2.4F, tripleShotInaccuracy,
                MutantSkeletonServantConfig.ARROW_EFFECTS.get(), true);
        pendingTripleShots--;
        playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.25F);
        if (pendingTripleShots == 0) {
            shotTarget = null;
        }
    }

    private void launchArrow(LivingEntity target, float velocity, float inaccuracy, boolean withEffects) {
        launchArrow(target, velocity, inaccuracy, withEffects, false);
    }

    private void launchArrow(LivingEntity target, float velocity, float inaccuracy, boolean withEffects,
            boolean normalShot) {
        if (target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        MutantSkeletonServantArrow arrow = new MutantSkeletonServantArrow(level(), this, soulJarUpgrade,
                maleficHelmUpgrade && normalShot && MutantSkeletonServantConfig.MALEFIC_IGNORE_INVULNERABILITY.get(), concentratedBoneUpgrades);
        arrow.shoot(target, velocity, inaccuracy);
        if (withEffects && isFullyUpgraded()) {
            LivingEntity patron = getTrueOwner();
            switch (this.random.nextInt(3)) {
                case 0 -> arrow.addEffect(new MobEffectInstance(GoetyEffects.WANE.get(), 100, 0));
                case 1 -> {
                    MobEffect poison = patron != null && CuriosFinder.hasWildSet(patron)
                            ? GoetyEffects.ACID_VENOM.get() : MobEffects.POISON;
                    arrow.addEffect(new MobEffectInstance(poison, 100, 0));
                }
                default -> {
                    MobEffect hunger = patron != null && CuriosFinder.hasWildSet(patron)
                            ? GeneratedMod.FAMISHED.get() : MobEffects.HUNGER;
                    arrow.addEffect(new MobEffectInstance(hunger, 100, 0));
                }
            }
        } else if (withEffects) {
            LivingEntity patron = getTrueOwner();
            if (random.nextInt(4) == 0) {
                if (patron != null && CuriosFinder.hasWildSet(patron)) {
                    arrow.addEffect(new MobEffectInstance(GoetyEffects.ACID_VENOM.get(), 80 + random.nextInt(60), 0));
                } else {
                    arrow.addEffect(new MobEffectInstance(MobEffects.POISON, 80 + random.nextInt(60), 0));
                }
            }
            if (random.nextInt(4) == 0) {
                MobEffect hunger = patron != null && CuriosFinder.hasWildSet(patron)
                        ? GeneratedMod.FAMISHED.get() : MobEffects.HUNGER;
                arrow.addEffect(new MobEffectInstance(hunger, 120 + random.nextInt(60), 1));
            }
            if (random.nextInt(4) == 0) {
                if (patron != null && (CuriosFinder.hasNecroSet(patron) || CuriosFinder.hasNamelessSet(patron))) {
                    arrow.addEffect(new MobEffectInstance(GoetyEffects.WANE.get(), 120 + random.nextInt(60), 0));
                } else {
                    arrow.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120 + random.nextInt(60), 0));
                }
            }
        }
        level().addFreshEntity(arrow);
    }

    private void applySoulMeleeDamage(LivingEntity target) {
        if (!soulJarUpgrade || level().isClientSide() || target == null || !target.isAlive()) {
            return;
        }
        int invulnerable = target.invulnerableTime;
        target.invulnerableTime = 0;
        target.hurt(level().damageSources().indirectMagic(this, getTrueOwner()), MutantSkeletonServantConfig.SOUL_JAR_MELEE_DAMAGE.get().floatValue());
        target.invulnerableTime = Math.max(invulnerable, target.invulnerableTime);
    }

    private void resolveBoneSweep() {
        if (sweepResolved) {
            return;
        }
        sweepResolved = true;
        double damage = getAttributeValue(Attributes.ATTACK_DAMAGE) + 1.0D;
        AABB area = getBoundingBox().inflate(4.0D);
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, area, this::canAttack)) {
            double dist = distanceTo(living);
            if (dist > 3.0D) {
                continue;
            }
            if (EntityUtil.getHeadAngle(this, getX() - living.getX(), getZ() - living.getZ()) >= 60.0F) {
                continue;
            }
            if (living.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), (float) damage)) {
                applySoulMeleeDamage(living);
                EntityUtil.disableShield(living, 40);
            }
            double x = living.getX() - getX();
            double z = living.getZ() - getZ();
            double length = Math.sqrt(x * x + z * z);
            if (length < 1.0E-4D) {
                length = 1.0D;
            }
            living.setDeltaMovement(x / length * 0.8D, 0.42D, z / length * 0.8D);
            living.hurtMarked = true;
            EntityUtil.sendPlayerVelocityPacket(living);
        }
        if (level() instanceof ServerLevel serverLevel) {
            ItemStack bone = new ItemStack(Items.BONE);
            ItemParticleOption boneParticle = new ItemParticleOption(ParticleTypes.ITEM, bone);
            for (int i = 0; i < 8; i++) {
                serverLevel.sendParticles(boneParticle,
                        getX() + (random.nextDouble() - 0.5D) * 3.0D, getY() + 0.6D + random.nextDouble(),
                        getZ() + (random.nextDouble() - 0.5D) * 3.0D,
                        1, 0.0D, 0.05D, 0.0D, 0.0D);
            }
        }
    }

    private void resolveConstrictRibs() {
        LivingEntity target = shotTarget != null ? shotTarget : getTarget();
        if (target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        double damage = getAttributeValue(Attributes.ATTACK_DAMAGE) + 6.0D;
        if (target.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), (float) damage)) {
            applySoulMeleeDamage(target);
        }
        EntityUtil.disableShield(target, 100);
        target.setDeltaMovement((random.nextDouble() * 2.0D - 1.0D) * 0.4D, 0.8D,
                (random.nextDouble() * 2.0D - 1.0D) * 0.4D);
        target.hurtMarked = true;
        EntityUtil.sendPlayerVelocityPacket(target);
        playSound(SoundEvents.GENERIC_EXPLODE, 0.5F, 0.8F + random.nextFloat() * 0.4F);
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 12; i++) {
                serverLevel.sendParticles(ParticleTypes.POOF, getRandomX(1.0D), getRandomY() + 0.6D, getRandomZ(1.0D),
                        1, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D,
                        0.0D);
            }
        }
    }

    public static final class ServantShootGoal extends Goal {
        private static final double MIN_SHOOT_DISTANCE_SQR = 16.0D;
        private final MutantSkeletonServant servant;
        private final double speedModifier = 1.1D;
        private final float attackRadius = MutantSkeletonServantConfig.SHOOT_RANGE.get().floatValue();
        private LivingEntity target;
        private int attackTime;
        private int seeTime;

        ServantShootGoal(MutantSkeletonServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        private int drawInterval() {
            int base = Math.max(1, MutantSkeletonServantConfig.SHOOT_INTERVAL_TICKS.get());
            return servant.maleficHelmUpgrade ? Math.max(1,
                    (int) Math.round(base * MutantSkeletonServantConfig.MALEFIC_INTERVAL_MULTIPLIER.get())) : base;
        }

        private boolean validTarget() {
            LivingEntity candidate = servant.getTarget();
            return candidate != null && candidate.isAlive() && servant.canAttack(candidate)
                    && servant.distanceToSqr(candidate) > MIN_SHOOT_DISTANCE_SQR;
        }

        @Override
        public boolean canUse() {
            return validTarget();
        }

        @Override
        public boolean canContinueToUse() {
            return validTarget() || servant.isAnimationPlaying();
        }

        @Override
        public void start() {
            target = servant.getTarget();
            attackTime = 0;
            seeTime = 0;
        }

        @Override
        public void stop() {
            target = null;
            seeTime = 0;
            servant.getNavigation().stop();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity current = servant.getTarget();
            if (current != null && current.isAlive() && servant.canAttack(current)) {
                target = current;
            }
            if (target == null || !target.isAlive() || !servant.canAttack(target)) {
                return;
            }
            double distance = servant.distanceToSqr(target);
            boolean visible = servant.hasLineOfSight(target);
            seeTime = visible ? Math.min(20, seeTime + 1) : Math.max(-20, seeTime - 1);
            servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (distance > attackRadius * attackRadius || seeTime < 0) {
                servant.getNavigation().moveTo(target, speedModifier);
            } else {
                servant.getNavigation().stop();
            }
            if (attackTime > 0) {
                attackTime--;
            }
            if (attackTime <= 0 && visible && !servant.isAnimationPlaying()
                    && distance > MIN_SHOOT_DISTANCE_SQR) {
                servant.performRangedAttack(target, 1.0F);
                attackTime = drawInterval();
            }
        }
    }

    public static final class MeleeArtsGoal extends Goal {

        private static final double BONE_SWEEP_REACH_SQR = 9.0D;
        private final MutantSkeletonServant servant;

        private int ticksUntilNextPathRecalculation;

        MeleeArtsGoal(MutantSkeletonServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        private static boolean anyArtEnabled() {
            return MutantSkeletonServantConfig.BONE_SWEEP.get()
                    || MutantSkeletonServantConfig.CONSTRICT_RIBS.get();
        }

        @Override
        public boolean canUse() {
            LivingEntity target = servant.getTarget();
            return anyArtEnabled() && target != null && target.isAlive()
                    && servant.canAttack(target) && !servant.isAnimationPlaying()
                    && servant.distanceToSqr(target) <= 64.0D;
        }

        @Override
        public boolean canContinueToUse() {
            if (servant.isAnimationPlaying()) {
                return true;
            }
            LivingEntity target = servant.getTarget();
            return target != null && target.isAlive() && servant.canAttack(target)
                    && servant.distanceToSqr(target) <= 81.0D;
        }

        @Override
        public void start() {
            servant.getNavigation().stop();
            this.ticksUntilNextPathRecalculation = 0;
        }

        private boolean inConstrictRibsReach(LivingEntity target) {
            double width = servant.getBbWidth() * 2.0D;
            double reachSqr = width * width + target.getBbWidth();
            return servant.distanceToSqr(target) <= reachSqr
                    || servant.getBoundingBox().intersects(target.getBoundingBox());
        }

        private Animation reachableArt(LivingEntity target) {
            boolean sweepReach = servant.distanceToSqr(target) <= BONE_SWEEP_REACH_SQR;
            boolean constrictReach = inConstrictRibsReach(target);
            boolean sweepEnabled = MutantSkeletonServantConfig.BONE_SWEEP.get();
            boolean constrictEnabled = MutantSkeletonServantConfig.CONSTRICT_RIBS.get();
            if (!sweepEnabled || !sweepReach) {
                if (constrictEnabled && constrictReach) return CONSTRICT_RIBS_ANIMATION;
                return null;
            }
            if (!constrictEnabled || !constrictReach) return MELEE_ANIMATION;
            return servant.random.nextInt(4) == 0 ? MELEE_ANIMATION : CONSTRICT_RIBS_ANIMATION;
        }

        @Override
        public void tick() {
            LivingEntity target = servant.getTarget();
            if (target == null) {
                return;
            }
            servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (servant.isAnimationPlaying()) {
                servant.getNavigation().stop();
                if (servant.getAnimation() == CONSTRICT_RIBS_ANIMATION && servant.getAnimationTick() == 5) {
                    target.stopRiding();
                }
                return;
            }
            Animation art = reachableArt(target);
            if (art == null) {
                if (--this.ticksUntilNextPathRecalculation <= 0) {
                    this.ticksUntilNextPathRecalculation = 4 + servant.getRandom().nextInt(7);
                    servant.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.1D);
                }
                return;
            }
            servant.getNavigation().stop();
            if (servant.consecutiveMeleeAttacks >= 5) {
                servant.shotTarget = target;
                servant.sweepResolved = false;
                servant.retreatJump = true;
                servant.consecutiveMeleeAttacks = 0;
                servant.startAnimation(MULTI_SHOT_ANIMATION);
            } else if (servant.random.nextInt(4) == 0) {
                servant.shotTarget = target;
                servant.sweepResolved = false;
                servant.meleeTarget = target;
                servant.consecutiveMeleeAttacks++;
                servant.startAnimation(art);
            }
        }
    }

}
