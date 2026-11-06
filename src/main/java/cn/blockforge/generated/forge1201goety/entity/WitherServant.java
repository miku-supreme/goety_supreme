package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
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
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbe;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.util.AnnihilationDamage;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
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
import cn.blockforge.generated.goetysupreme.network.WitherRoarPacket;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.util.WitherServantClientControls;
import cn.blockforge.generated.goetysupreme.event.WitherServantClientEvents;
import cn.blockforge.generated.goetysupreme.util.WitherServantFriendlyFire;
import cn.blockforge.generated.goetysupreme.event.WitherServantGriefingEvents;

public class WitherServant extends AbstractWitherServantBase
        implements OwnableEntity, RangedAttackMob, PowerableMob, SupremeChargeAuraHolder {
    private static final double SKULL_POWER = 0.1D;

    private static final int SIDE_HEAD_FIRE_MIN = 20;

    private static final int SIDE_HEAD_FIRE_SPAN = 11;

    private static final int RAM_LOCK = 170;

    private static final int RAM_DIVE_END = 140;

    private static final int RAM_RECOVER = 100;

    static final double RAM_HIT_INFLATE_XZ = 2.8D;

    static final double RAM_HIT_INFLATE_Y = 2.0D;

    static final double RAM_HIT_STOP_SQR = 9.0D;

    private static final float ANNIHILATION_DAMAGE_MULTIPLIER = 0.5F;

    private static final EntityDataAccessor<Boolean> ANNIHILATING =
            SynchedEntityData.defineId(WitherServant.class, EntityDataSerializers.BOOLEAN);

    private static final TagKey<EntityType<?>> TAG_GOETY_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "bosses"));

    private static final TagKey<EntityType<?>> TAG_GOETY_MINI_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "mini_bosses"));

    private static final TagKey<EntityType<?>> TAG_FORGE_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", "bosses"));

    private int annTicks;

    private int annCooldown;

    public WitherServant(EntityType<? extends Summoned> type, Level level) {
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
        this.entityData.define(ANNIHILATING, false);
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
        if (updateRiddenSteering()) {
            return; // 主人骑乘时完全接管：不寻路、不换点、不冲撞
        }
        updateCommand();
        updateGuardPosition();
        holdMainTarget();
        updateHeadTargets();

        if (updateAnnihilation()) {
            fireSideHeads();
        } else if (isRaging() && updateCharge()) {
            fireSideHeads();
        } else {
            updateFlightAndSalvo();
        }
        updateTailAttack();
    }

    @Override
    public void tick() {
        super.tick();
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
        launchSkullTowards(0, eye.add(pilot.getLookAngle().scale(42.0D)), false,
                random.nextDouble() < enhancedSkullChance());
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
                moveToward(hoverX, hoverY, hoverZ, 0.68D);
                faceTowards(aim, FACE_SNAP_YAW, FACE_SNAP_PITCH);
                if (distanceToSqr(hoverX, hoverY, hoverZ) < 6.25D || travelTicks == 0) {
                    travelTicks = 0; // 到位：开始驻守计时（站满 2~3.5 秒换下一个点）
                }
            } else {
                hoverAtWaypoint();
                faceTowards(aim, FACE_SNAP_YAW, FACE_SNAP_PITCH);
                if (stationHoldTicks <= 0) {
                    stationHoldTicks = 45 + random.nextInt(26);
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
        double angle = random.nextDouble() * Mth.TWO_PI;
        double radius = 10.0D + random.nextDouble() * 5.0D;
        hoverX = target.getX() + Mth.cos((float) angle) * radius;
        hoverY = target.getY() + target.getBbHeight() + 2.0D + random.nextDouble() * 4.0D;
        hoverZ = target.getZ() + Mth.sin((float) angle) * radius;
        double distance = Math.sqrt(Math.max(1.0D, distanceToSqr(hoverX, hoverY, hoverZ)));
        travelTicks = Mth.clamp((int) (distance / 0.35D), 30, 140);
        stationValid = true;
        stationHoldTicks = 0; // 新点位到位后重新站满一个完整周期再挪
        mainHeadCooldown = 4 + random.nextInt(6);
        sideAttackCooldown[0] = 10 + random.nextInt(10);
        sideAttackCooldown[1] = 16 + random.nextInt(10);
    }

    private void tickHeadFire(LivingEntity mainTarget) {
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
        boolean enhanced = random.nextDouble() < enhancedSkullChance();
        double power = enhanced ? EnhancedWitherSkull.SPEED_POWER : SKULL_POWER;
        double inertia = buried ? DANGEROUS_SKULL_INERTIA : SKULL_INERTIA;
        Vec3 aim = ProjectileIntercept.solveForTarget(headPosition(head), target, power, inertia);
        launchSkullTowards(head, aim, buried, enhanced);
        onSkullLaunched();
    }

    private void launchSkullTowards(int head, Vec3 aim, boolean dangerous, boolean enhanced) {
        Vec3 spawn = headPosition(head);
        Vec3 dir = aim.subtract(spawn);
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = new Vec3(0.0D, 0.0D, 1.0D);
        }
        ServantWitherSkull skull;
        if (enhanced) {
            skull = new EnhancedWitherSkull(level(), this, dir);
        } else {
            skull = new ServantWitherSkull(level(), this, dir.x, dir.y, dir.z);
        }
        skull.setMainHeadShot(head == 0);
        skull.setDirectDamage(ServantWitherSkull.SERVANT_DIRECT_DAMAGE + (isRaging() ? 3.0F : 0.0F));
        skull.setAstralSkull(isRaging());
        skull.setPosRaw(spawn.x, spawn.y, spawn.z);
        if (dangerous) {
            skull.setDangerous(true);
        }
        level().addFreshEntity(skull);
        if (!isSilent()) {
            if (enhanced) {
                playSound(SoundEvents.WITHER_SHOOT, 0.5F, 0.25F);
                playSound(ModSounds.HELL_BOLT_SHOOT.get(), 1.0F, 1.0F);
            } else {
                level().levelEvent(null, 1024, blockPosition(), 0);
            }
        }
    }

    private double enhancedSkullChance() {
        return (isRaging() ? WitherServantConfig.ENHANCED_SKULL_CHANCE_RAGING
                : WitherServantConfig.ENHANCED_SKULL_CHANCE).get();
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
        if (annTicks <= 0) {
            annCooldown = Math.max(annCooldown, 200);
            setAnnihilating(false);
        }
    }

    @Override
    public Component getName() {
        if (isStarEmbedded() && !hasCustomName()) {
            return Component.translatable("entity.goety_supreme.wither_annihilation");
        }
        return super.getName();
    }

    protected boolean updateCharge() {
        LivingEntity target = getTarget();
        if (ramTicks > 0) {
            --ramTicks;
            if (ramTicks >= RAM_LOCK) {
                setCharging(true);
                if (target == null) {
                    ramTicks = 0;
                    setCharging(false);
                    return false;
                }
                if (ramTicks == RAM_LOCK) {
                    lockChargeDir(target);
                }
                setDeltaMovement(getDeltaMovement().scale(0.6D));
                return true; // 蓄力：接管移动，只让侧头继续开火
            }
            if (ramTicks > RAM_DIVE_END) {
                setCharging(false);
                return false;
            }
            if (ramTicks >= RAM_RECOVER) {
                setCharging(true);
                doChargeDive(target);
                return true;
            }
            setCharging(false);
            setDeltaMovement(getDeltaMovement().scale(0.86D));
            return false;
        }
        setCharging(false);
        if (annTicks > 0 || isAnnihilating()) {
            return false; // 凋零灭绝蓄力期间不释放冲撞
        }
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

    protected void lockChargeDir(LivingEntity target) {
        Vec3 from = new Vec3(getHeadX(0), getHeadY(0), getHeadZ(0));
        Vec3 to = target.position().add(BallisticAim.leadOffset(target, 6.0D))
                .add(0.0D, target.getBbHeight() * 0.4D, 0.0D);
        Vec3 dir = to.subtract(from);
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
        AABB sweep = getBoundingBox().inflate(RAM_HIT_INFLATE_XZ, RAM_HIT_INFLATE_Y, RAM_HIT_INFLATE_XZ);
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
        if (target != null && target.isAlive() && distanceToSqr(target) < RAM_HIT_STOP_SQR) {
            ramTicks = RAM_RECOVER;
        }
    }

    private void emitChargeTrail(ServerLevel serverLevel, Vec3 dir) {
        for (int i = 0; i < RAM_TRAIL_SEGMENTS; i++) {
            Vec3 point = position().subtract(dir.scale(0.9D + i * 1.1D))
                    .add(0.0D, 0.6D + i * 0.45D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.ASH, point.x, point.y, point.z,
                    RAM_TRAIL_ASH_PER_SEGMENT, RAM_TRAIL_SPREAD, RAM_TRAIL_SPREAD * 0.8D,
                    RAM_TRAIL_SPREAD, 0.015D);
        }
        Vec3 tail = position().subtract(dir.scale(1.6D)).add(0.0D, 0.4D, 0.0D);
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, tail.x, tail.y, tail.z,
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
            ramTicks = RAM_RECOVER;
        }
    }

    private boolean updateAnnihilation() {
        if (!WitherServantConfig.EXTINCTION_ENABLED.get()) {
            annTicks = 0;
            setAnnihilating(false);
            return false;
        }
        if (annTicks > 0) {
            --annTicks;
            setAnnihilating(true);
            setDeltaMovement(getDeltaMovement().scale(0.6D));
            LivingEntity locked = getTarget();
            if (locked != null) {
                faceTowards(locked.position().add(0.0D, locked.getBbHeight() * 0.55D, 0.0D),
                        FACE_SNAP_YAW, FACE_SNAP_PITCH);
            }
            emitAnnihilationSmoke();
            if (annTicks == 0) {
                setAnnihilating(false);
                detonateAnnihilation();
            }
            return true;
        }
        setAnnihilating(false);
        if (annCooldown > 0) {
            --annCooldown;
            return false;
        }
        if (!isRaging()) {
            return false; // 起蓄力仍要求狂暴形态；已在蓄力的上面放行到底
        }
        LivingEntity target = getTarget();
        if (target == null) {
            target = findNearestAnnihilationTarget();
            if (target != null) {
                setTarget(target);
                setAlternativeTarget(0, target.getId());
            }
        }
        if (target == null || getInvulnerableTicks() > 0) {
            return false;
        }
        if (ramTicks > 0) {
            return false; // 冲撞进行中不穿插灭绝蓄力（两者互斥，见"蓄力期间不冲撞"）
        }
        double radius = WitherServantConfig.EXTINCTION_RADIUS.get();
        if (distanceToSqr(target) > radius * radius * 1.25D) {
            return false;
        }
        annTicks = WitherServantConfig.EXTINCTION_CHARGE_SECONDS.get() * 20;
        setAnnihilating(true);
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[CHARGE] 开始蓄力 target=" + AnnihilationProbe.entity(target)
                    + " dist=" + AnnihilationProbe.trim(Math.sqrt(distanceToSqr(target)))
                    + " " + AnnihilationProbe.goetyGates(target)
                    + " | config{" + AnnihilationProbe.goetyConfig() + "}");
        }
        sendCustomSound(GeneratedMod.WITHER_POWER.get(), SoundSource.HOSTILE, 10.0F, 0.9F, null);
        return true;
    }

    @Nullable
    private LivingEntity findNearestAnnihilationTarget() {
        double radius = WitherServantConfig.EXTINCTION_RADIUS.get();
        AABB scan = getBoundingBox().inflate(radius, radius, radius);
        LivingEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : level().getEntitiesOfClass(LivingEntity.class, scan,
                entity -> isAnnihilationUnit(entity) && distanceToSqr(entity) <= radius * radius
                        && hasLineOfSight(entity))) {
            double distance = distanceToSqr(candidate);
            if (distance < nearestDistance) {
                nearest = candidate;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    private boolean isAnnihilationUnit(@Nullable LivingEntity candidate) {
        return !(candidate instanceof Player) && isAnnihilationVictim(candidate);
    }

    private boolean isAnnihilationVictim(@Nullable LivingEntity candidate) {
        if (candidate == null || candidate == this || candidate.isSpectator() || !candidate.isAlive()
                || !canAttack(candidate)) {
            return false;
        }
        if (candidate instanceof Player) {
            return true;
        }
        if (candidate instanceof Enemy) {
            return true;
        }
        LivingEntity hatred = candidate instanceof Mob mob ? mob.getTarget() : null;
        return hatred == this || (hatred != null && isAlliedTo(hatred));
    }

    public boolean isAnnihilating() {
        return Boolean.TRUE.equals(entityData.get(ANNIHILATING));
    }

    private void setAnnihilating(boolean annihilating) {
        if (!level().isClientSide && isAnnihilating() != annihilating) {
            entityData.set(ANNIHILATING, annihilating);
        }
    }

    private void emitAnnihilationSmoke() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY(1.6D), getZ(),
                24, 2.6D, 2.4D, 2.6D, 0.035D);
        serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY(0.8D), getZ(),
                16, 3.2D, 1.4D, 3.2D, 0.02D);
        if (annTicks % 4 == 0) {
            serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(2.6D), getZ(),
                    6, 1.4D, 1.0D, 1.4D, 0.02D);
        }
    }

    private void detonateAnnihilation() {
        annCooldown = WitherServantConfig.EXTINCTION_COOLDOWN_SECONDS.get() * 20;
        double radius = WitherServantConfig.EXTINCTION_RADIUS.get();
        AABB area = getBoundingBox().inflate(radius, radius * 0.5D + 12.0D, radius);
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.recordAt(level(), position(), "[DETONATE] " + AnnihilationProbe.entity(this)
                    + " radius=" + radius
                    + " bossDamage=" + WitherServantConfig.EXTINCTION_BOSS_DAMAGE.get()
                    + " fallback=" + WitherServantConfig.EXTINCTION_FALLBACK_DAMAGE.get()
                    + " | config{" + AnnihilationProbe.goetyConfig() + "}");
            probeAnnihilationCandidates(area, radius);
            probeGoetyGates("爆炸前", area);
        }
        level().explode(this, getX(), getY(), getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
        if (AnnihilationProbe.isEnabled()) {
            probeGoetyGates("爆炸后", area);
        }
        if (isPowered()) {
            summonWitheringMinions();
        }
        playSound(SoundEvents.GENERIC_EXPLODE, 4.0F, 0.6F);
        playSound(SoundEvents.WITHER_DEATH, 5.0F, 0.75F);
        level().globalLevelEvent(1023, blockPosition(), 0);
        List<LivingEntity> victims = level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> isAnnihilationVictim(candidate)
                        && distanceToSqr(candidate) <= radius * radius);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(1.6D), getZ(),
                    6, radius * 0.45D, 4.0D, radius * 0.45D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY(1.6D), getZ(),
                    160, radius * 0.55D, 6.0D, radius * 0.55D, 0.06D);
            serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(1.6D), getZ(),
                    120, radius * 0.5D, 4.0D, radius * 0.5D, 0.01D);
        }
        for (LivingEntity victim : victims) {
            if (victim instanceof Player player) {
                if (AnnihilationProbe.isEnabled()) {
                    AnnihilationProbe.record("[VICTIM] " + AnnihilationProbe.entity(victim)
                            + " player=true dist=" + AnnihilationProbe.trim(Math.sqrt(distanceToSqr(victim)))
                            + " " + AnnihilationProbe.goetyGates(victim));
                }
                annihilatePlayer(player);
                continue;
            }
            boolean bossTier = isBossTier(victim);
            if (AnnihilationProbe.isEnabled()) {
                AnnihilationProbe.record("[VICTIM] " + AnnihilationProbe.entity(victim)
                        + " bossTier=" + bossTier
                        + " dist=" + AnnihilationProbe.trim(Math.sqrt(distanceToSqr(victim)))
                        + " " + AnnihilationProbe.goetyGates(victim));
            }
            if (bossTier) {
                annihilateBossTier(victim);
            } else {
                victim.invulnerableTime = 0;
                boolean killed = victim.hurt(victim.damageSources().genericKill(), Float.MAX_VALUE);
                if (AnnihilationProbe.isEnabled()) {
                    AnnihilationProbe.record("[EXECUTE] 秒杀尝试 " + AnnihilationProbe.entity(victim)
                            + " hurt=" + killed + " alive=" + victim.isAlive());
                }
                if (killed && !victim.isAlive() && level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SOUL, victim.getX(), victim.getY(0.8D), victim.getZ(),
                            12, 0.4D, 0.6D, 0.4D, 0.02D);
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, victim.getX(), victim.getY(0.8D),
                            victim.getZ(), 10, 0.4D, 0.5D, 0.4D, 0.03D);
                    broadcastAnnihilationKill(serverLevel, victim);
                } else if (victim.isAlive()) {
                    annihilateBossTier(victim);
                }
            }
        }
    }

    private void probeAnnihilationCandidates(AABB area, double radius) {
        List<LivingEntity> all = level().getEntitiesOfClass(LivingEntity.class, area);
        AnnihilationProbe.record("[SCAN] 扫描盒内 " + all.size() + " 个生物，半径=" + radius);
        for (LivingEntity candidate : all) {
            double distance = Math.sqrt(distanceToSqr(candidate));
            boolean inRadius = distanceToSqr(candidate) <= radius * radius;
            boolean unit = isAnnihilationUnit(candidate);
            boolean victim = isAnnihilationVictim(candidate);
            boolean bossTier = isBossTier(candidate);
            boolean enemy = candidate instanceof Enemy;
            boolean attackable = canAttack(candidate);
            LivingEntity hatred = candidate instanceof Mob mob ? mob.getTarget() : null;
            AnnihilationProbe.record("[SCAN-ITEM] " + AnnihilationProbe.entity(candidate)
                    + " dist=" + AnnihilationProbe.trim(distance)
                    + " inRadius=" + inRadius
                    + " unit=" + unit
                    + " victim=" + victim
                    + " bossTier=" + bossTier
                    + " enemy=" + enemy
                    + " canAttack=" + attackable
                    + " target=" + (hatred == null ? "null" : AnnihilationProbe.entity(hatred))
                    + " " + AnnihilationProbe.goetyGates(candidate));
        }
    }

    private void probeGoetyGates(String phase, AABB area) {
        for (LivingEntity candidate : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (AnnihilationProbe.hasGoetyGates(candidate)) {
                AnnihilationProbe.record("[GATES-" + phase + "] " + AnnihilationProbe.entity(candidate)
                        + " " + AnnihilationProbe.goetyGates(candidate));
            }
        }
    }

    public boolean debugDetonateNow() {
        if (level().isClientSide) {
            return false;
        }
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[MANUAL] 命令强制引爆 " + AnnihilationProbe.entity(this));
        }
        detonateAnnihilation();
        return true;
    }

    private void broadcastAnnihilationKill(ServerLevel serverLevel, LivingEntity victim) {
        UUID ownerId = getOwnerId();
        if (ownerId == null) {
            return;
        }
        net.minecraft.server.level.ServerPlayer owner =
                serverLevel.getServer().getPlayerList().getPlayer(ownerId);
        if (owner == null) {
            return;
        }
        owner.sendSystemMessage(Component.translatable("chat.goety_supreme.wither_annihilation_kill",
                victim.getName()));
    }

    private void annihilateBossTier(LivingEntity victim) {
        boolean applied = AnnihilationDamage.hurt(this, victim,
                (float) WitherServantConfig.EXTINCTION_BOSS_DAMAGE.get(),
                (float) WitherServantConfig.EXTINCTION_FALLBACK_DAMAGE.get());
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[STRONG-RESULT] applied=" + applied
                    + " " + AnnihilationProbe.entity(victim)
                    + " " + AnnihilationProbe.goetyGates(victim));
        }
        if (applied) {
            int seconds = WitherServantConfig.EXTINCTION_BOSS_WITHER_SECONDS.get();
            if (seconds > 0) {
                victim.addEffect(new MobEffectInstance(MobEffects.WITHER, seconds * 20, 2, false, true));
            }
        }
    }

    private void annihilatePlayer(Player player) {
        boolean applied = AnnihilationDamage.hurtPlayer(this, player,
                (float) WitherServantConfig.EXTINCTION_PLAYER_DAMAGE.get());
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[PLAYER-RESULT] applied=" + applied
                    + " " + AnnihilationProbe.entity(player));
        }
    }

    private static boolean isBossTier(LivingEntity victim) {
        if (victim.getMaxHealth() >= WitherServantConfig.EXTINCTION_BOSS_TIER_MIN_HEALTH.get()) {
            return true;
        }
        if (victim instanceof WitherBoss || victim instanceof EnderDragon) {
            return true;
        }
        EntityType<?> type = victim.getType();
        return type.is(TAG_GOETY_BOSSES) || type.is(TAG_GOETY_MINI_BOSSES) || type.is(TAG_FORGE_BOSSES);
    }

    @Override
    public void travel(Vec3 vanillaInput) {
        Entity controlled = getControllingPassenger();
        if (controlled instanceof Player pilot && isControlledByLocalInstance()
                && WitherServantConfig.RIDING_ENABLED.get() && pilot.isAlive()) {
            setYRot(pilot.getYRot());
            setXRot(pilot.getXRot() * 0.5F);
            setYHeadRot(getYRot());

            double speed = WitherServantConfig.RIDING_SPEED.get();
            float yawRad = getYRot() * Mth.DEG_TO_RAD;
            double sinYaw = Mth.sin(yawRad);
            double cosYaw = Mth.cos(yawRad);
            Vec3 wanted = new Vec3(-sinYaw * pilot.zza + cosYaw * pilot.xxa, 0.0D,
                    cosYaw * pilot.zza + sinYaw * pilot.xxa);
            if (wanted.lengthSqr() > 1.0E-6D) {
                wanted = wanted.normalize().multiply(speed, 0.0D, speed);
            }

            double vy = 0.0D;
            if (WitherServantClientControls.ascendHeld != WitherServantClientControls.descendHeld) {
                vy = WitherServantClientControls.ascendHeld ? 0.35D : -0.35D;
                if (WitherServantClientControls.ascendHeld) {
                    setOnGround(false);
                }
            }
            Vec3 dv = getDeltaMovement();
            setDeltaMovement(dv.x * 0.7D + wanted.x * 0.3D, vy, dv.z * 0.7D + wanted.z * 0.3D);
            move(MoverType.SELF, getDeltaMovement());
            this.resetFallDistance();
            return;
        }
        super.travel(vanillaInput);
    }

    private boolean updateRiddenSteering() {
        Entity controlled = getControllingPassenger();
        if (!(controlled instanceof Player pilot)) {
            return false;
        }
        if (!WitherServantConfig.RIDING_ENABLED.get()) {
            pilot.stopRiding();
            return false;
        }
        setTarget(null);
        setCharging(false);
        setAnnihilating(false);
        travelTicks = 0;
        ramTicks = 0;
        annTicks = 0;
        clearOwnerFollow(); // 骑乘操控期间不跟飞，别让残留的跟飞目标点跟缰绳抢方向
        pilot.fallDistance = 0.0F;

        updateHeadTargets();
        fireSideHeads();
        return true;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity first = getFirstPassenger();
        return first instanceof Player ? (Player) first : null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return getBbHeight() * 0.92D;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && this.isAlive() && held.is(Items.BONE)
                && ServantFeedHeal.feed(this, player, held, 2.0F)) {
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (!held.isEmpty() && ResourceLocation.fromNamespaceAndPath("goety", "ectoplasm").equals(
                BuiltInRegistries.ITEM.getKey(held.getItem()))
                && getTrueOwner() == player && isStarEmbedded()) {
            if (!level().isClientSide) {
                playWitherTheme(player);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
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
        if (!level().isClientSide && WitherServantConfig.RIDING_ENABLED.get()
                && hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).isEmpty()
                && !player.isSecondaryUseActive() && getTrueOwner() == player && getFirstPassenger() == null) {
            if (player.startRiding(this)) {
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, hand);
    }

    public void playWitherTheme(@Nullable Player replayFor) {
        sendCustomSound(GeneratedMod.WITHER_THEME.get(), SoundSource.MUSIC, 100.0F, 1.0F, replayFor);
    }

    private void sendCustomSound(SoundEvent sound, SoundSource source, float volume, float pitch,
            @Nullable Player onlyPlayer) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        double audibleRange = Math.max(16.0D, volume * 16.0D);
        double rangeSqr = audibleRange * audibleRange;
        for (ServerPlayer listener : serverLevel.players()) {
            if (onlyPlayer != null && listener != onlyPlayer) {
                continue;
            }
            if (listener.distanceToSqr(this) > rangeSqr) {
                continue;
            }
            listener.connection.send(new ClientboundSoundPacket(
                    BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound), source,
                    getX(), getY(), getZ(), volume, pitch, random.nextLong()));
        }
    }

    private void tickTargetSight() {
        if (level().isClientSide) {
            return;
        }
        if (annTicks > 0 || isAnnihilating()) {
            targetSightTicks = 0;
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
        float multiplier = this.isAnnihilating() ? ANNIHILATION_DAMAGE_MULTIPLIER : 1.0F;
        SupremeBossHurt.hurt(this, source, amount, cap, multiplier);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Raging", isRaging());
        tag.putBoolean("StarEmbedded", isStarEmbedded());
        tag.putInt("RamTicks", ramTicks);
        tag.putInt("RamCooldown", ramCooldown);
        tag.putInt("AnnTicks", annTicks);
        tag.putInt("AnnCooldown", annCooldown);
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
        annTicks = tag.getInt("AnnTicks");
        annCooldown = tag.getInt("AnnCooldown");
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
