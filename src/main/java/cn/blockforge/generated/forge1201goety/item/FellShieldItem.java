package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import cn.blockforge.generated.goetysupreme.event.FellShieldEvents;
import cn.blockforge.generated.goetysupreme.item.FellShieldItem;

public final class FellShieldItem extends ShieldItem {

    public static final int DURABILITY = 336 * 2;

    public FellShieldItem() {
        super(new Item.Properties().durability(DURABILITY).rarity(Rarity.RARE));
    }
}
