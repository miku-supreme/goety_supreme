package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.entity.FrostGolemPumpkinLayer;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.util.IframeHit;
import cn.blockforge.generated.goetysupreme.util.OwnerChain;

public abstract class AbstractFrostGolemServant extends AbstractGolemServant
        implements RangedAttackMob, Shearable {

    private static final EntityDataAccessor<Boolean> DATA_PUMPKIN =
            SynchedEntityData.defineId(AbstractFrostGolemServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_WINDMILL =
            SynchedEntityData.defineId(AbstractFrostGolemServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_WINDMILL_SPIN =
            SynchedEntityData.defineId(AbstractFrostGolemServant.class, EntityDataSerializers.BOOLEAN);

    public static final double FROST_MELEE_KNOCKBACK = 1.3D;

    private static final double SNOWBALL_GRAVITY = 0.03D;
    private static final double THROW_DRAG = 0.99D;

    public static final double SNOWBALL_MIN_SPEED = 0.6D;
    public static final double SNOWBALL_MAX_SPEED = 1.6D;

    public static final float SNOWBALL_INACCURACY = 0.35F;

    public AbstractFrostGolemServant(EntityType<? extends AbstractFrostGolemServant> entityType,
            Level level) {
        super(entityType, level);
    }

    @Override
    public abstract GolemServantConfig.FrostSection configSection();

    private boolean allowFrostSelfDamage;

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (!this.allowFrostSelfDamage
                && (source.is(DamageTypeTags.IS_FREEZING) || ModDamageSource.freezeAttacks(source))) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    public boolean hurtSelfFrost(float amount) {
        this.allowFrostSelfDamage = true;
        try {
            return hurt(this.damageSources().freeze(), amount);
        } finally {
            this.allowFrostSelfDamage = false;
        }
    }

    public abstract double launchVertical();

    @Override
    public float attackDamageFloorRatio() {
        return 0.6F;
    }

    public double launchHorizontal() {
        return FROST_MELEE_KNOCKBACK;
    }

    public boolean ownerWearsFrostRobes() {
        net.minecraft.world.entity.player.Player owner = OwnerChain.playerOwner(this);
        return owner != null && CuriosFinder.hasFrostRobes(owner);
    }

    public void applyMeleeChill(Entity target, int ticks, int amplifier) {
        if (level().isClientSide || !(target instanceof LivingEntity living)
                || !living.isAlive() || isAlliedTo(living)) {
            return;
        }
        boolean frost = ownerWearsFrostRobes();
        MobEffect effect = frost ? GoetyEffects.FREEZING.get() : MobEffects.MOVEMENT_SLOWDOWN;
        living.addEffect(new MobEffectInstance(effect, ticks, frost ? 0 : Math.max(0, amplifier)), this);
    }

    @Override
    public int attackSwingAnimation() {
        return ATTACK_SWING;
    }

    @Override
    public void applyMeleeKnockback(Entity target, double strength) {
        applyMeleeLaunch(target, launchHorizontal(), launchVertical() * difficultyScale());
    }

    public double difficultyScale() {
        return switch (level().getDifficulty()) {
            case EASY -> 0.75D;
            case HARD -> 1.5D;
            default -> 1.0D;
        };
    }

    public boolean prefersRanged(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (this.isSnowballVolleyCoolingDown()) {
            return false;
        }
        if (target instanceof FlyingMob) {
            return true;
        }
        if (target.getY() > getY() + 4.0D) {
            return true;
        }
        double melee = meleeRange();
        return meleeGapDistanceSqr(this, target) > gapThresholdSqr(melee);
    }

    public double meleeRange() {
        return configSection().MELEE_RANGE.get();
    }

    @Override
    public int meleeAnticipationTicks() {
        return 16;
    }

    @Override
    public double meleeAnticipationMax() {
        return 2.5D;
    }

    public double rangedRange() {
        return configSection().RANGED_RANGE.get();
    }

    public int rangedIntervalTicks() {
        return configSection().RANGED_INTERVAL_TICKS.get();
    }

    public int rangedActionTicks() {
        return ATTACK_LENGTHS[ATTACK_SWING];
    }

    public int rangedShotPeriodTicks() {
        return Math.max(rangedIntervalTicks(), rangedActionTicks());
    }

    public float rangedDamage() {
        return configSection().RANGED_DAMAGE.get().floatValue();
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.goalSelector.addGoal(1, new SnowballAttackGoal(this));
        this.goalSelector.addGoal(2, new FrostMeleeGoal(this));
        this.goalSelector.addGoal(3, new FrostApproachGoal(this, 1.0D));
        this.targetSelector.removeAllGoals(goal -> goal instanceof SummonTargetGoal);
        this.targetSelector.addGoal(3, new SummonTargetGoal(this, 1, true, false,
                SummonTargetGoal.predicate(this)));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        throwRangedProjectile(target, rangedDamage());
    }

    public boolean throwRangedProjectile(LivingEntity target, float damage) {
        return throwHarmfulSnowball(target, damage);
    }

    public boolean throwHarmfulSnowball(LivingEntity target, float damage) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return false;
        }
        startAttack(ATTACK_SWING, ATTACK_LENGTHS[ATTACK_SWING]);
        int delay = rangedReleaseDelayTicks();
        if (delay > 0) {
            this.pendingRangedReleases.add(
                    new PendingRangedRelease(this.tickCount + delay, target, damage));
            return true;
        }
        spawnHarmfulSnowball(target, damage);
        return true;
    }

    public int rangedReleaseDelayTicks() {
        return 0;
    }

    private void spawnHarmfulSnowball(LivingEntity target, float damage) {
        HarmfulSnowball snowball = new HarmfulSnowball(level(), this, damage);
        snowball.setIgnoresInvulnerability(true);
        aimHarmfulSnowball(snowball, target);
        level().addFreshEntity(snowball);
        playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
    }

    private record PendingRangedRelease(int releaseTick, LivingEntity target, float damage) {
    }

    private final java.util.ArrayDeque<PendingRangedRelease> pendingRangedReleases =
            new java.util.ArrayDeque<>();

    private void tickPendingRangedReleases() {
        if (!isAlive()) {
            this.pendingRangedReleases.clear();
            return;
        }
        while (!this.pendingRangedReleases.isEmpty()
                && this.pendingRangedReleases.peek().releaseTick() <= this.tickCount) {
            PendingRangedRelease pending = this.pendingRangedReleases.poll();
            LivingEntity target = pending.target();
            if (target != null && target.isAlive() && canAttack(target)) {
                spawnHarmfulSnowball(target, pending.damage());
            }
        }
    }

    public void aimHarmfulSnowball(HarmfulSnowball snowball, LivingEntity target) {
        aimSnowball(snowball, target);
    }

    public void aimSnowball(Snowball snowball, LivingEntity target) {
        Vec3 from = snowball.position();
        Vec3 center = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        BallisticAim.Solution aim = BallisticAim.solveFast(from, center,
                SNOWBALL_GRAVITY, THROW_DRAG, SNOWBALL_MIN_SPEED, SNOWBALL_MAX_SPEED);
        Vec3 motion = target.getDeltaMovement();
        Vec3 lead = BallisticAim.leadOffset(motion, aim.ticks());
        aim = BallisticAim.solveFast(from, center.add(lead),
                SNOWBALL_GRAVITY, THROW_DRAG, SNOWBALL_MIN_SPEED, SNOWBALL_MAX_SPEED);
        Vec3 velocity = aim.velocity();
        snowball.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), SNOWBALL_INACCURACY);
    }

    public void spawnStraySnowball(float damage) {
        HarmfulSnowball snowball = new HarmfulSnowball(level(), this, damage);
        snowball.setIgnoresInvulnerability(true);
        double yaw = this.random.nextDouble() * Math.PI * 2.0D;
        double pitch = (this.random.nextDouble() - 0.35D) * 0.6D;
        double speed = SNOWBALL_MIN_SPEED
                + this.random.nextDouble() * (SNOWBALL_MAX_SPEED - SNOWBALL_MIN_SPEED);
        double horizontal = Math.cos(pitch);
        double vx = -Math.sin(yaw) * horizontal;
        double vz = Math.cos(yaw) * horizontal;
        double vy = Math.sin(pitch);
        snowball.shoot(vx, vy, vz, (float) speed, SNOWBALL_INACCURACY * 2.0F);
        level().addFreshEntity(snowball);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PUMPKIN, true);
        this.entityData.define(DATA_WINDMILL, false);
        this.entityData.define(DATA_WINDMILL_SPIN, false);
    }

    public boolean hasPumpkin() {
        return this.entityData.get(DATA_PUMPKIN);
    }

    public void setPumpkin(boolean worn) {
        this.entityData.set(DATA_PUMPKIN, worn);
    }

    @Nullable
    public ResourceLocation pumpkinGlowTexture() {
        return null;
    }

    public enum PumpkinGlowStyle {

        JACK_O_LANTERN,

        SOUL_JAR
    }

    public PumpkinGlowStyle pumpkinGlowStyle() {
        return PumpkinGlowStyle.JACK_O_LANTERN;
    }

    public boolean isWindmillSpinning() {
        return this.entityData.get(DATA_WINDMILL);
    }

    public boolean isWindmillSpinPhase() {
        return this.isWindmillSpinning();
    }

    private void updateWindmillState() {
        boolean spinning = isSnowballVolleyActive();
        this.entityData.set(DATA_WINDMILL, spinning);
        this.entityData.set(DATA_WINDMILL_SPIN, spinning);
    }

    public int snowballVolleyTicks() {
        return 0;
    }

    public int snowballVolleyIntervalTicks() {
        return 4;
    }

    public int snowballVolleyCooldownTicks() {
        return 0;
    }

    private int volleyEndTick = Integer.MIN_VALUE;

    private int volleyCooldownUntil = Integer.MIN_VALUE;

    public boolean isSnowballVolleyActive() {
        return this.tickCount < this.volleyEndTick;
    }

    public boolean isSnowballVolleyCoolingDown() {
        return this.tickCount < this.volleyCooldownUntil;
    }

    public void startSnowballVolley(int ticks) {
        this.volleyEndTick = this.tickCount + Math.max(0, ticks);
    }

    public void startSnowballVolleyCooldown(int ticks) {
        this.volleyEndTick = Integer.MIN_VALUE;
        this.volleyCooldownUntil = this.tickCount + Math.max(0, ticks);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.updateWindmillState();
            this.tickPendingRangedReleases();
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        InteractionResult fed = tryFrostFeed(player, held);
        if (fed != null) {
            return fed;
        }
        if (!level().isClientSide() && canAdjustPumpkin(player)) {
            if (held.is(Items.SHEARS) && readyForShearing()) {
                shear(player.getSoundSource());
                held.hurtAndBreak(1, player, broken -> broken.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
            if (held.is(Blocks.CARVED_PUMPKIN.asItem()) && !hasPumpkin()) {
                setPumpkin(true);
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                spawnHeadPuff();
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    private InteractionResult tryFrostFeed(Player player, ItemStack held) {
        if (!held.is(Items.ICE) && !held.is(Items.SNOW_BLOCK)) {
            return null;
        }
        double percent = configSection().FEED_HEAL_PERCENT.get();
        if (percent <= 0.0D) {
            return InteractionResult.PASS;
        }
        LivingEntity owner = getTrueOwner();
        if (owner != null && owner != player) {
            return InteractionResult.PASS;
        }
        if (getHealth() >= getMaxHealth()) {
            return InteractionResult.PASS;
        }
        heal((float) (getMaxHealth() * percent / 100.0D));
        if (!level().isClientSide()) {
            playSound(SoundEvents.SNOW_PLACE, 1.0F,
                    (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private boolean canAdjustPumpkin(Player player) {
        LivingEntity owner = getTrueOwner();
        return owner == null || owner == player;
    }

    @Override
    public void shear(SoundSource source) {
        level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, source, 1.0F, 1.0F);
        if (!level().isClientSide()) {
            setPumpkin(false);
            spawnAtLocation(new ItemStack(Blocks.CARVED_PUMPKIN), 1.7F);
        }
    }

    @Override
    public boolean readyForShearing() {
        return isAlive() && hasPumpkin();
    }

    private void spawnHeadPuff() {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, getX(),
                    getY() + getBbHeight() * 0.85D, getZ(), 8,
                    getBbWidth() * 0.45D, 0.12D, getBbWidth() * 0.45D, 0.01D);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Pumpkin", hasPumpkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setPumpkin(!tag.contains("Pumpkin") || tag.getBoolean("Pumpkin"));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SNOW_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SNOW_GOLEM_DEATH;
    }

    private static final class FrostMeleeGoal extends MeleeAttackGoal {
        private final AbstractFrostGolemServant golem;

        FrostMeleeGoal(AbstractFrostGolemServant golem) {
            super(golem, 1.0D, true);
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = golem.getTarget();
            return target != null && !golem.prefersRanged(target) && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = golem.getTarget();
            return target != null && !golem.prefersRanged(target) && super.canContinueToUse();
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
            if (meleeGapDistanceSqr(golem, target) <= golem.meleeAnticipatedReachSqr(target)
                    && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                golem.doHurtTarget(target);
            }
        }
    }

    private static final class SnowballAttackGoal extends Goal {
        private final AbstractFrostGolemServant golem;

        private int readyAtTick = Integer.MIN_VALUE;

        private boolean volleyStarted;

        SnowballAttackGoal(AbstractFrostGolemServant golem) {
            this.golem = golem;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public void start() {
            this.volleyStarted = false;
        }

        @Override
        public boolean canUse() {
            return inRangedPosition();
        }

        @Override
        public boolean canContinueToUse() {
            return inRangedPosition();
        }

        private boolean inRangedPosition() {
            LivingEntity target = golem.getTarget();
            if (target == null || !target.isAlive() || !golem.prefersRanged(target)) {
                return false;
            }
            double range = golem.rangedRange();
            return golem.distanceToSqr(target) <= range * range;
        }

        @Override
        public void tick() {
            LivingEntity target = golem.getTarget();
            if (target == null) {
                return;
            }
            golem.getLookControl().setLookAt(target, 30.0F, 30.0F);
            golem.getNavigation().stop();

            int volleyTicks = golem.snowballVolleyTicks();
            if (volleyTicks > 0) {
                if (!this.volleyStarted) {
                    golem.startSnowballVolley(volleyTicks);
                    this.volleyStarted = true;
                    this.readyAtTick = golem.tickCount;
                } else if (!golem.isSnowballVolleyActive()) {
                    golem.startSnowballVolleyCooldown(golem.snowballVolleyCooldownTicks());
                    this.volleyStarted = false;
                    return;
                }
                if (golem.tickCount < this.readyAtTick) {
                    return;
                }
                if (golem.throwRangedProjectile(target, golem.rangedDamage())) {
                    this.readyAtTick = golem.tickCount + golem.snowballVolleyIntervalTicks();
                }
                return;
            }

            if (golem.tickCount < this.readyAtTick) {
                return;
            }
            if (golem.throwRangedProjectile(target, golem.rangedDamage())) {
                this.readyAtTick = golem.tickCount + golem.rangedShotPeriodTicks();
            }
        }
    }

    private static final class FrostApproachGoal extends Goal {

        private static final int RECALC_INTERVAL = 10;
        private final AbstractFrostGolemServant golem;
        private final double speed;
        private int timeToRecalcPath;

        FrostApproachGoal(AbstractFrostGolemServant golem, double speed) {
            this.golem = golem;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = golem.getTarget();
            if (target == null || !target.isAlive() || !golem.prefersRanged(target)) {
                return false;
            }
            double range = golem.rangedRange();
            return golem.distanceToSqr(target) > range * range;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            timeToRecalcPath = 0;
        }

        @Override
        public void tick() {
            LivingEntity target = golem.getTarget();
            if (target == null) {
                return;
            }
            golem.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (--timeToRecalcPath <= 0) {
                timeToRecalcPath = RECALC_INTERVAL;
                golem.getNavigation().moveTo(target, speed);
            }
        }
    }
}
