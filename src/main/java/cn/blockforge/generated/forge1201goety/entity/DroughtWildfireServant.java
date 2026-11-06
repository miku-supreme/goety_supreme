package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.ai.AmphibiousGroundNavigation;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesServantEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class DroughtWildfireServant extends AbstractJerotesServant implements RangedAttackMob {

    private static final EntityDataAccessor<Integer> ANIM_STATE =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> IDLE_ANIM =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROLL_ANIM =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICK =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_USE =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FLAME_TICK =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DROUGHT_BLAZE_TICK =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DROUGHT_BLAZE_USE_TICK =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHIELD_COUNT =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHIELD_ADD_COOLDOWN =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SHIELD_HEALTH =
            SynchedEntityData.defineId(DroughtWildfireServant.class, EntityDataSerializers.FLOAT);

    private static final int ANIM_NONE = 0;
    private static final int ANIM_SHOOT = 1;
    private static final int ANIM_FLAME = 2;
    private static final int ANIM_DROUGHT_BLAZE = 3;
    private static final int ANIM_ATTACK1 = 4;
    private static final int ANIM_ATTACK2 = 5;
    private static final int ANIM_DEAD = 6;

    private static final int SHIELD_ADD_COOLDOWN_TICKS = 120;
    private static final int SHIELD_MAX_COUNT = 4;

    private static float shieldMaxHealth() {
        return (float) (double) JerotesVillageServantConfig.DROUGHT_WILDFIRE.SHIELD_MAX_HEALTH.get();
    }

    private static final float FIRE_ABSORPTION_PERCENT = 80.0F;

    private static final int FIRE_ABSORPTION_COOLDOWN_TICKS = 5;

    private static final double RANGED_SPEED = 0.8D;
    private static final int RANGED_INTERVAL = 80;
    private static final float RANGED_RANGE = 20.0F;

    private static final double FIREBALL_MIN_DISTANCE = 8.0D;

    private static final double FIREBALL_SPEED = 1.0D;

    private static final int ATTACK_TICK_FLOOR = -60;

    private static final int FLAME_TICKS = 60;
    private static final double FLAME_RANGE = 9.0D;
    private static final double FLAME_HALF_ANGLE_RAD = Math.toRadians(22.5D / 2.0D);
    private static final int FLAME_DICE_SIDES = 6;
    private static final float FLAME_DICE_DIVISOR = 30.0F;
    private static final int FLAME_BURN_SECONDS = 12;

    private static final int FLAME_CAST_INTERVAL = 2;

    private static final int FLAME_START_TICK = 50;

    private static final int DROUGHT_BLAZE_CHARGE_TICKS = 600;
    private static final int DROUGHT_BLAZE_ANIM_TICKS = 25;
    private static final int DROUGHT_BLAZE_SUMMON_TICK = 5;

    private static final int SPELL_LEVEL = 3;

    private static final int SUMMON_LIMIT = 6;

    private static final int SUMMON_PER_CAST = 3;

    private static final int CONJURE_WILDFIRE_SPELL_LEVEL = 5;

    private static final int ATTACK_ANIM_TICKS = 30;

    private int deferredCastTick;
    private LivingEntity deferredCastTarget;

    private int fireAbsorptionCooldown;
    private float fireAbsorptionLastDamage;

    public float idleAnimProgress;
    public float rollAnimProgress;
    public float thisTickRenderTime;
    public float lastTickRenderTime;

    public final AnimationState idle1AnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    public final AnimationState flameAnimationState = new AnimationState();
    public final AnimationState droughtBlazeAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    public DroughtWildfireServant(EntityType<? extends DroughtWildfireServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner()
                && ServantFeedHeal.tryBlazeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.DROUGHT_WILDFIRE;
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        AmphibiousGroundNavigation navigation = new AmphibiousGroundNavigation(this, level);
        navigation.setCanOpenDoors(false);
        return navigation;
    }

    @Override
    public float getStandingEyeHeight(Pose pose, net.minecraft.world.entity.EntityDimensions dimensions) {
        return 1.875F;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/elemental/drought_wildfire.png";
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("drought_wildfire_ambient", SoundEvents.BLAZE_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("drought_wildfire_hurt", SoundEvents.BLAZE_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("drought_wildfire_death", SoundEvents.BLAZE_DEATH);
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIM_STATE, ANIM_NONE);
        this.entityData.define(ANIM_TICK, 0);
        this.entityData.define(IDLE_ANIM, 0);
        this.entityData.define(ROLL_ANIM, 0);
        this.entityData.define(ATTACK_TICK, 0);
        this.entityData.define(ATTACK_USE, 0);
        this.entityData.define(FLAME_TICK, 0);
        this.entityData.define(DROUGHT_BLAZE_TICK, 400);
        this.entityData.define(DROUGHT_BLAZE_USE_TICK, 0);
        this.entityData.define(SHIELD_COUNT, SHIELD_MAX_COUNT);
        this.entityData.define(SHIELD_ADD_COOLDOWN, 0);
        this.entityData.define(SHIELD_HEALTH, shieldMaxHealth());
    }

    public int getShieldCount() {
        return this.entityData.get(SHIELD_COUNT);
    }

    private float getShieldHealth() {
        return this.entityData.get(SHIELD_HEALTH);
    }

    private void setShieldHealth(float value) {
        this.entityData.set(SHIELD_HEALTH, value);
    }

    public boolean isSpecialAction() {
        int anim = this.entityData.get(ANIM_STATE);
        return anim == ANIM_SHOOT || anim == ANIM_FLAME || anim == ANIM_DROUGHT_BLAZE;
    }

    public int getIdleAnim() {
        return this.entityData.get(IDLE_ANIM);
    }

    public int getRollAnim() {
        return this.entityData.get(ROLL_ANIM);
    }

    private void setAnimationState(int state) {
        this.entityData.set(ANIM_STATE, state);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (level().isClientSide && ANIM_STATE.equals(key)) {
            switch (this.entityData.get(ANIM_STATE)) {
                case ANIM_SHOOT -> {
                    shootAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(shootAnimationState);
                }
                case ANIM_FLAME -> {
                    flameAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(flameAnimationState);
                }
                case ANIM_DROUGHT_BLAZE -> {
                    droughtBlazeAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(droughtBlazeAnimationState);
                }
                case ANIM_ATTACK1 -> {
                    attack1AnimationState.startIfStopped(tickCount);
                    stopMostAnimation(attack1AnimationState);
                }
                case ANIM_ATTACK2 -> {
                    attack2AnimationState.startIfStopped(tickCount);
                    stopMostAnimation(attack2AnimationState);
                }
                case ANIM_DEAD -> {
                    deadAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(deadAnimationState);
                }
                default -> stopAllAnimation();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    private List<AnimationState> allActionAnimations() {
        return List.of(shootAnimationState, flameAnimationState, droughtBlazeAnimationState,
                attack1AnimationState, attack2AnimationState, deadAnimationState);
    }

    private void stopMostAnimation(AnimationState keep) {
        for (AnimationState state : allActionAnimations()) {
            if (state != keep) {
                state.stop();
            }
        }
    }

    private void stopAllAnimation() {
        for (AnimationState state : allActionAnimations()) {
            state.stop();
        }
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new DroughtWitchAttackGoal(this));
    }

    private static final class DroughtWitchAttackGoal extends RangedAttackGoal {
        private final DroughtWildfireServant wildfire;
        private final double speedModifier = RANGED_SPEED;
        private final int attackIntervalMin = RANGED_INTERVAL;
        private final float attackRadiusSqr = RANGED_RANGE * RANGED_RANGE;
        private int attackTime = -1;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        DroughtWitchAttackGoal(DroughtWildfireServant wildfire) {
            super(wildfire, RANGED_SPEED, RANGED_INTERVAL, RANGED_RANGE);
            this.wildfire = wildfire;
            setFlags(java.util.EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public void start() {
            super.start();
            this.attackTime = -1;
            this.seeTime = 0;
            this.strafingTime = -1;
            this.wildfire.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.wildfire.setAggressive(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.wildfire.getTarget();
            return target != null && target.isAlive() && this.wildfire.canAttack(target);
        }

        @Override
        public void tick() {
            LivingEntity target = this.wildfire.getTarget();
            if (target == null) {
                return;
            }
            double d = this.wildfire.distanceToSqr(target.getX(), target.getY(), target.getZ());
            boolean hasLineOfSight = this.wildfire.getSensing().hasLineOfSight(target);
            boolean seen = this.seeTime > 0;
            if (hasLineOfSight != seen) {
                this.seeTime = 0;
            }
            this.seeTime = hasLineOfSight ? this.seeTime + 1 : this.seeTime - 1;
            if (d > this.attackRadiusSqr || this.seeTime < 20) {
                this.wildfire.getNavigation().moveTo(target, this.speedModifier);
                this.strafingTime = -1;
            } else {
                this.wildfire.getNavigation().stop();
                ++this.strafingTime;
            }
            if (this.strafingTime >= 20) {
                if (this.wildfire.getRandom().nextFloat() < 0.3F) {
                    this.strafingClockwise = !this.strafingClockwise;
                }
                if (this.wildfire.getRandom().nextFloat() < 0.3F) {
                    this.strafingBackwards = !this.strafingBackwards;
                }
                this.strafingTime = 0;
            }
            if (this.strafingTime > -1) {
                if (d > this.attackRadiusSqr * 0.75F) {
                    this.strafingBackwards = false;
                } else if (d < this.attackRadiusSqr * 0.25F) {
                    this.strafingBackwards = true;
                }
                float forward = this.strafingBackwards ? -0.5F : 0.5F;
                this.wildfire.getMoveControl().strafe(forward,
                        this.strafingClockwise ? 0.5F : -0.5F);
                Entity vehicle = this.wildfire.getControlledVehicle();
                if (vehicle instanceof Mob mob) {
                    mob.lookAt(target, 360.0F, 360.0F);
                }
                this.wildfire.lookAt(target, 360.0F, 360.0F);
            } else {
                this.wildfire.getLookControl().setLookAt(target, 360.0F, 360.0F);
            }
            if (--this.attackTime == 0) {
                if (!seen) {
                    return;
                }
                float f = (float) Math.sqrt(d) / this.attackRadiusSqr;
                this.wildfire.performRangedAttack(target, net.minecraft.util.Mth.clamp(f, 0.1F, 1.0F));
                this.attackTime = this.attackIntervalMin;
            } else if (this.attackTime < 0) {
                this.attackTime = this.attackIntervalMin;
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        if (isSpecialAction() || getAttackTick() > 0) {
            return;
        }
        if (getAttackTick() <= ATTACK_TICK_FLOOR
                && getAttackBoundingBox().intersects(target.getBoundingBox())
                && hasLineOfSight(target)) {
            this.deferredCastTick = ATTACK_ANIM_TICKS;
            this.deferredCastTarget = target;
            return;
        }
        doRangedCast(target);
    }

    private void doRangedCast(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)
                || isSpecialAction() || getAttackTick() > 0) {
            return;
        }
        if (distanceTo(target) > FIREBALL_MIN_DISTANCE || random.nextFloat() > 0.5F
                || !hasLineOfSight(target)) {
            this.entityData.set(ANIM_TICK, 50);
            setAnimationState(ANIM_SHOOT);
            castFeedback("magic_drought_fireball", SoundEvents.BLAZE_SHOOT,
                    "drought_fireball_display", ParticleTypes.FLAME, SPELL_LEVEL);
            spawnFireball(target);
            startSpellBurst(burstCastingTicks(SPELL_LEVEL), target, this::spawnFireball);
        } else {
            this.entityData.set(ANIM_TICK, FLAME_TICKS);
            setAnimationState(ANIM_FLAME);
            this.entityData.set(FLAME_TICK, FLAME_TICKS);
            castFeedback("magic_serpon_flames", SoundEvents.BLAZE_BURN,
                    "serpon_flames_display", ParticleTypes.FLAME, SPELL_LEVEL);
        }
    }

    private void spawnFireball(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        Vec3 dir = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D)
                .subtract(getX(), getY(1.0D), getZ()).normalize().scale(FIREBALL_SPEED);
        DroughtFireballServantEntity fireball =
                new DroughtFireballServantEntity(level(), this, dir, FLAME_BURN_SECONDS);
        fireball.setJerotesAcceleration();
        level().addFreshEntity(fireball);
        playSound(jerotesSound("drought_wildfire_shoot", SoundEvents.BLAZE_SHOOT), 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().isClientSide) {
            return super.doHurtTarget(target);
        }
        if (isSpecialAction() || getAttackTick() > ATTACK_TICK_FLOOR) {
            return false;
        }
        this.entityData.set(ATTACK_TICK, 30);
        this.entityData.set(ANIM_TICK, 30);
        this.entityData.set(ATTACK_USE, random.nextInt(30) > 15 || getShieldCount() <= 0 ? 2 : 1);
        if (this.entityData.get(ATTACK_USE) == 2) {
            setAnimationState(ANIM_ATTACK2);
            if (!isSilent()) {
                playSound(jerotesSound("drought_wildfire_shockwave", SoundEvents.GENERIC_EXPLODE),
                        5.0F, 1.0F);
            }
        } else {
            setAnimationState(ANIM_ATTACK1);
        }
        return true;
    }

    public int getAttackTick() {
        return this.entityData.get(ATTACK_TICK);
    }

    private void trueHurt() {
        int attackUse = this.entityData.get(ATTACK_USE);
        float damageMulti = 1.0F;
        float knockbackMulti = 1.0F;
        float reach = 0.5F;
        if (attackUse == 2) {
            knockbackMulti = 1.5F;
            reach = 1.25F;
        }
        if (attackUse == 1) {
            damageMulti = 2.0F;
            knockbackMulti = 1.25F;
            reach = 1.25F;
            if (!isSilent()) {
                playSound(jerotesSound("drought_wildfire_thrust", SoundEvents.BLAZE_BURN), 5.0F, 1.0F);
            }
        }
        float baseDamage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        for (LivingEntity hurt : level().getEntitiesOfClass(LivingEntity.class,
                getAttackBoundingBox().inflate(reach))) {
            if (hurt == this || !canAttack(hurt) || !hurt.isAlive()
                    || distanceTo(hurt) > 128.0D) {
                continue;
            }
            if (hurt instanceof DroughtBlazeServant blaze
                    && blaze.getTarget() != this && getTarget() != blaze) {
                continue;
            }
            boolean hit = hurt.hurt(ModDamageSource.summonAttack(this, getTrueOwner()),
                    baseDamage * damageMulti);
            if (!hit) {
                continue;
            }
            hurt.setSecondsOnFire(12);
            if (attackUse == 2 && sizeAllowsSpecialKnockback(hurt)) {
                double kb = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double d3 = Math.max(0.0D, 1.0D - Math.max(kb, 1.0D)) + 0.3D;
                double factor = d3 / 1.5D * (0.7D + knockbackMulti / 3.0D);
                hurt.setOnGround(false);
                hurt.setDeltaMovement(hurt.getDeltaMovement().add(
                        -(getX() - hurt.getX()) * factor,
                        -(getY() - hurt.getY()) * factor,
                        -(getZ() - hurt.getZ()) * factor));
                hurt.setDeltaMovement(hurt.getDeltaMovement().add(
                        0.0D, (7.0F - distanceTo(hurt)) / 10.0F, 0.0D));
                hurt.hurtMarked = true;
            }
        }
        if (attackUse == 2) {
            for (LivingEntity shake : level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(12.0D))) {
                if (shake != null && shake != this) {
                    applyJerotesEffect(shake, "quake", 20, 0);
                }
            }
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        jerotesParticle("drought_wildfire_shockwave", ParticleTypes.EXPLOSION),
                        getX(), getY() + 0.1D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            for (Boat boat : level().getEntitiesOfClass(Boat.class,
                    getAttackBoundingBox().inflate(reach))) {
                if (boat != null && distanceTo(boat) <= 128.0D) {
                    boat.hurt(damageSources().mobAttack(this), 30.0F);
                }
            }
        } else if (level() instanceof ServerLevel serverLevel) {
            double dx = -net.minecraft.util.Mth.sin(getYRot() * ((float) Math.PI / 180.0F));
            double dz = net.minecraft.util.Mth.cos(getYRot() * ((float) Math.PI / 180.0F));
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                    getX() + dx, getY(0.5D), getZ() + dz, 0, dx, 0.0D, dz, 0.0D);
        }
    }

    private static boolean sizeAllowsSpecialKnockback(LivingEntity hurt) {
        return hurt.getBbWidth() < 3.5F && hurt.getBbHeight() < 6.0F;
    }

    private void applyJerotesEffect(LivingEntity target, String path, int duration, int amplifier) {
        net.minecraft.world.effect.MobEffect effect =
                net.minecraftforge.registries.ForgeRegistries.MOB_EFFECTS.getValue(
                        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jerotes", path));
        if (effect == null) {
            effect = net.minecraftforge.registries.ForgeRegistries.MOB_EFFECTS.getValue(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        }
        if (effect == null && path.equals("quake")) {
            effect = net.minecraft.world.effect.MobEffects.CONFUSION;
        }
        if (effect != null) {
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    effect, duration, amplifier, false, false), this);
        }
    }

    private AABB getAttackBoundingBox() {
        AABB self = getBoundingBox();
        AABB base = self;
        Entity vehicle = getVehicle();
        if (vehicle != null) {
            AABB other = vehicle.getBoundingBox();
            base = new AABB(Math.min(self.minX, other.minX), self.minY,
                    Math.min(self.minZ, other.minZ), Math.max(self.maxX, other.maxX), self.maxY,
                    Math.max(self.maxZ, other.maxZ));
        }
        return base.inflate(Math.sqrt(2.04F) - 0.6D, 0.0D, Math.sqrt(2.04F) - 0.6D)
                .inflate(1.25D, 1.25D, 1.25D);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return getAttackBoundingBox().intersects(target.getBoundingBox());
    }

    private void flameCone() {
        if (level().isClientSide()) {
            return;
        }
        level().addFreshEntity(new SerponFlamesServantEntity(level(), this, SPELL_LEVEL, FLAME_BURN_SECONDS));
    }

    private void discardActiveFlameSnakes() {
        if (level().isClientSide()) {
            return;
        }
        for (SerponFlamesServantEntity snake : level().getEntitiesOfClass(SerponFlamesServantEntity.class,
                getBoundingBox().inflate(16.0D))) {
            if (snake.getOwner() == this) {
                snake.discard();
            }
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        discardActiveFlameSnakes();
        super.remove(reason);
    }

    private void summonDroughtBlazes() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int count = Math.min(SUMMON_PER_CAST, Math.max(0, SUMMON_LIMIT - countOwnedLimitedDroughtBlazes()));
        for (int i = 0; i < count; ++i) {
            DroughtBlazeServant blaze = ModEntityTypes.DROUGHT_BLAZE_SERVANT.get().create(serverLevel);
            if (blaze == null) {
                continue;
            }
            BlockPos summonPos = BlockFinder.SummonRadius(blockPosition(), blaze, serverLevel, 6, 9);
            if (summonPos == null) {
                summonPos = blockPosition();
            }
            blaze.moveTo(summonPos, getYRot(), 0.0F);
            blaze.setTrueOwner(this);
            blaze.setLimitedLife(AbstractJerotesServant.JEROTES_SERVANT_LIFESPAN_TICKS);
            blaze.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(summonPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            blaze.setTrueOwner(this);
            blaze.setPersistenceRequired();
            if (getTarget() != null && blaze.canAttack(getTarget())) {
                blaze.setTarget(getTarget());
            }
            if (serverLevel.addFreshEntity(blaze)) {
                serverLevel.sendParticles(ParticleTypes.LAVA, blaze.getRandomX(0.5D),
                        blaze.getY(), blaze.getRandomZ(0.5D), 20, 0.0D, 0.0D, 0.0D, 0.125D);
                GoetySummonFx.play(serverLevel, blaze);
                serverLevel.sendParticles(ParticleTypes.FLAME, blaze.getRandomX(0.5D),
                        blaze.getY(1.0D), blaze.getRandomZ(0.5D), 20, 0.3D, 0.3D, 0.3D, 0.05D);
            } else {
                blaze.discard();
            }
        }
    }

    private int countOwnedLimitedDroughtBlazes() {
        int n = 0;
        for (DroughtBlazeServant blaze : level().getEntitiesOfClass(DroughtBlazeServant.class,
                getBoundingBox().inflate(128.0D))) {
            if (blaze.getTrueOwner() == this && blaze.isLimitedLife()) {
                n++;
            }
        }
        return n;
    }

    private int countGateDroughtBlazes() {
        int n = 0;
        for (DroughtBlazeServant blaze : level().getEntitiesOfClass(DroughtBlazeServant.class,
                getBoundingBox().inflate(64.0D))) {
            if (gateKeeps(blaze)) {
                n++;
            }
        }
        return n;
    }

    private int countGateDroughtWildfires() {
        int n = 0;
        for (DroughtWildfireServant wildfire : level().getEntitiesOfClass(DroughtWildfireServant.class,
                getBoundingBox().inflate(64.0D))) {
            if (wildfire == this || gateKeeps(wildfire)) {
                n++;
            }
        }
        return n;
    }

    private boolean gateKeeps(AbstractJerotesServant other) {
        if (other == null || getTarget() == other || other.getTarget() == this) {
            return false;
        }
        return (getTrueOwner() == null && other.getTrueOwner() == null) || other.isAlliedTo(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide && amount > 0.0F && source.is(DamageTypeTags.IS_FIRE)) {
            fireAbsorptionSpell(amount);
        }
        if (isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= getHealth() * 100.0F
                && getShieldCount() > 0) {
            if (!level().isClientSide) {
                setShieldHealth(getShieldHealth() - amount);
            }
            if (amount > 0.0F) {
                playSound(jerotesCoreSound("twohanded_block", SoundEvents.SHIELD_BLOCK), 1.0F, 1.0F);
            }
            amount = 0.0F;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypes.LAVA) || super.isInvulnerableTo(source);
    }

    private void fireAbsorptionSpell(float amount) {
        if (fireAbsorptionCooldown > 0 && amount <= fireAbsorptionLastDamage) {
            return;
        }
        fireAbsorptionCooldown = FIRE_ABSORPTION_COOLDOWN_TICKS;
        fireAbsorptionLastDamage = amount;
        heal(amount * (FIRE_ABSORPTION_PERCENT / 100.0F));
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 2; ++i) {
                serverLevel.sendParticles(jerotesCoreParticle("flame", ParticleTypes.FLAME),
                        getRandomX(1.0D), getY(), getRandomZ(1.0D), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            if (!isInvisible()) {
                serverLevel.sendParticles(
                        jerotesCoreParticle("fire_absorption_display", ParticleTypes.FLAME),
                        getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (!isSilent()) {
            playSound(jerotesCoreSound("magic_fire_absorption", SoundEvents.FIRECHARGE_USE), 1.0F, 1.0F);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
        if (isSpecialAction() || getAttackTick() > 0) {
            setDeltaMovement(getDeltaMovement().multiply(0.005D, 1.0D, 0.005D));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            clearFire();
            if (!onGround() && getDeltaMovement().y < 0.0D) {
                setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
            }
            if (!isAggressive() && this.entityData.get(ANIM_STATE) == ANIM_SHOOT) {
                this.entityData.set(ANIM_TICK, 0);
                setAnimationState(ANIM_NONE);
                cancelSpellBurst();
            }
            if (isAlive()) {
            int animTick = this.entityData.get(ANIM_TICK);
            if (animTick > 0) {
                animTick--;
                this.entityData.set(ANIM_TICK, animTick);
                if (animTick <= 0) {
                    setAnimationState(ANIM_NONE);
                }
            }
            if (getAttackTick() <= ATTACK_TICK_FLOOR && isAlive() && getTarget() != null
                    && canAttack(getTarget())
                    && getAttackBoundingBox().intersects(getTarget().getBoundingBox())
                    && getSensing().hasLineOfSight(getTarget())) {
                swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                doHurtTarget(getTarget());
            }
            int attackTick = this.entityData.get(ATTACK_TICK);
            if (attackTick == 10) {
                trueHurt();
            }
            if (attackTick > 5 && attackTick <= 12 && this.entityData.get(ATTACK_USE) == 2
                    && isAlive() && isInWaterColumn()) {
                splashShockwave();
            }
            if (attackTick > ATTACK_TICK_FLOOR) {
                attackTick--;
                this.entityData.set(ATTACK_TICK, attackTick);
            }
            if (this.deferredCastTick > 0) {
                this.deferredCastTick--;
                if (this.deferredCastTick <= 0) {
                    LivingEntity pending = this.deferredCastTarget;
                    this.deferredCastTarget = null;
                    if (pending != null && pending.isAlive() && isAggressive()
                            && canAttack(pending) && !isSpecialAction() && getAttackTick() <= 0) {
                        doRangedCast(pending);
                    }
                }
            }
            int flameTick = this.entityData.get(FLAME_TICK);
            if (flameTick > 0) {
                flameTick--;
                this.entityData.set(FLAME_TICK, flameTick);
                if (flameTick <= FLAME_START_TICK && flameTick % FLAME_CAST_INTERVAL == 0) {
                    flameCone();
                }
                if (flameTick == 0) {
                    discardActiveFlameSnakes();
                }
            }
            int droughtBlazeTick = this.entityData.get(DROUGHT_BLAZE_TICK);
            if (droughtBlazeTick < DROUGHT_BLAZE_CHARGE_TICKS) {
                this.entityData.set(DROUGHT_BLAZE_TICK, droughtBlazeTick + 1);
            } else if (isAggressive() && !isSpecialAction()
                    && attackTick <= 0
                    && countGateDroughtBlazes() <= countGateDroughtWildfires() * 4) {
                this.entityData.set(ANIM_TICK, DROUGHT_BLAZE_ANIM_TICKS);
                setAnimationState(ANIM_DROUGHT_BLAZE);
                this.entityData.set(DROUGHT_BLAZE_TICK, 0);
                this.entityData.set(DROUGHT_BLAZE_USE_TICK, DROUGHT_BLAZE_ANIM_TICKS);
            }
            int droughtBlazeUse = this.entityData.get(DROUGHT_BLAZE_USE_TICK);
            if (droughtBlazeUse > 0) {
                droughtBlazeUse--;
                this.entityData.set(DROUGHT_BLAZE_USE_TICK, droughtBlazeUse);
                if (droughtBlazeUse == DROUGHT_BLAZE_SUMMON_TICK) {
                    summonDroughtBlazes();
                    castFeedback("magic_conjure_drought_blazes", SoundEvents.BLAZE_SHOOT,
                            "conjure_drought_blazes_display", ParticleTypes.FLAME, SPELL_LEVEL);
                    playSound(jerotesSound("drought_wildfire_drought_blaze", SoundEvents.BLAZE_SHOOT),
                            10.0F, 1.0F);
                }
            }
            tickSpellBurst();
            int shieldCd = this.entityData.get(SHIELD_ADD_COOLDOWN);
            if (shieldCd > 0) {
                this.entityData.set(SHIELD_ADD_COOLDOWN, shieldCd - 1);
            }
            if (fireAbsorptionCooldown > 0) {
                fireAbsorptionCooldown--;
            }
            if (isAlive() && random.nextInt(900) == 1) {
                heal(3.0F);
            }
            if (isAlive() && random.nextInt(900) == 1) {
                setShieldHealth(Math.min(shieldMaxHealth(), getShieldHealth() + 3.0F));
            }
            if (getShieldCount() < SHIELD_MAX_COUNT && shieldCd <= 0) {
                setShieldHealth(shieldMaxHealth());
                this.entityData.set(SHIELD_COUNT, getShieldCount() + 1);
                this.entityData.set(SHIELD_ADD_COOLDOWN, SHIELD_ADD_COOLDOWN_TICKS);
                shieldBurst(jerotesSound("drought_wildfire_regenerat", SoundEvents.BEACON_ACTIVATE));
            }
            if (getShieldCount() > 0 && getShieldHealth() <= 0.0F) {
                setShieldHealth(shieldMaxHealth());
                this.entityData.set(SHIELD_COUNT, getShieldCount() - 1);
                this.entityData.set(SHIELD_ADD_COOLDOWN, SHIELD_ADD_COOLDOWN_TICKS);
                shieldBurst(jerotesSound("drought_wildfire_break", SoundEvents.SHIELD_BREAK));
            }
            if (isAlive() && !isSpecialAction() && attackTick <= 0) {
                this.entityData.set(ROLL_ANIM, this.entityData.get(ROLL_ANIM) + 1);
                this.entityData.set(IDLE_ANIM, Math.min(10, this.entityData.get(IDLE_ANIM) + 1));
            } else if (this.entityData.get(IDLE_ANIM) == 0) {
                this.entityData.set(ROLL_ANIM, 0);
            } else {
                this.entityData.set(IDLE_ANIM, Math.max(0, this.entityData.get(IDLE_ANIM) - 1));
            }
            }
        } else {
            idle1AnimationState.startIfStopped(tickCount);
            if (isAlive()) {
                lastTickRenderTime = thisTickRenderTime;
                thisTickRenderTime = 0.0F;
            }
        }
    }

    private void shieldBurst(SoundEvent sound) {
        playSound(sound, 1.0F, 1.0F);
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; ++i) {
                serverLevel.sendParticles(ParticleTypes.LAVA, getRandomX(1.0D), getY(),
                        getRandomZ(1.0D), 1, 0.0D, -0.25D, 0.0D, 0.125D);
            }
        }
    }

    private void splashShockwave() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (int i = 0; i < 24; ++i) {
            double angle = Math.PI * 2.0D * i / 24.0D;
            double offsetX = Math.cos(angle) * 3.0D;
            double offsetZ = Math.sin(angle) * 3.0D;
            for (int k = 0; k < 2; ++k) {
                serverLevel.sendParticles(ParticleTypes.LAVA, getX() + offsetX, getY() - 0.05D,
                        getZ() + offsetZ, 0, 0.0D, 0.5D, 0.0D, 1.0D);
                serverLevel.sendParticles(ParticleTypes.LAVA, getX() + offsetX, getY() - 0.05D,
                        getZ() + offsetZ, 0, 0.0D, -0.5D, 0.0D, 1.0D);
            }
        }
    }

    private boolean isInWaterColumn() {
        if (isInWater()) {
            return true;
        }
        BlockPos onPos = getOnPos();
        for (int i = 0; i <= 4; ++i) {
            if (level().getFluidState(onPos.below(i)).is(Fluids.WATER)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        this.entityData.set(ANIM_TICK, 20);
        setAnimationState(ANIM_DEAD);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (!(getTrueOwner() instanceof Player player)) {
            return;
        }
        ItemStack helm = new ItemStack(GeneratedMod.DROUGHT_WILDFIRE_HELM.get());
        ReviveServantItem.setOwnerName(player, helm);
        ReviveServantItem.setSummon(this, helm);
        OwnerBoundLoot.deliver(this, helm, ParticleTypes.FLAME);
    }

    @Override
    public void tryKill(net.minecraft.world.entity.player.Player player) {
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
        tag.putInt("ShieldCount", getShieldCount());
        tag.putInt("ShieldAddCooldown", this.entityData.get(SHIELD_ADD_COOLDOWN));
        tag.putFloat("ShieldHealth", getShieldHealth());
        tag.putInt("IdleAnim", this.entityData.get(IDLE_ANIM));
        tag.putInt("RollAnim", this.entityData.get(ROLL_ANIM));
        tag.putInt("AttackTick", this.entityData.get(ATTACK_TICK));
        tag.putInt("AttackUse", this.entityData.get(ATTACK_USE));
        tag.putInt("FlameTick", this.entityData.get(FLAME_TICK));
        tag.putInt("DroughtBlazeTick", this.entityData.get(DROUGHT_BLAZE_TICK));
        tag.putInt("DroughtBlazeUseTick", this.entityData.get(DROUGHT_BLAZE_USE_TICK));
        tag.putInt("FireAbsorptionCooldown", this.fireAbsorptionCooldown);
        tag.putFloat("FireAbsorptionLastDamage", this.fireAbsorptionLastDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(SHIELD_COUNT, tag.getInt("ShieldCount"));
        this.entityData.set(SHIELD_ADD_COOLDOWN, tag.getInt("ShieldAddCooldown"));
        this.entityData.set(SHIELD_HEALTH, tag.getFloat("ShieldHealth"));
        this.entityData.set(IDLE_ANIM, tag.getInt("IdleAnim"));
        this.entityData.set(ROLL_ANIM, tag.getInt("RollAnim"));
        if (tag.contains("AttackTick")) {
            this.entityData.set(ATTACK_TICK, tag.getInt("AttackTick"));
        }
        if (tag.contains("AttackUse")) {
            this.entityData.set(ATTACK_USE, tag.getInt("AttackUse"));
        }
        if (tag.contains("FlameTick")) {
            this.entityData.set(FLAME_TICK, tag.getInt("FlameTick"));
        }
        if (tag.contains("DroughtBlazeTick")) {
            this.entityData.set(DROUGHT_BLAZE_TICK, tag.getInt("DroughtBlazeTick"));
        }
        if (tag.contains("DroughtBlazeUseTick")) {
            this.entityData.set(DROUGHT_BLAZE_USE_TICK, tag.getInt("DroughtBlazeUseTick"));
        }
        this.fireAbsorptionCooldown = tag.getInt("FireAbsorptionCooldown");
        this.fireAbsorptionLastDamage = tag.getFloat("FireAbsorptionLastDamage");
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType reason, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
        this.entityData.set(SHIELD_COUNT, SHIELD_MAX_COUNT);
        setShieldHealth(shieldMaxHealth());
        if (!level.isClientSide()
                && (reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.MOB_SUMMONED
                        || reason == MobSpawnType.COMMAND || reason == MobSpawnType.DISPENSER
                        || reason == MobSpawnType.STRUCTURE || reason == MobSpawnType.EVENT)) {
            castFeedback("magic_conjure_drought_wildfire", SoundEvents.BLAZE_SHOOT,
                    "conjure_drought_wildfire_display", ParticleTypes.FLAME,
                    CONJURE_WILDFIRE_SPELL_LEVEL);
            conjureBurst();
        }
        return result;
    }

    private void conjureBurst() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (int i = 0; i < 24; i++) {
            double angle = Math.PI * 2.0D * i / 24.0D;
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    getX() + Math.cos(angle) * 0.9D, getY() + 0.15D, getZ() + Math.sin(angle) * 0.9D,
                    1, 0.0D, 0.08D, 0.0D, 0.02D);
        }
        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(1.0D), getZ(),
                30, 0.45D, 0.9D, 0.45D, 0.05D);
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY(0.4D), getZ(),
                12, 0.4D, 0.3D, 0.4D, 0.02D);
    }

    @Override
    public void equipDefaultGear(net.minecraft.util.RandomSource random) {
    }
}
