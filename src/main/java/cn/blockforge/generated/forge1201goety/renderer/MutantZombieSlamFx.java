package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.util.MutantZombieSlamArea;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieSlamFx;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.mixin.MutantZombieMixin;

public final class MutantZombieSlamFx {

    private static final int IMPACT_RINGS = 8;

    private MutantZombieSlamFx() {
    }

    public static void impactBurst(ServerLevel level, double x, double y, double z, double radius) {
        BlockState ground = groundStateBelow(level, x, y, z);
        BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, ground);
        for (int i = 0; i < IMPACT_RINGS; i++) {
            ServerParticleUtil.circularParticles(level, particles, x, y + 0.05D, z, (float) radius);
        }
    }

    public static BlockState groundStateBelow(ServerLevel level, double x, double y, double z) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(
                Mth.floor(x), Mth.floor(y + 0.1D), Mth.floor(z));
        for (int i = 0; i < 4; i++) {
            BlockState state = level.getBlockState(cursor);
            if (!state.isAir()) {
                return state;
            }
            cursor.move(0, -1, 0);
        }
        return level.getBlockState(BlockPos.containing(x, y - 0.1D, z));
    }
}
