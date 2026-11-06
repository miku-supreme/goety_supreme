package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.ItemHelper;
import com.Polarice3.Goety.utils.WandUtil;
import java.util.EnumSet;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
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
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorMaterial;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorSetBonus;
import cn.blockforge.generated.goetysupreme.entity.PrismarineArrowEntity;
import cn.blockforge.generated.goetysupreme.entity.PrismarineBladeSlashEntity;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineBladeSlashRenderer;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.model.PrismarineSoldierModel;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineSoldierRenderer;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;

public class PrismarineSoldier extends Summoned implements RangedAttackMob {

    private static final float MELEE_CHANCE = 0.5F;

    private static final float SWORD_CHANCE = 0.6F;

    private static final float DUAL_WIELD_CHANCE = 0.8F;

    static final float BOW_ATTACK_RADIUS = 24.0F;

    public static final int BOW_DRAW_TICKS = 20;

    static final int BOW_GAP_TICKS = 20;

    private static final int BOW_SHOT_TICKS = 15;

    static final int THUNDER_TICKS = 70;

    private static final double THUNDER_HOVER_HEIGHT = 6.0D;

    static final double THUNDER_JUMP_VELOCITY = 1.05D;

    static final int THUNDER_COOLDOWN_TICKS = 200;

    private static final float HEAVY_CHANCE = 0.3F;

    private static final float HEAVY_CHANCE_ARMED = 0.5F;

    private static final int SINGLE_TICKS = 30;

    private static final int HEAVY_TICKS = 35;

    private static final int HEAVY_COOLDOWN = 300;

    private static final int HEAVY_COOLDOWN_ARMED = 200;

    private static final float HEAVY_HIT_COMBINED_MULT = 1.5F;

    private static final float HEAVY_SLASH_COMBINED_MULT = 1.0F;

    private static final int MELEE_ANIMATION_GAP = 15;

    private static final byte ARROW_PIERCE_LEVEL = 4;

    private static final int ARROW_KNOCKBACK = 3;

    private static final int EXPLOSIVE_EVERY = 3;

    private static final double ARROW_SPEED = 3.75D;

    private static final double BLADE_SLASH_SPEED = 1.1D;

    public static final int ATTACK_MELEE = 0;
    public static final int ATTACK_BOW = 1;
    public static final int ATTACK_THUNDER = 2;
    public static final int ATTACK_SINGLE = 3;
    public static final int ATTACK_HEAVY = 4;

    public static final int ATTACK_BLOCK = 5;

    private static final EntityDataAccessor<Integer> DATA_ATTACK_ANIMATION =
            SynchedEntityData.defineId(PrismarineSoldier.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICKS =
            SynchedEntityData.defineId(PrismarineSoldier.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(PrismarineSoldier.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_EMPOWERED =
            SynchedEntityData.defineId(PrismarineSoldier.class, EntityDataSerializers.BOOLEAN);

    private int attackAnimation;
    private int attackAnimationTicks;
    private int attackAnimationTick;

    private static final int MELEE_TICKS = 35;

    private static final int MELEE_MAIN_HIT_TICK = 11;

    private static final int MELEE_OFF_HIT_TICK = 18;

    private static final int SINGLE_HIT_TICK = 11;

    private static final int HEAVY_HIT_TICK = 13;

    @Nullable
    private LivingEntity pendingMainHandTarget;

    @Nullable
    private LivingEntity pendingOffHandTarget;

    @Nullable
    private LivingEntity pendingSingleTarget;

    @Nullable
    private LivingEntity pendingHeavyTarget;

    private float dualSwingTurnYaw;

    private static final double SWING_ARC_MAIN_HAND = 2.6D;
    private static final double SWING_ARC_OFF_HAND = 3.34D;

    private static final double SWING_AOE_BASE = 0.6D;
    private static final double SWING_AOE_PER_BLOCK = 0.28D;
    private static final double SWING_AOE_MIN = 1.2D;
    private static final double SWING_AOE_MAX = 2.0D;

    private static final float SWING_AOE_DAMAGE_FACTOR = 0.5F;

    private static final double SWING_AOE_KNOCKBACK = 0.45D;

    private static final float SWING_SIDE_DEGREES = 38.0F;

    private static final float DUAL_SWING_TURN_DEGREES = 90.0F;

    private static final double DUAL_SWING_TURN_RADIUS = 3.0D;

    private static final double HEAVY_AOE_RADIUS = 3.0D;

    private static final double HEAVY_AOE_DOT = 0.5D;

    private static final float HEAVY_AOE_DAMAGE_FACTOR = 0.75F;

    private static final double HEAVY_AOE_KNOCKBACK = 0.6D;

    private static final float HEAVY_SLASH_PITCH_SCALE = 0.8F;

    private static final int AURA_BREATH_PERIOD = 40;

    public static float auraPulse(float ageInTicks) {
        float phase = (ageInTicks % AURA_BREATH_PERIOD) / (float) AURA_BREATH_PERIOD;
        return 0.5F - 0.5F * Mth.cos(phase * (float) (Math.PI * 2.0D));
    }

    private static final int CHARGED_REFRESH_INTERVAL = 5;

    private static final double CHAIN_LIGHTNING_RANGE = 6.0D;

    private static final float CHAIN_LIGHTNING_STORM_MULT = 1.0F;
    private static final float CHAIN_LIGHTNING_CALM_MULT = 0.5F;

    private static final float MELEE_SWEEP_PITCH = 0.55F;

    private static final float MELEE_SWEEP_PITCH_VARIANCE = 0.1F;

    private static final float MELEE_SWEEP_VOLUME = 0.85F;

    private double thunderAnchorY;

    private boolean thunderHovering;

    private int heavyCooldown;

    private static final float BLOCK_TRIGGER_DAMAGE = 15.0F;

    private static final int BLOCK_TRIGGER_HIT_STREAK = 5;

    private static final int BLOCK_HIT_STREAK_WINDOW = 60;

    private static final int BLOCK_TICKS = 80;

    private static final int BLOCK_COOLDOWN_TICKS = 200;

    private static final float BLOCK_DAMAGE_REDUCTION = 0.85F;

    private static final double BLOCK_SLOWDOWN_AMOUNT = -0.5D;

    private static final UUID BLOCK_SLOWDOWN_ID = UUID.fromString("8d1f2e64-5b3a-4c77-9f0e-2a6c4d9b7a13");

    private static final String BLOCK_SLOWDOWN_NAME = "goety_supreme:block_slowdown";

    private static final TagKey<DamageType> GOETY_SHOCK_ATTACKS = TagKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "shock_attacks"));

    private static final float ELEMENT_DAMAGE_RESISTANCE = 0.5F;

    private static final float EMPOWERED_MAGIC_CUT = 0.25F;

    public static boolean isLightningDamage(DamageSource source) {
        return source.is(DamageTypeTags.IS_LIGHTNING) || source.is(GOETY_SHOCK_ATTACKS);
    }

    private int hitStreak;

    private int sinceLastHit = BLOCK_HIT_STREAK_WINDOW;

    private long lastBlockStartTick = Long.MIN_VALUE;

    private boolean canBlock() {
        return !this.holdsBow();
    }

    public boolean isBlockActive() {
        return this.canBlock()
                && this.isAttackAnimating() && this.getAttackAnimation() == ATTACK_BLOCK;
    }

    private boolean tryBlock() {
        if (!this.canBlock() || !this.blockCooldownReady()) {
            return false;
        }
        this.lastBlockStartTick = this.level().getGameTime();
        this.hitStreak = 0;
        this.startAttackAnimation(ATTACK_BLOCK, BLOCK_TICKS);
        this.syncBlockSlowdown();
        return true;
    }

    private boolean blockCooldownReady() {
        return this.lastBlockStartTick == Long.MIN_VALUE
                || this.level().getGameTime() - this.lastBlockStartTick >= BLOCK_COOLDOWN_TICKS;
    }

    private void syncBlockSlowdown() {
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }
        boolean blocking = this.isBlockActive();
        boolean applied = speed.getModifier(BLOCK_SLOWDOWN_ID) != null;
        if (blocking && !applied) {
            speed.addTransientModifier(new AttributeModifier(BLOCK_SLOWDOWN_ID, BLOCK_SLOWDOWN_NAME,
                    BLOCK_SLOWDOWN_AMOUNT, AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (!blocking && applied) {
            speed.removeModifier(BLOCK_SLOWDOWN_ID);
        }
    }

    private static boolean isPhysicalDamage(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_ARMOR)) {
            return false;
        }
        return !source.is(DamageTypeTags.WITCH_RESISTANT_TO)
                && !source.is(DamageTypeTags.IS_FIRE)
                && !source.is(DamageTypeTags.IS_FREEZING)
                && !source.is(DamageTypeTags.IS_DROWNING)
                && !source.is(DamageTypeTags.IS_LIGHTNING)
                && !source.is(GOETY_SHOCK_ATTACKS);
    }

    private int meleeCooldown;

    private int bowGapCooldown;

    private int thunderCooldown;

    public int getBowGapCooldown() {
        return this.bowGapCooldown;
    }

    public void setBowGapCooldown(int ticks) {
        this.bowGapCooldown = ticks;
    }

    public int getThunderCooldown() {
        return this.thunderCooldown;
    }

    public void setThunderCooldown(int ticks) {
        this.thunderCooldown = ticks;
    }

    private int arrowShotCount;

    private boolean hostile;

    public PrismarineSoldier(EntityType<? extends PrismarineSoldier> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        this.xpReward = 5;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0.0F);
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK_ANIMATION, -1);
        this.entityData.define(DATA_ATTACK_TICKS, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_EMPOWERED, false);
    }

    public boolean isLightningEmpowered() {
        return Boolean.TRUE.equals(this.entityData.get(DATA_EMPOWERED));
    }

    private void setLightningEmpowered(boolean empowered) {
        if (this.isLightningEmpowered() != empowered) {
            this.entityData.set(DATA_EMPOWERED, empowered);
        }
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt bolt) {
        super.thunderHit(level, bolt);
        this.setLightningEmpowered(true);
    }

    private static final double BODY_KNOCKBACK_RESISTANCE = 0.6D;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.PRISMARINE_SOLDIER_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.PRISMARINE_SOLDIER_ATTACK.get())
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, BODY_KNOCKBACK_RESISTANCE)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), SupremeMobsConfig.PRISMARINE_SOLDIER_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), SupremeMobsConfig.PRISMARINE_SOLDIER_ATTACK.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), 6.0D);
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), 0.0D);
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE), BODY_KNOCKBACK_RESISTANCE);
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), 0.3D);
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), 32.0D);
    }

    private static void setBaseAttribute(AttributeInstance attribute, double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner()) {
            float heal = prismarineHealAmount(held);
            if (heal > 0.0F && ServantFeedHeal.feed(this, player, held, heal)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
            InteractionResult swapped = this.replaceGear(player, held);
            if (swapped != null) {
                return swapped;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    private InteractionResult replaceGear(Player player, ItemStack held) {
        if (held.isEmpty() || !isPrismarineEquipment(held)) {
            return null;
        }
        EquipmentSlot slot = player.isShiftKeyDown() ? EquipmentSlot.OFFHAND : prismarineSlotFor(held);
        if (slot == EquipmentSlot.OFFHAND && this.holdsBow()) {
            return null;
        }
        if (!level().isClientSide) {
            ItemStack previous = this.getItemBySlot(slot).copy();
            float previousDropChance = this.getEquipmentDropChance(slot);
            boolean clearsOffhand = slot == EquipmentSlot.MAINHAND && held.getItem() instanceof BowItem;
            ItemStack previousOffhand = clearsOffhand
                    ? this.getItemBySlot(EquipmentSlot.OFFHAND).copy() : ItemStack.EMPTY;
            float previousOffhandDropChance = clearsOffhand
                    ? this.getEquipmentDropChance(EquipmentSlot.OFFHAND) : 0.0F;
            this.setItemSlot(slot, held.copyWithCount(1));
            this.setGuaranteedDrop(slot);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            RingServantGear.returnReplaced(player, previous, previousDropChance);
            RingServantGear.returnReplaced(player, previousOffhand, previousOffhandDropChance);
            Equipable equipable = Equipable.get(held);
            playSound(equipable == null ? SoundEvents.ARMOR_EQUIP_GENERIC : equipable.getEquipSound(),
                    1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private static boolean isPrismarineEquipment(ItemStack stack) {
        return stack.is(GeneratedMod.PRISMARINE_SWORD.get())
                || stack.is(GeneratedMod.PRISMARINE_AXE.get())
                || stack.is(GeneratedMod.PRISMARINE_BOW.get())
                || stack.is(GeneratedMod.PRISMARINE_HELMET.get())
                || stack.is(GeneratedMod.PRISMARINE_CHESTPLATE.get())
                || stack.is(GeneratedMod.PRISMARINE_LEGGINGS.get())
                || stack.is(GeneratedMod.PRISMARINE_BOOTS.get());
    }

    private static EquipmentSlot prismarineSlotFor(ItemStack stack) {
        Equipable equipable = Equipable.get(stack);
        if (equipable != null && equipable.getEquipmentSlot().getType() == EquipmentSlot.Type.ARMOR) {
            return equipable.getEquipmentSlot();
        }
        return EquipmentSlot.MAINHAND;
    }

    private static float prismarineHealAmount(ItemStack held) {
        if (held.is(Items.PRISMARINE_SHARD)) {
            return 3.0F;
        }
        if (held.is(net.minecraft.world.level.block.Blocks.PRISMARINE.asItem())) {
            return 15.0F;
        }
        if (held.is(net.minecraft.world.level.block.Blocks.PRISMARINE_BRICKS.asItem())) {
            return 35.0F;
        }
        return 0.0F;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ThunderBowGoal(this));
        this.goalSelector.addGoal(2, new WeaponMeleeGoal(this, 1.05D, true));
        this.goalSelector.addGoal(3, new PrismarineBowGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.followGoal();
        this.targetRetaliateGoal();
        this.ownerHurtGoals();
        this.targetSelector.addGoal(2, new SummonTargetGoal(this, true, true));
    }

    public boolean holdsBow() {
        return this.getMainHandItem().getItem() instanceof BowItem;
    }

    public void startAttackAnimation(int animation, int ticks) {
        if (this.level().isClientSide) {
            return;
        }
        this.pendingMainHandTarget = null;
        this.pendingOffHandTarget = null;
        this.pendingSingleTarget = null;
        this.pendingHeavyTarget = null;
        this.attackAnimation = animation;
        this.attackAnimationTicks = ticks;
        this.attackAnimationTick = ticks;
        this.entityData.set(DATA_ATTACK_ANIMATION, animation);
        this.entityData.set(DATA_ATTACK_TICKS, ticks);
        this.entityData.set(DATA_ATTACK_TICK, ticks);
    }

    public void stopAttackAnimation() {
        if (this.level().isClientSide) {
            return;
        }
        this.attackAnimationTick = 0;
        this.attackAnimationTicks = 0;
        this.entityData.set(DATA_ATTACK_TICK, 0);
        this.syncBlockSlowdown();
    }

    public boolean isAttackAnimating() {
        return this.entityData.get(DATA_ATTACK_TICK) > 0;
    }

    public int getAttackAnimation() {
        return this.entityData.get(DATA_ATTACK_ANIMATION);
    }

    public int getAttackAnimationTicks() {
        return this.entityData.get(DATA_ATTACK_TICKS);
    }

    public int getAttackAnimationTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    @Override
    public void tick() {
        super.tick();
        ServantTeleportHelper.tick(this);
        if (this.heavyCooldown > 0) {
            this.heavyCooldown--;
        }
        if (!this.level().isClientSide && this.attackAnimationTick > 0) {
            int before = this.attackAnimationTick;
            this.attackAnimationTick--;
            this.tickAttackAnimation();
            this.entityData.set(DATA_ATTACK_TICK, this.attackAnimationTick);
            if (before > 0 && this.attackAnimationTick == 0) {
                this.meleeCooldown = MELEE_ANIMATION_GAP;
            }
        }
        if (this.meleeCooldown > 0) {
            this.meleeCooldown--;
        }
        if (!this.level().isClientSide) {
            this.tickThunderHover();
            this.tickThunderChargeParticles();
            this.tickLightningEmpowerment();
            if (this.holdsBow() && this.isAttackAnimating() && this.getAttackAnimation() == ATTACK_BLOCK) {
                this.stopAttackAnimation();
            }
            if (this.sinceLastHit < BLOCK_HIT_STREAK_WINDOW) {
                this.sinceLastHit++;
                if (this.sinceLastHit >= BLOCK_HIT_STREAK_WINDOW) {
                    this.hitStreak = 0;
                }
            }
            if (this.bowGapCooldown > 0) {
                this.bowGapCooldown--;
            }
            if (this.thunderCooldown > 0) {
                this.thunderCooldown--;
            }
            this.syncBlockSlowdown();
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (this.getTrueOwner() == null) {
            return;
        }
        OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.PRISMARINE_CORE.get()));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_DROWNING) || source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        if (this.level().isClientSide) {
            return super.hurt(source, amount);
        }
        if (this.isLightningEmpowered() && isLightningDamage(source)) {
            return false;
        }
        if (this.isLightningEmpowered() && source.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }
        float adjusted;
        if (isLightningDamage(source)) {
            adjusted = amount * (1.0F - ELEMENT_DAMAGE_RESISTANCE);
        } else if (source.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            adjusted = amount * (1.0F - ELEMENT_DAMAGE_RESISTANCE)
                    * (this.isLightningEmpowered() ? (1.0F - EMPOWERED_MAGIC_CUT) : 1.0F);
        } else {
            adjusted = amount;
        }
        if (this.isBlockActive()) {
            float applied = isPhysicalDamage(source) ? adjusted * (1.0F - BLOCK_DAMAGE_REDUCTION) : adjusted;
            boolean blocked = super.hurt(source, applied);
            if (blocked) {
                this.playBlockClang();
            }
            return blocked;
        }
        boolean triggersBlock = this.canBlock() && isPhysicalDamage(source)
                && (amount > BLOCK_TRIGGER_DAMAGE
                        || this.hitStreak + 1 >= BLOCK_TRIGGER_HIT_STREAK);
        if (triggersBlock && this.tryBlock()) {
            float applied = adjusted * (1.0F - BLOCK_DAMAGE_REDUCTION);
            boolean blocked = super.hurt(source, applied);
            if (blocked) {
                this.sinceLastHit = 0;
                this.playBlockClang();
            }
            return blocked;
        }
        boolean damaged = super.hurt(source, adjusted);
        if (damaged && this.isAlive() && this.canBlock() && isPhysicalDamage(source)) {
            this.sinceLastHit = 0;
            this.hitStreak++;
        }
        return damaged;
    }

    private void playBlockClang() {
        float pitch = 0.85F + this.random.nextFloat() * 0.3F;
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.HOSTILE, 0.8F, pitch);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ANVIL_LAND, SoundSource.HOSTILE, 0.25F, pitch + 0.35F);
    }

    private void tickThunderChargeParticles() {
        if (this.attackAnimation != ATTACK_THUNDER || this.attackAnimationTick <= 0) {
            return;
        }
        if (this.attackAnimationTick <= 34 || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int period = this.attackAnimationTick > 50 ? 3 : 2;
        if (this.tickCount % period != 0) {
            return;
        }
        double angle = this.random.nextDouble() * Math.PI * 2.0D;
        double radius = 0.55D + this.random.nextDouble() * 0.55D;
        double px = this.getX() + Math.cos(angle) * radius;
        double pz = this.getZ() + Math.sin(angle) * radius;
        double py = this.getY() + 0.15D + this.random.nextDouble() * this.getBbHeight() * 0.95D;
        serverLevel.sendParticles(ModParticleTypes.ELECTRIC.get(), px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void tickThunderHover() {
        if (this.attackAnimation == ATTACK_THUNDER && this.attackAnimationTick > 0) {
            double gain = this.getY() - this.thunderAnchorY;
            if (!this.thunderHovering
                    && (gain >= THUNDER_HOVER_HEIGHT || this.getDeltaMovement().y() <= 0.02D)) {
                this.thunderHovering = true;
                this.strikeHoverLightning();
            }
            if (this.thunderHovering) {
                this.setNoGravity(true);
                this.setDeltaMovement(this.getDeltaMovement().x() * 0.8D, 0.0D,
                        this.getDeltaMovement().z() * 0.8D);
                this.resetFallDistance();
            }
            return;
        }
        if (this.thunderHovering) {
            this.thunderHovering = false;
            this.setNoGravity(false);
            this.setDeltaMovement(this.getDeltaMovement().x(), -0.08D, this.getDeltaMovement().z());
        }
    }

    private void strikeHoverLightning() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
        if (bolt == null) {
            return;
        }
        bolt.moveTo(this.getX(), this.getY(), this.getZ());
        bolt.setVisualOnly(true);
        serverLevel.addFreshEntity(bolt);
    }

    public void startThunderJump() {
        this.thunderAnchorY = this.getY();
        this.thunderHovering = false;
        this.setNoGravity(false);
    }

    public void cancelThunderHover() {
        this.thunderHovering = false;
        this.setNoGravity(false);
    }

    private void tickAttackAnimation() {
        if (this.attackAnimation == ATTACK_THUNDER) {
            if (this.attackAnimationTick == 34 || this.attackAnimationTick == 28) {
                this.fireThunderArrow(0.0D, false);
            } else if (this.attackAnimationTick == 22) {
                this.fireThunderArrow(0.0D, true);
            }
        } else if (this.attackAnimation == ATTACK_MELEE) {
            if (this.attackAnimationTick == MELEE_TICKS - MELEE_MAIN_HIT_TICK) {
                this.applyMainHandHit();
            } else if (this.attackAnimationTick == MELEE_TICKS - MELEE_OFF_HIT_TICK) {
                this.applyOffHandHit();
            }
        } else if (this.attackAnimation == ATTACK_SINGLE) {
            if (this.attackAnimationTick == SINGLE_TICKS - SINGLE_HIT_TICK) {
                this.applySingleHandHit();
            }
        } else if (this.attackAnimation == ATTACK_HEAVY) {
            if (this.attackAnimationTick == HEAVY_TICKS - HEAVY_HIT_TICK) {
                this.applyHeavyHit();
            }
        }
    }

    private void tickLightningEmpowerment() {
        if (!this.isLightningEmpowered()) {
            return;
        }
        if (this.tickCount % CHARGED_REFRESH_INTERVAL == 0 && !this.hasEffect(GoetyEffects.CHARGED.get())) {
            this.addEffect(new MobEffectInstance(GoetyEffects.CHARGED.get(), -1, 0, false, false, false));
        }
    }

    private void chainLightningOnHit(LivingEntity victim) {
        if (!this.isLightningEmpowered() || this.level().isClientSide || victim == null) {
            return;
        }
        float multiplier = this.level().isThundering()
                ? CHAIN_LIGHTNING_STORM_MULT : CHAIN_LIGHTNING_CALM_MULT;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * multiplier;
        WandUtil.chainLightning(victim, this, CHAIN_LIGHTNING_RANGE, damage, false);
    }

    private void applyMainHandHit() {
        LivingEntity target = this.pendingMainHandTarget;
        this.pendingMainHandTarget = null;
        boolean turn = this.shouldDualSwingTurn();
        float baseYaw = turn ? this.beginDualSwingTurn(DUAL_SWING_TURN_DEGREES) : 0.0F;
        this.swingAreaAttack(SWING_ARC_MAIN_HAND, target, 1.0D);
        if (turn) {
            this.endDualSwingTurn(baseYaw);
        }
        if (target != null && target.isAlive() && this.canAttack(target)) {
            super.doHurtTarget(target);
            this.chainLightningOnHit(target);
        }
    }

    private void applyOffHandHit() {
        LivingEntity target = this.pendingOffHandTarget;
        this.pendingOffHandTarget = null;
        boolean turn = this.shouldDualSwingTurn();
        float baseYaw = turn ? this.beginDualSwingTurn(-DUAL_SWING_TURN_DEGREES) : 0.0F;
        this.swingAreaAttack(SWING_ARC_OFF_HAND, target, -1.0D);
        if (turn) {
            this.endDualSwingTurn(baseYaw);
        }
        if (target != null && target.isAlive() && this.canAttack(target)) {
            target.invulnerableTime = 0;
            target.hurt(this.damageSources().mobAttack(this), this.offHandAttackDamage());
            this.chainLightningOnHit(target);
        }
    }

    private boolean shouldDualSwingTurn() {
        return this.offhandHasWeapon() && this.facingMultipleEnemies();
    }

    private boolean facingMultipleEnemies() {
        double radiusSqr = DUAL_SWING_TURN_RADIUS * DUAL_SWING_TURN_RADIUS;
        int count = 0;
        for (LivingEntity candidate : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(DUAL_SWING_TURN_RADIUS),
                candidate -> candidate != this && candidate.isAlive()
                        && !this.isAlliedTo(candidate) && this.canAttack(candidate)
                        && candidate.distanceToSqr(this) <= radiusSqr)) {
            if (++count >= 2) {
                return true;
            }
        }
        return false;
    }

    private float beginDualSwingTurn(float turnYaw) {
        float baseYaw = this.yBodyRot;
        this.dualSwingTurnYaw = turnYaw;
        float turned = Mth.wrapDegrees(baseYaw + turnYaw);
        this.yBodyRot = turned;
        this.setYRot(turned);
        this.yHeadRot = turned;
        return baseYaw;
    }

    private void endDualSwingTurn(float baseYaw) {
        this.dualSwingTurnYaw = 0.0F;
        this.yBodyRot = baseYaw;
        this.setYRot(baseYaw);
        this.yHeadRot = baseYaw;
    }

    private void applySingleHandHit() {
        LivingEntity target = this.pendingSingleTarget;
        this.pendingSingleTarget = null;
        this.swingAreaAttack(SWING_ARC_MAIN_HAND, target, 1.0D);
        if (target != null && target.isAlive() && this.canAttack(target)) {
            super.doHurtTarget(target);
            this.chainLightningOnHit(target);
        }
    }

    private void applyHeavyHit() {
        LivingEntity target = this.pendingHeavyTarget;
        this.pendingHeavyTarget = null;
        this.heavyFanAoe(target);
        this.playHeavySlashSound();
        if (target == null || !target.isAlive() || !this.canAttack(target)) {
            return;
        }
        this.releaseBladeSlash(target);
        this.damageMainHand();
        this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
    }

    private void heavyFanAoe(@Nullable LivingEntity primary) {
        if (this.level().isClientSide) {
            return;
        }
        double radius = HEAVY_AOE_RADIUS;
        double radiusSqr = radius * radius;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * HEAVY_AOE_DAMAGE_FACTOR;
        float facing = this.yBodyRot * ((float) Math.PI / 180.0F);
        double fwdX = -Mth.sin(facing);
        double fwdZ = Mth.cos(facing);
        boolean hitAny = false;
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(radius), candidate -> candidate != this && candidate != primary
                        && candidate.isAlive() && !this.isAlliedTo(candidate) && this.canAttack(candidate)
                        && candidate.distanceToSqr(this) <= radiusSqr)) {
            double dx = victim.getX() - this.getX();
            double dz = victim.getZ() - this.getZ();
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len > 1.0E-4D) {
                double dot = (dx * fwdX + dz * fwdZ) / len;
                if (dot < HEAVY_AOE_DOT) {
                    continue;
                }
                victim.knockback(HEAVY_AOE_KNOCKBACK, -dx / len, -dz / len);
            }
            victim.hurt(this.damageSources().mobAttack(this), damage);
            this.chainLightningOnHit(victim);
            hitAny = true;
        }
        if (hitAny && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + fwdX * radius * 0.6D,
                    this.getY() + 1.0D, this.getZ() + fwdZ * radius * 0.6D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void playHeavySlashSound() {
        float pitch = 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F) * HEAVY_SLASH_PITCH_SCALE;
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, pitch);
    }

    private void swingAreaAttack(double arcLength, @Nullable LivingEntity primary, double handSide) {
        if (this.level().isClientSide) {
            return;
        }
        this.playMeleeSweepSound();
        double radius = Mth.clamp(SWING_AOE_BASE + arcLength * SWING_AOE_PER_BLOCK, SWING_AOE_MIN, SWING_AOE_MAX);
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * SWING_AOE_DAMAGE_FACTOR;
        double radiusSqr = radius * radius;
        float facing = (this.yBodyRot + this.dualSwingTurnYaw) * ((float) Math.PI / 180.0F)
                + (float) (handSide * Math.toRadians(SWING_SIDE_DEGREES));
        double fwdX = -Mth.sin(facing);
        double fwdZ = Mth.cos(facing);
        boolean hitAny = false;
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(radius), candidate -> candidate != this && candidate != primary
                        && candidate.isAlive() && !this.isAlliedTo(candidate) && this.canAttack(candidate)
                        && candidate.distanceToSqr(this) <= radiusSqr)) {
            double dx = victim.getX() - this.getX();
            double dz = victim.getZ() - this.getZ();
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len > 1.0E-4D) {
                double dot = (dx * fwdX + dz * fwdZ) / len;
                if (dot < 0.5D) {
                    continue;
                }
                victim.knockback(SWING_AOE_KNOCKBACK, -dx / len, -dz / len);
            }
            victim.hurt(this.damageSources().mobAttack(this), damage);
            hitAny = true;
        }
        if (hitAny && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + fwdX * radius * 0.6D,
                    this.getY() + 1.0D, this.getZ() + fwdZ * radius * 0.6D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void playMeleeSweepSound() {
        float pitch = MELEE_SWEEP_PITCH + this.getRandom().nextFloat() * MELEE_SWEEP_PITCH_VARIANCE;
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, MELEE_SWEEP_VOLUME, pitch);
    }

    private float offHandAttackDamage() {
        double base = this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                - weaponAttackBonus(this.getMainHandItem());
        return (float) Math.max(1.0D, base + weaponAttackBonus(this.getOffhandItem()));
    }

    private static double weaponAttackBonus(ItemStack stack) {
        double bonus = 0.0D;
        for (AttributeModifier modifier : stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE)) {
            bonus += modifier.getAmount();
        }
        return bonus;
    }

    private boolean offhandHasWeapon() {
        return weaponAttackBonus(this.getOffhandItem()) > 0.0D;
    }

    private void damageMainHand() {
        ItemStack hand = this.getMainHandItem();
        if (!hand.isEmpty() && hand.isDamageableItem()) {
            ItemHelper.hurtAndBreak(hand, 1, this);
        }
    }

    private static double weaponPanelDamage(ItemStack stack) {
        return 1.0D + weaponAttackBonus(stack);
    }

    private void fireThunderArrow(double offset, boolean finalStrike) {
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        PrismarineArrowEntity arrow = new PrismarineArrowEntity(this.level(), this, true);
        arrow.setFinalStrike(finalStrike);
        arrow.setIgnoreInvulnerability(true);
        BallisticAim.Solution aim = BallisticAim.solveArrow(arrow.position(), target,
                BallisticAim.ARROW_MIN_SPEED, ARROW_SPEED);
        Vec3 velocity = offset == 0.0D ? aim.velocity() : aim.velocity().yRot((float) offset);
        arrow.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), 0.0F);
        this.playBowRelease();
        this.level().addFreshEntity(arrow);
        this.damageMainHand();
    }

    private void playBowRelease() {
        float pitch = 0.6F + this.getRandom().nextFloat() * 0.12F;
        this.playSound(SoundEvents.ARROW_SHOOT, 1.2F, pitch);
        this.playSound(SoundEvents.CROSSBOW_SHOOT, 0.9F, pitch);
    }

    public void playBowDraw() {
        this.playSound(SoundEvents.CROSSBOW_LOADING_START, 1.0F,
                0.65F + this.getRandom().nextFloat() * 0.1F);
    }

    public void playBowDrawMid() {
        this.playSound(SoundEvents.CROSSBOW_LOADING_MIDDLE, 0.9F,
                0.62F + this.getRandom().nextFloat() * 0.1F);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        this.startAttackAnimation(ATTACK_BOW, BOW_SHOT_TICKS);
        boolean explosive = ++this.arrowShotCount % EXPLOSIVE_EVERY == 0;
        PrismarineArrowEntity arrow = new PrismarineArrowEntity(this.level(), this);
        arrow.setExplosive(explosive);
        arrow.setPierceLevel(explosive ? (byte) 0 : ARROW_PIERCE_LEVEL);
        arrow.setKnockback(ARROW_KNOCKBACK);
        double maxSpeed = Math.max(BallisticAim.ARROW_MIN_SPEED, velocity * ARROW_SPEED);
        BallisticAim.Solution aim = BallisticAim.solveArrow(arrow.position(), target,
                BallisticAim.ARROW_MIN_SPEED, maxSpeed);
        Vec3 shot = aim.velocity();
        arrow.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        this.playBowRelease();
        this.level().addFreshEntity(arrow);
        this.damageMainHand();
    }

    public int getMeleeCooldown() {
        return this.meleeCooldown;
    }

    private static final int MELEE_ANTICIPATION_TICKS = 11;

    private static final double MELEE_ANTICIPATION_MAX = 2.0D;

    double meleeAnticipatedReachSqr(LivingEntity target) {
        double reach = getBbWidth() * 2.0D;
        reach = Math.max(1.0D, Math.sqrt(reach * reach + target.getBbWidth()) - getBbWidth() * 0.5D);
        reach = Math.max(0.6D, Math.min(reach, Math.max(0.7D, getBbHeight() * 0.4D)));
        double closing = closingSpeed(target);
        if (closing > 0.0D) {
            reach += Math.min(closing * MELEE_ANTICIPATION_TICKS, MELEE_ANTICIPATION_MAX);
        }
        return reach * reach;
    }

    private double closingSpeed(LivingEntity target) {
        double dx = getX() - target.getX();
        double dz = getZ() - target.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);
        if (dist < 1.0E-4D) {
            return 0.0D;
        }
        Vec3 theirs = target.getDeltaMovement();
        Vec3 ours = getDeltaMovement();
        return ((theirs.x - ours.x) * dx + (theirs.z - ours.z) * dz) / dist;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean singleHand = this.getOffhandItem().isEmpty();
        boolean offhandArmed = this.offhandHasWeapon();
        float heavyChance = offhandArmed ? HEAVY_CHANCE_ARMED : HEAVY_CHANCE;
        int heavyCooldownTicks = offhandArmed ? HEAVY_COOLDOWN_ARMED : HEAVY_COOLDOWN;
        if (this.heavyCooldown <= 0 && this.random.nextFloat() < heavyChance) {
            this.heavyCooldown = heavyCooldownTicks;
            this.startAttackAnimation(ATTACK_HEAVY, HEAVY_TICKS);
            if (target instanceof LivingEntity livingTarget) {
                this.pendingHeavyTarget = livingTarget;
            }
            return true;
        }
        if (!singleHand && target instanceof LivingEntity livingTarget) {
            this.startAttackAnimation(ATTACK_MELEE, MELEE_TICKS);
            this.pendingMainHandTarget = livingTarget;
            this.pendingOffHandTarget = livingTarget;
            return true;
        }
        if (target instanceof LivingEntity livingTarget) {
            this.startAttackAnimation(ATTACK_SINGLE, SINGLE_TICKS);
            this.pendingSingleTarget = livingTarget;
            return true;
        }
        return super.doHurtTarget(target);
    }

    private void releaseBladeSlash(Entity target) {
        float mainHandDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float slashDamage;
        float hitDamage;
        if (this.offhandHasWeapon()) {
            double offhandPanel = weaponPanelDamage(this.getOffhandItem());
            float combined = (float) (mainHandDamage + offhandPanel);
            hitDamage = combined * HEAVY_HIT_COMBINED_MULT;
            slashDamage = combined * HEAVY_SLASH_COMBINED_MULT;
        } else {
            hitDamage = mainHandDamage * 2.0F;
            slashDamage = mainHandDamage * 2.0F;
        }
        PrismarineBladeSlashEntity slash = new PrismarineBladeSlashEntity(this.level(), this);
        slash.setDamage(slashDamage);
        slash.markPreVictim(target);
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.5D) - this.getEyeY();
        double dz = target.getZ() - this.getZ();
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist > 0.001D) {
            slash.setYRot((float) (Mth.atan2(dx, dz) * (180.0D / Math.PI)));
            slash.setXRot((float) (Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180.0D / Math.PI)));
            slash.setDeltaMovement(dx / dist * BLADE_SLASH_SPEED,
                    dy / dist * BLADE_SLASH_SPEED * 0.2D,
                    dz / dist * BLADE_SLASH_SPEED);
        }
        this.level().addFreshEntity(slash);
        target.hurt(this.damageSources().mobAttack(this), hitDamage);
        if (target instanceof LivingEntity living) {
            this.chainLightningOnHit(living);
        }
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        ItemStack mainhand;
        ItemStack offhand = ItemStack.EMPTY;
        if (random.nextFloat() < MELEE_CHANCE) {
            ItemStack first = meleeWeapon(random);
            mainhand = first;
            if (random.nextFloat() < DUAL_WIELD_CHANCE) {
                ItemStack second = meleeWeapon(random);
                if (second.is(GeneratedMod.PRISMARINE_SWORD.get()) && first.is(GeneratedMod.PRISMARINE_SWORD.get())) {
                    second = new ItemStack(GeneratedMod.PRISMARINE_AXE.get());
                } else if (second.is(GeneratedMod.PRISMARINE_AXE.get()) && first.is(GeneratedMod.PRISMARINE_AXE.get())) {
                    second = new ItemStack(GeneratedMod.PRISMARINE_SWORD.get());
                }
                offhand = second;
            }
        } else {
            mainhand = new ItemStack(GeneratedMod.PRISMARINE_BOW.get());
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, mainhand);
        this.setItemSlot(EquipmentSlot.OFFHAND, offhand);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(GeneratedMod.PRISMARINE_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(GeneratedMod.PRISMARINE_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(GeneratedMod.PRISMARINE_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(GeneratedMod.PRISMARINE_BOOTS.get()));
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND && stack.getItem() instanceof BowItem) {
            super.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        } else if (slot == EquipmentSlot.OFFHAND && !stack.isEmpty() && holdsBow()) {
            stack = ItemStack.EMPTY;
        }
        super.setItemSlot(slot, stack);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (holdsBow() && !this.getOffhandItem().isEmpty()) {
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
        if (tag.contains("LightningEmpowered")) {
            this.setLightningEmpowered(tag.getBoolean("LightningEmpowered"));
        }
        if (tag.contains("LastBlockTick")) {
            this.lastBlockStartTick = tag.getLong("LastBlockTick");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("LightningEmpowered", this.isLightningEmpowered());
        if (this.lastBlockStartTick != Long.MIN_VALUE) {
            tag.putLong("LastBlockTick", this.lastBlockStartTick);
        }
    }

    private static ItemStack meleeWeapon(RandomSource random) {
        return random.nextFloat() < SWORD_CHANCE
                ? new ItemStack(GeneratedMod.PRISMARINE_SWORD.get())
                : new ItemStack(GeneratedMod.PRISMARINE_AXE.get());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        this.setConfigurableAttributes();
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.setHealth(this.getMaxHealth());
        return result;
    }

    @Override
    public void checkHostility() {
        this.hostile = false;
    }

    @Override
    public void setHostile(boolean hostile) {
        this.hostile = false;
    }

    @Override
    public boolean isHostile() {
        return this.hostile;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) {
            return true;
        }
        LivingEntity owner = this.getTrueOwner();
        if (ServantFaction.isSameFaction(this, entity)) {
            return true;
        }
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) {
            return true;
        }
        if (entity instanceof com.Polarice3.Goety.api.entities.IOwned other
                && this.getOwnerId() != null && this.getOwnerId().equals(other.getOwnerId())) {
            return true;
        }
        if (entity instanceof OwnableEntity ownable && owner != null && ownable.getOwner() == owner) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target != null && target != this && !this.isAlliedTo(target) && super.canAttack(target);
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target != null && this.canAttack(target) ? target : null);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.PRISMARINE_SOLDIER_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GeneratedMod.PRISMARINE_SOLDIER_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.PRISMARINE_SOLDIER_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        playSound(GeneratedMod.PRISMARINE_SOLDIER_STEP.get(), 2.0F / 5.0F, 1.0F);
    }

    @Override
    public void playHurtSound(DamageSource source) {
        if (this.isBlockActive()) {
            return;
        }
        super.playHurtSound(source);
    }
}
