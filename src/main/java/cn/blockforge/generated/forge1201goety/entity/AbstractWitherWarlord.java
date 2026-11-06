package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.common.entities.util.FirePillar;
import com.Polarice3.Goety.common.entities.util.ModFallingBlock;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import cn.blockforge.generated.goetysupreme.util.RagingStarDamageGuard;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.renderer.WarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyWarlordHellfirePillarMixin;

public abstract class AbstractWitherWarlord extends Summoned
        implements PowerableMob, UndeadCapeBearer {

    public static final int SKILL_NONE = 0;
    public static final int SKILL_SLASH = 1;
    public static final int SKILL_BACKSTEP = 2;
    public static final int SKILL_INTOLERABLE = 3;
    public static final int SKILL_ELITE = 4;
    public static final int SKILL_CHARGE = 5;
    public static final int SKILL_BLAZING = 6;
    public static final int SKILL_HELLFIRE_SLASH = 7;
    public static final int SKILL_GREAT_LEAP = 8;
    public static final int SKILL_CATACLYSM = 9;

    public static final int BLOCK_PHASE_NONE = 0;
    public static final int BLOCK_PHASE_START = 1;
    public static final int BLOCK_PHASE_HOLD = 2;
    public static final int BLOCK_PHASE_END = 3;
    public static final int BLOCK_PHASE_DASH = 4;

    public static final int INVULN_TICKS = 16;

    public static final float DAMAGE_CAP = 25.0F;

    public static float damageCap() {
        return SupremeMobsConfig.WITHER_WARLORD_DAMAGE_CAP.get().floatValue();
    }

    public static float applyDamageCap(float amount) {
        float cap = damageCap();
        return cap > 0.0F && amount > cap ? cap : amount;
    }

    public static final String HELLFIRE_PILLAR_TAG = "GoetySupremeWarlordHellfirePillar";

    private static final float HELLFIRE_RESISTANCE_MULTIPLIER = 0.5F;

    private static final float CATACLYSM_DAMAGE_MULTIPLIER = 0.1F;

    private static final float DRAW_DAMAGE_MULTIPLIER = 0.1F;

    static final int SKILL_ACTION_GAP_TICKS = 5;

    public static final int DRAW_ANIMATION_TICKS = 60;

    public static final int SHEATH_ANIMATION_TICKS = 45;

    public static final int BLADE_SHEATH_DELAY_TICKS = 200;

    public static final int ELITE_COOLDOWN_TICKS = 600;

    static final int ELITE_FIRST_FORCE_MIN = 100;
    static final int ELITE_FIRST_FORCE_RANDOM = 101;

    static final int ELITE_FORCE_PERIOD_TICKS = 1200;

    private static final double FOLLOW_RANGE = 64.0D;

    private static final double MELEE_REACH = 3.0D;

    static final double GREAT_LEAP_MIN_DISTANCE = 12.0D;
    static final int GREAT_LEAP_CHANCE = 120;

    static final int GREAT_LEAP_COOLDOWN_TICKS = 200;

    static final int GREAT_LEAP_FORCE_INTERVAL_TICKS = 200;

    static final int GREAT_LEAP_LOCK_TICKS = 37;

    static final double GREAT_LEAP_RISE_SPEED = 0.7D;

    static final int GREAT_LEAP_SLAM_TICK = 48;

    static final int GREAT_LEAP_END_TICK = 70;

    static final double GREAT_LEAP_IMPACT_RADIUS = 7.0D;
    static final float GREAT_LEAP_IMPACT_DAMAGE = 20.0F;

    static final double GREAT_LEAP_LOCK_RANGE = 64.0D;

    static final double GREAT_LEAP_DIVE_SPEED = 2.4D;

    private static final float HELLFIRE_SLASH_DAMAGE = 15.0F;

    private static final float SLASH_MAX_HEALTH_RATIO = 0.05F;

    static final float BLOCK_DASH_DAMAGE = 15.0F;

    static final float DASH_MAX_HEALTH_RATIO = 0.05F;

    static final int HELLFIRE_COOLDOWN_TICKS = 400;

    static final int HELLFIRE_CHANCE = 120;

    static final double HELLFIRE_RANGE = 6.0D;

    static final int HELLFIRE_FORCE_INTERVAL_TICKS = 200;
    static final int SLASH_FORCE_INTERVAL_TICKS = 200;

    static final int BASIC_ATTACK_COOLDOWN_TICKS = 20;

    static final double SLASH_TRIGGER_RANGE = 4.5D;

    static final int COMBAT_IDLE_WATCHDOG_TICKS = 60;

    static final double COMBAT_ENGAGE_RANGE = 20.0D;

    static final int SKILL_ABSOLUTE_TIMEOUT_TICKS = 300;

    static final int BLOCK_WINDOW_TICKS = 20;

    private static final int BLOCK_EXTEND_TICKS = 20;

    static final int BLOCK_MAX_HITS = 2;

    private static final float BLOCK_DAMAGE_MULTIPLIER = 0.5F;

    static final int BLOCK_START_TICKS = 20;

    static final int BLOCK_END_TICKS = 11;

    public static final int BLOCK_COOLDOWN_TICKS = 15 * 20;

    static final int BLOCK_DASH_HIT_GAP = 10;

    static final int BLOCK_DASH_TIMEOUT_TICKS = 60;

    static final double BLOCK_DASH_REACH = 2.5D;

    static final double BLOCK_DASH_SPEED = 1.15D;

    private static final float BURST_INVULN_DAMAGE_THRESHOLD = 40.0F;

    private static final int BURST_INVULN_DAMAGE_WINDOW_TICKS = 2 * 20;

    private static final int BURST_INVULN_TICKS = 24;

    static final int BLAZING_PILLAR_DELAY_TICKS = 33;

    static final int BLAZING_RAIN_TICKS = 100;

    static final int BLAZING_RAIN_INTERVAL = 20;

    static final float BLAZING_RAIN_DAMAGE = 10.0F;

    static final double BLAZING_RADIUS = 5.0D;

    static final int BLAZING_BURN_HEX_TICKS = 100;

    static final int BLAZING_PILLAR_RINGS = 3;

    static final int BLAZING_COOLDOWN_TICKS = BLOCK_COOLDOWN_TICKS;

    static final int CHARGE_COOLDOWN_TICKS = 400;

    static final int CHARGE_HORN_TICK = 5;

    static final int CHARGE_SPEED_TICKS = 200;

    static final double CHARGE_NEAR_DISTANCE = 8.0D;

    static final int CHARGE_NEAR_CHANCE = 4;

    static final int CHARGE_WITH_MINIONS_CHANCE = 120;
    static final int CHARGE_WITHOUT_MINIONS_CHANCE = 360;

    private static final double VOLLEY_SPREAD_YAW_DEG = 16.0D;

    private static final double VOLLEY_SPREAD_PITCH_DEG = 10.0D;

    private static final double VOLLEY_JITTER_DEG = 2.5D;

    static final double BACKSTEP_TRIGGER_RANGE = 5.0D;

    static final int BACKSTEP_CHANCE = 360;

    private static final float BACKSTEP_DAMAGE_THRESHOLD = 50.0F;

    private static final int BACKSTEP_DAMAGE_WINDOW_TICKS = 100;

    private static final double BACKSTEP_DISTANCE = 5.0D;
    private static final int BACKSTEP_MOVE_TICKS = 15;

    private static final double SKULL_POWER = 0.1D;
    private static final double SKULL_INERTIA = 0.95D;

    private static final double BASE_ATTACK = 5.0D;

    private static final double ATTACK_TOTAL_CAP = 24.0D;

    private static final double ARMOR_TOTAL_CAP = 25.0D;

    private static final UUID SABER_MODIFIER_ID = UUID.fromString("8f2b9f5e-9c31-4d2a-b6a4-2e2a0d1c7a01");
    private static final UUID RAGE_ATTACK_ID = UUID.fromString("8f2b9f5e-9c31-4d2a-b6a4-2e2a0d1c7a02");
    private static final UUID RAGE_ARMOR_ID = UUID.fromString("8f2b9f5e-9c31-4d2a-b6a4-2e2a0d1c7a03");

    private static final String NBT_CATACLYSM = "WarlordCataclysm";
    private static final String NBT_WITHER_ARMOR = "WarlordWitherArmor";

    private static final EntityDataAccessor<Integer> DATA_SKILL =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_BLOCK_PHASE =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CATACLYSM =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_WITHER_ARMOR =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_BLADE =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_SKY_BEAM =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_ENGAGED =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_TANTRUM =
            SynchedEntityData.defineId(AbstractWitherWarlord.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState combatWalkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState combatIdleAnimationState = new AnimationState();
    public final AnimationState slashAnimationState = new AnimationState();
    public final AnimationState backstepAnimationState = new AnimationState();
    public final AnimationState drawAnimationState = new AnimationState();
    public final AnimationState sheathAnimationState = new AnimationState();
    public final AnimationState blockStartAnimationState = new AnimationState();
    public final AnimationState blockLoopAnimationState = new AnimationState();
    public final AnimationState blockEndAnimationState = new AnimationState();
    public final AnimationState counterChargeAnimationState = new AnimationState();
    public final AnimationState blazingAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState hellfireSlashAnimationState = new AnimationState();
    public final AnimationState greatLeapAnimationState = new AnimationState();
    public final AnimationState cataclysmAnimationState = new AnimationState();

    public final AnimationState deathAnimationState = new AnimationState();

    public final AnimationState tantrumAnimationState = new AnimationState();

    int skillTick;

    int skillActionGap;

    int slashCooldown;
    int backstepCooldown;
    int intolerableCooldown;
    int warlordHurtCooldown;
    private int passiveTimer;

    int bladeStateTicks;
    int noTargetTicks;

    int eliteCooldown;

    int eliteForceTicks = -1;

    int greatLeapCooldown;

    int chargeCooldown;

    int hellfireCooldown;

    int slashIdleTicks;
    int hellfireIdleTicks;

    int leapIdleTicks;

    int combatIdleTicks;

    boolean queuedBackstep;
    boolean queuedHellfire;

    boolean chargeAfterElite;

    float drawLockYaw;

    int guardTicks;
    int guardHits;
    int blockEndTicks;
    @Nullable
    Entity lastHurtBy;

    @Nullable
    LivingEntity dashTarget;
    int dashTicks;
    int dashHits;

    private float playerHateDamage;

    int blazingRainTicks;
    int blazingCooldown;
    boolean forcedBlock;

    float burstDamage;
    private int burstDamageWindow;
    private int burstInvulnTicks;

    Vec3 backstepDir = Vec3.ZERO;
    private float backstepDamage;
    private int backstepDamageWindow;
    boolean forcedBackstep;

    @Nullable
    LivingEntity leapTarget;
    boolean leaping;
    int leapPhase;

    final List<UUID> minions = new ArrayList<>();

    static final int MAX_ELITE_SKELETONS = 8;

    static final int ELITE_TOTAL_REPLENISH_THRESHOLD = 8;

    static final double ELITE_COUNT_RADIUS = 128.0D;

    private boolean rageApplied;

    private int basaltAshTicks;

    public static final double BASALT_ASH_RADIUS = 48.0D;

    public static final int WEATHER_TICKS = 20 * 60 * 3;

    public static final int SKY_BEAM_HEIGHT = 48;

    public static final int SKY_BEAM_TICKS = 60;

    private static final int ASH_SAMPLES_PER_TICK = 40;

    private static final int ASH_PER_SAMPLE = 32;

    private static final double ASH_SPREAD_RADIUS = 5.0D;

    private static final double ASH_COLUMN_HEIGHT = 22.0D;

    private static final double ASH_SEND_RANGE = 48.0D;

    public AbstractWitherWarlord(EntityType<? extends AbstractWitherWarlord> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 500;
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(GeneratedMod.WITHER_WARLORD_HELMET.get()));
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        var attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.addPermanentModifier(new AttributeModifier(
                    SABER_MODIFIER_ID, "wither_saber", 10.0D,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.WITHER_WARLORD_HEALTH.get())
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.WITHER_WARLORD_ATTACK.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WarlordMeleeGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SKILL, SKILL_NONE);
        this.entityData.define(DATA_BLOCK_PHASE, BLOCK_PHASE_NONE);
        this.entityData.define(DATA_CATACLYSM, false);
        this.entityData.define(DATA_WITHER_ARMOR, false);
        this.entityData.define(DATA_BLADE, 0);
        this.entityData.define(DATA_SKY_BEAM, 0);
        this.entityData.define(DATA_ENGAGED, false);
        this.entityData.define(DATA_TANTRUM, false);
    }

    public int getSkill() {
        return this.entityData.get(DATA_SKILL);
    }

    void setSkill(int skill) {
        this.entityData.set(DATA_SKILL, skill);
    }

    public int getBlockPhase() {
        return this.entityData.get(DATA_BLOCK_PHASE);
    }

    void setBlockPhase(int phase) {
        if (this.getBlockPhase() != phase) {
            this.entityData.set(DATA_BLOCK_PHASE, phase);
        }
    }

    public boolean hasCataclysmed() {
        return this.entityData.get(DATA_CATACLYSM);
    }

    public boolean isEnhanced() {
        return this.hasCataclysmed();
    }

    private void setCataclysmed(boolean value) {
        this.entityData.set(DATA_CATACLYSM, value);
    }

    public boolean hasWitherArmor() {
        return this.entityData.get(DATA_WITHER_ARMOR);
    }

    private void setWitherArmor(boolean value) {
        this.entityData.set(DATA_WITHER_ARMOR, value);
    }

    @Override
    public boolean isPowered() {
        return this.hasWitherArmor();
    }

    public boolean isSkyBeamLit() {
        return this.entityData.get(DATA_SKY_BEAM) > 0;
    }

    private void setSkyBeamTicks(int ticks) {
        this.entityData.set(DATA_SKY_BEAM, ticks);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        AABB box = super.getBoundingBoxForCulling();
        return this.isSkyBeamLit() ? box.expandTowards(0.0D, SKY_BEAM_HEIGHT, 0.0D) : box;
    }

    public boolean isCombatStance() {
        return this.entityData.get(DATA_ENGAGED) || this.getSkill() != SKILL_NONE;
    }

    public boolean isSaberAttackActive() {
        return this.getSkill() != SKILL_NONE;
    }

    public boolean isSlashAnimating() {
        return this.getSkill() == SKILL_SLASH;
    }

    public boolean isBackstepAnimating() {
        return this.getSkill() == SKILL_BACKSTEP;
    }

    public boolean isDrawAnimating() {
        return this.bladeState() == 1;
    }

    public boolean isSheathAnimating() {
        return this.bladeState() == 3;
    }

    public boolean isBladeSheathed() {
        return this.bladeState() == 0;
    }

    public boolean isTantrumAnimating() {
        return this.entityData.get(DATA_TANTRUM);
    }

    public void setTantrumAnimating(boolean value) {
        if (this.isTantrumAnimating() != value) {
            this.entityData.set(DATA_TANTRUM, value);
        }
    }

    public boolean isBladeDrawn() {
        return this.bladeState() == 2;
    }

    int bladeState() {
        return this.entityData.get(DATA_BLADE);
    }

    void setBladeState(int state) {
        if (this.bladeState() != state) {
            this.entityData.set(DATA_BLADE, state);
        }
    }

    private static boolean isWarlordImmune(DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.LAVA)) {
            return true; // 火焰 / 岩浆 / 灼热地面
        }
        if (source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.FLY_INTO_WALL)) {
            return true; // 摔落 / 动能
        }
        if (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)
                || source.is(DamageTypes.DRY_OUT)) {
            return true; // 窒息 / 溺水 / 脱水
        }
        return source.is(DamageTypes.WITHER);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (isWarlordImmune(source)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    private boolean inBlockSkill() {
        return this.getSkill() == SKILL_INTOLERABLE && this.guardTicks > 0
                || this.isBlazingLocked();
    }

    private boolean isBlazingLocked() {
        return this.getSkill() == SKILL_BLAZING;
    }

    public boolean isActionLocked() {
        return this.isDrawAnimating() || this.isBackstepAnimating()
                || this.isBlazingLocked() || this.getSkill() == SKILL_INTOLERABLE;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isWarlordImmune(source)) {
            return false;
        }
        if (this.hasWitherArmor() && source.is(DamageTypeTags.IS_PROJECTILE)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY(1.8D), this.getZ(),
                        6, 0.5D, 0.6D, 0.5D, 0.02D);
            }
            return false;
        }
        if (this.isBlazingLocked()) {
            return false;
        }
        if (this.getSkill() == SKILL_INTOLERABLE && this.guardTicks > 0
                && source.getEntity() != null) {
            if (!this.level().isClientSide()) {
                this.lastHurtBy = source.getEntity();
                this.guardHits++;
                if (this.guardHits >= BLOCK_MAX_HITS) {
                    this.guardTicks = 0;
                } else {
                    this.guardTicks = Math.max(this.guardTicks, BLOCK_WINDOW_TICKS) + BLOCK_EXTEND_TICKS;
                }
            }
            float blocked = applyDamageCap(amount * BLOCK_DAMAGE_MULTIPLIER);
            this.invulnerableTime = 0;
            boolean result = super.hurt(source, blocked);
            this.invulnerableTime = 0;
            if (result && !this.level().isClientSide) {
                if (source.getEntity() instanceof Player) {
                    this.addPlayerHateDamage(blocked);
                }
            }
            return result;
        }
        if (this.warlordHurtCooldown > 0 && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)
                && !inBlockSkill()) {
            return false;
        }
        if (this.burstInvulnTicks > 0 && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)
                && !inBlockSkill()) {
            return false;
        }
        float capped = applyDamageCap(amount);
        boolean result = super.hurt(source, amount);
        if (result) {
            if (!this.level().isClientSide) {
                this.backstepDamage += capped;
                this.backstepDamageWindow = BACKSTEP_DAMAGE_WINDOW_TICKS;
                if (source.getEntity() instanceof Player) {
                    this.addPlayerHateDamage(capped);
                }
                if (this.backstepDamage >= BACKSTEP_DAMAGE_THRESHOLD) {
                    this.backstepDamage = 0.0F;
                    this.forcedBackstep = true;
                }
                if (source.getEntity() != null && this.intolerableCooldown <= 0) {
                    this.forcedBlock = true;
                }
                this.burstDamage += capped;
                this.burstDamageWindow = BURST_INVULN_DAMAGE_WINDOW_TICKS;
                if (this.burstDamage >= BURST_INVULN_DAMAGE_THRESHOLD) {
                    this.burstDamage = 0.0F;
                    this.burstInvulnTicks = BURST_INVULN_TICKS + Math.max(this.warlordHurtCooldown, 0);
                    this.warlordHurtCooldown = 0;
                }
            }
        }
        return result;
    }

    @Override
    public void actuallyHurt(DamageSource source, float amount) {
        if (this.warlordHurtCooldown > 0 && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)
                && !inBlockSkill()) {
            return;
        }
        if (this.burstInvulnTicks > 0 && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)
                && !inBlockSkill()) {
            return;
        }
        float multiplier = 1.0F;
        if (this.getSkill() == SKILL_CATACLYSM
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            multiplier *= CATACLYSM_DAMAGE_MULTIPLIER;
        }
        if (this.isDrawAnimating() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            multiplier *= DRAW_DAMAGE_MULTIPLIER;
        }
        if (this.isBlazingLocked() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            multiplier *= BLOCK_DAMAGE_MULTIPLIER;
        }
        if (ModDamageSource.hellfireAttacks(source)) {
            multiplier *= HELLFIRE_RESISTANCE_MULTIPLIER;
        }
        SupremeBossHurt.hurt(this, source, amount, damageCap(), multiplier);
        if (source.getEntity() != null && !inBlockSkill()) {
            this.warlordHurtCooldown = INVULN_TICKS;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.updateAnimationStates();
        WarlordSaberTrail.tick(this);
    }

    @Override
    public float tickHeadTurn(float yaw, float speed) {
        if (this.isDrawAnimating()) {
            this.yBodyRot = this.getYRot();
            return speed;
        }
        return super.tickHeadTurn(yaw, speed);
    }

    private void updateAnimationStates() {
        this.combatWalkAnimationState.startIfStopped(this.tickCount);
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.combatIdleAnimationState.startIfStopped(this.tickCount);
        int phase = this.getBlockPhase();
        syncAnimation(this.slashAnimationState, this.isSlashAnimating());
        syncAnimation(this.backstepAnimationState, this.isBackstepAnimating());
        syncAnimation(this.drawAnimationState, this.isDrawAnimating());
        syncAnimation(this.sheathAnimationState, this.isSheathAnimating());
        syncAnimation(this.blockStartAnimationState,
                this.getSkill() == SKILL_INTOLERABLE && phase == BLOCK_PHASE_START);
        syncAnimation(this.blockLoopAnimationState,
                this.getSkill() == SKILL_INTOLERABLE && phase == BLOCK_PHASE_HOLD);
        syncAnimation(this.blockEndAnimationState,
                this.getSkill() == SKILL_INTOLERABLE && phase == BLOCK_PHASE_END);
        syncAnimation(this.counterChargeAnimationState,
                this.getSkill() == SKILL_INTOLERABLE && phase == BLOCK_PHASE_DASH);
        syncAnimation(this.blazingAnimationState, this.getSkill() == SKILL_BLAZING);
        syncAnimation(this.chargeAnimationState,
                this.getSkill() == SKILL_CHARGE || this.getSkill() == SKILL_ELITE);
        syncAnimation(this.hellfireSlashAnimationState, this.getSkill() == SKILL_HELLFIRE_SLASH);
        syncAnimation(this.greatLeapAnimationState, this.getSkill() == SKILL_GREAT_LEAP);
        syncAnimation(this.cataclysmAnimationState, this.getSkill() == SKILL_CATACLYSM);
        syncAnimation(this.deathAnimationState, this.deathTime > 0);
        syncAnimation(this.tantrumAnimationState, this.isTantrumAnimating());
    }

    private void syncAnimation(AnimationState state, boolean active) {
        if (active) {
            state.startIfStopped(this.tickCount);
        } else {
            state.stop();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            return;
        }
        LivingEntity engagedTarget = this.getTarget();
        this.entityData.set(DATA_ENGAGED, engagedTarget != null && engagedTarget.isAlive());
        if (this.warlordHurtCooldown > 0) {
            this.warlordHurtCooldown--;
        }
        if (this.burstInvulnTicks > 0) {
            this.burstInvulnTicks--;
        }
        if (this.eliteCooldown > 0) {
            this.eliteCooldown--;
        }
        if (this.greatLeapCooldown > 0) {
            this.greatLeapCooldown--;
        }
        if (this.chargeCooldown > 0) {
            this.chargeCooldown--;
        }
        if (this.hellfireCooldown > 0) {
            this.hellfireCooldown--;
        }
        if (this.blazingCooldown > 0) {
            this.blazingCooldown--;
        }
        if (this.slashCooldown > 0) {
            this.slashCooldown--;
        }
        if (this.backstepCooldown > 0) {
            this.backstepCooldown--;
        }
        if (this.intolerableCooldown > 0) {
            this.intolerableCooldown--;
        }
        if (this.skillActionGap > 0) {
            this.skillActionGap--;
        }
        if (this.backstepDamageWindow > 0 && --this.backstepDamageWindow == 0) {
            this.backstepDamage = 0.0F;
        }
        if (this.burstDamageWindow > 0 && --this.burstDamageWindow == 0) {
            this.burstDamage = 0.0F;
        }
        if (this.eliteForceTicks > 0) {
            LivingEntity forcedTarget = this.getTarget();
            if (forcedTarget != null && forcedTarget.isAlive()) {
                this.eliteForceTicks--;
            }
        }
        int skyBeam = this.entityData.get(DATA_SKY_BEAM);
        if (skyBeam > 0) {
            this.entityData.set(DATA_SKY_BEAM, skyBeam - 1);
        }
        this.tickBladeState();
        this.tickSkill();
        this.tickPassives();
        this.tickMinions();
        this.tickBasaltAsh();
        if (this.isDrawAnimating()) {
            this.getNavigation().stop();
            this.setYRot(this.drawLockYaw);
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
        }
        if (this.getSkill() == SKILL_CATACLYSM) {
            this.getNavigation().stop();
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
        }
        if (!this.hasCataclysmed() && this.getHealth() <= this.getMaxHealth() * 0.5F) {
            this.startCataclysm();
        }
    }

    private static final int[] SLASH_HIT_TICKS = {16, 35, 45};

    void tickSlash() {
        for (int i = 0; i < SLASH_HIT_TICKS.length; i++) {
            int hit = SLASH_HIT_TICKS[i];
            if (this.skillTick == hit) {
                this.playSound(SoundEvents.WITHER_SHOOT, 1.4F, 0.8F);
                boolean last = hit == SLASH_HIT_TICKS[SLASH_HIT_TICKS.length - 1];
                this.slashArea(3.2D, 2.0F * (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), last);
            }
        }
        if (this.skillTick >= 70) {
            this.endSkill(40);
        }
    }

    private void slashArea(double radius, float damage, boolean lastHit) {
        Vec3 look = this.getLookAngle();
        AABB box = this.getBoundingBox().inflate(radius).move(look.x * 1.5D, 0.0D, look.z * 1.5D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!this.canAttack(victim) || victim == this) {
                continue;
            }
            float amount = withMaxHealthBonus(damage, victim, SLASH_MAX_HEALTH_RATIO);
            boolean goetyGuard = WarlordShieldBreak.hasGoetyGuard(victim);
            if (goetyGuard && !lastHit) {
                this.dealWarlordDamage(victim, amount, this.damageSources().mobAttack(this));
            } else if (goetyGuard) {
                WarlordShieldBreak.attackThroughGoetyGuard(victim,
                        () -> this.dealWarlordDamage(victim, amount, WarlordShieldBreak.piercingSource(this)));
            } else {
                WarlordShieldBreak.breakGuard(victim);
                this.dealWarlordDamage(victim, amount, WarlordShieldBreak.piercingSource(this));
            }
            if (lastHit && !this.isFriendlyTo(victim)) {
                Vec3 push = victim.position().subtract(this.position()).normalize().scale(0.8D);
                victim.push(push.x, 0.7D, push.z);
            }
        }
    }

    void tickBackstep() {
        if (this.skillTick == 1) {
            LivingEntity target = this.getTarget();
            Vec3 away = target != null
                    ? this.position().subtract(target.position())
                    : this.getLookAngle().reverse();
            Vec3 horizontal = new Vec3(away.x, 0.0D, away.z);
            if (horizontal.lengthSqr() < 1.0E-6D) {
                Vec3 look = this.getLookAngle();
                horizontal = new Vec3(-look.x, 0.0D, -look.z);
            }
            this.backstepDir = horizontal.lengthSqr() < 1.0E-6D ? Vec3.ZERO : horizontal.normalize();
            this.getNavigation().stop();
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.2F, 0.6F);
        }
        if (this.skillTick >= 1 && this.skillTick <= BACKSTEP_MOVE_TICKS) {
            this.stepBackstep();
        } else if (this.backstepDir.lengthSqr() > 1.0E-6D) {
            this.backstepDir = Vec3.ZERO;
            this.getNavigation().stop();
        }
        if (this.skillTick == 10) {
            this.fireSkullVolley(9);
        }
        if (this.skillTick >= 30) {
            this.backstepDir = Vec3.ZERO;
            this.endSkill(60);
        }
    }

    private void stepBackstep() {
        if (this.backstepDir.lengthSqr() < 1.0E-6D) {
            return;
        }
        double step = BACKSTEP_DISTANCE / BACKSTEP_MOVE_TICKS;
        double nextX = this.getX() + this.backstepDir.x * step;
        double nextZ = this.getZ() + this.backstepDir.z * step;
        if (!this.canBackstepTo(nextX, nextZ)) {
            this.backstepDir = Vec3.ZERO;
            this.getNavigation().stop();
            return;
        }
        this.getNavigation().stop();
        this.fallDistance = 0.0F;
        this.setDeltaMovement(this.backstepDir.x * step, this.getDeltaMovement().y,
                this.backstepDir.z * step);
    }

    private boolean canBackstepTo(double x, double z) {
        BlockPos pos = new BlockPos(Mth.floor(x), Mth.floor(this.getY()), Mth.floor(z));
        if (!this.level().isInWorldBounds(pos) || !this.level().getWorldBorder().isWithinBounds(pos)) {
            return false;
        }
        BlockPos below = pos.below();
        if (this.level().getBlockState(below).getCollisionShape(this.level(), below).isEmpty()) {
            return false;
        }
        AABB bounds = this.getBoundingBox().move(x - this.getX(), 0.0D, z - this.getZ());
        return this.level().noCollision(this, bounds);
    }

    private void fireSkullVolley(int count) {
        Vec3 origin = new Vec3(this.getX(), this.getY(1.6D), this.getZ());
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            target = this.nearestEnemy(32.0D);
        }
        boolean aimed = target != null && target.isAlive();
        Vec3 aim;
        if (aimed) {
            this.faceTowards(target);
            aim = ProjectileIntercept.solveForTarget(origin, target, SKULL_POWER, SKULL_INERTIA)
                    .subtract(origin);
        } else {
            aim = this.getLookAngle();
        }
        if (aim.lengthSqr() < 1.0E-6D) {
            aim = this.getLookAngle();
        }
        if (aim.lengthSqr() < 1.0E-6D) {
            aim = new Vec3(0.0D, 0.0D, 1.0D);
        }
        aim = aim.normalize();
        Vec3 side = new Vec3(aim.z, 0.0D, -aim.x);
        side = side.lengthSqr() < 1.0E-6D ? new Vec3(1.0D, 0.0D, 0.0D) : side.normalize();
        Vec3 up = aim.cross(side).normalize();
        Vec3 muzzle = origin.add(aim.scale(1.2D));
        int enhancedIndex = count > 0 ? this.random.nextInt(count) : -1;
        for (int i = 0; i < count; i++) {
            int col = i % 3;
            int row = (i / 3) % 3;
            double yaw = Math.toRadians((col - 1) * VOLLEY_SPREAD_YAW_DEG
                    + (this.random.nextDouble() - 0.5D) * VOLLEY_JITTER_DEG);
            double pitch = Math.toRadians((row - 1) * VOLLEY_SPREAD_PITCH_DEG
                    + (this.random.nextDouble() - 0.5D) * VOLLEY_JITTER_DEG);
            double cy = Math.cos(yaw);
            double sy = Math.sin(yaw);
            double cp = Math.cos(pitch);
            double sp = Math.sin(pitch);
            Vec3 dir = aim.scale(cy * cp)
                    .add(side.scale(sy * cp))
                    .add(up.scale(sp))
                    .normalize();
            if (i == enhancedIndex) {
                EnhancedWitherSkull enhanced = new EnhancedWitherSkull(this.level(), this, dir);
                enhanced.setPos(muzzle.x, muzzle.y, muzzle.z);
                this.level().addFreshEntity(enhanced);
            } else {
                WitherSkull skull = new WitherSkull(this.level(), this, dir.x, dir.y, dir.z);
                skull.setPos(muzzle.x, muzzle.y, muzzle.z);
                this.level().addFreshEntity(skull);
            }
        }
        this.playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.7F);
    }

    void faceTowards(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double dy = target.getEyeY() - this.getEyeY();
        double flat = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, flat) * (180.0D / Math.PI)));
        this.setYRot(yaw);
        this.setXRot(pitch);
        this.yBodyRot = yaw;
        this.setYHeadRot(yaw);
        this.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    @Nullable
    private LivingEntity nearestEnemy(double radius) {
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity candidate : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(radius))) {
            if (candidate == this || !candidate.isAlive() || !this.canAttack(candidate)) {
                continue;
            }
            double dist = this.distanceToSqr(candidate);
            if (dist < bestDist) {
                bestDist = dist;
                best = candidate;
            }
        }
        return best;
    }

    public boolean isFriendlyTo(LivingEntity other) {
        return this.isAlliedTo(other) || ServantFaction.isSameFaction(this, other);
    }

    private void startCataclysm() {
        this.setCataclysmed(true);
        this.beginSkill(SKILL_CATACLYSM);
    }

    void tickCataclysm() {
        if (this.skillTick == 1) {
            Vec3 look = this.getLookAngle();
            Vec3 origin = this.position();
            for (int i = 1; i <= 6; i++) {
                Vec3 at = origin.add(look.x * i * 2.5D, 0.0D, look.z * i * 2.5D);
                this.spawnHellfireRing(at, 3.5D, 24, 8);
            }
            this.playSound(SoundEvents.WITHER_SPAWN, 3.0F, 0.6F);
        }
        if (this.skillTick == 40) {
            this.fireSkyBeam();
        }
        if (this.skillTick >= 70) {
            this.setWitherArmor(true);
            this.endSkill(120);
        }
    }

    private void fireSkyBeam() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setSkyBeamTicks(SKY_BEAM_TICKS);
        this.playSound(SoundEvents.WITHER_SPAWN, 3.0F, 0.5F);
        if (server.dimension() == Level.OVERWORLD) {
            server.setWeatherParameters(0, WEATHER_TICKS, true, true);
        } else {
            this.basaltAshTicks = WEATHER_TICKS;
        }
    }

    private void tickBasaltAsh() {
        if (this.basaltAshTicks <= 0) {
            return;
        }
        this.basaltAshTicks--;
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double radiusSqr = BASALT_ASH_RADIUS * BASALT_ASH_RADIUS;
        List<ServerPlayer> viewers = new ArrayList<>();
        for (ServerPlayer player : server.players()) {
            if (player.distanceToSqr(this) <= radiusSqr) {
                viewers.add(player);
            }
        }
        if (viewers.isEmpty()) {
            return;
        }
        double baseX = this.getX();
        double baseY = this.getY();
        double baseZ = this.getZ();
        double sendRangeSqr = ASH_SEND_RANGE * ASH_SEND_RANGE;
        for (int i = 0; i < ASH_SAMPLES_PER_TICK; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double dist = Math.sqrt(this.random.nextDouble()) * BASALT_ASH_RADIUS;
            double px = baseX + Math.cos(angle) * dist;
            double pz = baseZ + Math.sin(angle) * dist;
            double py = baseY + 1.0D + this.random.nextDouble() * ASH_COLUMN_HEIGHT;
            BlockPos probe = BlockPos.containing(px, py, pz);
            if (!server.isInWorldBounds(probe)
                    || server.getBlockState(probe).isCollisionShapeFullBlock(server, probe)) {
                continue;
            }
            for (ServerPlayer viewer : viewers) {
                if (viewer.distanceToSqr(px, py, pz) > sendRangeSqr) {
                    continue;
                }
                server.sendParticles(viewer, ParticleTypes.WHITE_ASH, true,
                        px, py, pz, ASH_PER_SAMPLE,
                        ASH_SPREAD_RADIUS, ASH_SPREAD_RADIUS * 0.5D, ASH_SPREAD_RADIUS, 0.0D);
            }
        }
    }

    void spawnHellfireRing(Vec3 center, double radius, int duration, int spokes) {
        for (int i = 0; i < spokes; i++) {
            double angle = Math.PI * 2.0D * i / spokes;
            Vec3 at = center.add(Math.cos(angle) * radius, 0.0D, Math.sin(angle) * radius);
            this.spawnGoetyHellfirePillar(at.x, at.y, at.z, duration, false);
        }
        this.spawnGoetyHellfirePillar(center.x, center.y, center.z, duration, false);
    }

    void spawnGoetyHellfirePillar(double x, double y, double z, int duration,
            boolean muteSelfDamage) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        FirePillar pillar = new FirePillar(server, x, y, z);
        if (muteSelfDamage) {
            pillar.getPersistentData().putBoolean(HELLFIRE_PILLAR_TAG, true);
        }
        pillar.setOwner(this);
        pillar.setDuration(Math.max(20, duration));
        server.addFreshEntity(pillar);
    }

    private void tickPassives() {
        this.passiveTimer++;
        if (this.getHealth() < this.getMaxHealth() && this.passiveTimer % 20 == 0) {
            this.heal(1.0F);
        }
        if (this.passiveTimer % 20 != 0) {
            return;
        }
        int withered = 0;
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(32.0D))) {
            if (entity != this && entity.hasEffect(MobEffects.WITHER)) {
                withered++;
            }
        }
        if (withered > 0) {
            this.heal(Math.min(3, withered));
        }
        int enemies = 0;
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(24.0D))) {
            if (entity == this || !entity.isAlive() || entity.isSpectator()
                    || this.isFriendlyTo(entity)) {
                continue;
            }
            boolean attacking = entity instanceof Mob mob && mob.getTarget() == this;
            if (!attacking) {
                attacking = entity.getLastHurtMob() == this;
            }
            if (attacking) {
                enemies++;
            }
        }
        this.updateRage(enemies);
        AbstractGreenSmith.rallySkeletons((ServerLevel) this.level(), this, true,
                entity -> this.isFriendlyTo(entity));
    }

    private void updateRage(int enemies) {
        var attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        var armor = this.getAttribute(Attributes.ARMOR);
        if (attack == null || armor == null) {
            return;
        }
        if (enemies <= 2) {
            if (this.rageApplied) {
                attack.removeModifier(RAGE_ATTACK_ID);
                armor.removeModifier(RAGE_ARMOR_ID);
                this.rageApplied = false;
            }
            return;
        }
        int extra = enemies - 2;
        attack.removeModifier(RAGE_ATTACK_ID);
        armor.removeModifier(RAGE_ARMOR_ID);
        double currentAttack = attack.getValue();
        double currentArmor = armor.getValue();
        double rageAttack = Math.max(0.0D,
                Math.min(ATTACK_TOTAL_CAP - currentAttack, BASE_ATTACK * 0.05D * extra));
        double rageArmor = Math.max(0.0D, Math.min(ARMOR_TOTAL_CAP - currentArmor, extra));
        attack.addTransientModifier(new AttributeModifier(RAGE_ATTACK_ID, "warlord_rage_attack",
                rageAttack, AttributeModifier.Operation.ADDITION));
        armor.addTransientModifier(new AttributeModifier(RAGE_ARMOR_ID, "warlord_rage_armor",
                rageArmor, AttributeModifier.Operation.ADDITION));
        this.rageApplied = true;
    }

    public float getPlayerHateDamage() {
        return this.playerHateDamage;
    }

    private void addPlayerHateDamage(float amount) {
        this.playerHateDamage += amount;
    }

    public void resetPlayerHateDamage() {
        this.playerHateDamage = 0.0F;
    }

    static float withMaxHealthBonus(float baseAmount, LivingEntity target, float ratio) {
        return baseAmount + target.getMaxHealth() * ratio;
    }

    void dealWarlordDamage(LivingEntity target, float baseAmount, DamageSource source) {
        float amount = baseAmount;
        if (isFrostEnemy(target)) {
            amount *= 2.0F;
        }
        MobEffect chillHide = goetyEffect("chill_hide");
        if (chillHide != null && target.hasEffect(chillHide)) {
            amount *= 0.5F;
        }
        target.hurt(source, amount);
        if (target.isAlive()) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0, false, true), this);
        }
    }

    public void applyHellfireSlashDamage(LivingEntity target) {
        float amount = withMaxHealthBonus(HELLFIRE_SLASH_DAMAGE, target, SLASH_MAX_HEALTH_RATIO);
        this.dealWarlordDamage(target, amount, this.damageSources().mobAttack(this));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return false;
    }

    private static boolean isFrostEnemy(LivingEntity entity) {
        if (entity instanceof SnowGolem || entity instanceof Stray
                || entity instanceof AbstractFrostGolemServant) {
            return true;
        }
        String type = entity.getType().toString().toLowerCase();
        return type.contains("snow") || type.contains("frost") || type.contains("ice");
    }

    static MobEffect goetyEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    List<Mob> getLivingMinions() {
        List<Mob> result = new ArrayList<>();
        this.minions.removeIf(uuid -> {
            Entity entity = ((ServerLevel) this.level()).getEntity(uuid);
            if (entity instanceof Mob living && living.isAlive()) {
                result.add(living);
                return false;
            }
            return true;
        });
        return result;
    }

    private void tickMinions() {
        if (this.minions.isEmpty()) {
            return;
        }
        List<Mob> alive = this.getLivingMinions();
        if (alive.isEmpty()) {
            MobEffect shielding = goetyEffect("shielding");
            if (shielding != null) {
                this.removeEffect(shielding);
            }
            this.minions.clear();
            return;
        }
        if (this.tickCount % 20 == 0) {
            MobEffect shielding = goetyEffect("shielding");
            if (shielding != null) {
                this.addEffect(new MobEffectInstance(shielding, 40, 1, false, false));
            }
        }
        LivingEntity target = this.getTarget();
        if (target != null) {
            for (Mob minion : alive) {
                if (minion.getTarget() == null) {
                    minion.setTarget(target);
                }
            }
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    private static final int DEATH_ANIMATION_TICKS = 42;

    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= DEATH_ANIMATION_TICKS
                && !this.level().isClientSide && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide && !(this instanceof WitherWarlordServant)) {
            this.spawnAtLocation(new ItemStack(GeneratedMod.WITHERING_SABER.get()));
            this.spawnAtLocation(new ItemStack(GeneratedMod.WITHER_WARLORD_HELMET.get()));
            this.spawnAtLocation(new ItemStack(Items.NETHER_STAR));
        }
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.WITHER) {
            return false;
        }
        if (effect.getEffect() == MobEffects.WEAKNESS) {
            return false;
        }
        if (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(NBT_CATACLYSM, this.hasCataclysmed());
        tag.putBoolean(NBT_WITHER_ARMOR, this.hasWitherArmor());
        tag.putInt("WarlordBasaltAsh", this.basaltAshTicks);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(NBT_CATACLYSM)) {
            this.setCataclysmed(tag.getBoolean(NBT_CATACLYSM));
        }
        if (tag.contains(NBT_WITHER_ARMOR)) {
            this.setWitherArmor(tag.getBoolean(NBT_WITHER_ARMOR));
        }
        this.basaltAshTicks = tag.getInt("WarlordBasaltAsh");
    }

    @Override
    public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty,
            MobSpawnType spawnType,
            @Nullable net.minecraft.world.entity.SpawnGroupData spawnGroupData,
            @Nullable net.minecraft.nbt.CompoundTag tag) {
        net.minecraft.world.entity.SpawnGroupData result =
                super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(GeneratedMod.WITHER_WARLORD_HELMET.get()));
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        return result;
    }

    void tickSkill() {
        WarlordSkillScheduler.tickSkill(this);
    }

    void trackCombatIdle() {
        WarlordSkillScheduler.trackCombatIdle(this);
    }

    void runCombatWatchdog() {
        WarlordSkillScheduler.runCombatWatchdog(this);
    }

    void rollSkillChances() {
        WarlordSkillScheduler.rollSkillChances(this);
    }

    void tickForceTimers() {
        WarlordSkillScheduler.tickForceTimers(this);
    }

    boolean targetWithinRange(double range) {
        return WarlordSkillScheduler.targetWithinRange(this, range);
    }

    boolean targetInSlashRange() {
        return WarlordSkillScheduler.targetInSlashRange(this);
    }

    void tickBladeState() {
        WarlordSkillScheduler.tickBladeState(this);
    }

    void beginSkill(int skill) {
        WarlordSkillScheduler.beginSkill(this, skill);
    }

    void endSkill(int recovery) {
        WarlordSkillScheduler.endSkill(this, recovery);
    }

    int pickWeightedSkill() {
        return WarlordSkillScheduler.pickWeightedSkill(this);
    }

    int skillCooldown(int skill) {
        return WarlordSkillScheduler.skillCooldown(this, skill);
    }

    void tickIntolerable() {
        WarlordBlockCounter.tickIntolerable(this);
    }

    void beginBlockEnd() {
        WarlordBlockCounter.beginBlockEnd(this);
    }

    void resolveBlockCounter() {
        WarlordBlockCounter.resolveBlockCounter(this);
    }

    void startBlockDash() {
        WarlordBlockCounter.startBlockDash(this);
    }

    void tickDash() {
        WarlordBlockCounter.tickDash(this);
    }

    void strikeDashTarget() {
        WarlordBlockCounter.strikeDashTarget(this);
    }

    void tickElite() {
        WarlordEliteSummon.tickElite(this);
    }

    void summonMinions() {
        WarlordEliteSummon.summonMinions(this);
    }

    public void performBetrayalSkills() {
        WarlordEliteSummon.performBetrayalSkills(this);
    }

    void enchantEliteGear(ItemStack stack) {
        WarlordEliteSummon.enchantEliteGear(this, stack);
    }

    void purgeRiderlessEliteHorses(ServerLevel server) {
        WarlordEliteSummon.purgeRiderlessEliteHorses(this, server);
    }

    boolean tryTriggerCharge() {
        return WarlordCharge.tryTriggerCharge(this);
    }

    void tickCharge() {
        WarlordCharge.tickCharge(this);
    }

    void tickBlazing() {
        WarlordBlazing.tickBlazing(this);
    }

    void startBlazingRain() {
        WarlordBlazing.startBlazingRain(this);
    }

    void applyBlazingRainDamage() {
        WarlordBlazing.applyBlazingRainDamage(this);
    }

    void lockInPlace() {
        WarlordBlazing.lockInPlace(this);
    }

    void spawnDenseHellfirePillars(Vec3 center, double radius, int duration) {
        WarlordBlazing.spawnDenseHellfirePillars(this, center, radius, duration);
    }

    void tickHellfireSlash() {
        WarlordBlazing.tickHellfireSlash(this);
    }

    void spawnBladeBeam() {
        WarlordBlazing.spawnBladeBeam(this);
    }

    Vec3 saberOrigin(Vec3 look) {
        return WarlordBlazing.saberOrigin(this, look);
    }

    void tickGreatLeap() {
        WarlordGreatLeap.tickGreatLeap(this);
    }

    void landLeapImpact() {
        WarlordGreatLeap.landLeapImpact(this);
    }

    void spawnIgnisFlameBurst(ServerLevel server, Vec3 center) {
        WarlordGreatLeap.spawnIgnisFlameBurst(this, server, center);
    }

    void raiseGroundBlocks(Vec3 center, double radius, float popUp) {
        WarlordGreatLeap.raiseGroundBlocks(this, center, radius, popUp);
    }

    LivingEntity pickGreatLeapTarget() {
        return WarlordGreatLeap.pickGreatLeapTarget(this);
    }

    boolean isLeapTargetValid(@Nullable LivingEntity candidate) {
        return WarlordGreatLeap.isLeapTargetValid(this, candidate);
    }

    LivingEntity findHighestHealthTarget(double radius) {
        return WarlordGreatLeap.findHighestHealthTarget(this, radius);
    }

    <T extends Summoned> int countOwnedElite(ServerLevel server, Class<T> type) {
        return WarlordEliteSummon.countOwnedElite(this, server, type);
    }

    private static final class WarlordMeleeGoal extends MeleeAttackGoal {
        private final AbstractWitherWarlord warlord;

        WarlordMeleeGoal(AbstractWitherWarlord warlord, double speed) {
            super(warlord, speed, true);
            this.warlord = warlord;
        }

        @Override
        public boolean canUse() {
            return !this.warlord.isActionLocked() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.warlord.isActionLocked() && super.canContinueToUse();
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
        }

        @Override
        public double getAttackReachSqr(LivingEntity target) {
            return MELEE_REACH * MELEE_REACH;
        }

        private boolean wasInReach;

        @Override
        public void tick() {
            super.tick();
            LivingEntity target = this.warlord.getTarget();
            boolean inReach = target != null
                    && this.warlord.distanceToSqr(target) <= MELEE_REACH * MELEE_REACH;
            // 只在刚进入近战范围那一次停路，避免每刻 stop → 下 tick 又重新寻路导致来回扭头
            if (inReach && !this.wasInReach) {
                this.warlord.getNavigation().stop();
            }
            this.wasInReach = inReach;
        }
    }
}
