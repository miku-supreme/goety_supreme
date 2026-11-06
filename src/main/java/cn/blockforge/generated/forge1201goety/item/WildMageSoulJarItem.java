package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.api.entities.IOwned;
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
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.item.ServantHelmItem;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.renderer.SoulJarReviveFx;
import cn.blockforge.generated.goetysupreme.entity.WildMageServant;
import cn.blockforge.generated.goetysupreme.item.WildMageSoulJarItem;

public class WildMageSoulJarItem extends ReviveServantItem {

    private static final ResourceLocation WHISPERER_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "whisperer");
    private static final ResourceLocation WAVE_WHISPERER_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wavewhisperer");

    public WildMageSoulJarItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
            LivingEntity target, InteractionHand hand) {
        if (!isOwnedWhisperer(target, player)) {
            return InteractionResult.PASS;
        }
        Entity stored = ReviveServantItem.getSummon(stack, player.level());
        if (!(stored instanceof WildMageServant mage)) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel server = (ServerLevel) player.level();
        if (mage.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (!ServantLimits.hasRoom(server, player, mage.getType())) {
            return InteractionResult.CONSUME;
        }
        mage.moveTo(target.getX(), target.getY(), target.getZ(), player.getYRot(), 0.0F);
        mage.setTrueOwner(player);
        mage.setHealth(mage.getMaxHealth());
        mage.setHasLifespan(false);
        mage.setPersistenceRequired();
        if (!server.addFreshEntity(mage)) {
            return InteractionResult.FAIL;
        }
        SoulJarReviveFx.play(server, mage, player);
        target.discard();
        ReviveCooldown.grantAfterRevive(player, stack);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        player.swing(hand);
        server.sendParticles(ParticleTypes.SPORE_BLOSSOM_AIR, mage.getX(), mage.getY(1.0D),
                mage.getZ(), 20, 0.4D, 0.6D, 0.4D, 0.01D);
        server.playSound(null, mage.getX(), mage.getY(0.5D), mage.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.8F, 1.2F);
        return InteractionResult.sidedSuccess(false);
    }

    private static boolean isOwnedWhisperer(LivingEntity target, Player player) {
        if (!(target instanceof IOwned owned) || owned.getTrueOwner() != player) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        return WHISPERER_ID.equals(id) || WAVE_WHISPERER_ID.equals(id);
    }
}
