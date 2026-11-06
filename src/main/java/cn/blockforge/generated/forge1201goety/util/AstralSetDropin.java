package cn.blockforge.generated.goetysupreme.util; // ① 只改这一行成你自己的包名，类名与文件内容不用动

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.event.CreativeTabEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GolemMolds;
import cn.blockforge.generated.goetysupreme.util.SupremeCreativeTab;

public final class AstralSetDropin {

    public static final String NAMESPACE = GeneratedMod.MOD_ID;

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, NAMESPACE);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, NAMESPACE);

    public static final RegistryObject<Block> ASTRAL_WITHER_SKULL =
            BLOCKS.register("astral_wither_skull",
                    () -> new SkullBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.QUARTZ)
                            .strength(1.0F)
                            .noOcclusion()));

    public static final RegistryObject<Block> PRISON_STEEL_BLOCK =
            BLOCKS.register("prison_steel_block",
                    () -> new Block(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .instrument(NoteBlockInstrument.PLING)
                            .requiresCorrectToolForDrops()
                            .strength(5.0F, 1200.0F)
                            .sound(SoundType.METAL)
                            .lightLevel(state -> 5)));

    public static final RegistryObject<Block> GRAVE_SOUL_SAND =
            BLOCKS.register("grave_soul_sand",
                    () -> new SoulSandBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SAND)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(0.1F)
                            .sound(SoundType.SOUL_SAND)
                            .speedFactor(0.4F)));

    public static final RegistryObject<Item> ASTRAL_WITHER_SKULL_ITEM =
            ITEMS.register("astral_wither_skull",
                    () -> new BlockItem(ASTRAL_WITHER_SKULL.get(),
                            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> PRISON_STEEL_BLOCK_ITEM =
            ITEMS.register("prison_steel_block",
                    () -> new BlockItem(PRISON_STEEL_BLOCK.get(),
                            new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> GRAVE_SOUL_SAND_ITEM =
            ITEMS.register("grave_soul_sand",
                    () -> new BlockItem(GRAVE_SOUL_SAND.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        LOGGER.info("[AstralSetDropin] 三件方块已挂入注册事件总线（命名空间 {}）", NAMESPACE);
    }

    private static final class SkullBlock extends HorizontalDirectionalBlock {

        private static final VoxelShape SHAPE =
                Shapes.box(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);

        SkullBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    private AstralSetDropin() {
    }
}
