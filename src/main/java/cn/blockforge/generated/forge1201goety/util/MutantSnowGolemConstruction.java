package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.util.MutantPotions;
import cn.blockforge.generated.goetysupreme.util.MutantSnowGolemConstruction;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class MutantSnowGolemConstruction {

    private static final ResourceLocation GOETY_SNOW_BRICKS =
            ResourceLocation.fromNamespaceAndPath("goety", "snow_bricks");

    private MutantSnowGolemConstruction() {
    }

    private static Block snowBricks() {
        return ForgeRegistries.BLOCKS.getValue(GOETY_SNOW_BRICKS);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) {
            return;
        }
        if (!MutantMonstersCompat.isLoaded()) {
            return;
        }
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack held = event.getItemStack();
        if (!MutantPotions.isMutantPotion(held) || player.isSecondaryUseActive()) {
            return;
        }
        BlockPos pumpkinPos = event.getPos();
        if (!level.getBlockState(pumpkinPos).is(Blocks.CARVED_PUMPKIN)) {
            return;
        }
        List<BlockPos> structure = findStructure(level, pumpkinPos);
        if (structure == null) {
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (level.isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        performSummon((ServerLevel) level, serverPlayer, event, pumpkinPos, structure);
    }

    private static List<BlockPos> findStructure(Level level, BlockPos pumpkinPos) {
        Block bricks = snowBricks();
        if (bricks == null) {
            return null;
        }
        BlockPos middleRow = pumpkinPos.below();
        BlockPos basePos = pumpkinPos.below(2);
        BlockState baseState = level.getBlockState(basePos);
        if (!level.getBlockState(middleRow).is(Blocks.SNOW_BLOCK) || !baseState.is(bricks)) {
            return null;
        }
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}) {
            Direction positive = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
            Direction negative = positive.getOpposite();
            BlockPos sideA = middleRow.relative(positive);
            BlockPos sideB = middleRow.relative(negative);
            if (level.getBlockState(sideA).is(Blocks.SNOW_BLOCK)
                    && level.getBlockState(sideB).is(Blocks.SNOW_BLOCK)) {
                List<BlockPos> blocks = new ArrayList<>(5);
                blocks.add(pumpkinPos);
                blocks.add(middleRow);
                blocks.add(sideA);
                blocks.add(sideB);
                blocks.add(basePos);
                return blocks;
            }
        }
        return null;
    }

    private static void performSummon(ServerLevel level, ServerPlayer player,
            PlayerInteractEvent.RightClickBlock event, BlockPos pumpkinPos, List<BlockPos> structure) {
        if (!MutantMonstersCompat.isLoaded()) {
            return;
        }
        if (!ServantLimits.hasRoom(level, player, ModEntityTypes.MUTANT_SNOW_GOLEM_SERVANT.get())) {
            return;
        }
        ItemStack held = event.getItemStack();
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
        for (BlockPos pos : structure) {
            level.destroyBlock(pos, false);
        }

        MutantSnowGolemServant servant = ModEntityTypes.MUTANT_SNOW_GOLEM_SERVANT.get().create(level);
        if (servant == null) {
            return;
        }
        servant.moveTo(pumpkinPos.getX() + 0.5D, pumpkinPos.getY(), pumpkinPos.getZ() + 0.5D,
                player.getYRot(), 0.0F);
        servant.setTrueOwner(player);
        servant.setWandering(false);
        servant.setStaying(false);
        servant.setPersistenceRequired();
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(pumpkinPos),
                MobSpawnType.MOB_SUMMONED, null, null);
        if (!level.addFreshEntity(servant)) {
            return;
        }

        double x = pumpkinPos.getX() + 0.5D;
        double y = pumpkinPos.getY() + 0.5D;
        double z = pumpkinPos.getZ() + 0.5D;
        level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 30,
                0.45D, 0.7D, 0.45D, 0.06D);
        level.sendParticles(ParticleTypes.POOF, x, y, z, 8,
                0.3D, 0.4D, 0.3D, 0.05D);
        level.playSound(null, pumpkinPos, SoundEvents.ZOMBIE_VILLAGER_CURE,
                SoundSource.PLAYERS, 1.0F, 1.15F);
        player.playSound(SoundEvents.SNOW_GOLEM_AMBIENT, 1.0F, 0.9F);
        CriteriaTriggers.SUMMONED_ENTITY.trigger(player, servant);
    }
}
