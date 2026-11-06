package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ally.undead.skeleton.AbstractSkeletonServant;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.SkeletonServant;
import com.Polarice3.Goety.utils.ItemHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.BoneScoutArrow;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySkeletonServantRenderer;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;

public class GoetySkeletonServant extends SkeletonServant implements RagingStarServant {

    private static final double BASE_ATTACK_DAMAGE = 4.0D;

    private static final double STAR_MAX_HEALTH = 40.0D;

    private static final double STAR_ARMOR = 8.0D;
    private static final double STAR_ARMOR_TOUGHNESS = 4.0D;

    private static final double STAR_KNOCKBACK_RESISTANCE = 0.8D;

    private static final double STAR_ARROW_DAMAGE_MULTIPLIER = 1.25D;

    private static final float STAR_ARROW_SPEED_MULTIPLIER = 1.25F;

    private static final float STAR_ARROW_INACCURACY = 1.0F;

    private static final int BURST_LENGTH = 30;

    private static final int BURST_ARROWS = 20;

    private static final int BURST_CYCLE = 500;

    private static final double BURST_RANGE_SQR = 512.0D;

    private static final double MELEE_SPECIAL_RANGE_SQR = 32.0D;

    private static final float MELEE_SPECIAL_MULTIPLIER = 5.0F;

    private static final int MELEE_SPECIAL_COOLDOWN = 500;

    private static final java.util.UUID STAR_HEALTH_UUID =
            java.util.UUID.fromString("e5f4d7a6-8f9b-4c5c-9a0a-6b1f2c9e45d7");
    private static final java.util.UUID STAR_KNOCKBACK_UUID =
            java.util.UUID.fromString("b2c4e6a8-1d3f-4a5b-8c7e-9f0d1a2b3c4d");

    private static final EntityDataAccessor<Boolean> STARRED =
            SynchedEntityData.defineId(GoetySkeletonServant.class, EntityDataSerializers.BOOLEAN);

    private int burstTicks;

    private int burstArrowsFired;
    private int burstCooldown;

    private int meleeSpecialCooldown;

    private boolean inMachineGunBurst;

    public GoetySkeletonServant(EntityType<? extends GoetySkeletonServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return AbstractSkeletonServant.setCustomAttributes()
                .add(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE);
    }

    @Override
    public void setConfigurableAttributes() {
        super.setConfigurableAttributes();
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && Math.abs(attack.getBaseValue() - BASE_ATTACK_DAMAGE) > 1.0E-4D) {
            attack.setBaseValue(BASE_ATTACK_DAMAGE);
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STARRED, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            RagingStarSupport.tickStarParticles(this);
            return;
        }
        RagingStarSupport.tickStarProtection(this);
        tickRagingStar();
    }

    @Override
    public boolean isRagingStarred() {
        return entityData.hasItem(STARRED) && Boolean.TRUE.equals(entityData.get(STARRED));
    }

    @Override
    public void setRagingStarred(boolean starred) {
        if (starred == isRagingStarred()) {
            return;
        }
        entityData.set(STARRED, starred);
        if (level().isClientSide()) {
            return;
        }
        getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, starred);
        if (starred) {
            applyStarAttributes();
            setHealth(getMaxHealth());
        }
    }

    private void applyStarAttributes() {
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        if (health != null && health.getModifier(STAR_HEALTH_UUID) == null) {
            double delta = STAR_MAX_HEALTH - health.getBaseValue();
            if (Math.abs(delta) > 1.0E-4D) {
                health.addPermanentModifier(new AttributeModifier(STAR_HEALTH_UUID,
                        "Raging star health", delta, AttributeModifier.Operation.ADDITION));
            }
        }
        setBaseIfDifferent(Attributes.ARMOR, STAR_ARMOR);
        setBaseIfDifferent(Attributes.ARMOR_TOUGHNESS, STAR_ARMOR_TOUGHNESS);
        AttributeInstance knockback = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (knockback != null && knockback.getModifier(STAR_KNOCKBACK_UUID) == null) {
            knockback.addPermanentModifier(new AttributeModifier(STAR_KNOCKBACK_UUID,
                    "Raging star knockback resistance", STAR_KNOCKBACK_RESISTANCE,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public int getTicksUsingItem() {
        int ticks = super.getTicksUsingItem();
        return isRagingStarred() ? ticks * 2 : ticks;
    }

    private void setBaseIfDifferent(net.minecraft.world.entity.ai.attributes.Attribute attribute, double wanted) {
        AttributeInstance instance = getAttribute(attribute);
        if (instance != null && Math.abs(instance.getBaseValue() - wanted) > 1.0E-4D) {
            instance.setBaseValue(wanted);
        }
    }

    private void tickRagingStar() {
        if (!isRagingStarred()) {
            return;
        }
        if (tickCount % 20 == 0 && getHealth() < getMaxHealth()) {
            heal(1.0F);
        }
        if (tickCount % 20 == 1) {
            applyStarAttributes();
        }
        LivingEntity target = getTarget();
        tickMeleeSpecial(target);
        if (burstTicks > 0) {
            tickMachineGunBurst(target);
            return;
        }
        if (burstCooldown > 0) {
            --burstCooldown;
        }
        if (burstCooldown <= 0 && target != null && target.isAlive()
                && getMainHandItem().getItem() instanceof BowItem
                && distanceToSqr(target) <= BURST_RANGE_SQR) {
            burstTicks = BURST_LENGTH;
            burstArrowsFired = 0;
            burstCooldown = BURST_CYCLE - BURST_LENGTH;
        }
    }

    private void tickMachineGunBurst(LivingEntity target) {
        int elapsed = BURST_LENGTH - burstTicks;
        if (burstArrowsFired < BURST_ARROWS) {
            int due = (int) ((long) burstArrowsFired * BURST_LENGTH / BURST_ARROWS);
            if (elapsed >= due && target != null && target.isAlive()
                    && getMainHandItem().getItem() instanceof BowItem) {
                inMachineGunBurst = true;
                try {
                    performRangedAttack(target, 2.0F);
                } finally {
                    inMachineGunBurst = false;
                }
                ++burstArrowsFired;
            }
        }
        --burstTicks;
    }

    private void tickMeleeSpecial(LivingEntity target) {
        if (meleeSpecialCooldown > 0) {
            --meleeSpecialCooldown;
            return;
        }
        if (target == null || !target.isAlive() || distanceToSqr(target) > MELEE_SPECIAL_RANGE_SQR) {
            return;
        }
        meleeSpecialCooldown = MELEE_SPECIAL_COOLDOWN;
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * MELEE_SPECIAL_MULTIPLIER;
        target.hurt(damageSources().mobAttack(this), damage);
        swing(InteractionHand.MAIN_HAND);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        InteractionHand hand = ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem);
        ItemStack weapon = getItemInHand(hand);
        ItemStack projectile = getProjectile(weapon);
        AbstractArrow arrow = getMobArrow(projectile, power);
        if (getMainHandItem().getItem() instanceof BowItem bow) {
            arrow = bow.customArrow(arrow);
            if (!inMachineGunBurst) {
                ItemHelper.hurtAndBreak(getMainHandItem(), 1, this);
            }
        }
        double damage = arrow.getBaseDamage() + getArrowPower() + getBaseRangeDamage();
        if (isRagingStarred()) {
            damage *= STAR_ARROW_DAMAGE_MULTIPLIER;
        }
        arrow.setBaseDamage(damage);

        double dx = target.getX() - getX();
        double dy = target.getY(0.3333333333333333D) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Mth.sqrt((float) (dx * dx + dz * dz));
        float speed = isRagingStarred() ? 1.6F * STAR_ARROW_SPEED_MULTIPLIER : 1.6F;
        float inaccuracy = isRagingStarred()
                ? STAR_ARROW_INACCURACY
                : (float) (14 - level().getDifficulty().getId() * 4);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, speed, inaccuracy);
        SoundEvent shootSound = getShootSound();
        if (shootSound != null) {
            playSound(shootSound, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
        }
        level().addFreshEntity(arrow);
    }

    @Override
    public AbstractArrow getMobArrow(ItemStack projectile, float power) {
        if (!isRagingStarred()) {
            return super.getMobArrow(projectile, power);
        }
        BoneScoutArrow arrow = new BoneScoutArrow(level(), this);
        arrow.setEnchantmentEffectsFromEntity(this, power);
        arrow.setEffectsFromItem(projectile);
        return arrow;
    }

    @Override
    public boolean isSunSensitive() {
        return !isRagingStarred() && super.isSunSensitive();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (isRagingStarred() && RagingStarSupport.isVanillaHarmful(effect)) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isRagingStarred() && amount > 0.0F) {
            amount *= RagingStarSupport.DAMAGE_MULTIPLIER;
            if (RagingStarSupport.isElementReducedSource(source)) {
                amount *= RagingStarSupport.ELEMENT_DAMAGE_MULTIPLIER;
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tryKill(Player player) {
        if (isRagingStarred() && getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        super.tryKill(player);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean hitByPlayer) {
        super.dropCustomDeathLoot(source, looting, hitByPlayer);
        if (isRagingStarred()) {
            OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.RAGING_STAR.get()));
        }
    }

    @Override
    public Component getName() {
        if (isRagingStarred() && !hasCustomName()) {
            return Component.translatable("entity.goety_supreme.bone_scout");
        }
        return super.getName();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(RagingStarSupport.NBT_KEY, isRagingStarred());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        float savedHealth = tag.contains("Health") ? tag.getFloat("Health") : getMaxHealth();
        super.readAdditionalSaveData(tag);
        boolean starred = tag.getBoolean(RagingStarSupport.NBT_KEY);
        if (starred) {
            entityData.set(STARRED, true);
            getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, true);
            applyStarAttributes();
            setHealth(Math.min(savedHealth, getMaxHealth()));
        }
    }

    public static Class<? extends AbstractSkeletonServant> servantBaseClass() {
        return SkeletonServant.class;
    }
}
