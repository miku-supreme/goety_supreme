package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritServant;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritSlashEntity;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritTeleports;

public class SakuraSpiritServant extends Summoned implements ISakuraSpirit, ISakuraSpiritTeleporter {
    private static final float FALL_DRAG = 0.6F;
    private static final int TELEPORT_COOLDOWN = 30;
    private static final int TELEPORT_TRIES = 12;
    private static final double TELEPORT_RADIUS = 32.0D;
    private static final int MAX_FALL_TICKS = 40;

    private int lastMeleeHitTick;
    private int airTime;
    private int teleportCooldownUntil;

    public SakuraSpiritServant(EntityType<? extends Summoned> type, Level level) {
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

    @Override
    public void setConfigurableAttributes() {
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.MAX_HEALTH),
                SakuraSpiritConfig.MAX_HEALTH.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.ATTACK_DAMAGE),
                SakuraSpiritConfig.ATTACK_DAMAGE.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.ARMOR), SakuraSpiritConfig.ARMOR.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.ARMOR_TOUGHNESS),
                SakuraSpiritConfig.ARMOR_TOUGHNESS.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.KNOCKBACK_RESISTANCE),
                SakuraSpiritConfig.KNOCKBACK_RESISTANCE.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.FOLLOW_RANGE),
                SakuraSpiritConfig.FOLLOW_RANGE.get());
        SakuraSpiritEntity.setBaseAttribute(getAttribute(Attributes.MOVEMENT_SPEED),
                SakuraSpiritConfig.MOVEMENT_SPEED.get());
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
        this.followGoal();
        this.targetRetaliateGoal();
        this.targetSelector.addGoal(2, new SummonTargetGoal(this, true, true));
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
    public void updateInvisibilityStatus() {
        super.updateInvisibilityStatus();
        this.removeEffectParticles();
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
        this.setConfigurableAttributes();
        this.setHealth(this.getMaxHealth());
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.enchantAllEquipped(level.getRandom());
        return data;
    }

    @Override
    public boolean tryTeleport() {
        if (this.teleportCooldownUntil > this.tickCount) {
            return false;
        }
        BlockPos pos = this.findSafePosAround();
        if (pos != null) {
            this.teleportCooldownUntil = this.tickCount + TELEPORT_COOLDOWN;
            this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(true),
                    SoundSource.AMBIENT, 0.6F, 1.0F);
            this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(false),
                    SoundSource.AMBIENT, 0.6F, 1.0F);
            return true;
        }
        return false;
    }

    private SoundEvent enderlingTeleport(boolean out) {
        SoundEvent sound = out
                ? com.Polarice3.Goety.init.ModSounds.ENDERLING_TELEPORT_OUT.get()
                : com.Polarice3.Goety.init.ModSounds.ENDERLING_TELEPORT_IN.get();
        return sound == null ? SoundEvents.ENDERMAN_TELEPORT : sound;
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
        this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(true),
                SoundSource.AMBIENT, 0.6F, 1.0F);
        this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(false),
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
        this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(true),
                SoundSource.AMBIENT, 0.6F, 1.0F);
        this.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        this.level().playSound((Player) null, this.blockPosition(), enderlingTeleport(false),
                SoundSource.AMBIENT, 0.6F, 1.0F);
        return true;
    }

    @Nullable
    public BlockPos findSafePosAround() {
        RandomSource random = this.getRandom();
        for (int i = 0; i < TELEPORT_TRIES; i++) {
            double x = this.getX() + (random.nextDouble() - 0.5D) * TELEPORT_RADIUS;
            double z = this.getZ() + (random.nextDouble() - 0.5D) * TELEPORT_RADIUS;
            int y = this.blockPosition().getY() + random.nextInt(8) - 4;
            BlockPos pos = BlockPos.containing(x, y, z);
            if (this.level().getBlockState(pos).isAir()
                    && this.level().getBlockState(pos.above()).isAir()
                    && this.level().getBlockState(pos.below()).isSolid()) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y() < 0.0D) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x(), motion.y() * FALL_DRAG, motion.z());
        }
        super.aiStep();
        if (!this.level().isClientSide) {
            if (!this.onGround()) {
                if (++this.airTime > MAX_FALL_TICKS) {
                    this.tryTeleport();
                    this.airTime = 0;
                }
            } else {
                this.airTime = 0;
            }

            LivingEntity target = this.getTarget();
            LivingEntity owner = this.getTrueOwner();
            if (owner != null && owner.isAlive()) {
                LivingEntity ownerHurtBy = owner.getLastHurtByMob();
                if (ownerHurtBy != null && ownerHurtBy.isAlive() && ownerHurtBy != this
                        && this.distanceToSqr(ownerHurtBy) > 36.0D && this.random.nextInt(20) == 0) {
                    BlockPos avengerPos = ownerHurtBy.blockPosition();
                    if (this.level().getBlockState(avengerPos).isAir()
                            && this.level().getBlockState(avengerPos.above()).isAir()) {
                        this.teleportTo(avengerPos.getX() + 0.5D, avengerPos.getY(), avengerPos.getZ() + 0.5D);
                    }
                }
            }
            if (target != null && target.isAlive()) {
                double distSqr = this.distanceToSqr(target);
                if (distSqr > 256.0D && this.random.nextInt(20) == 0) {
                    BlockPos targetPos = target.blockPosition();
                    if (this.level().getBlockState(targetPos).isAir()
                            && this.level().getBlockState(targetPos.above()).isAir()) {
                        this.teleportCooldownUntil = this.tickCount + TELEPORT_COOLDOWN;
                        this.teleportTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
                    }
                } else if (distSqr > 100.0D && this.random.nextInt(40) == 0) {
                    this.tryTeleport();
                }
            }
            this.evadeProjectiles();
            this.spaceOutSiblings();

            BlockState under = this.level().getBlockState(this.blockPosition());
            if ((under.is(BlockTags.FIRE) || under.is(Blocks.CACTUS) || under.is(Blocks.MAGMA_BLOCK)
                    || under.is(Blocks.LAVA)) && this.random.nextInt(20) == 0) {
                this.tryTeleport();
            }
        }
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
            this.level().playSound((Player) null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_STEP,
                    SoundSource.AMBIENT, 0.4F, 1.0F);
        }
    }

    private void evadeProjectiles() {
        for (Projectile projectile : this.level().getEntitiesOfClass(Projectile.class,
                this.getBoundingBox().inflate(10.0D))) {
            Entity owner = projectile.getOwner();
            if (owner != null && this.isAlliedTo(owner)) {
                continue;
            }
            Vec3 motion = projectile.getDeltaMovement();
            if (motion.lengthSqr() < 0.01D) {
                continue;
            }
            Vec3 incoming = projectile.position().vectorTo(this.position()).normalize();
            Vec3 forward = motion.normalize();
            if (incoming.dot(forward) <= 0.7D || this.distanceToSqr(projectile) >= 64.0D) {
                continue;
            }
            Vec3 side = SakuraSpiritSlashEntity.perpendicular(forward);
            Vec3 dodge = incoming.dot(side) > 0.0D ? side : side.scale(-1.0D);
            if (this.random.nextInt(70) == 0) {
                this.tryTeleport();
            }
            this.getNavigation().moveTo(this.getX() + dodge.x * 5.0D, this.getY(),
                    this.getZ() + dodge.z * 5.0D, 1.5D);
            break;
        }
    }

    private void spaceOutSiblings() {
        if (this.onGround()) {
            return;
        }
        SakuraSpiritServant nearest = null;
        double nearestSqr = 9.0D;
        for (SakuraSpiritServant sibling : this.level().getEntitiesOfClass(SakuraSpiritServant.class,
                this.getBoundingBox().inflate(5.0D))) {
            if (sibling == this || sibling.getTrueOwner() != this.getTrueOwner()) {
                continue;
            }
            double sqr = this.distanceToSqr(sibling);
            if (sqr < nearestSqr) {
                nearestSqr = sqr;
                nearest = sibling;
            }
        }
        if (nearest == null) {
            return;
        }
        Vec3 away = this.position().subtract(nearest.position());
        away = new Vec3(away.x, 0.0D, away.z);
        if (away.lengthSqr() < 1.0E-4D) {
            double angle = this.getId() * 2.399963229728653D;
            away = new Vec3(Math.cos(angle), 0.0D, Math.sin(angle));
        }
        away = away.normalize().scale(3.0D);
        this.push(away.x * 0.08D, 0.0D, away.z * 0.08D);
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
    public int getMaxSpawnClusterSize() {
        return 2;
    }
}
