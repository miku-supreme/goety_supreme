package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;

final class PlayerMobEnderPearl {

    private final PlayerMobServant mob;

    PlayerMobEnderPearl(PlayerMobServant mob) {
        this.mob = mob;
    }

    boolean isEnderPearlEngagement() {
        if (this.mob.level().isClientSide || this.mob.enderPearlCooldown > 0) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !this.mob.isValidCombatTargetForGoal(target)) {
            return false;
        }
        double minDistance = Math.max(1.0D, PlayerMobServantConfig.ENDER_PEARL_MIN_DISTANCE.get());
        if (this.mob.distanceToSqr(target) <= minDistance * minDistance) {
            return false;
        }
        if (isRangedWeaponHeld() || this.mob.isThrowableStack(this.mob.getMainHandItem())) {
            return false;
        }
        if (this.mob.isWandCasterSetup()) {
            return false;
        }
        return findEnderPearlStack() != null;
    }

    private boolean isRangedWeaponHeld() {
        ItemStack main = this.mob.getMainHandItem();
        return main.getItem() instanceof ProjectileWeaponItem || PlayerMobWeapons.isTridentLike(main);
    }

    @Nullable
    ItemStack findEnderPearlStack() {
        if (this.mob.getMainHandItem().is(Items.ENDER_PEARL)) {
            return this.mob.getMainHandItem();
        }
        if (this.mob.getOffhandItem().is(Items.ENDER_PEARL)) {
            return this.mob.getOffhandItem();
        }
        for (int i = 0; i < this.mob.servantStorage.getContainerSize(); i++) {
            ItemStack stack = this.mob.servantStorage.getItem(i);
            if (stack.is(Items.ENDER_PEARL)) {
                return stack;
            }
        }
        return null;
    }

    boolean throwEnderPearlAt(LivingEntity target) {
        if (this.mob.level().isClientSide || target == null || !target.isAlive()) {
            return false;
        }
        ItemStack source = findEnderPearlStack();
        if (source == null) {
            this.mob.enderPearlCooldown = PlayerMobServant.ENDER_PEARL_RETRY_TICKS;
            return false;
        }
        if (!this.mob.hasLineOfSight(target)) {
            this.mob.enderPearlCooldown = PlayerMobServant.ENDER_PEARL_RETRY_TICKS;
            return false;
        }
        this.mob.stopShieldGuard();
        Vec3 from = new Vec3(this.mob.getX(), this.mob.getEyeY() - 0.1D, this.mob.getZ());
        Vec3 center = target.position();
        Vec3 firstLanding = findEnderPearlLanding(target, center);
        BallisticAim.Solution first = BallisticAim.solveFast(from,
                firstLanding != null ? firstLanding : center,
                PlayerMobServant.ENDER_PEARL_GRAVITY, PlayerMobServant.ENDER_PEARL_DRAG,
                PlayerMobServant.ENDER_PEARL_MIN_SPEED, PlayerMobServant.ENDER_PEARL_MAX_SPEED);
        Vec3 motion = target.getDeltaMovement();
        Vec3 predicted = center.add(BallisticAim.leadOffset(motion, first.ticks()));
        Vec3 landing = findEnderPearlLanding(target, predicted);
        if (landing == null) {
            landing = predicted;
        }
        BallisticAim.Solution aim = BallisticAim.solveFast(from, landing,
                PlayerMobServant.ENDER_PEARL_GRAVITY, PlayerMobServant.ENDER_PEARL_DRAG,
                PlayerMobServant.ENDER_PEARL_MIN_SPEED, PlayerMobServant.ENDER_PEARL_MAX_SPEED);
        Vec3 shot = aim.velocity();
        if (shot.lengthSqr() < 1.0E-6D) {
            this.mob.enderPearlCooldown = PlayerMobServant.ENDER_PEARL_RETRY_TICKS;
            return false;
        }
        ServantThrownEnderpearl pearl = new ServantThrownEnderpearl(this.mob.level(), this.mob);
        pearl.setPearlLanding(landing);
        pearl.setPos(from.x, from.y, from.z);
        pearl.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        this.mob.playSound(SoundEvents.ENDER_PEARL_THROW, 0.5F,
                0.4F / (this.mob.getRandom().nextFloat() * 0.4F + 0.8F));
        this.mob.level().addFreshEntity(pearl);
        source.shrink(1);
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.servantFood.addExhaustion(0.1F);
        this.mob.setCombatChaseSprint(false);
        this.mob.enderPearlCooldown = Math.max(1, PlayerMobServantConfig.ENDER_PEARL_THROW_INTERVAL.get());
        return true;
    }

    @Nullable
    private Vec3 findEnderPearlLanding(LivingEntity target, Vec3 predicted) {
        double baseY = target.getY();
        Vec3 towardServant = new Vec3(this.mob.getX() - predicted.x, 0.0D, this.mob.getZ() - predicted.z);
        if (towardServant.lengthSqr() < 1.0E-4D) {
            double angle = this.mob.getRandom().nextDouble() * Math.PI * 2.0D;
            towardServant = new Vec3(Math.cos(angle), 0.0D, Math.sin(angle));
        } else {
            towardServant = towardServant.normalize();
        }
        for (int attempt = 0; attempt < 10; attempt++) {
            double radius = 0.8D + this.mob.getRandom().nextDouble()
                    * (PlayerMobServant.ENDER_PEARL_LANDING_RADIUS - 0.8D);
            double angle = (this.mob.getRandom().nextDouble() - 0.5D) * (Math.PI * 0.6D);
            Vec3 dir = towardServant.yRot((float) angle);
            Vec3 spot = findStandableSpot(predicted.x + dir.x * radius,
                    predicted.z + dir.z * radius, baseY);
            if (spot != null) {
                return spot;
            }
        }
        return findStandableSpot(predicted.x, predicted.z, baseY);
    }

    @Nullable
    Vec3 findStandableSpot(double x, double z, double yHint) {
        int floorY = Mth.floor(yHint);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = floorY + 2; y >= floorY - 6; y--) {
            pos.set(Mth.floor(x), y, Mth.floor(z));
            boolean ground = this.mob.level().getBlockState(pos.below()).blocksMotion();
            boolean headroom = !this.mob.level().getBlockState(pos).blocksMotion()
                    && !this.mob.level().getBlockState(pos.above()).blocksMotion();
            if (!ground || !headroom) {
                continue;
            }
            if (this.mob.level().noCollision(this.mob,
                    this.mob.getDimensions(Pose.STANDING).makeBoundingBox(x, y, z))
                    && !ServantTeleportHelper.isDangerousLanding(this.mob.level(), this.mob, x, y, z)) {
                return new Vec3(x, y, z);
            }
        }
        return null;
    }
}
