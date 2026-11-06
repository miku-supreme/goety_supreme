package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.item.ArcaneScrollItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class ArcaneScrollItem extends ReviveServantItem {

    public ArcaneScrollItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target,
            InteractionHand hand) {
        if (!(target instanceof PlayerMobServant servant) || servant.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (servant.hasArcaneScroll()) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                        Component.translatable("info.goety_supreme.servant.already_arcane")
                                .withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.FAIL;
        }
        if (!player.level().isClientSide && player.level() instanceof ServerLevel server) {
            if (!reviveStored(server, player, servant, stack)) {
                grantScroll(server, player, servant, stack);
            }
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    private static void grantScroll(ServerLevel server, Player player, PlayerMobServant servant,
            ItemStack stack) {
        servant.setArcaneScroll(true);
        servant.lockArcaneEquipmentDrops();
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        servant.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.2F, 1.0F);
        servant.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
        server.sendParticles(ParticleTypes.ENCHANT, servant.getX(),
                servant.getY() + servant.getBbHeight() * 0.5D, servant.getZ(),
                40, 0.6D, 0.8D, 0.6D, 0.6D);
        player.displayClientMessage(Component.translatable(
                "info.goety_supreme.servant.arcane_awakened", servant.getDisplayName()), true);
        if (player instanceof ServerPlayer serverPlayer) {
            awardArcaneAwakened(serverPlayer);
        }
    }

    private static void awardArcaneAwakened(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        Advancement advancement = server.getAdvancements()
                .getAdvancement(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "arcane_awakened"));
        if (advancement != null) {
            player.getAdvancements().award(advancement, "arcane_awakened");
        }
    }

    private static boolean reviveStored(ServerLevel server, Player player, PlayerMobServant target,
            ItemStack stack) {
        Entity stored;
        try {
            stored = ReviveServantItem.getSummon(stack, server);
        } catch (RuntimeException | LinkageError ignored) {
            return false;
        }
        if (!(stored instanceof PlayerMobServant revived)) {
            if (stored != null) {
                stored.discard();
            }
            return false;
        }
        List<ItemStack> carried = new ArrayList<>();
        for (int i = 0; i < target.getServantStorage().getContainerSize(); i++) {
            ItemStack carriedStack = target.getServantStorage().getItem(i);
            if (carriedStack.isEmpty() || isNoDrop(carriedStack)) {
                continue;
            }
            carried.add(carriedStack.copy());
        }
        revived.setUUID(UUID.randomUUID());
        revived.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
        revived.setTrueOwner(player);
        revived.setHealth(revived.getMaxHealth());
        revived.deathTime = 0;
        revived.hurtTime = 0;
        revived.setPersistenceRequired();
        revived.lockArcaneEquipmentDrops();
        if (!server.addFreshEntity(revived)) {
            revived.discard();
            return false;
        }
        for (ItemStack carriedStack : carried) {
            ItemStack leftover = carriedStack.copy();
            revived.addStackToStorage(leftover);
            if (!leftover.isEmpty()) {
                revived.spawnAtLocation(leftover);
            }
        }
        target.discard();
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        server.sendParticles(ParticleTypes.PORTAL, revived.getX(),
                revived.getY() + revived.getBbHeight() * 0.5D, revived.getZ(),
                40, 0.5D, 0.6D, 0.5D, 0.5D);
        playTotemRevival(server, revived);
        player.displayClientMessage(Component.translatable(
                "info.goety_supreme.servant.arcane_revived", revived.getDisplayName()), true);
        return true;
    }

    private static void playTotemRevival(ServerLevel server, PlayerMobServant revived) {
        revived.playSound(SoundEvents.TOTEM_USE, 1.0F, 1.0F);
        server.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                revived.getX(), revived.getY() + revived.getBbHeight() * 0.5D, revived.getZ(),
                30, 0.5D, 0.6D, 0.5D, 0.5D);
    }

    private static boolean isNoDrop(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(PlayerMobServant.NO_DROP_TAG);
    }
}
