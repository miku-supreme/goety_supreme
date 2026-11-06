package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class SerponOpenDoorServantGoal extends Goal {

    private final Mob mob;
    private final boolean closeDoor;
    private BlockPos doorPos = BlockPos.ZERO;
    private boolean hasDoor;
    private boolean passed;
    private float doorOpenDirX;
    private float doorOpenDirZ;

    private int forgetTime;

    public SerponOpenDoorServantGoal(Mob mob, boolean closeDoor) {
        this.mob = mob;
        this.closeDoor = closeDoor;
    }

    private boolean isDoorOpen() {
        if (!this.hasDoor) {
            return false;
        }
        BlockState state = this.mob.level().getBlockState(this.doorPos);
        if (!(state.getBlock() instanceof DoorBlock)) {
            this.hasDoor = false;
            return false;
        }
        return state.getValue(DoorBlock.OPEN);
    }

    private void setDoorOpen(boolean open) {
        if (!this.hasDoor) {
            return;
        }
        BlockState state = this.mob.level().getBlockState(this.doorPos);
        if (state.getBlock() instanceof DoorBlock door) {
            door.setOpen(this.mob, this.mob.level(), state, this.doorPos, open);
        }
    }

    @Override
    public boolean canUse() {
        if (!this.mob.horizontalCollision) {
            return false;
        }
        PathNavigation navigation = this.mob.getNavigation();
        if (!(navigation instanceof FlyingPathNavigation flying)) {
            return false;
        }
        Path path = flying.getPath();
        if (path == null || path.isDone() || !flying.canOpenDoors()) {
            return false;
        }
        for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
            Node node = path.getNode(i);
            this.doorPos = new BlockPos(node.x, node.y + 1, node.z);
            if (this.mob.distanceToSqr(this.doorPos.getX(), this.mob.getY(), this.doorPos.getZ()) > 2.25D) {
                continue;
            }
            this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level(), this.doorPos);
            if (this.hasDoor) {
                return true;
            }
        }
        this.doorPos = this.mob.blockPosition().above();
        this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level(), this.doorPos);
        return this.hasDoor;
    }

    @Override
    public boolean canContinueToUse() {
        return this.closeDoor && this.forgetTime > 0 && !this.passed;
    }

    @Override
    public void start() {
        this.passed = false;
        this.forgetTime = 20;
        this.setDoorOpen(true);
        this.doorOpenDirX = (float) ((double) this.doorPos.getX() + 0.5D - this.mob.getX());
        this.doorOpenDirZ = (float) ((double) this.doorPos.getZ() + 0.5D - this.mob.getZ());
    }

    @Override
    public void stop() {
        this.setDoorOpen(false);
    }

    @Override
    public void tick() {
        if (this.forgetTime > 0) {
            --this.forgetTime;
        }
        float dx = (float) ((double) this.doorPos.getX() + 0.5D - this.mob.getX());
        float dz = (float) ((double) this.doorPos.getZ() + 0.5D - this.mob.getZ());
        if (this.doorOpenDirX * dx + this.doorOpenDirZ * dz < 0.0F) {
            this.passed = true;
        }
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }
}
