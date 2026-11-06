package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;

public final class ServantResurrection {
    final BlockPos pos;
    int tick;

    ServantResurrection(BlockPos pos, int tick) {
        this.pos = pos.immutable();
        this.tick = tick;
    }
}
