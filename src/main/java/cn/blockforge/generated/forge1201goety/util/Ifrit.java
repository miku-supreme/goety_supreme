package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.projectiles.Lavaball;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.IfritCombo.Action;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.util.IfritAnimator;
import cn.blockforge.generated.goetysupreme.renderer.IfritBossBarOverlay;
import cn.blockforge.generated.goetysupreme.renderer.IfritClawTrail;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.util.IfritGrandCurtain;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import net.minecraft.server.level.ServerBossEvent;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;

public class Ifrit extends net.minecraft.world.entity.monster.Monster {

    public static final float MAX_HEALTH = 360.0F;

    public static final double ARMOR_VALUE = 8.0D;

    public static final double ARMOR_TOUGHNESS_VALUE = 4.0D;

    public static final float DAMAGE_CAP = 20.0F;

    public static float damageCap() {
        return SupremeMobsConfig.IFRIT_DAMAGE_CAP.get().floatValue();
    }

    public static float applyDamageCap(float amount) {
        float cap = damageCap();
        return cap > 0.0F && amount > cap ? cap : amount;
    }

    public static final int INVULNERABLE_TICKS = 15;

    public static final int DYNAMIC_REDUCTION_TICKS = 4;

    static final float DYNAMIC_REDUCTION_MULTIPLIER = 0.5F;

    static final int REGEN_INTERVAL_TICKS = 20;
    static final float REGEN_AMOUNT = 1.0F;

    static final float DMG_MELEE = 20.0F;

    static final float DMG_WALTZ = 22.0F;

    static final float DMG_COMBO1 = 10.0F;
    static final float DMG_COMBO2_DROP = 13.0F;
    static final float DMG_COMBO2_WING = 18.0F;
    static final float DMG_COMBO3 = 14.0F;
    static final float DMG_COMBO4_TURN = 14.0F;

    static final float DMG_COMBO_START = 14.0F;

    static final int COMBO_START_HIT_1 = 17;
    static final int COMBO_START_HIT_2 = 21;

    static final float DMG_DODGE_CUT = 14.0F;

    static final float HELLFIRE_BASIC = 4.0F;

    static final float HELLFIRE_WALTZ = 6.0F;

    static final float HELLFIRE_COMBO1 = 2.0F;

    static final float HELLFIRE_CHASE = 8.0F;

    static final int WALTZ_LEAP_TICK = 20;
    static final int WALTZ_LEAP_FIRST = 20;
    static final int WALTZ_LEAP_LAST = 33;

    static final int COMBO_START_ADVANCE_FIRST = 3;
    static final int COMBO_START_ADVANCE_LAST = 20;

    static final int COMBO3_ADVANCE_FIRST = 2;
    static final int COMBO3_ADVANCE_LAST = 18;

    static final int COMBO4_ADVANCE_FIRST = 2;
    static final int COMBO4_ADVANCE_LAST = 22;

    static final double COMBO2_DIVE_MIN_SPEED = 0.7D;
    static final double COMBO2_DIVE_MAX_SPEED = 2.1D;

    static final double COMBO2_DIVE_TICKS = 3.2D;

    static final double COMBO2_CHASE_SPEED = 0.62D;

    static final double COMBO2_DIVE_STOP_RANGE = 1.4D;

    static final int COMBO2_DIVE_FIRST = 4;
    static final int COMBO2_DIVE_LAST = 10;

    static final float DMG_CHASE = 15.0F;

    static final float DMG_CHASE_CUT = 20.0F;

    static final int DASH_SWEEP_FIRST = 10;

    static final int DASH_SWEEP_LAST = 25;

    static final int DASH_LAND_TICK = 26;

    static final int RUSH_SWEEP_LAST = 27;

    static final int RUSH_EARLY_END_TICK = 28;

    static final float WALTZ_HEAL_PER_TARGET = 10.0F;

    static final float COMBO3_HEAL_PER_TARGET = 5.0F;

    static final float COMBO_HEAL_PER_TARGET = 5.0F;

    static final float RUSH_CUT_HEAL_PER_TARGET = 5.0F;

    static final int BLOCK_MAX_TICKS = 60;

    static final float BLOCK_DAMAGE_MULTIPLIER = 0.2F;

    public boolean useBossBar() { return false; }
    public boolean allowPhaseTwo() { return false; }
    public boolean usesBossTargeting() { return false; }
    public boolean dropsLoot() { return false; }
    static final EntityDataAccessor<Integer> DATA_ACTION =
            SynchedEntityData.defineId(Ifrit.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_ACTION_TICK =
            SynchedEntityData.defineId(Ifrit.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CLONE =
            SynchedEntityData.defineId(Ifrit.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> DATA_BOSS_BAR_ID =
            SynchedEntityData.defineId(Ifrit.class, EntityDataSerializers.OPTIONAL_UUID);

    private ServerBossEvent bossBar;
    private int comboCooldown;
    private int dashCooldown;
    private int waltzCooldown;
    int cloneCooldown;
    int ballroomCooldown;
    int dodgeCooldown;

    int enderWatcherCooldown;

    private int dynamicReductionTicks;

    int ifritInvulTicks;

    private int regenTicks;

    int dodgeStreak;

    boolean comboDodgeArmed;

    boolean approachDodgeSpent;

    int approachDashCooldown;

    double dodgeTravelled;
    Vec3 dodgeOrigin = Vec3.ZERO;

    Vec3 waltzLeapDir = Vec3.ZERO;

    float hellfireBonus;

    @Nullable
    IfritGrandCurtain grandCurtain;

    boolean grandCurtainUsed;

    int impactSoundTick = Integer.MIN_VALUE;

    final List<Lavaball> activeMeteors = new ArrayList<>();

    int waterScanCooldown;

    boolean waterSink;

    int comboHits;

    boolean lastStageHit;

    boolean shieldBreakStrike;

    @Nullable
    Vec3 comboAdvanceOrigin;

    boolean comboResetPending;

    boolean scriptedCombo;
    boolean scriptedComboStarted;
    boolean comboFinished;

    Action lastSkillAction;

    @Nullable
    LivingEntity chaseTarget;

    final List<LivingEntity> dashHitVictims = new ArrayList<>();

    boolean rushEarlyFinish;

    int quakeTicks;

    int quakeAge;

    Vec3 quakeDir = Vec3.ZERO;
    double quakeOriginX;
    double quakeOriginZ;

    double quakeRefY;

    final java.util.Set<Integer> quakeHits = new java.util.HashSet<>();

    int cloneMode = IfritClones.MODE_NONE;
    int echoDelayTicks;

    boolean echoReleased;

    @Nullable
    Action mirroredAction;

    int cloneFreeTicks;

    boolean openingClonePending;

    boolean openingCloneUsed;

    int danceLockTicks;

    int danceAge;

    boolean danceDashing;
    @Nullable
    Action echoAction;
    @Nullable
    UUID cloneOwnerUuid;

    boolean sharingTarget;

    public Ifrit(EntityType<? extends Ifrit> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 240;
        setMaxUpStep(1.6F);
        if (!level.isClientSide()) {
            this.entityData.set(DATA_BOSS_BAR_ID, java.util.Optional.of(this.bossBar().getId()));
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SupremeMobsConfig.IFRIT_HEALTH.get())
                .add(Attributes.ARMOR, ARMOR_VALUE)
                .add(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_VALUE)
                .add(Attributes.ATTACK_DAMAGE, SupremeMobsConfig.IFRIT_ATTACK.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public boolean fireImmune() {
        return !isClone();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ACTION, Action.NONE.ordinal());
        this.entityData.define(DATA_ACTION_TICK, 0);
        this.entityData.define(DATA_CLONE, false);
        this.entityData.define(DATA_BOSS_BAR_ID, java.util.Optional.empty());
    }

    public java.util.Optional<java.util.UUID> getBossBarId() {
        return this.entityData.get(DATA_BOSS_BAR_ID);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new IfritCombatGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return !Ifrit.this.isClone() && !Ifrit.this.isRootedAction() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !Ifrit.this.isClone() && !Ifrit.this.isRootedAction()
                        && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public boolean isClone() {
        return this.entityData.get(DATA_CLONE);
    }

    public void setClone(boolean clone) {
        this.entityData.set(DATA_CLONE, clone);
    }

    public int getCloneMode() {
        return this.cloneMode;
    }

    public void setCloneMode(int mode) {
        this.cloneMode = mode;
        this.scriptedCombo = mode == IfritClones.MODE_COMBO;
    }

    public void setCloneOwner(Ifrit owner) {
        this.cloneOwnerUuid = owner.getUUID();
    }

    public void setEchoAction(@Nullable Action action) {
        this.echoAction = action == null ? Action.MELEE : action;
    }

    public void startEchoDelay() {
        this.echoDelayTicks = IfritClones.ECHO_DELAY;
    }

    @Nullable
    Ifrit resolveCloneOwner() {
        if (this.cloneOwnerUuid == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        Entity owner = server.getEntity(this.cloneOwnerUuid);
        return owner instanceof Ifrit ifrit ? ifrit : null;
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        return other instanceof Ifrit || super.isAlliedTo(other);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (this.sharingTarget || this.level() == null || this.level().isClientSide) {
            return;
        }
        this.sharingTarget = true;
        try {
            if (isClone()) {
                Ifrit owner = resolveCloneOwner();
                if (owner != null && owner.isAlive()) {
                    LivingEntity current = owner.getTarget();
                    if (target != null && (current == null || !current.isAlive())) {
                        owner.setTarget(target);
                    }
                }
            } else {
                for (Ifrit clone : IfritClones.ownedLightDanceClones(this)) {
                    if (clone.getTarget() != target) {
                        clone.setTarget(target);
                    }
                }
            }
        } finally {
            this.sharingTarget = false;
        }
    }

    public Action getAction() {
        return IfritCombo.ACTIONS[Mth.clamp(this.entityData.get(DATA_ACTION), 0, IfritCombo.ACTIONS.length - 1)];
    }

    public int getActionTick() {
        return this.entityData.get(DATA_ACTION_TICK);
    }

    public boolean isBusy() {
        return getAction() != Action.NONE;
    }

    public boolean isDodging() {
        Action action = getAction();
        return action == Action.DODGE_L || action == Action.DODGE_R;
    }

    public boolean isDisplacementAction() {
        return switch (getAction()) {
            case DASH, RUSH, DASH_REC, COMBO_CHASE -> true;
            default -> false;
        };
    }

    public boolean isSilentStepAction() {
        return switch (getAction()) {
            case DODGE_L, DODGE_R, COMBO_DODGE_START, DASH, RUSH, DASH_REC, COMBO_CHASE -> true;
            default -> false;
        };
    }

    public boolean keepsAuthoredLegs() {
        return switch (getAction()) {
            case MELEE, WALTZ, COMBO_START, COMBO_DODGE_START, COMBO1, COMBO2, COMBO3, COMBO4,
                    COMBO_REST, DODGE_SLASH, DASH, RUSH, COMBO_CHASE -> true;
            default -> false;
        };
    }

    public boolean isBlocking() {
        return getAction() == Action.COMBO_BLOCK;
    }

    public boolean isClawAttackActive() {
        return switch (getAction()) {
            case MELEE, WALTZ, COMBO_START, COMBO_DODGE_START, COMBO1, COMBO2, COMBO3, COMBO4,
                    COMBO_CHASE, DASH, RUSH, DODGE_SLASH -> true;
            default -> false;
        };
    }

    @Nullable
    public String currentClipKey() {
        Action action = getAction();
        return action == Action.NONE ? null : IfritCombo.DEFS[action.ordinal()].clip;
    }

    public boolean isRootedAction() {
        return switch (getAction()) {
            case CLONE, BALLROOM, GRAND_CURTAIN -> true;
            default -> false;
        };
    }

    public void healFromMeteor(int hits) {
        IfritMeteors.heal(this, hits);
    }

    void burst(SimpleParticleType particle, int count, double speed) {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(particle, this.getX(), this.getY() + 0.4D, this.getZ(),
                    count, 1.2D, 0.3D, 1.2D, speed);
        }
    }

    void faceInstantly(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        this.setYRot(yaw);
        this.yRotO = yaw;
        this.setYHeadRot(yaw);
        this.yBodyRot = yaw;
        this.yBodyRotO = yaw;
    }

    public void spawnCurtainMeteor(double x, double y, double z) {
        IfritMeteors.spawn(this, x, y, z);
    }

    public void discardCurtainMeteors() {
        IfritMeteors.discard(this);
    }

    private void tickMeteors() {
        IfritMeteors.tick(this);
    }

    @Override
    public void tick() {
        super.tick();
        IfritClawTrail.tick(this);
    }

    @Override
    public void die(DamageSource cause) {
        if (!this.level().isClientSide && !isClone()) {
            IfritGrandCurtain.endHook(this);
            IfritClones.poofAllClones(this);
            this.spawnAtLocation(new net.minecraft.world.item.ItemStack(GeneratedMod.INFERNAL_MASK.get()));
        }
        super.die(cause);
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!this.level().isClientSide && !isClone()) {
            IfritGrandCurtain.endHook(this);
            IfritClones.poofAllClones(this);
        }
        super.remove(reason);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            return;
        }
        tickCooldowns();
        tickMeteors();
        IfritMovement.tickWipeQuake(this);
        if (this.dynamicReductionTicks > 0) {
            this.dynamicReductionTicks--;
        }
        if (!isClone()) {
            tickRegen();
            IfritWater.tick(this);
            if (this.bossBar != null && this.tickCount % 5 == 0) {
                this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
            }
        } else {
            IfritClones.tick(this);
        }
        if (this.random.nextFloat() < 0.12F && this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 0.2D, this.getZ(),
                    1, 0.3D, 0.05D, 0.3D, 0.01D);
        }
        IfritCombo.tickSignatureParticles(this);
        IfritDodge.tryEnderWatcherDodge(this);
        IfritClones.tickOpeningClone(this);
        if (!isClone()) {
            IfritGrandCurtain.tickHook(this);
        }
        IfritCombo.tickAction(this);
    }

    public boolean isPhaseTwo() {
        if (isClone()) {
            Ifrit owner = resolveCloneOwner();
            return owner != null && owner.isAlive()
                    && owner.getHealth() <= owner.getMaxHealth() * 0.5F;
        }
        return this.getHealth() <= this.getMaxHealth() * 0.5F;
    }

    static final double ASH_BODY_RADIUS = 1.7D;

    static final double ASH_BODY_MIN_Y = 0.5D;
    static final double ASH_BODY_MAX_Y = 5.2D;

    static final double ASH_PHASE_TWO_RADIUS = 2.5D;

    static final double ASH_PHASE_TWO_OUTER_RADIUS = 3.6D;

    private void tickCooldowns() {
        if (this.ifritInvulTicks > 0) {
            this.ifritInvulTicks--;
        }
        if (this.comboCooldown > 0) {
            this.comboCooldown--;
        }
        if (this.dashCooldown > 0) {
            this.dashCooldown--;
        }
        if (this.approachDashCooldown > 0) {
            this.approachDashCooldown--;
        }
        if (this.waltzCooldown > 0) {
            this.waltzCooldown--;
        }
        if (this.cloneCooldown > 0) {
            this.cloneCooldown--;
        }
        if (this.ballroomCooldown > 0) {
            this.ballroomCooldown--;
        }
        if (this.dodgeCooldown > 0) {
            this.dodgeCooldown--;
        }
        if (this.enderWatcherCooldown > 0) {
            this.enderWatcherCooldown--;
        }
    }

    private void tickRegen() {
        if (++this.regenTicks >= REGEN_INTERVAL_TICKS) {
            this.regenTicks = 0;
            if (this.isAlive() && this.getHealth() < this.getMaxHealth()) {
                this.heal(REGEN_AMOUNT);
            }
        }
    }

    @Override
    public void updateSwimming() {
        this.setSwimming(false);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    private ServerBossEvent bossBar() {
        if (this.bossBar == null) {
            this.bossBar = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED,
                    BossEvent.BossBarOverlay.PROGRESS);
            this.bossBar.setVisible(true);
        }
        return this.bossBar;
    }

    @Override
    public void startSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (!isClone()) {
            this.bossBar().addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if (!isClone() && this.bossBar != null) {
            this.bossBar.removePlayer(player);
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (!isClone() && this.bossBar != null) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    private static boolean isIfritImmune(DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.HOT_FLOOR)) {
            return true; // 鐏劙 / 宀╂祮 / 鐏肩儹鍦伴潰
        }
        if (source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.FLY_INTO_WALL)
                || source.is(DamageTypes.FALLING_ANVIL)) {
            return true; // 鎽旇惤 / 鍔ㄨ兘 / 閾佺牕
        }
        if (source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.IN_WALL)
                || source.is(DamageTypes.DROWN) || source.is(DamageTypes.DRY_OUT)) {
            return true; // 娴嗘灉涓?/ 绐掓伅 / 婧烘按 / 鑴辨按
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (!isClone() && isIfritImmune(source)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (getAction() == Action.SPAWN) {
            return false;
        }
        if (ExecutionDamage.isExecution(source, amount)) {
            return super.hurt(source, amount);
        }
        if (this.ifritInvulTicks > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (IfritDodge.tryDodge(this, source, amount)) {
            return false;
        }
        if (isDodging()) {
            return false;
        }
        if (IfritDodge.isDashInvulnerable(this) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (isBlocking()) {
            triggerBlockCounter(source.getEntity());
            amount *= BLOCK_DAMAGE_MULTIPLIER;
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && (source.is(DamageTypeTags.IS_FREEZING) || ModDamageSource.freezeAttacks(source))) {
            amount *= 2.0F;
        }
        boolean hurt = super.hurt(source, amount);
        if (!isClone() && this.invulnerableTime > 0) {
            this.invulnerableTime = 0;
        }
        return hurt;
    }

    @Override
    public void actuallyHurt(DamageSource source, float amount) {
        if (!isClone() && this.ifritInvulTicks > 0
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        float dealt;
        if (isClone()) {
            dealt = amount;
            super.actuallyHurt(source, amount);
        } else {
            float multiplier = 1.0F;
            if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                if (this.dynamicReductionTicks > 0) {
                    multiplier *= DYNAMIC_REDUCTION_MULTIPLIER;
                }
                multiplier *= IfritCombo.skillDamageMultiplier(this);
            }
            dealt = SupremeBossHurt.hurt(this, source, amount, damageCap(), multiplier);
        }
        if (dealt > 0.0F && !this.level().isClientSide) {
            this.dynamicReductionTicks = DYNAMIC_REDUCTION_TICKS;
            if (source.getEntity() != null) {
                this.ifritInvulTicks = INVULNERABLE_TICKS;
            }
        }
    }

    @Override
    public void knockback(double strength, double x, double z) {
        if (getAction() == Action.SPAWN) {
            return;
        }
        super.knockback(strength, x, z);
    }

    private void triggerBlockCounter(@Nullable Entity attacker) {
        if (!(attacker instanceof LivingEntity living) || !living.isAlive()) {
            return;
        }
        this.chaseTarget = living;
        IfritCombo.startAction(this, Action.COMBO_CHASE);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        this.setPersistenceRequired();
        IfritCombo.startAction(this, Action.SPAWN);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IfritClone", isClone());
        tag.putInt("IfritCloneMode", this.cloneMode);
        tag.putInt("IfritEchoDelay", this.echoDelayTicks);
        tag.putBoolean("IfritOpeningCloneUsed", this.openingCloneUsed);
        tag.putBoolean("IfritGrandCurtainUsed", this.grandCurtainUsed);
        tag.putBoolean("IfritComboFinished", this.comboFinished);
        tag.putInt("IfritCloneFreeTicks", this.cloneFreeTicks);
        if (this.echoAction != null) {
            tag.putInt("IfritEchoAction", this.echoAction.ordinal());
        }
        if (this.mirroredAction != null) {
            tag.putInt("IfritMirroredAction", this.mirroredAction.ordinal());
        }
        if (this.cloneOwnerUuid != null) {
            tag.putUUID("IfritCloneOwner", this.cloneOwnerUuid);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.openingCloneUsed = tag.getBoolean("IfritOpeningCloneUsed");
        this.grandCurtainUsed = tag.getBoolean("IfritGrandCurtainUsed");
        if (tag.getBoolean("IfritClone")) {
            setClone(true);
            setCloneMode(tag.getInt("IfritCloneMode"));
            this.echoDelayTicks = tag.getInt("IfritEchoDelay");
            this.comboFinished = tag.getBoolean("IfritComboFinished");
            this.cloneFreeTicks = tag.getInt("IfritCloneFreeTicks");
            if (tag.contains("IfritEchoAction")) {
                this.echoAction = IfritCombo.ACTIONS[Mth.clamp(tag.getInt("IfritEchoAction"), 0, IfritCombo.ACTIONS.length - 1)];
            }
            if (tag.contains("IfritMirroredAction")) {
                this.mirroredAction =
                        IfritCombo.ACTIONS[Mth.clamp(tag.getInt("IfritMirroredAction"), 0, IfritCombo.ACTIONS.length - 1)];
            }
            if (tag.hasUUID("IfritCloneOwner")) {
                this.cloneOwnerUuid = tag.getUUID("IfritCloneOwner");
            }
            if (this.scriptedCombo) {
                this.scriptedComboStarted = true;
            }
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return isClone() ? null : SoundEvents.BLAZE_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 1.4F;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        if (isSilentStepAction()) {
            return;
        }
        playSound(GeneratedMod.IFRIT_STEP.get(), 0.6F, 0.9F + this.random.nextFloat() * 0.15F);
    }

    public static final class IfritCombatGoal extends Goal {
        private final Ifrit ifrit;
        private int repathCooldown;

        private Action lastAction = Action.NONE;

        IfritCombatGoal(Ifrit ifrit) {
            this.ifrit = ifrit;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.ifrit.getTarget();
            return !this.ifrit.isClone() && target != null && target.isAlive()
                    && this.ifrit.getAction() != Action.SPAWN;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.ifrit.getTarget();
            return !this.ifrit.isClone() && target != null && target.isAlive()
                    && this.ifrit.getAction() != Action.SPAWN;
        }

        @Override
        public void tick() {
            Ifrit ifrit = this.ifrit;
            LivingEntity target = ifrit.getTarget();
            if (target == null) {
                return;
            }
            ifrit.getLookControl().setLookAt(target, 30.0F, 30.0F);
            Action action = ifrit.getAction();
            if (action == Action.NONE && this.lastAction != Action.NONE) {
                this.repathCooldown = 0;
            }
            this.lastAction = action;
            if (this.repathCooldown > 0) {
                this.repathCooldown--;
            }
            if (ifrit.isBusy()) {
                ifrit.getNavigation().stop();
                return;
            }
            double distance = ifrit.distanceTo(target);
            if (ifrit.comboDodgeArmed) {
                if (distance <= IfritDodge.APPROACH_RANGE) {
                    ifrit.comboCooldown = Math.max(ifrit.comboCooldown, 150);
                    IfritCombo.startAction(ifrit, Action.COMBO_DODGE_START);
                    return;
                }
                if (ifrit.approachDashCooldown <= 0) {
                    ifrit.approachDashCooldown = IfritDodge.APPROACH_DASH_COOLDOWN;
                    ifrit.comboCooldown = Math.max(ifrit.comboCooldown, 40);
                    IfritCombo.startAction(ifrit, ifrit.random.nextBoolean() ? Action.DASH : Action.RUSH);
                    return;
                }
            }
            if (distance > 7.0D && ifrit.dashCooldown <= 0) {
                ifrit.dashCooldown = 120;
                ifrit.comboCooldown = Math.max(ifrit.comboCooldown, 40);
                IfritCombo.startAction(ifrit, ifrit.random.nextBoolean() ? Action.DASH : Action.RUSH);
                return;
            }
            if (distance <= 5.5D && ifrit.waltzCooldown <= 0) {
                ifrit.waltzCooldown = 60;
                IfritCombo.startAction(ifrit, Action.WALTZ);
                return;
            }
            if (distance >= 3.0D && distance <= 14.0D && ifrit.ballroomCooldown <= 0
                    && ifrit.random.nextFloat() < 0.06F) {
                ifrit.ballroomCooldown = IfritClones.BALLROOM_COOLDOWN_TICKS;
                IfritCombo.startAction(ifrit, Action.BALLROOM);
                return;
            }
            if (!ifrit.isClone() && ifrit.cloneCooldown <= 0 && !IfritClones.hasLivingLightDanceClone(ifrit)
                    && ifrit.random.nextFloat() < 0.03F) {
                IfritCombo.startAction(ifrit, Action.CLONE);
                return;
            }
            if (distance <= 8.0D && ifrit.comboCooldown <= 0
                    && ifrit.random.nextFloat() < 0.35F) {
                ifrit.comboCooldown = 150;
                IfritCombo.startAction(ifrit, ifrit.random.nextBoolean()
                        ? Action.COMBO_DODGE_START : Action.COMBO_START);
                return;
            }
            if (distance <= 4.5D && ifrit.random.nextFloat() < 0.02F) {
                IfritCombo.startAction(ifrit, Action.COMBO_BLOCK);
                return;
            }
            if (this.repathCooldown <= 0) {
                this.repathCooldown = 10;
                if (!ifrit.getNavigation().moveTo(target, 1.15D)
                        && ifrit.hasLineOfSight(target)) {
                    ifrit.getMoveControl().setWantedPosition(target.getX(), target.getY(),
                            target.getZ(), 1.15D);
                    this.repathCooldown = 5;
                }
            }
        }

        @Override
        public void stop() {
            this.ifrit.getNavigation().stop();
        }
    }
}

