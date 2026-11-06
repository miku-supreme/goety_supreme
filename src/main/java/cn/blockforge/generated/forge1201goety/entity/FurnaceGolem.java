package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.nether.FireBreathSpell;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolemFireball;
import cn.blockforge.generated.goetysupreme.model.FurnaceGolemModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ServantRobeFireDamage;

public class FurnaceGolem extends AbstractGolemServant {
    public static final int ATTACK_FIRE_SECONDS = 5;
    public static final int BURNING_ATTACK_FIRE_SECONDS = 10;
    public static final int BURNING_RETALIATION_SECONDS = 3;

    public static final int COAL_BURN_TICKS = 1600;
    public static final int MAX_BURN_TICKS = 72000;
    public static final int BURN_FRAMES = 6;
    public static final int BURN_FRAME_TICKS = 2;

    public static final int BURNING_HEAL_INTERVAL_TICKS = 40;
    public static final float BURNING_HEAL_AMOUNT = 1.0F;

    public static final int IGNITE_FRONT_TICKS = 20;
    public static final int IGNITE_BACK_TICKS = 15;

    public static final int IGNITE_FRONT_FUEL_TICK = 10;

    public static final int IGNITE_BACK_FUEL_TICK = 6;

    public static final int IGNITE_SEGMENT_FRONT = 0;

    public static final int IGNITE_SEGMENT_BACK = 1;

    public static final byte IGNITE_EVENT = 5;
    public static final byte IGNITE_BACK_EVENT = 21;

    public static int igniteTicks(int segment) {
        return segment == IGNITE_SEGMENT_BACK ? IGNITE_BACK_TICKS : IGNITE_FRONT_TICKS;
    }

    public static int igniteFuelTick(int segment) {
        return segment == IGNITE_SEGMENT_BACK ? IGNITE_BACK_FUEL_TICK : IGNITE_FRONT_FUEL_TICK;
    }

    public static final int ATTACK_IMPACT_TICK = 10;

    private static final double ATTACK_IMPACT_REACH_TOLERANCE = 1.5D;

    public static final double FURNACE_MELEE_AOE_RADIUS = 2.8D;

    private Entity pendingAttackTarget;

    private boolean attackImpactDone;

    private int igniteAnimationTick;
    private int igniteHoldTicks;

    private int igniteSegment;

    private int igniteAnimationTicks;
    private BlockPos igniteTarget;
    private static final int COAL_BLOCK_TICKS = 16000;
    private static final EntityDataAccessor<Integer> DATA_BURN_TICKS =
            SynchedEntityData.defineId(FurnaceGolem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BURN_DURATION =
            SynchedEntityData.defineId(FurnaceGolem.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_BURNING_ALERT =
            SynchedEntityData.defineId(FurnaceGolem.class, EntityDataSerializers.BOOLEAN);
    private static final UUID SPEED_ID = UUID.fromString("4b76a8cb-6755-4e76-8c2f-82df0b7dcd11");
    private static final UUID DAMAGE_ID = UUID.fromString("8d2f8408-5fd7-4f75-9b27-11cb8c5a2d01");
    private static final UUID HEALTH_ID = UUID.fromString("34c7c2f2-0c24-46dd-a4ca-b218f281a9f3");
    private static final UUID COAL_ARMOR_ID = UUID.fromString("9c46f2d1-3b7e-4a58-9d21-6e0b2c5a8f47");

    private static final int COAL_ARMOR_PER_COAL = 1;

    private static final int COAL_ARMOR_PER_BLOCK = 2;

    private static final int FIRE_BREATH_BASE_RANGE = 8;

    private static final int FIRE_BREATH_CLUSTER_RADIUS = 10;

    private static final float FIRE_BREATH_MAX_UP_PITCH = 28.0F;

    private static final float FIRE_BREATH_MAX_DOWN_PITCH = 80.0F;

    private static final float FIRE_BREATH_TURN_DEGREES_PER_TICK = 10.0F;

    private static final float FIRE_BREATH_PITCH_DEGREES_PER_TICK = 12.0F;

    private static final double FIRE_BREATH_FRONT_ARC_DEGREES = 60.0D;

    private static final float FIRE_BREATH_LOOK_DEGREES = 10.0F;

    private static final float FIRE_BREATH_SOUND_VOLUME = 1.6F;

    private static final int FIRE_BREATH_FIREBALL_INTERVAL_TICKS = 15;

    private static final double FIRE_BREATH_LAVA_AHEAD = 1.0D;

    private static final double FIRE_BREATH_LAVA_RADIUS = 0.9D;

    private int fireBreathCooldown;

    private int fireBreathDamageTimer;
    private double fireBreathAimX;
    private double fireBreathAimY;
    private double fireBreathAimZ;

    private float fireBreathYaw;
    private float fireBreathPitch;

    private boolean fireBreathAimSeeded;

    private LivingEntity fireBreathTarget;

    private boolean fireBreathQueued;

    private boolean fireBreathQueuedByMelee;

    private LivingEntity fireBreathQueuedTarget;

    private boolean fireBreathSoundPlayed;

    private int fireBreathLavaTimer;

    private int fireBreathFireballTimer;

    private boolean coalBlockHealthGranted;

    private int coalArmor;

    public FurnaceGolem(EntityType<? extends FurnaceGolem> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.FURNACE);
    }

    @Override
    public GolemServantConfig.Section configSection() {
        return GolemServantConfig.FURNACE;
    }
    @Override public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_BURN_TICKS, 0);
        entityData.define(DATA_BURN_DURATION, 0);
        entityData.define(DATA_BURNING_ALERT, false);
    }
    public boolean isBurning() { return entityData.get(DATA_BURN_TICKS) > 0; }

    public boolean isBurningAlert() { return entityData.get(DATA_BURNING_ALERT); }
    public int getBurnTicks() { return entityData.get(DATA_BURN_TICKS); }
    private void setBurnTicks(int ticks) { entityData.set(DATA_BURN_TICKS, Math.max(0, Math.min(MAX_BURN_TICKS, ticks))); }
    private int fuelDuration() { return entityData.get(DATA_BURN_DURATION); }
    private boolean blockFuel() { return fuelDuration() >= COAL_BLOCK_TICKS; }

    private double fuelScale() {
        if (fuelDuration() <= COAL_BURN_TICKS) return 0.0D;
        return Math.min(1.0D, (fuelDuration() - COAL_BURN_TICKS)
                / (double) (COAL_BLOCK_TICKS - COAL_BURN_TICKS));
    }

    @Override public boolean fireImmune() { return true; }

    @Override public void tick() {
        super.tick();
        if (level().isClientSide) {
            return;
        }
        if (isInWaterOrBubble()) {
            setBurnTicks(0);
            entityData.set(DATA_BURN_DURATION, 0);
            igniteHoldTicks = 0;
            igniteTarget = null;
            coalBlockHealthGranted = false;
            coalArmor = 0;
        } else if (getBurnTicks() > 0) {
            setBurnTicks(getBurnTicks() - 1);
            if (getBurnTicks() == 0) {
                entityData.set(DATA_BURN_DURATION, 0);
                coalBlockHealthGranted = false;
                coalArmor = 0;
            }
        } else if (fuelDuration() != 0) {
            entityData.set(DATA_BURN_DURATION, 0);
        }
        updateModifiers();
        tickBurningHeal();
        tickFireHeal();
        entityData.set(DATA_BURNING_ALERT, isBurning() && getTarget() != null);
        tickBurningPassive();
        if (igniteHoldTicks > 0) {
            tickIgnitePose();
        } else if (isBurning() && tickCount % 10 == 0
                && getTarget() == null && getAttackAnimationTick() == 0) {
            startIgnitePose();
        }
        tickFireBreathCooldowns();
    }

    private void startIgnitePose() {
        BlockPos origin = blockPosition();
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        int scanH = GolemServantConfig.FURNACE.FURNACE_SCAN_HORIZONTAL.get();
        int scanV = GolemServantConfig.FURNACE.FURNACE_SCAN_VERTICAL.get();
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-scanH, -scanV, -scanH),
                origin.offset(scanH, scanV, scanH))) {
            if (!isIgnitableFurnace(pos)) continue;
            double d = pos.distToCenterSqr(getX(), getY(), getZ());
            if (d < bestDist) { bestDist = d; best = pos.immutable(); }
        }
        if (best == null) return;
        igniteTarget = best;
        igniteSegment = this.random.nextBoolean() ? IGNITE_SEGMENT_BACK : IGNITE_SEGMENT_FRONT;
        igniteHoldTicks = igniteTicks(igniteSegment);
        double dx = best.getX() + 0.5D - getX();
        double dz = best.getZ() + 0.5D - getZ();
        if (dx * dx + dz * dz > 1.0E-4D) {
            setRot((float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0D), 0.0F);
        }
        getNavigation().stop();
        level().broadcastEntityEvent(this, igniteSegment == IGNITE_SEGMENT_BACK
                ? IGNITE_BACK_EVENT : IGNITE_EVENT);
    }

    private void tickIgnitePose() {
        if (!isBurning() || getTarget() != null || !isAlive()) {
            igniteHoldTicks = 0;
            igniteTarget = null;
            return;
        }
        if (igniteTarget != null) {
            getLookControl().setLookAt(igniteTarget.getX() + 0.5D, igniteTarget.getY() + 0.6D,
                    igniteTarget.getZ() + 0.5D, 25.0F, 25.0F);
            getNavigation().stop();
            if (igniteTicks(igniteSegment) - igniteHoldTicks == igniteFuelTick(igniteSegment)) {
                igniteFurnace(igniteTarget);
            }
        }
        if (--igniteHoldTicks <= 0) igniteTarget = null;
    }

    private boolean isIgnitableFurnace(BlockPos pos) {
        BlockEntity be = level().getBlockEntity(pos);
        if (!(be instanceof AbstractFurnaceBlockEntity furnace)) return false;
        return !furnace.getItem(0).isEmpty() && furnace.getItem(1).isEmpty();
    }

    private void igniteFurnace(BlockPos pos) {
        if (!isIgnitableFurnace(pos)) return;
        BlockEntity be = level().getBlockEntity(pos);
        if (!(be instanceof AbstractFurnaceBlockEntity furnace)) return;
        furnace.setItem(1, new ItemStack(Items.COAL));
        furnace.setChanged();
        BlockState state = level().getBlockState(pos);
        if (state.hasProperty(AbstractFurnaceBlock.LIT)) {
            level().setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, true), 3);
        }
        level().playSound(null, pos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() + 0.8D,
                    pos.getZ() + 0.5D, 8, 0.2D, 0.2D, 0.2D, 0.01D);
        }
    }

    private void updateModifiers() {
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance damage = getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance armor = getAttribute(Attributes.ARMOR);
        if (speed == null || damage == null || health == null) return;
        remove(speed, SPEED_ID); remove(damage, DAMAGE_ID); remove(health, HEALTH_ID);
        if (armor != null) {
            remove(armor, COAL_ARMOR_ID);
            if (isBurning() && this.coalArmor > 0) {
                armor.addTransientModifier(new AttributeModifier(COAL_ARMOR_ID, "Furnace golem coal armor",
                        this.coalArmor, AttributeModifier.Operation.ADDITION));
            }
        }
        if (isBurning()) {
            double scale = fuelScale();
            double speedBonus = 0.25D + 0.25D * scale;
            speed.addTransientModifier(new AttributeModifier(SPEED_ID, "Furnace golem fuel speed",
                    speedBonus, AttributeModifier.Operation.MULTIPLY_BASE));
            damage.addTransientModifier(new AttributeModifier(DAMAGE_ID, "Furnace golem fuel damage",
                    1.0D + 2.0D * scale, AttributeModifier.Operation.ADDITION));
            if (scale >= 1.0D) {
                double bonus = GolemServantConfig.FURNACE.COAL_BLOCK_HEALTH_BONUS.get();
                health.addTransientModifier(new AttributeModifier(HEALTH_ID, "Furnace golem block fuel health",
                        bonus, AttributeModifier.Operation.ADDITION));
                if (!coalBlockHealthGranted) {
                    coalBlockHealthGranted = true;
                    setHealth((float) Math.min(getMaxHealth(), getHealth() + bonus));
                }
                if (getHealth() > getMaxHealth()) setHealth(getMaxHealth());
            }
        }
    }
    private static void remove(AttributeInstance a, UUID id) {
        AttributeModifier modifier = a.getModifier(id);
        if (modifier != null) a.removeModifier(modifier);
    }

    private void tickBurningHeal() {
        if (!isBurning() || !isAlive() || getHealth() >= getMaxHealth()
                || tickCount % BURNING_HEAL_INTERVAL_TICKS != 0) {
            return;
        }
        heal(BURNING_HEAL_AMOUNT);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == IGNITE_EVENT || id == IGNITE_BACK_EVENT) {
            int segment = id == IGNITE_BACK_EVENT ? IGNITE_SEGMENT_BACK : IGNITE_SEGMENT_FRONT;
            this.igniteSegment = segment;
            this.igniteAnimationTicks = igniteTicks(segment);
            this.igniteAnimationTick = this.igniteAnimationTicks;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isAlive()) {
            return;
        }
        if (this.igniteAnimationTick > 0) {
            --this.igniteAnimationTick;
        }
        tickMeleeImpact();
        if (isFireBreathing()) {
            getNavigation().stop();
        }
        tickFireBreath();
    }

    public int getIgniteAnimationTick() {
        return this.igniteAnimationTick;
    }

    public int getIgniteAnimationTicks() {
        return this.igniteAnimationTicks > 0 ? this.igniteAnimationTicks
                : igniteTicks(this.igniteSegment);
    }

    public int getIgniteSegment() {
        return this.igniteSegment;
    }

    public boolean isFireBreathing() {
        return isAttackAnimating() && getAttackAnimation() == ATTACK_FIRE_BREATH;
    }

    private boolean canFireBreathAt(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!isFireImmuneTarget(target)) {
            return true;
        }
        return ServantRobeFireDamage.masterWearsFireRobe(this);
    }

    private static boolean isFireImmuneTarget(LivingEntity target) {
        return target.fireImmune()
                || target.isInvulnerableTo(target.damageSources().onFire())
                || target.hasEffect(MobEffects.FIRE_RESISTANCE);
    }

    private void tickFireBreathCooldowns() {
        if (level().isClientSide) {
            return;
        }
        if (this.fireBreathCooldown > 0) {
            --this.fireBreathCooldown;
        }
        tickFireBreathDecision();
        tickQueuedFireBreath();
    }

    private void tickFireBreathDecision() {
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        if (!furnace.FIRE_BREATH_ENABLED.get() || !furnace.FIRE_BREATH_GUARANTEED_ENABLED.get()
                || !isBurning() || this.fireBreathCooldown > 0) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        if (isFireImmuneTarget(target)) {
            return;
        }
        double range = furnace.FIRE_BREATH_GUARANTEED_RANGE.get();
        if (distanceToSqr(target) <= range * range) {
            fireFireBreath(target);
        }
    }

    private boolean shouldYieldToGuaranteedFireBreath() {
        if (level().isClientSide) {
            return false;
        }
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        if (!furnace.FIRE_BREATH_ENABLED.get() || !furnace.FIRE_BREATH_GUARANTEED_ENABLED.get()
                || !isBurning() || this.fireBreathCooldown > 0) {
            return false;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (isFireImmuneTarget(target)) {
            return false;
        }
        double range = furnace.FIRE_BREATH_GUARANTEED_RANGE.get();
        return distanceToSqr(target) <= range * range;
    }

    private void tryQueueMeleeFireBreath(Entity attacked) {
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        if (level().isClientSide || !isAlive() || !isBurning() || this.fireBreathCooldown > 0
                || this.fireBreathQueued || !furnace.FIRE_BREATH_ENABLED.get()
                || !furnace.FIRE_BREATH_GUARANTEED_ENABLED.get()
                || !(attacked instanceof LivingEntity target) || !target.isAlive()
                || target == this || isAlliedTo(target) || !canAttack(target)
                || !isFireImmuneTarget(target) || !canFireBreathAt(target)) {
            return;
        }
        double range = furnace.FIRE_BREATH_GUARANTEED_RANGE.get();
        if (distanceToSqr(target) > range * range || this.random.nextInt(10) != 0) {
            return;
        }
        this.fireBreathQueued = true;
        this.fireBreathQueuedByMelee = true;
        this.fireBreathQueuedTarget = target;
    }

    private void clearQueuedFireBreath() {
        this.fireBreathQueued = false;
        this.fireBreathQueuedByMelee = false;
        this.fireBreathQueuedTarget = null;
    }

    private void fireFireBreath(LivingEntity target) {
        Vec3 aim = pickClusterAim(nearbyEnemies(FIRE_BREATH_CLUSTER_RADIUS), target);
        if (aim == null) {
            aim = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        }
        if (isAttackAnimating()) {
            this.fireBreathQueued = true;
            this.fireBreathQueuedByMelee = false;
            this.fireBreathQueuedTarget = null;
            return;
        }
        this.fireBreathTarget = target;
        beginFireBreath(aim);
    }

    private void beginFireBreath(Vec3 aim) {
        clearQueuedFireBreath();
        this.fireBreathAimX = aim.x;
        this.fireBreathAimY = aim.y;
        this.fireBreathAimZ = aim.z;
        this.fireBreathDamageTimer = 0;
        this.fireBreathSoundPlayed = false;
        this.fireBreathLavaTimer = GolemServantConfig.FURNACE.FIRE_BREATH_LAVA_INTERVAL_TICKS.get();
        this.fireBreathFireballTimer = FIRE_BREATH_FIREBALL_INTERVAL_TICKS;
        this.fireBreathYaw = Mth.wrapDegrees(getYRot());
        this.fireBreathPitch = getXRot();
        this.fireBreathAimSeeded = false;
        this.fireBreathCooldown = GolemServantConfig.FURNACE.FIRE_BREATH_GUARANTEED_COOLDOWN_TICKS.get();
        getLookControl().setLookAt(aim.x, aim.y, aim.z, FIRE_BREATH_LOOK_DEGREES, FIRE_BREATH_LOOK_DEGREES);
        startAttack(ATTACK_FIRE_BREATH, ATTACK_LENGTHS[ATTACK_FIRE_BREATH]);
        playSound(ModSounds.FIRE_BREATH_START.get(), 1.0F, 1.0F);
    }

    private void tickQueuedFireBreath() {
        if (!this.fireBreathQueued || level().isClientSide) {
            return;
        }
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        LivingEntity target = getTarget();
        if (!isAlive() || !isBurning() || !furnace.FIRE_BREATH_ENABLED.get()
                || !furnace.FIRE_BREATH_GUARANTEED_ENABLED.get()
                || target == null || !target.isAlive() || !canAttack(target) || isAlliedTo(target)
                || !canFireBreathAt(target)
                || (isFireImmuneTarget(target) && !this.fireBreathQueuedByMelee)
                || (this.fireBreathQueuedByMelee && (target != this.fireBreathQueuedTarget
                        || !ServantRobeFireDamage.masterWearsFireRobe(this)))) {
            clearQueuedFireBreath();
            return;
        }
        double range = furnace.FIRE_BREATH_GUARANTEED_RANGE.get();
        if (distanceToSqr(target) > range * range) {
            clearQueuedFireBreath();
            return;
        }
        if (this.fireBreathCooldown > 0 || isFireBreathing() || isAttackAnimating()) {
            return;
        }
        clearQueuedFireBreath();
        Vec3 aim = pickClusterAim(nearbyEnemies(FIRE_BREATH_CLUSTER_RADIUS), target);
        if (aim == null) {
            aim = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        }
        this.fireBreathTarget = target;
        beginFireBreath(aim);
    }

    private void tickFireBreath() {
        if (!isFireBreathing() || !attackReached(FIRE_BREATH_START_TICK)
                || attackReached(FIRE_BREATH_END_TICK + 1)) {
            return;
        }
        if (!level().isClientSide) {
            refreshFireBreathAim();
            faceFireBreathAim();
            if (!this.fireBreathSoundPlayed) {
                this.fireBreathSoundPlayed = true;
                playSound(GeneratedMod.FURNACE_FIRE_BREATH.get(), FIRE_BREATH_SOUND_VOLUME, 1.0F);
            }
        }
        emitFireBreathParticles();
        if (level().isClientSide) {
            return;
        }
        if (--this.fireBreathLavaTimer <= 0) {
            this.fireBreathLavaTimer = GolemServantConfig.FURNACE.FIRE_BREATH_LAVA_INTERVAL_TICKS.get();
            applyFireBreathLavaZone();
        }
        if (--this.fireBreathFireballTimer <= 0) {
            this.fireBreathFireballTimer = FIRE_BREATH_FIREBALL_INTERVAL_TICKS;
            shootFireBreathFireball();
        }
        if (this.fireBreathDamageTimer > 0) {
            --this.fireBreathDamageTimer;
            return;
        }
        this.fireBreathDamageTimer = GolemServantConfig.FURNACE.FIRE_BREATH_DAMAGE_INTERVAL.get();
        castFireBreath();
    }

    private void refreshFireBreathAim() {
        Vec3 cluster = pickClusterAim(nearbyEnemies(FIRE_BREATH_CLUSTER_RADIUS), this.fireBreathTarget);
        if (cluster != null) {
            this.fireBreathAimX = cluster.x;
            this.fireBreathAimY = cluster.y;
            this.fireBreathAimZ = cluster.z;
            return;
        }
        LivingEntity target = this.fireBreathTarget;
        if (target == null || !target.isAlive()) {
            return;
        }
        this.fireBreathAimX = target.getX();
        this.fireBreathAimY = target.getY() + target.getBbHeight() * 0.5D;
        this.fireBreathAimZ = target.getZ();
    }

    private void faceFireBreathAim() {
        if (!this.fireBreathAimSeeded) {
            this.fireBreathAimSeeded = true;
            this.fireBreathYaw = Mth.wrapDegrees(getYRot());
            this.fireBreathPitch = getXRot();
        }
        double dx = this.fireBreathAimX - getX();
        double dy = this.fireBreathAimY - getEyeY();
        double dz = this.fireBreathAimZ - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float) (Mth.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        float targetPitch = (float) (-(Mth.atan2(dy, horizontal) * 180.0D / Math.PI));
        targetPitch = Mth.clamp(targetPitch, -FIRE_BREATH_MAX_UP_PITCH, FIRE_BREATH_MAX_DOWN_PITCH);
        this.fireBreathYaw = Mth.wrapDegrees(approachDegrees(
                this.fireBreathYaw, targetYaw, FIRE_BREATH_TURN_DEGREES_PER_TICK));
        this.fireBreathPitch = approachDegrees(
                this.fireBreathPitch, targetPitch, FIRE_BREATH_PITCH_DEGREES_PER_TICK);
        setYRot(this.fireBreathYaw);
        this.yBodyRot = this.fireBreathYaw;
        this.yHeadRot = this.fireBreathYaw;
        setXRot(this.fireBreathPitch);
    }

    private static float approachDegrees(float current, float target, float maxStep) {
        return current + Mth.clamp(Mth.wrapDegrees(target - current), -maxStep, maxStep);
    }

    private List<LivingEntity> nearbyEnemies(double radius) {
        return level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(radius, radius, radius),
                candidate -> candidate != this && candidate.isAlive()
                        && !isAlliedTo(candidate) && canAttack(candidate)
                        && distanceToSqr(candidate) <= radius * radius);
    }

    private Vec3 pickClusterAim(List<LivingEntity> enemies, LivingEntity target) {
        Vec3 origin = position();
        Vec3 anchor = null;
        if (target != null && target.isAlive()) {
            Vec3 toTarget = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(origin);
            if (toTarget.lengthSqr() > 1.0E-4D) {
                anchor = toTarget.normalize();
            }
        }
        double cosLimit = Math.cos(Math.toRadians(35.0D));
        double frontLimit = Math.cos(Math.toRadians(FIRE_BREATH_FRONT_ARC_DEGREES));
        Vec3 best = null;
        int bestCount = 0;
        for (LivingEntity candidate : enemies) {
            Vec3 dir = candidate.position().subtract(origin);
            if (dir.lengthSqr() < 1.0E-4D) {
                continue;
            }
            dir = dir.normalize();
            if (anchor != null && dir.dot(anchor) < frontLimit) {
                continue;
            }
            int count = 0;
            for (LivingEntity other : enemies) {
                Vec3 otherDir = other.position().subtract(origin);
                if (otherDir.lengthSqr() < 1.0E-4D) {
                    continue;
                }
                if (dir.dot(otherDir.normalize()) >= cosLimit) {
                    ++count;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                best = candidate.position().add(0.0D, candidate.getBbHeight() * 0.5D, 0.0D);
            }
        }
        return best;
    }

    private void emitFireBreathParticles() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 look = getLookAngle();
        Vec3 chest = new Vec3(getX(), getY() + getBbHeight() * 0.6D, getZ()).add(look.scale(0.7D));
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        double reach = Math.max(1.0D, FIRE_BREATH_BASE_RANGE + furnace.FIRE_BREATH_RANGE_LEVEL.get());
        double radius = furnace.FIRE_BREATH_RADIUS.get();
        for (int i = 0; i < 24; i++) {
            double distance = 0.2D + this.random.nextDouble() * reach;
            double spread = 0.3D + radius * (distance / reach);
            double ox = (this.random.nextDouble() - 0.5D) * spread;
            double oy = (this.random.nextDouble() - 0.5D) * spread;
            double oz = (this.random.nextDouble() - 0.5D) * spread;
            server.sendParticles(ParticleTypes.SMALL_FLAME,
                    chest.x + look.x * distance + ox,
                    chest.y + look.y * distance + oy,
                    chest.z + look.z * distance + oz,
                    0, look.x, look.y, look.z, 0.5D);
        }
        for (int i = 0; i < 12; i++) {
            double distance = 0.4D + this.random.nextDouble() * reach;
            double spread = radius * (distance / reach);
            server.sendParticles(ParticleTypes.FLAME,
                    chest.x + look.x * distance + (this.random.nextDouble() - 0.5D) * spread,
                    chest.y + look.y * distance + (this.random.nextDouble() - 0.5D) * spread,
                    chest.z + look.z * distance + (this.random.nextDouble() - 0.5D) * spread,
                    0, look.x, look.y, look.z, 0.3D);
        }
        for (int i = 0; i < 6; i++) {
            server.sendParticles(ParticleTypes.LAVA,
                    chest.x + (this.random.nextDouble() - 0.5D) * 0.5D,
                    chest.y + (this.random.nextDouble() - 0.5D) * 0.4D,
                    chest.z + (this.random.nextDouble() - 0.5D) * 0.5D,
                    0, 0.0D, 0.15D, 0.0D, 0.1D);
        }
    }

    private void castFireBreath() {
        if (!(level() instanceof ServerLevel)) {
            return;
        }
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        int range = FIRE_BREATH_BASE_RANGE + furnace.FIRE_BREATH_RANGE_LEVEL.get();
        int burning = furnace.FIRE_BREATH_BURNING.get();
        WidenedFireBreathSpell spell = new WidenedFireBreathSpell();
        spell.damage = furnace.FIRE_BREATH_DAMAGE.get().floatValue();
        spell.mobSpellResult(this, ItemStack.EMPTY,
                new SpellStat(0, 1, range, 0.0D, burning, 0.0F));
    }

    private void applyFireBreathLavaZone() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        float damage = GolemServantConfig.FURNACE.FIRE_BREATH_LAVA_DAMAGE.get().floatValue();
        if (damage <= 0.0F) {
            return;
        }
        Vec3 look = getLookAngle();
        double fx = getX() + look.x * FIRE_BREATH_LAVA_AHEAD;
        double fz = getZ() + look.z * FIRE_BREATH_LAVA_AHEAD;
        AABB area = new AABB(
                fx - FIRE_BREATH_LAVA_RADIUS, getY() - 0.5D, fz - FIRE_BREATH_LAVA_RADIUS,
                fx + FIRE_BREATH_LAVA_RADIUS, getY() + getBbHeight() + 0.5D, fz + FIRE_BREATH_LAVA_RADIUS);
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || !victim.isAlive() || isAlliedTo(victim)) {
                continue;
            }
            victim.hurt(damageSources().lava(), damage);
        }
    }

    private void shootFireBreathFireball() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 from = new Vec3(getX(), getY() + getBbHeight() * 0.66D, getZ());
        Vec3 dir = new Vec3(this.fireBreathAimX - from.x, this.fireBreathAimY - from.y,
                this.fireBreathAimZ - from.z);
        if (dir.lengthSqr() < 1.0E-6D) {
            dir = getLookAngle();
        }
        if (dir.lengthSqr() < 1.0E-6D) {
            return;
        }
        dir = dir.normalize();
        FurnaceGolemFireball fireball =
                new FurnaceGolemFireball(level(), this, dir.x, dir.y, dir.z);
        fireball.setPos(from.x, from.y, from.z);
        server.addFreshEntity(fireball);
        playSound(SoundEvents.BLAZE_SHOOT, 1.2F,
                1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
    }

    private void tickBurningPassive() {
        if (level().isClientSide || !isBurning()) {
            return;
        }
        GolemServantConfig.FurnaceSection furnace = GolemServantConfig.FURNACE;
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        int interval = Math.max(1, furnace.BURNING_PARTICLE_INTERVAL_TICKS.get());
        if (tickCount % interval == 0) {
            double radius = furnace.BURNING_PARTICLE_RADIUS.get();
            for (int i = 0; i < 3; i++) {
                double ox = (this.random.nextDouble() - 0.5D) * 2.0D * radius;
                double oy = this.random.nextDouble() * getBbHeight();
                double oz = (this.random.nextDouble() - 0.5D) * 2.0D * radius;
                server.sendParticles(ParticleTypes.FLAME, getX() + ox, getY() + oy, getZ() + oz,
                        0, 0.0D, 0.05D, 0.0D, 0.0D);
            }
        }
        if (blockFuel()) {
            int auraInterval = Math.max(1, furnace.COAL_BLOCK_AURA_INTERVAL_TICKS.get());
            if (tickCount % auraInterval == 0) {
                double radius = furnace.COAL_BLOCK_AURA_RADIUS.get();
                AABB area = getBoundingBox().inflate(radius);
                for (LivingEntity nearby : server.getEntitiesOfClass(LivingEntity.class, area)) {
                    if (nearby == this || !nearby.isAlive() || isAlliedTo(nearby)) {
                        continue;
                    }
                    if (distanceToSqr(nearby) > radius * radius * 4.0D) {
                        continue;
                    }
                    nearby.setSecondsOnFire(furnace.COAL_BLOCK_AURA_IGNITE_SECONDS.get());
                }
            }
        }
    }

    private static final class WidenedFireBreathSpell extends FireBreathSpell {
        @Override
        public List<Entity> getBreathTarget(LivingEntity caster, double range) {
            return AbstractGolemServant.breathTargets(caster, range,
                    GolemServantConfig.FURNACE.FIRE_BREATH_RADIUS.get());
        }
    }

    @Override
    public int attackSwingAnimation() {
        return ATTACK_FURNACE_SWING;
    }

    @Override
    public float[] recoveryImpactTicks() {
        return getAttackAnimation() == ATTACK_FURNACE_SWING
                ? AbstractGolemServant.FURNACE_SWING_RECOVERY_IMPACT
                : null;
    }

    @Override
    public int meleeAnticipationTicks() {
        return 16;
    }

    @Override
    public double meleeAnticipationMax() {
        return 3.0D;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.goalSelector.addGoal(1, new FurnaceMeleeGoal(this, 1.0D, true));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().isClientSide) {
            return false;
        }
        if (isAttackAnimating() || this.fireBreathQueued) {
            return false;
        }
        this.pendingAttackTarget = target;
        this.attackImpactDone = false;
        int animation = attackSwingAnimation();
        startAttack(animation, ATTACK_LENGTHS[animation]);
        tryQueueMeleeFireBreath(target);
        return true;
    }

    private void tickMeleeImpact() {
        if (level().isClientSide || this.attackImpactDone || this.pendingAttackTarget == null
                || !isAttackAnimating()) {
            return;
        }
        if (!attackReached(ATTACK_IMPACT_TICK)) {
            return;
        }
        this.attackImpactDone = true;
        Entity target = this.pendingAttackTarget;
        this.pendingAttackTarget = null;
        if (target instanceof LivingEntity living && living.isAlive() && !isAlliedTo(living)
                && distanceToSqr(living) <= meleeReachSqr(living) * ATTACK_IMPACT_REACH_TOLERANCE) {
            applyMeleeHit(living);
        }
        applyFurnaceMeleeAoe(target);
    }

    private void applyFurnaceMeleeAoe(Entity primaryTarget) {
        if (!(level() instanceof ServerLevel)) {
            return;
        }
        double radius = FURNACE_MELEE_AOE_RADIUS;
        AABB area = getBoundingBox().inflate(radius, radius * 0.75D, radius);
        float attackDamage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area, this::canAttack)) {
            if (victim == this || victim == primaryTarget || isAlliedTo(victim)
                    || distanceToSqr(victim) > radius * radius || victim.isRemoved()) {
                continue;
            }
            if (victim.hurt(damageSources().mobAttack(this), attackDamage)) {
                applyMeleeHitKnockback(victim);
                doEnchantDamageEffects(this, victim);
                onAttackHit(victim);
            }
        }
    }

    @Override
    public float attackDamageFloorRatio() {
        return 0.6F;
    }

    @Override
    public void applyMeleeHitKnockback(Entity target) {
        applyVanillaGolemKnockback(target);
    }

    private double meleeReachSqr(LivingEntity target) {
        double reach = getBbWidth() * 2.0D;
        return reach * reach + target.getBbWidth();
    }

    private static final class FurnaceMeleeGoal extends MeleeAttackGoal {
        private final FurnaceGolem golem;

        FurnaceMeleeGoal(FurnaceGolem golem, double speed, boolean followTarget) {
            super(golem, speed, followTarget);
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            return !golem.isAttackAnimating() && !golem.shouldYieldToGuaranteedFireBreath()
                    && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return golem.isAttackAnimating() || super.canContinueToUse();
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
            if (golem.isAttackAnimating()) {
                return;
            }
            if (meleeGapDistanceSqr(golem, target) <= golem.meleeAnticipatedReachSqr(target)
                    && this.isTimeToAttack()) {
                golem.doHurtTarget(target);
                this.resetAttackCooldown();
            }
        }
    }

    @Override public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level().isClientSide && AbstractFurnaceBlockEntity.isFuel(stack)) {
            int duration = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
            if (duration <= 0) return InteractionResult.PASS;
            boolean coalBlock = stack.is(Items.COAL_BLOCK);
            boolean coal = !coalBlock && stack.is(Items.COAL);
            if (!player.getAbilities().instabuild) stack.shrink(1);
            int old = getBurnTicks();
            int next = Math.min(MAX_BURN_TICKS, old + duration);
            setBurnTicks(next);
            entityData.set(DATA_BURN_DURATION, Math.max(fuelDuration(), Math.min(COAL_BLOCK_TICKS, duration)));
            if (coalBlock) {
                addCoalArmor(COAL_ARMOR_PER_BLOCK);
            } else if (coal) {
                addCoalArmor(COAL_ARMOR_PER_COAL);
            }
            level().playSound(null, this, SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
            level().playSound(null, this, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.NEUTRAL, 1, 1);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private void addCoalArmor(int amount) {
        if (amount <= 0 || this.coalArmor > 0) {
            return;
        }
        this.coalArmor = amount;
        level().playSound(null, this, SoundEvents.ANVIL_USE, SoundSource.NEUTRAL, 0.8F,
                1.2F + random.nextFloat() * 0.2F);
        if (level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME, getX(), getY() + getBbHeight() * 0.6D, getZ(),
                    6, 0.35D, 0.5D, 0.35D, 0.02D);
        }
    }

    public int getCoalArmor() {
        return this.coalArmor;
    }
    @Override
    public void onAttackHit(Entity target) {
        if (level().isClientSide || !(target instanceof LivingEntity living)) return;
        double scale = isBurning() ? Math.max(1.0D, fuelScale()) : 0.0D;
        int fireDamage = isBurning() ? (int) Math.round(1.0D + 2.0D * scale) : 1;
        int fireSeconds = isBurning() ? (int) Math.round(5.0D + 5.0D * scale) : ATTACK_FIRE_SECONDS;
        applyBonusDamage(living, damageSources().onFire(), fireDamage);
        living.setSecondsOnFire(fireSeconds);
    }
    @Override public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        Entity attacker = source.getDirectEntity();
        if (result && isBurning() && attacker instanceof LivingEntity living
                && source.getEntity() == attacker && distanceToSqr(attacker) <= 25.0D) {
            applyBonusDamage(living, damageSources().onFire(), 1.0F);
            living.setSecondsOnFire(BURNING_RETALIATION_SECONDS);
        }
        return result;
    }
    @Override public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("BurnTicks", getBurnTicks());
        tag.putInt("BurnDuration", fuelDuration());
        tag.putInt("CoalArmor", this.coalArmor);
    }
    @Override public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setBurnTicks(tag.getInt("BurnTicks"));
        entityData.set(DATA_BURN_DURATION, Math.min(COAL_BLOCK_TICKS, Math.max(0, tag.getInt("BurnDuration"))));
        this.coalArmor = getBurnTicks() > 0
                ? Math.max(0, Math.min(COAL_ARMOR_PER_BLOCK, tag.getInt("CoalArmor")))
                : 0;
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        spawnAtLocation(new ItemStack(Items.IRON_INGOT, 1 + random.nextInt(2)), 0.3F);
        OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.REINFORCED_BLAST_FURNACE.get()));
    }
}
