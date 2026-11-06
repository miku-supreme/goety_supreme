package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;

public final class BallisticAim {

    public static final double ARROW_GRAVITY = 0.05D;

    public static final double ARROW_DRAG = 0.99D;

    public static final double ARROW_MIN_SPEED = 0.1D;

    public static final double MAX_LEAD_SPEED = 0.4D;

    public static final double MAX_LEAD_DISTANCE = 3.0D;

    public static final double ARROW_MAX_LEAD_DISTANCE = 8.0D;

    public record Solution(Vec3 velocity, int ticks) {
    }

    private BallisticAim() {
    }

    public static Vec3 leadVelocity(Entity target) {
        return target == null ? Vec3.ZERO : leadVelocity(target.getDeltaMovement());
    }

    public static Vec3 leadVelocity(Vec3 motion) {
        if (motion == null) {
            return Vec3.ZERO;
        }
        double vx = motion.x;
        double vz = motion.z;
        double speed = Math.sqrt(vx * vx + vz * vz);
        if (speed > MAX_LEAD_SPEED) {
            double scale = MAX_LEAD_SPEED / speed;
            vx *= scale;
            vz *= scale;
        }
        return new Vec3(vx, 0.0D, vz);
    }

    public static Vec3 capHorizontalSpeed(Vec3 motion) {
        if (motion == null) {
            return Vec3.ZERO;
        }
        Vec3 horizontal = leadVelocity(motion);
        return new Vec3(horizontal.x, motion.y, horizontal.z);
    }

    public static Vec3 leadOffset(Entity target, double ticks) {
        return target == null ? Vec3.ZERO : leadOffset(target.getDeltaMovement(), ticks);
    }

    public static Vec3 leadOffset(Vec3 motion, double ticks) {
        if (motion == null || ticks <= 0.0D) {
            return Vec3.ZERO;
        }
        Vec3 lead = leadVelocity(motion).scale(ticks);
        double distance = lead.horizontalDistance();
        if (distance > MAX_LEAD_DISTANCE) {
            lead = lead.scale(MAX_LEAD_DISTANCE / distance);
        }
        return lead;
    }

    public static Solution solveArrow(Vec3 from, Entity target, double minSpeed, double maxSpeed) {
        Vec3 center = target.getBoundingBox().getCenter();
        Vec3 motion = leadVelocity(target);
        Solution solution = solveFast(from, center, ARROW_GRAVITY, ARROW_DRAG, minSpeed, maxSpeed, 1, 60);
        for (int i = 0; i < ARROW_LEAD_ITERATIONS; i++) {
            Vec3 lead = motion.scale(solution.ticks());
            if (lead.horizontalDistance() > ARROW_MAX_LEAD_DISTANCE) {
                lead = lead.scale(ARROW_MAX_LEAD_DISTANCE / lead.horizontalDistance());
            }
            Solution next = solveFast(from, center.add(lead), ARROW_GRAVITY, ARROW_DRAG,
                    minSpeed, maxSpeed, 1, 60);
            boolean stable = next.ticks() == solution.ticks();
            solution = next;
            if (stable) {
                break;
            }
        }
        return solution;
    }

    private static final int ARROW_LEAD_ITERATIONS = 6;

    public static Solution solve(Vec3 from, Vec3 to, double gravity, double drag,
            double minSpeed, double maxSpeed) {
        return solve(from, to, gravity, drag, minSpeed, maxSpeed, 3, 60);
    }

    public static Solution solve(Vec3 from, Vec3 to, double gravity, double drag,
            double minSpeed, double maxSpeed, int minTicks, int maxTicks) {
        Vec3 delta = to.subtract(from);
        double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        double bestSpeed = Double.MAX_VALUE;
        int bestTicks = minTicks;
        double bestScale = 0.0D;
        double bestDrop = 0.0D;
        for (int n = minTicks; n <= maxTicks; n++) {
            double[] v = velocityFor(delta, horizontal, gravity, drag, n);
            double speed = v[3];
            if (speed < bestSpeed) {
                bestSpeed = speed;
                bestTicks = n;
                bestScale = v[0];
                bestDrop = v[1];
            }
        }
        return clamp(delta, bestScale, bestDrop, bestTicks, gravity, minSpeed, maxSpeed, bestSpeed);
    }

    public static Solution solveFast(Vec3 from, Vec3 to, double gravity, double drag,
            double minSpeed, double maxSpeed) {
        return solveFast(from, to, gravity, drag, minSpeed, maxSpeed, 3, 60);
    }

    public static Solution solveFast(Vec3 from, Vec3 to, double gravity, double drag,
            double minSpeed, double maxSpeed, int minTicks, int maxTicks) {
        Vec3 delta = to.subtract(from);
        double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        double easiestSpeed = Double.MAX_VALUE;
        int easiestTicks = minTicks;
        double easiestScale = 0.0D;
        double easiestDrop = 0.0D;
        for (int n = minTicks; n <= maxTicks; n++) {
            double[] v = velocityFor(delta, horizontal, gravity, drag, n);
            double speed = v[3];
            if (speed < easiestSpeed) {
                easiestSpeed = speed;
                easiestTicks = n;
                easiestScale = v[0];
                easiestDrop = v[1];
            }
            if (speed <= maxSpeed) {
                return clamp(delta, v[0], v[1], n, gravity, minSpeed, maxSpeed, speed);
            }
        }
        return clamp(delta, easiestScale, easiestDrop, easiestTicks, gravity, minSpeed, maxSpeed, easiestSpeed);
    }

    private static double[] velocityFor(Vec3 delta, double horizontal, double gravity, double drag, int n) {
        double s = (1.0D - Math.pow(drag, n)) / (1.0D - drag);
        double drop = gravity * (n - s) / (1.0D - drag);
        double vy = (delta.y + drop) / s;
        double vh = horizontal / s;
        double speed = Math.sqrt(vh * vh + vy * vy);
        return new double[] {s, drop, vy, speed};
    }

    private static Solution clamp(Vec3 delta, double scale, double drop, int ticks,
            double gravity, double minSpeed, double maxSpeed, double speed) {
        double vx = delta.x / scale;
        double vy = (delta.y + drop) / scale;
        double vz = delta.z / scale;
        double length = Math.sqrt(vx * vx + vy * vy + vz * vz);
        if (length < 1.0E-4D) {
            return new Solution(new Vec3(0.0D, Math.min(maxSpeed, Math.max(minSpeed, speed)), 0.0D), ticks);
        }
        double clamped = Math.min(maxSpeed, Math.max(minSpeed, length));
        double unit = clamped / length;
        return new Solution(new Vec3(vx * unit, vy * unit, vz * unit), ticks);
    }
}
