package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.research.Research;
import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.GolemConstructionEvents;
import cn.blockforge.generated.goetysupreme.util.GolemMolds;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GolemConstructionEvents {

    private static final ResourceLocation ANIMATION_CORE_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "animation_core");

    private GolemConstructionEvents() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty() || !isAnimationCore(stack)) {
            return;
        }
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();

        if (state.is(Blocks.REDSTONE_BLOCK) && GolemMolds.isMagmaMold(level, pos)) {
            event.setCanceled(true);
            if (summon(level, player, stack, pos, ModEntityTypes.MAGMA_GOLEM.get(),
                    ResearchList.BYGONE, SoundEvents.IRON_GOLEM_DAMAGE, ParticleTypes.LAVA)) {
                GolemMolds.clearMagmaMold(level, pos);
            }
            return;
        }
        if (state.is(AstralSetDropin.PRISON_STEEL_BLOCK.get()) && GolemMolds.isPrisonMold(level, pos)) {
            event.setCanceled(true);
            if (summon(level, player, stack, pos, ModEntityTypes.PRISON_GOLEM.get(),
                    ResearchList.WARRED, SoundEvents.ANVIL_LAND, ParticleTypes.CRIT)) {
                GolemMolds.clearPrisonMold(level, pos);
            }
        }
    }

    private static boolean isAnimationCore(ItemStack stack) {
        Item core = BuiltInRegistries.ITEM.get(ANIMATION_CORE_ID);
        return core != null && stack.is(core);
    }

    private static <T extends AbstractGolemServant> boolean summon(Level level, Player player,
            ItemStack stack, BlockPos pos, EntityType<T> type, Research research,
            net.minecraft.sounds.SoundEvent sound, net.minecraft.core.particles.ParticleOptions particle) {
        if (!SEHelper.hasResearch(player, research)) {
            player.displayClientMessage(Component.translatable("info.goety.research.fail"), true);
            return false;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        if (!ServantLimits.hasRoom(serverLevel, player, type)) {
            return false;
        }
        T golem = type.create(serverLevel);
        if (golem == null) {
            return false;
        }
        golem.moveTo(pos.getX() + 0.5D, pos.getY() + 0.05D, pos.getZ() + 0.5D, 0.0F, 0.0F);
        golem.setTrueOwner(player);
        golem.setOwnerId(player.getUUID());
        golem.setPersistenceRequired();
        golem.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pos),
                MobSpawnType.MOB_SUMMONED, null, null);
        if (!serverLevel.addFreshEntity(golem)) {
            return false;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golem);
        }
        serverLevel.sendParticles(particle, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                24, 0.6D, 1.1D, 0.6D, 0.05D);
        serverLevel.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 0.85F);
        return true;
    }
}
