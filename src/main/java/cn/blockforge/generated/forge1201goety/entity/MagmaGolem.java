package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.HellBlast;
import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.EnumSet;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.event.GolemConstructionEvents;
import cn.blockforge.generated.goetysupreme.util.GolemMolds;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolemFireball;
import cn.blockforge.generated.goetysupreme.compat.MagmaGolemJadePlugin;
import cn.blockforge.generated.goetysupreme.renderer.MagmaGolemRenderer;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;

public class MagmaGolem extends AbstractGolemServant {

    private static final EntityDataAccessor<Boolean> DATA_PETRIFIED =
            SynchedEntityData.defineId(MagmaGolem.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_RECOVERY_PERCENT =
            SynchedEntityData.defineId(MagmaGolem.class, EntityDataSerializers.INT);

    private int waterTicks;

    private int lavaSoakTicks;
    private int soakGoalTicks;

    private int wetnessTicks;

    private static final int WETNESS_PETRIFY_TICKS = 20;

    @Nullable
    private static MobEffect cachedWetnessEffect;
    private static boolean wetnessEffectResolved;

    private static final int TARGET_SIGHT_GRACE_TICKS = 20;

    private int targetSightTicks;

    public static final double MAGMA_MELEE_AOE_RADIUS = 2.8D;

    public MagmaGolem(EntityType<? extends MagmaGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.MAGMA)
                .add(Attributes.FOLLOW_RANGE, GolemServantConfig.MAGMA.CHASE_RANGE.get());
    }

    @Override
    public void setConfigurableAttributes() {
        super.setConfigurableAttributes();
        AttributeInstance followRange = getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.setBaseValue(GolemServantConfig.MAGMA.CHASE_RANGE.get());
        }
    }

    @Override
    public GolemServantConfig.Section configSection() {
        return GolemServantConfig.MAGMA;
    }

    private GolemServantConfig.MagmaSection magmaConfig() {
        return GolemServantConfig.MAGMA;
    }

    private boolean ownerWaterWarded() {
        LivingEntity owner = getTrueOwner();
        return owner != null && (CuriosFinder.hasUnholySet(owner) || CuriosFinder.hasNetherSet(owner));
    }

    private int waterSlownessLevel() {
        return ownerWaterWarded() ? 5 : Math.max(1, magmaConfig().WATER_SLOWNESS_LEVEL.get());
    }

    private int waterPetrifySeconds() {
        return ownerWaterWarded() ? 3 : magmaConfig().WATER_PETRIFY_SECONDS.get();
    }

    private int rollSoakGoalTicks() {
        int min = Math.max(0, magmaConfig().STONE_RECOVERY_MIN_SECONDS.get()) * 20;
        int max = Math.max(0, magmaConfig().STONE_RECOVERY_MAX_SECONDS.get()) * 20;
        int hi = Math.max(min, max);
        int rolled = min + (hi > min ? random.nextInt(hi - min + 1) : 0);
        if (ownerWaterWarded()) {
            rolled = rolled * 2 / 3;
        }
        return Math.max(1, rolled);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public int meleeAnticipationTicks() {
        return AbstractGolemServant.MAGMA_SWEEP_IMPACT_TICK;
    }

    @Override
    public double meleeAnticipationMax() {
        return 2.0D;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.goalSelector.addGoal(1, new FireballGoal(this));
        this.goalSelector.addGoal(2, new CloseMeleeGoal(this));
        this.goalSelector.addGoal(3, new ApproachGoal(this, 1.0D));
    }

    private static final class CloseMeleeGoal extends MeleeAttackGoal {
        private final MagmaGolem golem;

        CloseMeleeGoal(MagmaGolem golem) {
            super(golem, 1.0D, true);
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !golem.isPetrified() && !golem.isAttackAnimating()
                    && withinMeleeRange() && hasTargetInSight();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !golem.isPetrified() && withinMeleeRange()
                    && hasTargetInSight();
        }

        private boolean hasTargetInSight() {
            LivingEntity target = golem.getTarget();
            return target != null && golem.hasLineOfSight(target);
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
            if (golem.isAttackAnimating()) {
                return;
            }
            if (meleeGapDistanceSqr(golem, target) <= golem.meleeAnticipatedReachSqr(target)
                    && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                golem.doHurtTarget(target);
            }
        }

        private boolean withinMeleeRange() {
            LivingEntity target = golem.getTarget();
            if (target == null) {
                return false;
            }
            double range = magmaRange();
            return meleeGapDistanceSqr(golem, target) <= gapThresholdSqr(range);
        }

        private static double magmaRange() {
            return GolemServantConfig.MAGMA.MELEE_RANGE.get();
        }
    }

    private static final class ApproachGoal extends Goal {

        private static final int RECALC_INTERVAL = 10;
        private final MagmaGolem golem;
        private final double speed;
        private int timeToRecalcPath;

        ApproachGoal(MagmaGolem golem, double speed) {
            this.golem = golem;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (golem.isPetrified()) {
                return false;
            }
            LivingEntity target = golem.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            double maxRange = GolemServantConfig.MAGMA.RANGED_RANGE.get();
            return golem.distanceToSqr(target) > maxRange * maxRange;
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

    private static final class FireballGoal extends Goal {
        private final MagmaGolem golem;

        private boolean thrown;

        private LivingEntity pendingTarget;

        private Vec3 fallbackAim;

        FireballGoal(MagmaGolem golem) {
            this.golem = golem;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (golem.isPetrified() || GolemServantConfig.MAGMA.FIREBALL_DAMAGE.get() <= 0.0D) {
                return false;
            }
            if (golem.isAttackAnimating() || golem.fireballCooldown > 0) {
                return false;
            }
            LivingEntity target = golem.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            double minRange = GolemServantConfig.MAGMA.MELEE_RANGE.get();
            double maxRange = GolemServantConfig.MAGMA.RANGED_RANGE.get();
            double distSqr = golem.distanceToSqr(target);
            return distSqr > minRange * minRange && distSqr <= maxRange * maxRange
                    && golem.hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            return golem.isAttackAnimating();
        }

        @Override
        public void start() {
            this.thrown = false;
            this.pendingTarget = golem.getTarget();
            this.fallbackAim = this.pendingTarget != null
                    ? fireballAimPoint(this.pendingTarget) : null;
            golem.fireballCooldown = Math.max(1, GolemServantConfig.MAGMA.FIREBALL_INTERVAL_TICKS.get());
            golem.startFireballAnimation();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = golem.getTarget();
            if (target == null) {
                target = this.pendingTarget;
            }
            if (target == null) {
                return;
            }
            golem.getNavigation().stop();
            golem.getLookControl().setLookAt(target, 30.0F, 30.0F);
            spawnChargeFlames(golem);
            if (!this.thrown && golem.attackReached(AbstractGolemServant.FIREBALL_RELEASE_TICK)) {
                this.thrown = true;
                if (target.isAlive()) {
                    if (golem.hasLineOfSight(target)) {
                        golem.fireFireball(target);
                    }
                } else if (target.isRemoved()) {
                    if (this.fallbackAim != null) {
                        golem.fireFireballTo(this.fallbackAim);
                    }
                } else {
                    golem.fireFireballTo(fireballAimPoint(target));
                }
            }
        }

        @Override
        public void stop() {
            this.thrown = false;
            this.pendingTarget = null;
            this.fallbackAim = null;
        }

        private static void spawnChargeFlames(MagmaGolem golem) {
            if (!(golem.level() instanceof ServerLevel serverLevel)) {
                return;
            }
            int count = 6 + golem.random.nextInt(4);
            double rx = golem.getBbWidth() * 0.9D;
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    golem.getX(), golem.getY(1.1D), golem.getZ(),
                    count, rx, 0.7D, rx, 0.01D);
        }
    }

    public void startFireballAnimation() {
        startAttack(AbstractGolemServant.ATTACK_FIREBALL,
                AbstractGolemServant.ATTACK_LENGTHS[AbstractGolemServant.ATTACK_FIREBALL]);
    }

    private int fireballCooldown;

    private Entity pendingMeleeTarget;

    private boolean meleeImpactDone;

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().isClientSide) {
            return false;
        }
        this.pendingMeleeTarget = target;
        this.meleeImpactDone = false;
        int animation = attackSwingAnimation();
        startAttack(animation, ATTACK_LENGTHS[animation]);
        return true;
    }

    private void tickMagmaMelee() {
        if (level().isClientSide || this.meleeImpactDone || this.pendingMeleeTarget == null
                || !isAttackAnimating()) {
            return;
        }
        if (!attackReached(AbstractGolemServant.MAGMA_SWEEP_IMPACT_TICK)) {
            return;
        }
        this.meleeImpactDone = true;
        Entity target = this.pendingMeleeTarget;
        this.pendingMeleeTarget = null;
        double range = magmaConfig().MELEE_RANGE.get();
        if (target instanceof LivingEntity living && living.isAlive() && !isAlliedTo(living)
                && distanceToSqr(living) <= range * range * 2.25D && hasLineOfSight(living)) {
            float attackDamage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE));
            if (living.hurt(damageSources().mobAttack(this), attackDamage)) {
                applyMeleeKnockback(living, MELEE_KNOCKBACK);
                doEnchantDamageEffects(this, living);
                onAttackHit(living);
            }
        }
        applyMagmaMeleeAoe(target);
        if (!isSilent()) {
            playSound(GeneratedMod.MAGMA_GOLEM_ATTACK.get(), 1.0F, 1.0F);
        }
    }

    private void applyMagmaMeleeAoe(Entity primaryTarget) {
        if (!(level() instanceof ServerLevel)) {
            return;
        }
        double radius = MAGMA_MELEE_AOE_RADIUS;
        AABB area = getBoundingBox().inflate(radius, radius * 0.75D, radius);
        float attackDamage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area, this::canAttack)) {
            if (victim == this || victim == primaryTarget || isAlliedTo(victim)
                    || distanceToSqr(victim) > radius * radius || victim.isRemoved()
                    || !hasLineOfSight(victim)) {
                continue;
            }
            if (victim.hurt(damageSources().mobAttack(this), attackDamage)) {
                applyMeleeKnockback(victim, MELEE_KNOCKBACK);
                doEnchantDamageEffects(this, victim);
                onAttackHit(victim);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isAlive()) {
            return;
        }
        if (this.fireballCooldown > 0) {
            --this.fireballCooldown;
        }
        tickMagmaMelee();
        tickTargetSight();
    }

    private void tickTargetSight() {
        if (level().isClientSide) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            targetSightTicks = 0;
            return;
        }
        if (hasLineOfSight(target)) {
            targetSightTicks = 0;
            return;
        }
        if (++targetSightTicks > TARGET_SIGHT_GRACE_TICKS) {
            setTarget(null);
            getNavigation().stop();
            targetSightTicks = 0;
        }
    }

    @Override
    public int attackSwingAnimation() {
        return AbstractGolemServant.ATTACK_MAGMA_SWEEP;
    }

    public void fireFireball(LivingEntity target) {
        if (level().isClientSide) {
            return;
        }
        Vec3 from = handPosition();
        Vec3 aim = ProjectileIntercept.solveForTarget(from, target,
                MagmaGolemFireball.ACCELERATION_POWER, MagmaGolemFireball.INERTIA);
        Vec3 dir = aim.subtract(from);
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        spawnFireball(from, dir.normalize());
    }

    public void fireFireballTo(Vec3 targetPos) {
        if (level().isClientSide) {
            return;
        }
        Vec3 from = handPosition();
        Vec3 dir = targetPos.subtract(from);
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        spawnFireball(from, dir.normalize());
    }

    private void spawnFireball(Vec3 from, Vec3 unit) {
        LivingEntity owner = getTrueOwner();
        if (owner != null && CuriosFinder.hasUnholySet(owner)) {
            spawnHellBlast(from, unit);
        } else {
            MagmaGolemFireball fireball = new MagmaGolemFireball(level(), this, unit.x, unit.y, unit.z);
            fireball.setPos(from.x, from.y, from.z);
            level().addFreshEntity(fireball);
        }
        if (!isSilent()) {
            playSound(SoundEvents.GHAST_SHOOT, 2.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        }
    }

    private void spawnHellBlast(Vec3 from, Vec3 unit) {
        HellBlast blast = new HellBlast(from.x, from.y, from.z, unit.x, unit.y, unit.z, level());
        blast.setOwner(this);
        blast.setDamage(GolemServantConfig.MAGMA.FIREBALL_DAMAGE.get().floatValue());
        blast.setRadius(GolemServantConfig.MAGMA.FIREBALL_EXPLOSION_RADIUS.get().floatValue());
        blast.setFiery(1);
        level().addFreshEntity(blast);
    }

    private Vec3 handPosition() {
        double yaw = Math.toRadians(this.getYRot());
        double localX = GolemClips.HAND_OFFSET_X / 16.0D;
        double localZ = GolemClips.HAND_OFFSET_Z / 16.0D;
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);
        double worldX = localX * cos + localZ * sin;
        double worldZ = -localX * sin + localZ * cos;
        return new Vec3(getX() + worldX, getY() + GolemClips.HAND_OFFSET_Y / 16.0D, getZ() + worldZ);
    }

    private static Vec3 fireballAimPoint(LivingEntity target) {
        return target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.LAVA);
    }

    public boolean isPetrified() {
        return Boolean.TRUE.equals(entityData.get(DATA_PETRIFIED));
    }

    private void setPetrified(boolean petrified) {
        if (isPetrified() != petrified) {
            entityData.set(DATA_PETRIFIED, petrified);
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_PETRIFIED, false);
        entityData.define(DATA_RECOVERY_PERCENT, 0);
    }

    public int getRecoveryPercent() {
        return entityData.get(DATA_RECOVERY_PERCENT);
    }

    private void syncRecoveryPercent() {
        int percent = 0;
        if (isPetrified() && soakGoalTicks > 0) {
            percent = Mth.clamp((int) Math.round(lavaSoakTicks * 100.0D / soakGoalTicks), 0, 100);
        }
        if (entityData.get(DATA_RECOVERY_PERCENT) != percent) {
            entityData.set(DATA_RECOVERY_PERCENT, percent);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            return;
        }

        boolean lavaBath = isInLavaBath();
        if (lavaBath) {
            waterTicks = 0;
        }

        if (isPetrified()) {
            boolean wet = hasWetness();
            if (wet) {
                lavaSoakTicks = 0;
            } else if (lavaBath) {
                ++lavaSoakTicks;
                if (tickCount % 5 == 0 && level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LAVA,
                            getX(), getY(1.2D), getZ(), 2, 0.5D, 0.6D, 0.5D, 0.02D);
                }
                if (lavaSoakTicks >= soakGoalTicks) {
                    recoverFromStone();
                }
            } else {
                lavaSoakTicks = Math.max(0, lavaSoakTicks - 2);
            }
            syncRecoveryPercent();
            return;
        }

        syncRecoveryPercent();

        GolemServantConfig.MagmaSection config = magmaConfig();

        if (isInWater()) {
            ++waterTicks;
            int level = Math.max(1, waterSlownessLevel()) - 1;
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, level, false, true));
            if (waterTicks > waterPetrifySeconds() * 20) {
                enterStoneState();
            }
        } else {
            waterTicks = 0;
        }

        if (hasWetness()) {
            if (++wetnessTicks > WETNESS_PETRIFY_TICKS) {
                enterStoneState();
                return;
            }
        } else {
            wetnessTicks = 0;
        }

        if (lavaBath && isAlive() && getHealth() < getMaxHealth() && tickCount % 20 == 0) {
            heal((float) (double) config.LAVA_HEAL_PER_SECOND.get());
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LAVA,
                        getX(), getY(1.2D), getZ(), 1, 0.3D, 0.5D, 0.3D, 0.0D);
            }
        } else {
            tickFireHeal();
        }
    }

    private boolean hasWetness() {
        if (!wetnessEffectResolved) {
            wetnessEffectResolved = true;
            for (MobEffect effect : ForgeRegistries.MOB_EFFECTS.getValues()) {
                ResourceLocation id = ForgeRegistries.MOB_EFFECTS.getKey(effect);
                if (id == null || !id.getPath().contains("wet")) {
                    continue;
                }
                boolean cataclysm = id.getNamespace().contains("cataclysm");
                if (cataclysm || cachedWetnessEffect == null) {
                    cachedWetnessEffect = effect;
                }
                if (cataclysm) {
                    break;
                }
            }
        }
        return cachedWetnessEffect != null && this.hasEffect(cachedWetnessEffect);
    }

    private boolean isInLavaBath() {
        if (isInLava()) {
            return true;
        }
        AABB box = getBoundingBox().inflate(0.1D);
        BlockPos min = BlockPos.containing(box.minX, box.minY - 1.0D, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.minY + 1.0D, box.maxZ);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int y = min.getY(); y <= max.getY(); y++) {
            for (int x = min.getX(); x <= max.getX(); x++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    if (level().getFluidState(cursor.set(x, y, z)).is(FluidTags.LAVA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void enterStoneState() {
        setPetrified(true);
        setNoAi(true);
        setTarget(null);
        targetSightTicks = 0;
        getNavigation().stop();
        setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
        soakGoalTicks = rollSoakGoalTicks();
        lavaSoakTicks = 0;
        playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 0.7F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ASH,
                    getX(), getY(1.2D), getZ(), 12, 0.6D, 0.8D, 0.6D, 0.0D);
        }
    }

    private void recoverFromStone() {
        setPetrified(false);
        setNoAi(false);
        lavaSoakTicks = 0;
        waterTicks = 0;
        heal(getMaxHealth() * 0.1F);
        playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.4F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    getX(), getY(1.2D), getZ(), 16, 0.6D, 0.9D, 0.6D, 0.02D);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Petrified", isPetrified());
        tag.putInt("WaterTicks", waterTicks);
        tag.putInt("LavaSoakTicks", lavaSoakTicks);
        tag.putInt("SoakGoalTicks", soakGoalTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setPetrified(tag.getBoolean("Petrified"));
        waterTicks = tag.getInt("WaterTicks");
        lavaSoakTicks = tag.getInt("LavaSoakTicks");
        soakGoalTicks = tag.getInt("SoakGoalTicks");
        setNoAi(isPetrified());
        syncRecoveryPercent();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isPetrified() && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            amount *= magmaConfig().STONE_DAMAGE_MULTIPLIER.get().floatValue();
        }
        return super.hurt(source, amount);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        int iron = random.nextInt(2);
        int redstone = 2 + random.nextInt(2);
        int magma = 4 + random.nextInt(2);
        if (iron > 0) {
            spawnAtLocation(new ItemStack(Items.IRON_BLOCK, iron), 0.3F);
        }
        spawnAtLocation(new ItemStack(Items.REDSTONE_BLOCK, redstone), 0.3F);
        spawnAtLocation(new ItemStack(Items.MAGMA_BLOCK, magma), 0.3F);
    }

    @Override
    public float attackDamageFloorRatio() {
        return 0.6F;
    }

    @Override
    public void onAttackHit(Entity target) {
        if (level().isClientSide) {
            return;
        }
        GolemServantConfig.MagmaSection config = magmaConfig();
        float fireDamage = config.MELEE_FIRE_DAMAGE.get().floatValue();
        if (fireDamage > 0.0F && target instanceof LivingEntity living) {
            applyBonusDamage(living, damageSources().onFire(), fireDamage);
        }
        int seconds = config.MELEE_IGNITE_SECONDS.get();
        if (seconds > 0) {
            target.setSecondsOnFire(seconds);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.MAGMA_GOLEM_AMBIENT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GeneratedMod.MAGMA_GOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.MAGMA_GOLEM_DEATH.get();
    }

    @Override
    public boolean usesAnimationDrivenFootsteps() {
        return true;
    }

    @Override
    public void playFootstepSound() {
        playSound(GeneratedMod.MAGMA_GOLEM_STEP.get(), 1.0F, 1.0F);
    }
}
