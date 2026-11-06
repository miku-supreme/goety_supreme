package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import cn.blockforge.generated.goetysupreme.block.ConcentratedBoneChunkBlock;

public final class ConcentratedBoneChunkBlock extends Block {
    public ConcentratedBoneChunkBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .sound(SoundType.BONE_BLOCK)
                .strength(2.0F));
    }
}
