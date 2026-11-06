package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.entity.HorseServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.util.MountReachBonus;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.renderer.SkeletonHorseServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyMountReachMixin;

public class SkeletonHorseServant extends Summoned implements PlayerRideable {

    private static final EntityDataAccessor<ItemStack> DATA_HORSE_ARMOR =
            SynchedEntityData.defineId(SkeletonHorseServant.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Boolean> DATA_NETHERITE_ARMOR =
            SynchedEntityData.defineId(SkeletonHorseServant.class, EntityDataSerializers.BOOLEAN);

    public static final double NETHERITE_HORSE_ARMOR_PROTECTION = 15.0D;

    public static final double NETHERITE_HORSE_ARMOR_TOUGHNESS = 6.0D;

    private static final UUID ARMOR_MODIFIER_ID = UUID.fromString("8a2e3b41-6c1d-4f57-9b0a-2f7d5e6c1a33");

    private static final UUID TOUGHNESS_MODIFIER_ID = UUID.fromString("c9f1d7a4-3e52-4b08-9d6c-5a8f2e1b7c40");

    private static final UUID KNOCKBACK_MODIFIER_ID = UUID.fromString("5b7c9d1e-2a34-4f68-8c0b-1d9e3f5a7b62");

    private static final float BONE_HEAL = 2.0F;

    public SkeletonHorseServant(EntityType<? extends SkeletonHorseServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RiderDirectsMountGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    public ItemStack getHorseArmor() {
        return this.entityData.get(DATA_HORSE_ARMOR);
    }

    public void setHorseArmor(ItemStack stack) {
        this.entityData.set(DATA_HORSE_ARMOR, stack);
        if (!this.level().isClientSide) {
            this.updateHorseArmorModifier();
        }
    }

    private void clearHorseArmor() {
        this.entityData.set(DATA_HORSE_ARMOR, ItemStack.EMPTY);
    }

    public boolean hasNetheriteArmor() {
        return this.entityData.get(DATA_NETHERITE_ARMOR);
    }

    public void setNetheriteArmor(boolean value) {
        this.entityData.set(DATA_NETHERITE_ARMOR, value);
        if (!this.level().isClientSide) {
            this.updateHorseArmorModifier();
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HORSE_ARMOR, ItemStack.EMPTY);
        this.entityData.define(DATA_NETHERITE_ARMOR, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ItemStack armor = this.getHorseArmor();
        if (!armor.isEmpty()) {
            tag.put("HorseArmor", armor.save(new CompoundTag()));
        }
        if (this.hasNetheriteArmor()) {
            tag.putBoolean("NetheriteHorseArmor", true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("HorseArmor", 10)) {
            this.setHorseArmor(ItemStack.of(tag.getCompound("HorseArmor")));
        }
        if (tag.getBoolean("NetheriteHorseArmor")) {
            this.setNetheriteArmor(true);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof HorseArmorItem) {
            if (this.hasNetheriteArmor()) {
                return super.mobInteract(player, hand);
            }
            if (!this.level().isClientSide) {
                ItemStack old = this.getHorseArmor();
                this.setHorseArmor(stack.copyWithCount(1));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                if (!old.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(old);
                }
                this.playSound(SoundEvents.HORSE_ARMOR, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (stack.is(Items.BONE) && player == this.getTrueOwner() && this.isAlive()
                && ServantFeedHeal.feed(this, player, stack, BONE_HEAL)) {
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (stack.isEmpty() && player.isSecondaryUseActive()) {
            if (!this.getHorseArmor().isEmpty()) {
                if (!this.level().isClientSide) {
                    ItemStack old = this.getHorseArmor();
                    this.clearHorseArmor();
                    this.updateHorseArmorModifier();
                    player.getInventory().placeItemBackInInventory(old);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        if (stack.isEmpty() && !player.isSecondaryUseActive()
                && this.getFirstPassenger() == null && this.getTrueOwner() == player) {
            if (!this.level().isClientSide && player.startRiding(this)) {
                return InteractionResult.CONSUME;
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof Player player ? player : null;
    }

    @Override
    public void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (!this.level().isClientSide) {
            MountReachBonus.applyToRider(passenger, MountReachBonus.bonusFor(this));
        }
    }

    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level().isClientSide) {
            MountReachBonus.applyToRider(passenger, 0.0D);
        }
    }

    @Override
    public Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float forward = player.zza;
        if (forward <= 0.0F) {
            forward *= 0.25F;
        }
        return new Vec3(player.xxa * 0.5F, 0.0D, forward);
    }

    @Override
    public float getRiddenSpeed(Player player) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    public void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        this.setYRot(player.getYRot());
        this.yRotO = this.getYRot();
        this.yHeadRot = this.getYRot();
        this.yBodyRot = this.getYRot();
    }

    private void updateHorseArmorModifier() {
        AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
        AttributeInstance toughness = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        AttributeInstance knockback = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (armor == null) {
            return;
        }
        armor.removeModifier(ARMOR_MODIFIER_ID);
        if (toughness != null) {
            toughness.removeModifier(TOUGHNESS_MODIFIER_ID);
        }
        if (knockback != null) {
            knockback.removeModifier(KNOCKBACK_MODIFIER_ID);
        }
        boolean netherite = this.hasNetheriteArmor();
        if (netherite) {
            armor.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, "Netherite horse armor bonus",
                    NETHERITE_HORSE_ARMOR_PROTECTION, AttributeModifier.Operation.ADDITION));
            if (toughness != null) {
                toughness.addTransientModifier(new AttributeModifier(TOUGHNESS_MODIFIER_ID,
                        "Netherite horse armor toughness", NETHERITE_HORSE_ARMOR_TOUGHNESS,
                        AttributeModifier.Operation.ADDITION));
            }
        } else {
            ItemStack stack = this.getHorseArmor();
            if (stack.getItem() instanceof HorseArmorItem armorItem && armorItem.getProtection() > 0) {
                armor.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, "Horse armor bonus",
                        armorItem.getProtection(), AttributeModifier.Operation.ADDITION));
            }
        }
        double knockbackResistance = netherite ? this.netheriteHorseArmorKnockbackResistance() : 0.0D;
        if (knockback != null && knockbackResistance > 0.0D) {
            knockback.addTransientModifier(new AttributeModifier(KNOCKBACK_MODIFIER_ID,
                    "Netherite horse armor knockback resistance", knockbackResistance,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    public double netheriteHorseArmorKnockbackResistance() {
        return 0.0D;
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        ItemStack armor = this.getHorseArmor();
        if (!armor.isEmpty()) {
            this.spawnAtLocation(armor);
            this.clearHorseArmor();
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.SKELETON_HORSE_AMBIENT_WATER
                : SoundEvents.SKELETON_HORSE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HORSE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_HORSE_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        SoundType soundType = state.getSoundType(this.level(), pos, this);
        this.playSound(SoundEvents.HORSE_STEP, soundType.getVolume() * 0.15F, soundType.getPitch());
    }

    private static final class RiderDirectsMountGoal extends Goal {

        private static final double KEEP_MIN = 8.0D;

        private static final double KEEP_MAX = 14.0D;

        private static final double SPEAR_IMPACT_RANGE = 2.0D;

        private static final double SPEAR_SPEED = 1.4D;

        private static final int SPEAR_AWAY_MIN = 8;
        private static final int SPEAR_AWAY_MAX = 9;

        private static final int SPEAR_AWAY_VERTICAL = 7;

        private static final int SPEAR_AWAY_TICKS = 60;

        private static final double SPEAR_AWAY_ARRIVE_SQR = 4.0D;
        private final SkeletonHorseServant horse;

        private boolean spearAway;
        private int spearAwayTicks;

        private int ticksUntilNextPathRecalculation;
        @Nullable
        private Vec3 spearAwayPos;

        RiderDirectsMountGoal(SkeletonHorseServant horse) {
            this.horse = horse;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Nullable
        private Mob rider() {
            return this.horse.getFirstPassenger() instanceof Mob mob ? mob : null;
        }

        @Override
        public boolean canUse() {
            Mob rider = this.rider();
            return rider != null && rider.getTarget() != null && rider.getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void tick() {
            Mob rider = this.rider();
            if (rider == null) {
                return;
            }
            LivingEntity target = rider.getTarget();
            if (target == null) {
                return;
            }
            double distance = this.horse.distanceTo(target);
            PathNavigation navigation = this.horse.getNavigation();
            ItemStack mainHand = rider.getMainHandItem();
            boolean archer = mainHand.is(Items.BOW);
            if (JerotesItems.isSpear(mainHand) || JerotesItems.isPike(mainHand)
                    || FellSpearItem.isSpear(mainHand)) {
                this.tickSpearPass(target, distance, navigation);
            } else {
                this.resetSpearAway();
                boolean recalcPath = --this.ticksUntilNextPathRecalculation <= 0;
                if (recalcPath) {
                    this.ticksUntilNextPathRecalculation = 4 + this.horse.getRandom().nextInt(7);
                }
                if (archer && distance < KEEP_MIN) {
                    Vec3 away = this.horse.position().subtract(target.position());
                    away = new Vec3(away.x, 0.0D, away.z);
                    if (away.lengthSqr() < 1.0E-6D) {
                        away = new Vec3(1.0D, 0.0D, 0.0D);
                    }
                    away = away.normalize().scale(KEEP_MIN);
                    if (recalcPath) {
                        navigation.moveTo(this.horse.getX() + away.x, this.horse.getY(),
                                this.horse.getZ() + away.z, 1.2D);
                    }
                } else if (!archer || distance > KEEP_MAX) {
                    if (recalcPath) {
                        navigation.moveTo(target, 1.2D);
                    }
                } else {
                    navigation.stop();
                }
            }
            Vec3 facing = this.spearAway && this.spearAwayPos != null
                    ? this.spearAwayPos : target.position();
            float yaw = (float) (Mth.atan2(facing.z - this.horse.getZ(),
                    facing.x - this.horse.getX()) * (180.0D / Math.PI)) - 90.0F;
            this.horse.setYRot(yaw);
            this.horse.yRotO = yaw;
            this.horse.yBodyRot = yaw;
            this.horse.setYHeadRot(yaw);
        }

        private void tickSpearPass(LivingEntity target, double distance, PathNavigation navigation) {
            if (this.spearAway) {
                this.spearAwayTicks++;
                Vec3 point = this.spearAwayPos;
                if (point == null || this.spearAwayTicks > SPEAR_AWAY_TICKS
                        || this.horse.distanceToSqr(point.x, point.y, point.z) < SPEAR_AWAY_ARRIVE_SQR) {
                    this.resetSpearAway();
                    navigation.moveTo(target, SPEAR_SPEED);
                    return;
                }
                navigation.moveTo(point.x, point.y, point.z, SPEAR_SPEED);
                return;
            }
            navigation.moveTo(target, SPEAR_SPEED);
            if (distance <= SPEAR_IMPACT_RANGE) {
                this.spearAway = true;
                this.spearAwayTicks = 0;
                this.spearAwayPos = this.awayPoint(target);
                Vec3 point = this.spearAwayPos;
                if (point != null) {
                    navigation.moveTo(point.x, point.y, point.z, SPEAR_SPEED);
                }
            }
        }

        private void resetSpearAway() {
            this.spearAway = false;
            this.spearAwayTicks = 0;
            this.spearAwayPos = null;
        }

        @Nullable
        private Vec3 awayPoint(LivingEntity target) {
            int horizontal = SPEAR_AWAY_MIN
                    + this.horse.getRandom().nextInt(SPEAR_AWAY_MAX - SPEAR_AWAY_MIN + 1);
            Vec3 pos = LandRandomPos.getPosAway(this.horse, horizontal, SPEAR_AWAY_VERTICAL,
                    target.position());
            if (pos != null) {
                return pos;
            }
            Vec3 away = this.horse.position().subtract(target.position());
            Vec3 flat = new Vec3(away.x, 0.0D, away.z);
            if (flat.lengthSqr() < 1.0E-4D) {
                return null;
            }
            return this.horse.position().add(flat.normalize().scale(horizontal));
        }

        @Override
        public void start() {
            this.resetSpearAway();
        }

        @Override
        public void stop() {
            this.horse.getNavigation().stop();
            this.resetSpearAway();
        }
    }
}
