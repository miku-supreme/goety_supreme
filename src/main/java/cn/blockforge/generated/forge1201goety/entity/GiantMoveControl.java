package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.shapes.VoxelShape;

final class GiantMoveControl extends MoveControl {

    private float trueSpeed;
    private float additionalRot;

    GiantMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.STRAFE) {
            float speed = (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float wanted = (float) this.speedModifier * speed;
            float forward = this.strafeForwards;
            float right = this.strafeRight;
            float length = Mth.sqrt(forward * forward + right * right);
            this.trueSpeed = Mth.lerp(0.1F, this.trueSpeed, wanted);
            if (length < 1.0F) {
                length = 1.0F;
            }
            length = this.trueSpeed / length;
            forward *= length;
            right *= length;
            float sin = Mth.sin(this.mob.getYRot() * ((float) Math.PI / 180.0F));
            float cos = Mth.cos(this.mob.getYRot() * ((float) Math.PI / 180.0F));
            float strafeX = forward * cos - right * sin;
            float strafeZ = right * cos + forward * sin;
            if (!this.isWalkable(strafeX, strafeZ)) {
                this.strafeForwards = 1.0F;
                this.strafeRight = 0.0F;
            }
            this.mob.setSpeed(this.trueSpeed);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = MoveControl.Operation.WAIT;
        } else if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if (d3 < 2.5000003E-7F) {
                this.trueSpeed = Mth.lerp(0.1F, this.trueSpeed, 0.0F);
                this.mob.setZza(this.trueSpeed);
                if (this.additionalRot > 0.0F) {
                    this.additionalRot -= 0.1F;
                }
                return;
            }
            this.trueSpeed = Mth.lerp(0.1F, this.trueSpeed,
                    (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            float targetYaw = (float) (Mth.atan2(d1, d0) * (180.0D / Math.PI)) - 90.0F;
            if (this.additionalRot < 1.0F) {
                this.additionalRot += 0.1F;
            }
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), targetYaw, 5.0F + this.additionalRot * 5.0F));
            this.mob.setSpeed(this.trueSpeed);
            BlockPos pos = this.mob.blockPosition();
            BlockState state = this.mob.level().getBlockState(pos);
            VoxelShape shape = state.getCollisionShape(this.mob.level(), pos);
            if (d2 > (double) this.mob.getStepHeight() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())
                    || !shape.isEmpty() && this.mob.getY() < shape.max(Direction.Axis.Y) + (double) pos.getY()
                    && !state.is(BlockTags.DOORS) && !state.is(BlockTags.FENCES)) {
                this.mob.getJumpControl().jump();
                this.operation = MoveControl.Operation.JUMPING;
            }
        } else if (this.operation == MoveControl.Operation.JUMPING) {
            this.trueSpeed = Mth.lerp(0.1F, this.trueSpeed,
                    (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            this.mob.setSpeed(this.trueSpeed);
            if (this.mob.onGround()) {
                this.operation = MoveControl.Operation.WAIT;
            }
        } else {
            this.trueSpeed = Mth.lerp(0.1F, this.trueSpeed, 0.0F);
            this.mob.setZza(this.trueSpeed);
            if (this.additionalRot > 0.0F) {
                this.additionalRot -= 0.1F;
            }
        }
    }

    private boolean isWalkable(float x, float z) {
        PathNavigation navigation = this.mob.getNavigation();
        NodeEvaluator evaluator = navigation.getNodeEvaluator();
        return evaluator.getBlockPathType(this.mob.level(),
                Mth.floor(this.mob.getX() + (double) x), this.mob.getBlockY(),
                Mth.floor(this.mob.getZ() + (double) z)) == BlockPathTypes.WALKABLE;
    }

    public float rotlerp(float current, float target, float maxDelta) {
        float delta = Mth.wrapDegrees(target - current);
        if (delta > maxDelta) {
            delta = maxDelta;
        }
        if (delta < -maxDelta) {
            delta = -maxDelta;
        }
        float result = current + delta;
        if (result < 0.0F) {
            result += 360.0F;
        } else if (result > 360.0F) {
            result -= 360.0F;
        }
        return result;
    }
}
