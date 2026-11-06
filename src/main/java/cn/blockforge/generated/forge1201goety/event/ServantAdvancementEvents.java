package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.ServantAdvancementEvents;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.entity.WildMageServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ServantAdvancementEvents {

    private static final ResourceLocation WILD_MAGE_SERVANT =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "wild_mage_servant");

    private ServantAdvancementEvents() {
    }

    @SubscribeEvent
    public static void onWildMageKilled(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead == null || dead.level().isClientSide() || !(dead instanceof WildMage)) {
            return;
        }
        ServerPlayer player = creditPlayer(event.getSource().getEntity());
        if (player == null) {
            player = creditPlayer(event.getSource().getDirectEntity());
        }
        if (player != null) {
            award(player, WILD_MAGE_SERVANT, "kill_wild_mage");
        }
    }

    @Nullable
    public static ServerPlayer creditPlayer(@Nullable Entity source) {
        if (source instanceof ServerPlayer player) {
            return player;
        }
        if (source instanceof LivingEntity living && living instanceof IOwned owned
                && owned.getTrueOwner() instanceof ServerPlayer owner) {
            return owner;
        }
        return null;
    }

    public static void award(ServerPlayer player, ResourceLocation advancementId, String criterion) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        Advancement advancement = server.getAdvancements().getAdvancement(advancementId);
        if (advancement != null) {
            player.getAdvancements().award(advancement, criterion);
        }
    }

    public static boolean isDone(ServerPlayer player, ResourceLocation advancementId) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return true;
        }
        Advancement advancement = server.getAdvancements().getAdvancement(advancementId);
        if (advancement == null) {
            return true;
        }
        return player.getAdvancements().getOrStartProgress(advancement).isDone();
    }
}
