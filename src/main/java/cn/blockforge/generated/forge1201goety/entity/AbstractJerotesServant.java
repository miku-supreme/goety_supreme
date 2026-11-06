package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ai.servant.ServantFollowOwnerGoal;
import com.Polarice3.Goety.common.entities.ai.servant.ServantPatrolGoal;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.ai.SerponOpenDoorServantGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantOwnerTargetGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;

public abstract class AbstractJerotesServant extends Summoned implements IServant, OwnableEntity {

    public static final int JEROTES_SERVANT_LIFESPAN_TICKS = 60 * 20;

    private static final int WANDERING_FLAG = 1;
    private static final int STAYING_FLAG = 2;
    private static final int NATURAL_FLAG = 4;

    private static final EntityDataAccessor<Byte> SERVANT_FLAGS =
            SynchedEntityData.defineId(AbstractJerotesServant.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Integer> DATA_CAST_TICKS =
            SynchedEntityData.defineId(AbstractJerotesServant.class, EntityDataSerializers.INT);

    private static final double LOST_TARGET_DISTANCE = 32.0D;

    private static final int LOST_TARGET_TICKS = 200;

    private boolean hostile;

    private int lostTargetTicks;

    private int summonPotency;

    public AbstractJerotesServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
    }

    public int getSummonPotency() {
        return summonPotency;
    }

    public void setSummonPotency(int potency) {
        this.summonPotency = Math.max(0, potency);
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    private int spellBurstTicks;

    private LivingEntity spellBurstTarget;

    private java.util.function.Consumer<LivingEntity> spellBurstShot;

    public final void startSpellBurst(int castingTicks, LivingEntity target,
            java.util.function.Consumer<LivingEntity> shot) {
        this.spellBurstTicks = castingTicks;
        this.spellBurstTarget = target;
        this.spellBurstShot = shot;
    }

    public final void tickSpellBurst() {
        if (this.spellBurstTicks <= 0 || this.spellBurstShot == null) {
            return;
        }
        --this.spellBurstTicks;
        if (this.spellBurstTicks <= 0 || this.spellBurstTicks % 5 != 0) {
            return;
        }
        LivingEntity target = this.spellBurstTarget;
        if (target == null || !target.isAlive()) {
            target = getTarget();
        }
        if (target != null && target.isAlive()) {
            this.spellBurstShot.accept(target);
        }
    }

    public static int burstCastingTicks(int spellLevel) {
        return (spellLevel * 3 - 1) * 5 + 4;
    }

    public final void cancelSpellBurst() {
        this.spellBurstTicks = 0;
        this.spellBurstTarget = null;
        this.spellBurstShot = null;
    }

    public static ItemStack jerotesItem(String path) {
        Item item = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
    }

    public static SoundEvent jerotesSound(String path, SoundEvent fallback) {
        SoundEvent event = ForgeRegistries.SOUND_EVENTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return event == null ? fallback : event;
    }

    public static SoundEvent jerotesCoreSound(String path, SoundEvent fallback) {
        SoundEvent event = ForgeRegistries.SOUND_EVENTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        return event == null ? fallback : event;
    }

    public static boolean isJerotesEffect(MobEffectInstance instance, String path) {
        if (instance == null) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.MOB_EFFECTS.getKey(instance.getEffect());
        return id != null && "jerotes".equals(id.getNamespace()) && id.getPath().equals(path);
    }

    public static MobEffect jerotesVillageEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
    }

    public static MobEffect jerotesCoreEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
    }

    public static ParticleOptions jerotesParticle(String path, ParticleOptions fallback) {
        Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return type instanceof SimpleParticleType simple ? simple : fallback;
    }

    public static ParticleOptions jerotesCoreParticle(String path, ParticleOptions fallback) {
        Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        return type instanceof SimpleParticleType simple ? simple : fallback;
    }

    public void castFeedback(String soundPath, SoundEvent fallbackSound,
            String displayParticlePath, ParticleOptions fallbackParticle) {
        if (!isSilent()) {
            playSound(jerotesSound(soundPath, fallbackSound), 1.0F, 1.0F);
        }
        showSpellDisplay(displayParticlePath, fallbackParticle);
    }

    public void castFeedback(String soundPath, SoundEvent fallbackSound,
            String displayParticlePath, ParticleOptions fallbackParticle, int spellLevel) {
        castFeedback(soundPath, fallbackSound, displayParticlePath, fallbackParticle);
        showSpellLevelDisplay(spellLevel);
    }

    public void showSpellDisplay(String displayParticlePath, ParticleOptions fallbackParticle) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(jerotesParticle(displayParticlePath, fallbackParticle),
                    getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public void showSpellLevelDisplay(int spellLevel) {
        if (spellLevel <= 0 || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        String path = "spell_level_" + Math.min(spellLevel, 9);
        serverLevel.sendParticles(jerotesCoreParticle(path, ParticleTypes.ENCHANT),
                getX(), getBoundingBox().maxY + 1.0D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private static final int FIRE_ABSORPTION_SPELL_COOLDOWN_TICKS = 5;

    private static final String FIRE_ABSORPTION_SPELL_TICK = "GoetySupremeFireAbsorptionTick";
    private static final String FIRE_ABSORPTION_SPELL_DAMAGE = "GoetySupremeFireAbsorptionDamage";

    public void castFireAbsorption(DamageSource source, float amount, float percentage) {
        if (amount <= 0.0F || level().isClientSide() || !isAbsorptionFireDamage(source)) {
            return;
        }
        CompoundTag data = getPersistentData();
        long now = level().getGameTime();
        boolean hasLast = data.contains(FIRE_ABSORPTION_SPELL_DAMAGE);
        long lastTick = data.getLong(FIRE_ABSORPTION_SPELL_TICK);
        float lastDamage = data.getFloat(FIRE_ABSORPTION_SPELL_DAMAGE);
        if (hasLast && now - lastTick < FIRE_ABSORPTION_SPELL_COOLDOWN_TICKS
                && amount <= lastDamage) {
            return;
        }
        data.putLong(FIRE_ABSORPTION_SPELL_TICK, now);
        data.putFloat(FIRE_ABSORPTION_SPELL_DAMAGE, amount);
        heal(amount * (percentage / 100.0F));
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 2; ++i) {
                serverLevel.sendParticles(jerotesCoreParticle("flame", ParticleTypes.FLAME),
                        getRandomX(1.0D), getY(), getRandomZ(1.0D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            if (!isInvisible()) {
                serverLevel.sendParticles(
                        jerotesCoreParticle("fire_absorption_display", ParticleTypes.FLAME),
                        getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (!isSilent()) {
            playSound(jerotesCoreSound("magic_fire_absorption", SoundEvents.FIRECHARGE_USE), 1.0F, 1.0F);
        }
    }

    private static boolean isAbsorptionFireDamage(DamageSource source) {
        return source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.ON_FIRE);
    }

    private static final int COLD_ABSORPTION_SPELL_COOLDOWN_TICKS = 5;

    private static final String COLD_ABSORPTION_SPELL_TICK = "GoetySupremeColdAbsorptionTick";
    private static final String COLD_ABSORPTION_SPELL_DAMAGE = "GoetySupremeColdAbsorptionDamage";

    public void castColdAbsorption(DamageSource source, float amount, float percentage) {
        if (amount <= 0.0F || level().isClientSide() || !source.is(DamageTypeTags.IS_FREEZING)) {
            return;
        }
        CompoundTag data = getPersistentData();
        long now = level().getGameTime();
        boolean hasLast = data.contains(COLD_ABSORPTION_SPELL_DAMAGE);
        long lastTick = data.getLong(COLD_ABSORPTION_SPELL_TICK);
        float lastDamage = data.getFloat(COLD_ABSORPTION_SPELL_DAMAGE);
        if (hasLast && now - lastTick < COLD_ABSORPTION_SPELL_COOLDOWN_TICKS
                && amount <= lastDamage) {
            return;
        }
        data.putLong(COLD_ABSORPTION_SPELL_TICK, now);
        data.putFloat(COLD_ABSORPTION_SPELL_DAMAGE, amount);
        heal(amount * (percentage / 100.0F));
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 2; ++i) {
                serverLevel.sendParticles(jerotesCoreParticle("frost", ParticleTypes.SNOWFLAKE),
                        getRandomX(1.0D), getY(), getRandomZ(1.0D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            if (!isInvisible()) {
                serverLevel.sendParticles(
                        jerotesCoreParticle("cold_absorption_display", ParticleTypes.SNOWFLAKE),
                        getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (!isSilent()) {
            playSound(jerotesCoreSound("magic_cold_absorption", SoundEvents.GLASS_BREAK), 2.0F, 1.0F);
        }
    }

    public void equipJerotes(EquipmentSlot slot, String itemPath) {
        ItemStack stack = jerotesItem(itemPath);
        if (!stack.isEmpty()) {
            setItemSlot(slot, stack);
        }
    }

    public static boolean isGiveableWeapon(ItemStack stack) {
        if (stack == null || stack.isEmpty() || GoetyStaffGuard.isGoetyStaff(stack)) {
            return false;
        }
        if (JerotesRangedWeaponGoal.isRangedWeapon(stack)) {
            return true;
        }
        return stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)
                || stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .containsKey(Attributes.ATTACK_DAMAGE);
    }

    public InteractionResult giveEquipmentFromOwner(Player player, InteractionHand hand, boolean allowRanged) {
        if (player.isSpectator() || !isAlive()) {
            return null;
        }
        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty() || !isOwnedThroughServants(player)) {
            return null;
        }
        EquipmentSlot slot = equipmentSlotForGiving(held, allowRanged);
        if (slot == null) {
            return null;
        }
        if (!level().isClientSide()) {
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
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private static EquipmentSlot equipmentSlotForGiving(ItemStack held, boolean allowRanged) {
        Equipable equipable = Equipable.get(held);
        if (equipable != null) {
            EquipmentSlot slot = equipable.getEquipmentSlot();
            if (slot != null && slot.getType() == EquipmentSlot.Type.ARMOR) {
                return slot;
            }
        }
        if (!isGiveableWeapon(held)) {
            return null;
        }
        if (!allowRanged && JerotesRangedWeaponGoal.isRangedWeapon(held)) {
            return null;
        }
        return EquipmentSlot.MAINHAND;
    }

    public boolean isOwnedThroughServants(Player player) {
        LivingEntity owner = getTrueOwner();
        for (int depth = 0; owner != null && depth < 16; depth++) {
            if (owner == player) {
                return true;
            }
            owner = owner instanceof AbstractJerotesServant servant ? servant.getTrueOwner()
                    : owner instanceof OwnableEntity ownable ? ownable.getOwner() : null;
        }
        return false;
    }

    public static boolean isJerotesEntity(Entity entity, String path) {
        if (entity == null) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return id != null && "jerotesvillage".equals(id.getNamespace()) && id.getPath().equals(path);
    }

    public boolean holdsJerotesItem(String path) {
        ItemStack main = getMainHandItem();
        if (main.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(main.getItem());
        return id != null && "jerotesvillage".equals(id.getNamespace()) && id.getPath().equals(path);
    }

    public boolean holdsJerotesReachWeapon() {
        if (FellSpearItem.isSpear(getMainHandItem())) {
            return true;
        }
        return holdsJerotesItem("ancient_second_rounder_warspear")
                || holdsJerotesItem("ancient_second_rounder_pike")
                || holdsJerotesItem("serpon_spear")
                || holdsJerotesItem("serpon_pike")
                || holdsJerotesItem("serpon_glaive");
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity target) {
        return holdsJerotesReachWeapon() ? 9.0D : super.getMeleeAttackRangeSqr(target);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SERVANT_FLAGS, (byte) 0);
        entityData.define(DATA_CAST_TICKS, 0);
    }

    public int getCastTicks() {
        return entityData.get(DATA_CAST_TICKS);
    }

    public void setCastTicks(int ticks) {
        entityData.set(DATA_CAST_TICKS, Math.max(0, ticks));
    }

    public boolean isCasting() {
        return getCastTicks() > 0;
    }

    public boolean floats() {
        return true;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal ->
                goal instanceof ServantPatrolGoal || goal instanceof ServantFollowOwnerGoal);
        this.targetSelector.removeAllGoals(goal -> true);
        if (opensDoors()) {
            if (getNavigation() instanceof GroundPathNavigation groundNavigation) {
                groundNavigation.setCanOpenDoors(true);
            }
            this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
            this.goalSelector.addGoal(0, new SerponOpenDoorServantGoal(this, true));
        }
        if (floats()) {
            this.goalSelector.addGoal(0, new FloatGoal(this));
        }
        this.targetSelector.addGoal(1, new ServantOwnerTargetGoal.HurtBy<>(this));
        this.targetSelector.addGoal(2, new ServantOwnerTargetGoal.Hurt<>(this));
        this.targetSelector.addGoal(1, new ServantHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new SummonTargetGoal(this));
        registerCombatGoals();
        if (this.getNavigation() instanceof FlyingPathNavigation) {
            this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        } else {
            this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        }
        this.goalSelector.addGoal(7, new CommandPositionGoal(this, 1.15D));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        if (JerotesVillageCompat.isJerotesSpearGoalLoadable()) {
            JvillageSpearGoals.attachIfEligible(this.goalSelector, this);
        }
        if (!FellSpearAttackGoal.isAttached(this.goalSelector)) {
            this.goalSelector.addGoal(0, new FellSpearAttackGoal(this));
        }
    }

    public abstract void registerCombatGoals();

    public boolean opensDoors() {
        return true;
    }

    public abstract String servantTexturePath();

    public abstract JerotesVillageServantConfig.Section configSection();

    @Override
    public void setConfigurableAttributes() {
        configSection().applyTo(this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        setConfigurableAttributes();
        equipDefaultGear(random);
        setHealth(getMaxHealth());
        return result;
    }

    public abstract void equipDefaultGear(RandomSource random);

    @Override
    public void convertNewEquipment(Entity original) {
        setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        equipDefaultGear(getRandom());
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
        tag.putInt("SummonPotency", summonPotency);
        tag.putInt("commandTick", commandTick);
        if (commandPos != null) {
            tag.put("commandPos", NbtUtils.writeBlockPos(commandPos));
        }
        if (boundPos != null) {
            tag.put("boundPos", NbtUtils.writeBlockPos(boundPos));
            tag.putString("boundDim", boundDim);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        }
        setWandering(tag.getBoolean("wandering"));
        setStaying(tag.getBoolean("staying"));
        summonPotency = tag.contains("SummonPotency") ? Math.max(0, tag.getInt("SummonPotency")) : 0;
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        setHealth(Math.min(getHealth(), getMaxHealth()));
        if (tag.contains("commandPos", 10)) {
            commandPos = NbtUtils.readBlockPos(tag.getCompound("commandPos")).immutable();
        }
        if (tag.contains("boundPos", 10)) {
            boundPos = NbtUtils.readBlockPos(tag.getCompound("boundPos")).immutable();
            boundDim = tag.contains("boundDim")
                    ? tag.getString("boundDim") : level().dimension().location().toString();
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
        super.setTarget(target != null && canAttack(target) ? target : null);
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
        if (entity instanceof OwnableEntity ownable && owner != null && ownable.getOwner() == owner) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            tickTargetWatch();
        }
        if (!level().isClientSide && getCastTicks() > 0) {
            setCastTicks(getCastTicks() - 1);
        }
        updateCommand();
        updateGuardPosition();
        if (isStaying() && getTarget() == null) {
            getNavigation().stop();
        }
        if (isCasting()) {
            getNavigation().stop();
        }
        ServantTeleportHelper.tick(this);
    }

    private void tickTargetWatch() {
        LivingEntity target = getTarget();
        if (target == null) {
            lostTargetTicks = 0;
            return;
        }
        if (isTargetGone(target)) {
            releaseTarget();
            return;
        }
        if (distanceToSqr(target) > LOST_TARGET_DISTANCE * LOST_TARGET_DISTANCE
                && !getSensing().hasLineOfSight(target)) {
            if (++lostTargetTicks >= LOST_TARGET_TICKS) {
                releaseTarget();
            }
        } else {
            lostTargetTicks = 0;
        }
    }

    public boolean isTargetGone(LivingEntity target) {
        return target == null || target.isRemoved() || !target.isAlive()
                || target.level() != level()
                || (target instanceof Player player
                        && (player.isCreative() || player.isSpectator()));
    }

    public void releaseTarget() {
        lostTargetTicks = 0;
        setTarget(null);
        if (isUsingItem()) {
            UseAnim anim = getUseItem().getUseAnimation();
            boolean rangedInUse = getUseItem().getItem() instanceof BowItem
                    || getUseItem().getItem() instanceof CrossbowItem
                    || anim == UseAnim.BOW || anim == UseAnim.CROSSBOW || anim == UseAnim.SPEAR;
            if (rangedInUse) {
                stopUsingItem();
            }
        }
        getNavigation().stop();
    }

    private void updateCommand() {
        if (!isCommanded() || getTarget() != null) {
            return;
        }
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
        if (!isGuardingArea() || getTarget() == null || boundPos == null) {
            return;
        }
        if (getTarget().distanceToSqr(boundPos.getX() + 0.5D, boundPos.getY(), boundPos.getZ() + 0.5D)
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) {
            releaseTarget();
        }
    }

    @Override
    public void setNatural(boolean natural) {
        setFlag(NATURAL_FLAG, natural);
    }

    @Override
    public boolean isNatural() {
        return getFlag(NATURAL_FLAG);
    }

    @Override
    public boolean isWandering() {
        return getFlag(WANDERING_FLAG);
    }

    @Override
    public void setWandering(boolean value) {
        setFlag(WANDERING_FLAG, value);
    }

    @Override
    public boolean isStaying() {
        return getFlag(STAYING_FLAG) && !isCommanded();
    }

    @Override
    public void setStaying(boolean value) {
        setFlag(STAYING_FLAG, value);
        if (value) {
            getNavigation().stop();
        }
    }

    @Override
    public boolean canUpdateMove() {
        return true;
    }

    @Override
    public void updateMoveMode(Player player) {
        ServantMoveModes.cycle(this, player);
    }

    @Override
    public void setCommandPos(BlockPos pos) {
        setCommandPos(pos, true);
    }

    @Override
    public void setCommandPos(BlockPos pos, boolean removeEntity) {
        if (removeEntity) {
            commandPosEntity = null;
        }
        commandPos = pos == null ? null : pos.immutable();
        commandTick = pos == null ? 0 : (removeEntity ? 200 : Integer.MAX_VALUE);
        setStaying(false);
    }

    @Override
    public BlockPos getCommandPos() {
        return commandPos;
    }

    @Override
    public void setCommandPosEntity(LivingEntity entity) {
        commandPosEntity = entity;
        commandPos = entity == null ? null : entity.blockPosition();
        commandTick = entity == null ? 0 : Integer.MAX_VALUE;
        setStaying(false);
    }

    @Override
    public LivingEntity getCommandPosEntity() {
        return commandPosEntity;
    }

    @Override
    public int getCommandTick() {
        return commandTick;
    }

    @Override
    public void setCommandTick(int ticks) {
        commandTick = ticks;
    }

    @Override
    public boolean isCommanded() {
        return commandPos != null || commandPosEntity != null;
    }

    @Override
    public BlockPos getBoundPos() {
        return boundPos;
    }

    @Override
    public void setBoundPos(BlockPos pos) {
        boundPos = pos == null ? null : pos.immutable();
        if (boundPos != null) {
            boundDim = level().dimension().location().toString();
        }
    }

    @Override
    public String getBoundDim() {
        return boundDim;
    }

    @Override
    public void setBoundDim(String dimension) {
        boundDim = dimension == null ? level().dimension().location().toString() : dimension;
    }

    @Override
    public void tryKill(Player player) {
        if (!level().isClientSide) {
            hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
        }
    }

    public void m_6472_(DamageSource source, float damage) {
        if (damage <= 0.0F) {
            return;
        }
        float reduced = Math.max(1.0F, damage / 4.0F);
        boolean fire = source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) {
                continue;
            }
            ItemStack stack = getItemBySlot(slot);
            if (fire && stack.getItem().isFireResistant()) {
                continue;
            }
            if (!(stack.getItem() instanceof net.minecraft.world.item.ArmorItem)) {
                continue;
            }
            int amount = (int) Math.min(reduced, Math.max(0, stack.getMaxDamage()));
            if (amount > 0) {
                stack.hurtAndBreak(amount, this, e -> e.broadcastBreakEvent(slot));
            }
        }
    }

    private boolean getFlag(int flag) {
        return (entityData.get(SERVANT_FLAGS) & flag) != 0;
    }

    private void setFlag(int flag, boolean value) {
        byte state = entityData.get(SERVANT_FLAGS);
        entityData.set(SERVANT_FLAGS, value ? (byte) (state | flag) : (byte) (state & ~flag));
    }
}
