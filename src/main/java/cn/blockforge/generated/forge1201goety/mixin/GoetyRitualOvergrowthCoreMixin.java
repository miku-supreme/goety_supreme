package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.item.OvergrowthCoreItem;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordHelmetItem;
import com.Polarice3.Goety.common.ritual.Ritual;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Ritual.class, remap = false)
public abstract class GoetyRitualOvergrowthCoreMixin {

    @Redirect(
            method = "consumeAdditionalIngredientFromPedestal",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;hasCraftingRemainingItem()Z"),
            remap = false,
            require = 0)
    private boolean goetySupreme$consumeRitualIngredient(ItemStack stack) {
        if (stack.getItem() instanceof OvergrowthCoreItem
                || stack.getItem() instanceof HellfireSaberItem
                || stack.getItem() instanceof WitherWarlordHelmetItem) {
            return false;
        }
        return stack.hasCraftingRemainingItem();
    }
}
