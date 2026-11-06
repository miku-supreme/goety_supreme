package cn.blockforge.generated.goetysupreme.item;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.sounds.SoundEvent;
import cn.blockforge.generated.goetysupreme.item.MusicDiscItem;

public class MusicDiscItem extends RecordItem {

    public MusicDiscItem(int comparatorOutput, Supplier<SoundEvent> sound,
            Properties properties, int lengthInTicks) {
        super(comparatorOutput, sound, properties, lengthInTicks);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.JUKEBOX) || state.getValue(JukeboxBlock.HAS_RECORD)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof JukeboxBlockEntity jukebox) {
            ItemStack held = context.getItemInHand();
            jukebox.setRecordWithoutPlaying(held.copyWithCount(1));
            if (jukebox.getItem(0).isEmpty()) {
                return InteractionResult.PASS;
            }
            level.setBlock(pos, state.setValue(JukeboxBlock.HAS_RECORD, true), 2);
            jukebox.startPlaying();
            if (context.getPlayer() != null) {
                context.getPlayer().awardStat(Stats.PLAY_RECORD);
            }
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos,
                    GameEvent.Context.of(context.getPlayer(), state));
            held.shrink(1);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
