package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveIceSpikeServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.model.CorrosiverServantModel;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveSnowCloudServantEntity;
import cn.blockforge.generated.goetysupreme.item.DracognathusRevivalHornItem;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.spell.FrostbornFocus;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.WitherServantClientControls;
import cn.blockforge.generated.goetysupreme.event.WitherServantClientEvents;

public class CorrosiverServant extends AbstractJerotesServant {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState idleWindAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();
    public final AnimationState attack4AnimationState = new AnimationState();
    public final AnimationState attack5AnimationState = new AnimationState();
    public final AnimationState attack6AnimationState = new AnimationState();
    public final AnimationState attack7AnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();
    public final AnimationState windAnimationState = new AnimationState();
    public final AnimationState snowAnimationState = new AnimationState();
    public final AnimationState iceAnimationState = new AnimationState();
    public final AnimationState teleportAnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();
    public final AnimationState startAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_USE =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_WIND =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_WIND_ANIM =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANGRY_ANIM =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BEAST_TOUGH_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_BEAST_TOUGH =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CAIRN_SOUL_JAR =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FROSTBORN_FOCUS =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SPELL_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BREATH_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BREATH_USE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SNOW_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SNOW_USE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_WINDCALLER_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_WINDCALLER_USE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ICE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ICE_USE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TELEPORT_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TELEPORT_USE_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_START_TICK =
            SynchedEntityData.defineId(CorrosiverServant.class, EntityDataSerializers.INT);

    private static final int BASE_SPELL_LEVEL = 5;

    private static final int ATTACK_HIT_TICK = 5;

    private static final int ATTACK_READY_TICK = -5;

    private static final int DEFAULT_TOUGH_INVULN_TICKS = 20;

    private static final float DAMAGE_MULTIPLIER = 0.71428573F;

    private static final int BREATH_COOLDOWN = 240;
    private static final int ICE_COOLDOWN = 300;
    private static final int SNOW_COOLDOWN = 480;
    private static final int WINDCALLER_COOLDOWN = 720;
    private static final int TELEPORT_COOLDOWN = 300;

    private static final TagKey<net.minecraft.world.item.Item> MEAT = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "meat"));

    private static final UUID CAIRN_JAR_HEALTH_UUID =
            UUID.fromString("9f2c6d51-8a1b-4e2f-9c3d-71a5e0b6d241");

    private static final UUID FROSTBORN_HEALTH_UUID =
            UUID.fromString("c47d0f93-2b5e-4a17-8d64-5f9c31e7a082");

    private static final TagKey<EntityType<?>> NO_SPECIAL_KNOCKBACK =
            TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath("jerotes", "no_special_knockback"));

    private int animTick;
    private int spellLevel = BASE_SPELL_LEVEL;
    private int healSelf = 30;

    public int start = -101;
    private float health1 = 1.0F;

    public float thisTickRenderTime;
    public float lastTickRenderTime = 6.0F;
    public float windAnimProgress;
    public float angryAnimProgress;

    public CorrosiverServant(EntityType<? extends CorrosiverServant> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.1F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 225.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.6D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Animal.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/corrosiver/corrosiver.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.CORROSIVER;
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public float getSoundVolume() {
        return 5.0F;
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 3.185F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("corrosiver_ambient", SoundEvents.RAVAGER_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("corrosiver_hurt", SoundEvents.RAVAGER_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("corrosiver_death", SoundEvents.RAVAGER_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(jerotesSound("corrosiver_walk", SoundEvents.RAVAGER_STEP), 0.15F, 1.0F);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return !this.isBeastTough() && super.isOnFire();
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean isFreezing() {
        return false;
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_STATE, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_ATTACK_USE, 0);
        this.entityData.define(DATA_WIND, false);
        this.entityData.define(DATA_WIND_ANIM, 0);
        this.entityData.define(DATA_ANGRY_ANIM, 0);
        this.entityData.define(DATA_BEAST_TOUGH_TICK, 0);
        this.entityData.define(DATA_BEAST_TOUGH, false);
        this.entityData.define(DATA_CAIRN_SOUL_JAR, false);
        this.entityData.define(DATA_FROSTBORN_FOCUS, false);
        this.entityData.define(DATA_SPELL_TICK, 0);
        this.entityData.define(DATA_BREATH_TICK, 0);
        this.entityData.define(DATA_BREATH_USE_TICK, 0);
        this.entityData.define(DATA_SNOW_TICK, 0);
        this.entityData.define(DATA_SNOW_USE_TICK, 0);
        this.entityData.define(DATA_WINDCALLER_TICK, 0);
        this.entityData.define(DATA_WINDCALLER_USE_TICK, 0);
        this.entityData.define(DATA_ICE_TICK, 0);
        this.entityData.define(DATA_ICE_USE_TICK, 0);
        this.entityData.define(DATA_TELEPORT_TICK, 0);
        this.entityData.define(DATA_TELEPORT_USE_TICK, 0);
        this.entityData.define(DATA_START_TICK, 0);
    }

    public int getAnimTick() {
        return this.animTick;
    }

    public void setAnimTick(int ticks) {
        this.animTick = ticks;
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAttackTick(int ticks) {
        this.entityData.set(DATA_ATTACK_TICK, ticks);
    }

    public int getAttackUse() {
        return this.entityData.get(DATA_ATTACK_USE);
    }

    public void setAttackUse(int use) {
        this.entityData.set(DATA_ATTACK_USE, use);
    }

    public boolean isWind() {
        return this.entityData.get(DATA_WIND);
    }

    public void setWind(boolean wind) {
        this.entityData.set(DATA_WIND, wind);
    }

    public int getWindAnim() {
        return this.entityData.get(DATA_WIND_ANIM);
    }

    public int getAngryAnim() {
        return this.entityData.get(DATA_ANGRY_ANIM);
    }

    public boolean isBeastTough() {
        return this.entityData.get(DATA_BEAST_TOUGH);
    }

    public void setBeastTough(boolean value) {
        this.entityData.set(DATA_BEAST_TOUGH, value);
    }

    public int getBeastToughTick() {
        return this.entityData.get(DATA_BEAST_TOUGH_TICK);
    }

    public void setBeastToughTick(int ticks) {
        this.entityData.set(DATA_BEAST_TOUGH_TICK, ticks);
    }

    private int summonLifespanTicks() {
        int lifespan = ServantLimits.SUMMONED_LIFESPAN_TICKS;
        if (hasCairnSoulJar()) {
            lifespan += 30 * 20;
        }
        if (hasFrostbornFocus()) {
            lifespan += 30 * 20;
        }
        return lifespan;
    }

    public boolean hasCairnSoulJar() {
        return this.entityData.hasItem(DATA_CAIRN_SOUL_JAR) && this.entityData.get(DATA_CAIRN_SOUL_JAR);
    }

    public boolean hasFrostbornFocus() {
        return this.entityData.hasItem(DATA_FROSTBORN_FOCUS) && this.entityData.get(DATA_FROSTBORN_FOCUS);
    }

    public float skillDamageScale() {
        if (!hasFrostbornFocus()) {
            return 1.0F;
        }
        Double scale = JerotesVillageServantConfig.CORROSIVER_FROSTBORN_DAMAGE_SCALE.get();
        return scale == null ? 1.2F : scale.floatValue();
    }

    public static float ownerSkillScale(Entity owner) {
        return owner instanceof CorrosiverServant cs ? cs.skillDamageScale() : 1.0F;
    }

    private int cooldownTicks(int base) {
        if (!hasCairnSoulJar()) {
            return base;
        }
        Double scale = JerotesVillageServantConfig.CORROSIVER_CAIRN_JAR_COOLDOWN_SCALE.get();
        double s = scale == null ? 0.75D : scale;
        return Math.max(1, (int) Math.round(base * s));
    }

    private int toughInvulnTicks() {
        Integer ticks = JerotesVillageServantConfig.CORROSIVER_TOUGH_INVULN_TICKS.get();
        return ticks == null ? DEFAULT_TOUGH_INVULN_TICKS : Math.max(0, ticks);
    }

    public int getSpellTick() {
        return this.entityData.get(DATA_SPELL_TICK);
    }

    public void setSpellTick(int ticks) {
        this.entityData.set(DATA_SPELL_TICK, ticks);
    }

    public int getBreathTick() {
        return this.entityData.get(DATA_BREATH_TICK);
    }

    public void setBreathTick(int ticks) {
        this.entityData.set(DATA_BREATH_TICK, ticks);
    }

    public int getBreathUseTick() {
        return this.entityData.get(DATA_BREATH_USE_TICK);
    }

    public void setBreathUseTick(int ticks) {
        this.entityData.set(DATA_BREATH_USE_TICK, ticks);
    }

    public int getSnowTick() {
        return this.entityData.get(DATA_SNOW_TICK);
    }

    public void setSnowTick(int ticks) {
        this.entityData.set(DATA_SNOW_TICK, ticks);
    }

    public int getSnowUseTick() {
        return this.entityData.get(DATA_SNOW_USE_TICK);
    }

    public void setSnowUseTick(int ticks) {
        this.entityData.set(DATA_SNOW_USE_TICK, ticks);
    }

    public int getWindcallerTick() {
        return this.entityData.get(DATA_WINDCALLER_TICK);
    }

    public void setWindcallerTick(int ticks) {
        this.entityData.set(DATA_WINDCALLER_TICK, ticks);
    }

    public int getWindcallerUseTick() {
        return this.entityData.get(DATA_WINDCALLER_USE_TICK);
    }

    public void setWindcallerUseTick(int ticks) {
        this.entityData.set(DATA_WINDCALLER_USE_TICK, ticks);
    }

    public int getIceTick() {
        return this.entityData.get(DATA_ICE_TICK);
    }

    public void setIceTick(int ticks) {
        this.entityData.set(DATA_ICE_TICK, ticks);
    }

    public int getIceUseTick() {
        return this.entityData.get(DATA_ICE_USE_TICK);
    }

    public void setIceUseTick(int ticks) {
        this.entityData.set(DATA_ICE_USE_TICK, ticks);
    }

    public int getTeleportTick() {
        return this.entityData.get(DATA_TELEPORT_TICK);
    }

    public void setTeleportTick(int ticks) {
        this.entityData.set(DATA_TELEPORT_TICK, ticks);
    }

    public int getTeleportUseTick() {
        return this.entityData.get(DATA_TELEPORT_USE_TICK);
    }

    public void setTeleportUseTick(int ticks) {
        this.entityData.set(DATA_TELEPORT_USE_TICK, ticks);
    }

    public int getStartTick() {
        return this.entityData.get(DATA_START_TICK);
    }

    public void setStartTick(int ticks) {
        this.entityData.set(DATA_START_TICK, ticks);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!DATA_ANIM_STATE.equals(key) || !this.level().isClientSide) {
            return;
        }
        AnimationState state = animationForId(this.entityData.get(DATA_ANIM_STATE));
        if (state == null) {
            allAnimations().forEach(AnimationState::stop);
            return;
        }
        state.start(this.tickCount);
        for (AnimationState other : allAnimations()) {
            if (other != state) {
                other.stop();
            }
        }
    }

    private void playAnimation(String name, int ticks) {
        if (this.level().isClientSide) {
            return;
        }
        this.animTick = ticks;
        this.entityData.set(DATA_ANIM_STATE, animId(name));
    }

    private static int animId(String name) {
        return switch (name) {
            case "attack1" -> 1;
            case "attack2" -> 2;
            case "attack3" -> 3;
            case "attack4" -> 4;
            case "attack5" -> 5;
            case "attack6" -> 6;
            case "attack7" -> 7;
            case "breath" -> 8;
            case "wind" -> 9;
            case "snow" -> 10;
            case "ice" -> 11;
            case "teleport" -> 12;
            case "dead" -> 13;
            case "start" -> 14;
            default -> 0;
        };
    }

    private AnimationState animationForId(int id) {
        return switch (id) {
            case 1 -> this.attack1AnimationState;
            case 2 -> this.attack2AnimationState;
            case 3 -> this.attack3AnimationState;
            case 4 -> this.attack4AnimationState;
            case 5 -> this.attack5AnimationState;
            case 6 -> this.attack6AnimationState;
            case 7 -> this.attack7AnimationState;
            case 8 -> this.breathAnimationState;
            case 9 -> this.windAnimationState;
            case 10 -> this.snowAnimationState;
            case 11 -> this.iceAnimationState;
            case 12 -> this.teleportAnimationState;
            case 13 -> this.deadAnimationState;
            case 14 -> this.startAnimationState;
            default -> null;
        };
    }

    private List<AnimationState> allAnimations() {
        return List.of(this.idleAnimationState, this.idleWindAnimationState,
                this.attack1AnimationState, this.attack2AnimationState, this.attack3AnimationState,
                this.attack4AnimationState, this.attack5AnimationState, this.attack6AnimationState,
                this.attack7AnimationState, this.breathAnimationState, this.windAnimationState,
                this.snowAnimationState, this.iceAnimationState, this.teleportAnimationState,
                this.deadAnimationState, this.startAnimationState);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.lastTickRenderTime = this.thisTickRenderTime;
            this.thisTickRenderTime = 0.0F;
            if (this.isAlive()) {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.idleWindAnimationState.startIfStopped(this.tickCount);
            }
            for (int i = 0; i < 3; ++i) {
                this.level().addParticle(ParticleTypes.SNOWFLAKE,
                        this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D),
                        0.0D, 0.0D, 0.0D);
            }
            return;
        }
        serverTick();
    }

    private void serverTick() {
        if (this.animTick > 0) {
            --this.animTick;
            if (this.animTick == 0) {
                this.entityData.set(DATA_ANIM_STATE, 0);
            }
        }
        if (this.getAttackTick() > ATTACK_READY_TICK) {
            this.setAttackTick(this.getAttackTick() - 1);
        }
        if (this.getBeastToughTick() > 0) {
            this.setBeastToughTick(this.getBeastToughTick() - 1);
        }
        this.setBeastTough(this.getBeastToughTick() > 0);
        if (this.isBeastTough() && this.getRemainingFireTicks() > 0) {
            this.clearFire();
        }
        this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));

        tickStart();

        if (this.walkAnimation.speed() > 0.0F && this.onGround()
                && this.getHealth() > this.getMaxHealth() / 4.0F) {
            float phase = Math.abs(Mth.cos(this.walkAnimation.position() * 0.8F - 1.5F));
            if (phase < 0.2F) {
                applyQuake(this.getBoundingBox().inflate(8.0D), 20, 0);
            }
        }

        if (this.random.nextInt(60) == 1 && this.isAlive()) {
            this.heal(3.0F);
        }

        if (this.isInWater() && !(this.navigation instanceof AmphibiousPathNavigation)) {
            this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.2F, true);
            this.navigation = new AmphibiousPathNavigation(this, this.level());
        } else if (!this.isInWater() && this.navigation instanceof AmphibiousPathNavigation) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.level());
        }

        if (this.getAttackTick() == ATTACK_HIT_TICK && this.isAlive()) {
            trueHurt();
        }
        if (this.getAttackTick() > 8 && this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget(), 360.0F, 360.0F);
        }

        tickSpells();

        tickWind();
    }

    private void tickStart() {
        if (this.start > 0) {
            --this.start;
            if (this.start == 0) {
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            jerotesSound("corrosiver_ambient", SoundEvents.RAVAGER_AMBIENT),
                            SoundSource.NEUTRAL, 10.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
                }
                this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 660, 1));
                this.start = -1;
            }
        } else if (this.start > -100) {
            --this.start;
            if (this.start <= -100) {
                this.start = -101;
            }
        }
        if (this.getStartTick() > 0) {
            this.setStartTick(this.getStartTick() - 1);
        }
    }

    private void tickSpells() {
        if (this.level().isClientSide) {
            return;
        }
        LivingEntity target = this.getTarget();

        int windcallerCap = this.cooldownTicks(WINDCALLER_COOLDOWN);
        int iceCap = this.cooldownTicks(ICE_COOLDOWN);
        int breathCap = this.cooldownTicks(BREATH_COOLDOWN);
        int snowCap = this.cooldownTicks(SNOW_COOLDOWN);
        int teleportCap = this.cooldownTicks(TELEPORT_COOLDOWN);

        if (this.getSpellTick() > 0) {
            this.setSpellTick(this.getSpellTick() - 1);
        }
        if (this.getWindcallerTick() < windcallerCap) {
            this.setWindcallerTick(this.getWindcallerTick() + 1);
        }
        if (this.getIceTick() < iceCap) {
            this.setIceTick(this.getIceTick() + 1);
        }
        if (this.getBreathTick() < breathCap) {
            this.setBreathTick(this.getBreathTick() + 1);
        }
        if (this.getSnowTick() < snowCap) {
            this.setSnowTick(this.getSnowTick() + 1);
        }
        if (this.getTeleportTick() < teleportCap) {
            this.setTeleportTick(this.getTeleportTick() + 1);
        }
        if (this.getWindcallerUseTick() > 0) {
            this.setWindcallerUseTick(this.getWindcallerUseTick() - 1);
        }
        if (this.getIceUseTick() > 0) {
            this.setIceUseTick(this.getIceUseTick() - 1);
        }
        if (this.getBreathUseTick() > 0) {
            this.setBreathUseTick(this.getBreathUseTick() - 1);
        }
        if (this.getSnowUseTick() > 0) {
            this.setSnowUseTick(this.getSnowUseTick() - 1);
        }
        if (this.getTeleportUseTick() > 0) {
            this.setTeleportUseTick(this.getTeleportUseTick() - 1);
        }

        if (this.getBreathTick() >= breathCap && this.spellNeed(0.0D, 16.0D, 1)) {
            this.setSpellTick(25);
            this.setBreathUseTick(20);
        }
        if (this.getBreathUseTick() == 20) {
            this.setBreathTick(0);
            this.setAnimTick(20);
            this.playAnimation("breath", 20);
        } else if (this.getBreathUseTick() == 10) {
            castBreath(target == null ? this : target);
        }

        if (this.getIceTick() >= iceCap && this.spellNeed(0.0D, 14.0D, 1)) {
            this.setSpellTick(35);
            this.setIceUseTick(30);
        }
        if (this.getIceUseTick() == 30) {
            this.setIceTick(0);
            this.setAnimTick(30);
            this.playAnimation("ice", 30);
        } else if (this.getIceUseTick() == 10) {
            castIceSpike(target == null ? this : target);
        }

        if (this.getWindcallerTick() >= windcallerCap && this.spellNeed(0.0D, 18.0D, 4)) {
            this.setSpellTick(25);
            this.setWindcallerUseTick(20);
        }
        if (this.getWindcallerUseTick() == 20) {
            this.setWindcallerTick(0);
            this.setAnimTick(20);
            this.playAnimation("wind", 20);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    jerotesSound("corrosiver_windcaller", SoundEvents.RAVAGER_ROAR),
                    SoundSource.NEUTRAL, 10.0F, 1.0F);
        } else if (this.getWindcallerUseTick() == 6) {
            castWindcaller(target);
        }

        if (this.getSnowTick() >= snowCap && this.spellNeed(0.0D, 12.0D, 4)) {
            this.setSpellTick(25);
            this.setSnowUseTick(20);
        }
        if (this.getSnowUseTick() == 20) {
            this.setSnowTick(0);
            this.setAnimTick(20);
            this.playAnimation("snow", 20);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    jerotesSound("corrosiver_snow", SoundEvents.RAVAGER_ROAR),
                    SoundSource.NEUTRAL, 10.0F, 1.0F);
        } else if (this.getSnowUseTick() == 10 && target != null && this.distanceTo(target) <= 32.0D) {
            castSnowCloud(target);
        }

        if (this.getTeleportTick() >= teleportCap && this.spellNeed(12.0D, 64.0D, 2)) {
            this.setSpellTick(85);
            this.setTeleportUseTick(80);
        }
        if (this.getTeleportUseTick() == 70) {
            this.setTeleportTick(0);
            this.setAnimTick(20);
            this.playAnimation("teleport", 20);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    jerotesSound("corrosiver_teleport", SoundEvents.ENDERMAN_TELEPORT),
                    SoundSource.NEUTRAL, 10.0F, 1.0F);
        } else if (this.getTeleportUseTick() == 60 && target != null && this.distanceTo(target) <= 128.0D) {
            whirlwindTeleport(target);
        }
    }

    private boolean specialAction() {
        return this.getWindcallerUseTick() > 0 || this.getIceUseTick() > 3
                || this.getSnowUseTick() > 0 || this.getTeleportUseTick() > 0
                || this.getStartTick() > 0 || this.getBreathTick() < 20;
    }

    private boolean spellNeed(double min, double max, int time) {
        LivingEntity target = this.getTarget();
        return this.getSpellTick() <= 0 && !this.specialAction() && this.isAlive()
                && !this.isInWater() && this.getAttackTick() <= ATTACK_READY_TICK
                && target != null && target.isAlive()
                && this.distanceTo(target) > min && this.distanceTo(target) < max
                && this.random.nextInt(time * 20) == 1;
    }

    private boolean isBitterColdBiome() {
        return this.level().getBiome(this.blockPosition()).unwrapKey()
                .map(key -> "jerotesvillage".equals(key.location().getNamespace())
                        && "bitter_cold".equals(key.location().getPath()))
                .orElse(false);
    }

    private void tickWind() {
        boolean bitterCold = isBitterColdBiome();
        if (this.getHealth() > this.getMaxHealth() / 3.0F && bitterCold) {
            if (this.isWind()) {
                this.setWind(false);
            }
        } else if (!this.isWind() && this.isAlive()) {
            this.setWind(true);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        jerotesSound("corrosiver_wind", SoundEvents.RAVAGER_ROAR),
                        SoundSource.NEUTRAL, 10.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }
            if (this.level() instanceof ServerLevel serverLevel && !this.isInvisible()) {
                serverLevel.sendParticles(
                        jerotesParticle("bitter_cold_cyclone_wind_display", ParticleTypes.SNOWFLAKE),
                        this.getX(), this.getBoundingBox().maxY + 0.5D, this.getZ(),
                        0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        boolean windUp = this.isWind() || this.getTeleportUseTick() > 0 || this.getStartTick() > 0;
        if (windUp) {
            this.entityData.set(DATA_WIND_ANIM, Math.min(10, this.getWindAnim() + 1));
        } else {
            this.entityData.set(DATA_WIND_ANIM, Math.max(0, this.getWindAnim() - 1));
        }
        if (this.isAggressive()) {
            this.entityData.set(DATA_ANGRY_ANIM, Math.min(10, this.getAngryAnim() + 1));
        } else {
            this.entityData.set(DATA_ANGRY_ANIM, Math.max(0, this.getAngryAnim() - 1));
        }
        if (!this.isWind()) {
            return;
        }
        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
        if (--this.healSelf <= 0) {
            this.healSelf = 30;
            this.heal(this.getMaxHealth() / (225.0F * 2.0F));
        }
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false));
        LivingEntity target = this.getTarget();
        if (target != null && this.tickCount % 5 == 0 && this.getAttackTick() <= 0) {
            AABB aura = this.getAttackBoundingBox().deflate(0.5D);
            for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, aura)) {
                if (victim == this || this.isAlliedTo(victim) || this.distanceToSqr(victim) > 128.0D) {
                    continue;
                }
                boolean enemy = this.isAggressive() || (victim instanceof Mob mob && mob.getTarget() == this)
                        || victim.getLastHurtMob() == this || victim.getLastHurtByMob() == this;
                if (enemy) {
                    applyCorrosive(victim, 160, 1);
                }
                if (!isAuraKnockbackTarget(victim)) {
                    continue;
                }
                double resist = victim.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null
                        ? victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0.0D;
                double strength = Math.max(0.3D, 1.3D - resist);
                Vec3 motion = victim.getDeltaMovement();
                double cap = JerotesVillageServantConfig.CORROSIVER_SPECIAL_KNOCKBACK_CAP.get();
                double offsetX = Mth.clamp(this.getX() - victim.getX(), -cap, cap);
                double offsetY = Mth.clamp(this.getY() - victim.getY(), -cap, cap);
                double offsetZ = Mth.clamp(this.getZ() - victim.getZ(), -cap, cap);
                victim.setOnGround(false);
                victim.setDeltaMovement(
                        motion.x - offsetX * strength / 3.0D,
                        motion.y - offsetY * 1.5D * strength,
                        motion.z - offsetZ * strength / 3.0D);
            }
        }
    }

    @Override
    public void aiStep() {
        Entity rider = this.getControllingPassenger();
        if (rider instanceof Player pilot) {
            if (!this.level().isClientSide()) {
                if (!JerotesVillageServantConfig.CORROSIVER_RIDING_ENABLED.get()) {
                    pilot.stopRiding();
                } else {
                    this.setTarget(null);
                    this.getNavigation().stop();
                }
            }
            pilot.fallDistance = 0.0F;
        }
        super.aiStep();
        if (this.level().isClientSide) {
            return;
        }
        if (this.isBeastTough() && this.getRemainingFireTicks() > 0) {
            this.clearFire();
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.getAttackTick() > ATTACK_READY_TICK) {
            return false;
        }
        int roll = this.random.nextInt(35);
        this.setAttackTick(15);
        this.setAnimTick(20);
        if (roll > 30) {
            this.setAttackUse(1);
            this.playAnimation("attack1", 20);
        } else if (roll > 25) {
            this.setAttackUse(2);
            this.playAnimation("attack2", 20);
        } else if (roll > 20) {
            this.setAttackUse(3);
            this.playAnimation("attack3", 20);
        } else if (roll > 15) {
            this.setAttackUse(3);
            this.playAnimation("attack4", 20);
        } else if (roll > 10) {
            this.setAttackUse(4);
            this.playAnimation("attack5", 20);
        } else if (roll > 5) {
            this.setAttackUse(4);
            this.playAnimation("attack6", 20);
        } else {
            this.setAttackTick(13);
            this.setAttackUse(5);
            this.playAnimation("attack7", 20);
        }
        return true;
    }

    private void trueHurt() {
        if (!this.isSilent()) {
            this.playSound(jerotesSound("corrosiver_attack", SoundEvents.RAVAGER_ATTACK), 10.0F,
                    0.8F + this.random.nextFloat() * 0.4F);
        }
        int use = Math.max(1, this.getAttackUse());
        float damageMulti = switch (use) {
            case 1 -> 1.5F;
            case 3 -> 1.15F;
            case 4 -> 1.25F;
            case 5 -> 1.0F;
            default -> 1.0F;
        };
        float knockbackMulti = switch (use) {
            case 1 -> 0.75F;
            case 3 -> 1.15F;
            case 4 -> 1.05F;
            case 5 -> 2.0F;
            default -> 1.25F;
        };
        float attackDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (attackDamage <= 0.0F) {
            attackDamage = 1.0F;
        }
        damageMulti += Math.max(0.0F, Math.min(3.0F,
                (Math.min(6.0F, 2.0F * this.fallDistance) + Math.min(8.0F, this.fallDistance)
                        + this.fallDistance) / attackDamage));

        AABB hitBox = this.getAttackBoundingBox().inflate(0.75D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, hitBox)) {
            if (victim == this || this.isAlliedTo(victim)) {
                continue;
            }
            if (victim instanceof CorrosiverServant) {
                continue;
            }
            if (use != 4 && !this.hasLineOfSight(victim)) {
                continue;
            }
            float hitDamage = attackDamage * damageMulti * this.skillDamageScale();
            if (!victim.hurt(this.damageSources().mobAttack(this), hitDamage)) {
                continue;
            }
            this.setLastHurtMob(victim);
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));

            if (this.level() instanceof ServerLevel server) {
                Vec3 delta = victim.position().subtract(this.position());
                double count = 100.0D * Math.max(1.0D,
                        victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,
                                server.getBlockState(victim.blockPosition())),
                        victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                        (int) count, 0.3D, 0.3D, 0.3D, 0.15D);
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, iceRockState()),
                        delta.x, delta.y, delta.z, (int) count, 0.3D, 0.3D, 0.3D, 0.15D);
                server.sendParticles(ParticleTypes.SNOWFLAKE, victim.getX(), this.getY(), victim.getZ(),
                        16, 0.3D, 0.3D, 0.3D, 0.15D);
            }

            double resist = victim.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null
                    ? Math.max(victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0D) : 0.0D;
            if (isSpecialKnockbackTarget(victim)) {
                double factor = (Math.max(0.0D, 1.0D - resist) + knockbackMulti / 2.0D)
                        * (0.7D + knockbackMulti / 3.0D);
                double xzFactor = factor + (this.isWind() ? 0.3D : 0.0D);
                double yFactor = factor + (this.isWind() ? 0.35D : 0.0D);
                Vec3 motion = victim.getDeltaMovement();
                double addY = use == 5 ? 2.0D : 0.0D;
                double cap = JerotesVillageServantConfig.CORROSIVER_SPECIAL_KNOCKBACK_CAP.get();
                double offsetX = Mth.clamp(this.getX() - victim.getX(), -cap, cap);
                double offsetY = Mth.clamp(this.getY() - victim.getY(), -cap, cap);
                double offsetZ = Mth.clamp(this.getZ() - victim.getZ(), -cap, cap);
                victim.setOnGround(false);
                victim.setDeltaMovement(
                        motion.x - offsetX * 1.4D * xzFactor,
                        motion.y + addY - offsetY * 2.0D * yFactor,
                        motion.z - offsetZ * 1.4D * xzFactor);
            }
            int freeze = this.isWind() ? 420 : 280;
            if (victim.getTicksFrozen() < freeze) {
                victim.setTicksFrozen(freeze);
            }
            applyCorrosive(victim, this.isWind() ? 400 : 200, difficultyLevel());
            if (this.isWind()) {
                applyEffect(victim, "jerotes", "weakness",
                        MobEffects.WEAKNESS, 200, 0);
                applyEffect(victim, "jerotes", "slowness", MobEffects.MOVEMENT_SLOWDOWN, 200, 0);
            }
        }
        if (use == 1 || use == 3 || use == 4) {
            applyQuake(this.getBoundingBox().inflate(16.0D), 20, 0);
        }
        if (use == 1 || use == 4) {
            float volume = 10.0F;
            this.playSound(use == 1 ? SoundEvents.ANVIL_LAND : SoundEvents.GENERIC_EXPLODE,
                    volume, 1.0F);
        }
        sweepAttackParticles();
    }

    private void sweepAttackParticles() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double dx = -Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F));
        double dz = Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F));
        server.sendParticles(ParticleTypes.SWEEP_ATTACK,
                this.getX() + dx, this.getY(0.5D), this.getZ() + dz, 0, dx, 0.0D, dz, 0.0D);
    }

    private static net.minecraft.world.level.block.state.BlockState iceRockState() {
        net.minecraft.world.level.block.Block rock = ForgeRegistries.BLOCKS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", "ice_rock"));
        return (rock == null ? Blocks.PACKED_ICE : rock).defaultBlockState();
    }

    private int difficultyLevel() {
        return switch (this.level().getDifficulty()) {
            case PEACEFUL, EASY -> 0;
            case HARD -> 2;
            default -> 1;
        };
    }

    private static boolean isSpecialKnockbackTarget(LivingEntity entity) {
        if (entity.getType().is(NO_SPECIAL_KNOCKBACK)) {
            return false;
        }
        float width = entity.getBbWidth();
        float height = entity.getBbHeight();
        boolean small = mobSizeFind(width, height, 0.75F, 1.75F);
        boolean medium = mobSizeFind(width, height, 1.75F, 3.5F) && !small;
        boolean large = mobSizeFind(width, height, 3.5F, 6.0F) && !small && !medium;
        return small || medium || large;
    }

    private static boolean isAuraKnockbackTarget(LivingEntity entity) {
        if (entity.getType().is(NO_SPECIAL_KNOCKBACK)) {
            return false;
        }
        float width = entity.getBbWidth();
        float height = entity.getBbHeight();
        boolean small = mobSizeFind(width, height, 0.75F, 1.75F);
        boolean medium = mobSizeFind(width, height, 1.75F, 3.5F) && !small;
        return small || medium;
    }

    private static boolean mobSizeFind(float width, float height, float average, float max) {
        return !(width >= max || height >= max || (width >= average && height >= average));
    }

    public AABB getAttackBoundingBox() {
        AABB box = this.getBoundingBox();
        box = box.inflate(Math.sqrt(2.04F) - 0.6D, 0.0D, Math.sqrt(2.04F) - 0.6D);
        double add = this.isWind() ? 1.5D : 1.0D;
        return box.inflate(add, add, add);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return this.getAttackBoundingBox().intersects(entity.getBoundingBox());
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.start > -100) {
            return true;
        }
        if (this.isWind()) {
            if (source.is(DamageTypeTags.IS_FALL)
                    || source.is(DamageTypeTags.IS_FIRE)) {
                return true;
            }
        }
        if (source.is(DamageTypes.THORNS) || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(DamageTypes.CACTUS) || source.is(DamageTypeTags.IS_FREEZING)) {
            return true;
        }
        String id = source.getMsgId();
        if (id.contains("corrosion") || id.contains("cold") || id.contains("freeze")) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    private static boolean isMagicDamage(DamageSource source) {
        return source.is(DamageTypeTags.WITCH_RESISTANT_TO)
                || source.getDirectEntity() instanceof ThrownPotion
                || source.getDirectEntity() instanceof AreaEffectCloud;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (ExecutionDamage.isExecution(source, amount)) {
            this.setBeastToughTick(0);
            this.setBeastTough(false);
            return super.hurt(source, amount);
        }
        if (this.getBeastToughTick() > 0
                && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + 2.0D, this.getZ(),
                        12, 1.0D, 1.0D, 1.0D, 0.05D);
                if (!this.isInvisible()) {
                    server.sendParticles(jerotesCoreParticle("beast_tough_display", ParticleTypes.ENCHANT),
                            this.getX(), this.getBoundingBox().maxY + 0.5D, this.getZ(),
                            0, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            return false;
        }
        if (isMagicDamage(source)) {
            amount *= this.isWind() ? 0.7F : 0.8F;
        }
        float damageMulti = DAMAGE_MULTIPLIER;
        if (source.is(DamageTypeTags.IS_DROWNING)) {
            damageMulti *= 0.25F;
        }
        if (source.is(DamageTypeTags.IS_FIRE)) {
            damageMulti *= 0.25F;
        }
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            damageMulti *= 0.25F;
        }
        if (!super.hurt(source, amount * damageMulti)) {
            return false;
        }
        int invuln = this.toughInvulnTicks();
        if (invuln > 0) {
            this.setBeastToughTick(invuln);
            this.setBeastTough(true);
        }
        return true;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        if (isJerotesEffect(instance, "corrosive")) {
            return false;
        }
        if (isJerotesEffect(instance, "aback")) {
            return false;
        }
        if (instance.getEffect() == MobEffects.WEAKNESS) {
            return false;
        }
        return super.canBeAffected(instance);
    }

    private void castBreath(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        castFeedback("magic_corrosiver_breath", SoundEvents.RAVAGER_ROAR,
                "corrosiver_breath_display", ParticleTypes.SNOWFLAKE);
        boolean playerTarget = target instanceof Player;
        int count = Math.max(1, this.spellLevel);
        if (!playerTarget && this.random.nextBoolean()) {
            Vec3 look = this.getLookAngle();
            CorrosiverBreathAreaServantEntity area = new CorrosiverBreathAreaServantEntity(server, this);
            area.setPos(this.getX() + look.x * 2.25D,
                    this.getY() + this.getBbHeight() * 0.25D + 0.125D,
                    this.getZ() + look.z * 2.25D);
            area.setSpellLevel(this.spellLevel);
            area.setYRot(this.getYRot());
            server.addFreshEntity(area);
            return;
        }
        for (int i = 0; i < count; ++i) {
            double spread = -(count - 1) * 5.0D / 2.0D + i * 5.0D;
            Vec3 dir = rotateY(this.getLookAngle(), spread);
            CorrosiverBreathServantEntity breath = new CorrosiverBreathServantEntity(server, this);
            breath.setPos(this.getX() + dir.x, this.getEyeY() - 0.2D, this.getZ() + dir.z);
            breath.setSpellLevel(this.spellLevel);
            breath.shoot(dir.x, dir.y, dir.z, 1.0F, 0.0F);
            server.addFreshEntity(breath);
        }
    }

    private void castIceSpike(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        castFeedback("magic_corrosive_ice_spike", SoundEvents.RAVAGER_ROAR,
                "corrosive_ice_spike_display", ParticleTypes.SNOWFLAKE);
        int count = Math.max(1, this.spellLevel);
        for (int i = 0; i < count; ++i) {
            double spread = -(count - 1) * 5.0D / 2.0D + i * 5.0D;
            Vec3 toTarget = target == this ? this.getLookAngle()
                    : target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(this.getEyePosition());
            Vec3 dir = rotateY(toTarget.normalize(), spread);
            CorrosiveIceSpikeServantEntity spike = new CorrosiveIceSpikeServantEntity(server, this);
            spike.setPos(this.getX() + dir.x, this.getEyeY() - 0.2D, this.getZ() + dir.z);
            spike.setSpellLevel(this.spellLevel);
            spike.shoot(dir.x, dir.y, dir.z, 1.5F, 0.0F);
            server.addFreshEntity(spike);
        }
    }

    private void castSnowCloud(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        castFeedback("magic_corrosive_snow_cloud", SoundEvents.RAVAGER_ROAR,
                "corrosive_snow_cloud_display", ParticleTypes.SNOWFLAKE);
        int level = BASE_SPELL_LEVEL;
        int duration = 6 + (level - 1) * 3;
        CorrosiveSnowCloudServantEntity cloud = new CorrosiveSnowCloudServantEntity(server,
                target.getX(), target.getY() + target.getBbHeight() + 2.0D, target.getZ());
        cloud.setOwner(this);
        cloud.setSpellLevel(level);
        cloud.setDuration(20 * duration);
        server.addFreshEntity(cloud);
    }

    private void castWindcaller(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        castFeedback("magic_bitter_cold_windcaller", SoundEvents.RAVAGER_ROAR,
                "bitter_cold_windcaller_display", ParticleTypes.SNOWFLAKE);
        List<CorrosiverServant> corrosivers = server.getEntitiesOfClass(CorrosiverServant.class,
                this.getBoundingBox().inflate(64.0D));
        List<BitterColdWindServant> existing = server.getEntitiesOfClass(BitterColdWindServant.class,
                this.getBoundingBox().inflate(64.0D));
        if (existing.size() < corrosivers.size() * 16) {
            int min = Math.max(1, this.spellLevel);
            int count = min + this.random.nextInt(3);
            for (int i = 0; i < count; ++i) {
                BlockPos pos = findWindSummonPos(server, 8);
                if (pos == null) {
                    continue;
                }
                BitterColdWindServant wind = ModEntityTypes.BITTER_COLD_WIND_SERVANT.get()
                        .create(server);
                if (wind == null) {
                    continue;
                }
                wind.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                        this.random.nextFloat() * 360.0F, 0.0F);
                wind.setTrueOwner(this);
                wind.finalizeSpawn(server, server.getCurrentDifficultyAt(pos),
                        net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED, null, null);
                wind.setLimitedLife(summonLifespanTicks());
                wind.setPersistenceRequired();
                if (target != null && target != this) {
                    wind.setTarget(target);
                }
                wind.playSummonParticles();
                server.addFreshEntity(wind);
            }
            if (this.hasFrostbornFocus()) {
                this.trySummonFrostbornGolem(server, target);
            }
        }
        if (target != null && target != this) {
            for (BitterColdWindServant wind : existing) {
                if (wind.distanceTo(this) > 64.0F || wind == target || this.isAlliedTo(wind)) {
                    continue;
                }
                wind.setTarget(target);
            }
            server.sendParticles(jerotesParticle("target", ParticleTypes.SNOWFLAKE),
                    target.getX(), target.getY() + 0.1D, target.getZ(),
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void trySummonFrostbornGolem(ServerLevel server, LivingEntity target) {
        double chance = JerotesVillageServantConfig.CORROSIVER_FROSTBORN_GOLEM_CHANCE.get() == null
                ? 0.5D : JerotesVillageServantConfig.CORROSIVER_FROSTBORN_GOLEM_CHANCE.get();
        if (this.random.nextDouble() >= Math.min(1.0D, Math.max(0.0D, chance))) {
            return;
        }
        int cap = JerotesVillageServantConfig.CORROSIVER_FROSTBORN_GOLEM_CAP.get() == null
                ? 2 : Math.max(0, JerotesVillageServantConfig.CORROSIVER_FROSTBORN_GOLEM_CAP.get());
        List<IceGolemServant> held = server.getEntitiesOfClass(IceGolemServant.class,
                this.getBoundingBox().inflate(64.0D), golem -> golem.isAlive() && golem.getTrueOwner() == this);
        if (held.size() >= cap) {
            return;
        }
        BlockPos pos = findWindSummonPos(server, 8);
        if (pos == null) {
            return;
        }
        IceGolemServant golem = ModEntityTypes.ICE_GOLEM_SERVANT.get().create(server);
        if (golem == null) {
            return;
        }
        golem.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, this.random.nextFloat() * 360.0F, 0.0F);
        golem.finalizeSpawn(server, server.getCurrentDifficultyAt(pos),
                MobSpawnType.MOB_SUMMONED, null, null);
        golem.setTrueOwner(this);
        golem.setLimitedLife(summonLifespanTicks());
        golem.setPersistenceRequired();
        if (target != null && target != this && !golem.isAlliedTo(target)) {
            golem.setTarget(target);
        }
        server.addFreshEntity(golem);
        GoetySummonFx.play(server, golem);
        server.sendParticles(ParticleTypes.SNOWFLAKE, golem.getX(), golem.getY(1.0D),
                golem.getZ(), 12, 0.3D, 0.5D, 0.3D, 0.01D);
        server.playSound(null, golem.getX(), golem.getY(0.7D), golem.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.6F,
                1.2F + golem.getRandom().nextFloat() * 0.3F);
    }

    private BlockPos findWindSummonPos(ServerLevel server, int radius) {
        BlockPos origin = this.blockPosition();
        for (int attempt = 0; attempt < 12; ++attempt) {
            int dx = this.random.nextInt(radius * 2 + 1) - radius;
            int dz = this.random.nextInt(radius * 2 + 1) - radius;
            int dy = this.random.nextInt(5) - 2;
            BlockPos candidate = origin.offset(dx, dy, dz);
            if (this.level().isEmptyBlock(candidate)
                    && this.level().isEmptyBlock(candidate.above())
                    && !this.level().isEmptyBlock(candidate.below())) {
                return candidate;
            }
        }
        return null;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide() && hand == InteractionHand.MAIN_HAND
                && JerotesVillageServantConfig.CORROSIVER_RIDING_ENABLED.get()
                && player == this.getTrueOwner()
                && CuriosFinder.hasFrostRobes(player)
                && player.getItemInHand(hand).isEmpty()
                && !player.isSecondaryUseActive()
                && this.getFirstPassenger() == null) {
            if (player.startRiding(this)) {
                return InteractionResult.CONSUME;
            }
        }
        if (!this.level().isClientSide() && player == this.getTrueOwner()
                && applyGoetyUpgrade(player, hand, player.getItemInHand(hand))) {
            return InteractionResult.SUCCESS;
        }
        if (player == this.getTrueOwner() && this.isAlive()
                && player.getItemInHand(hand).is(MEAT)) {
            ItemStack meat = player.getItemInHand(hand);
            FoodProperties food = meat.getFoodProperties(this);
            float heal = food == null ? 0.0F : (float) food.getNutrition();
            if (heal > 0.0F && ServantFeedHeal.feed(this, player, meat, heal)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void travel(Vec3 vanillaInput) {
        Entity controlled = this.getControllingPassenger();
        if (controlled instanceof Player pilot && this.isControlledByLocalInstance()
                && JerotesVillageServantConfig.CORROSIVER_RIDING_ENABLED.get() && pilot.isAlive()) {
            this.setYRot(pilot.getYRot());
            this.setXRot(pilot.getXRot() * 0.5F);
            this.setYHeadRot(this.getYRot());

            double speed = JerotesVillageServantConfig.CORROSIVER_RIDING_SPEED.get();
            float yawRad = this.getYRot() * Mth.DEG_TO_RAD;
            double sinYaw = Mth.sin(yawRad);
            double cosYaw = Mth.cos(yawRad);
            Vec3 wanted = new Vec3(-sinYaw * pilot.zza + cosYaw * pilot.xxa, 0.0D,
                    cosYaw * pilot.zza + sinYaw * pilot.xxa);
            if (wanted.lengthSqr() > 1.0E-6D) {
                wanted = wanted.normalize().multiply(speed, 0.0D, speed);
            }

            Vec3 dv = this.getDeltaMovement();
            double vy = dv.y;
            if (this.onGround()) {
                vy = 0.0D;
                if (WitherServantClientControls.ascendHeld) {
                    vy = 0.42D * this.getBlockJumpFactor();
                    this.setOnGround(false);
                }
            } else {
                vy -= 0.08D;
                if (vy < -1.0D) {
                    vy = -1.0D;
                }
            }
            this.setDeltaMovement(dv.x * 0.7D + wanted.x * 0.3D, vy, dv.z * 0.7D + wanted.z * 0.3D);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.resetFallDistance();
            this.calculateEntityAnimation(false);
            return;
        }
        super.travel(vanillaInput);
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity first = this.getFirstPassenger();
        return first instanceof Player player ? player : null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.9D;
    }

    private boolean applyGoetyUpgrade(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId == null) {
            return false;
        }
        if (JerotesVillageServantConfig.CORROSIVER_CAIRN_JAR_ENABLED.get()
                && !hasCairnSoulJar()
                && ResourceLocation.fromNamespaceAndPath("goety", "soul_jar").equals(itemId)
                && SoulJar.isCairn(stack)) {
            this.entityData.set(DATA_CAIRN_SOUL_JAR, true);
            float oldHealth = this.getHealth();
            this.refreshUpgradeHealth(true);
            this.setHealth((float) Math.min(this.getMaxHealth(), oldHealth
                    + configOr(JerotesVillageServantConfig.CORROSIVER_CAIRN_JAR_HEALTH, 50.0D)));
            consumeUpgradeItem(player, hand);
            this.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 0.75F);
            spawnUpgradeParticles();
            return true;
        }
        boolean frostbornItem = ResourceLocation.fromNamespaceAndPath("goety", "frostborn_focus").equals(itemId)
                || ResourceLocation.fromNamespaceAndPath("goety_supreme", "frostborn_focus").equals(itemId);
        if (JerotesVillageServantConfig.CORROSIVER_FROSTBORN_ENABLED.get()
                && !hasFrostbornFocus() && frostbornItem) {
            this.entityData.set(DATA_FROSTBORN_FOCUS, true);
            float oldHealth = this.getHealth();
            this.refreshUpgradeHealth(true);
            this.setHealth((float) Math.min(this.getMaxHealth(), oldHealth
                    + configOr(JerotesVillageServantConfig.CORROSIVER_FROSTBORN_HEALTH, 25.0D)));
            consumeUpgradeItem(player, hand);
            this.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.25F);
            spawnUpgradeParticles();
            return true;
        }
        return false;
    }

    private void consumeUpgradeItem(Player player, InteractionHand hand) {
        if (!player.getAbilities().instabuild) {
            player.getItemInHand(hand).shrink(1);
        }
    }

    private void spawnUpgradeParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(),
                    this.getY() + this.getBbHeight() * 0.55D, this.getZ(),
                    18, this.getBbWidth() * 0.55D, this.getBbHeight() * 0.45D,
                    this.getBbWidth() * 0.55D, 0.03D);
        }
    }

    private void refreshUpgradeHealth(boolean raiseCurrent) {
        net.minecraft.world.entity.ai.attributes.AttributeInstance maxHealth =
                this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }
        applyHealthModifier(maxHealth, CAIRN_JAR_HEALTH_UUID, "Cairn soul jar",
                hasCairnSoulJar() ? configOr(JerotesVillageServantConfig.CORROSIVER_CAIRN_JAR_HEALTH, 50.0D) : 0.0D);
        applyHealthModifier(maxHealth, FROSTBORN_HEALTH_UUID, "Frostborn focus",
                hasFrostbornFocus() ? configOr(JerotesVillageServantConfig.CORROSIVER_FROSTBORN_HEALTH, 25.0D) : 0.0D);
        if (raiseCurrent) {
            this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));
        }
    }

    private static double configOr(ForgeConfigSpec.DoubleValue value, double fallback) {
        return value == null || value.get() == null ? fallback : value.get();
    }

    private static void applyHealthModifier(
            net.minecraft.world.entity.ai.attributes.AttributeInstance maxHealth,
            UUID uuid, String name, double bonus) {
        net.minecraft.world.entity.ai.attributes.AttributeModifier modifier = maxHealth.getModifier(uuid);
        if (modifier != null && modifier.getAmount() != bonus) {
            maxHealth.removePermanentModifier(uuid);
            modifier = null;
        }
        if (bonus > 0.0D && modifier == null) {
            maxHealth.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    uuid, name, bonus,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
        }
    }

    private void whirlwindTeleport(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        castFeedback("whirlwind_shroud", SoundEvents.ENDERMAN_TELEPORT,
                "whirlwind_shroud_display", ParticleTypes.SNOWFLAKE);
        BlockPos pos = null;
        BlockPos targetPos = target.blockPosition();
        for (int i = 0; i < 16; ++i) {
            int dx = this.random.nextInt(9) - 4;
            int dz = this.random.nextInt(9) - 4;
            int dy = this.random.nextInt(5) - 2;
            BlockPos candidate = targetPos.offset(dx, dy, dz);
            if (this.level().isEmptyBlock(candidate) && this.level().isEmptyBlock(candidate.above())
                    && !this.level().isEmptyBlock(candidate.below())) {
                pos = candidate;
                break;
            }
        }
        if (pos == null) {
            return;
        }
        Vec3 from = this.position();
        Vec3 to = Vec3.atBottomCenterOf(pos);
        double dist = from.distanceTo(to);
        int points = (int) Math.floor(dist) * 6 + 1;
        for (int i = 0; i <= points; ++i) {
            Vec3 p = from.lerp(to, (double) i / points);
            server.sendParticles(ParticleTypes.SNOWFLAKE, p.x, p.y + 1.0D, p.z, 2, 0.1D, 0.1D, 0.1D, 0.01D);
        }
        this.teleportTo(to.x, to.y, to.z);
        this.getNavigation().stop();
    }

    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 1) {
            this.setAnimTick(40);
            this.playAnimation("dead", 40);
        }
        if (this.deathTime >= 40 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.CHANGED_DIMENSION);
        }
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null || !JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        ItemStack horn = new ItemStack(GeneratedMod.DRACOGNATHUS_REVIVAL_HORN.get());
        ReviveServantItem.setOwnerName(getTrueOwner(), horn);
        ReviveServantItem.setSummon(this, horn);
        OwnerBoundLoot.deliver(this, horn);
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
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpellLevel", this.spellLevel);
        tag.putInt("Start", this.start);
        tag.putBoolean("IsWind", this.isWind());
        tag.putInt("BeastToughTick", this.getBeastToughTick());
        tag.putBoolean("CairnSoulJar", hasCairnSoulJar());
        tag.putBoolean("FrostbornFocus", hasFrostbornFocus());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("SpellLevel")) {
            this.spellLevel = tag.getInt("SpellLevel");
        }
        if (tag.contains("Start")) {
            this.start = tag.getInt("Start");
        }
        if (tag.contains("IsWind")) {
            this.setWind(tag.getBoolean("IsWind"));
        }
        this.setBeastToughTick(tag.getInt("BeastToughTick"));
        this.entityData.set(DATA_CAIRN_SOUL_JAR, tag.getBoolean("CairnSoulJar"));
        this.entityData.set(DATA_FROSTBORN_FOCUS, tag.getBoolean("FrostbornFocus"));
        this.refreshUpgradeHealth(false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        this.spellLevel = switch (level.getDifficulty()) {
            case PEACEFUL, EASY -> BASE_SPELL_LEVEL - 1;
            case HARD -> BASE_SPELL_LEVEL + 1;
            default -> BASE_SPELL_LEVEL;
        };
        this.setAnimTick(40);
        this.setStartTick(40);
        this.playAnimation("start", 40);
        return result;
    }

    private static Vec3 rotateY(Vec3 vec, double degrees) {
        double rad = Math.toRadians(degrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new Vec3(vec.x * cos - vec.z * sin, vec.y, vec.x * sin + vec.z * cos);
    }

    private void applyQuake(AABB box, int duration, int amplifier) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jerotes", "quake"));
        if (effect == null) {
            return;
        }
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim != this && !this.isAlliedTo(victim)) {
                victim.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
            }
        }
    }

    private void applyCorrosive(LivingEntity victim, int duration, int amplifier) {
        applyEffect(victim, "jerotes", "corrosive", MobEffects.POISON, duration, amplifier);
    }

    private static void applyEffect(LivingEntity victim, String namespace, String path,
            MobEffect fallback, int duration, int amplifier) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(namespace, path));
        victim.addEffect(new MobEffectInstance(effect == null ? fallback : effect,
                duration, amplifier, false, effect != null));
    }
}
