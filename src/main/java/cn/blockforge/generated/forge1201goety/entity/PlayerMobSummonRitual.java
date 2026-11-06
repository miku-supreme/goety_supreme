package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.blocks.entities.DarkAltarBlockEntity;
import com.Polarice3.Goety.common.crafting.RitualRecipe;
import com.Polarice3.Goety.common.ritual.SummonRitual;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobSummonRitual;

public final class PlayerMobSummonRitual extends SummonRitual {
    public PlayerMobSummonRitual(RitualRecipe recipe) {
        super(recipe, true, false);
    }

    @Override
    public void initSummoned(LivingEntity entity, Level level, BlockPos pos,
            DarkAltarBlockEntity altar, Player player) {
        super.initSummoned(entity, level, pos, altar, player);
        if (!(entity instanceof PlayerMobServant servant) || altar == null) {
            return;
        }
        ItemStack weapon = pickWeapon(altar);
        if (!weapon.isEmpty()) {
            servant.setItemSlot(EquipmentSlot.MAINHAND, weapon.copyWithCount(1));
            servant.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        }
    }

    private static ItemStack pickWeapon(DarkAltarBlockEntity altar) {
        if (altar.consumedIngredients == null) {
            return ItemStack.EMPTY;
        }
        for (ItemStack stack : altar.consumedIngredients) {
            if (isWeapon(stack)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean isWeapon(ItemStack stack) {
        return !stack.isEmpty() && (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)
                || PlayerMobServant.isTridentLike(stack)
                || stack.is(Items.BOW) || stack.is(Items.CROSSBOW));
    }
}
