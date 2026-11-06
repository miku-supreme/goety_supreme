package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import cn.blockforge.generated.goetysupreme.block.PrismarineCoreBlock;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;

public class PrismarineCoreBlock extends Block {
    public PrismarineCoreBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS)
                .lightLevel(state -> 15));
    }
}
