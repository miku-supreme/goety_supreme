package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import com.Polarice3.Goety.utils.CuriosFinder;

import java.util.UUID;
import java.util.function.Predicate;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowSerponCombatExecuterGoal;
import cn.blockforge.generated.goetysupreme.ai.JerotesAllyHelpGoal;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.ai.SerponFlyingStrollGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public abstract class AbstractSerpon extends AbstractJvillageInventoryServant implements Merchant,
        com.jerotes.jerotes.entity.Interface.FactionEntity,
        com.jerotes.jerotes.entity.Interface.WizardEntity {

    @Override
    public String getFirstFactionTypeName() {
        return "serpon_combat_team";
    }

    @Override
    public java.util.List<String> getFactionTypeListEvenThoughTame() {
        return new java.util.ArrayList<>(java.util.List.of("serpon_combat_team"));
    }

    private MerchantOffers serponOffers;

    private final java.util.List<Integer> tradeCooldownTicks = new java.util.ArrayList<>();

    private final java.util.List<Long> tradeCooldownEnds = new java.util.ArrayList<>();

    private final java.util.List<Boolean> tradeDone = new java.util.ArrayList<>();

    private final java.util.List<Boolean> tradeOneTime = new java.util.ArrayList<>();
    private Player tradingPlayer;
    private int tradeXp;

    public static final int FLY_RESET = 500;

    public static final int FLY_LAND_THRESHOLD = 50;

    private static final double OWNER_FOLLOW_START = 10.0D;
    private static final double OWNER_FOLLOW_STOP = 3.0D;

    private static final int OWNER_FOLLOW_RECALC_TICKS = 10;

    private static final double OWNER_FOLLOW_SPEED = 1.0D;

    private static final double OWNER_FOLLOW_STANDOFF = 2.5D;

    private static final int SHIELD_PARRY_COOLDOWN = 35;
    private static final int SHIELD_PARRY_WINDOW = 4;
    private static final float PARRY_DAMAGE_MULTIPLIER = 1.3F;
    private static final float PARRY_MAX_DAMAGE_MULTIPLIER = 5.5F;
    private static final float PARRY_KNOCKBACK = 1.1F;

    private static final int BLOCK_TICKS = 12;

    private static final UUID PREPARE_MAX_HEALTH_UUID = UUID.fromString("5217434d-3249-4efd-94fb-569ad5706a3a");
    private static final UUID PREPARE_ATTACK_DAMAGE_UUID = UUID.fromString("b3c91c6b-a156-42e2-8bfe-083189792db3");
    private static final UUID PREPARE_MOVEMENT_SPEED_UUID = UUID.fromString("61edd5dc-697a-445a-8be5-dd5ddcce8ffa");
    private static final UUID PREPARE_KNOCKBACK_RESISTANCE_UUID = UUID.fromString("bf29282a-b559-4716-a590-1824ec1346bd");

    private static final EntityDataAccessor<Integer> DATA_BLOCK_TICKS =
            SynchedEntityData.defineId(AbstractSerpon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_PREPARE =
            SynchedEntityData.defineId(AbstractSerpon.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_SHIELD_LEVEL =
            SynchedEntityData.defineId(AbstractSerpon.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_COMBAT_STYLE =
            SynchedEntityData.defineId(AbstractSerpon.class, EntityDataSerializers.INT);

    private int fly = FLY_RESET;
    private boolean hasBoss;
    private boolean landNavigatorType = true;

    private boolean ownerFollowing;

    private Vec3 ownerFollowTarget;

    private int ownerFollowRecalc;

    private int shieldCooldown;
    private int shieldCanUse = 1;

    private int prepareExpireTick;

    public AbstractSerpon(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        setMaxUpStep(5.1F);
        setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0F);
        setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.xpReward = 20;
        setCanPickUpLoot(false);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.875F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        if (spawnType == MobSpawnType.MOB_SUMMONED && !level().isClientSide()) {
            spawnPortalBurst();
        }
        return result;
    }

    @Override
    public void lifeSpanDamage() {
        if (level().isClientSide()) {
            return;
        }
        spawnPortalBurst();
        discard();
    }

    @Override
    public Predicate<Entity> summonPredicate() {
        EntityType<?> self = getType();
        return candidate -> candidate.getType() == self
                || ServantLimits.sharesSerponCap(self, candidate.getType());
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ServantWanderGoal);
        if (JerotesVillageCompat.isJerotesFlyingStrollLoadable()) {
            this.goalSelector.addGoal(8, JvillageNativeGoals.flyingStroll(this, 0.8D, 10));
        } else {
            this.goalSelector.addGoal(8, new SerponFlyingStrollGoal(this, 0.8D, 10));
        }
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        if (supportsRangedWeapons()) {
            boolean nativeRanged = JerotesVillageCompat.isJerotesRangedGoalLoadable()
                    && JvillageNativeGoals.attachRanged(this.goalSelector, this);
            this.goalSelector.addGoal(1, new JerotesRangedWeaponGoal(this, nativeRanged));
        }
        if (supportsSpellAttack() && JerotesVillageCompat.isJerotesSpellGoalLoadable()) {
            JvillageNativeGoals.attachSpellAttack(this.goalSelector, this, getSpellLevel());
        }
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Mob.class, 6.0F));
        if (JerotesVillageCompat.isJerotesHelpGoalLoadable()) {
            JvillageNativeGoals.attachFactionHelp(this.targetSelector, this);
        } else {
            this.targetSelector.addGoal(1, new JerotesAllyHelpGoal(this));
        }
    }

    public boolean supportsRangedWeapons() {
        return true;
    }

    public boolean supportsSpellAttack() {
        return true;
    }

    public int getSpellLevel() {
        return 1;
    }

    public double meleeReachSqr(LivingEntity target) {
        double reach = (getBbWidth() + target.getBbWidth()) / 2.0D + (Math.sqrt(2.04D) - 0.6D) + 0.5D;
        return reach * reach;
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity target) {
        return meleeReachSqr(target);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("serpon_ambient", SoundEvents.VILLAGER_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("serpon_hurt", SoundEvents.VILLAGER_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("serpon_death", SoundEvents.VILLAGER_DEATH);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !isSilent()) {
            playSound(jerotesSound("serpon_attack", SoundEvents.PLAYER_ATTACK_STRONG), 1.0F, 1.0F);
        }
        if (hit && !level().isClientSide() && JerotesBreakRules.meleeCanBreak(level())) {
            ItemStack hand = getMainHandItem();
            if (!hand.isEmpty() && hand.isDamageableItem()) {
                hand.hurtAndBreak(1, this, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return hit;
    }

    public boolean serponSweep(Entity target, double inflateXZ, double inflateY) {
        if (getMainHandItem().isEmpty()) {
            return false;
        }
        float splash = (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F;
        double knockback = getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.5D;
        double yaw = getYRot() * (Math.PI / 180.0D);
        AABB area = target.getBoundingBox().inflate(inflateXZ, inflateY, inflateXZ);
        for (LivingEntity other : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other == this || other == target || !canAttack(other) || isAlliedTo(other)) {
                continue;
            }
            other.invulnerableTime = 0;
            other.hurt(damageSources().mobAttack(this), splash);
            if (knockback > 0.0D) {
                other.knockback(knockback, Math.sin(yaw), -Math.cos(yaw));
            }
        }
        setDeltaMovement(getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        serponSweepParticles();
        return true;
    }

    public void serponSweepParticles() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        double dx = -Mth.sin(getYRot() * ((float) Math.PI / 180.0F));
        double dz = Mth.cos(getYRot() * ((float) Math.PI / 180.0F));
        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                getX() + dx, getY(0.5D), getZ() + dz, 0, dx, 0.0D, dz, 0.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_BLOCK_TICKS, 0);
        entityData.define(DATA_PREPARE, false);
        entityData.define(DATA_SHIELD_LEVEL, 1);
        entityData.define(DATA_COMBAT_STYLE, 1);
    }

    public int getCombatStyle() {
        return entityData.get(DATA_COMBAT_STYLE);
    }

    public void setCombatStyle(int style) {
        entityData.set(DATA_COMBAT_STYLE, style);
    }

    public int getBlockTicks() {
        return entityData.get(DATA_BLOCK_TICKS);
    }

    public void setBlockTicks(int ticks) {
        entityData.set(DATA_BLOCK_TICKS, Math.max(0, ticks));
    }

    public boolean isPrepare() {
        return entityData.get(DATA_PREPARE);
    }

    public void setPrepare(boolean prepare) {
        setPrepare(prepare, false);
    }

    public void setPrepare(boolean prepare, boolean heal) {
        entityData.set(DATA_PREPARE, prepare);
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attackDamage = getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance knockbackResistance = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance movementSpeed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (maxHealth != null) {
            maxHealth.removeModifier(PREPARE_MAX_HEALTH_UUID);
        }
        if (attackDamage != null) {
            attackDamage.removeModifier(PREPARE_ATTACK_DAMAGE_UUID);
        }
        if (knockbackResistance != null) {
            knockbackResistance.removeModifier(PREPARE_KNOCKBACK_RESISTANCE_UUID);
        }
        if (movementSpeed != null) {
            movementSpeed.removeModifier(PREPARE_MOVEMENT_SPEED_UUID);
        }
        if (prepare) {
            if (maxHealth != null) {
                maxHealth.addTransientModifier(new AttributeModifier(PREPARE_MAX_HEALTH_UUID,
                        "Prepare max health", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE));
            }
            if (attackDamage != null) {
                attackDamage.addTransientModifier(new AttributeModifier(PREPARE_ATTACK_DAMAGE_UUID,
                        "Prepare attack damage", 0.6D, AttributeModifier.Operation.MULTIPLY_BASE));
            }
            if (knockbackResistance != null) {
                double amount = Math.min(0.3D, 1.0D - knockbackResistance.getBaseValue());
                knockbackResistance.addTransientModifier(new AttributeModifier(PREPARE_KNOCKBACK_RESISTANCE_UUID,
                        "Prepare knockback resistance", amount, AttributeModifier.Operation.ADDITION));
            }
            if (movementSpeed != null) {
                movementSpeed.addTransientModifier(new AttributeModifier(PREPARE_MOVEMENT_SPEED_UUID,
                        "Prepare movement speed", 0.2D, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
        if (heal) {
            setHealth(getMaxHealth());
        }
    }

    public void setPrepareTimed(int ticks) {
        setPrepare(true, true);
        prepareExpireTick = ticks;
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        if (name != null && "Prepare".equals(ChatFormatting.stripFormatting(name.getString()))) {
            setPrepare(true, true);
        }
    }

    public static boolean isJerotesItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && "jerotesvillage".equals(id.getNamespace()) && id.getPath().equals(path);
    }

    public boolean shieldCanUse() {
        return shieldCanUse == 1 && shieldCooldown <= 0;
    }

    public int getShieldLevel() {
        return entityData.get(DATA_SHIELD_LEVEL);
    }

    public void setShieldLevel(int level) {
        entityData.set(DATA_SHIELD_LEVEL, Math.max(1, level));
    }

    public void disableShield() {
        if (random.nextFloat() <= 1.0F - (float) (getShieldLevel() - 1) * 0.2F) {
            if (shieldCooldown < 100) {
                shieldCooldown = 100;
            }
            shieldCanUse = 0;
            if (isJerotesItem(getItemInHand(InteractionHand.MAIN_HAND), "serpon_spiked_shield")) {
                stopUsingItem();
            }
        }
    }

    public void disableShieldTry() {
        if (random.nextFloat() <= 0.15D - (double) ((float) (getShieldLevel() - 1) * 0.05F)) {
            if (shieldCooldown < 100) {
                shieldCooldown = 100;
            }
            shieldCanUse = 0;
            if (isJerotesItem(getItemInHand(InteractionHand.MAIN_HAND), "serpon_spiked_shield")) {
                stopUsingItem();
            }
        }
    }

    public void disableShieldBreak(int tick) {
        if (tick == 0) {
            return;
        }
        if (shieldCooldown < tick) {
            shieldCooldown = tick;
        }
        shieldCanUse = 0;
        if (isJerotesItem(getItemInHand(InteractionHand.MAIN_HAND), "serpon_spiked_shield")) {
            stopUsingItem();
        }
    }

    public void hurtUsedShield(float amount) {
        if (!JerotesBreakRules.shieldCanBreak(level())) {
            return;
        }
        ItemStack shield = isJerotesItem(getItemInHand(InteractionHand.OFF_HAND), "serpon_spiked_shield")
                ? getItemInHand(InteractionHand.OFF_HAND) : getItemInHand(InteractionHand.MAIN_HAND);
        if (!isJerotesItem(shield, "serpon_spiked_shield") || amount < 3.0F) {
            return;
        }
        int damage = 1 + Mth.floor(amount);
        InteractionHand hand = shield == getItemInHand(InteractionHand.MAIN_HAND)
                ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        shield.hurtAndBreak(damage, this, e -> e.broadcastBreakEvent(hand));
        if (shield.isEmpty()) {
            setItemInHand(hand, ItemStack.EMPTY);
            playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + random.nextFloat() * 0.4F);
        }
    }

    private boolean canBlockWithShield(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) {
            return false;
        }
        if (source.getDirectEntity() instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) {
            return false;
        }
        Vec3 sourcePos = source.getSourcePosition();
        if (sourcePos == null) {
            return true;
        }
        Vec3 facing = this.getViewVector(1.0F);
        Vec3 toSource = sourcePos.vectorTo(this.position()).normalize();
        toSource = new Vec3(toSource.x, 0.0D, toSource.z);
        Vec3 flatFacing = new Vec3(facing.x, 0.0D, facing.z);
        return toSource.dot(flatFacing) < 0.0D;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.EXPLOSION)) {
            amount /= 4.0F;
        }
        boolean shieldUp = !level().isClientSide() && isUsingItem()
                && isJerotesItem(getUseItem(), "serpon_spiked_shield");
        boolean blocking = amount > 0.0F && shieldUp && shieldCanUse() && canBlockWithShield(source);
        if (!blocking) {
            if (!level().isClientSide() && amount > 0.0F
                    && source.getDirectEntity() instanceof LivingEntity attacker) {
                ItemStack weapon = attacker.getMainHandItem();
                if (weapon.canDisableShield(getUseItem(), this, attacker)) {
                    disableShield();
                }
                disableShieldTry();
            }
            return super.hurt(source, amount);
        }
        setBlockTicks(BLOCK_TICKS);
        shieldCooldown = SHIELD_PARRY_COOLDOWN;
        shieldCanUse = 0;
        stopUsingItem();
        hurtUsedShield(amount);
        playSound(jerotesSound("serpon_block", SoundEvents.SHIELD_BLOCK),
                1.0F, 0.8F + random.nextFloat() * 0.4F);
        spawnParryParticles();
        if (source.getDirectEntity() instanceof LivingEntity attacker && attacker != this) {
            float baseDamage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
            float parryDamage = Math.min(amount * PARRY_DAMAGE_MULTIPLIER,
                    PARRY_MAX_DAMAGE_MULTIPLIER * baseDamage);
            attacker.invulnerableTime = 0;
            attacker.hurt(damageSources().thorns(this), parryDamage);
            double resistance = Math.max(attacker.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
            attacker.knockback(PARRY_KNOCKBACK * resistance,
                    getX() - attacker.getX(), getZ() - attacker.getZ());
        }
        return false;
    }

    @Override
    public void m_6472_(DamageSource source, float damage) {
        if (!JerotesBreakRules.armorCanBreak(level())) {
            return;
        }
        super.m_6472_(source, damage);
    }

    private void spawnParryParticles() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        Vec3 look = getViewVector(1.0F).normalize();
        Vec3 base = position().add(look.scale(0.35D));
        double radius = 1.325D;
        double heightOffset = getY(0.8F) - getY();
        double yaw = Math.toRadians(getYRot());
        double pitch = Math.toRadians(getXRot());
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        for (int i = 0; i < 80; ++i) {
            double angle = Math.toRadians(-90.0D + i * 180.0D / 80.0D);
            double zOffset = radius * Math.cos(angle);
            double xOffset = radius * Math.sin(angle);
            double rotatedX = xOffset * cosYaw - zOffset * sinYaw;
            double rotatedZ = xOffset * sinYaw + zOffset * cosYaw;
            double finalYOffset = heightOffset + Math.sin(-pitch) * zOffset;
            serverLevel.sendParticles(ParticleTypes.CRIT,
                    base.x + rotatedX, getY() + finalYOffset, base.z + rotatedZ,
                    0, rotatedX * 0.07D, look.y * 0.07D, rotatedZ * 0.07D, 1.0D);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL) || super.isInvulnerableTo(source);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void playBlockFallSound() {
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        tickAirborneOwnerFollow();
    }

    @Override
    public void tick() {
        super.tick();
        if (getBlockTicks() > 0) {
            setBlockTicks(getBlockTicks() - 1);
        }
        tickExtra();
        if (level().isClientSide()) {
            tickPrepareParticles();
            return;
        }
        tickShieldCooldown();
        tickShieldUse();
        tickPrepareExpiry();
        tickPrepareParticles();
        tickSerponFlight();
        tickBiomeWeakness();
        tickSlowRegeneration();
        tickTradeCooldowns();
    }

    public void tickExtra() {
    }

    private void tickPrepareParticles() {
        if (!isPrepare() || tickCount % 3 != 0) {
            return;
        }
        for (int i = 0; i < 4; ++i) {
            double angle = Math.PI * 2.0D * i / 4.0D;
            double radius = 0.45D;
            level().addParticle(ParticleTypes.FLAME,
                    getX() + Math.cos(angle) * radius, getY(0.65F), getZ() + Math.sin(angle) * radius,
                    0.0D, 0.12D, 0.0D);
        }
    }

    private void tickPrepareExpiry() {
        if (prepareExpireTick > 0) {
            --prepareExpireTick;
            if (prepareExpireTick <= 0) {
                prepareExpireTick = 0;
                setPrepare(false, true);
            }
        }
    }

    private void tickShieldCooldown() {
        if (shieldCooldown > 0) {
            --shieldCooldown;
            shieldCanUse = 0;
        }
        if (shieldCooldown <= 0) {
            shieldCanUse = 1;
        }
    }

    private void tickShieldUse() {
        LivingEntity shieldTarget = getTarget();
        if (shieldTarget == null || !shieldTarget.isAlive()) {
            stopBlockingUse();
            return;
        }
        if (canRaiseShield()) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.OFF_HAND);
                setBlockTicks(Math.max(getBlockTicks(), SHIELD_PARRY_WINDOW));
            }
            return;
        }
        if (canRaiseTwoHandedBlock()) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.MAIN_HAND);
            }
            return;
        }
        stopBlockingUse();
    }

    private boolean canRaiseTwoHandedBlock() {
        if (!supportsTwoHandedBlock() || !isAlive() || !shieldCanUse()) {
            return false;
        }
        if (!getOffhandItem().isEmpty() || attackAnim > 0.0F) {
            return false;
        }
        if (!isBlockingTwoHandedWeapon(getMainHandItem())) {
            return false;
        }
        return !isUsingItem() || getUseItem().getUseAnimation() == UseAnim.BLOCK;
    }

    private static boolean isBlockingTwoHandedWeapon(ItemStack stack) {
        return !stack.isEmpty() && !(stack.getItem() instanceof ShieldItem)
                && stack.getUseAnimation() == UseAnim.BLOCK;
    }

    private void stopBlockingUse() {
        if (isUsingItem() && getUseItem().getUseAnimation() == UseAnim.BLOCK) {
            stopUsingItem();
        }
    }

    public boolean supportsTwoHandedBlock() {
        return true;
    }

    private boolean canRaiseShield() {
        if (!isAlive() || !hasSpikedShieldInOffhand() || !shieldCanUse()) {
            return false;
        }
        LivingEntity target = getTarget();
        if (holdsRangedWeapon() && target != null) {
            double firingRange = isAimingBlowpipe() ? 5.0D : 8.0D;
            return distanceToSqr(target) <= firingRange * firingRange;
        }
        return true;
    }

    private void tickSerponFlight() {
        if (tickCount % 200 == 0) {
            hasBoss = !level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(64.0D),
                    AbstractSerpon::isSerponCombatExecuter).isEmpty();
        }
        boolean airborneFollow = keepsAirborneFlight();
        fly = (fly > 0 && !hasBoss && !airborneFollow) ? fly - 1 : FLY_RESET;
        boolean land = !airborneFollow && (onGround() || (fly <= FLY_LAND_THRESHOLD && !hasBoss));
        if (!land && landNavigatorType) {
            this.moveControl = new FlyingMoveControl(this, 10, true);
            FlyingPathNavigation flying = new FlyingPathNavigation(this, level());
            flying.setCanOpenDoors(true);
            flying.setCanFloat(true);
            this.navigation = flying;
            landNavigatorType = false;
        }
        if (land && !landNavigatorType) {
            this.moveControl = new MoveControl(this);
            GroundPathNavigation ground = new GroundPathNavigation(this, level());
            ground.setCanOpenDoors(true);
            ground.setCanFloat(true);
            this.navigation = ground;
            landNavigatorType = true;
        }
        if ((fly > FLY_LAND_THRESHOLD || hasBoss) && needsUpwardThrust()) {
            setDeltaMovement(getDeltaMovement().add(0.0D, 0.5D, 0.0D));
        }
    }

    private boolean keepsAirborneFlight() {
        LivingEntity owner = getTrueOwner();
        return owner != null && !isStaying();
    }

    private boolean needsUpwardThrust() {
        BlockPos pathTarget = getNavigation().getTargetPos();
        if (pathTarget != null && !getNavigation().isDone()) {
            BlockState state = level().getBlockState(pathTarget);
            LivingEntity target = getTarget();
            boolean above = state.isAir()
                    || pathTarget.getY() > getY() + 1.0D
                    || (target != null && !target.onGround())
                    || (target != null && target.getY() > getY() + 1.0D);
            if (above && onGround()) {
                return true;
            }
        }
        return horizontalCollision;
    }

    private void tickAirborneOwnerFollow() {
        LivingEntity owner = getTrueOwner();
        boolean eligible = owner != null && owner.isAlive() && !owner.isSpectator()
                && owner.level() == level()
                && keepsAirborneFlight() && isFollowing() && !isCommanded()
                && getTarget() == null && !isPassenger() && !isLeashed()
                && isNoGravity();
        if (!eligible) {
            if (ownerFollowing) {
                ownerFollowing = false;
                ownerFollowTarget = null;
                getMoveControl().setWantedPosition(getX(), getY(), getZ(), 0.0D);
            }
            return;
        }
        double distanceSqr = distanceToSqr(owner);
        if (ownerFollowing) {
            if (distanceSqr <= OWNER_FOLLOW_STOP * OWNER_FOLLOW_STOP) {
                ownerFollowing = false;
            }
        } else if (distanceSqr > OWNER_FOLLOW_START * OWNER_FOLLOW_START) {
            ownerFollowing = true;
            ownerFollowRecalc = 0;
        }
        if (!ownerFollowing) {
            if (ownerFollowTarget != null) {
                ownerFollowTarget = null;
                getMoveControl().setWantedPosition(getX(), getY(), getZ(), 0.0D);
            }
            return;
        }
        if (--ownerFollowRecalc <= 0 || ownerFollowTarget == null) {
            ownerFollowRecalc = OWNER_FOLLOW_RECALC_TICKS;
            ownerFollowTarget = ownerHoverPoint(owner);
        }
        getMoveControl().setWantedPosition(ownerFollowTarget.x, ownerFollowTarget.y,
                ownerFollowTarget.z, OWNER_FOLLOW_SPEED);
        getLookControl().setLookAt(owner, 10.0F, getMaxHeadXRot());
    }

    private Vec3 ownerHoverPoint(LivingEntity owner) {
        double dx = getX() - owner.getX();
        double dz = getZ() - owner.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-3D) {
            float yaw = owner.getYRot() * ((float) Math.PI / 180.0F);
            dx = Mth.sin(yaw) * OWNER_FOLLOW_STANDOFF;
            dz = -Mth.cos(yaw) * OWNER_FOLLOW_STANDOFF;
        } else {
            dx = dx / horizontal * OWNER_FOLLOW_STANDOFF;
            dz = dz / horizontal * OWNER_FOLLOW_STANDOFF;
        }
        double wave = (Math.sin((tickCount + (getId() & 0xFF) * 7) * 0.05D) + 1.0D) * 0.5D;
        return new Vec3(owner.getX() + dx, owner.getY() + 1.0D + wave * 1.5D, owner.getZ() + dz);
    }

    private void tickBiomeWeakness() {
        if (!JerotesVillageServantConfig.SERPON_WEAKNESS_IN_NON_HOT_BIOMES.get()) {
            return;
        }
        if (isPrepare() || isInLava() || hasBoss
                || level().getBiome(blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)) {
            return;
        }
        LivingEntity owner = getTrueOwner();
        if (owner != null && (CuriosFinder.hasUnholySet(owner) || CuriosFinder.hasNetherSet(owner))) {
            return;
        }
        addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0));
    }

    private void tickSlowRegeneration() {
        if (isAlive() && random.nextInt(900) == 1) {
            heal(3.0F);
        }
    }

    @Override
    public void tickDeath() {
        if (!level().isClientSide() && deathTime == 19 && level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(serponPortalParticle(), getX(), getY(0.5D), getZ(), 1,
                    0.0D, 0.0D, 0.0D, 0.0D);
        }
        super.tickDeath();
    }

    public static ParticleOptions serponPortalParticle() {
        net.minecraft.core.particles.ParticleType<?> particle =
                ForgeRegistries.PARTICLE_TYPES.getValue(
                        ResourceLocation.fromNamespaceAndPath("jerotesvillage", "serpon_portal"));
        if (particle instanceof net.minecraft.core.particles.SimpleParticleType simple) {
            return simple;
        }
        return ParticleTypes.PORTAL;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    public static final double BETRAYAL_RADIUS = 32.0D;

    @Override
    public void tryKill(Player player) {
        if (level().isClientSide()) {
            return;
        }
        if (warnsBeforeExecution() && getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        onExecuted(player);
    }

    public boolean warnsBeforeExecution() {
        return false;
    }

    public void onExecuted(Player player) {
        spawnPortalBurst();
        discard();
    }

    public void spawnPortalBurst() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(serponPortalParticle(), getX(), getY(0.5D), getZ(), 1,
                0.0D, 0.0D, 0.0D, 0.0D);
        serverLevel.sendParticles(ParticleTypes.PORTAL, getX(), getY(0.75D), getZ(),
                24, 0.4D, 0.7D, 0.4D, 0.15D);
    }

    public static EntityType<?> jvillageCounterpart(EntityType<?> type) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id == null || !"goety_supreme".equals(id.getNamespace())) {
            return null;
        }
        ResourceLocation counterpart =
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", id.getPath());
        if (!ForgeRegistries.ENTITY_TYPES.containsKey(counterpart)) {
            return null;
        }
        return ForgeRegistries.ENTITY_TYPES.getValue(counterpart);
    }

    public boolean convertToJvillage(ServerLevel serverLevel, Player player) {
        return convertToJvillage(serverLevel, player, false);
    }

    public boolean convertToJvillage(ServerLevel serverLevel, Player player, boolean preserveNbt) {
        if (spawnJvillageCounterpart(serverLevel, player, preserveNbt) == null) {
            return false;
        }
        discard();
        return true;
    }

    public Entity spawnJvillageCounterpart(ServerLevel serverLevel, Player player, boolean preserveNbt) {
        if (isRemoved()) {
            return null;
        }
        EntityType<?> counterpart = jvillageCounterpart(getType());
        if (counterpart == null) {
            return null;
        }
        Entity spawned = counterpart.create(serverLevel);
        if (spawned == null) {
            return null;
        }
        spawnPortalBurst();
        spawned.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        Mob mob = spawned instanceof Mob m ? m : null;
        if (mob != null) {
            mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            mob.setPersistenceRequired();
        }
        if (preserveNbt) {
            copyStateToJvillage(spawned);
        }
        spawned.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        spawned.setYRot(getYRot());
        spawned.setXRot(getXRot());
        if (spawned instanceof LivingEntity living) {
            living.setYBodyRot(this.yBodyRot);
            living.setYHeadRot(getYHeadRot());
        }
        spawned.setDeltaMovement(getDeltaMovement());
        spawned.setOldPosAndRot();
        if (mob != null && player != null) {
            mob.setTarget(player);
        }
        return serverLevel.addFreshEntity(spawned) ? spawned : null;
    }

    private static final String[] BETRAYAL_CARRY_BAN = {
            "id", "UUID", "UUIDMost", "UUIDLeast", "Pos", "Motion", "Rotation",
            "Owner", "GoetyOwner", "TrueOwner", "Team", "Tags", "Leashed", "DataOwner",
            "Passengers", "RootVehicle", "Wandering", "Staying", "LimitedLife", "Lifespan",
            "commandPos", "commandTick"
    };

    private void copyStateToJvillage(Entity spawned) {
        CompoundTag carry = new CompoundTag();
        try {
            saveWithoutId(carry);
        } catch (RuntimeException | LinkageError ignored) {
            return;
        }
        for (String ban : BETRAYAL_CARRY_BAN) {
            carry.remove(ban);
        }
        boolean loaded = false;
        try {
            spawned.load(carry);
            loaded = true;
        } catch (RuntimeException | LinkageError ignored) {
        }
        if (spawned instanceof LivingEntity living) {
            if (!loaded) {
                for (MobEffectInstance effect : getActiveEffects()) {
                    living.addEffect(new MobEffectInstance(effect));
                }
            }
            living.setHealth(living.getMaxHealth());
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        LivingEntity owner = getTrueOwner();
        if (owner != null && owner != player) {
            return super.mobInteract(player, hand);
        }
        if (owner == player && ServantFeedHeal.tryBlazeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (owner == player && openServantInventory(player, hand)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (held.isEmpty()) {
            if (bothHandsEmpty(player) && hasTrades()) {
                if (!level().isClientSide()) {
                    refreshTradeCooldowns();
                    setTradingPlayer(player);
                    openTradingScreen(player, getDisplayName(), 1);
                }
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
            return super.mobInteract(player, hand);
        }
        EquipmentSlot slot = equipSlotFor(held);
        if (!supportsRangedWeapons() && JerotesRangedWeaponGoal.isRangedWeapon(held)) {
            return super.mobInteract(player, hand);
        }
        boolean mainHandWeapon = slot == EquipmentSlot.MAINHAND && isGiveableWeapon(held);
        boolean anyArmor = slot != null && slot.getType() == EquipmentSlot.Type.ARMOR;
        boolean offhandAccepted = slot == EquipmentSlot.OFFHAND && acceptsEquipment(held);
        if (slot == null || !(mainHandWeapon || anyArmor || offhandAccepted)) {
            return super.mobInteract(player, hand);
        }
        if (!level().isClientSide()) {
            ItemStack old = getItemBySlot(slot).copy();
            setItemSlot(slot, held.copyWithCount(1));
            setDropChance(slot, 0.0F);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            if (!old.isEmpty() && !RingServantGear.isRingGear(old) && !player.addItem(old)) {
                player.drop(old, false);
            }
            playSound(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private static boolean bothHandsEmpty(Player player) {
        return player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty();
    }

    public static EquipmentSlot equipSlotFor(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor) {
            return armor.getEquipmentSlot();
        }
        if (stack.getItem() instanceof ShieldItem || isJerotesItem(stack, "serpon_spiked_shield")) {
            return EquipmentSlot.OFFHAND;
        }
        return EquipmentSlot.MAINHAND;
    }

    public boolean acceptsEquipment(ItemStack stack) {
        return true;
    }

    public static final String[] SERPON_MELEE_WEAPONS = {
            "serpon_sword", "serpon_axe", "serpon_dagger", "serpon_mace",
            "serpon_spear", "serpon_pike", "serpon_glaive"
    };

    public static boolean isAnyJerotesItem(ItemStack stack, String... paths) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null || !"jerotesvillage".equals(id.getNamespace())) {
            return false;
        }
        for (String path : paths) {
            if (path.equals(id.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSerponCombatExecuter(Entity entity) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (id == null || !"serpon_combat_executer".equals(id.getPath())) {
            return false;
        }
        return "jerotesvillage".equals(id.getNamespace()) || "goety_supreme".equals(id.getNamespace());
    }

    @Override
    public boolean isNoGravity() {
        return fly > FLY_LAND_THRESHOLD || super.isNoGravity();
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsPrepare", isPrepare());
        tag.putInt("PrepareExpire", prepareExpireTick);
        tag.putInt("Fly", fly);
        tag.putInt("ShieldCoolDown", shieldCooldown);
        tag.putInt("ShieldCanUse", shieldCanUse);
        tag.putInt("ShieldLevel", getShieldLevel());
        tag.putInt("CombatStyle", getCombatStyle());
        saveTradeData(tag);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("IsPrepare")) {
            setPrepare(tag.getBoolean("IsPrepare"));
        }
        if (tag.contains("PrepareExpire")) {
            prepareExpireTick = tag.getInt("PrepareExpire");
        }
        if (tag.contains("Fly")) {
            fly = tag.getInt("Fly");
        }
        if (tag.contains("ShieldCoolDown")) {
            shieldCooldown = tag.getInt("ShieldCoolDown");
        }
        if (tag.contains("ShieldCanUse")) {
            shieldCanUse = tag.getInt("ShieldCanUse");
        }
        if (tag.contains("ShieldLevel")) {
            setShieldLevel(tag.getInt("ShieldLevel"));
        }
        if (tag.contains("CombatStyle")) {
            setCombatStyle(tag.getInt("CombatStyle"));
        }
        loadTradeData(tag);
    }

    public boolean holdsRangedWeapon() {
        return JerotesRangedWeaponGoal.isRangedWeapon(getMainHandItem())
                || JerotesRangedWeaponGoal.isRangedWeapon(getOffhandItem());
    }

    public boolean isAimingBlowpipe() {
        return false;
    }

    public boolean hasSpikedShieldInOffhand() {
        return isJerotesItem(getOffhandItem(), "serpon_spiked_shield");
    }

    public boolean wearsRingDressedSerponArmor() {
        return RingServantGear.ringScopeApplies(this);
    }

    public void equipRingSerponArmor(EquipmentSlot slot, String itemPath) {
        equipJerotes(slot, itemPath);
        RingServantGear.markRingGear(getItemBySlot(slot));
    }

    public void buildOffers(MerchantOffers offers) {
    }

    public final void addTrade(MerchantOffers offers, MerchantOffer offer,
            int cooldownTicks, boolean oneTime) {
        offers.add(offer);
        tradeCooldownTicks.add(cooldownTicks);
        tradeCooldownEnds.add(0L);
        tradeDone.add(false);
        tradeOneTime.add(oneTime);
    }

    public static ItemStack jerotesStack(String path, int count) {
        Item item = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return item == null ? ItemStack.EMPTY : new ItemStack(item, count);
    }

    public boolean hasTrades() {
        return !getOffers().isEmpty();
    }

    @Override
    public MerchantOffers getOffers() {
        if (serponOffers == null) {
            serponOffers = new MerchantOffers();
            buildOffers(serponOffers);
        }
        return serponOffers;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.serponOffers = offers;
    }

    @Override
    public void setTradingPlayer(Player player) {
        this.tradingPlayer = player;
    }

    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        int index = getOffers().indexOf(offer);
        if (index < 0) {
            return;
        }
        int cooldown = tradeCooldownTicks.get(index);
        boolean oneTime = index < tradeOneTime.size() && tradeOneTime.get(index);
        if (cooldown >= 0 || oneTime) {
            tradeDone.set(index, true);
        }
        if (cooldown > 0) {
            tradeCooldownEnds.set(index, level().getGameTime() + cooldown);
        }
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return jerotesSound("serpon_ambient", SoundEvents.VILLAGER_AMBIENT);
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
    }

    @Override
    public int getVillagerXp() {
        return tradeXp;
    }

    @Override
    public void overrideXp(int xp) {
        this.tradeXp = xp;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean canRestock() {
        return false;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide();
    }

    private void tickTradeCooldowns() {
        if (serponOffers == null || serponOffers.isEmpty()) {
            return;
        }
        long now = level().getGameTime();
        for (int i = 0; i < serponOffers.size() && i < tradeCooldownEnds.size(); ++i) {
            long end = tradeCooldownEnds.get(i);
            if (end > 0 && now >= end) {
                tradeCooldownEnds.set(i, 0L);
                tradeDone.set(i, false);
                serponOffers.get(i).resetUses();
            }
        }
    }

    private void refreshTradeCooldowns() {
        tickTradeCooldowns();
    }

    private void saveTradeData(net.minecraft.nbt.CompoundTag tag) {
        getOffers();
        long[] ends = new long[tradeCooldownEnds.size()];
        byte[] done = new byte[tradeDone.size()];
        for (int i = 0; i < ends.length; ++i) {
            ends[i] = tradeCooldownEnds.get(i);
        }
        for (int i = 0; i < done.length; ++i) {
            done[i] = (byte) (tradeDone.get(i) ? 1 : 0);
        }
        tag.putLongArray("SerponTradeEnds", ends);
        tag.putByteArray("SerponTradeDone", done);
    }

    private void loadTradeData(net.minecraft.nbt.CompoundTag tag) {
        getOffers();
        long[] ends = tag.getLongArray("SerponTradeEnds");
        byte[] done = tag.getByteArray("SerponTradeDone");
        for (int i = 0; i < serponOffers.size(); ++i) {
            tradeCooldownEnds.set(i, i < ends.length ? ends[i] : 0L);
            boolean used = i < done.length && done[i] != 0;
            tradeDone.set(i, used);
            boolean tracked = tradeCooldownTicks.get(i) >= 0
                    || (i < tradeOneTime.size() && tradeOneTime.get(i));
            if (used && tracked) {
                serponOffers.get(i).setToOutOfStock();
            }
        }
    }
}
