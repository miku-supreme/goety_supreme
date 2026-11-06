package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.compat.JerotesTwoHandedEffects;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.renderer.SedimentLordServantAnimation;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;

public class SedimentLordServant extends AbstractSedimentServant {

    private static final int SPELL_LEVEL = 3;

    private static final int ATTACK_INTERVAL = 10;

    private static final int SPECIAL_COOLDOWN = 120;

    private static final int LEAD_COOLDOWN_TICKS = 600;

    private static final int LEAD_READY_INITIAL = 540;

    private static final int SURGE_ATTACK_INTERVAL = 5;

    private static final int ORDERED_GUARD_LIFESPAN = 120 * 20;

    private static final int ORDERED_HEAVY_LIFESPAN = 120 * 20;

    private static final int ORDERED_GUARD_CAP = 12;

    private static final int ORDERED_HEAVY_CAP = 4;

    private static final double NETHER_STAR_HEALTH_BONUS = 35.0D;
    private static final double SOUL_JAR_HEALTH_BONUS = 50.0D;
    private static final java.util.UUID NETHER_STAR_HEALTH_ID =
            java.util.UUID.fromString("9d1c5f4e-2f6a-4c2b-8f9e-1a2b3c4d5e6f");
    private static final java.util.UUID SOUL_JAR_HEALTH_ID =
            java.util.UUID.fromString("3b7e8a10-4c5d-4e6f-9a0b-1c2d3e4f5a6b");

    private static final double BERSERK_SPEED_BONUS = 0.05D;
    private static final java.util.UUID BERSERK_SPEED_ID =
            java.util.UUID.fromString("6f2a4c8e-1b3d-4f5a-8c7e-9d0b1a2c3e4f");

    private static final int BERSERK_TICKS = 120 * 20;

    private static final int LEAD_COOLDOWN_SOUL_JAR = 20 * 20;

    private static final float ORDERED_HEAVY_CHANCE = 0.35F;

    private static final int HEAVY_FOCUS_GUARANTEED = 1;

    private static final float HEAVY_FOCUS_SECOND_CHANCE = 0.5F;

    private static final float STABILIZER_FOCUS_SWAP_CHANCE = 0.2F;

    private static final ResourceLocation ANCIENT_BOW_ID =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "ancient_second_rounder_bow");

    private static final double DEG_TO_RAD = Math.PI / 180.0D;

    private int attackTick;
    private int specialAttackTick;
    private int specialAttackCooldown;
    private int specialAttackUse;
    private int actionSurge;
    private int leadTick = LEAD_READY_INITIAL;
    private int leadUseTick;
    private int shieldAttackTick = -20;
    private int specialBlockTick;

    private boolean netherStarBoon;

    private boolean soulJarBoon;

    private boolean heavyGuardFocusBoon;

    private boolean soulStabilizerFocusBoon;

    private int berserkTicks;

    private boolean berserkBoon;



    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState startAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState swordAttack1AnimationState = new AnimationState();
    public final AnimationState swordAttack2AnimationState = new AnimationState();
    public final AnimationState swordAttack3AnimationState = new AnimationState();
    public final AnimationState swordAttack4AnimationState = new AnimationState();
    public final AnimationState leadAnimationState = new AnimationState();

    public final AnimationState block1AnimationState = new AnimationState();
    public final AnimationState block2AnimationState = new AnimationState();
    public final AnimationState block3AnimationState = new AnimationState();
    public final AnimationState shieldAttackAnimationState = new AnimationState();
    public final AnimationState pushingAttackAnimationState = new AnimationState();
    public final AnimationState tripAttackAnimationState = new AnimationState();
    public final AnimationState precisionAttackAnimationState = new AnimationState();
    public final AnimationState sweepingAttackAnimationState = new AnimationState();
    public final AnimationState menacingAttackAnimationState = new AnimationState();
    public final AnimationState maneuveringAttackAnimationState = new AnimationState();
    public final AnimationState lungingAttackAnimationState = new AnimationState();
    public final AnimationState goadingAttackAnimationState = new AnimationState();
    public final AnimationState feintingAttackAnimationState = new AnimationState();
    public final AnimationState disarmingAttackAnimationState = new AnimationState();
    public final AnimationState distractingStrikeAnimationState = new AnimationState();

    public final AnimationState dead1AnimationState = new AnimationState();
    public final AnimationState dead2AnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(SedimentLordServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_WALK_ANIM =
            SynchedEntityData.defineId(SedimentLordServant.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_BERSERK =
            SynchedEntityData.defineId(SedimentLordServant.class, EntityDataSerializers.BOOLEAN);

    private int animTick;

    private int startTick;

    public SedimentLordServant(EntityType<? extends SedimentLordServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 300;
        this.setCanPickUpLoot(true);
        this.setMaxUpStep(1.1F);
        setShieldLevel(3);
    }

    @Override
    public SpawnGroupData finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty,
            net.minecraft.world.entity.MobSpawnType spawnType, SpawnGroupData spawnData,
            net.minecraft.nbt.CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        this.startTick = 40;
        return result;
    }

    public void beginCoffinEntrance() {
        this.startTick = 40;
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 170.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SEDIMENT_LORD;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/sediment_lord.png";
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new LordMeleeGoal(this, 1.4D));
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "ancient_second_rounder_noble_helmet");
        equipJerotes(EquipmentSlot.CHEST, "ancient_second_rounder_noble_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "ancient_second_rounder_noble_leggings");
        equipJerotes(EquipmentSlot.FEET, "ancient_second_rounder_noble_boots");
        float roll = random.nextFloat();
        if (RingSetBonuses.ancientSwordExcluded(this)) {
            while (roll > 0.65F) {
                roll = random.nextFloat();
            }
        }
        if (roll > 0.65F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_greatsword");
        } else if (roll > 0.35F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_battleaxe");
        } else {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_warhammer");
        }
    }

    @Override
    public net.minecraft.sounds.SoundEvent getAmbientSound() {
        return jerotesSound("sediment_lord_ambient", SoundEvents.PILLAGER_AMBIENT);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return isDamageSourceBlocked(source)
                ? SoundEvents.SHIELD_BLOCK
                : jerotesSound("sediment_lord_hurt", SoundEvents.PILLAGER_HURT);
    }

    @Override
    public EquipmentSlot replacementSlotFor(net.minecraft.world.item.ItemStack held) {
        if (held.isEmpty()) {
            return null;
        }
        if (held.is(LORD_NOBLE_ARMOR)) {
            return armorSlotFor(held);
        }
        if (held.is(GUARD_HEAVY_WEAPON)) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public boolean acceptsRangedWeaponGiving() {
        return false;
    }

    @Override
    public net.minecraft.world.InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (isOwnedBy(player) && isAlive() && !player.isSpectator()) {
            ItemStack held = player.getItemInHand(hand);
            if (giveBoon(player, held)) {
                return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    private boolean giveBoon(Player player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }
        if (held.is(net.minecraft.world.item.Items.NETHER_STAR)) {
            if (!netherStarBoon && !level().isClientSide) {
                netherStarBoon = true;
                applyBoonModifiersAndFollowHealth(NETHER_STAR_HEALTH_BONUS);
                consumeGift(player, held);
                boonFeedback();
            }
            return true;
        }
        if (isDrownedNecromancerSoulJar(held)) {
            if (!soulJarBoon && !level().isClientSide) {
                soulJarBoon = true;
                applyBoonModifiersAndFollowHealth(SOUL_JAR_HEALTH_BONUS);
                consumeGift(player, held);
                boonFeedback();
            }
            return true;
        }
        if (held.is(GeneratedMod.HEAVY_ARMORED_SEDIMENT_GUARD_FOCUS.get())) {
            if (!heavyGuardFocusBoon && !level().isClientSide) {
                heavyGuardFocusBoon = true;
                consumeGift(player, held);
                boonFeedback();
            }
            return true;
        }
        if (held.is(GeneratedMod.SEDIMENT_SOUL_STABILIZER_FOCUS.get())) {
            if (!soulStabilizerFocusBoon && !level().isClientSide) {
                soulStabilizerFocusBoon = true;
                consumeGift(player, held);
                boonFeedback();
            }
            return true;
        }
        if (held.is(GeneratedMod.BERSERK_ESSENCE.get())) {
            if (!level().isClientSide) {
                berserkTicks = BERSERK_TICKS;
                berserkBoon = true;
                applyBoonModifiers();
                consumeGift(player, held);
                boonFeedback();
                startLeadAnimation();
                playLeadSound();
                leadTick = 0;
                sedimentOrder();
            }
            return true;
        }
        return false;
    }

    private static boolean isDrownedNecromancerSoulJar(ItemStack stack) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (!ResourceLocation.fromNamespaceAndPath("goety", "soul_jar").equals(id)) {
            return false;
        }
        return SoulJar.isDrowned(stack);
    }

    private void consumeGift(Player player, ItemStack held) {
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
    }

    private void boonFeedback() {
        playSound(jerotesSound("sediment_lord_action", SoundEvents.BEACON_ACTIVATE), 2.0F, 0.8F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(1.0D), getZ(),
                    24, 0.5D, 0.6D, 0.5D, 0.02D);
        }
    }

    private void applyBoonModifiers() {
        setHealthBonus(NETHER_STAR_HEALTH_ID, "sediment_lord_nether_star", netherStarBoon,
                NETHER_STAR_HEALTH_BONUS);
        setHealthBonus(SOUL_JAR_HEALTH_ID, "sediment_lord_soul_jar", soulJarBoon,
                SOUL_JAR_HEALTH_BONUS);
        net.minecraft.world.entity.ai.attributes.AttributeInstance speed =
                getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.removeModifier(BERSERK_SPEED_ID);
            if (berserkTicks > 0) {
                speed.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        BERSERK_SPEED_ID, "sediment_lord_berserk", BERSERK_SPEED_BONUS,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
            }
        }
    }

    private void applyBoonModifiersAndFollowHealth(double bonus) {
        float oldHealth = getHealth();
        applyBoonModifiers();
        setHealth(Math.min(getMaxHealth(), oldHealth + (float) bonus));
    }

    private void setHealthBonus(java.util.UUID id, String name, boolean active, double amount) {
        net.minecraft.world.entity.ai.attributes.AttributeInstance maxHealth =
                getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }
        maxHealth.removeModifier(id);
        if (active) {
            maxHealth.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    id, name, amount,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
            if (getHealth() > getMaxHealth()) {
                setHealth(getMaxHealth());
            }
        }
    }

    private boolean berserkActive() {
        return berserkTicks > 0;
    }

    public boolean isBerserk() {
        return this.level().isClientSide()
                ? this.entityData.get(DATA_BERSERK)
                : this.berserkActive();
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return jerotesSound("sediment_lord_death", SoundEvents.PILLAGER_DEATH);
    }

    @Override
    public boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
        if (!newItem.is(GUARD_HEAVY_WEAPON)) {
            return false;
        }
        return super.canReplaceCurrentItem(newItem, oldItem);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        if (stack.canEquip(EquipmentSlot.HEAD, this) || stack.canEquip(EquipmentSlot.CHEST, this)
                || stack.canEquip(EquipmentSlot.LEGS, this) || stack.canEquip(EquipmentSlot.FEET, this)) {
            return false;
        }
        return super.canHoldItem(stack);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.LEVITATION && super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("LordAttackTick", attackTick);
        tag.putInt("LordSpecialTick", specialAttackTick);
        tag.putInt("LordSpecialCooldown", specialAttackCooldown);
        tag.putInt("LordSpecialUse", specialAttackUse);
        tag.putInt("LordActionSurge", actionSurge);
        tag.putInt("LordLeadTick", leadTick);
        tag.putInt("LordLeadUseTick", leadUseTick);
        tag.putInt("LordShieldAttackTick", shieldAttackTick);
        tag.putInt("LordSpecialBlockTick", specialBlockTick);
        tag.putInt("StartTick", startTick);
        tag.putInt("LordAnimTick", animTick);
        tag.putBoolean("LordNetherStarBoon", netherStarBoon);
        tag.putBoolean("LordSoulJarBoon", soulJarBoon);
        tag.putBoolean("LordHeavyGuardFocusBoon", heavyGuardFocusBoon);
        tag.putBoolean("LordSoulStabilizerFocusBoon", soulStabilizerFocusBoon);
        tag.putInt("LordBerserkTicks", berserkTicks);
        tag.putBoolean("LordBerserkBoon", berserkBoon);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        attackTick = tag.getInt("LordAttackTick");
        specialAttackTick = tag.getInt("LordSpecialTick");
        specialAttackCooldown = tag.getInt("LordSpecialCooldown");
        specialAttackUse = tag.getInt("LordSpecialUse");
        actionSurge = tag.getInt("LordActionSurge");
        leadTick = tag.contains("LordLeadTick") ? tag.getInt("LordLeadTick") : LEAD_READY_INITIAL;
        leadUseTick = tag.getInt("LordLeadUseTick");
        shieldAttackTick = tag.contains("LordShieldAttackTick")
                ? tag.getInt("LordShieldAttackTick") : -20;
        specialBlockTick = tag.getInt("LordSpecialBlockTick");
        startTick = tag.getInt("StartTick");
        animTick = tag.getInt("LordAnimTick");
        netherStarBoon = tag.getBoolean("LordNetherStarBoon");
        soulJarBoon = tag.getBoolean("LordSoulJarBoon");
        heavyGuardFocusBoon = tag.getBoolean("LordHeavyGuardFocusBoon");
        soulStabilizerFocusBoon = tag.getBoolean("LordSoulStabilizerFocusBoon");
        berserkTicks = tag.getInt("LordBerserkTicks");
        berserkBoon = tag.getBoolean("LordBerserkBoon");
        applyBoonModifiers();
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (!(getTrueOwner() instanceof Player player)) {
            return;
        }
        ItemStack totem = new ItemStack(GeneratedMod.SEDIMENT_LORD_TOTEM.get());
        ReviveServantItem.setOwnerName(player, totem);
        ReviveServantItem.setSummon(this, totem);
        OwnerBoundLoot.deliver(this, totem);
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
    public void tickDeath() {
        if (deathTime <= 0) {
            playAnimation(getMainHandItem().isEmpty() ? "dead1" : "dead2", 40);
        }
        ++deathTime;
        if (deathTime >= 40 && !level().isClientSide && !isRemoved()) {
            level().broadcastEntityEvent(this, (byte) 60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ANIM_STATE, 0);
        entityData.define(DATA_ATTACK_WALK_ANIM, 0);
        entityData.define(DATA_BERSERK, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide && isAlive()) {
            idleAnimationState.startIfStopped(tickCount);
            spawnAmbientParticles();
        }
        if (!level().isClientSide && isAlive()) {
            entityData.set(DATA_BERSERK, berserkTicks > 0);
            if (netherStarBoon && tickCount % 20 == 0 && getHealth() < getMaxHealth()) {
                heal(1.0F);
            }
            if (berserkTicks > 0) {
                --berserkTicks;
                if (berserkTicks == 0) {
                    applyBoonModifiers();
                }
            }
        }
    }

    private void spawnAmbientParticles() {
        for (int i = 0; i < 3; i++) {
            level().addParticle(ParticleTypes.PORTAL,
                    getRandomX(0.5D), getY(), getRandomZ(0.5D),
                    0.0D, 0.0D, 0.0D);
        }
        if (entityData.get(DATA_BERSERK) && random.nextInt(3) == 0) {
            level().addParticle(ParticleTypes.SOUL,
                    getRandomX(0.9D), getRandomY(), getRandomZ(0.9D),
                    (random.nextDouble() - 0.5D) * 0.03D, 0.02D + random.nextDouble() * 0.02D,
                    (random.nextDouble() - 0.5D) * 0.03D);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!DATA_ANIM_STATE.equals(key) || !level().isClientSide) {
            return;
        }
        AnimationState state = animationForId(entityData.get(DATA_ANIM_STATE));
        if (state == null) {
            allAnimations().forEach(AnimationState::stop);
            return;
        }
        state.start(tickCount);
        for (AnimationState other : allAnimations()) {
            if (other != state) {
                other.stop();
            }
        }
    }

    private void playAnimation(String name, int ticks) {
        if (level().isClientSide) {
            return;
        }
        animTick = ticks;
        setAnimState(animId(name));
    }

    private void setAnimState(int id) {
        entityData.set(DATA_ANIM_STATE, id);
    }

    private static int animId(String name) {
        return switch (name) {
            case "start" -> 1;
            case "attack1" -> 2;
            case "attack2" -> 3;
            case "swordAttack1" -> 4;
            case "swordAttack2" -> 5;
            case "swordAttack3" -> 6;
            case "swordAttack4" -> 7;
            case "lead" -> 8;
            case "block1" -> 9;
            case "block2" -> 10;
            case "block3" -> 11;
            case "shieldAttack" -> 12;
            case "dead1" -> 13;
            case "dead2" -> 14;
            case "pushingAttack" -> 15;
            case "tripAttack" -> 16;
            case "precisionAttack" -> 17;
            case "sweepingAttack" -> 18;
            case "menacingAttack" -> 19;
            case "maneuveringAttack" -> 20;
            case "lungingAttack" -> 21;
            case "goadingAttack" -> 22;
            case "feintingAttack" -> 23;
            case "disarmingAttack" -> 24;
            case "distractingStrike" -> 25;
            default -> 0;
        };
    }

    private AnimationState animationForId(int id) {
        return switch (id) {
            case 1 -> startAnimationState;
            case 2 -> attack1AnimationState;
            case 3 -> attack2AnimationState;
            case 4 -> swordAttack1AnimationState;
            case 5 -> swordAttack2AnimationState;
            case 6 -> swordAttack3AnimationState;
            case 7 -> swordAttack4AnimationState;
            case 8 -> leadAnimationState;
            case 9 -> block1AnimationState;
            case 10 -> block2AnimationState;
            case 11 -> block3AnimationState;
            case 12 -> shieldAttackAnimationState;
            case 13 -> dead1AnimationState;
            case 14 -> dead2AnimationState;
            case 15 -> pushingAttackAnimationState;
            case 16 -> tripAttackAnimationState;
            case 17 -> precisionAttackAnimationState;
            case 18 -> sweepingAttackAnimationState;
            case 19 -> menacingAttackAnimationState;
            case 20 -> maneuveringAttackAnimationState;
            case 21 -> lungingAttackAnimationState;
            case 22 -> goadingAttackAnimationState;
            case 23 -> feintingAttackAnimationState;
            case 24 -> disarmingAttackAnimationState;
            case 25 -> distractingStrikeAnimationState;
            default -> null;
        };
    }

    private List<AnimationState> allAnimations() {
        List<AnimationState> states = new ArrayList<>();
        states.add(startAnimationState);
        states.add(attack1AnimationState);
        states.add(attack2AnimationState);
        states.add(swordAttack1AnimationState);
        states.add(swordAttack2AnimationState);
        states.add(swordAttack3AnimationState);
        states.add(swordAttack4AnimationState);
        states.add(leadAnimationState);
        states.add(block1AnimationState);
        states.add(block2AnimationState);
        states.add(block3AnimationState);
        states.add(shieldAttackAnimationState);
        states.add(dead1AnimationState);
        states.add(dead2AnimationState);
        states.add(pushingAttackAnimationState);
        states.add(tripAttackAnimationState);
        states.add(precisionAttackAnimationState);
        states.add(sweepingAttackAnimationState);
        states.add(menacingAttackAnimationState);
        states.add(maneuveringAttackAnimationState);
        states.add(lungingAttackAnimationState);
        states.add(goadingAttackAnimationState);
        states.add(feintingAttackAnimationState);
        states.add(disarmingAttackAnimationState);
        states.add(distractingStrikeAnimationState);
        return states;
    }

    public int getAttackWalkAnim() {
        return entityData.get(DATA_ATTACK_WALK_ANIM);
    }

    private void setAttackWalkAnim(int value) {
        entityData.set(DATA_ATTACK_WALK_ANIM, value);
    }

    private AABB lordAttackBox() {
        AABB box = getBoundingBox().inflate(Math.sqrt(2.04D) - 0.6D, 0.0D, Math.sqrt(2.04D) - 0.6D);
        return getMainHandItem().isEmpty() ? box.inflate(0.5D) : box.inflate(0.75D);
    }

    public boolean isWithinLordReach(LivingEntity target) {
        return target != null && lordAttackBox().intersects(target.getBoundingBox());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (specialAction() || (attackTick > 0 && actionSurge <= 0) || attackTick > 5) {
            return false;
        }
        if (!level().isClientSide) {
            attackTick = 20;
            int roll = 1 + random.nextInt(40);
            if (actionSurge <= 0 && specialAttackCooldown <= 0 && random.nextFloat() > 0.5F) {
                startSpecialAttack(roll);
            } else {
                rollActionSurge();
                swing(InteractionHand.MAIN_HAND, true);
                if (getMainHandItem().isEmpty()) {
                    playAnimation(roll <= 30 ? "attack1" : "attack2", 5);
                } else if (roll >= 30) {
                    playAnimation("swordAttack1", 20);
                } else if (roll >= 20) {
                    playAnimation("swordAttack2", 20);
                } else if (roll >= 10) {
                    playAnimation("swordAttack3", 20);
                } else {
                    playAnimation("swordAttack4", 20);
                }
            }
            if (getMainHandItem().isEmpty()) {
                playAnimation(roll <= 30 ? "attack1" : "attack2", 5);
                return super.doHurtTarget(target);
            }
            damageMainHandWeapon();
        }
        return true;
    }

    private void startSpecialAttack(int roll) {
        specialAttackCooldown = SPECIAL_COOLDOWN;
        specialAttackTick = 20;
        String animation;
        int animationTicks = 20;
        if (roll > 36) {
            specialAttackUse = 1;
            animation = "pushingAttack";
        } else if (roll > 32) {
            specialAttackUse = 2;
            animation = "tripAttack";
        } else if (roll > 28) {
            specialAttackUse = 3;
            animation = "precisionAttack";
        } else if (roll > 24) {
            specialAttackUse = 4;
            animation = "sweepingAttack";
        } else if (roll > 20) {
            specialAttackUse = 5;
            animation = "menacingAttack";
        } else if (roll > 16) {
            specialAttackUse = 6;
            animation = "maneuveringAttack";
        } else if (roll > 12) {
            specialAttackTick = 25;
            specialAttackUse = 8;
            animation = "goadingAttack";
            animationTicks = 25;
        } else if (roll > 8) {
            specialAttackTick = 25;
            specialAttackUse = 9;
            animation = "feintingAttack";
            animationTicks = 25;
        } else if (roll > 4) {
            specialAttackUse = 10;
            animation = "disarmingAttack";
        } else {
            specialAttackUse = 11;
            animation = "distractingStrike";
        }
        showSpellMark(attackDisplay(specialAttackUse));
        playAnimation(animation, animationTicks);
        swing(InteractionHand.MAIN_HAND, true);
    }

    private void rollActionSurge() {
        boolean surged;
        if (berserkActive()) {
            surged = true;
            actionSurge = Math.min(actionSurge + 1, 10);
        } else {
            float roll = random.nextFloat();
            if (actionSurge <= 0) {
                surged = roll <= 0.25F;
                actionSurge = surged ? 1 : 0;
            } else if (actionSurge == 1) {
                surged = roll <= 0.75F;
                actionSurge = surged ? 2 : 0;
            } else if (actionSurge == 2) {
                surged = roll <= 0.25F;
                actionSurge = surged ? 3 : 0;
            } else if (actionSurge < 10) {
                surged = roll <= 0.1F;
                actionSurge = surged ? actionSurge + 1 : 0;
            } else {
                surged = false;
                actionSurge = 0;
            }
        }
        if (surged) {
            if (!isSilent()) {
                playSound(jerotesSound("sediment_lord_action", SoundEvents.PLAYER_ATTACK_CRIT), 5.0F, 1.0F);
            }
            showSpellMark(jerotesCoreParticle("action_surge_display", ParticleTypes.CRIT));
        }
    }

    private void showSpellMark(ParticleOptions particle) {
        if (isInvisible()) {
            return;
        }
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particle, getX(), getBoundingBox().maxY + 0.5D, getZ(),
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private ParticleOptions attackDisplay(int use) {
        return switch (use) {
            case 1 -> jerotesParticle("pushing_attack_display", ParticleTypes.CRIT);
            case 2 -> jerotesParticle("trip_attack_display", ParticleTypes.CRIT);
            case 3 -> jerotesParticle("precision_attack_display", ParticleTypes.CRIT);
            case 4 -> jerotesParticle("sweeping_attack_display", ParticleTypes.SWEEP_ATTACK);
            case 5 -> jerotesParticle("menacing_attack_display", ParticleTypes.CRIT);
            case 6 -> jerotesParticle("maneuvering_attack_display", ParticleTypes.CRIT);
            case 7 -> jerotesParticle("lunging_attack_display", ParticleTypes.CRIT);
            case 8 -> jerotesParticle("goading_attack_display", ParticleTypes.CRIT);
            case 9 -> jerotesParticle("feinting_attack_display", ParticleTypes.CRIT);
            case 10 -> jerotesParticle("disarming_attack_display", ParticleTypes.CRIT);
            case 11 -> jerotesParticle("distracting_strike_display", ParticleTypes.CRIT);
            default -> ParticleTypes.CRIT;
        };
    }

    public boolean specialAction() {
        return leadUseTick > 0 || shieldAttackTick > 0
                || (specialAttackTick > 0 && specialAttackUse != 7);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (specialAction()) {
            getNavigation().stop();
        }

        if (attackTick > 0) {
            attackTick--;
        }
        if (attackTick == 10 && isAlive() && specialAttackTick <= 0 && !getMainHandItem().isEmpty()) {
            trueHurt();
        }
        if (specialAttackTick == 10 && isAlive()) {
            trueHurtSpecial(specialAttackUse);
        }
        if ((attackTick > 18 || specialAttackTick > 15) && getTarget() != null) {
            lookAt(getTarget(), 360.0F, 360.0F);
            getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
        }
        if (specialAttackTick <= 13 && specialAttackTick > 10 && specialAttackUse == 7) {
            rushAttack();
        }
        if (specialAttackTick > 0 && specialAttackUse == 7 && level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; ++i) {
                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        getRandomX(0.25D) - getDeltaMovement().x,
                        getRandomY() - 0.5D - getDeltaMovement().y,
                        getRandomZ(0.25D) - getDeltaMovement().z,
                        0, getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z, -0.5D);
            }
        }

        if (isAlive()) {
            updateLead();
            if (shieldAttackTick > 0 && shieldAttackTick < 60 && shieldAttackTick % 5 == 0) {
                shieldAttack();
            }
            LivingEntity target = getTarget();
            if (target != null && !specialAction() && attackTick <= 0 && actionSurge <= 0
                    && specialAttackCooldown <= 0 && hasLineOfSight(target)
                    && distanceToSqr(target) > 9.0D && distanceToSqr(target) < 36.0D
                    && random.nextInt(berserkActive() ? 90 : 120) == 1) {
                attackTick = 20;
                specialAttackCooldown = SPECIAL_COOLDOWN;
                specialAttackTick = 20;
                specialAttackUse = 7;
                swing(InteractionHand.MAIN_HAND, true);
                showSpellMark(attackDisplay(7));
                playAnimation("lungingAttack", 20);
            }
        }

        if (specialAttackTick > 0) {
            specialAttackTick--;
        }
        if (specialAttackCooldown > 0) {
            specialAttackCooldown--;
        }
        if (specialBlockTick > 0) {
            specialBlockTick--;
        }
        if (shieldAttackTick > -20) {
            shieldAttackTick--;
        }
        if (startTick > 0) {
            if (startTick == 40) {
                playAnimation("start", 40);
            }
            startTick--;
        }
        if (animTick > 0) {
            animTick--;
            if (animTick <= 0 && isAlive()) {
                setAnimState(0);
            }
        }
        if (isAlive() && !getMainHandItem().isEmpty() && !specialAction()
                && attackTick <= 0 && specialAttackTick <= 0
                && getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D) {
            setAttackWalkAnim(Math.min(getAttackWalkAnim() + 1, 10));
        } else {
            setAttackWalkAnim(Math.max(getAttackWalkAnim() - 1, 0));
        }
    }

    private void updateLead() {
        LivingEntity target = getTarget();
        int leadCooldown = soulJarBoon ? LEAD_COOLDOWN_SOUL_JAR : LEAD_COOLDOWN_TICKS;
        if (target != null && target.isAlive() && leadTick >= leadCooldown && !specialAction()
                && attackTick <= 0 && distanceTo(target) > 2.0D && distanceTo(target) < 32.0D
                && random.nextInt(soulJarBoon ? 15 : 20) == 1) {
            leadUseTick = 25;
            lookAt(target, 360.0F, 360.0F);
        }
        if (leadUseTick == 25) {
            leadTick = 0;
            startLeadAnimation();
            if (target != null) {
                lookAt(target, 360.0F, 360.0F);
            }
        }
        if (leadUseTick == 15) {
            sedimentOrder();
            playLeadSound();
        }
        leadTick = Math.min(leadCooldown, leadTick + 1);
        leadUseTick = Math.max(0, leadUseTick - 1);
    }

    private void startLeadAnimation() {
        setCastTicks(25);
        playAnimation("lead", 25);
    }

    private void playLeadSound() {
        if (!isSilent()) {
            playSound(jerotesSound("sediment_lord_lead", SoundEvents.EVOKER_CAST_SPELL), 10.0F, 1.0F);
        }
    }

    private void sedimentOrder() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        LivingEntity target = getTarget();
        spawnSedimentOrderLine(serverLevel, target);
        int count = 4 + random.nextInt(3);
        int guardSlots = ORDERED_GUARD_CAP - countOwnedByType(SedimentGuardServant.class);
        int guardCount = Math.max(0, Math.min(count, guardSlots));
        boolean[] asStabilizer = new boolean[guardCount];
        boolean anyGuard = false;
        for (int i = 0; i < guardCount; ++i) {
            asStabilizer[i] = soulStabilizerFocusBoon
                    && random.nextFloat() < STABILIZER_FOCUS_SWAP_CHANCE;
            if (!asStabilizer[i]) {
                anyGuard = true;
            }
        }
        if (soulStabilizerFocusBoon && !anyGuard && guardCount > 0) {
            asStabilizer[0] = false;
        }
        boolean bowAssigned = false;
        for (int i = 0; i < guardCount; ++i) {
            AbstractSedimentServant unit = asStabilizer[i]
                    ? ModEntityTypes.SEDIMENT_SOUL_STABILIZER_SERVANT.get().create(serverLevel)
                    : ModEntityTypes.SEDIMENT_GUARD_SERVANT.get().create(serverLevel);
            if (unit == null) {
                continue;
            }
            boolean giveBow = false;
            if (soulStabilizerFocusBoon && !bowAssigned && unit instanceof SedimentGuardServant) {
                giveBow = true;
                bowAssigned = true;
            }
            spawnOrderedUnit(serverLevel, unit, giveBow);
        }
        int heavySlots = ORDERED_HEAVY_CAP - countOwnedByType(HeavyArmoredSedimentGuardServant.class);
        if (heavySlots > 0) {
            if (heavyGuardFocusBoon) {
                int heavies = HEAVY_FOCUS_GUARANTEED
                        + (random.nextFloat() < HEAVY_FOCUS_SECOND_CHANCE ? 1 : 0);
                for (int i = 0; i < heavies && i < heavySlots; ++i) {
                    summonOrderedHeavy(serverLevel);
                }
            } else if (random.nextFloat() < ORDERED_HEAVY_CHANCE) {
                summonOrderedHeavy(serverLevel);
            }
        }
        for (AbstractSedimentServant ally : serverLevel.getEntitiesOfClass(AbstractSedimentServant.class,
                getBoundingBox().inflate(32.0D))) {
            if (ally == this || !ally.isAlliedTo(this)) {
                continue;
            }
            ally.setAngryTick(3600);
        }
        if (netherStarBoon) {
            MobEffect necrosis = goetyEffect("necrosis");
            for (AbstractSedimentServant ally : serverLevel.getEntitiesOfClass(AbstractSedimentServant.class,
                    getBoundingBox().inflate(32.0D))) {
                if (ally != this && !ally.isAlliedTo(this)) {
                    continue;
                }
                if (necrosis != null) {
                    ally.addEffect(new MobEffectInstance(necrosis, 30 * 20, 0));
                }
            }
            MobEffect shielded = goetyEffect("shielded");
            MobEffect rallying = goetyEffect("rallying");
            if (shielded != null) {
                addEffect(new MobEffectInstance(shielded, 15 * 20, 1));
            }
            if (rallying != null) {
                addEffect(new MobEffectInstance(rallying, 15 * 20, 0));
            }
        }
        if (target != null) {
            serverLevel.sendParticles(jerotesParticle("target", ParticleTypes.CRIT),
                    target.getX(), target.getY() + 0.1D, target.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        serverLevel.gameEvent(GameEvent.ENTITY_PLACE, blockPosition(), GameEvent.Context.of(this));
        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(1.0D), getZ(),
                24, 0.5D, 0.6D, 0.5D, 0.02D);
        showSpellMark(jerotesParticle("sediment_order_display", ParticleTypes.SOUL_FIRE_FLAME));
    }

    private void spawnSedimentOrderLine(ServerLevel serverLevel, LivingEntity target) {
        if (target == null) {
            return;
        }
        Vec3 from = getEyePosition();
        Vec3 offset = target.getEyePosition().subtract(from);
        double length = offset.length();
        if (length < 1.0E-4D) {
            return;
        }
        Vec3 dir = offset.scale(1.0D / length);
        int points = (int) Math.floor(length) * 6;
        for (int i = 1; i <= points; ++i) {
            Vec3 pos = from.add(dir.scale(i / 6.0D));
            serverLevel.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y, pos.z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private int orderedLifespan(int base) {
        int lifespan = base;
        if (soulJarBoon) {
            lifespan += 30 * 20;
        }
        if (berserkBoon) {
            lifespan += 60 * 20;
        }
        return lifespan;
    }

    private int countOwnedByType(Class<? extends AbstractSedimentServant> type) {
        int n = 0;
        for (AbstractSedimentServant owned : level().getEntitiesOfClass(AbstractSedimentServant.class,
                getBoundingBox().inflate(96.0D, 96.0D, 96.0D))) {
            if (owned != this && type.isInstance(owned) && owned.isAlive() && owned.getTrueOwner() == this) {
                ++n;
            }
        }
        return n;
    }

    private void spawnOrderedUnit(ServerLevel serverLevel,
            AbstractSedimentServant unit, boolean giveBow) {
        BlockPos spawnPos = BlockFinder.SummonRadius(blockPosition(), unit, serverLevel, 4, 8);
        if (spawnPos == null) {
            spawnPos = blockPosition().offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
        }
        unit.moveTo(spawnPos, getYRot(), 0.0F);
        MobUtil.moveDownToGround(unit);
        unit.setTrueOwner(this);
        unit.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos),
                MobSpawnType.MOB_SUMMONED, null, null);
        unit.setTrueOwner(this);
        if (giveBow) {
            ItemStack bow = ancientSecondRounderBow();
            if (!bow.isEmpty()) {
                unit.setItemSlot(EquipmentSlot.MAINHAND, bow);
            }
        }
        if (unit instanceof SedimentGuardServant guard
                && RingServantGear.handles(RingServantGear.chainRing(this))) {
            guard.equipGuaranteedShield();
        }
        unit.setLimitedLife(orderedLifespan(ORDERED_GUARD_LIFESPAN));
        unit.setPersistenceRequired();
        unit.setAngryTick(3600);
        if (serverLevel.addFreshEntity(unit)) {
            GoetySummonFx.play(serverLevel, unit);
        } else {
            unit.discard();
        }
    }

    private void summonOrderedHeavy(ServerLevel serverLevel) {
        HeavyArmoredSedimentGuardServant heavy =
                ModEntityTypes.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT.get().create(serverLevel);
        if (heavy == null) {
            return;
        }
        BlockPos spawnPos = BlockFinder.SummonRadius(blockPosition(), heavy, serverLevel, 4, 8);
        if (spawnPos == null) {
            spawnPos = blockPosition().offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
        }
        heavy.moveTo(spawnPos, getYRot(), 0.0F);
        MobUtil.moveDownToGround(heavy);
        heavy.setTrueOwner(this);
        heavy.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos),
                MobSpawnType.MOB_SUMMONED, null, null);
        heavy.setTrueOwner(this);
        heavy.setLimitedLife(orderedLifespan(ORDERED_HEAVY_LIFESPAN));
        heavy.setPersistenceRequired();
        heavy.setAngryTick(3600);
        if (serverLevel.addFreshEntity(heavy)) {
            GoetySummonFx.play(serverLevel, heavy);
        } else {
            heavy.discard();
        }
    }

    private void trueHurt() {
        playSound(jerotesSound("sediment_lord_attack", SoundEvents.PLAYER_ATTACK_SWEEP), 5.0F, 1.0F);
        for (LivingEntity hurt : level().getEntitiesOfClass(LivingEntity.class, lordAttackBox().inflate(0.5D))) {
            if (!validTarget(hurt) || !hasLineOfSight(hurt)) {
                continue;
            }
            super.doHurtTarget(hurt);
        }
        sweepParticles(0.5F);
    }

    private void trueHurtSpecial(int use) {
        playSound(jerotesSound("sediment_lord_attack", SoundEvents.PLAYER_ATTACK_SWEEP), 5.0F, 1.0F);
        playSound(jerotesSound(specialSound(use), SoundEvents.PLAYER_ATTACK_SWEEP), 5.0F, 1.0F);
        int spellLevel = effectLevel();
        float damageMulti = 1.25F;
        float knockbackMulti = 1.25F;
        float reach = 0.75F;
        if (use == 1) {
            damageMulti = 1.5F;
            knockbackMulti = 2.0F;
        } else if (use == 2) {
            knockbackMulti = 0.05F;
        } else if (use == 3) {
            damageMulti = 2.0F;
            reach = 1.0F;
        } else if (use == 4) {
            reach = 1.5F;
        }
        if (berserkActive() && (use == 1 || use == 2 || use == 3 || use == 4 || use == 8 || use == 10)) {
            damageMulti += 0.25F;
        }
        boolean success = false;
        float baseDamage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        for (LivingEntity hurt : level().getEntitiesOfClass(LivingEntity.class, lordAttackBox().inflate(reach))) {
            if (!validTarget(hurt) || !hasLineOfSight(hurt)) {
                continue;
            }
            if (!hurt.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), baseDamage * damageMulti)) {
                continue;
            }
            success = true;
            knockTarget(hurt, knockbackMulti);
            if (use == 1) {
                pushTargetAway(hurt, knockbackMulti);
            } else if (use == 2) {
                applyJerotesEffect(hurt, "prone", spellLevel * 20, 0);
            } else if (use == 5) {
                applyJerotesEffect(hurt, "aback", spellLevel * 40, spellLevel / 2);
            } else if (use == 8 && berserkActive()) {
                applyGoetyEffect(hurt, "wane", spellLevel * 20, 0);
            } else if (use == 9 && hurt instanceof Mob mob && mob.getTarget() != this) {
                if (berserkActive()) {
                    applyGoetyEffect(hurt, "wane", spellLevel * 20, spellLevel / 2);
                } else {
                    hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, spellLevel * 20, spellLevel / 2));
                }
            } else if (use == 10) {
                disarm(hurt);
            }
        }
        if (success) {
            if (use == 6) {
                maneuveringBuff(32.0D, spellLevel);
            } else if (use == 11) {
                buffAllies(MobEffects.DAMAGE_BOOST, 16.0D, spellLevel);
            }
        }
        sweepParticles(reach);
    }

    private void sweepParticles(float reach) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, getX(), getY(0.5D), getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void knockTarget(LivingEntity target, float multi) {
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        double length = Math.max(1.0E-4D, Math.sqrt(dx * dx + dz * dz));
        double strength = 0.45D * multi;
        target.push(dx / length * strength, 0.12D * Math.min(multi, 1.5F), dz / length * strength);
        target.hurtMarked = true;
    }

    private void pushTargetAway(LivingEntity target, float multi) {
        net.minecraft.world.phys.AABB box = target.getBoundingBox();
        if (box.getXsize() > 2.6D || box.getYsize() > 3.6D) {
            return;
        }
        double factor = (0.5D / 1.5D) * (0.7D + multi / 3.0D);
        target.setOnGround(false);
        target.setDeltaMovement(target.getDeltaMovement().add(
                -(getX() - target.getX()) * factor,
                -(getY() - target.getY()) * factor,
                -(getZ() - target.getZ()) * factor));
        target.hurtMarked = true;
    }

    private void maneuveringBuff(double radius, int spellLevel) {
        int duration = spellLevel * 20;
        for (LivingEntity ally : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(radius))) {
            if (ally == this || !isAlliedTo(ally)) {
                continue;
            }
            ally.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration, spellLevel / 2));
            if (berserkActive()) {
                applyGoetyEffect(ally, "charged", duration, 0);
            }
            break;
        }
        if (berserkActive()) {
            for (AbstractSedimentServant ally : level().getEntitiesOfClass(AbstractSedimentServant.class,
                    getBoundingBox().inflate(6.0D))) {
                if (ally == this || !ally.isAlliedTo(this)) {
                    continue;
                }
                ally.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration / 2, 0));
            }
        }
    }

    private void buffAllies(MobEffect effect, double radius, int spellLevel) {
        int duration = spellLevel * 20;
        int amplifier = berserkActive() ? 1 : spellLevel / 2;
        for (LivingEntity ally : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(radius))) {
            if (ally == this || !isAlliedTo(ally)) {
                continue;
            }
            ally.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }
    }

    private int effectLevel() {
        Difficulty difficulty = level().getDifficulty();
        if (difficulty == Difficulty.HARD) {
            return SPELL_LEVEL + 1;
        }
        if (difficulty == Difficulty.EASY || difficulty == Difficulty.PEACEFUL) {
            return SPELL_LEVEL - 1;
        }
        return SPELL_LEVEL;
    }

    private void disarm(LivingEntity target) {
        if (level().isClientSide || !level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (target instanceof Player player
                && (level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || player.getAbilities().instabuild)) {
            return;
        }
        ItemStack weapon = target.getMainHandItem();
        if (weapon.isEmpty() || weapon.getRarity() == Rarity.EPIC) {
            return;
        }
        target.spawnAtLocation(weapon);
        target.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    private void rushAttack() {
        if (getTarget() != null) {
            lookAt(getTarget(), 360.0F, 360.0F);
        }
        float yaw = getYRot() * (float) DEG_TO_RAD;
        float pitch = getXRot() * (float) DEG_TO_RAD;
        float fx = -net.minecraft.util.Mth.sin(yaw) * net.minecraft.util.Mth.cos(pitch);
        float fy = -net.minecraft.util.Mth.sin(pitch);
        float fz = net.minecraft.util.Mth.cos(yaw) * net.minecraft.util.Mth.cos(pitch);
        float length = net.minecraft.util.Mth.sqrt(fx * fx + fy * fy + fz * fz);
        if (length < 1.0E-4F) {
            return;
        }
        float speed = 0.35F / length * 2.0F;
        push(fx * speed, fy * speed, fz * speed);
        hurtMarked = true;
    }

    private void shieldAttack() {
        swing(InteractionHand.MAIN_HAND, true);
        playSound(SoundEvents.PLAYER_ATTACK_CRIT, 10.0F, 1.0F);
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        for (LivingEntity hurt : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.0D))) {
            if (!validTarget(hurt)) {
                continue;
            }
            hurt.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), damage);
            knockTarget(hurt, 0.5F);
            if (hurt.getUseItem().getItem() instanceof ShieldItem) {
                if (hurt instanceof Player player) {
                    player.disableShield(true);
                } else {
                    hurt.stopUsingItem();
                }
            }
        }
        sweepParticles(2.0F);
    }

    @Override
    public void blockedByShield(LivingEntity target) {
        super.blockedByShield(target);
        if (!level().isClientSide && shieldAttackTick <= -20) {
            shieldAttackTick = 60;
            playSound(jerotesSound("sediment_lord_special", SoundEvents.SHIELD_BREAK), 10.0F, 1.0F);
            showSpellMark(jerotesParticle("shield_piercing_combo_display", ParticleTypes.CRIT));
            playAnimation("shieldAttack", 70);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return isJerotesEntity(source.getDirectEntity(), "ancient_second_rounder_arrow")
                || isJerotesEntity(source.getEntity(), "ancient_second_rounder_arrow")
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        if (berserkActive() && amount > 0.0F) {
            if (source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE)) {
                amount *= 1.5F;
            } else if (isMagicDamage(source)
                    || source.is(net.minecraft.tags.DamageTypeTags.IS_EXPLOSION)
                    || source.is(net.minecraft.tags.DamageTypeTags.IS_FREEZING)) {
                amount *= 0.75F;
            }
        }
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FALL)) {
            amount /= 3.0F;
        }
        if (isDamageSourceBlocks(source) && specialBlockTick > 0) {
            amount /= 2.0F;
        }
        if (!level().isClientSide && specialBlockTick <= 0 && !specialAction() && isAlive()
                && getTarget() != null && getTarget().isAlive() && !getMainHandItem().isEmpty()
                && !(getOffhandItem().getItem() instanceof ShieldItem)
                && isDamageSourceBlocks(source)) {
            int origin;
            int maxRoll;
            double threshold;
            if (berserkActive()) {
                origin = 5;
                maxRoll = Math.max(origin + 1, (int) (getAttributeValue(Attributes.ATTACK_DAMAGE) * 12.5D));
                threshold = amount * 3.0D;
            } else {
                origin = 1;
                maxRoll = Math.max(2, (int) getAttributeValue(Attributes.ATTACK_DAMAGE) * 10);
                threshold = amount * 3.5D;
            }
            if (random.nextInt(origin, maxRoll) > threshold) {
                specialBlockTick = 5;
                playSound(jerotesSound("sediment_lord_block", SoundEvents.SHIELD_BLOCK), 10.0F, 1.0F);
                triggerBlockAnimation(source);
                return false;
            }
        }
        if (shieldAttackTick > 0 && !level().isClientSide) {
            shieldAttackTick = 0;
        }
        if (source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR)
                && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            amount /= 2.0F;
        }
        return super.hurt(source, amount);
    }

    private static boolean isMagicDamage(DamageSource source) {
        if (source.is(net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO)) {
            return true;
        }
        Entity direct = source.getDirectEntity();
        Entity causing = source.getEntity();
        return direct instanceof net.minecraft.world.entity.projectile.ThrownPotion
                || causing instanceof net.minecraft.world.entity.projectile.ThrownPotion
                || direct instanceof net.minecraft.world.entity.AreaEffectCloud
                || causing instanceof net.minecraft.world.entity.AreaEffectCloud
                || direct instanceof net.minecraft.world.entity.projectile.AbstractHurtingProjectile
                || causing instanceof net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
    }

    @Override
    public boolean canRaiseShield() {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return specialBlockTick > 4 || super.isBlocking();
    }

    private void triggerBlockAnimation(DamageSource source) {
        level().broadcastEntityEvent(this, (byte) 101);
        JerotesTwoHandedEffects.blockUse(this, getMainHandItem(), source.getEntity(), source);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 101) {
            int roll = random.nextInt(30);
            AnimationState block = roll > 20 ? block1AnimationState
                    : roll > 10 ? block2AnimationState : block3AnimationState;
            block.start(tickCount);
            return;
        }
        super.handleEntityEvent(id);
    }

    private void damageMainHandWeapon() {
        if (level().isClientSide() || !JerotesBreakRules.meleeCanBreak(level())) {
            return;
        }
        ItemStack hand = getMainHandItem();
        if (!hand.isEmpty() && hand.isDamageableItem()) {
            hand.hurtAndBreak(1, this, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public void travel(net.minecraft.world.phys.Vec3 movement) {
        super.travel(movement);
        if (specialAction()) {
            setDeltaMovement(getDeltaMovement().multiply(0.05D, 1.0D, 0.05D));
        }
    }

    private boolean isDamageSourceBlocks(DamageSource source) {
        if (source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_SHIELD)) {
            return false;
        }
        Vec3 sourcePos = source.getSourcePosition();
        if (sourcePos == null) {
            return false;
        }
        Vec3 toSource = new Vec3(sourcePos.x - getX(), 0.0D, sourcePos.z - getZ());
        if (toSource.lengthSqr() < 1.0E-6D) {
            return true;
        }
        return getLookAngle().dot(toSource.normalize()) > 0.0D;
    }

    private boolean validTarget(LivingEntity candidate) {
        return candidate != null && candidate != this && candidate.isAlive()
                && candidate != getTrueOwner() && !isAlliedTo(candidate) && canAttack(candidate);
    }

    private String specialSound(int use) {
        return switch (use) {
            case 1 -> "sediment_lord_pushing";
            case 2 -> "sediment_lord_trip";
            case 3 -> "sediment_lord_precision";
            case 4 -> "sediment_lord_sweeping";
            case 5 -> "sediment_lord_menacing";
            case 6 -> "sediment_lord_maneuvering";
            case 7 -> "sediment_lord_lunging";
            case 8 -> "sediment_lord_goading";
            case 9 -> "sediment_lord_feinting";
            case 10 -> "sediment_lord_disarming";
            case 11 -> "sediment_lord_distracting";
            default -> "sediment_lord_attack";
        };
    }

    private void applyJerotesEffect(LivingEntity target, String path, int duration, int amplifier) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        if (effect != null) {
            target.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }
    }

    private MobEffect goetyEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    private void applyGoetyEffect(LivingEntity target, String path, int duration, int amplifier) {
        MobEffect effect = goetyEffect(path);
        if (effect != null) {
            target.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }
    }

    private ItemStack ancientSecondRounderBow() {
        net.minecraft.world.item.Item bow = ForgeRegistries.ITEMS.getValue(ANCIENT_BOW_ID);
        return bow == null ? ItemStack.EMPTY : new ItemStack(bow);
    }

    private static final class LordMeleeGoal extends Goal {
        private final SedimentLordServant lord;
        private final double speedModifier;
        private net.minecraft.world.level.pathfinder.Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        LordMeleeGoal(SedimentLordServant lord, double speedModifier) {
            this.lord = lord;
            this.speedModifier = speedModifier;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            long now = lord.level().getGameTime();
            if (now - lastCanUseCheck < 10L) {
                return false;
            }
            lastCanUseCheck = now;
            LivingEntity target = lord.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            path = lord.getNavigation().createPath(target, 0);
            return path != null || lord.isWithinLordReach(target);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = lord.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            return !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative());
        }

        @Override
        public void start() {
            if (path != null) {
                lord.getNavigation().moveTo(path, speedModifier);
            }
            lord.setAggressive(true);
            ticksUntilNextPathRecalculation = 0;
            ticksUntilNextAttack = 0;
        }

        @Override
        public void stop() {
            LivingEntity target = lord.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                lord.setTarget(null);
            }
            lord.setAggressive(false);
            lord.getNavigation().stop();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = lord.getTarget();
            if (target == null) {
                return;
            }
            lord.getLookControl().setLookAt(target, 30.0F, 30.0F);
            ticksUntilNextPathRecalculation = Math.max(ticksUntilNextPathRecalculation - 1, 0);
            double distanceSqr = lord.distanceToSqr(target);
            if (!lord.isWithinLordReach(target)) {
                if (lord.getSensing().hasLineOfSight(target) && ticksUntilNextPathRecalculation <= 0
                        && (pathedTargetX == 0.0D && pathedTargetY == 0.0D && pathedTargetZ == 0.0D
                                || target.distanceToSqr(pathedTargetX, pathedTargetY, pathedTargetZ) >= 1.0D
                                || lord.getRandom().nextFloat() < 0.05F)) {
                    pathedTargetX = target.getX();
                    pathedTargetY = target.getY();
                    pathedTargetZ = target.getZ();
                    ticksUntilNextPathRecalculation = 4 + lord.getRandom().nextInt(7);
                    double distance = lord.distanceTo(target);
                    if (distance > 1024.0D) {
                        ticksUntilNextPathRecalculation += 10;
                    } else if (distance > 256.0D) {
                        ticksUntilNextPathRecalculation += 5;
                    }
                    if (!lord.getNavigation().moveTo(target, speedModifier)) {
                        ticksUntilNextPathRecalculation += 15;
                    }
                    ticksUntilNextPathRecalculation = adjustedTickDelay(ticksUntilNextPathRecalculation);
                }
                strafingTime = -1;
            } else {
                lord.getNavigation().stop();
                if (strafingTime >= 10) {
                    if (lord.getRandom().nextFloat() < 0.3F) {
                        strafingClockwise = !strafingClockwise;
                    }
                    if (lord.getRandom().nextFloat() < 0.3F) {
                        strafingBackwards = !strafingBackwards;
                    }
                    strafingTime = 0;
                }
                ++strafingTime;
                if (distanceSqr > 6.75D) {
                    strafingBackwards = false;
                } else if (distanceSqr < 2.25D) {
                    strafingBackwards = true;
                }
                lord.getMoveControl().strafe(strafingBackwards ? -0.2F : 0.2F,
                        strafingClockwise ? 0.2F : -0.2F);
            }
            ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
            checkAndPerformAttack(target);
        }

        private void checkAndPerformAttack(LivingEntity target) {
            if (ticksUntilNextAttack <= 0 && lord.isWithinLordReach(target)
                    && lord.getSensing().hasLineOfSight(target)) {
                ticksUntilNextAttack = adjustedTickDelay(
                        lord.actionSurge > 0 ? SURGE_ATTACK_INTERVAL : ATTACK_INTERVAL);
                lord.swing(InteractionHand.MAIN_HAND, true);
                lord.doHurtTarget(target);
            }
        }
    }
}
