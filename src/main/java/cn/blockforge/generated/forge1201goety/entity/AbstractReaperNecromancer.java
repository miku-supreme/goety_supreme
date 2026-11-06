package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.SEHelper;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.util.CastQueue;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.util.FlightKnockbackRecovery;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.ReaperBladeEntity;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBladeRenderer;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.model.ReaperNecromancerModel;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.spell.ShockwaveFocus;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;

public abstract class AbstractReaperNecromancer extends Summoned implements UndeadCapeBearer {

    public static final double MAX_HEALTH = 220.0D;
    public static final double ATTACK_DAMAGE = 8.0D;

    static final double FOLLOW_RANGE = 32.0D;

    private static final double MOVEMENT_SPEED = 0.25D;

    private static final double FLYING_SPEED = 0.5D;

    private static final double KNOCKBACK_RESISTANCE = 1.0D;

    public static final int SOUL_HARVEST = 15;

    public static final int MAX_SOUL_JAR_UPGRADES = 2;

    public static final float SOUL_JAR_SIZE_PER_LEVEL = 0.15F;

    public static final float BLADE_DAMAGE = 8.0F;

    public static final float BLADE_SPEED = 1.0F;

    public static final float SPIN_DAMAGE = 4.0F;

    public static final float SPIN_SPEED = 0.15F;

    public static final float SOUL_JAR_BLADE_SPEED_PER_LEVEL = 0.10F;

    public static final float SOUL_JAR_WIDTH_PER_LEVEL = 0.10F;

    public static final int SOUL_JAR_SAPPED_TICKS = 100;

    public static final int CAST_NONE = 0;
    public static final int CAST_ATTACK = 1;
    public static final int CAST_SUMMON = 2;
    public static final int CAST_PUNISH = 3;
    public static final int CAST_SPIN = 4;
    public static final int CAST_SNAP = 5;

    private static final int[] CAST_LENGTHS = {0, 20, 36, 45, 70, 30};

    private static final float[] CAST_IMPACT = {0.0F, 0.45F, 0.70F, 6.0F / 45.0F, 0.50F, 0.60F};

    static final int ATTACK_COOLDOWN = 24;
    static final int SUMMON_COOLDOWN = 300;

    static final int SPIN_COOLDOWN = 200;

    static final int PUNISH_COOLDOWN = 200;

    static final int SNAP_COOLDOWN = 400;

    private static final int SNAP_SHOCKWAVE_POTENCY = 1;
    private static final int SNAP_SHOCKWAVE_RADIUS = 4;

    private static final int WEIGHT_SUMMON = 6;

    private static final int WEIGHT_SPIN = 5;

    private static final int WEIGHT_PUNISH = 5;

    private static final int WEIGHT_SNAP_HEALTHY = 1;

    private static final int WEIGHT_SNAP_HURT = 18;

    private static final int SUMMON_LAUGH_DELAY = 12;

    private static final float LAUGH_REAPER_VOLUME = 1.8F;
    private static final float LAUGH_REAPER_PITCH = 0.9F;
    private static final float LAUGH_NECRO_VOLUME = 0.9F;
    private static final float LAUGH_NECRO_PITCH = 0.7F;

    static final double MELEE_RANGE_SQR = 9.0D;

    static final double ATTACK_RANGE_SQR = 400.0D;

    private static final double PUNISH_RADIUS = 16.0D;

    private static final int PUNISH_TICKS = 37;
    private static final int PUNISH_INTERVAL = 10;

    private static final EntityDataAccessor<Integer> DATA_CAST_ANIMATION =
            SynchedEntityData.defineId(AbstractReaperNecromancer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_CAST_TICKS =
            SynchedEntityData.defineId(AbstractReaperNecromancer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SOUL_JAR_UPGRADES =
            SynchedEntityData.defineId(AbstractReaperNecromancer.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_ATTACK_SPEED =
            SynchedEntityData.defineId(AbstractReaperNecromancer.class, EntityDataSerializers.FLOAT);

    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState restAnimationState = new AnimationState();
    public final AnimationState castAnimationState = new AnimationState();

    public final AnimationState deathAnimationState = new AnimationState();

    public final AnimationState takeoffAnimationState = new AnimationState();
    public final AnimationState landingAnimationState = new AnimationState();
    private int reaperTransitionTicks;
    private boolean reaperTransitionTakeoff;
    private boolean reaperLastMoving;
    private int postCastBlendTicks;

    private int lastCastType;

    public static final int POST_CAST_BLEND_TICKS = 6;

    public boolean isReaperTransitioning() {
        return this.reaperTransitionTicks > 0;
    }

    public boolean isReaperTransitionTakeoff() {
        return this.reaperTransitionTakeoff;
    }

    public int getPostCastBlendTicks() {
        return this.postCastBlendTicks;
    }

    public int getLastCastAnimation() {
        return this.lastCastType;
    }

    private static final int DEATH_ANIMATION_TICKS = 35;

    public float reaperFloatBlend = 0.0F;

    public float reaperFloatBlendAge = Float.NaN;

    int attackCooldown;
    int summonCooldown;
    int spinCooldown;
    int punishCooldown;
    int snapCooldown;
    int spellPickCooldown;
    int pendingSpell = CAST_NONE;

    private boolean pendingAttackMelee;

    private int lastCastAnimation = CAST_NONE;
    private int castAnimation;
    private int castElapsed;
    private int castLength;
    private int castImpact;
    private boolean castApplied;

    private final CastQueue castQueue = new CastQueue();

    private boolean directSummonToMaster;

    private int soulJarUpgrades;

    private int summonLaughDelay;

    private int cheatSheets;

    private boolean hauntFocusGiven;

    public int cantDo;

    private int punishTicks;
    private int punishNextTick;
    private final List<UUID> punishEnemies = new ArrayList<>();
    private final List<UUID> punishAllies = new ArrayList<>();

    private static final ResourceLocation REAPER_SERVANT_TYPE_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "reaper_servant");

    private static final int MAX_HELD_SERVANTS = 6;
    private static final String SUMMONER_TAG = "ReaperSummoner";

    private static final String HAUNT_FOCUS_PATH = "haunt_focus";

    private static final ResourceLocation HAUNT_TYPE_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "haunt");

    private static final String HAUNT_SUMMONER_TAG = "ReaperHauntSummoner";

    private static final int HAUNT_BASE_COUNT = 2;

    private static final float HAUNT_BONUS_CHANCE = 0.5F;

    private static final int HAUNT_CAP = 6;

    private static final int REFUSE_SHAKE_TICKS = 40;

    public static final ResourceLocation ROTTING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "rotting_focus");
    public static final ResourceLocation OSSEOUS_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "osseous_focus");
    public static final ResourceLocation SPOOKY_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "spooky_focus");
    public static final ResourceLocation REAPING_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "reaping_focus");
    public static final ResourceLocation VANGUARD_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "vanguard_focus");
    public static final ResourceLocation BLACKGUARD_FOCUS_ID = ResourceLocation.fromNamespaceAndPath("goety", "blackguard_focus");

    private static final ResourceLocation[] FOCUS_IDS = {
            ROTTING_FOCUS_ID, OSSEOUS_FOCUS_ID, SPOOKY_FOCUS_ID,
            REAPING_FOCUS_ID, VANGUARD_FOCUS_ID, BLACKGUARD_FOCUS_ID
    };

    private static final ResourceLocation[] FOCUS_SERVANT_IDS = {
            ResourceLocation.fromNamespaceAndPath("goety", "zombie_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "skeleton_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "wraith_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "reaper_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "vanguard_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "blackguard_servant")
    };

    private static final float[] FOCUS_CHANCE = {0.5F, 0.5F, 0.05F, 0.05F, 0.15F, 0.05F};

    private static final String[] FOCUS_TAGS = {
            "ReaperFocusZombie", "ReaperFocusSkeleton", "ReaperFocusWraith",
            "ReaperFocusReaper", "ReaperFocusVanguard", "ReaperFocusBlackguard"
    };

    private final boolean[] focusUnlocked = new boolean[FOCUS_IDS.length];

    public AbstractReaperNecromancer(EntityType<? extends AbstractReaperNecromancer> entityType,
            Level level) {
        super(entityType, level);
        this.xpReward = 40;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.REAPER_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.REAPER_ATTACK.get())
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FLYING_SPEED, FLYING_SPEED)
                .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE);
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

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_SOUL_JAR_UPGRADES.equals(key)) {
            this.soulJarUpgrades = this.entityData.get(DATA_SOUL_JAR_UPGRADES);
            this.refreshDimensions();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ReaperCast", this.castAnimation);
        tag.putInt("ReaperCastElapsed", this.castElapsed);
        tag.putInt("ReaperAttackCd", this.attackCooldown);
        tag.putInt("ReaperSummonCd", this.summonCooldown);
        tag.putInt("ReaperSpinCd", this.spinCooldown);
        tag.putInt("ReaperPunishCd", this.punishCooldown);
        tag.putInt("ReaperSnapCd", this.snapCooldown);
        tag.putInt("ReaperSoulJarUpgrades", this.soulJarUpgrades);
        tag.putInt(FocusSlotRules.CHEAT_SHEETS_TAG, this.cheatSheets);
        tag.putBoolean("ReaperHauntFocus", this.hauntFocusGiven);
        tag.putFloat("AttackSpeed", this.getAttackSpeed());
        for (int i = 0; i < FOCUS_TAGS.length; i++) {
            tag.putBoolean(FOCUS_TAGS[i], this.focusUnlocked[i]);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackCooldown = tag.getInt("ReaperAttackCd");
        this.summonCooldown = tag.getInt("ReaperSummonCd");
        this.spinCooldown = tag.getInt("ReaperSpinCd");
        this.punishCooldown = tag.getInt("ReaperPunishCd");
        this.snapCooldown = tag.getInt("ReaperSnapCd");
        this.soulJarUpgrades = Mth.clamp(tag.getInt("ReaperSoulJarUpgrades"), 0, MAX_SOUL_JAR_UPGRADES);
        this.cheatSheets = Mth.clamp(tag.getInt(FocusSlotRules.CHEAT_SHEETS_TAG),
                0, FocusSlotRules.MAX_CHEAT_SHEETS);
        this.hauntFocusGiven = tag.getBoolean("ReaperHauntFocus");
        if (tag.contains("AttackSpeed")) {
            this.setAttackSpeed(tag.getFloat("AttackSpeed"));
        }
        for (int i = 0; i < FOCUS_TAGS.length; i++) {
            this.focusUnlocked[i] = tag.getBoolean(FOCUS_TAGS[i]);
        }
        this.entityData.set(DATA_SOUL_JAR_UPGRADES, this.soulJarUpgrades);
        this.refreshSoulJarAttributes();
        this.refreshDimensions();
    }

    public int getSoulJarUpgrades() {
        return this.entityData.get(DATA_SOUL_JAR_UPGRADES);
    }

    public float soulJarDamageBonus() {
        return this.getSoulJarUpgrades();
    }

    public float soulJarSizeScale() {
        int level = this.getSoulJarUpgrades();
        return level <= 0 ? 1.0F : 1.0F + level * SOUL_JAR_SIZE_PER_LEVEL;
    }

    public float soulJarBladeSpeedScale() {
        return 1.0F + this.getSoulJarUpgrades() * SOUL_JAR_BLADE_SPEED_PER_LEVEL;
    }

    public float soulJarWidthScale() {
        return 1.0F + this.getSoulJarUpgrades() * SOUL_JAR_WIDTH_PER_LEVEL;
    }

    public void applySoulJarSapped(LivingEntity victim) {
        if (this.getSoulJarUpgrades() < MAX_SOUL_JAR_UPGRADES || GoetyEffects.SAPPED == null) {
            return;
        }
        victim.addEffect(new MobEffectInstance(GoetyEffects.SAPPED.get(),
                SOUL_JAR_SAPPED_TICKS, 0, false, true));
    }

    public void applySoulJarSappedAround(ServerLevel server, double x, double y, double z, double radius) {
        if (this.getSoulJarUpgrades() < MAX_SOUL_JAR_UPGRADES || GoetyEffects.SAPPED == null) {
            return;
        }
        AABB area = new AABB(x - radius, y - radius, z - radius,
                x + radius, y + radius, z + radius).inflate(1.0D);
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != this && !this.isMyAlly(e))) {
            victim.addEffect(new MobEffectInstance(GoetyEffects.SAPPED.get(),
                    SOUL_JAR_SAPPED_TICKS, 0, false, true));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean soulReaperJar = ReaperSoulJarItem.holdsSoulReaper(stack, this.level());
        if (this.getTrueOwner() == player && !soulReaperJar
                && stack.is(GeneratedMod.REAPER_SOUL_JAR.get())
                && this.getSoulJarUpgrades() < MAX_SOUL_JAR_UPGRADES) {
            if (!this.level().isClientSide) {
                this.upgradeSoulJar();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && !soulReaperJar && this.getHealth() < this.getMaxHealth()
                && stack.is(GeneratedMod.REAPER_SOUL_JAR.get())) {
            if (!this.level().isClientSide) {
                this.healFromSoulJar(player, stack);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && this.getHealth() < this.getMaxHealth()
                && isEctoplasm(stack)) {
            if (!this.level().isClientSide) {
                ServantFeedHeal.feed(this, player, stack, this.getMaxHealth() / 4.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player) {
            if (FocusSlotRules.tryFeedCheatSheet(this, player, stack, this.cheatSheets,
                    this.getHurtSound(this.damageSources().generic()),
                    value -> this.cheatSheets = value)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (!this.hauntFocusGiven && isHauntFocus(stack)) {
                if (!this.level().isClientSide) {
                    if (this.grantedFocusCount() >= this.focusSlots()) {
                        this.refuseFocus();
                    } else {
                        this.grantHauntFocus(player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        int focusIndex = this.focusIndexToUnlock(stack);
        if (focusIndex >= 0) {
            if (!this.level().isClientSide) {
                if (this.grantedFocusCount() >= this.focusSlots()) {
                    this.refuseFocus();
                } else {
                    this.unlockFocus(player, stack, focusIndex);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.getTrueOwner() == player && player.getMainHandItem().isEmpty() && this.isAlive()
                && !this.isCasting() && this.summonCooldown <= 0) {
            if (!this.level().isClientSide) {
                this.summonCooldown = SUMMON_COOLDOWN;
                this.directSummonToMaster = true;
                this.startCast(CAST_SUMMON, false);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    private int focusIndexToUnlock(ItemStack stack) {
        for (int i = 0; i < FOCUS_IDS.length; i++) {
            if (this.focusUnlocked[i]) {
                continue;
            }
            Item item = ForgeRegistries.ITEMS.getValue(FOCUS_IDS[i]);
            if (item != null && stack.is(item)) {
                return i;
            }
        }
        return -1;
    }

    private void unlockFocus(Player player, ItemStack stack, int index) {
        this.focusUnlocked[index] = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.playFocusGrantFx();
    }

    private void grantHauntFocus(Player player, ItemStack stack) {
        this.hauntFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.playFocusGrantFx();
    }

    private void playFocusGrantFx() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + this.getBbHeight() * 0.6D,
                    this.getZ(), 24, 0.5D, 0.7D, 0.5D, 0.02D);
        }
        this.playMergedLaugh();
    }

    private int focusSlots() {
        return FocusSlotRules.maxSlots(FocusSlotRules.NECROMANCER_BASE_SLOTS, this.cheatSheets);
    }

    private int grantedFocusCount() {
        int count = 0;
        for (boolean unlocked : this.focusUnlocked) {
            if (unlocked) {
                ++count;
            }
        }
        if (this.hauntFocusGiven) {
            ++count;
        }
        return count;
    }

    private void refuseFocus() {
        FocusSlotRules.refuse(this, this.getHurtSound(this.damageSources().generic()));
    }

    private static boolean isHauntFocus(ItemStack stack) {
        if (stack.isEmpty() || !OminousCompat.loaded()) {
            return false;
        }
        ResourceLocation id = OminousCompat.item(HAUNT_FOCUS_PATH);
        if (id == null) {
            return false;
        }
        Item item = ForgeRegistries.ITEMS.getValue(id);
        return item != null && stack.is(item);
    }

    public void upgradeSoulJar() {
        if (this.getSoulJarUpgrades() >= MAX_SOUL_JAR_UPGRADES) {
            return;
        }
        this.soulJarUpgrades = this.getSoulJarUpgrades() + 1;
        this.entityData.set(DATA_SOUL_JAR_UPGRADES, this.soulJarUpgrades);
        this.refreshSoulJarAttributes();
        this.refreshDimensions();
        this.playMergedLaugh();
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + this.getBbHeight() * 0.6D,
                    this.getZ(), 24, 0.5D, 0.7D, 0.5D, 0.02D);
        }
    }

    private void healFromSoulJar(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.setHealth(this.getMaxHealth());
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + this.getBbHeight() * 0.6D,
                    this.getZ(), 24, 0.5D, 0.7D, 0.5D, 0.02D);
        }
        this.playMergedLaugh();
    }

    private static boolean isEctoplasm(ItemStack stack) {
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("goety", "ectoplasm"));
        return item != null && stack.is(item);
    }

    private void refreshSoulJarAttributes() {
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health == null) {
            return;
        }
        int level = this.soulJarUpgrades;
        double factor = level <= 0 ? 1.0D : level * 1.25D;
        health.setBaseValue(SupremeMobsConfig.REAPER_HEALTH.get() * factor);
        AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(SupremeMobsConfig.REAPER_ATTACK.get() + level);
        }
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions base = super.getDimensions(pose);
        float scale = this.soulJarSizeScale();
        return scale == 1.0F ? base : base.scale(scale);
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbstractReaperNecromancerGoals.SpellGoal(this));
        this.goalSelector.addGoal(2, new AbstractReaperNecromancerGoals.CombatGoal(this));
        this.goalSelector.addGoal(6, new AbstractReaperNecromancerGoals.FlyingWanderGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.isSpellCastingParticles()) {
            this.spellCastParticles();
        }
        if (this.cantDo > 0) {
            --this.cantDo;
        }
        if (!this.level().isClientSide) {
            this.tickCooldowns();
            this.tickCast();
            this.tickPunish();
            this.tickSummonLaugh();
            this.tickSunlight();
        } else {
            this.tickClientAnimations();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 9) {
            this.cantDo = REFUSE_SHAKE_TICKS;
            return;
        }
        super.handleEntityEvent(id);
    }

    private void tickCooldowns() {
        if (this.attackCooldown > 0) --this.attackCooldown;
        if (this.summonCooldown > 0) --this.summonCooldown;
        if (this.spinCooldown > 0) --this.spinCooldown;
        if (this.punishCooldown > 0) --this.punishCooldown;
        if (this.snapCooldown > 0) --this.snapCooldown;
        if (this.spellPickCooldown > 0) --this.spellPickCooldown;
    }

    @Override
    public void tickDeath() {
        this.castQueue.clear();
        ++this.deathTime;
        if (this.deathTime >= DEATH_ANIMATION_TICKS
                && !this.level().isClientSide && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        this.castQueue.clear();
        super.remove(reason);
    }

    private void tickSummonLaugh() {
        if (this.summonLaughDelay <= 0) {
            return;
        }
        if (--this.summonLaughDelay == 0) {
            this.playMergedLaugh();
        }
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
        if (this.castElapsed >= this.castLength) {
            this.setCastAnimation(CAST_NONE, 0);
            this.castElapsed = 0;
            this.flushQueuedCast();
        } else {
            this.entityData.set(DATA_CAST_TICKS, Math.max(0, this.castLength - this.castElapsed));
        }
    }

    private void tickClientAnimations() {
        if (this.deathTime > 0) {
            this.deathAnimationState.startIfStopped(this.tickCount);
            this.idleAnimationState.stop();
            this.restAnimationState.stop();
            this.castAnimationState.stop();
            return;
        }
        int cast = this.getCastAnimation();
        int lastCast = this.lastCastAnimation;
        boolean castEnded = cast == CAST_NONE && lastCast != CAST_NONE;
        if (cast != this.lastCastAnimation) {
            this.lastCastAnimation = cast;
            if (cast != CAST_NONE) {
                this.castAnimationState.start(this.tickCount);
            }
        }
        if (castEnded) {
            this.lastCastType = lastCast;
            this.postCastBlendTicks = POST_CAST_BLEND_TICKS;
            this.reaperLastMoving = false;
            this.reaperFloatBlend = 0.0F;
        }
        if (cast == CAST_NONE) {
            this.idleAnimationState.startIfStopped(this.tickCount);
            this.restAnimationState.startIfStopped(this.tickCount);

            if (this.postCastBlendTicks > 0) {
                --this.postCastBlendTicks;
            } else {
                boolean moving = this.isMovingForAnimation();
                if (moving != this.reaperLastMoving) {
                    this.reaperLastMoving = moving;
                    this.reaperTransitionTakeoff = moving;
                    this.reaperTransitionTicks = 10;
                    if (moving) {
                        this.takeoffAnimationState.start(this.tickCount);
                        this.landingAnimationState.stop();
                    } else {
                        this.landingAnimationState.start(this.tickCount);
                        this.takeoffAnimationState.stop();
                    }
                }
                if (this.reaperTransitionTicks > 0) {
                    --this.reaperTransitionTicks;
                    if (this.reaperTransitionTicks <= 0) {
                        this.takeoffAnimationState.stop();
                        this.landingAnimationState.stop();
                    }
                }
            }
        } else {
            this.idleAnimationState.stop();
            this.restAnimationState.stop();
            this.postCastBlendTicks = 0;
            this.reaperTransitionTicks = 0;
            this.takeoffAnimationState.stop();
            this.landingAnimationState.stop();
        }
    }

    public boolean isMovingForAnimation() {
        return this.walkAnimation.speed() > 0.01F;
    }

    public int getCastAnimation() {
        return this.entityData.get(DATA_CAST_ANIMATION);
    }

    public int getCastAnimationTick() {
        return this.entityData.get(DATA_CAST_TICKS);
    }

    private void spellCastParticles() {
        for (int i = 0; i < this.random.nextInt(35) + 10; ++i) {
            this.level().addParticle(
                    GeneratedMod.SOUL_TOTEM_EFFECT.get(),
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

    private void setCastAnimation(int animation, int length) {
        this.castAnimation = animation;
        this.castLength = length;
        this.castElapsed = 0;
        this.castApplied = false;
        this.castImpact = animation == CAST_NONE ? 0
                : Math.max(1, Math.round(length * CAST_IMPACT[animation]));
        this.entityData.set(DATA_CAST_ANIMATION, animation);
        this.entityData.set(DATA_CAST_TICKS, animation == CAST_NONE ? 0 : length);
    }

    void startCast(int animation, boolean meleeAttack) {
        if (this.castAnimation != CAST_NONE && this.castElapsed < this.castLength) {
            this.castQueue.offer(animation, animation, meleeAttack);
            return;
        }
        this.beginCast(animation, meleeAttack);
    }

    private void beginCast(int animation, boolean meleeAttack) {
        this.pendingAttackMelee = meleeAttack;
        this.setCastAnimation(animation, CAST_LENGTHS[animation]);
    }

    private void flushQueuedCast() {
        if (this.castQueue.isEmpty() || this.isCasting()) {
            return;
        }
        int animation = this.castQueue.animation();
        boolean meleeAttack = this.castQueue.flag();
        this.castQueue.clear();
        if (this.isDeadOrDying() || this.isRemoved()) {
            return;
        }
        this.beginCast(animation, meleeAttack);
    }

    private void applyCast(int animation) {
        switch (animation) {
            case CAST_ATTACK -> this.performAttack(this.pendingAttackMelee);
            case CAST_SUMMON -> this.castSummon();
            case CAST_PUNISH -> this.castPunish();
            case CAST_SPIN -> this.castSpin();
            case CAST_SNAP -> this.castSnap();
            default -> {
            }
        }
    }

    int pickWeightedSpell() {
        boolean hurt = this.getHealth() <= this.getMaxHealth() * 0.5F;
        int weightSummon = this.summonCooldown <= 0 ? WEIGHT_SUMMON : 0;
        int weightSpin = this.spinCooldown <= 0 ? WEIGHT_SPIN : 0;
        int weightPunish = this.punishCooldown <= 0 ? WEIGHT_PUNISH : 0;
        int weightSnap = 0;
        if (this.snapCooldown <= 0) {
            if (hurt) {
                weightSnap = this.hasMinionsOrSpins() ? WEIGHT_SNAP_HURT : 0;
            } else if (this.ownedReaperServantCount() >= 3) {
                weightSnap = WEIGHT_SNAP_HEALTHY;
            }
        }
        int total = weightSummon + weightSpin + weightPunish + weightSnap;
        if (total <= 0) {
            return CAST_NONE;
        }
        int roll = this.random.nextInt(total);
        if ((roll -= weightSummon) < 0) {
            return CAST_SUMMON;
        }
        if ((roll -= weightSpin) < 0) {
            return CAST_SPIN;
        }
        if ((roll -= weightPunish) < 0) {
            return CAST_PUNISH;
        }
        return weightSnap > 0 ? CAST_SNAP : CAST_NONE;
    }

    private void performAttack(boolean melee) {
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        if (melee && this.distanceToSqr(target) <= (MELEE_RANGE_SQR + 1.0D)) {
            this.doHurtTarget(target);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        this.getX() + this.getLookAngle().x, this.getY(0.8D),
                        this.getZ() + this.getLookAngle().z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        this.fireBlade(target);
    }

    private void fireBlade(LivingEntity target) {
        Vec3 dir = new Vec3(target.getX() - this.getX(),
                target.getEyeY() - this.getEyeY(), target.getZ() - this.getZ());
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = this.getLookAngle();
        }
        dir = dir.normalize();
        ReaperBladeEntity blade = new ReaperBladeEntity(this.level(), this);
        blade.setPos(this.getX() + dir.x * 0.8D, this.getEyeY() - 0.2D + dir.y * 0.8D,
                this.getZ() + dir.z * 0.8D);
        float speed = BLADE_SPEED * this.soulJarBladeSpeedScale();
        blade.shoot(dir.x, dir.y, dir.z, speed, 0.0F);
        blade.setDamage(BLADE_DAMAGE + this.soulJarDamageBonus());
        blade.setWidthScale(this.soulJarWidthScale());
        this.level().addFreshEntity(blade);
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F,
                0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public void harvestSoul(LivingEntity victim) {
        if (this.level().isClientSide) {
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, victim.getX(), victim.getY() + victim.getBbHeight() * 0.5D,
                    victim.getZ(), 6, 0.3D, 0.4D, 0.3D, 0.01D);
        }
        if (this.getTrueOwner() instanceof Player owner) {
            SEHelper.increaseSouls(owner, SOUL_HARVEST);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            this.harvestSoul(living);
            this.applySoulJarSapped(living);
        }
        return hit;
    }

    private void castSummon() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        int room = MAX_HELD_SERVANTS - this.countOwnedSummons(server);
        if (room <= 0) {
            return;
        }
        int count = Math.min(this.random.nextFloat() < 0.5F ? 3 : 2, room);
        com.Polarice3.Goety.utils.ServerParticleUtil.gatheringParticles(ParticleTypes.SOUL, this, server);
        LivingEntity owner = this.directSummonToMaster && this.getTrueOwner() != null
                ? this.getTrueOwner() : this.getEffectiveOwner();
        for (int i = 0; i < count; i++) {
            Summoned minion = createSummon(server);
            if (minion == null) {
                continue;
            }
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double radius = 2.0D + this.random.nextDouble() * 1.5D;
            double x = this.getX() + Math.cos(angle) * radius;
            double z = this.getZ() + Math.sin(angle) * radius;
            minion.setTrueOwner(owner);
            boolean reaperServant = REAPER_SERVANT_TYPE_ID.equals(
                    ForgeRegistries.ENTITY_TYPES.getKey(minion.getType()));
            double y = reaperServant ? this.getY()
                    : MinionGroundPlacement.groundFeetY(server, x, z, this.getY(),
                            MinionGroundPlacement.SKELETON_CLEARANCE);
            minion.moveTo(x, y, z, this.getYRot(), 0.0F);
            minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS + getSoulJarUpgrades() * 30 * 20);
            minion.setPersistenceRequired();
            minion.getPersistentData().putUUID(SUMMONER_TAG, this.getUUID());
            if (server.addFreshEntity(minion)) {
                com.Polarice3.Goety.utils.ServerParticleUtil.summonUndeadParticles(server, minion);
                server.sendParticles(ParticleTypes.SOUL, x, y + 0.5D, z,
                        20, 0.4D, 0.6D, 0.4D, 0.02D);
            }
        }
        this.summonHaunts(server);
        this.playSound(com.Polarice3.Goety.init.ModSounds.NECROMANCER_SUMMON.get(), 1.2F, 0.7F);
        this.playSound(GeneratedMod.REAPER_AMBIENT_DULL.get(), 1.0F, 0.8F);
        this.summonLaughDelay = SUMMON_LAUGH_DELAY;
    }

    private void summonHaunts(ServerLevel server) {
        if (!this.hauntFocusGiven || !OminousCompat.loaded()) {
            return;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(HAUNT_TYPE_ID);
        if (type == null) {
            return;
        }
        int room = HAUNT_CAP - this.countSummonedHaunts(server);
        if (room <= 0) {
            return;
        }
        LivingEntity owner = this.directSummonToMaster && this.getTrueOwner() != null
                ? this.getTrueOwner() : this.getEffectiveOwner();
        int base = Math.min(HAUNT_BASE_COUNT, room);
        boolean spawned = false;
        for (int i = 0; i < base; ++i) {
            spawned |= this.spawnHaunt(server, type, owner);
        }
        room -= base;
        if (room > 0 && this.random.nextFloat() < HAUNT_BONUS_CHANCE) {
            int bonus = Math.min(1 + this.random.nextInt(2), room);
            for (int i = 0; i < bonus; ++i) {
                spawned |= this.spawnHaunt(server, type, owner);
            }
        }
    }

    private boolean spawnHaunt(ServerLevel server, EntityType<?> type, LivingEntity owner) {
        Entity entity = type.create(server);
        if (!(entity instanceof Mob mob) || !(entity instanceof IOwned owned)) {
            if (entity != null) {
                entity.discard();
            }
            return false;
        }
        BlockPos pos = BlockFinder.SummonRadius(this.blockPosition(), mob, server);
        if (pos == null) {
            pos = this.blockPosition();
        }
        owned.setTrueOwner(owner);
        mob.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, this.getYRot(), 0.0F);
        MobUtil.moveDownToGround(mob);
        owned.setLimitedLife(MobUtil.getSummonLifespan(server));
        mob.finalizeSpawn(server, server.getCurrentDifficultyAt(mob.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        mob.getPersistentData().putUUID(HAUNT_SUMMONER_TAG, this.getUUID());
        ExtraSummonWindup.Kind kind = ExtraSummonWindup.summon(server, mob, mob.position(), this);
        if (kind == ExtraSummonWindup.Kind.FAILED) {
            return false;
        }
        ServerParticleUtil.summonUndeadParticles(server, mob);
        server.sendParticles(ParticleTypes.SOUL, mob.getX(), mob.getY() + 0.5D, mob.getZ(),
                20, 0.4D, 0.6D, 0.4D, 0.02D);
        return true;
    }

    private int countSummonedHaunts(ServerLevel server) {
        AABB area = this.getBoundingBox().inflate(48.0D);
        return server.getEntitiesOfClass(Entity.class, area, entity -> {
            if (entity == this) {
                return false;
            }
            CompoundTag data = entity.getPersistentData();
            return data.hasUUID(HAUNT_SUMMONER_TAG)
                    && this.getUUID().equals(data.getUUID(HAUNT_SUMMONER_TAG));
        }).size();
    }

    @Nullable
    private Summoned createSummon(ServerLevel level) {
        EntityType<?> type = this.pickSummonType();
        if (type == null) {
            type = ForgeRegistries.ENTITY_TYPES.getValue(REAPER_SERVANT_TYPE_ID);
        }
        if (type == null) {
            return null;
        }
        Entity entity = type.create(level);
        return entity instanceof Summoned summoned ? summoned : null;
    }

    @Nullable
    private EntityType<?> pickSummonType() {
        EntityType<?> chosen = null;
        for (int i = 0; i < FOCUS_IDS.length; i++) {
            if (!this.focusUnlocked[i] || this.random.nextFloat() >= FOCUS_CHANCE[i]) {
                continue;
            }
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(FOCUS_SERVANT_IDS[i]);
            if (type != null) {
                chosen = type;
            }
        }
        return chosen;
    }

    public LivingEntity getEffectiveOwner() {
        return this;
    }

    private void castSpin() {
        LivingEntity target = this.getTarget();
        Vec3 aim = target != null
                ? new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5D, target.getZ())
                : this.position().add(this.getLookAngle().scale(4.0D));
        Vec3 dir = aim.subtract(this.getEyePosition());
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = this.getLookAngle();
        }
        dir = dir.normalize();
        ReaperSpinEntity spin = new ReaperSpinEntity(this.level(), this);
        spin.setPos(this.getX() + dir.x * 1.2D, this.getEyeY() - 0.2D + dir.y * 1.2D,
                this.getZ() + dir.z * 1.2D);
        double spinSpeed = SPIN_SPEED;
        spin.setSpeed(spinSpeed);
        spin.setDeltaMovement(dir.scale(spinSpeed));
        spin.setDamage(SPIN_DAMAGE + this.soulJarDamageBonus());
        spin.setWidthScale(this.soulJarWidthScale());
        this.level().addFreshEntity(spin);
        this.playSound(SoundEvents.WITHER_SHOOT, 1.0F, 0.8F);
    }

    private void castPunish() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.5F, 0.85F);
        AABB area = this.getBoundingBox().inflate(PUNISH_RADIUS);
        List<LivingEntity> candidates = server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != this && e.isAlive());
        List<LivingEntity> enemies = new ArrayList<>();
        List<LivingEntity> allies = new ArrayList<>();
        for (LivingEntity e : candidates) {
            if (this.isMyAlly(e)) {
                allies.add(e);
            } else if (!(e instanceof AbstractReaperNecromancer)) {
                enemies.add(e);
            }
        }
        enemies.sort(Comparator.comparingDouble(LivingEntity::getMaxHealth).reversed());
        allies.sort(Comparator.comparingDouble(e -> e.getHealth() / Math.max(1.0F, e.getMaxHealth())));
        this.punishEnemies.clear();
        this.punishAllies.clear();
        for (int i = 0; i < Math.min(2, enemies.size()); i++) {
            this.punishEnemies.add(enemies.get(i).getUUID());
        }
        for (int i = 0; i < Math.min(2, allies.size()); i++) {
            this.punishAllies.add(allies.get(i).getUUID());
        }
        if (this.punishEnemies.isEmpty()) {
            return;
        }
        this.punishTicks = PUNISH_TICKS;
        this.punishNextTick = 0;
    }

    private void tickPunish() {
        if (this.punishTicks <= 0) {
            return;
        }
        --this.punishTicks;
        if (this.punishTicks <= 0) {
            this.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.2F, 1.4F);
        }
        this.drawPunishLinks();
        if (this.punishNextTick > 0) {
            --this.punishNextTick;
            return;
        }
        this.punishNextTick = PUNISH_INTERVAL;
        this.applyPunishDamage();
        if (this.punishTicks <= 0) {
            this.punishEnemies.clear();
            this.punishAllies.clear();
        }
    }

    private void drawPunishLinks() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        for (UUID id : this.punishEnemies) {
            Entity target = server.getEntity(id);
            if (target != null) {
                this.drawLine(server, this, target, ParticleTypes.SOUL_FIRE_FLAME);
            }
        }
        for (UUID id : this.punishAllies) {
            Entity target = server.getEntity(id);
            if (target != null) {
                this.drawLine(server, this, target, ParticleTypes.SOUL);
            }
        }
    }

    private void drawLine(ServerLevel server, Entity from, Entity to, net.minecraft.core.particles.SimpleParticleType particle) {
        Vec3 a = new Vec3(from.getX(), from.getY() + from.getBbHeight() * 0.6D, from.getZ());
        Vec3 b = new Vec3(to.getX(), to.getY() + to.getBbHeight() * 0.6D, to.getZ());
        double distance = a.distanceTo(b);
        int steps = Mth.clamp((int) (distance * 2.0D), 2, 48);
        for (int i = 0; i <= steps; i++) {
            Vec3 p = a.lerp(b, i / (double) steps);
            server.sendParticles(particle, p.x, p.y, p.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void applyPunishDamage() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        float totalDamage = 0.0F;
        LivingEntity indirect = this.getTrueOwner() != null ? this.getTrueOwner() : this;
        for (UUID id : this.punishEnemies) {
            if (server.getEntity(id) instanceof LivingEntity victim && victim.isAlive()) {
                float damage = 5.0F + victim.getMaxHealth() * 0.02F + this.soulJarDamageBonus();
                if (victim.hurt(this.damageSources().indirectMagic(this, indirect), damage)) {
                    totalDamage += damage;
                    this.applySoulJarSapped(victim);
                }
            }
        }
        if (totalDamage <= 0.0F) {
            return;
        }
        List<LivingEntity> livingAllies = new ArrayList<>();
        for (UUID id : this.punishAllies) {
            if (server.getEntity(id) instanceof LivingEntity ally && ally.isAlive()) {
                livingAllies.add(ally);
            }
        }
        if (livingAllies.isEmpty()) {
            return;
        }
        float heal = totalDamage * 0.5F / livingAllies.size();
        for (LivingEntity ally : livingAllies) {
            ally.heal(heal);
            server.sendParticles(ParticleTypes.HEART, ally.getX(), ally.getY() + ally.getBbHeight() * 0.8D,
                    ally.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
        }
    }

    private void castSnap() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.playSound(GeneratedMod.REAPER_SNAP.get(), 4.0F, 1.0F);
        AABB area = this.getBoundingBox().inflate(48.0D);
        List<Summoned> minions = server.getEntitiesOfClass(
                Summoned.class, area, this::isOwnedReaperServant);
        Summoned best = this.pickSnapSacrifice(minions);
        if (best != null) {
            double bx = best.getX();
            double by = best.getY() + best.getBbHeight() * 0.5D;
            double bz = best.getZ();
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, bx, by, bz,
                    18, 0.4D, 0.6D, 0.4D, 0.02D);
            best.discard();
            ShockwaveFocus.cast(server, this, bx, by, bz, SNAP_SHOCKWAVE_RADIUS, SNAP_SHOCKWAVE_POTENCY);
            this.applySoulJarSappedAround(server, bx, by, bz, SNAP_SHOCKWAVE_RADIUS + 1.0D);
            this.heal(best.getMaxHealth() * 0.5F);
        }
        for (ReaperSpinEntity spin : server.getEntitiesOfClass(ReaperSpinEntity.class, area)) {
            if (spin.isOwnedBy(this)) {
                spin.detonate();
            }
        }
    }

    private Summoned pickSnapSacrifice(List<Summoned> minions) {
        LivingEntity target = this.getTarget();
        Summoned closest = null;
        double closestDistanceSqr = Double.MAX_VALUE;
        if (target != null && target.isAlive()) {
            for (Summoned minion : minions) {
                double distanceSqr = minion.distanceToSqr(target);
                if (distanceSqr > SNAP_SHOCKWAVE_RADIUS * SNAP_SHOCKWAVE_RADIUS) {
                    continue;
                }
                if (closest == null || distanceSqr < closestDistanceSqr
                        || distanceSqr == closestDistanceSqr && minion.getHealth() > closest.getHealth()) {
                    closest = minion;
                    closestDistanceSqr = distanceSqr;
                }
            }
        }
        if (closest != null) {
            return closest;
        }
        Summoned healthiest = null;
        for (Summoned minion : minions) {
            if (healthiest == null || minion.getHealth() > healthiest.getHealth()) {
                healthiest = minion;
            }
        }
        return healthiest;
    }

    public boolean isOwnedReaperServant(Summoned minion) {
        return minion != this
                && REAPER_SERVANT_TYPE_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(minion.getType()))
                && (minion.getTrueOwner() == this.getEffectiveOwner() || this.isSummonedByMe(minion));
    }

    private int ownedReaperServantCount() {
        if (!(this.level() instanceof ServerLevel server)) {
            return 0;
        }
        AABB area = this.getBoundingBox().inflate(48.0D);
        return server.getEntitiesOfClass(Summoned.class, area, this::isOwnedReaperServant).size();
    }

    private int countOwnedSummons(ServerLevel server) {
        AABB area = this.getBoundingBox().inflate(48.0D);
        return server.getEntitiesOfClass(Summoned.class, area, this::isSummonedByMe).size();
    }

    private boolean isSummonedByMe(Summoned minion) {
        if (minion == this) {
            return false;
        }
        CompoundTag data = minion.getPersistentData();
        return data.hasUUID(SUMMONER_TAG) && this.getUUID().equals(data.getUUID(SUMMONER_TAG));
    }

    private boolean hasMinionsOrSpins() {
        if (!(this.level() instanceof ServerLevel server)) {
            return false;
        }
        AABB area = this.getBoundingBox().inflate(48.0D);
        if (!server.getEntitiesOfClass(Summoned.class, area, this::isOwnedReaperServant).isEmpty()) {
            return true;
        }
        for (ReaperSpinEntity spin : server.getEntitiesOfClass(ReaperSpinEntity.class, area)) {
            if (spin.isOwnedBy(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!ExecutionDamage.isExecution(source, amount)) {
            return super.hurt(source, amount);
        }
        this.invulnerableTime = 0;
        boolean dead = super.hurt(source, Float.MAX_VALUE);
        if (!dead && !this.isRemoved() && !this.isDeadOrDying()) {
            this.setHealth(0.0F);
            this.die(source);
            return true;
        }
        return dead;
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

    private void tickSunlight() {
        if (!this.inDirectSunlight() || this.sunlightProtected()) {
            return;
        }
        if (this.tickCount % 20 == 0) {
            this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false));
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SMOKE, this.getX(),
                        this.getY() + this.getBbHeight() * 0.8D, this.getZ(), 3, 0.3D, 0.2D, 0.3D, 0.01D);
            }
        }
    }

    private boolean inDirectSunlight() {
        if (!this.level().isDay()) {
            return false;
        }
        if (!this.level().canSeeSky(this.blockPosition())) {
            return false;
        }
        return !this.level().isRainingAt(this.blockPosition());
    }

    private boolean sunlightProtected() {
        return this.getTrueOwner() instanceof Player owner
                && (CuriosFinder.hasNecroSet(owner) || CuriosFinder.hasNamelessSet(owner));
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }

    public boolean isMyAlly(LivingEntity other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Summoned summoned && summoned.getTrueOwner() == this) {
            return true;
        }
        LivingEntity owner = this.getTrueOwner();
        if (owner != null) {
            if (other == owner) {
                return true;
            }
            return other instanceof Summoned summoned && summoned.getTrueOwner() == owner;
        }
        return other instanceof AbstractReaperNecromancer;
    }

    private void playMergedLaugh() {
        SoundEvent necroLaugh = com.Polarice3.Goety.init.ModSounds.NECROMANCER_LAUGH.get();
        if (necroLaugh != null) {
            this.playSound(necroLaugh, LAUGH_NECRO_VOLUME, LAUGH_NECRO_PITCH);
        }
        SoundEvent reaperLaugh = GeneratedMod.REAPER_AMBIENT_DULL.get();
        if (reaperLaugh != null) {
            this.playSound(reaperLaugh, LAUGH_REAPER_VOLUME, LAUGH_REAPER_PITCH);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.REAPER_AMBIENT_DULL.get();
    }

    @Override
    public SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return GeneratedMod.REAPER_HURT_DULL.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.REAPER_DEATH_DULL.get();
    }

    @Override
    public void playStepSound(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 240;
    }
}
