package cn.blockforge.generated.goetysupreme.entity;

import java.util.List;
import com.Polarice3.Goety.common.blocks.entities.PedestalBlockEntity;
import com.Polarice3.Goety.common.crafting.RitualRecipe;
import com.Polarice3.Goety.common.ritual.ConvertRitual;
import com.Polarice3.Goety.utils.ItemHelper;
import com.Polarice3.Goety.utils.LichdomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class NamelessWitherRitual extends ConvertRitual {

    public NamelessWitherRitual(RitualRecipe recipe) {
        super(recipe, true, false);
    }

    @Override
    public boolean consumeAdditionalIngredients(Level world, BlockPos darkAltarPos, Player player,
            List<Ingredient> remainingAdditionalIngredients, int time,
            List<ItemStack> consumedIngredients) {
        int firstConsumeTime = (int) this.getRecipe().getDurationPerIngredient();
        if (player != null && !LichdomHelper.isLich(player) && time >= firstConsumeTime) {
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
    public void finish(Level world, BlockPos darkAltarPos,
            com.Polarice3.Goety.common.blocks.entities.DarkAltarBlockEntity tileEntity,
            Player castingPlayer, ItemStack activationItem) {
        if (castingPlayer == null || !LichdomHelper.isLich(castingPlayer)) {
            return;
        }
        super.finish(world, darkAltarPos, tileEntity, castingPlayer, activationItem);
    }
}
