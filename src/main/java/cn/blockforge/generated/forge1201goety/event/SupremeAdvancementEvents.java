package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.block.ScorchbackEggBlock;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.event.ServantAdvancementEvents;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.entity.SoulReaperServant;
import cn.blockforge.generated.goetysupreme.event.SupremeAdvancementEvents;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class SupremeAdvancementEvents {

    private static final String MOD_ID = GeneratedMod.MOD_ID;

    private static final ResourceLocation WHY_RISK_YOUR_LIFE = id("why_risk_your_life");

    private static final ResourceLocation ZOMBIE_LORD = id("zombie_lord");

    private static final ResourceLocation BACK_FROM_EXTINCTION = id("back_from_extinction");

    private static final ResourceLocation INTERFERING_WITH_JUSTICE = id("interfering_with_justice");

    private static final ResourceLocation WAILING_ECHO = id("wailing_echo");

    private static final ResourceLocation TASTIER_PREY = id("tastier_prey");

    private static final ResourceLocation HUNTERS_END = id("hunters_end");

    private static final ResourceLocation NARROW_ENCOUNTER = id("narrow_encounter");

    private static final ResourceLocation LONELY_HEART = id("lonely_heart");

    private static final ResourceLocation APPLE_A_DAY = id("apple_a_day");

    private static final double ENCOUNTER_RANGE = 32.0D;

    private static final double PAIR_SEARCH_RANGE = 48.0D;

    private static final double PAIR_DISTANCE = 12.0D;

    private SupremeAdvancementEvents() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead == null || dead.level().isClientSide()) {
            return;
        }
        ServerPlayer player = ServantAdvancementEvents.creditPlayer(event.getSource().getEntity());
        if (player == null) {
            player = ServantAdvancementEvents.creditPlayer(event.getSource().getDirectEntity());
        }
        if (player == null) {
            return;
        }
        if (isSerponCombatExecuter(dead)) {
            ServantAdvancementEvents.award(player, WHY_RISK_YOUR_LIFE, "execute_ally");
        } else if (dead instanceof WitherWarlord) {
            ServantAdvancementEvents.award(player, INTERFERING_WITH_JUSTICE, "defeat_warlord");
        } else if (dead instanceof ReaperNecromancer) {
            ServantAdvancementEvents.award(player, WAILING_ECHO, "kill_reaper");
        } else if (dead instanceof SoulReaper) {
            ServantAdvancementEvents.award(player, HUNTERS_END, "defeat_soul_reaper");
        } else if (dead instanceof Ifrit ifrit && !ifrit.isClone()) {
            ServantAdvancementEvents.award(player, LONELY_HEART, "defeat_ifrit");
        } else if (dead instanceof GreenSmith) {
            ServantAdvancementEvents.award(player, APPLE_A_DAY, "kill_smith_necromancer");
        }
    }

    private static boolean isSerponCombatExecuter(LivingEntity entity) {
        return entity != null && "cn.blockforge.generated.goetysupreme.SerponCombatExecuter"
                .equals(entity.getClass().getName());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        if (player.tickCount % 20 != 0 || player.isSpectator()) {
            return;
        }
        meetSoulReaper(player);
        pairServants(player);
    }

    private static void meetSoulReaper(ServerPlayer player) {
        if (ServantAdvancementEvents.isDone(player, TASTIER_PREY)) {
            return;
        }
        AABB area = player.getBoundingBox().inflate(ENCOUNTER_RANGE);
        if (!player.level().getEntitiesOfClass(SoulReaper.class, area, LivingEntity::isAlive).isEmpty()) {
            ServantAdvancementEvents.award(player, TASTIER_PREY, "meet_soul_reaper");
        }
    }

    private static void pairServants(ServerPlayer player) {
        if (ServantAdvancementEvents.isDone(player, NARROW_ENCOUNTER)) {
            return;
        }
        AABB area = player.getBoundingBox().inflate(PAIR_SEARCH_RANGE);
        var warlords = player.level().getEntitiesOfClass(WitherWarlordServant.class, area,
                servant -> servant.isAlive() && owns(servant, player));
        if (warlords.isEmpty()) {
            return;
        }
        var smiths = player.level().getEntitiesOfClass(GreenSmithServant.class, area,
                servant -> servant.isAlive() && owns(servant, player));
        for (WitherWarlordServant warlord : warlords) {
            for (GreenSmithServant smith : smiths) {
                if (warlord.distanceToSqr(smith) <= PAIR_DISTANCE * PAIR_DISTANCE) {
                    ServantAdvancementEvents.award(player, NARROW_ENCOUNTER, "servants_meet");
                    return;
                }
            }
        }
    }

    private static boolean owns(@Nullable LivingEntity servant, ServerPlayer player) {
        return servant instanceof IOwned owned && owned.getTrueOwner() == player;
    }

    public static void onSedimentLordRevived(ServerPlayer player) {
        ServantAdvancementEvents.award(player, ZOMBIE_LORD, "revive_from_coffin");
    }

    public static void onScorchbackEggHatched(@Nullable net.minecraft.world.entity.Entity owner) {
        if (owner instanceof ServerPlayer player) {
            ServantAdvancementEvents.award(player, BACK_FROM_EXTINCTION, "hatch_egg");
        }
    }
}
