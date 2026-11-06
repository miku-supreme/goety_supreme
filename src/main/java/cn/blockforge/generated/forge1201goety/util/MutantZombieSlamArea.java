package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.util.MutantZombieSlamArea;
import cn.blockforge.generated.goetysupreme.mixin.MutantZombieMixin;

public final class MutantZombieSlamArea {

    public static final double RADIUS = 2.0D;

    private static final double HEIGHT = 2.0D;

    private MutantZombieSlamArea() {
    }

    public static List<LivingEntity> victims(Entity slamer, double centerX, double centerZ,
            double baseY, Predicate<LivingEntity> attackable) {
        AABB box = new AABB(centerX - RADIUS, baseY - 1.0D, centerZ - RADIUS,
                centerX + RADIUS, baseY + HEIGHT, centerZ + RADIUS);
        List<LivingEntity> victims = new ArrayList<>();
        for (LivingEntity living : slamer.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (living == slamer || !living.isAlive() || !attackable.test(living)) {
                continue;
            }
            double dx = living.getX() - centerX;
            double dz = living.getZ() - centerZ;
            if (dx * dx + dz * dz > RADIUS * RADIUS) {
                continue;
            }
            victims.add(living);
        }
        return victims;
    }

    public static float rollDamage(RandomSource random) {
        return (float) MutantZombieServantConfig.randomBetween(
                MutantZombieServantConfig.SLAM_GROUND_DAMAGE_FIRST_MIN,
                MutantZombieServantConfig.SLAM_GROUND_DAMAGE_FIRST_MAX, random);
    }

    public static void pushAway(Entity entity, double centerX, double centerZ,
            double horizontal, double vertical) {
        double x = entity.getX() - centerX;
        double z = entity.getZ() - centerZ;
        double length = Math.sqrt(x * x + z * z);
        if (length < 1.0E-4D) {
            length = 1.0D;
        }
        entity.setDeltaMovement(x / length * horizontal, vertical, z / length * horizontal);
        entity.hurtMarked = true;
    }
}
