package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.block.ScorchbackEggBlock;
import cn.blockforge.generated.goetysupreme.event.SupremeAdvancementEvents;

public class ScorchbackEggBlock extends Block {

    public static final IntegerProperty HATCH = IntegerProperty.create("hatch", 0, 2);

    public static final int HATCH_TICKS = 2400;

    private static final int STAGE_TICKS = HATCH_TICKS / 3;

    private static final double TAME_RANGE = 12.0D;

    public ScorchbackEggBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG));
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean pistonMoved) {
        super.onPlace(state, level, pos, oldState, pistonMoved);
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, STAGE_TICKS);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!nearHeat(level, pos)) {
            level.scheduleTick(pos, this, STAGE_TICKS);
            return;
        }
        int hatch = state.getValue(HATCH);
        if (hatch < 2) {
            level.setBlock(pos, state.setValue(HATCH, hatch + 1), Block.UPDATE_ALL);
            level.scheduleTick(pos, this, STAGE_TICKS);
            return;
        }
        this.hatch(level, pos);
    }

    private void hatch(ServerLevel level, BlockPos pos) {
        Scorchback baby = ModEntityTypes.SCORCHBACK.get().create(level);
        if (baby != null) {
            baby.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                    level.random.nextFloat() * 360.0F, 0.0F);
            baby.setBaby(true);
            Player nearest = level.getEntitiesOfClass(Player.class,
                    new AABB(pos).inflate(TAME_RANGE),
                    player -> player.isAlive() && !player.isSpectator()).stream()
                    .min((a, b) -> Double.compare(a.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()),
                            b.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())))
                    .orElse(null);
            if (nearest != null) {
                baby.setTrueOwner(nearest);
            }
            level.addFreshEntity(baby);
            SupremeAdvancementEvents.onScorchbackEggHatched(nearest);
        }
        level.removeBlock(pos, false);
        level.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D,
                24, 0.25D, 0.25D, 0.25D, 0.02D);
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5D, pos.getY() + 0.4D,
                pos.getZ() + 0.5D, 12, 0.2D, 0.3D, 0.2D, 0.02D);
    }

    public static boolean nearHeat(BlockGetter level, BlockPos center) {
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-3, -2, -3), center.offset(3, 2, 3))) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE) || state.is(Blocks.LAVA)
                    || state.is(Blocks.MAGMA_BLOCK) || state.is(Blocks.CAMPFIRE)
                    || state.is(Blocks.SOUL_CAMPFIRE) || state.is(Blocks.LAVA_CAULDRON)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 2.0D, 15.0D, 16.0D, 14.0D);
    }
}
