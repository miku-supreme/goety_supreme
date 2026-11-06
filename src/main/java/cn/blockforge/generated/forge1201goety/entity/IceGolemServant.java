package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.model.IceGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IceGolemServantRenderer;

public class IceGolemServant extends AbstractFrostGolemServant {

    private static final double SLAM_FRONT_DISTANCE = 1.5D;

    private static final double SLAM_INFLATE = 1.5D;

    private static final double SLAM_VERTICAL_INFLATE = 3.0D;

    private static final float SLAM_PARTICLE_RADIUS = (float) SLAM_INFLATE;

    private static final int SLAM_SHOCKWAVE_COLOR = 0x9FD8FF;

    public static final float ATTACK_ANIM_SPEED = 12.0F / 10.0F;

    private static final int SLAM_LEFT_TICK = 10;
    private static final int SLAM_RIGHT_TICK = 22;

    private static final int SNOWBALL_RELEASE_DELAY_TICKS = 10;

    private static final int SNOWBALL_VOLLEY_TICKS = 5 * 20;

    private static final int SNOWBALL_VOLLEY_INTERVAL_TICKS = 4;

    private static final int SNOWBALL_VOLLEY_COOLDOWN_TICKS = 5 * 20;

    private static final float FIXED_RANGED_DAMAGE = 1.5F;

    private static final float WINDMILL_STORM_DAMAGE = 2.5F;

    private static final int WINDMILL_STORM_INTERVAL_TICKS = 10;

    private static final double WINDMILL_STORM_REACH = 1.1D;

    private static final double WINDMILL_STORM_RADIUS = 1.1D;

    private int windmillStormTimer;

    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState windmillAnimationState = new AnimationState();

    public final AnimationState windmillSpinAnimationState = new AnimationState();

    public final AnimationState attackAnimationState = new AnimationState();

    private int lastAttackAnimationTick;

    private boolean slamLeftFired;
    private boolean slamRightFired;

    public IceGolemServant(EntityType<? extends IceGolemServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.ICE)
                .add(Attributes.FOLLOW_RANGE, GolemServantConfig.ICE.CHASE_RANGE.get());
    }

    @Override
    public GolemServantConfig.IceSection configSection() {
        return GolemServantConfig.ICE;
    }

    @Override
    public void setConfigurableAttributes() {
        super.setConfigurableAttributes();
        AttributeInstance followRange = getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.setBaseValue(GolemServantConfig.ICE.CHASE_RANGE.get());
        }
    }

    @Override
    public double launchVertical() {
        return 0.225D;
    }

    @Override
    public double launchHorizontal() {
        return 0.65D;
    }

    private void applySlamKnockback(LivingEntity target, boolean leftHand) {
        if (leftHand) {
            applyMeleeLaunch(target, MELEE_KNOCKBACK, MELEE_LAUNCH * MELEE_KNOCKBACK);
        } else {
            applyMeleeLaunch(target, launchHorizontal(), launchVertical() * difficultyScale());
        }
    }

    @Override
    public int snowballVolleyTicks() {
        return SNOWBALL_VOLLEY_TICKS;
    }

    @Override
    public int snowballVolleyIntervalTicks() {
        return SNOWBALL_VOLLEY_INTERVAL_TICKS;
    }

    @Override
    public int snowballVolleyCooldownTicks() {
        return SNOWBALL_VOLLEY_COOLDOWN_TICKS;
    }

    @Override
    public float rangedDamage() {
        return FIXED_RANGED_DAMAGE;
    }

    @Override
    public int rangedReleaseDelayTicks() {
        return SNOWBALL_RELEASE_DELAY_TICKS;
    }

    @Override
    public int attackSwingAnimation() {
        return ATTACK_ICE_SWING;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().isClientSide) {
            return false;
        }
        if (isMeleeAnimating()) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            aimAt(living);
        }
        this.slamLeftFired = false;
        this.slamRightFired = false;
        startAttack(ATTACK_ICE_SWING, ATTACK_LENGTHS[ATTACK_ICE_SWING]);
        return true;
    }

    @Override
    public void onAttackHit(Entity target) {
        applyMeleeChill(target, 40, 0);
    }

    private void groundSlam(boolean leftHand) {
        aimAtTarget();
        double yawRad = Math.toRadians(slamFacingYaw());
        double lookX = -Math.sin(yawRad);
        double lookZ = Math.cos(yawRad);
        double side = leftHand ? -0.35D : 0.35D;
        Vec3 center = new Vec3(
                getX() + lookX * SLAM_FRONT_DISTANCE + lookZ * side,
                getY(),
                getZ() + lookZ * SLAM_FRONT_DISTANCE - lookX * side);
        AABB box = new AABB(center, center)
                .inflate(SLAM_INFLATE, SLAM_VERTICAL_INFLATE, SLAM_INFLATE);
        float damage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (living == this || !living.isAlive() || this.isAlliedTo(living)) {
                continue;
            }
            if (living.hurt(this.damageSources().mobAttack(this), damage)) {
                this.applySlamKnockback(living, leftHand);
                this.doEnchantDamageEffects(this, living);
                this.onAttackHit(living);
            }
        }
        if (level() instanceof ServerLevel serverLevel) {
            BlockPos below = BlockPos.containing(center.x, getY() - 1.0D, center.z);
            BlockState ground = serverLevel.getBlockState(below);
            BlockParticleOption dust = new BlockParticleOption(ParticleTypes.BLOCK, ground);
            for (int i = 0; i < 8; i++) {
                ServerParticleUtil.circularParticles(serverLevel, dust, center.x, getY() + 0.25D, center.z,
                        SLAM_PARTICLE_RADIUS);
            }
            ColorUtil color = new ColorUtil(SLAM_SHOCKWAVE_COLOR);
            serverLevel.sendParticles(new SmashParticleOption(color, SLAM_PARTICLE_RADIUS * 1.4F, 8),
                    center.x, getY() + 0.2D, center.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            serverLevel.sendParticles(new SmashParticleOption(color, SLAM_PARTICLE_RADIUS * 2.0F, 10),
                    center.x, getY() + 0.2D, center.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            ServerParticleUtil.windShockwaveParticle(serverLevel, color, SLAM_PARTICLE_RADIUS, 0.0F, 10,
                    new Vec3(center.x, getY() + 0.2D, center.z));
            for (int i = 0; i < 10; i++) {
                double angle = this.random.nextDouble() * Math.PI * 2.0D;
                double dist = SLAM_PARTICLE_RADIUS * this.random.nextDouble();
                serverLevel.sendParticles(GoetyCompat.frostParticle(),
                        center.x + Math.cos(angle) * dist, getY() + 0.12D,
                        center.z + Math.sin(angle) * dist, 1, 0.0D, 0.0D, 0.0D, 0.01D);
            }
        }
        playSound(SoundEvents.GENERIC_EXPLODE, 1.2F, 0.6F);
    }

    private void tickMeleeSlams() {
        if (!isMeleeAnimating()) {
            return;
        }
        if (!this.slamLeftFired && attackReached(SLAM_LEFT_TICK)) {
            this.slamLeftFired = true;
            groundSlam(true);
        }
        if (!this.slamRightFired && attackReached(SLAM_RIGHT_TICK)) {
            this.slamRightFired = true;
            groundSlam(false);
        }
    }

    private void aimAt(LivingEntity target) {
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        float yaw = (float) (Mth.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        setYRot(yaw);
        this.yBodyRot = yaw;
        this.yHeadRot = yaw;
    }

    private void aimAtTarget() {
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        aimAt(target);
    }

    public boolean isWindmillAnimating() {
        return isWindmillSpinning() && !isMeleeAnimating();
    }

    public boolean isWindmillSpinAnimating() {
        return isWindmillAnimating() && isWindmillSpinPhase();
    }

    public boolean isMeleeAnimating() {
        return isAttackAnimating() && getAttackAnimation() == ATTACK_ICE_SWING;
    }

    private void tickClientAnimations() {
        this.idleAnimationState.startIfStopped(this.tickCount);
        int attackTick = getAttackAnimationTick();
        if (isMeleeAnimating()) {
            if (attackTick > this.lastAttackAnimationTick) {
                this.attackAnimationState.start(this.tickCount);
            }
        } else {
            this.attackAnimationState.stop();
        }
        this.lastAttackAnimationTick = attackTick;
        if (isWindmillSpinAnimating()) {
            this.windmillSpinAnimationState.startIfStopped(this.tickCount);
        } else {
            this.windmillSpinAnimationState.stop();
        }
        this.windmillAnimationState.stop();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ICE_GOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.ICE_GOLEM_DEATH.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            tickClientAnimations();
            return;
        }
        if (!isAlive()) {
            return;
        }
        tickMeleeSlams();
        tickWindmillStorm();
        if (tickCount % 20 != 0) {
            return;
        }
        double melt = GolemServantConfig.ICE.MELT_DAMAGE_PER_SECOND.get();
        if (melt <= 0.0D || !isMelting()) {
            return;
        }
        hurtSelfFrost((float) melt);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.DRIPPING_WATER,
                    getX(), getY() + getBbHeight() * 0.6D, getZ(), 6,
                    getBbWidth() * 0.4D, getBbHeight() * 0.3D, getBbWidth() * 0.4D, 0.0D);
        }
    }

    private void tickWindmillStorm() {
        if (!isSnowballVolleyActive()) {
            this.windmillStormTimer = 0;
            return;
        }
        if (--this.windmillStormTimer > 0) {
            return;
        }
        this.windmillStormTimer = WINDMILL_STORM_INTERVAL_TICKS;
        Vec3 look = getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        if (flat.lengthSqr() < 1.0E-6D) {
            return;
        }
        flat = flat.normalize();
        applyWindmillStormBox(flat);
        applyWindmillStormBox(flat.scale(-1.0D));
    }

    private void applyWindmillStormBox(Vec3 dir) {
        double cx = getX() + dir.x * WINDMILL_STORM_REACH;
        double cz = getZ() + dir.z * WINDMILL_STORM_REACH;
        AABB area = new AABB(
                cx - WINDMILL_STORM_RADIUS, getY() - 0.5D, cz - WINDMILL_STORM_RADIUS,
                cx + WINDMILL_STORM_RADIUS, getY() + getBbHeight() + 0.5D,
                cz + WINDMILL_STORM_RADIUS);
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || !victim.isAlive() || isAlliedTo(victim)) {
                continue;
            }
            if (victim instanceof Player player && (player.isCreative() || player.isSpectator())) {
                continue;
            }
            victim.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), WINDMILL_STORM_DAMAGE);
        }
    }

    private boolean isMelting() {
        if (ownerWearsFrostRobes()) {
            return false;
        }
        if (isInWaterRainOrBubble()) {
            return true;
        }
        return level().getBiome(blockPosition()).value().getBaseTemperature() > 1.0F;
    }

    private static final ResourceLocation PUMPKIN_GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/ice_golem_pumpkin_glow.png");

    @Override
    @Nullable
    public ResourceLocation pumpkinGlowTexture() {
        return getTarget() != null || isAggressive() ? PUMPKIN_GLOW : null;
    }
}
