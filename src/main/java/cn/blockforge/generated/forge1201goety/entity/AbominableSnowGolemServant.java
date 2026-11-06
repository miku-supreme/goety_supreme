package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.frost.FrostBreathSpell;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.block.AbominableSnowGolemSnowBlock;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.entity.FrostGolemPumpkinLayer;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.util.IframeHit;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantJackOLanternLayer;
import cn.blockforge.generated.goetysupreme.util.MutantThrowStandIns;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemClips;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;

public class AbominableSnowGolemServant extends AbstractFrostGolemServant {

    public AbominableSnowGolemServant(EntityType<? extends AbominableSnowGolemServant> entityType,
            Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.SNOW)
                .add(Attributes.FOLLOW_RANGE, GolemServantConfig.SNOW.CHASE_RANGE.get());
    }

    @Override
    public GolemServantConfig.SnowSection configSection() {
        return GolemServantConfig.SNOW;
    }

    private static final EntityDataAccessor<Boolean> DATA_BARRAGE_ACTIVE =
            SynchedEntityData.defineId(AbominableSnowGolemServant.class, EntityDataSerializers.BOOLEAN);

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BARRAGE_ACTIVE, false);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDeadOrDying();
    }

    @Override
    public boolean isPushable() {
        return !this.isDeadOrDying();
    }

    @Override
    public void setConfigurableAttributes() {
        super.setConfigurableAttributes();
        AttributeInstance followRange = getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.setBaseValue(GolemServantConfig.SNOW.CHASE_RANGE.get());
        }
    }

    @Override
    public double launchVertical() {
        return 1.0D;
    }

    @Override
    public void tryKill(Player player) {
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
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        spawnAtLocation(new ItemStack(Blocks.SNOW_BLOCK, 4), 0.3F);
        dropGoetyBlock("snow_bricks", 2);
        Block lamp = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("goety", "freezing_lamp"));
        if (lamp != null && lamp != Blocks.AIR) {
            OwnerBoundLoot.deliver(this, new ItemStack(lamp));
        }
    }

    private void dropGoetyBlock(String path, int count) {
        if (count <= 0) {
            return;
        }
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("goety", path));
        if (block == null || block == Blocks.AIR) {
            return;
        }
        spawnAtLocation(new ItemStack(block, count), 0.3F);
    }

    private static final double SNOW_BLOCK_GRAVITY = 0.06D;
    private static final double SNOW_BLOCK_DRAG = 0.99D;
    private static final double SNOW_BLOCK_MIN_SPEED = 0.5D;

    private static final double SNOW_BLOCK_MAX_SPEED = 1.2D;
    private static final float SNOW_BLOCK_INACCURACY = 0.15F;

    private static final float SNOW_BLOCK_DAMAGE_MULTIPLIER = 2.0F;

    private static final double SNOW_BLOCK_REACH = 18.0D;

    private static final double SNOWBALL_RANGE_BONUS = 4.0D;

    private static final int SNOWBALL_VOLLEY_EMPTY_TIMEOUT_TICKS = 40;

    private static final int STRAY_SNOWBALLS_PER_VOLLEY = 2;

    private static final int STALEMATE_WINDOW_TICKS = 40;
    private static final double STALEMATE_BAND = 1.5D;
    private static final int STALEMATE_FORCED_RANGED_TICKS = 60;

    private int stalemateTicks;
    private double stalemateMinDistance = Double.MAX_VALUE;
    private double stalemateMaxDistance;
    private int forcedRangedTicks;

    public float snowBlockAoeDamage() {
        return GolemServantConfig.SNOW.SNOW_BLOCK_AOE_DAMAGE.get().floatValue();
    }

    @Override
    public boolean prefersRanged(LivingEntity target) {
        return this.forcedRangedTicks > 0 || super.prefersRanged(target);
    }

    private void tickStalemateRanged() {
        if (level().isClientSide) {
            return;
        }
        if (this.forcedRangedTicks > 0) {
            --this.forcedRangedTicks;
        }
        LivingEntity target = getTarget();
        double slam = GolemServantConfig.SNOW.SLAM_RADIUS.get();
        if (target == null || !target.isAlive()) {
            resetStalemate();
            return;
        }
        if (distanceToSqr(target) <= slam * slam) {
            this.forcedRangedTicks = 0;
            resetStalemate();
            return;
        }
        double range = rangedRange();
        if (super.prefersRanged(target) || distanceToSqr(target) > range * range) {
            resetStalemate();
            return;
        }
        double distance = distanceTo(target);
        this.stalemateMinDistance = Math.min(this.stalemateMinDistance, distance);
        this.stalemateMaxDistance = Math.max(this.stalemateMaxDistance, distance);
        if (++this.stalemateTicks >= STALEMATE_WINDOW_TICKS) {
            if (this.stalemateMaxDistance - this.stalemateMinDistance <= STALEMATE_BAND
                    && this.stalemateMinDistance > slam + 0.5D) {
                this.forcedRangedTicks = STALEMATE_FORCED_RANGED_TICKS;
            }
            resetStalemate();
        }
    }

    private void resetStalemate() {
        this.stalemateTicks = 0;
        this.stalemateMinDistance = Double.MAX_VALUE;
        this.stalemateMaxDistance = 0.0D;
    }

    @Override
    public double rangedRange() {
        return Math.min(super.rangedRange(), SNOW_BLOCK_REACH);
    }

    public double snowballRange() {
        return rangedRange() + SNOWBALL_RANGE_BONUS;
    }

    @Override
    public int rangedShotPeriodTicks() {
        return rangedActionTicks() + Math.max(1, rangedIntervalTicks());
    }

    @Override
    public int rangedActionTicks() {
        return ATTACK_LENGTHS[ATTACK_SNOW_THROW_BLOCK];
    }

    @Override
    public boolean throwRangedProjectile(LivingEntity target, float damage) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return false;
        }
        if (isSkillLocked()) {
            return false;
        }
        if (isAttackAnimating()) {
            return false;
        }
        this.pendingThrowTarget = target;
        this.pendingThrowDamage = damage;
        this.pendingThrowAim = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        startAttack(ATTACK_SNOW_THROW_BLOCK, ATTACK_LENGTHS[ATTACK_SNOW_THROW_BLOCK]);
        return true;
    }

    private void tickRangedThrow() {
        if (level().isClientSide || this.pendingThrowTarget == null) {
            return;
        }
        if (!isAttackAnimating() || getAttackAnimation() != ATTACK_SNOW_THROW_BLOCK) {
            this.pendingThrowTarget = null;
            return;
        }
        if (!attackReached(AbstractGolemServant.snowThrowBlockReleaseTick())) {
            return;
        }
        LivingEntity target = this.pendingThrowTarget;
        float damage = this.pendingThrowDamage;
        this.pendingThrowTarget = null;
        Vec3 fallbackAim = this.pendingThrowAim;
        this.pendingThrowAim = null;
        if (target != null && target.isAlive() && canAttack(target)) {
            fireSnowBlock(target, damage);
        } else if (target != null) {
            fireSnowBlockTo(target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D), damage);
        } else if (fallbackAim != null) {
            fireSnowBlockTo(fallbackAim, damage);
        }
        if (this.volleyCooldown <= 0
                && this.random.nextDouble() < snowballVolleyChance(target)) {
            this.pendingVolleyAfterThrow = true;
        }
    }

    private void tickIceStorm() {
        if (!isSnowballBarrageActive() && !isBreathing()) {
            this.iceStormDamageTimer = 0;
            return;
        }
        if (level().isClientSide) {
            spawnIceStormParticles();
            return;
        }
        if (--this.iceStormDamageTimer > 0) {
            return;
        }
        this.iceStormDamageTimer = ICE_STORM_DAMAGE_INTERVAL_TICKS;
        applyIceStormDamage();
    }

    private void spawnIceStormParticles() {
        double radius = Math.max(1.3D, getBbWidth() * 0.9D);
        double bottom = getY() + 0.15D;
        double top = getY() + getBbHeight() - 0.15D;
        double baseAngle = this.tickCount * 0.4D;
        for (int layer = 0; layer < ICE_STORM_PARTICLE_LAYERS; layer++) {
            double y = ICE_STORM_PARTICLE_LAYERS == 1
                    ? bottom
                    : bottom + (top - bottom) * layer / (ICE_STORM_PARTICLE_LAYERS - 1);
            for (int i = 0; i < ICE_STORM_PARTICLES_PER_LAYER; i++) {
                double angle = baseAngle + i * (Math.PI * 2.0D / ICE_STORM_PARTICLES_PER_LAYER);
                level().addParticle(ParticleTypes.SNOWFLAKE,
                        getX() + Math.cos(angle) * radius, y, getZ() + Math.sin(angle) * radius,
                        0.0D, -0.02D, 0.0D);
            }
        }
    }

    private void applyIceStormDamage() {
        AABB area = getBoundingBox().inflate(ICE_STORM_RADIUS, 0.6D, ICE_STORM_RADIUS);
        boolean ignoreIframes = isBreathing();
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (victim == this || !victim.isAlive() || isAlliedTo(victim)) {
                continue;
            }
            if (victim instanceof Player player && (player.isCreative() || player.isSpectator())) {
                continue;
            }
            DamageSource source = ModDamageSource.summonAttack(this, getTrueOwner());
            if (!ignoreIframes) {
                victim.hurt(source, ICE_STORM_DAMAGE);
                continue;
            }
            IframeHit.hurt(victim, source, ICE_STORM_DAMAGE);
        }
    }

    private void fireSnowBlock(LivingEntity target, float damage) {
        Vec3 center = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        launchSnowBlock(center, target.getDeltaMovement(), damage);
    }

    private void fireSnowBlockTo(Vec3 center, float damage) {
        launchSnowBlock(center, Vec3.ZERO, damage);
    }

    private void launchSnowBlock(Vec3 center, Vec3 motion, float damage) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        MutantSnowGolem standIn = MutantThrowStandIns.forLevel(serverLevel);
        if (standIn == null) {
            return;
        }
        standIn.setPos(getX(), getY(), getZ());
        standIn.setYRot(getYRot());
        standIn.yHeadRot = getYHeadRot();
        standIn.yBodyRot = getYRot();

        AbominableSnowGolemSnowBlock block = new AbominableSnowGolemSnowBlock(standIn, this);
        block.setDamage(damage * SNOW_BLOCK_DAMAGE_MULTIPLIER);
        Vec3 from = throwHandPosition();
        BallisticAim.Solution aim = BallisticAim.solveFast(from, center,
                SNOW_BLOCK_GRAVITY, SNOW_BLOCK_DRAG, SNOW_BLOCK_MIN_SPEED, SNOW_BLOCK_MAX_SPEED);
        Vec3 lead = BallisticAim.leadOffset(motion, aim.ticks());
        aim = BallisticAim.solveFast(from, center.add(lead),
                SNOW_BLOCK_GRAVITY, SNOW_BLOCK_DRAG, SNOW_BLOCK_MIN_SPEED, SNOW_BLOCK_MAX_SPEED);
        Vec3 velocity = aim.velocity();
        block.setPos(from.x, from.y, from.z);
        block.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), SNOW_BLOCK_INACCURACY);
        serverLevel.addFreshEntity(block);
        playSound(SoundEvents.SNOWBALL_THROW, 1.0F, 0.8F + this.random.nextFloat() * 0.2F);

        playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        playSound(Blocks.SNOW_BLOCK.defaultBlockState().getSoundType().getPlaceSound(), 1.0F,
                1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
    }

    private static final double THROW_HAND_FORWARD = 1.12D;
    private static final double THROW_HAND_LATERAL = 1.79D;
    private static final double THROW_HAND_HEIGHT = 7.18D;

    private Vec3 throwHandPosition() {
        float yaw = this.yBodyRot * Mth.DEG_TO_RAD;
        double forwardX = -Mth.sin(yaw);
        double forwardZ = Mth.cos(yaw);
        double leftX = Mth.cos(yaw);
        double leftZ = Mth.sin(yaw);
        return new Vec3(
                getX() + forwardX * THROW_HAND_FORWARD + leftX * THROW_HAND_LATERAL,
                getY() + THROW_HAND_HEIGHT,
                getZ() + forwardZ * THROW_HAND_FORWARD + leftZ * THROW_HAND_LATERAL);
    }

    @Override
    public void onAttackHit(Entity target) {
        applyMeleeChill(target, 150, 1);
    }

    @Override
    public int meleeAnticipationTicks() {
        return SNOW_SLAM_IMPACT_TICK;
    }

    @Override
    public double meleeAnticipationMax() {
        return 2.5D;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
        this.goalSelector.addGoal(2, new SnowSlamMeleeGoal(this, 1.0D));
    }

    private static final class SnowSlamMeleeGoal extends MeleeAttackGoal {
        private final AbominableSnowGolemServant golem;

        SnowSlamMeleeGoal(AbominableSnowGolemServant golem, double speed) {
            super(golem, speed, true);
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            return !golem.isAttackAnimating() && !golem.isSkillLocked()
                    && !golem.shouldYieldToGuaranteedFrostBreath()
                    && golem.getTarget() != null && !golem.prefersRanged(golem.getTarget())
                    && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (golem.isAttackAnimating()) {
                return true;
            }
            LivingEntity target = golem.getTarget();
            return target != null && !golem.prefersRanged(target) && super.canContinueToUse();
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

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().isClientSide || isSkillLocked() || isAttackAnimating()) {
            return false;
        }
        if (!(target instanceof LivingEntity living) || !living.isAlive() || !canAttack(living)) {
            return false;
        }
        this.snowSlamImpactDone = false;
        lockSlamFacing(living);
        startAttack(ATTACK_SNOW_SLAM, ATTACK_LENGTHS[ATTACK_SNOW_SLAM]);
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isAlive()) {
            return;
        }
        tickSkillCooldowns();
        tickSkillMovementLock();
        tickRangedThrow();
        tickPendingVolley();
        tickFrostBreath();
        tickIceStorm();
        tickSnowSlamFacing();
        tickSnowSlam();
        tickSnowballVolley();
        tickChillHideAura();
        tickStalemateRanged();
    }

    private void tickSnowSlamFacing() {
        if (level().isClientSide) {
            return;
        }
        if (isAttackAnimating() && getAttackAnimation() == ATTACK_SNOW_SLAM) {
            applySlamYaw();
        }
    }

    private void applySlamYaw() {
        setYRot(this.slamYaw);
        this.yBodyRot = this.slamYaw;
        this.yHeadRot = this.slamYaw;
    }

    private void lockSlamFacing(LivingEntity target) {
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        this.slamYaw = (float) (Mth.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        applySlamYaw();
    }

    private void tickSnowSlam() {
        if (level().isClientSide || this.snowSlamImpactDone || !isAttackAnimating()
                || getAttackAnimation() != ATTACK_SNOW_SLAM
                || !attackReached(SNOW_SLAM_IMPACT_TICK)) {
            return;
        }
        this.snowSlamImpactDone = true;
        double radius = GolemServantConfig.SNOW.SLAM_RADIUS.get();
        double knockback = GolemServantConfig.SNOW.SLAM_KNOCKBACK.get() * difficultyScale();
        double launch = GolemServantConfig.SNOW.SLAM_LAUNCH.get() * difficultyScale();
        double maxHealthPercent = GolemServantConfig.SNOW.SLAM_MAX_HEALTH_DAMAGE_PERCENT.get();
        float damage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                slamAttackBox(radius), this::canAttack)) {
            if (victim == this || isAlliedTo(victim)) {
                continue;
            }
            float victimDamage = damage + (float) (victim.getMaxHealth() * maxHealthPercent / 100.0D);
            if (victim.hurt(damageSources().mobAttack(this), victimDamage)) {
                applySlamKnockback(victim, knockback, launch);
                onAttackHit(victim);
            }
        }
        spawnSlamShockwave();
        playSound(GeneratedMod.ICE_GOLEM_ATTACK.get(), 1.2F, 0.7F);
    }

    private void spawnSlamShockwave() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        float yawRad = slamFacingYaw() * Mth.DEG_TO_RAD;
        double lookX = -Mth.sin(yawRad);
        double lookZ = Mth.cos(yawRad);
        double x = getX() + lookX * GRAVE_SLAM_FORWARD_OFFSET;
        double y = getY() + 0.15D;
        double z = getZ() + lookZ * GRAVE_SLAM_FORWARD_OFFSET;
        BlockState ground = groundStateBelow(server, x, getY(), z);
        BlockParticleOption blockParticles = new BlockParticleOption(ParticleTypes.BLOCK, ground);
        ColorUtil color = new ColorUtil(SLAM_SHOCKWAVE_COLOR);
        double radius = GolemServantConfig.SNOW.SLAM_RADIUS.get();
        server.sendParticles(new SmashParticleOption(color, 2.5F, 8),
                x, y + 0.05D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(color, 4.0F, 10),
                x, y + 0.05D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(color, 5.5F, 12),
                x, y + 0.05D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        ServerParticleUtil.windShockwaveParticle(server, color, (float) radius, 0.0F, 12,
                new Vec3(x, y, z));
        for (int i = 0; i < 8; i++) {
            ServerParticleUtil.circularParticles(server, blockParticles, x, y, z, (float) radius);
        }
        for (int i = 0; i < 14; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double dist = radius * this.random.nextDouble();
            server.sendParticles(GoetyCompat.frostParticle(),
                    x + Math.cos(angle) * dist, y + 0.1D, z + Math.sin(angle) * dist,
                    1, 0.0D, 0.0D, 0.0D, 0.01D);
        }
        playSound(SoundEvents.GENERIC_EXPLODE, 1.2F, 0.6F);
    }

    private static BlockState groundStateBelow(ServerLevel level, double x, double y, double z) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(
                (int) Math.floor(x), (int) Math.floor(y + 0.1D), (int) Math.floor(z));
        for (int i = 0; i < 4; i++) {
            BlockState state = level.getBlockState(cursor);
            if (!state.isAir()) {
                return state;
            }
            cursor.move(0, -1, 0);
        }
        return level.getBlockState(BlockPos.containing(x, y - 0.1D, z));
    }

    private double snowballVolleyChance(LivingEntity throwTarget) {
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        double base = snow.SNOWBALL_VOLLEY_CHANCE.get();
        List<LivingEntity> enemies = nearbyEnemies(snowballRange());
        if (enemies.size() <= 1) {
            if (enemies.size() == 1 && isVolleyHighValue(enemies.get(0))) {
                return 0.0D;
            }
            return base;
        }
        LivingEntity aim = throwTarget != null && throwTarget.isAlive() ? throwTarget : getTarget();
        boolean highValue = aim != null && isVolleyHighValue(aim);
        if (countWeakEnemies(enemies) >= snow.SNOWBALL_VOLLEY_SWARM_COUNT.get()) {
            return highValue ? base : snow.SNOWBALL_VOLLEY_SWARM_CHANCE.get();
        }
        return highValue ? snow.SNOWBALL_VOLLEY_HIGH_VALUE_CHANCE.get() : base;
    }

    private boolean isVolleyHighValue(LivingEntity entity) {
        return entity.getMaxHealth() > GolemServantConfig.SNOW.SNOWBALL_VOLLEY_HIGH_HEALTH.get()
                || RagingStarSupport.isBossLike(entity);
    }

    private int countWeakEnemies(List<LivingEntity> enemies) {
        double threshold = GolemServantConfig.SNOW.SNOWBALL_VOLLEY_WEAK_HEALTH.get();
        int count = 0;
        for (LivingEntity enemy : enemies) {
            if (enemy.getMaxHealth() <= threshold) {
                ++count;
            }
        }
        return count;
    }

    private void beginSnowballVolley(GolemServantConfig.SnowSection snow) {
        this.volleyRoundsLeft = snow.SNOWBALL_VOLLEY_ROUNDS.get();
        this.volleyWindup = SNOW_THROW_BALL_RELEASE_TICK;
        this.volleyShotTimer = 0;
        this.volleyEmptyTicks = 0;
        this.volleyCooldown = snow.SNOWBALL_VOLLEY_COOLDOWN_TICKS.get();
        startAttack(ATTACK_SNOW_THROW_BALL, ATTACK_LENGTHS[ATTACK_SNOW_THROW_BALL]);
    }

    private void tickPendingVolley() {
        if (level().isClientSide || !this.pendingVolleyAfterThrow || isAttackAnimating()) {
            return;
        }
        this.pendingVolleyAfterThrow = false;
        if (this.volleyCooldown <= 0) {
            beginSnowballVolley(GolemServantConfig.SNOW);
        }
    }

    private void tickSnowballVolley() {
        if (level().isClientSide) {
            return;
        }
        setBarrageActive(this.volleyRoundsLeft > 0);
        if (this.volleyRoundsLeft <= 0) {
            return;
        }
        boolean throwing = isAttackAnimating() && getAttackAnimation() == ATTACK_SNOW_THROW_BALL;
        boolean spinning = isAttackAnimating() && getAttackAnimation() == ATTACK_SNOW_THROW_BALL_SPIN;
        if (throwing) {
            if (attackReached(SNOW_THROW_BALL_SPIN_TRANSITION_TICK)) {
                loopSnowballSpin();
            }
        } else if (spinning) {
            if (attackReached(ATTACK_LENGTHS[ATTACK_SNOW_THROW_BALL_SPIN] - 1)) {
                loopSnowballSpin();
            }
        } else {
            loopSnowballSpin();
            return;
        }
        if (this.volleyWindup > 0) {
            --this.volleyWindup;
            return;
        }
        if (this.volleyShotTimer > 0) {
            --this.volleyShotTimer;
            return;
        }
        List<LivingEntity> targets = pickVolleyTargets();
        if (targets.isEmpty()) {
            if (++this.volleyEmptyTicks >= SNOWBALL_VOLLEY_EMPTY_TIMEOUT_TICKS) {
                this.volleyRoundsLeft = 0;
                this.volleyEmptyTicks = 0;
                finishSnowballVolley();
            }
            return;
        }
        this.volleyEmptyTicks = 0;
        for (LivingEntity target : targets) {
            HarmfulSnowball snowball = new HarmfulSnowball(level(), this,
                    GolemServantConfig.SNOW.SNOWBALL_VOLLEY_DAMAGE.get().floatValue());
            aimHarmfulSnowball(snowball, target);
            level().addFreshEntity(snowball);
        }
        for (int stray = 0; stray < STRAY_SNOWBALLS_PER_VOLLEY; stray++) {
            spawnStraySnowball(GolemServantConfig.SNOW.SNOWBALL_VOLLEY_DAMAGE.get().floatValue());
        }
        throwAllyHealSnowball();
        this.volleyShotTimer = Math.max(0, GolemServantConfig.SNOW.SNOWBALL_VOLLEY_INTERVAL_TICKS.get() - 1);
        if (--this.volleyRoundsLeft <= 0) {
            this.volleyRoundsLeft = 0;
            finishSnowballVolley();
        }
        playSound(SoundEvents.SNOW_GOLEM_SHOOT, 0.8F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
    }

    private void finishSnowballVolley() {
        if (isAttackAnimating() && getAttackAnimation() == ATTACK_SNOW_THROW_BALL_SPIN) {
            startAttack(ATTACK_SNOW_THROW_BALL_FINISH, ATTACK_LENGTHS[ATTACK_SNOW_THROW_BALL_FINISH]);
        }
    }

    private void loopSnowballSpin() {
        startAttack(ATTACK_SNOW_THROW_BALL_SPIN, ATTACK_LENGTHS[ATTACK_SNOW_THROW_BALL_SPIN]);
    }

    private List<LivingEntity> pickVolleyTargets() {
        List<LivingEntity> enemies = nearbyEnemies(snowballRange());
        enemies.sort(Comparator.comparingDouble(this::distanceToSqr));
        int max = GolemServantConfig.SNOW.SNOWBALL_VOLLEY_MAX_TARGETS.get();
        if (enemies.size() > max) {
            return new ArrayList<>(enemies.subList(0, max));
        }
        return enemies;
    }

    private List<LivingEntity> nearbyEnemies(double radius) {
        return level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(radius, radius, radius),
                candidate -> candidate != this && isAutoTargetable(candidate)
                        && distanceToSqr(candidate) <= radius * radius);
    }

    private void throwAllyHealSnowball() {
        MutantSnowGolemServant ally = pickWoundedSnowGolemAlly();
        if (ally == null) {
            return;
        }
        Snowball snowball = new Snowball(level(), this);
        snowball.getPersistentData().putFloat(ServantHandler.SNOWBALL_HEAL_TAG, 2.0F);
        aimSnowball(snowball, ally);
        level().addFreshEntity(snowball);
    }

    @Nullable
    private MutantSnowGolemServant pickWoundedSnowGolemAlly() {
        double range = snowballRange();
        return level().getEntitiesOfClass(MutantSnowGolemServant.class,
                        getBoundingBox().inflate(range, range, range),
                        candidate -> candidate.isAlive()
                                && candidate.getHealth() < candidate.getMaxHealth()
                                && ServantFaction.isSameFaction(this, candidate)
                                && distanceToSqr(candidate) <= range * range)
                .stream()
                .min(Comparator.comparingDouble(ally -> ally.getHealth() / ally.getMaxHealth()))
                .orElse(null);
    }

    private static final TagKey<net.minecraft.world.entity.EntityType<?>> FROST_SERVANT_TAG =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "frost_heal"));
    private static final ResourceLocation FROST_BREATH_FOCUS = ResourceLocation.fromNamespaceAndPath("goety", "frost_breath_focus");
    private static final ResourceLocation CHILLING_FOCUS = ResourceLocation.fromNamespaceAndPath("goety", "chilling_focus");

    private static final int SLAM_SHOCKWAVE_COLOR = 0x9FD8FF;

    private static final int FROST_BREATH_BASE_RANGE = 8;

    private static final float ICE_STORM_DAMAGE = 5.0F;

    private static final int ICE_STORM_DAMAGE_INTERVAL_TICKS = 10;

    private static final double ICE_STORM_RADIUS = 1.8D;

    private static final int ICE_STORM_PARTICLE_LAYERS = 5;

    private static final int ICE_STORM_PARTICLES_PER_LAYER = 3;

    private int iceStormDamageTimer;

    private static final int FROST_BREATH_SNOWBALL_INTERVAL_TICKS = 10;

    private static final int FROST_BREATH_SNOWBALL_COUNT = 4;

    private static final float FROST_BREATH_SNOWBALL_SCATTER_YAW_DEGREES = 9.0F;

    private static final float FROST_BREATH_SNOWBALL_SCATTER_PITCH_DEGREES = 5.0F;

    private static final float FROST_BREATH_SNOWBALL_VOLUME = 0.8F;

    private int frostBreathSnowballTimer;

    private static final float FROST_BREATH_MAX_UP_PITCH = 28.0F;

    private static final float FROST_BREATH_MAX_DOWN_PITCH = 80.0F;

    private static final float FROST_BREATH_TURN_DEGREES_PER_TICK = 10.0F;

    private static final float FROST_BREATH_PITCH_DEGREES_PER_TICK = 12.0F;

    private static final double FROST_BREATH_FRONT_ARC_DEGREES = 60.0D;

    private static final float FROST_BREATH_LOOK_DEGREES = 10.0F;

    private static final float FROST_BREATH_SOUND_VOLUME = 1.6F;

    private boolean frostBreathUpgrade;
    private boolean chillingUpgrade;
    private int frostBreathCooldown;
    private int frostBreathDamageTimer;
    private double frostBreathAimX;
    private double frostBreathAimY;
    private double frostBreathAimZ;

    private float frostBreathYaw;
    private float frostBreathPitch;

    private boolean frostBreathAimSeeded;

    private LivingEntity frostBreathTarget;

    private boolean frostBreathQueued;
    private int chillHideCooldown;

    private boolean snowSlamImpactDone;
    private int volleyCooldown;
    private int volleyRoundsLeft;
    private int volleyWindup;
    private int volleyShotTimer;

    private int volleyEmptyTicks;

    private boolean pendingVolleyAfterThrow;

    private float slamYaw;

    private LivingEntity pendingThrowTarget;
    private float pendingThrowDamage;

    private Vec3 pendingThrowAim;

    public boolean isBreathing() {
        return isAttackAnimating() && getAttackAnimation() == ATTACK_FROST_BREATH;
    }

    private boolean isSkillLocked() {
        return isBreathing() || this.volleyRoundsLeft > 0;
    }

    public boolean isSnowballBarrageActive() {
        return this.entityData.get(DATA_BARRAGE_ACTIVE);
    }

    private void setBarrageActive(boolean active) {
        this.entityData.set(DATA_BARRAGE_ACTIVE, active);
    }

    private boolean isSlamAnimating() {
        return isAttackAnimating() && getAttackAnimation() == ATTACK_SNOW_SLAM;
    }

    private void tickFrostBreathDecision() {
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        if (!this.frostBreathUpgrade || !snow.FROST_BREATH_ENABLED.get()
                || this.frostBreathCooldown > 0 || this.volleyRoundsLeft > 0) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double distanceSqr = distanceToSqr(target);
        if (snow.FROST_BREATH_GUARANTEED_ENABLED.get()
                && distanceSqr <= guaranteedRangeSqr(snow)) {
            fireFrostBreath(snow, target);
        }
    }

    private double guaranteedRangeSqr(GolemServantConfig.SnowSection snow) {
        double range = snow.FROST_BREATH_GUARANTEED_RANGE.get();
        return range * range;
    }

    private boolean shouldYieldToGuaranteedFrostBreath() {
        if (!this.frostBreathUpgrade) {
            return false;
        }
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        if (!snow.FROST_BREATH_GUARANTEED_ENABLED.get() || this.frostBreathCooldown > 0) {
            return false;
        }
        LivingEntity target = getTarget();
        return target != null && target.isAlive() && distanceToSqr(target) <= guaranteedRangeSqr(snow);
    }

    private void fireFrostBreath(GolemServantConfig.SnowSection snow, LivingEntity target) {
        Vec3 aim = pickClusterAim(nearbyEnemies(snow.FROST_BREATH_CLUSTER_RADIUS.get()), target);
        if (aim == null) {
            aim = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        }
        if (isSlamAnimating()) {
            this.frostBreathQueued = true;
            return;
        }
        this.frostBreathTarget = target;
        beginFrostBreath(aim);
    }

    private void tickQueuedFrostBreath() {
        if (!this.frostBreathQueued || level().isClientSide) {
            return;
        }
        if (this.frostBreathCooldown > 0) {
            this.frostBreathQueued = false;
            return;
        }
        if (isBreathing()) {
            this.frostBreathQueued = false;
            return;
        }
        if (isSlamAnimating() || this.volleyRoundsLeft > 0 || isAttackAnimating()) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            this.frostBreathQueued = false;
            return;
        }
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        double distanceSqr = distanceToSqr(target);
        if (distanceSqr > guaranteedRangeSqr(snow)) {
            this.frostBreathQueued = false;
            return;
        }
        Vec3 aim = pickClusterAim(nearbyEnemies(snow.FROST_BREATH_CLUSTER_RADIUS.get()), target);
        if (aim == null) {
            aim = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        }
        this.frostBreathQueued = false;
        this.frostBreathTarget = target;
        beginFrostBreath(aim);
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
        double frontLimit = Math.cos(Math.toRadians(FROST_BREATH_FRONT_ARC_DEGREES));
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

    private void beginFrostBreath(Vec3 aim) {
        this.frostBreathAimX = aim.x;
        this.frostBreathAimY = aim.y;
        this.frostBreathAimZ = aim.z;
        this.frostBreathDamageTimer = 0;
        this.frostBreathSnowballTimer = 0;
        this.frostBreathYaw = Mth.wrapDegrees(getYRot());
        this.frostBreathPitch = getXRot();
        this.frostBreathAimSeeded = false;
        this.frostBreathCooldown = GolemServantConfig.SNOW.FROST_BREATH_GUARANTEED_COOLDOWN_TICKS.get();
        getLookControl().setLookAt(aim.x, aim.y, aim.z, FROST_BREATH_LOOK_DEGREES, FROST_BREATH_LOOK_DEGREES);
        startAttack(ATTACK_FROST_BREATH, ATTACK_LENGTHS[ATTACK_FROST_BREATH]);
    }

    private void tickFrostBreath() {
        if (!isBreathing() || !attackReached(AbstractGolemServant.FROST_BREATH_START_TICK)
                || attackReached(AbstractGolemServant.FROST_BREATH_END_TICK + 1)) {
            return;
        }
        if (!level().isClientSide) {
            refreshFrostBreathAim();
            faceAim();
        }
        emitFrostBreathParticles();
        if (level().isClientSide) {
            return;
        }
        tickFrostBreathSnowballs();
        if (this.frostBreathDamageTimer > 0) {
            --this.frostBreathDamageTimer;
            return;
        }
        this.frostBreathDamageTimer = GolemServantConfig.SNOW.FROST_BREATH_DAMAGE_INTERVAL.get();
        castFrostBreath();
    }

    private void tickFrostBreathSnowballs() {
        if (!GolemServantConfig.SNOW.FROST_BREATH_SNOWBALL_ENABLED.get()) {
            return;
        }
        if (this.frostBreathSnowballTimer > 0) {
            --this.frostBreathSnowballTimer;
            return;
        }
        this.frostBreathSnowballTimer = FROST_BREATH_SNOWBALL_INTERVAL_TICKS;
        fireFrostBreathSnowballs();
    }

    private void fireFrostBreathSnowballs() {
        LivingEntity primary = this.frostBreathTarget;
        if (primary == null || !primary.isAlive() || !canAttack(primary)) {
            primary = getTarget();
        }
        if (primary != null && !primary.isAlive()) {
            primary = null;
        }
        float damage = GolemServantConfig.SNOW.FROST_BREATH_SNOWBALL_DAMAGE.get().floatValue();
        Vec3 mouth = frostBreathMouth();
        if (primary != null && canAttack(primary)) {
            HarmfulSnowball aimed = new HarmfulSnowball(level(), this, damage);
            aimed.setPos(mouth.x, mouth.y, mouth.z);
            aimed.setIgnoresInvulnerability(true);
            aimHarmfulSnowball(aimed, primary);
            level().addFreshEntity(aimed);
        }
        float baseYaw = this.frostBreathAimSeeded ? this.frostBreathYaw : getYRot();
        float basePitch = this.frostBreathAimSeeded ? this.frostBreathPitch : getXRot();
        for (int i = 1; i < FROST_BREATH_SNOWBALL_COUNT; i++) {
            HarmfulSnowball stray = new HarmfulSnowball(level(), this, damage);
            stray.setPos(mouth.x, mouth.y, mouth.z);
            stray.setIgnoresInvulnerability(true);
            float yaw = baseYaw + (this.random.nextFloat() * 2.0F - 1.0F)
                    * FROST_BREATH_SNOWBALL_SCATTER_YAW_DEGREES;
            float pitch = Mth.clamp(basePitch + (this.random.nextFloat() * 2.0F - 1.0F)
                            * FROST_BREATH_SNOWBALL_SCATTER_PITCH_DEGREES,
                    -FROST_BREATH_MAX_UP_PITCH, FROST_BREATH_MAX_DOWN_PITCH);
            Vec3 direction = Vec3.directionFromRotation(pitch, yaw);
            double speed = SNOWBALL_MIN_SPEED
                    + this.random.nextDouble() * (SNOWBALL_MAX_SPEED - SNOWBALL_MIN_SPEED);
            stray.shoot(direction.x, direction.y, direction.z, (float) speed, SNOWBALL_INACCURACY);
            level().addFreshEntity(stray);
        }
        playSound(SoundEvents.SNOW_GOLEM_SHOOT, FROST_BREATH_SNOWBALL_VOLUME,
                1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
    }

    private Vec3 frostBreathMouth() {
        return new Vec3(getX(), getEyeY() - 0.35D, getZ()).add(getLookAngle().scale(1.4D));
    }

    private void faceAim() {
        if (!this.frostBreathAimSeeded) {
            this.frostBreathAimSeeded = true;
            this.frostBreathYaw = Mth.wrapDegrees(getYRot());
            this.frostBreathPitch = getXRot();
        }
        double dx = this.frostBreathAimX - getX();
        double dy = this.frostBreathAimY - getEyeY();
        double dz = this.frostBreathAimZ - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float) (Mth.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        float targetPitch = (float) (-(Mth.atan2(dy, horizontal) * 180.0D / Math.PI));
        targetPitch = Mth.clamp(targetPitch, -FROST_BREATH_MAX_UP_PITCH, FROST_BREATH_MAX_DOWN_PITCH);
        this.frostBreathYaw = Mth.wrapDegrees(approachDegrees(
                this.frostBreathYaw, targetYaw, FROST_BREATH_TURN_DEGREES_PER_TICK));
        this.frostBreathPitch = approachDegrees(
                this.frostBreathPitch, targetPitch, FROST_BREATH_PITCH_DEGREES_PER_TICK);
        setYRot(this.frostBreathYaw);
        this.yBodyRot = this.frostBreathYaw;
        this.yHeadRot = this.frostBreathYaw;
        setXRot(this.frostBreathPitch);
    }

    private static float approachDegrees(float current, float target, float maxStep) {
        return current + Mth.clamp(Mth.wrapDegrees(target - current), -maxStep, maxStep);
    }

    private void refreshFrostBreathAim() {
        Vec3 cluster = pickClusterAim(nearbyEnemies(GolemServantConfig.SNOW.FROST_BREATH_CLUSTER_RADIUS.get()),
                this.frostBreathTarget);
        if (cluster != null) {
            this.frostBreathAimX = cluster.x;
            this.frostBreathAimY = cluster.y;
            this.frostBreathAimZ = cluster.z;
            return;
        }
        LivingEntity target = this.frostBreathTarget;
        if (target == null || !target.isAlive()) {
            return;
        }
        this.frostBreathAimX = target.getX();
        this.frostBreathAimY = target.getY() + target.getBbHeight() * 0.5D;
        this.frostBreathAimZ = target.getZ();
    }

    private void emitFrostBreathParticles() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 look = getLookAngle();
        Vec3 mouth = new Vec3(getX(), getEyeY() - 0.35D, getZ()).add(look.scale(1.4D));
        double reach = Math.max(1.0D,
                FROST_BREATH_BASE_RANGE + GolemServantConfig.SNOW.FROST_BREATH_RANGE_LEVEL.get());
        double radius = widenedFrostBreathRadius();
        for (int i = 0; i < 24; i++) {
            double distance = 0.3D + this.random.nextDouble() * reach;
            double spread = 0.25D + radius * (distance / reach);
            double ox = (this.random.nextDouble() - 0.5D) * spread;
            double oy = (this.random.nextDouble() - 0.5D) * spread;
            double oz = (this.random.nextDouble() - 0.5D) * spread;
            server.sendParticles(GoetyCompat.frostParticle(),
                    mouth.x + look.x * distance + ox,
                    mouth.y + look.y * distance + oy,
                    mouth.z + look.z * distance + oz,
                    0, look.x, look.y, look.z, 0.35D);
        }
        for (int i = 0; i < 10; i++) {
            double distance = 0.5D + this.random.nextDouble() * reach;
            double spread = radius * (distance / reach);
            server.sendParticles(ParticleTypes.SNOWFLAKE,
                    mouth.x + look.x * distance + (this.random.nextDouble() - 0.5D) * spread,
                    mouth.y + look.y * distance + (this.random.nextDouble() - 0.5D) * spread,
                    mouth.z + look.z * distance + (this.random.nextDouble() - 0.5D) * spread,
                    0, look.x, look.y, look.z, 0.2D);
        }
    }

    private void castFrostBreath() {
        if (!(level() instanceof ServerLevel)) {
            return;
        }
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        int range = FROST_BREATH_BASE_RANGE + snow.FROST_BREATH_RANGE_LEVEL.get();
        WidenedFrostBreathSpell spell = new WidenedFrostBreathSpell();
        spell.damage = snow.FROST_BREATH_DAMAGE.get().floatValue();
        spell.mobSpellResult(this, ItemStack.EMPTY,
                new SpellStat(0, 1, range, 0.0D, 0, 0.0F));
        applyFrostBreathSlow(range);
        playSound(ModSounds.FROST_BREATH.get(), FROST_BREATH_SOUND_VOLUME, 1.0F);
    }

    private void applyFrostBreathSlow(int range) {
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        int ticks = snow.FROST_BREATH_SLOW_TICKS.get();
        if (ticks <= 0) {
            return;
        }
        int amplifier = snow.FROST_BREATH_SLOW_AMPLIFIER.get();
        double radius = snow.FROST_BREATH_RADIUS.get() + snow.FROST_BREATH_WIDTH_BONUS.get();
        for (Entity entity : AbstractGolemServant.breathTargets(this, range, radius)) {
            if (entity instanceof LivingEntity living && living.isAlive()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ticks,
                        amplifier, false, true));
            }
        }
    }

    private static double widenedFrostBreathRadius() {
        return GolemServantConfig.SNOW.FROST_BREATH_RADIUS.get()
                + GolemServantConfig.SNOW.FROST_BREATH_WIDTH_BONUS.get();
    }

    private static final class WidenedFrostBreathSpell extends FrostBreathSpell {
        @Override
        public List<Entity> getBreathTarget(LivingEntity caster, double range) {
            return AbstractGolemServant.breathTargets(caster, range, widenedFrostBreathRadius());
        }
    }

    private void tickChillHideAura() {
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        if (!this.chillingUpgrade || !snow.CHILLING_ENABLED.get() || level().isClientSide) {
            return;
        }
        if (this.chillHideCooldown > 0) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        applyChillHide();
        this.chillHideCooldown = snow.CHILL_HIDE_COOLDOWN_TICKS.get();
    }

    private void applyChillHide() {
        if (!(level() instanceof ServerLevel server) || GoetyEffects.CHILL_HIDE == null) {
            return;
        }
        GolemServantConfig.SnowSection snow = GolemServantConfig.SNOW;
        addEffect(new MobEffectInstance(GoetyEffects.CHILL_HIDE.get(),
                snow.CHILL_HIDE_TICKS.get(), snow.CHILL_HIDE_LEVEL.get() - 1,
                false, true, true));
        double radius = snow.CHILL_HIDE_ALLY_RADIUS.get();
        AABB area = getBoundingBox().inflate(radius);
        for (LivingEntity nearby : server.getEntitiesOfClass(LivingEntity.class, area)) {
            if (nearby == this || !nearby.isAlive() || distanceToSqr(nearby) > radius * radius) {
                continue;
            }
            if (!(nearby instanceof AbstractFrostGolemServant) && !(nearby instanceof SnowGolemServant)
                    && !nearby.getType().is(FROST_SERVANT_TAG)) {
                continue;
            }
            if (!ServantFaction.isSameFaction(this, nearby)) {
                continue;
            }
            nearby.addEffect(new MobEffectInstance(GoetyEffects.CHILL_HIDE.get(),
                    snow.CHILL_HIDE_ALLY_TICKS.get(), snow.CHILL_HIDE_ALLY_LEVEL.get() - 1,
                    false, true, true));
        }
        spawnChillHideParticles();
        playSound(SoundEvents.GLASS_PLACE, 1.0F, 0.8F);
    }

    private void spawnChillHideParticles() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(ModParticleTypes.FROST.get(),
                getX(), getY() + getBbHeight() * 0.5D, getZ(),
                24, getBbWidth() * 0.7D, getBbHeight() * 0.45D, getBbWidth() * 0.7D, 0.02D);
        ServerParticleUtil.circularParticles(server,
                new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()),
                getX(), getY() + 0.15D, getZ(), (float) (getBbWidth() + 1.5D));
    }

    private void tickSkillCooldowns() {
        if (level().isClientSide) {
            return;
        }
        if (this.frostBreathCooldown > 0) {
            --this.frostBreathCooldown;
        }
        if (this.chillHideCooldown > 0) {
            --this.chillHideCooldown;
        }
        if (this.volleyCooldown > 0) {
            --this.volleyCooldown;
        }
        tickFrostBreathDecision();
        tickQueuedFrostBreath();
    }

    private void tickSkillMovementLock() {
        if (level().isClientSide) {
            return;
        }
        if (this.volleyRoundsLeft > 0) {
            getNavigation().stop();
            return;
        }
        if (!isAttackAnimating()) {
            return;
        }
        int animation = getAttackAnimation();
        if (animation == ATTACK_SNOW_SLAM || animation == ATTACK_FROST_BREATH) {
            getNavigation().stop();
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (player == getTrueOwner()) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(held.getItem());
            boolean chillingFocus = GolemServantConfig.SNOW.CHILLING_ENABLED.get()
                    && !this.chillingUpgrade && CHILLING_FOCUS.equals(itemId);
            boolean breathFocus = GolemServantConfig.SNOW.FROST_BREATH_ENABLED.get()
                    && !this.frostBreathUpgrade && FROST_BREATH_FOCUS.equals(itemId);
            if (chillingFocus || breathFocus) {
                if (!level().isClientSide) {
                    if (chillingFocus) {
                        this.chillingUpgrade = true;
                        consumeFocus(player, held);
                        applyChillHide();
                        this.chillHideCooldown = GolemServantConfig.SNOW.CHILL_HIDE_COOLDOWN_TICKS.get();
                        playSound(SoundEvents.GLASS_PLACE, 1.0F, 0.8F);
                    } else {
                        this.frostBreathUpgrade = true;
                        consumeFocus(player, held);
                        playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.25F);
                    }
                    spawnUpgradeParticles(ParticleTypes.SNOWFLAKE);
                }
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    private void consumeFocus(Player player, ItemStack held) {
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
    }

    private void spawnUpgradeParticles(net.minecraft.core.particles.SimpleParticleType particle) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particle, getX(), getY() + getBbHeight() * 0.55D, getZ(),
                    18, getBbWidth() * 0.55D, getBbHeight() * 0.45D, getBbWidth() * 0.55D, 0.03D);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.ICE_GOLEM_AMBIENT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GeneratedMod.ICE_GOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.ICE_GOLEM_DEATH.get();
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    public boolean usesAnimationDrivenFootsteps() {
        return true;
    }

    @Override
    public void playFootstepSound() {
        playSound(GeneratedMod.ICE_GOLEM_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("FrostBreathUpgrade", this.frostBreathUpgrade);
        tag.putBoolean("ChillingUpgrade", this.chillingUpgrade);
        tag.putInt("FrostBreathCooldown", this.frostBreathCooldown);
        tag.putInt("ChillHideCooldown", this.chillHideCooldown);
        tag.putInt("SnowballVolleyCooldown", this.volleyCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.frostBreathUpgrade = tag.getBoolean("FrostBreathUpgrade");
        this.chillingUpgrade = tag.getBoolean("ChillingUpgrade");
        this.frostBreathCooldown = tag.getInt("FrostBreathCooldown");
        this.chillHideCooldown = tag.getInt("ChillHideCooldown");
        this.volleyCooldown = tag.getInt("SnowballVolleyCooldown");
    }

    @Override
    public void tickDeath() {
        setBarrageActive(false);
        ++this.deathTime;
        if (level() instanceof ServerLevel serverLevel && this.deathTime % 2 == 0) {
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()),
                    getX() + (this.random.nextDouble() - 0.5D) * getBbWidth(),
                    getY() + this.random.nextDouble() * getBbHeight(),
                    getZ() + (this.random.nextDouble() - 0.5D) * getBbWidth(),
                    4, 0.1D, 0.1D, 0.1D, 0.02D);
        }
        int collapse = Math.max(1, GolemServantConfig.SNOW.DEATH_COLLAPSE_TICKS.get());
        if (this.deathTime >= collapse && !this.isRemoved()) {
            if (!level().isClientSide()) {
                this.dropExperience();
            }
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    private static final ResourceLocation PUMPKIN_GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/giant_snow_golem_pumpkin_glow.png");

    @Override
    @Nullable
    public ResourceLocation pumpkinGlowTexture() {
        return PUMPKIN_GLOW;
    }

    @Override
    public PumpkinGlowStyle pumpkinGlowStyle() {
        return PumpkinGlowStyle.SOUL_JAR;
    }
}
