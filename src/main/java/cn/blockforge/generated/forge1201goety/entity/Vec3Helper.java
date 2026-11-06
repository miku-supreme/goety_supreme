package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;

final class Vec3Helper {
    static double x(BlockPos pos) {
        return pos.getX() + 0.5D;
    }

    static double y(BlockPos pos) {
        return pos.getY();
    }

    static double z(BlockPos pos) {
        return pos.getZ() + 0.5D;
    }
}
