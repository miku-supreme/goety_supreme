package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.entities.neutral.DrownedNecromancer;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.renderer.SoulJarReviveFx;

public final class SoulJarReviveFx {

    private static final int PUFF_LOOPS = 8;

    private SoulJarReviveFx() {
    }

    public static void play(ServerLevel server, LivingEntity servant, Player player) {
        if (server == null || servant == null) {
            return;
        }
        for (int i = 0; i < PUFF_LOOPS; ++i) {
            ServerParticleUtil.addParticlesAroundSelf(server, ParticleTypes.SCULK_SOUL, servant);
            ServerParticleUtil.addParticlesAroundSelf(server, ParticleTypes.POOF, servant);
        }
        servant.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.5F);
        if (servant instanceof DrownedNecromancer drowned) {
            drowned.playSound(ModSounds.DROWNED_NECROMANCER_AMBIENT.get(), 2.0F, 0.5F);
        } else if (servant instanceof AbstractNecromancer necromancer) {
            necromancer.playSound(ModSounds.NECROMANCER_LAUGH.get(), 2.0F, 0.5F);
        }
    }
}
