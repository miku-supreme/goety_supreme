package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.ScorchbackCombatMath;

public final class ScorchbackCombatMath {
    public static final int RAIN_FIRST_TICK = 14;
    public static final int RAIN_INTERVAL = 5;
    public static final int RAIN_VOLLEYS = 10;
    public static final int RAIN_SHOTS = 2;
    public static final int RAIN_LAST_TICK = RAIN_FIRST_TICK + (RAIN_VOLLEYS - 1) * RAIN_INTERVAL;
    public static final int ASCENT_TICKS = 6;

    public static final double ASCENT_SPEED = 1.0875D;
    public static final double DIVE_SPEED = 1.95D;

    public static final int RAIN_EXTRA_MIN = 2;
    public static final int RAIN_EXTRA_MAX = 3;

    public static final double RAIN_EXTRA_SCATTER_RADIUS = 3.0D;
    public static final int COMBO_HIT_1 = 11;
    public static final int COMBO_HIT_2 = 23;
    public static final int COMBO_SLAM = 46;
    public static final int CHARGE_COOLDOWN = 400;
    public static final int CHARGE_SPIKE_INTERVAL = 2;
    public static final int CHARGE_SPIKE_LANES = 5;

    public static final double CHARGE_KNOCKBACK_ORIGIN = 2.5D;
    public static final double CHARGE_KNOCKBACK_SPEED = 4.5D;
    public static final double CHARGE_KNOCKBACK_LIFT = 0.32D;

    public static final double COMBO_SLAM_RADIUS = 5.5D;

    public static final double SLAM_RADIUS = 6.0D;

    public static final float WALK_CLIP_TICKS = 12.0F;

    public static final float WALK_TICKS_PER_RADIAN = 0.5F;

    public static final float WALK_FOOTFALL_TICKS = WALK_CLIP_TICKS * 0.5F;

    public static int footfallIndex(float limbSwing) {
        return (int) Math.floor(limbSwing * WALK_TICKS_PER_RADIAN / WALK_FOOTFALL_TICKS);
    }

    public static int volleyAt(int tick) {
        int elapsed = tick - RAIN_FIRST_TICK;
        return elapsed >= 0 && tick <= RAIN_LAST_TICK && elapsed % RAIN_INTERVAL == 0
                ? elapsed / RAIN_INTERVAL : -1;
    }

    public static Vec3 ascentVelocity(float yaw, int volley, int shot) {
        double spread = 28.0D + (volley % 3) * 8.0D;
        double angle = Math.toRadians(yaw + (shot == 0 ? -spread : spread));
        double elevation = Math.toRadians(shot == 0 ? 66.0D : 74.0D);
        double horizontal = ASCENT_SPEED * Math.cos(elevation);
        return new Vec3(-Math.sin(angle) * horizontal, ASCENT_SPEED * Math.sin(elevation),
                Math.cos(angle) * horizontal);
    }

    public static Vec3 diveVelocity(Vec3 from, Vec3 aim) {
        Vec3 offset = aim.subtract(from);
        return offset.lengthSqr() < 1.0E-8D ? new Vec3(0.0D, -DIVE_SPEED, 0.0D)
                : offset.normalize().scale(DIVE_SPEED);
    }

    public static float yawTo(Vec3 from, Vec3 to, float fallback) {
        Vec3 d = to.subtract(from);
        return d.horizontalDistanceSqr() < 1.0E-8D ? fallback
                : (float) Math.toDegrees(Math.atan2(-d.x, d.z));
    }

    public static Vec3 forward(float yaw) {
        double angle = Math.toRadians(yaw);
        return new Vec3(-Math.sin(angle), 0.0D, Math.cos(angle));
    }

    public static Vec3 localToWorld(Vec3 local, Vec3 origin, float yaw, double size) {
        double angle = Math.toRadians(yaw);
        double c = Math.cos(angle), s = Math.sin(angle);
        return origin.add((local.x * c - local.z * s) * size, local.y * size,
                (local.x * s + local.z * c) * size);
    }

    public static Vec3 chargeSpikeOffset(int lane, int row) {
        double side = (lane - (CHARGE_SPIKE_LANES - 1) * 0.5D) * 0.48D;
        return new Vec3(side, 0.0D, -1.15D - ((row + lane) & 1) * 0.25D);
    }

    public static Vec3 scatterInCircle(Vec3 center, double radius, double angle, double factor) {
        double clamped = Math.max(0.0D, Math.min(1.0D, factor));
        double r = radius * Math.sqrt(clamped);
        return center.add(Math.cos(angle * Math.PI * 2.0D) * r, 0.0D,
                Math.sin(angle * Math.PI * 2.0D) * r);
    }

    private ScorchbackCombatMath() { }
}
