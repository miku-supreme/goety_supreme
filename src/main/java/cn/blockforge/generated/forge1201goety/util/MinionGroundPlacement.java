package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;

public final class MinionGroundPlacement {

    public static final int SEARCH_RANGE = 128;

    public static final int SKELETON_CLEARANCE = 3;

    public static final int WITHER_CLEARANCE = 4;

    private MinionGroundPlacement() {
    }

    public static double groundFeetY(ServerLevel level, double x, double z, double referenceY,
            int height) {
        int origin = Mth.floor(referenceY);
        int lowest = Math.max(level.getMinBuildHeight(), origin - SEARCH_RANGE);
        int highest = Math.min(level.getMaxBuildHeight() - height - 1, origin + SEARCH_RANGE);
        int best = Integer.MIN_VALUE;
        int bestDistance = Integer.MAX_VALUE;
        for (int y = origin; y >= lowest; --y) {
            if (isStandable(level, x, z, y, height)) {
                best = y;
                bestDistance = origin - y;
                break;
            }
        }
        for (int y = origin + 1; y <= highest; ++y) {
            if (isStandable(level, x, z, y, height)) {
                if (y - origin < bestDistance) {
                    best = y;
                }
                break;
            }
        }
        return best == Integer.MIN_VALUE ? referenceY : best;
    }

    public static boolean isStandable(ServerLevel level, double x, double z, int feetY, int height) {
        BlockPos feet = BlockPos.containing(x, feetY, z);
        if (!level.hasChunkAt(feet)) {
            return false;
        }
        BlockPos floorPos = feet.below();
        BlockState floor = level.getBlockState(floorPos);
        if (floor.getCollisionShape(level, floorPos).isEmpty() || !floor.getFluidState().isEmpty()) {
            return false;
        }
        for (int i = 0; i < height; ++i) {
            BlockPos pos = feet.above(i);
            BlockState state = level.getBlockState(pos);
            if (!state.getCollisionShape(level, pos).isEmpty() || !state.getFluidState().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static double groundFeetYUnderWater(ServerLevel level, double x, double z,
            double referenceY, int height) {
        int origin = Mth.floor(referenceY);
        int lowest = Math.max(level.getMinBuildHeight(), origin - SEARCH_RANGE);
        int highest = Math.min(level.getMaxBuildHeight() - height - 1, origin + SEARCH_RANGE);
        int best = Integer.MIN_VALUE;
        int bestDistance = Integer.MAX_VALUE;
        for (int y = origin; y >= lowest; --y) {
            if (isStandableUnderWater(level, x, z, y, height)) {
                best = y;
                bestDistance = origin - y;
                break;
            }
        }
        for (int y = origin + 1; y <= highest; ++y) {
            if (isStandableUnderWater(level, x, z, y, height)) {
                if (y - origin < bestDistance) {
                    best = y;
                }
                break;
            }
        }
        return best == Integer.MIN_VALUE ? referenceY : best;
    }

    public static boolean isStandableUnderWater(ServerLevel level, double x, double z,
            int feetY, int height) {
        BlockPos feet = BlockPos.containing(x, feetY, z);
        if (!level.hasChunkAt(feet)) {
            return false;
        }
        BlockPos floorPos = feet.below();
        if (level.getBlockState(floorPos).getCollisionShape(level, floorPos).isEmpty()) {
            return false;
        }
        for (int i = 0; i < height; ++i) {
            BlockPos pos = feet.above(i);
            if (!level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
