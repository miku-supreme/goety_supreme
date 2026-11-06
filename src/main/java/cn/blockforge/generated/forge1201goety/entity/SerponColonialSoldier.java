package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;

public class SerponColonialSoldier extends AbstractSerpon implements RangedAttackMob {

    @Override
    public boolean acceptsEquipment(net.minecraft.world.item.ItemStack stack) {
        return isAnyJerotesItem(stack, "serpon_helmet", "serpon_chestplate", "serpon_leggings",
                "serpon_boots", "serpon_spiked_shield", "serpon_blowpipe", "serpon_javelin",
                "serpon_dagger", "serpon_sword");
    }

    private static final int JAVELIN_COOLDOWN = 60;
    private static final int BLOWPIPE_COOLDOWN = 40;

    private static final double FOLLOW_RANGE = 32.0D;

    private int rangedCooldown;

    public SerponColonialSoldier(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_colonial_soldier.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_COLONIAL_SOLDIER;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new SerponRangedGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponColonialSoldier.this.meleeReachSqr(target);
            }
        });
    }

    @Override
    public void tickExtra() {
        if (rangedCooldown > 0) {
            rangedCooldown--;
        }
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        if (wearsRingDressedSerponArmor()) {
            equipRingSerponArmor(EquipmentSlot.HEAD, "serpon_helmet");
            equipRingSerponArmor(EquipmentSlot.CHEST, "serpon_chestplate");
            equipRingSerponArmor(EquipmentSlot.LEGS, "serpon_leggings");
            equipRingSerponArmor(EquipmentSlot.FEET, "serpon_boots");
        } else {
            if (random.nextFloat() < 0.5F) {
                equipJerotes(EquipmentSlot.HEAD, "serpon_helmet");
            }
            if (random.nextFloat() < 0.5F) {
                equipJerotes(EquipmentSlot.CHEST, "serpon_chestplate");
            }
            if (random.nextFloat() < 0.5F) {
                equipJerotes(EquipmentSlot.LEGS, "serpon_leggings");
            }
            if (random.nextFloat() < 0.5F) {
                equipJerotes(EquipmentSlot.FEET, "serpon_boots");
            }
        }
        float weaponRoll = random.nextFloat();
        if (weaponRoll > 0.75F) {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_sword");
        } else if (weaponRoll > 0.6F) {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_blowpipe");
        } else if (weaponRoll > 0.3F) {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_dagger");
        } else {
            equipJerotes(EquipmentSlot.MAINHAND, "serpon_javelin");
        }
        if (random.nextFloat() > 0.4F) {
            setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        } else {
            equipJerotes(EquipmentSlot.OFFHAND, "serpon_spiked_shield");
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    public void equipGuaranteedShield() {
        equipJerotes(EquipmentSlot.OFFHAND, "serpon_spiked_shield");
        setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    public boolean holdsRangedWeapon() {
        return holdsJerotesItem("serpon_javelin") || holdsJerotesItem("serpon_blowpipe");
    }

    @Override
    public boolean isAimingBlowpipe() {
        return holdsJerotesItem("serpon_blowpipe");
    }

    public int rangedCooldown() {
        return rangedCooldown;
    }

    public void setRangedCooldown(int ticks) {
        this.rangedCooldown = ticks;
    }

    public int cooldownForHeldWeapon() {
        return holdsJerotesItem("serpon_javelin") ? JAVELIN_COOLDOWN : BLOWPIPE_COOLDOWN;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        ItemStack main = getMainHandItem();
        if (main.getItem() instanceof BowItem) {
            shootServantArrow(target, velocity);
            return;
        }
        if (holdsJerotesItem("serpon_javelin")) {
            throwJavelin(target);
            return;
        }
        if (main.getItem() instanceof TridentItem) {
            ThrownTrident trident =
                    new ThrownTrident(level(), this,
                            main.copyWithCount(1));
            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
            trident.setPos(getX(), getEyeY() - 0.1D, getZ());
            Vec3 aim = target.getBoundingBox().getCenter();
            double dx = aim.x - trident.getX();
            double dy = aim.y - trident.getY();
            double dz = aim.z - trident.getZ();
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            trident.shoot(dx, dy + horizontal * 0.2D, dz, 2.5F, 2.0F);
            playSound(SoundEvents.TRIDENT_THROW, 1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 0.8F));
            level().addFreshEntity(trident);
            if (JerotesBreakRules.rangeCanBreak(level())) {
                main.hurtAndBreak(1, this, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
            swing(InteractionHand.MAIN_HAND);
            return;
        }
        if (JerotesRangedWeaponGoal.isThrowableAmmo(main) || holdsJerotesItem("serpon_javelin")) {
            throwJavelin(target);
            if (!level().isClientSide && !holdsJerotesItem("serpon_javelin")) {
                main.shrink(1);
            }
            return;
        }
        if (holdsJerotesItem("serpon_blowpipe")) {
            shootDart(target);
        }
    }

    private void shootServantArrow(LivingEntity target, float power) {
        AbstractArrow arrow = ProjectileUtil.getMobArrow(this,
                new ItemStack(Items.ARROW), Math.max(0.1F, power));
        arrow.setPos(getX(), getEyeY() - 0.1D, getZ());
        double dx = target.getX() - getX();
        double dy = target.getY(0.35D) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, power * 3.0F, 1.0F);
        playSound(SoundEvents.ARROW_SHOOT, 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 1.2F) + power * 0.5F);
        level().addFreshEntity(arrow);
        if (JerotesBreakRules.rangeCanBreak(level())) {
            getMainHandItem().hurtAndBreak(1, this,
                    e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
        swing(InteractionHand.MAIN_HAND);
    }

    private void throwJavelin(LivingEntity target) {
        ItemStack copy = getMainHandItem().copyWithCount(1);
        SerponJavelinEntity thrown = new SerponJavelinEntity(level(), this, copy);
        thrown.pickup = AbstractArrow.Pickup.DISALLOWED;
        thrown.setPos(getX(), getEyeY(), getZ());
        Vec3 aim = target.getBoundingBox().getCenter();
        double dx = aim.x - thrown.getX();
        double dy = aim.y - thrown.getY();
        double dz = aim.z - thrown.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        thrown.shoot(dx, dy + horizontal * 0.2D, dz, 2.2F, 3.0F);
        playSound(SoundEvents.TRIDENT_THROW, 1.0F, 1.0F / (random.nextFloat() * 0.2F + 0.9F));
        level().addFreshEntity(thrown);
    }

    private void shootDart(LivingEntity target) {
        AbstractArrow dart = ProjectileUtil.getMobArrow(this, new ItemStack(Items.ARROW), 1.0F);
        dart.setPos(getX(), getEyeY() - 0.1D, getZ());
        dart.setBaseDamage(4.0D + getSummonPotency());
        dart.setPierceLevel((byte) 100);
        double dx = target.getX() - getX();
        double dy = target.getY(0.35D) - dart.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        dart.shoot(dx, dy + horizontal * 0.2D, dz, 3.0F, 1.0F);
        playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.addFreshEntity(dart);
        }
    }

    private static final class SerponRangedGoal extends Goal {
        private final SerponColonialSoldier servant;

        SerponRangedGoal(SerponColonialSoldier servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = servant.getTarget();
            if (target == null || !target.isAlive() || servant.isAlliedTo(target)
                    || !servant.holdsRangedWeapon()) {
                return false;
            }
            double minRange = servant.isAimingBlowpipe() ? 5.0D : 8.0D;
            return servant.distanceToSqr(target) > minRange * minRange;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = servant.getTarget();
            if (target == null) {
                return;
            }
            servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distanceSqr = servant.distanceToSqr(target);
            double keepDistance = servant.isAimingBlowpipe() ? 15.0D : 12.0D;
            if (distanceSqr > keepDistance * keepDistance) {
                servant.getNavigation().moveTo(target, servant.isAimingBlowpipe() ? 0.5D : 1.0D);
            } else {
                servant.getNavigation().stop();
            }
            if (servant.rangedCooldown() <= 0) {
                servant.performRangedAttack(target, 1.0F);
                servant.setRangedCooldown(servant.cooldownForHeldWeapon());
            }
        }
    }
}
