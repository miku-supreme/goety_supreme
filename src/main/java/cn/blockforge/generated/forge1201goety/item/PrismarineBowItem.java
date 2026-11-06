package cn.blockforge.generated.goetysupreme.item;

import java.util.function.Predicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PrismarineArrowEntity;
import cn.blockforge.generated.goetysupreme.item.PrismarineBowItem;

public class PrismarineBowItem extends BowItem {

    private static final Predicate<ItemStack> AMMO = stack ->
            stack.is(ItemTags.ARROWS) || stack.is(GeneratedMod.PRISMARINE_ARROW.get());

    public PrismarineBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return AMMO;
    }
}
