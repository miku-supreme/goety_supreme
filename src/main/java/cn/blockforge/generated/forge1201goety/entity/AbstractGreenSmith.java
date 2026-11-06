package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.GatherTrailParticleOption;
import com.Polarice3.Goety.client.particles.RisingCircleParticleOption;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.client.particles.SoulShockwaveParticleOption;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.PhantomServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import com.Polarice3.Goety.common.entities.projectiles.MonsoonCloud;
import com.Polarice3.Goety.common.entities.projectiles.NecroBolt;
import com.Polarice3.Goety.common.entities.projectiles.PoisonQuill;
import com.Polarice3.Goety.common.entities.projectiles.SteamMissile;
import com.Polarice3.Goety.common.entities.projectiles.SurgingOrb;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.common.entities.util.TridentStorm;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.wild.BlossomSpell;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SThunderBoltPacket;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmithGoals;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.util.CastQueue;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithStaffFxLayer;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSunImmunity;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.renderer.WindBlastFx;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSoulJarMixin;
import cn.blockforge.generated.goetysupreme.mixin.ReviveServantItemSoulJarGuardMixin;

public abstract class AbstractGreenSmith extends Summoned implements UndeadCapeBearer {

    public static final double MAX_HEALTH = 360.0D;
    public static final double ATTACK_DAMAGE = 10.0D;
    public static final double ARMOR = 12.0D;
    public static final double ARMOR_TOUGHNESS = 6.0D;
    public static final double MOVEMENT_SPEED = 0.25D;
    public static final double KNOCKBACK_RESISTANCE = 1.0D;

    public static final double FOLLOW_RANGE = 64.0D;

    public static final float DAMAGE_CAP = 20.0F;

    public static float damageCap() {
        return SupremeMobsConfig.GREEN_SMITH_DAMAGE_CAP.get().floatValue();
    }

    public static float applyDamageCap(float amount) {
        float cap = damageCap();
        return cap > 0.0F && amount > cap ? cap : amount;
    }

    public static final float TELEPORT_DAMAGE = 12.0F;

    static final int FORCED_TELEPORT_MIN_HITS = 4;
    static final int FORCED_TELEPORT_MAX_HITS = 6;

    int hitsSinceTeleport;

    int forcedTeleportHits;

    public static final int MAX_SOUL_JAR_UPGRADES = 2;

    static final double[] SOUL_JAR_HEALTH_FACTOR = {1.0D, 1.5D, 2.5D};

    static final float SOUL_JAR_DAMAGE_BONUS = 1.0F;

    static final int SOUL_JAR_LIFESPAN_BONUS_TICKS = 30 * 20;
    static final float SOUL_JAR_PROJECTILE_SPEED_PER_LEVEL = 0.10F;
    static final int SOUL_JAR_RALLY_TICKS_PER_LEVEL = 50;
    static final float SOUL_JAR_RALLY_HEAL_PER_LEVEL = 2.5F;
    static final float SOUL_JAR_CLONE_HEAL_PER_LEVEL = 4.0F;
    static final int SOUL_JAR_CLONE_LASER_TICKS_PER_LEVEL = 5;
    static final double SOUL_JAR_WIND_RADIUS_PER_LEVEL = 0.5D;

    static final float SOUL_JAR_CLOUD_RADIUS_PER_LEVEL = 1.0F;
    static final float SOUL_JAR_FIELD_REDUCTION_PER_LEVEL = 0.025F;

    static final int SOUL_JAR_THORN_DAMAGE_PER_LEVEL = 3;

    static final double SOUL_JAR_THORN_RADIUS_PER_LEVEL = 1.0D;

    static final float FLYING_SKULL_DAMAGE = 5.0F;

    static final float SOUL_JAR_SKULL_EXPLOSION_PER_LEVEL = 0.5F;

    static final float POISON_DART_EXTRA_BASE = 1.0F;

    static final int POISON_DART_PIERCE = 2;

    static final EntityDataAccessor<Integer> DATA_SOUL_JAR_UPGRADES =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.INT);

    static final EntityDataAccessor<Boolean> DATA_SPOOKY_FOCUS =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.BOOLEAN);
    static final EntityDataAccessor<Boolean> DATA_REAPING_FOCUS =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.BOOLEAN);
    static final EntityDataAccessor<Boolean> DATA_PHANTASM_FOCUS =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.BOOLEAN);

    int soulJarUpgrades;

    static final int HURT_INVULNERABLE_TICKS = 15;

    public static final int ANTI_HEAL_TICKS = 100;

    static final int REGEN_INTERVAL = 20;

    static final float INTERRUPT_DAMAGE = 30.0F;

    static final float CHARGE_REDUCTION_NECROMANCER = 0.20F;
    static final float CHARGE_REDUCTION_PER_KIND = 0.05F;

    static final float MIN_CHARGE_SCALE = 0.50F;

    public static final int CAST_NONE = 0;
    public static final int CAST_ATTACK = 1;
    public static final int CAST_SUMMON = 2;
    public static final int CAST_CAST = 3;
    public static final int CAST_FORWARD = 4;
    public static final int CAST_WIND = 5;

    public static final int CAST_RALLY = 6;
    public static final int CAST_FIELD = 7;
    public static final int CAST_DEATH = 8;

    static final int[] CAST_LENGTHS = {0, 20, 36, 60, 80, 50, 65, 445, 90};

    static final float[] CAST_IMPACT = {
            0.0F, 0.50F, 35.0F / 36.0F, 5.0F / 60.0F, 0.30F, 28.0F / 50.0F, 0.10F, 35.0F / 445.0F, 0.0F};

    static final int DEATH_ANIMATION_TICKS = 90;

    static final int NAMELESS_STAFF_COLOR = 11009086;

    static final int GREEN_LIGHTNING_COLOR = 0x7CE23A;

    static final int CLONE_FALLBACK_LIFE = 200;

    static final int CLONE_STEAM_SHOTS = 5;

    static final int CLONE_POISON_SHOTS = 5;

    static final int CLONE_BURST_INTERVAL = 2;

    static final float CLONE_VANGUARD_CHANCE = 0.15F;

    static final int CLONE_LASER_TICKS = 20;

    static final float CLONE_LASER_STEP_DEGREES = 60.0F;

    static final int CLONE_HEAL_RADIUS_LEVEL = 4;

    static final double SOUL_HEAL_RADIUS_PER_LEVEL = 8.0D;

    static final double CLONE_HEAL_RADIUS = SOUL_HEAL_RADIUS_PER_LEVEL * CLONE_HEAL_RADIUS_LEVEL;
    static final float CLONE_HEAL_AMOUNT = 16.0F;

    static final int SOUL_HEAL_COLOR = 2804175;

    static final int[] SOUL_HEAL_RING_DELAYS = {0, 5, 10};

    static final int CLONE_BURST_STEAM = 0;
    static final int CLONE_BURST_POISON = 1;

    public static final int ATTACK_COOLDOWN = 26;

    public static final int SUMMON_COOLDOWN = 600;

    static final int REFUSE_SHAKE_TICKS = 40;

    static final byte REFUSE_SHAKE_EVENT = 9;
    public static final int BORROW_COOLDOWN = 200;
    public static final int LASER_COOLDOWN = 600;

    public static final int RALLY_COOLDOWN = 600;
    public static final int WIND_COOLDOWN = 400;

    static final int FIELD_COOLDOWN = 1600;

    static final int FIELD_DURATION = 400;

    static final int FIELD_SHIELD_WIND_COLOR = 0x9BE07A;

    public static final int FIELD_SHIELD_AURA_COLOR = 0x7CCB5B;

    static final int FIELD_SHIELD_WIND_INTERVAL = 5;
    static final int FIELD_SHIELD_AURA_INTERVAL = 15;

    public static final int BORROW_CHARGE_TICKS = 20;

    public static final int BORROW_SKILLS = 4;

    static final int BORROW_TRIDENT_COUNT = 8;
    static final int BORROW_THORN_BALL_COUNT = 3;

    static final double BORROW_THORN_BALL_SPREAD = 0.22D;
    static final int BORROW_SCYTHE_COUNT = 3;

    static final int SKULL_BURST_BATCHES = 2;
    static final int SKULL_BURST_PER_BATCH = 7;

    static final int SKULL_BURST_INTERVAL = 5;

    static final int TRIDENT_STORM_WARMUP = 40;

    static final int TRIDENT_BURST_INTERVAL = 5;

    static final int RALLY_BUFF_TICKS = 200;
    static final int RALLY_IRON_HIDE_LEVEL = 1;
    static final int RALLY_RALLYING_LEVEL = 1;

    static final float RALLY_HEAL_MIN = 10.0F;
    static final float RALLY_HEAL_MAX = 15.0F;

    static final int RALLY_NECROSIS_TICKS = 10 * 20;

    static final int RALLY_SUN_IMMUNE_TICKS = 30 * 20;

    static final double RALLY_RADIUS = 32.0D;

    public static final int LASER_CHARGE_TICKS = 30;

    static final int LASER_FIRE_DELAY = 15;

    static final float LASER_CHARGE_TURN_DEGREES = 10.0F;

    static final float LASER_TRACK_TURN_DEGREES = 1.2F;

    static final double[][] CHARGE_STAFF_TIP = {
            {0.00D, 0.827D, 0.412D, 2.251D},
            {0.10D, 0.777D, 0.403D, 2.776D},
            {0.20D, 0.548D, 0.426D, 3.391D},
            {0.25D, 0.387D, 0.458D, 3.611D},
            {0.30D, 0.377D, 0.459D, 3.616D},
            {0.50D, 0.342D, 0.464D, 3.635D},
            {1.00D, 0.279D, 0.472D, 3.663D},
            {1.50D, 0.258D, 0.475D, 3.672D},
    };

    static final float LASER_START_YAW_MIN = 14.0F;
    static final float LASER_START_YAW_MAX = 24.0F;

    static final float LASER_START_PITCH_OFFSET = 3.0F;

    static final int WEIGHT_SUMMON = 6;

    static final int WEIGHT_BORROW = 10;
    static final int WEIGHT_LASER = 2;
    static final int WEIGHT_WIND = 5;

    static final int WEIGHT_RALLY_HEALTHY = 3;
    static final int WEIGHT_RALLY_AT_HALF = 12;
    static final int WEIGHT_RALLY_NEAR_DEATH = 36;

    static final int WEIGHT_RALLY_RETREAT_BONUS = 24;

    static final int RETREAT_HEAL_WINDOW = 200;
    static final int WEIGHT_FIELD_HURT = 20;

    static final int WEIGHT_FIELD_HEALTHY = 0;

    static final ResourceLocation[] SKELETON_SERVANT_VARIANTS = {
            ResourceLocation.fromNamespaceAndPath("goety", "skeleton_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "sunken_skeleton_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "mossy_skeleton_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "stray_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "rattled_servant"),
    };

    public static final double MELEE_RANGE_SQR = 9.0D;

    public static final double ATTACK_RANGE_SQR = 1024.0D;

    public static final float BOLT_DAMAGE = 12.0F;
    static final float LIGHTNING_DAMAGE = 5.0F;
    static final int LIGHTNING_RADIUS = 4;

    static final int LIGHTNING_TRAP_DURATION = 10;

    static final int FIELD_BOLT_INTERVAL = 20;
    static final int FIELD_BOLT_TARGETS = 4;
    static final double FIELD_BOLT_RANGE = 16.0D;

    static final int STAFF_GATHER_PARTICLES = 8;

    static final double STAFF_GATHER_RADIUS_MIN = 0.28D;
    static final double STAFF_GATHER_RADIUS_MAX = 0.72D;

    public double staffFxX;
    public double staffFxY;
    public double staffFxZ;

    public boolean staffFxReady;

    public int staffFxTick = -1;

    static final ResourceLocation GREEN_FIRE_ID =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "green_fire");
    static final ResourceLocation NECROMANCER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "necromancer_servant");

    static final ResourceLocation CAIRN_NECROMANCER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "cairn_necromancer_servant");

    static final ResourceLocation MOSSY_NECROMANCER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "mossy_necromancer_servant");
    static final ResourceLocation VANGUARD_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "vanguard_servant");
    static final ResourceLocation BLACKGUARD_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "blackguard_servant");

    static final ResourceLocation DROWNED_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "drowned_servant");

    static final String SUNKEN_NECROMANCER_SERVANT_PATH = "sunken_necromancer_servant";

    static final ResourceLocation DRAUGR_NECROMANCER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety_cataclysm", "draugr_necromancer_servant");

    static final float OMINOUS_REPLACEMENT_CHANCE = 0.5F;
    static final float DRAUGR_REPLACEMENT_CHANCE = 0.5F;
    static final String GOETY_CATACLYSM_MOD_ID = "goety_cataclysm";

    static boolean ominousLoaded() {
        return OminousCompat.loaded();
    }

    static boolean cataclysmLoaded() {
        return ModList.get().isLoaded(GOETY_CATACLYSM_MOD_ID);
    }

    static final ResourceLocation SPOOKY_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "spooky_focus");
    static final ResourceLocation REAPING_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "reaping_focus");
    static final ResourceLocation PHANTASM_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "phantasm_focus");

    static final String HAUNT_FOCUS_PATH = "haunt_focus";
    static final ResourceLocation HAUNT_ENTITY_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "haunt");

    static final ResourceLocation BONE_DRUID_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "skeleton_druid_focus");
    static final ResourceLocation SKELETON_DRUID_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "skeleton_druid_servant");
    static final ResourceLocation VEXING_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "vexing_focus");
    static final ResourceLocation TWILIGHT_WRAITH_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "twilight_wraith_servant");
    static final int HAUNT_FOCUS_BATCH = 3;
    static final int HAUNT_FOCUS_CAP = 6;
    static final ResourceLocation WRAITH_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wraith_servant");
    static final ResourceLocation REAPER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "reaper_servant");

    static final String REDSTONE_FOCUS_PATH = "redstone_focus";
    static final ResourceLocation GRAVE_CUBE_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "grave_cube");

    static final int MAX_FOCUS_FAMILIARS = 2;

    static final double APPROACH_TELEPORT_MIN_DISTANCE = 48.0D;

    static final double APPROACH_TELEPORT_DISTANCE_MIN = 24.0D;
    static final double APPROACH_TELEPORT_DISTANCE_MAX = 36.0D;

    static final int APPROACH_TELEPORT_COOLDOWN = 300;

    static final double PHANTOM_SPAWN_HEIGHT = 3.0D;

    static final int PHANTOM_POTENCY = 3;

    static final int MAX_NECROMANCERS = 3;
    static final int MAX_VANGUARDS = 3;
    static final int MAX_BLACKGUARDS = 3;

    static final int MAX_SKELETONS = 16;

    static final float SMALL_BOSS_MIN_HEALTH = 220.0F;

    static final float SMALL_BOSS_SUMMON_CHANCE = 0.30F;

    public static final String NECROMANCER_TAG = "GreenSmithNecromancer";
    static final String CLONE_TAG = "GreenSmithClone";
    static final String NIGHT_DONE_TAG = "GreenSmithNightDone";

    static final EntityDataAccessor<Integer> DATA_CAST_ANIMATION =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_CAST_TICKS =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_ANTI_HEAL =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Boolean> DATA_FIELD_ACTIVE =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.BOOLEAN);

    static final EntityDataAccessor<Float> DATA_ATTACK_SPEED =
            SynchedEntityData.defineId(AbstractGreenSmith.class, EntityDataSerializers.FLOAT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState castAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_ATTACK = 1;

    public static final int EFFECT_BORROW = 20;

    public static final int EFFECT_LASER_CHARGE = 21;

    public static final int EFFECT_LASER_FIRE = 22;

    public static final int EFFECT_CLONE_SUMMON = 30;
    public static final int EFFECT_CLONE_BORROW = 32;
    public static final int EFFECT_CLONE_HEAL = 33;

    public static final int SPELL_NONE = 0;
    public static final int SPELL_SUMMON = 1;
    public static final int SPELL_BORROW = 3;
    public static final int SPELL_LASER = 4;
    public static final int SPELL_WIND = 5;
    public static final int SPELL_RALLY = 6;
    public static final int SPELL_FIELD = 7;

    public static final int SPELL_CLOUD = 8;

    public static final int EFFECT_CLOUD = 34;

    public static final int EFFECT_CLONE_CLOUD = 36;

    public static final int CLOUD_COOLDOWN = 400;
    static final int WEIGHT_CLOUD = 6;

    static final float CLOUD_RADIUS = 3.0F;
    static final int CLOUD_LIFESPAN = 100;
    static final float CLOUD_EXTRA_DAMAGE = 6.0F;

    static final double CLOUD_HOVER_HEIGHT = 4.0D;

    static final float CLONE_ORB_EXTRA_DAMAGE = 4.0F;

    static final float CLONE_ORB_SPREAD_DEGREES = 10.0F;

    static final int[] CLONE_COMBO_SPELLS = {
            SPELL_SUMMON, SPELL_BORROW, SPELL_LASER, SPELL_RALLY};

    int[] cloneComboPool() {
        return GreenSmithClones.cloneComboPool(this);
    }

    private int lastCastAnimation = CAST_NONE;
    int castAnimation;
    int castEffect = EFFECT_NONE;
    int castElapsed;
    int castLength;
    public int castImpact;
    boolean castApplied;
    private boolean castUnstoppable;
    float castDamageTaken;

    final CastQueue castQueue = new CastQueue();

    public int borrowedSkill;

    boolean directSummonToMaster;

    public int cantDo;

    public int attackCooldown;
    public int summonCooldown;
    public int borrowCooldown;
    public int laserCooldown;
    public int rallyCooldown;
    public int windCooldown;
    int fieldCooldown;

    public int cloudCooldown;

    boolean draugrSoulJarGiven;

    final List<ResourceLocation> smallBossSoulJarTypes = new ArrayList<>();

    int smallBossPity;
    public int spellPickCooldown;
    int pendingSpell = CAST_NONE;

    public int retreatHealTicks;

    float warlordHateDamage;

    private int warlordScanCooldown;

    private int skeletonRallyCooldown;

    private boolean warlordEngaged;

    public static final float WARLORD_HATE_THRESHOLD = 20.0F;

    private boolean fleeingWarlord;

    public boolean isFleeingWarlord() {
        return this.fleeingWarlord;
    }

    public void setFleeingWarlord(boolean fleeing) {
        if (fleeing == this.fleeingWarlord) {
            return;
        }
        this.fleeingWarlord = fleeing;
        if (fleeing) {
            this.cancelCastingMove();
            this.setTarget(null);
        }
    }

    int hurtInvulnerable;
    int antiHealTicks;
    private int regenTimer;

    int fieldTicks;
    int fieldStrikeTimer;

    int fieldBoltTimer;

    int fieldStrikeCount;
    public boolean fieldUsedAtHalf;

    float fieldYaw;
    float fieldPitch;

    UUID laserTarget;
    int laserTicks;

    @Nullable
    GreenSmithLaserBeam laserBeam;

    boolean hardKill;

    boolean cloneDismissPending;

    boolean cloneHealClaimed;

    int cloneSlot;

    int cloneBurstRemaining;
    int cloneBurstTimer;
    int cloneBurstKind;

    int tridentBurstRemaining;

    int tridentBurstTimer;

    @Nullable
    UUID tridentTarget;

    int skullBurstRemaining;

    int skullBurstTimer;

    @Nullable
    UUID skullTarget;

    final List<UUID> windClones = new ArrayList<>();

    boolean cloneComboPending;

    final List<UUID> necromancers = new ArrayList<>();

    boolean spookyFocusGiven;
    boolean reapingFocusGiven;
    boolean phantasmFocusGiven;

    boolean hauntFocusGiven;

    boolean boneDruidFocusGiven;

    boolean vexFocusGiven;

    boolean redstoneFocusGiven;

    int cheatSheets;

    final List<UUID> spookyFamiliars = new ArrayList<>();
    final List<UUID> reaperFamiliars = new ArrayList<>();
    final List<UUID> phantomFamiliars = new ArrayList<>();

    final List<UUID> boneDruidFamiliars = new ArrayList<>();
    final List<UUID> vexFamiliars = new ArrayList<>();
    final List<UUID> redstoneFamiliars = new ArrayList<>();

    final Map<UUID, Integer> pendingFamiliars = new HashMap<>();

    int teleportToTargetCooldown;

    public AbstractGreenSmith(EntityType<? extends AbstractGreenSmith> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 220;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.GREEN_SMITH_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.GREEN_SMITH_ATTACK.get())
                .add(Attributes.ARMOR, ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CAST_ANIMATION, CAST_NONE);
        this.entityData.define(DATA_CAST_TICKS, 0);
        this.entityData.define(DATA_ANTI_HEAL, 0);
        this.entityData.define(DATA_FIELD_ACTIVE, false);
        this.entityData.define(DATA_ATTACK_SPEED, DEFAULT_ATTACK_SPEED);
        this.entityData.define(DATA_SOUL_JAR_UPGRADES, 0);
        this.entityData.define(DATA_SPOOKY_FOCUS, false);
        this.entityData.define(DATA_REAPING_FOCUS, false);
        this.entityData.define(DATA_PHANTASM_FOCUS, false);
    }

    public static final float DEFAULT_ATTACK_SPEED = 1.0F;

    static final float MIN_ATTACK_SPEED = 0.1F;

    public void setAttackSpeed(float attackSpeed) {
        this.entityData.set(DATA_ATTACK_SPEED, Math.max(attackSpeed, MIN_ATTACK_SPEED));
    }

    public float getAttackSpeed() {
        return this.entityData.get(DATA_ATTACK_SPEED);
    }

    public int scaledAttackCooldown(int baseTicks) {
        int speed = Mth.floor(Math.max(this.getAttackSpeed(), 1.0F));
        return Math.max(1, baseTicks / speed);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("GreenAttackCd", this.attackCooldown);
        tag.putInt("GreenSummonCd", this.summonCooldown);
        tag.putInt("GreenBorrowCd", this.borrowCooldown);
        tag.putInt("GreenLaserCd", this.laserCooldown);
        tag.putInt("GreenRallyCd", this.rallyCooldown);
        tag.putInt("GreenWindCd", this.windCooldown);
        tag.putInt("GreenFieldCd", this.fieldCooldown);
        tag.putInt("GreenCloudCd", this.cloudCooldown);
        tag.putInt("GreenTeleportCd", this.teleportToTargetCooldown);
        tag.putInt("GreenFieldTicks", this.fieldTicks);
        tag.putInt("GreenAntiHeal", this.antiHealTicks);
        tag.putBoolean("GreenFieldUsedAtHalf", this.fieldUsedAtHalf);
        tag.putBoolean(NIGHT_DONE_TAG, this.nightDone);
        tag.putFloat("AttackSpeed", this.getAttackSpeed());
        tag.putInt("GreenSmithSoulJarUpgrades", this.soulJarUpgrades);
        tag.putBoolean("GreenDraugrSoulJar", this.draugrSoulJarGiven);
        if (!this.smallBossSoulJarTypes.isEmpty()) {
            ListTag list = new ListTag();
            for (ResourceLocation type : this.smallBossSoulJarTypes) {
                list.add(StringTag.valueOf(type.toString()));
            }
            tag.put("GreenSmallBossSoulJars", list);
        }
        tag.putInt("GreenSmallBossPity", this.smallBossPity);
        tag.putBoolean("GreenSpookyFocus", this.spookyFocusGiven);
        tag.putBoolean("GreenReapingFocus", this.reapingFocusGiven);
        tag.putBoolean("GreenPhantasmFocus", this.phantasmFocusGiven);
        tag.putBoolean("GreenHauntFocus", this.hauntFocusGiven);
        tag.putBoolean("GreenBoneDruidFocus", this.boneDruidFocusGiven);
        tag.putBoolean("GreenVexFocus", this.vexFocusGiven);
        tag.putBoolean("GreenRedstoneFocus", this.redstoneFocusGiven);
        tag.putInt(FocusSlotRules.CHEAT_SHEETS_TAG, this.cheatSheets);
        this.saveNecromancers(tag);
        this.saveFocusFamiliars(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.castAnimation = CAST_NONE;
        this.castEffect = EFFECT_NONE;
        this.castElapsed = 0;
        this.castLength = 0;
        this.castApplied = false;
        this.entityData.set(DATA_CAST_ANIMATION, CAST_NONE);
        this.entityData.set(DATA_CAST_TICKS, 0);
        this.attackCooldown = tag.getInt("GreenAttackCd");
        this.summonCooldown = tag.getInt("GreenSummonCd");
        this.borrowCooldown = tag.getInt("GreenBorrowCd");
        this.laserCooldown = tag.getInt("GreenLaserCd");
        this.rallyCooldown = tag.contains("GreenRallyCd")
                ? tag.getInt("GreenRallyCd") : tag.getInt("GreenDrainCd");
        this.windCooldown = tag.getInt("GreenWindCd");
        this.fieldCooldown = tag.getInt("GreenFieldCd");
        this.cloudCooldown = tag.getInt("GreenCloudCd");
        this.teleportToTargetCooldown = tag.getInt("GreenTeleportCd");
        this.draugrSoulJarGiven = tag.getBoolean("GreenDraugrSoulJar");
        this.smallBossSoulJarTypes.clear();
        ListTag smallBossJars = tag.getList("GreenSmallBossSoulJars", 8);
        for (int i = 0; i < smallBossJars.size(); i++) {
            this.addSmallBossType(ResourceLocation.tryParse(smallBossJars.getString(i)));
        }
        if (tag.contains("GreenSmallBossSoulJar")) {
            this.addSmallBossType(ResourceLocation.tryParse(tag.getString("GreenSmallBossSoulJar")));
        }
        this.smallBossPity = Math.max(0, tag.getInt("GreenSmallBossPity"));
        this.fieldTicks = tag.getInt("GreenFieldTicks");
        this.antiHealTicks = tag.getInt("GreenAntiHeal");
        this.fieldUsedAtHalf = tag.getBoolean("GreenFieldUsedAtHalf");
        this.nightDone = tag.getBoolean(NIGHT_DONE_TAG);
        if (tag.contains("AttackSpeed")) {
            this.setAttackSpeed(tag.getFloat("AttackSpeed"));
        }
        this.loadNecromancers(tag);
        this.spookyFocusGiven = tag.getBoolean("GreenSpookyFocus");
        this.reapingFocusGiven = tag.getBoolean("GreenReapingFocus");
        this.phantasmFocusGiven = tag.getBoolean("GreenPhantasmFocus");
        this.hauntFocusGiven = tag.getBoolean("GreenHauntFocus");
        this.boneDruidFocusGiven = tag.getBoolean("GreenBoneDruidFocus");
        this.vexFocusGiven = tag.getBoolean("GreenVexFocus");
        this.redstoneFocusGiven = tag.getBoolean("GreenRedstoneFocus");
        this.cheatSheets = Mth.clamp(tag.getInt(FocusSlotRules.CHEAT_SHEETS_TAG),
                0, FocusSlotRules.MAX_CHEAT_SHEETS);
        this.entityData.set(DATA_SPOOKY_FOCUS, this.spookyFocusGiven);
        this.entityData.set(DATA_REAPING_FOCUS, this.reapingFocusGiven);
        this.entityData.set(DATA_PHANTASM_FOCUS, this.phantasmFocusGiven);
        this.loadFocusFamiliars(tag);
        this.entityData.set(DATA_ANTI_HEAL, this.antiHealTicks);
        this.entityData.set(DATA_FIELD_ACTIVE, this.fieldTicks > 0);
        this.soulJarUpgrades = Mth.clamp(tag.getInt("GreenSmithSoulJarUpgrades"),
                0, MAX_SOUL_JAR_UPGRADES);
        this.entityData.set(DATA_SOUL_JAR_UPGRADES, this.soulJarUpgrades);
        this.refreshSoulJarAttributes();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_SOUL_JAR_UPGRADES.equals(key)) {
            this.soulJarUpgrades = this.entityData.get(DATA_SOUL_JAR_UPGRADES);
            this.refreshDimensions();
        } else if (DATA_SPOOKY_FOCUS.equals(key)) {
            this.spookyFocusGiven = this.entityData.get(DATA_SPOOKY_FOCUS);
        } else if (DATA_REAPING_FOCUS.equals(key)) {
            this.reapingFocusGiven = this.entityData.get(DATA_REAPING_FOCUS);
        } else if (DATA_PHANTASM_FOCUS.equals(key)) {
            this.phantasmFocusGiven = this.entityData.get(DATA_PHANTASM_FOCUS);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == REFUSE_SHAKE_EVENT) {
            this.cantDo = REFUSE_SHAKE_TICKS;
            return;
        }
        super.handleEntityEvent(id);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return GreenSmithTributes.mobInteract(this, player, hand);
    }

    void refuseSummon() {
        GreenSmithTributes.refuseSummon(this);
    }

    int focusSlots() {
        return GreenSmithTributes.focusSlots(this);
    }

    int grantedFocusCount() {
        return GreenSmithTributes.grantedFocusCount(this);
    }

    LivingEntity summonOwner() {
        return GreenSmithTributes.summonOwner(this);
    }

    static boolean isItem(ItemStack stack, ResourceLocation id) {
        return GreenSmithTributes.isItem(stack, id);
    }

    static boolean isDraugrSoulJar(ItemStack stack) {
        return GreenSmithTributes.isDraugrSoulJar(stack);
    }

    void applyFocusGift(Player player, ItemStack stack, boolean spooky) {
        GreenSmithTributes.applyFocusGift(this, player, stack, spooky);
    }

    void applyPhantasmFocusGift(Player player, ItemStack stack) {
        GreenSmithTributes.applyPhantasmFocusGift(this, player, stack);
    }

    void applyHauntFocusGift(Player player, ItemStack stack) {        this.hauntFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(0.6D), this.getZ(),
                    20, this.getBbWidth() * 0.6D, this.getBbHeight() * 0.4D,
                    this.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY(1.0D), this.getZ(),
                    12, this.getBbWidth() * 0.5D, this.getBbHeight() * 0.3D,
                    this.getBbWidth() * 0.5D, 0.02D);
        }
        this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.4F);
    }

    void applyBoneDruidFocusGift(Player player, ItemStack stack) {
        GreenSmithTributes.applyBoneDruidFocusGift(this, player, stack);
    }

    void applyVexFocusGift(Player player, ItemStack stack) {
        GreenSmithTributes.applyVexFocusGift(this, player, stack);
    }

    void applyRedstoneFocusGift(Player player, ItemStack stack) {
        GreenSmithTributes.applyRedstoneFocusGift(this, player, stack);
    }

    void applyDraugrSoulJar(Player player, ItemStack stack) {
        GreenSmithSoulJarHelper.applyDraugrSoulJar(this, player, stack);
    }

    LivingEntity storedNecromancer(ItemStack stack) {
        return GreenSmithTributes.storedNecromancer(this, stack);
    }

    ResourceLocation pickSmallBossType() {
        return GreenSmithSoulJarHelper.pickSmallBossType(this);
    }

    boolean addSmallBossType(ResourceLocation type) {
        return GreenSmithTributes.addSmallBossType(this, type);
    }

    boolean ownerHasRoomForSmallBoss(ServerLevel server, ResourceLocation typeId) {
        return GreenSmithSoulJarHelper.ownerHasRoomForSmallBoss(this, server, typeId);
    }

    static boolean isNecromancerLike(LivingEntity entity) {
        return GreenSmithTributes.isNecromancerLike(entity);
    }

    static boolean isNecromancerLike(LivingEntity entity, ResourceLocation key) {
        return GreenSmithTributes.isNecromancerLike(entity, key);
    }

    static boolean isSmallBossNecromancer(LivingEntity necromancer) {
        return GreenSmithTributes.isSmallBossNecromancer(necromancer);
    }

    void applySmallBossSoulJar(Player player, ItemStack stack, LivingEntity stored) {
        GreenSmithSoulJarHelper.applySmallBossSoulJar(this, player, stack, stored);
    }

    void applySoulJarUpgrade(Player player, ItemStack stack) {
        GreenSmithSoulJarHelper.applySoulJarUpgrade(this, player, stack);
    }

    void refreshSoulJarAttributes() {
        GreenSmithSoulJarHelper.refreshSoulJarAttributes(this);
    }

    public int getSoulJarUpgrades() {
        return GreenSmithTributes.getSoulJarUpgrades(this);
    }

    public float getSoulJarSizeScale() {
        return GreenSmithTributes.getSoulJarSizeScale(this);
    }

    public float soulJarDamageBonus() {
        return GreenSmithSoulJarHelper.soulJarDamageBonus(this);
    }

    float effectiveSoulJarDamageBonus() {
        return GreenSmithSoulJarHelper.effectiveSoulJarDamageBonus(this);
    }

    void setSoulJarUpgrades(int value) {
        GreenSmithTributes.setSoulJarUpgrades(this, value);
    }

    int effectiveSoulJarUpgrades() {
        return GreenSmithSoulJarHelper.effectiveSoulJarUpgrades(this);
    }

    int soulJarLifespanBonusTicks() {
        return GreenSmithSoulJarHelper.soulJarLifespanBonusTicks(this);
    }

    float soulJarProjectileSpeedScale() {
        return GreenSmithTributes.soulJarProjectileSpeedScale(this);
    }

    double windBlastRadius(double base) {
        return GreenSmithSpells.windBlastRadius(this, base);
    }

    void applySoulJarSummonBuffs(Summoned minion) {
        GreenSmithSoulJarHelper.applySoulJarSummonBuffs(this, minion);
    }

    static boolean isSkeletonServant(LivingEntity entity) {
        return GreenSmithTributes.isSkeletonServant(entity);
    }

    boolean nightDone;

    void saveNecromancers(CompoundTag tag) {
        GreenSmithTributes.saveNecromancers(this, tag);
    }

    void loadNecromancers(CompoundTag tag) {
        GreenSmithTributes.loadNecromancers(this, tag);
    }

    void saveFocusFamiliars(CompoundTag tag) {
        GreenSmithTributes.saveFocusFamiliars(this, tag);
    }

    void loadFocusFamiliars(CompoundTag tag) {
        GreenSmithTributes.loadFocusFamiliars(this, tag);
    }

    static void saveRoster(CompoundTag tag, String prefix, List<UUID> roster) {
        GreenSmithTributes.saveRoster(tag, prefix, roster);
    }

    static void loadRoster(CompoundTag tag, String prefix, List<UUID> roster) {
        GreenSmithTributes.loadRoster(tag, prefix, roster);
    }

    public int getCastAnimation() {
        return this.entityData.get(DATA_CAST_ANIMATION);
    }

    public int getCastAnimationTick() {
        return this.entityData.get(DATA_CAST_TICKS);
    }

    void spellCastParticles() {
        for (int i = 0; i < this.random.nextInt(35) + 10; ++i) {
            this.level().addParticle(
                    GeneratedMod.GREEN_TOTEM_EFFECT.get(),
                    this.getX(), this.getY(), this.getZ(), 0.45D, 0.45D, 0.45D);
        }
    }

    public boolean isCasting() {
        return this.getCastAnimation() != CAST_NONE;
    }

    public boolean isSpellCastingParticles() {
        int cast = this.getCastAnimation();
        return cast != CAST_NONE && cast != CAST_ATTACK;
    }

    public int getAntiHealTicks() {
        return this.entityData.get(DATA_ANTI_HEAL);
    }

    public int getAntiHealTotal() {
        return ANTI_HEAL_TICKS;
    }

    public boolean isSecondPhase() {
        return this.getHealth() <= this.getMaxHealth() * 0.5F || this.isFieldActive();
    }

    public boolean isFieldActive() {
        return this.entityData.get(DATA_FIELD_ACTIVE);
    }

    void setFieldActive(boolean active) {
        this.entityData.set(DATA_FIELD_ACTIVE, active);
    }

    public boolean isMovingForAnimation() {
        return this.walkAnimation.speed() > 0.01F;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbstractGreenSmithGoals.SpellGoal(this));
        this.goalSelector.addGoal(2, new AbstractGreenSmithGoals.CombatGoal(this));
        this.goalSelector.addGoal(6, new AbstractGreenSmithLookGoals.CastGatedStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new AbstractGreenSmithLookGoals.CastGatedLookAtPlayerGoal(this));
        this.goalSelector.addGoal(8, new AbstractGreenSmithLookGoals.CastGatedRandomLookGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.isSpellCastingParticles()) {
            this.spellCastParticles();
        }
        if (this.hurtInvulnerable > 0) {
            --this.hurtInvulnerable;
        }
        if (this.cantDo > 0) {
            --this.cantDo;
        }
        if (!this.level().isClientSide) {
            this.tickPendingFamiliars();
            if (this.isDeadOrDying()) {
                return;
            }
            this.tickCooldowns();
            this.tickWarlordEncounter();
            if (this.isClone()) {
                this.getNavigation().stop();
                this.tickCloneBurst();
            } else if (!this.isFleeingWarlord()) {
                this.tickWindClones();
                this.tickTridentBurst();
                this.tickSkullBurst();
                this.tickSkeletonRally();
                this.tickDistanceTeleport();
            }
            this.tickCast();
            this.tickAntiHeal();
            this.tickRegen();
            this.tickField();
            if (!this.isFleeingWarlord()) {
                this.tickLaser();
            }
            this.tickNecromancerRoster();
            this.tickNightfall();
        } else {
            this.tickClientAnimations();
        }
    }

    void tickCooldowns() {
        if (this.attackCooldown > 0) --this.attackCooldown;
        if (this.summonCooldown > 0) --this.summonCooldown;
        if (this.borrowCooldown > 0) --this.borrowCooldown;
        if (this.laserCooldown > 0) --this.laserCooldown;
        if (this.rallyCooldown > 0) --this.rallyCooldown;
        if (this.windCooldown > 0) --this.windCooldown;
        if (this.fieldCooldown > 0) --this.fieldCooldown;
        if (this.cloudCooldown > 0) --this.cloudCooldown;
        if (this.spellPickCooldown > 0) --this.spellPickCooldown;
        if (this.retreatHealTicks > 0) --this.retreatHealTicks;
        if (this.teleportToTargetCooldown > 0) --this.teleportToTargetCooldown;
    }

    void tickWarlordEncounter() {
        if (--this.warlordScanCooldown > 0) {
            return;
        }
        this.warlordScanCooldown = 10;
        boolean nearby = this.hasNearbyWarlord();
        if (nearby != this.warlordEngaged) {
            this.warlordEngaged = nearby;
            this.warlordHateDamage = 0.0F;
        }
    }

    boolean hasNearbyWarlord() {
        for (AbstractWitherWarlord warlord : this.level().getEntitiesOfClass(
                AbstractWitherWarlord.class, this.getBoundingBox().inflate(FOLLOW_RANGE))) {
            if (warlord.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public float getWarlordHateDamage() {
        return this.warlordHateDamage;
    }

    public boolean isWarlordHateReached() {
        return this.warlordHateDamage > WARLORD_HATE_THRESHOLD;
    }

    static AbstractWitherWarlord warlordAttacker(DamageSource source) {
        if (source.getEntity() instanceof AbstractWitherWarlord warlord) {
            return warlord;
        }
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.Projectile projectile
                && projectile.getOwner() instanceof AbstractWitherWarlord warlord) {
            return warlord;
        }
        return null;
    }

    void tickAntiHeal() {
        if (this.antiHealTicks > 0) {
            --this.antiHealTicks;
            this.entityData.set(DATA_ANTI_HEAL, this.antiHealTicks);
        }
    }

    void tickRegen() {
        if (++this.regenTimer < REGEN_INTERVAL) {
            return;
        }
        this.regenTimer = 0;
        if (this.isAntiHealActive() || this.getHealth() >= this.getMaxHealth()) {
            return;
        }
        this.heal(1.0F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(GreenSmithFx.heal(), this.getX(),
                    this.getY() + this.getBbHeight() * 0.6D, this.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.01D);
        }
    }

    public boolean isAntiHealActive() {
        return this.getAntiHealTicks() > 0 || this.hasEffect(MobEffects.WITHER);
    }

    void tickCast() {
        if (this.isDeadOrDying() || this.isRemoved()) {
            this.castQueue.clear();
        }
        if (this.castAnimation == CAST_NONE) {
            return;
        }
        ++this.castElapsed;
        if (this.castEffect == EFFECT_LASER_CHARGE) {
            this.tickLaserCharge();
        }
        if (this.castEffect == EFFECT_BORROW && !this.castApplied) {
            this.tickBorrowCharge();
        }
        if (this.castEffect == EFFECT_CLOUD && !this.castApplied) {
            this.tickMonsoonCharge();
        }
        if (!this.castApplied && this.castElapsed >= this.castImpact) {
            this.castApplied = true;
            this.applyCast(this.castEffect);
            this.directSummonToMaster = false;
        }
        if (this.cloneDismissPending && this.cloneBurstRemaining <= 0
                && this.castElapsed >= this.castLength) {
            this.dismissClone();
            return;
        }
        boolean selfAiming = this.castEffect == EFFECT_LASER_CHARGE
                || this.castEffect == EFFECT_LASER_FIRE;
        if (!selfAiming && this.castAnimation != CAST_FIELD) {
            LivingEntity aimTarget = this.getTarget();
            if (aimTarget != null && aimTarget.isAlive()) {
                this.aimAt(aimTarget, 8.0F);
            }
        }
        if (this.castElapsed >= this.castLength) {
            int finished = this.castAnimation;
            this.setCastAnimation(CAST_NONE, 0);
            this.castElapsed = 0;
            this.laserTicks = 0;
            this.laserTarget = null;
            this.clearCorruptedBeam();
            if (this.cloneDismissPending) {
                this.dismissClone();
            }
            if (finished == CAST_WIND && this.cloneComboPending) {
                this.startCloneCombo();
            }
            this.flushQueuedCast();
        } else {
            this.entityData.set(DATA_CAST_TICKS, Math.max(0, this.castLength - this.castElapsed));
        }
    }

    public void spawnStaffGatherFx() {
        if (!this.staffFxReady || !this.isCasting() || !this.level().isClientSide) {
            return;
        }
        if (this.staffFxTick == this.tickCount) {
            return;
        }
        this.staffFxTick = this.tickCount;
        Vec3 tip = new Vec3(this.staffFxX, this.staffFxY, this.staffFxZ);
        GatherTrailParticleOption gather =
                new GatherTrailParticleOption(new ColorUtil(NAMELESS_STAFF_COLOR), tip);
        for (int i = 0; i < STAFF_GATHER_PARTICLES; i++) {
            double theta = this.random.nextDouble() * Math.PI * 2.0D;
            double phi = Math.acos(2.0D * this.random.nextDouble() - 1.0D);
            double radius = STAFF_GATHER_RADIUS_MIN
                    + this.random.nextDouble() * (STAFF_GATHER_RADIUS_MAX - STAFF_GATHER_RADIUS_MIN);
            double ox = radius * Math.sin(phi) * Math.cos(theta);
            double oy = radius * Math.cos(phi);
            double oz = radius * Math.sin(phi) * Math.sin(theta);
            this.level().addParticle(gather, tip.x + ox, tip.y + oy, tip.z + oz,
                    0.0D, 0.0D, 0.0D);
        }
        ParticleOptions core = GreenSmithFx.necroFire();
        for (int i = 0; i < 2; i++) {
            this.level().addParticle(core, tip.x, tip.y, tip.z, 0.0D, 0.0D, 0.0D);
        }
    }

    public void resetStaffFx() {
        this.staffFxReady = false;
        this.staffFxTick = -1;
    }

    void dismissClone() {
        GreenSmithClones.dismissClone(this);
    }

    @Override
    public void lifeSpanDamage() {
        if (this.isClone()) {
            this.dismissClone();
        } else {
            super.lifeSpanDamage();
        }
    }

    void tickClientAnimations() {
        int cast = this.getCastAnimation();
        if (cast != this.lastCastAnimation) {
            this.lastCastAnimation = cast;
            if (cast != CAST_NONE) {
                this.castAnimationState.start(this.tickCount);
            }
        }
        if (cast == CAST_NONE) {
            this.idleAnimationState.startIfStopped(this.tickCount);
            this.walkAnimationState.startIfStopped(this.tickCount);
        } else {
            this.idleAnimationState.stop();
            this.walkAnimationState.stop();
        }
        if (this.deathTime == 1) {
            this.deathAnimationState.start(this.tickCount);
        }
    }

    void setCastAnimation(int animation, int length) {
        this.castAnimation = animation;
        this.castLength = length;
        this.castElapsed = 0;
        this.castApplied = false;
        this.castImpact = animation == CAST_NONE ? 0
                : Math.max(1, Math.round(length * CAST_IMPACT[animation]));
        this.entityData.set(DATA_CAST_ANIMATION, animation);
        this.entityData.set(DATA_CAST_TICKS, animation == CAST_NONE ? 0 : length);
        if (animation == CAST_NONE) {
            this.resetStaffFx();
        }
    }

    public void startCast(int animation) {
        this.startCast(animation, animation, false);
    }

    public void startCast(int animation, boolean unstoppable) {
        this.startCast(animation, animation, unstoppable);
    }

    public void startCast(int animation, int effect, boolean unstoppable) {
        if (this.castAnimation != CAST_NONE && this.castElapsed < this.castLength) {
            this.castQueue.offer(animation, effect, unstoppable);
            return;
        }
        this.beginCast(animation, effect, unstoppable);
    }

    void beginCast(int animation, int effect, boolean unstoppable) {
        this.directSummonToMaster = false;
        if (animation == CAST_SUMMON && !this.isClone()) {
            this.playGoetySound("prepare_summon", 0.85F, 1.0F);
        }
        this.castDamageTaken = 0.0F;
        this.castUnstoppable = unstoppable;
        this.castEffect = effect;
        this.setCastAnimation(animation, CAST_LENGTHS[animation]);
        if (!this.isNonChargeCast(animation)) {
            this.castImpact = this.scaledCharge(this.castImpact);
        }
    }

    void flushQueuedCast() {
        if (this.castQueue.isEmpty() || this.isCasting()) {
            return;
        }
        int animation = this.castQueue.animation();
        int effect = this.castQueue.effect();
        boolean unstoppable = this.castQueue.flag();
        this.castQueue.clear();
        if (this.isDeadOrDying() || this.isRemoved()) {
            return;
        }
        this.beginCast(animation, effect, unstoppable);
    }

    float chargeTimeScale() {
        if (!(this.level() instanceof ServerLevel server)) {
            return 1.0F;
        }
        java.util.Set<ResourceLocation> kinds = new java.util.HashSet<>();
        boolean hasNecromancer = false;
        for (UUID id : this.necromancers) {
            if (!(server.getEntity(id) instanceof LivingEntity living) || !living.isAlive()) {
                continue;
            }
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(living.getType());
            if (key == null) {
                continue;
            }
            kinds.add(key);
            if (key.getPath().contains("necromancer")) {
                hasNecromancer = true;
            }
        }
        float scale = 1.0F;
        if (hasNecromancer) {
            scale -= CHARGE_REDUCTION_NECROMANCER;
        }
        scale -= CHARGE_REDUCTION_PER_KIND * kinds.size();
        return Mth.clamp(scale, MIN_CHARGE_SCALE, 1.0F);
    }

    public int scaledCharge(int baseTicks) {
        return Math.max(1, Math.round(baseTicks * this.chargeTimeScale()));
    }

    void applyCast(int effect) {
        switch (effect) {
            case CAST_ATTACK -> this.fireNamelessBolt();
            case CAST_SUMMON -> this.castSummonNecromancers();
            case CAST_WIND -> this.castWindBurst();
            case CAST_RALLY -> this.castRally();
            case CAST_FIELD -> this.castField();
            case EFFECT_BORROW -> this.castBorrowedSkill();
            case EFFECT_LASER_CHARGE -> this.beginLaserFire();
            case EFFECT_LASER_FIRE -> this.castCorruptLaser();
            case EFFECT_CLONE_SUMMON -> this.cloneSummonSkeleton();
            case EFFECT_CLONE_BORROW -> this.cloneBorrowedSkill();
            case EFFECT_CLONE_HEAL -> this.cloneHealFocus();
            case EFFECT_CLOUD -> this.castMonsoonCloud();
            case EFFECT_CLONE_CLOUD -> this.cloneLightningOrb();
            default -> {
            }
        }
    }

    boolean isNonChargeCast(int animation) {
        return animation == CAST_ATTACK || animation == CAST_SUMMON || animation == CAST_RALLY;
    }

    void tickCastInterrupt() {
        if (this.castAnimation == CAST_NONE || this.castUnstoppable || this.isFieldActive()
                || this.isNonChargeCast(this.castAnimation)) {
            return;
        }
        if (this.castDamageTaken > INTERRUPT_DAMAGE) {
            this.cancelCastingMove();
            if (this.level() instanceof ServerLevel server) {
                this.teleportBackward(server, 8.0D, false);
            }
        }
    }

    void cancelCastingMove() {
        this.setCastAnimation(CAST_NONE, 0);
        this.castElapsed = 0;
        this.castApplied = false;
        this.castDamageTaken = 0.0F;
        this.castQueue.clear();
        this.directSummonToMaster = false;
        this.laserTicks = 0;
        this.laserTarget = null;
        this.clearCorruptedBeam();
        this.resetTridentBurst();
        this.resetSkullBurst();
        this.abortCloneCombo();
    }

    int rallyWeight() {
        return GreenSmithSpells.rallyWeight(this);
    }

    public int pickWeightedSpell() {
        boolean hurt = this.getHealth() <= this.getMaxHealth() * 0.5F;
        if (hurt && !this.fieldUsedAtHalf && this.fieldCooldown <= 0 && this.fieldTicks <= 0) {
            return SPELL_FIELD;
        }
        int[] weights = new int[9];
        weights[SPELL_SUMMON] = this.summonCooldown <= 0 ? WEIGHT_SUMMON : 0;
        weights[SPELL_BORROW] = this.borrowCooldown <= 0 ? WEIGHT_BORROW : 0;
        weights[SPELL_LASER] = this.laserCooldown <= 0 ? WEIGHT_LASER : 0;
        weights[SPELL_WIND] = this.windCooldown <= 0 ? WEIGHT_WIND : 0;
        weights[SPELL_CLOUD] = ominousLoaded() && this.cloudCooldown <= 0 ? WEIGHT_CLOUD : 0;
        weights[SPELL_RALLY] = this.rallyWeight();
        weights[SPELL_FIELD] = this.fieldCooldown <= 0 && this.fieldTicks <= 0
                ? (hurt ? WEIGHT_FIELD_HURT : WEIGHT_FIELD_HEALTHY) : 0;
        int total = 0;
        for (int w : weights) {
            total += w;
        }
        if (total <= 0) {
            return SPELL_NONE;
        }
        int roll = this.random.nextInt(total);
        for (int i = 0; i < weights.length; i++) {
            roll -= weights[i];
            if (roll < 0) {
                return i;
            }
        }
        return SPELL_NONE;
    }

    void fireNamelessBolt() {
        GreenSmithSpells.fireNamelessBolt(this);
    }

    ResourceLocation[] pickNecromancerTrio(ResourceLocation[] base) {
        return GreenSmithNecromancerRoster.pickNecromancerTrio(this, base);
    }

    ResourceLocation availableDraugrServant() {
        if (!this.draugrSoulJarGiven || !cataclysmLoaded()) {
            return null;
        }
        return isRegisteredEntityType(DRAUGR_NECROMANCER_SERVANT_ID)
                ? DRAUGR_NECROMANCER_SERVANT_ID : null;
    }

    ResourceLocation availableSunkenServant() {
        if (!ominousLoaded()) {
            return null;
        }
        return OminousCompat.entity(SUNKEN_NECROMANCER_SERVANT_PATH);
    }

    static boolean isRegisteredEntityType(ResourceLocation id) {
        return ForgeRegistries.ENTITY_TYPES.getValue(id) != null;
    }

    static boolean isSunkenNecromancer(ResourceLocation id) {
        return id != null && SUNKEN_NECROMANCER_SERVANT_PATH.equals(id.getPath());
    }

    void castSummonNecromancers() {
        GreenSmithNecromancerRoster.castSummonNecromancers(this);
    }

    void summonFocusWraithsOrDruids(ServerLevel server, Vec3 right) {
        GreenSmithNecromancerRoster.summonFocusWraithsOrDruids(this, server, right);
    }

    void summonFocusDruids(ServerLevel server, Vec3 right) {
        GreenSmithNecromancerRoster.summonFocusDruids(this, server, right);
    }

    void summonFocusWraiths(ServerLevel server, Vec3 right) {
        GreenSmithNecromancerRoster.summonFocusWraiths(this, server, right);
    }

    void summonFocusReapers(ServerLevel server, Vec3 right, List<Summoned> necromancers) {
        GreenSmithNecromancerRoster.summonFocusReapers(this, server, right, necromancers);
    }

    void summonFocusPhantoms(ServerLevel server, Vec3 right) {
        GreenSmithNecromancerRoster.summonFocusPhantoms(this, server, right);
    }

    void summonFocusHaunts(ServerLevel server, Vec3 look, Vec3 right) {
        GreenSmithNecromancerRoster.summonFocusHaunts(this, server, look, right);
    }

    int countHaunts(ServerLevel server) {
        return server.getEntitiesOfClass(Entity.class,
                this.getBoundingBox().inflate(64.0D, 16.0D, 64.0D),
                entity -> HAUNT_ENTITY_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))
                        && SummonCapUtil.summonedBy(entity, this)).size();
    }

    boolean summonPhantom(ServerLevel server, double x, double y, double z,
            int potency, List<UUID> roster) {
        EntityType<?> type = ModEntityType.PHANTOM_SERVANT.get();
        Entity entity = type.create(server);
        if (!(entity instanceof PhantomServant minion)) {
            if (entity != null) {
                entity.discard();
            }
            return false;
        }
        minion.setTrueOwner(this.summonOwner());
        minion.moveTo(x, y, z, this.getYRot(), 0.0F);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS
                + this.soulJarLifespanBonusTicks());
        minion.setPhantomSize(potency);
        minion.setPersistenceRequired();
        this.applySoulJarSummonBuffs(minion);
        markGreenSmithNecromancer(minion, this);
        ExtraSummonWindup.Kind kind = ExtraSummonWindup.summon(server, minion, new Vec3(x, y, z), this);
        if (kind == ExtraSummonWindup.Kind.FAILED) {
            return false;
        }
        if (kind == ExtraSummonWindup.Kind.GROUND_CIRCLE) {
            ExtraSummonWindup.playSound(minion, this);
        }
        roster.add(minion.getUUID());
        this.pendingFamiliars.put(minion.getUUID(), 30);
        GoetySummonFx.play(server, minion);
        server.sendParticles(GreenSmithFx.necroFire(), x, y + 0.4D, z,
                16, 0.4D, 0.5D, 0.4D, 0.02D);
        return true;
    }

    int pruneFamiliars(ServerLevel server, List<UUID> roster) {
        roster.removeIf(id -> {
            if (this.pendingFamiliars.containsKey(id)) {
                return false;
            }
            return !(server.getEntity(id) instanceof LivingEntity living) || !living.isAlive();
        });
        return roster.size();
    }

    void tickPendingFamiliars() {
        if (this.pendingFamiliars.isEmpty() || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        var iterator = this.pendingFamiliars.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (server.getEntity(entry.getKey()) != null) {
                iterator.remove();
                continue;
            }
            int left = entry.getValue() - 1;
            if (left <= 0) {
                iterator.remove();
            } else {
                entry.setValue(left);
            }
        }
    }

    boolean summonFamiliar(ServerLevel server, ResourceLocation id,
            double x, double y, double z, List<UUID> roster) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(id);
        if (type == null) {
            return false;
        }
        Entity entity = type.create(server);
        if (!(entity instanceof Summoned minion)) {
            return false;
        }
        minion.setTrueOwner(this.summonOwner());
        double feetY = MinionGroundPlacement.groundFeetY(server, x, z, y,
                MinionGroundPlacement.SKELETON_CLEARANCE);
        minion.moveTo(x, feetY, z, this.getYRot(), 0.0F);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS
                + this.soulJarLifespanBonusTicks());
        minion.setPersistenceRequired();
        this.applySoulJarSummonBuffs(minion);
        markGreenSmithNecromancer(minion, this);
        ExtraSummonWindup.Kind kind = ExtraSummonWindup.summon(server, minion, new Vec3(x, feetY, z), this);
        if (kind == ExtraSummonWindup.Kind.FAILED) {
            return false;
        }
        if (kind == ExtraSummonWindup.Kind.GROUND_CIRCLE) {
            ExtraSummonWindup.playSound(minion, this);
        }
        roster.add(minion.getUUID());
        this.pendingFamiliars.put(minion.getUUID(), 30);
        GoetySummonFx.play(server, minion);
        server.sendParticles(GreenSmithFx.necroFire(), x, feetY + 0.4D, z,
                16, 0.4D, 0.5D, 0.4D, 0.02D);
        return true;
    }

    void tickNecromancerRoster() {
        if (this.necromancers.isEmpty() || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.necromancers.removeIf(id -> {
            Entity entity = server.getEntity(id);
            return !(entity instanceof LivingEntity living) || !living.isAlive();
        });
    }

    void tickSkeletonRally() {
        if (--this.skeletonRallyCooldown > 0) {
            return;
        }
        this.skeletonRallyCooldown = 10;
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        rallySkeletons(server, this, false, entity -> this.isMyAlly(entity));
    }

    static final double RALLY_SKELETON_RADIUS = 16.0D;

    public static void rallySkeletons(ServerLevel server, Mob commander, boolean wither,
            Predicate<LivingEntity> isAlly) {
        LivingEntity target = commander.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        for (Mob mob : server.getEntitiesOfClass(Mob.class,
                commander.getBoundingBox().inflate(RALLY_SKELETON_RADIUS))) {
            if (mob == commander || !mob.isAlive() || mob.isSpectator()) {
                continue;
            }
            if (!isRallySkeleton(mob, wither) || !isOwnerlessSkeleton(mob)) {
                continue;
            }
            LivingEntity current = mob.getTarget();
            if (current == target) {
                continue;
            }
            if (current == null || !current.isAlive() || isAlly.test(current)) {
                mob.setTarget(target);
            }
        }
    }

    static boolean isRallySkeleton(Mob mob, boolean wither) {
        boolean isWither = mob instanceof WitherSkeleton
                || "WitherSkeletonServant".equals(mob.getClass().getSimpleName());
        if (wither) {
            return isWither;
        }
        boolean isSkeleton = mob instanceof AbstractSkeleton || isSkeletonServantClass(mob);
        return isSkeleton && !isWither;
    }

    static boolean isSkeletonServantClass(Mob mob) {
        for (Class<?> type = mob.getClass(); type != null; type = type.getSuperclass()) {
            if ("com.Polarice3.Goety.common.entities.ally.undead.skeleton.AbstractSkeletonServant"
                    .equals(type.getName())) {
                return true;
            }
        }
        return false;
    }

    static boolean isOwnerlessSkeleton(Mob mob) {
        if (mob instanceof IOwned owned) {
            return owned.getTrueOwner() == null;
        }
        return true;
    }

    int countServantsOf(ServerLevel server, String category) {
        int count = 0;
        for (UUID id : this.necromancers) {
            if (!(server.getEntity(id) instanceof LivingEntity living) || !living.isAlive()) {
                continue;
            }
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(living.getType());
            if (key == null) {
                continue;
            }
            if (isServantCategory(key.getPath(), category)) {
                ++count;
            }
        }
        return count;
    }

    static boolean isServantCategory(String path, String category) {
        return switch (category) {
            case "blackguard" -> path.contains("blackguard");
            case "vanguard" -> path.contains("vanguard");
            case "necromancer" -> path.contains("necromancer");
            case "skeleton" -> !path.contains("blackguard") && !path.contains("vanguard")
                    && !path.contains("necromancer");
            default -> false;
        };
    }

    static boolean isBoneFocusNecromancer(ResourceLocation servantId) {
        return MOSSY_NECROMANCER_SERVANT_ID.equals(servantId)
                || NECROMANCER_SERVANT_ID.equals(servantId);
    }

    static void markGreenSmithNecromancer(Summoned minion, AbstractGreenSmith source) {
        if (minion instanceof AbstractNecromancer) {
            minion.getPersistentData().putUUID(NECROMANCER_TAG, source.getUUID());
        }
    }

    void strikeLightning(ServerLevel server, double x, double y, double z, boolean beam) {
        GreenSmithFieldSkills.strikeLightning(this, server, x, y, z, beam);
    }

    void castBorrowedSkill() {
        GreenSmithBorrowSkill.castBorrowedSkill(this);
    }

    public Vec3 chargeStaffTip() {
        return GreenSmithBorrowSkill.chargeStaffTip(this);
    }

    void tickBorrowCharge() {
        GreenSmithBorrowSkill.tickBorrowCharge(this);
    }

    void borrowTridentStorm(ServerLevel server, @Nullable LivingEntity target) {
        GreenSmithBorrowSkill.borrowTridentStorm(this, server, target);
    }

    void borrowThornBall(ServerLevel server, @Nullable LivingEntity target) {
        GreenSmithBorrowSkill.borrowThornBall(this, server, target);
    }

    void borrowSpinScythe(ServerLevel server, @Nullable LivingEntity target) {
        GreenSmithBorrowSkill.borrowSpinScythe(this, server, target);
    }

    void borrowHauntedSkulls(ServerLevel server, @Nullable LivingEntity target) {
        GreenSmithBorrowSkill.borrowHauntedSkulls(this, server, target);
    }

    void tickTridentBurst() {
        GreenSmithBorrowSkill.tickTridentBurst(this);
    }

    void resetTridentBurst() {
        GreenSmithBorrowSkill.resetTridentBurst(this);
    }

    void tickSkullBurst() {
        if (this.skullBurstRemaining <= 0 || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (--this.skullBurstTimer > 0) {
            return;
        }
        this.skullBurstTimer = SKULL_BURST_INTERVAL;
        LivingEntity target = this.skullTarget != null
                && server.getEntity(this.skullTarget) instanceof LivingEntity living && living.isAlive()
                ? living : this.getTarget();
        if (target == null) {
            this.resetSkullBurst();
            return;
        }
        this.skullTarget = target.getUUID();
        this.fireSkullBatch(server, target);
        if (--this.skullBurstRemaining <= 0) {
            this.skullTarget = null;
        }
    }

    void fireSkullBatch(ServerLevel server, LivingEntity target) {
        Vec3 base = new Vec3(target.getX() - this.getX(), target.getEyeY() - this.getEyeY(),
                target.getZ() - this.getZ()).normalize();
        double skullSpeed = this.soulJarProjectileSpeedScale();
        for (int i = 0; i < SKULL_BURST_PER_BATCH; i++) {
            double spread = (i - (SKULL_BURST_PER_BATCH - 1) / 2.0D) * 0.075D;
            double dx = base.x * Math.cos(spread) - base.z * Math.sin(spread);
            double dz = base.x * Math.sin(spread) + base.z * Math.cos(spread);
            HauntedSkullProjectile skull = new HauntedSkullProjectile(this,
                    dx * skullSpeed, base.y * skullSpeed, dz * skullSpeed, server);
            skull.setPos(this.getX() + dx * 0.8D, this.getEyeY() + 0.4D, this.getZ() + dz * 0.8D);
            this.configureSkull(skull);
            server.addFreshEntity(skull);
            server.sendParticles(ParticleTypes.WHITE_ASH, skull.getX(), skull.getY(), skull.getZ(),
                    6, 0.15D, 0.15D, 0.15D, 0.02D);
        }
    }

    void resetSkullBurst() {
        this.skullBurstRemaining = 0;
        this.skullBurstTimer = 0;
        this.skullTarget = null;
    }

    void configureSkull(HauntedSkullProjectile skull) {
        if (GoetyCompat.hasSkullSetDamage()) {
            skull.setDamage(FLYING_SKULL_DAMAGE + this.effectiveSoulJarDamageBonus());
        }
        int levels = this.effectiveSoulJarUpgrades();
        if (levels > 0) {
            try {
                var getter = skull.getClass().getMethod("getExplosionPower");
                var setter = skull.getClass().getMethod("setExplosionPower", float.class);
                float power = (float) getter.invoke(skull);
                setter.invoke(skull, power + levels * SOUL_JAR_SKULL_EXPLOSION_PER_LEVEL);
            } catch (ReflectiveOperationException | RuntimeException ignored) {
            }
        }
    }

    void tickLaserCharge() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.castElapsed == 1 || this.castElapsed % 10 == 0) {
            this.playSound(SoundEvents.BEACON_POWER_SELECT, 1.6F, 0.85F);
        }
        Vec3 tip = this.chargeStaffTip();
        int count = 6 + this.castElapsed / 2;
        server.sendParticles(ParticleTypes.WITCH, tip.x, tip.y, tip.z,
                count, 0.18D, 0.18D, 0.18D, 0.02D);
        server.sendParticles(ParticleTypes.PORTAL, tip.x, tip.y, tip.z,
                Math.max(2, count / 3), 0.12D, 0.12D, 0.12D, 0.05D);
        LivingEntity target = this.getTarget();
        if (target != null) {
            this.aimAt(target, LASER_CHARGE_TURN_DEGREES);
        }
    }

    void aimAt(LivingEntity target, float maxDelta) {
        float[] aim = targetAngles(target);
        this.setYRot(approachAngle(this.getYRot(), aim[0], maxDelta));
        this.setXRot(approachAngle(this.getXRot(), aim[1], maxDelta));
        this.yBodyRot = this.getYRot();
        this.setYHeadRot(this.getYRot());
    }

    void aimBeside(LivingEntity target, float yawOffset, float pitchOffset) {
        float[] aim = targetAngles(target);
        this.setYRot(aim[0] + yawOffset);
        this.setXRot(Mth.clamp(aim[1] + pitchOffset, -90.0F, 90.0F));
        this.yBodyRot = this.getYRot();
        this.setYHeadRot(this.getYRot());
    }

    float[] targetAngles(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double dy = target.getEyeY() - this.getEyeY();
        double flat = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, flat) * (180.0D / Math.PI)));
        return new float[]{yaw, pitch};
    }

    static float approachAngle(float current, float target, float maxDelta) {
        float delta = Mth.wrapDegrees(target - current);
        delta = Mth.clamp(delta, -maxDelta, maxDelta);
        return current + delta;
    }

    void beginLaserFire() {
        GreenSmithLaserSkill.beginLaserFire(this);
    }

    void castCorruptLaser() {
        GreenSmithLaserSkill.castCorruptLaser(this);
    }

    void spawnCorruptedBeam(ServerLevel server) {
        GreenSmithLaserSkill.spawnCorruptedBeam(this, server);
    }

    void tickLaser() {
        GreenSmithLaserSkill.tickLaser(this);
    }

    void clearCorruptedBeam() {
        GreenSmithLaserSkill.clearCorruptedBeam(this);
    }

    void castRally() {
        GreenSmithSpells.castRally(this);
    }

    float rollRallyHeal(float perLevelBonus) {
        return GreenSmithSpells.rollRallyHeal(this, perLevelBonus);
    }

    boolean isMyNecromancerServant(LivingEntity entity) {
        if (!this.isMyAlly(entity) || !(entity instanceof Summoned)) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key != null && key.getPath().contains("necromancer");
    }

    void castMonsoonCloud() {
        GreenSmithMisc.castMonsoonCloud(this);
    }

    void playMonsoonStrikeFx(ServerLevel server, double x, double y, double z) {
        GreenSmithMisc.playMonsoonStrikeFx(this, server, x, y, z);
    }

    void tickMonsoonCharge() {
        GreenSmithMisc.tickMonsoonCharge(this);
    }

    void cloneLightningOrb() {
        GreenSmithClones.cloneLightningOrb(this);
    }

    void castWindBurst() {
        GreenSmithSpells.castWindBurst(this);
    }

    void startCloneCombo() {
        GreenSmithClones.startCloneCombo(this);
    }

    void beginCloneCombo(int spell, int borrowSkill, int impactTicks) {
        GreenSmithClones.beginCloneCombo(this, spell, borrowSkill, impactTicks);
    }

    List<AbstractGreenSmith> livingWindClones() {
        return GreenSmithClones.livingWindClones(this);
    }

    void tickWindClones() {
        GreenSmithClones.tickWindClones(this);
    }

    void abortCloneCombo() {
        GreenSmithClones.abortCloneCombo(this);
    }

    void cloneSummonSkeleton() {
        GreenSmithClones.cloneSummonSkeleton(this);
    }

    void cloneHealFocus() {
        GreenSmithClones.cloneHealFocus(this);
    }

    boolean claimCloneHeal() {
        return GreenSmithClones.claimCloneHeal(this);
    }

    void applyCloneHealFocus(ServerLevel server, AbstractGreenSmith master) {
        GreenSmithClones.applyCloneHealFocus(this, server, master);
    }

    void healCloneTarget(ServerLevel server, LivingEntity target, float healAmount) {
        GreenSmithClones.healCloneTarget(this, server, target, healAmount);
    }

    void sendSoulHealShockwave(ServerLevel server, double x, double y, double z) {
        if (GoetyCompat.isGoety60()) {
            server.sendParticles(new SoulShockwaveParticleOption(), x, y + 0.5D, z,
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    void sendSoulHealRings(ServerLevel server, LivingEntity target) {
        ColorUtil color = new ColorUtil(SOUL_HEAL_COLOR);
        for (int delay : SOUL_HEAL_RING_DELAYS) {
            server.sendParticles(new RisingCircleParticleOption(delay),
                    target.getX(), target.getY(), target.getZ(), 0,
                    color.red, color.green, color.blue, 1.0D);
        }
    }

    void cloneBorrowedSkill() {
        GreenSmithClones.cloneBorrowedSkill(this);
    }

    void beginCloneBurst(int kind) {
        GreenSmithClones.beginCloneBurst(this, kind);
    }

    void tickCloneBurst() {
        GreenSmithClones.tickCloneBurst(this);
    }

    void faceCloneTarget() {
        GreenSmithClones.faceCloneTarget(this);
    }

    void fireCloneSteamMissile(ServerLevel server) {
        GreenSmithClones.fireCloneSteamMissile(this, server);
    }

    void fireClonePoisonDart(ServerLevel server) {
        GreenSmithClones.fireClonePoisonDart(this, server);
    }

    Vec3 cloneAimDirection() {
        return GreenSmithClones.cloneAimDirection(this);
    }

    void cloneSnap() {
        GreenSmithClones.cloneSnap(this);
    }

    @Nullable
    LivingEntity findSacrifice(ServerLevel server, AbstractGreenSmith master) {
        return GreenSmithNecromancerRoster.findSacrifice(this, server, master);
    }

    void cloneHauntedSkull(ServerLevel server) {
        GreenSmithClones.cloneHauntedSkull(this, server);
    }

    void cloneCorruptLaser(ServerLevel server) {
        GreenSmithClones.cloneCorruptLaser(this, server);
    }

    AbstractGreenSmith master() {
        LivingEntity owner = this.getTrueOwner();
        return owner instanceof AbstractGreenSmith green ? green : this;
    }

    void summonSkeletonServant(ServerLevel server, double x, double y, double z) {
        AbstractGreenSmith master = this.master();
        if (master.countServantsOf(server, "skeleton") >= MAX_SKELETONS) {
            return;
        }
        ResourceLocation variant;
        if (this.random.nextFloat() < CLONE_VANGUARD_CHANCE
                && master.countServantsOf(server, "vanguard") < MAX_VANGUARDS) {
            variant = VANGUARD_SERVANT_ID;
        } else {
            variant = SKELETON_SERVANT_VARIANTS[
                    this.random.nextInt(SKELETON_SERVANT_VARIANTS.length)];
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(variant);
        if (type == null) {
            return;
        }
        Entity entity = type.create(server);
        if (!(entity instanceof Summoned minion)) {
            return;
        }
        minion.setTrueOwner(master);
        double feetY = MinionGroundPlacement.groundFeetY(server, x, z, y,
                MinionGroundPlacement.SKELETON_CLEARANCE);
        minion.moveTo(x, feetY, z, this.getYRot(), 0.0F);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS
                + this.soulJarLifespanBonusTicks());
        minion.setPersistenceRequired();
        master.applySoulJarSummonBuffs(minion);
        markGreenSmithNecromancer(minion, master);
        if (server.addFreshEntity(minion)) {
            master.necromancers.add(minion.getUUID());
            GoetySummonFx.play(server, minion);
            server.sendParticles(GreenSmithFx.necroFire(), x, feetY + 0.4D, z,
                    16, 0.4D, 0.5D, 0.4D, 0.02D);
        }
    }

    void applyTremblingFocus(ServerLevel server) {
        double radius = this.windBlastRadius(3.0D);
        double power = 4.0D;
        int durationTicks = 60;
        for (LivingEntity entity : server.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(radius))) {
            if (entity == this || this.isAlliedTo(entity) || entity.isAlliedTo(this)) {
                continue;
            }
            double dx = entity.getX() - this.getX();
            double dz = entity.getZ() - this.getZ();
            double distSqr = Math.max(dx * dx + dz * dz, 0.001D);
            MobUtil.push(entity, dx / distSqr * power, 0.2D, dz / distSqr * power);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, durationTicks, 0));
        }
        ColorUtil white = new ColorUtil(0xFFFFFF);
        server.sendParticles(new CircleExplodeParticleOption(white.red, white.green, white.blue,
                        (float) (radius * 2.0D), (int) radius),
                this.getX(), this.getY() + 0.5D, this.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(white, (float) radius, 10),
                this.getX(), this.getY() + 1.0D, this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        this.playGoetySound("wind_horn", 3.0F, 1.0F);
    }

    void teleportBackward(ServerLevel server, double distance, boolean windBlast) {
        GreenSmithMisc.teleportBackward(this, server, distance, windBlast);
    }

    void castField() {
        this.fieldTicks = FIELD_DURATION;
        this.fieldStrikeTimer = 0;
        this.fieldBoltTimer = 0;
        this.fieldStrikeCount = 0;
        this.fieldYaw = this.getYRot();
        this.fieldPitch = this.getXRot();
        this.entityData.set(DATA_FIELD_ACTIVE, true);
        if (this.level() instanceof ServerLevel server) {
            this.playGoetySound("thunder_strike_epic", 1.5F, 0.55F);
        }
    }

    void tickField() {
        GreenSmithFieldSkills.tickField(this);
    }

    void tickFieldShield(ServerLevel server) {
        GreenSmithFieldSkills.tickFieldShield(this, server);
    }

    void fieldStrike(ServerLevel server) {
        GreenSmithFieldSkills.fieldStrike(this, server);
    }

    void fieldDirectBolts(ServerLevel server) {
        GreenSmithFieldSkills.fieldDirectBolts(this, server);
    }

    void fieldSummonSkeleton(ServerLevel server, double x, double y, double z) {
        GreenSmithFieldSkills.fieldSummonSkeleton(this, server, x, y, z);
    }

    public static void skyFocusShield(ServerLevel server, LivingEntity caster) {
        var wind = GreenSmithFx.chillHideWind(FIELD_SHIELD_WIND_COLOR,
                (float) (caster.getBbWidth() * 1.9D),
                (float) (caster.getBbHeight() * 0.62D), caster.getId());
        if (wind != null) {
            server.sendParticles(wind, caster.getX(), caster.getY(), caster.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (caster.tickCount % FIELD_SHIELD_AURA_INTERVAL == 0) {
            server.sendParticles(GeneratedMod.FIELD_SHIELD_AURA.get(),
                    caster.getX(), caster.getY() + caster.getBbHeight() * 0.5D, caster.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static Vec3 skyFocusStrike(ServerLevel server, LivingEntity caster, double spread) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : null;
        Vec3 origin = target != null ? target.position() : caster.position();
        double x = origin.x + (caster.getRandom().nextDouble() * 2.0D - 1.0D) * spread;
        double z = origin.z + (caster.getRandom().nextDouble() * 2.0D - 1.0D) * spread;
        double y = server.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                BlockPos.containing(x, origin.y, z)).getY();
        MagicLightningTrap trap = new MagicLightningTrap(server, x, y, z);
        trap.setOwner(caster);
        trap.setColor(GREEN_LIGHTNING_COLOR);
        trap.setDuration(LIGHTNING_TRAP_DURATION);
        trap.setRadius(LIGHTNING_RADIUS);
        trap.setDamage(LIGHTNING_DAMAGE);
        server.addFreshEntity(trap);
        server.sendParticles(GreenSmithFx.necroFire(), x, y, z, 24, 0.4D, 0.3D, 0.4D, 0.03D);
        for (int[] off : new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            spawnGreenFireFor(server, caster, x + off[0], y, z + off[1]);
        }
        com.mojang.logging.LogUtils.getLogger().info("[skyfocus] green strike x={} y={} z={}", x, y, z);
        return new Vec3(x, y, z);
    }

    public static void spawnGreenFireFor(ServerLevel server, LivingEntity owner, double x, double y, double z) {
        AABB nearby = new AABB(x - 0.75D, y - 0.5D, z - 0.75D, x + 0.75D, y + 0.5D, z + 0.75D);
        if (!server.getEntitiesOfClass(GreenFireEntity.class, nearby).isEmpty()) {
            return;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(GREEN_FIRE_ID);
        if (type == null) {
            return;
        }
        Entity fire = type.create(server);
        if (fire instanceof GreenFireEntity green) {
            green.setOwner(owner);
            green.moveTo(x, y, z, 0.0F, 0.0F);
            server.addFreshEntity(green);
            com.mojang.logging.LogUtils.getLogger().info("[skyfocus] green fire x={} y={} z={}", x, y, z);
        }
    }

    public static void skyFocusSummonSkeleton(ServerLevel server, LivingEntity owner, double x, double y, double z) {
        ResourceLocation variant = SKELETON_SERVANT_VARIANTS[owner.getRandom().nextInt(SKELETON_SERVANT_VARIANTS.length)];
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(variant);
        if (type == null) {
            return;
        }
        Entity entity = type.create(server);
        if (!(entity instanceof Summoned minion)) {
            return;
        }
        minion.setTrueOwner(owner);
        minion.moveTo(x, y, z, owner.getYRot(), 0.0F);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        minion.setLimitedLife(FIELD_DURATION);
        minion.setPersistenceRequired();
        if (server.addFreshEntity(minion)) {
            com.mojang.logging.LogUtils.getLogger().info("[skyfocus] skeleton type={} alive={}", variant, minion.isAlive());
        }
    }

    public void spawnGreenFire(ServerLevel server, double x, double y, double z) {
        GreenSmithFieldSkills.spawnGreenFire(this, server, x, y, z);
    }

    void tickNightfall() {
        GreenSmithMisc.tickNightfall(this);
    }

    void tickDistanceTeleport() {
        GreenSmithMisc.tickDistanceTeleport(this);
    }

    boolean tryTeleportCloserToTarget(ServerLevel server, LivingEntity target) {
        return GreenSmithMisc.tryTeleportCloserToTarget(this, server, target);
    }

    boolean isSafeTeleportPos(ServerLevel server, BlockPos pos) {
        return GreenSmithMisc.isSafeTeleportPos(this, server, pos);
    }

    public boolean teleportAway(ServerLevel server, int radius) {
        return GreenSmithMisc.teleportAway(this, server, radius);
    }

    void noteTeleport() {
        GreenSmithMisc.noteTeleport(this);
    }

    int forcedTeleportHits() {
        return GreenSmithMisc.forcedTeleportHits(this);
    }

    boolean inHazard() {
        return this.isInLava() || this.isInPowderSnow;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return GreenSmithDefense.hurt(this, source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return GreenSmithDefense.isInvulnerableTo(this, source);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return GreenSmithDefense.canBeAffected(this, effect);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return GreenSmithDefense.causeFallDamage(this, fallDistance, multiplier, source);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return GreenSmithDefense.canBreatheUnderwater(this);
    }

    @Override
    public MobType getMobType() {
        return GreenSmithDefense.getMobType(this);
    }

    @Override
    public void actuallyHurt(DamageSource source, float amount) {
        GreenSmithDefense.actuallyHurt(this, source, amount);
    }

    @Override
    public void tryKill(Player player) {
        GreenSmithDefense.tryKill(this, player);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        GreenSmithDefense.dropCustomDeathLoot(this, source, looting, recentlyHit);
    }

    public void dropGreenSmithLoot(int looting) {
        GreenSmithDefense.dropGreenSmithLoot(this, looting);
    }

    public void dropGoetyItem(String path, int count) {
        GreenSmithDefense.dropGoetyItem(this, path, count);
    }

    public boolean isClone() {
        return GreenSmithDefense.isClone(this);
    }

    void playGoetySound(String path, float volume, float pitch) {
        GreenSmithDefense.playGoetySound(this, path, volume, pitch);
    }

    @Override
    public LivingEntity getMasterOwner() {
        return GreenSmithDefense.getMasterOwner(this);
    }

    public boolean isMyAlly(LivingEntity other) {
        return GreenSmithDefense.isMyAlly(this, other);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        return GreenSmithDefense.isAlliedTo(this, other);
    }

    @Override
    public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType,
            @Nullable net.minecraft.world.entity.SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        return GreenSmithDefense.finalizeSpawn(this, level, difficulty, spawnType, spawnGroupData, tag);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return GreenSmithDefense.getDimensions(this, pose);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GreenSmithDefense.getAmbientSound(this);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GreenSmithDefense.getHurtSound(this, source);
    }

    @Override
    public SoundEvent getDeathSound() {
        return GreenSmithDefense.getDeathSound(this);
    }

    @Override
    public void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        GreenSmithDefense.playStepSound(this, pos, state);
    }

    @Override
    public int getAmbientSoundInterval() {
        return GreenSmithDefense.getAmbientSoundInterval(this);
    }

    @Override
    public void playAmbientSound() {
        GreenSmithDefense.playAmbientSound(this);
    }

    @Override
    public void die(DamageSource cause) {
        GreenSmithDefense.die(this, cause);
    }

    @Override
    public void tickDeath() {
        GreenSmithDefense.tickDeath(this);
    }

    public int getFieldTicks() {
        return GreenSmithDefense.getFieldTicks(this);
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        GreenSmithDefense.remove(this, reason);
    }

    InteractionResult superMobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }

    boolean superHurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    boolean superIsInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source);
    }

    boolean superCanBeAffected(MobEffectInstance effect) {
        return super.canBeAffected(effect);
    }

    void superActuallyHurt(DamageSource source, float amount) {
        super.actuallyHurt(source, amount);
    }

    void superDropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
    }

    LivingEntity superGetMasterOwner() {
        return super.getMasterOwner();
    }

    boolean superIsAlliedTo(Entity other) {
        return super.isAlliedTo(other);
    }

    net.minecraft.world.entity.SpawnGroupData superFinalizeSpawn(ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType,
            @Nullable net.minecraft.world.entity.SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    EntityDimensions superGetDimensions(Pose pose) {
        return super.getDimensions(pose);
    }

    void superDie(DamageSource cause) {
        super.die(cause);
    }

    void superRemove(Entity.RemovalReason reason) {
        super.remove(reason);
    }
}
