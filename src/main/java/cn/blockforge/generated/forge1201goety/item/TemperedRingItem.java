package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.items.curios.SingleStackItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.item.TemperedRingItem;

public class TemperedRingItem extends SingleStackItem {

    public TemperedRingItem() {
        this(new Item.Properties().stacksTo(1));
    }

    public TemperedRingItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
            TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.goety_supreme.tempered_ring.desc")
                .withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("item.goety_supreme.tempered_ring.desc2")
                .withStyle(ChatFormatting.BLUE));
    }
}
