package cn.blockforge.generated.goetysupreme.compat;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.config.MobsConfig;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.entity.EnderStowawayServant;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;

public final class ServantTeleportHelper {
    private static final double PLAYER_TELEPORT_DISTANCE_SQR = 16.0D * 16.0D;
    private static final double MOB_TELEPORT_DISTANCE_SQR = 32.0D * 32.0D;
    private static final double MOB_LINE_OF_SIGHT_TELEPORT_DISTANCE_SQR = 8.0D * 8.0D;
    private static final int TELEPORT_CHECK_INTERVAL = 10;
    private static final int[][] OWNER_OFFSETS = {
            {0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1},
            {2, 0}, {-2, 0}, {0, 2}, {0, -2},
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {-1, 2}, {1, -2}, {-1, -2},
            {3, 0}, {-3, 0}, {0, 3}, {0, -3},
            {3, 1}, {3, -1}, {-3, 1}, {-3, -1},
            {1, 3}, {-1, 3}, {1, -3}, {-1, -3},
            {2, 2}, {2, -2}, {-2, 2}, {-2, -2}
    };

    private ServantTeleportHelper() {
    }

    public static void tick(Mob mob) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved()
                || mob.tickCount % TELEPORT_CHECK_INTERVAL != 0) {
            return;
        }
        LivingEntity owner = teleportTargetOwner(mob);
        if (owner != null) {
            tryTeleportNearOwner(mob, owner, (ServerLevel) owner.level());
        }
    }

    public static LivingEntity teleportTargetOwner(Mob mob) {
        if (!mob.isAlive() || mob.isRemoved()
                || !(mob instanceof IServant servant) || !servant.isFollowing() || servant.isCommanded()
                || mob.getTarget() != null || mob.isLeashed() || mob.isPassenger() || mob.isNoAi()) {
            return null;
        }

        LivingEntity owner = servant.getTrueOwner();
        if (!isTeleportableOwner(mob, owner) || !owner.isAlive() || owner.isSpectator()
                || !(owner.level() instanceof ServerLevel targetLevel)) {
            return null;
        }
        if (owner instanceof Player) {
            boolean configAllows = Boolean.TRUE.equals(MobsConfig.ServantTeleport.get())
                    || isEnderServant(mob);
            if (!configAllows
                    || mob.level() != targetLevel
                    || mob.distanceToSqr(owner) < PLAYER_TELEPORT_DISTANCE_SQR) {
                return null;
            }
        } else if (mob.level() == targetLevel
                && mob.distanceToSqr(owner) < MOB_TELEPORT_DISTANCE_SQR
                && (mob.hasLineOfSight(owner)
                || mob.distanceToSqr(owner) < MOB_LINE_OF_SIGHT_TELEPORT_DISTANCE_SQR)) {
            return null;
        }
        return owner;
    }

    private static boolean isEnderServant(Mob mob) {
        return mob instanceof EndermanServant
                || mob instanceof EnderStowawayServant
                || mob instanceof MutantEndermanServant;
    }

    private static boolean isTeleportableOwner(Mob mob, LivingEntity owner) {
        return owner instanceof Player
                || mob instanceof SnowGolemServant && MutantMonstersCompat.isLoaded()
                        && owner instanceof MutantSnowGolemServant;
    }

    public static boolean isDangerousLanding(Level level, Mob mob, double x, double y, double z) {
        AABB box = mob.getDimensions(mob.getPose()).makeBoundingBox(x, y, z);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int bx = Mth.floor(box.minX); bx <= Mth.floor(box.maxX); bx++) {
            for (int by = Mth.floor(box.minY); by <= Mth.floor(box.maxY); by++) {
                for (int bz = Mth.floor(box.minZ); bz <= Mth.floor(box.maxZ); bz++) {
                    pos.set(bx, by, bz);
                    if (isDangerousBlock(level.getBlockState(pos))) {
                        return true;
                    }
                }
            }
        }
        pos.set(Mth.floor(x), Mth.floor(y) - 1, Mth.floor(z));
        return isDangerousBlock(level.getBlockState(pos));
    }

    private static boolean isDangerousBlock(BlockState state) {
        if (state.getFluidState().is(FluidTags.LAVA)) {
            return true;
        }
        return state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE)
                || state.is(Blocks.MAGMA_BLOCK) || state.is(Blocks.CACTUS)
                || state.is(Blocks.COBWEB) || state.is(Blocks.SWEET_BERRY_BUSH)
                || state.is(Blocks.POWDER_SNOW) || state.is(Blocks.CAMPFIRE)
                || state.is(Blocks.SOUL_CAMPFIRE) || state.is(Blocks.WITHER_ROSE);
    }

    public static BlockPos findLanding(Mob mob, LivingEntity center, double minRadius, double maxRadius,
            int baseY, int verticalRange) {
        Level level = mob.level();
        for (int attempt = 0; attempt < 24; attempt++) {
            double angle = mob.getRandom().nextDouble() * Math.PI * 2.0D;
            double radius = minRadius + mob.getRandom().nextDouble() * (maxRadius - minRadius);
            BlockPos top = BlockPos.containing(center.getX() + Math.cos(angle) * radius,
                    baseY + verticalRange, center.getZ() + Math.sin(angle) * radius);
            if (!level.hasChunkAt(top)) {
                continue;
            }
            for (int dy = 0; dy <= verticalRange * 2; dy++) {
                BlockPos pos = top.below(dy);
                if (pos.getY() <= level.getMinBuildHeight() || pos.getY() + mob.getBbHeight()
                        >= level.getMaxBuildHeight()) {
                    continue;
                }
                Vec3 destination = Vec3.atBottomCenterOf(pos);
                AABB box = mob.getDimensions(mob.getPose()).makeBoundingBox(destination);
                if (level.getWorldBorder().isWithinBounds(box)
                        && level.getBlockState(pos.below()).blocksMotion()
                        && level.noCollision(mob, box) && !level.containsAnyLiquid(box)
                        && !isDangerousLanding(level, mob, destination.x, destination.y,
                                destination.z)) {
                    return pos;
                }
            }
        }
        return null;
    }

    private static boolean tryTeleportNearOwner(Mob mob, LivingEntity owner, ServerLevel targetLevel) {
        BlockPos ownerPos = owner.blockPosition();
        for (int[] offset : OWNER_OFFSETS) {
            for (int offsetY = 0; offsetY >= -1; offsetY--) {
                BlockPos destination = ownerPos.offset(offset[0], offsetY, offset[1]);
                if (teleportToSafePosition(mob, targetLevel, destination)) {
                    return true;
                }
            }
            BlockPos upperDestination = ownerPos.offset(offset[0], 1, offset[1]);
            if (teleportToSafePosition(mob, targetLevel, upperDestination)) {
                return true;
            }
        }
        return false;
    }

    private static boolean teleportToSafePosition(Mob mob, ServerLevel targetLevel, BlockPos destination) {
        if (!targetLevel.isInWorldBounds(destination)
                || !targetLevel.getWorldBorder().isWithinBounds(destination)) {
            return false;
        }

        BlockPos support = destination.below();
        if (!targetLevel.loadedAndEntityCanStandOn(support, mob)
                || targetLevel.getBlockState(support).getBlock() instanceof LeavesBlock) {
            return false;
        }

        double x = destination.getX() + 0.5D;
        double y = destination.getY();
        double z = destination.getZ() + 0.5D;
        AABB targetBounds = mob.getBoundingBox().move(x - mob.getX(), y - mob.getY(), z - mob.getZ());
        if (!targetLevel.noCollision(mob, targetBounds)) {
            return false;
        }
        if (isDangerousLanding(targetLevel, mob, x, y, z)) {
            return false;
        }

        mob.getNavigation().stop();
        mob.fallDistance = 0.0F;
        mob.setDeltaMovement(0.0D, 0.0D, 0.0D);
        return mob.teleportTo(targetLevel, x, y, z, Set.of(), mob.getYRot(), mob.getXRot());
    }
}
