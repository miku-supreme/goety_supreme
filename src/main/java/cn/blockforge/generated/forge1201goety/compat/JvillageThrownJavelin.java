package cn.blockforge.generated.goetysupreme.compat;

import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrownJavelin;

public final class JvillageThrownJavelin {

    private JvillageThrownJavelin() {
    }

    public static boolean isJavelin(ItemStack stack) {
        return stack != null && !stack.isEmpty()
                && stack.getItem() instanceof JerotesItemThrownJavelinUse;
    }

    public static boolean tryThrow(LivingEntity thrower, LivingEntity target) {
        if (thrower.level().isClientSide || target == null) {
            return false;
        }
        ItemStack stack = thrower.getMainHandItem();
        if (!(stack.getItem() instanceof JerotesItemThrownJavelinUse javelin)
                || !javelin.isJerotesThrownJavelin()
                || !javelin.JerotesThrownJavelinCanRange()) {
            return false;
        }
        if (!javelin.JerotesNormalThrownJavelin()) {
            javelin.JerotesNotNormalThrownJavelinUse();
            return true;
        }
        Projectile projectile = javelin.JerotesThrownJavelin(thrower, stack);
        if (projectile == null) {
            return false;
        }
        double maxSpeed = Math.max(0.1D, javelin.getJavelinSpeed());
        BallisticAim.Solution aim = BallisticAim.solveArrow(projectile.position(), target,
                BallisticAim.ARROW_MIN_SPEED, maxSpeed);
        Vec3 shot = aim.velocity();
        projectile.shoot(shot.x, shot.y, shot.z, (float) shot.length(), 0.0F);
        thrower.playSound(SoundEvents.TRIDENT_THROW, 1.0F,
                1.0F / (thrower.getRandom().nextFloat() * 0.4F + 0.8F));
        thrower.level().addFreshEntity(projectile);
        return true;
    }
}
