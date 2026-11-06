package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import cn.blockforge.generated.goetysupreme.item.EntwinedLanternItem;
import cn.blockforge.generated.goetysupreme.item.OvergrowthCoreItem;

public final class OvergrowthCoreItem extends Item {

    public OvergrowthCoreItem() {
        super(new Properties().stacksTo(16).rarity(Rarity.RARE));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(this);
    }
}
