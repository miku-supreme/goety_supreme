package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.BearServant;
import com.Polarice3.Goety.common.entities.ally.Gnasher;
import com.Polarice3.Goety.common.entities.ally.Leapleaf;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.Wavewhisperer;
import com.Polarice3.Goety.common.entities.ally.Whisperer;
import com.Polarice3.Goety.common.entities.neutral.AbstractVine;
import com.Polarice3.Goety.common.entities.neutral.PoisonAnemone;
import com.Polarice3.Goety.common.entities.neutral.PoisonQuillVine;
import com.Polarice3.Goety.common.entities.neutral.QuickGrowingKelp;
import com.Polarice3.Goety.common.entities.neutral.QuickGrowingVine;
import com.Polarice3.Goety.common.entities.projectiles.BioMine;
import com.Polarice3.Goety.common.entities.projectiles.PoisonBolt;
import com.Polarice3.Goety.common.magic.spells.wild.BlossomSpell;
import com.Polarice3.Goety.client.particles.RisingCircleParticleOption;
import com.Polarice3.Goety.client.particles.SoulShockwaveParticleOption;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.Polarice3.Goety.utils.SoundUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import com.Polarice3.Goety.common.entities.ai.path.ModWaterPathNavigation;
import com.Polarice3.Goety.common.entities.util.SummonCircle;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.CastQueue;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.ScaledSummonCircle;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.model.WildMageModel;
import cn.blockforge.generated.goetysupreme.entity.WildMageServant;

public abstract class AbstractWildMage extends Summoned implements RangedAttackMob, UndeadCapeBearer {

    public final ModWaterPathNavigation waterNavigation;
    public final GroundPathNavigation groundNavigation;

    public static final double MAX_HEALTH = 220.0D;
    public static final double ATTACK_DAMAGE = 4.0D;

    private static final double FOLLOW_RANGE = 32.0D;

    private static final int SMALL_SUMMON_COOLDOWN = 10 * 20;

    private static final int DIRECT_SUMMON_COOLDOWN = 10 * 20;

    private static final int LARGE_SUMMON_COOLDOWN = 20 * 20;

    private static final int SEED_COOLDOWN = 10 * 20;

    private static final int MINE_COOLDOWN = 10 * 20;

    private static final int HEAL_COOLDOWN = 20 * 20;

    private static final int BUFF_COOLDOWN = 20 * 20;
    private static final int ENTANGLE_COOLDOWN = 15 * 20;

    private static final double HEAL_RADIUS = 16.0D;

    private static final float HEAL_MIN = 5.0F;
    private static final float HEAL_MAX = 10.0F;
    private static final float HEAL_MIN_PER_LEVEL = 2.5F;
    private static final float HEAL_MAX_PER_LEVEL = 5.0F;

    private static final int SOUL_HEAL_COLOR = 2804175;

    private static final int[] SOUL_HEAL_RING_DELAYS = {0, 5, 10};

    private static final int ATTACK_COOLDOWN = 20;

    private static final int QUICK_VINE_LIFESPAN_SECONDS = 4;

    private static final int POISON_VINE_LIFESPAN_SECONDS = 14;

    private static final int POISON_VINE_CAP = 6;

    private static final double POISON_VINE_COUNT_RADIUS = 48.0D;

    public static final int CAST_NONE = 0;
    public static final int CAST_ATTACK = 1;
    public static final int CAST_SMALL_SUMMON = 2;
    public static final int CAST_LARGE_SUMMON = 3;
    public static final int CAST_SEEDS = 4;
    public static final int CAST_MINES = 5;
    public static final int CAST_HEAL = 6;
    public static final int CAST_BUFF = 7;
    public static final int CAST_ENTANGLE = 8;

    private static final int[] CAST_LENGTHS = {0, 18, 40, 45, 40, 40, 65, 55, 25};

    private static final float[] CAST_IMPACT = {
            0.0F, 6.0F / 18.0F, 22.0F / 40.0F, 13.0F / 45.0F, 25.0F / 40.0F,
            20.0F / 40.0F, 45.0F / 65.0F, 10.0F / 55.0F, 12.0F / 25.0F};

    public static final ResourceLocation MAULING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "mauling_focus");
    public static final ResourceLocation LEAPING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "leaping_focus");

    public static final ResourceLocation BLOSSOMING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "blossoming_focus");

    public static final ResourceLocation SPIDER_NEST_ID = ResourceLocation.fromNamespaceAndPath("goety", "spider_nest");
    public static final ResourceLocation HUNTING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "hunting_focus");

    public static final ResourceLocation TWILIGHT_ALPHA_WOLF_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "alpha_wolf_focus");
    public static final ResourceLocation TWILIGHT_BEETLE_NEST_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "beetle_nest");

    public static final ResourceLocation TWILIGHT_KING_SPIDER_FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "king_spider_focus");
    private static final ResourceLocation TWILIGHT_MIST_WOLF_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "mist_wolf_servant");
    private static final ResourceLocation TWILIGHT_KING_SPIDER_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "king_spider_servant");
    private static final ResourceLocation TWILIGHT_SLIME_BEETLE_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "slime_beetle_servant");
    private static final ResourceLocation TWILIGHT_PINCH_BEETLE_ID =
            ResourceLocation.fromNamespaceAndPath("goetytwilight", "pinch_beetle_servant");

    private static final ResourceLocation WEB_SPIDER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "web_spider_servant");
    private static final ResourceLocation BLACK_WOLF_ID = ResourceLocation.fromNamespaceAndPath("goety", "black_wolf");

    private static final int WHISPERER_ESCORT_LIMIT = 4;

    private static final double WHISPERER_ESCORT_SIDE_OFFSET = 1.5D;

    private static final String BROOD_FOCUS_PATH = "brood_focus";
    private static final ResourceLocation BROOD_MOTHER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "brood_mother_servant");

    private static final float BROOD_SUMMON_CHANCE = 0.5F;

    private static final int BROOD_SUMMON_LIMIT = 1;

    private static final ResourceLocation SLIME_SERVANT_ID = ResourceLocation.fromNamespaceAndPath("goety", "slime_servant");

    private static final ResourceLocation TROPICAL_SLIME_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "tropical_slime_servant");

    private static final int SLIME_SUMMON_LIMIT = 2;

    private static final int SLIME_SUMMON_SIZE = 3;

    private static final int SLIME_SUMMON_SIZE_APEX = 4;

    private static final double SLIME_SUMMON_SIDE_OFFSET = 1.5D;
    private static final double SLIME_SUMMON_DISTANCE = 2.0D;

    private static final int BEAST_SUMMON_LIMIT = 3;
    private static final int LEAPLEAF_SUMMON_LIMIT = 2;

    private static final String LEAPKELP_PATH = "leapkelp";

    private static EntityType<?> leapkelpType;
    private static boolean leapkelpResolved;

    private static final int APEX_WHISPERER_COUNT = 2;

    private static final int WHISPERER_SUMMON_LIMIT = 4;

    private static final double APEX_WHISPERER_SIDE_OFFSET = 0.5D;

    private boolean maulingUnlocked = true;

    private boolean leapingUnlocked;

    private boolean blossomingUnlocked;

    private boolean spiderNestUnlocked;

    private boolean huntingUnlocked;

    private static final String MAULING_KEY = "WildMageMaulingFocus";
    private static final String HUNTING_KEY = "WildMageHuntingFocus";

    private boolean broodFocusUnlocked;

    private boolean alphaWolfUnlocked;

    private boolean beetleNestUnlocked;

    private boolean kingSpiderUnlocked;

    public static final int MAX_SOUL_JAR_UPGRADES = 2;

    public static final double SOUL_JAR_HEALTH_FACTOR = 1.25D;

    private int soulJarUpgrades;

    private static final ColorUtil WILD_SUMMON_COLOR = new ColorUtil(4209428);
    private static final int WILD_SUMMON_COLOR_FROM = 4209428;
    private static final int WILD_SUMMON_COLOR_TO = 5983773;

    private static final EntityDataAccessor<Integer> DATA_CAST_ANIMATION =
            SynchedEntityData.defineId(AbstractWildMage.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_CAST_TICKS =
            SynchedEntityData.defineId(AbstractWildMage.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_SOUL_JAR_UPGRADES =
            SynchedEntityData.defineId(AbstractWildMage.class, EntityDataSerializers.INT);

    public static final float SOUL_JAR_SIZE_PER_LEVEL = 0.15F;

    private static final EntityDataAccessor<Float> DATA_ATTACK_SPEED =
            SynchedEntityData.defineId(AbstractWildMage.class, EntityDataSerializers.FLOAT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState castAnimationState = new AnimationState();

    private int smallSummonCooldown;
    private int largeSummonCooldown;
    private int seedCooldown;
    private int mineCooldown;
    private int healCooldown;
    private int buffCooldown;
    private int entangleCooldown;
    private int attackCooldown;
    private int spellPickCooldown;

    private int directSummonCooldown;

    private int headShakeTicks;

    private int pendingSpell = CAST_NONE;

    private int lastCastAnimation = CAST_NONE;
    private int castAnimation;
    private int castElapsed;
    private int castLength;
    private int castImpact;
    private boolean castApplied;

    private final CastQueue castQueue = new CastQueue();

    private boolean directSummonToMaster;

    private static final String SUMMONER_TAG = "WildMageSummoner";

    public AbstractWildMage(EntityType<? extends AbstractWildMage> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 30;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.waterNavigation = new ModWaterPathNavigation(this, level);
        this.groundNavigation = this.getNavigation() instanceof GroundPathNavigation ground
                ? ground : new GroundPathNavigation(this, level);
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
            this.moveRelative(0.01F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    private boolean wantsToSwim() {
        if (this.getTarget() != null && this.getTarget().isInWater()) {
            return true;
        }
        return this.getTrueOwner() != null && this.getTrueOwner().isInWater();
    }

    private void spellCastParticles() {
        for (int i = 0; i < this.random.nextInt(35) + 10; ++i) {
            this.level().addParticle(
                    GeneratedMod.WILD_TOTEM_EFFECT.get(),
                    this.getX(), this.getY(), this.getZ(), 0.45D, 0.45D, 0.45D);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.WILD_MAGE_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.WILD_MAGE_ATTACK.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CAST_ANIMATION, CAST_NONE);
        this.entityData.define(DATA_CAST_TICKS, 0);
        this.entityData.define(DATA_SOUL_JAR_UPGRADES, 0);
        this.entityData.define(DATA_ATTACK_SPEED, DEFAULT_ATTACK_SPEED);
    }

    public static final float DEFAULT_ATTACK_SPEED = 1.0F;

    private static final float MIN_ATTACK_SPEED = 0.1F;

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

    public float soulJarSizeScale() {
        int level = getSoulJarUpgrades();
        return level <= 0 ? 1.0F : 1.0F + level * SOUL_JAR_SIZE_PER_LEVEL;
    }

    @Override
    public net.minecraft.world.entity.EntityDimensions getDimensions(net.minecraft.world.entity.Pose pose) {
        net.minecraft.world.entity.EntityDimensions base = super.getDimensions(pose);
        float scale = soulJarSizeScale();
        return scale == 1.0F ? base : base.scale(scale);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_SOUL_JAR_UPGRADES.equals(key)) {
            this.soulJarUpgrades = this.entityData.get(DATA_SOUL_JAR_UPGRADES);
            this.refreshDimensions();
        }
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("WildMageCast", this.castAnimation);
        tag.putInt("WildMageCastElapsed", this.castElapsed);
        tag.putInt("WildMageSmallSummon", this.smallSummonCooldown);
        tag.putInt("WildMageDirectSummon", this.directSummonCooldown);
        tag.putInt("WildMageLargeSummon", this.largeSummonCooldown);
        tag.putInt("WildMageSeeds", this.seedCooldown);
        tag.putInt("WildMageMines", this.mineCooldown);
        tag.putInt("WildMageHeal", this.healCooldown);
        tag.putInt("WildMageBuff", this.buffCooldown);
        tag.putInt("WildMageEntangle", this.entangleCooldown);
        tag.putBoolean(MAULING_KEY, this.maulingUnlocked);
        tag.putBoolean("WildMageLeapingFocus", this.leapingUnlocked);
        tag.putBoolean("WildMageBlossomingFocus", this.blossomingUnlocked);
        tag.putBoolean("WildMageSpiderNest", this.spiderNestUnlocked);
        tag.putBoolean(HUNTING_KEY, this.huntingUnlocked);
        tag.putBoolean("WildMageBroodFocus", this.broodFocusUnlocked);
        tag.putBoolean("WildMageAlphaWolf", this.alphaWolfUnlocked);
        tag.putBoolean("WildMageBeetleNest", this.beetleNestUnlocked);
        tag.putBoolean("WildMageKingSpider", this.kingSpiderUnlocked);
        tag.putInt("WildMageSoulJarUpgrades", this.soulJarUpgrades);
        tag.putFloat("AttackSpeed", this.getAttackSpeed());
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.smallSummonCooldown = tag.getInt("WildMageSmallSummon");
        this.directSummonCooldown = tag.getInt("WildMageDirectSummon");
        this.largeSummonCooldown = tag.getInt("WildMageLargeSummon");
        this.seedCooldown = tag.getInt("WildMageSeeds");
        this.mineCooldown = tag.getInt("WildMageMines");
        this.healCooldown = tag.getInt("WildMageHeal");
        this.buffCooldown = tag.getInt("WildMageBuff");
        this.entangleCooldown = tag.getInt("WildMageEntangle");
        this.leapingUnlocked = tag.getBoolean("WildMageLeapingFocus");
        this.blossomingUnlocked = tag.getBoolean("WildMageBlossomingFocus");
        this.spiderNestUnlocked = tag.getBoolean("WildMageSpiderNest");
        this.broodFocusUnlocked = tag.getBoolean("WildMageBroodFocus");
        this.alphaWolfUnlocked = tag.getBoolean("WildMageAlphaWolf");
        this.beetleNestUnlocked = tag.getBoolean("WildMageBeetleNest");
        this.kingSpiderUnlocked = tag.getBoolean("WildMageKingSpider");
        this.maulingUnlocked = !tag.contains(MAULING_KEY) || tag.getBoolean(MAULING_KEY);
        this.huntingUnlocked = !tag.contains(HUNTING_KEY) || tag.getBoolean(HUNTING_KEY);
        this.soulJarUpgrades = Mth.clamp(tag.getInt("WildMageSoulJarUpgrades"),
                0, MAX_SOUL_JAR_UPGRADES);
        if (tag.contains("AttackSpeed")) {
            this.setAttackSpeed(tag.getFloat("AttackSpeed"));
        }
        this.entityData.set(DATA_SOUL_JAR_UPGRADES, this.soulJarUpgrades);
        this.refreshSoulJarAttributes();
        this.refreshDimensions();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && stack.is(GeneratedMod.WILD_MAGE_SOUL_JAR.get())
                && this.soulJarUpgrades < MAX_SOUL_JAR_UPGRADES) {
            if (!this.level().isClientSide) {
                this.applySoulJarUpgrade(player, stack);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && this.getHealth() < this.getMaxHealth()
                && stack.is(GeneratedMod.WILD_MAGE_SOUL_JAR.get())) {
            if (!this.level().isClientSide) {
                this.healFromSoulJar(player, stack);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && this.getHealth() < this.getMaxHealth()
                && stack.is(Items.BONE_MEAL)) {
            if (!this.level().isClientSide) {
                ServantFeedHeal.feed(this, player, stack, this.getMaxHealth() / 4.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        boolean mauling = isFocus(stack, MAULING_FOCUS_ID) && !this.maulingUnlocked;
        boolean leaping = isFocus(stack, LEAPING_FOCUS_ID) && !this.leapingUnlocked;
        boolean blossoming = isFocus(stack, BLOSSOMING_FOCUS_ID) && !this.blossomingUnlocked;
        boolean spiderNest = isFocus(stack, SPIDER_NEST_ID) && !this.spiderNestUnlocked;
        boolean hunting = isFocus(stack, HUNTING_FOCUS_ID) && !this.huntingUnlocked;
        boolean brood = isOminousFocus(stack, BROOD_FOCUS_PATH) && !this.broodFocusUnlocked;
        boolean alphaWolf = isFocus(stack, TWILIGHT_ALPHA_WOLF_FOCUS_ID) && !this.alphaWolfUnlocked;
        boolean beetleNest = isFocus(stack, TWILIGHT_BEETLE_NEST_ID) && !this.beetleNestUnlocked;
        boolean kingSpider = isFocus(stack, TWILIGHT_KING_SPIDER_FOCUS_ID) && !this.kingSpiderUnlocked;
        if (mauling || leaping || blossoming || spiderNest || hunting || brood
                || alphaWolf || beetleNest || kingSpider) {
            if (!this.level().isClientSide) {
                this.unlockFocus(player, stack, mauling, leaping, blossoming, spiderNest, hunting,
                        brood, alphaWolf, beetleNest, kingSpider);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && player.getMainHandItem().isEmpty() && this.isAlive()
                && !this.isCasting() && this.directSummonCooldown <= 0) {
            int spell = this.pickDirectSummonSpell();
            if (spell != CAST_NONE) {
                if (!this.level().isClientSide) {
                    this.getNavigation().stop();
                    this.directSummonCooldown = DIRECT_SUMMON_COOLDOWN;
                    this.directSummonToMaster = true;
                    this.requestCast(spell);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        if (this.getTrueOwner() == player && player.getMainHandItem().isEmpty()
                && this.isAlive() && !this.isCasting()) {
            if (!this.level().isClientSide) {
                this.playSound(this.getHurtSound(this.damageSources().generic()), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 9);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 9) {
            this.headShakeTicks = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    public boolean isShakingHead() {
        return this.headShakeTicks > 0;
    }

    private int pickDirectSummonSpell() {
        int[] candidates = new int[4];
        int size = 0;
        if (this.smallSummonCooldown <= 0) {
            candidates[size++] = CAST_SMALL_SUMMON;
        }
        if (this.seedCooldown <= 0) {
            candidates[size++] = CAST_SEEDS;
        }
        if (this.largeSummonCooldown <= 0 && this.hasLargeSummon()) {
            candidates[size++] = CAST_LARGE_SUMMON;
        }
        if (size == 0) {
            return CAST_NONE;
        }
        return candidates[this.random.nextInt(size)];
    }

    private void applySoulJarUpgrade(Player player, ItemStack stack) {
        ++this.soulJarUpgrades;
        this.entityData.set(DATA_SOUL_JAR_UPGRADES, this.soulJarUpgrades);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.refreshSoulJarAttributes();
        this.setHealth(this.getMaxHealth());
        this.refreshDimensions();
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SOUL, this.getX(),
                    this.getY(0.6D), this.getZ(),
                    18, this.getBbWidth() * 0.6D, this.getBbHeight() * 0.45D,
                    this.getBbWidth() * 0.6D, 0.03D);
        }
        this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.8F);
        this.playSoulJarLaugh();
    }

    private void healFromSoulJar(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.setHealth(this.getMaxHealth());
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SOUL, this.getX(),
                    this.getY(0.6D), this.getZ(),
                    18, this.getBbWidth() * 0.6D, this.getBbHeight() * 0.45D,
                    this.getBbWidth() * 0.6D, 0.03D);
        }
        this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.8F);
        this.playSoulJarLaugh();
    }

    private void playSoulJarLaugh() {
        SoundEvent laugh = ModSounds.NECROMANCER_LAUGH.get();
        if (laugh != null) {
            this.playSound(laugh, 1.0F, SOUL_JAR_LAUGH_PITCH);
        }
    }

    private static final float SOUL_JAR_LAUGH_PITCH = 0.4F;

    private void refreshSoulJarAttributes() {
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(SupremeMobsConfig.WILD_MAGE_HEALTH.get()
                    * Math.max(1.0D, this.soulJarUpgrades * SOUL_JAR_HEALTH_FACTOR));
        }
    }

    public int getSoulJarUpgrades() {
        return this.soulJarUpgrades;
    }

    private static boolean isFocus(ItemStack stack, ResourceLocation id) {
        Item item = ForgeRegistries.ITEMS.getValue(id);
        return item != null && stack.is(item);
    }

    private static boolean isOminousFocus(ItemStack stack, String path) {
        ResourceLocation id = OminousCompat.item(path);
        return id != null && stack.is(ForgeRegistries.ITEMS.getValue(id));
    }

    private void unlockFocus(Player player, ItemStack stack, boolean mauling, boolean leaping,
            boolean blossoming, boolean spiderNest, boolean hunting, boolean brood,
            boolean alphaWolf, boolean beetleNest, boolean kingSpider) {
        if (mauling) {
            this.maulingUnlocked = true;
        }
        if (leaping) {
            this.leapingUnlocked = true;
        }
        if (blossoming) {
            this.blossomingUnlocked = true;
        }
        if (spiderNest) {
            this.spiderNestUnlocked = true;
        }
        if (hunting) {
            this.huntingUnlocked = true;
        }
        if (brood) {
            this.broodFocusUnlocked = true;
        }
        if (alphaWolf) {
            this.alphaWolfUnlocked = true;
        }
        if (beetleNest) {
            this.beetleNestUnlocked = true;
        }
        if (kingSpider) {
            this.kingSpiderUnlocked = true;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY(0.8D), this.getZ(),
                    24, 0.6D, 0.8D, 0.6D, 0.05D);
        }
        this.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F, 1.2F);
    }

    private boolean hasLargeSummon() {
        return this.maulingUnlocked || this.leapingUnlocked || this.broodFocusUnlocked
                || this.alphaWolfUnlocked || this.kingSpiderUnlocked;
    }

    private boolean hasBlossom() {
        return this.blossomingUnlocked;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SpellGoal());
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.0D, 12.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.headShakeTicks > 0) {
            --this.headShakeTicks;
        }
        if (this.level().isClientSide && this.isSpellCastingParticles()) {
            this.spellCastParticles();
        }
        if (!this.level().isClientSide) {
            this.tickCooldowns();
            this.tickCast();
        } else {
            this.tickClientAnimations();
        }
    }

    private void tickCooldowns() {
        if (this.smallSummonCooldown > 0) --this.smallSummonCooldown;
        if (this.directSummonCooldown > 0) --this.directSummonCooldown;
        if (this.largeSummonCooldown > 0) --this.largeSummonCooldown;
        if (this.seedCooldown > 0) --this.seedCooldown;
        if (this.mineCooldown > 0) --this.mineCooldown;
        if (this.healCooldown > 0) --this.healCooldown;
        if (this.buffCooldown > 0) --this.buffCooldown;
        if (this.entangleCooldown > 0) --this.entangleCooldown;
        if (this.attackCooldown > 0) --this.attackCooldown;
    }

    private void tickCast() {
        if (this.isDeadOrDying() || this.isRemoved()) {
            this.castQueue.clear();
        }
        if (this.castAnimation == CAST_NONE) {
            return;
        }
        ++this.castElapsed;
        if (!this.castApplied && this.castElapsed >= this.castImpact) {
            this.castApplied = true;
            this.applyCast(this.castAnimation);
            this.directSummonToMaster = false;
        }
        if (this.castLength >= 30 && this.castElapsed == this.castLength - 15) {
            this.playSound(ModSounds.NECRO_CAST.get(), 0.8F, 1.0F);
        }
        if (this.castElapsed >= this.castLength) {
            this.setCastAnimation(CAST_NONE, 0);
            this.castElapsed = 0;
            this.flushQueuedCast();
        } else {
            this.entityData.set(DATA_CAST_TICKS, Math.max(0, this.castLength - this.castElapsed));
        }
    }

    private void tickClientAnimations() {
        int cast = this.getCastAnimation();
        if (cast != this.lastCastAnimation) {
            this.lastCastAnimation = cast;
            if (cast != CAST_NONE) {
                this.castAnimationState.start(this.tickCount);
            }
        }
        if (cast == CAST_NONE) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        } else {
            this.idleAnimationState.stop();
        }
    }

    public int getCastAnimation() {
        return this.entityData.get(DATA_CAST_ANIMATION);
    }

    public int getCastTicks() {
        return this.entityData.get(DATA_CAST_TICKS);
    }

    public int getCastAnimationTick() {
        return this.entityData.get(DATA_CAST_TICKS);
    }

    public boolean isCasting() {
        return this.getCastAnimation() != CAST_NONE;
    }

    public boolean isSpellCastingParticles() {
        int cast = this.getCastAnimation();
        return cast != CAST_NONE && cast != CAST_ATTACK;
    }

    private void setCastAnimation(int animation, int length) {
        this.castAnimation = animation;
        this.castLength = length;
        this.castElapsed = 0;
        this.castApplied = false;
        this.castImpact = animation == CAST_NONE ? 0
                : Math.max(1, Math.round(length * CAST_IMPACT[animation]));
        this.entityData.set(DATA_CAST_ANIMATION, animation);
        this.entityData.set(DATA_CAST_TICKS, length);
        if (animation != CAST_NONE) {
            this.playSound(ModSounds.NECRO_CAST.get(), 1.0F, 1.0F);
            this.castAnimationState.start(this.tickCount);
        }
    }

    private void requestCast(int spell) {
        if (this.castAnimation != CAST_NONE && this.castElapsed < this.castLength) {
            this.castQueue.offer(spell, spell, false);
            return;
        }
        this.beginCast(spell);
    }

    private void beginCast(int spell) {
        this.getNavigation().stop();
        this.setCastAnimation(spell, CAST_LENGTHS[spell]);
    }

    private void flushQueuedCast() {
        if (this.castQueue.isEmpty() || this.isCasting()) {
            return;
        }
        int spell = this.castQueue.animation();
        this.castQueue.clear();
        if (this.isDeadOrDying() || this.isRemoved()) {
            return;
        }
        this.beginCast(spell);
    }

    @Override
    public void die(DamageSource cause) {
        this.castQueue.clear();
        super.die(cause);
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        this.castQueue.clear();
        super.remove(reason);
    }

    private class SpellGoal extends Goal {

        @Override
        public boolean canUse() {
            if (AbstractWildMage.this.isCasting()) {
                return false;
            }
            LivingEntity target = AbstractWildMage.this.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (AbstractWildMage.this.spellPickCooldown > 0) {
                --AbstractWildMage.this.spellPickCooldown;
                return false;
            }
            int spell = AbstractWildMage.this.pickSpell(target);
            if (spell == CAST_NONE) {
                AbstractWildMage.this.spellPickCooldown = 5;
                return false;
            }
            AbstractWildMage.this.pendingSpell = spell;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return AbstractWildMage.this.isCasting();
        }

        @Override
        public void start() {
            int spell = AbstractWildMage.this.pendingSpell;
            AbstractWildMage.this.pendingSpell = CAST_NONE;
            if (spell == CAST_NONE) {
                return;
            }
            AbstractWildMage.this.requestCast(spell);
        }

        @Override
        public void tick() {
            LivingEntity target = AbstractWildMage.this.getTarget();
            if (target != null) {
                AbstractWildMage.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public EnumSet<Goal.Flag> getFlags() {
            return EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK);
        }
    }

    private int pickSpell(LivingEntity target) {
        if (target.distanceToSqr(this) > 32.0D * 32.0D) {
            return CAST_NONE;
        }
        if (this.smallSummonCooldown <= 0) {
            return CAST_SMALL_SUMMON;
        }
        if (this.seedCooldown <= 0) {
            return CAST_SEEDS;
        }
        if (this.entangleCooldown <= 0) {
            return CAST_ENTANGLE;
        }
        if (this.mineCooldown <= 0 && (isAquatic() || this.hasBlossom())) {
            return CAST_MINES;
        }
        if (this.largeSummonCooldown <= 0 && this.hasLargeSummon()) {
            return CAST_LARGE_SUMMON;
        }
        if (this.healCooldown <= 0 && this.hasWoundedWildkin()) {
            return CAST_HEAL;
        }
        if (this.buffCooldown <= 0 && this.hasServantToRally()) {
            return CAST_BUFF;
        }
        if (this.attackCooldown <= 0 && this.hasLineOfSight(target)) {
            return CAST_ATTACK;
        }
        return CAST_NONE;
    }

    private void applyCast(int spell) {
        LivingEntity target = this.getTarget();
        switch (spell) {
            case CAST_ATTACK -> {
                this.attackCooldown = this.scaledAttackCooldown(ATTACK_COOLDOWN);
                if (target != null) {
                    this.performRangedAttack(target, 1.0F);
                }
            }
            case CAST_SMALL_SUMMON -> {
                this.smallSummonCooldown = SMALL_SUMMON_COOLDOWN;
                this.castSmallSummon();
            }
            case CAST_LARGE_SUMMON -> {
                this.largeSummonCooldown = LARGE_SUMMON_COOLDOWN;
                this.castLargeSummon();
            }
            case CAST_SEEDS -> {
                this.seedCooldown = SEED_COOLDOWN;
                this.castSeeds();
            }
            case CAST_ENTANGLE -> {
                this.entangleCooldown = ENTANGLE_COOLDOWN;
                this.castEntangle();
            }
            case CAST_MINES -> {
                this.mineCooldown = MINE_COOLDOWN;
                if (isAquatic()) {
                    this.castMines(target);
                } else {
                    this.castBlossom(target);
                }
            }
            case CAST_HEAL -> {
                this.healCooldown = HEAL_COOLDOWN;
                this.castHeal();
            }
            case CAST_BUFF -> {
                this.buffCooldown = BUFF_COOLDOWN;
                this.castBuff();
            }
            default -> {
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        int necroLevel = this.soulJarUpgrades;
        int spreadFrom = necroLevel <= 0 ? 0 : -necroLevel;
        int spreadTo = necroLevel <= 0 ? 0 : necroLevel;
        for (int i = spreadFrom; i <= spreadTo; ++i) {
            Vec3 view = this.getViewVector(1.0F);
            PoisonBolt bolt = new PoisonBolt(this,
                    view.x + i / 10.0F, view.y, view.z + i / 10.0F, level);
            bolt.setExtraDamage(necroLevel);
            bolt.setPos(this.getX() + view.x / 2.0D, this.getEyeY() - 0.2D,
                    this.getZ() + view.z / 2.0D);
            if (!level.addFreshEntity(bolt)) {
                continue;
            }
            SoundUtil.playSoulBolt(this);
            this.swing(InteractionHand.MAIN_HAND);
        }
    }

    private void castSmallSummon() {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        Direction direction = summonRowDirection();
        double stepX = direction.getStepX();
        double stepZ = direction.getStepZ();
        double rowDistance = 2.0D;
        if (isAquatic()) {
            for (int index = -3; index <= 3; ++index) {
                double x = this.getX() + rowDistance * stepX + index * stepZ;
                double z = this.getZ() + rowDistance * stepZ + index * stepX;
                QuickGrowingKelp kelp = new QuickGrowingKelp(ModEntityType.QUICK_GROWING_KELP.get(), level);
                kelp.setTrueOwner(this);
                kelp.setPerpetual(false);
                kelp.setLifeSpan(QUICK_VINE_LIFESPAN_SECONDS + getSoulJarUpgrades());
                kelp.setWarmup(0);
                kelp.moveTo(x, MinionGroundPlacement.groundFeetYUnderWater(level, x, z, this.getY(),
                        MinionGroundPlacement.SKELETON_CLEARANCE), z, this.getYRot(), 0.0F);
                kelp.setPersistenceRequired();
                if (level.addFreshEntity(kelp)) {
                    this.summonParticles(level, kelp);
                }
            }
            this.spawnWhisperers(level, stepX, stepZ, true);
            this.summonBurst(true);
            return;
        }
        for (int index = -3; index <= 3; ++index) {
            double x = this.getX() + rowDistance * stepX + index * stepZ;
            double z = this.getZ() + rowDistance * stepZ + index * stepX;
            QuickGrowingVine vine = new QuickGrowingVine(ModEntityType.QUICK_GROWING_VINE.get(), level);
            vine.setTrueOwner(this);
            vine.setPerpetual(false);
            vine.setLifeSpan(QUICK_VINE_LIFESPAN_SECONDS + getSoulJarUpgrades());
            vine.setWarmup(0);
            vine.moveTo(x, MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE), z, this.getYRot(), 0.0F);
            vine.setPersistenceRequired();
            if (level.addFreshEntity(vine)) {
                this.summonParticles(level, vine);
            }
        }
        this.spawnWhisperers(level, stepX, stepZ, false);
        this.summonBurst(false);
    }

    private void spawnWhisperers(ServerLevel level, double stepX, double stepZ, boolean aquatic) {
        double centerX = this.getX() + stepX * 3.0D;
        double centerZ = this.getZ() + stepZ * 3.0D;
        int count = this.soulJarUpgrades >= MAX_SOUL_JAR_UPGRADES ? APEX_WHISPERER_COUNT : 1;
        int remaining = WHISPERER_SUMMON_LIMIT - this.countOwnedWhisperers(level);
        if (remaining <= 0) {
            return;
        }
        count = Math.min(count, remaining);
        for (int index = 0; index < count; ++index) {
            double side = count == 1 ? 0.0D
                    : (index == 0 ? -APEX_WHISPERER_SIDE_OFFSET : APEX_WHISPERER_SIDE_OFFSET);
            double x = centerX + stepZ * side;
            double z = centerZ + stepX * side;
            Summoned summon = aquatic
                    ? new Wavewhisperer(ModEntityType.WAVEWHISPERER.get(), level)
                    : new Whisperer(ModEntityType.WHISPERER.get(), level);
            double y = aquatic
                    ? MinionGroundPlacement.groundFeetYUnderWater(level, x, z, this.getY(),
                            MinionGroundPlacement.SKELETON_CLEARANCE)
                    : MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                            MinionGroundPlacement.SKELETON_CLEARANCE);
            this.spawnSummonedAlly(level, summon, x, y, z);
        }
        this.spawnWhispererEscorts(level, stepX, stepZ, centerX, centerZ);
    }

    private void spawnWhispererEscorts(ServerLevel level, double stepX, double stepZ,
            double centerX, double centerZ) {
        if (!this.spiderNestUnlocked && !this.huntingUnlocked) {
            return;
        }
        boolean web = this.spiderNestUnlocked && this.huntingUnlocked
                ? this.random.nextBoolean()
                : this.spiderNestUnlocked;
        List<ResourceLocation> capTypes = List.of(WEB_SPIDER_SERVANT_ID, BLACK_WOLF_ID);
        int remaining = WHISPERER_ESCORT_LIMIT - this.countOwnedEscorts(level, capTypes);
        if (remaining <= 0) {
            return;
        }
        int count = Math.min(2, remaining);
        ResourceLocation typeId = web ? WEB_SPIDER_SERVANT_ID : BLACK_WOLF_ID;
        for (int index = 0; index < count; ++index) {
            double side = count == 1 || index == 0 ? -1.0D : 1.0D;
            double x = centerX + stepZ * side * WHISPERER_ESCORT_SIDE_OFFSET;
            double z = centerZ + stepX * side * WHISPERER_ESCORT_SIDE_OFFSET;
            this.spawnWhispererEscort(level, typeId, x, z);
        }
    }

    private void spawnWhispererEscort(ServerLevel level, ResourceLocation typeId, double x, double z) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(typeId);
        if (type == null) {
            return;
        }
        Entity entity = type.create(level);
        if (!(entity instanceof Summoned summoned)) {
            if (entity != null) {
                entity.discard();
            }
            return;
        }
        double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                MinionGroundPlacement.SKELETON_CLEARANCE);
        this.spawnSummonedAlly(level, summoned, x, y, z);
    }

    private int countOwnedWhisperers(ServerLevel level) {
        return level.getEntitiesOfClass(Whisperer.class,
                this.getBoundingBox().inflate(64.0D, 24.0D, 64.0D),
                this::ownedByMe).size();
    }

    private int countOwnedEscorts(ServerLevel level, List<ResourceLocation> types) {
        return level.getEntitiesOfClass(Entity.class,
                this.getBoundingBox().inflate(64.0D, 16.0D, 64.0D),
                entity -> entity instanceof IOwned && ownedByMe(entity)
                        && types.contains(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())))
                .size();
    }

    private Direction summonRowDirection() {
        LivingEntity target = getTarget();
        if (target != null) {
            double dx = target.getX() - getX();
            double dz = target.getZ() - getZ();
            if (dx * dx + dz * dz > 1.0E-4D) {
                return Direction.getNearest(dx, 0.0D, dz);
            }
        }
        return this.getDirection();
    }

    public void debugStartCast(int spell) {
        if (spell > 0 && spell < CAST_LENGTHS.length) {
            this.requestCast(spell);
        }
    }

    private void castLargeSummon() {
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] enter entity={} aquatic={}", this.getUUID(), isAquatic());
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        Vec3 forward = horizontalLook();
        com.mojang.logging.LogUtils.getLogger().info(
                "[wildmage][large] options mauling={} leaping={} brood={} bear={}/{} leap={}/{} brood={}/{}",
                this.maulingUnlocked, this.leapingUnlocked, this.broodFocusUnlocked,
                this.countOwnedLargeSummons(level, entity -> entity instanceof BearServant || entity instanceof Gnasher),
                BEAST_SUMMON_LIMIT,
                this.countOwnedLargeSummons(level, entity -> entity instanceof Leapleaf),
                LEAPLEAF_SUMMON_LIMIT,
                this.countOwnedBroodMothers(level), BROOD_SUMMON_LIMIT);
        boolean chosen = false;
        int wolfOwned = this.countOwnedLargeSummons(level, entity -> this.ownedByMe(entity) && (TWILIGHT_MIST_WOLF_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())) || TWILIGHT_KING_SPIDER_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))));
        int wolfCap = this.kingSpiderUnlocked && this.alphaWolfUnlocked ? 3 : 2;
        java.util.List<Integer> candidates = new java.util.ArrayList<>();
        if (!isAquatic() && (this.kingSpiderUnlocked || this.alphaWolfUnlocked) && wolfOwned < wolfCap) candidates.add(0);
        if (isAquatic()) candidates.add(1);
        if (!isAquatic() && this.broodFocusUnlocked && this.countOwnedBroodMothers(level) < BROOD_SUMMON_LIMIT) candidates.add(2);
        if (!isAquatic() && this.maulingUnlocked) candidates.add(3);
        if (!isAquatic() && this.leapingUnlocked
                && this.countOwnedLargeSummons(level, entity -> entity instanceof Leapleaf) < LEAPLEAF_SUMMON_LIMIT) candidates.add(4);
        if (candidates.isEmpty()) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] reject reason=all capped");
            this.summonBurst(false);
            return;
        }
        int pick = candidates.get(this.random.nextInt(candidates.size()));
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] candidates={} pick={}", candidates, pick);
        if (pick == 0) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] chosen=wolf count=2");
            chosen = true;
            this.summonWolfChiefFamily(level, forward);
            this.summonBurst(false);
            return;
        }
        if (pick == 1) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] chosen=aquatic");
            chosen = true;
            EntityType<?> leapkelp = leapkelpType();
            if (leapkelp == null || this.rollSummonBear()) {
                int count = Math.min(this.random.nextFloat() < 0.5F ? 2 : 1,
                        BEAST_SUMMON_LIMIT - this.countOwnedLargeSummons(level,
                                entity -> entity instanceof Gnasher || entity instanceof BearServant));
                for (int index = 0; index < count; ++index) {
                    double offset = count == 1 ? 0.0D : (index == 0 ? -1.5D : 1.5D);
                    double x = this.getX() + forward.x * 2.0D + forward.z * offset;
                    double z = this.getZ() + forward.z * 2.0D - forward.x * offset;
                    Gnasher shark = new Gnasher(ModEntityType.GNASHER.get(), level);
                    this.spawnLargeSummonedAlly(level, shark, x,
                            MinionGroundPlacement.groundFeetYUnderWater(level, x, z, this.getY(),
                                    MinionGroundPlacement.SKELETON_CLEARANCE), z);
                }
            } else if (this.countOwnedLargeSummons(level, entity -> entity instanceof Leapleaf)
                    < LEAPLEAF_SUMMON_LIMIT) {
                double x = this.getX() + forward.x * 2.0D;
                double z = this.getZ() + forward.z * 2.0D;
                this.spawnLeapkelp(level, leapkelp, x,
                        MinionGroundPlacement.groundFeetYUnderWater(level, x, z, this.getY(),
                                MinionGroundPlacement.SKELETON_CLEARANCE), z);
            }
            this.summonBurst(true);
            return;
        }
        if (pick == 2 && this.spawnBroodMother(level, forward)) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] chosen=brood count=1");
            chosen = true;
            this.summonBurst(false);
            return;
        }
        if (pick == 3) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] chosen=bear");
            chosen = true;
            int count = Math.min(this.random.nextFloat() < 0.5F ? 2 : 1,
                    BEAST_SUMMON_LIMIT - this.countOwnedLargeSummons(level,
                            entity -> entity instanceof BearServant || entity instanceof Gnasher));
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] count={}", count);
            for (int index = 0; index < count; ++index) {
                double offset = count == 1 ? 0.0D : (index == 0 ? -1.5D : 1.5D);
                double x = this.getX() + forward.x * 2.0D + forward.z * offset;
                double z = this.getZ() + forward.z * 2.0D - forward.x * offset;
                BearServant bear = new BearServant(ModEntityType.BEAR_SERVANT.get(), level);
                this.spawnLargeSummonedAlly(level, bear, x,
                        MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                                MinionGroundPlacement.SKELETON_CLEARANCE), z);
            }
        } else if (pick == 4) {
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] chosen=leapleaf count=1");
            chosen = true;
            Leapleaf leapleaf = new Leapleaf(ModEntityType.LEAPLEAF.get(), level);
            double x = this.getX() + forward.x * 2.0D;
            double z = this.getZ() + forward.z * 2.0D;
            this.spawnLargeSummonedAlly(level, leapleaf, x,
                    MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                            MinionGroundPlacement.SKELETON_CLEARANCE), z);
        }
        if (!chosen) {
            com.mojang.logging.LogUtils.getLogger().info(
                    "[wildmage][large] reject reason=all capped or pick empty");
        }
        this.summonBurst(false);
    }

    private boolean rollSummonBear() {
        return this.maulingUnlocked && this.leapingUnlocked
                ? this.random.nextFloat() < 0.5F
                : this.maulingUnlocked;
    }

    private static EntityType<?> leapkelpType() {
        if (!leapkelpResolved) {
            leapkelpResolved = true;
            ResourceLocation id = OminousCompat.entity(LEAPKELP_PATH);
            leapkelpType = id == null ? null : ForgeRegistries.ENTITY_TYPES.getValue(id);
        }
        return leapkelpType;
    }

    private void spawnLeapkelp(ServerLevel level, EntityType<?> type, double x, double y, double z) {
        Entity created = type.create(level);
        if (created instanceof Summoned summoned) {
            this.spawnLargeSummonedAlly(level, summoned, x, y, z);
        } else if (created != null) {
            created.discard();
        }
    }

    private int countOwnedLargeSummons(ServerLevel level,
            java.util.function.Predicate<Entity> filter) {
        return level.getEntitiesOfClass(Entity.class,
                this.getBoundingBox().inflate(64.0D, 24.0D, 64.0D),
                entity -> this.ownedByMe(entity) && filter.test(entity)).size();
    }

    private void castSeeds() {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        boolean aquatic = isAquatic();
        int alive = countOwnedPoisonVines(level);
        int count = POISON_VINE_CAP - alive;
        if (count <= 0) {
            spawnSlimySummons(level);
            this.summonBurst(aquatic);
            return;
        }
        double radius = 2.6D;
        int placed = 0;
        for (int slot = 0; slot < POISON_VINE_CAP && placed < count; ++slot) {
            double angle = slot * Mth.TWO_PI / POISON_VINE_CAP;
            double x = this.getX() + Math.cos(angle) * radius;
            double z = this.getZ() + Math.sin(angle) * radius;
            EntityType<? extends AbstractVine> vineType = aquatic
                    ? ModEntityType.POISON_ANEMONE.get()
                    : ModEntityType.POISON_QUILL_VINE.get();
            double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE);
            if (!level.getEntitiesOfClass(AbstractVine.class,
                    vineType.getDimensions().makeBoundingBox(x, y, z).inflate(0.25D)).isEmpty()) {
                continue;
            }
            AbstractVine vine = aquatic
                    ? new PoisonAnemone(vineType, level)
                    : new PoisonQuillVine(vineType, level);
            vine.setTrueOwner(this);
            vine.setPerpetual(false);
            vine.setLifeSpan(POISON_VINE_LIFESPAN_SECONDS + getSoulJarUpgrades());
            vine.setWarmup(0);
            vine.moveTo(x, y, z, this.getYRot(), 0.0F);
            vine.setPersistenceRequired();
            if (level.addFreshEntity(vine)) {
                this.summonParticles(level, vine);
            }
            ++placed;
        }
        spawnSlimySummons(level);
        this.summonBurst(aquatic);
    }

    private void spawnSlimySummons(ServerLevel level) {
        if (this.beetleNestUnlocked && this.random.nextFloat() < BROOD_SUMMON_CHANCE) {
            this.spawnBeetlePair(level);
            return;
        }
        ResourceLocation slimeId = isAquatic() ? TROPICAL_SLIME_ID : SLIME_SERVANT_ID;
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(slimeId);
        if (type == null) {
            return;
        }
        int remaining = SLIME_SUMMON_LIMIT - countOwnedSlimes(level, slimeId);
        if (remaining <= 0) {
            return;
        }
        int count = Math.min(2, remaining);
        Direction direction = summonRowDirection();
        double stepX = direction.getStepX();
        double stepZ = direction.getStepZ();
        double centerX = this.getX() + stepX * SLIME_SUMMON_DISTANCE;
        double centerZ = this.getZ() + stepZ * SLIME_SUMMON_DISTANCE;
        for (int index = 0; index < count; ++index) {
            double side = count == 1 || index == 0 ? -1.0D : 1.0D;
            double x = centerX + stepZ * side * SLIME_SUMMON_SIDE_OFFSET;
            double z = centerZ + stepX * side * SLIME_SUMMON_SIDE_OFFSET;
            Entity entity = type.create(level);
            if (!(entity instanceof Summoned slime)) {
                if (entity != null) {
                    entity.discard();
                }
                continue;
            }
            SummonCapUtil.markDirectSummon(slime);
            double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE);
            this.spawnSummonedAlly(level, slime, x, y, z);
            applySlimeSize(slime, this.slimeSummonSize());
        }
    }

    private void summonWolfChiefFamily(ServerLevel level, Vec3 forward) {
        boolean king;
        if (this.kingSpiderUnlocked && this.alphaWolfUnlocked) {
            king = this.random.nextBoolean();
        } else {
            king = this.kingSpiderUnlocked;
        }
        ResourceLocation chosen = king ? TWILIGHT_KING_SPIDER_ID : TWILIGHT_MIST_WOLF_ID;
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(chosen);
        if (type == null) {
            return;
        }
        int owned = this.countOwnedLargeSummons(level, entity -> this.ownedByMe(entity)
                && (TWILIGHT_MIST_WOLF_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))
                || TWILIGHT_KING_SPIDER_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))));
        int cap = this.kingSpiderUnlocked && this.alphaWolfUnlocked ? 3 : 2;
        int count = Math.min(2, cap - owned);
        for (int index = 0; index < count; ++index) {
            double offset = count == 1 ? 0.0D : (index == 0 ? -1.5D : 1.5D);
            double x = this.getX() + forward.x * 2.0D + forward.z * offset;
            double z = this.getZ() + forward.z * 2.0D - forward.x * offset;
            Entity entity = type.create(level);
            if (!(entity instanceof Summoned wolf)) {
                if (entity != null) {
                    entity.discard();
                }
                continue;
            }
            double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE);
            this.spawnLargeSummonedAlly(level, wolf, x, y, z);
        }
    }

    private void spawnBeetlePair(ServerLevel level) {
        EntityType<?> slimeType = ForgeRegistries.ENTITY_TYPES.getValue(TWILIGHT_SLIME_BEETLE_ID);
        EntityType<?> pinchType = ForgeRegistries.ENTITY_TYPES.getValue(TWILIGHT_PINCH_BEETLE_ID);
        if (slimeType == null && pinchType == null) {
            return;
        }
        int owned = this.countOwnedLargeSummons(level, entity -> this.ownedByMe(entity)
                && (TWILIGHT_SLIME_BEETLE_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))
                || TWILIGHT_PINCH_BEETLE_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))));
        int room = 2 - owned;
        for (int index = 0; index < 2 && room > 0; ++index) {
            EntityType<?> type = index == 0 ? slimeType : pinchType;
            if (type == null) {
                continue;
            }
            double side = index == 0 ? -1.0D : 1.0D;
            double x = this.getX() + this.getLookAngle().z * side * 1.5D;
            double z = this.getZ() - this.getLookAngle().x * side * 1.5D;
            Entity entity = type.create(level);
            if (!(entity instanceof Summoned beetle)) {
                if (entity != null) {
                    entity.discard();
                }
                continue;
            }
            double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE);
            this.spawnLargeSummonedAlly(level, beetle, x, y, z);
            --room;
        }
    }

    private int countOwnedSlimes(ServerLevel level, ResourceLocation slimeId) {
        return level.getEntitiesOfClass(Entity.class,
                this.getBoundingBox().inflate(64.0D, 16.0D, 64.0D),
                entity -> entity instanceof IOwned && ownedByMe(entity)
                        && slimeId.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()))
                        && SummonCapUtil.countsTowardCap(entity, slimeId))
                .size();
    }

    private int slimeSummonSize() {
        return this.soulJarUpgrades >= MAX_SOUL_JAR_UPGRADES ? SLIME_SUMMON_SIZE_APEX : SLIME_SUMMON_SIZE;
    }

    private static void applySlimeSize(Entity entity, int size) {
        try {
            java.lang.reflect.Method method = entity.getClass().getMethod("setSize", int.class, boolean.class);
            method.invoke(entity, size, Boolean.TRUE);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
        }
    }

    private boolean spawnBroodMother(ServerLevel level, Vec3 forward) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(BROOD_MOTHER_SERVANT_ID);
        if (type == null) {
            return false;
        }
        Entity entity = type.create(level);
        if (!(entity instanceof Summoned brood)) {
            if (entity != null) {
                entity.discard();
            }
            return false;
        }
        double x = this.getX() + forward.x * 2.0D;
        double z = this.getZ() + forward.z * 2.0D;
        double y = MinionGroundPlacement.groundFeetY(level, x, z, this.getY(),
                MinionGroundPlacement.SKELETON_CLEARANCE);
        this.spawnLargeSummonedAlly(level, brood, x, y, z);
        return true;
    }

    private int countOwnedBroodMothers(ServerLevel level) {
        return level.getEntitiesOfClass(Entity.class,
                this.getBoundingBox().inflate(64.0D, 16.0D, 64.0D),
                entity -> entity.isAlive() && entity instanceof IOwned && ownedByMe(entity)
                        && BROOD_MOTHER_SERVANT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())))
                .size();
    }

    private int countOwnedPoisonVines(ServerLevel level) {
        return level.getEntitiesOfClass(PoisonQuillVine.class,
                this.getBoundingBox().inflate(POISON_VINE_COUNT_RADIUS),
                vine -> vine.isAlive() && ownedByMe(vine)).size();
    }

    private void castEntangle() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        int level = Math.max(0, this.getSoulJarUpgrades());
        int maxTargets = 4 + level;
        int durationTicks = 100 + Math.round(level * 16.6F);
        int count = 0;
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][entangle] targets start max={}", maxTargets);
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(12.0D))) {
            if (victim == this || victim.isDeadOrDying()
                    || com.Polarice3.Goety.utils.MobUtil.areAllies(this, victim)
                    || victim.getType().is(com.Polarice3.Goety.init.ModTags.EntityTypes.UNTANGLEABLE)
                    || !victim.canBeAffected(new net.minecraft.world.effect.MobEffectInstance(
                            com.Polarice3.Goety.common.effects.GoetyEffects.TANGLED.get(), durationTicks))) {
                continue;
            }
            com.Polarice3.Goety.common.entities.projectiles.EntangleVines vines =
                    new com.Polarice3.Goety.common.entities.projectiles.EntangleVines(
                            server, this, victim);
            vines.setLifeSpan(80 + durationTicks);
            if (server.addFreshEntity(vines) && ++count >= maxTargets) {
                break;
            }
        }
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][entangle] targets={}", count);
    }

    private void castMines(@Nullable LivingEntity target) {
        if (!(this.level() instanceof ServerLevel level) || target == null) {
            return;
        }
        for (int index = 0; index < 3; ++index) {
            double angle = this.random.nextDouble() * Mth.TWO_PI;
            double distance = 1.0D + this.random.nextDouble() * 2.0D;
            double x = target.getX() + Math.cos(angle) * distance;
            double z = target.getZ() + Math.sin(angle) * distance;
            BioMine mine = new BioMine(ModEntityType.BIOMINE.get(), level);
            mine.setOwner(this);
            mine.setLifeTicks(600);
            mine.setExtraDamage(this.soulJarUpgrades);
            mine.moveTo(x, MinionGroundPlacement.groundFeetY(level, x, z, target.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE), z, 0.0F, 0.0F);
            level.addFreshEntity(mine);
        }
    }

    private static final int BLOSSOM_POTENCY_PER_UPGRADE = 3;

    private void castBlossom(@Nullable LivingEntity target) {
        if (!(this.level() instanceof ServerLevel level) || target == null) {
            return;
        }
        this.getLookControl().setLookAt(target, 360.0F, 360.0F);
        this.lookAt(target, 360.0F, 360.0F);
        BlossomSpell blossom = new BlossomSpell();
        blossom.SpellResult(level, this, ItemStack.EMPTY,
                blossom.defaultStats().increasePotency(this.soulJarUpgrades * BLOSSOM_POTENCY_PER_UPGRADE));
    }

    private void castHeal() {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(HEAL_RADIUS));
        this.sendSoulHealShockwave(level, this.getX(), this.getY(), this.getZ());
        this.playSound(ModSounds.SOUL_HEAL.get(), 1.0F, 1.0F);
        float minHeal = HEAL_MIN + this.soulJarUpgrades * HEAL_MIN_PER_LEVEL;
        float maxHeal = HEAL_MAX + this.soulJarUpgrades * HEAL_MAX_PER_LEVEL;
        for (LivingEntity living : targets) {
            if (!living.isAlive() || !isHealableWildkin(living)) {
                continue;
            }
            float amount = minHeal + this.random.nextFloat() * Math.max(0.0F, maxHeal - minHeal);
            living.heal(amount);
            living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 0), this);
            this.sendSoulHealRings(level, living);
        }
    }

    private void sendSoulHealShockwave(ServerLevel level, double x, double y, double z) {
        if (GoetyCompat.isGoety60()) {
            level.sendParticles(new SoulShockwaveParticleOption(), x, y + 0.5D, z,
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void sendSoulHealRings(ServerLevel level, LivingEntity target) {
        ColorUtil color = new ColorUtil(SOUL_HEAL_COLOR);
        for (int delay : SOUL_HEAL_RING_DELAYS) {
            level.sendParticles(new RisingCircleParticleOption(delay),
                    target.getX(), target.getY(), target.getZ(), 0,
                    color.red, color.green, color.blue, 1.0D);
        }
    }

    private void castBuff() {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        this.playSound(SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(
                        this.random.nextInt(SoundEvents.GOAT_HORN_SOUND_VARIANTS.size())).value(),
                2.5F, 1.0F);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(15.0D));
        for (LivingEntity living : targets) {
            if (!living.isAlive() || living == this || !isHealableWildkin(living)) {
                continue;
            }
            living.addEffect(new MobEffectInstance(GoetyEffects.BUFF.get(), 15 * 20, 0), this);
            living.addEffect(new MobEffectInstance(GoetyEffects.RALLIED.get(), 15 * 20, 0), this);
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20, 0), this);
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, living.getX(), living.getY(1.0D),
                    living.getZ(), 6, 0.5D, 0.6D, 0.5D, 0.02D);
        }
    }

    private Vec3 horizontalLook() {
        Vec3 look = this.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        return flat.lengthSqr() < 1.0E-4D ? new Vec3(0.0D, 0.0D, 1.0D) : flat.normalize();
    }

    private void summonBurst(boolean aquatic) {
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(aquatic ? ParticleTypes.BUBBLE : ParticleTypes.HAPPY_VILLAGER,
                    this.getX(), this.getY(0.6D), this.getZ(), 18, 1.0D, 0.6D, 1.0D, 0.05D);
        }
        this.playSound(aquatic ? ModSounds.WAVEWHISPERER_SUMMON.get() : ModSounds.WHISPERER_SUMMON.get(),
                1.0F, VOICE_PITCH);
    }

    private void summonParticles(ServerLevel level, net.minecraft.world.entity.Entity entity) {
        for (int i = -1; i <= 1; ++i) {
            ServerParticleUtil.sendGodRay(level,
                    entity.getX() + i * 0.6D, entity.getY(), entity.getZ(), WILD_SUMMON_COLOR);
        }
        int count = 10 + this.random.nextInt(10);
        for (int i = 0; i < count; ++i) {
            level.sendParticles(new com.Polarice3.Goety.client.particles.MagicSmokeParticleOption(
                            0x4FAF42, 0xB7FF8A,
                            10 + this.random.nextInt(10), 0.2F),
                    entity.getRandomX(1.5D), entity.getRandomY(), entity.getRandomZ(1.5D),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void spawnSummonedAlly(ServerLevel level, Summoned summoned, double x, double y, double z) {
        this.spawnSummonedAlly(level, summoned, x, y, z, false);
    }

    private void spawnLargeSummonedAlly(ServerLevel level, Summoned summoned, double x, double y, double z) {
        this.spawnSummonedAlly(level, summoned, x, y, z, true);
    }

    private void spawnSummonedAlly(ServerLevel level, Summoned summoned, double x, double y, double z,
            boolean summonCircle) {
        LivingEntity master = this.directSummonToMaster && this.getTrueOwner() != null
                ? this.getTrueOwner() : this;
        summoned.setTrueOwner(master);
        summoned.getPersistentData().putUUID(SUMMONER_TAG, this.getUUID());
        summoned.moveTo(x, y, z, this.getYRot(), 0.0F);
        summoned.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS + getSoulJarUpgrades() * 30 * 20);
        summoned.setPersistenceRequired();
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] spawn type={} pos={},{},{} circle={}", summoned.getType(), x, y, z, summonCircle);
        if (summonCircle) {
            ScaledSummonCircle circle = new ScaledSummonCircle(level, new Vec3(x, y, z), summoned,
                    false, true, master);
            circle.lifeSpan = 20;
            circle.noParticles = false;
            boolean circleAdded = level.addFreshEntity(circle);
            com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] circle addFreshEntity={} circleId={}", circleAdded, circle.getId());
            return;
        }
        summoned.finalizeSpawn(level, level.getCurrentDifficultyAt(summoned.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        boolean added = level.addFreshEntity(summoned);
        com.mojang.logging.LogUtils.getLogger().info("[wildmage][large] direct addFreshEntity={} alive={} removed={}", added, summoned.isAlive(), summoned.isRemoved());
        if (added) {
            this.summonParticles(level, summoned);
        }
    }

    private boolean ownedByMe(Entity entity) {
        if (entity == null || entity == this) {
            return false;
        }
        net.minecraft.nbt.CompoundTag data = entity.getPersistentData();
        if (data.hasUUID(SUMMONER_TAG) && this.getUUID().equals(data.getUUID(SUMMONER_TAG))) {
            return true;
        }
        return entity instanceof IOwned owned && owned.getTrueOwner() == this;
    }

    public boolean isAquatic() {
        return this.isInWater() || this.isUnderWater();
    }

    private static final float VOICE_PITCH = 0.8F;

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * VOICE_PITCH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.WHISPERER_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return ModSounds.WHISPERER_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.WHISPERER_DEATH.get();
    }

    @Override
    public void playStepSound(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        this.playSound(ModSounds.WHISPERER_STEP.get(), 0.15F, VOICE_PITCH);
    }

    private boolean hasWoundedWildkin() {
        if (this.getHealth() < this.getMaxHealth() * 0.8F) {
            return true;
        }
        for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(HEAL_RADIUS))) {
            if (living.isAlive() && living.getHealth() < living.getMaxHealth() * 0.8F
                    && isHealableWildkin(living) && isFriendlyWild(living)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasServantToRally() {
        for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(15.0D))) {
            if (living.isAlive() && living != this && isHealableWildkin(living)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWildkin(Entity entity) {
        return entity instanceof AbstractWildMage
                || entity instanceof BearServant
                || entity instanceof Leapleaf
                || entity instanceof Whisperer
                || entity instanceof Gnasher
                || entity instanceof AbstractVine;
    }

    private static boolean isSlimeServant(Entity entity) {
        return entity != null && SLIME_SERVANT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    private boolean isHealableWildkin(LivingEntity living) {
        return (isWildkin(living) || isSlimeServant(living))
                && (isFriendlyWild(living) || ownedByMe(living));
    }

    private boolean isFriendlyWild(LivingEntity living) {
        if (living == this) {
            return true;
        }
        LivingEntity myOwner = this.getTrueOwner();
        if (myOwner != null) {
            if (living == myOwner || MobUtil.getOwner(living) == myOwner) {
                return true;
            }
        }
        return this.isAlliedTo(living);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || effect.getEffect() == GoetyEffects.ACID_VENOM.get()) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    public ItemStack wildStaff() {
        return ItemStack.EMPTY;
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

    public boolean ownedBySomeone() {
        return this.getTrueOwner() != null;
    }
}
