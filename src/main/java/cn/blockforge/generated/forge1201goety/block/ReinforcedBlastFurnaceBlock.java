package cn.blockforge.generated.goetysupreme.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.block.ReinforcedBlastFurnaceBlock;
import cn.blockforge.generated.goetysupreme.entity.ReinforcedBlastFurnaceBlockEntity;

public class ReinforcedBlastFurnaceBlock extends BlastFurnaceBlock {
    public ReinforcedBlastFurnaceBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(3.5F)
                .sound(SoundType.STONE)
                .lightLevel(state -> state.getValue(BlastFurnaceBlock.LIT) ? 13 : 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlastFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, GeneratedMod.REINFORCED_BLAST_FURNACE_BLOCK_ENTITY.get());
    }
}
