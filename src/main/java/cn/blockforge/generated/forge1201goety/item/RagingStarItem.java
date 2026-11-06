package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.item.RagingStarItem;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class RagingStarItem extends Item {
    public RagingStarItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
            InteractionHand hand) {
        if (target instanceof com.Polarice3.Goety.api.entities.IOwned owned
                && owned.getLifespan() > 0) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                        Component.translatable("info.goety_supreme.servant.has_lifespan")
                                .withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.FAIL;
        }
        if (target instanceof RagingStarServant servant) {
            return applyToServant(stack, player, target, servant);
        }
        if (!(target instanceof WitherServant servant) || servant.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (servant.getPersistentData().getBoolean("goety_storm:no_boon")) {
            return InteractionResult.FAIL;
        }
        if (servant.isStarEmbedded()) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(Component.translatable("info.goety_supreme.wither_servant.already_embedded")
                        .withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.FAIL;
        }
        if (!player.level().isClientSide) {
            servant.unlockRagingForm(); // 内部完成 markStarEmbedded 与护甲值/韧性加成
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            servant.playSound(SoundEvents.BEACON_ACTIVATE, 2.0F, 0.65F);
            servant.playWitherTheme(null);
            OwnerBoundLoot.deliver(servant, new ItemStack(GeneratedMod.MUSIC_DISC_WITHER_THEME.get()));
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    private static InteractionResult applyToServant(ItemStack stack, Player player, LivingEntity target,
            RagingStarServant servant) {
        if (servant.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (servant.isRagingStarred()) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(Component.translatable("info.goety_supreme.servant.already_embedded")
                        .withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.FAIL;
        }
        if (!player.level().isClientSide) {
            servant.setRagingStarred(true);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            target.playSound(GeneratedMod.ENGENDER_HERO.get(), 2.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}
