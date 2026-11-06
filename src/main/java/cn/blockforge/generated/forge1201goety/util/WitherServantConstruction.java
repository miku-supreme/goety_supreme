package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.utils.SEHelper;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.util.WitherServantConstruction;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherServantConstruction {

    private WitherServantConstruction() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled() || event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) {
            return;
        }
        Player player = event.getEntity();
        ItemStack held = event.getItemStack();
        if (held.isEmpty() || held.getItem() != ModItems.ANIMATION_CORE.get()) {
            return;
        }

        Level level = event.getLevel();
        Structure structure = findStructure(level, event.getPos());
        if (structure == null) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)
                || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!SEHelper.hasResearch(serverPlayer, ResearchList.FORBIDDEN)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("info.goety_supreme.wither_servant.requires_forbidden_scroll"), true);
            level.playSound(null, event.getPos(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 0.5F, 0.55F);
            return;
        }

        summon(serverLevel, serverPlayer, held, structure);
    }

    private static Structure findStructure(Level level, BlockPos clicked) {
        for (int dy = -1; dy <= 1; ++dy) {
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dz = -1; dz <= 1; ++dz) {
                    BlockPos body = clicked.offset(dx, dy, dz);
                    Structure xAxis = match(level, body, Direction.EAST, Direction.WEST);
                    if (xAxis != null && xAxis.blocks().contains(clicked)) {
                        return xAxis;
                    }
                    Structure zAxis = match(level, body, Direction.SOUTH, Direction.NORTH);
                    if (zAxis != null && zAxis.blocks().contains(clicked)) {
                        return zAxis;
                    }
                }
            }
        }
        return null;
    }

    private static Structure match(Level level, BlockPos body, Direction positive, Direction negative) {
        BlockPos sideA = body.relative(positive);
        BlockPos sideB = body.relative(negative);
        BlockPos stem = body.below();
        BlockPos skullCenter = body.above();
        BlockPos skullA = sideA.above();
        BlockPos skullB = sideB.above();

        Block soulSand = AstralSetDropin.GRAVE_SOUL_SAND.get();
        Block skull = AstralSetDropin.ASTRAL_WITHER_SKULL.get();
        if (!level.getBlockState(body).is(soulSand)
                || !level.getBlockState(sideA).is(soulSand)
                || !level.getBlockState(sideB).is(soulSand)
                || !level.getBlockState(stem).is(soulSand)
                || !level.getBlockState(skullCenter).is(skull)
                || !level.getBlockState(skullA).is(skull)
                || !level.getBlockState(skullB).is(skull)) {
            return null;
        }
        return new Structure(stem, List.of(body, sideA, sideB, stem, skullCenter, skullA, skullB));
    }

    private static void summon(ServerLevel level, ServerPlayer player, ItemStack core, Structure structure) {
        if (!ServantLimits.hasRoom(level, player, ModEntityTypes.WITHER_SERVANT.get())) {
            return;
        }
        WitherServant servant = ModEntityTypes.WITHER_SERVANT.get().create(level);
        if (servant == null) {
            return;
        }

        BlockPos spawnPos = structure.spawnPos();
        servant.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY() + 0.05D, spawnPos.getZ() + 0.5D,
                player.getYRot(), 0.0F);
        servant.setTrueOwner(player);
        servant.setWandering(false);
        servant.setStaying(false);
        servant.setPersistenceRequired();
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                MobSpawnType.MOB_SUMMONED, null, null);

        for (BlockPos pos : structure.blocks()) {
            level.destroyBlock(pos, false);
        }
        if (!level.addFreshEntity(servant)) {
            return;
        }
        if (!player.getAbilities().instabuild) {
            core.shrink(1);
        }

        double x = spawnPos.getX() + 0.5D;
        double y = spawnPos.getY() + 1.5D;
        double z = spawnPos.getZ() + 0.5D;
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 48,
                1.1D, 1.4D, 1.1D, 0.08D);
        level.sendParticles(ParticleTypes.POOF, x, y, z, 24,
                0.8D, 1.0D, 0.8D, 0.06D);
        level.playSound(null, spawnPos, SoundEvents.WITHER_SPAWN,
                SoundSource.HOSTILE, 1.5F, 1.0F);
        CriteriaTriggers.SUMMONED_ENTITY.trigger(player, servant);
    }

    private record Structure(BlockPos spawnPos, List<BlockPos> blocks) {
    }
}
