package cn.blockforge.generated.goetysupreme.util;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.util.MutantPotions;

public final class MutantPotions {

    public static final ResourceLocation CHEMICAL_X_POTION =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "chemical_x");

    private MutantPotions() {
    }

    @Nullable
    public static Potion chemicalXPotion() {
        return ForgeRegistries.POTIONS.getValue(CHEMICAL_X_POTION);
    }

    public static boolean isMutantPotion(ItemStack stack) {
        if (!stack.is(Items.POTION)) {
            return false;
        }
        Potion chemicalX = chemicalXPotion();
        return chemicalX != null && PotionUtils.getPotion(stack) == chemicalX;
    }

    public static ItemStack mutantPotionStack() {
        ItemStack stack = new ItemStack(Items.POTION);
        Potion chemicalX = chemicalXPotion();
        if (chemicalX != null) {
            PotionUtils.setPotion(stack, chemicalX);
        }
        return stack;
    }
}
