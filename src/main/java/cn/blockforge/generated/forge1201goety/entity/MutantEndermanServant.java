package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.Set;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.EntityFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.util.EntityUtil;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import java.util.ArrayList;
import java.util.Optional;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;
import cn.blockforge.generated.goetysupreme.entity.EndersoulCloneServant;
import cn.blockforge.generated.goetysupreme.entity.EndersoulFragmentServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.MutantEndermanLandingStrike;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.block.MutantEndermanServantThrownBlock;
import cn.blockforge.generated.goetysupreme.util.MutantEndermanTeleSmashArea;
import cn.blockforge.generated.goetysupreme.data.MutantEndermanWatchlings;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantSkillContinuity;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.mixin.MutantEndermanTeleSmashMixin;

public class MutantEndermanServant extends AbstractMutantServant implements IServant,
        net.minecraft.world.entity.OwnableEntity {

    public static final Animation MELEE_ANIMATION = MutantEnderman.MELEE_ANIMATION;
    public static final Animation THROW_ANIMATION = MutantEnderman.THROW_ANIMATION;
    public static final Animation STARE_ANIMATION = MutantEnderman.STARE_ANIMATION;
    public static final Animation TELEPORT_ANIMATION = MutantEnderman.TELEPORT_ANIMATION;
    public static final Animation SCREAM_ANIMATION = MutantEnderman.SCREAM_ANIMATION;
    public static final Animation CLONE_ANIMATION = MutantEnderman.CLONE_ANIMATION;
    public static final Animation TELESMASH_ANIMATION = MutantEnderman.TELESMASH_ANIMATION;
    public static final Animation DEATH_ANIMATION = MutantEnderman.DEATH_ANIMATION;
    private static final Animation[] ANIMATIONS =
            {MELEE_ANIMATION, THROW_ANIMATION, STARE_ANIMATION, TELEPORT_ANIMATION,
                    SCREAM_ANIMATION, CLONE_ANIMATION, TELESMASH_ANIMATION, DEATH_ANIMATION};
    private static final EntityDataAccessor<Optional<BlockPos>> TELEPORT_POSITION =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> CLONE_FORM =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> VOID_UPGRADES =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.BYTE);
    private static final int VOID_SHARD = 1;
    private static final int VOID_ECHO = 2;
    private static final int VOID_EYE = 4;
    private static final UUID VOID_HEALTH_UUID = UUID.fromString("9b102e6d-8d51-436b-a1da-848cb426c350");

    private static final UUID FULL_UPGRADE_ARMOR_UUID = UUID.fromString("5e7c1a0f-2b64-4a31-9d18-3f0b2c7a6d41");

    private static final UUID FULL_UPGRADE_TOUGHNESS_UUID = UUID.fromString("0d3f8b21-6a95-4c7e-8f2d-71b4e9c05a63");
    private long nextVoidEchoHitTick;
    private boolean teleportInProgress;

    private long lastTeleportSummonTick = Long.MIN_VALUE;
    private final List<EndersoulCloneServant> clones = new ArrayList<>();
    private final List<LivingEntity> screamVictims = new ArrayList<>();
    private final int[] heldBlockTicks = new int[4];
    private int hasTargetTicks;
    private int armScale;
    private int previousArmScale;
    private int cloneCooldown;
    private int stareCooldown;
    private int cloneProtectionTicks;

    private int cloneRevealHits;

    private float voidEyeDamageAccum;

    private long voidEyeHitWindowStart;
    private int voidEyeHitWindowCount;

    private long teleportRaidInvulnUntil;

    private boolean voidEyeRaidRetreat;

    private boolean voidErosionRaidPending;

    private int pendingSecondClawArm = -1;

    private boolean clawSwingIsSecond;

    private static final int CLAW_STRIKE_TICK = 3;
    private static final int TELESMASH_STRIKE_TICK = 18;
    private static final int SCREAM_STRIKE_TICK = 40;

    private static final double UPGRADED_CLAW_RADIUS = 3.5D;
    private static final float UPGRADED_CLAW_HALF_ANGLE = 30.0F;

    private static final float RAID_HEAL_BASE = 1.0F;
    private static final float RAID_HEAL_FULL = 2.0F;
    private static final float CLONE_HEAL_BASE = 5.0F;
    private static final float CLONE_HEAL_FULL = 10.0F;

    private static final float VOID_EYE_RAID_DAMAGE = 40.0F;

    private static final double VOID_EYE_RETREAT_DISTANCE = 6.0D;

    private static final double VOID_EYE_RETREAT_MIN = 9.0D;
    private static final double VOID_EYE_RETREAT_MAX = 15.0D;

    private static final EntityDataAccessor<Integer> ANIMATION_ID =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_TICK =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> ACTIVE_ARM =
            SynchedEntityData.defineId(MutantEndermanServant.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<BlockState>[] HELD_BLOCKS = heldBlockAccessors();

    @SuppressWarnings("unchecked")
    private static EntityDataAccessor<BlockState>[] heldBlockAccessors() {
        EntityDataAccessor<BlockState>[] accessors = new EntityDataAccessor[4];
        for (int arm = 0; arm < 4; arm++) {
            accessors[arm] = SynchedEntityData.defineId(MutantEndermanServant.class,
                    EntityDataSerializers.BLOCK_STATE);
        }
        return accessors;
    }

    private LivingEntity artTarget;

    private int clawCooldown;
    private int teleportCooldown;
    private int telesmashCooldown;
    private int screamCooldown;
    private int grabCooldown;

    private MutantEnderman throwStandIn;
    private ServerLevel throwStandInLevel;

    public MutantEndermanServant(EntityType<? extends MutantEndermanServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        this.xpReward = 5;
        setMaxUpStep(1.4F);
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MutantEndermanServantConfig.MAX_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, MutantEndermanServantConfig.ATTACK_DAMAGE.get())
                .add(Attributes.FOLLOW_RANGE, MutantEndermanServantConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, MutantEndermanServantConfig.MOVEMENT_SPEED.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, MutantEndermanServantConfig.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.ARMOR, MutantEndermanServantConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, MutantEndermanServantConfig.ARMOR_TOUGHNESS.get());
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return registerAttributes();
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), MutantEndermanServantConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), MutantEndermanServantConfig.ATTACK_DAMAGE.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), MutantEndermanServantConfig.MOVEMENT_SPEED.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                MutantEndermanServantConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), MutantEndermanServantConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), MutantEndermanServantConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), MutantEndermanServantConfig.FOLLOW_RANGE.get());
        refreshVoidHealth();
    }

    private boolean hasVoidUpgrade(int flag) {
        return entityData.hasItem(VOID_UPGRADES) && (entityData.get(VOID_UPGRADES) & flag) != 0;
    }

    private int summonLifespanTicks() {
        int lifespan = ServantLimits.SUMMONED_LIFESPAN_TICKS;
        if (hasVoidUpgrade(VOID_ECHO)) {
            lifespan += 30 * 20;
        }
        if (hasVoidUpgrade(VOID_EYE)) {
            lifespan += 30 * 20;
        }
        return lifespan;
    }

    public boolean hasVoidEyeUpgrade() {
        return hasVoidUpgrade(VOID_EYE);
    }

    public boolean isFullyUpgraded() {
        return hasVoidUpgrade(VOID_SHARD) && hasVoidUpgrade(VOID_ECHO) && hasVoidUpgrade(VOID_EYE);
    }

    public float scaleDamage(float base) {
        if (base <= 0.0F || !isFullyUpgraded()) {
            return base;
        }
        float multiplier = MutantEndermanServantConfig.FULL_UPGRADE_DAMAGE_MULTIPLIER.get().floatValue();
        return Math.max(base, (float) Math.ceil(base * (double) multiplier));
    }

    private void refreshVoidHealth() {
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        if (health == null) return;
        double bonus = (hasVoidUpgrade(VOID_SHARD) ? MutantEndermanServantConfig.VOID_SHARD_HEALTH.get() : 0.0D)
                + (hasVoidUpgrade(VOID_ECHO) ? MutantEndermanServantConfig.VOID_ECHO_HEALTH.get() : 0.0D)
                + (hasVoidEyeUpgrade() ? MutantEndermanServantConfig.VOID_EYE_HEALTH.get() : 0.0D);
        if (health.getModifier(VOID_HEALTH_UUID) != null) health.removePermanentModifier(VOID_HEALTH_UUID);
        if (bonus > 0.0D) {
            health.addPermanentModifier(new AttributeModifier(VOID_HEALTH_UUID, "Void upgrades health", bonus,
                    AttributeModifier.Operation.ADDITION));
        }
        boolean fullyUpgraded = isFullyUpgraded();
        applyFullUpgradeModifier(getAttribute(Attributes.ARMOR), FULL_UPGRADE_ARMOR_UUID,
                "Void full upgrade armor", fullyUpgraded ? MutantEndermanServantConfig.FULL_UPGRADE_ARMOR.get() : 0.0D);
        applyFullUpgradeModifier(getAttribute(Attributes.ARMOR_TOUGHNESS), FULL_UPGRADE_TOUGHNESS_UUID,
                "Void full upgrade armor toughness",
                fullyUpgraded ? MutantEndermanServantConfig.FULL_UPGRADE_ARMOR_TOUGHNESS.get() : 0.0D);
    }

    private void applyFullUpgradeModifier(AttributeInstance attribute, UUID uuid, String name, double bonus) {
        if (attribute == null) {
            return;
        }
        AttributeModifier existing = attribute.getModifier(uuid);
        if (existing != null && existing.getAmount() != bonus) {
            attribute.removePermanentModifier(uuid);
        }
        if (bonus > 0.0D && attribute.getModifier(uuid) == null) {
            attribute.addPermanentModifier(new AttributeModifier(uuid, name, bonus,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) return InteractionResult.PASS;
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner() && applyVoidUpgrade(player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (isAlive() && player == getTrueOwner() && tryChorusHeal(player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private boolean tryChorusHeal(Player player, ItemStack held) {
        if (!MutantEndermanServantConfig.CHORUS_HEAL_ENABLED.get()) return false;
        if (!held.is(Items.CHORUS_FRUIT) || getHealth() >= getMaxHealth()) return false;
        if (!level().isClientSide()) {
            if (!player.getAbilities().instabuild) held.shrink(1);
            SoundEvent sound = getAmbientSound();
            playSound(sound != null ? sound : SoundEvents.ENDERMAN_AMBIENT, 1.0F, 1.25F);
            heal((float) (double) MutantEndermanServantConfig.CHORUS_HEAL_AMOUNT.get());
            if (level() instanceof ServerLevel server) {
                for (int i = 0; i < 7; ++i) {
                    double dx = random.nextGaussian() * 0.02D;
                    double dy = random.nextGaussian() * 0.02D;
                    double dz = random.nextGaussian() * 0.02D;
                    server.sendParticles(healParticle(),
                            getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D),
                            0, dx, dy, dz, 0.5D);
                }
            }
        }
        return true;
    }

    private static ParticleOptions healParticle() {
        Object particle = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath("goety", "heal"));
        return particle instanceof SimpleParticleType type ? type : ParticleTypes.HEART;
    }

    private boolean applyVoidUpgrade(Player player, ItemStack stack) {
        if (stack.isEmpty()) return false;
        ResourceLocation item = BuiltInRegistries.ITEM.getKey(stack.getItem());
        int flag;
        if (ResourceLocation.fromNamespaceAndPath("goety", "void_shard").equals(item)) flag = VOID_SHARD;
        else if (ResourceLocation.fromNamespaceAndPath("goety", "void_echo").equals(item)) flag = VOID_ECHO;
        else if (ResourceLocation.fromNamespaceAndPath("goety", "void_eye").equals(item)) flag = VOID_EYE;
        else return false;
        if (level().isClientSide() || hasVoidUpgrade(flag)) return true;
        boolean enabled = flag == VOID_SHARD ? MutantEndermanServantConfig.VOID_SHARD_ENABLED.get()
                : flag == VOID_ECHO ? MutantEndermanServantConfig.VOID_ECHO_ENABLED.get()
                : MutantEndermanServantConfig.VOID_EYE_ENABLED.get();
        if (!enabled) return true;
        boolean wasFullyUpgraded = isFullyUpgraded();
        float oldHealth = getHealth();
        float oldMaxHealth = getMaxHealth();
        entityData.set(VOID_UPGRADES, (byte) (entityData.get(VOID_UPGRADES) | flag));
        refreshVoidHealth();
        setHealth(Math.min(getMaxHealth(), oldHealth + Math.max(0.0F, getMaxHealth() - oldMaxHealth)));
        refreshVoidEyeEffects();
        if (!player.getAbilities().instabuild) stack.shrink(1);
        if (!wasFullyUpgraded && isFullyUpgraded()) {
            playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.0F);
        } else {
            playSound(voidVaultUnlockSound(), 1.0F, 0.8F);
        }
        sendSoulParticles(position(), 64);
        return true;
    }

    private void refreshVoidEyeEffects() {
        if (!hasVoidEyeUpgrade() || level().isClientSide() || !isAlive()) return;
        ensureVoidEyeEffect(GoetyEffects.SHIELDING.get(), MutantEndermanServantConfig.VOID_EYE_SHIELDING_LEVEL.get() - 1);
        ensureVoidEyeEffect(GoetyEffects.RALLYING.get(), MutantEndermanServantConfig.VOID_EYE_RALLYING_LEVEL.get() - 1);
        ensureVoidEyeEffect(GoetyEffects.DEFLECTIVE.get(), MutantEndermanServantConfig.VOID_EYE_DEFLECTIVE_LEVEL.get() - 1);
    }

    private void ensureVoidEyeEffect(MobEffect effect, int amplifier) {
        MobEffectInstance current = getEffect(effect);
        if (current == null || current.getAmplifier() < amplifier
                || current.getAmplifier() == amplifier && !current.isInfiniteDuration()) {
            addEffect(new MobEffectInstance(effect, -1, amplifier, false, false, true));
        }
    }

    public boolean hurtWithVoidEffect(LivingEntity victim, DamageSource source, float amount) {
        if (level().isClientSide() || amount <= 0.0F || isAlliedTo(victim)) return false;
        boolean hit = victim.hurt(source, amount);
        if (hit) applyVoidTouched(victim);
        return hit;
    }

    public void applyVoidTouched(LivingEntity victim) {
        applyVoidTouched(victim, false);
    }

    private void applyVoidTouched(LivingEntity victim, boolean guaranteed) {
        if (!level().isClientSide() && hasVoidUpgrade(VOID_ECHO) && victim != null && victim.isAlive()
                && !isAlliedTo(victim) && (guaranteed || random.nextFloat() < MutantEndermanServantConfig.VOID_TOUCHED_CHANCE.get().floatValue())) {
            victim.addEffect(new MobEffectInstance(GoetyEffects.VOID_TOUCHED.get(),
                    MutantEndermanServantConfig.VOID_TOUCHED_SECONDS.get() * 20, 0), this);
        }
    }

    private void trySummonWatchlings(boolean fromClones) {
        if (!(level() instanceof ServerLevel server) || !isAlive() || !hasVoidUpgrade(VOID_SHARD)) {
            return;
        }
        float chance = fromClones
                ? MutantEndermanServantConfig.WATCHLING_CLONE_CHANCE.get().floatValue()
                : MutantEndermanServantConfig.WATCHLING_TELEPORT_CHANCE.get().floatValue();
        if (chance <= 0.0F || random.nextFloat() >= chance) {
            return;
        }
        int minimum = MutantEndermanServantConfig.WATCHLING_CLONE_MIN.get();
        int maximum = Math.max(minimum, MutantEndermanServantConfig.WATCHLING_CLONE_MAX.get());
        int count = fromClones
                ? minimum + random.nextInt(maximum - minimum + 1)
                : MutantEndermanServantConfig.WATCHLING_TELEPORT_COUNT.get();
        MutantEndermanWatchlings ledger = MutantEndermanWatchlings.get(server);
        for (int i = 0; i < count && ledger.count(getUUID()) < MutantEndermanServantConfig.WATCHLING_CAP.get(); i++) {
            if (random.nextFloat() < MutantEndermanServantConfig.WATCHLING_ENDERMAN_CHANCE.get().floatValue()) {
                Summoned enderman = ModEntityTypes.ENDERMAN_SERVANT.get().create(server);
                if (enderman != null) {
                    spawnSummoned(server, ledger, enderman);
                }
                continue;
            }
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(
                    ResourceLocation.fromNamespaceAndPath("goety", "watchling_servant")).orElse(null);
            if (type == null || !(type.create(server) instanceof Summoned watchling)) return;
            spawnSummoned(server, ledger, watchling);
        }
    }

    private boolean spawnSummoned(ServerLevel server, MutantEndermanWatchlings ledger, Summoned summoned) {
        BlockPos landing = findLanding(summoned, this, 1, 5, blockPosition().getY(), 5);
        if (landing == null) {
            summoned.discard();
            return false;
        }
        summoned.moveTo(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D, getYRot(), 0.0F);
        summoned.setTrueOwner(this);
        summoned.finalizeSpawn(server, server.getCurrentDifficultyAt(landing), MobSpawnType.MOB_SUMMONED,
                null, null);
        summoned.setHostile(false);
        summoned.setNatural(false);
        summoned.setWandering(false);
        summoned.setStaying(false);
        summoned.setLimitedLife(summonLifespanTicks());
        summoned.setPersistenceRequired();
        summoned.setTarget(getTarget());
        summoned.getPersistentData().putUUID(MutantEndermanWatchlings.SUMMONER_TAG, getUUID());
        if (!ledger.register(getUUID(), summoned.getUUID())) {
            summoned.discard();
            return false;
        }
        if (!server.addFreshEntity(summoned)) {
            ledger.release(getUUID(), summoned.getUUID());
            summoned.discard();
            return false;
        }
        GoetySummonFx.play(server, summoned);
        return true;
    }

    @Override
    public void teleportTo(double x, double y, double z) {
        Vec3 before = position();
        super.teleportTo(x, y, z);
        if (!teleportInProgress && position().distanceToSqr(before) > 0.0001D) rollTeleportSummon();
    }

    @Override
    public boolean teleportTo(ServerLevel destination, double x, double y, double z,
            Set<RelativeMovement> relative, float yaw, float pitch) {
        Vec3 before = position();
        Level oldLevel = level();
        boolean moved;
        teleportInProgress = true;
        try {
            moved = super.teleportTo(destination, x, y, z, relative, yaw, pitch);
        } finally {
            teleportInProgress = false;
        }
        if (moved && (oldLevel != destination || before.distanceToSqr(new Vec3(x, y, z)) > 0.0001D)
                && destination.getEntity(getUUID()) instanceof MutantEndermanServant arrived) {
            arrived.rollTeleportSummon();
        }
        return moved;
    }

    private void rollTeleportSummon() {
        if (level().isClientSide()) {
            return;
        }
        long now = level().getGameTime();
        if (now == lastTeleportSummonTick) {
            return;
        }
        lastTeleportSummonTick = now;
        trySummonWatchlings(false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        setConfigurableAttributes();
        setHealth(getMaxHealth());
        return result;
    }

    @Override
    public void convertNewEquipment(Entity original) {
        super.convertNewEquipment(original);
        setConfigurableAttributes();
        setHealth(getMaxHealth());
        if (getTrueOwner() instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, this);
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.removeAllGoals(goal -> true);

        this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new ServantHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new SummonTargetGoal(this));

        this.goalSelector.addGoal(1, new EndermanArtsGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new CommandPositionGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new ServantWanderGoal(this, 1.0D, 0.001F));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TELEPORT_POSITION, Optional.empty());
        this.entityData.define(CLONE_FORM, false);
        this.entityData.define(VOID_UPGRADES, (byte) 0);
        this.entityData.define(SERVANT_FLAGS, (byte) 0);
        this.entityData.define(ANIMATION_ID, -1);
        this.entityData.define(ANIMATION_TICK, 0);
        this.entityData.define(ACTIVE_ARM, (byte) 0);
        for (EntityDataAccessor<BlockState> accessor : HELD_BLOCKS) {
            this.entityData.define(accessor, Blocks.AIR.defaultBlockState());
        }
    }

    private SoundEvent teleportSound() {
        return mutantSound("entity.mutant_enderman.teleport", SoundEvents.ENDERMAN_TELEPORT);
    }

    private static SoundEvent voidVaultUnlockSound() {
        SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("goety", "void_frame_unlock"));
        return event == null ? SoundEvents.END_PORTAL_FRAME_FILL : event;
    }

    private SoundEvent screamSound() {
        return mutantSound("entity.mutant_enderman.scream", SoundEvents.ENDERMAN_SCREAM);
    }

    private SoundEvent stareSound() {
        return mutantSound("entity.mutant_enderman.stare", SoundEvents.ENDERMAN_STARE);
    }

    private SoundEvent morphSound() {
        return mutantSound("entity.mutant_enderman.morph", SoundEvents.ENDERMAN_TELEPORT);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return mutantSound("entity.mutant_enderman.ambient", SoundEvents.ENDERMAN_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return mutantSound("entity.mutant_enderman.hurt", SoundEvents.ENDERMAN_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return mutantSound("entity.mutant_enderman.death", SoundEvents.ENDERMAN_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }
        tag.putInt("MutantAnimation", animationIndex(getAnimation()));
        tag.putInt("MutantAnimationTick", getAnimationTick());
        tag.putByte("MutantActiveArm", (byte) getActiveArm());
        for (int arm = 0; arm < 4; arm++) {
            tag.putInt("MutantHeldBlock" + arm, Block.getId(getHeldBlock(arm)));
        }
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
        tag.putInt("commandTick", commandTick);
        tag.putByte("VoidUpgrades", entityData.get(VOID_UPGRADES));
        tag.putInt("VoidEchoProtectionTicks", (int) Math.max(0L,
                Math.min((long) MutantEndermanServantConfig.VOID_ECHO_PROTECTION_TICKS.get(), nextVoidEchoHitTick - level().getGameTime())));
        if (commandPos != null) {
            tag.put("commandPos", NbtUtils.writeBlockPos(commandPos));
        }
        if (commandPosEntity != null) {
            tag.putUUID("commandPosEntity", commandPosEntity.getUUID());
        }
        if (boundPos != null) {
            tag.put("boundPos", NbtUtils.writeBlockPos(boundPos));
            tag.putString("boundDim", boundDim);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(VOID_UPGRADES, (byte) (tag.getByte("VoidUpgrades") & 7));
        nextVoidEchoHitTick = level().getGameTime() + Mth.clamp(tag.getInt("VoidEchoProtectionTicks"), 0, MutantEndermanServantConfig.VOID_ECHO_PROTECTION_TICKS.get());
        refreshVoidHealth();
        refreshVoidEyeEffects();
        if (tag.hasUUID("Owner")) {
            setOwnerId(tag.getUUID("Owner"));
        } else if (tag.hasUUID("GoetyOwner")) {
            setOwnerId(tag.getUUID("GoetyOwner"));
        }
        setAnimation(isDeadOrDying() ? DEATH_ANIMATION : Animation.NONE);
        entityData.set(CLONE_FORM, false);
        entityData.set(TELEPORT_POSITION, Optional.empty());
        if (isDeadOrDying()) {
            setAnimationTick(deathTime);
        }
        if (tag.contains("MutantActiveArm")) {
            setActiveArm(Mth.clamp(tag.getByte("MutantActiveArm"), 0, 3));
        }
        for (int arm = 0; arm < 4; arm++) {
            if (tag.contains("MutantHeldBlock" + arm)) {
                setHeldBlock(arm, Block.stateById(tag.getInt("MutantHeldBlock" + arm)));
            }
        }
        if (tag.contains("wandering")) {
            setWandering(tag.getBoolean("wandering"));
        }
        if (tag.contains("staying")) {
            setStaying(tag.getBoolean("staying"));
        }
        if (tag.contains("commandPos", 10)) {
            commandPos = NbtUtils.readBlockPos(tag.getCompound("commandPos")).immutable();
        } else if (tag.contains("GoetyCommandX")) {
            commandPos = new BlockPos(tag.getInt("GoetyCommandX"), tag.getInt("GoetyCommandY"),
                    tag.getInt("GoetyCommandZ"));
        }
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        if (tag.hasUUID("commandPosEntity")) {
            commandPosEntity = EntityFinder.getLivingEntityByUuiD(tag.getUUID("commandPosEntity"));
        }
        if (tag.contains("boundPos", 10)) {
            boundPos = NbtUtils.readBlockPos(tag.getCompound("boundPos")).immutable();
            boundDim = tag.contains("boundDim") ? tag.getString("boundDim") : level().dimension().location().toString();
        }
    }

    @Override
    public void updateMoveMode(Player player) {
        if (!isWandering() && !isStaying() && !isGuardingArea()) {
            setBoundPos(null);
            setWandering(true);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.wander", getDisplayName()), true);
        } else if (!isStaying() && !isGuardingArea()) {
            setBoundPos(null);
            setWandering(false);
            setStaying(true);
            player.displayClientMessage(Component.translatable("info.goety.servant.staying", getDisplayName()), true);
        } else if (!isGuardingArea()) {
            setBoundPos(blockPosition());
            setWandering(false);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.guard", getDisplayName()), true);
        } else {
            setBoundPos(null);
            setWandering(false);
            setStaying(false);
            player.displayClientMessage(Component.translatable("info.goety.servant.follow", getDisplayName()), true);
        }
        playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
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
    public void die(DamageSource source) {
        super.die(source);
        if (!level().isClientSide()) {
            finishAnimation(false);
            goalSelector.getRunningGoals().forEach(Goal::stop);
            startAnimation(DEATH_ANIMATION);
            setTarget(null);
        }
    }

    @Override
    public void tickDeath() {
        ++deathTime;
        setDeltaMovement(0.0D, Math.min(0.0D, getDeltaMovement().y), 0.0D);
        if (!level().isClientSide()) {
            setAnimation(DEATH_ANIMATION);
            setAnimationTick(deathTime);
            if (deathTime == 80) {
                playSound(getDeathSound(), 5.0F, getVoicePitch());
            }
            if (deathTime >= 60 && deathTime < 280) {
                if (deathTime % 3 == 0) {
                    sendSoulParticles(position().add(0.0D, 2.3D, 0.0D), 12);
                }
                for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                        getBoundingBox().inflate(10.0D, 8.0D, 10.0D), entity -> entity != this
                                && entity.isAlive() && !entity.isSpectator() && !isAlliedTo(entity)
                                && !(entity instanceof Player player && player.getAbilities().invulnerable))) {
                    victim.fallDistance = Math.min(victim.fallDistance, 4.5F);
                    if (deathTime >= 80 && deathTime < 260 && distanceToSqr(victim) > 64.0D) {
                        Vec3 pull = new Vec3(getX() - victim.getX(), 0.0D, getZ() - victim.getZ())
                                .normalize().scale(0.8D);
                        double vertical = getY() + 4.0D > victim.getY()
                                ? Math.max(0.4D, victim.getDeltaMovement().y) : victim.getDeltaMovement().y;
                        victim.setDeltaMovement(pull.x, vertical, pull.z);
                        victim.hurtMarked = true;
                        EntityUtil.sendPlayerVelocityPacket(victim);
                    }
                }
                if (deathTime >= 80 && random.nextInt(3) != 0) {
                    EndersoulFragmentServant fragment = ModEntityTypes.ENDERSOUL_FRAGMENT_SERVANT.get().create(level());
                    if (fragment != null) {
                        fragment.bindTo(this);
                        fragment.setPos(getX(), getY() + 3.8D, getZ());
                        fragment.setDeltaMovement((random.nextDouble() - 0.5D) * 1.5D,
                                random.nextDouble() * 0.8D, (random.nextDouble() - 0.5D) * 1.5D);
                        level().addFreshEntity(fragment);
                    }
                }
            }
            if (deathTime >= getDeathDuration()) {
                sendSoulParticles(position(), 256);
                discard();
            }
        }
    }

    public int getDeathDuration() {
        return Math.max(20, MutantEndermanServantConfig.DEATH_FADE_TICKS.get());
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 60) {
            for (int i = 0; i < 30; ++i) {
                level().addParticle(isOnFire() ? ParticleTypes.FLAME : ParticleTypes.POOF,
                        getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D),
                        random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D,
                        random.nextGaussian() * 0.02D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this || entity instanceof EndersoulCloneServant clone && clone.belongsTo(this)) {
            return true;
        }
        LivingEntity owner = getTrueOwner();
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) {
            return true;
        }
        if (entity instanceof com.Polarice3.Goety.api.entities.IOwned other
                && (getUUID().equals(other.getOwnerId())
                || getOwnerId() != null && getOwnerId().equals(other.getOwnerId()))) {
            return true;
        }
        if (entity instanceof net.minecraft.world.entity.OwnableEntity ownable && owner != null
                && ownable.getOwner() == owner) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void tick() {
        super.tick();
        updateAnimation();
        updatePoseState();
        if (!isAlive()) {
            return;
        }
        if (!level().isClientSide()) {
            updateCommand();
            updateGuardPosition();
            refreshVoidEyeEffects();
            updateCooldowns();
            if (!isNoAi()) {
                updateBlockGrab();
            }
            if (isStaying() && getTarget() == null) {
                getNavigation().stop();
            }
            if (!isAnimationPlaying()) {
                ServantTeleportHelper.tick(this);
            }
        }
    }

    private void updatePoseState() {
        previousArmScale = armScale;
        if (!level().isClientSide()) {
            setAggressive(getTarget() != null && isAlive());
        }
        hasTargetTicks = isAggressive() ? 20 : Math.max(0, hasTargetTicks - 1);
        if (hasTargetTicks > 0) {
            armScale = Math.min(10, armScale + 1);
        } else if (!isHoldingAnyBlock()) {
            armScale = Math.max(0, armScale - 1);
        }
        for (int arm = 0; arm < 4; arm++) {
            heldBlockTicks[arm] = hasTargetTicks > 0 && !getHeldBlock(arm).isAir()
                    ? Math.min(10, heldBlockTicks[arm] + 1) : Math.max(0, heldBlockTicks[arm] - 1);
        }
        if (level().isClientSide() && getAnimation() == SCREAM_ANIMATION
                && getAnimationTick() >= 40 && getAnimationTick() < 160) {
            for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(20.0D, 12.0D, 20.0D), this::canAttack)) {
                if (distanceToSqr(victim) <= square(MutantEndermanServantConfig.SCREAM_RADIUS.get())) {
                    victim.setXRot(victim.getXRot() + (random.nextFloat() - 0.3F) * 6.0F);
                }
            }
        }
        if (level().isClientSide() && !isClone()) {
            for (int i = 0; i < 3; i++) {
                level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D),
                        getRandomY() + (deathTime > 0 ? 0.75D : -0.25D), getRandomZ(0.5D),
                        (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(),
                        (random.nextDouble() - 0.5D) * 2.0D);
            }
        }
    }

    public int getHasTargetTicks() {
        return hasTargetTicks;
    }

    public float getArmScale(float partialTick) {
        return Mth.lerp(partialTick, previousArmScale, armScale) / 10.0F;
    }

    public int getHeldBlockTick(int arm) {
        return arm >= 0 && arm < 4 ? heldBlockTicks[arm] : 0;
    }

    public Optional<BlockPos> getTeleportPosition() {
        return entityData.get(TELEPORT_POSITION);
    }

    public boolean isClone() {
        return entityData.hasItem(CLONE_FORM) && entityData.get(CLONE_FORM);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return isClone() ? EntityDimensions.scalable(0.6F, 2.9F) : super.getDimensions(pose);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return isClone() ? 2.55F : 3.9F;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (CLONE_FORM.equals(key)) {
            refreshDimensions();
        }
    }

    private void updateCooldowns() {
        clawCooldown = Math.max(0, clawCooldown - 1);
        teleportCooldown = Math.max(0, teleportCooldown - 1);
        telesmashCooldown = Math.max(0, telesmashCooldown - 1);
        screamCooldown = Math.max(0, screamCooldown - 1);
        grabCooldown = Math.max(0, grabCooldown - 1);
        cloneCooldown = Math.max(0, cloneCooldown - 1);
        stareCooldown = Math.max(0, stareCooldown - 1);
        cloneProtectionTicks = Math.max(0, cloneProtectionTicks - 1);
    }

    private void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) {
            return;
        }
        if (getTarget().distanceToSqr(boundPos.getX() + 0.5D, boundPos.getY(), boundPos.getZ() + 0.5D)
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) {
            setTarget(null);
        }
    }

    public BlockState getHeldBlock(int arm) {
        if (arm < 0 || arm >= 4 || !entityData.hasItem(HELD_BLOCKS[arm])) {
            return Blocks.AIR.defaultBlockState();
        }
        return entityData.get(HELD_BLOCKS[arm]);
    }

    public void setHeldBlock(int arm, BlockState state) {
        if (arm < 0 || arm >= 4 || !entityData.hasItem(HELD_BLOCKS[arm])) {
            return;
        }
        entityData.set(HELD_BLOCKS[arm], state == null ? Blocks.AIR.defaultBlockState() : state);
    }

    public boolean isHoldingAnyBlock() {
        for (int arm = 0; arm < 4; arm++) {
            if (entityData.hasItem(HELD_BLOCKS[arm]) && !entityData.get(HELD_BLOCKS[arm]).isAir()) {
                return true;
            }
        }
        return false;
    }

    public int getActiveArm() {
        return entityData.hasItem(ACTIVE_ARM) ? entityData.get(ACTIVE_ARM) : 0;
    }

    public void setActiveArm(int arm) {
        if (entityData.hasItem(ACTIVE_ARM)) {
            entityData.set(ACTIVE_ARM, (byte) Mth.clamp(arm, 0, 3));
        }
    }

    private int getAvailableHand() {
        int firstEmpty = -1;
        int count = 0;
        for (int arm = 0; arm < 4; arm++) {
            if (getHeldBlock(arm).isAir()) {
                count++;
                if (firstEmpty < 0 || random.nextInt(count) == 0) {
                    firstEmpty = arm;
                }
            }
        }
        return firstEmpty;
    }

    private int getFavorableHand() {
        for (int arm = 0; arm < 4; arm++) {
            if (!getHeldBlock(arm).isAir() && (arm <= 1 || getActiveArm() != arm)) {
                return arm;
            }
        }
        return -1;
    }

    private void updateBlockGrab() {
        if (!MutantEndermanServantConfig.BLOCK_GRAB_ENABLED.get() || getTarget() == null) {
            return;
        }
        if (grabCooldown > 0 || isAnimationPlaying()) {
            return;
        }
        if (!ForgeEventFactory.getMobGriefingEvent(level(), this)) {
            grabCooldown = 40;
            return;
        }
        int hand = getAvailableHand();
        if (hand < 0) {
            grabCooldown = 60;
            return;
        }
        for (int attempt = 0; attempt < 12; attempt++) {
            BlockPos probe = blockPosition().offset(
                    random.nextInt(4) - 2, random.nextInt(5) - 2, random.nextInt(4) - 2);
            if (!level().hasChunkAt(probe) || !level().getWorldBorder().isWithinBounds(probe)) {
                continue;
            }
            BlockState state = level().getBlockState(probe);
            if (canBlockBeHeld(state) && state.getDestroySpeed(level(), probe) >= 0.0F
                    && level().destroyBlock(probe, false, this)) {
                setHeldBlock(hand, state);
                grabCooldown = MutantEndermanServantConfig.BLOCK_GRAB_COOLDOWN_SECONDS.get() * 20;
                return;
            }
        }
        grabCooldown = 20;
    }

    private boolean canBlockBeHeld(BlockState state) {
        return !state.isAir() && !state.hasBlockEntity()
                && state.isRedstoneConductor(net.minecraft.world.level.EmptyBlockGetter.INSTANCE, BlockPos.ZERO)
                && !state.is(net.minecraft.tags.BlockTags.create(
                        ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_enderman_holdable_immune")));
    }

    public static BlockPos findLanding(Mob mob, LivingEntity center, double minRadius, double maxRadius,
            int baseY, int verticalRange) {
        return ServantTeleportHelper.findLanding(mob, center, minRadius, maxRadius, baseY, verticalRange);
    }

    public Animation getAnimation() {
        if (!entityData.hasItem(ANIMATION_ID)) {
            return Animation.NONE;
        }
        int id = entityData.get(ANIMATION_ID);
        return id >= 0 && id < ANIMATIONS.length ? ANIMATIONS[id] : Animation.NONE;
    }

    public void setAnimation(Animation animation) {
        if (!entityData.hasItem(ANIMATION_ID)) {
            return;
        }
        entityData.set(ANIMATION_ID, animationIndex(animation));
        if (animation == null || animation == Animation.NONE) {
            entityData.set(ANIMATION_TICK, 0);
        }
    }

    public int getAnimationTick() {
        return entityData.hasItem(ANIMATION_TICK) ? entityData.get(ANIMATION_TICK) : 0;
    }

    public void setAnimationTick(int tick) {
        if (entityData.hasItem(ANIMATION_TICK)) {
            entityData.set(ANIMATION_TICK, Math.max(0, tick));
        }
    }

    public Animation[] getAnimations() {
        return ANIMATIONS.clone();
    }

    public boolean isAnimationPlaying() {
        return getAnimation() != Animation.NONE;
    }

    private int animationIndex(Animation animation) {
        for (int i = 0; i < ANIMATIONS.length; i++) {
            if (ANIMATIONS[i] == animation) {
                return i;
            }
        }
        return -1;
    }

    private void startAnimation(Animation animation) {
        setAnimation(animation);
        setAnimationTick(0);
        if (animation == MELEE_ANIMATION || animation == TELESMASH_ANIMATION) {
            ambientSoundTime = -getAmbientSoundInterval();
        }
        if (animation == STARE_ANIMATION) {
            playSound(stareSound(), 2.5F, 0.7F + random.nextFloat() * 0.2F);
        }
    }

    private void updateAnimation() {
        if (level().isClientSide() || !isAlive()) {
            return;
        }
        Animation animation = getAnimation();
        if (animation == Animation.NONE || animation == DEATH_ANIMATION) {
            return;
        }
        if (isNoAi()) {
            finishAnimation(false);
            return;
        }
        if (!isValidArtTarget(artTarget)) {
            artTarget = resolveContinuationTarget(artTarget);
        }
        int tick = getAnimationTick();
        advanceAnimationEffects(animation, tick);
        if (getAnimation() != animation) {
            return;
        }
        if (animation == STARE_ANIMATION && isValidArtTarget(artTarget) && !isBeingLookedAtBy(artTarget)) {
            finishAnimation(true);
        } else if (tick + 1 >= animation.duration()) {
            if (animation == MELEE_ANIMATION && pendingSecondClawArm >= 0) {
                chainSecondClaw();
                return;
            }
            finishAnimation(true);
        } else {
            setAnimationTick(tick + 1);
        }
    }

    private void chainSecondClaw() {
        int nextArm = pendingSecondClawArm;
        pendingSecondClawArm = -1;
        clawSwingIsSecond = true;
        setActiveArm(nextArm);
        setAnimationTick(0);
    }

    private void finishAnimation(boolean resolve) {
        Animation animation = getAnimation();
        if (animation == TELEPORT_ANIMATION && resolve) {
            this.teleportRaidInvulnUntil = level().getGameTime() + 20L;
            getTeleportPosition().ifPresent(pos -> {
                Vec3 destination = Vec3.atBottomCenterOf(pos);
                AABB box = getBoundingBox().move(destination.subtract(position()));
                if (level().noCollision(this, box) && !level().containsAnyLiquid(box)) {
                    stopRiding();
                    teleportTo(destination.x, destination.y, destination.z);
                    fallDistance = 0.0F;
                    setDeltaMovement(Vec3.ZERO);
                }
            });
        } else if (animation == STARE_ANIMATION && resolve && artTarget != null && canAttack(artTarget)) {
            artTarget.stopRiding();
            hurtWithVoidEffect(artTarget, piercingDamage(), scaleDamage(2.0F));
            artTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 160 + random.nextInt(140)));
            artTarget.setDeltaMovement((getX() - artTarget.getX()) * 0.1D, 0.3D,
                    (getZ() - artTarget.getZ()) * 0.1D);
            artTarget.hurtMarked = true;
            EntityUtil.sendPlayerVelocityPacket(artTarget);
        }
        if (animation == STARE_ANIMATION) {
            stareCooldown = 200;
        }
        if (isClone() || !clones.isEmpty()) {
            clearClones();
        }
        screamVictims.clear();
        entityData.set(TELEPORT_POSITION, Optional.empty());
        setAnimation(Animation.NONE);
        pendingSecondClawArm = -1;
        clawSwingIsSecond = false;
        voidEyeRaidRetreat = false;
        artTarget = null;
        getNavigation().stop();
    }

    private DamageSource piercingDamage() {
        return ModDamageSource.ownedDamageSource(level(),
                fuzs.mutantmonsters.init.ModRegistry.PIERCING_MOB_ATTACK_DAMAGE_TYPE,
                this, getTrueOwner() == null ? this : getTrueOwner());
    }

    private void advanceAnimationEffects(Animation animation, int tick) {
        if (animation == MELEE_ANIMATION && tick == CLAW_STRIKE_TICK) {
            resolveClaw();
        } else if (animation == THROW_ANIMATION && tick == 0) {
            launchRubble();
        } else if (animation == TELEPORT_ANIMATION && tick == 0) {
            teleportTowardsTarget();
            healFromVoidSkill(RAID_HEAL_BASE, RAID_HEAL_FULL);
        } else if (animation == TELESMASH_ANIMATION && tick == 0) {
            startTeleSmash();
        } else if (animation == TELESMASH_ANIMATION && tick == TELESMASH_STRIKE_TICK) {
            resolveTeleSmash();
        } else if (animation == SCREAM_ANIMATION && tick == SCREAM_STRIKE_TICK) {
            resolveScream();
        } else if (animation == SCREAM_ANIMATION && tick > SCREAM_STRIKE_TICK && tick < 160) {
            screamVictims.removeIf(victim -> !victim.isAlive() || !canAttack(victim)
                    || victim.level() != level() || distanceToSqr(victim) > square(
                            MutantEndermanServantConfig.SCREAM_RADIUS.get()));
            if ((tick - SCREAM_STRIKE_TICK) % 10 == 0) {
                applyScreamPulse();
            }
            for (LivingEntity victim : screamVictims) {
                victim.setXRot(victim.getXRot() + (random.nextFloat() - 0.3F) * 6.0F);
            }
        } else if (animation == CLONE_ANIMATION) {
            if (tick == 0) {
                spawnClones();
                healFromVoidSkill(CLONE_HEAL_BASE, CLONE_HEAL_FULL);
            } else {
                clones.removeIf(clone -> !clone.isAlive() || clone.level() != level());
                if (!clones.isEmpty()) {
                    if (tick % 30 == 0) {
                        for (Mob attacker : level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(24.0D),
                                mob -> canAttack(mob) && mob.getTarget() == this)) {
                            attacker.setTarget(clones.get(random.nextInt(clones.size())));
                        }
                    }
                    if (artTarget != null) {
                        getNavigation().moveTo(artTarget, 1.2D);
                        if (distanceToSqr(artTarget) < 4.0D && tick % 20 == 0) {
                            hurtWithVoidEffect(artTarget, ModDamageSource.summonAttack(this, getTrueOwner()),
                                    scaleDamage(1.0F));
                            swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }

    private static double square(double value) {
        return value * value;
    }

    private boolean isValidArtTarget(LivingEntity target) {
        return target != null && target.isAlive() && target.level() == level() && canAttack(target);
    }

    private LivingEntity resolveContinuationTarget(LivingEntity previous) {
        LivingEntity current = getTarget();
        if (current != null && current != previous && current.isAlive() && current.level() == level()
                && canAttack(current)) {
            return current;
        }
        return ServantSkillContinuity.reacquire(this, previous);
    }

    private void healFromVoidSkill(float normal, float fullyUpgraded) {
        if (!isAlive()) {
            return;
        }
        float amount = isFullyUpgraded() ? fullyUpgraded : normal;
        if (amount > 0.0F) {
            heal(amount);
        }
    }

    private void applyScreamPulse() {
        float damage = scaleDamage(MutantEndermanServantConfig.SCREAM_DAMAGE.get().floatValue());
        if (damage <= 0.0F) {
            return;
        }
        for (LivingEntity victim : screamVictims) {
            if (victim.isAlive() && canAttack(victim)) {
                hurtWithVoidEffect(victim, piercingDamage(), damage);
            }
        }
    }

    private void resolveClaw() {
        playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 1.2F));
        boolean dual = isFullyUpgraded();
        int arm = getActiveArm();
        if (clawSwingIsSecond) {
            clawSwingIsSecond = false;
            sweepClaw(arm, true, true);
            clawCooldown = 20 + random.nextInt(10);
            return;
        }
        sweepClaw(arm, dual, false);
        if (dual) {
            pendingSecondClawArm = randomArmOnOtherSide(arm);
        }
        clawCooldown = 20 + random.nextInt(10);
    }

    private int randomArmOnOtherSide(int arm) {
        return arm < 2 ? 2 + random.nextInt(2) : random.nextInt(2);
    }

    private void sweepClaw(int arm, boolean upgradedSweep, boolean ignoreInvulnerability) {
        boolean rightArm = arm >= 2;
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        double radius = upgradedSweep ? UPGRADED_CLAW_RADIUS : 4.0D;
        AABB area = getBoundingBox().inflate(radius);
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, area, this::canAttack)) {
            if (living == this) {
                continue;
            }
            double dist = distanceTo(living);
            double dx = getX() - living.getX();
            double dz = getZ() - living.getZ();
            if (getBoundingBox().minY >= living.getBoundingBox().maxY || dist > radius) {
                continue;
            }
            double angleWindow = upgradedSweep
                    ? UPGRADED_CLAW_HALF_ANGLE
                    : 3.0F + (1.0F - (float) (dist / radius)) * 40.0F;
            if (EntityUtil.getHeadAngle(this, dx, dz) >= angleWindow) {
                continue;
            }
            if (ignoreInvulnerability && living.invulnerableTime > 0) {
                living.invulnerableTime = 0;
            }
            hurtWithVoidEffect(living, ModDamageSource.summonAttack(this, getTrueOwner()),
                    damage > 0.0F ? scaleDamage(damage + (rightArm ? 1.0F : 3.0F)) : 0.0F);
            double knock = 0.4F + random.nextFloat() * 0.2F + (rightArm ? 0.0F : 0.2F);
            double inv = dist <= 1.0E-4D ? 1.0D : knock / dist;
            living.setDeltaMovement(living.getDeltaMovement().add(-dx * inv, knock * 0.6D, -dz * inv));
            living.hurtMarked = true;
            EntityUtil.sendPlayerVelocityPacket(living);
        }
    }

    private void launchRubble() {
        LivingEntity target = artTarget;
        if (!(level() instanceof ServerLevel serverLevel) || target == null || !target.isAlive()) {
            return;
        }
        int arm = getActiveArm();
        BlockState held = getHeldBlock(arm);
        if (held.isAir()) {
            arm = getFavorableHand();
            if (arm < 0) {
                return;
            }
            held = getHeldBlock(arm);
            setActiveArm(arm);
        }
        MutantEnderman standIn = getOrCreateThrowStandIn(serverLevel);
        if (standIn == null) {
            return;
        }
        standIn.setPos(getX(), getY(), getZ());
        standIn.setYRot(getYRot());
        standIn.yHeadRot = getYHeadRot();
        standIn.yBodyRot = getYRot();
        standIn.setHeldBlock(arm, Block.getId(held), 10);
        standIn.setTarget(target);
        MutantEndermanServantThrownBlock block = new MutantEndermanServantThrownBlock(standIn, arm, this);
        if (!serverLevel.addFreshEntity(block)) {
            return;
        }
        setHeldBlock(arm, Blocks.AIR.defaultBlockState());
    }

    private MutantEnderman getOrCreateThrowStandIn(ServerLevel serverLevel) {
        if (throwStandIn != null && throwStandInLevel == serverLevel && !throwStandIn.isRemoved()) {
            return throwStandIn;
        }
        throwStandIn = null;
        throwStandInLevel = null;
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_enderman"));
        if (type == null) {
            return null;
        }
        Object created = type.create(serverLevel);
        if (created instanceof MutantEnderman enderman) {
            throwStandIn = enderman;
            throwStandInLevel = serverLevel;
            enderman.setNoAi(true);
            enderman.setPersistenceRequired();
        }
        return throwStandIn;
    }

    private void teleportTowardsTarget() {
        teleportCooldown = MutantEndermanServantConfig.TELEPORT_COOLDOWN_SECONDS.get() * 20;
        BlockPos landing = artTarget == null ? null
                : voidEyeRaidRetreat ? findRetreatLanding(artTarget)
                : findLanding(this, artTarget, 2, 6, artTarget.blockPosition().getY(), 4);
        voidEyeRaidRetreat = false;
        if (landing == null) {
            return;
        }
        entityData.set(TELEPORT_POSITION, Optional.of(landing));
        Vec3 destination = Vec3.atBottomCenterOf(landing);
        voidErosionRaidPending = true;
        teleportBurst(position());
        teleportBurst(destination);
        getNavigation().stop();
    }

    private BlockPos findRetreatLanding(LivingEntity target) {
        BlockPos best = null;
        double bestDistance = -1.0D;
        double current = distanceToSqr(target);
        for (int attempt = 0; attempt < 10; attempt++) {
            BlockPos candidate = findLanding(this, this, VOID_EYE_RETREAT_MIN, VOID_EYE_RETREAT_MAX,
                    blockPosition().getY(), 6);
            if (candidate == null) {
                continue;
            }
            double distance = candidate.distSqr(target.blockPosition());
            if (distance > bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
            if (distance > current + 9.0D) {
                break;
            }
        }
        return best;
    }

    private void teleportBurst(Vec3 center) {
        boolean stun = hasVoidUpgrade(VOID_ECHO);
        AABB area = getBoundingBox().move(center.subtract(position())).inflate(3.0D);
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area, this::canAttack)) {
            if (hurtWithVoidEffect(victim, ModDamageSource.summonAttack(this, getTrueOwner()), scaleDamage(4.0F))) {
                if (voidErosionRaidPending) {
                    applyVoidTouched(victim, true);
                    voidErosionRaidPending = false;
                }
                if (stun) {
                    victim.addEffect(new MobEffectInstance(GoetyEffects.STUNNED.get(), 20, 0), this);
                }
                if (random.nextInt(3) == 0) {
                    victim.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140 + random.nextInt(60)));
                }
            }
            double dx = victim.getX() - center.x;
            double dz = victim.getZ() - center.z;
            victim.setDeltaMovement((6.0D * Math.signum(dx) - dx) * 0.2D, 0.2D,
                    (6.0D * Math.signum(dz) - dz) * 0.2D);
            victim.hurtMarked = true;
            EntityUtil.sendPlayerVelocityPacket(victim);
        }
        sendSoulParticles(center, 256);
        level().playSound(null, center.x, center.y, center.z, teleportSound(), getSoundSource(), 1.0F, 1.0F);
    }

    public static ParticleOptions soulParticle() {
        return MutantEndermanTeleSmashArea.soulParticle();
    }

    private void sendSoulParticles(Vec3 center, int count) {
        if (level() instanceof ServerLevel server) {
            server.sendParticles(soulParticle(), center.x, center.y + 1.5D, center.z, count,
                    getBbWidth() * 0.5D, getBbHeight() * 0.5D, getBbWidth() * 0.5D, 0.9D);
        }
    }

    private void startTeleSmash() {
        LivingEntity target = artTarget;
        if (target == null) {
            return;
        }
        if (MutantEndermanServantConfig.TELESMASH_ENABLED.get()) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 5));
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                    160 + target.getRandom().nextInt(160)));
        }
        getNavigation().stop();
    }

    private void resolveTeleSmash() {
        telesmashCooldown = MutantEndermanServantConfig.TELESMASH_COOLDOWN_SECONDS.get() * 20;
        LivingEntity target = artTarget;
        if (!(level() instanceof ServerLevel server) || target == null || !canAttack(target)) {
            return;
        }
        for (LivingEntity victim : MutantEndermanTeleSmashArea.victims(this, target, this::canAttack)) {
            teleSmashVictim(server, victim);
        }
    }

    private void teleSmashVictim(ServerLevel server, LivingEntity target) {
        MutantEndermanTeleSmashArea.lift(server, target, random);
        boolean hit = hurtWithVoidEffect(target, piercingDamage(),
                scaleDamage(MutantEndermanServantConfig.TELESMASH_DAMAGE.get().floatValue()));
        if (hit) {
            MutantEndermanLandingStrike.mark(target,
                    MutantEndermanServantConfig.TELESMASH_LANDING_DAMAGE.get());
        }
    }

    private void resolveScream() {
        playSound(screamSound(), 5.0F, 0.7F + random.nextFloat() * 0.2F);
        if (level().isClientSide()) {
            return;
        }
        double radius = MutantEndermanServantConfig.SCREAM_RADIUS.get();
        AABB box = getBoundingBox().inflate(radius, 12.0D, radius);
        float damage = scaleDamage(MutantEndermanServantConfig.SCREAM_DAMAGE.get().floatValue());
        sendSoulParticles(position(), 256);
        if (level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
            level().getLevelData().setRaining(false);
        }
        screamVictims.clear();
        screamVictims.addAll(level().getEntitiesOfClass(LivingEntity.class, box,
                e -> canAttack(e) && distanceToSqr(e) <= radius * radius));
        for (LivingEntity victim : screamVictims) {
            if (damage > 0.0F) {
                boolean hit = hurtWithVoidEffect(victim, piercingDamage(), damage);
                if (hit) {
                    applyVoidTouched(victim, true);
                }
            }
            if (!MutantEndermanServantConfig.SCREAM_DEBUFFS.get() || !(victim instanceof Mob mob)) {
                continue;
            }
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3));
            if (random.nextInt(2) == 0) {
                MobEffect poison = getTrueOwner() != null && CuriosFinder.hasWildSet(getTrueOwner())
                        ? GoetyEffects.ACID_VENOM.get() : MobEffects.POISON;
                mob.addEffect(new MobEffectInstance(poison, 120 + random.nextInt(180),
                        random.nextInt(2)), this);
            }
            if (random.nextInt(4) != 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300 + random.nextInt(300),
                        random.nextInt(2)));
            }
            if (random.nextInt(3) != 0) {
                MobEffect hunger = getTrueOwner() != null && CuriosFinder.hasWildSet(getTrueOwner())
                        ? GeneratedMod.FAMISHED.get() : MobEffects.HUNGER;
                mob.addEffect(new MobEffectInstance(hunger, 120 + random.nextInt(60),
                        10 + random.nextInt(2)));
            }
            if (random.nextInt(4) != 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120 + random.nextInt(400)));
            }
        }
        screamCooldown = MutantEndermanServantConfig.SCREAM_COOLDOWN_SECONDS.get() * 20;
    }

    private boolean isBeingLookedAtBy(LivingEntity target) {
        if (target == null || !canAttack(target) || !target.hasLineOfSight(this)) {
            return false;
        }
        if (target instanceof Mob mob) {
            return mob.getTarget() == this;
        }
        Vec3 direction = getEyePosition().subtract(target.getEyePosition());
        double distance = direction.length();
        return distance > 0.001D && target.getViewVector(1.0F).normalize().dot(direction.normalize())
                > 1.0D - 0.08D / distance;
    }

    private void spawnClones() {
        cloneCooldown = MutantEndermanServantConfig.CLONE_COOLDOWN_SECONDS.get() * 20;
        if (!(level() instanceof ServerLevel server)) {
            finishAnimation(false);
            return;
        }
        LivingEntity anchor = artTarget;
        entityData.set(CLONE_FORM, true);
        cloneProtectionTicks = 20;
        cloneRevealHits = 0;
        setMaxUpStep(1.0F);
        stopRiding();
        removeAllEffects();
        refreshVoidEyeEffects();
        playSound(morphSound(), 1.0F, getVoicePitch());
        sendSoulParticles(position(), 256);
        for (int i = 0; i < MutantEndermanServantConfig.CLONE_COUNT.get(); i++) {
            EndersoulCloneServant clone = ModEntityTypes.ENDERSOUL_CLONE_SERVANT.get().create(server);
            if (clone == null) {
                continue;
            }
            clone.bindTo(this);
            BlockPos landing = anchor == null ? null
                    : findLanding(clone, anchor, 2, 12, anchor.blockPosition().getY(), 6);
            if (landing == null) {
                landing = findLanding(clone, this, 1, 6, blockPosition().getY(), 6);
            }
            if (landing == null) {
                clone.discard();
                continue;
            }
            clone.moveTo(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D, getYRot(), 0.0F);
            clone.setLimitedLife(summonLifespanTicks());
            if (server.addFreshEntity(clone)) {
                clones.add(clone);
                EntityUtil.sendParticlePacket(clone, soulParticle(), 256);
            }
        }
        if (clones.isEmpty()) {
            cloneCooldown = 40;
        } else {
            trySummonWatchlings(true);
            BlockPos landing = anchor == null ? null
                    : findLanding(this, anchor, 3, 12, anchor.blockPosition().getY(), 6);
            if (landing == null) {
                landing = findLanding(this, this, 3, 12, blockPosition().getY(), 6);
            }
            if (landing != null) {
                super.teleportTo(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D);
                sendSoulParticles(position(), 256);
            }
            for (Mob attacker : level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(24.0D),
                    mob -> canAttack(mob) && mob.getTarget() == this)) {
                attacker.setTarget(clones.get(random.nextInt(clones.size())));
            }
        }
    }

    private void clearClones() {
        boolean morphed = isClone();
        if (!clones.isEmpty()) {
            cloneCooldown = MutantEndermanServantConfig.CLONE_COOLDOWN_SECONDS.get() * 20;
        }
        entityData.set(CLONE_FORM, false);
        cloneProtectionTicks = 0;
        cloneRevealHits = 0;
        setMaxUpStep(1.4F);
        for (EndersoulCloneServant clone : clones) {
            for (Mob attacker : level().getEntitiesOfClass(Mob.class, clone.getBoundingBox().inflate(24.0D),
                    mob -> canAttack(mob) && mob.getTarget() == clone)) {
                attacker.setTarget(isAlive() ? this : null);
            }
            clone.dismiss();
        }
        clones.clear();
        if (morphed && !isRemoved()) {
            playSound(morphSound(), 1.0F, getVoicePitch());
            sendSoulParticles(position(), 256);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source) || (source.getEntity() != null && isAlliedTo(source.getEntity()))) {
            return false;
        }
        boolean forced = source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || source.is(ModDamageSource.DISMISSED);
        if (!forced && (getAnimation() == TELEPORT_ANIMATION || getAnimation() == SCREAM_ANIMATION
                || isClone() && cloneProtectionTicks > 0)) {
            return false;
        }
        if (!forced && level().getGameTime() < this.teleportRaidInvulnUntil) {
            return false;
        }
        if (!forced && hasVoidUpgrade(VOID_ECHO) && level().getGameTime() < nextVoidEchoHitTick) return false;
        if (!forced && isClone() && isFullyUpgraded() && cloneRevealHits == 0) {
            ++cloneRevealHits;
            if (!level().isClientSide()) {
                playSound(getHurtSound(source), 1.0F, getVoicePitch());
                sendSoulParticles(position(), 32);
            }
            return false;
        }
        boolean revealHit = !forced && isClone() && isFullyUpgraded();
        float applied = amount;
        if (!forced && hasVoidUpgrade(VOID_ECHO)) {
            applied *= MutantEndermanServantConfig.VOID_ECHO_DAMAGE_MULTIPLIER.get().floatValue();
        }
        if (revealHit) {
            applied *= 0.5F;
        }
        float healthBefore = this.getHealth();
        boolean hit = super.hurt(source, applied);
        if (hit && amount > 0.0F && !forced && hasVoidUpgrade(VOID_ECHO) && !level().isClientSide()) {
            nextVoidEchoHitTick = level().getGameTime() + MutantEndermanServantConfig.VOID_ECHO_PROTECTION_TICKS.get();
        }
        float taken = Math.max(0.0F, healthBefore - this.getHealth());
        boolean forcedRaid = false;
        if (hit && taken > 0.0F && hasVoidEyeUpgrade() && !isClone() && !level().isClientSide()) {
            voidEyeDamageAccum += taken;
            if (voidEyeDamageAccum >= VOID_EYE_RAID_DAMAGE) {
                voidEyeDamageAccum = Math.min(voidEyeDamageAccum - VOID_EYE_RAID_DAMAGE,
                        VOID_EYE_RAID_DAMAGE - 0.001F);
                forcedRaid = startVoidEyeRaid(source);
            }
            if (!forcedRaid) {
                long now = level().getGameTime();
                if (this.voidEyeHitWindowCount == 0 || now - this.voidEyeHitWindowStart > 60L) {
                    this.voidEyeHitWindowStart = now;
                    this.voidEyeHitWindowCount = 1;
                } else {
                    ++this.voidEyeHitWindowCount;
                }
                if (this.voidEyeHitWindowCount >= 4) {
                    this.voidEyeHitWindowCount = 0;
                    forcedRaid = startVoidEyeRaid(source);
                }
            }
        }
        if (hit && !level().isClientSide() && isAlive()) {
            if (isClone()) {
                if (!isFullyUpgraded() || cloneRevealHits >= 1) {
                    finishAnimation(false);
                }
            } else if (getAnimation() == STARE_ANIMATION) {
                finishAnimation(false);
            } else if (!forcedRaid && !isAnimationPlaying() && !isNoAi()
                    && MutantEndermanServantConfig.TELEPORT_ENABLED.get() && teleportCooldown == 0
                    && (source.is(DamageTypeTags.IS_PROJECTILE) || random.nextInt(3) != 0)) {
                LivingEntity target = getTarget();
                if (target == null && source.getEntity() instanceof LivingEntity attacker && canAttack(attacker)) {
                    setTarget(attacker);
                    target = attacker;
                }
                if (target != null && canAttack(target)) {
                    artTarget = target;
                    startAnimation(TELEPORT_ANIMATION);
                }
            }
        }
        return hit;
    }

    private boolean startVoidEyeRaid(DamageSource source) {
        LivingEntity target = getTarget();
        if (target == null && source.getEntity() instanceof LivingEntity attacker && canAttack(attacker)) {
            target = attacker;
            setTarget(attacker);
        }
        if (target == null || !canAttack(target)) {
            return false;
        }
        double distance = distanceTo(target);
        if (distance < VOID_EYE_RETREAT_DISTANCE) {
            voidEyeRaidRetreat = true;
        } else {
            voidEyeRaidRetreat = false;
        }
        artTarget = target;
        startAnimation(TELEPORT_ANIMATION);
        return true;
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!level().isClientSide() && clones != null && !clones.isEmpty()) {
            clearClones();
        }
        super.remove(reason);
    }

    public static final class EndermanArtsGoal extends Goal {
        private final MutantEndermanServant servant;
        private int cloneReadyTicks;
        private int screamReadyTicks;
        private int telesmashReadyTicks;
        private int throwReadyTicks;
        private int teleportReadyTicks;

        private int ticksUntilNextPathRecalculation;

        EndermanArtsGoal(MutantEndermanServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = servant.getTarget();
            return target != null && target.isAlive() && servant.canAttack(target);
        }

        @Override
        public boolean canContinueToUse() {
            return servant.isAnimationPlaying() || canUse();
        }

        @Override
        public void start() {
            servant.grabCooldown = Math.min(servant.grabCooldown, 40);
            this.ticksUntilNextPathRecalculation = 0;
        }

        @Override
        public void stop() {
            cloneReadyTicks = screamReadyTicks = telesmashReadyTicks = throwReadyTicks = teleportReadyTicks = 0;
            servant.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity target = servant.getTarget();
            if (target != null) {
                servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (servant.isAnimationPlaying()) {
                if (target == null || (servant.getAnimation() != THROW_ANIMATION
                        && servant.getAnimation() != CLONE_ANIMATION)) {
                    servant.getNavigation().stop();
                }
                return;
            }
            if (target == null) {
                return;
            }
            double distSqr = servant.distanceToSqr(target);
            boolean visible = servant.hasLineOfSight(target);
            cloneReadyTicks = readyTicks(cloneReadyTicks, MutantEndermanServantConfig.CLONES_ENABLED.get()
                    && servant.cloneCooldown == 0 && distSqr < 400.0D);
            screamReadyTicks = readyTicks(screamReadyTicks, MutantEndermanServantConfig.SCREAM_ENABLED.get()
                    && servant.screamCooldown == 0 && distSqr <= square(
                            MutantEndermanServantConfig.SCREAM_RADIUS.get()));
            telesmashReadyTicks = readyTicks(telesmashReadyTicks,
                    MutantEndermanServantConfig.TELESMASH_ENABLED.get() && servant.telesmashCooldown == 0
                            && distSqr >= square(MutantEndermanServantConfig.TELESMASH_MIN_DISTANCE.get())
                            && distSqr <= square(MutantEndermanServantConfig.TELESMASH_MAX_DISTANCE.get()) && visible);
            throwReadyTicks = readyTicks(throwReadyTicks, MutantEndermanServantConfig.BLOCK_THROW_ENABLED.get()
                    && servant.getFavorableHand() >= 0
                    && distSqr >= square(MutantEndermanServantConfig.BLOCK_THROW_MIN_DISTANCE.get())
                    && distSqr <= square(MutantEndermanServantConfig.BLOCK_THROW_MAX_DISTANCE.get()) && visible);
            teleportReadyTicks = readyTicks(teleportReadyTicks, MutantEndermanServantConfig.TELEPORT_ENABLED.get()
                    && servant.teleportCooldown == 0
                    && distSqr >= square(MutantEndermanServantConfig.TELEPORT_MIN_DISTANCE.get())
                    && distSqr <= square(MutantEndermanServantConfig.TELEPORT_MAX_DISTANCE.get()));

            if (cloneReadyTicks >= 100) {
                begin(target, CLONE_ANIMATION);
                return;
            }
            if (screamReadyTicks >= 160) {
                begin(target, SCREAM_ANIMATION);
                return;
            }
            if (telesmashReadyTicks >= 60) {
                begin(target, TELESMASH_ANIMATION);
                return;
            }
            if (throwReadyTicks >= 40) {
                beginThrow(target);
                return;
            }
            if (teleportReadyTicks >= 40) {
                begin(target, TELEPORT_ANIMATION);
                return;
            }
            if (cloneReadyTicks > 0 && servant.random.nextInt(40) == 0) {
                begin(target, CLONE_ANIMATION);
                return;
            }
            if (screamReadyTicks > 0 && servant.random.nextInt(servant.isInWaterOrRain() ? 40 : 80) == 0) {
                begin(target, SCREAM_ANIMATION);
                return;
            }
            if (telesmashReadyTicks > 0 && servant.random.nextInt(20) == 0) {
                begin(target, TELESMASH_ANIMATION);
                return;
            }
            if (throwReadyTicks > 0 && servant.random.nextInt(12) == 0) {
                beginThrow(target);
                return;
            }
            if (teleportReadyTicks > 0 && servant.random.nextInt(20) == 0) {
                begin(target, TELEPORT_ANIMATION);
                return;
            }
            if (MutantEndermanServantConfig.CLAW_ENABLED.get() && servant.clawCooldown == 0
                    && distSqr <= square(Math.max(3.5D, MutantEndermanServantConfig.CLAW_REACH.get() - 0.5D))) {
                servant.setActiveArm(servant.random.nextInt(4));
                begin(target, MELEE_ANIMATION);
                return;
            }
            if (servant.stareCooldown == 0 && distSqr > 16.0D && distSqr < 1024.0D
                    && servant.isBeingLookedAtBy(target) && servant.random.nextInt(80) == 0) {
                begin(target, STARE_ANIMATION);
                return;
            }
            if (distSqr > 9.0D) {
                if (--this.ticksUntilNextPathRecalculation <= 0) {
                    this.ticksUntilNextPathRecalculation = 4 + servant.getRandom().nextInt(7);
                    servant.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.2D);
                }
            } else {
                servant.getNavigation().stop();
            }
        }

        private void begin(LivingEntity target, Animation animation) {
            if (animation == CLONE_ANIMATION) cloneReadyTicks = 0;
            if (animation == SCREAM_ANIMATION) screamReadyTicks = 0;
            if (animation == TELESMASH_ANIMATION) telesmashReadyTicks = 0;
            if (animation == THROW_ANIMATION) throwReadyTicks = 0;
            if (animation == TELEPORT_ANIMATION) teleportReadyTicks = 0;
            servant.artTarget = target;
            servant.startAnimation(animation);
        }

        private void beginThrow(LivingEntity target) {
            servant.setActiveArm(servant.getFavorableHand());
            begin(target, THROW_ANIMATION);
        }

        private static int readyTicks(int ticks, boolean eligible) {
            return eligible ? Math.min(ticks + 1, 200) : 0;
        }

        private static double square(double value) {
            return value * value;
        }
    }

}
