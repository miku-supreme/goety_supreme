package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.blocks.entities.IWaystoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.MobilePrisonBindingEvents;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonBlock;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonFrameBlock;

public final class MobilePrisonBlockEntity extends BlockEntity implements IWaystoneBlock {

    public static final int DOOR_FULL_OPEN = 8;

    private static final int[] DOOR_PARTS = {
            MobilePrisonFrameBlock.DOOR_PART_BOTTOM,
            MobilePrisonFrameBlock.DOOR_PART_MIDDLE,
            MobilePrisonFrameBlock.DOOR_PART_TOP};

    private static final ThreadLocal<Boolean> REMOVING = ThreadLocal.withInitial(() -> false);

    private int doorStage;
    private int doorTarget;

    public MobilePrisonBlockEntity(BlockPos pos, BlockState state) {
        super(GeneratedMod.MOBILE_PRISON_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MobilePrisonBlockEntity be) {
        be.tickDoor(level, pos, state);
    }

    private void tickDoor(Level level, BlockPos pos, BlockState state) {
        if (doorStage == doorTarget) {
            return;
        }
        doorStage += doorTarget > doorStage ? 1 : -1;
        applyDoor(level, pos, state.getValue(MobilePrisonBlock.FACING), doorStage);
        if (doorStage == DOOR_FULL_OPEN) {
            playDoorSound(level, pos, state.getValue(MobilePrisonBlock.FACING), true);
        } else if (doorStage == 0) {
            playDoorSound(level, pos, state.getValue(MobilePrisonBlock.FACING), false);
        }
    }

    public void openDoor() {
        doorTarget = DOOR_FULL_OPEN;
        setChanged();
    }

    public void toggleDoor() {
        if (doorTarget > 0) {
            doorTarget = 0;
        } else {
            openDoor();
        }
        setChanged();
    }

    private void applyDoor(Level level, BlockPos anchor, Direction facing, int stage) {
        for (int part : DOOR_PARTS) {
            BlockPos cell = anchor.offset(MobilePrisonFrameBlock.worldOffsetOf(part, facing));
            BlockState cellState = level.getBlockState(cell);
            if (cellState.is(GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get())) {
                level.setBlock(cell, cellState.setValue(MobilePrisonFrameBlock.DOOR, stage),
                        Block.UPDATE_CLIENTS);
            }
        }
    }

    private static void playDoorSound(Level level, BlockPos pos, Direction facing, boolean open) {
        BlockPos door = pos.offset(MobilePrisonFrameBlock.worldOffsetOf(
                MobilePrisonFrameBlock.DOOR_PART_MIDDLE, facing));
        level.playSound(null, door.getX() + 0.5D, door.getY() + 0.5D, door.getZ() + 0.5D,
                open ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE,
                SoundSource.BLOCKS, 0.6F, open ? 0.85F : 1.1F);
    }

    @Override
    public Direction getDirection() {
        return getBlockState().getValue(MobilePrisonBlock.FACING);
    }

    @Override
    public GlobalPos getPosition() {
        return GlobalPos.of(level.dimension(), worldPosition);
    }

    @Override
    public int getSoulCost() {
        return 0;
    }

    @Override
    public boolean isShowBlock() {
        return false;
    }

    @Override
    public void setShowBlock(boolean show) {
    }

    @Nullable
    public static BlockPos findAnchor(Level level, BlockPos anyCell) {
        Block anchorBlock = GeneratedMod.MOBILE_PRISON_BLOCK.get();
        BlockState here = level.getBlockState(anyCell);
        if (here.is(anchorBlock)) {
            return anyCell.immutable();
        }
        if (!here.is(GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get())) {
            return null;
        }
        Direction facing = here.getValue(MobilePrisonFrameBlock.FACING);
        for (int part = 0; part <= 26; part++) {
            if (part == MobilePrisonBlock.ANCHOR_PART) {
                continue;
            }
            BlockPos candidate = anyCell.subtract(MobilePrisonFrameBlock.worldOffsetOf(part, facing));
            if (level.getBlockState(candidate).is(anchorBlock)) {
                return candidate.immutable();
            }
        }
        return null;
    }

    public static BlockPos interiorOf(BlockPos anchor) {
        return anchor.above();
    }

    public static void removeWholePrison(Level level, BlockPos anyCell, boolean dropAnchorItem) {
        removeWholePrison(level, anyCell, null, dropAnchorItem);
    }

    public static void removeWholePrison(Level level, BlockPos anyCell, @Nullable BlockState removedState,
            boolean dropAnchorItem) {
        if (level.isClientSide || REMOVING.get()) {
            return;
        }
        REMOVING.set(true);
        try {
            BlockPos anchor = findAnchor(level, anyCell);
            if (anchor != null) {
                BlockState anchorState = level.getBlockState(anchor);
                removeFrames(level, anchor, anchorState.getValue(MobilePrisonBlock.FACING));
                if (dropAnchorItem) {
                    level.destroyBlock(anchor, true);
                } else {
                    level.setBlock(anchor, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            } else {
                removeFrames(level, anyCell, removedState);
            }
        } finally {
            REMOVING.set(false);
        }
    }

    public static void removeFrames(Level level, BlockPos anchor, Direction facing) {
        if (level.isClientSide) {
            return;
        }
        boolean outer = REMOVING.get();
        REMOVING.set(true);
        try {
            Block frameBlock = GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get();
            for (int part = 0; part <= 26; part++) {
                if (part == MobilePrisonBlock.ANCHOR_PART) {
                    continue;
                }
                BlockPos cell = anchor.offset(MobilePrisonBlock.worldOffsetOf(part, facing));
                if (level.getBlockState(cell).is(frameBlock)) {
                    level.setBlock(cell, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        } finally {
            REMOVING.set(outer);
        }
    }

    private static void removeFrames(Level level, BlockPos anyCell, @Nullable BlockState removedState) {
        if (level.isClientSide) {
            return;
        }
        Block thisBlock = GeneratedMod.MOBILE_PRISON_BLOCK.get();
        BlockState here = level.getBlockState(anyCell);
        if (here.is(thisBlock)) {
            removeFrames(level, anyCell, here.getValue(MobilePrisonBlock.FACING));
            return;
        }
        BlockState info = here.is(GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get()) ? here : removedState;
        if (info == null || !info.is(GeneratedMod.MOBILE_PRISON_FRAME_BLOCK.get())) {
            return;
        }
        int part = info.getValue(MobilePrisonFrameBlock.PART);
        Direction facing = info.getValue(MobilePrisonFrameBlock.FACING);
        removeFrames(level, anyCell.subtract(MobilePrisonBlock.worldOffsetOf(part, facing)), facing);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("DoorStage", doorStage);
        tag.putInt("DoorTarget", doorTarget);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        doorStage = tag.getInt("DoorStage");
        doorTarget = tag.getInt("DoorTarget");
    }
}
