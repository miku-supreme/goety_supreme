package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.ReinforcedBlastFurnaceBlockEntity;

public class ReinforcedBlastFurnaceBlockEntity extends BlastFurnaceBlockEntity {
    public ReinforcedBlastFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return GeneratedMod.REINFORCED_BLAST_FURNACE_BLOCK_ENTITY.get();
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.goety_supreme.reinforced_blast_furnace");
    }

    @Override
    public int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        return AbstractFurnaceBlockEntity.getFuel().getOrDefault(fuel.getItem(), 0);
    }
}
