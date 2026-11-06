package cn.blockforge.generated.goetysupreme.util;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.FlightKnockbackRecovery;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class FlightKnockbackRecovery {

    private static final double VERTICAL_DAMPING = 0.6D;

    private static final double STEER_ACCELERATION = 0.3D;

    private static final double STEER_DAMPING = 0.6D;

    private static final double STEER_MIN_DISTANCE_SQR = 9.0D;

    private FlightKnockbackRecovery() {
    }

    public static void tick(LivingEntity mob, @Nullable LivingEntity target, @Nullable LivingEntity owner) {
        if (mob.isVehicle()) {
            return;
        }
        LivingEntity destination = target != null && target.isAlive() && target != mob
                ? target : owner;
        if (destination == null || destination == mob || !destination.isAlive()
                || destination.level() != mob.level()) {
            return;
        }
        if (mob.hurtTime <= 0) {
            return;
        }
        Vec3 velocity = mob.getDeltaMovement().multiply(1.0D, VERTICAL_DAMPING, 1.0D);
        Vec3 toDestination = new Vec3(destination.getX() - mob.getX(), 0.0D,
                destination.getZ() - mob.getZ());
        if (toDestination.horizontalDistanceSqr() > STEER_MIN_DISTANCE_SQR) {
            Vec3 direction = toDestination.normalize();
            velocity = velocity.add(
                    direction.x * STEER_ACCELERATION - velocity.x * STEER_DAMPING,
                    0.0D,
                    direction.z * STEER_ACCELERATION - velocity.z * STEER_DAMPING);
        }
        mob.setDeltaMovement(velocity);
    }
}
