package cn.blockforge.generated.goetysupreme.compat;

import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobEnderPearlGoal;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrowShoot;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class JvillageThrowShoot {

    private JvillageThrowShoot() {
    }

    public static boolean isThrowUse(ItemStack stack) {
        return stack != null && !stack.isEmpty()
                && stack.getItem() instanceof JerotesItemThrowUse;
    }

    public static boolean canThrow(ItemStack stack, LivingEntity holder) {
        return stack != null && !stack.isEmpty()
                && stack.getItem() instanceof JerotesItemThrowUse throwUse
                && throwUse.isJerotesThrow(holder);
    }

    public static boolean throwAt(LivingEntity thrower, InteractionHand hand, LivingEntity target) {
        if (thrower.level().isClientSide || target == null) {
            return false;
        }
        ItemStack stack = thrower.getItemInHand(hand);
        if (!(stack.getItem() instanceof JerotesItemThrowUse throwUse)
                || !throwUse.isJerotesThrow(thrower)) {
            return false;
        }
        float speed = throwUse.useJerotesThrowShootSpeed(thrower, hand);
        Projectile projectile = throwUse.useJerotesThrowShoot(thrower, hand);
        if (projectile == null) {
            return false;
        }
        double dx = target.getX() - thrower.getX();
        double dy = target.getY(1.0D / 3.0D) - projectile.getY();
        double dz = target.getZ() - thrower.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        projectile.shoot(dx, dy + horizontal * (0.2D - speed * 0.03D), dz, speed, 2.0F);
        thrower.level().playSound(null, thrower.getX(), thrower.getY(), thrower.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F,
                0.4F / (thrower.getRandom().nextFloat() * 0.4F + 0.8F));
        thrower.level().addFreshEntity(projectile);
        if (throwUse.isJerotesThrowShrinkSelf()) {
            stack.shrink(1);
        }
        if (throwUse.isJerotesThrowShrinkOther()) {
            InteractionHand other = hand == InteractionHand.MAIN_HAND
                    ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            thrower.getItemInHand(other).shrink(1);
        }
        return true;
    }
}
