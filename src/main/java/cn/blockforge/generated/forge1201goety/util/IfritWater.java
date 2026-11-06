package cn.blockforge.generated.goetysupreme.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

final class IfritWater {

    private static final int SCAN_INTERVAL = 10;
    private static final int LAND_RANGE = 32;

    private IfritWater() {
    }

    static void tick(Ifrit mob) {
        if (!mob.isInWater()) {
            mob.waterSink = false;
            mob.waterScanCooldown = 0;
            return;
        }
        if (--mob.waterScanCooldown > 0) {
            if (mob.waterSink) {
                sinkStep(mob);
            }
            return;
        }
        mob.waterScanCooldown = SCAN_INTERVAL;
        BlockPos land = findDryLand(mob);
        if (land != null) {
            teleportToLand(mob, land);
            mob.waterSink = false;
            return;
        }
        mob.waterSink = true;
        sinkStep(mob);
    }

    private static void sinkStep(Ifrit mob) {
        Vec3 movement = mob.getDeltaMovement();
        mob.setDeltaMovement(movement.x, Math.min(movement.y, -0.05D), movement.z);
        mob.hasImpulse = true;
    }

    @Nullable
    private static BlockPos findDryLand(Ifrit mob) {
        BlockPos origin = mob.blockPosition();
        for (int radius = 2; radius <= LAND_RANGE; radius += 2) {
            for (int attempt = 0; attempt < 10; attempt++) {
                double angle = mob.getRandom().nextDouble() * Math.PI * 2.0D;
                int dx = (int) Math.round(Math.cos(angle) * radius);
                int dz = (int) Math.round(Math.sin(angle) * radius);
                BlockPos column = origin.offset(dx, 0, dz);
                for (int dy = 4; dy >= -6; dy--) {
                    BlockPos feet = column.offset(0, dy, 0);
                    if (isDryStandable(mob, feet)) {
                        return feet;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isDryStandable(Ifrit mob, BlockPos feet) {
        if (!mob.level().getFluidState(feet).isEmpty()
                || !mob.level().getFluidState(feet.above()).isEmpty()) {
            return false;
        }
        BlockPos below = feet.below();
        BlockState belowState = mob.level().getBlockState(below);
        if (belowState.getCollisionShape(mob.level(), below).isEmpty()) {
            return false;
        }
        BlockState feetState = mob.level().getBlockState(feet);
        if (!feetState.getCollisionShape(mob.level(), feet).isEmpty()) {
            return false;
        }
        BlockState headState = mob.level().getBlockState(feet.above());
        return headState.getCollisionShape(mob.level(), feet.above()).isEmpty();
    }

    private static void teleportToLand(Ifrit mob, BlockPos pos) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(ParticleTypes.PORTAL, mob.getX(), mob.getY(0.5D), mob.getZ(),
                40, 0.5D, 0.8D, 0.5D, 0.15D);
        mob.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        mob.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        server.sendParticles(ParticleTypes.PORTAL, mob.getX(), mob.getY(0.5D), mob.getZ(),
                40, 0.5D, 0.8D, 0.5D, 0.15D);
    }
}
