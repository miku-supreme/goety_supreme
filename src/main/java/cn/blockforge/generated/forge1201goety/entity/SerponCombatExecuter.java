package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.CuriosFinder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.MountSummonSpell;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.entity.SerpenperthMeteorEntity;
import cn.blockforge.generated.goetysupreme.data.SerponAwayData;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponSiroccoEntity;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public class SerponCombatExecuter extends AbstractSerpon {

    @Override
    public boolean acceptsEquipment(ItemStack stack) {
        if (equipSlotFor(stack) == EquipmentSlot.MAINHAND
                && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).isEmpty()) {
            return true;
        }
        if (!isAnyJerotesItem(stack, "serpon_powerhouse_helmet", "serpon_powerhouse_chestplate",
                "serpon_powerhouse_leggings", "serpon_powerhouse_boots")) {
            return false;
        }
        return hasFireSecretorSerpentTrim(stack);
    }

    @Override
    public boolean supportsTwoHandedBlock() {
        return false;
    }

    @Override
    public void buildOffers(net.minecraft.world.item.trading.MerchantOffers offers) {
        ItemStack price = jerotesStack("serpon_alloy_ingot", 64);
        if (price.isEmpty()) {
            return;
        }
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                price.copy(), jerotesStack("serpon_credential", 1), 1, 30, 0.0F), 12000, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                price.copy(), jerotesStack("serpent_dominater_armor_trim_smithing_template", 1),
                1, 30, 0.0F), 12000, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                price.copy(), jerotesStack("serpon_combat_executer_medal", 1), 1, 50, 0.0F), 0, true);
    }

    private boolean hasFireSecretorSerpentTrim(ItemStack stack) {
        java.util.Optional<net.minecraft.world.item.armortrim.ArmorTrim> trim =
                net.minecraft.world.item.armortrim.ArmorTrim.getTrim(level().registryAccess(), stack);
        return trim.isPresent()
                && isRegistryKey(trim.get().pattern(), "jerotesvillage", "serpent_dominater")
                && isRegistryKey(trim.get().material(), "jerotesvillage", "fire_secretor_adhesive");
    }

    private static boolean isRegistryKey(net.minecraft.core.Holder<?> holder, String namespace, String path) {
        return holder.unwrapKey()
                .map(key -> key.location().equals(ResourceLocation.fromNamespaceAndPath(namespace, path)))
                .orElse(false);
    }

    private static final double FOLLOW_RANGE = 32.0D;

    private static final int SPELL_LEVEL = 6;

    private static final int SPELL_WINDUP = 60;

    private static final int SPELL_COOLDOWN = 240;

    private static final double SPELL_RANGE = 64.0D;

    private static final int PIKE_SUMMON_DISTANCE = 4;

    private static final double PIKE_SUMMON_HEIGHT = 12.0D;

    private static final float FLAME_SLASH_CHANCE = 0.1F;
    private static final float WHIRLWIND_SLASH_CHANCE = 0.1F;

    private static final int SPECIAL_MELEE_CHARGES = 3;

    private static final double RUSH_RANGE = 12.0D;
    private static final int RUSH_CHANCE = 40;

    private static final double FURY_ARMOR_BONUS = 13.0D;
    private static final double FURY_ATTACK_BONUS = 4.0D;
    private static final double FURY_KNOCKBACK_BONUS = 0.2D;

    private static final int LAST_STAND_TICKS = 160;

    private static final double UNCLEAN_BLOOD_HEALTH_BONUS = 30.0D;
    private static final float UNCLEAN_BLOOD_STRENGTH_BONUS = 0.5F;
    private static final int UNCLEAN_BLOOD_RUSH_CHANCE = 30;
    private static final double UNCLEAN_BLOOD_RUSH_RANGE = 9.0D;
    private static final float UNCLEAN_BLOOD_SLASH_CHANCE = 0.125F;
    private static final double UNCLEAN_BLOOD_FURY_ARMOR_BONUS = 15.0D;
    private static final double UNCLEAN_BLOOD_FURY_ATTACK_BONUS = 4.0D;
    private static final double UNCLEAN_BLOOD_FURY_KNOCKBACK_BONUS = 0.3D;
    private static final int UNCLEAN_BLOOD_LAST_STAND_TICKS = 200;

    private static final double DROUGHT_HELM_HEALTH_BONUS = 20.0D;
    private static final float DROUGHT_HELM_STRENGTH_BONUS = 0.25F;
    private static final int DROUGHT_HELM_SPELL_COOLDOWN = 10 * 20;
    private static final int DROUGHT_HELM_SPELL_CHANCE_DENOM = 100;
    private static final int DROUGHT_HELM_HOT_WIND_MIN = 65;
    private static final int DROUGHT_HELM_PIKE_MIN = 30;
    private static final int DROUGHT_HELM_FURY_BLOCK_TICKS = 3;
    private static final double DROUGHT_HELM_BLOCK_ATTACK_FACTOR = 12.5D;

    private static final UUID UNCLEAN_BLOOD_HEALTH_ID =
            UUID.fromString("0a7c4f1e-3d21-4f6a-9a41-26b1e0f2c011");
    private static final UUID DROUGHT_HELM_HEALTH_ID =
            UUID.fromString("0a7c4f1e-3d21-4f6a-9a41-26b1e0f2c012");

    private static final int[] WAVE_THRESHOLDS = {75, 50, 25};
    private static final int MAX_REINFORCEMENTS = 6;

    private static final int REINFORCEMENT_COOLDOWN_TICKS = 120 * 20;

    private static final double WEAPON_REACH = 3.2D;

    private static final float SOUND_VOLUME = 5.0F;

    private static final float FIRE_ABSORPTION_PERCENTAGE = 100.0F;

    private static final float BLOCK_DESTROY_STRENGTH = 5.0F;

    private static final boolean BLOCK_DESTROY_ENABLED = false;

    private static final double BLOCK_DESTROY_COOLDOWN = 30.0D;

    private static final TagKey<net.minecraft.world.level.block.Block> SHAPELESS_AS_AIR =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("jerotes", "shapeless_as_air"));

    private static final TagKey<Biome> SERPON_FIND_BIOME =
            TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("jerotesvillage", "serpon_find"));

    private static final UUID FURY_ARMOR_ID = UUID.fromString("0a7c4f1e-3d21-4f6a-9a41-26b1e0f2c001");
    private static final UUID FURY_ATTACK_ID = UUID.fromString("0a7c4f1e-3d21-4f6a-9a41-26b1e0f2c002");
    private static final UUID FURY_KNOCKBACK_ID = UUID.fromString("0a7c4f1e-3d21-4f6a-9a41-26b1e0f2c003");
    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(SerponCombatExecuter.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> DATA_SERPON_POWER =
            SynchedEntityData.defineId(SerponCombatExecuter.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> DATA_SPELL_CHANCE =
            SynchedEntityData.defineId(SerponCombatExecuter.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState swordAttack1AnimationState = new AnimationState();
    public final AnimationState swordAttack2AnimationState = new AnimationState();
    public final AnimationState swordAttack3AnimationState = new AnimationState();
    public final AnimationState swordAttack4AnimationState = new AnimationState();
    public final AnimationState block1AnimationState = new AnimationState();
    public final AnimationState block2AnimationState = new AnimationState();
    public final AnimationState block3AnimationState = new AnimationState();
    public final AnimationState flameAttackAnimationState = new AnimationState();
    public final AnimationState windAttackAnimationState = new AnimationState();
    public final AnimationState hotWindSpellAnimationState = new AnimationState();
    public final AnimationState pikeSpellAnimationState = new AnimationState();
    public final AnimationState meteorSpellAnimationState = new AnimationState();
    public final AnimationState pushAnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    private int animTick;

    private int attackTick;

    private int flameAttackTick;
    private int windAttackTick;

    private int specialMeleeChance;

    private int spellTick;

    private int spellChance;
    private int spellCooldown;
    private boolean fury;

    private boolean lastStand;
    private int lastStandTick;

    private final int[] reinforcementCooldowns = new int[WAVE_THRESHOLDS.length];
    private final boolean[] reinforcementArmed = new boolean[WAVE_THRESHOLDS.length];

    private boolean uncleanBloodBoon;
    private boolean droughtHelmBoon;

    private double blockDestroyTick;

    private float strengthCache = 1.0F;
    private int strengthCacheTick = -1;

    private int curseTick;

    private int curseActiveTick;

    private boolean trueCome;

    public SerponCombatExecuter(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 700;
        this.curseTick = CURSE_START_TICKS;
        setShieldLevel(5);
        java.util.Arrays.fill(reinforcementArmed, true);
    }

    @Override
    public boolean supportsRangedWeapons() {
        return false;
    }

    @Override
    public boolean supportsSpellAttack() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        LivingEntity owner = getTrueOwner();
        if (owner == player && isAlive() && !player.isSpectator()) {
            ItemStack held = player.getItemInHand(hand);
            if (giveBoons(player, held)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    private boolean giveBoons(Player player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }
        if (isGoetyBlood(held)) {
            boolean wasFury = fury;
            boolean firstBlood = !uncleanBloodBoon;
            if ((!uncleanBloodBoon || wasFury) && !level().isClientSide) {
                uncleanBloodBoon = true;
                if (wasFury) {
                    setFury(false);
                }
                if (firstBlood) {
                    applyBoonModifiersAndFollowHealth(UNCLEAN_BLOOD_HEALTH_BONUS);
                } else {
                    applyBoonModifiers();
                }
                if (wasFury) {
                    setHealth(getMaxHealth());
                }
                consumeGift(player, held);
                boonFeedback(ParticleTypes.SOUL_FIRE_FLAME);
            }
            return true;
        }
        if (held.is(GeneratedMod.DROUGHT_WILDFIRE_HELM.get())) {
            if (!droughtHelmBoon && !level().isClientSide) {
                droughtHelmBoon = true;
                applyBoonModifiersAndFollowHealth(DROUGHT_HELM_HEALTH_BONUS);
                consumeGift(player, held);
                boonFeedback(ParticleTypes.FLAME);
            }
            return true;
        }
        return false;
    }

    private static boolean isGoetyBlood(ItemStack stack) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && "goety".equals(id.getNamespace()) && "unholy_blood".equals(id.getPath());
    }

    private void consumeGift(Player player, ItemStack held) {
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
    }

    private void boonFeedback(ParticleOptions particle) {
        playSound(jerotesSound("serpon_combat_executer_fury", SoundEvents.RAVAGER_ROAR), 1.0F, 1.0F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particle, getX(), getY(1.0D), getZ(), 24, 0.5D, 0.6D, 0.5D, 0.03D);
        }
    }

    private void applyBoonModifiers() {
        setHealthBoon(UNCLEAN_BLOOD_HEALTH_ID, "executer_unclean_blood", uncleanBloodBoon,
                UNCLEAN_BLOOD_HEALTH_BONUS);
        setHealthBoon(DROUGHT_HELM_HEALTH_ID, "executer_drought_helm", droughtHelmBoon,
                DROUGHT_HELM_HEALTH_BONUS);
    }

    private void applyBoonModifiersAndFollowHealth(double bonus) {
        float oldHealth = getHealth();
        applyBoonModifiers();
        setHealth(Math.min(getMaxHealth(), oldHealth + (float) bonus));
    }

    private void setHealthBoon(UUID id, String name, boolean active, double amount) {
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }
        maxHealth.removeModifier(id);
        if (active) {
            maxHealth.addPermanentModifier(new AttributeModifier(id, name, amount,
                    AttributeModifier.Operation.ADDITION));
            if (getHealth() > getMaxHealth()) {
                setHealth(getMaxHealth());
            }
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ANIM_STATE, 0);
        entityData.define(DATA_SERPON_POWER, 1.0F);
        entityData.define(DATA_SPELL_CHANCE, 0);
    }

    public int getSyncedSpellChance() {
        return entityData.get(DATA_SPELL_CHANCE);
    }

    private void setSyncedSpellChance(int value) {
        if (entityData.get(DATA_SPELL_CHANCE) != value) {
            entityData.set(DATA_SPELL_CHANCE, value);
        }
    }

    public float syncedSerponPower() {
        if (!level().isClientSide()) {
            return serponStrengthBase();
        }
        return entityData.get(DATA_SERPON_POWER);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            if (isAlive()) {
                idleAnimationState.startIfStopped(tickCount);
            }
            if (getSyncedSpellChance() == 1) {
                spawnSpellChargeParticlesClient();
            }
            return;
        }
        if (animTick > 0) {
            --animTick;
            if (animTick == 0) {
                setAnimState(0);
            }
        }
        float power = serponStrengthBase();
        if (Math.abs(entityData.get(DATA_SERPON_POWER) - power) >= 0.001F) {
            entityData.set(DATA_SERPON_POWER, power);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!DATA_ANIM_STATE.equals(key) || !level().isClientSide()) {
            return;
        }
        int id = entityData.get(DATA_ANIM_STATE);
        AnimationState state = animationForId(id);
        if (state == null) {
            stopAllAnimations();
            return;
        }
        state.startIfStopped(tickCount);
        if (id != 9 && id != 10 && id != 11 && id != 15) {
            stopMostAnimations(state);
        }
    }

    private void stopMostAnimations(AnimationState exception) {
        for (AnimationState other : allAnimations()) {
            if (other != exception && other != pushAnimationState) {
                other.stop();
            }
        }
    }

    private void stopAllAnimations() {
        for (AnimationState other : allAnimations()) {
            if (other != pushAnimationState) {
                other.stop();
            }
        }
    }

    private void playAnimation(String name, int ticks) {
        if (level().isClientSide()) {
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
            case "attack1" -> 1;
            case "attack2" -> 2;
            case "swordAttack1" -> 3;
            case "swordAttack2" -> 4;
            case "swordAttack3" -> 5;
            case "swordAttack4" -> 6;
            case "windAttack" -> 7;
            case "flameAttack" -> 8;
            case "block1" -> 9;
            case "block2" -> 10;
            case "block3" -> 11;
            case "pikeSpell" -> 12;
            case "hotWindSpell" -> 13;
            case "meteorSpell" -> 14;
            case "push" -> 15;
            case "dead" -> 16;
            default -> 0;
        };
    }

    private AnimationState animationForId(int id) {
        return switch (id) {
            case 1 -> attack1AnimationState;
            case 2 -> attack2AnimationState;
            case 3 -> swordAttack1AnimationState;
            case 4 -> swordAttack2AnimationState;
            case 5 -> swordAttack3AnimationState;
            case 6 -> swordAttack4AnimationState;
            case 7 -> windAttackAnimationState;
            case 8 -> flameAttackAnimationState;
            case 9 -> block1AnimationState;
            case 10 -> block2AnimationState;
            case 11 -> block3AnimationState;
            case 12 -> pikeSpellAnimationState;
            case 13 -> hotWindSpellAnimationState;
            case 14 -> meteorSpellAnimationState;
            case 15 -> pushAnimationState;
            case 16 -> deadAnimationState;
            default -> null;
        };
    }

    private List<AnimationState> allAnimations() {
        List<AnimationState> states = new ArrayList<>();
        states.add(attack1AnimationState);
        states.add(attack2AnimationState);
        states.add(swordAttack1AnimationState);
        states.add(swordAttack2AnimationState);
        states.add(swordAttack3AnimationState);
        states.add(swordAttack4AnimationState);
        states.add(windAttackAnimationState);
        states.add(flameAttackAnimationState);
        states.add(block1AnimationState);
        states.add(block2AnimationState);
        states.add(block3AnimationState);
        states.add(pikeSpellAnimationState);
        states.add(hotWindSpellAnimationState);
        states.add(meteorSpellAnimationState);
        states.add(pushAnimationState);
        states.add(deadAnimationState);
        return states;
    }

    @Override
    public void tickDeath() {
        if (lastStandTick > 0) {
            return;
        }
        if (deathTime <= 0) {
            playAnimation("dead", 40);
        }
        ++deathTime;
        if (deathTime >= 40 && !level().isClientSide() && !isRemoved()) {
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(serponPortalParticle(), getX(), getY(0.5D), getZ(), 1,
                        0.0D, 0.0D, 0.0D, 0.0D);
            }
            level().broadcastEntityEvent(this, (byte) 60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean warnsBeforeExecution() {
        return true;
    }

    @Override
    public void warnKill(Player player) {
        setKillChance(60);
        if (player != null) {
            player.displayClientMessage(Component.translatable(
                    "info.goety_supreme.servant.try_kill.serpon_combat_executer"), true);
        }
    }

    @Override
    public void onExecuted(Player player) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }
        if (jvillageCounterpart(getType()) == null) {
            spawnPortalBurst();
            discard();
            return;
        }
        summonReinforcementWave(serverLevel, 0);
        setTrueOwner(null);
        setOwnerId(null);
        setWandering(false);
        setStaying(false);
        if (player != null) {
            setTarget(player);
        }
        Entity hostile = spawnJvillageCounterpart(serverLevel, player, true);
        if (hostile == null) {
            spawnPortalBurst();
            discard();
            return;
        }
        if (player != null) {
            setTarget(player);
        }
        if (hostile instanceof LivingEntity living) {
            meteorWave(living);
        } else {
            meteorWave();
        }
        List<AbstractSerpon> allies = serverLevel.getEntitiesOfClass(AbstractSerpon.class,
                getBoundingBox().inflate(BETRAYAL_RADIUS), candidate -> candidate != this);
        for (AbstractSerpon ally : allies) {
            ally.convertToJvillage(serverLevel, player);
        }
        discard();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.MAX_HEALTH, 270.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_combat_executer.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_COMBAT_EXECUTER;
    }

    @Override
    public float getSoundVolume() {
        return SOUND_VOLUME;
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "serpon_powerhouse_helmet");
        equipJerotes(EquipmentSlot.CHEST, "serpon_powerhouse_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "serpon_powerhouse_leggings");
        equipJerotes(EquipmentSlot.FEET, "serpon_powerhouse_boots");
        equipJerotes(EquipmentSlot.MAINHAND, "glaive_of_serpent_dominater");
        this.applySerpentArmorTrim();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    private void applySerpentArmorTrim() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        RegistryAccess access = server.registryAccess();
        Optional<Holder.Reference<TrimPattern>> pattern = access
                .registryOrThrow(Registries.TRIM_PATTERN)
                .getHolder(ResourceKey.create(Registries.TRIM_PATTERN,
                        ResourceLocation.fromNamespaceAndPath("jerotesvillage", "serpent_dominater")));
        Optional<Holder.Reference<TrimMaterial>> material = access
                .registryOrThrow(Registries.TRIM_MATERIAL)
                .getHolder(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath("jerotesvillage", "fire_secretor_adhesive")));
        if (pattern.isEmpty() || material.isEmpty()) {
            return;
        }
        ArmorTrim trim = new ArmorTrim(material.get(), pattern.get());
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = this.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                ArmorTrim.setTrim(access, stack, trim);
            }
        }
    }

    @Override
    public net.minecraft.sounds.SoundEvent getAmbientSound() {
        return jerotesSound("serpon_combat_executer_ambient",
                super.getAmbientSound());
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("serpon_combat_executer_hurt",
                super.getHurtSound(source));
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return jerotesSound("serpon_combat_executer_death",
                super.getDeathSound());
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponCombatExecuter.this.getMeleeAttackRangeSqr(target);
            }
        });
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity target) {
        if (getMainHandItem().isEmpty()) {
            return super.getMeleeAttackRangeSqr(target);
        }
        return WEAPON_REACH * WEAPON_REACH;
    }

    private AABB attackBox() {
        AABB box = getBoundingBox().inflate(Math.sqrt(2.04D) - 0.6D, 0.0D, Math.sqrt(2.04D) - 0.6D);
        if (!getMainHandItem().isEmpty()) {
            return box.inflate(1.6D, 1.6D, 1.6D);
        }
        return box.inflate(0.5D, 0.5D, 0.5D);
    }

    private boolean specialAction() {
        return spellTick > 0 || flameAttackTick > 0 || windAttackTick > 0 || curseActiveTick > 0;
    }

    public float serponStrengthBase() {
        if (level().isClientSide()) {
            return 1.0F;
        }
        if (strengthCacheTick == tickCount) {
            return strengthCache;
        }
        strengthCacheTick = tickCount;
        float base = 1.0F;
        Holder<Biome> biome = level().getBiome(blockPosition());
        SerponAwayData awayData = SerponAwayData.get(level());
        boolean serponAway = awayData != null && awayData.isSerponAway();
        LivingEntity owner = getTrueOwner();
        boolean netherSet = wearsNetherSet(owner);
        if (biome.is(SERPON_FIND_BIOME) && awayData != null && !serponAway) {
            base += 0.5F;
        } else if (biome.is(BiomeTags.SNOW_GOLEM_MELTS)) {
            base += 0.25F;
        } else if (awayData != null && biome.is(BiomeTags.SPAWNS_SNOW_FOXES) && !netherSet) {
            base -= 0.5F;
        }
        if (serponAway) {
            base -= 0.5F;
        }
        BlockPos below = blockPosition().below();
        if (level().getBlockState(below).is(BlockTags.FIRE)
                || level().getFluidState(below).is(FluidTags.LAVA)) {
            base += 0.25F;
        }
        int allies = countNearbySerponAllies();
        if (allies > 0) {
            base += Math.min(1.0F, allies / 12.0F);
        }
        if (level().isDay()) {
            base += 0.25F;
        } else if (level().isNight()) {
            base += 0.15F;
        }
        if (isSunBurnTick()) {
            base += 0.25F;
        }
        LivingEntity target = getTarget();
        if (target instanceof Mob mob && isOtherSunBurnTick(mob)) {
            base += 0.25F;
        }
        if (fury) {
            base += 0.25F;
        }
        if (wearsUnholySet(owner)) {
            base += 0.5F;
        }
        if (uncleanBloodBoon) {
            base += UNCLEAN_BLOOD_STRENGTH_BONUS;
        }
        if (droughtHelmBoon) {
            base += DROUGHT_HELM_STRENGTH_BONUS;
        }
        strengthCache = Mth.clamp(base, 0.25F, 5.0F);
        return strengthCache;
    }

    private static boolean wearsNetherSet(LivingEntity owner) {
        return owner != null && CuriosFinder.hasNetherSet(owner);
    }

    private static boolean wearsUnholySet(LivingEntity owner) {
        return owner != null && CuriosFinder.hasUnholySet(owner);
    }

    private int countNearbySerponAllies() {
        List<AbstractSerpon> allies = level().getEntitiesOfClass(
                AbstractSerpon.class, getBoundingBox().inflate(64.0D));
        LivingEntity owner = getTrueOwner();
        int count = 0;
        for (AbstractSerpon ally : allies) {
            if (ally == this || !ally.isAlive()) {
                continue;
            }
            if (owner == null ? ally.getTrueOwner() != null : !owner.equals(ally.getTrueOwner())) {
                continue;
            }
            ++count;
        }
        return count;
    }

    private static boolean isOtherSunBurnTick(Mob mob) {
        if (!mob.level().isDay() || mob.level().isClientSide()) {
            return false;
        }
        float brightness = mob.getLightLevelDependentMagicValue();
        BlockPos pos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
        boolean flagged = mob.isInWaterRainOrBubble() || mob.isFreezing();
        return brightness > 0.5F && mob.getRandom().nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F
                && !flagged && mob.level().canSeeSky(pos);
    }

    @Override
    public void push(double x, double y, double z) {
        super.push(0.0D, 0.0D, 0.0D);
    }

    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(0.0D, 0.0D, 0.0D);
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
        this.resetFallDistance();
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
        if (specialAction() && spellTick != 1) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05D, 1.0D, 0.05D));
        }
    }

    @Override
    public void setYRot(float yRot) {
        if (curseActiveTick <= 0) {
            super.setYRot(yRot);
        }
    }

    @Override
    public void setXRot(float xRot) {
        if (curseActiveTick <= 0) {
            super.setXRot(xRot);
        }
    }

    @Override
    public void setYHeadRot(float yHeadRot) {
        if (curseActiveTick <= 0) {
            super.setYHeadRot(yHeadRot);
        }
    }

    @Override
    public void lookAt(Entity entity, float deltaYaw, float deltaPitch) {
        if (curseActiveTick <= 0) {
            super.lookAt(entity, deltaYaw, deltaPitch);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!(target instanceof LivingEntity) || specialAction() || attackTick > 0) {
            return false;
        }
        float flameChance = uncleanBloodBoon ? UNCLEAN_BLOOD_SLASH_CHANCE : FLAME_SLASH_CHANCE;
        float windChance = uncleanBloodBoon ? UNCLEAN_BLOOD_SLASH_CHANCE : WHIRLWIND_SLASH_CHANCE;
        float roll = random.nextFloat();
        if (specialMeleeChance <= 0 && roll < flameChance + windChance) {
            if (roll < flameChance) {
                flameAttackTick = 25;
                playAnimation("flameAttack", 15);
                showSlashDisplay("flame_slash_display");
            } else {
                windAttackTick = 25;
                playAnimation("windAttack", 15);
                showSlashDisplay("whirlwind_slash_display");
            }
            specialMeleeChance = SPECIAL_MELEE_CHARGES;
            return true;
        }
        attackTick = 20;
        playMeleeAnimation();
        if (specialMeleeChance > 0) {
            --specialMeleeChance;
        }
        return true;
    }

    private void showSlashDisplay(String particlePath) {
        if (isInvisible() || !(level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(jerotesParticle(particlePath, ParticleTypes.CRIT),
                getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void playMeleeAnimation() {
        int attackRandom = random.nextInt(40);
        if (getMainHandItem().isEmpty()) {
            playAnimation(attackRandom < 30 ? "attack1" : "attack2", 5);
        } else if (attackRandom > 30) {
            playAnimation("swordAttack1", 20);
        } else if (attackRandom > 20) {
            playAnimation("swordAttack2", 20);
        } else if (attackRandom > 10) {
            playAnimation("swordAttack3", 20);
        } else {
            playAnimation("swordAttack4", 20);
        }
    }

    private void tickAttack() {
        if (attackTick <= 0) {
            return;
        }
        --attackTick;
        if (attackTick == 10 && isAlive() && !getMainHandItem().isEmpty()) {
            trueHurt();
        }
        if (attackTick > 18 && getTarget() != null) {
            getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
            lookAt(getTarget(), 360.0F, 360.0F);
        }
    }

    private void trueHurt() {
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_attack", SoundEvents.PLAYER_ATTACK_STRONG),
                    5.0F, 1.0F);
        }
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE)
                * Math.min(1.0F, 0.5F + serponStrengthBase() / 1.5F);
        double knockback = getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.5D;
        AABB area = attackBox().inflate(0.5D);
        for (LivingEntity other : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other == this || !canAttack(other) || isAlliedTo(other)) {
                continue;
            }
            other.invulnerableTime = 0;
            if (other.hurt(damageSources().mobAttack(this), damage)) {
                other.setRemainingFireTicks(16);
                applyFacingKnockback(other, knockback);
            }
        }
        serponSweepParticles();
    }

    private void applyFacingKnockback(LivingEntity target, double strength) {
        if (strength <= 0.0D) {
            return;
        }
        float yaw = getYRot() * ((float) Math.PI / 180.0F);
        target.knockback(strength, Mth.sin(yaw), -Mth.cos(yaw));
    }

    private void tickFlameWind() {
        if (flameAttackTick > 0) {
            --flameAttackTick;
            this.telegraphFlameWind(true);
            if (flameAttackTick == 10) {
                flameAttack();
            }
        }
        if (windAttackTick > 0) {
            --windAttackTick;
            this.telegraphFlameWind(false);
            if (windAttackTick == 10) {
                windAttack();
            }
        }
    }

    private void telegraphFlameWind(boolean flame) {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        for (int i = 0; i < 5; ++i) {
            server.sendParticles(ParticleTypes.FLAME,
                    getRandomX(4.0D), getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0D),
                    getRandomZ(4.0D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (!flame) {
            return;
        }
        for (int i = 0; i < 24; ++i) {
            double angle = (Math.PI * 2.0D * i) / 24.0D;
            server.sendParticles(ParticleTypes.FLAME,
                    getX() + Math.cos(angle) * 5.0D, getY(), getZ() + Math.sin(angle) * 5.0D,
                    0, 0.0D, 0.12D, 0.0D, 1.0D);
        }
    }

    private void flameAttack() {
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_attack", SoundEvents.PLAYER_ATTACK_STRONG),
                    5.0F, 1.0F);
            playSound(jerotesSound("serpon_combat_executer_flame", SoundEvents.FIRECHARGE_USE),
                    10.0F, 1.0F);
        }
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE)
                * 2.5F * (0.5F + serponStrengthBase() / 1.5F);
        DamageSource fireSource = JerotesMagicDamage.fireMelee(this, this);
        AABB area = attackBox().inflate(0.75D);
        for (LivingEntity other : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other == this || !canAttack(other) || isAlliedTo(other)) {
                continue;
            }
            other.invulnerableTime = 0;
            if (other.hurt(fireSource, damage)) {
                other.setRemainingFireTicks(32);
                applyFacingKnockback(other, getAttributeValue(Attributes.ATTACK_KNOCKBACK));
                applyFacingKnockback(other, 2.0D);
            } else {
                float fallback = (float) getAttributeValue(Attributes.ATTACK_DAMAGE)
                        * 2.0F * serponStrengthBase();
                other.invulnerableTime = 0;
                if (other.hurt(damageSources().mobAttack(this), fallback)) {
                    applyFacingKnockback(other, getAttributeValue(Attributes.ATTACK_KNOCKBACK));
                }
            }
            igniteGroundAround(other);
        }
        serponSweepParticles();
    }

    private void igniteGroundAround(LivingEntity target) {
        if (!(level() instanceof ServerLevel serverLevel)
                || !serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        AABB box = AABB.ofSize(new Vec3(target.getX(), target.getY(), target.getZ()), 4.0D, 3.0D, 4.0D);
        BlockState fire = Blocks.FIRE.defaultBlockState();
        for (BlockPos pos : BlockPos.betweenClosed(
                Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
                Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
            if (!serverLevel.getBlockState(pos).isAir() || serverLevel.getBlockState(pos.below()).isAir()) {
                continue;
            }
            serverLevel.setBlock(pos, fire, 2);
        }
    }

    private void windAttack() {
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_attack", SoundEvents.PLAYER_ATTACK_STRONG),
                    5.0F, 1.0F);
            playSound(jerotesSound("serpon_combat_executer_wind", SoundEvents.PLAYER_ATTACK_SWEEP),
                    10.0F, 1.0F);
        }
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE)
                * 2.25F * (0.5F + serponStrengthBase() / 1.5F);
        AABB area = attackBox().inflate(0.75D);
        for (LivingEntity other : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other == this || !canAttack(other) || isAlliedTo(other)) {
                continue;
            }
            applyFacingKnockback(other, 5.0D);
            other.invulnerableTime = 0;
            if (other.hurt(damagesourceWind(), damage)) {
                applyFacingKnockback(other, getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 3.0D);
                double resist = Math.max(other.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
                double factor = Math.max(0.0D, 1.0D - resist) + 1.0D;
                other.setDeltaMovement(other.getDeltaMovement().add(
                        -(getX() - other.getX()) * 1.4D * (factor + 0.3D),
                        -(getY() - other.getY()) * 1.4D * (factor + 0.35D),
                        -(getZ() - other.getZ()) * 1.4D * (factor + 0.3D)));
            }
        }
        if (level() instanceof ServerLevel serverLevel) {
            AABB fling = AABB.ofSize(position(), 7.0D, 6.0D, 7.0D);
            for (BlockPos pos : BlockPos.betweenClosed(
                    Mth.floor(fling.minX), Mth.floor(fling.minY), Mth.floor(fling.minZ),
                    Mth.floor(fling.maxX), Mth.floor(fling.maxY), Mth.floor(fling.maxZ))) {
                spawnFlungBlock(serverLevel, pos);
            }
        }
        serponSweepParticles();
    }

    private DamageSource damagesourceWind() {
        return damageSources().fall();
    }

    @Override
    public void tickExtra() {
        if (level().isClientSide()) {
            return;
        }
        tickTrueComeEntrance();
        if (lastStandTick > 0) {
            --lastStandTick;
            if (level() instanceof ServerLevel server) {
                for (int i = 0; i < 3; ++i) {
                    double angle = i * (Math.PI * 2.0D / 3.0D);
                    server.sendParticles(ParticleTypes.FLAME,
                            getX() + Math.cos(angle) * 0.45D, getY(0.65D),
                            getZ() + Math.sin(angle) * 0.45D,
                            1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        tickAttack();
        tickFlameWind();
        tickRush();
        tickSpells();
        tickBlockDestroy();
        tickFury();
        tickCurse();
        tickReinforcements();
    }

    private void tickBlockDestroy() {
        if (!BLOCK_DESTROY_ENABLED) {
            return;
        }
        if (blockDestroyTick > 0.0D) {
            blockDestroyTick -= 1.0D;
        }
        LivingEntity target = getTarget();
        boolean blocked = horizontalCollision
                || (target != null && target.getY() > getY() && verticalCollision);
        boolean mobGriefing = level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        if (blockDestroyTick > 0.0D || !blocked || !mobGriefing) {
            return;
        }
        boolean canBlockDestroy = mobGriefing;
        if (blockDestroy(BLOCK_DESTROY_STRENGTH)) {
            blockDestroyTick = BLOCK_DESTROY_COOLDOWN;
        }
        if (!canBlockDestroy && onGround()) {
            jumpFromGround();
        }
    }

    private AABB blockDestroyAABB() {
        AABB aabb = getBoundingBox();
        Path path = getNavigation().getPath();
        if (path != null) {
            double targetY = path.getTarget().getY();
            if (targetY - getY() > 1.0D) {
                aabb = aabb.move(0.0D, 1.0D, 0.0D);
            } else if (targetY - getY() < -1.0D) {
                aabb = aabb.move(0.0D, -1.0D, 0.0D);
            }
        }
        return aabb;
    }

    private boolean blockDestroy(float destroy) {
        Level level = level();
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
        }
        AABB aabb = blockDestroyAABB();
        int minX = Mth.floor(aabb.minX);
        int minY = Mth.floor(aabb.minY);
        int minZ = Mth.floor(aabb.minZ);
        int maxX = Mth.floor(aabb.maxX);
        int maxY = Mth.floor(aabb.maxY);
        int maxZ = Mth.floor(aabb.maxZ);
        boolean anyDestroyed = false;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    pos.set(x, y, z);
                    BlockState state = serverLevel.getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getDestroySpeed(serverLevel, pos);
                    if (hardness < 0.0F || hardness >= destroy) {
                        continue;
                    }
                    if (state.is(SHAPELESS_AS_AIR)) {
                        continue;
                    }
                    if (!ForgeEventFactory.onEntityDestroyBlock(this, pos, state)) {
                        continue;
                    }
                    if (!ForgeHooks.canEntityDestroy(serverLevel, pos, this)) {
                        continue;
                    }
                    if (isReplaceable(state) && random.nextFloat() > 0.005F) {
                        boolean success = random.nextBoolean()
                                ? serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3)
                                : serverLevel.destroyBlock(pos, false, this);
                        if (success) {
                            anyDestroyed = true;
                        }
                        continue;
                    }
                    if (serverLevel.destroyBlock(pos, true, this)) {
                        anyDestroyed = true;
                    }
                }
            }
        }
        return anyDestroyed;
    }

    private static boolean isReplaceable(BlockState state) {
        return state.is(BlockTags.REPLACEABLE)
                || state.is(BlockTags.REPLACEABLE_BY_TREES)
                || state.is(BlockTags.FLOWERS)
                || state.is(BlockTags.CROPS);
    }

    private void spawnFlungBlock(ServerLevel serverLevel, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState block = serverLevel.getBlockState(pos);
        BlockState blockAbove = serverLevel.getBlockState(above);
        double fallingFactor = random.nextBoolean()
                ? 1.0D + 0.4D + random.nextGaussian() * 0.2D
                : 1.0D - Mth.clamp(0.2D + random.nextGaussian() * 0.2D, 0.2D, 0.9D);
        if (block.isAir() || !block.isSolidRender(serverLevel, pos)
                || block.hasBlockEntity() || !blockAbove.isAir()) {
            return;
        }
        FlungBlockEntity flung = new FlungBlockEntity(serverLevel,
                pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, block,
                (float) (0.32D + fallingFactor * 0.2D));
        serverLevel.addFreshEntity(flung);
    }

    private void tickTrueComeEntrance() {
        if (this.trueCome) {
            return;
        }
        this.trueCome = true;
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (int counts = 0; counts < 360; ++counts) {
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    getRandomX(1.0D), getRandomY(), getRandomZ(1.0D),
                    4, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        for (int i = 0; i < 64; ++i) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    getRandomX(4.0D),
                    getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0D),
                    getRandomZ(4.0D),
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static final int CURSE_CHARGE_TICKS = 800;

    private static final int CURSE_START_TICKS = 400;

    private static final int CURSE_ACTIVE_TICKS = 60;

    private void tickCurse() {
        if (curseActiveTick > 0) {
            --curseActiveTick;
            getNavigation().stop();
            if (level() instanceof ServerLevel server) {
                this.spawnCurseParticles(server);
            }
            return;
        }
        if (!fury) {
            return;
        }
        if (curseTick < CURSE_CHARGE_TICKS) {
            ++curseTick;
            return;
        }
        LivingEntity target = getTarget();
        if (!(target instanceof Player player) || !player.isAlive()) {
            return;
        }
        curseTick = 0;
        setXRot(45.0F);
        curseActiveTick = CURSE_ACTIVE_TICKS;
        getNavigation().stop();
        if (level() instanceof ServerLevel server) {
            server.playSound(null, blockPosition(),
                    jerotesSound("serpon_combat_executer_curse", SoundEvents.EVOKER_CAST_SPELL),
                    getSoundSource(), 5.0F, 1.0F);
        }
    }

    private void spawnCurseParticles(ServerLevel server) {
        double x = getX();
        double y = getY(1.1F);
        double z = getZ();
        int remaining = this.curseActiveTick;
        for (int i = 0; i < 4; ++i) {
            double angle = (Math.PI * 2.0D * (remaining + i * 10)) / 60.0D;
            double ox = Math.cos(angle) * 1.45D;
            double oz = Math.sin(angle) * 1.45D;
            server.sendParticles(ParticleTypes.FLAME, x + ox, y, z + oz,
                    0, 0.0D, 0.12D, 0.0D, 1.0D);
            server.sendParticles(ParticleTypes.ENCHANT, x + ox * 1.25D, y, z + oz * 1.25D,
                    0, 0.0D, 0.15D, 0.0D, 1.0D);
        }
        for (int i = 0; i < 8; ++i) {
            double angle = (Math.PI * 2.0D * (remaining + i * 5)) / 60.0D;
            double ox = Math.cos(angle) * 4.0D;
            double oz = Math.sin(angle) * 4.0D;
            server.sendParticles(ParticleTypes.FLAME, x + ox, y, z + oz,
                    0, 0.0D, 0.12D, 0.0D, 1.0D);
            server.sendParticles(ParticleTypes.ENCHANT, x + ox * 1.25D, y, z + oz * 1.25D,
                    0, 0.0D, 0.15D, 0.0D, 1.0D);
        }
        double centreY = getY(0.5D);
        for (int i = 0; i < 60; ++i) {
            double theta = this.random.nextDouble() * Math.PI * 2.0D;
            double phi = Math.acos(2.0D * this.random.nextDouble() - 1.0D);
            double px = x + 3.0D * Math.sin(phi) * Math.cos(theta);
            double py = centreY + 3.0D * Math.cos(phi) * 0.6D;
            double pz = z + 3.0D * Math.sin(phi) * Math.sin(theta);
            double vx = x - px;
            double vy = centreY - py;
            double vz = z - pz;
            double length = Math.sqrt(vx * vx + vy * vy + vz * vz);
            double scale = length < 1.0E-6D ? 0.0D : 0.15D / length;
            server.sendParticles(ParticleTypes.ENCHANTED_HIT, px, py, pz, 0,
                    vx * scale, vy * scale, vz * scale, 1.0D);
        }
    }

    private void tickRush() {
        if (specialAction()) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        double rushRange = uncleanBloodBoon ? UNCLEAN_BLOOD_RUSH_RANGE : RUSH_RANGE;
        int rushChance = uncleanBloodBoon ? UNCLEAN_BLOOD_RUSH_CHANCE : RUSH_CHANCE;
        if (distanceTo(target) > rushRange && random.nextInt(rushChance) == 0) {
            rushAttack(true);
        }
    }

    private void rushAttack(boolean forward) {
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 5; ++i) {
                Vec3 movement = getDeltaMovement();
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0,
                        movement.x, movement.y - 0.1D, movement.z, 0.0D);
                serverLevel.sendParticles(ParticleTypes.FLAME,
                        getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0,
                        movement.x, movement.y - 0.1D, movement.z, 0.0D);
            }
        }
        float yaw = getYRot() * ((float) Math.PI / 180.0F);
        float pitch = getXRot() * ((float) Math.PI / 180.0F);
        float dirX = -Mth.sin(yaw) * Mth.cos(pitch);
        float dirY = -Mth.sin(pitch);
        float dirZ = Mth.cos(yaw) * Mth.cos(pitch);
        float length = Mth.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        if (length <= 0.0F) {
            return;
        }
        float strength = 0.5F + (serponStrengthBase() - 1.0F) * 0.15F;
        float scale = strength / length * 2.0F;
        Vec3 movement = new Vec3(dirX * scale, dirY * scale, dirZ * scale);
        setDeltaMovement(forward ? movement : movement.scale(-1.0D));
        playAnimation("push", 20);
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_dash", SoundEvents.RAVAGER_ROAR),
                    1.0F, 1.0F);
        }
    }

    private void tickFury() {
        if (!fury && isAlive() && getHealth() <= getMaxHealth() / 2.0F) {
            setFury(true);
        }
    }

    public boolean isFury() {
        return fury;
    }

    private void setFury(boolean value) {
        if (this.fury == value) {
            return;
        }
        this.fury = value;
        if (!value) {
            setFuryModifier(Attributes.ARMOR, 0.0D, FURY_ARMOR_ID, "executer_fury_armor", false);
            setFuryModifier(Attributes.ATTACK_DAMAGE, 0.0D, FURY_ATTACK_ID, "executer_fury_attack", false);
            setFuryModifier(Attributes.KNOCKBACK_RESISTANCE, 0.0D, FURY_KNOCKBACK_ID,
                    "executer_fury_knockback", false);
            return;
        }
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = getItemBySlot(slot);
            if (stack.isEmpty()) {
                continue;
            }
            stack.hurtAndBreak(2400, this, worn -> {
            });
            setItemSlot(slot, ItemStack.EMPTY);
            setDropChance(slot, 0.0F);
        }
        double armorBonus = uncleanBloodBoon ? UNCLEAN_BLOOD_FURY_ARMOR_BONUS : FURY_ARMOR_BONUS;
        double attackBonus = uncleanBloodBoon ? UNCLEAN_BLOOD_FURY_ATTACK_BONUS : FURY_ATTACK_BONUS;
        double knockbackBonus = uncleanBloodBoon
                ? UNCLEAN_BLOOD_FURY_KNOCKBACK_BONUS : FURY_KNOCKBACK_BONUS;
        setFuryModifier(Attributes.ARMOR, armorBonus, FURY_ARMOR_ID, "executer_fury_armor", true);
        setFuryModifier(Attributes.ATTACK_DAMAGE, attackBonus, FURY_ATTACK_ID, "executer_fury_attack", true);
        setFuryModifier(Attributes.KNOCKBACK_RESISTANCE, knockbackBonus, FURY_KNOCKBACK_ID,
                "executer_fury_knockback", true);
        if (!isSilent() && tickCount > 0) {
            playSound(jerotesSound("serpon_combat_executer_fury", SoundEvents.RAVAGER_ROAR), 1.0F, 1.0F);
        }
        if (tickCount > 0 && level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    getX(), getY(1.1D), getZ(), 60, 0.5D, 0.9D, 0.5D, 0.08D);
        }
    }

    private void setFuryModifier(Attribute attribute, double amount, UUID id, String name, boolean add) {
        AttributeInstance instance = getAttribute(attribute);
        if (instance == null) {
            return;
        }
        instance.removeModifier(id);
        if (add) {
            instance.addTransientModifier(new AttributeModifier(id, name, amount,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    private void tickSpells() {
        if (spellTick > 0) {
            ++spellTick;
            if (spellChance == 1) {
                if (spellTick == 20 || spellTick == 30 || spellTick == 40 || spellTick == 50) {
                    hotWind();
                }
            } else if (spellChance == 2) {
                if (spellTick == 20) {
                    pikeRain();
                }
            } else if (spellChance == 3) {
                if (spellTick == 30 || spellTick == 40) {
                    meteorWave();
                }
            }
            if (spellTick >= SPELL_WINDUP) {
                spellTick = 0;
                spellChance = 0;
                setSyncedSpellChance(0);
                spellCooldown = droughtHelmBoon ? DROUGHT_HELM_SPELL_COOLDOWN : SPELL_COOLDOWN;
            }
            return;
        }
        if (spellCooldown > 0) {
            --spellCooldown;
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive() || isDeadOrDying()) {
            return;
        }
        int spellChanceDenom = droughtHelmBoon ? DROUGHT_HELM_SPELL_CHANCE_DENOM : 120;
        if (distanceTo(target) > SPELL_RANGE || random.nextInt(spellChanceDenom) != 0) {
            return;
        }
        rushAttack(false);
        int roll = 1 + random.nextInt(100);
        if (droughtHelmBoon) {
            if (roll >= DROUGHT_HELM_HOT_WIND_MIN) {
                spellChance = 1;
                setSyncedSpellChance(1);
            } else if (roll >= DROUGHT_HELM_PIKE_MIN) {
                spellChance = 2;
                setSyncedSpellChance(2);
            } else {
                spellChance = 3;
                setSyncedSpellChance(3);
            }
        } else if (roll > 60) {
            spellChance = 1;
            setSyncedSpellChance(1);
        } else if (roll > 20) {
            spellChance = 2;
            setSyncedSpellChance(2);
        } else {
            spellChance = 3;
            setSyncedSpellChance(3);
        }
        spellTick = 1;
        playAnimation(spellChance == 2 ? "pikeSpell" : spellChance == 3 ? "meteorSpell" : "hotWindSpell", 60);
        getLookControl().setLookAt(target, 360.0F, 360.0F);
        lookAt(target, 360.0F, 360.0F);
        if (!isSilent()) {
            if (spellChance == 2) {
                playSound(jerotesSound("serpon_combat_executer_javelin", SoundEvents.ARROW_SHOOT),
                        10.0F, 0.8F + random.nextFloat() * 0.4F);
            } else if (spellChance == 3) {
                playSound(jerotesSound("serpon_combat_executer_meteor", SoundEvents.BLAZE_SHOOT),
                        10.0F, 1.0F);
            } else {
                playSound(jerotesSound("serpon_combat_executer_hotwind", SoundEvents.BLAZE_SHOOT),
                        10.0F, 1.0F);
            }
        }
    }

    private void showSpellIcon(String displayPath) {
        if (isInvisible() || !(level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(jerotesParticle(displayPath, ParticleTypes.CRIT),
                getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void spawnSpellChargeParticlesClient() {
        Vec3 center = position().add(0.0D, getY(0.5D) - getY(), 0.0D);
        double radius = 3.0D;
        int particleCount = 40;
        for (int i = 0; i < particleCount; ++i) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double inclination = this.random.nextDouble() * Math.PI;
            double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
            double offsetY = radius * Math.cos(inclination);
            double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);
            Vec3 particlePos = center.add(offsetX, offsetY, offsetZ);
            Vec3 direction = center.subtract(particlePos).normalize();
            Vec3 velocity = direction.scale(0.15D);
            level().addParticle(ParticleTypes.FLAME, particlePos.x, particlePos.y, particlePos.z,
                    velocity.x, velocity.y, velocity.z);
        }
    }

    private void hotWind() {
        this.showSpellIcon("serpon_sirocco_display");
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_hotwind", SoundEvents.BLAZE_SHOOT), 10.0F, 1.0F);
            playSound(jerotesSound("magic_serpon_sirocco", SoundEvents.PLAYER_ATTACK_CRIT), 10.0F, 1.0F);
        }
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int level = spellLevel();
        for (LivingEntity other : serverLevel.getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(level * 2.0D, level * 2.0D, 3.0D))) {
            if (other == this || !canAttack(other) || isAlliedTo(other)) {
                continue;
            }
            applyFacingKnockback(other, level);
            double resist = other.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null
                    ? Math.max(other.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0D) : 0.0D;
            double factor = Math.max(0.0D, 1.0D - resist) + 0.3D;
            other.setDeltaMovement(other.getDeltaMovement().add(
                    -(getX() - other.getX()) * 0.5D * (factor + 0.3D),
                    -(getY() - other.getY()) * 0.5D * (factor + 0.35D),
                    -(getZ() - other.getZ()) * 0.5D * (factor + 0.3D)));
            if (!isSilent()) {
                serverLevel.playSound(null, getX(), getY(), getZ(),
                        SoundEvents.PLAYER_ATTACK_KNOCKBACK, getSoundSource(), 10.0F, 1.0F);
            }
        }
        LivingEntity target = getTarget();
        int count = Math.max(1, level - 2);
        float distance = 12.0F;
        for (int i = 0; i < count; ++i) {
            if (target != null) {
                getLookControl().setLookAt(target, 360.0F, 360.0F);
                lookAt(target, 360.0F, 360.0F);
            }
            SerponSiroccoEntity spell = new SerponSiroccoEntity(serverLevel, this, level);
            spell.setPos(getX(), getY(0.7D) - spell.getBbHeight() / 2.5D, getZ());
            spell.shootFromRotation(this, getXRot(),
                    getYRot() - (count - 1) * distance / 2.0F + i * distance,
                    0.0F, 0.45F, 0.45F);
            serverLevel.addFreshEntity(spell);
        }
    }

    private int spellLevel() {
        return switch (level().getDifficulty()) {
            case PEACEFUL, EASY -> SPELL_LEVEL - 1;
            case HARD -> SPELL_LEVEL + 1;
            default -> SPELL_LEVEL;
        };
    }

    private void pikeRain() {
        LivingEntity target = getTarget();
        if (target == null || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        this.showSpellIcon("rain_of_serpon_javelin_display");
        int level = spellLevel();
        int min = level * 4;
        int count = min + random.nextInt(level * 4 + 1);
        BlockPos basePos = BlockPos.containing(target.position());
        ParticleOptions javelinParticle = jerotesParticle("serpon_javelin", ParticleTypes.CRIT);
        ParticleOptions javelinDownParticle = jerotesParticle("serpon_javelin_down", ParticleTypes.CRIT);
        for (int i = 0; i < count; ++i) {
            BlockPos landing = SerpenperthMeteorEntity.findSpawnPositionNear(serverLevel, basePos,
                    PIKE_SUMMON_DISTANCE);
            double x = landing.getX() + 0.5D;
            double z = landing.getZ() + 0.5D;
            double y = landing.getY() + PIKE_SUMMON_HEIGHT;
            SerponJavelinEntity javelin =
                    new SerponJavelinEntity(serverLevel, this, jerotesItem("serpon_javelin"));
            javelin.setPos(x, y, z);
            javelin.setBaseDamage(12.0D);
            javelin.setDeltaMovement((random.nextDouble() - 0.5D) * 0.05D, -0.75D,
                    (random.nextDouble() - 0.5D) * 0.05D);
            serverLevel.addFreshEntity(javelin);
            serverLevel.sendParticles(javelinParticle, javelin.getX(), javelin.getY(1.5D),
                    javelin.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            serverLevel.sendParticles(javelinDownParticle, javelin.getX(), javelin.getY(1.5D),
                    javelin.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void meteorWave() {
        meteorWave(this);
    }

    private void meteorWave(LivingEntity meteorOwner) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        this.showSpellIcon("serpenperth_meteor_display");
        LivingEntity target = getTarget();
        Vec3 targetPos = target != null ? target.position() : position();
        int spellLevelDamage = spellLevel();
        int countMin = Math.max(3, spellLevelDamage);
        int countMax = Math.max(6, spellLevelDamage * 2);
        int count = countMin + random.nextInt(countMax - countMin + 1);
        int summonDistance = 6;
        float summonHeight = (target != null ? target.getBbHeight() : 2.0F) + 15.0F;
        BlockPos basePos = BlockPos.containing(targetPos);
        for (int i = 0; i < count; ++i) {
            BlockPos shootPos = SerpenperthMeteorEntity.findSpawnPositionNear(
                    serverLevel, basePos, Math.max(1, summonDistance / 3));
            BlockPos landing = SerpenperthMeteorEntity.findSpawnPositionNear(serverLevel, basePos, summonDistance);
            SerpenperthMeteorEntity cloud =
                    new SerpenperthMeteorEntity(ModEntityTypes.SERPENPERTH_METEOR.get(), serverLevel);
            cloud.setSpellLevelDamage(spellLevelDamage);
            cloud.setPos(landing.getX() + 0.5D, targetPos.y + 0.125D, landing.getZ() + 0.5D);
            cloud.setStartPos(new Vec3(shootPos.getX() + 0.5D, shootPos.getY() + summonHeight,
                    shootPos.getZ() + 0.5D));
            cloud.setOwner(meteorOwner);
            cloud.setOrder(i);
            serverLevel.addFreshEntity(cloud);
        }
    }

    private void tickReinforcements() {
        if (!(level() instanceof ServerLevel serverLevel) || !isAlive()) {
            return;
        }
        float ratio = getHealth() / getMaxHealth();
        for (int i = 0; i < WAVE_THRESHOLDS.length; ++i) {
            if (reinforcementCooldowns[i] > 0) {
                --reinforcementCooldowns[i];
            }
            if (ratio > WAVE_THRESHOLDS[i] / 100.0F) {
                reinforcementArmed[i] = true;
                continue;
            }
            if (!reinforcementArmed[i] || reinforcementCooldowns[i] > 0) {
                continue;
            }
            if (countAliveReinforcements() >= MAX_REINFORCEMENTS) {
                continue;
            }
            reinforcementArmed[i] = false;
            reinforcementCooldowns[i] = REINFORCEMENT_COOLDOWN_TICKS;
            summonReinforcementWave(serverLevel, i);
        }
    }

    private int reinforcementLifespanTicks() {
        int lifespan = ServantLimits.SUMMONED_LIFESPAN_TICKS;
        if (uncleanBloodBoon) {
            lifespan += 30 * 20;
        }
        if (droughtHelmBoon) {
            lifespan += 30 * 20;
        }
        return lifespan;
    }

    private void summonReinforcementWave(ServerLevel serverLevel, int index) {
        int slots = MAX_REINFORCEMENTS - countAliveReinforcements();
        int count = Math.max(0, Math.min(2 + index, slots));
        LivingEntity target = getTarget();
        for (int i = 0; i < count; ++i) {
            EntityType<?> type = pickReinforcementType();
            Entity entity = type == null ? null : type.create(serverLevel);
            if (!(entity instanceof Summoned minion)) {
                continue;
            }
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double radius = 2.5D + random.nextDouble() * 2.0D;
            double x = getX() + Math.cos(angle) * radius;
            double z = getZ() + Math.sin(angle) * radius;
            minion.setTrueOwner(this);
            minion.moveTo(x, getY(), z, getYRot(), 0.0F);
            minion.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(minion.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            minion.setTrueOwner(this);
            if (minion instanceof AbstractSerpon serponMinion) {
                serponMinion.setPrepare(this.isPrepare(), true);
            }
            if (minion instanceof SerponColonialSoldier soldier
                    && RingServantGear.chainRing(this) == RingServantGear.Ring.IMMUTABLE) {
                soldier.equipGuaranteedShield();
            }
            minion.setLimitedLife(reinforcementLifespanTicks());
            minion.setPersistenceRequired();
            if (target != null && minion.canAttack(target)) {
                minion.setTarget(target);
            }
            if (serverLevel.addFreshEntity(minion)) {
                GoetySummonFx.play(serverLevel, minion);
                if (!isInvisible()) {
                    for (int flame = 0; flame < 12; ++flame) {
                        serverLevel.sendParticles(ParticleTypes.FLAME,
                                minion.getRandomX(0.5D), minion.getRandomY(), minion.getRandomZ(0.5D),
                                4, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                    serverLevel.sendParticles(serponPortalParticle(),
                            minion.getX(), minion.getY(0.5D), minion.getZ(),
                            0, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            } else {
                minion.discard();
            }
        }
    }

    private int countAliveReinforcements() {
        int n = 0;
        for (AbstractSerpon ally : level().getEntitiesOfClass(AbstractSerpon.class,
                getBoundingBox().inflate(96.0D))) {
            if (ally != this && ally.isAlive() && ally.getTrueOwner() == this) {
                ++n;
            }
        }
        return n;
    }

    private EntityType<?> pickReinforcementType() {
        int roll = random.nextInt(5);
        if (roll == 0) {
            return ModEntityTypes.SERPON_PIONEER.get();
        }
        if (roll == 1) {
            return ModEntityTypes.SERPON_COLONIAL_SOLDIER.get();
        }
        if (roll == 2) {
            return ModEntityTypes.SERPON_RIOTER.get();
        }
        if (roll == 3) {
            return ModEntityTypes.SERPON_INTRUDER.get();
        }
        return ModEntityTypes.SERPON_FIRE_APOSTLE.get();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypes.THORNS) || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.IN_WALL)) {
            return true;
        }
        if (fury && source.is(DamageTypeTags.IS_EXPLOSION)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castFireAbsorption(source, amount, FIRE_ABSORPTION_PERCENTAGE);
        if (isInvulnerableTo(source)) {
            return false;
        }
        if (amount < getMaxHealth() * 20.0F && amount >= getHealth() && !lastStand) {
            this.lastStand = true;
            this.lastStandTick = uncleanBloodBoon ? UNCLEAN_BLOOD_LAST_STAND_TICKS : LAST_STAND_TICKS;
            if (!level().isClientSide()) {
                if (!isSilent()) {
                    playSound(jerotesSound("serpon_combat_executer_fury", SoundEvents.RAVAGER_ROAR), 1.0F, 1.0F);
                }
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                            getX(), getY(1.1D), getZ(), 40, 0.4D, 0.8D, 0.4D, 0.05D);
                }
            }
            return false;
        }
        if (amount < getMaxHealth() * 20.0F && lastStandTick > 0) {
            return false;
        }
        if (amount > 0.0F && !level().isClientSide() && tryBlock(source, amount)) {
            return false;
        }
        if (isFury() && canSelfResistMagic(source, amount)) {
            return super.hurt(source, amount / 3.0F / serponStrengthBase());
        }
        return super.hurt(source, amount / serponStrengthBase());
    }

    private boolean canSelfResistMagic(DamageSource source, float amount) {
        if (!isMagicDamage(source)) {
            return false;
        }
        Integer otherLevel = spellEntityLevel(source);
        if (otherLevel != null) {
            return spellLevel() <= otherLevel;
        }
        int selfLevel = spellLevel();
        int spelllevel = Math.max(0, selfLevel - 1);
        float maxResistance = spelllevel * spelllevel * 10.0F;
        float afterDamage = amount * Math.max(0.05F, (100.0F - spelllevel * spelllevel) / 100.0F);
        if (Float.isNaN(afterDamage) || Float.isInfinite(afterDamage)) {
            return false;
        }
        return afterDamage <= maxResistance;
    }

    private boolean isMagicDamage(DamageSource source) {
        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            return true;
        }
        Entity direct = source.getDirectEntity();
        Entity causing = source.getEntity();
        return direct instanceof ThrownPotion || causing instanceof ThrownPotion
                || direct instanceof AreaEffectCloud || causing instanceof AreaEffectCloud
                || direct instanceof AbstractHurtingProjectile || causing instanceof AbstractHurtingProjectile
                || direct instanceof SerponSiroccoEntity || causing instanceof SerponSiroccoEntity
                || direct instanceof SerpenperthMeteorEntity || causing instanceof SerpenperthMeteorEntity;
    }

    private Integer spellEntityLevel(DamageSource source) {
        Entity direct = source.getDirectEntity();
        Entity causing = source.getEntity();
        if (direct instanceof SerponSiroccoEntity sirocco) {
            return sirocco.getSpellLevelDamage();
        }
        if (causing instanceof SerponSiroccoEntity sirocco) {
            return sirocco.getSpellLevelDamage();
        }
        if (direct instanceof SerpenperthMeteorEntity meteor) {
            return meteor.getSpellLevelDamage();
        }
        if (causing instanceof SerpenperthMeteorEntity meteor) {
            return meteor.getSpellLevelDamage();
        }
        return null;
    }

    private boolean tryBlock(DamageSource source, float amount) {
        boolean normalUse = getTarget() != null && isDamageSourceBlocked(source);
        boolean furyUse = !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && fury;
        if (isNoAi() || specialAction() || !isAlive() || getBlockTicks() > 0
                || getMainHandItem().isEmpty() || !(normalUse || furyUse)) {
            return false;
        }
        double blockAttackFactor = droughtHelmBoon ? DROUGHT_HELM_BLOCK_ATTACK_FACTOR : 10.0D;
        int attackDamage = (int) (getAttributeValue(Attributes.ATTACK_DAMAGE) * blockAttackFactor);
        if (attackDamage < 2) {
            attackDamage = 2;
        }
        float strength = serponStrengthBase();
        float nonFuryBase = droughtHelmBoon ? 1.35F : 1.5F;
        float furyBase = droughtHelmBoon ? 0.25F : 0.35F;
        float chance = Math.max(nonFuryBase - (strength / 3.0F - 1.0F), 0.2F);
        if (fury) {
            chance = Math.max(furyBase - (strength / 3.0F - 1.0F), 0.1F);
        }
        if (random.nextInt(attackDamage + 1) <= chance * amount) {
            return false;
        }
        setBlockTicks(fury ? (droughtHelmBoon ? DROUGHT_HELM_FURY_BLOCK_TICKS : 2) : 5);
        if (!isSilent()) {
            playSound(jerotesSound("serpon_combat_executer_block", SoundEvents.SHIELD_BLOCK), 10.0F, 1.0F);
        }
        playRandomBlockAnimation();
        LivingEntity target = getTarget();
        if (target != null && attackTick <= 0) {
            if (attackBox().intersects(target.getBoundingBox())) {
                doHurtTarget(target);
            } else {
                getLookControl().setLookAt(target, 360.0F, 360.0F);
                lookAt(target, 360.0F, 360.0F);
                rushAttack(true);
            }
        }
        return true;
    }

    private void playRandomBlockAnimation() {
        int roll = random.nextInt(30);
        if (roll > 20) {
            playAnimation("block1", 10);
        } else if (roll > 10) {
            playAnimation("block2", 10);
        } else {
            playAnimation("block3", 10);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect == null) {
            return false;
        }
        return effect.getEffect() != MobEffects.POISON
                && effect.getEffect() != MobEffects.WEAKNESS
                && !isJerotesEffect(effect, "aback")
                && !isJerotesEffect(effect, "deadly_poison")
                && !isJerotesEffect(effect, "corrosive")
                && super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackTick", this.attackTick);
        tag.putInt("FlameAttackTick", this.flameAttackTick);
        tag.putInt("WindAttackTick", this.windAttackTick);
        tag.putInt("SpecialMeleeChance", this.specialMeleeChance);
        tag.putInt("SpellTick", this.spellTick);
        tag.putInt("SpellChance", this.spellChance);
        tag.putInt("SpellCooldown", this.spellCooldown);
        tag.putBoolean("LastStand", this.lastStand);
        tag.putInt("LastStandTick", this.lastStandTick);
        tag.putBoolean("ExecuterFury", this.fury);
        tag.putInt("CurseTick", this.curseTick);
        tag.putInt("CurseActiveTick", this.curseActiveTick);
        tag.putBoolean("TrueCome", this.trueCome);
        tag.putIntArray("ReinforcementCooldowns", this.reinforcementCooldowns);
        tag.putByteArray("ReinforcementArmed", armedBytes());
        tag.putBoolean("UncleanBloodBoon", this.uncleanBloodBoon);
        tag.putBoolean("DroughtHelmBoon", this.droughtHelmBoon);
        tag.putDouble("BlockDestroyTick", this.blockDestroyTick);
    }

    private byte[] armedBytes() {
        byte[] bytes = new byte[reinforcementArmed.length];
        for (int i = 0; i < reinforcementArmed.length; ++i) {
            bytes[i] = (byte) (reinforcementArmed[i] ? 1 : 0);
        }
        return bytes;
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackTick = tag.getInt("AttackTick");
        this.flameAttackTick = tag.getInt("FlameAttackTick");
        this.windAttackTick = tag.getInt("WindAttackTick");
        this.specialMeleeChance = tag.getInt("SpecialMeleeChance");
        this.spellTick = tag.getInt("SpellTick");
        this.spellChance = tag.getInt("SpellChance");
        this.setSyncedSpellChance(this.spellChance);
        this.spellCooldown = tag.getInt("SpellCooldown");
        this.lastStand = tag.getBoolean("LastStand");
        this.lastStandTick = tag.getInt("LastStandTick");
        this.curseTick = tag.getInt("CurseTick");
        this.curseActiveTick = tag.getInt("CurseActiveTick");
        this.trueCome = !tag.contains("TrueCome") || tag.getBoolean("TrueCome");
        int[] cooldowns = tag.getIntArray("ReinforcementCooldowns");
        for (int i = 0; i < reinforcementCooldowns.length; ++i) {
            reinforcementCooldowns[i] = i < cooldowns.length ? cooldowns[i] : 0;
        }
        if (tag.contains("ReinforcementArmed")) {
            byte[] armed = tag.getByteArray("ReinforcementArmed");
            for (int i = 0; i < reinforcementArmed.length; ++i) {
                reinforcementArmed[i] = i >= armed.length || armed[i] != 0;
            }
        }
        this.uncleanBloodBoon = tag.getBoolean("UncleanBloodBoon");
        this.droughtHelmBoon = tag.getBoolean("DroughtHelmBoon");
        this.blockDestroyTick = tag.getDouble("BlockDestroyTick");
        if (tag.getBoolean("ExecuterFury")) {
            setFury(true);
        }
        applyBoonModifiers();
    }
}
