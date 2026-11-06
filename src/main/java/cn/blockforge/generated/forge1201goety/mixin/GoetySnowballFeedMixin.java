package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MobUtil.class, remap = false)
public abstract class GoetySnowballFeedMixin {
    @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true, require = 0)
    private static void goetySupreme$allowSnowballFeed(Projectile projectile, Entity target,
            CallbackInfoReturnable<Boolean> callback) {
        boolean mutantSnowGolem = MutantMonstersCompat.isLoaded() && target instanceof MutantSnowGolemServant;
        if (projectile instanceof Snowball && (mutantSnowGolem
                || target instanceof SnowGolemServant && projectile.getOwner() instanceof SnowGolemServant)) {
            callback.setReturnValue(true);
        }
    }
}
