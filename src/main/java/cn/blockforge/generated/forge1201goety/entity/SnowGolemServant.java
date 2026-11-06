package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ai.ServantHurtByTargetGoal;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.event.ForgeEventFactory;
import cn.blockforge.generated.goetysupreme.config.SnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantOwnerTargetGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.util.OwnerChain;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.data.SnowServantRoster;

public class SnowGolemServant extends Summoned implements IServant, OwnableEntity, RangedAttackMob,
        Shearable, IForgeShearable {
    private static final int WANDERING_FLAG = 1;
    private static final int STAYING_FLAG = 2;
    private static final int NATURAL_FLAG = 4;
    private static final int PUMPKIN_FLAG = 16;
    private static final int ATTACK_INTERVAL_TICKS = 20;
    private static final double SUPPORT_RANGE_SQR = 36.0D;
    private static final net.minecraft.network.syncher.EntityDataAccessor<Byte> SERVANT_FLAGS =
            net.minecraft.network.syncher.SynchedEntityData.defineId(SnowGolemServant.class,
                    net.minecraft.network.syncher.EntityDataSerializers.BYTE);

    private boolean hostile;
    private LivingEntity supportTarget;
    private int supportCooldown;

    private int snowballPotency;

    public SnowGolemServant(EntityType<? extends SnowGolemServant> entityType, Level level) {
        super(entityType, level);
        setHostile(false);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return SnowGolem.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void setConfigurableAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), 4.0D);
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), (double) 0.2F);
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), 32.0D);
    }

    private static void setBaseAttribute(AttributeInstance attribute, double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
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
        entityData.define(SERVANT_FLAGS, (byte) PUMPKIN_FLAG);
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
        if (MutantMonstersCompat.isLoaded()) {
            this.goalSelector.addGoal(2, new HealMutantSnowGolemGoal(this));
        }
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, ATTACK_INTERVAL_TICKS, 10.0F));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new CommandPositionGoal(this, 1.15D));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        UUID owner = getOwnerId();
        if (owner != null) tag.putUUID("Owner", owner);
        tag.putBoolean("wandering", isWandering());
        tag.putBoolean("staying", isStaying());
        tag.putBoolean("Pumpkin", hasPumpkin());
        tag.putInt("commandTick", commandTick);
        tag.putInt("SnowballPotency", snowballPotency);
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
        setPumpkin(!tag.contains("Pumpkin") || tag.getBoolean("Pumpkin"));
        setConfigurableAttributes();
        setHealth(Math.min(getHealth(), getMaxHealth()));
        commandTick = tag.contains("commandTick") ? tag.getInt("commandTick") : Integer.MAX_VALUE;
        snowballPotency = tag.contains("SnowballPotency") ? Math.max(0, tag.getInt("SnowballPotency")) : 0;
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

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader reader) {
        return reader.getBlockState(pos).is(Blocks.SNOW) ? 10.0F : super.getWalkTargetValue(pos, reader);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.7F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SNOW_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SNOW_GOLEM_DEATH;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            return;
        }
        if (isAlive()) {
            tickVanillaMelt();
        }
        if (!isAlive() || !ForgeEventFactory.getMobGriefingEvent(level(), this)) {
            return;
        }
        BlockState snow = Blocks.SNOW.defaultBlockState();
        for (int i = 0; i < 4; i++) {
            BlockPos pos = new BlockPos(Mth.floor(getX() + (i % 2 * 2 - 1) * 0.25D),
                    Mth.floor(getY()), Mth.floor(getZ() + (i / 2 % 2 * 2 - 1) * 0.25D));
            if (level().isEmptyBlock(pos) && snow.canSurvive(level(), pos)) {
                level().setBlockAndUpdate(pos, snow);
                level().gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(this, snow));
            }
        }
    }

    private void tickVanillaMelt() {
        if (ownerWearsFrostRobes()) {
            return;
        }
        if (isInWaterRainOrBubble()) {
            hurt(damageSources().drown(), 1.0F);
        } else if (level().getBiome(blockPosition()).value().getBaseTemperature() > 1.0F) {
            hurt(damageSources().onFire(), 1.0F);
        }
    }

    private boolean ownerWearsFrostRobes() {
        Player owner = OwnerChain.playerOwner(this);
        return owner != null && CuriosFinder.hasFrostRobes(owner);
    }

    public boolean hasPumpkin() {
        return getFlag(PUMPKIN_FLAG);
    }

    public void setPumpkin(boolean worn) {
        setFlag(PUMPKIN_FLAG, worn);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(Items.CARVED_PUMPKIN) && !hasPumpkin() && canAdjustPumpkin(player)) {
            if (!level().isClientSide) {
                setPumpkin(true);
                if (!player.getAbilities().instabuild) held.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    private boolean canAdjustPumpkin(Player player) {
        return player == null || getTrueOwner() == null || getMasterOwner() == player;
    }

    @Override
    public boolean readyForShearing() {
        return isAlive() && hasPumpkin();
    }

    @Override
    public boolean isShearable(ItemStack item, Level level, BlockPos pos) {
        return readyForShearing();
    }

    @Override
    public List<ItemStack> onSheared(Player player, ItemStack item, Level level, BlockPos pos, int fortune) {
        if (!readyForShearing() || !canAdjustPumpkin(player)) return List.of();
        level.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR,
                player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        gameEvent(GameEvent.SHEAR, player);
        if (level.isClientSide) return List.of();
        setPumpkin(false);
        return List.of(new ItemStack(Items.CARVED_PUMPKIN));
    }

    @Override
    public void shear(SoundSource category) {
        if (!readyForShearing()) return;
        level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, category, 1.0F, 1.0F);
        if (!level().isClientSide) {
            setPumpkin(false);
            spawnAtLocation(new ItemStack(Items.CARVED_PUMPKIN), 1.7F);
        }
    }

    public void updateSummonRoster() {
        if (level() instanceof ServerLevel serverLevel) {
            SnowServantRoster roster = SnowServantRoster.get(serverLevel);
            if (isAlive() && !isRemoved()) roster.track(getUUID(), getOwnerId());
            else roster.forget(getUUID());
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (level() instanceof ServerLevel serverLevel) {
            if (reason.shouldDestroy()) SnowServantRoster.get(serverLevel).forget(getUUID());
            else if (!isRemoved()) updateSummonRoster();
        }
        super.remove(reason);
    }

    @Override
    public void tick() {
        super.tick();
        updateSummonRoster();
        if (!level().isClientSide && supportCooldown > 0) supportCooldown--;
        updateCommand();
        updateGuardPosition();
        if (isStaying() && getTarget() == null) getNavigation().stop();
        ServantTeleportHelper.tick(this);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceModifier) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) return;
        throwSnowballAt(target);
    }

    private void throwSupportSnowball(MutantSnowGolemServant target) {
        supportTarget = target;
        throwSnowballAt(target);
    }

    private void throwSnowballAt(LivingEntity target) {
        Snowball snowball = new Snowball(level(), this);
        double dx = target.getX() - getX();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        LivingEntity master = getMasterOwner();
        float spread = (float) (master != null && CuriosFinder.hasFrostRobes(master)
                ? SnowGolemServantConfig.SNOWBALL_SPREAD_FROST_SET.get()
                : SnowGolemServantConfig.SNOWBALL_SPREAD.get()).doubleValue();
        snowball.shoot(dx, target.getEyeY() - (double) 1.1F - snowball.getY() + horizontal * (double) 0.2F,
                dz, 1.6F, spread);
        level().addFreshEntity(snowball);
        playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
    }

    public void applySnowballEffect(LivingEntity target) {
        if (target == null || !target.isAlive() || isAlliedTo(target)) return;
        SnowballVolley.applyEffect(getMasterOwner(), snowballPotency, target);
    }

    public void setSnowballPotency(int potency) {
        this.snowballPotency = Math.max(0, potency);
    }

    public int getSnowballPotency() {
        return snowballPotency;
    }

    private void updateCommand() {
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

    private void updateGuardPosition() {
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

    private static final class HealMutantSnowGolemGoal extends Goal {

        private static final int SEARCH_INTERVAL = 10;

        private static final int CONTEST_CHECK_INTERVAL = 10;

        private final SnowGolemServant servant;
        private MutantSnowGolemServant target;
        private int throwCooldown;
        private int searchCooldown;

        private int contestCacheTicks;

        private boolean contested;
        HealMutantSnowGolemGoal(SnowGolemServant servant) { this.servant = servant; setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK)); }
        @Override public boolean canUse() {
            if (servant.supportCooldown > 0) return false;
            if (usable()) return true;
            if (searchCooldown > 0) { --searchCooldown; return false; }
            searchCooldown = SEARCH_INTERVAL;
            AABB box = servant.getBoundingBox().inflate(8.0D);
            target = servant.level().getEntitiesOfClass(MutantSnowGolemServant.class, box,
                    candidate -> candidate.isAlive() && candidate.getHealth() < candidate.getMaxHealth()
                            && ServantFaction.isSameFaction(servant, candidate)
                            && countSnowServantsTargeting(candidate) < 2).stream()
                    .min((a, b) -> Float.compare(a.getHealth() / a.getMaxHealth(), b.getHealth() / b.getMaxHealth())).orElse(null);
            contestCacheTicks = 0;
            return target != null;
        }
        @Override public boolean canContinueToUse() { return usable(); }

        private boolean usable() {
            return isHealCandidate(target) && !isContested(target);
        }
        private boolean isHealCandidate(MutantSnowGolemServant mutant) {
            return mutant != null && mutant.isAlive() && mutant.getHealth() < mutant.getMaxHealth()
                    && ServantFaction.isSameFaction(servant, mutant)
                    && servant.distanceToSqr(mutant) <= 64.0D;
        }
        private boolean isContested(MutantSnowGolemServant mutant) {
            if (contestCacheTicks <= 0) {
                contested = countSnowServantsTargeting(mutant) >= 2;
                contestCacheTicks = CONTEST_CHECK_INTERVAL;
            } else {
                --contestCacheTicks;
            }
            return contested;
        }
        @Override public void start() { servant.supportTarget = target; }
        @Override public boolean requiresUpdateEveryTick() { return true; }
        @Override public void tick() {
            if (throwCooldown > 0) throwCooldown--;
            boolean canHeal = servant.distanceToSqr(target) <= SUPPORT_RANGE_SQR
                    && servant.getSensing().hasLineOfSight(target);
            if (canHeal) servant.getNavigation().stop();
            else servant.getNavigation().moveTo(target, 1.0D);
            servant.getLookControl().setLookAt(target, 10.0F, servant.getMaxHeadXRot());
            if (canHeal && throwCooldown <= 0) {
                servant.throwSupportSnowball(target);
                throwCooldown = ATTACK_INTERVAL_TICKS;
                servant.supportCooldown = 60;
            }
        }
        @Override public void stop() { servant.getNavigation().stop(); target = null; throwCooldown = 0; servant.supportTarget = null; }
        private int countSnowServantsTargeting(MutantSnowGolemServant mutant) {
            return servant.level().getEntitiesOfClass(SnowGolemServant.class, mutant.getBoundingBox().inflate(8.0D),
                    other -> other != servant && (other.supportTarget == mutant || other.getTarget() == mutant)
                            && ServantFaction.isSameFaction(servant, other)).size();
        }
    }
}
