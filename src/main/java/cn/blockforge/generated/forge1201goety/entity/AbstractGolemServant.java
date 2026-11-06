package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantOwnerTargetGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.model.IceGolemServantModel;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.util.ServantRobeFireDamage;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemClips;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;

public abstract class AbstractGolemServant extends Summoned implements IServant, OwnableEntity {

    private static final int WANDERING_FLAG = 1;
    private static final int STAYING_FLAG = 2;
    private static final int NATURAL_FLAG = 4;

    private static final EntityDataAccessor<Byte> SERVANT_FLAGS =
            SynchedEntityData.defineId(AbstractGolemServant.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Boolean> DATA_CHARGING =
            SynchedEntityData.defineId(AbstractGolemServant.class, EntityDataSerializers.BOOLEAN);

    private static final double CHARGE_STOP_DISTANCE_SQR = 4.0D;

    private static final double CHARGE_MIN_SPEED = 0.05D;

    private boolean hostile;

    private int attackAnimationTick;

    private int attackAnimationTicks;

    private int attackAnimation;

    private static final double FOOTSTEP_PHASE = Math.PI;

    private static final float FOOTSTEP_MIN_SPEED = 0.05F;

    private int lastFootstepIndex = -1;

    public static final int ATTACK_SWING = 0;

    public static final int ATTACK_SWEEP = 1;

    public static final int ATTACK_SMASH = 2;

    public static final int ATTACK_FIREBALL = 3;

    public static final int ATTACK_MAGMA_SWEEP = 4;

    public static final int ATTACK_SNOW_SLAM = 5;

    public static final int ATTACK_SNOW_THROW_BLOCK = 6;

    public static final int ATTACK_SNOW_THROW_BALL = 7;

    public static final int ATTACK_FROST_BREATH = 8;

    public static final int ATTACK_SNOW_THROW_BALL_SPIN = 9;

    public static final int ATTACK_FIRE_BREATH = 10;

    public static final int ATTACK_SNOW_THROW_BALL_FINISH = 11;

    public static final int ATTACK_ICE_SWING = 12;

    public static final int ATTACK_FURNACE_SWING = 13;

    public static final int SNOW_THROW_BLOCK_CLIP_TICKS = 40;

    public static final int SNOW_THROW_BLOCK_ACTION_TICKS = SNOW_THROW_BLOCK_CLIP_TICKS;

    public static final float SWEEP_CLIP_TICKS = 27.0F;

    public static final float SWEEP_WINDUP_TICKS = 13.0F;

    public static final float SWEEP_IMPACT_CLIP_TICK =
            AbstractGolemServant.SWEEP_IMPACT_TICK * SWEEP_CLIP_TICKS / SWEEP_WINDUP_TICKS;

    public static final int SWEEP_ACTION_TICKS = (int) Math.ceil(AbstractGolemServant.SWEEP_IMPACT_TICK
            + (SWEEP_CLIP_TICKS - SWEEP_IMPACT_CLIP_TICK));

    public static final float[] SWEEP_RECOVERY_IMPACT =
            {AbstractGolemServant.SWEEP_IMPACT_TICK, SWEEP_IMPACT_CLIP_TICK};

    public static final float FURNACE_SWING_CLIP_TICKS = 30.0F;

    public static final float FURNACE_SWING_WINDUP_TICKS = 15.0F;

    public static final int FURNACE_SWING_IMPACT_ACTION_TICK = 10;

    public static final float FURNACE_SWING_IMPACT_CLIP_TICK = FURNACE_SWING_IMPACT_ACTION_TICK
            * FURNACE_SWING_CLIP_TICKS / FURNACE_SWING_WINDUP_TICKS;

    public static final int FURNACE_SWING_ACTION_TICKS = (int) Math.ceil(
            FURNACE_SWING_IMPACT_ACTION_TICK + (FURNACE_SWING_CLIP_TICKS - FURNACE_SWING_IMPACT_CLIP_TICK));

    public static final float[] FURNACE_SWING_RECOVERY_IMPACT =
            {FURNACE_SWING_IMPACT_ACTION_TICK, FURNACE_SWING_IMPACT_CLIP_TICK};

    public static final int[] ATTACK_LENGTHS = {10, SWEEP_ACTION_TICKS, 32, 50, 23, 40,
            SNOW_THROW_BLOCK_ACTION_TICKS, 63, 95, 25, 90, 13, 42, FURNACE_SWING_ACTION_TICKS};

    public static final int SWEEP_IMPACT_TICK = 10;

    public static final int SMASH_IMPACT_TICK = 25;

    public static final int FIREBALL_RELEASE_TICK = 35;

    public static final int MAGMA_SWEEP_IMPACT_TICK = 10;

    public static final int SNOW_SLAM_IMPACT_TICK = 25;

    public static final int SNOW_THROW_BLOCK_RELEASE_TICK = 18;

    public static int snowThrowBlockReleaseTick() {
        return (int) Math.ceil(SNOW_THROW_BLOCK_RELEASE_TICK
                * (double) SNOW_THROW_BLOCK_ACTION_TICKS / SNOW_THROW_BLOCK_CLIP_TICKS);
    }

    public static final int SNOW_THROW_BALL_RELEASE_TICK = 25;

    public static final int SNOW_THROW_BALL_SPIN_TRANSITION_TICK = 49;

    public static final int FROST_BREATH_START_TICK = 20;

    public static final int FROST_BREATH_END_TICK = 85;

    public static final int FIRE_BREATH_START_TICK = 15;

    public static final int FIRE_BREATH_END_TICK = 85;

    public static boolean hasBreathLineOfSight(LivingEntity caster, Entity target) {
        if (caster == null || target == null || caster.level() != target.level()) {
            return false;
        }
        Vec3 from = new Vec3(caster.getX(), caster.getEyeY() - caster.getBbHeight() * 0.25D, caster.getZ());
        Vec3 to = new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5D, target.getZ());
        net.minecraft.world.phys.BlockHitResult hit = caster.level().clip(
                new net.minecraft.world.level.ClipContext(from, to,
                        net.minecraft.world.level.ClipContext.Block.COLLIDER,
                        net.minecraft.world.level.ClipContext.Fluid.NONE, caster));
        return hit.getType() == net.minecraft.world.phys.HitResult.Type.MISS;
    }

    public static List<Entity> breathTargets(LivingEntity caster, double range, double radius) {
        List<Entity> targets = new ArrayList<>();
        if (caster == null) {
            return targets;
        }
        Vec3 eye = caster.getEyePosition();
        Vec3 look = caster.getViewVector(1.0F);
        Vec3 flatLook = new Vec3(look.x, 0.0D, look.z);
        boolean directional = flatLook.lengthSqr() > 1.0E-6D;
        if (directional) {
            flatLook = flatLook.normalize();
        }
        double bandBottom = caster.getY() - 1.0D;
        double bandTop = caster.getY() + caster.getBbHeight() + 1.0D;
        for (Entity entity : caster.level().getEntities(caster,
                caster.getBoundingBox().expandTowards(look.x * range, look.y * range, look.z * range)
                        .inflate(radius, radius, radius))) {
            if (entity == caster || !entity.isPickable()
                    || !(entity instanceof LivingEntity living)
                    || !living.isAlive() || caster.isAlliedTo(living)) {
                continue;
            }
            if (living instanceof Player player && (player.isCreative() || player.isSpectator())) {
                continue;
            }
            if (living.getBoundingBox().maxY < bandBottom
                    || living.getBoundingBox().minY > bandTop) {
                continue;
            }
            Vec3 center = living.position().add(0.0D, living.getBbHeight() * 0.5D, 0.0D);
            if (eye.distanceTo(center) > range + living.getBbWidth()) {
                continue;
            }
            if (directional) {
                Vec3 to = new Vec3(center.x - caster.getX(), 0.0D, center.z - caster.getZ());
                double horizontal = to.length();
                if (horizontal > 1.0E-4D) {
                    double halfAngle = Math.atan2(radius + living.getBbWidth() * 0.5D, horizontal);
                    if (to.normalize().dot(flatLook) < Math.cos(halfAngle)) {
                        continue;
                    }
                }
            }
            if (!hasBreathLineOfSight(caster, living)) {
                continue;
            }
            targets.add(living);
        }
        return targets;
    }

    public static final double MELEE_KNOCKBACK = 0.4D;

    public static final double MELEE_LAUNCH = 0.4D;

    public static final double VANILLA_MELEE_LAUNCH = 0.4D;

    public static final double GOETY_KNOCKBACK_RESISTANCE_FACTOR = 0.5D;

    public static final double GOETY_KNOCKBACK_MAX_LAUNCH = 0.4D;

    public static final double SLAM_LAUNCH = 0.68D;

    public static final byte ATTACK_EVENT = 4;

    public static final byte ANIMATION_EVENT = 6;

    public AbstractGolemServant(EntityType<? extends AbstractGolemServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
        setMaxUpStep(1.0F);
    }

    public abstract GolemServantConfig.Section configSection();

    public static AttributeSupplier.Builder baseAttributes(GolemServantConfig.Section section) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, section.MAX_HEALTH.get())
                .add(Attributes.MOVEMENT_SPEED, section.MOVEMENT_SPEED.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, section.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.ATTACK_DAMAGE, section.ATTACK_DAMAGE.get())
                .add(Attributes.ARMOR, section.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, section.ARMOR_TOUGHNESS.get());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void setConfigurableAttributes() {
        GolemServantConfig.Section section = configSection();
        setBaseAttribute(Attributes.MAX_HEALTH, configuredMaxHealth());
        setBaseAttribute(Attributes.MOVEMENT_SPEED, section.MOVEMENT_SPEED.get());
        setBaseAttribute(Attributes.KNOCKBACK_RESISTANCE, section.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(Attributes.ATTACK_DAMAGE, configuredAttackDamage());
        setBaseAttribute(Attributes.ARMOR, section.ARMOR.get());
        setBaseAttribute(Attributes.ARMOR_TOUGHNESS, section.ARMOR_TOUGHNESS.get());
    }

    public double configuredMaxHealth() {
        return configSection().MAX_HEALTH.get();
    }

    public double configuredAttackDamage() {
        return configSection().ATTACK_DAMAGE.get();
    }

    private void setBaseAttribute(net.minecraft.world.entity.ai.attributes.Attribute attribute, double value) {
        AttributeInstance instance = getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
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
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SERVANT_FLAGS, (byte) 0);
        entityData.define(DATA_CHARGING, false);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.removeAllGoals(goal -> true);
        this.targetSelector.addGoal(0, new ServantOwnerTargetGoal.HurtBy<>(this));
        this.targetSelector.addGoal(1, new ServantOwnerTargetGoal.Hurt<>(this));
        this.targetSelector.addGoal(2, new ServantHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new SummonTargetGoal(this));
        this.goalSelector.addGoal(1, new GolemMeleeGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new CommandPositionGoal(this, 1.15D));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, wanderSpeed(), 0.00001F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    public double wanderSpeed() {
        return 1.0D;
    }

    public int meleeAnticipationTicks() {
        return 0;
    }

    public double meleeAnticipationMax() {
        return 2.5D;
    }

    public double meleeAnticipatedReachSqr(LivingEntity target) {
        double reach = meleeGapReach(target);
        int ticks = meleeAnticipationTicks();
        double closing = closingSpeed(target);
        if (ticks > 0 && closing > 0.0D) {
            reach += Math.min(closing * ticks, meleeAnticipationMax());
        }
        return reach * reach;
    }

    public final double meleeGapReach(LivingEntity target) {
        double fromHitbox = meleeStartReach(target) - getBbWidth() * 0.5D;
        return Math.max(0.6D, Math.min(fromHitbox, meleeArmGapReach()));
    }

    public double meleeArmGapReach() {
        return Math.max(0.7D, getBbHeight() * 0.4D);
    }

    public static double meleeGapDistanceSqr(Entity attacker, Entity target) {
        return PlayerMobMeleeAttackGoal.gapDistanceSqr(attacker, target);
    }

    public static double gapThresholdSqr(double gapRange) {
        double r = Math.max(1.0D, gapRange);
        return r * r;
    }

    public double meleeStartReach(LivingEntity target) {
        double reach = getBbWidth() * 2.0D;
        return Math.sqrt(reach * reach + target.getBbWidth());
    }

    public double closingSpeed(LivingEntity target) {
        double dx = getX() - target.getX();
        double dz = getZ() - target.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);
        if (dist < 1.0E-4D) {
            return 0.0D;
        }
        Vec3 theirs = target.getDeltaMovement();
        Vec3 ours = getDeltaMovement();
        return ((theirs.x - ours.x) * dx + (theirs.z - ours.z) * dz) / dist;
    }

    private static final class GolemMeleeGoal extends MeleeAttackGoal {
        private final AbstractGolemServant golem;

        GolemMeleeGoal(AbstractGolemServant golem) {
            super(golem, 1.0D, true);
            this.golem = golem;
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
            if (meleeGapDistanceSqr(golem, target) <= golem.meleeAnticipatedReachSqr(target)
                    && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                golem.swing(InteractionHand.MAIN_HAND);
                golem.doHurtTarget(target);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        int animation = attackSwingAnimation();
        startAttack(animation, ATTACK_LENGTHS[animation]);
        return applyMeleeHit(target);
    }

    public boolean applyMeleeHit(Entity target) {
        float attackDamage = rollAttackDamage((float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        boolean hit = target.hurt(this.damageSources().mobAttack(this), attackDamage);
        if (hit) {
            applyMeleeHitKnockback(target);
            this.doEnchantDamageEffects(this, target);
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
            onAttackHit(target);
        }
        return hit;
    }

    public void applyMeleeHitKnockback(Entity target) {
        applyMeleeKnockback(target, MELEE_KNOCKBACK);
    }

    public void applyVanillaGolemKnockback(Entity target) {
        applyMeleeLaunch(target, 0.0D, VANILLA_MELEE_LAUNCH);
    }

    public void applyMeleeKnockback(Entity target, double strength) {
        applyMeleeLaunch(target, strength, MELEE_LAUNCH * strength);
    }

    public void applyMeleeLaunch(Entity target, double horizontal, double vertical) {
        if (horizontal <= 0.0D && vertical <= 0.0D) {
            return;
        }
        double resistance = target instanceof LivingEntity living
                ? living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0.0D;
        double launch = Math.max(0.0D, 1.0D - resistance);
        if (launch <= 0.0D) {
            return;
        }
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len < 1.0E-4D) {
            dx = -Mth.sin(this.getYRot() * Mth.DEG_TO_RAD);
            dz = Mth.cos(this.getYRot() * Mth.DEG_TO_RAD);
        } else {
            dx /= len;
            dz /= len;
        }
        target.setDeltaMovement(target.getDeltaMovement()
                .add(dx * horizontal * launch, vertical * launch, dz * horizontal * launch));
        target.hurtMarked = true;
    }

    public void applyForcefulKnockback(LivingEntity target, double strength,
            double ratioX, double ratioZ, double factor, double launch) {
        LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(target, (float) strength, ratioX, ratioZ);
        if (event.isCanceled()) {
            return;
        }
        strength = event.getStrength();
        ratioX = event.getRatioX();
        ratioZ = event.getRatioZ();
        strength *= 1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * factor;
        if (strength <= 0.0D) {
            return;
        }
        target.hasImpulse = true;
        Vec3 motion = target.getDeltaMovement();
        Vec3 push = new Vec3(ratioX, 0.0D, ratioZ).normalize().scale(strength);
        target.setDeltaMovement(
                motion.x / 2.0D - push.x,
                target.onGround() ? Math.min(launch, motion.y / 2.0D + strength) : motion.y,
                motion.z / 2.0D - push.z);
        target.hurtMarked = true;
    }

    public void applySlamKnockback(LivingEntity target, double strength, double launch) {
        double dx = getX() - target.getX();
        double dz = getZ() - target.getZ();
        if (dx * dx + dz * dz < 1.0E-4D) {
            dx = Mth.sin(getYRot() * Mth.DEG_TO_RAD);
            dz = -Mth.cos(getYRot() * Mth.DEG_TO_RAD);
        }
        applyForcefulKnockback(target, strength, dx, dz, GOETY_KNOCKBACK_RESISTANCE_FACTOR, launch);
    }

    public static final double GRAVE_SLAM_FORWARD_OFFSET = 2.0D;

    public static final double GRAVE_SLAM_HORIZONTAL_RADIUS = 3.5D;

    public static final double GRAVE_SLAM_VERTICAL_RADIUS = 3.5D;

    public AABB slamAttackBox(double horizontalRadius) {
        return attackBox(GRAVE_SLAM_FORWARD_OFFSET, horizontalRadius, GRAVE_SLAM_VERTICAL_RADIUS);
    }

    public AABB attackBox(double forwardOffset, double horizontalRadius, double verticalRadius) {
        float yawRad = slamFacingYaw() * Mth.DEG_TO_RAD;
        double lookX = -Mth.sin(yawRad);
        double lookZ = Mth.cos(yawRad);
        double centerX = getX() + lookX * forwardOffset;
        double centerZ = getZ() + lookZ * forwardOffset;
        double centerY = getY();
        return new AABB(
                centerX - horizontalRadius, centerY - verticalRadius, centerZ - horizontalRadius,
                centerX + horizontalRadius, centerY + verticalRadius, centerZ + horizontalRadius);
    }

    public float slamFacingYaw() {
        return this.yBodyRot;
    }

    public int attackSwingAnimation() {
        return ATTACK_SWEEP;
    }

    public float attackDamageFloorRatio() {
        return 0.5F;
    }

    public float rollAttackDamage(float damage) {
        return (int) damage > 0
                ? damage * attackDamageFloorRatio() + (float) this.random.nextInt((int) damage)
                : damage;
    }

    public void onAttackHit(Entity target) {
    }

    public void startAttack(int animation, int lengthTicks) {
        this.attackAnimation = animation;
        this.attackAnimationTicks = lengthTicks;
        this.attackAnimationTick = lengthTicks;
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) (ANIMATION_EVENT + Math.max(0, animation)));
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_EVENT) {
            this.attackAnimation = ATTACK_SWING;
            this.attackAnimationTicks = ATTACK_LENGTHS[ATTACK_SWING];
            this.attackAnimationTick = ATTACK_LENGTHS[ATTACK_SWING];
        } else if (id >= ANIMATION_EVENT && id < ANIMATION_EVENT + ATTACK_LENGTHS.length) {
            int animation = id - ANIMATION_EVENT;
            this.attackAnimation = animation;
            this.attackAnimationTicks = ATTACK_LENGTHS[animation];
            this.attackAnimationTick = ATTACK_LENGTHS[animation];
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    public int getAttackAnimationTicks() {
        return this.attackAnimationTicks;
    }

    public boolean isAttackAnimating() {
        return this.attackAnimationTick > 0;
    }

    public int getAttackAnimation() {
        return this.attackAnimation;
    }

    public float[] recoveryImpactTicks() {
        return null;
    }

    public boolean attackReached(int tick) {
        return isAlive() && this.attackAnimationTick > 0
                && this.attackAnimationTicks - this.attackAnimationTick >= tick;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
        tickAnimationDrivenFootsteps();
    }

    public void applyBonusDamage(LivingEntity target, DamageSource source, float amount) {
        if (level().isClientSide || target == null || !target.isAlive() || amount <= 0.0F) {
            return;
        }
        source = ServantRobeFireDamage.upgrade(this, source);
        int invulnerable = target.invulnerableTime;
        target.invulnerableTime = 0;
        target.hurt(source, amount);
        target.invulnerableTime = Math.max(invulnerable, target.invulnerableTime);
    }

    public boolean isStandingOnFire() {
        BlockPos feet = blockPosition();
        return isFireBlock(level().getBlockState(feet))
                || isFireBlock(level().getBlockState(feet.below()));
    }

    private static boolean isFireBlock(BlockState state) {
        if (state.is(BlockTags.FIRE) || state.is(Blocks.MAGMA_BLOCK)) {
            return true;
        }
        if (state.is(Blocks.CAMPFIRE) || state.is(Blocks.SOUL_CAMPFIRE)) {
            return state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT);
        }
        return false;
    }

    public double fireHealPerSecond() {
        return configSection().FIRE_HEAL_PER_SECOND.get();
    }

    public void tickFireHeal() {
        if (level().isClientSide || !isAlive() || getHealth() >= getMaxHealth() || tickCount % 20 != 0) {
            return;
        }
        double amount = fireHealPerSecond();
        if (amount <= 0.0D || !isStandingOnFire()) {
            return;
        }
        heal((float) amount);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    getX(), getY() + 0.4D, getZ(), 1, 0.3D, 0.3D, 0.3D, 0.01D);
        }
    }

    @Override
    public int decreaseAirSupply(int air) {
        return air;
    }

    @Override
    public void doPush(Entity entity) {
        if (!(entity instanceof Player)) {
            super.doPush(entity);
        }
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7D
                && this.random.nextInt(5) == 0;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        GolemServantConfig.Section section = configSection();
        ItemStack held = player.getItemInHand(hand);
        if (section.feedEnabled() && held.is(section.healItem)) {
            LivingEntity owner = getTrueOwner();
            if (owner != null && owner != player) {
                return super.mobInteract(player, hand);
            }
            if (getHealth() < getMaxHealth()) {
                heal((float) (getMaxHealth() * section.FEED_HEAL_PERCENT.get() / 100.0D));
                if (!level().isClientSide()) {
                    playSound(healSound(), 1.0F,
                            (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                }
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
            return InteractionResult.PASS;
        }
        return super.mobInteract(player, hand);
    }

    public SoundEvent healSound() {
        return SoundEvents.IRON_GOLEM_REPAIR;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        if (this.usesAnimationDrivenFootsteps()) {
            return;
        }
        this.playFootstepSound();
    }

    public void playFootstepSound() {
        playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }

    public boolean usesAnimationDrivenFootsteps() {
        return false;
    }

    private void tickAnimationDrivenFootsteps() {
        if (!this.usesAnimationDrivenFootsteps() || this.level().isClientSide) {
            return;
        }
        int index = (int) Math.floor(this.walkAnimation.position() / FOOTSTEP_PHASE);
        if (this.lastFootstepIndex < 0) {
            this.lastFootstepIndex = index;
            return;
        }
        if (index == this.lastFootstepIndex) {
            return;
        }
        this.lastFootstepIndex = index;
        if (this.walkAnimation.speed() < FOOTSTEP_MIN_SPEED
                || !this.onGround() || this.isPassenger()
                || this.isInWater() || this.isInLava()) {
            return;
        }
        BlockPos below = this.getOnPos();
        if (this.level().getBlockState(below).isAir()) {
            return;
        }
        this.playFootstepSound();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) tag.putUUID("Owner", owner);
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
        tag.putInt("commandTick", commandTick);
        if (commandPos != null) tag.put("commandPos", NbtUtils.writeBlockPos(commandPos));
        if (boundPos != null) {
            tag.put("boundPos", NbtUtils.writeBlockPos(boundPos));
            tag.putString("boundDim", boundDim);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) setOwnerId(tag.getUUID("Owner"));
        setWandering(tag.getBoolean("wandering"));
        setStaying(tag.getBoolean("staying"));
        setConfigurableAttributes();
        setHealth(Math.min(getHealth(), getMaxHealth()));
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        if (tag.contains("commandPos", 10)) commandPos = NbtUtils.readBlockPos(tag.getCompound("commandPos")).immutable();
        if (tag.contains("boundPos", 10)) {
            boundPos = NbtUtils.readBlockPos(tag.getCompound("boundPos")).immutable();
            boundDim = tag.contains("boundDim") ? tag.getString("boundDim") : level().dimension().location().toString();
        }
    }

    @Override
    public UUID getOwnerUUID() {
        return getOwnerId();
    }

    @Override
    public void checkHostility() {
        hostile = false;
    }

    @Override
    public void setHostile(boolean hostile) {
        this.hostile = false;
    }

    @Override
    public boolean isHostile() {
        return hostile;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target != null && target != this && !isAlliedTo(target) && super.canAttack(target);
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target != null && canAttack(target) ? target : null);
    }

    public boolean isAutoTargetable(LivingEntity candidate) {
        return candidate != null && candidate.isAlive() && candidate != this
                && !isAlliedTo(candidate) && canAttack(candidate)
                && com.Polarice3.Goety.utils.MobUtil.isOwnedTargetable(this, candidate);
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == this) return true;
        LivingEntity owner = getTrueOwner();
        if (ServantFaction.isSameFaction(this, entity)) return true;
        if (entity == owner || (owner != null && owner.isAlliedTo(entity))) return true;
        if (entity instanceof com.Polarice3.Goety.api.entities.IOwned other
                && getOwnerId() != null && getOwnerId().equals(other.getOwnerId())) return true;
        if (entity instanceof OwnableEntity ownable && owner != null && ownable.getOwner() == owner) return true;
        return super.isAlliedTo(entity);
    }

    public void updateCommand() {
        if (!isCommanded() || getTarget() != null) return;
        if (commandTick != Integer.MAX_VALUE && --commandTick <= 0) {
            commandPos = null;
            commandPosEntity = null;
            return;
        }
        if (commandPosEntity != null) {
            commandPos = commandPosEntity.blockPosition();
            if (distanceToSqr(commandPosEntity) <= 4.0D) {
                commandPos = null;
                commandPosEntity = null;
            }
        }
    }

    public void updateGuardPosition() {
        if (!isGuardingArea() || getTarget() == null || boundPos == null) return;
        if (getTarget().distanceToSqr(boundPos.getX() + 0.5D, boundPos.getY(), boundPos.getZ() + 0.5D)
                > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE * 4.0D) setTarget(null);
    }

    @Override public void setNatural(boolean natural) { setFlag(NATURAL_FLAG, natural); }
    @Override public boolean isNatural() { return getFlag(NATURAL_FLAG); }
    @Override public boolean isWandering() { return getFlag(WANDERING_FLAG); }
    @Override public void setWandering(boolean value) { setFlag(WANDERING_FLAG, value); }
    @Override public boolean isStaying() { return getFlag(STAYING_FLAG) && !isCommanded(); }
    @Override public void setStaying(boolean value) { setFlag(STAYING_FLAG, value); if (value) getNavigation().stop(); }
    @Override public boolean canUpdateMove() { return true; }

    @Override
    public void updateMoveMode(Player player) {
        ServantMoveModes.cycle(this, player);
    }

    @Override public void setCommandPos(BlockPos pos) { setCommandPos(pos, true); }
    @Override public void setCommandPos(BlockPos pos, boolean removeEntity) { if (removeEntity) commandPosEntity = null; commandPos = pos == null ? null : pos.immutable(); commandTick = pos == null ? 0 : (removeEntity ? 200 : Integer.MAX_VALUE); setStaying(false); }
    @Override public BlockPos getCommandPos() { return commandPos; }
    @Override public void setCommandPosEntity(LivingEntity entity) { commandPosEntity = entity; commandPos = entity == null ? null : entity.blockPosition(); commandTick = entity == null ? 0 : Integer.MAX_VALUE; setStaying(false); }
    @Override public LivingEntity getCommandPosEntity() { return commandPosEntity; }
    @Override public int getCommandTick() { return commandTick; }
    @Override public void setCommandTick(int ticks) { commandTick = ticks; }
    @Override public boolean isCommanded() { return commandPos != null || commandPosEntity != null; }
    @Override public BlockPos getBoundPos() { return boundPos; }
    @Override public void setBoundPos(BlockPos pos) { boundPos = pos == null ? null : pos.immutable(); if (boundPos != null) boundDim = level().dimension().location().toString(); }
    @Override public String getBoundDim() { return boundDim; }
    @Override public void setBoundDim(String dimension) { boundDim = dimension == null ? level().dimension().location().toString() : dimension; }
    @Override public void tryKill(Player player) { if (!level().isClientSide) hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE); }

    private boolean getFlag(int flag) { return (entityData.get(SERVANT_FLAGS) & flag) != 0; }
    private void setFlag(int flag, boolean value) { byte state = entityData.get(SERVANT_FLAGS); entityData.set(SERVANT_FLAGS, value ? (byte) (state | flag) : (byte) (state & ~flag)); }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            updateCommand();
            updateGuardPosition();
            if (isStaying() && getTarget() == null) getNavigation().stop();
            updateChargingFlag();
        }
        ServantTeleportHelper.tick(this);
    }

    private void updateChargingFlag() {
        LivingEntity target = getTarget();
        boolean charging = target != null && target.isAlive()
                && distanceToSqr(target) > CHARGE_STOP_DISTANCE_SQR
                && getDeltaMovement().horizontalDistance() > CHARGE_MIN_SPEED;
        entityData.set(DATA_CHARGING, charging);
    }

    public boolean isCharging() {
        return Boolean.TRUE.equals(entityData.get(DATA_CHARGING));
    }

}
