package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;
import cn.blockforge.generated.goetysupreme.api.VitalityPumpkinHead;

public final class VitalityPumpkinConstructions {
    private static final double OWNER_RANGE = 5.0D;

    private static final ResourceLocation GOETY_SNOW_BRICKS =
            ResourceLocation.fromNamespaceAndPath("goety", "snow_bricks");
    private static final ResourceLocation GOETY_FREEZING_LAMP =
            ResourceLocation.fromNamespaceAndPath("goety", "freezing_lamp");

    private static BlockPattern snowGolemFull;
    private static BlockPattern ironGolemFull;
    private static BlockPattern furnaceGolemFull;
    private static BlockPattern prismarineCoreFull;
    private static BlockPattern iceGolemFull;
    private static BlockPattern giantSnowGolemFull;

    private VitalityPumpkinConstructions() {
    }

    public static void trySummonServant(Level level, BlockPos pumpkinPos) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        BlockPattern.BlockPatternMatch snow = snowPattern().find(serverLevel, pumpkinPos);
        if (snow != null) {
            summonSnowGolemServant(serverLevel, snow);
            return;
        }
        BlockPattern.BlockPatternMatch iron = ironPattern().find(serverLevel, pumpkinPos);
        if (iron != null) {
            summonIronGolemServant(serverLevel, iron);
            return;
        }
        BlockPattern.BlockPatternMatch furnace = furnacePattern().find(serverLevel, pumpkinPos);
        if (furnace != null) {
            summonFurnaceGolemServant(serverLevel, furnace);
            return;
        }
        BlockPattern.BlockPatternMatch prismarine = prismarineCorePattern().find(serverLevel, pumpkinPos);
        if (prismarine != null) {
            summonPrismarineSoldierServant(serverLevel, prismarine);
            return;
        }
        BlockPattern.BlockPatternMatch ice = icePattern().find(serverLevel, pumpkinPos);
        if (ice != null) {
            summonIceGolemServant(serverLevel, ice);
            return;
        }
        BlockPattern giantPattern = giantSnowPattern();
        if (giantPattern != null) {
            BlockPattern.BlockPatternMatch giantSnow = giantPattern.find(serverLevel, pumpkinPos);
            if (giantSnow != null) {
                summonGiantSnowGolemServant(serverLevel, giantSnow);
            }
        }
    }

    private static Block goetyBlock(ResourceLocation id) {
        return ForgeRegistries.BLOCKS.getValue(id);
    }

    private static BlockPattern snowPattern() {
        if (snowGolemFull == null) {
            snowGolemFull = BlockPatternBuilder.start()
                    .aisle("^", "#", "#")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
                    .build();
        }
        return snowGolemFull;
    }

    private static BlockPattern ironPattern() {
        if (ironGolemFull == null) {
            ironGolemFull = BlockPatternBuilder.start()
                    .aisle("~^~", "###", "~#~")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                    .where('~', BlockInWorld.hasState(net.minecraft.world.level.block.state.BlockState::isAir))
                    .build();
        }
        return ironGolemFull;
    }

    private static BlockPattern furnacePattern() {
        if (furnaceGolemFull == null) {
            furnaceGolemFull = BlockPatternBuilder.start()
                    .aisle("~^~", "#F#", "~#~")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('F', BlockInWorld.hasState(state ->
                            state.is(GeneratedMod.REINFORCED_BLAST_FURNACE_BLOCK.get())))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                    .where('~', BlockInWorld.hasState(net.minecraft.world.level.block.state.BlockState::isAir))
                    .build();
        }
        return furnaceGolemFull;
    }

    private static BlockPattern prismarineCorePattern() {
        if (prismarineCoreFull == null) {
            prismarineCoreFull = BlockPatternBuilder.start()
                    .aisle("~^~", "#C#", "~#~")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('C', BlockInWorld.hasState(state ->
                            state.is(GeneratedMod.PRISMARINE_CORE_BLOCK.get())))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.PRISMARINE_BRICKS)))
                    .where('~', BlockInWorld.hasState(net.minecraft.world.level.block.state.BlockState::isAir))
                    .build();
        }
        return prismarineCoreFull;
    }

    private static BlockPattern icePattern() {
        if (iceGolemFull == null) {
            iceGolemFull = BlockPatternBuilder.start()
                    .aisle("~^~", "PBP", "~B~")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.PACKED_ICE)))
                    .where('B', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.BLUE_ICE)))
                    .where('~', BlockInWorld.hasState(net.minecraft.world.level.block.state.BlockState::isAir))
                    .build();
        }
        return iceGolemFull;
    }

    private static BlockPattern giantSnowPattern() {
        Block bricks = goetyBlock(GOETY_SNOW_BRICKS);
        Block lamp = goetyBlock(GOETY_FREEZING_LAMP);
        if (bricks == null || lamp == null) {
            return null;
        }
        if (giantSnowGolemFull == null) {
            giantSnowGolemFull = BlockPatternBuilder.start()
                    .aisle("~~^~~", "sB0Bs", "~BBB~", "~~s~~")
                    .where('^', BlockInWorld.hasState(state -> state.getBlock() instanceof VitalityPumpkinHead))
                    .where('s', BlockInWorld.hasState(BlockStatePredicate.forBlock(bricks)))
                    .where('B', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
                    .where('0', BlockInWorld.hasState(BlockStatePredicate.forBlock(lamp)))
                    .where('~', BlockInWorld.hasState(net.minecraft.world.level.block.state.BlockState::isAir))
                    .build();
        }
        return giantSnowGolemFull;
    }

    private static void summonSnowGolemServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        BlockPos feet = match.getBlock(0, 2, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.SNOW_GOLEM_SERVANT.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        SnowGolemServant servant = ModEntityTypes.SNOW_GOLEM_SERVANT.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.SNOWFLAKE, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                20, 0.35D, 0.6D, 0.35D, 0.03D);
        level.playSound(null, feet, SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.BLOCKS, 1.0F, 1.15F);
        triggerSummon(owner, servant);
    }

    private static void summonIronGolemServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        BlockPos feet = match.getBlock(1, 2, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.IRON_GOLEM_SERVANT.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        IronGolemServant servant = ModEntityTypes.IRON_GOLEM_SERVANT.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.CLOUD, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                20, 0.45D, 0.9D, 0.45D, 0.05D);
        level.playSound(null, feet, SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.BLOCKS, 1.0F, 0.9F);
        triggerSummon(owner, servant);
    }

    private static void summonFurnaceGolemServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        BlockPos feet = match.getBlock(1, 2, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.FURNACE_GOLEM.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        FurnaceGolem servant = ModEntityTypes.FURNACE_GOLEM.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.FLAME, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                24, 0.45D, 0.9D, 0.45D, 0.04D);
        level.playSound(null, feet, SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.BLOCKS, 1.0F, 0.8F);
        triggerSummon(owner, servant);
    }

    private static void summonPrismarineSoldierServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        BlockPos feet = match.getBlock(1, 2, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.PRISMARINE_SOLDIER.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        PrismarineSoldier servant = ModEntityTypes.PRISMARINE_SOLDIER.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.SPLASH, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                24, 0.45D, 0.9D, 0.45D, 0.06D);
        level.sendParticles(ParticleTypes.BUBBLE, feet.getX() + 0.5D, feet.getY() + 0.8D, feet.getZ() + 0.5D,
                16, 0.4D, 0.7D, 0.4D, 0.04D);
        level.playSound(null, feet, SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
        triggerSummon(owner, servant);
    }

    private static void summonIceGolemServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        BlockPos feet = match.getBlock(1, 2, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.ICE_GOLEM_SERVANT.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        IceGolemServant servant = ModEntityTypes.ICE_GOLEM_SERVANT.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.SNOWFLAKE, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                24, 0.45D, 0.9D, 0.45D, 0.05D);
        level.playSound(null, feet, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.7F);
        triggerSummon(owner, servant);
    }

    private static void summonGiantSnowGolemServant(ServerLevel level, BlockPattern.BlockPatternMatch match) {
        if (!MutantMonstersCompat.isLoaded()) {
            return;
        }
        BlockPos feet = match.getBlock(2, 3, 0).getPos();
        ServerPlayer owner = nearestOwner(level, feet);
        if (owner == null || !SEHelper.hasResearch(owner, ResearchList.WARRED)) {
            if (owner != null) {
                owner.displayClientMessage(Component.translatable("info.goety.research.fail"), true);
            }
            return;
        }
        if (!ServantLimits.hasRoom(level, owner, ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SERVANT.get())) {
            return;
        }
        CarvedPumpkinBlock.clearPatternBlocks(level, match);
        AbominableSnowGolemServant servant = ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SERVANT.get().create(level);
        boolean added = false;
        if (servant != null) {
            servant.moveTo(feet.getX() + 0.5D, feet.getY() + 0.05D, feet.getZ() + 0.5D, 0.0F, 0.0F);
            bindOwner(servant, owner);
            servant.setWandering(false);
            servant.setStaying(false);
            servant.setPersistenceRequired();
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(feet), MobSpawnType.MOB_SUMMONED, null, null);
            added = level.addFreshEntity(servant);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, match);
        if (!added) {
            return;
        }
        level.sendParticles(ParticleTypes.SNOWFLAKE, feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D,
                36, 0.6D, 1.2D, 0.6D, 0.06D);
        level.sendParticles(ParticleTypes.POOF, feet.getX() + 0.5D, feet.getY() + 0.7D, feet.getZ() + 0.5D,
                10, 0.4D, 0.6D, 0.4D, 0.05D);
        level.playSound(null, feet, SoundEvents.SNOW_GOLEM_AMBIENT, SoundSource.BLOCKS, 1.2F, 0.7F);
        triggerSummon(owner, servant);
    }

    private static ServerPlayer nearestOwner(ServerLevel level, BlockPos feet) {
        net.minecraft.world.entity.player.Player player = level.getNearestPlayer(
                feet.getX() + 0.5D, feet.getY() + 0.5D, feet.getZ() + 0.5D, OWNER_RANGE, false);
        return player instanceof ServerPlayer serverPlayer ? serverPlayer : null;
    }

    private static void bindOwner(net.minecraft.world.entity.LivingEntity servant, ServerPlayer owner) {
        if (servant instanceof com.Polarice3.Goety.api.entities.IOwned owned && owner != null) {
            owned.setTrueOwner(owner);
            owned.setOwnerId(owner.getUUID());
        }
    }

    private static void triggerSummon(ServerPlayer owner, net.minecraft.world.entity.Entity summoned) {
        if (owner != null) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(owner, summoned);
        }
    }
}
