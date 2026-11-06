package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.item.BrokenReaperScytheItem;
import cn.blockforge.generated.goetysupreme.item.GreenAppleItem;
import cn.blockforge.generated.goetysupreme.item.OvergrowthCoreItem;

public class BrokenReaperScytheItem extends Item {

    public BrokenReaperScytheItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copyWithCount(1);
    }
}
