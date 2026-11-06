package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;
import cn.blockforge.generated.goetysupreme.api.VitalityPumpkinHead;
import cn.blockforge.generated.goetysupreme.block.VitalityPumpkinLanternBlock;

public final class VitalityPumpkinLanternBlock extends CarvedPumpkinBlock implements VitalityPumpkinHead {
    public VitalityPumpkinLanternBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.is(state.getBlock())) {
            return;
        }
        VitalityPumpkinConstructions.trySummonServant(level, pos);
    }
}
