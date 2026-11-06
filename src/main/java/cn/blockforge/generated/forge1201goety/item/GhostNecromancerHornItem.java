package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.entities.util.SummonCircleBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.item.GhostNecromancerHornItem;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;

public class GhostNecromancerHornItem extends Item {

    private static final int CHARGE_TICKS = 25;

    private static final int GOAT_HORN_CALL_INDEX = 6;

    public GhostNecromancerHornItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return CHARGE_TICKS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                living.getX(), living.getY(0.7D), living.getZ(),
                6, 0.45D, 0.7D, 0.45D, 0.01D);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(level instanceof ServerLevel server) || !(entity instanceof Player player)) {
            return stack;
        }
        BlockPos pos = player.blockPosition().relative(player.getDirection());
        if (canSummonHere(server)) {
            playSummoningCall(server, player, pos);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                    48, 1.1D, 1.2D, 1.1D, 0.03D);
            summonReaper(server, player, pos);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        } else {
            player.displayClientMessage(
                    Component.translatable("item.goety_supreme.ghost_necromancer_horn.failure"), true);
            server.playSound(null, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return stack;
    }

    private static boolean canSummonHere(Level level) {
        return level.dimension() == Level.OVERWORLD
                && level.isNight()
                && level.getMoonPhase() == 0
                && !level.isRaining()
                && !level.isThundering();
    }

    private static void playSummoningCall(ServerLevel level, Player player, BlockPos pos) {
        Object variant = SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(GOAT_HORN_CALL_INDEX);
        if (variant instanceof Holder.Reference<?> ref && ref.value() instanceof SoundEvent call) {
            level.playSound(null, pos, call, SoundSource.NEUTRAL, 16.0F, 1.0F);
        }
    }

    private static void summonReaper(ServerLevel level, Player player, BlockPos pos) {
        ReaperNecromancer reaper = ModEntityTypes.REAPER_NECROMANCER.get().create(level);
        if (reaper == null) {
            return;
        }
        reaper.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, player.getYRot(), 0.0F);
        reaper.finalizeSpawn(level, level.getCurrentDifficultyAt(pos),
                MobSpawnType.MOB_SUMMONED, null, null);
        level.addFreshEntity(new SummonCircleBoss(level, pos, reaper));
    }
}
