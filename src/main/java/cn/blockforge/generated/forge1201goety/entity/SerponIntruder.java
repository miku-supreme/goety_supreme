package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.EliteServant;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.entity.SerponIntruder;

public class SerponIntruder extends AbstractSerpon implements EliteServant {

    @Override
    public boolean acceptsEquipment(net.minecraft.world.item.ItemStack stack) {
        return isAnyJerotesItem(stack, "serpon_powerhouse_helmet", "serpon_powerhouse_chestplate",
                "serpon_powerhouse_leggings", "serpon_powerhouse_boots",
                "serpon_spear", "serpon_pike");
    }

    private static final double FOLLOW_RANGE = 32.0D;

    private static final int RUSH_TICKS = 40;
    private static final int RUSH_COOLDOWN = 120;

    private static final double RUSH_RANGE = 64.0D;

    private static final java.util.UUID SPEAR_DAMAGE_UUID =
            java.util.UUID.fromString("7f0e2b41-5c9d-4a86-9f1e-2d3c4b5a6e70");

    private int rushTick;
    private int rushCooldown;

    public SerponIntruder(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 80;
        setShieldLevel(3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.37D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.MAX_HEALTH, 55.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_intruder.png";
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_INTRUDER;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponIntruder.this.meleeReachSqr(target);
            }
        });
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity target) {
        return holdsLongWeapon() ? 9.0D : super.getMeleeAttackRangeSqr(target);
    }

    private boolean holdsLongWeapon() {
        return FellSpearItem.isSpear(getMainHandItem())
                || holdsJerotesItem("serpon_spear") || holdsJerotesItem("serpon_glaive");
    }

    private boolean holdsSpear() {
        return isSpear(getMainHandItem());
    }

    private static boolean isSpear(net.minecraft.world.item.ItemStack stack) {
        return FellSpearItem.isSpear(stack)
                || isAnyJerotesItem(stack, "serpon_spear", "serpon_pike");
    }

    @Override
    public void doPush(Entity entity) {
        if (rushTick > 0 && entity == getTarget() && !holdsSpear()) {
            doHurtTarget(entity);
            rushTick = 0;
        }
        super.doPush(entity);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        net.minecraft.world.entity.ai.attributes.AttributeInstance attack =
                holdsSpear() ? getAttribute(Attributes.ATTACK_DAMAGE) : null;
        if (attack != null) {
            attack.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    SPEAR_DAMAGE_UUID, "Serpon spear damage multiple", 1.0D,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        boolean hit;
        try {
            hit = super.doHurtTarget(target);
        } finally {
            if (attack != null) {
                attack.removeModifier(SPEAR_DAMAGE_UUID);
            }
        }
        if (hit && !level().isClientSide()) {
            serponSweep(target, 1.5D, 1.0D);
        }
        return hit;
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "serpon_powerhouse_helmet");
        equipJerotes(EquipmentSlot.CHEST, "serpon_powerhouse_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "serpon_powerhouse_leggings");
        equipJerotes(EquipmentSlot.FEET, "serpon_powerhouse_boots");
        if (random.nextFloat() > 0.5F) {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_spear");
        } else {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_glaive");
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    @Override
    public void tickExtra() {
        if (rushCooldown > 0) {
            --rushCooldown;
        }
        if (rushTick > 0) {
            --rushTick;
            if (getTarget() != null) {
                if (!level().isClientSide()) {
                    removeEffect(MobEffects.MOVEMENT_SPEED);
                    removeEffect(MobEffects.JUMP);
                    addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0));
                }
                getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
                lookAt(getTarget(), 360.0F, 360.0F);
                rushAttack();
            }
        }
        if (level().isClientSide()) {
            return;
        }
        LivingEntity target = getTarget();
        if (target == null || distanceToSqr(target) >= RUSH_RANGE * RUSH_RANGE
                || rushTick > 0 || rushCooldown > 0 || !hasLineOfSight(target)) {
            return;
        }
        boolean startRush = random.nextInt(60) == 1;
        if (!startRush) {
            startRush = random.nextInt(20) == 1
                    && isUsingItem() && isSpear(getUseItem())
                    && !(isSpear(getMainHandItem()) && getUseItem() != getMainHandItem());
        }
        if (startRush) {
            rushTick = RUSH_TICKS;
            rushCooldown = RUSH_COOLDOWN;
            if (!isSilent()) {
                playSound(jerotesSound("serpon_dash", SoundEvents.RAVAGER_ROAR), 1.0F, 1.0F);
            }
        }
    }

    private void rushAttack() {
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 5; ++i) {
                var movement = getDeltaMovement();
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0,
                        movement.x, movement.y - 0.1D, movement.z, 0.0D);
                serverLevel.sendParticles(ParticleTypes.FLAME,
                        getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0,
                        movement.x, movement.y - 0.1D, movement.z, 0.0D);
            }
        }
        float yaw = getYRot() * ((float) Math.PI / 180.0F);
        float pitch = getXRot() * ((float) Math.PI / 180.0F);
        float dirX = -Mth.sin(yaw) * Mth.cos(pitch);
        float dirY = -Mth.sin(pitch);
        float dirZ = Mth.cos(yaw) * Mth.cos(pitch);
        float length = Mth.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float strength = 0.1F;
        setDeltaMovement(getDeltaMovement().add(
                dirX / length * strength * 2.0D,
                dirY / length * strength * 1.0D,
                dirZ / length * strength * 2.0D));
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("RushTick", this.rushTick);
        tag.putInt("RushCooldown", this.rushCooldown);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.rushTick = tag.getInt("RushTick");
        this.rushCooldown = tag.getInt("RushCooldown");
    }
}
