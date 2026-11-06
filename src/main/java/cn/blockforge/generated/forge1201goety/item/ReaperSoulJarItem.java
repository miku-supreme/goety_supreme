package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.renderer.SoulJarReviveFx;
import cn.blockforge.generated.goetysupreme.entity.SoulReaperServant;

public class ReaperSoulJarItem extends ReviveServantItem {

    private static final ResourceLocation REAPER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "reaper_servant");

    private static final ResourceLocation SOUL_REAPER_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "soul_reaper_servant");

    public ReaperSoulJarItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public static boolean holdsSoulReaper(ItemStack stack, Level level) {
        return stack.is(GeneratedMod.REAPER_SOUL_JAR.get())
                && ReviveServantItem.getSummon(stack, level) instanceof SoulReaperServant;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
            LivingEntity target, InteractionHand hand) {
        boolean reaperTarget = isOwnedReaperServant(target, player);
        boolean soulReaperTarget = isOwnedSoulReaperServant(target, player);
        if (!reaperTarget && !soulReaperTarget) {
            return InteractionResult.PASS;
        }
        Entity stored = ReviveServantItem.getSummon(stack, player.level());
        if (reaperTarget && stored instanceof ReaperNecromancerServant reaper) {
            return this.reviveStored(stack, player, target, hand, reaper);
        }
        if (reaperTarget && stored instanceof SoulReaperServant soulReaper) {
            return this.reviveStored(stack, player, target, hand, soulReaper);
        }
        if (soulReaperTarget && stored instanceof SoulReaperServant soulReaper) {
            return this.reviveStored(stack, player, target, hand, soulReaper);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult reviveStored(ItemStack stack, Player player, LivingEntity target,
            InteractionHand hand, Summoned servant) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel server = (ServerLevel) player.level();
        if (servant.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (!ServantLimits.hasRoom(server, player, servant.getType())) {
            return InteractionResult.CONSUME;
        }
        servant.moveTo(target.getX(), target.getY(), target.getZ(), player.getYRot(), 0.0F);
        servant.setTrueOwner(player);
        servant.setHealth(servant.getMaxHealth());
        servant.setHasLifespan(false);
        servant.setPersistenceRequired();
        if (!server.addFreshEntity(servant)) {
            return InteractionResult.FAIL;
        }
        SoulJarReviveFx.play(server, servant, player);
        target.discard();
        ReviveCooldown.grantAfterRevive(player, stack);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        player.swing(hand);
        server.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY(1.0D), servant.getZ(),
                24, 0.4D, 0.7D, 0.4D, 0.01D);
        server.playSound(null, servant.getX(), servant.getY(0.5D), servant.getZ(),
                SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.9F, 0.6F);
        return InteractionResult.sidedSuccess(false);
    }

    private static boolean isOwnedReaperServant(LivingEntity target, Player player) {
        if (!(target instanceof IOwned owned) || owned.getTrueOwner() != player) {
            return false;
        }
        return REAPER_SERVANT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()));
    }

    private static boolean isOwnedSoulReaperServant(LivingEntity target, Player player) {
        if (!(target instanceof IOwned owned) || owned.getTrueOwner() != player) {
            return false;
        }
        return SOUL_REAPER_SERVANT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()));
    }
}
