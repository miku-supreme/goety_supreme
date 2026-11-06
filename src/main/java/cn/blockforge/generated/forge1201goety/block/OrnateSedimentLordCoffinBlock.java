package cn.blockforge.generated.goetysupreme.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.block.OrnateSedimentLordCoffinBlock;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public class OrnateSedimentLordCoffinBlock extends Block {

    public static final BooleanProperty EMPTY = BooleanProperty.create("empty");

    public OrnateSedimentLordCoffinBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
                .strength(12.0F, 24.0F));
        registerDefaultState(stateDefinition.any().setValue(EMPTY, Boolean.FALSE));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EMPTY);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.TOTEM_OF_UNDYING)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ServerLevel server = (ServerLevel) level;
        SedimentLordServant lord = ModEntityTypes.SEDIMENT_LORD_SERVANT.get().create(server);
        if (lord == null) {
            return InteractionResult.PASS;
        }
        if (!ServantLimits.hasRoom(server, player, lord.getType())) {
            return InteractionResult.CONSUME;
        }
        lord.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                player.getYRot(), 0.0F);
        lord.finalizeSpawn(server, server.getCurrentDifficultyAt(pos),
                MobSpawnType.MOB_SUMMONED, null, null);
        lord.setTrueOwner(player);
        lord.setHealth(lord.getMaxHealth());
        lord.setPersistenceRequired();
        if (!server.addFreshEntity(lord)) {
            return InteractionResult.FAIL;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        level.setBlockAndUpdate(pos, state.setValue(EMPTY, Boolean.TRUE));
        server.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, lord.getX(), lord.getY(1.0D), lord.getZ(),
                40, 0.5D, 0.9D, 0.5D, 0.25D);
        server.sendParticles(ParticleTypes.SOUL, lord.getX(), lord.getY(0.6D), lord.getZ(),
                16, 0.4D, 0.6D, 0.4D, 0.02D);
        server.playSound(null, lord.getX(), lord.getY(0.5D), lord.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.8F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(EMPTY)) {
            return;
        }
        if (random.nextInt(12) != 0) {
            return;
        }
        double x = pos.getX() + 0.15D + random.nextDouble() * 0.7D;
        double y = pos.getY() + 0.9D + random.nextDouble() * 0.2D;
        double z = pos.getZ() + 0.15D + random.nextDouble() * 0.7D;
        level.addParticle(ParticleTypes.SOUL, x, y, z, 0.0D, 0.012D, 0.0D);
    }
}
