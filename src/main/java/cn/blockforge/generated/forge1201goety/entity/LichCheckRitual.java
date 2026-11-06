package cn.blockforge.generated.goetysupreme.entity;

import java.util.List;
import com.Polarice3.Goety.common.blocks.entities.DarkAltarBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.PedestalBlockEntity;
import com.Polarice3.Goety.common.crafting.RitualRecipe;
import com.Polarice3.Goety.common.ritual.CraftItemRitual;
import com.Polarice3.Goety.utils.ItemHelper;
import com.Polarice3.Goety.utils.LichdomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class LichCheckRitual extends CraftItemRitual {

    public LichCheckRitual(RitualRecipe recipe) {
        super(recipe);
    }

    @Override
    public boolean consumeAdditionalIngredients(Level world, BlockPos darkAltarPos, Player player,
            List<Ingredient> remainingAdditionalIngredients, int time,
            List<ItemStack> consumedIngredients) {
        int firstConsumeTime = (int) this.getRecipe().getDurationPerIngredient();
        if (player != null && !LichdomHelper.isLich(player) && time >= firstConsumeTime) {
            // 非巫妖：把基座上的材料弹出——物品弹出会让祭坛判定材料缺失并中断仪式
            for (PedestalBlockEntity pedestal : this.getPedestals(world, darkAltarPos)) {
                pedestal.itemStackHandler.ifPresent(handler -> {
                    ItemStack stack = handler.extractItem(0, 1, false);
                    if (!stack.isEmpty()) {
                        handler.setStackInSlot(0, ItemStack.EMPTY);
                        ItemHelper.addItemEntity(world, pedestal.getBlockPos().above(), stack);
                    }
                });
            }
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                        Component.translatable("info.goety_supreme.ritual.lichRequired"), true);
            }
            return false;
        }
        return super.consumeAdditionalIngredients(world, darkAltarPos, player,
                remainingAdditionalIngredients, time, consumedIngredients);
    }

    @Override
    public boolean isValid(Level world, BlockPos darkAltarPos, DarkAltarBlockEntity tileEntity,
            Player castingPlayer, ItemStack activationItem,
            List<Ingredient> remainingAdditionalIngredients) {
        return super.isValid(world, darkAltarPos, tileEntity, castingPlayer, activationItem,
                remainingAdditionalIngredients);
    }

    @Override
    public void finish(Level world, BlockPos darkAltarPos, DarkAltarBlockEntity tileEntity,
            Player castingPlayer, ItemStack activationItem) {
        if (castingPlayer == null || !LichdomHelper.isLich(castingPlayer)) {
            return;
        }
        super.finish(world, darkAltarPos, tileEntity, castingPlayer, activationItem);
    }
}
