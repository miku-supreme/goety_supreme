package cn.blockforge.generated.goetysupreme.item;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import com.Polarice3.Goety.api.items.ISoulRepair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.item.DarkSpearItem;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class DarkSpearItem extends FellSpearItem implements ISoulRepair {

    private static final int DURABILITY = 166;

    private static final float TIER_DAMAGE_BONUS = 3.0F;

    private static final float ATTACK_DAMAGE = 4.0F;

    private static final Tier DARK_TIER = new Tier() {
        @Override
        public int getUses() {
            return DURABILITY;
        }

        @Override
        public float getSpeed() {
            return 8.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return TIER_DAMAGE_BONUS;
        }

        @Override
        public int getLevel() {
            return 4;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public Ingredient getRepairIngredient() {
            Item ingot = ForgeRegistries.ITEMS.getValue(
                    ResourceLocation.fromNamespaceAndPath("goety", "dark_ingot"));
            return ingot == null ? Ingredient.EMPTY : Ingredient.of(ingot);
        }

        @Override
        public TagKey<Block> getTag() {
            return BlockTags.NEEDS_DIAMOND_TOOL;
        }
    };

    public DarkSpearItem() {
        super(DARK_TIER, ATTACK_DAMAGE, DURABILITY);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity,
            Consumer<T> onBroken) {
        if (stack.getDamageValue() + amount >= stack.getMaxDamage()) {
            if (stack.getDamageValue() != stack.getMaxDamage() - 1) {
                stack.setDamageValue(stack.getMaxDamage() - 1);
                onBroken.accept(entity);
            }
            return 0;
        }
        return amount;
    }

    public static boolean isPersistBroken(ItemStack stack) {
        return !stack.isEmpty() && stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
            TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("info.goety_supreme.dark_spear")
                .withStyle(ChatFormatting.GRAY));
    }
}
