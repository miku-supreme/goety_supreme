package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.item.BlazingEmberItem;
import cn.blockforge.generated.goetysupreme.util.BlazingEmberRevive;
import cn.blockforge.generated.goetysupreme.item.CryptSoulJarItem;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.item.WildMageSoulJarItem;

public class BlazingEmberItem extends ReviveServantItem {

    private static final double OWNER_FALLBACK_RANGE = 32.0D;

    private static final long NO_JEROTES_WARN_INTERVAL = 100L;

    private static final String TAG_NO_JEROTES_WARN = "goety_supreme:ember_no_jerotes_warn";

    private static final String TAG_COOLDOWN_WARN = "goety_supreme:ember_cooldown_warn";

    public BlazingEmberItem() {
        super(new Item.Properties().fireResistant().stacksTo(1));
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();
        if (level.isClientSide() || entity.isRemoved() || !(level instanceof ServerLevel server)) {
            return false;
        }
        BlockPos firePos = fireAt(server, entity.blockPosition());
        if (firePos == null) {
            return false;
        }
        if (!JerotesVillageCompat.isJvillageServantLoadable()) {
            warnNoJerotes(server, stack, entity);
            return false;
        }
        Player owner = ownerOf(server, stack, entity);
        if (owner == null) {
            return false;
        }
        if (ReviveCooldown.isOnCooldown(owner, stack)) {
            warnCooldown(owner, entity);
            return false;
        }
        if (reviveAtFire(server, owner, stack, firePos)) {
            entity.discard();
        }
        return false;
    }

    private static void warnCooldown(Player player, ItemEntity entity) {
        CompoundTag data = entity.getPersistentData();
        long now = entity.level().getGameTime();
        if (data.contains(TAG_COOLDOWN_WARN)
                && now - data.getLong(TAG_COOLDOWN_WARN) < NO_JEROTES_WARN_INTERVAL) {
            return;
        }
        data.putLong(TAG_COOLDOWN_WARN, now);
        player.displayClientMessage(Component.translatable(
                "item.goety_supreme.blazing_ember.cooldown"), true);
    }

    private static Player ownerOf(ServerLevel server, ItemStack stack, ItemEntity entity) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("owner_name")) {
            Player named = server.getServer().getPlayerList().getPlayerByName(tag.getString("owner_name"));
            if (named != null && !named.isSpectator()) {
                return named;
            }
        }
        Player nearest = server.getNearestPlayer(entity, OWNER_FALLBACK_RANGE);
        return nearest != null && !nearest.isSpectator() ? nearest : null;
    }

    private static void warnNoJerotes(ServerLevel server, ItemStack stack, ItemEntity entity) {
        Player target = ownerOf(server, stack, entity);
        if (target == null) {
            return;
        }
        CompoundTag data = entity.getPersistentData();
        long now = server.getGameTime();
        if (data.contains(TAG_NO_JEROTES_WARN)
                && now - data.getLong(TAG_NO_JEROTES_WARN) < NO_JEROTES_WARN_INTERVAL) {
            return;
        }
        data.putLong(TAG_NO_JEROTES_WARN, now);
        target.displayClientMessage(Component.translatable(
                "item.goety_supreme.blazing_ember.no_jerotes"), true);
    }

    private static boolean reviveAtFire(ServerLevel server, Player player, ItemStack stack,
            BlockPos firePos) {
        return BlazingEmberRevive.reviveAtFire(server, player, stack, firePos);
    }

    private static BlockPos fireAt(Level level, BlockPos clicked) {
        if (isFire(level.getBlockState(clicked))) {
            return clicked;
        }
        BlockPos above = clicked.above();
        if (isFire(level.getBlockState(above))) {
            return above;
        }
        BlockPos below = clicked.below();
        if (isFire(level.getBlockState(below))) {
            return below;
        }
        return null;
    }

    private static boolean isFire(BlockState state) {
        return state.is(BlockTags.FIRE);
    }
}
