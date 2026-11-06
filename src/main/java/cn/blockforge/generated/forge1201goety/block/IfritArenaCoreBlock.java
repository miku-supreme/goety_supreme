package cn.blockforge.generated.goetysupreme.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.block.IfritArenaCoreBlock;
import cn.blockforge.generated.goetysupreme.entity.IfritArenaCoreBlockEntity;

public final class IfritArenaCoreBlock extends BaseEntityBlock {

    public IfritArenaCoreBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .noOcclusion()
                .instabreak()
                .sound(SoundType.STONE)
                .noLootTable());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IfritArenaCoreBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        if (type == GeneratedMod.IFRIT_ARENA_CORE_ENTITY.get()) {
            BlockEntityTicker<IfritArenaCoreBlockEntity> ticker = IfritArenaCoreBlockEntity::serverTick;
            return castTicker(ticker);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> BlockEntityTicker<T> castTicker(BlockEntityTicker<?> ticker) {
        return (BlockEntityTicker<T>) ticker;
    }
}
