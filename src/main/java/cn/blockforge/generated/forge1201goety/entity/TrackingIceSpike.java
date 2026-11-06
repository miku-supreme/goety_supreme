package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.IceSpike;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import cn.blockforge.generated.goetysupreme.entity.TrackingIceSpike;

public class TrackingIceSpike extends IceSpike {

    private static final double HOMING_STRENGTH = 0.22D;

    private static final double MIN_SPEED = 0.8D;

    private boolean homingLost;
    private double closestDistSqr = Double.MAX_VALUE;
    private LivingEntity trackedTarget;

    public TrackingIceSpike(EntityType<? extends TrackingIceSpike> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.isRemoved()) {
            return;
        }
        LivingEntity target = this.resolveTarget();
        if (target == null) {
            return;
        }
        if (target != this.trackedTarget) {
            this.trackedTarget = target;
            this.closestDistSqr = Double.MAX_VALUE;
        }
        double distSqr = this.distanceToSqr(target);
        double threshold = target.getBbWidth();
        if (this.closestDistSqr < Double.MAX_VALUE && distSqr > this.closestDistSqr
                && this.closestDistSqr < threshold * threshold) {
            this.homingLost = true;
            return;
        }
        this.closestDistSqr = Math.min(this.closestDistSqr, distSqr);
        Vec3 velocity = this.getDeltaMovement();
        double speed = velocity.length();
        if (speed < 1.0E-4D) {
            return;
        }
        Vec3 aim = new Vec3(target.getX() - this.getX(),
                target.getEyeY() - 0.2D - this.getY(),
                target.getZ() - this.getZ());
        if (aim.lengthSqr() < 1.0E-6D) {
            return;
        }
        Vec3 steered = velocity.normalize().scale(1.0D - HOMING_STRENGTH)
                .add(aim.normalize().scale(HOMING_STRENGTH));
        if (steered.lengthSqr() < 1.0E-6D) {
            return;
        }
        this.setDeltaMovement(steered.normalize().scale(Math.max(speed, MIN_SPEED)));
    }

    @NotNull
    private LivingEntity resolveTarget() {
        if (this.homingLost) {
            return null;
        }
        if (this.getOwner() instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive()) {
                return target;
            }
        }
        return null;
    }
}
