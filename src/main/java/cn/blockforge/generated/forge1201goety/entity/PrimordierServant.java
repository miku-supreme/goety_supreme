package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.PrimordierServant;
import cn.blockforge.generated.goetysupreme.model.PrimordierServantModel;

public class PrimordierServant extends AbstractJerotesServant {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();
    public final AnimationState attack4AnimationState = new AnimationState();
    public final AnimationState attack5AnimationState = new AnimationState();
    public final AnimationState attack6AnimationState = new AnimationState();
    public final AnimationState attack7AnimationState = new AnimationState();
    public final AnimationState attack8AnimationState = new AnimationState();
    public final AnimationState shockAnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    private static final TagKey<EntityType<?>> NO_SPECIAL_KNOCKBACK =
            TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath("jerotes", "no_special_knockback"));

    private static final int MAX_LIFTED_BLOCKS = 96;

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANIM_TICK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_USE =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_WALK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SHOCK_TICK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SHOCK_USE_TICK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SHOULD_SHOCK =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHOCK_JUMP =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_JUMP_ANIM =
            SynchedEntityData.defineId(PrimordierServant.class, EntityDataSerializers.INT);

    public float rushFindXRot;
    public float rushFindYRot;

    public PrimordierServant(EntityType<? extends PrimordierServant> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.1F);
        this.xpReward = 100;
        this.moveControl = new GiantMoveControl(this);
        this.lookControl = new GiantLookControl(this, 1);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 320.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 25.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_STATE, 0);
        this.entityData.define(DATA_ANIM_TICK, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_ATTACK_USE, 0);
        this.entityData.define(DATA_ATTACK_WALK, 0);
        this.entityData.define(DATA_SHOCK_TICK, 600);
        this.entityData.define(DATA_SHOCK_USE_TICK, 0);
        this.entityData.define(DATA_SHOULD_SHOCK, true);
        this.entityData.define(DATA_SHOCK_JUMP, false);
        this.entityData.define(DATA_JUMP_ANIM, 0);
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new PrimordierMeleeGoal(this, 1.6D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/primordier.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.PRIMORDIER;
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
        return box.inflate(horizontal, 0.0D, horizontal).inflate(1.5D, 1.5D, 1.5D);
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

    public boolean specialAction() {
        return this.getShockUseTick() > 0 || this.isShockJump();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.isAggressive() && !this.specialAction() && this.getShockTick() >= 600
                && this.isShouldShock() && this.getTarget() instanceof LivingEntity) {
            return false;
        }
        if (this.getAttackTick() > -10 || this.specialAction()) {
            return false;
        }
        if (!this.level().isClientSide) {
            int roll = this.getRandom().nextInt(40);
            if (roll > 35) {
                this.setAnimTick(60);
                this.setAttackTick(50);
                this.setAnimationState("attack1");
                this.setAttackUse(1);
            } else if (roll > 30) {
                this.setAnimTick(40);
                this.setAttackTick(42);
                this.setAnimationState("attack2");
                this.setAttackUse(2);
            } else if (roll > 25) {
                this.setAnimTick(40);
                this.setAttackTick(42);
                this.setAnimationState("attack3");
                this.setAttackUse(2);
            } else if (roll > 20) {
                this.setAnimTick(50);
                this.setAttackTick(40);
                this.setAnimationState("attack4");
                this.setAttackUse(2);
            } else if (roll > 15) {
                this.setAnimTick(50);
                this.setAttackTick(40);
                this.setAnimationState("attack5");
                this.setAttackUse(2);
            } else if (roll > 10) {
                this.setAnimTick(60);
                this.setAttackTick(50);
                this.setAnimationState("attack6");
                this.setAttackUse(4);
            } else if (roll > 5) {
                this.setAnimTick(40);
                this.setAttackTick(40);
                this.setAnimationState("attack7");
                this.setAttackUse(3);
            } else {
                this.setAnimTick(40);
                this.setAttackTick(40);
                this.setAnimationState("attack8");
                this.setAttackUse(3);
            }
        }
        this.getNavigation().stop();
        return true;
    }

    public boolean trueHurt() {
        float damageMulti = 1.0F;
        float knockbackMulti = 1.0F;
        float reach = 0.5F;
        if (this.getAttackUse() == 1) {
            damageMulti = 1.75F;
            reach = 0.75F;
        }
        if (this.getAttackUse() == 2) {
            knockbackMulti = 1.5F;
        }
        if (this.getAttackUse() == 3) {
            damageMulti = 1.5F;
            knockbackMulti = 1.25F;
            reach = 1.25F;
        }
        if (this.getAttackUse() == 4) {
            damageMulti = 1.75F;
            knockbackMulti = 1.25F;
            reach = 0.75F;
        }
        if (!this.isSilent()) {
            this.playSound(jerotesSound("primordier_attack", SoundEvents.RAVAGER_ATTACK), 5.0F, 1.0F);
        }
        float baseDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double attackKnockback = this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        AABB box = this.getAttackBoundingBox().inflate(reach);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim == this || !victim.isAlive() || !this.canAttack(victim) || this.isAlliedTo(victim)) {
                continue;
            }
            if (victim instanceof PrimordierServant other
                    && other.getTarget() != this && this.getTarget() != other) {
                continue;
            }
            if (this.distanceToSqr(victim) > 128.0D) {
                continue;
            }
            if (!this.hasLineOfSight(victim)) {
                continue;
            }
            if (!this.inFrontOf(victim) && this.getAttackUse() != 1 && this.getAttackUse() != 3) {
                continue;
            }
            if (victim.hurt(this.damageSources().mobAttack(this), baseDamage * damageMulti)) {
                if (attackKnockback > 0.0D) {
                    victim.knockback(attackKnockback * knockbackMulti,
                            Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F)),
                            -Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                }
                if (!this.level().isClientSide) {
                    if (victim.getTicksFrozen() < 280) {
                        victim.setTicksFrozen(280);
                    }
                    victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1), this);
                    victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, 2), this);
                }
                if (isKnockbackSize(victim) && !victim.getType().is(NO_SPECIAL_KNOCKBACK)) {
                    double resistance = 0.0D;
                    if (victim.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                        resistance = Math.max(
                                victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
                    }
                    double d3 = Math.max(0.0D, 1.0D - resistance) + 0.4D;
                    victim.setOnGround(false);
                    victim.setDeltaMovement(victim.getDeltaMovement().add(
                            -((this.getX() - victim.getX()) * d3 / 2.0D) * (0.7D + knockbackMulti / 3.0D),
                            -((this.getY() - victim.getY()) * d3 / 2.0D) * (0.7D + knockbackMulti / 3.0D),
                            -((this.getZ() - victim.getZ()) * d3 / 2.0D) * (0.7D + knockbackMulti / 3.0D)));
                }
            }
        }
        this.attackUseBurst();
        this.sweepAttackParticle();
        return true;
    }

    private static boolean isKnockbackSize(LivingEntity entity) {
        return mobSizeFind(entity, 0.75F, 1.75F)
                || mobSizeFind(entity, 1.75F, 3.5F)
                || mobSizeFind(entity, 3.5F, 6.0F);
    }

    private static boolean mobSizeFind(LivingEntity entity, float average, float max) {
        float width = entity.getBbWidth();
        float height = entity.getBbHeight();
        return !(width >= max || height >= max || (width >= average && height >= average));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
            this.heal(5.0F);
        }
        if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
        }
        if (!this.level().isClientSide && this.walkAnimation.isMoving() && this.onGround()
                && this.getHealth() > this.getMaxHealth() / 4.0F) {
            float cycle = (float) Math.cos(this.walkAnimation.position() * 0.8F - 1.5F);
            if (Math.abs(cycle) < 0.2F) {
                this.applyQuakeIn(this.getBoundingBox().inflate(8.0D));
            }
        }
        if (!this.level().isClientSide) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
            if (this.getAnimTick() == 0) {
                this.setAnimationState("");
            }
            this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
            if (this.getAttackTick() == 20 && this.isAlive()) {
                this.trueHurt();
            }
            LivingEntity aiTarget = this.getTarget();
            if (aiTarget != null && this.getAttackTick() <= -10) {
                this.getLookControl().setLookAt(aiTarget, 360.0F, 360.0F);
                this.lookAt(aiTarget, 360.0F, 360.0F);
            }
            if (this.getAttackTick() > 0 || (aiTarget != null && this.isWithinMeleeAttackRange(aiTarget))) {
                this.setAttackWalk(Math.min(this.getAttackWalk() + 1, 40));
                if (this.getAttackTick() > 0) {
                    int use = this.getAttackUse();
                    if (use == 4 || use == 3 || use == 1) {
                        this.getNavigation().stop();
                    }
                    this.setAttackWalk(Math.min(this.getAttackWalk() + 1, 40));
                }
            } else {
                this.setAttackWalk(Math.max(this.getAttackWalk() - 1, 0));
            }
            this.tickShock();
        }
        if (this.isAlive()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
        if (this.isShockJump() && (this.getTarget() == null || this.getTarget().distanceToSqr(this) > 16.0D)) {
            this.setJumpAnim(Math.min(this.getJumpAnim() + 1, 20));
        } else {
            this.setJumpAnim(Math.max(this.getJumpAnim() - 2, 0));
        }
    }

    private void tickShock() {
        LivingEntity target = this.getTarget();
        if (this.isAggressive() && this.getAttackTick() <= -10 && !this.specialAction()
                && this.getShockTick() >= 600 && this.isShouldShock() && target != null) {
            this.setShouldShock(false);
            this.setShockTick(0);
            this.setShockUseTick(120);
            this.setAnimTick(60);
            this.setAnimationState("shock");
            this.strikeDisplayBurst();
        }
        if (!this.isAggressive() && target == null && !this.isShouldShock()) {
            this.setShouldShock(true);
        }
        if (this.getShockUseTick() > 0) {
            if (this.getShockUseTick() == 85 && !this.isSilent()) {
                this.playSound(jerotesSound("primordier_shock", SoundEvents.RAVAGER_ROAR), 5.0F, 1.0F);
            }
            if (this.getShockUseTick() >= 75 && this.getShockUseTick() < 90) {
                if (this.getShockUseTick() == 89) {
                    this.strikeDisplayBurst();
                }
                if (target != null) {
                    this.getLookControl().setLookAt(target, 360.0F, 360.0F);
                    this.lookAt(target, 360.0F, 360.0F);
                }
                this.breathBurst();
                this.applyBreathFreezeAndQuake();
            }
            if (this.getShockUseTick() >= 75 && target != null) {
                this.getNavigation().setSpeedModifier(0.1D);
                this.rushFindXRot = this.getXRot();
                this.rushFindYRot = this.getYRot();
                this.lookAt(target, 360.0F, 360.0F);
            }
            if (this.getShockUseTick() == 73 && target != null) {
                this.getNavigation().stop();
                this.setShockJump(true);
                this.setAttackUse(3);
                this.trueHurt();
                this.jumpToTarget(target);
            }
        }
        if (this.isShockJump() && this.getShockUseTick() < 60
                && (this.onGround() || this.getShockUseTick() <= 0)) {
            if (this.onGround()) {
                this.setAttackUse(3);
                this.trueHurt();
            }
            this.setShockUseTick(0);
            this.setShockJump(false);
        } else if (this.isShockJump() && target != null) {
            this.rushAttack();
        }
        this.setShockTick(Math.min(600, this.getShockTick() + 1));
        this.setShockUseTick(Math.max(0, this.getShockUseTick() - 1));
    }

    private void strikeDisplayBurst() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        ParticleOptions particle = jerotesParticle("intimidation_strike_display", ParticleTypes.SNOWFLAKE);
        serverLevel.sendParticles(particle, this.getX(), this.getBoundingBox().maxY + 0.5D, this.getZ(),
                0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private void breathBurst() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        float body = this.yBodyRot * ((float) Math.PI / 180.0F) + 1.5707964F;
        double cx = this.getX() + 2.25D * Mth.cos(body);
        double cy = this.getY() + 3.15D;
        double cz = this.getZ() + 2.25D * Mth.sin(body);
        float headYaw = -this.getYHeadRot() * ((float) Math.PI / 180.0F);
        double lookX = Mth.sin(headYaw);
        double lookZ = Mth.cos(headYaw);
        double threshold = 1.0D - 40.0D / 180.0D;
        for (int i = 0; i < 70; i++) {
            double angle = Math.PI * 2.0D * i / 70.0D;
            double ox = Math.cos(angle);
            double oz = Math.sin(angle);
            if (ox * lookX + oz * lookZ <= threshold) {
                continue;
            }
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, cx, cy, cz, 0, ox * 0.5D, -0.04D, oz * 0.5D, 1.0D);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, cx, cy, cz, 0, ox, -0.08D, oz, 1.0D);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, cx, cy, cz, 0, ox * 0.7D, -0.12D, oz * 0.7D, 1.0D);
        }
    }

    private void applyBreathFreezeAndQuake() {
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getAttackBoundingBox().inflate(3.0D))) {
            if (victim == this || !victim.isAlive() || !this.canAttack(victim) || this.isAlliedTo(victim)) {
                continue;
            }
            if (!this.hasLineOfSight(victim) || !this.inFrontOf(victim)) {
                continue;
            }
            if (victim.getTicksFrozen() < 360) {
                victim.setTicksFrozen(360);
            }
        }
        this.applyQuakeIn(this.getBoundingBox().inflate(16.0D));
    }

    private void applyQuakeIn(AABB area) {
        MobEffect quake = jerotesCoreEffect("quake");
        if (quake == null) {
            return;
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || !victim.isAlive()) {
                continue;
            }
            victim.addEffect(new MobEffectInstance(quake, 20, 0, false, false), this);
        }
    }

    private void attackUseBurst() {
        int use = this.getAttackUse();
        if (use == 1 || use == 3 || use == 4) {
            this.applyQuakeIn(this.getBoundingBox().inflate(16.0D));
        }
        if (use == 1 || use == 3) {
            if (use == 3) {
                Vec3 center = this.position().add(0.0D, 0.2D, 0.0D);
                this.ringBurst(center);
                this.liftBlocks(this.getX(), this.getY() - 1.0D, this.getZ(), 3.0F, false);
            } else {
                float body = this.yBodyRot * ((float) Math.PI / 180.0F) + 1.5707964F;
                Vec3 center = new Vec3(this.getX() + 5.0D * Mth.cos(body), this.getY() + 0.1D,
                        this.getZ() + 5.0D * Mth.sin(body));
                this.ringBurst(center);
                this.liftBlocks(this.getX() + 5.0D * Mth.cos(body), this.getY() - 0.9D,
                        this.getZ() + 5.0D * Mth.sin(body), 3.0F, false);
            }
        }
        if (use == 4) {
            float body = this.yBodyRot * ((float) Math.PI / 180.0F);
            Vec3 first = new Vec3(this.getX() + 4.75D * Mth.cos(body + 1.7453293F), this.getY() + 0.1D,
                    this.getZ() + 5.0D * Mth.sin(body + 1.7453293F));
            Vec3 second = new Vec3(this.getX() + 4.75D * Mth.cos(body + 1.3962634F), this.getY() + 0.1D,
                    this.getZ() + 5.0D * Mth.sin(body + 1.3962634F));
            this.ringBurst(first);
            this.ringBurst(second);
            this.liftBlocks(this.getX(), this.getY() - 1.0D, this.getZ(), 12.0F, true);
        }
    }

    private void ringBurst(Vec3 center) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (int i = 0; i < 70; i++) {
            double angle = Math.PI * 2.0D * i / 70.0D;
            double ox = Math.cos(angle);
            double oz = Math.sin(angle);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, center.x + ox * 1.4D, center.y, center.z + oz * 1.4D, 0, ox * 1.4D, -0.04D, oz * 1.4D, 1.0D);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, center.x + ox, center.y, center.z + oz, 0, ox, -0.04D, oz, 1.0D);
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, center.x + ox * 0.7D, center.y, center.z + oz * 0.7D, 0, ox * 0.7D, -0.04D, oz * 0.7D, 1.0D);
        }
    }

    private void liftBlocks(double cx, double cy, double cz, float extraHalf, boolean angleFilter) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        double size = this.getBbWidth() / 2.0F + extraHalf;
        AABB area = AABB.ofSize(new Vec3(cx, cy, cz), size, 0.0D, size);
        float headYaw = -this.getYHeadRot() * ((float) Math.PI / 180.0F);
        Vec3 look = new Vec3(Mth.sin(headYaw), 0.0D, Mth.cos(headYaw));
        int spawned = 0;
        for (BlockPos pos : BlockPos.betweenClosed(
                Mth.floor(area.minX), Mth.floor(area.minY), Mth.floor(area.minZ),
                Mth.floor(area.maxX), Mth.floor(area.maxY), Mth.floor(area.maxZ))) {
            if (spawned >= MAX_LIFTED_BLOCKS) {
                break;
            }
            if (angleFilter) {
                Vec3 toPos = new Vec3(pos.getX() + 0.5D - this.getX(), 0.0D, pos.getZ() + 0.5D - this.getZ()).normalize();
                if (look.x * toPos.x + look.z * toPos.z <= 1.0D - 120.0D / 180.0D) {
                    continue;
                }
            }
            if (this.spawnVisualFallingBlock(serverLevel, pos)) {
                spawned++;
            }
        }
    }

    private boolean spawnVisualFallingBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || !state.isRedstoneConductor(level, pos) || state.hasBlockEntity()) {
            return false;
        }
        BlockPos above = pos.above();
        if (level.getBlockState(above).blocksMotion()) {
            return false;
        }
        Entity created = EntityType.FALLING_BLOCK.create(level);
        if (!(created instanceof FallingBlockEntity falling)) {
            return false;
        }
        CompoundTag tag = new CompoundTag();
        tag.put("BlockState", NbtUtils.writeBlockState(state));
        falling.load(tag);
        falling.disableDrop();
        falling.dropItem = false;
        falling.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
        falling.setDeltaMovement((this.random.nextDouble() - 0.5D) * 0.3D,
                0.4D + this.random.nextDouble() * 0.4D,
                (this.random.nextDouble() - 0.5D) * 0.3D);
        falling.hasImpulse = true;
        level.addFreshEntity(falling);
        return true;
    }

    public void jumpToTarget(LivingEntity target) {
        if (target == null) {
            return;
        }
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float dirX = -Mth.sin(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float dirY = -Mth.sin(pitch * ((float) Math.PI / 180.0F));
        float dirZ = Mth.cos(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float length = Mth.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float distance = Math.min(this.distanceTo(target), 16.0F);
        this.setOnGround(false);
        this.setDeltaMovement(dirX * (distance / length * 1.35F),
                1.25D + Math.min(8.0D, Math.max(0.0D, target.getY() - this.getY())) * 0.2D,
                dirZ * (distance / length * 1.35F));
        this.getNavigation().moveTo(target, 1.0D);
    }

    public boolean rushAttack() {
        if (!this.level().isClientSide) {
            this.setXRot(this.rushFindXRot);
            this.setYRot(this.rushFindYRot);
            this.setYBodyRot(this.rushFindYRot);
            this.setYHeadRot(this.rushFindYRot);
        }
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float dirX = -Mth.sin(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float dirZ = Mth.cos(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float dirY = -Mth.sin(pitch * ((float) Math.PI / 180.0F));
        float length = Mth.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        if (length < 1.0E-4F) {
            return false;
        }
        Vec3 motion = this.getDeltaMovement();
        this.setOnGround(false);
        this.setDeltaMovement(motion.add(dirX * (5.0F / length * 0.05F), 0.0D, dirZ * (5.0F / length * 0.05F)));
        return true;
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
        if ((this.specialAction() && !this.onGround()) || this.getAttackTick() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.005D, 1.0D, 0.005D));
        }
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        double inflate = this.walkAnimation.speed() < 0.01F ? 0.0D : 1.0D;
        return this.getAttackBoundingBox().inflate(inflate, 0.0D, inflate).intersects(target.getBoundingBox());
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
        ItemStack torch = jerotesItem("torch_of_firm_ice");
        if (!torch.isEmpty()) {
            spawnAtLocation(torch);
        }
    }

    private static final float COLD_ABSORPTION_PERCENTAGE = 80.0F;

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return (source.is(DamageTypeTags.IS_FALL) && this.isShockJump())
                || source.is(DamageTypeTags.IS_FREEZING)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castColdAbsorption(source, amount, COLD_ABSORPTION_PERCENTAGE);
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return super.hurt(source, amount * 2.0F);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN || effect.getEffect() == MobEffects.WEAKNESS) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public int calculateFallDamage(float fallDistance, float multiplier) {
        return super.calculateFallDamage(fallDistance, multiplier) - 22;
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
        return 4.5F;
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Override
    public int getMaxHeadXRot() {
        return 15;
    }

    @Override
    public int getHeadRotSpeed() {
        return 5;
    }

    boolean stopLookTime() {
        return this.getAttackTick() > 0;
    }

    @Override
    public void lookAt(Entity entity, float maxYawIncrease, float maxPitchIncrease) {
        if (!this.stopLookTime()) {
            super.lookAt(entity, maxYawIncrease, maxPitchIncrease);
        }
    }

    @Override
    public void setXRot(float xRot) {
        if (!this.stopLookTime()) {
            super.setXRot(xRot);
        }
    }

    @Override
    public void setYRot(float yRot) {
        if (!this.stopLookTime()) {
            super.setYRot(yRot);
        }
    }

    @Override
    public void setYBodyRot(float yBodyRot) {
        if (!this.stopLookTime()) {
            super.setYBodyRot(yBodyRot);
        }
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7D;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("primordier_ambient", SoundEvents.RAVAGER_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("primordier_hurt", SoundEvents.RAVAGER_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("primordier_death", SoundEvents.RAVAGER_DEATH);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(jerotesSound("primordier_rush", SoundEvents.RAVAGER_STEP), 0.15F, 1.0F);
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

    public void setAttackUse(int value) {
        this.entityData.set(DATA_ATTACK_USE, value);
    }

    public int getAttackUse() {
        return this.entityData.get(DATA_ATTACK_USE);
    }

    public int getAttackWalk() {
        return this.entityData.get(DATA_ATTACK_WALK);
    }

    public void setAttackWalk(int value) {
        this.entityData.set(DATA_ATTACK_WALK, value);
    }

    public int getShockTick() {
        return this.entityData.get(DATA_SHOCK_TICK);
    }

    public void setShockTick(int value) {
        this.entityData.set(DATA_SHOCK_TICK, value);
    }

    public int getShockUseTick() {
        return this.entityData.get(DATA_SHOCK_USE_TICK);
    }

    public void setShockUseTick(int value) {
        this.entityData.set(DATA_SHOCK_USE_TICK, value);
    }

    public boolean isShouldShock() {
        return this.entityData.get(DATA_SHOULD_SHOCK);
    }

    public void setShouldShock(boolean value) {
        this.entityData.set(DATA_SHOULD_SHOCK, value);
    }

    public boolean isShockJump() {
        return this.entityData.get(DATA_SHOCK_JUMP);
    }

    public void setShockJump(boolean value) {
        this.entityData.set(DATA_SHOCK_JUMP, value);
    }

    public int getJumpAnim() {
        return this.entityData.get(DATA_JUMP_ANIM);
    }

    public void setJumpAnim(int value) {
        this.entityData.set(DATA_JUMP_ANIM, value);
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
            case "attack4" -> 4;
            case "attack5" -> 5;
            case "attack6" -> 6;
            case "attack7" -> 7;
            case "attack8" -> 8;
            case "shock" -> 9;
            case "dead" -> 11;
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
            case 4 -> this.attack4AnimationState.startIfStopped(this.tickCount);
            case 5 -> this.attack5AnimationState.startIfStopped(this.tickCount);
            case 6 -> this.attack6AnimationState.startIfStopped(this.tickCount);
            case 7 -> this.attack7AnimationState.startIfStopped(this.tickCount);
            case 8 -> this.attack8AnimationState.startIfStopped(this.tickCount);
            case 9 -> this.shockAnimationState.startIfStopped(this.tickCount);
            case 11 -> this.deadAnimationState.startIfStopped(this.tickCount);
            default -> {
            }
        }
    }

    private void stopAllAnimation() {
        this.attack1AnimationState.stop();
        this.attack2AnimationState.stop();
        this.attack3AnimationState.stop();
        this.attack4AnimationState.stop();
        this.attack5AnimationState.stop();
        this.attack6AnimationState.stop();
        this.attack7AnimationState.stop();
        this.attack8AnimationState.stop();
        this.shockAnimationState.stop();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AnimTick", this.getAnimTick());
        tag.putInt("AttackTick", this.getAttackTick());
        tag.putInt("AttackUse", this.getAttackUse());
        tag.putInt("AttackWalk", this.getAttackWalk());
        tag.putInt("ShockTick", this.getShockTick());
        tag.putInt("ShockUseTick", this.getShockUseTick());
        tag.putBoolean("ShouldShock", this.isShouldShock());
        tag.putBoolean("ShockJump", this.isShockJump());
        tag.putInt("JumpAnim", this.getJumpAnim());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAnimTick(tag.getInt("AnimTick"));
        this.setAttackTick(tag.getInt("AttackTick"));
        this.setAttackUse(tag.getInt("AttackUse"));
        this.setAttackWalk(tag.getInt("AttackWalk"));
        this.setShockTick(tag.contains("ShockTick") ? tag.getInt("ShockTick") : 600);
        this.setShockUseTick(tag.getInt("ShockUseTick"));
        this.setShouldShock(tag.getBoolean("ShouldShock"));
        this.setShockJump(tag.getBoolean("ShockJump"));
        this.setJumpAnim(tag.getInt("JumpAnim"));
    }

}
