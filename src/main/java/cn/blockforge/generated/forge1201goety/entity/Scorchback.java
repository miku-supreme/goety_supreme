package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.SlamParticleOption;
import com.Polarice3.Goety.client.particles.SparkleParticleOption;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.util.ScorchbackCombatMath;
import cn.blockforge.generated.goetysupreme.util.ScorchbackComboSweep;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackQuill;
import cn.blockforge.generated.goetysupreme.util.ScorchbackRainSockets;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.api.SupremeChargeAuraHolder;

public class Scorchback extends Summoned implements SupremeChargeAuraHolder {

    public static final int SPAWN_TICKS = 28;
    public static final int COMBO_TICKS = 58;
    public static final int SLAM_TICKS = 36;
    public static final int CHARGE_TICKS = 162;
    public static final int RAIN_TICKS = 131;

    private static final int COMBO_HIT_1 = ScorchbackCombatMath.COMBO_HIT_1;
    private static final int COMBO_HIT_2 = ScorchbackCombatMath.COMBO_HIT_2;
    private static final int COMBO_SLAM = ScorchbackCombatMath.COMBO_SLAM;

    private static final int SLAM_IMPACT = 24;

    private static final int CHARGE_GO = 18;
    private static final int CHARGE_END = 150;

    static final double MELEE_REACH = 4.6D;

    private static final double STAY_CHASE_RANGE = 8.0D;

    static final double CHASE_SPEED = 1.15D;

    static final double OWNER_TARGET_LEASH = 32.0D;

    private static final ResourceLocation BUSTED_EFFECT_ID = ResourceLocation.fromNamespaceAndPath("goety", "busted");
    private static final int BUSTED_TICKS = 20;

    private static final int ATTACK_FIRE_SECONDS = 5;

    private static final double FLUID_SURFACE_EPSILON = 0.02D;

    private static final double FLUID_SURFACE_SNAP = 0.75D;

    private static final float CHARGE_BASE_DAMAGE = 15.0F;
    private static final float CHARGE_MAX_HEALTH_PERCENT = 0.04F;

    private static final double CHARGE_LEAP_IMPULSE = 0.55D;

    private static final float MAX_UP_STEP = 1.0F;

    private static final float FOOTSTEP_MIN_SPEED = 0.05F;

    private static final EntityDataAccessor<Integer> DATA_ACTION =
            SynchedEntityData.defineId(Scorchback.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ACTION_TICK =
            SynchedEntityData.defineId(Scorchback.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ACTION_YAW =
            SynchedEntityData.defineId(Scorchback.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_BABY =
            SynchedEntityData.defineId(Scorchback.class, EntityDataSerializers.BOOLEAN);

    public static final int GROW_TICKS = 24000;

    private static final int FEED_GROW_STEP = GROW_TICKS / 20;

    private static final double BABY_HEALTH = 200.0D;
    private int growTimer;

    private float digestHeal;

    private static final int DIGEST_INTERVAL = 8;
    private int digestTimer;

    private static final float NETHER_WART_HEAL = 1.0F;

    private Vec3 rainAim = Vec3.ZERO;

    int comboCooldown;
    int slamCooldown;
    int chargeCooldown;
    int rainCooldown;

    private int lastFootfallIndex = -1;

    public Scorchback(EntityType<? extends Summoned> entityType, Level level) {
        super(entityType, level);
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.setMaxUpStep(MAX_UP_STEP);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400.0D)
                .add(Attributes.ARMOR, 16.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3125D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void setConfigurableAttributes() {
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(this.getType());
    }

    @Override
    public boolean isSunSensitive() {
        return false;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.targetSelector.addGoal(0, new OwnerTargetGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new ScorchbackCombatGoal(this));
        this.goalSelector.addGoal(3, new ScorchbackChargeGoal(this));
        this.goalSelector.addGoal(4, new ScorchbackRainGoal(this));
        this.goalSelector.addGoal(6, new ScorchbackApproachGoal(this));
        this.goalSelector.addGoal(7, new ServantWanderGoal(this, 1.0D, 0.001F, () -> !this.isBusy()));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F) {
            @Override public boolean canUse() { return !Scorchback.this.isBusy() && super.canUse(); }
            @Override public boolean canContinueToUse() {
                return !Scorchback.this.isBusy() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this) {
            @Override public boolean canUse() { return !Scorchback.this.isBusy() && super.canUse(); }
            @Override public boolean canContinueToUse() {
                return !Scorchback.this.isBusy() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ACTION, Action.NONE.ordinal());
        this.entityData.define(DATA_ACTION_TICK, 0);
        this.entityData.define(DATA_ACTION_YAW, 0.0F);
        this.entityData.define(DATA_BABY, false);
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(DATA_BABY);
    }

    public void setBaby(boolean baby) {
        this.entityData.set(DATA_BABY, baby);
        if (!this.level().isClientSide()) {
            net.minecraft.world.entity.ai.attributes.AttributeInstance health =
                    this.getAttribute(Attributes.MAX_HEALTH);
            if (health != null) {
                health.setBaseValue(baby ? BABY_HEALTH : 400.0D);
            }
            this.setHealth(this.getMaxHealth());
            this.refreshDimensions();
        }
    }

    @Override
    public net.minecraft.world.entity.EntityDimensions getDimensions(net.minecraft.world.entity.Pose pose) {
        return this.isBaby() ? super.getDimensions(pose).scale(0.5F) : super.getDimensions(pose);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ScorchbackAction", this.getAction().ordinal());
        tag.putInt("ScorchbackActionTick", this.getActionTick());
        tag.putFloat("ScorchbackDigest", this.digestHeal);
        tag.putBoolean("ScorchbackBaby", this.isBaby());
        tag.putInt("ScorchbackGrow", this.growTimer);
        tag.putFloat("ScorchbackActionYaw", this.getActionYaw());
        tag.putInt("ScorchbackChargeCooldown", this.chargeCooldown);
        tag.putDouble("RainAimX", this.rainAim.x);
        tag.putDouble("RainAimY", this.rainAim.y);
        tag.putDouble("RainAimZ", this.rainAim.z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ScorchbackAction")) {
            this.setAction(Action.values()[Mth.clamp(tag.getInt("ScorchbackAction"), 0,
                    Action.values().length - 1)]);
        }
        this.setActionTick(tag.getInt("ScorchbackActionTick"));
        this.digestHeal = tag.getFloat("ScorchbackDigest");
        if (tag.getBoolean("ScorchbackBaby")) {
            this.setBaby(true);
        }
        this.growTimer = tag.getInt("ScorchbackGrow");
        this.entityData.set(DATA_ACTION_YAW, tag.contains("ScorchbackActionYaw")
                ? tag.getFloat("ScorchbackActionYaw") : this.getYRot());
        this.chargeCooldown = Math.max(0, tag.getInt("ScorchbackChargeCooldown"));
        this.rainAim = tag.contains("RainAimX")
                ? new Vec3(tag.getDouble("RainAimX"), tag.getDouble("RainAimY"), tag.getDouble("RainAimZ"))
                : this.position().add(ScorchbackCombatMath.forward(this.getActionYaw()).scale(10.0D));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (!this.level().isClientSide() && this.getAction() == Action.NONE) {
            this.startAction(Action.SPAWN);
        }
    }

    public Action getAction() {
        return Action.values()[this.entityData.get(DATA_ACTION)];
    }

    public void setAction(Action action) {
        this.entityData.set(DATA_ACTION, action.ordinal());
    }

    public int getActionTick() {
        return this.entityData.get(DATA_ACTION_TICK);
    }

    public void setActionTick(int tick) {
        this.entityData.set(DATA_ACTION_TICK, tick);
    }

    public void startAction(Action action) {
        this.setAction(action);
        this.setActionTick(0);
        this.entityData.set(DATA_ACTION_YAW, this.getYRot());
        this.updateActionFacing(true);
        if (action == Action.RAIN) {
            LivingEntity target = this.getTarget();
            this.rainAim = this.canAttackTarget(target) ? target.position()
                    : this.position().add(ScorchbackCombatMath.forward(this.getActionYaw()).scale(10.0D));
        }
    }

    public float getActionYaw() {
        return this.entityData.get(DATA_ACTION_YAW);
    }

    public boolean hasActionFacing() {
        Action action = this.getAction();
        return action == Action.COMBO || action == Action.SLAM || action == Action.CHARGE || action == Action.RAIN;
    }

    private void updateActionFacing(boolean starting) {
        if (!this.hasActionFacing()) {
            return;
        }
        float yaw = this.getActionYaw();
        LivingEntity target = this.getTarget();
        if (this.canAttackTarget(target)) {
            float wanted = ScorchbackCombatMath.yawTo(this.position(), target.position(), yaw);
            yaw = starting ? wanted : Mth.approachDegrees(yaw, wanted,
                    this.getAction() == Action.CHARGE ? 6.0F : 12.0F);
        }
        this.entityData.set(DATA_ACTION_YAW, Mth.wrapDegrees(yaw));
        this.applyActionFacing();
        if (starting) {
            this.yBodyRotO = this.yBodyRot;
            this.yHeadRotO = this.yHeadRot;
            this.yRotO = this.getYRot();
        }
    }

    private void applyActionFacing() {
        float yaw = this.getActionYaw();
        this.setYRot(yaw);
        this.yBodyRot = yaw;
        this.yHeadRot = yaw;
        this.setXRot(0.0F);
    }

    @Override
    public float tickHeadTurn(float yaw, float speed) {
        if (this.hasActionFacing()) {
            this.applyActionFacing();
            return speed;
        }
        return super.tickHeadTurn(yaw, speed);
    }

    public boolean isChargeDashing() {
        int tick = this.getActionTick();
        return this.getAction() == Action.CHARGE && tick >= CHARGE_GO && tick < CHARGE_END;
    }

    @Override
    public boolean isPushable() {
        return !this.isChargeDashing() && super.isPushable();
    }

    public boolean isBusy() {
        return this.getAction() != Action.NONE;
    }

    @Nullable
    public String currentClipKey() {
        return switch (this.getAction()) {
            case SPAWN -> "spawn";
            case COMBO -> "combo";
            case SLAM -> "slam";
            case CHARGE -> "charge";
            case RAIN -> "rain";
            default -> null;
        };
    }

    private SupremeChargeAura supremeChargeAura;

    @Override
    public SupremeChargeAura supremeChargeAura() {
        if (this.supremeChargeAura == null) {
            this.supremeChargeAura = SupremeChargeAura.of(SupremeChargeAura.Theme.MAGMA);
        }
        return this.supremeChargeAura;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.walkFluidSurface();
            this.tickAnimationDrivenFootsteps();
            this.tickDigest();
            this.tickGrowth();
            this.tickCooldowns();
            this.advanceAction();
        } else {
            this.supremeChargeAura().tick(this, this.isChargeDashing());
        }
    }

    private void tickGrowth() {
        if (!this.isBaby()) {
            return;
        }
        if (++this.growTimer >= GROW_TICKS) {
            this.growTimer = 0;
            this.setBaby(false);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                        this.getRandomX(1.5D), this.getY() + 1.0D, this.getRandomZ(1.5D),
                        20, 0.3D, 0.4D, 0.3D, 0.5D);
            }
        }
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        int count = 1 + this.getRandom().nextInt(2) + Math.max(0, looting);
        for (int i = 0; i < count; i++) {
            this.spawnAtLocation(new ItemStack(GeneratedMod.SCORCHBACK_EGG.get()));
        }
    }

    private void tickCooldowns() {
        if (this.comboCooldown > 0) {
            this.comboCooldown--;
        }
        if (this.slamCooldown > 0) {
            this.slamCooldown--;
        }
        if (this.chargeCooldown > 0) {
            this.chargeCooldown--;
        }
        if (this.rainCooldown > 0) {
            this.rainCooldown--;
        }
    }

    private void advanceAction() {
        Action action = this.getAction();
        if (action == Action.NONE) {
            return;
        }
        int t = this.getActionTick();
        this.updateActionFacing(false);
        this.getNavigation().stop();
        switch (action) {
            case SPAWN -> {
                if (t >= SPAWN_TICKS) {
                    this.setAction(Action.NONE);
                }
            }
            case COMBO -> {
                this.tickCombo(t);
                if (t >= COMBO_TICKS) {
                    this.setAction(Action.NONE);
                }
            }
            case SLAM -> {
                this.tickSlam(t);
                if (t >= SLAM_TICKS) {
                    this.setAction(Action.NONE);
                }
            }
            case CHARGE -> {
                this.tickCharge(t);
                if (t >= CHARGE_TICKS) {
                    this.setAction(Action.NONE);
                    this.chargeCooldown = ScorchbackCombatMath.CHARGE_COOLDOWN;
                }
            }
            case RAIN -> {
                this.tickRain(t);
                if (t >= RAIN_TICKS) {
                    this.setAction(Action.NONE);
                }
            }
            default -> this.setAction(Action.NONE);
        }
        this.setActionTick(this.getAction() == Action.NONE ? 0 : t + 1);
    }

    private void tickCombo(int t) {
        this.emitComboSweep(t - COMBO_HIT_1, false);
        this.emitComboSweep(t - COMBO_HIT_2, true);
        this.emitGroundWave(ScorchbackCombatMath.COMBO_SLAM_RADIUS, t - COMBO_SLAM);
        if (t == COMBO_HIT_1 || t == COMBO_HIT_2) {
            this.hitFan(3.8D, 20.0F, true);
            this.playSound(GeneratedMod.SCORCHBACK_ATTACK.get(), 1.2F, 0.95F + t * 0.002F);
            if (this.level() instanceof ServerLevel server) {
                Vec3 at = ScorchbackCombatMath.localToWorld(ScorchbackComboSweep.localPosition(t),
                        this.position(), this.getActionYaw(), this.isBaby() ? 0.5D : 1.0D);
                server.sendParticles(ParticleTypes.CRIT,
                        at.x, at.y, at.z,
                        12, 0.4D, 0.3D, 0.4D, 0.2D);
            }
        } else if (t == COMBO_SLAM) {
            this.redstoneSmashFx(ScorchbackCombatMath.COMBO_SLAM_RADIUS);
            this.slamGround(ScorchbackCombatMath.COMBO_SLAM_RADIUS, 20.0F, 0.9D);
        }
    }

    private void emitComboSweep(int age, boolean reverse) {
        if (age < -1 || age > 5 || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        int tick = (reverse ? COMBO_HIT_2 : COMBO_HIT_1) + age;
        double size = this.isBaby() ? 0.5D : 1.0D;
        Vec3 forward = ScorchbackCombatMath.forward(this.getActionYaw());
        for (int i = 0; i < 6; i++) {
            Vec3 at = ScorchbackCombatMath.localToWorld(
                    ScorchbackComboSweep.localPosition(tick - 1.0F + i / 5.0F),
                    this.position(), this.getActionYaw(), size);
            server.sendParticles(new SparkleParticleOption(0.95F, 1.0F, 0.38F, 0.10F, 8),
                    at.x, at.y, at.z, 1, 0.025D, 0.025D, 0.025D, 0.0D);
            server.sendParticles(ParticleTypes.FLAME, at.x, at.y, at.z,
                    0, forward.x * 0.025D, 0.01D, forward.z * 0.025D, 1.0D);
        }
        if (age == 0) {
            Vec3 at = ScorchbackCombatMath.localToWorld(ScorchbackComboSweep.localPosition(tick),
                    this.position(), this.getActionYaw(), size);
            server.sendParticles(ParticleTypes.SWEEP_ATTACK, at.x, at.y, at.z,
                    0, 0.0D, 0.0D, 0.0D, 0.0D);
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.15F, reverse ? 0.8F : 0.95F);
        }
    }

    private void hitFan(double reach, float damage, boolean busted) {
        Vec3 look = this.getLookAngle();
        if (look.lengthSqr() > 1.0E-6D) {
            look = look.normalize();
        }
        AABB area = this.getBoundingBox().inflate(reach, 2.0D, reach);
        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (!this.canAttackTarget(target)) {
                continue;
            }
            Vec3 to = target.position().subtract(this.position()).normalize();
            if (look.dot(to) < 0.35D) {
                continue;
            }
            target.hurt(this.damageSources().mobAttack(this), damage);
            ignite(target);
            if (busted) {
                applyBusted(target);
            }
        }
    }

    private static void applyBusted(LivingEntity target) {
        MobEffect busted = ForgeRegistries.MOB_EFFECTS.getValue(BUSTED_EFFECT_ID);
        if (busted != null) {
            target.addEffect(new MobEffectInstance(busted, BUSTED_TICKS, 0));
        }
    }

    private void redstoneSmashFx(double radius) {
        this.playSound(ModSounds.REDSTONE_MONSTROSITY_SMASH.get(), 1.6F, 0.75F);
        this.playSound(GeneratedMod.SCORCHBACK_ATTACK.get(), 1.0F, 0.8F);
        if (this.level() instanceof ServerLevel server) {
            ColorUtil color = new ColorUtil(11163136);
            server.sendParticles(new SlamParticleOption(color,
                            (float) (this.getBbWidth() * 2.0D), 20),
                    this.getX(), this.getY() + 0.1D, this.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void slamGround(double radius, float damage, double lift) {
        AABB area = this.getBoundingBox().inflate(radius, 2.5D, radius);
        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (!this.canAttackTarget(target)) {
                continue;
            }
            target.hurt(this.damageSources().mobAttack(this), damage);
            ignite(target);
            Vec3 push = target.position().subtract(this.position());
            if (push.lengthSqr() < 1.0E-4D) {
                push = new Vec3(0.0D, 0.0D, 1.0D);
            }
            push = push.normalize().scale(1.25D);
            target.push(push.x, lift, push.z);
            target.hurtMarked = true;
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION,
                    this.getX(), this.getY() + 0.2D, this.getZ(), 2, radius * 0.4D, 0.1D, radius * 0.4D, 0.0D);
            server.sendParticles(ParticleTypes.CRIT,
                    this.getX(), this.getY() + 0.3D, this.getZ(), 40, radius * 0.5D, 0.1D, radius * 0.5D, 0.25D);
        }
    }

    private void tickSlam(int t) {
        this.emitGroundWave(ScorchbackCombatMath.SLAM_RADIUS, t - SLAM_IMPACT);
        if (t != SLAM_IMPACT) {
            return;
        }
        this.redstoneSmashFx(ScorchbackCombatMath.SLAM_RADIUS);
        this.slamGround(ScorchbackCombatMath.SLAM_RADIUS, 16.0F, 0.55D);
        this.deflectProjectiles(ScorchbackCombatMath.SLAM_RADIUS + 0.5D);
    }

    private void emitGroundWave(double maxRadius, int age) {
        if (age < 0 || age > 6 || age % 2 != 0 || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        double radius = maxRadius * (age + 2.0D) / 8.0D;
        for (int i = 0; i < 16; i++) {
            double angle = Math.PI * 2.0D * i / 16.0D;
            double x = this.getX() + Math.cos(angle) * radius;
            double z = this.getZ() + Math.sin(angle) * radius;
            net.minecraft.world.phys.BlockHitResult floor = server.clip(new net.minecraft.world.level.ClipContext(
                    new Vec3(x, this.getY() + 0.8D, z), new Vec3(x, this.getY() - 1.5D, z),
                    net.minecraft.world.level.ClipContext.Block.COLLIDER,
                    net.minecraft.world.level.ClipContext.Fluid.NONE, this));
            if (floor.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
                continue;
            }
            Vec3 at = floor.getLocation().add(0.0D, 0.05D, 0.0D);
            server.sendParticles(new net.minecraft.core.particles.BlockParticleOption(ParticleTypes.BLOCK,
                            server.getBlockState(floor.getBlockPos())),
                    at.x, at.y, at.z, 2, 0.08D, 0.06D, 0.08D, 0.08D);
            server.sendParticles(ParticleTypes.SMALL_FLAME, at.x, at.y, at.z,
                    0, Math.cos(angle) * 0.04D, 0.03D, Math.sin(angle) * 0.04D, 1.0D);
        }
    }

    private void deflectProjectiles(double radius) {
        AABB area = this.getBoundingBox().inflate(radius);
        for (Projectile projectile : this.level().getEntitiesOfClass(Projectile.class, area)) {
            Entity owner = projectile.getOwner();
            if (owner == this || (owner instanceof LivingEntity living && this.isAlliedTo(living))) {
                continue;
            }
            Vec3 back = projectile.position().subtract(this.position());
            if (back.lengthSqr() < 1.0E-4D) {
                back = new Vec3(0.0D, 1.0D, 0.0D);
            }
            back = back.normalize().scale(1.2D);
            projectile.setDeltaMovement(back.x, 0.9D, back.z);
            projectile.hurtMarked = true;
        }
    }

    private void tickCharge(int t) {
        if (t == 0) {
            this.playSound(SoundEvents.RAVAGER_ROAR, 1.6F, 0.85F);
        }
        if (t == CHARGE_GO) {
            this.playSound(GeneratedMod.SCORCHBACK_STEP.get(), 1.4F, 0.8F);
            this.burstChargeStart();
        }
        if (t < CHARGE_GO || t >= CHARGE_END) {
            return;
        }
        Vec3 dir = ScorchbackCombatMath.forward(this.getActionYaw());
        double speed = 0.62D;
        double vy = this.getDeltaMovement().y;
        if (this.onGround() && this.chargeLeapBlockedAhead()) {
            vy = CHARGE_LEAP_IMPULSE;
        }
        this.setDeltaMovement(dir.x * speed, vy, dir.z * speed);
        this.hurtMarked = true;

        this.emitChargeTrail(t);

        AABB area = this.getBoundingBox().inflate(1.4D, 1.2D, 1.4D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (!this.canAttackTarget(victim)) {
                continue;
            }
            float damage = CHARGE_BASE_DAMAGE + (float) victim.getMaxHealth() * CHARGE_MAX_HEALTH_PERCENT;
            if (!victim.hurt(this.damageSources().mobAttack(this), damage)) {
                continue;
            }
            ignite(victim);
            double originX = this.getX() - dir.x * ScorchbackCombatMath.CHARGE_KNOCKBACK_ORIGIN;
            double originZ = this.getZ() - dir.z * ScorchbackCombatMath.CHARGE_KNOCKBACK_ORIGIN;
            double pushX = victim.getX() - originX;
            double pushZ = victim.getZ() - originZ;
            double pushDistSqr = Math.max(pushX * pushX + pushZ * pushZ, 0.05D);
            victim.push(pushX / pushDistSqr * ScorchbackCombatMath.CHARGE_KNOCKBACK_SPEED,
                    ScorchbackCombatMath.CHARGE_KNOCKBACK_LIFT,
                    pushZ / pushDistSqr * ScorchbackCombatMath.CHARGE_KNOCKBACK_SPEED);
            victim.hurtMarked = true;
            this.playSound(GeneratedMod.SCORCHBACK_ATTACK.get(), 1.5F, 0.75F);
        }
        if ((t - CHARGE_GO) % ScorchbackCombatMath.CHARGE_SPIKE_INTERVAL == 0) {
            this.placeChargeQuills((t - CHARGE_GO) / ScorchbackCombatMath.CHARGE_SPIKE_INTERVAL);
        }
    }

    private boolean chargeLeapBlockedAhead() {
        Vec3 look = this.getLookAngle();
        double reach = 0.55D + this.getBbWidth() * 0.5D;
        AABB box = this.getBoundingBox();
        AABB ahead = new AABB(box.minX, box.minY, box.minZ, box.maxX,
                Math.min(box.maxY, box.minY + 1.6D), box.maxZ)
                .inflate(0.08D)
                .move(look.x * reach, 0.0D, look.z * reach);
        BlockPos from = BlockPos.containing(ahead.minX, ahead.minY, ahead.minZ);
        BlockPos to = BlockPos.containing(ahead.maxX, ahead.maxY, ahead.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(
                Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
                Math.min(from.getZ(), to.getZ()),
                Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
                Math.max(from.getZ(), to.getZ()))) {
            net.minecraft.world.level.block.state.BlockState state = this.level().getBlockState(pos);
            if (!state.is(BlockTags.FENCES) && !state.is(BlockTags.FENCE_GATES)
                    && !state.is(BlockTags.WALLS)) {
                continue;
            }
            if (!state.getCollisionShape(this.level(), pos).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void burstChargeStart() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double midY = this.getY() + this.getBbHeight() * 0.5D;
        server.sendParticles(new SparkleParticleOption(2.6F, 1.0F, 0.22F, 0.12F, 14),
                this.getX(), midY, this.getZ(), 48, 0.75D, 0.6D, 0.75D, 0.3D);
        server.sendParticles(new DustParticleOptions(new Vector3f(1.0F, 0.16F, 0.08F), 1.8F),
                this.getX(), midY, this.getZ(), 30, 0.6D, 0.5D, 0.6D, 0.05D);
        ServerParticleUtil.windParticle(server, new ColorUtil(1.0F, 0.24F, 0.14F),
                2.6F, 2.8F, this.getId(), this.position());
    }

    private void emitChargeTrail(int t) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 look = this.getLookAngle();
        double backX = this.getX() - look.x * 1.4D;
        double backZ = this.getZ() - look.z * 1.4D;
        double midY = this.getY() + this.getBbHeight() * 0.5D;
        server.sendParticles(new SparkleParticleOption(2.2F, 1.0F, 0.18F, 0.10F, 11),
                backX, midY, backZ, 5, 0.45D, 0.4D, 0.45D, 0.04D);
        server.sendParticles(new DustParticleOptions(new Vector3f(1.0F, 0.12F, 0.06F), 1.6F),
                backX, this.getY() + 0.25D, backZ, 3, 0.5D, 0.15D, 0.5D, 0.0D);
        ServerParticleUtil.windParticle(server, new ColorUtil(1.0F, 0.2F, 0.12F),
                1.8F, 2.2F, 6, this.getId(), this.position());
        if (t % 3 == 0) {
            server.sendParticles(ParticleTypes.CRIT, this.getX(), midY, this.getZ(),
                    4, 0.4D, 0.3D, 0.4D, 0.12D);
        }
        if (t % 5 == 0) {
            server.sendParticles(new DustParticleOptions(new Vector3f(1.0F, 0.3F, 0.12F), 1.2F),
                    this.getX(), this.getY() + 0.3D, this.getZ(), 3, 0.3D, 0.2D, 0.3D, 0.04D);
        }
    }

    private void placeChargeQuills(int row) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        for (int lane = 0; lane < ScorchbackCombatMath.CHARGE_SPIKE_LANES; lane++) {
            Vec3 behind = ScorchbackCombatMath.localToWorld(
                    ScorchbackCombatMath.chargeSpikeOffset(lane, row), this.position(),
                    this.getActionYaw(), this.isBaby() ? 0.5D : 1.0D);
            net.minecraft.world.phys.BlockHitResult floor = server.clip(new net.minecraft.world.level.ClipContext(
                    behind.add(0.0D, 0.8D, 0.0D), behind.add(0.0D, -3.0D, 0.0D),
                    net.minecraft.world.level.ClipContext.Block.COLLIDER,
                    net.minecraft.world.level.ClipContext.Fluid.NONE, this));
            if (floor.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
                continue;
            }
            ScorchbackQuill quill = new ScorchbackQuill(ModEntityTypes.SCORCHBACK_QUILL.get(), server);
            quill.plant(this, floor.getLocation(), this.getActionYaw(), lane);
            server.addFreshEntity(quill);
        }
    }

    @Override
    public void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
    }

    private void tickAnimationDrivenFootsteps() {
        int index = ScorchbackCombatMath.footfallIndex(this.walkAnimation.position());
        if (this.lastFootfallIndex < 0) {
            this.lastFootfallIndex = index;
            return;
        }
        if (index == this.lastFootfallIndex) {
            return;
        }
        this.lastFootfallIndex = index;
        if (this.walkAnimation.speed() < FOOTSTEP_MIN_SPEED
                || !this.onGround() || this.isPassenger()
                || this.isInWater() || this.isInLava()) {
            return;
        }
        BlockPos below = this.getOnPos();
        if (this.level().getBlockState(below).isAir()) {
            return;
        }
        this.playSound(GeneratedMod.SCORCHBACK_STEP.get(), 0.9F,
                0.9F + this.getRandom().nextFloat() * 0.2F);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return GeneratedMod.SCORCHBACK_DEATH.get();
    }

    @Override
    public boolean canStandOnFluid(FluidState fluid) {
        return fluid.is(Fluids.WATER) || fluid.is(Fluids.LAVA);
    }

    private void walkFluidSurface() {
        if (this.isPassenger() || this.isInPowderSnow) {
            return;
        }
        if (this.getDeltaMovement().y > 0.0D) {
            return;
        }
        BlockPos.MutableBlockPos cursor = this.blockPosition().mutable();
        int feetBlock = cursor.getY();
        FluidState atFeet = this.level().getFluidState(cursor);
        boolean feetInWater = atFeet.is(Fluids.WATER);
        boolean feetInLava = atFeet.is(Fluids.LAVA);
        if (!feetInWater && !feetInLava) {
            return;
        }
        double surface = -1.0D;
        if (feetInWater || feetInLava) {
            surface = feetBlock + atFeet.getHeight(this.level(), cursor);
        }
        cursor.setY(feetBlock + 1);
        FluidState above = this.level().getFluidState(cursor);
        if ((feetInWater && above.is(Fluids.WATER)) || (feetInLava && above.is(Fluids.LAVA))) {
            surface = Math.max(surface, feetBlock + 1 + above.getHeight(this.level(), cursor));
        }
        cursor.setY(feetBlock);
        if (surface < 0.0D) {
            return;
        }
        if (this.getY() < surface - FLUID_SURFACE_EPSILON) {
            if (surface - this.getY() > FLUID_SURFACE_SNAP) {
                return;
            }
            this.setPos(this.getX(), surface, this.getZ());
            this.setDeltaMovement(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
        }
        this.fallDistance = 0.0F;
        this.setOnGround(true);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    private void tickRain(int t) {
        if (t == 4) {
            this.playSound(GeneratedMod.SCORCHBACK_ATTACK.get(), 1.15F, 0.85F);
        }
        int volley = ScorchbackCombatMath.volleyAt(t);
        if (volley >= 0) {
            this.launchRainVolley(volley);
        }
    }

    private void launchRainVolley(int volley) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (this.canAttackTarget(target) && this.distanceToSqr(target) <= 24.0D * 24.0D) {
            Vec3 lead = target.getDeltaMovement().multiply(12.0D, 0.0D, 12.0D);
            if (lead.lengthSqr() > 9.0D) {
                lead = lead.normalize().scale(3.0D);
            }
            this.rainAim = target.position().add(lead);
        }
        this.playSound(SoundEvents.CROSSBOW_SHOOT, 1.1F, 0.75F + volley * 0.04F);
        this.playSound(SoundEvents.FIRECHARGE_USE, 0.65F, 1.2F);
        for (int shot = 0; shot < ScorchbackCombatMath.RAIN_SHOTS; shot++) {
            Vec3 origin = ScorchbackRainSockets.position(this, volley, shot);
            double angle = volley * 2.399963D + shot * Math.PI;
            double spread = shot == 0 ? 0.15D : 1.6D + (volley % 2) * 0.5D;
            Vec3 aim = this.rainAim.add(Math.cos(angle) * spread, 0.15D, Math.sin(angle) * spread);
            aim = this.groundAim(server, aim);
            ScorchbackQuill quill = new ScorchbackQuill(ModEntityTypes.SCORCHBACK_QUILL.get(), server);
            quill.launch(this, origin, aim, volley, shot);
            if (server.addFreshEntity(quill)) {
                server.sendParticles(ParticleTypes.FLAME, origin.x, origin.y, origin.z,
                        6, 0.06D, 0.08D, 0.06D, 0.025D);
                server.sendParticles(new SparkleParticleOption(0.7F, 1.0F, 0.28F, 0.06F, 7),
                        origin.x, origin.y, origin.z, 4, 0.06D, 0.06D, 0.06D, 0.025D);
            }
        }
        this.launchRainExtraQuills(server, volley, this.canAttackTarget(target) ? target : null);
    }

    private Vec3 groundAim(ServerLevel server, Vec3 aim) {
        net.minecraft.world.phys.BlockHitResult ground = server.clip(new net.minecraft.world.level.ClipContext(
                aim.add(0.0D, 3.0D, 0.0D), aim.add(0.0D, -6.0D, 0.0D),
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE, this));
        return ground.getType() == net.minecraft.world.phys.HitResult.Type.MISS
                ? aim : ground.getLocation();
    }

    private void launchRainExtraQuills(ServerLevel server, int volley, @Nullable LivingEntity primary) {
        int extra = ScorchbackCombatMath.RAIN_EXTRA_MIN
                + this.random.nextInt(ScorchbackCombatMath.RAIN_EXTRA_MAX
                        - ScorchbackCombatMath.RAIN_EXTRA_MIN + 1);
        List<LivingEntity> others = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(24.0D, 12.0D, 24.0D),
                living -> living != primary && this.canAttackTarget(living));
        for (int index = 0; index < extra; index++) {
            Vec3 aim;
            if (!others.isEmpty()) {
                LivingEntity other = others.remove(this.random.nextInt(others.size()));
                Vec3 lead = other.getDeltaMovement().multiply(12.0D, 0.0D, 12.0D);
                if (lead.lengthSqr() > 9.0D) {
                    lead = lead.normalize().scale(3.0D);
                }
                aim = other.position().add(lead).add(0.0D, other.getBbHeight() * 0.5D, 0.0D);
            } else if (primary != null) {
                aim = ScorchbackCombatMath.scatterInCircle(
                        primary.position().add(0.0D, primary.getBbHeight() * 0.5D, 0.0D),
                        ScorchbackCombatMath.RAIN_EXTRA_SCATTER_RADIUS,
                        this.random.nextDouble(), this.random.nextDouble());
            } else {
                continue;
            }
            int shot = this.random.nextInt(ScorchbackCombatMath.RAIN_SHOTS);
            Vec3 origin = ScorchbackRainSockets.position(this, volley, shot)
                    .add((this.random.nextDouble() - 0.5D) * 0.5D, 0.0D,
                            (this.random.nextDouble() - 0.5D) * 0.5D);
            aim = this.groundAim(server, aim);
            ScorchbackQuill quill = new ScorchbackQuill(ModEntityTypes.SCORCHBACK_QUILL.get(), server);
            quill.launch(this, origin, aim, volley, shot);
            if (server.addFreshEntity(quill)) {
                server.sendParticles(ParticleTypes.FLAME, origin.x, origin.y, origin.z,
                        6, 0.06D, 0.08D, 0.06D, 0.025D);
                server.sendParticles(new SparkleParticleOption(0.7F, 1.0F, 0.28F, 0.06F, 7),
                        origin.x, origin.y, origin.z, 4, 0.06D, 0.06D, 0.06D, 0.025D);
            }
        }
    }

    private static void ignite(LivingEntity target) {
        target.setSecondsOnFire(ATTACK_FIRE_SECONDS);
    }

    public boolean canAttackTarget(@Nullable LivingEntity target) {
        if (target == null || target == this || !target.isAlive()) {
            return false;
        }
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return false;
        }
        return !this.isAlliedTo(target);
    }

    @Nullable
    LivingEntity validChaseTarget() {
        if (this.isBusy()) {
            return null;
        }
        LivingEntity target = this.getTarget();
        if (!this.canAttackTarget(target)) {
            return null;
        }
        if (this.isStaying() && this.distanceToSqr(target) > STAY_CHASE_RANGE * STAY_CHASE_RANGE) {
            return null;
        }
        return target;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isScorchbackFood(stack)) {
            if (!this.level().isClientSide()) {
                boolean fed = false;
                if (this.isBaby()) {
                    this.growTimer = Math.min(GROW_TICKS, this.growTimer + FEED_GROW_STEP);
                    fed = true;
                }
                if (this.digestHeal > 0.0F || this.getHealth() < this.getMaxHealth()) {
                    this.digestHeal += this.foodHealValue(stack);
                    fed = true;
                }
                if (fed) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                    if (this.level() instanceof ServerLevel server) {
                        server.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D),
                                this.getY() + 0.6D, this.getRandomZ(1.0D), 7, 0.05D, 0.05D, 0.05D, 0.5D);
                    }
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        if (stack.is(Items.NETHER_WART) && this.getTrueOwner() == player
                && ServantFeedHeal.feed(this, player, stack, NETHER_WART_HEAL)) {
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private boolean isScorchbackFood(ItemStack stack) {
        if (stack.isEmpty() || !stack.isEdible()) {
            return false;
        }
        if (stack.is(Items.BEETROOT_SOUP) || stack.is(Items.MUSHROOM_STEW)
                || stack.is(Items.RABBIT_STEW) || stack.is(Items.SUSPICIOUS_STEW)
                || stack.is(Items.PUMPKIN_PIE) || stack.is(Items.COOKIE) || stack.is(Items.CAKE)
                || stack.is(Items.HONEY_BOTTLE) || stack.is(Items.GOLDEN_APPLE)
                || stack.is(Items.ENCHANTED_GOLDEN_APPLE) || stack.is(Items.DRIED_KELP)
                || stack.is(Items.BREAD) || stack.is(Items.POISONOUS_POTATO)) {
            return false;
        }
        if (isMeat(stack)) {
            return true;
        }
        return stack.is(Items.CARROT) || stack.is(Items.POTATO) || stack.is(Items.BEETROOT)
                || stack.is(Items.APPLE) || stack.is(Items.MELON_SLICE) || stack.is(Items.PUMPKIN)
                || stack.is(Items.SWEET_BERRIES) || stack.is(Items.GLOW_BERRIES)
                || stack.is(Items.BROWN_MUSHROOM) || stack.is(Items.RED_MUSHROOM)
                || stack.is(Items.KELP) || stack.is(Items.SEA_PICKLE) || stack.is(Items.BAMBOO)
                || stack.is(Items.SUGAR_CANE) || stack.is(Items.WHEAT)
                || stack.is(Items.CACTUS) || stack.is(Items.CHORUS_FRUIT);
    }

    private static boolean isMeat(ItemStack stack) {
        return stack.is(Items.BEEF) || stack.is(Items.COOKED_BEEF)
                || stack.is(Items.PORKCHOP) || stack.is(Items.COOKED_PORKCHOP)
                || stack.is(Items.CHICKEN) || stack.is(Items.COOKED_CHICKEN)
                || stack.is(Items.MUTTON) || stack.is(Items.COOKED_MUTTON)
                || stack.is(Items.RABBIT) || stack.is(Items.COOKED_RABBIT)
                || stack.is(Items.COD) || stack.is(Items.COOKED_COD)
                || stack.is(Items.SALMON) || stack.is(Items.COOKED_SALMON)
                || stack.is(Items.TROPICAL_FISH) || stack.is(Items.PUFFERFISH)
                || stack.is(Items.ROTTEN_FLESH) || stack.is(Items.SPIDER_EYE);
    }

    private float foodHealValue(ItemStack stack) {
        FoodProperties food = stack.getFoodProperties(this);
        if (food == null) {
            return 0.0F;
        }
        return food.getNutrition() * 1.0F + food.getSaturationModifier() * 2.0F;
    }

    private void tickDigest() {
        if (this.digestHeal <= 0.0F || this.getHealth() >= this.getMaxHealth()) {
            return;
        }
        if (++this.digestTimer < DIGEST_INTERVAL) {
            return;
        }
        this.digestTimer = 0;
        this.heal(1.0F);
        this.digestHeal -= 1.0F;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!this.isBusy() && target instanceof LivingEntity living && this.canAttackTarget(living)) {
            this.startAction(Action.COMBO);
            return true;
        }
        return super.doHurtTarget(target);
    }
}
