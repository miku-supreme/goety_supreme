package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.item.EntwinedLanternItem;
import cn.blockforge.generated.goetysupreme.util.EntwinedLanternRitual;

public final class EntwinedLanternItem extends Item {

    private static final int CHARGE_TICKS = 25;

    private static final int COOLDOWN_TICKS = 200;

    public EntwinedLanternItem() {
        super(new Item.Properties().stacksTo(1));
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
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseTicks) {
        if (level instanceof ServerLevel server) {
            ServerParticleUtil.addParticlesAroundSelf(server, ParticleTypes.SOUL_FIRE_FLAME, living);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(level instanceof ServerLevel server) || !(entity instanceof Player player)) {
            return stack;
        }
        BlockPos pos = player.blockPosition().relative(player.getDirection());
        if (EntwinedLanternRitual.trySummon(server, player, pos)) {
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        } else {
            player.displayClientMessage(Component
                    .translatable("item.goety_supreme.entwined_lantern.fail")
                    .withStyle(ChatFormatting.GRAY), true);
            server.playSound(null, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return stack;
    }
}
