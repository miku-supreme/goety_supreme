package cn.blockforge.generated.goetysupreme.entity;

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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowSerponCombatExecuterGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;

public class WarPythonServant extends AbstractJerotesServant {

    public static final int SEGMENTS = 16;

    private static final float PART_DAMAGE_MULTIPLIER = 0.8F;

    private static final float FIRE_ABSORPTION_PERCENTAGE = 20.0F;

    private static final double OWNER_FOLLOW_START = 10.0D;
    private static final double OWNER_FOLLOW_STOP = 3.0D;
    private static final int OWNER_FOLLOW_RECALC_TICKS = 10;
    private static final double OWNER_FOLLOW_STANDOFF = 2.5D;
    private static final double OWNER_FOLLOW_SPEED = 1.0D;

    private boolean ownerFollowing;
    private Vec3 ownerFollowTarget;
    private int ownerFollowRecalc;

    private static final EntityDataAccessor<java.util.Optional<UUID>> CHILD_UUID =
            SynchedEntityData.defineId(WarPythonServant.class, EntityDataSerializers.OPTIONAL_UUID);

    private int inLove;

    private UUID loveCause;

    public WarPythonServant(EntityType<? extends WarPythonServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 30;
        setMaxUpStep(2.1F);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GUNPOWDER);
    }

    public boolean canMate(WarPythonServant other) {
        return other != this && !isBaby() && !other.isBaby() && inLove > 0 && other.inLove > 0;
    }

    public WarPythonServant getBreedOffspring(ServerLevel level) {
        return ModEntityTypes.WAR_PYTHON_SERVANT.get().create(level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (player == getTrueOwner() && ServantFeedHeal.tryBlazeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (player == getTrueOwner() && ServantFeedHeal.tryFireChargeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (isFood(held) && isAlive() && !isBaby()) {
            if (!level().isClientSide() && inLove <= 0) {
                inLove = 600;
                loveCause = player.getUUID();
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                spawnLoveParticles();
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private void spawnLoveParticles() {
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 7; i++) {
                serverLevel.sendParticles(ParticleTypes.HEART,
                        getRandomX(0.5D), getRandomY() + 0.3D, getRandomZ(0.5D),
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void tickBreeding() {
        if (inLove <= 0) {
            return;
        }
        --inLove;
        if (inLove % 30 != 0 || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (WarPythonServant other : level().getEntitiesOfClass(WarPythonServant.class,
                getBoundingBox().inflate(8.0D))) {
            if (!canMate(other)) {
                continue;
            }
            WarPythonServant child = getBreedOffspring(serverLevel);
            if (child == null) {
                return;
            }
            child.setBaby(true);
            child.moveTo(getX(), getY(), getZ(), 0.0F, 0.0F);
            LivingEntity owner = getTrueOwner() != null ? getTrueOwner() : other.getTrueOwner();
            if (owner != null) {
                child.setTrueOwner(owner);
            }
            serverLevel.addFreshEntity(child);
            serverLevel.sendParticles(ParticleTypes.HEART,
                    getX(), getY() + getBbHeight() * 0.5D, getZ(),
                    4, 0.2D, 0.2D, 0.2D, 0.0D);
            inLove = 0;
            other.inLove = 0;
            break;
        }
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/war_python.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.WAR_PYTHON;
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        return navigation;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ServantWanderGoal);
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(2, new FollowSerponCombatExecuterGoal(this, 1.1D));
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.5F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("war_python_ambient", SoundEvents.ENDER_DRAGON_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("war_python_hurt", SoundEvents.ENDER_DRAGON_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("war_python_death", SoundEvents.ENDER_DRAGON_DEATH);
    }

    @Override
    public void doPush(Entity entity) {
        if (!(entity instanceof WarPythonPartEntity)) {
            super.doPush(entity);
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FIRE)
                || source.is(DamageTypeTags.IS_FALL)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castFireAbsorption(source, amount, FIRE_ABSORPTION_PERCENTAGE);
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !isSilent()) {
            playSound(jerotesSound("war_python_attack", SoundEvents.ENDER_DRAGON_GROWL), 1.0F, 1.0F);
        }
        return hit;
    }

    @Override
    public int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        resetFallDistance();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void playBlockFallSound() {
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CHILD_UUID, java.util.Optional.empty());
    }

    public UUID getChildId() {
        return entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(UUID uniqueId) {
        entityData.set(CHILD_UUID, java.util.Optional.ofNullable(uniqueId));
    }

    public EntityType<WarPythonPartEntity> partType() {
        return ModEntityTypes.WAR_PYTHON_PART.get();
    }

    public float partRadius() {
        return 0.4F;
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.tickAirborneOwnerFollow();
    }

    private void tickAirborneOwnerFollow() {
        LivingEntity owner = getTrueOwner();
        boolean eligible = owner != null && owner.isAlive() && !owner.isSpectator()
                && owner.level() == level() && isFollowing() && !isCommanded()
                && getTarget() == null && !isPassenger() && !isLeashed()
                && isNoGravity()
                && getNavigation().isDone();
        if (!eligible) {
            if (ownerFollowing) {
                ownerFollowing = false;
                ownerFollowTarget = null;
            }
            return;
        }
        double distanceSqr = distanceToSqr(owner);
        if (ownerFollowing) {
            if (distanceSqr <= OWNER_FOLLOW_STOP * OWNER_FOLLOW_STOP) {
                ownerFollowing = false;
            }
        } else if (distanceSqr > OWNER_FOLLOW_START * OWNER_FOLLOW_START) {
            ownerFollowing = true;
            ownerFollowRecalc = 0;
        }
        if (!ownerFollowing) {
            if (ownerFollowTarget != null) {
                ownerFollowTarget = null;
                getMoveControl().setWantedPosition(getX(), getY(), getZ(), 0.0D);
            }
            return;
        }
        if (--ownerFollowRecalc <= 0 || ownerFollowTarget == null) {
            ownerFollowRecalc = OWNER_FOLLOW_RECALC_TICKS;
            ownerFollowTarget = ownerHoverPoint(owner);
        }
        getMoveControl().setWantedPosition(ownerFollowTarget.x, ownerFollowTarget.y,
                ownerFollowTarget.z, OWNER_FOLLOW_SPEED);
        getLookControl().setLookAt(owner, 10.0F, getMaxHeadXRot());
    }

    private Vec3 ownerHoverPoint(LivingEntity owner) {
        double dx = getX() - owner.getX();
        double dz = getZ() - owner.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-3D) {
            float yaw = owner.getYRot() * ((float) Math.PI / 180.0F);
            dx = Mth.sin(yaw) * OWNER_FOLLOW_STANDOFF;
            dz = -Mth.cos(yaw) * OWNER_FOLLOW_STANDOFF;
        } else {
            dx = dx / horizontal * OWNER_FOLLOW_STANDOFF;
            dz = dz / horizontal * OWNER_FOLLOW_STANDOFF;
        }
        double wave = (Math.sin((tickCount + (getId() & 0xFF) * 7) * 0.05D) + 1.0D) * 0.5D;
        return new Vec3(owner.getX() + dx, owner.getY() + 2.0D + wave * 1.0D, owner.getZ() + dz);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity currentTarget = getTarget();
        if (currentTarget != null && (!currentTarget.isAlive()
                || (currentTarget instanceof Player player
                        && (player.isCreative() || player.isSpectator())))) {
            setTarget(null);
        }
        if (random.nextInt(900) == 1 && deathTime == 0) {
            heal(3.0F);
        }
        tickBreeding();
        if (level().isClientSide) {
            level().addParticle(ParticleTypes.FLAME, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D),
                    0.0D, 0.0D, 0.0D);
            return;
        }
        ensureParts();
    }

    private void ensureParts() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        UUID childId = getChildId();
        if (childId != null && server.getEntity(childId) != null) {
            return;
        }
        LivingEntity parentPart = this;
        PlayerTeam team = (PlayerTeam) getTeam();
        for (int i = 0; i < SEGMENTS; ++i) {
            WarPythonPartEntity part = new WarPythonPartEntity(
                    partType(), parentPart, partRadius(), 180.0F, 0.0F);
            part.setBodyIndex(i);
            part.damageMultiplier = PART_DAMAGE_MULTIPLIER;
            if (parentPart == this) {
                setChildId(part.getUUID());
            }
            part.setInitialPartPos(this);
            parentPart = part;
            server.addFreshEntity(part);
            if (team != null) {
                server.getScoreboard().addPlayerToTeam(part.getStringUUID(), team);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (getChildId() != null) {
            tag.putUUID("ChildUUID", getChildId());
        }
        tag.putInt("InLove", inLove);
        if (loveCause != null) {
            tag.putUUID("LoveCause", loveCause);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("ChildUUID")) {
            setChildId(tag.getUUID("ChildUUID"));
        }
        inLove = tag.getInt("InLove");
        loveCause = tag.hasUUID("LoveCause") ? tag.getUUID("LoveCause") : null;
    }
}
