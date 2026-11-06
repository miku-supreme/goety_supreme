package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.config.BitterColdWindFocusConfig;
import cn.blockforge.generated.goetysupreme.config.DroughtBlazeFocusConfig;
import cn.blockforge.generated.goetysupreme.config.EndermanServantFocusConfig;
import cn.blockforge.generated.goetysupreme.config.FrostbornFocusConfig;
import cn.blockforge.generated.goetysupreme.config.FrostSnowFocusConfig;
import cn.blockforge.generated.goetysupreme.config.IronGuardFocusConfig;
import cn.blockforge.generated.goetysupreme.config.PlayerMobFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.config.SedimentFocusConfig;
import cn.blockforge.generated.goetysupreme.config.ServantCapConfig;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public final class ServantLimits {

    public static final int SUMMONED_LIFESPAN_TICKS = 120 * 20;

    private static final Map<UUID, Long> LAST_WARN = new ConcurrentHashMap<>();
    private static final long WARN_COOLDOWN_MS = 3000L;

    private ServantLimits() {
    }

    public static int limitFor(net.minecraft.world.entity.EntityType<?> type) {
        if (type == ModEntityTypes.ICE_GOLEM_SERVANT.get()) {
            return FrostbornFocusConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.IRON_GOLEM_SERVANT.get()) {
            return IronGuardFocusConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.SNOW_GOLEM_SERVANT.get()) {
            return FrostSnowFocusConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.ENDERMAN_SERVANT.get()) {
            return EndermanServantFocusConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.PLAYER_MOB_SERVANT.get()) {
            return PlayerMobFocusConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.SAKURA_SPIRIT_SERVANT.get()) {
            return SakuraSpiritConfig.SUMMON_LIMIT.get();
        }
        if (type == ModEntityTypes.FURNACE_GOLEM.get()) {
            return ServantCapConfig.FURNACE_GOLEM.get();
        }
        if (type == ModEntityTypes.MAGMA_GOLEM.get()) {
            return ServantCapConfig.MAGMA_GOLEM.get();
        }
        if (type == ModEntityTypes.PRISON_GOLEM.get()) {
            return ServantCapConfig.PRISON_GOLEM.get();
        }
        if (type == ModEntityTypes.SCORCHBACK.get()) {
            return ServantCapConfig.SCORCHBACK.get();
        }
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            if (type == ModEntityTypes.SEDIMENT_GUARD_SERVANT.get()) {
                return SedimentFocusConfig.GUARD.summonLimit.get();
            }
            if (type == ModEntityTypes.SEDIMENT_SOUL_STABILIZER_SERVANT.get()) {
                return SedimentFocusConfig.SOUL_STABILIZER.summonLimit.get();
            }
            if (type == ModEntityTypes.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT.get()) {
                return SedimentFocusConfig.HEAVY_GUARD.summonLimit.get();
            }
            if (type == ModEntityTypes.SEDIMENT_LORD_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SEDIMENT_LORD.get();
            }
            if (type == ModEntityTypes.CORROSIVER_SERVANT.get()) {
                return ServantCapConfig.JEROTES_CORROSIVER.get();
            }
            if (type == ModEntityTypes.LIGHTNING_WORM_SERVANT.get()) {
                return ServantCapConfig.JEROTES_LIGHTNING_WORM.get();
            }
            if (type == ModEntityTypes.BITTER_COLD_WIND_SERVANT.get()) {
                return BitterColdWindFocusConfig.SUMMON_LIMIT.get();
            }
            if (type == ModEntityTypes.FLAMOAR_SERVANT.get()) {
                return ServantCapConfig.JEROTES_FLAMOAR.get();
            }
            if (type == ModEntityTypes.PRIMORDIER_SERVANT.get()) {
                return ServantCapConfig.JEROTES_PRIMORDIER.get();
            }
            if (type == ModEntityTypes.FROST_YETI_SERVANT.get()) {
                return ServantCapConfig.JEROTES_FROST_YETI.get();
            }
            if (type == ModEntityTypes.DROUGHT_BLAZE_SERVANT.get()) {
                return DroughtBlazeFocusConfig.SUMMON_LIMIT.get();
            }
            if (type == ModEntityTypes.DROUGHT_WILDFIRE_SERVANT.get()) {
                return ServantCapConfig.JEROTES_DROUGHT_WILDFIRE.get();
            }
            if (type == ModEntityTypes.BULGE_SERVANT.get()) {
                return SedimentFocusConfig.BULGE.summonLimit.get();
            }
            if (type == ModEntityTypes.SEA_WAVE_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SEA_WAVE.get();
            }
        }
        if (type == ModEntityTypes.WILD_MAGE_SERVANT.get()) {
            return ServantCapConfig.WILD_MAGE_SERVANT.get();
        }
        if (type == ModEntityTypes.REAPER_NECROMANCER_SERVANT.get()) {
            return ServantCapConfig.REAPER_NECROMANCER_SERVANT.get();
        }
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            if (type == ModEntityTypes.SECOND_ROUNDER_ZOMBIE_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SECOND_ROUNDER_ZOMBIE.get();
            }
            if (type == ModEntityTypes.SECOND_ROUNDER_SKELETON_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SECOND_ROUNDER_SKELETON.get();
            }
            if (type == ModEntityTypes.SECOND_ROUNDER_SPIDER_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SECOND_ROUNDER_SPIDER.get();
            }
            if (type == ModEntityTypes.ENDER_STOWAWAY_SERVANT.get()) {
                return ServantCapConfig.JEROTES_ENDER_STOWAWAY.get();
            }
            if (type == ModEntityTypes.SERPON_COLONIAL_SOLDIER.get()) {
                return ServantCapConfig.JEROTES_SERPON_COLONIAL_SOLDIER.get();
            }
            if (type == ModEntityTypes.SERPON_RIOTER.get()) {
                return ServantCapConfig.JEROTES_SERPON_RIOTER.get();
            }
            if (type == ModEntityTypes.SERPON_PIONEER.get()) {
                return ServantCapConfig.JEROTES_SERPON_PIONEER.get();
            }
            if (type == ModEntityTypes.SERPON_FIRE_APOSTLE.get()) {
                return ServantCapConfig.JEROTES_SERPON_FIRE_APOSTLE.get();
            }
            if (type == ModEntityTypes.SERPON_INTRUDER.get()) {
                return ServantCapConfig.JEROTES_SERPON_INTRUDER.get();
            }
            if (type == ModEntityTypes.SERPON_FLAME_WIZARD.get()) {
                return ServantCapConfig.JEROTES_SERPON_FLAME_WIZARD.get();
            }
            if (type == ModEntityTypes.WAR_PYTHON_SERVANT.get()) {
                return ServantCapConfig.JEROTES_WAR_PYTHON.get();
            }
            if (type == ModEntityTypes.ROYAL_WAR_PYTHON_SERVANT.get()) {
                return ServantCapConfig.JEROTES_ROYAL_WAR_PYTHON.get();
            }
            if (type == ModEntityTypes.SERPON_COMBAT_EXECUTER.get()) {
                return ServantCapConfig.JEROTES_SERPON_COMBAT_EXECUTER.get();
            }
            if (type == ModEntityTypes.SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT.get()) {
                return ServantCapConfig.JEROTES_SECOND_ROUNDER_ANIMATED_ARMOR.get();
            }
            if (type == ModEntityTypes.FLYING_FIREBALL_SERVANT.get()) {
                return ServantCapConfig.JEROTES_FLYING_FIREBALL.get();
            }
            if (type == ModEntityTypes.IRON_TEETH_SERVANT.get()) {
                return ServantCapConfig.JEROTES_IRON_TEETH.get();
            }
        }
        if (isType(ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SERVANT, type)) {
            return ServantCapConfig.GIANT_SNOW_GOLEM.get();
        }
        if (type == ModEntityTypes.WITHER_SERVANT.get()) {
            return ServantCapConfig.WITHER_SERVANT.get();
        }
        if (type == ModEntityTypes.WITHER_WARLORD_SERVANT.get()) {
            return ServantCapConfig.WITHER_WARLORD_SERVANT.get();
        }
        if (isType(ModEntityTypes.MUTANT_ZOMBIE_SERVANT, type)) {
            return ServantCapConfig.MUTANT_ZOMBIE_SERVANT.get();
        }
        if (isType(ModEntityTypes.MUTANT_SKELETON_SERVANT, type)) {
            return ServantCapConfig.MUTANT_SKELETON_SERVANT.get();
        }
        if (isType(ModEntityTypes.MUTANT_SNOW_GOLEM_SERVANT, type)) {
            return ServantCapConfig.MUTANT_SNOW_GOLEM_SERVANT.get();
        }
        if (isType(ModEntityTypes.MUTANT_ENDERMAN_SERVANT, type)) {
            return ServantCapConfig.MUTANT_ENDERMAN_SERVANT.get();
        }
        return Integer.MAX_VALUE;
    }

    private static <T extends net.minecraft.world.entity.EntityType<?>> boolean isType(
            RegistryObject<T> object, net.minecraft.world.entity.EntityType<?> type) {
        return object.isPresent() && object.get() == type;
    }

    public static int countOwned(ServerLevel level, UUID owner, Predicate<net.minecraft.world.entity.EntityType<?>> matches) {
        int n = 0;
        for (ServerLevel dimension : level.getServer().getAllLevels()) {
            for (Entity entity : dimension.getAllEntities()) {
                if (!(entity instanceof LivingEntity living) || !(entity instanceof IOwned owned)) {
                    continue;
                }
                if (!matches.test(entity.getType()) || !living.isAlive()) {
                    continue;
                }
                LivingEntity trueOwner = owned.getTrueOwner();
                if (trueOwner != null && trueOwner.getUUID().equals(owner)) {
                    n++;
                }
            }
        }
        return n;
    }

    public static boolean hasRoom(ServerLevel level, Player player, net.minecraft.world.entity.EntityType<?> type) {
        int limit = limitFor(type);
        if (limit == Integer.MAX_VALUE || player == null) {
            return true;
        }
        if (countOwned(level, player.getUUID(),
                t -> t == type || sharesSerponCap(type, t)) < limit) {
            return true;
        }
        warn(player, "msg.goety_supreme.servant_limit_reached");
        return false;
    }

    public static boolean sharesSerponCap(net.minecraft.world.entity.EntityType<?> a,
            net.minecraft.world.entity.EntityType<?> b) {
        return (isSerponLegion(a) && isSerponLegion(b)) || (isSerponBerserker(a) && isSerponBerserker(b));
    }

    private static boolean isSerponLegion(net.minecraft.world.entity.EntityType<?> type) {
        return isType(ModEntityTypes.SERPON_COLONIAL_SOLDIER, type)
                || isType(ModEntityTypes.SERPON_PIONEER, type);
    }

    private static boolean isSerponBerserker(net.minecraft.world.entity.EntityType<?> type) {
        return isType(ModEntityTypes.SERPON_RIOTER, type)
                || isType(ModEntityTypes.SERPON_INTRUDER, type);
    }

    public static boolean hasRoom(ServerLevel level, Player player,
            Predicate<net.minecraft.world.entity.EntityType<?>> matches, int limit, String messageKey) {
        if (player == null) {
            return true;
        }
        if (countOwned(level, player.getUUID(), matches) < limit) {
            return true;
        }
        warn(player, messageKey);
        return false;
    }

    private static void warn(Player player, String messageKey) {
        long now = System.currentTimeMillis();
        Long last = LAST_WARN.put(player.getUUID(), now);
        if (last != null && now - last < WARN_COOLDOWN_MS) {
            return;
        }
        player.displayClientMessage(Component.translatable(messageKey), true);
    }
}
