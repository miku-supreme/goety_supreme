package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.api.items.IPersist;
import com.Polarice3.Goety.api.items.ISoulRepair;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.projectiles.ScytheSlash;
import com.Polarice3.Goety.common.entities.projectiles.VoidSlash;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.neutral.Wartling;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModTiers;
import com.Polarice3.Goety.common.items.equipment.BladeOfEnderItem;
import com.Polarice3.Goety.common.items.equipment.DarkScytheItem;
import com.Polarice3.Goety.common.items.equipment.DeathScytheItem;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ItemHelper;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import com.mojang.datafixers.util.Pair;
import java.util.function.Supplier;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.extensions.IForgeItemStack;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.debug.WandFocusProbe;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobBowAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobCrossbowAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobEnderPearlGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobFocusFireEscapeGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobSakuraQiGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobThrowAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobTrappedEscapeGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.item.ArcaneScrollItem;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.api.FellSpearPlayerLike;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.item.GreenAppleItem;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrownJavelin;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrowShoot;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.spell.PlayerMobFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantGoals;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryMenu;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobStrafeMoveControl;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;
import cn.blockforge.generated.goetysupreme.event.ServantArrowDespawnEvents;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownEnderpearl;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownTrident;

public class PlayerMobServant extends Summoned
        implements IServant, RangedAttackMob, CrossbowAttackMob, MenuProvider,
        FellSpearPlayerLike {
    private static final EntityDataAccessor<Boolean> CHARGING_CROSSBOW =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> MOB_NAME =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Integer> FOOD_LEVEL =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> FOOD_SATURATION =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> CHASE_SPRINTING =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.BOOLEAN);

    static final EntityDataAccessor<Boolean> ARCANE_SCROLL =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.BOOLEAN);

    private int summonPotency;

    public static final float POTENCY_RANGED_DAMAGE_BONUS = 1.0F;

    public static final int STORAGE_SIZE = 10;

    public static final int ARCANE_STORAGE_SIZE = 15;

    public static final String NO_DROP_TAG = "GoetySupremeNoDrop";

    public PlayerMobServant(EntityType<? extends PlayerMobServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        this.moveControl = new PlayerMobStrafeMoveControl(this);
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, PlayerMobServantConfig.MAX_HEALTH.get())
                .add(Attributes.FOLLOW_RANGE, PlayerMobServantConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, PlayerMobServantConfig.MOVEMENT_SPEED.get())
                .add(Attributes.ATTACK_DAMAGE, PlayerMobServantConfig.MELEE_DAMAGE.get())
                .add(Attributes.ARMOR, PlayerMobServantConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, PlayerMobServantConfig.ARMOR_TOUGHNESS.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, PlayerMobServantConfig.KNOCKBACK_RESISTANCE.get())
                .add(ForgeMod.ENTITY_REACH.get(), 3.0D);
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), PlayerMobServantConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), PlayerMobServantConfig.MOVEMENT_SPEED.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), PlayerMobServantConfig.MELEE_DAMAGE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), PlayerMobServantConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), PlayerMobServantConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                PlayerMobServantConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), PlayerMobServantConfig.FOLLOW_RANGE.get());
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    @Override
    public void healServant() {
        this.healing.healServant();
    }


    private static void setBaseAttribute(AttributeInstance attribute, double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    private final PlayerMobServantHealing healing = new PlayerMobServantHealing(this);
    private final PlayerMobServantEquipment equipment = new PlayerMobServantEquipment(this);
    private final PlayerMobServantFood food = new PlayerMobServantFood(this);
    final PlayerMobServantStorage storage = new PlayerMobServantStorage(this);
    private final PlayerMobWeapons weapons = new PlayerMobWeapons(this);
    private final PlayerMobEnderPearl enderPearl = new PlayerMobEnderPearl(this);
    private final PlayerMobWandMode wandMode = new PlayerMobWandMode(this);
    private PlayerMobServantThreat threat;

    private PlayerMobServantThreat threat() {
        if (this.threat == null) {
            this.threat = new PlayerMobServantThreat(this);
        }
        return this.threat;
    }

    private int shieldDisableTicks;
    private int shieldBlockCount;

    private boolean fixedCustomName;

    final SimpleContainer servantStorage = new SimpleContainer(ARCANE_STORAGE_SIZE);
    final FoodData servantFood = new FoodData();
    boolean survivalInitialized;
    boolean starterFoodGiven;
    private boolean storageDropped;
    int hungerRegenTick;
    double lastExhaustX;
    double lastExhaustZ;




     public boolean eatingBite;
    ItemStack eatWeaponStash = ItemStack.EMPTY;
    int eatPendingNutrition;
    float eatPendingSaturationMod;
    int eatRestartCooldown;

    int eatWalkRepath;
    @Nullable
    Vec3 eatWalkDest;

    @Nullable
    PlayerMobEatMode pendingPlayerMobEatMode;

     public boolean fleeEatActive;

     public int fleeEatCooldown;

    final java.util.ArrayDeque<Integer> recentHurtTicks = new java.util.ArrayDeque<>();

    float hurtDamageSinceEscape;

    boolean panicEscapeLatched;

    int focusFireEscapeCooldown;

    int partingShotCooldown;

    public static final int FOCUS_FIRE_LONG_WINDOW_TICKS = 40;

    public static final float PANIC_ESCAPE_HP_FRACTION = 0.25F;

    public static final int FOCUS_FIRE_COOLDOWN_TICKS = 60;

    public static final int PARTING_SHOT_COOLDOWN_TICKS = 40;

    private static final double ATTACKER_SCAN_RADIUS = 16.0D;

    int attackerCacheTick = -1000;
    List<LivingEntity> attackerCache = new ArrayList<>();

    private int shieldToggleWindowTicks;
    private int shieldToggleCount;
    private boolean lastCrouchGuard;

    private int crouchGuardNoMeleeBlocks;

    private int forcedStandTicks;

    private int crossbowSpecialAmmo;

    int enderPearlCooldown;

    int weaponSlashCooldown;

    private static final int WEAPON_SLASH_INTERVAL_TICKS = 40;

    private static final int SOUL_REPAIR_INTERVAL_TICKS = 20;

    private static final EquipmentSlot[] SOUL_REPAIR_SLOTS = {
            EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private int hazardEscapeCooldown;

    private final Map<String, Integer> wandFocusCooldowns = new HashMap<>();
    private int wandSummonCooldown;
    private final Map<String, List<String>> wandBuffEffects = new HashMap<>();

    private boolean wandLongWindup;

    private boolean wandPartingShot;

    private boolean wandSneakCast;

    private LivingEntity escapePartingShotTarget;

    private boolean escapePartingShotInstant;

    boolean meleeWandMode;

    boolean meleeWandStashed;

    boolean weakeningInterlude;
    int weakeningInterludeTicks;

    boolean healInterlude;
    int healInterludeTicks;

    int healWandSwapSlot = -1;

    private static final EntityDataAccessor<Integer> CASTING_HAND =
            SynchedEntityData.defineId(PlayerMobServant.class, EntityDataSerializers.INT);

    static final double ENDER_PEARL_GRAVITY = 0.03D;
    static final double ENDER_PEARL_DRAG = 0.99D;

    static final double ENDER_PEARL_MIN_SPEED = 0.5D;
    static final double ENDER_PEARL_MAX_SPEED = 2.0D;

    static final double ENDER_PEARL_LANDING_RADIUS = 1.8D;

    static final int ENDER_PEARL_RETRY_TICKS = 10;

    public void populateDefaultWeapons(RandomSource random, DifficultyInstance difficulty) {
        this.equipment.populateDefaultWeapons(random, difficulty);
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        if (this.equipment.isConfigured()) {
            return;
        }
        super.populateDefaultEquipmentSlots(random, difficulty);
        if (!this.equipment.isConfigured()) {
            this.populateDefaultWeapons(random, difficulty);
        }
    }

    private void applyRingArmorIfMissing(RandomSource random) {
        this.equipment.applyRingArmorIfMissing(random);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        setConfigurableAttributes();
        setHealth(getMaxHealth());
        ensureMobName();
        populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        applyRingArmorIfMissing(level.getRandom());
        initServantSurvival();
        setPersistenceRequired();
        setWandering(false);
        setStaying(false);
        return result;
    }

    public String getMobName() {
        return entityData.get(MOB_NAME);
    }

    public int getSummonPotency() {
        return summonPotency;
    }

    public void setSummonPotency(int potency) {
        this.summonPotency = Math.max(0, potency);
    }

    public void setMobName(String name) {
        String normalized = name == null ? "" : name.trim();
        if (!PlayerMobServantConfig.isMobName(normalized)) {
            normalized = "";
        }
        fixedCustomName = false;
        entityData.set(MOB_NAME, normalized);
        if (!normalized.isEmpty() && getCustomName() == null) {
            setCustomName(Component.literal(normalized));
        }
    }

    public void setFixedCustomName(Component name) {
        if (name == null || name.getString().trim().isEmpty()) {
            return;
        }
        fixedCustomName = true;
        String plainName = name.getString().trim();
        entityData.set(MOB_NAME, PlayerMobServantConfig.isMobName(plainName) ? plainName : "");
        setCustomName(name);
        setCustomNameVisible(true);
    }

    public boolean isFixedCustomName() {
        return fixedCustomName;
    }

    private void ensureMobName() {
        if (level().isClientSide || fixedCustomName || !getMobName().isEmpty() || getCustomName() != null) {
            return;
        }
        List<String> names = PlayerMobServantConfig.mobNames();
        if (!names.isEmpty()) {
            setMobName(names.get(getRandom().nextInt(names.size())));
        }
    }

    @Override
    public void readServantData(CompoundTag tag) {
        super.readServantData(tag);
        loadWandCastingData(tag);
        if (tag.getBoolean("PlayerMobFixedName")) {
            fixedCustomName = true;
            String fixedMobName = tag.contains("PlayerMobFixedMobName")
                    ? tag.getString("PlayerMobFixedMobName") : "";
            if (PlayerMobServantConfig.isMobName(fixedMobName)) {
                entityData.set(MOB_NAME, fixedMobName);
            } else if (getCustomName() != null
                    && PlayerMobServantConfig.isMobName(getCustomName().getString().trim())) {
                entityData.set(MOB_NAME, getCustomName().getString().trim());
            } else {
                entityData.set(MOB_NAME, "");
            }
            if (getCustomName() != null) {
                setCustomNameVisible(true);
            }
            return;
        }
        if (tag.contains("PlayerMobName")) {
            setMobName(tag.getString("PlayerMobName"));
        } else if (getCustomName() != null) {
            String matched = PlayerMobServantConfig.matchMobName(getCustomName().getString());
            if (matched != null) {
                setMobName(matched);
            }
        }
    }

    @Override
    public void saveServantData(CompoundTag tag) {
        super.saveServantData(tag);
        saveWandCastingData(tag);
        if (fixedCustomName) {
            tag.putBoolean("PlayerMobFixedName", true);
            String fixedMobName = getMobName();
            if (PlayerMobServantConfig.isMobName(fixedMobName)) {
                tag.putString("PlayerMobFixedMobName", fixedMobName);
            }
            return;
        }
        if (!getMobName().isEmpty()) {
            tag.putString("PlayerMobName", getMobName());
        }
    }


    @Override
    public void registerGoals() {
        super.registerGoals();
        goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal
                || goal instanceof RandomStrollGoal
                || goal instanceof LookAtPlayerGoal);
        targetSelector.removeAllGoals(goal -> goal instanceof NearestAttackableTargetGoal);

        goalSelector.addGoal(0, new PlayerMobTrappedEscapeGoal(this));
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PlayerMobFocusFireEscapeGoal(this));
        goalSelector.addGoal(1, new PlayerMobServantGoals.PlayerMobFleeEatGoal(this));
        goalSelector.addGoal(1, new PlayerMobServantGoals.PlayerMobEatGoal(this));
        goalSelector.addGoal(2, new PlayerMobEnderPearlGoal(this));
        goalSelector.addGoal(2, new PlayerMobWandSpellGoal(this));
        goalSelector.addGoal(2, new PlayerMobThrowAttackGoal(this));
        goalSelector.addGoal(2, new PlayerMobSakuraQiGoal(this));
        goalSelector.addGoal(3, new PlayerMobServantGoals.AdaptiveWeaponGoal(this, 1.0D));
        goalSelector.addGoal(3, new PlayerMobServantGoals.PlayerMobTridentAttackGoal(this));
        goalSelector.addGoal(4, new PlayerMobBowAttackGoal(this, 1.0D, 20, 20.0F));
        goalSelector.addGoal(4, new PlayerMobCrossbowAttackGoal(this, 1.0D, 15.0F));
        goalSelector.addGoal(6, new PlayerMobServantGoals.PlayerCommandPositionGoal(this, 1.0D));
        goalSelector.addGoal(7, new PlayerMobServantGoals.PlayerMobPickupGoal(this));
        goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.001F));
        goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        targetSelector.addGoal(3, new SummonTargetGoal(this));
        targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                target -> isValidCombatTargetForGoal(target)));
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide) {
            ensureMobName();
            if (this.hasArcaneScroll()) {
                this.tickArcaneScroll();
            }
            tickServantSurvival();
            if (tickCount % 40 == 0) {
                tickMeleeWandMode();
                tryUpgradeGearFromStorage();
            }
            if (shieldDisableTicks > 0) {
                shieldDisableTicks--;
            }
            if (enderPearlCooldown > 0) {
                enderPearlCooldown--;
            }
            if (weaponSlashCooldown > 0) {
                weaponSlashCooldown--;
            }
            if (hazardEscapeCooldown > 0) {
                hazardEscapeCooldown--;
            }
            if (fleeEatCooldown > 0) {
                fleeEatCooldown--;
            }
            if (focusFireEscapeCooldown > 0) {
                focusFireEscapeCooldown--;
            }
            if (partingShotCooldown > 0) {
                partingShotCooldown--;
            }
            tickWandCastingState();
            if (getTarget() != null && !meleeWandMode && (tickCount & 3) == 0
                    && isSupportOnlyWandSetup()
                    && !PlayerMobWandCasting.hasUsableSupportFocus(this, getOffhandItem())) {
                enterMeleeWandMode();
            }
            tickHealInterlude();
            tickWeakeningInterlude();
            if (isUsingShield() && !canGuardWithShield(getTarget())) {
                stopShieldGuard();
            }
            if (shieldDisableTicks == 0 && (!isUsingItem() || !isShieldItem(getUseItem()))) {
                shieldBlockCount = 0;
                crouchGuardNoMeleeBlocks = 0;
            }
            tickSakuraGear();
            tickSoulRepair();
            keepEyesOnTargetWhileEating();
        }
        super.aiStep();
        updateCombatCrouch(getTarget());
        if (!level().isClientSide) {
            updateSneakSpeedModifier();
            updateCastFollowRange();
            if (panicEscapeLatched && getHealth() >= getMaxHealth()) {
                panicEscapeLatched = false;
            }
            updateShieldAntiAbuse();
        }
        if (!level().isClientSide) {
            if (getTarget() != null && !isValidCombatTargetForGoal(getTarget())) {
                setTarget(null);
            }
            if (getTarget() == null && isSprinting()) {
                setCombatChaseSprint(false);
            }
        }
        ServantTeleportHelper.tick(this);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (PlayerMobWandCasting.isNamespace(held, "goetydelight")) {
            return InteractionResult.PASS;
        }
        if (held.isEmpty() && hand == InteractionHand.MAIN_HAND && !player.isSecondaryUseActive()
                && getOwnerId() != null && getOwnerId().equals(player.getUUID())) {
            if (!level().isClientSide) {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, this, buf -> buf.writeInt(getId()));
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        EquipmentSlot slot = equipmentSlotForGift(held);
        if (slot == null || getOwnerId() == null || !getOwnerId().equals(player.getUUID())) {
            return super.mobInteract(player, hand);
        }

        if (!level().isClientSide) {
            ItemStack previous = getItemBySlot(slot).copy();
            float previousDropChance = getEquipmentDropChance(slot);
            int given = slot.getType() == EquipmentSlot.Type.ARMOR ? 1 : held.getCount();
            ItemStack equipped = held.copyWithCount(given);
            setItemSlot(slot, equipped);
            setGuaranteedDrop(slot);

            if (!player.getAbilities().instabuild) {
                held.shrink(given);
            }
            RingServantGear.returnReplaced(player, previous, previousDropChance);

            Equipable equipable = Equipable.get(equipped);
            playSound(equipable == null ? SoundEvents.ITEM_PICKUP : equipable.getEquipSound(), 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    private static EquipmentSlot equipmentSlotForGift(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        if (GoetyStaffGuard.isGoetyStaff(stack)) {
            return null;
        }

        Equipable equipable = Equipable.get(stack);
        if (equipable != null) {
            EquipmentSlot slot = equipable.getEquipmentSlot();
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.OFFHAND) {
                return slot;
            }
        }

        Item item = stack.getItem();
        if (item instanceof ProjectileWeaponItem || isTridentLike(stack)
                || stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)
                || stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this || entity == getTrueOwner()) {
            return true;
        }
        if (entity instanceof LivingEntity living && isAllyWith(living)) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target != null && isValidCombatTargetForGoal(target) ? target : null);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem weapon) {
        return weapon instanceof BowItem || weapon instanceof CrossbowItem;
    }

    @Override
    public ItemStack getProjectile(ItemStack weapon) {
        if (weapon.getItem() instanceof ProjectileWeaponItem projectileWeapon) {
            boolean crossbow = weapon.getItem() instanceof CrossbowItem;
            ItemStack special = findPreferredAmmo(crossbow);
            if (!special.isEmpty()) {
                crossbowSpecialAmmo = special.is(Items.TIPPED_ARROW) ? AMMO_TIPPED : AMMO_FIREWORK;
                return special;
            }
            crossbowSpecialAmmo = AMMO_PLAIN;
            ItemStack held = ProjectileWeaponItem.getHeldProjectile(this,
                    projectileWeapon.getSupportedHeldProjectiles());
            return net.minecraftforge.common.ForgeHooks.getProjectile(this, weapon,
                    held.isEmpty() ? new ItemStack(Items.ARROW) : held);
        }
        return net.minecraftforge.common.ForgeHooks.getProjectile(this, weapon, ItemStack.EMPTY);
    }

    private static final int AMMO_PLAIN = 0;

    private static final int AMMO_TIPPED = 1;

    private static final int AMMO_FIREWORK = 2;

    private ItemStack findPreferredAmmo(boolean allowFirework) {
        ItemStack off = getOffhandItem();
        if (off.is(Items.TIPPED_ARROW)) {
            return off;
        }
        for (int i = 0; i < servantStorage.getContainerSize(); i++) {
            ItemStack stack = servantStorage.getItem(i);
            if (stack.is(Items.TIPPED_ARROW)) {
                return stack;
            }
        }
        if (allowFirework) {
            if (off.is(Items.FIREWORK_ROCKET)) {
                return off;
            }
            for (int i = 0; i < servantStorage.getContainerSize(); i++) {
                ItemStack stack = servantStorage.getItem(i);
                if (stack.is(Items.FIREWORK_ROCKET)) {
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private void consumeSpecialAmmo(boolean tipped, boolean firework) {
        if (level().isClientSide) {
            return;
        }
        Predicate<ItemStack> match = tipped ? stack -> stack.is(Items.TIPPED_ARROW)
                : stack -> stack.is(Items.FIREWORK_ROCKET);
        if (!tipped && !firework) {
            return;
        }
        ItemStack off = getOffhandItem();
        if (match.test(off)) {
            off.shrink(1);
            return;
        }
        for (int i = 0; i < servantStorage.getContainerSize(); i++) {
            ItemStack stack = servantStorage.getItem(i);
            if (match.test(stack)) {
                stack.shrink(1);
                return;
            }
        }
    }

    @Override
    public org.joml.Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 ignored, float angle) {
        LivingEntity target = getTarget();
        Vec3 origin = getEyePosition();
        Vec3 aim = target != null && target.isAlive()
                ? target.getBoundingBox().getCenter()
                : origin.add(getViewVector(1.0F));
        Vec3 direction = aim.subtract(origin).normalize();
        org.joml.Vector3f shot = new org.joml.Vector3f((float) direction.x, (float) direction.y,
                (float) direction.z);
        shot.rotateY(angle * ((float) Math.PI / 180.0F));
        return shot;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack weapon = getMainHandItem();
        if (weapon.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(weapon)) {
            performCrossbowAttack(this, 1.6F);
            crossbowSpecialAmmo = AMMO_PLAIN;
        } else if (weapon.getItem() instanceof BowItem) {
            ItemStack arrowStack = getProjectile(weapon);
            if (arrowStack.isEmpty()) {
                arrowStack = new ItemStack(net.minecraft.world.item.Items.ARROW);
            }
            AbstractArrow arrow = ProjectileUtil.getMobArrow(this, arrowStack, distanceFactor);
            markServantArrow(arrow);
            BallisticAim.Solution aim = BallisticAim.solveArrow(arrow.position(), target,
                    BallisticAim.ARROW_MIN_SPEED, ARROW_MAX_SPEED);
            Vec3 shot = aim.velocity();
            applyConfiguredArrowDamage(arrow, shot.length());
            arrow.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
            playSound(SoundEvents.ARROW_SHOOT, 1.0F,
                    1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
            level().addFreshEntity(arrow);
            consumeBowAmmo(arrowStack);
            servantFood.addExhaustion(0.1F);
        }
    }

    private void consumeBowAmmo(ItemStack arrowStack) {
        if (level().isClientSide) {
            return;
        }
        if (arrowStack.is(Items.TIPPED_ARROW) || arrowStack.is(Items.FIREWORK_ROCKET)) {
            arrowStack.shrink(1);
        }
    }

    @Override
    public void setChargingCrossbow(boolean charging) {
        entityData.set(CHARGING_CROSSBOW, charging);
    }

    public boolean isChargingCrossbow() {
        return entityData.get(CHARGING_CROSSBOW);
    }

    @Override
    public boolean goetySupreme$useSpearAsPlayer() {
        return true;
    }

    @Override
    public void onCrossbowAttackPerformed() {
        noActionTime = 0;
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity target, ItemStack crossbow, Projectile projectile,
            float projectileAngle) {
        markServantArrow(projectile);
        if (projectile instanceof net.minecraft.world.entity.projectile.FireworkRocketEntity) {
            Vec3 aim = target.getBoundingBox().getCenter().subtract(projectile.position());
            if (aim.lengthSqr() > 1.0E-6D) {
                aim = aim.normalize().scale(1.6D);
            }
            projectile.shoot(aim.x, aim.y, aim.z, (float) aim.length(), 0.0F);
        } else {
            BallisticAim.Solution aim = BallisticAim.solveArrow(projectile.position(), target,
                    BallisticAim.ARROW_MIN_SPEED, ARROW_MAX_SPEED);
            Vec3 shot = aim.velocity();
            if (projectileAngle != 0.0F) {
                shot = shot.yRot((float) Math.toRadians(projectileAngle));
            }
            applyConfiguredArrowDamage(projectile, shot.length());
            projectile.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        }
        if (crossbowSpecialAmmo == AMMO_TIPPED) {
            consumeSpecialAmmo(true, false);
        } else if (crossbowSpecialAmmo == AMMO_FIREWORK) {
            consumeSpecialAmmo(false, true);
        }
        servantFood.addExhaustion(0.1F);
    }

    private static final double ARROW_MAX_SPEED = 3.0D;

    private static final double ARROW_REFERENCE_SPEED = 1.6D;

    static final double TRIDENT_MAX_SPEED = 2.5D;

    private static void markServantArrow(Projectile projectile) {
        if (projectile instanceof AbstractArrow arrow) {
            arrow.getPersistentData().putBoolean(ServantArrowDespawnEvents.SERVANT_ARROW_TAG, true);
        }
    }

    private void applyConfiguredArrowDamage(Projectile projectile, double launchSpeed) {
        if (projectile instanceof AbstractArrow arrow) {
            double configured = PlayerMobServantConfig.BOW_DAMAGE.get()
                    + summonPotency * POTENCY_RANGED_DAMAGE_BONUS;
            double scale = launchSpeed > 1.0E-4D ? ARROW_REFERENCE_SPEED / launchSpeed : 1.0D;
            arrow.setBaseDamage(configured * scale);
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CHARGING_CROSSBOW, false);
        entityData.define(MOB_NAME, "");
        entityData.define(FOOD_LEVEL, 20);
        entityData.define(FOOD_SATURATION, 4.0F);
        entityData.define(CHASE_SPRINTING, false);
        entityData.define(CASTING_HAND, -1);
        entityData.define(ARCANE_SCROLL, false);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    private void hurtUsedShieldLikePlayer(float damage) {
        ItemStack shield = getUseItem();
        if (level().isClientSide || damage < 3.0F || shield.isEmpty()) {
            return;
        }
        InteractionHand hand = getUsedItemHand();
        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND
                ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        shield.hurtAndBreak(1 + Mth.floor(damage), this,
                entity -> entity.broadcastBreakEvent(hand));
        if (shield.isEmpty()) {
            setItemSlot(slot, ItemStack.EMPTY);
            stopUsingItem();
            stopShieldGuard();
            playSound(SoundEvents.SHIELD_BREAK, 0.8F,
                    0.8F + level().random.nextFloat() * 0.4F);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!isValidCombatTargetForGoal(target)) {
            return false;
        }
        stopShieldGuard();
        if (isUsingTridentAim()) {
            stopUsingItem();
        }
        boolean critical = !level().isClientSide && isPlayerCriticalHit(target);
        if (critical) {
            applyCritModifiers();
        }
        boolean attacked = super.doHurtTarget(target);
        if (critical) {
            removeCritModifiers();
        }
        startShieldGuard(target);
        if (attacked) {
            crouchGuardNoMeleeBlocks = 0;
            servantFood.addExhaustion(0.1F);
            tryWeaponSlash();
            if (target instanceof LivingEntity livingTarget) {
                ItemStack weapon = getMainHandItem();
                if (SakuraGearCompat.isHeavyWeapon(weapon)) {
                    weapon.getItem().hurtEnemy(weapon, livingTarget, this);
                }
            }
            if (critical) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_CRIT,
                        getSoundSource(), 1.0F, 1.0F);
                if (level() instanceof ServerLevel serverLevel) {
                    AABB box = target.getBoundingBox();
                    Vec3 center = box.getCenter();
                    serverLevel.sendParticles(ParticleTypes.CRIT, center.x, center.y, center.z,
                            8, box.getXsize() * 0.5D, box.getYsize() * 0.5D, box.getZsize() * 0.5D, 0.1D);
                }
                return true;
            }
            performSweepAttack(target);
        }
        return attacked;
    }

    private static final java.util.UUID CRIT_DAMAGE_MODIFIER_ID =
            java.util.UUID.nameUUIDFromBytes("goety_supreme:jump_crit_damage".getBytes(java.nio.charset.StandardCharsets.UTF_8));
    private static final java.util.UUID CRIT_KNOCKBACK_MODIFIER_ID =
            java.util.UUID.nameUUIDFromBytes("goety_supreme:jump_crit_knockback".getBytes(java.nio.charset.StandardCharsets.UTF_8));

    private static final java.util.UUID SNEAK_SPEED_MODIFIER_ID =
            java.util.UUID.nameUUIDFromBytes("goety_supreme:sneak_speed".getBytes(java.nio.charset.StandardCharsets.UTF_8));

    private static final double CAST_FOLLOW_RANGE = 48.0D;
    private static final java.util.UUID CAST_FOLLOW_RANGE_MODIFIER_ID =
            java.util.UUID.nameUUIDFromBytes("goety_supreme:cast_follow_range".getBytes(java.nio.charset.StandardCharsets.UTF_8));

    private boolean isPlayerCriticalHit(Entity target) {
        return fallDistance > 0.0F && !onGround() && !onClimbable() && !isInWater()
                && !hasEffect(net.minecraft.world.effect.MobEffects.BLINDNESS) && !isPassenger()
                && target instanceof LivingEntity && !isSprinting();
    }

    private void applyCritModifiers() {
        AttributeInstance damage = getAttribute(Attributes.ATTACK_DAMAGE);
        if (damage != null) {
            damage.addTransientModifier(new AttributeModifier(CRIT_DAMAGE_MODIFIER_ID,
                    "jump_crit_damage", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        AttributeInstance knockback = getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (knockback != null) {
            knockback.addTransientModifier(new AttributeModifier(CRIT_KNOCKBACK_MODIFIER_ID,
                    "jump_crit_knockback", 2.0D, AttributeModifier.Operation.ADDITION));
        }
    }

    private void removeCritModifiers() {
        AttributeInstance damage = getAttribute(Attributes.ATTACK_DAMAGE);
        if (damage != null) {
            damage.removeModifier(CRIT_DAMAGE_MODIFIER_ID);
        }
        AttributeInstance knockback = getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (knockback != null) {
            knockback.removeModifier(CRIT_KNOCKBACK_MODIFIER_ID);
        }
    }

    public boolean shouldAttemptJumpAttack(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive()) {
            return false;
        }
        if (!onGround() || isPassenger() || isInWater() || onClimbable()) {
            return false;
        }
        if (eatingBite || isUsingTridentAim()) {
            return false;
        }
        ItemStack weapon = getMainHandItem();
        if (weapon.isEmpty() || (!isMeleeWeapon(weapon) && !isTridentLike(weapon))) {
            return false;
        }
        if (JerotesItems.isSpear(weapon) || JerotesItems.isPike(weapon)
                || FellSpearItem.isSpear(weapon)) {
            return false;
        }
        if (target.getY() - getY() > 1.5D) {
            return false;
        }
        return !canSweepWith(weapon) || !hasRivalNearby(target);
    }

    private void tickSakuraGear() {
        if ((tickCount & 1) != 0 || !SakuraGearCompat.isLoaded()) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive() || !isValidCombatTargetForGoal(target)) {
            return;
        }
        ItemStack weapon = getMainHandItem();
        if (SakuraGearCompat.isHeavyWeapon(weapon)) {
            SakuraGearCompat.tryEmpowerHeavy(this, weapon);
        }
    }

    private void tryWeaponSlash() {
        if (level().isClientSide || weaponSlashCooldown > 0) {
            return;
        }
        ItemStack weapon = getMainHandItem();
        if (weapon.isEmpty()) {
            return;
        }
        if (weapon.getItem() instanceof IPersist persist && persist.isBroken(weapon)) {
            return;
        }
        Vec3 look = getViewVector(1.0F);
        if (weapon.getItem() instanceof DeathScytheItem) {
            ScytheSlash slash = new ScytheSlash(weapon, level(),
                    getX() + look.x / 2.0D, getEyeY() - 0.2D, getZ() + look.z / 2.0D,
                    look.x, look.y, look.z);
            slash.setOwner(this);
            slash.setDamage(DarkScytheItem.getInitialDamage());
            slash.setTotalLife(300);
            level().addFreshEntity(slash);
            weaponSlashCooldown = WEAPON_SLASH_INTERVAL_TICKS;
        } else if (weapon.getItem() instanceof BladeOfEnderItem) {
            float velocityBonus = 0.0F;
            float radiusBonus = 0.0F;
            if (weapon.isEnchanted()) {
                velocityBonus += EnchantmentHelper.getItemEnchantmentLevel(
                        ModEnchantments.VELOCITY.get(), weapon) / 3.0F;
                radiusBonus += EnchantmentHelper.getItemEnchantmentLevel(
                        ModEnchantments.RADIUS.get(), weapon) / 4.0F;
            }
            VoidSlash slash = new VoidSlash(weapon, level(), this);
            slash.moveTo(getX() + look.x / 2.0D, getEyeY() - 0.2D, getZ() + look.z / 2.0D);
            slash.setDamage(ItemConfig.BladeOfEnderDamage.get().floatValue());
            slash.setMaxLifeSpan(MathHelper.secondsToTicks(0.5F));
            slash.setRadius(slash.getRadius() + radiusBonus);
            slash.setMaxRadius(slash.getMaxRadius() + radiusBonus);
            slash.slash(look, 0.5D + velocityBonus);
            slash.setVoidLevel(1);
            level().addFreshEntity(slash);
            weaponSlashCooldown = WEAPON_SLASH_INTERVAL_TICKS;
        }
    }

    private void tickSoulRepair() {
        if (level().isClientSide) {
            return;
        }
        LivingEntity owner = getTrueOwner();
        if (owner == null) {
            owner = getMasterOwner();
        }
        if (!(owner instanceof Player player) || player.isSpectator()) {
            return;
        }
        if (player.tickCount % SOUL_REPAIR_INTERVAL_TICKS != 0) {
            return;
        }
        for (EquipmentSlot slot : SOUL_REPAIR_SLOTS) {
            repairSoulItem(getItemBySlot(slot), player);
        }
        for (int i = 0; i < servantStorage.getContainerSize(); i++) {
            repairSoulItem(servantStorage.getItem(i), player);
        }
    }

    private static void repairSoulItem(ItemStack stack, Player owner) {
        if (stack.isEmpty()) {
            return;
        }
        Item item = stack.getItem();
        if (item instanceof IPersist persist && persist.isBroken(stack)) {
            return;
        }
        if (item instanceof ISoulRepair soulRepair) {
            soulRepair.repairTick(stack, owner, false);
        } else if (item instanceof TieredItem tiered && tiered.getTier() == ModTiers.DARK) {
            ItemHelper.repairTick(stack, owner, false);
        }
    }

    private static boolean canSweepWith(ItemStack weapon) {
        return !weapon.isEmpty()
                && ((IForgeItemStack) (Object) weapon).canPerformAction(ToolActions.SWORD_SWEEP);
    }

    private boolean hasRivalNearby(LivingEntity target) {
        AABB box = target.getBoundingBox().inflate(3.0D, 2.0D, 3.0D);
        for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, box,
                this::isValidCombatTargetForGoal)) {
            if (entity != target && entity.distanceToSqr(target) < 36.0D) {
                return true;
            }
        }
        return false;
    }

    public double meleeAttackReachSqr() {
        double reach = meleeReach();
        return reach * reach;
    }

    private double meleeReach() {
        double reach = Math.max(0.5D, PlayerMobServantConfig.MELEE_ATTACK_RANGE.get());
        AttributeInstance reachAttribute = getAttribute(ForgeMod.ENTITY_REACH.get());
        if (reachAttribute != null) {
            reach += Math.max(0.0D, reachAttribute.getValue() - 3.0D);
        }
        return reach;
    }

    public void jumpForCrit() {
        setCombatChaseSprint(false);
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, 0.42D, motion.z);
        hasImpulse = true;
    }

    public void setCombatChaseSprint(boolean wantSprint) {
        if (level().isClientSide) {
            return;
        }
        boolean active = wantSprint && !isPassenger() && !eatingBite
                && !isCrouchGuarding() && !isDaggerSneaking();
        if (isSprinting() != active) {
            setSprinting(active);
        }
        if (entityData.get(CHASE_SPRINTING) != active) {
            entityData.set(CHASE_SPRINTING, active);
        }
    }

    public boolean isSpearChasing() {
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        ItemStack main = this.getMainHandItem();
        if (!JerotesItems.isSpear(main) && !JerotesItems.isPike(main)
                && !FellSpearItem.isSpear(main)) {
            return false;
        }
        return this.distanceToSqr(target) > this.meleeAttackReachSqr();
    }

    @Override
    public boolean canSpawnSprintParticle() {
        if (level().isClientSide) {
            return entityData.get(CHASE_SPRINTING) && !isInWater() && !isSpectator()
                    && !isCrouching() && !isInLava() && isAlive() && !isInFluidType();
        }
        return super.canSpawnSprintParticle();
    }

    private void performSweepAttack(Entity target) {
        if (level().isClientSide || !onGround()) {
            return;
        }
        ItemStack weapon = getItemInHand(InteractionHand.MAIN_HAND);
        if (!canSweepWith(weapon)) {
            return;
        }
        float sweepDamage = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this);
        AttributeInstance reachAttribute = getAttribute(ForgeMod.ENTITY_REACH.get());
        double reachSqr = Mth.square(reachAttribute == null ? 3.0D : reachAttribute.getValue());
        AABB box = target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
        double deltaX = target.getX() - getX();
        double deltaZ = target.getZ() - getZ();
        double dirX;
        double dirZ;
        if (deltaX * deltaX + deltaZ * deltaZ > 1.0E-6D) {
            double length = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            dirX = deltaX / length;
            dirZ = deltaZ / length;
        } else {
            dirX = -Mth.sin(getYRot() * Mth.DEG_TO_RAD);
            dirZ = Mth.cos(getYRot() * Mth.DEG_TO_RAD);
        }
        for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (entity == this || entity == target || isAlliedTo(entity)) {
                continue;
            }
            if (entity instanceof ArmorStand armorStand && armorStand.isMarker()) {
                continue;
            }
            if (distanceToSqr(entity) >= reachSqr) {
                continue;
            }
            entity.knockback(0.4D, -dirX, -dirZ);
            entity.hurt(damageSources().mobAttack(this), sweepDamage);
        }
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_SWEEP,
                getSoundSource(), 1.0F, 1.0F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, getX() + dirX, getY(0.5D),
                    getZ() + dirZ, 0, dirX, 0.0D, dirZ, 0.0D);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean shieldUp = !level().isClientSide && amount > 0.0F && isUsingShieldAnyHand();
        boolean blocking = shieldUp && isDamageSourceBlocked(source);
        if (blocking && canDisableShield(source)) {
            disableShieldFor(PlayerMobServantConfig.SHIELD_BREAK_DISABLE_TICKS.get());
            float before = getHealth();
            boolean broke = super.hurt(source, amount);
            if (broke) {
                recordHurt(before - getHealth());
            }
            return broke;
        }
        if (shieldUp && !blocking && source.is(DamageTypeTags.BYPASSES_SHIELD)) {
            disableShieldFor(PlayerMobServantConfig.SHIELD_BREAK_DISABLE_TICKS.get());
        }

        float before = getHealth();
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            recordHurt(before - getHealth());
        }
        if (blocking) {
            if (!hurt) {
                registerShieldBlock();
                hurtUsedShieldLikePlayer(amount);
            } else if (isCrouchGuarding()) {
                Vec3 motion = getDeltaMovement();
                setDeltaMovement(motion.x * 0.05D, motion.y, motion.z * 0.05D);
            }
        }
        return hurt;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        if (isUsingShieldAnyHand()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return super.getHurtSound(source);
    }

    private boolean canDisableShield(DamageSource source) {
        Entity attackerEntity = source.getEntity();
        if (!(attackerEntity instanceof LivingEntity attacker)) {
            return false;
        }
        ItemStack attackerStack = attacker.getMainHandItem();
        ItemStack shieldStack = getUseItem();
        if (attackerStack.isEmpty() || !isShieldItem(shieldStack)) {
            return false;
        }
        return ((IForgeItemStack) (Object) attackerStack).canDisableShield(shieldStack, this, attacker);
    }

    private void registerShieldBlock() {
        shieldBlockCount++;
        if (isCrouchGuarding()) {
            crouchGuardNoMeleeBlocks++;
            if (crouchGuardNoMeleeBlocks >= PlayerMobServantConfig.SHIELD_CROUCH_FORCE_STAND_BLOCKS.get()) {
                forceStandShield();
            }
        } else {
            crouchGuardNoMeleeBlocks = 0;
        }
        int limit = isCrouchGuarding()
                ? Math.max(1, PlayerMobServantConfig.SHIELD_CROUCH_BLOCK_LIMIT.get())
                : Math.max(0, PlayerMobServantConfig.SHIELD_BLOCK_LIMIT.get());
        if (shieldBlockCount > limit) {
            disableShieldFor(PlayerMobServantConfig.SHIELD_BLOCK_DISABLE_TICKS.get());
        }
    }

    private void updateShieldAntiAbuse() {
        if (forcedStandTicks > 0) {
            forcedStandTicks--;
        }
        if (!isUsingShield()) {
            shieldToggleCount = 0;
            shieldToggleWindowTicks = 0;
            lastCrouchGuard = false;
            crouchGuardNoMeleeBlocks = 0;
            return;
        }
        boolean crouchGuard = isCrouchGuarding();
        if (crouchGuard != lastCrouchGuard) {
            registerShieldToggle();
            lastCrouchGuard = crouchGuard;
        }
        if (shieldToggleWindowTicks > 0) {
            shieldToggleWindowTicks--;
            if (shieldToggleWindowTicks == 0) {
                shieldToggleCount = 0;
            }
        }
    }

    private void registerShieldToggle() {
        if (shieldToggleWindowTicks <= 0) {
            shieldToggleWindowTicks = PlayerMobServantConfig.SHIELD_TOGGLE_WINDOW_TICKS.get();
            shieldToggleCount = 0;
        }
        shieldToggleCount++;
        if (shieldToggleCount > PlayerMobServantConfig.SHIELD_TOGGLE_LIMIT.get()) {
            forceStandShield();
        }
    }

    private void forceStandShield() {
        if (level().isClientSide) {
            return;
        }
        setShiftKeyDown(false);
        updateShieldPose();
        forcedStandTicks = Math.max(forcedStandTicks, PlayerMobServantConfig.FORCED_STAND_TICKS.get());
        shieldToggleCount = 0;
        shieldToggleWindowTicks = 0;
        crouchGuardNoMeleeBlocks = 0;
        lastCrouchGuard = false;
    }

    private void disableShieldFor(int ticks) {
        boolean wasGuarding = isUsingShieldAnyHand();
        shieldDisableTicks = Math.max(shieldDisableTicks, Math.max(0, ticks));
        shieldBlockCount = 0;
        stopShieldGuard();
        if (wasGuarding) {
            playShieldBreakLikeHauntedArmor();
        }
    }

    private void playShieldBreakLikeHauntedArmor() {
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte) 30);
        }
    }

    public void warlordBreakShield(int ticks) {
        if (level().isClientSide) {
            return;
        }
        if (isUsingShield()) {
            disableShieldFor(ticks);
        } else {
            shieldBlockCount = 0;
        }
    }

    private void updateShieldPose() {
        boolean crouching = isShiftKeyDown();
        if (crouching && getPose() != Pose.CROUCHING) {
            setPose(Pose.CROUCHING);
        } else if (!crouching && getPose() == Pose.CROUCHING) {
            setPose(Pose.STANDING);
        }
    }

     public void updateCombatCrouch(LivingEntity target) {
        if (level().isClientSide) {
            return;
        }
        boolean want = isSneakCasting();
        if (!eatingBite && forcedStandTicks <= 0 && target != null && target.isAlive()) {
            if (isUsingShield() && isWithinCrouchRange(target)) {
                want = true;
            } else if (JerotesItems.isDagger(getMainHandItem())) {
                want = true;
            }
        }
        if (isShiftKeyDown() != want) {
            setShiftKeyDown(want);
        }
        updateShieldPose();
    }

    public boolean isDaggerSneaking() {
        return isShiftKeyDown() && JerotesItems.isDagger(getMainHandItem());
    }

    private void updateSneakSpeedModifier() {
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }
        speed.removeModifier(SNEAK_SPEED_MODIFIER_ID);
        if (isShiftKeyDown()) {
            double multiplier = PlayerMobServantConfig.SNEAK_SPEED_MULTIPLIER.get();
            speed.addTransientModifier(new AttributeModifier(SNEAK_SPEED_MODIFIER_ID,
                    "goety_supreme:sneak_speed", multiplier - 1.0D,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    private void updateCastFollowRange() {
        AttributeInstance follow = getAttribute(Attributes.FOLLOW_RANGE);
        if (follow == null) {
            return;
        }
        follow.removeModifier(CAST_FOLLOW_RANGE_MODIFIER_ID);
        if (!isCastingWand()) {
            return;
        }
        double bonus = CAST_FOLLOW_RANGE - follow.getValue();
        if (bonus > 0.0D) {
            follow.addTransientModifier(new AttributeModifier(CAST_FOLLOW_RANGE_MODIFIER_ID,
                    "goety_supreme:cast_follow_range", bonus, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 29) {
            level().playLocalSound(getX(), getY(), getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS,
                    1.0F, 0.8F + getRandom().nextFloat() * 0.4F, false);
            return;
        }
        if (id == 30) {
            level().playLocalSound(getX(), getY(), getZ(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS,
                    0.8F, 0.8F + getRandom().nextFloat() * 0.4F, false);
            return;
        }
        super.handleEntityEvent(id);
    }

     public boolean canGuardWithShield(Entity target) {
        if (!Boolean.TRUE.equals(PlayerMobServantConfig.SHIELD_ENABLED.get())
                || shieldDisableTicks > 0 || !(target instanceof LivingEntity living)
                || !isValidCombatTargetForGoal(living)
                || !(isShieldConfiguration() || isTwoHandedBlockConfiguration())) {
            return false;
        }
        return true;
    }

    public boolean isCrouchGuarding() {
        return isUsingShield() && isShiftKeyDown();
    }

     public void startShieldGuard(Entity target) {
        if (level().isClientSide || !canGuardWithShield(target)) {
            return;
        }
        if (isTwoHandedBlockConfiguration()) {
            if (!isUsingTwoHandedBlock()) {
                if (isUsingItem()) {
                    stopUsingItem();
                }
                startUsingItem(InteractionHand.MAIN_HAND);
            }
        } else if (!isUsingShield()) {
            startUsingItem(InteractionHand.OFF_HAND);
        }
        updateCombatCrouch(getTarget());
    }

    private boolean isWithinCrouchRange(Entity target) {
        double range = Math.max(0.0D, PlayerMobServantConfig.SHIELD_APPROACH_RANGE.get());
        return target.distanceToSqr(this) <= range * range;
    }

     public void stopShieldGuard() {
        if (isUsingShield() || isUsingTwoHandedBlock()) {
            stopUsingItem();
        }
        setShiftKeyDown(false);
        updateShieldPose();
    }

    public boolean isUsingShield() {
        return isUsingItem() && getUsedItemHand() == InteractionHand.OFF_HAND && isShieldItem(getUseItem());
    }

    public boolean isUsingShieldAnyHand() {
        return isUsingItem() && isShieldItem(getUseItem());
    }

    public void startWandShield(InteractionHand hand) {
        if (level().isClientSide || shieldDisableTicks > 0 || !isShieldItem(getItemInHand(hand))) {
            return;
        }
        if (isUsingItem()) {
            if (getUsedItemHand() == hand && isShieldItem(getUseItem())) {
                return;
            }
            stopUsingItem();
        }
        startUsingItem(hand);
    }

    public void stopWandShield() {
        if (isUsingItem() && isShieldItem(getUseItem())) {
            stopUsingItem();
        }
    }

    public int getShieldDisableTicks() {
        return shieldDisableTicks;
    }

    public void tickWandCastingState() {
        if (wandSummonCooldown > 0) {
            wandSummonCooldown--;
        }
        if (!wandFocusCooldowns.isEmpty()) {
            wandFocusCooldowns.entrySet().removeIf(entry -> {
                int remaining = entry.getValue() - 1;
                entry.setValue(remaining);
                return remaining <= 0;
            });
        }
    }

    public boolean isWandFocusOnCooldown(String itemId) {
        return itemId != null && !itemId.isEmpty() && wandFocusCooldowns.getOrDefault(itemId, 0) > 0;
    }

    public int getWandFocusCooldown(String itemId) {
        return itemId == null || itemId.isEmpty() ? 0 : wandFocusCooldowns.getOrDefault(itemId, 0);
    }

    public void addWandFocusCooldown(String itemId, int ticks) {
        if (itemId != null && !itemId.isEmpty() && ticks > 0) {
            wandFocusCooldowns.put(itemId, ticks);
        }
    }

    public int getWandSummonCooldown() {
        return wandSummonCooldown;
    }

    public void setWandSummonCooldown(int ticks) {
        wandSummonCooldown = Math.max(0, ticks);
    }

    public List<String> getWandBuffEffects(String itemId) {
        if (itemId == null || itemId.isEmpty()) {
            return List.of();
        }
        return wandBuffEffects.getOrDefault(itemId, List.of());
    }

    public void rememberWandBuffEffects(String itemId, Set<String> effectIds) {
        if (itemId == null || itemId.isEmpty() || effectIds == null || effectIds.isEmpty()) {
            return;
        }
        Set<String> merged = new HashSet<>(getWandBuffEffects(itemId));
        merged.addAll(effectIds);
        wandBuffEffects.put(itemId, new ArrayList<>(merged));
    }

    public void setCastingHand(int hand) {
        entityData.set(CASTING_HAND, hand);
    }

    public int getCastingHand() {
        return entityData.get(CASTING_HAND);
    }

    public boolean isCastingWand() {
        return getCastingHand() >= 0;
    }

    public void setWandSneakCast(boolean value) {
        this.wandSneakCast = value;
    }

    public boolean isSneakCasting() {
        return wandSneakCast;
    }

    public void setWandLongWindup(boolean value) {
        this.wandLongWindup = value;
    }

    public boolean isCastingLongWandWindup() {
        return wandLongWindup && isCastingWand();
    }

    public void setWandPartingShot(boolean value) {
        this.wandPartingShot = value;
    }

    public boolean isCastingPartingShot() {
        return wandPartingShot && isCastingWand();
    }

    public LivingEntity escapePartingShotTarget() {
        return escapePartingShotTarget;
    }

    public boolean escapePartingShotInstant() {
        return escapePartingShotInstant;
    }

    public void requestEscapePartingShot(LivingEntity target, boolean instantOnly) {
        this.escapePartingShotTarget = target;
        this.escapePartingShotInstant = instantOnly;
    }

    public void clearForcedPartingShot() {
        this.escapePartingShotTarget = null;
        this.escapePartingShotInstant = false;
    }

    private void saveWandCastingData(CompoundTag tag) {
        tag.putBoolean("MeleeWandMode", meleeWandMode);
        tag.putBoolean("MeleeWandStashed", meleeWandStashed);
        if (!wandFocusCooldowns.isEmpty()) {
            CompoundTag cooldowns = new CompoundTag();
            wandFocusCooldowns.forEach(cooldowns::putInt);
            tag.put("WandFocusCooldowns", cooldowns);
        }
        if (wandSummonCooldown > 0) {
            tag.putInt("WandSummonCooldown", wandSummonCooldown);
        }
        if (!wandBuffEffects.isEmpty()) {
            CompoundTag buffs = new CompoundTag();
            wandBuffEffects.forEach((focusId, effectIds) -> {
                ListTag list = new ListTag();
                for (String effectId : effectIds) {
                    list.add(StringTag.valueOf(effectId));
                }
                buffs.put(focusId, list);
            });
            tag.put("WandBuffEffects", buffs);
        }
    }

    private void loadWandCastingData(CompoundTag tag) {
        wandFocusCooldowns.clear();
        wandSummonCooldown = 0;
        wandBuffEffects.clear();
        meleeWandMode = tag.getBoolean("MeleeWandMode");
        meleeWandStashed = tag.getBoolean("MeleeWandStashed");
        weakeningInterlude = false;
        weakeningInterludeTicks = 0;
        healInterlude = false;
        healInterludeTicks = 0;
        healWandSwapSlot = -1;
        CompoundTag cooldowns = tag.getCompound("WandFocusCooldowns");
        for (String key : cooldowns.getAllKeys()) {
            wandFocusCooldowns.put(key, cooldowns.getInt(key));
        }
        wandSummonCooldown = Math.max(0, tag.getInt("WandSummonCooldown"));
        CompoundTag buffs = tag.getCompound("WandBuffEffects");
        for (String key : buffs.getAllKeys()) {
            ListTag list = buffs.getList(key, Tag.TAG_STRING);
            List<String> effectIds = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                effectIds.add(list.getString(i));
            }
            if (!effectIds.isEmpty()) {
                wandBuffEffects.put(key, effectIds);
            }
        }
    }

    private boolean isShieldConfiguration() {
        ItemStack main = getMainHandItem();
        return (isMeleeWeapon(main) || isTridentLike(main)) && isShieldItem(getOffhandItem());
    }

    boolean isTwoHandedBlockConfiguration() {
        return getOffhandItem().isEmpty() && JerotesItems.isTwoHanded(getMainHandItem());
    }

    public boolean isUsingTwoHandedBlock() {
        return isUsingItem() && getUsedItemHand() == InteractionHand.MAIN_HAND
                && !getUseItem().isEdible() && isTwoHandedBlockConfiguration();
    }

    static boolean isShieldItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ShieldItem;
    }

    public boolean isWandInMainHand() {
        return GoetyStaffGuard.isGoetyStaff(getMainHandItem());
    }

    public boolean isWandCasterSetup() {
        return isWandInMainHand() || GoetyStaffGuard.isGoetyStaff(getOffhandItem());
    }

    public boolean isPureWandCasterSetup() {
        if (isWandInMainHand()) {
            return true;
        }
        if (!GoetyStaffGuard.isGoetyStaff(getOffhandItem())) {
            return false;
        }
        return !isMainHandOffensiveWeapon();
    }

    private boolean isMainHandOffensiveWeapon() {
        ItemStack main = getMainHandItem();
        return isMeleeWeapon(main) || isTridentLike(main)
                || main.getItem() instanceof ProjectileWeaponItem
                || JerotesItems.isSpear(main) || JerotesItems.isPike(main);
    }

    public boolean canUseMeleeAi() {
        return this.weapons.canUseMeleeAi();
    }

    public static boolean isTridentLike(ItemStack stack) {
        return PlayerMobWeapons.isTridentLike(stack);
    }

    public static boolean isMeleeWeapon(ItemStack stack) {
        return PlayerMobWeapons.isMeleeWeapon(stack);
    }

    public static boolean isThrowMeleeWeapon(ItemStack stack) {
        return PlayerMobWeapons.isThrowMeleeWeapon(stack);
    }

    public static double tridentMeleeRangeSqr() {
        return PlayerMobWeapons.tridentMeleeRangeSqr();
    }

    public boolean isTridentRangedEngagement() {
        return this.weapons.isTridentRangedEngagement();
    }

    public void throwTridentCopy(LivingEntity target) {
        this.weapons.throwTridentCopy(target);
    }

    public boolean isUsingTridentAim() {
        return this.weapons.isUsingTridentAim();
    }

    public boolean isThrowableStack(ItemStack stack) {
        return this.weapons.isThrowableStack(stack);
    }

    public InteractionHand throwHand() {
        return this.weapons.throwHand();
    }

    public boolean isThrowEngagement() {
        return this.weapons.isThrowEngagement();
    }

    public boolean isThrowEscapeEngagement() {
        return this.weapons.isThrowEscapeEngagement();
    }

    public boolean performThrowAttack(LivingEntity target) {
        return this.weapons.performThrowAttack(target);
    }

    public boolean isEnderPearlEngagement() {
        return this.enderPearl.isEnderPearlEngagement();
    }

    public boolean throwEnderPearlAt(LivingEntity target) {
        return this.enderPearl.throwEnderPearlAt(target);
    }

    private static final int HAZARD_ESCAPE_COOLDOWN_TICKS = 30;

    private static final double HAZARD_ESCAPE_MAX_RADIUS = 20.0D;

    private static final double HAZARD_ESCAPE_MIN_RADIUS = 4.0D;

    public boolean isTrappedInHazard() {
        if (level().isClientSide || isPassenger() || getVehicle() != null) {
            return false;
        }
        if (isInLava()) {
            return true;
        }
        AABB box = getBoundingBox().inflate(0.05D);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = Mth.floor(box.minX); x <= Mth.floor(box.maxX); x++) {
            for (int y = Mth.floor(box.minY); y <= Mth.floor(box.maxY); y++) {
                for (int z = Mth.floor(box.minZ); z <= Mth.floor(box.maxZ); z++) {
                    pos.set(x, y, z);
                    if (isHazardBlock(level().getBlockState(pos))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isHazardBlock(BlockState state) {
        return state.is(Blocks.COBWEB) || state.is(Blocks.SWEET_BERRY_BUSH)
                || state.is(Blocks.POWDER_SNOW);
    }

    public boolean isHazardEscapeReady() {
        return hazardEscapeCooldown <= 0;
    }

    public int tryEscapeHazard() {
        Vec3 spot = findHazardEscapeSpot();
        if (spot == null) {
            hazardEscapeCooldown = HAZARD_ESCAPE_COOLDOWN_TICKS;
            return 0;
        }
        Vec3 flat = new Vec3(spot.x - getX(), 0.0D, spot.z - getZ());
        if (flat.lengthSqr() > 1.0E-4D && this.enderPearl.findEnderPearlStack() != null) {
            int settle = throwEnderPearlToSpot(spot);
            if (settle > 0) {
                return settle;
            }
        }
        double travel = flat.length();
        if (travel > 0.5D && PlayerMobWandSpellGoal.canBurstDisplacement(this)) {
            int settle = PlayerMobWandSpellGoal.burstDisplacement(this, flat.normalize(),
                    Math.min(travel, 24.0D));
            if (settle > 0) {
                hazardEscapeCooldown = HAZARD_ESCAPE_COOLDOWN_TICKS;
                return settle;
            }
        }
        getNavigation().moveTo(spot.x, spot.y, spot.z, 1.4D);
        hazardEscapeCooldown = HAZARD_ESCAPE_COOLDOWN_TICKS;
        return 0;
    }

    @Nullable
    private Vec3 findHazardEscapeSpot() {
        for (double radius = HAZARD_ESCAPE_MIN_RADIUS;
                radius <= HAZARD_ESCAPE_MAX_RADIUS; radius += 2.0D) {
            for (int i = 0; i < 16; i++) {
                double angle = (Math.PI * 2.0D * i) / 16.0D
                        + getRandom().nextDouble() * 0.25D;
                double x = getX() + Math.cos(angle) * radius;
                double z = getZ() + Math.sin(angle) * radius;
                Vec3 spot = this.enderPearl.findStandableSpot(x, z, getY());
                if (spot != null && !isHazardAt(spot)) {
                    return spot;
                }
            }
        }
        return null;
    }

    private boolean isHazardAt(Vec3 spot) {
        BlockPos base = BlockPos.containing(spot.x, spot.y, spot.z);
        return isHazardBlock(level().getBlockState(base))
                || isHazardBlock(level().getBlockState(base.above()));
    }

    private int throwEnderPearlToSpot(Vec3 landing) {
        ItemStack source = this.enderPearl.findEnderPearlStack();
        if (source == null) {
            hazardEscapeCooldown = ENDER_PEARL_RETRY_TICKS;
            return 0;
        }
        Vec3 from = new Vec3(getX(), getEyeY() - 0.1D, getZ());
        BallisticAim.Solution aim = BallisticAim.solveFast(from, landing,
                ENDER_PEARL_GRAVITY, ENDER_PEARL_DRAG,
                ENDER_PEARL_MIN_SPEED, ENDER_PEARL_MAX_SPEED);
        Vec3 shot = aim.velocity();
        if (shot.lengthSqr() < 1.0E-6D) {
            hazardEscapeCooldown = ENDER_PEARL_RETRY_TICKS;
            return 0;
        }
        stopShieldGuard();
        ServantThrownEnderpearl pearl = new ServantThrownEnderpearl(level(), this);
        pearl.setPearlLanding(landing);
        pearl.setPos(from.x, from.y, from.z);
        pearl.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        playSound(SoundEvents.ENDER_PEARL_THROW, 0.5F,
                0.4F / (getRandom().nextFloat() * 0.4F + 0.8F));
        level().addFreshEntity(pearl);
        source.shrink(1);
        swing(InteractionHand.MAIN_HAND);
        servantFood.addExhaustion(0.1F);
        setCombatChaseSprint(false);
        hazardEscapeCooldown = HAZARD_ESCAPE_COOLDOWN_TICKS;
        enderPearlCooldown = Math.max(enderPearlCooldown,
                PlayerMobServantConfig.ENDER_PEARL_THROW_INTERVAL.get());
        return Mth.clamp(aim.ticks() + 4, 6, 40);
    }

    public void arriveByEnderPearl(Vec3 landing) {
        if (level().isClientSide) {
            return;
        }
        if (isPassenger()) {
            stopRiding();
        }
        stopShieldGuard();
        getNavigation().stop();
        setCombatChaseSprint(false);
        Vec3 spot = this.enderPearl.findStandableSpot(landing.x, landing.z, landing.y);
        if (spot == null) {
            if (!randomTeleport(landing.x, landing.y, landing.z, true)) {
                return;
            }
        } else {
            teleportTo(spot.x, spot.y, spot.z);
            level().broadcastEntityEvent(this, (byte) 46);
        }
        resetFallDistance();
        hurt(damageSources().fall(),
                (float) PlayerMobServantConfig.ENDER_PEARL_DAMAGE.get().doubleValue());
    }

    @Override
    public boolean isHostile() {
        return this.threat().isHostile();
    }

    public void setHostile(boolean hostile) {
        this.threat().setHostile(hostile);
    }

    public void checkHostility() {
        this.threat().checkHostility();
    }

    public boolean isValidCombatTargetForGoal(Entity entity) {
        return this.threat().isValidCombatTargetForGoal(entity);
    }

    public boolean isHostileTowardServant(LivingEntity entity) {
        return this.threat().isHostileTowardServant(entity);
    }

    public List<LivingEntity> nearbyAttackers(double radius) {
        return this.threat().nearbyAttackers(radius);
    }

    public LivingEntity nearestThreat(@Nullable LivingEntity target, double radius) {
        return this.threat().nearestThreat(target, radius);
    }

    public boolean isUnderFocusFire() {
        return this.threat().isUnderFocusFire();
    }

    public boolean isUnderPanicEscape() {
        return this.threat().isUnderPanicEscape();
    }

    public boolean isFocusFireEscapeReady() {
        return this.threat().isFocusFireEscapeReady();
    }

    public void markFocusFireEscaped() {
        this.threat().markFocusFireEscaped();
    }

    public boolean isPartingShotReady() {
        return this.threat().isPartingShotReady();
    }

    public void markPartingShotUsed() {
        this.threat().markPartingShotUsed();
    }

    private void recordHurt(float damage) {
        this.threat().recordHurt(damage);
    }

    public SimpleContainer getServantStorage() {
        return this.storage.getServantStorage();
    }

    public boolean hasArcaneScroll() {
        return this.entityData.get(ARCANE_SCROLL);
    }

    public void setArcaneScroll(boolean granted) {
        this.entityData.set(ARCANE_SCROLL, granted);
        if (granted) {
            this.storage.lockArcaneEquipmentDrops();
        }
    }

    public void lockArcaneEquipmentDrops() {
        this.storage.lockArcaneEquipmentDrops();
    }

    public int getActiveStorageSize() {
        return this.storage.getActiveStorageSize();
    }

    private void tickArcaneScroll() {
        this.storage.tickArcaneScroll();
    }
    public void tryKill(Player player) {
        if (this.hasArcaneScroll() && this.getKillChance() <= 0) {
            this.warnKill(player);
            return;
        }
        super.tryKill(player);
    }

    public void markEquipDropOnDeath(EquipmentSlot slot) {
        setGuaranteedDrop(slot);
    }

    public int getGuiFoodLevel() {
        return entityData.get(FOOD_LEVEL);
    }

    public float getGuiSaturation() {
        return entityData.get(FOOD_SATURATION);
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new PlayerMobServantInventoryMenu(windowId, playerInventory, this);
    }
    private void initServantSurvival() {
        this.food.initServantSurvival();
    }

    private void tickServantSurvival() {
        this.food.tickServantSurvival();
    }

    private boolean servantIsHurt() {
        return this.food.servantIsHurt();
    }

    void syncServantFoodData() {
        int food = servantFood.getFoodLevel();
        float saturation = servantFood.getSaturationLevel();
        Integer syncedFood = entityData.get(FOOD_LEVEL);
        Float syncedSaturation = entityData.get(FOOD_SATURATION);
        if (syncedFood == null || syncedFood != food) {
            entityData.set(FOOD_LEVEL, food);
        }
        if (syncedSaturation == null || Float.compare(syncedSaturation, saturation) != 0) {
            entityData.set(FOOD_SATURATION, saturation);
        }
    }

    public double eatRangeSqr() {
        return this.food.eatRangeSqr();
    }

    public boolean isUsingFood() {
        return this.food.isUsingFood();
    }

    private void keepEyesOnTargetWhileEating() {
        this.food.keepEyesOnTargetWhileEating();
    }

    public boolean canStartEatBite() {
        return this.food.canStartEatBite();
    }

    public int findBestFoodSlot() {
        return this.food.findBestFoodSlot();
    }

    public boolean hasFleeEatFood() {
        return this.food.hasFleeEatFood();
    }

    public void startEatBite() {
        this.food.startEatBite();
    }

    public void startEatBite(PlayerMobEatMode mode) {
        this.food.startEatBite(mode);
    }

    public void tickEatWalk(@Nullable LivingEntity threat) {
        this.food.tickEatWalk(threat);
    }

    public void resumeEatBite() {
        this.food.resumeEatBite();
    }

    public void finishOrResumeEatBite() {
        this.food.finishOrResumeEatBite();
    }

    public void cancelEatBite() {
        this.food.cancelEatBite();
    }

    void restoreEatWeapon() {
        this.food.restoreEatWeapon();
    }

    public boolean addStackToStorage(ItemStack stack) {
        return this.storage.addStackToStorage(stack);
    }

    public boolean hasStorageRoom() {
        return this.storage.hasStorageRoom();
    }

    public int storageRoomFor(ItemStack stack) {
        return this.storage.storageRoomFor(stack);
    }

    public void tryUpgradeGearFromStorage() {
        this.storage.tryUpgradeGearFromStorage();
    }
    public boolean isMeleeWandMode() {
        return this.wandMode.isMeleeWandMode();
    }

    public boolean isWeakeningInterlude() {
        return this.wandMode.isWeakeningInterlude();
    }

    public boolean isHealInterlude() {
        return this.wandMode.isHealInterlude();
    }

    public void finishHealInterlude() {
        this.wandMode.finishHealInterlude();
    }

    public boolean hasCastableHeal() {
        return this.wandMode.hasCastableHeal();
    }

    public void finishWeakeningInterlude() {
        this.wandMode.finishWeakeningInterlude();
    }

    public ItemStack meleeWandStack() {
        return this.wandMode.meleeWandStack();
    }

    private void enterMeleeWandMode() {
        this.wandMode.enterMeleeWandMode();
    }

    private void tickMeleeWandMode() {
        this.wandMode.tickMeleeWandMode();
    }

    private void tickHealInterlude() {
        this.wandMode.tickHealInterlude();
    }

    private void tickWeakeningInterlude() {
        this.wandMode.tickWeakeningInterlude();
    }

    private boolean isSupportOnlyWandSetup() {
        return this.wandMode.isSupportOnlyWandSetup();
    }

    private static boolean isNoDrop(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(NO_DROP_TAG);
    }

    @Override
    public void die(DamageSource source) {
        ItemStack sealedScroll = this.sealIntoArcaneScroll();
        super.die(source);
        if (level().isClientSide || storageDropped) {
            return;
        }
        storageDropped = true;
        if (this.hasArcaneScroll()) {
            ItemStack scroll = sealedScroll != null ? sealedScroll
                    : new ItemStack(GeneratedMod.ARCANE_SCROLL.get());
            OwnerBoundLoot.deliverNoCooldown(this, scroll);
            servantStorage.clearContent();
            eatWeaponStash = ItemStack.EMPTY;
            return;
        }
        for (int i = 0; i < servantStorage.getContainerSize(); i++) {
            ItemStack stack = servantStorage.getItem(i);
            if (stack.isEmpty() || isNoDrop(stack)) {
                continue;
            }
            spawnAtLocation(stack.copy(), 0.0F);
        }
        servantStorage.clearContent();
        if (!eatWeaponStash.isEmpty()) {
            if (!isNoDrop(eatWeaponStash) && !RingServantGear.isRingGear(eatWeaponStash)) {
                spawnAtLocation(eatWeaponStash.copy(), 0.0F);
            }
            eatWeaponStash = ItemStack.EMPTY;
        }
    }

    @Nullable
    private ItemStack sealIntoArcaneScroll() {
        if (this.level().isClientSide || this.storageDropped || !this.hasArcaneScroll()) {
            return null;
        }
        ItemStack scroll = new ItemStack(GeneratedMod.ARCANE_SCROLL.get());
        try {
            ReviveServantItem.setOwnerName(getTrueOwner(), scroll);
            ReviveServantItem.setSummon(this, scroll);
        } catch (RuntimeException | LinkageError ignored) {
            return new ItemStack(GeneratedMod.ARCANE_SCROLL.get());
        }
        return scroll;
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        if (this.hasArcaneScroll()) {
            return;
        }
        super.dropCustomDeathLoot(source, looting, recentlyHit);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("PlayerMobSurvivalInit", survivalInitialized);
        tag.putBoolean("PlayerMobStarterFoodGiven", starterFoodGiven);
        tag.putInt("PlayerMobEatCooldown", eatRestartCooldown);
        tag.putInt("SummonPotency", summonPotency);
        tag.putBoolean("PlayerMobArcaneScroll", this.hasArcaneScroll());
        ListTag items = new ListTag();
        for (int i = 0; i < servantStorage.getContainerSize(); i++) {
            ItemStack stack = servantStorage.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            CompoundTag itemTag = new CompoundTag();
            itemTag.putByte("Slot", (byte) i);
            stack.save(itemTag);
            items.add(itemTag);
        }
        tag.put("PlayerMobItems", items);
        CompoundTag foodTag = new CompoundTag();
        servantFood.addAdditionalSaveData(foodTag);
        tag.put("PlayerMobFood", foodTag);
        if (!eatWeaponStash.isEmpty()) {
            CompoundTag stashTag = new CompoundTag();
            eatWeaponStash.save(stashTag);
            tag.put("PlayerMobEatStash", stashTag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        survivalInitialized = tag.getBoolean("PlayerMobSurvivalInit");
        starterFoodGiven = tag.getBoolean("PlayerMobStarterFoodGiven");
        eatRestartCooldown = Math.max(0, tag.getInt("PlayerMobEatCooldown"));
        summonPotency = tag.contains("SummonPotency") ? Math.max(0, tag.getInt("SummonPotency")) : 0;
        this.setArcaneScroll(tag.getBoolean("PlayerMobArcaneScroll"));
        servantStorage.clearContent();
        if (tag.contains("PlayerMobItems", Tag.TAG_LIST)) {
            ListTag items = tag.getList("PlayerMobItems", Tag.TAG_COMPOUND);
            for (int i = 0; i < items.size(); i++) {
                CompoundTag itemTag = items.getCompound(i);
                int slot = itemTag.getByte("Slot") & 0xFF;
                ItemStack stack = ItemStack.of(itemTag);
                if (!stack.isEmpty() && slot < servantStorage.getContainerSize()) {
                    servantStorage.setItem(slot, stack);
                }
            }
        }
        if (tag.contains("PlayerMobFood", Tag.TAG_COMPOUND)) {
            servantFood.readAdditionalSaveData(tag.getCompound("PlayerMobFood"));
        }
        if (tag.contains("PlayerMobEatStash", Tag.TAG_COMPOUND)) {
            eatWeaponStash = ItemStack.of(tag.getCompound("PlayerMobEatStash"));
            if (!eatWeaponStash.isEmpty()) {
                eatingBite = true;
            }
        } else {
            eatWeaponStash = ItemStack.EMPTY;
        }
        lastExhaustX = getX();
        lastExhaustZ = getZ();
    }

}
