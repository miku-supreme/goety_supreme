package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.util.GolemMolds;

public final class GolemMolds {
    private GolemMolds() {
    }

    private static BlockPos at(BlockPos center, int[] p) {
        return center.offset(p[0], p[1], p[2]);
    }

    private static final int[][] MAGMA_BASE = {
            {-1, -1, -1}, {0, -1, -1}, {1, -1, -1},
            {-1, -1, 0}, {0, -1, 0}, {1, -1, 0},
            {-1, -1, 1}, {0, -1, 1}, {1, -1, 1}
    };

    private static final int[][] MAGMA_BRICK_RING = {
            {2, 0, 0}, {2, 0, 1}, {2, 0, -1}, {-2, 0, 0}, {-2, 0, 1}, {-2, 0, -1},
            {0, 0, 2}, {1, 0, 2}, {-1, 0, 2}, {0, 0, -2}, {1, 0, -2}, {-1, 0, -2}
    };

    private static final int[][] MAGMA_CORNER_REDSTONE = {
            {1, 0, 1}, {1, 0, -1}, {-1, 0, 1}, {-1, 0, -1}
    };

    private static final int[][] MAGMA_EDGE_IRON = {
            {0, 0, 1}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}
    };

    private static final int[][] MAGMA_CORNER_LAVA = {
            {2, 0, 2}, {2, 0, -2}, {-2, 0, 2}, {-2, 0, -2}
    };

    private static final Block[] BRICK_BLOCKS = {
            Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.CHISELED_STONE_BRICKS, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES,
            Blocks.POLISHED_BLACKSTONE_BRICKS
    };

    public static boolean isMagmaMold(Level level, BlockPos center) {
        if (!level.getBlockState(center).is(Blocks.REDSTONE_BLOCK)) {
            return false;
        }
        for (int[] p : MAGMA_BASE) {
            if (!level.getBlockState(at(center, p)).is(Blocks.MAGMA_BLOCK)) {
                return false;
            }
        }
        for (int[] p : MAGMA_CORNER_REDSTONE) {
            if (!level.getBlockState(at(center, p)).is(Blocks.REDSTONE_BLOCK)) {
                return false;
            }
        }
        for (int[] p : MAGMA_EDGE_IRON) {
            if (!level.getBlockState(at(center, p)).is(Blocks.IRON_BLOCK)) {
                return false;
            }
        }
        for (int[] p : MAGMA_BRICK_RING) {
            if (!level.getBlockState(at(center, p)).is(Blocks.NETHER_BRICKS)) {
                return false;
            }
        }
        for (int[] p : MAGMA_CORNER_LAVA) {
            if (!level.getFluidState(at(center, p)).is(FluidTags.LAVA)) {
                return false;
            }
        }
        return true;
    }

    public static void clearMagmaMold(Level level, BlockPos center) {
        for (int[] p : MAGMA_CORNER_REDSTONE) {
            clear(level, at(center, p));
        }
        for (int[] p : MAGMA_EDGE_IRON) {
            clear(level, at(center, p));
        }
        clear(level, center);
    }

    private static boolean isBrick(BlockState state) {
        for (Block block : BRICK_BLOCKS) {
            if (state.is(block)) {
                return true;
            }
        }
        return state.getBlock().getDescriptionId().contains("bricks");
    }

    private static final int[][] PRISON_BRICK_BELOW = {
            {0, -1, 0}, {0, -1, 1}, {0, -1, -1}, {1, -1, 0}, {-1, -1, 0},
            {1, -1, 1}, {1, -1, -1}, {-1, -1, 1}, {-1, -1, -1},
            {2, -1, 1}, {2, -1, -1}, {-2, -1, 1}, {-2, -1, -1},
            {1, -1, 2}, {-1, -1, 2}, {1, -1, -2}, {-1, -1, -2}
    };

    private static final int[][] PRISON_BRICK_ABOVE = {
            {1, 0, 3}, {-1, 0, 3}, {1, 0, -3}, {-1, 0, -3},
            {3, 0, 1}, {3, 0, -1}, {-3, 0, 1}, {-3, 0, -1}
    };

    private static final int[][] PRISON_IRON_DIAMOND_BELOW = {
            {2, -1, 0}, {-2, -1, 0}, {0, -1, 2}, {0, -1, -2}
    };

    private static final int[][] PRISON_IRON_DIAMOND_ABOVE = {
            {0, 0, 3}, {0, 0, -3}, {3, 0, 0}, {-3, 0, 0},
            {-2, 0, 2}, {2, 0, 2}, {2, 0, -2}, {-2, 0, -2}
    };

    private static final int[][] PRISON_STEEL_PLATE = {
            {0, 0, 0},
            {1, 0, 2}, {-1, 0, 2}, {1, 0, -2}, {-1, 0, -2},
            {2, 0, 1}, {2, 0, -1}, {-2, 0, 1}, {-2, 0, -1}
    };

    private static final int[][] PRISON_REDSTONE_PLATE = {
            {0, 0, 1}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0},
            {0, 0, 2}, {0, 0, -2}, {2, 0, 0}, {-2, 0, 0},
            {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}
    };

    public static boolean isPrisonMold(Level level, BlockPos center) {
        if (!level.getBlockState(center).is(AstralSetDropin.PRISON_STEEL_BLOCK.get())) {
            return false;
        }
        for (int[][] group : new int[][][] { PRISON_BRICK_BELOW, PRISON_BRICK_ABOVE }) {
            for (int[] p : group) {
                if (!isBrick(level.getBlockState(at(center, p)))) {
                    return false;
                }
            }
        }
        for (int[][] group : new int[][][] { PRISON_IRON_DIAMOND_BELOW, PRISON_IRON_DIAMOND_ABOVE }) {
            for (int[] p : group) {
                if (!level.getBlockState(at(center, p)).is(Blocks.IRON_BLOCK)) {
                    return false;
                }
            }
        }
        for (int[] p : PRISON_STEEL_PLATE) {
            if (!level.getBlockState(at(center, p)).is(AstralSetDropin.PRISON_STEEL_BLOCK.get())) {
                return false;
            }
        }
        for (int[] p : PRISON_REDSTONE_PLATE) {
            if (!level.getBlockState(at(center, p)).is(Blocks.REDSTONE_BLOCK)) {
                return false;
            }
        }
        return true;
    }

    public static void clearPrisonMold(Level level, BlockPos center) {
        for (int[] p : PRISON_STEEL_PLATE) {
            clear(level, at(center, p));
        }
        for (int[] p : PRISON_REDSTONE_PLATE) {
            clear(level, at(center, p));
        }
    }

    private static void clear(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            return;
        }
        level.levelEvent(2001, pos, Block.getId(state));
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
    }
}
