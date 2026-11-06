package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.util.SummonCircleBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.util.EntwinedLanternRitual;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.util.WildMageTempleSpawn;

public final class EntwinedLanternRitual {

    public static final int TEMPLE_RANGE = 24;

    private static final int GOAT_HORN_CALL_INDEX = 6;

    private EntwinedLanternRitual() {
    }

    public static boolean trySummon(ServerLevel level, Player player, BlockPos pos) {
        if (!WildMageTempleSpawn.hasTempleNear(level, player.blockPosition(), TEMPLE_RANGE)) {
            return false;
        }
        playSummoningCall(level, player, pos);
        WildMage mage = new WildMage(ModEntityTypes.WILD_MAGE.get(), level);
        mage.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                player.getYRot() + 180.0F, 0.0F);
        mage.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED,
                null, null);
        level.addFreshEntity(new SummonCircleBoss(level, pos, mage));
        return true;
    }

    private static void playSummoningCall(ServerLevel level, Player player, BlockPos pos) {
        Object variant = SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(GOAT_HORN_CALL_INDEX);
        if (variant instanceof Holder.Reference<?> ref && ref.value() instanceof SoundEvent call) {
            player.playSound(call, 16.0F, 1.0F);
            level.playSound(null, pos, call, SoundSource.NEUTRAL, 16.0F, 1.0F);
        }
    }
}
