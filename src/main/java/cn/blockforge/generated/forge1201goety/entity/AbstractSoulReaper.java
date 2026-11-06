package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.SEHelper;
import java.util.List;
import java.util.Optional;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.client.particles.SphereExplodeParticleOption;
import cn.blockforge.generated.goetysupreme.client.particles.SoulVFXBridge;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.util.FlightKnockbackRecovery;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.util.SoulReaperAmbush;
import cn.blockforge.generated.goetysupreme.model.SoulReaperModel;
import cn.blockforge.generated.goetysupreme.entity.SoulReaperServant;

public abstract class AbstractSoulReaper extends Summoned {

    public static final int CAST_NONE = 0;
    public static final int CAST_ATTACK = 1;
    public static final int CAST_SUMMON_COFFIN = 2;
    public static final int CAST_CONTROL_ATTACK = 3;
    public static final int CAST_CONTROL_REVIVE = 4;
    public static final int CAST_BURST = 5;

    public static final int POST_CAST_BLEND_TICKS = 6;

    private static final int[] CAST_LENGTHS = {0, 20, 36, 30, 36, 45};

    private static final float[] CAST_IMPACT = {0.0F, 0.45F, 0.70F, 0.50F, 0.55F, 0.60F};

    static final int ATTACK_COOLDOWN = 20;
    static final int CONTROL_COOLDOWN = 100;
    static final int BURST_COOLDOWN = 600;

    static final double MELEE_RANGE_SQR = 16.0D;

    static final double MELEE_APPROACH_SQR = 4.0D;

    static final double BURST_TRIGGER_RADIUS = 8.0D;

    static final double CONTROL_RANGE_SQR = 1600.0D;

    private static final double COFFIN_DROP_HEIGHT = 20.0D;

    private static final EntityDataAccessor<Integer> DATA_CAST_ANIMATION =
            SynchedEntityData.defineId(AbstractSoulReaper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_CAST_TICKS =
            SynchedEntityData.defineId(AbstractSoulReaper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_LEVEL =
            SynchedEntityData.defineId(AbstractSoulReaper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> DATA_COFFIN =
            SynchedEntityData.defineId(AbstractSoulReaper.class, EntityDataSerializers.OPTIONAL_UUID);

    public final AnimationState restAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState coffinEnterAnimationState = new AnimationState();
    public final AnimationState controlAttackAnimationState = new AnimationState();
    public final AnimationState controlReviveAnimationState = new AnimationState();
    public final AnimationState burstAnimationState = new AnimationState();

    public final AnimationState deathAnimationState = new AnimationState();

    public final AnimationState takeoffAnimationState = new AnimationState();
    public final AnimationState landingAnimationState = new AnimationState();
    public final AnimationState restInAnimationState = new AnimationState();
    private int soulTransitionTicks;
    private boolean soulTransitionTakeoff;
    private boolean soulLastMoving;
    private int postCastBlendTicks;

    private int lastCastType;

    public boolean isSoulTransitioning() {
        return this.soulTransitionTicks > 0;
    }

    public boolean isSoulTransitionTakeoff() {
        return this.soulTransitionTakeoff;
    }

    public int getPostCastBlendTicks() {
        return this.postCastBlendTicks;
    }

    public int getLastCastAnimation() {
        return this.lastCastType;
    }

    private static final int DEATH_ANIMATION_TICKS = 32;

    int attackCooldown;
    int controlCooldown;
    int burstCooldown;

    private int burstSecondTicks;

    boolean pendingBurstAfterCoffin;

    private int gapCloseCooldown;
    private int hurtPursuitCooldown;

    private int dashSharedCooldown;

    private boolean dashing;
    @Nullable
    private UUID dashTargetId;
    private double dashBaseSpeed;

    boolean halfHealthBurstUsed;
    private int pendingSpell = CAST_NONE;

    private int lastCastAnimation = CAST_NONE;

    private int lastCastTick;
    private int castAnimation;
    private int castElapsed;
    private int castLength;
    private int castImpact;
    private boolean castApplied;

    @Nullable
    private UUID coffinId;

    private int level;

    public AbstractSoulReaper(EntityType<? extends AbstractSoulReaper> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 60;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
    }

    public static final double BASE_MOVEMENT_SPEED = 0.25D;

    public static final double BASE_FLYING_SPEED = 0.6D;

    private static final int GROWTH_REFRESH_INTERVAL = 20;

    private int growthRefreshCooldown;

    public static AttributeSupplier.Builder createAttributes() {
        return Summoned.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SoulReaperConfig.HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SoulReaperConfig.ATTACK.get())
                .add(Attributes.FOLLOW_RANGE, SoulReaperConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, BASE_MOVEMENT_SPEED)
                .add(Attributes.FLYING_SPEED, BASE_FLYING_SPEED)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CAST_ANIMATION, CAST_NONE);
        this.entityData.define(DATA_CAST_TICKS, 0);
        this.entityData.define(DATA_LEVEL, 0);
        this.entityData.define(DATA_COFFIN, Optional.empty());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_LEVEL.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SoulReaperCast", this.castAnimation);
        tag.putInt("SoulReaperAttackCd", this.attackCooldown);
        tag.putInt("SoulReaperControlCd", this.controlCooldown);
        tag.putInt("SoulReaperBurstCd", this.burstCooldown);
        tag.putInt("SoulReaperGapCloseCd", this.gapCloseCooldown);
        tag.putInt("SoulReaperHurtPursuitCd", this.hurtPursuitCooldown);
        tag.putInt("SoulReaperDashSharedCd", this.dashSharedCooldown);
        tag.putBoolean("SoulReaperHalfBurst", this.halfHealthBurstUsed);
        tag.putBoolean("SoulReaperPendingBurst", this.pendingBurstAfterCoffin);
        tag.putInt("SoulReaperLevel", this.level);
        if (this.coffinId != null) {
            tag.putUUID("SoulReaperCoffin", this.coffinId);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackCooldown = tag.getInt("SoulReaperAttackCd");
        this.controlCooldown = tag.getInt("SoulReaperControlCd");
        this.burstCooldown = tag.getInt("SoulReaperBurstCd");
        this.gapCloseCooldown = tag.getInt("SoulReaperGapCloseCd");
        this.hurtPursuitCooldown = tag.getInt("SoulReaperHurtPursuitCd");
        this.dashSharedCooldown = tag.getInt("SoulReaperDashSharedCd");
        this.halfHealthBurstUsed = tag.getBoolean("SoulReaperHalfBurst");
        this.pendingBurstAfterCoffin = tag.getBoolean("SoulReaperPendingBurst");
        this.level = Mth.clamp(tag.getInt("SoulReaperLevel"), 0, MAX_GROWTH_LEVEL);
        if (tag.hasUUID("SoulReaperCoffin")) {
            this.coffinId = tag.getUUID("SoulReaperCoffin");
        }
        this.entityData.set(DATA_LEVEL, this.level);
        this.entityData.set(DATA_COFFIN, Optional.ofNullable(this.coffinId));
    }

    public static final int MAX_GROWTH_LEVEL = 3;

    public static final float GROWTH_SIZE_PER_LEVEL = 0.15F;

    public int getSoulReaperLevel() {
        return this.entityData.get(DATA_LEVEL);
    }

    public int getGrowthLevel() {
        return this.level;
    }

    public void levelUp() {
        if (this.level >= MAX_GROWTH_LEVEL) {
            return;
        }
        this.level = this.getSoulReaperLevel() + 1;
        this.entityData.set(DATA_LEVEL, this.level);
        this.refreshGrowthAttributes();
        this.setHealth(this.getMaxHealth());
        this.refreshDimensions();
        this.playGrowthLaugh();
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(),
                    this.getY() + this.getBbHeight() * 0.6D, this.getZ(), 24, 0.4D, 0.7D, 0.4D, 0.02D);
        }
    }

    public void playGrowthLaugh() {
        SoundEvent laugh = ModSounds.NECROMANCER_LAUGH.get();
        if (laugh != null) {
            this.playSound(laugh, 1.0F, 0.8F);
        } else {
            this.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.2F, 0.8F);
        }
    }

    public float growthSizeScale() {
        return 1.0F + this.getSoulReaperLevel() * GROWTH_SIZE_PER_LEVEL;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions base = super.getDimensions(pose);
        float scale = this.growthSizeScale();
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
        this.goalSelector.addGoal(1, new SummonCoffinGoal(this));
        this.goalSelector.addGoal(2, new BurstGoal(this));
        this.goalSelector.addGoal(3, new ControlGoal(this));
        this.goalSelector.addGoal(4, new CombatGoal(this));
        this.goalSelector.addGoal(6, new FlyingWanderGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 16.0F) {
            @Override
            public boolean canUse() {
                return !AbstractSoulReaper.this.isCombatLocked() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !AbstractSoulReaper.this.isCombatLocked() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return !AbstractSoulReaper.this.isCombatLocked() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !AbstractSoulReaper.this.isCombatLocked() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void aiStep() {
        this.tickDash();
        super.aiStep();
        this.lockCombatLook();
        this.finishDashIfArrived();
        this.tickBurstChargeParticles();
        if (!this.level().isClientSide) {
            this.tickCooldowns();
            this.tickCast();
            this.tickCoffinRecall();
            if (--this.growthRefreshCooldown <= 0) {
                this.growthRefreshCooldown = GROWTH_REFRESH_INTERVAL;
                this.refreshGrowthAttributes();
            }
        } else {
            this.tickClientAnimations();
        }
    }

    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= DEATH_ANIMATION_TICKS
                && !this.level().isClientSide && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    private void tickCoffinRecall() {
        // 只允许一个摄魂棺：名下若有多余的摄魂棺直接移除，只留当前追踪的那一个。
        // 每 5 刻查一次，避免每刻全图扫实体。
        if (this.tickCount % 5 == 0 && this.level() instanceof ServerLevel server) {
            SoulCoffin tracked = this.getCoffin();
            for (SoulCoffin c : server.getEntitiesOfClass(SoulCoffin.class,
                    this.getBoundingBox().inflate(256.0D), c -> c.isOwnedBy(this))) {
                if (c != tracked) {
                    c.discard();
                }
            }
        }
        SoulCoffin coffin = this.getCoffin();
        if (coffin != null) {
            if (coffin.distanceToSqr(this) > 24.0D * 24.0D && !this.isCasting()) {
                coffin.repositionNearMaster();
            }
        } else if (this.getCoffinId() != null) {
            this.setCoffin(null);
        }
    }

    private void tickCooldowns() {
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if (this.controlCooldown > 0) {
            --this.controlCooldown;
        }
        if (this.burstCooldown > 0) {
            --this.burstCooldown;
        }
        if (this.burstSecondTicks > 0) {
            --this.burstSecondTicks;
            if (this.burstSecondTicks == 0 && this.level() instanceof ServerLevel server) {
                this.burstExplosion(server, this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ());
                SoulCoffin coffin = this.getCoffin();
                if (coffin != null) {
                    this.burstExplosion(server, coffin.getX(),
                            coffin.getY() + coffin.getBbHeight() * 0.5D, coffin.getZ());
                }
            }
        }
        if (this.gapCloseCooldown > 0) {
            --this.gapCloseCooldown;
        }
        if (this.hurtPursuitCooldown > 0) {
            --this.hurtPursuitCooldown;
        }
        if (this.dashSharedCooldown > 0) {
            --this.dashSharedCooldown;
        }
    }

    private void tickCast() {
        if (this.castAnimation == CAST_NONE) {
            return;
        }
        ++this.castElapsed;
        if (!this.castApplied && this.castElapsed >= this.castImpact) {
            this.castApplied = true;
            this.applyCast(this.castAnimation);
        }
        if (this.castElapsed >= this.castLength) {
            int finished = this.castAnimation;
            this.setCastAnimation(CAST_NONE, 0);
            this.castElapsed = 0;
            int queued = this.pendingSpell;
            this.pendingSpell = CAST_NONE;
            if (finished == CAST_CONTROL_ATTACK && this.pendingBurstAfterCoffin) {
                this.pendingBurstAfterCoffin = false;
                this.startCast(CAST_BURST);
            } else if (queued != CAST_NONE) {
                // 施法期间排队的技能：本次施法播完再接上，不打断动作
                this.startCast(queued);
            }
        } else {
            this.entityData.set(DATA_CAST_TICKS, Math.max(0, this.castLength - this.castElapsed));
        }
    }

    private void tickBurstChargeParticles() {
        if (!(this.level() instanceof ServerLevel server) || this.getCastAnimation() != CAST_BURST) {
            return;
        }
        int total = CAST_LENGTHS[CAST_BURST];
        int elapsed = total - this.entityData.get(DATA_CAST_TICKS);
        int impact = Math.max(1, Math.round(total * CAST_IMPACT[CAST_BURST]));
        if (elapsed <= 0 || elapsed >= impact) {
            return;
        }
        Vec3 center = this.position().add(0.0D, this.getY(0.5D) - this.getY(), 0.0D);
        double radius = 3.0D;
        int particleCount = 60;
        for (int i = 0; i < particleCount; ++i) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double inclination = this.random.nextDouble() * Math.PI;
            double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
            double offsetY = radius * Math.cos(inclination);
            double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);
            Vec3 particlePos = center.add(offsetX, offsetY, offsetZ);
            Vec3 velocity = center.subtract(particlePos).normalize().scale(0.15D);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    particlePos.x, particlePos.y, particlePos.z, 0,
                    velocity.x, velocity.y, velocity.z, 1.0D);
        }
    }

    private void tickClientAnimations() {
        if (this.deathTime > 0) {
            this.deathAnimationState.startIfStopped(this.tickCount);
            this.restAnimationState.stop();
            this.walkAnimationState.stop();
            this.attackAnimationState.stop();
            this.coffinEnterAnimationState.stop();
            this.controlAttackAnimationState.stop();
            this.controlReviveAnimationState.stop();
            this.burstAnimationState.stop();
            return;
        }
        int cast = this.getCastAnimation();
        int castTick = this.getCastAnimationTick();
        boolean restarted = cast != CAST_NONE
                && (cast != this.lastCastAnimation || castTick > this.lastCastTick);
        if (restarted) {
            // start() 会重置累积时间，重复触发同一动画时从头播
            AnimationState state = this.castAnimationState(cast);
            state.start(this.tickCount);
        }
        int lastCast = this.lastCastAnimation;
        if (cast == CAST_NONE && lastCast != CAST_NONE) {
            this.postCastBlendTicks = POST_CAST_BLEND_TICKS;
            this.soulLastMoving = false;
            this.lastCastType = lastCast;
            // 照凶兆 PoseTransition：施法结束先播「进入休息姿势」的过渡动画，避免闪绑定姿势
            this.restInAnimationState.start(this.tickCount);
            // 施法动画是一次性的：播完就停，避免长期保持 started
            this.castAnimationState(lastCast).stop();
        }
        this.lastCastAnimation = cast;
        this.lastCastTick = castTick;
        if (cast == CAST_NONE) {
            this.restAnimationState.startIfStopped(this.tickCount);
            this.walkAnimationState.startIfStopped(this.tickCount);

            if (this.postCastBlendTicks > 0) {
                --this.postCastBlendTicks;
            } else {
                boolean moving = this.isMovingForAnimation();
                if (moving != this.soulLastMoving) {
                    this.soulLastMoving = moving;
                    this.soulTransitionTakeoff = moving;
                    this.soulTransitionTicks = 10;
                    if (moving) {
                        this.takeoffAnimationState.start(this.tickCount);
                        this.landingAnimationState.stop();
                    } else {
                        this.landingAnimationState.start(this.tickCount);
                        this.takeoffAnimationState.stop();
                    }
                }
                if (this.soulTransitionTicks > 0) {
                    --this.soulTransitionTicks;
                    if (this.soulTransitionTicks <= 0) {
                        this.takeoffAnimationState.stop();
                        this.landingAnimationState.stop();
                    }
                }
            }
        } else {
            // 施法期间不 stop walk/takeoff/landing：castTick==1 那一帧模型会回退播这些动画，
            // 若已 stop 会出现一帧绑定姿势
            this.restAnimationState.startIfStopped(this.tickCount);
            this.walkAnimationState.startIfStopped(this.tickCount);
            this.restInAnimationState.stop();
            this.postCastBlendTicks = 0;
            this.soulTransitionTicks = 0;
        }
    }

    private AnimationState castAnimationState(int cast) {
        return switch (cast) {
            case CAST_ATTACK -> this.attackAnimationState;
            case CAST_SUMMON_COFFIN -> this.coffinEnterAnimationState;
            case CAST_CONTROL_ATTACK -> this.controlAttackAnimationState;
            case CAST_CONTROL_REVIVE -> this.controlReviveAnimationState;
            case CAST_BURST -> this.burstAnimationState;
            default -> this.attackAnimationState;
        };
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

    public boolean isCasting() {
        return this.getCastAnimation() != CAST_NONE;
    }

    boolean isCombatLocked() {
        if (this.isCasting()) {
            return true;
        }
        LivingEntity target = this.getTarget();
        return target != null && target.isAlive() && this.distanceToSqr(target) <= 16.0D * 16.0D;
    }

    private void lockCombatLook() {
        if (!this.isCombatLocked()) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive()) {
            this.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }

    public boolean showsBackCoffin() {
        return !this.hasCoffin();
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

    void startCast(int animation) {
        if (this.isCasting()) {
            return;
        }
        this.setCastAnimation(animation, CAST_LENGTHS[animation]);
    }

    void forceStartCast(int animation) {
        if (this.isCasting()) {
            // 施法中不打断：排队到本次施法播完再接
            this.pendingSpell = animation;
            return;
        }
        this.setCastAnimation(animation, CAST_LENGTHS[animation]);
    }

    private void applyCast(int animation) {
        switch (animation) {
            case CAST_ATTACK -> this.performAttack();
            case CAST_SUMMON_COFFIN -> this.castSummonCoffin();
            case CAST_CONTROL_ATTACK -> this.castControl();
            case CAST_CONTROL_REVIVE -> this.castRevive();
            case CAST_BURST -> this.castBurst();
            default -> {
            }
        }
    }

    @Nullable
    public UUID getCoffinId() {
        return this.entityData.get(DATA_COFFIN).orElse(null);
    }

    @Nullable
    public SoulCoffin getCoffin() {
        UUID id = this.getCoffinId();
        if (id == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        Entity entity = server.getEntity(id);
        return entity instanceof SoulCoffin coffin && coffin.isAlive() ? coffin : null;
    }

    public void dismissCoffin() {
        SoulCoffin coffin = this.getCoffin();
        if (coffin != null) {
            coffin.discard();
        }
        this.setCoffin(null);
        if (!this.level().isClientSide) {
            this.playSound(GeneratedMod.SOUL_COFFIN_TELEPORT.get(), 1.2F, 1.0F);
        }
    }

    private void setCoffin(@Nullable SoulCoffin coffin) {
        this.coffinId = coffin == null ? null : coffin.getUUID();
        this.entityData.set(DATA_COFFIN, Optional.ofNullable(this.coffinId));
    }

    public boolean hasCoffin() {
        return this.getCoffinId() != null;
    }

    private void performAttack() {
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double reach = MELEE_RANGE_SQR + 2.0D;
        if (this.distanceToSqr(target) > reach) {
            if (this.level() instanceof ServerLevel server) {
                this.spawnScytheSweep(server,
                        this.getX() + this.getLookAngle().x, this.getY(0.8D),
                        this.getZ() + this.getLookAngle().z);
            }
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F,
                    0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return;
        }
        float damage = this.baseAttackDamage();
        boolean hurt = target.hurt(this.damageSources().mobAttack(this), damage);
        if (hurt) {
            this.harvestSoul(target);
            this.applySapped(target);
            float lifesteal = this.servantLifestealRatio();
            if (lifesteal > 0.0F) {
                this.heal(damage * lifesteal);
            }
        }
        if (this.level() instanceof ServerLevel server) {
            Vec3 look = this.getLookAngle();
            AABB sweep = this.getBoundingBox().inflate(4.5D, 3.0D, 4.5D);
            for (LivingEntity other : server.getEntitiesOfClass(LivingEntity.class, sweep,
                    e -> e.isAlive() && e != this && e != target && !this.isAlly(e))) {
                Vec3 delta = new Vec3(other.getX() - this.getX(), 0.0D, other.getZ() - this.getZ());
                if (delta.lengthSqr() < 1.0E-4D) {
                    continue;
                }
                if (delta.normalize().dot(look) < 0.0D) {
                    continue;
                }
                if (Math.abs(other.getY() - this.getY()) > 3.0D) {
                    continue;
                }
                if (other.hurt(this.damageSources().mobAttack(this), damage)) {
                    // 每命中一个单位各给一份灵魂
                    this.harvestSoul(other);
                    float sweepLifesteal = this.servantLifestealRatio();
                    if (sweepLifesteal > 0.0F) {
                        this.heal(damage * sweepLifesteal);
                    }
                }
            }
            this.spawnScytheSweep(server,
                    target.getX(), target.getY(target.getBbHeight() * 0.5D), target.getZ());
        }
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F,
                0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    private void spawnScytheSweep(ServerLevel server, double x, double y, double z) {
        RandomSource random = this.getRandom();
        Vec3 forward = this.getLookAngle();
        int points = 14;
        double radius = 2.2D;
        for (int i = 0; i < points; i++) {
            double t = points <= 1 ? 0.5D : i / (double) (points - 1);
            Vec3 dir = forward.yRot((float) (-0.9D + 1.8D * t));
            double px = x + dir.x * radius;
            double pz = z + dir.z * radius;
            double py = y + (t - 0.5D) * 0.8D;
            for (int j = 0; j < 4; j++) {
                double jx = px + (random.nextDouble() - 0.5D);
                double jy = py + (random.nextDouble() - 0.5D);
                double jz = pz + (random.nextDouble() - 0.5D);
                server.sendParticles(ParticleTypes.FLAME, jx, jy, jz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, jx, jy, jz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        server.sendParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        for (int i = 0; i < 4; i++) {
            double angle = (i - 1.5D) * 0.55D;
            double ox = Math.cos(angle) * 0.75D;
            double oz = Math.sin(angle) * 0.75D;
            server.sendParticles(ParticleTypes.SWEEP_ATTACK,
                    x + ox, y + (i % 2 == 0 ? 0.25D : -0.15D), z + oz,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        server.sendParticles(ParticleTypes.ENCHANTED_HIT, x, y, z, 10, 0.5D, 0.4D, 0.5D, 0.15D);
    }

    private float baseAttackDamage() {
        AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        float base = attack == null ? 0.0F : (float) attack.getValue();
        return base * this.soulPowerDamageMultiplier() + this.soulPowerDamageBonus();
    }

    private void applySapped(LivingEntity victim) {
        if (GoetyEffects.SAPPED == null || SoulReaperConfig.SAPPED_TICKS.get() <= 0) {
            return;
        }
        int level = Math.min(this.getSoulReaperLevel(), MAX_GROWTH_LEVEL);
        int ticks = SoulReaperConfig.SAPPED_TICKS.get() + level * 20;
        int amplifier = level >= MAX_GROWTH_LEVEL ? 1 : 0;
        victim.addEffect(new MobEffectInstance(GoetyEffects.SAPPED.get(), ticks, amplifier, false, true));
    }

    public void harvestSoul(LivingEntity victim) {
        if (this.level().isClientSide) {
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL,
                    victim.getX(), victim.getY() + victim.getBbHeight() * 0.5D,
                    victim.getZ(), 6, 0.3D, 0.4D, 0.3D, 0.01D);
        }
        int amount = SoulReaperConfig.SOUL_STEAL.get();
        if (amount <= 0) {
            return;
        }
        if (this.isServantForm()) {
            if (this.getTrueOwner() instanceof Player owner) {
                SEHelper.increaseSouls(owner, amount);
            }
            return;
        }
        if (victim instanceof Player player) {
            SEHelper.decreaseSouls(player, amount);
        }
    }

    private void castSummonCoffin() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.getCoffin() != null) {
            return;
        }
        LivingEntity target = this.getTarget();
        double x = target != null ? target.getX() : this.getX();
        double z = target != null ? target.getZ() : this.getZ();
        double baseY = target != null ? target.getY() : this.getY();
        SoulCoffin coffin = new SoulCoffin(ModEntityTypes.SOUL_COFFIN.get(), server);
        coffin.setMaster(this);
        coffin.setPos(x, baseY + COFFIN_DROP_HEIGHT, z);
        coffin.beginEntrance(baseY + 1.0D);
        if (server.addFreshEntity(coffin)) {
            this.setCoffin(coffin);
        }
        this.playSound(GeneratedMod.REAPER_SNAP.get(), 2.0F, 1.0F);
        com.Polarice3.Goety.utils.ServerParticleUtil.gatheringParticles(ParticleTypes.SOUL, this, server);
    }

    private void castControl() {
        SoulCoffin coffin = this.getCoffin();
        if (coffin == null) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (coffin.isDowned() || target == null) {
            coffin.startRevive();
            return;
        }
        boolean multi = this.countOtherEnemies(target) > 0;
        double distance = coffin.distanceTo(target);
        if (multi || distance > SoulReaperConfig.CONTROL_NEAR_RANGE.get()) {
            coffin.slamAt(target, multi);
        } else {
            coffin.shockwave();
        }
    }

    private void castRevive() {
        SoulCoffin coffin = this.getCoffin();
        if (coffin != null) {
            coffin.startRevive();
        }
    }

    private int countOtherEnemies(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel server)) {
            return 0;
        }
        AABB area = target.getBoundingBox().inflate(12.0D);
        List<LivingEntity> others = server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != target && e.isAlive() && !this.isAlly(e) && isHostileTarget(e));
        return others.size();
    }

    private static boolean isHostileTarget(LivingEntity entity) {
        return entity instanceof Player
                || entity instanceof net.minecraft.world.entity.monster.Enemy
                || isWight(entity)
                || isWarden(entity);
    }

    public boolean canActivelyTarget(LivingEntity candidate) {
        if (candidate == null || !candidate.isAlive() || candidate == this) {
            return false;
        }
        if (this.isServantForm()) {
            LivingEntity owner = this.getTrueOwner();
            if (owner != null && candidate == owner) {
                return false;
            }
            if (candidate instanceof Creeper creeper) {
                boolean grudged = owner instanceof Player player && SEHelper.isGrudged(player, creeper);
                boolean masterTarget = this.getTarget() == creeper;
                boolean griefing = this.level().getGameRules()
                        .getBoolean(net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                boolean configAllows = com.Polarice3.Goety.config.MobsConfig.ServantsAttackCreepers.get();
                if (!grudged && !masterTarget && (griefing || !configAllows)) {
                    return false;
                }
            }
            if (com.Polarice3.Goety.utils.MobUtil.isOwnedTargetable(this, candidate)) {
                return true;
            }
            if (this.getTarget() == candidate) {
                return true;
            }
            return owner != null && candidate instanceof net.minecraft.world.entity.Mob mob
                    && mob.getTarget() == owner;
        }
        return isHostileTarget(candidate);
    }

    private void castBurst() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        com.Polarice3.Goety.utils.ServerParticleUtil.gatheringParticles(ParticleTypes.SOUL, this, server);
        this.burstSecondTicks = 10;
        this.burstExplosion(server, this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ());
        SoulCoffin coffin = this.getCoffin();
        if (coffin != null) {
            this.burstExplosion(server, coffin.getX(),
                    coffin.getY() + coffin.getBbHeight() * 0.5D, coffin.getZ());
            if (this.isServantForm()) {
                coffin.playSoulBurst();
            } else {
                coffin.forceDown();
            }
        }
    }

    private void burstExplosion(ServerLevel server, double x, double y, double z) {
        double radius = SoulReaperConfig.BURST_RADIUS.get();
        float cr = 42.0F / 255.0F;
        float cg = 201.0F / 255.0F;
        float cb = 207.0F / 255.0F;
        server.sendParticles(new CircleExplodeParticleOption(cr, cg, cb, (float) radius, 1),
                x, y, z, 0, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SphereExplodeParticleOption(cr, cg, cb, (float) radius, 1),
                x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        if (GoetyCompat.isGoety60()) {
            SoulVFXBridge.cubeSpawn(server, new Vec3(x, y, z));
        }
        server.playSound(null, x, y, z, ModSounds.SOUL_EXPLODE.get(), this.getSoundSource(), 2.0F, 1.0F);
        this.areaDamage(server, new Vec3(x, y, z), radius,
                (float) (double) SoulReaperConfig.BURST_DAMAGE.get(), true);
    }

    private void areaDamage(ServerLevel server, Vec3 center, double radius, float damage, boolean magic) {
        AABB area = new AABB(center, center).inflate(radius);
        List<LivingEntity> victims = server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != this && !this.isAlly(e));
        for (LivingEntity victim : victims) {
            if (victim.distanceToSqr(center) > radius * radius) {
                continue;
            }
            float amount = (damage * this.soulPowerDamageMultiplier() + this.soulPowerDamageBonus())
                    * this.damageMultiplierAgainst(victim);
            DamageSource source = magic
                    ? this.damageSources().indirectMagic(this, this.getTrueOwner() != null
                            ? this.getTrueOwner() : this)
                    : this.damageSources().mobAttack(this);
            victim.hurt(source, amount);
        }
    }

    private static final ResourceLocation WIGHT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wight");
    private static final ResourceLocation WARDEN_ID =
            ResourceLocation.fromNamespaceAndPath("minecraft", "warden");

    public static boolean isWight(Entity entity) {
        return entity != null && WIGHT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    public static boolean isWarden(Entity entity) {
        return entity != null && WARDEN_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && this.getHealth() < this.getMaxHealth()
                && isEctoplasm(stack)) {
            if (!this.level().isClientSide) {
                ServantFeedHeal.feed(this, player, stack, 4.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    private static boolean isEctoplasm(ItemStack stack) {
        var item = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "ectoplasm"));
        return item != null && stack.is(item);
    }

    public float damageMultiplierAgainst(LivingEntity victim) {
        if (isWarden(victim)) {
            return 1.5F;
        }
        if (isWight(victim)) {
            return 2.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();
        if (isWarden(attacker) || isWight(attacker)) {
            amount *= 0.5F;
        }
        if (this.getCastAnimation() == CAST_SUMMON_COFFIN) {
            amount *= 0.25F;
        }
        boolean result = super.hurt(source, amount);
        if (result) {
            this.tryHurtPursuit(source.getEntity() instanceof LivingEntity living ? living : null);
        }
        return result;
    }

    void tryGapClose(LivingEntity target, double distSqr) {
        if (this.level().isClientSide || this.isCasting() || this.gapCloseCooldown > 0
                || this.dashSharedCooldown > 0) {
            return;
        }
        if (distSqr < 16.0D || distSqr > 144.0D || !this.hasLineOfSight(target)) {
            return;
        }
        this.dashToward(target, 1.8D);
        this.gapCloseCooldown = 200;
        this.dashSharedCooldown = 40;
    }

    private void tryHurtPursuit(LivingEntity attacker) {
        if (this.level().isClientSide || this.hurtPursuitCooldown > 0
                || this.dashSharedCooldown > 0) {
            return;
        }
        LivingEntity toward = attacker != null && attacker.isAlive() ? attacker : this.getTarget();
        if (toward == null || toward == this || toward.level() != this.level()) {
            return;
        }
        this.dashToward(toward, 1.1D);
        this.hurtPursuitCooldown = 200;
        this.dashSharedCooldown = 40;
    }

    private void dashToward(LivingEntity target, double speed) {
        this.dashing = true;
        this.dashTargetId = target.getUUID();
        this.dashBaseSpeed = speed;
        if (!this.level().isClientSide && this.distanceToSqr(target) > 4.0D) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_CRIT,
                    net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        this.tickDash();
        this.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    private void tickDash() {
        if (!this.dashing || this.level().isClientSide) {
            return;
        }
        if (!(this.level() instanceof ServerLevel server) || this.dashTargetId == null) {
            this.dashing = false;
            return;
        }
        Entity entity = server.getEntity(this.dashTargetId);
        if (!(entity instanceof LivingEntity target) || !target.isAlive()
                || target.level() != this.level()) {
            this.dashing = false;
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }
        Vec3 targetPoint = new Vec3(target.getX(),
                target.getY() + target.getBbHeight() * 0.5D, target.getZ());
        Vec3 selfPoint = new Vec3(this.getX(),
                this.getY() + this.getBbHeight() * 0.5D, this.getZ());
        Vec3 delta = targetPoint.subtract(selfPoint);
        double distance = delta.length();
        if (distance <= 2.0D) {
            this.stopDash(targetPoint, delta);
            return;
        }
        Vec3 direction = delta.scale(1.0D / distance);
        double speed = Math.min(this.dashBaseSpeed, distance * 0.4D);
        if (speed >= distance) {
            this.stopDash(targetPoint, delta);
            return;
        }
        this.setDeltaMovement(direction.scale(speed));
        this.hasImpulse = true;
    }

    private void finishDashIfArrived() {
        if (!this.dashing || this.level().isClientSide) {
            return;
        }
        if (!(this.level() instanceof ServerLevel server) || this.dashTargetId == null) {
            this.dashing = false;
            return;
        }
        Entity entity = server.getEntity(this.dashTargetId);
        if (!(entity instanceof LivingEntity target)) {
            this.dashing = false;
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }
        Vec3 targetPoint = new Vec3(target.getX(),
                target.getY() + target.getBbHeight() * 0.5D, target.getZ());
        Vec3 selfPoint = new Vec3(this.getX(),
                this.getY() + this.getBbHeight() * 0.5D, this.getZ());
        Vec3 delta = targetPoint.subtract(selfPoint);
        if (delta.length() <= 2.0D) {
            this.stopDash(targetPoint, delta);
        }
    }

    private void stopDash(Vec3 targetPoint, Vec3 delta) {
        this.dashing = false;
        this.dashTargetId = null;
        this.setDeltaMovement(Vec3.ZERO);
        if (delta.lengthSqr() > 1.0E-6D) {
            Vec3 direction = delta.normalize();
            Vec3 stopCenter = targetPoint.subtract(direction.scale(2.0D));
            this.setPos(stopCenter.x, stopCenter.y - this.getBbHeight() * 0.5D, stopCenter.z);
        }
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity killed) {
        boolean result = super.killedEntity(level, killed);
        if (killed instanceof Player player) {
            this.onPlayerDefeated(player);
        }
        return result;
    }

    public void onPlayerDefeated(Player player) {
        if (this.level().isClientSide) {
            return;
        }
        player.getPersistentData().putBoolean(SoulReaperAmbush.DEFEATED_TAG, true);
        this.discardCoffin();
        this.discard();
    }

    private void discardCoffin() {
        SoulCoffin coffin = this.getCoffin();
        if (coffin != null) {
            coffin.discard();
        }
        this.setCoffin(null);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide) {
            this.discardCoffin();
        }
        super.die(source);
    }

    @Override
    public void remove(net.minecraft.world.entity.Entity.RemovalReason reason) {
        if (!this.level().isClientSide) {
            this.discardCoffin();
        }
        super.remove(reason);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        // 仆从版不落敌对版掉落物：只有敌对版（implements Enemy）才掉这批。
        if (!(this instanceof net.minecraft.world.entity.monster.Enemy)) {
            return;
        }
        dropGoetyItem("forbidden_fragment", 1);
        this.spawnAtLocation(new ItemStack(GeneratedMod.COFFIN_SHARD.get(), 2));
        dropGoetyItem("shadow_essence", 2);
        this.spawnAtLocation(new ItemStack(GeneratedMod.BROKEN_REAPER_SCYTHE.get(), 1));
        int graveDust = 2 + this.random.nextInt(1 + Math.max(0, looting));
        dropGoetyItem("grave_dust", graveDust);
    }

    private void dropGoetyItem(String path, int count) {
        if (count <= 0) {
            return;
        }
        net.minecraft.world.item.Item item =
                ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
        if (item == null) {
            return;
        }
        this.spawnAtLocation(new ItemStack(item, count));
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
    public boolean isSunBurnTick() {
        return false;
    }

    public boolean isAlly(LivingEntity other) {
        if (other == this) {
            return true;
        }
        if (other instanceof SoulCoffin coffin && coffin.isOwnedBy(this)) {
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
            if (other instanceof Summoned summoned && summoned.getTrueOwner() == owner) {
                return true;
            }
        }
        // 主人链上溯：其它仆从的仆从（同顶主人）也算友方，避免友伤
        if (other instanceof Summoned otherSummoned && otherSummoned.getMasterOwner() != null
                && this.getMasterOwner() != null
                && otherSummoned.getMasterOwner() == this.getMasterOwner()) {
            return true;
        }
        return other instanceof AbstractSoulReaper;
    }

    public abstract boolean isServantForm();

    public static int soulPowerTierFor(int souls) {
        int threshold = SoulReaperConfig.SPAWN_SOUL_THRESHOLD.get();
        return souls <= threshold ? 0 : (souls - threshold) / 1000;
    }

    public int soulPowerTier() {
        return 0;
    }

    public float soulPowerDamageBonus() {
        return 0.0F;
    }

    public float soulPowerDamageMultiplier() {
        return 1.0F;
    }

    public void refreshGrowthAttributes() {
    }

    public float servantLifestealRatio() {
        return 0.0F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.REAPER_AMBIENT_DULL.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GeneratedMod.REAPER_HURT_DULL.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.REAPER_DEATH_DULL.get();
    }

    @Override
    public void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    public int getAmbientSoundInterval() {
        return 240;
    }
}
