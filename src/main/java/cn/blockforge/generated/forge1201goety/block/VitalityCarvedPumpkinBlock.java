package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.block.VitalityCarvedPumpkinBlock;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;
import cn.blockforge.generated.goetysupreme.api.VitalityPumpkinHead;

public final class VitalityCarvedPumpkinBlock extends CarvedPumpkinBlock implements VitalityPumpkinHead {
    public VitalityCarvedPumpkinBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CARVED_PUMPKIN));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.is(state.getBlock())) {
            return;
        }
        VitalityPumpkinConstructions.trySummonServant(level, pos);
    }
}
