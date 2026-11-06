package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = {
        "com.Polarice3.goety_cataclysm.common.entities.ally.undead.draugr.DraugrServant",
        "com.Polarice3.goety_cataclysm.common.entities.ally.undead.draugr.RoyalDraugrServant"},
        remap = false)
public abstract class CataclysmDraugrRingWeaponMixin {

    @Inject(method = "createSpawnWeapon", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$forceBlackSteelAxe(CallbackInfoReturnable<ItemStack> callback) {
        ItemStack result = callback.getReturnValue();
        if (result == null || result.isEmpty()) {
            return;
        }
        if (!RingSetBonuses.draugrAxeForced((LivingEntity) (Object) this)) {
            return;
        }
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(result.getItem());
        if (key == null || !"black_steel_sword".equals(key.getPath())) {
            return;
        }
        Item axe = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "black_steel_axe"));
        if (axe != null) {
            callback.setReturnValue(new ItemStack(axe));
        }
    }
}
