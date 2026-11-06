package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.event.DracognathusRevivalEvents;
import cn.blockforge.generated.goetysupreme.item.DracognathusRevivalHornItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;

public class DracognathusRevivalHornItem extends ReviveServantItem {

    private static final ResourceLocation DRACOGNATHUS =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "dracognathus");

    private static final ResourceLocation CORROSIVER_SERVANT =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "corrosiver_servant");

    public DracognathusRevivalHornItem() {
        super(new Item.Properties().stacksTo(1).rarity(net.minecraft.world.item.Rarity.RARE));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
            LivingEntity target, InteractionHand hand) {
        if (!isOwnDracognathus(target, player)) {
            return InteractionResult.PASS;
        }
        if (target.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player.isSpectator() || !(target.level() instanceof ServerLevel server)) {
            return InteractionResult.PASS;
        }
        return revive(server, player, stack, target, hand);
    }

    public static boolean isOwnDracognathus(LivingEntity target, Player player) {
        if (target == player || !target.isAlive()) {
            return false;
        }
        if (!DRACOGNATHUS.equals(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()))) {
            return false;
        }
        return target instanceof OwnableEntity ownable
                && player.getUUID().equals(ownable.getOwnerUUID());
    }

    public static InteractionResult revive(ServerLevel server, Player player, ItemStack stack,
            LivingEntity host, InteractionHand hand) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(CORROSIVER_SERVANT);
        if (type == null) {
            return InteractionResult.FAIL;
        }
        Entity entity = type.create(server);
        if (entity == null) {
            return InteractionResult.FAIL;
        }
        double x = host.getX();
        double y = host.getY();
        double z = host.getZ();
        entity.moveTo(x, y, z, host.getYRot(), 0.0F);
        if (entity instanceof AbstractJerotesServant servant) {
            servant.setTrueOwner(player);
        }
        if (entity instanceof Mob mob) {
            mob.finalizeSpawn(server, server.getCurrentDifficultyAt(entity.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            mob.setPersistenceRequired();
        }
        if (!server.addFreshEntity(entity)) {
            return InteractionResult.FAIL;
        }
        host.discard();
        ReviveCooldown.grantAfterRevive(player, stack);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        server.playSound(null, x, y + 0.5D, z, SoundEvents.TOTEM_USE, SoundSource.PLAYERS,
                1.0F, 0.85F);
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y + 0.8D, z,
                30, 0.45D, 0.8D, 0.45D, 0.02D);
        server.sendParticles(ParticleTypes.SOUL, x, y + 0.6D, z,
                18, 0.35D, 0.6D, 0.35D, 0.01D);
        player.swing(hand, true);
        return InteractionResult.CONSUME;
    }
}
