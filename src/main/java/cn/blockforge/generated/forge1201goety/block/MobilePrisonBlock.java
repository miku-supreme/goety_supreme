package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonBlock;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonFrameBlock;

public final class MobilePrisonBlock extends BaseEntityBlock {

    public static final BooleanProperty CRIMSON = MobilePrisonFrameBlock.CRIMSON;

    public static final DirectionProperty FACING = MobilePrisonFrameBlock.FACING;

    public static final int ANCHOR_PART = 4;

    public MobilePrisonBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(5.0F, 1200.0F)
                .sound(SoundType.METAL)
                .noOcclusion()
                .pushReaction(PushReaction.BLOCK)
                .lightLevel(state -> 15));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CRIMSON, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRIMSON, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos anchor = context.getClickedPos();
        Direction facing = context.getHorizontalDirection().getOpposite();
        for (int part = 0; part <= 26; part++) {
            if (part == ANCHOR_PART) {
                continue;
            }
            BlockState existing = level.getBlockState(anchor.offset(worldOffsetOf(part, facing)));
            if (!existing.canBeReplaced()) {
                Player player = context.getPlayer();
                if (player != null && !level.isClientSide) {
                    player.displayClientMessage(
                            Component.translatable("info.goety_supreme.mobile_prison.no_space"), true);
                }
                return null;
            }
        }
        return this.defaultBlockState().setValue(CRIMSON, false).setValue(FACING, facing);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);
        if (!level.isClientSide && !moved && !oldState.is(state.getBlock())) {
            buildFrame(state, level, pos);
        }
    }

    private void buildFrame(BlockState state, Level level, BlockPos anchor) {
        boolean crimson = state.getValue(CRIMSON);
        Direction facing = state.getValue(FACING);
        for (int part = 0; part <= 26; part++) {
            if (part == ANCHOR_PART) {
                continue;
            }
            BlockPos cell = anchor.offset(worldOffsetOf(part, facing));
            BlockState frame = GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get().defaultBlockState()
                    .setValue(MobilePrisonFrameBlock.PART, part)
                    .setValue(MobilePrisonFrameBlock.DOOR, 0)
                    .setValue(MobilePrisonFrameBlock.CRIMSON, crimson)
                    .setValue(MobilePrisonFrameBlock.FACING, facing);
            level.setBlock(cell, frame, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && !level.isClientSide) {
            MobilePrisonBlockEntity.removeFrames(level, pos, state.getValue(FACING));
        }
        super.onRemove(state, level, pos, newState, moved);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        return MobilePrisonBlock.interact(level, pos, state, player, hand, ANCHOR_PART,
                hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static InteractionResult interact(Level level, BlockPos anchor, BlockState state, Player player,
            InteractionHand hand, int part, Vec3 local) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (!player.getItemInHand(hand).isEmpty()) {
            return InteractionResult.PASS;
        }
        boolean doorClick = MobilePrisonFrameBlock.isDoorPart(part) && local.y >= 0.10D;
        boolean coreClick = !doorClick && part == MobilePrisonFrameBlock.CORE_PART;
        if (!doorClick && !coreClick) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            if (doorClick) {
                if (level.getBlockEntity(anchor) instanceof MobilePrisonBlockEntity prison) {
                    prison.toggleDoor();
                }
            } else {
                setCrimson(level, anchor, state, !state.getValue(CRIMSON));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static void setCrimson(Level level, BlockPos anchor, BlockState state, boolean crimson) {
        level.setBlock(anchor, state.setValue(CRIMSON, crimson), Block.UPDATE_ALL);
        Block frameBlock = GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get();
        Direction facing = state.getValue(FACING);
        for (int part = 0; part <= 26; part++) {
            if (part == ANCHOR_PART) {
                continue;
            }
            BlockPos cell = anchor.offset(worldOffsetOf(part, facing));
            BlockState cellState = level.getBlockState(cell);
            if (cellState.is(frameBlock)) {
                level.setBlock(cell, cellState.setValue(MobilePrisonFrameBlock.CRIMSON, crimson),
                        Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return clippedCollision(ANCHOR_PART, false, state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return clippedCollision(ANCHOR_PART, false, state.getValue(FACING));
    }

    public static BlockPos worldOffsetOf(int part, Direction facing) {
        return rotateOffset(MobilePrisonFrameBlock.offsetOf(part), facing);
    }

    public static BlockPos rotateOffset(BlockPos cageLocal, Direction facing) {
        int x = cageLocal.getX();
        int y = cageLocal.getY();
        int z = cageLocal.getZ();
        return switch (facing) {
            case EAST -> new BlockPos(-z, y, x);
            case SOUTH -> new BlockPos(-x, y, -z);
            case WEST -> new BlockPos(z, y, -x);
            default -> new BlockPos(x, y, z);
        };
    }

    public static float[] rotateBox(float[] box, Direction facing) {
        float x0 = box[0];
        float y0 = box[1];
        float z0 = box[2];
        float x1 = box[3];
        float y1 = box[4];
        float z1 = box[5];
        float rx0;
        float rx1;
        float rz0;
        float rz1;
        switch (facing) {
            case EAST -> {
                rx0 = 16.0F - z1;
                rx1 = 16.0F - z0;
                rz0 = x0;
                rz1 = x1;
            }
            case SOUTH -> {
                rx0 = 16.0F - x1;
                rx1 = 16.0F - x0;
                rz0 = 16.0F - z1;
                rz1 = 16.0F - z0;
            }
            case WEST -> {
                rx0 = z0;
                rx1 = z1;
                rz0 = 16.0F - x1;
                rz1 = 16.0F - x0;
            }
            default -> {
                rx0 = x0;
                rx1 = x1;
                rz0 = z0;
                rz1 = z1;
            }
        }
        return new float[] {rx0, y0, rz0, rx1, y1, rz1};
    }

    public static float[][] cageBoxes(boolean doorOpen) {
        if (doorOpen) {
            return new float[][] {
                    {-16.0F, 0.0F, -16.0F, 32.0F, 2.5F, 32.0F},      // 地板
                    {-16.0F, 45.5F, -16.0F, 32.0F, 48.0F, 32.0F},    // 顶板
                    {-16.0F, 2.5F, 28.5F, 32.0F, 45.5F, 31.5F},      // 后墙
                    {-14.5F, 2.5F, -16.0F, -11.5F, 45.5F, 32.0F},    // 左墙
                    {28.5F, 2.5F, -16.0F, 31.5F, 45.5F, 32.0F},      // 右墙
                    {-16.0F, 2.5F, -14.5F, 0.5F, 45.5F, -11.5F},
                    {15.5F, 2.5F, -14.5F, 32.0F, 45.5F, -11.5F}};
        }
        return new float[][] {
                {-16.0F, 0.0F, -16.0F, 32.0F, 2.5F, 32.0F},
                {-16.0F, 45.5F, -16.0F, 32.0F, 48.0F, 32.0F},
                {-16.0F, 2.5F, 28.5F, 32.0F, 45.5F, 31.5F},
                {-14.5F, 2.5F, -16.0F, -11.5F, 45.5F, 32.0F},
                {28.5F, 2.5F, -16.0F, 31.5F, 45.5F, 32.0F},
                {-16.0F, 2.5F, -14.5F, 32.0F, 45.5F, -11.5F}};
    }

    public static VoxelShape clippedCollision(int part, boolean doorOpen, Direction facing) {
        BlockPos off = worldOffsetOf(part, facing);
        int sx = off.getX() * 16;
        int sy = off.getY() * 16;
        int sz = off.getZ() * 16;
        VoxelShape shape = Shapes.empty();
        for (float[] cageBox : cageBoxes(doorOpen)) {
            float[] box = rotateBox(cageBox, facing);
            double x0 = Math.max(box[0], sx);
            double y0 = Math.max(box[1], sy);
            double z0 = Math.max(box[2], sz);
            double x1 = Math.min(box[3], sx + 16.0D);
            double y1 = Math.min(box[4], sy + 16.0D);
            double z1 = Math.min(box[5], sz + 16.0D);
            if (x1 > x0 && y1 > y0 && z1 > z0) {
                shape = Shapes.or(shape, Block.box(x0 - sx, y0 - sy, z0 - sz,
                        x1 - sx, y1 - sy, z1 - sz));
            }
        }
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MobilePrisonBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        if (type == GeneratedMod.MOBILE_PRISON_BLOCK_ENTITY.get()) {
            BlockEntityTicker<MobilePrisonBlockEntity> ticker = MobilePrisonBlockEntity::serverTick;
            return castTicker(ticker);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> BlockEntityTicker<T> castTicker(BlockEntityTicker<?> ticker) {
        return (BlockEntityTicker<T>) ticker;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }
}
