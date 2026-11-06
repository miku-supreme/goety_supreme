package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.block.OrnateSedimentLordCoffinBlock;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.event.SupremeAdvancementEvents;

public class SedimentLordTotemItem extends ReviveServantItem {

    public SedimentLordTotemItem() {
        super(new Item.Properties().stacksTo(1).rarity(net.minecraft.world.item.Rarity.RARE));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);
        if (player == null || !(state.getBlock() instanceof OrnateSedimentLordCoffinBlock)) {
            return InteractionResult.PASS;
        }
        if (ReviveCooldown.isOnCooldown(player, stack)) {
            return InteractionResult.FAIL;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ServerLevel server = (ServerLevel) level;
        Entity summon = ReviveServantItem.getSummon(stack, level);
        if (!(summon instanceof SedimentLordServant lord)) {
            player.displayClientMessage(
                    Component.translatable("item.goety_supreme.sediment_lord_totem.empty"), true);
            return InteractionResult.CONSUME;
        }
        if (!ServantLimits.hasRoom(server, player, lord.getType())) {
            return InteractionResult.CONSUME;
        }
        lord.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                player.getYRot(), 0.0F);
        lord.setTrueOwner(player);
        lord.setHealth(lord.getMaxHealth());
        lord.setPersistenceRequired();
        lord.beginCoffinEntrance();
        if (!server.addFreshEntity(lord)) {
            return InteractionResult.FAIL;
        }
        ReviveCooldown.grantAfterRevive(player, stack);
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            SupremeAdvancementEvents.onSedimentLordRevived(serverPlayer);
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        server.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, lord.getX(), lord.getY(1.0D), lord.getZ(),
                40, 0.5D, 0.9D, 0.5D, 0.25D);
        server.sendParticles(ParticleTypes.SOUL, lord.getX(), lord.getY(0.6D), lord.getZ(),
                16, 0.4D, 0.6D, 0.4D, 0.02D);
        server.playSound(null, lord.getX(), lord.getY(0.5D), lord.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.8F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
