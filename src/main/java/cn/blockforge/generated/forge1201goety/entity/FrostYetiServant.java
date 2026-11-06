package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.FrostYetiServant;
import cn.blockforge.generated.goetysupreme.model.FrostYetiServantModel;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.compat.JvillageIceRockProjectile;

public class FrostYetiServant extends AbstractJerotesServant {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();
    public final AnimationState throwAnimationState = new AnimationState();
    public final AnimationState deterAnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANIM_TICK =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_THROW_TICK =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_DETER_TICK =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_JUMP_TICK =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SHOULD_DETER =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_YETI_RUSH =
            SynchedEntityData.defineId(FrostYetiServant.class, EntityDataSerializers.BOOLEAN);

    private static final java.util.UUID RUSH_SPEED_UUID = java.util.UUID.fromString("c237172f-7a44-460d-89a4-c9115ee0a48d");

    private static final int ICE_ROCK_SPELL_LEVEL = 3;

    private static final float ICE_ROCK_SPEED = 1.6F;

    private static final int DETER_SPELL_LEVEL = 3;

    private static final double DETER_RADIUS = DETER_SPELL_LEVEL * 6.0D;

    private static final int DETER_MAIN_EFFECT_SECONDS = DETER_SPELL_LEVEL;

    private static final int DETER_TICK_TOTAL = 400;

    private static final int DETER_ATTACK_LOCK_TICKS = 360;

    private static final int DETER_RANGED_WINDOW_TICKS = 320;

    public FrostYetiServant(EntityType<? extends FrostYetiServant> type, Level level) {
        super(type, level);
        this.xpReward = 50;
        this.setMaxUpStep(1.1F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 128.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2D)
                .add(Attributes.ATTACK_DAMAGE, 14.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_STATE, 0);
        this.entityData.define(DATA_ANIM_TICK, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_THROW_TICK, 0);
        this.entityData.define(DATA_DETER_TICK, 0);
        this.entityData.define(DATA_JUMP_TICK, 0);
        this.entityData.define(DATA_SHOULD_DETER, false);
        this.entityData.define(DATA_YETI_RUSH, false);
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new FrostYetiMeleeGoal(this, 1.2D, true));
        this.goalSelector.addGoal(1, new FrostYetiRangedGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/frost_yeti.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.FROST_YETI;
    }

    @Override
    public void equipDefaultGear(net.minecraft.util.RandomSource random) {
    }

    public AABB getAttackBoundingBox() {
        AABB box = this.getBoundingBox();
        Entity vehicle = this.getVehicle();
        if (vehicle != null) {
            AABB vehicleBox = vehicle.getBoundingBox();
            box = new AABB(
                    Math.min(box.minX, vehicleBox.minX), box.minY, Math.min(box.minZ, vehicleBox.minZ),
                    Math.max(box.maxX, vehicleBox.maxX), box.maxY, Math.max(box.maxZ, vehicleBox.maxZ));
        }
        double horizontal = Math.sqrt(2.04D) - 0.6D;
        return box.inflate(horizontal, 0.0D, horizontal).inflate(0.75D, 0.75D, 0.75D);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return this.getAttackBoundingBox().intersects(target.getBoundingBox());
    }

    private boolean inFrontOf(LivingEntity target) {
        float headYaw = -this.getYHeadRot() * ((float) Math.PI / 180.0F);
        Vec3 look = new Vec3(Mth.sin(headYaw), 0.0D, Mth.cos(headYaw));
        Vec3 toSelf = this.position().subtract(target.getPosition(0.5F));
        Vec3 flat = new Vec3(toSelf.x, 0.0D, toSelf.z);
        if (flat.lengthSqr() < 1.0E-6D) {
            return true;
        }
        return flat.normalize().dot(look) < 0.0D;
    }

    private void sweepAttackParticle() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        double dx = -Mth.sin(this.getYRot() * 0.017453292F);
        double dz = Mth.cos(this.getYRot() * 0.017453292F);
        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                this.getX() + dx, this.getY(0.5D), this.getZ() + dz, 0, dx, 0.0D, dz, 0.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.getAttackTick() > -10) {
            return false;
        }
        if (this.getDeterTick() >= DETER_ATTACK_LOCK_TICKS) {
            return false;
        }
        if (this.level().isClientSide) {
            return true;
        }
        this.setAttackTick(0);
        int roll = this.getRandom().nextInt(30);
        if (roll > 20) {
            this.setAnimTick(12);
            this.setAnimationState("attack1");
        } else if (roll > 10) {
            this.setAnimTick(15);
            this.setAttackTick(15);
            this.setAnimationState("attack2");
            return true;
        } else {
            this.setAnimTick(12);
            this.setAnimationState("attack3");
        }
        return this.instantMelee(target);
    }

    private boolean instantMelee(Entity target) {
        if (!(target instanceof LivingEntity living)) {
            return false;
        }
        DamageSource freezeSource = JerotesMagicDamage.freezeMelee(this, this);
        boolean hit;
        if (this.getRandom().nextInt(3) == 0 && !living.isInvulnerableTo(freezeSource)) {
            living.setIsInPowderSnow(true);
            if (living.getTicksFrozen() < 240) {
                living.setTicksFrozen(240);
            }
            hit = this.hitOnce(living, freezeSource, 1.0F);
            if (hit) {
                this.playAttackSound(1.0F);
                this.applyRampantFromWeakness(living);
                this.sweepAround(living, freezeSource, 1.0F);
                this.sweepAttackParticle();
            }
        } else {
            hit = super.doHurtTarget(living);
            if (hit) {
                this.playAttackSound(1.0F);
                this.applyRampantFromWeakness(living);
                this.sweepAround(living, this.damageSources().mobAttack(this), 0.5F);
                this.sweepAttackParticle();
            }
        }
        return hit;
    }

    private void sweepAround(LivingEntity center, DamageSource source, float damageMulti) {
        AABB area = center.getBoundingBox().inflate(0.5D, 0.2D, 0.5D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || victim == center || !victim.isAlive()
                    || !this.canAttack(victim) || this.isAlliedTo(victim)) {
                continue;
            }
            if (!this.hasLineOfSight(victim)) {
                continue;
            }
            if (this.hitOnce(victim, source, damageMulti)) {
                this.applyRampantFromWeakness(victim);
            }
        }
    }

    private boolean hitOnce(LivingEntity victim, DamageSource source, float damageMulti) {
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMulti;
        if (damage <= 0.0F || !victim.hurt(source, damage)) {
            return false;
        }
        double knockback = this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * damageMulti;
        if (knockback > 0.0D) {
            victim.knockback(knockback,
                    Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F)),
                    -Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F)));
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        }
        return true;
    }

    private void playAttackSound(float volume) {
        if (!this.isSilent()) {
            this.playSound(jerotesSound("frost_yeti_attack", SoundEvents.POLAR_BEAR_WARNING), volume, 1.0F);
        }
    }

    public boolean trueHurt() {
        if (this.getDeterTick() >= DETER_ATTACK_LOCK_TICKS) {
            return false;
        }
        this.playAttackSound(2.0F);
        DamageSource freezeSource = JerotesMagicDamage.freezeMelee(this, this);
        AABB box = this.getAttackBoundingBox().inflate(0.5D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim == this || !victim.isAlive() || !this.canAttack(victim) || this.isAlliedTo(victim)) {
                continue;
            }
            if (!this.hasLineOfSight(victim) || !this.inFrontOf(victim)) {
                continue;
            }
            DamageSource source = victim.isInvulnerableTo(freezeSource)
                    ? this.damageSources().mobAttack(this) : freezeSource;
            if (this.hitOnce(victim, source, 1.0F)) {
                this.applyRampantFromWeakness(victim);
            }
        }
        this.sweepAttackParticle();
        return true;
    }

    public void performDeter() {
        if (this.level().isClientSide) {
            return;
        }
        this.showSpellDisplay("yeti_deter_display", ParticleTypes.SNOWFLAKE);
        int seconds = DETER_MAIN_EFFECT_SECONDS;   // 3
        int amplifier = DETER_SPELL_LEVEL - 1;     // 2 → 黑暗 / 虚弱 / 缓慢都是 III 级
        AABB area = this.getBoundingBox().inflate(DETER_RADIUS, DETER_RADIUS, DETER_RADIUS);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || !victim.isAlive() || this.isAlliedTo(victim) || !this.canAttack(victim)) {
                continue;
            }
            if (victim instanceof FrostYetiServant) {
                continue;
            }
            if (!this.inFrontOf(victim) || !this.hasLineOfSight(victim)) {
                continue;
            }
            if (victim.getHealth() >= this.getHealth()) {
                continue;
            }
            this.applyDeterEffects(victim, seconds, amplifier);
        }
        LivingEntity target = this.getTarget();
        if (target != null && target != this && target.getHealth() < this.getHealth()
                && !wearsFreezeImmuneWearables(target)) {
            this.applyDeterEffects(target, seconds, amplifier);
        }
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * seconds, 0), this);
        if (!this.isSilent()) {
            this.playSound(jerotesSound("frost_yeti_deter", SoundEvents.POLAR_BEAR_WARNING),
                    15.0F, 0.8F + this.getRandom().nextFloat() * 0.4F);
        }
    }

    private void applyDeterEffects(LivingEntity victim, int seconds, int amplifier) {
        victim.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * seconds, amplifier), this);
        victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * seconds * 6, amplifier), this);
        victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * seconds * 3, amplifier), this);
    }

    private static boolean wearsFreezeImmuneWearables(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES);
    }

    private void applyRampantFromWeakness(LivingEntity victim) {
        if (this.level().isClientSide) {
            return;
        }
        MobEffectInstance weakness = victim.getEffect(MobEffects.WEAKNESS);
        if (weakness == null) {
            return;
        }
        MobEffect rampant = jerotesVillageEffect("rampant");
        if (rampant != null) {
            this.addEffect(new MobEffectInstance(rampant, weakness.getDuration(), weakness.getAmplifier()), this);
        }
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (!this.level().isClientSide) {
            this.setThrowTick(100);
            this.setAnimTick(13);
            this.setAnimationState("throw");
        }
        this.showSpellDisplay("ice_rock_throw_display", ParticleTypes.SNOWFLAKE);
        Projectile iceRock = JvillageIceRockProjectile.spawn(this.level(), this, target,
                ICE_ROCK_SPELL_LEVEL,
                6 + (ICE_ROCK_SPELL_LEVEL - 1) * 3,
                ICE_ROCK_SPEED,
                Math.max(0, 3 - ICE_ROCK_SPELL_LEVEL));
        if (iceRock != null) {
            this.level().addFreshEntity(iceRock);
        } else {
            HarmfulSnowball snowball = new HarmfulSnowball(this.level(), this,
                    (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75F);
            double dx = target.getX() - this.getX();
            double dy = target.getEyeY() - snowball.getY() - 0.4D;
            double dz = target.getZ() - this.getZ();
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            snowball.shoot(dx, dy + horizontal * 0.12D, dz, 1.4F, 2.0F);
            this.level().addFreshEntity(snowball);
        }
        if (!this.isSilent()) {
            this.playSound(jerotesSound("magic_ice_rock_throw", SoundEvents.SNOWBALL_THROW), 2.0F, 1.0F);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
            this.heal(3.0F);
        }
        if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
        }
        if (!this.horizontalCollision && this.getJumpTick() > 0) {
            if (!this.level().isClientSide) {
                this.setJumpTick(this.getJumpTick() - 1);
            }
        }
        if (this.horizontalCollision) {
            if (!this.level().isClientSide) {
                this.setJumpTick(this.getJumpTick() + 1);
            }
        }
        if (this.horizontalCollision && this.getJumpTick() < 40 && this.onGround()) {
            this.setDeltaMovement(0.1D, 0.8D, 0.1D);
            if (!this.level().isClientSide) {
                this.setJumpTick(0);
            }
        }
        if (!this.level().isClientSide) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
            if (this.getAnimTick() == 0) {
                this.setAnimationState("");
            }
            this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
            this.setThrowTick(Math.max(0, this.getThrowTick() - 1));
            this.setDeterTick(Math.max(0, this.getDeterTick() - 1));
            if (this.getAttackTick() == 5 && this.isAlive()) {
                this.trueHurt();
            }
            if (this.getAttackTick() > 10) {
                LivingEntity lookTarget = this.getTarget();
                if (lookTarget != null) {
                    this.getLookControl().setLookAt(lookTarget, 360.0F, 360.0F);
                    this.lookAt(lookTarget, 360.0F, 360.0F);
                }
            }
            if (this.getAttackTick() > 0) {
                this.getNavigation().stop();
            }
            if (this.isAggressive() && this.getDeterTick() <= 0 && this.isShouldDeter() && this.getTarget() != null) {
                this.setShouldDeter(false);
                this.setDeterTick(DETER_TICK_TOTAL);
                this.setAnimTick(40);
                this.setAnimationState("deter");
                this.performDeter();
            }
            if (this.getTarget() == null && !this.isAggressive() && !this.isShouldDeter()) {
                this.setShouldDeter(true);
            }
            if (this.getDeterTick() >= DETER_ATTACK_LOCK_TICKS) {
                this.getNavigation().stop();
            }
            LivingEntity target = this.getTarget();
            if (target != null && this.getDeterTick() < DETER_ATTACK_LOCK_TICKS) {
                boolean rush = !(this.distanceTo(target) < 24.0F && !this.horizontalCollision);
                if (rush != this.isYetiRush()) {
                    this.setYetiRush(rush);
                }
            } else if (this.isYetiRush()) {
                this.setYetiRush(false);
            }
        }
        if (this.isAlive()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public void die(DamageSource source) {
        if (this.deathTime <= 0 && !this.level().isClientSide) {
            this.setAnimTick(20);
            this.setAnimationState("dead");
        }
        super.die(source);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (!(getTrueOwner() instanceof Player)) {
            return;
        }
        ItemStack hair = jerotesItem("frost_yeti_hair");
        if (!hair.isEmpty()) {
            net.minecraft.world.entity.item.ItemEntity dropped = this.spawnAtLocation(hair);
            if (dropped != null) {
                dropped.setGlowingTag(true);
            }
        }
    }

    private static final float COLD_ABSORPTION_PERCENTAGE = 80.0F;

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FREEZING)
                || source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castColdAbsorption(source, amount, COLD_ABSORPTION_PERCENTAGE);
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return super.hurt(source, amount * 0.5F);
        }
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return super.hurt(source, amount * 2.0F);
        }
        return super.hurt(source, amount);
    }

    @Override
    public int calculateFallDamage(float fallDistance, float multiplier) {
        return super.calculateFallDamage(fallDistance, multiplier) - 12;
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
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.95F;
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("frost_yeti_ambient", SoundEvents.POLAR_BEAR_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("frost_yeti_hurt", SoundEvents.POLAR_BEAR_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("frost_yeti_death", SoundEvents.POLAR_BEAR_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        if (this.isYetiRush()) {
            this.playSound(jerotesSound("frost_yeti_rush", SoundEvents.POLAR_BEAR_STEP), 0.15F, 1.0F);
        } else {
            super.playStepSound(pos, state);
        }
    }

    public void setAnimTick(int value) {
        this.entityData.set(DATA_ANIM_TICK, value);
    }

    public int getAnimTick() {
        return this.entityData.get(DATA_ANIM_TICK);
    }

    public void setAttackTick(int value) {
        this.entityData.set(DATA_ATTACK_TICK, value);
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setThrowTick(int value) {
        this.entityData.set(DATA_THROW_TICK, value);
    }

    public int getThrowTick() {
        return this.entityData.get(DATA_THROW_TICK);
    }

    public void setDeterTick(int value) {
        this.entityData.set(DATA_DETER_TICK, value);
    }

    public int getDeterTick() {
        return this.entityData.get(DATA_DETER_TICK);
    }

    public boolean isShouldDeter() {
        return this.entityData.get(DATA_SHOULD_DETER);
    }

    public void setShouldDeter(boolean value) {
        this.entityData.set(DATA_SHOULD_DETER, value);
    }

    public boolean isYetiRush() {
        return this.entityData.get(DATA_YETI_RUSH) && this.isAlive();
    }

    public void setYetiRush(boolean value) {
        this.entityData.set(DATA_YETI_RUSH, value);
        this.setMaxUpStep(value ? 3.1F : 1.1F);
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }
        speed.removeModifier(RUSH_SPEED_UUID);
        if (value) {
            speed.addTransientModifier(new AttributeModifier(RUSH_SPEED_UUID, "Rush movement speed",
                    0.1D, AttributeModifier.Operation.ADDITION));
        }
    }

    public void setJumpTick(int value) {
        this.entityData.set(DATA_JUMP_TICK, value);
    }

    public int getJumpTick() {
        return this.entityData.get(DATA_JUMP_TICK);
    }

    public void setAnimationState(String name) {
        int id = animationId(name);
        if (this.level().isClientSide) {
            this.applyAnimation(id);
        } else {
            this.entityData.set(DATA_ANIM_STATE, id);
        }
    }

    private static int animationId(String name) {
        return switch (name) {
            case "attack1" -> 1;
            case "attack2" -> 2;
            case "attack3" -> 3;
            case "throw" -> 4;
            case "deter" -> 5;
            case "dead" -> 6;
            default -> 0;
        };
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_ANIM_STATE.equals(key) && this.level().isClientSide) {
            this.applyAnimation(this.entityData.get(DATA_ANIM_STATE));
        }
    }

    private void applyAnimation(int id) {
        this.stopAllAnimation();
        switch (id) {
            case 1 -> this.attack1AnimationState.startIfStopped(this.tickCount);
            case 2 -> this.attack2AnimationState.startIfStopped(this.tickCount);
            case 3 -> this.attack3AnimationState.startIfStopped(this.tickCount);
            case 4 -> this.throwAnimationState.startIfStopped(this.tickCount);
            case 5 -> this.deterAnimationState.startIfStopped(this.tickCount);
            case 6 -> this.deadAnimationState.startIfStopped(this.tickCount);
            default -> {
            }
        }
    }

    private void stopAllAnimation() {
        this.attack1AnimationState.stop();
        this.attack2AnimationState.stop();
        this.attack3AnimationState.stop();
        this.throwAnimationState.stop();
        this.deterAnimationState.stop();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AnimTick", this.getAnimTick());
        tag.putInt("AttackTick", this.getAttackTick());
        tag.putInt("ThrowTick", this.getThrowTick());
        tag.putInt("DeterTick", this.getDeterTick());
        tag.putInt("JumpTick", this.getJumpTick());
        tag.putBoolean("ShouldDeter", this.isShouldDeter());
        tag.putBoolean("IsYetiRush", this.isYetiRush());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAnimTick(tag.getInt("AnimTick"));
        this.setAttackTick(tag.getInt("AttackTick"));
        this.setThrowTick(tag.getInt("ThrowTick"));
        this.setDeterTick(tag.getInt("DeterTick"));
        this.setJumpTick(tag.getInt("JumpTick"));
        this.setShouldDeter(tag.getBoolean("ShouldDeter"));
        this.setYetiRush(tag.getBoolean("IsYetiRush"));
    }

    private static final class FrostYetiMeleeGoal extends Goal {

        private final FrostYetiServant mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        @Nullable
        private net.minecraft.world.level.pathfinder.Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;

        private FrostYetiMeleeGoal(FrostYetiServant mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            long now = this.mob.level().getGameTime();
            if (now - this.lastCanUseCheck < 20L) {
                return false;
            }
            this.lastCanUseCheck = now;
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            this.path = this.mob.getNavigation().createPath(target, 0);
            return this.path != null || this.mob.getAttackBoundingBox().intersects(target.getBoundingBox());
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            }
            if (!this.mob.isWithinRestriction(target.blockPosition())) {
                return false;
            }
            if (this.mob.getThrowTick() <= 0 && this.mob.getDeterTick() > 0
                    && this.mob.getDeterTick() <= DETER_RANGED_WINDOW_TICKS) {
                return false;
            }
            return !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative());
        }

        @Override
        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
        }

        @Override
        public void stop() {
            LivingEntity target = this.mob.getTarget();
            if (target != null && !net.minecraft.world.entity.EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                this.mob.setTarget(null);
            }
            this.mob.setAggressive(false);
            if (this.mob.getTarget() == null) {
                this.mob.getNavigation().stop();
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return;
            }
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            boolean seen = this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target);
            if (seen && this.ticksUntilNextPathRecalculation <= 0
                    && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
                            || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D
                            || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = target.getX();
                this.pathedTargetY = target.getY();
                this.pathedTargetZ = target.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                double distance = this.mob.distanceToSqr(target);
                if (distance > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (distance > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            if (this.ticksUntilNextAttack <= 0 && this.mob.getAttackBoundingBox().intersects(target.getBoundingBox())
                    && this.mob.getSensing().hasLineOfSight(target)) {
                this.ticksUntilNextAttack = this.adjustedTickDelay(20);
                this.mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
            }
        }
    }

    private static final class FrostYetiRangedGoal extends Goal {

        private final FrostYetiServant mob;
        private final double speedModifier;
        private final int attackInterval;
        private final float attackRadiusSqr;
        @Nullable
        private LivingEntity target;
        private int attackTime = -1;
        private int seeTime;

        private FrostYetiRangedGoal(FrostYetiServant mob, double speedModifier, int attackInterval, float attackRadius) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.attackInterval = attackInterval;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (this.mob.getThrowTick() > 0) {
                return false;
            }
            if (this.mob.getDeterTick() <= 0 || this.mob.getDeterTick() > DETER_RANGED_WINDOW_TICKS) {
                return false;
            }
            if (this.mob.distanceToSqr(target) < 12.0D) {
                return false;
            }
            this.target = target;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return (this.canUse() || (this.target != null && this.target.isAlive() && this.mob.getThrowTick() <= 0
                    && !this.mob.getNavigation().isDone()));
        }

        @Override
        public void start() {
            this.mob.setAggressive(true);
            this.seeTime = 0;
            this.attackTime = -1;
        }

        @Override
        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.attackTime = -1;
        }

        @Override
        public void tick() {
            if (this.target == null) {
                return;
            }
            double distance = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean seen = this.mob.getSensing().hasLineOfSight(this.target);
            this.seeTime = seen ? this.seeTime + 1 : 0;
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (--this.attackTime == 0) {
                if (!seen) {
                    return;
                }
                float factor = Mth.clamp((float) Math.sqrt(distance) / (float) Math.sqrt(this.attackRadiusSqr), 0.1F, 1.0F);
                this.mob.performRangedAttack(this.target, factor);
                this.attackTime = this.attackInterval;
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distance) / Math.sqrt(this.attackRadiusSqr),
                        this.attackInterval, this.attackInterval));
            }
        }
    }
}
