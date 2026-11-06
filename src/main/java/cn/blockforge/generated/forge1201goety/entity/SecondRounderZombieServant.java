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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ForgeRingArmorNormalizer;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.util.ZombieServantConversion;

public class SecondRounderZombieServant extends AbstractJerotesServant {

    public static final float GIANT_SCALE = 1.75F;

    private static final EntityDataAccessor<Boolean> DATA_GIANT =
            SynchedEntityData.defineId(SecondRounderZombieServant.class, EntityDataSerializers.BOOLEAN);

    public SecondRounderZombieServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, -1.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/second_rounder_zombie.png";
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, difficulty -> difficulty == Difficulty.HARD));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult give = giveEquipmentFromOwner(player, hand, false);
        return give != null ? give : super.mobInteract(player, hand);
    }

    @Override
    public boolean isSunSensitive() {
        return true;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity killed) {
        boolean result = super.killedEntity(level, killed);
        if (isUpgraded() && getTrueOwner() != null && level.random.nextFloat() <= 0.5F) {
            ZombieServantConversion.convert(level, killed, getTrueOwner());
        }
        return result;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_GIANT, false);
    }

    public boolean isGiant() {
        return entityData.get(DATA_GIANT);
    }

    public void setGiant(boolean giant) {
        entityData.set(DATA_GIANT, giant);
        if (getAttribute(Attributes.MAX_HEALTH) != null) {
            getAttribute(Attributes.MAX_HEALTH)
                    .setBaseValue(giant ? JerotesVillageServantConfig.SECOND_ROUNDER_ZOMBIE.giantHealth()
                            : JerotesVillageServantConfig.SECOND_ROUNDER_ZOMBIE.MAX_HEALTH.get());
        }
        if (getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            getAttribute(Attributes.ATTACK_DAMAGE)
                    .setBaseValue(giant ? JerotesVillageServantConfig.SECOND_ROUNDER_ZOMBIE.giantAttack()
                            : JerotesVillageServantConfig.SECOND_ROUNDER_ZOMBIE.ATTACK_DAMAGE.get());
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
        return JerotesVillageServantConfig.SECOND_ROUNDER_ZOMBIE;
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
        if (spawnType != null && spawnType != MobSpawnType.CONVERSION
                && level.getRandom().nextFloat() < 0.05F) {
            setGiant(true);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        float chance = 0.3F;
        equipIfRolled(random, chance, EquipmentSlot.MAINHAND, "wilderness_stick", Items.IRON_SWORD);
        if (canSpawnArmor()) {
            populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
        }
        if (canSpawnArmor() && ForgeRingArmorNormalizer.hasNormalizableArmor(this)) {
            return;
        }
        equipIfRolled(random, chance, EquipmentSlot.HEAD, "wilderness_giant_helmet", Items.IRON_HELMET);
        equipIfRolled(random, chance, EquipmentSlot.CHEST, "wilderness_giant_chestplate", Items.LEATHER_CHESTPLATE);
        equipIfRolled(random, chance, EquipmentSlot.LEGS, "wilderness_giant_leggings", Items.LEATHER_LEGGINGS);
        equipIfRolled(random, chance, EquipmentSlot.FEET, "wilderness_giant_boots", Items.LEATHER_BOOTS);
    }

    private void equipIfRolled(RandomSource random, float chance, EquipmentSlot slot,
            String jerotesPath, net.minecraft.world.item.Item fallback) {
        if (random.nextFloat() > chance) {
            return;
        }
        ItemStack stack = isGiant() ? jerotesItem(jerotesPath) : new ItemStack(fallback);
        if (stack.isEmpty()) {
            stack = new ItemStack(fallback);
        }
        setItemSlot(slot, stack);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Giant", isGiant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Giant")) {
            setGiant(tag.getBoolean("Giant"));
        }
    }
}
