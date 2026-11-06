package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.item.ServantHelmItem;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.renderer.SoulJarReviveFx;

public class ServantHelmItem extends ReviveServantItem {

    private static final ResourceLocation GOETY_WILDFIRE_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wildfire");

    private final Predicate<LivingEntity> targetFilter;

    private final Function<ServerLevel, ? extends Summoned> factory;

    private final ParticleOptions particle;
    private final SoundEvent sound;

    public ServantHelmItem(Predicate<LivingEntity> targetFilter,
            Function<ServerLevel, ? extends Summoned> factory,
            ParticleOptions particle, SoundEvent sound) {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).setNoRepair());
        this.targetFilter = targetFilter;
        this.factory = factory;
        this.particle = particle;
        this.sound = sound;
    }

    public static ServantHelmItem droughtWildfire() {
        return new ServantHelmItem(
                ServantHelmItem::isGoetyWildfire,
                level -> new DroughtWildfireServant(ModEntityTypes.DROUGHT_WILDFIRE_SERVANT.get(), level),
                ParticleTypes.FLAME,
                SoundEvents.FIRECHARGE_USE);
    }

    public static ServantHelmItem seaWave() {
        return new ServantHelmItem(
                entity -> entity instanceof BulgeServant && !(entity instanceof SeaWaveServant),
                level -> new SeaWaveServant(ModEntityTypes.SEA_WAVE_SERVANT.get(), level),
                ParticleTypes.SPLASH,
                SoundEvents.GENERIC_SPLASH);
    }

    public static boolean isGoetyWildfire(Entity entity) {
        return entity != null && GOETY_WILDFIRE_ID.equals(
                ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
            LivingEntity target, InteractionHand hand) {
        if (!targetFilter.test(target)) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel server = (ServerLevel) player.level();
        Entity stored = ReviveServantItem.getSummon(stack, server);
        Summoned servant = stored instanceof Summoned s ? s : factory.apply(server);
        if (servant == null) {
            return InteractionResult.PASS;
        }
        if (!ServantLimits.hasRoom(server, player, servant.getType())) {
            return InteractionResult.CONSUME;
        }
        servant.moveTo(target.getX(), target.getY(), target.getZ(), player.getYRot(), 0.0F);
        if (servant instanceof IOwned) {
            ((IOwned) servant).setTrueOwner(player);
        }
        servant.setHealth(servant.getMaxHealth());
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
        server.sendParticles(particle, servant.getX(), servant.getY(0.9D), servant.getZ(),
                30, 0.5D, 0.8D, 0.5D, 0.15D);
        server.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY(0.6D), servant.getZ(),
                12, 0.35D, 0.5D, 0.35D, 0.02D);
        server.playSound(null, servant.getX(), servant.getY(0.5D), servant.getZ(),
                sound, SoundSource.PLAYERS, 1.0F, 0.8F);
        return InteractionResult.sidedSuccess(false);
    }
}
