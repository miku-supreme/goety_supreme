package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritTeleports;

public final class SakuraSpiritTeleports {
    private static final int TRIES = 16;

    @Nullable
    public static BlockPos ringPosAround(Mob mob, LivingEntity center, double rMin, double rMax,
            int yBase, int yJitter) {
        Level level = mob.level();
        RandomSource random = mob.getRandom();
        double span = Math.max(0.0D, rMax - rMin);
        int ySpread = Math.max(1, yJitter * 2 + 1);
        for (int i = 0; i < TRIES; i++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double radius = rMin + random.nextDouble() * span;
            double x = center.getX() + Math.cos(angle) * radius;
            double z = center.getZ() + Math.sin(angle) * radius;
            int y = yBase + random.nextInt(ySpread) - yJitter;
            BlockPos pos = BlockPos.containing(x, y, z);
            if (level.getBlockState(pos).isAir()
                    && level.getBlockState(pos.above()).isAir()
                    && level.getBlockState(pos.below()).isSolid()) {
                return pos;
            }
        }
        return null;
    }

    private SakuraSpiritTeleports() {
    }
}
