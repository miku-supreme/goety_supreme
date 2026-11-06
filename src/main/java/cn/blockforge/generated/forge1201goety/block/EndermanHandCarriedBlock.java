package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.EnderHandHeldBlockEntity;
import cn.blockforge.generated.goetysupreme.block.EndermanHandCarriedBlock;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocusSpell;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class EndermanHandCarriedBlock {

    public static final String HELD_POS_TAG = "GoetySupremeHeldBlockPos";

    public static void spawn(ServerLevel level, Player player, BlockState state, BlockPos pos) {
        CompoundTag data = player.getPersistentData();
        int seq = data.getInt(EnderHandHeldBlockEntity.SEQ_KEY) + 1;
        data.putInt(EnderHandHeldBlockEntity.SEQ_KEY, seq);
        EnderHandHeldBlockEntity.pickup(level, player, state, pos, seq);
    }

    public static EnderHandHeldBlockEntity takeForThrow(ServerLevel level, Player player) {
        EnderHandHeldBlockEntity carried = findHeld(level, player);
        if (carried != null) {
            carried.releaseForThrow();
        }
        return carried;
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getPersistentData().remove(EndermanHandFocusSpell.HELD_BLOCK_TAG);
            player.getPersistentData().remove(HELD_POS_TAG);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        sweepLegacyDisplay(event.getEntity());
        ensureDisplay(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        ensureDisplay(event.getEntity());
    }

    @SubscribeEvent
    public static void onChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        ensureDisplay(event.getEntity());
    }

    private static void ensureDisplay(Player player) {
        if (!MutantMonstersCompat.isLoaded()) {
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        ServerLevel level = serverPlayer.serverLevel();
        CompoundTag data = player.getPersistentData();
        if (!data.contains(EndermanHandFocusSpell.HELD_BLOCK_TAG)) {
            return;
        }
        if (findHeld(level, player) != null) {
            return; // 显示实体还在，重连后 MM 的 owner 解析会在其 tick 内自愈
        }
        BlockState state = NbtUtils.readBlockState(level.holderLookup(Registries.BLOCK),
                data.getCompound(EndermanHandFocusSpell.HELD_BLOCK_TAG));
        if (state.isAir()) {
            return;
        }
        BlockPos pos = data.contains(HELD_POS_TAG) ? BlockPos.of(data.getLong(HELD_POS_TAG)) : player.blockPosition();
        spawn(level, player, state, pos);
    }

    private static EnderHandHeldBlockEntity findHeld(ServerLevel level, Player player) {
        int seq = player.getPersistentData().getInt(EnderHandHeldBlockEntity.SEQ_KEY);
        for (EnderHandHeldBlockEntity carried : level.getEntitiesOfClass(
                EnderHandHeldBlockEntity.class, player.getBoundingBox().inflate(24.0D))) {
            if (carried.isHeld() && carried.getOwner() == player
                    && carried.getPersistentData().getInt(EnderHandHeldBlockEntity.SEQ_KEY) == seq) {
                return carried;
            }
        }
        return null;
    }

    private static void sweepLegacyDisplay(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.hasUUID(LEGACY_DISPLAY_UUID_TAG)) {
            return;
        }
        java.util.UUID displayId = data.getUUID(LEGACY_DISPLAY_UUID_TAG);
        data.remove(LEGACY_DISPLAY_UUID_TAG);
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        for (ServerLevel level : serverPlayer.server.getAllLevels()) {
            if (level.getEntity(displayId) instanceof net.minecraft.world.entity.item.ItemEntity leftover) {
                leftover.discard();
            }
        }
    }

    private static final String LEGACY_DISPLAY_UUID_TAG = "GoetySupremeHeldBlockDisplay";

    private EndermanHandCarriedBlock() {
    }
}
