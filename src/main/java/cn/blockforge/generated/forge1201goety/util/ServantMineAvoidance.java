package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.projectiles.ScatterMine;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;

public final class ServantMineAvoidance {

    public static final double SCAN_RADIUS = 4.5D;

    public static final double PANIC_RADIUS = 2.5D;

    public static final double DESTINATION_CLEARANCE = 3.0D;

    private ServantMineAvoidance() {
    }

    @Nullable
    public static Vec3 nearestThreatMine(LivingEntity mob, double radius) {
        if (mob == null || mob.level().isClientSide) {
            return null;
        }
        Vec3 best = null;
        double bestSqr = Double.MAX_VALUE;
        for (ScatterMine mine : mob.level().getEntitiesOfClass(ScatterMine.class,
                mob.getBoundingBox().inflate(radius))) {
            if (!isThreat(mob, mine)) {
                continue;
            }
            double distance = mine.distanceToSqr(mob);
            if (distance < bestSqr) {
                bestSqr = distance;
                best = mine.position();
            }
        }
        return best;
    }

    @Nullable
    public static Vec3 safeDestination(LivingEntity mob, @Nullable Vec3 candidate, double step) {
        if (mob == null || candidate == null || mob.level().isClientSide) {
            return candidate;
        }
        AABB box = new AABB(candidate, candidate).inflate(DESTINATION_CLEARANCE);
        Vec3 push = Vec3.ZERO;
        int threats = 0;
        for (ScatterMine mine : mob.level().getEntitiesOfClass(ScatterMine.class, box)) {
            if (!isThreat(mob, mine)) {
                continue;
            }
            Vec3 away = mob.position().subtract(mine.position());
            away = new Vec3(away.x, 0.0D, away.z);
            if (away.lengthSqr() < 1.0E-4D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            }
            push = push.add(away.normalize());
            threats++;
        }
        if (threats == 0 || push.lengthSqr() < 1.0E-4D) {
            return candidate;
        }
        return mob.position().add(push.normalize().scale(Math.max(2.0D, step)));
    }

    @Nullable
    public static Vec3 chaseWaypoint(LivingEntity mob, @Nullable Vec3 targetPos, double step) {
        if (mob == null || targetPos == null || mob.level().isClientSide) {
            return null;
        }
        Vec3 forward = new Vec3(targetPos.x - mob.getX(), 0.0D, targetPos.z - mob.getZ());
        if (forward.lengthSqr() < 1.0E-4D) {
            return null;
        }
        Vec3 dir = forward.normalize();
        Vec3 side = new Vec3(-dir.z, 0.0D, dir.x);
        double lateral = 0.0D;
        boolean blocked = false;
        for (ScatterMine mine : mob.level().getEntitiesOfClass(ScatterMine.class,
                mob.getBoundingBox().inflate(SCAN_RADIUS))) {
            if (!isThreat(mob, mine)) {
                continue;
            }
            Vec3 toMine = new Vec3(mine.getX() - mob.getX(), 0.0D, mine.getZ() - mob.getZ());
            if (toMine.dot(dir) < -0.5D) {
                continue;
            }
            blocked = true;
            lateral += toMine.dot(side) >= 0.0D ? -1.0D : 1.0D;
        }
        if (!blocked) {
            return null;
        }
        if (lateral == 0.0D) {
            lateral = 1.0D;
        }
        Vec3 blended = dir.add(side.scale(Math.signum(lateral))).normalize()
                .scale(Math.max(2.0D, step));
        return mob.position().add(blended.x, 0.0D, blended.z);
    }

    private static boolean isThreat(LivingEntity mob, ScatterMine mine) {
        if (mine == null || !mine.isAlive()) {
            return false;
        }
        LivingEntity owner = mine.getOwner();
        if (owner == null) {
            return true;
        }
        if (owner == mob) {
            return false;
        }
        if (mob instanceof PlayerMobServant servant) {
            LivingEntity master = servant.getMasterOwner();
            if (master == null) {
                master = servant.getTrueOwner();
            }
            if (owner == master) {
                return false;
            }
        }
        return !mob.isAlliedTo(owner) && !owner.isAlliedTo(mob);
    }
}
