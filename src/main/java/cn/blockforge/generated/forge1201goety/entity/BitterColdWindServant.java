package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.event.ImmutableRingEvents;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;

public class BitterColdWindServant extends AbstractJerotesServant {

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID =
            SynchedEntityData.defineId(BitterColdWindServant.class, EntityDataSerializers.BYTE);

    public BitterColdWindServant(EntityType<? extends BitterColdWindServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        setMaxUpStep(2.1F);
        setCanPickUpLoot(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.MAX_HEALTH, 26.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 3.6D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/bitter_cold_wind.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.BITTER_COLD_WIND;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        if (RingServantGear.ringScopeApplies(this)) {
            ItemStack blueIce = new ItemStack(Items.BLUE_ICE);
            RingServantGear.markRingGear(blueIce);
            setItemSlot(EquipmentSlot.MAINHAND, blueIce);
            setDropChance(EquipmentSlot.MAINHAND, 0.0F);
            return;
        }
        setItemSlot(EquipmentSlot.MAINHAND, createSpawnWeapon(random.nextFloat()));
    }

    @Override
    public boolean canSpawnArmor() {
        return false;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    @Override
    public boolean onClimbable() {
        return isClimbing();
    }

    public boolean isClimbing() {
        return (entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean climbing) {
        byte flags = entityData.get(DATA_FLAGS_ID);
        entityData.set(DATA_FLAGS_ID, climbing ? (byte) (flags | 1) : (byte) (flags & 0xFFFFFFFE));
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.8F;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        var motion = getDeltaMovement();
        if (!onGround() && motion.y < 0.0D) {
            setDeltaMovement(motion.multiply(1.0D, 0.3D, 1.0D));
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_DROWNING)
                || source.is(DamageTypeTags.IS_FREEZING)
                || source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypes.DRY_OUT)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(DamageTypes.ON_FIRE)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return super.hurt(source, amount * 4.0F);
        }
        if (source.is(DamageTypes.IN_WALL)) {
            return super.hurt(source, amount * 2.0F);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean isFreezing() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        if (stack.canEquip(EquipmentSlot.HEAD, this)
                || stack.canEquip(EquipmentSlot.CHEST, this)
                || stack.canEquip(EquipmentSlot.LEGS, this)
                || stack.canEquip(EquipmentSlot.FEET, this)) {
            return false;
        }
        return super.canHoldItem(stack);
    }

    @Override
    public boolean canReplaceCurrentItem(ItemStack newStack, ItemStack currentStack) {
        if (currentStack.is(Items.BLUE_ICE)) {
            return false;
        }
        if (newStack.is(Items.BLUE_ICE)) {
            return true;
        }
        return super.canReplaceCurrentItem(newStack, currentStack);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != net.minecraftforge.common.ForgeMod.WATER_TYPE.get()
                && super.canDrownInFluidType(type);
    }

    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(strength * 3.0D, x * 3.0D, z * 3.0D);
    }

    @Override
    public void push(double x, double y, double z) {
        super.push(x * 3.0D, y * 3.0D, z * 3.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level() instanceof ServerLevel server) {
            server.broadcastEntityEvent(this, (byte) 4);
        }
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            if (!isSilent()) {
                playSound(ambientSound(), 1.0F, 1.0F);
            }
            if (getMainHandItem().is(Items.BLUE_ICE) && target.getTicksFrozen() < 220) {
                target.setTicksFrozen(220);
            }
        }
        return hit;
    }

    private static ItemStack createSpawnWeapon(float roll) {
        if (roll > 0.95F) {
            return new ItemStack(Items.IRON_SWORD);
        }
        if (roll > 0.90F) {
            return new ItemStack(Items.IRON_AXE);
        }
        if (roll > 0.85F) {
            return new ItemStack(Items.WOODEN_SWORD);
        }
        if (roll > 0.75F) {
            return new ItemStack(Items.WOODEN_AXE);
        }
        if (roll > 0.70F) {
            return jerotesItemOr("jerotes", "simple_javelin", Items.BLUE_ICE);
        }
        if (roll > 0.68F) {
            return jerotesItemOr("jerotes", "iron_javelin", Items.BLUE_ICE);
        }
        if (roll > 0.55F) {
            return new ItemStack(Items.BLUE_ICE);
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack jerotesItemOr(String namespace, String path, net.minecraft.world.item.Item fallback) {
        net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath(namespace, path));
        return new ItemStack(item != null ? item : fallback);
    }

    private SoundEvent ambientSound() {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", "bitter_cold_wind_ambient"));
        return sound != null ? sound : SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ambientSound();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return ambientSound();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ambientSound();
    }

    public void playSummonParticles() {
        if (level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SNOWFLAKE,
                    getRandomX(0.5D), getRandomY(), getRandomZ(0.5D),
                    20, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
