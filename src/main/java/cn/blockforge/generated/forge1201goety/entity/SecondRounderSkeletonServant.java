package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ForgeRingArmorNormalizer;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;

public class SecondRounderSkeletonServant extends AbstractJerotesServant implements RangedAttackMob {

    public static final float GIANT_SCALE = 1.75F;

    private static final EntityDataAccessor<Boolean> DATA_SKIN_A =
            SynchedEntityData.defineId(SecondRounderSkeletonServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SKIN_B =
            SynchedEntityData.defineId(SecondRounderSkeletonServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SKIN_C =
            SynchedEntityData.defineId(SecondRounderSkeletonServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_GIANT =
            SynchedEntityData.defineId(SecondRounderSkeletonServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_BOW_LEVEL =
            SynchedEntityData.defineId(SecondRounderSkeletonServant.class, EntityDataSerializers.INT);

    public SecondRounderSkeletonServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean isFreezing() {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/second_rounder_skeleton_0.png";
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new BowGoal(this, 0.5D, 20, 15.0F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult give = giveEquipmentFromOwner(player, hand, true);
        return give != null ? give : super.mobInteract(player, hand);
    }

    @Override
    public boolean isSunSensitive() {
        return true;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_SKIN_A, false);
        entityData.define(DATA_SKIN_B, false);
        entityData.define(DATA_SKIN_C, false);
        entityData.define(DATA_GIANT, false);
        entityData.define(DATA_BOW_LEVEL, 15);
    }

    public boolean isSkinA() {
        return entityData.get(DATA_SKIN_A);
    }

    public boolean isSkinB() {
        return entityData.get(DATA_SKIN_B);
    }

    public boolean isSkinC() {
        return entityData.get(DATA_SKIN_C);
    }

    public void setSkinA(boolean value) {
        entityData.set(DATA_SKIN_A, value);
    }

    public void setSkinB(boolean value) {
        entityData.set(DATA_SKIN_B, value);
    }

    public void setSkinC(boolean value) {
        entityData.set(DATA_SKIN_C, value);
    }

    public int getBowLevel() {
        return entityData.get(DATA_BOW_LEVEL);
    }

    public void setBowLevel(int level) {
        entityData.set(DATA_BOW_LEVEL, level);
    }

    public boolean isGiant() {
        return entityData.get(DATA_GIANT);
    }

    public void setGiant(boolean giant) {
        entityData.set(DATA_GIANT, giant);
        if (getAttribute(Attributes.MAX_HEALTH) != null) {
            getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(giant ? JerotesVillageServantConfig.SECOND_ROUNDER_SKELETON.giantHealth()
                            : JerotesVillageServantConfig.SECOND_ROUNDER_SKELETON.MAX_HEALTH.get());
        }
        if (getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(giant ? JerotesVillageServantConfig.SECOND_ROUNDER_SKELETON.giantAttack()
                            : JerotesVillageServantConfig.SECOND_ROUNDER_SKELETON.ATTACK_DAMAGE.get());
        }
        setMaxUpStep(giant ? 1.1F : 0.6F);
        if (giant) {
            setHealth(getMaxHealth());
        } else {
            setHealth(Math.min(getHealth(), getMaxHealth()));
        }
        refreshDimensions();
    }

    @Override
    public void setConfigurableAttributes() {
        super.setConfigurableAttributes();
        if (isGiant()) {
            setGiant(true);
        }
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SECOND_ROUNDER_SKELETON;
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return isGiant() ? 3.05F : 1.9F;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return isGiant() ? EntityDimensions.scalable(1.05F, 3.4F) : super.getDimensions(pose);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
        if (spawnType != null && spawnType != MobSpawnType.CONVERSION) {
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.5F) {
                setSkinA(true);
            }
            if (random.nextFloat() < 0.5F) {
                setSkinB(true);
            }
            if (random.nextFloat() < 0.5F) {
                setSkinC(true);
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        float weaponRoll = random.nextFloat();
        ItemStack weapon;
        if (weaponRoll <= 0.6F) {
            weapon = new ItemStack(Items.BOW);
        } else if (weaponRoll <= 0.85F) {
            weapon = new ItemStack(Items.IRON_SWORD);
        } else {
            weapon = new ItemStack(Items.IRON_AXE);
        }
        setItemSlot(EquipmentSlot.MAINHAND, weapon);
        setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        if (canSpawnArmor()) {
            populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
        }
        if (!canSpawnArmor() || !ForgeRingArmorNormalizer.hasNormalizableArmor(this)) {
            float chance = 0.3F;
            if (random.nextFloat() <= chance) {
                setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            }
            if (random.nextFloat() <= chance) {
                setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
            }
            if (random.nextFloat() <= chance) {
                setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
            }
            if (random.nextFloat() <= chance) {
                setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsSkinA", isSkinA());
        tag.putBoolean("IsSkinB", isSkinB());
        tag.putBoolean("IsSkinC", isSkinC());
        tag.putBoolean("IsGiant", isGiant());
        tag.putInt("BowLevel", getBowLevel());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("IsSkinA")) {
            setSkinA(tag.getBoolean("IsSkinA"));
        }
        if (tag.contains("IsSkinB")) {
            setSkinB(tag.getBoolean("IsSkinB"));
        }
        if (tag.contains("IsSkinC")) {
            setSkinC(tag.getBoolean("IsSkinC"));
        }
        if (tag.contains("IsGiant")) {
            setGiant(tag.getBoolean("IsGiant"));
        }
        setBowLevel(tag.contains("BowLevel") ? tag.getInt("BowLevel") : 15);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        ItemStack bowStack = getMainHandItem();
        if (!(bowStack.getItem() instanceof BowItem) && getOffhandItem().getItem() instanceof BowItem) {
            bowStack = getOffhandItem();
        }
        int bowLevel = getBowLevel();
        int low = Math.min(bowLevel, 20);
        int high = Math.max(bowLevel, 20);
        int drawTicks = low + random.nextInt(high - low + 1);
        float power = BowItem.getPowerForTime(drawTicks);
        AbstractArrow arrow = ProjectileUtil.getMobArrow(this, new ItemStack(Items.ARROW), power);
        arrow.setBaseDamage(arrow.getBaseDamage() + getSummonPotency());
        arrow.setPos(getX(), getEyeY() - 0.1D, getZ());
        double dx = target.getX() - getX();
        double dy = target.getY(0.35D) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float inaccuracy = bowLevel >= 20 ? 0.0F : 1.0F;
        int speedOfPowerTime = Math.max(10, Math.min(drawTicks, 15)) - 10;
        arrow.shoot(dx, dy + horizontal * (0.2D - 0.02D * speedOfPowerTime), dz, power * 3.0F, inaccuracy);
        if (power >= 1.0F) {
            arrow.setCritArrow(true);
        }
        playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + power * 0.5F);
        level().addFreshEntity(arrow);
    }

    private boolean isHoldingBow() {
        ItemStack main = getMainHandItem();
        ItemStack off = getOffhandItem();
        LivingEntity target = getTarget();
        boolean mainMelee = main.getItem() instanceof SwordItem || main.getItem() instanceof AxeItem;
        if (mainMelee && target != null && distanceTo(target) <= 8.0D) {
            return false;
        }
        return main.getItem() instanceof BowItem
                || (off.getItem() instanceof BowItem && (main.isEmpty() || mainMelee));
    }

    private static final class BowGoal extends Goal {
        private final SecondRounderSkeletonServant mob;
        private final double speedModifier;
        private final int attackIntervalMin;
        private final float attackRadiusSqr;
        private int attackTime = -1;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        BowGoal(SecondRounderSkeletonServant mob, double speedModifier, int attackIntervalMin, float radius) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.attackIntervalMin = attackIntervalMin;
            this.attackRadiusSqr = radius * radius;
            setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return mob.getTarget() != null && mob.getTarget().isAlive() && mob.isHoldingBow();
        }

        @Override
        public boolean canContinueToUse() {
            return (canUse() || !mob.getNavigation().isDone()) && mob.isHoldingBow();
        }

        @Override
        public void start() {
            super.start();
            mob.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            mob.setAggressive(false);
            seeTime = 0;
            attackTime = -1;
            mob.stopUsingItem();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = mob.getTarget();
            if (target == null) {
                return;
            }
            double distanceSqr = mob.distanceToSqr(target);
            boolean seen = mob.getSensing().hasLineOfSight(target);
            boolean wasSeen = seeTime > 0;
            if (seen != wasSeen) {
                seeTime = 0;
            }
            seeTime = seen ? seeTime + 1 : seeTime - 1;
            if (distanceSqr <= attackRadiusSqr && seeTime >= 20) {
                mob.getNavigation().stop();
                ++strafingTime;
            } else {
                mob.getNavigation().moveTo(target, speedModifier);
                strafingTime = -1;
            }
            if (strafingTime >= 20) {
                if (mob.getRandom().nextFloat() < 0.3F) {
                    strafingClockwise = !strafingClockwise;
                }
                if (mob.getRandom().nextFloat() < 0.3F) {
                    strafingBackwards = !strafingBackwards;
                }
                strafingTime = 0;
            }
            if (strafingTime > -1) {
                if (distanceSqr > attackRadiusSqr * 0.75F) {
                    strafingBackwards = false;
                } else if (distanceSqr < attackRadiusSqr * 0.25F) {
                    strafingBackwards = true;
                }
                mob.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            } else {
                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (mob.isUsingItem()) {
                if (!seen && seeTime < -60) {
                    mob.stopUsingItem();
                } else if (seen) {
                    int ticks = mob.getTicksUsingItem();
                    if (ticks >= 20) {
                        mob.stopUsingItem();
                        mob.performRangedAttack(target, BowItem.getPowerForTime(ticks));
                        attackTime = attackIntervalMin;
                    }
                }
            }
            if (!(mob.isUsingItem() && mob.getMainHandItem().getItem() instanceof BowItem)
                    && --attackTime <= 0 && seeTime >= -60) {
                mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(mob,
                        item -> item instanceof BowItem));
            }
        }
    }

}
