package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritServant;
import cn.blockforge.generated.goetysupreme.item.SakuraSpiritSpawnEgg;

public final class SakuraSpiritSpawnEgg extends ForgeSpawnEggItem {
    public SakuraSpiritSpawnEgg() {
        super(ModEntityTypes.SAKURA_SPIRIT, 16758213, 16738740, new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide && player != null && player.isShiftKeyDown()) {
            if (spawnSakuraServant(context, (ServerLevel) level, player)) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    private static boolean spawnSakuraServant(UseOnContext context, ServerLevel level, Player player) {
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        SakuraSpiritServant servant = (SakuraSpiritServant) ModEntityTypes.SAKURA_SPIRIT_SERVANT.get().create(level);
        if (servant == null) {
            return false;
        }
        servant.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        servant.setOwnerId(player.getUUID());
        servant.setTrueOwner(player);
        servant.setHostile(false);
        servant.setPersistenceRequired();
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.SPAWN_EGG, null,
                (CompoundTag) null);
        level.addFreshEntity(servant);
        ItemStack held = context.getItemInHand();
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
        return true;
    }
}
