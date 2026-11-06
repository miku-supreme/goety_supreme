package cn.blockforge.generated.goetysupreme.block;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonBlock;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonFrameBlock;

public final class MobilePrisonFrameBlock extends Block {

    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 26);

    public static final IntegerProperty DOOR = IntegerProperty.create("door", 0, 8);

    public static final BooleanProperty CRIMSON = BooleanProperty.create("crimson");

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final int DOOR_PART_BOTTOM = 1;
    public static final int DOOR_PART_MIDDLE = 10;
    public static final int DOOR_PART_TOP = 19;

    public static final int INTERIOR_PART = 13;

    public static final int CORE_PART = 22;

    private static final Set<Integer> LIT_PARTS = Set.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 18, 19, 20, 21, 22, 23, 24, 25, 26);

    public MobilePrisonFrameBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(5.0F, 1200.0F)
                .sound(SoundType.METAL)
                .noOcclusion()
                .pushReaction(PushReaction.BLOCK)
                .lightLevel(state -> LIT_PARTS.contains(state.getValue(PART)) ? 15 : 0));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0).setValue(DOOR, 0).setValue(CRIMSON, false)
                .setValue(FACING, Direction.NORTH));
    }

    public static BlockPos offsetOf(int part) {
        return new BlockPos(part % 3 - 1, part / 9, part / 3 % 3 - 1);
    }

    public static BlockPos worldOffsetOf(int part, Direction facing) {
        return MobilePrisonBlock.worldOffsetOf(part, facing);
    }

    public static boolean isDoorPart(int part) {
        return part == DOOR_PART_BOTTOM || part == DOOR_PART_MIDDLE || part == DOOR_PART_TOP;
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, DOOR, CRIMSON, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int part = state.getValue(PART);
        boolean doorOpen = isDoorPart(part) && state.getValue(DOOR) > 0;
        return doorShape(part, doorOpen, state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        int part = state.getValue(PART);
        boolean doorOpen = isDoorPart(part) && state.getValue(DOOR) > 0;
        return doorShape(part, doorOpen, state.getValue(FACING));
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos,
            PathComputationType type) {
        if (type != PathComputationType.LAND) {
            return super.isPathfindable(state, level, pos, type);
        }
        int part = state.getValue(PART);
        if (isDoorPart(part)) {
            return state.getValue(DOOR) > 0;
        }
        return part == INTERIOR_PART || part == CORE_PART;
    }

    private static final float[][] DOOR_LEAF_BOTTOM = {
            {1.0F, 0.0F, -6.0F, 3.0F, 16.0F, -5.0F},
            {1.0F, 0.0F, -3.0F, 3.0F, 16.0F, -2.0F},
            {1.0F, 0.0F, 0.0F, 3.0F, 16.0F, 1.0F}};
    private static final float[][] DOOR_LEAF_MIDDLE = {
            {0.0F, 0.0F, -9.0F, 4.0F, 16.0F, 3.0F},
            {4.0F, 0.0F, -7.0F, 5.0F, 16.0F, 1.0F}};
    private static final float[][] DOOR_LEAF_TOP = {
            {1.0F, 0.0F, -8.0F, 4.0F, 16.0F, 3.0F}};

    private static VoxelShape doorShape(int part, boolean doorOpen, Direction facing) {
        VoxelShape shape = MobilePrisonBlock.clippedCollision(part, doorOpen, facing);
        if (!doorOpen) {
            return shape;
        }
        float[][] leaf = switch (part) {
            case DOOR_PART_BOTTOM -> DOOR_LEAF_BOTTOM;
            case DOOR_PART_MIDDLE -> DOOR_LEAF_MIDDLE;
            case DOOR_PART_TOP -> DOOR_LEAF_TOP;
            default -> null;
        };
        if (leaf == null) {
            return shape;
        }
        for (float[] cellBox : leaf) {
            float[] b = MobilePrisonBlock.rotateBox(cellBox, facing);
            shape = Shapes.or(shape, Block.box(b[0] / 16.0D, b[1] / 16.0D, b[2] / 16.0D,
                    b[3] / 16.0D, b[4] / 16.0D, b[5] / 16.0D));
        }
        return shape;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        int part = state.getValue(PART);
        BlockPos anchor = pos.subtract(worldOffsetOf(part, state.getValue(FACING)));
        BlockState anchorState = level.getBlockState(anchor);
        if (!anchorState.is(GeneratedMod.MOBILE_PRISON_BLOCK.get())) {
            return InteractionResult.PASS;
        }
        return MobilePrisonBlock.interact(level, anchor, anchorState, player, hand, part,
                hit.getLocation().subtract(anchor.getX(), anchor.getY(), anchor.getZ()));
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(level, pos, state, player);
        if (!level.isClientSide) {
            MobilePrisonBlockEntity.removeWholePrison(level, pos, state, true);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            MobilePrisonBlockEntity.removeWholePrison(level, pos, state, true);
        }
        super.onRemove(state, level, pos, newState, moved);
    }
}
