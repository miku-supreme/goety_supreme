package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.ItemStackHurtClampMixin;

@Mixin(Mob.class)
public abstract class MobRingGearLockMixin {

    @Inject(method = "m_7808_", at = @At("HEAD"), cancellable = true)
    private void goetySupreme$lockRingGear(ItemStack newItem, ItemStack oldItem,
            CallbackInfoReturnable<Boolean> callback) {
        if (RingServantGear.isRingGear(oldItem)) {
            callback.setReturnValue(false);
        }
    }
}
