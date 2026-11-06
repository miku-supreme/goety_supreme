package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;

public final class ProjectileIntercept {

    private static final int MAX_TICKS = 120;

    private static final int REFINE_STEPS = 30;

    private ProjectileIntercept() {
    }

    public static Vec3 solveForTarget(Vec3 from, LivingEntity target, double power, double inertia) {
        Vec3 center = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        Vec3 velocity = BallisticAim.capHorizontalSpeed(target.getDeltaMovement());
        Vec3 intercept = solve(from, center, velocity, power, inertia);
        double verticalLead = intercept.y - center.y;
        if (target.onGround()) {
            verticalLead = 0.0D;
        } else {
            verticalLead = Mth.clamp(verticalLead, -2.0D, 2.0D);
        }
        return new Vec3(intercept.x, center.y + verticalLead, intercept.z);
    }

    public static Vec3 solve(Vec3 from, Vec3 targetCenter, Vec3 targetVelocity, double power, double inertia) {
        double ticks = solveTicks(from, targetCenter, targetVelocity, power, inertia);
        return targetCenter.add(targetVelocity.scale(ticks));
    }

    public static double solveTicks(Vec3 from, Vec3 targetCenter, Vec3 targetVelocity, double power, double inertia) {
        if (power <= 0.0D || inertia <= 0.0D || inertia >= 1.0D) {
            return 0.0D;
        }
        if (gap(0.0D, from, targetCenter, targetVelocity, power, inertia) >= 0.0D) {
            return 0.0D;
        }
        double low = 0.0D;
        double high = MAX_TICKS;
        boolean crossed = false;
        double closestTicks = 0.0D;
        double closestGap = -Double.MAX_VALUE;
        for (int n = 1; n <= MAX_TICKS; ++n) {
            double current = gap(n, from, targetCenter, targetVelocity, power, inertia);
            if (current >= 0.0D) {
                low = n - 1.0D;
                high = n;
                crossed = true;
                break;
            }
            if (current > closestGap) {
                closestGap = current;
                closestTicks = n;
            }
        }
        if (!crossed) {
            return closestTicks;
        }
        for (int i = 0; i < REFINE_STEPS; ++i) {
            double mid = (low + high) * 0.5D;
            if (gap(mid, from, targetCenter, targetVelocity, power, inertia) < 0.0D) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return (low + high) * 0.5D;
    }

    public static double travel(double ticks, double power, double inertia) {
        double drift = inertia / (1.0D - inertia);
        double decay = (1.0D - Math.pow(inertia, ticks)) / (1.0D - inertia);
        return power * drift * (ticks - decay);
    }

    private static double gap(double ticks, Vec3 from, Vec3 targetCenter, Vec3 targetVelocity,
            double power, double inertia) {
        Vec3 predicted = targetCenter.add(targetVelocity.scale(ticks));
        return travel(ticks, power, inertia) - predicted.distanceTo(from);
    }
}
