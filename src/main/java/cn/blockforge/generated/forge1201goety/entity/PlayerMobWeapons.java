package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrownJavelin;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrowShoot;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;

final class PlayerMobWeapons {

    private final PlayerMobServant mob;

    PlayerMobWeapons(PlayerMobServant mob) {
        this.mob = mob;
    }

    boolean canUseMeleeAi() {
        return this.mob.meleeWandMode || isThrowMeleeWeapon(this.mob.getMainHandItem());
    }

    static boolean isTridentLike(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.getItem() instanceof TridentItem || stack.is(Items.TRIDENT)) {
            return true;
        }
        return stack.getTags().anyMatch(tag -> {
            String path = tag.location().getPath().toLowerCase(java.util.Locale.ROOT);
            String[] segments = path.split("/");
            for (String segment : segments) {
                if (segment.equals("trident") || segment.equals("tridents")) {
                    return true;
                }
            }
            return false;
        });
    }

    static boolean isMeleeWeapon(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem() instanceof ProjectileWeaponItem
                || isTridentLike(stack)) {
            return false;
        }
        return stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)
                || stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE);
    }

    static boolean isThrowMeleeWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return isMeleeWeapon(stack) || isTridentLike(stack) || JerotesItems.isDagger(stack)
                || JerotesItems.isSpear(stack) || JerotesItems.isPike(stack);
    }

    static double tridentMeleeRangeSqr() {
        double range = Math.max(1.0D, PlayerMobServantConfig.TRIDENT_MELEE_RANGE.get());
        return range * range;
    }

    boolean isTridentRangedEngagement() {
        LivingEntity target = this.mob.getTarget();
        return isTridentLike(this.mob.getMainHandItem())
                && target != null && this.mob.isValidCombatTargetForGoal(target)
                && this.mob.distanceToSqr(target) > tridentMeleeRangeSqr();
    }

    void throwTridentCopy(LivingEntity target) {
        if (this.mob.level().isClientSide) {
            return;
        }
        if (JerotesVillageCompat.isJerotesThrownJavelinLoadable()
                && JvillageThrownJavelin.isJavelin(this.mob.getMainHandItem())
                && JvillageThrownJavelin.tryThrow(this.mob, target)) {
            this.mob.servantFood.addExhaustion(0.1F);
            return;
        }
        ItemStack copy = this.mob.getMainHandItem().copyWithCount(1);
        ServantThrownTrident thrown = new ServantThrownTrident(this.mob.level(), this.mob, copy);
        thrown.pickup = AbstractArrow.Pickup.DISALLOWED;
        thrown.setPos(this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        BallisticAim.Solution aim = BallisticAim.solveArrow(thrown.position(), target,
                BallisticAim.ARROW_MIN_SPEED, PlayerMobServant.TRIDENT_MAX_SPEED);
        Vec3 shot = aim.velocity();
        thrown.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        this.mob.playSound(SoundEvents.TRIDENT_THROW, 1.0F,
                1.0F / (this.mob.getRandom().nextFloat() * 0.2F + 0.9F));
        this.mob.level().addFreshEntity(thrown);
        this.mob.servantFood.addExhaustion(0.1F);
    }

    boolean isUsingTridentAim() {
        return this.mob.isUsingItem() && this.mob.getUsedItemHand() == InteractionHand.MAIN_HAND
                && isTridentLike(this.mob.getUseItem());
    }

    boolean isThrowableStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (JerotesVillageCompat.isJerotesThrowUseLoadable()
                && JvillageThrowShoot.canThrow(stack, this.mob)) {
            return true;
        }
        Item item = stack.getItem();
        return item instanceof SnowballItem || item instanceof EggItem
                || item instanceof ThrowablePotionItem;
    }

    InteractionHand throwHand() {
        if (this.mob.isWandCasterSetup()) {
            return null;
        }
        if (isThrowableStack(this.mob.getMainHandItem())) {
            return InteractionHand.MAIN_HAND;
        }
        if (!isThrowableStack(this.mob.getOffhandItem())) {
            return null;
        }
        if (this.mob.getMainHandItem().isEmpty()) {
            return InteractionHand.OFF_HAND;
        }
        return isThrowMeleeWeapon(this.mob.getMainHandItem()) ? InteractionHand.OFF_HAND : null;
    }

    boolean isThrowEngagement() {
        InteractionHand hand = throwHand();
        if (hand == null) {
            return false;
        }
        if (hand == InteractionHand.MAIN_HAND || this.mob.getMainHandItem().isEmpty()) {
            return true;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return true;
        }
        return PlayerMobMeleeAttackGoal.gapDistanceSqr(this.mob, target) > this.mob.meleeAttackReachSqr();
    }

    boolean isThrowEscapeEngagement() {
        InteractionHand hand = throwHand();
        if (hand == null) {
            return false;
        }
        return hand == InteractionHand.MAIN_HAND || this.mob.getMainHandItem().isEmpty();
    }

    boolean performThrowAttack(LivingEntity target) {
        if (this.mob.level().isClientSide || target == null || !target.isAlive()) {
            return false;
        }
        InteractionHand hand = throwHand();
        if (hand == null) {
            return false;
        }
        boolean thrown;
        if (JerotesVillageCompat.isJerotesThrowUseLoadable()
                && JvillageThrowShoot.isThrowUse(this.mob.getItemInHand(hand))) {
            thrown = JvillageThrowShoot.throwAt(this.mob, hand, target);
        } else {
            thrown = throwVanillaProjectile(target, hand);
        }
        if (thrown) {
            this.mob.swing(hand);
            this.mob.servantFood.addExhaustion(0.1F);
            this.mob.setCombatChaseSprint(false);
        }
        return thrown;
    }

    private boolean throwVanillaProjectile(LivingEntity target, InteractionHand hand) {
        ItemStack stack = this.mob.getItemInHand(hand);
        Item item = stack.getItem();
        float speed;
        Projectile projectile;
        boolean potionStyle = false;
        if (item instanceof EggItem) {
            speed = 1.5F;
            ThrownEgg egg = new ThrownEgg(this.mob.level(), this.mob);
            egg.setItem(stack);
            projectile = egg;
        } else if (item instanceof SnowballItem) {
            speed = 1.5F;
            Snowball snowball = new Snowball(this.mob.level(), this.mob);
            snowball.setItem(stack);
            projectile = snowball;
        } else if (item instanceof ThrowablePotionItem) {
            speed = 0.5F;
            ThrownPotion potion = new ThrownPotion(this.mob.level(), this.mob);
            potion.setItem(stack);
            projectile = potion;
            potionStyle = true;
        } else {
            return false;
        }
        projectile.setPos(this.mob.getX(), this.mob.getEyeY() - 0.1D, this.mob.getZ());
        if (potionStyle) {
            projectile.shootFromRotation(this.mob, this.mob.getXRot(), this.mob.getYRot(), -20.0F, speed, 1.0F);
        } else {
            double dx = target.getX() - projectile.getX();
            double dy = target.getY(1.0D / 3.0D) - projectile.getY();
            double dz = target.getZ() - projectile.getZ();
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            projectile.shoot(dx, dy + horizontal * (0.2D - speed * 0.03D), dz, speed, 2.0F);
        }
        this.mob.playSound(SoundEvents.SNOWBALL_THROW, 0.5F,
                0.4F / (this.mob.getRandom().nextFloat() * 0.4F + 0.8F));
        this.mob.level().addFreshEntity(projectile);
        stack.shrink(1);
        return true;
    }
}
