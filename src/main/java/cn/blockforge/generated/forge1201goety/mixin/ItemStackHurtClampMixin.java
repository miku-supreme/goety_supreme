package cn.blockforge.generated.goetysupreme.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackHurtClampMixin {

    @ModifyVariable(method = "m_220157_", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private int goetySupreme$clampHurtAmount(int amount) {
        ItemStack self = (ItemStack) (Object) this;
        int cap = self.getMaxDamage();
        if (cap <= 0 || amount <= cap) {
            return amount;
        }
        return cap;
    }
}
