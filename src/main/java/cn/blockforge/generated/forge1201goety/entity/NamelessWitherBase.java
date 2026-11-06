package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.util.ServantArmorTrims;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.api.SupremeChargeAuraHolder;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;
import cn.blockforge.generated.goetysupreme.data.WitheringMinionLedger;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class NamelessWitherBase extends AbstractWitherServantBase
        implements OwnableEntity, RangedAttackMob, PowerableMob, SupremeChargeAuraHolder {
    private static final double SKULL_POWER = 0.12D;

    private static final int SIDE_HEAD_FIRE_MIN = 27;

    private static final int SIDE_HEAD_FIRE_SPAN = 14;

    protected int ramLockTicks = 195;

    protected int ramDiveEndTicks = 195;

    protected int ramRecoverTicks = 6;

    protected double ramHitInflateXZ = 1.0D;

    protected double ramHitInflateY = 1.0D;

    protected double ramHitStopSqr = 1.0D;

    private int sharedSkullCooldown;

    public NamelessWitherBase(EntityType<? extends Summoned> type, Level level) {
        super(type, level);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD_TARGETS.get(0), 0);
        this.entityData.define(HEAD_TARGETS.get(1), 0);
        this.entityData.define(HEAD_TARGETS.get(2), 0);
        this.entityData.define(INVULNERABLE_TICKS, 0);
        this.entityData.define(RAGING, false);
        this.entityData.define(CHARGING, false);
        this.entityData.define(STAR_EMBEDDED, false);
    }

    @Override
    public void customServerAiStep() {
        sanitizeTargets();
        super.customServerAiStep();
        tickTargetSight();
        sanitizeTargets();

        updateInvulnerability();
        updateSpawnSequence();
        if (spawnSequenceActive) {
            setDeltaMovement(Vec3.ZERO);
            clearOwnerFollow();
            return;
        }
        syncStarArmor();
        updateRageState();
        updateRagingRegen();
        updateHalfHealthResistance();
        updateCommand();
        updateGuardPosition();
        holdMainTarget();
        updateHeadTargets();

        runCombatBranch();
        updateTailAttack();
    }

    protected void runCombatBranch() {
        if (this.ramTicks > 0) {
            this.updateCharge();
            return;
        }
        if (isRaging() && updateCharge()) {
            fireSideHeads();
        } else {
            updateFlightAndSalvo();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.sharedSkullCooldown > 0) {
            --this.sharedSkullCooldown;
        }
        updateHeadAngles();
        if (roarCooldown > 0) {
            --roarCooldown;
        }
        if (this.level().isClientSide) {
            spawnAmbientParticles();
        } else {
            ServantTeleportHelper.tick(this);
        }
    }

    public void handleRoar(LivingEntity pilot) {
        if (level().isClientSide || roarCooldown > 0 || pilot != getControllingPassenger()) {
            return;
        }
        Vec3 eye = pilot.getEyePosition();
        launchSkullTowards(0, eye.add(pilot.getLookAngle().scale(42.0D)), false);
        roarCooldown = 20;
    }

    private void updateFlightAndSalvo() {
        LivingEntity target = getTarget();
        haltMoveControl();

        if (isStaying()) {
            travelTicks = 0;
            stationValid = false; // 取消站桩点：解除待命后按跟随/战斗重新挑点
            stationHoldTicks = 0;
            clearOwnerFollow();
            setDeltaMovement(Vec3.ZERO);
            if (target != null) {
                Vec3 aim = target.position().add(0.0D, target.getBbHeight() * 0.55D, 0.0D);
                faceTowards(aim, FACE_SNAP_YAW, FACE_SNAP_PITCH);
                tickHeadFire(target);
            }
            return;
        }

        if (target != null) {
            orbitInitialized = false;
            Vec3 aim = target.position().add(0.0D, target.getBbHeight() * 0.55D, 0.0D);
            if (needsNewStation(target)) {
                chooseCombatWaypoint(target);
            }
            if (travelTicks > 0) {
                --travelTicks;
                moveToward(hoverX, hoverY, hoverZ, 0.5D);
                faceTowards(aim, FACE_SNAP_YAW, FACE_SNAP_PITCH);
                if (distanceToSqr(hoverX, hoverY, hoverZ) < 6.25D || travelTicks == 0) {
                    travelTicks = 0; // 到位：开始驻守计时（站满 2~3.5 秒换下一个点）
                }
            } else {
                hoverAtWaypoint();
                faceTowards(aim, FACE_SNAP_YAW, FACE_SNAP_PITCH);
                if (stationHoldTicks <= 0) {
                    stationHoldTicks = 65 + random.nextInt(26);
                } else if (--stationHoldTicks == 0) {
                    chooseCombatWaypoint(target);
                }
            }
            tickHeadFire(target);
            return;
        }

        travelTicks = 0;
        mainHeadCooldown = 0;
        stationHoldTicks = 0;
        if (getInvulnerableTicks() > 0) {
            setDeltaMovement(getDeltaMovement().scale(0.85D)); // 出生保护期悬空，同原版
            return;
        }
        if (commandPosEntity == null && commandPos == null) {
            LivingEntity owner = getTrueOwner();
            if (isFollowing() && owner != null && owner.isAlive() && owner.level() == level()) {
                if (owner.getBbHeight() > OWNER_HOVER_MAX_HEIGHT) {
                    maintainGiantOwnerFollow(owner);
                } else {
                    followOwner(owner);
                }
                return;
            }
        }
        clearOwnerFollow();
        Vec3 destination = peacefulDestination();
        if (destination != null) {
            double groundY = groundYAt(destination.x, destination.z);
            moveToward(destination.x, groundY, destination.z, PEACEFUL_MOVE_SPEED);
            faceDirectionOfTravel(8.0F);
        } else {
            landAt(getX(), getZ());
        }
    }

    private void chooseCombatWaypoint(LivingEntity target) {
        double oldX = this.hoverX;
        double oldY = this.hoverY;
        double oldZ = this.hoverZ;
        boolean hadOld = this.stationValid;
        double angle = random.nextDouble() * Mth.TWO_PI;
        double radius = 7.0D + random.nextDouble() * 5.0D;
        double nx = target.getX() + Mth.cos((float) angle) * radius;
        double ny = target.getY() + target.getBbHeight() + this.hoverHeightOffset();
        double nz = target.getZ() + Mth.sin((float) angle) * radius;
        if (hadOld) {
            // 与上一个站桩点：水平距离 ≤ 20 格、竖直差 ≤ 2 格
            double dx = nx - oldX;
            double dz = nz - oldZ;
            double d2 = dx * dx + dz * dz;
            if (d2 > 20.0D * 20.0D) {
                double f = 20.0D / Math.sqrt(d2);
                nx = oldX + dx * f;
                nz = oldZ + dz * f;
            }
            double dy = ny - oldY;
            if (dy > 2.0D) {
                ny = oldY + 2.0D;
            } else if (dy < -2.0D) {
                ny = oldY - 2.0D;
            }
        }
        hoverX = nx;
        hoverY = ny;
        hoverZ = nz;
        double distance = Math.sqrt(Math.max(1.0D, distanceToSqr(hoverX, hoverY, hoverZ)));
        travelTicks = Mth.clamp((int) (distance / 0.35D), 30, 140);
        stationValid = true;
        stationHoldTicks = 0; // 新点位到位后重新站满一个完整周期再挪
        mainHeadCooldown = 4 + random.nextInt(6);
        sideAttackCooldown[0] = 10 + random.nextInt(10);
        sideAttackCooldown[1] = 16 + random.nextInt(10);
    }

    private void tickHeadFire(LivingEntity mainTarget) {
        if (this.sharedSkullCooldown > 0) {
            return;
        }
        if (--mainHeadCooldown <= 0) {
            launchSkull(0, mainTarget);
            mainHeadCooldown = MAIN_HEAD_FIRE_MIN + random.nextInt(MAIN_HEAD_FIRE_SPAN);
        }
        fireSideHeads();
    }

    private void fireSideHeads() {
        for (int head = 1; head <= 2; ++head) {
            int index = head - 1;
            if (--sideAttackCooldown[index] > 0) {
                continue;
            }
            if (this.sharedSkullCooldown > 0) {
                continue;
            }
            sideAttackCooldown[index] = SIDE_HEAD_FIRE_MIN + random.nextInt(SIDE_HEAD_FIRE_SPAN);
            LivingEntity sideTarget = headTarget(head);
            if (sideTarget != null && distanceToSqr(sideTarget) <= TARGET_RANGE_SQR && hasLineOfSight(sideTarget)) {
                launchSkull(head, sideTarget);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        if (!level().isClientSide && target != null) {
            launchSkull(0, target);
        }
    }

    private void launchSkull(int head, LivingEntity target) {
        boolean buried = isTargetBuried(target);
        double power = SKULL_POWER;
        double inertia = buried ? DANGEROUS_SKULL_INERTIA : SKULL_INERTIA;
        Vec3 aim = ProjectileIntercept.solveForTarget(headPosition(head), target, power, inertia);
        launchSkullTowards(head, aim, buried);
        this.sharedSkullCooldown = this.sharedSkullGap();
        onSkullLaunched();
    }

    protected int sharedSkullGap() {
        return 10;
    }

    protected double hoverHeightOffset() {
        return 1.5D + random.nextDouble() * 2.0D;
    }

    private void launchSkullTowards(int head, Vec3 aim, boolean dangerous) {
        Vec3 spawn = headPosition(head);
        Vec3 dir = aim.subtract(spawn);
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = new Vec3(0.0D, 0.0D, 1.0D);
        }
        ServantWitherSkull skull = new ServantWitherSkull(level(), this, dir.x, dir.y, dir.z);
        skull.setMainHeadShot(head == 0);
        skull.setDirectDamage(12.0F);
        skull.setAstralSkull(false);
        skull.setPosRaw(spawn.x, spawn.y, spawn.z);
        if (dangerous) {
            skull.setDangerous(true);
        }
        level().addFreshEntity(skull);
        if (!isSilent()) {
            level().levelEvent(null, 1024, blockPosition(), 0);
        }
    }

    private void updateRageState() {
        boolean starred = isStarEmbedded();
        if (starred && !isRaging()) {
            enterRagingForm();
        } else if (!starred && isRaging()) {
            exitRagingForm();
        }
    }

    private void exitRagingForm() {
        if (level().isClientSide || !isRaging()) {
            return;
        }
        entityData.set(RAGING, false);
        applyKnockbackResistance();
        ramTicks = 0;
        ramCooldown = 100;
        forceRamNext = false; // 退出狂暴，欠着的那次必冲一并作废
        setCharging(false);
        setDeltaMovement(getDeltaMovement().scale(0.4D));
        playSound(SoundEvents.BEACON_DEACTIVATE, 1.5F, 0.7F);
    }

    protected boolean updateCharge() {
        LivingEntity target = getTarget();
        if (ramTicks > 0) {
            --ramTicks;
            if (ramTicks >= ramLockTicks) {
                setCharging(true);
                if (ramTicks == ramLockTicks) {
                    lockChargeDir(target);
                }
                setDeltaMovement(getDeltaMovement().scale(0.6D));
                return true; // 蓄力：接管移动，只让侧头继续开火
            }
            if (ramTicks > ramDiveEndTicks) {
                setCharging(false);
                return false;
            }
            if (ramTicks >= ramRecoverTicks) {
                setCharging(true);
                doChargeDive(target);
                return true;
            }
            setCharging(false);
            setDeltaMovement(getDeltaMovement().scale(0.86D));
            return false;
        }
        setCharging(false);
        if (target != null) {
            if (ramCooldown > 0) {
                --ramCooldown;
            }
            if (forceRamNext || ramCooldown <= 0) {
                double distance = distanceToSqr(target);
                if (forceRamNext || (distance > 64.0D && distance < TARGET_RANGE_SQR)) {
                    forceRamNext = false; // 用掉这次必冲
                    ramTicks = RAM_TOTAL;
                    ramHits.clear();
                    ramCooldown = 160 + random.nextInt(120);
                    playSound(SoundEvents.WITHER_SHOOT, 1.5F, 0.5F);
                } else {
                    ramCooldown = 40;
                }
            }
        }
        return false;
    }

    protected void lockChargeDir(@Nullable LivingEntity target) {
        Vec3 from = new Vec3(getHeadX(0), getHeadY(0), getHeadZ(0));
        Vec3 dir;
        if (target != null && target.isAlive()) {
            Vec3 to = target.position().add(BallisticAim.leadOffset(target, 6.0D))
                    .add(0.0D, target.getBbHeight() * 0.4D, 0.0D);
            dir = to.subtract(from);
        } else {
            dir = this.getLookAngle();
        }
        if (dir.lengthSqr() < 1.0E-6D) {
            dir = new Vec3(0.0D, 0.0D, 1.0D);
        }
        chargeDir = dir.normalize();
        faceDir(chargeDir);
    }

    protected void doChargeDive(@Nullable LivingEntity target) {
        if (target != null && target.isAlive() && tickCount % 4 == 0) {
            Vec3 to = target.position().add(0.0D, target.getBbHeight() * 0.4D, 0.0D).subtract(position());
            if (to.lengthSqr() > 1.0E-6D) {
                chargeDir = chargeDir.add(to.normalize().scale(0.18D)).normalize();
            }
            faceDir(chargeDir);
        }
        setDeltaMovement(chargeDir.scale(RAM_DIVE_SPEED).add(0.0D, -RAM_DIVE_SINK, 0.0D));
        if (level() instanceof ServerLevel serverLevel) {
            emitChargeTrail(serverLevel, chargeDir);
        }
        AABB sweep = getBoundingBox().inflate(ramHitInflateXZ, ramHitInflateY, ramHitInflateXZ);
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, sweep,
                candidate -> isValidEnemy(candidate, 40.0D))) {
            if (!ramHits.add(victim.getUUID())) {
                continue;
            }
            onRamHit(victim);
        }
        if (shouldRamDestroyBlocks()) {
            destroyChargeBlocks();
        }
        if (target != null && target.isAlive() && distanceToSqr(target) < ramHitStopSqr) {
            ramTicks = ramRecoverTicks;
        }
    }

    private void emitChargeTrail(ServerLevel serverLevel, Vec3 dir) {
        for (int i = 0; i < RAM_TRAIL_SEGMENTS; i++) {
            Vec3 point = position().subtract(dir.scale(0.9D + i * 1.1D))
                    .add(0.0D, 0.6D + i * 0.45D, 0.0D);
            // 冲锋拖尾换成绿色火焰粒子（绿豆绿火），去掉原来的灰烬/粉粒子
            serverLevel.sendParticles(GreenSmithFx.necroFire(), point.x, point.y, point.z,
                    RAM_TRAIL_ASH_PER_SEGMENT, RAM_TRAIL_SPREAD, RAM_TRAIL_SPREAD * 0.8D,
                    RAM_TRAIL_SPREAD, 0.015D);
        }
        Vec3 tail = position().subtract(dir.scale(1.6D)).add(0.0D, 0.4D, 0.0D);
        serverLevel.sendParticles(GreenSmithFx.necroFire(), tail.x, tail.y, tail.z,
                RAM_TRAIL_SMOKE, 0.35D, 0.35D, 0.35D, 0.02D);
    }

    private void destroyChargeBlocks() {
        boolean griefing = WitherServantConfig.blockDestructionAllowed()
                && ForgeEventFactory.getMobGriefingEvent(level(), this);
        AABB box = getBoundingBox().inflate(0.35D);
        int minX = Mth.floor(box.minX);
        int maxX = Mth.floor(box.maxX);
        int minY = Mth.floor(box.minY);
        int maxY = Mth.floor(box.maxY);
        int minZ = Mth.floor(box.minZ);
        int maxZ = Mth.floor(box.maxZ);
        boolean broken = false;
        boolean blocked = false;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level().getBlockState(pos);
                    if (state.isAir() || !state.blocksMotion()) {
                        continue;
                    }
                    boolean weak = state.getDestroySpeed(level(), pos) <= COBBLESTONE_HARDNESS;
                    if (griefing && weak && level().destroyBlock(pos, true)) {
                        broken = true;
                    } else if ((!griefing || !weak) && isFacingAhead(pos)) {
                        blocked = true;
                    }
                }
            }
        }
        if (broken) {
            level().levelEvent(1022, blockPosition(), 0);
        }
        if (blocked) {
            ramTicks = ramRecoverTicks;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && this.isAlive()) {
            if (isSoulJar(held) && ServantFeedHeal.feed(this, player, held, 100.0F)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (held.is(Items.BONE) && ServantFeedHeal.feed(this, player, held, 2.0F)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        if (isRaging() && getTrueOwner() == player && held.is(Items.WITHER_SKELETON_SKULL)) {
            if (!level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                forceRamNext = true;
                playSound(SoundEvents.WITHER_SKELETON_AMBIENT, 2.0F, 0.6F);
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(1.6D), getZ(),
                            18, 0.8D, 0.7D, 0.8D, 0.03D);
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    protected static boolean isSoulJar(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = id.getPath();
        return path.contains("soul_jar") && !path.equals("empty_soul_jar");
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
            setAlternativeTarget(0, 0);
            targetSightTicks = 0;
        }
    }

    @Override
    public void actuallyHurt(DamageSource source, float amount) {
        if (source.is(ModDamageSource.DISMISSED) || ExecutionDamage.isExecution(source, amount)) {
            super.actuallyHurt(source, amount);
            return;
        }
        float cap = this.isRaging()
                ? (float) (double) WitherServantConfig.RAGING_MAX_DAMAGE_PER_HIT.get() : 0.0F;
        float multiplier = 1.0F;
        SupremeBossHurt.hurt(this, source, amount, cap, multiplier);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Raging", isRaging());
        tag.putBoolean("StarEmbedded", isStarEmbedded());
        tag.putInt("RamTicks", ramTicks);
        tag.putInt("RamCooldown", ramCooldown);
        tag.putBoolean("SpawnSequenceActive", spawnSequenceActive);
        tag.putBoolean("SpawnExplosionDone", spawnExplosionDone);
        tag.putBoolean("HalfHealthResistanceOwned", halfHealthResistance != null
                && getEffect(MobEffects.DAMAGE_RESISTANCE) == halfHealthResistance);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        halfHealthResistance = tag.getBoolean("HalfHealthResistanceOwned")
                ? getEffect(MobEffects.DAMAGE_RESISTANCE) : null;
        ramTicks = tag.getInt("RamTicks");
        ramCooldown = tag.getInt("RamCooldown");
        spawnSequenceActive = tag.getBoolean("SpawnSequenceActive");
        spawnExplosionDone = tag.getBoolean("SpawnExplosionDone");
        if (tag.getBoolean("StarEmbedded")) {
            entityData.set(STAR_EMBEDDED, true);
        }
        if (tag.getBoolean("Raging") && !level().isClientSide) {
            entityData.set(RAGING, true);
            applyRagingAttributes();
        }
        if (!level().isClientSide) {
            armorSynced = true;
            applyStarArmor();
        }
    }

}
