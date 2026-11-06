package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;

public class SecondRounderSpiderServant extends AbstractJerotesServant {

    private static final EntityDataAccessor<Integer> DATA_COLOR =
            SynchedEntityData.defineId(SecondRounderSpiderServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_PUSH_TICK =
            SynchedEntityData.defineId(SecondRounderSpiderServant.class, EntityDataSerializers.INT);

    private boolean climbing;

    private boolean pendingJockey;

    public SecondRounderSpiderServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.34D)
                .add(Attributes.MAX_HEALTH, 28.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SECOND_ROUNDER_SPIDER;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/second_rounder_spider.png";
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_COLOR, 1);
        entityData.define(DATA_PUSH_TICK, 0);
    }

    public int getColor() {
        return entityData.get(DATA_COLOR);
    }

    public void setColor(int color) {
        entityData.set(DATA_COLOR, color);
    }

    public int getPushTick() {
        return entityData.get(DATA_PUSH_TICK);
    }

    public void setPushTick(int ticks) {
        entityData.set(DATA_PUSH_TICK, ticks);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (getPushTick() > 0) {
                setPushTick(getPushTick() - 1);
            }
            if (pendingJockey) {
                pendingJockey = false;
                if (level() instanceof ServerLevel serverLevel) {
                    spawnSkeletonJockey(serverLevel);
                }
            }
        }
        setClimbing(this.horizontalCollision);
    }

    public boolean isClimbing() {
        return climbing;
    }

    public void setClimbing(boolean value) {
        this.climbing = value;
    }

    @Override
    public boolean onClimbable() {
        return isClimbing();
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
        if (!state.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(state, motionMultiplier);
        }
    }

    @Override
    public void doPush(Entity entity) {
        if (entity != this && !entity.isPassenger() && !isAlliedTo(entity) && getTarget() != null) {
            setPushTick(5);
        } else {
            super.doPush(entity);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        setColor(level.getRandom().nextInt(0x1000000));
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        if (spawnType != null && spawnType != MobSpawnType.CONVERSION
                && level.getRandom().nextInt(40) == 1) {
            pendingJockey = true;
        }
        if (data == null && level.getDifficulty() == Difficulty.HARD
                && level.getRandom().nextFloat() < 0.1F * difficulty.getSpecialMultiplier()) {
            int roll = level.getRandom().nextInt(5);
            MobEffect effect = roll <= 1 ? MobEffects.MOVEMENT_SPEED
                    : roll <= 2 ? MobEffects.DAMAGE_BOOST
                    : roll <= 3 ? MobEffects.REGENERATION
                    : roll <= 4 ? MobEffects.INVISIBILITY : null;
            if (effect != null) {
                addEffect(new MobEffectInstance(effect, -1));
            }
        }
        return data;
    }

    private void spawnSkeletonJockey(ServerLevel serverLevel) {
        SecondRounderSkeletonServant rider =
                ModEntityTypes.SECOND_ROUNDER_SKELETON_SERVANT.get().create(serverLevel);
        if (rider == null) {
            return;
        }
        rider.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
        rider.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPosition()),
                MobSpawnType.JOCKEY, null, null);
        if (getOwnerId() != null) {
            rider.setOwnerId(getOwnerId());
        }
        LivingEntity trueOwner = getTrueOwner();
        if (trueOwner != null) {
            rider.setTrueOwner(trueOwner);
        }
        serverLevel.addFreshEntity(rider);
        rider.startRiding(this);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Color", getColor());
        tag.putInt("PushTick", getPushTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setColor(tag.contains("Color") ? tag.getInt("Color") : 1);
        setPushTick(tag.contains("PushTick") ? tag.getInt("PushTick") : 0);
    }
}
