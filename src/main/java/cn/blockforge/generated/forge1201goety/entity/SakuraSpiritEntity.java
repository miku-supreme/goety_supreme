package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.ai.SakuraSpiritMeleeGoal;
import cn.blockforge.generated.goetysupreme.ai.SakuraSpiritSlashGoal;
import cn.blockforge.generated.goetysupreme.ai.SakuraSpiritTeleportGoal;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpirit;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpiritTeleporter;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritEntity;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritGear;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritTeleports;

public class SakuraSpiritEntity extends Monster implements ISakuraSpirit, ISakuraSpiritTeleporter {
    private static final float FALL_DRAG = 0.6F;
    private static final int TELEPORT_COOLDOWN = 30;
    private static final int TELEPORT_TRIES = 12;
    private static final double TELEPORT_RADIUS = 32.0D;

    private int lastMeleeHitTick;
    private int teleportCooldownUntil;

    public SakuraSpiritEntity(EntityType<? extends SakuraSpiritEntity> type, Level level) {
        super(type, level);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0.0F);
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        super.setItemSlot(slot, SakuraSpiritGear.canEquipSlot(slot) ? stack : ItemStack.EMPTY);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        SakuraSpiritGear.enforceLowerArmorRule(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, SakuraSpiritConfig.MAX_HEALTH.get())
                .add(Attributes.ATTACK_DAMAGE, SakuraSpiritConfig.ATTACK_DAMAGE.get())
                .add(Attributes.ARMOR, SakuraSpiritConfig.ARMOR.get())
                .add(Attributes.ARMOR_TOUGHNESS, SakuraSpiritConfig.ARMOR_TOUGHNESS.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, SakuraSpiritConfig.KNOCKBACK_RESISTANCE.get())
                .add(Attributes.FOLLOW_RANGE, SakuraSpiritConfig.FOLLOW_RANGE.get())
                .add(Attributes.MOVEMENT_SPEED, SakuraSpiritConfig.MOVEMENT_SPEED.get());
    }

    public void syncConfiguredAttributes() {
        setBaseAttribute(getAttribute(Attributes.MAX_HEALTH), SakuraSpiritConfig.MAX_HEALTH.get());
        setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE), SakuraSpiritConfig.ATTACK_DAMAGE.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR), SakuraSpiritConfig.ARMOR.get());
        setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS), SakuraSpiritConfig.ARMOR_TOUGHNESS.get());
        setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                SakuraSpiritConfig.KNOCKBACK_RESISTANCE.get());
        setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE), SakuraSpiritConfig.FOLLOW_RANGE.get());
        setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED), SakuraSpiritConfig.MOVEMENT_SPEED.get());
    }

    public static void setBaseAttribute(net.minecraft.world.entity.ai.attributes.AttributeInstance attribute,
            double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SakuraSpiritTeleportGoal(this));
        this.goalSelector.addGoal(1, new SakuraSpiritMeleeGoal(this));
        this.goalSelector.addGoal(1, new SakuraSpiritSlashGoal(this, this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, true,
                SakuraSpiritGear::isHuntable));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, 0, true, true,
                SakuraSpiritGear::isHuntable));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            this.lastMeleeHitTick = this.tickCount;
        }
        return hit;
    }

    @Override
    public int getLastMeleeHitTick() {
        return this.lastMeleeHitTick;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.AMETHYST_BLOCK_HIT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.AMETHYST_BLOCK_BREAK;
    }

    @Override
    public float getVoicePitch() {
        return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.8F;
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        if (!SakuraSpiritConfig.SCAVENGE_GEAR.get()) {
            return;
        }
        Item weapon = SakuraSpiritGear.randomWeapon(random);
        if (weapon != null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(weapon));
        }
        SakuraSpiritGear.equipScavengedArmor(this, random);
    }

    private void enchantAllEquipped(RandomSource random) {
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.HEAD,
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = this.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                SakuraSpiritGear.enchantRandomly(stack, random);
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        this.syncConfiguredAttributes();
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.enchantAllEquipped(level.getRandom());
        return data;
    }

    @Override
    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y() < 0.0D) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x(), motion.y() * FALL_DRAG, motion.z());
        }
        super.aiStep();
        if (this.level().isClientSide && !this.isDeadOrDying()) {
            for (int i = 0; i < 2; i++) {
                float offsetX = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
                float offsetY = this.getEyeHeight()
                        + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F;
                float offsetZ = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
                this.level().addParticle(ParticleTypes.CHERRY_LEAVES,
                        this.xOld + offsetX, this.yOld + offsetY, this.zOld + offsetZ, 0.0D, 0.0D, 0.0D);
            }
        }
        if (!this.level().isClientSide && this.random.nextInt(200) == 0) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_STEP,
                    SoundSource.AMBIENT, 0.4F, 1.0F);
        }
    }

    @Override
    public boolean tryTeleport() {
        if (this.teleportCooldownUntil > this.tickCount) {
            return false;
        }
        RandomSource random = this.getRandom();
        for (int i = 0; i < TELEPORT_TRIES; i++) {
            double x = this.getX() + (random.nextDouble() - 0.5D) * TELEPORT_RADIUS;
            double z = this.getZ() + (random.nextDouble() - 0.5D) * TELEPORT_RADIUS;
            int y = this.blockPosition().getY() + random.nextInt(8) - 4;
            BlockPos pos = BlockPos.containing(x, y, z);
            if (this.level().getBlockState(pos).isAir()
                    && this.level().getBlockState(pos.above()).isAir()
                    && this.level().getBlockState(pos.below()).isSolid()) {
                this.teleportCooldownUntil = this.tickCount + TELEPORT_COOLDOWN;
                this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                        SoundSource.AMBIENT, 0.6F, 1.0F);
                this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                        SoundSource.AMBIENT, 0.6F, 1.0F);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryTeleportAwayFrom(LivingEntity target, double minDistance) {
        if (this.teleportCooldownUntil > this.tickCount) {
            return false;
        }
        BlockPos pos = SakuraSpiritTeleports.ringPosAround(this, target, minDistance,
                minDistance + TELEPORT_RADIUS * 0.5D, this.blockPosition().getY(), 4);
        if (pos == null) {
            return false;
        }
        this.teleportCooldownUntil = this.tickCount + TELEPORT_COOLDOWN;
        this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.AMBIENT, 0.6F, 1.0F);
        this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.AMBIENT, 0.6F, 1.0F);
        return true;
    }

    @Override
    public boolean tryTeleportNearTo(LivingEntity target, double minDistance, double maxDistance) {
        if (this.teleportCooldownUntil > this.tickCount) {
            return false;
        }
        BlockPos pos = SakuraSpiritTeleports.ringPosAround(this, target, minDistance, maxDistance,
                target.blockPosition().getY(), 2);
        if (pos == null) {
            return false;
        }
        this.teleportCooldownUntil = this.tickCount + TELEPORT_COOLDOWN;
        this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.AMBIENT, 0.6F, 1.0F);
        this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.AMBIENT, 0.6F, 1.0F);
        return true;
    }

    @Override
    public boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypeTags.IS_FIRE)
                || source.is(DamageTypeTags.IS_DROWNING)
                || source.is(DamageTypeTags.IS_EXPLOSION)
                || source.is(DamageTypeTags.IS_FREEZING)
                || source.is(DamageTypeTags.IS_LIGHTNING)
                || source.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }
}
