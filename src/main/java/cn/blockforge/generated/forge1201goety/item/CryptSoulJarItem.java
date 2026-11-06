package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
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
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.CryptSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.renderer.SoulJarReviveFx;

public class CryptSoulJarItem extends ReviveServantItem {

    private static final ResourceLocation CAIRN_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "cairn_necromancer_servant");

    public CryptSoulJarItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
            LivingEntity target, InteractionHand hand) {
        if (!isOwnedGrownCairn(target, player)) {
            return InteractionResult.PASS;
        }
        Entity stored = ReviveServantItem.getSummon(stack, player.level());
        if (!(stored instanceof CryptNecromancerServant crypt)) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel server = (ServerLevel) player.level();
        if (crypt.getTrueOwner() != player) {
            return InteractionResult.PASS;
        }
        if (!ServantLimits.hasRoom(server, player, crypt.getType())) {
            return InteractionResult.CONSUME;
        }
        crypt.moveTo(target.getX(), target.getY(), target.getZ(), player.getYRot(), 0.0F);
        crypt.applyOwner(player);
        if (!server.addFreshEntity(crypt)) {
            return InteractionResult.FAIL;
        }
        SoulJarReviveFx.play(server, crypt, player);
        target.discard();
        ReviveCooldown.grantAfterRevive(player, stack);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        player.swing(hand);
        server.sendParticles(ParticleTypes.SOUL, crypt.getX(), crypt.getY(1.0D), crypt.getZ(),
                24, 0.4D, 0.7D, 0.4D, 0.01D);
        server.playSound(null, crypt.getX(), crypt.getY(0.5D), crypt.getZ(),
                SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.9F, 0.6F);
        return InteractionResult.sidedSuccess(false);
    }

    private static boolean isOwnedGrownCairn(LivingEntity target, Player player) {
        if (!(target instanceof IOwned owned) || owned.getTrueOwner() != player) {
            return false;
        }
        if (!(target instanceof AbstractNecromancer necromancer)) {
            return false;
        }
        if (necromancer.getNecroLevel() < 1) {
            return false;
        }
        return CAIRN_SERVANT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()));
    }
}
