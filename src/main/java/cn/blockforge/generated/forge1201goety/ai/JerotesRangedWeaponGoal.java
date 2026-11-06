package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.entity.SerponDartEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;

public class JerotesRangedWeaponGoal extends Goal {

    private static final double BLOWPIPE_KEEP_DISTANCE = 12.0D;
    private static final double DEFAULT_KEEP_DISTANCE = 15.0D;

    private static final double MAX_FIRE_DISTANCE = 20.0D;

    private final AbstractJerotesServant mob;

    private final boolean nativeBowJavelinThrow;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public JerotesRangedWeaponGoal(AbstractJerotesServant mob) {
        this(mob, false);
    }

    public JerotesRangedWeaponGoal(AbstractJerotesServant mob, boolean nativeBowJavelinThrow) {
        this.mob = mob;
        this.nativeBowJavelinThrow = nativeBowJavelinThrow;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public static boolean isRangedWeapon(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return true;
        }
        if (stack.getItem() instanceof TridentItem) {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String ns = id.getNamespace();
        String path = id.getPath();
        if ("jerotesvillage".equals(ns) && "serpon_blowpipe".equals(path)) {
            return true;
        }
        if ("jerotes".equals(ns) || "jerotesvillage".equals(ns)) {
            return path.endsWith("_javelin") || path.endsWith("_dagger")
                    || path.endsWith("_throwing_spear") || path.equals("serpon_javelin")
                    || path.equals("serpon_dagger");
        }
        return false;
    }

    public static boolean isThrowableAmmo(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String ns = id.getNamespace();
        if (!"jerotes".equals(ns) && !"jerotesvillage".equals(ns)) {
            return false;
        }
        String path = id.getPath();
        return path.endsWith("_javelin") || path.endsWith("_dagger")
                || path.endsWith("_throwing_spear");
    }

    private static boolean isJavelinLike(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String path = id.getPath();
        return path.endsWith("_javelin") || path.equals("serpon_javelin")
                || path.endsWith("_throwing_spear");
    }

    private static boolean isJerotes(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && "jerotesvillage".equals(id.getNamespace()) && id.getPath().equals(path);
    }

    private boolean handledWeapon(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (nativeBowJavelinThrow) {
            return stack.getItem() instanceof CrossbowItem
                    || isJerotes(stack, "serpon_blowpipe");
        }
        return isRangedWeapon(stack);
    }

    private ItemStack heldWeapon() {
        ItemStack main = mob.getMainHandItem();
        if (handledWeapon(main)) {
            return main;
        }
        ItemStack off = mob.getOffhandItem();
        return handledWeapon(off) ? off : ItemStack.EMPTY;
    }

    private InteractionHand weaponHand() {
        return handledWeapon(mob.getMainHandItem()) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    private boolean holdsBlowpipe() {
        return isJerotes(mob.getMainHandItem(), "serpon_blowpipe")
                || isJerotes(mob.getOffhandItem(), "serpon_blowpipe");
    }

    private double keepDistance() {
        return holdsBlowpipe() ? BLOWPIPE_KEEP_DISTANCE : DEFAULT_KEEP_DISTANCE;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && !mob.isAlliedTo(target) && !heldWeapon().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return (canUse() || !mob.getNavigation().isDone()) && !heldWeapon().isEmpty();
    }

    @Override
    public void start() {
        mob.setAggressive(true);
    }

    @Override
    public void stop() {
        mob.setAggressive(false);
        seeTime = 0;
        attackTime = -1;
        strafingTime = -1;
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
        if (seen != seeTime > 0) {
            seeTime = 0;
        }
        seeTime = seen ? seeTime + 1 : seeTime - 1;

        double keep = keepDistance();
        double keepSqr = keep * keep;
        if (distanceSqr <= keepSqr && seeTime >= 20) {
            mob.getNavigation().stop();
            ++strafingTime;
        } else {
            mob.getNavigation().moveTo(target, 1.0D);
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
            if (distanceSqr > keepSqr * 0.75F) {
                strafingBackwards = false;
            } else if (distanceSqr < keepSqr * 0.25F) {
                strafingBackwards = true;
            }
            mob.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        } else {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        ItemStack weapon = heldWeapon();
        if (weapon.getItem() instanceof BowItem) {
            tickBow(target, weapon);
        } else if (weapon.getItem() instanceof CrossbowItem) {
            tickCrossbow(target, weapon);
        } else if (isJavelinLike(weapon)) {
            tickThrown(target, weapon, distanceSqr);
        } else if (weapon.getItem() instanceof TridentItem) {
            tickTrident(target, weapon, distanceSqr);
        } else {
            tickThrown(target, weapon, distanceSqr);
        }
    }

    private void tickBow(LivingEntity target, ItemStack weapon) {
        if (mob.isUsingItem()) {
            if (!mob.getSensing().hasLineOfSight(target) && seeTime < -60) {
                mob.stopUsingItem();
            } else if (mob.getSensing().hasLineOfSight(target)) {
                int ticks = mob.getTicksUsingItem();
                if (ticks >= 20) {
                    mob.stopUsingItem();
                    shootArrow(target, BowItem.getPowerForTime(ticks));
                    damageRangedWeapon(weapon);
                    attackTime = 20;
                }
            }
        } else if (--attackTime <= 0 && seeTime >= -60) {
            mob.startUsingItem(weaponHand());
        }
    }

    private void tickCrossbow(LivingEntity target, ItemStack weapon) {
        if (mob.isUsingItem()) {
            if (mob.getTicksUsingItem() >= 25) {
                mob.stopUsingItem();
                shootArrow(target, 1.0F);
                attackTime = 25;
            }
        } else if (--attackTime <= 0 && seeTime >= -60) {
            mob.startUsingItem(weaponHand());
        }
    }

    private void tickTrident(LivingEntity target, ItemStack weapon, double distanceSqr) {
        if (attackTime > 0) {
            --attackTime;
            return;
        }
        if (!mob.getSensing().hasLineOfSight(target) || distanceSqr > MAX_FIRE_DISTANCE * MAX_FIRE_DISTANCE) {
            return;
        }
        ThrownTrident trident = new ThrownTrident(mob.level(), mob, weapon.copyWithCount(1));
        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
        trident.setPos(mob.getX(), mob.getEyeY() - 0.1D, mob.getZ());
        Vec3 aim = target.getBoundingBox().getCenter();
        double dx = aim.x - trident.getX();
        double dy = aim.y - trident.getY();
        double dz = aim.z - trident.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        trident.shoot(dx, dy + horizontal * 0.2D, dz, 2.5F, 2.0F);
        mob.playSound(SoundEvents.TRIDENT_THROW, 1.0F,
                1.0F / (mob.getRandom().nextFloat() * 0.4F + 0.8F));
        mob.level().addFreshEntity(trident);
        attackTime = 60;
        mob.swing(weaponHand());
        damageRangedWeapon(weapon);
    }

    private void tickThrown(LivingEntity target, ItemStack weapon, double distanceSqr) {
        if (attackTime > 0) {
            --attackTime;
            return;
        }
        if (!mob.getSensing().hasLineOfSight(target) || distanceSqr > MAX_FIRE_DISTANCE * MAX_FIRE_DISTANCE) {
            return;
        }
        if (isJerotes(weapon, "serpon_blowpipe")) {
            shootDart(target);
            attackTime = 40;
        } else {
            boolean javelinLike = isJavelinLike(weapon);
            throwJavelin(target, weapon, javelinLike ? 2.2F : 1.5F);
            attackTime = javelinLike ? 60 : 40;
            if (!mob.level().isClientSide) {
                weapon.shrink(1);
            }
        }
        mob.swing(weaponHand());
        damageRangedWeapon(weapon);
    }

    private void damageRangedWeapon(ItemStack weapon) {
        if (!JerotesBreakRules.rangeCanBreak(mob.level())) {
            return;
        }
        if (!(weapon.getItem() instanceof BowItem)
                && !(weapon.getItem() instanceof net.minecraft.world.item.TridentItem)) {
            return;
        }
        InteractionHand hand = weaponHand();
        weapon.hurtAndBreak(1, mob, e -> e.broadcastBreakEvent(hand));
    }

    private void shootArrow(LivingEntity target, float power) {
        AbstractArrow arrow = ProjectileUtil.getMobArrow(mob, new ItemStack(Items.ARROW), power);
        arrow.setPos(mob.getX(), mob.getEyeY() - 0.1D, mob.getZ());
        double dx = target.getX() - mob.getX();
        double dy = target.getY(0.35D) - arrow.getY();
        double dz = target.getZ() - mob.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, power * 3.0F, 1.0F);
        mob.playSound(SoundEvents.ARROW_SHOOT, 1.0F,
                1.0F / (mob.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);
        mob.level().addFreshEntity(arrow);
    }

    private void throwJavelin(LivingEntity target, ItemStack stack, float speed) {
        SerponJavelinEntity thrown =
                new SerponJavelinEntity(mob.level(), mob, stack.copyWithCount(1));
        thrown.pickup = AbstractArrow.Pickup.DISALLOWED;
        thrown.setPos(mob.getX(), mob.getEyeY(), mob.getZ());
        Vec3 aim = target.getBoundingBox().getCenter();
        double dx = aim.x - thrown.getX();
        double dy = aim.y - thrown.getY();
        double dz = aim.z - thrown.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        thrown.shoot(dx, dy + horizontal * 0.2D, dz, speed, 3.0F);
        mob.playSound(SoundEvents.TRIDENT_THROW, 1.0F,
                1.0F / (mob.getRandom().nextFloat() * 0.2F + 0.9F));
        mob.level().addFreshEntity(thrown);
    }

    private void shootDart(LivingEntity target) {
        SerponDartEntity dart = new SerponDartEntity(mob.level(), mob);
        dart.setSecondsOnFire(100);
        dart.setBaseDamage(dart.getBaseDamage() + mob.getSummonPotency());
        double dx = target.getX() - mob.getX();
        double dy = target.getY(0.35D) - dart.getY();
        double dz = target.getZ() - mob.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        dart.shoot(dx, dy + horizontal * 0.2D, dz, 3.0F, 1.0F);
        mob.playSound(SoundEvents.ARROW_SHOOT, 1.0F,
                1.0F / (mob.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        mob.level().addFreshEntity(dart);
    }
}
