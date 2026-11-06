package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;

public final class RingSetBonuses {

    private static final ResourceLocation GRAND_TURBAN =
            ResourceLocation.fromNamespaceAndPath("goety", "grand_turban");
    private static final ResourceLocation GRAND_ROBE =
            ResourceLocation.fromNamespaceAndPath("goety", "grand_robe");

    public static final String VANGUARD = "vanguard_servant";

    public static final String BLACKGUARD = "blackguard_servant";

    public static final String PIKER = "piker_servant";

    public static final String ROYALGUARD = "royalguard_servant";

    public static final String DROWNED = "drowned_servant";

    public static final String WITHER_SKELETON = "wither_skeleton_servant";

    public static final String ZPIGLIN = "zpiglin_servant";

    public static final String ZPIGLIN_BRUTE = "zpiglin_brute_servant";

    public static final String STONE_MINISTROSITY = "stone_ministrosity";

    public static final String REDSTONE_MINISTROSITY = "redstone_ministrosity";

    public static final String CATACLYSM_DRAUGR = "draugr_servant";

    public static final String CATACLYSM_ROYAL_DRAUGR = "royal_draugr_servant";

    private static final UUID GEO_IRON_TEETH_HEALTH_UUID =
            UUID.fromString("8b7d4a1e-5c2f-4a90-9d33-6f1c2e7a4b10");

    private RingSetBonuses() {
    }

    public static boolean pathIs(LivingEntity entity, String path) {
        if (entity == null) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key != null && path.equals(key.getPath());
    }

    private static boolean anyPath(LivingEntity entity, String... paths) {
        for (String path : paths) {
            if (pathIs(entity, path)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetheriteSwapEntity(LivingEntity entity) {
        return anyPath(entity, WITHER_SKELETON, ZPIGLIN, ZPIGLIN_BRUTE);
    }

    public static boolean wearsNamelessSet(LivingEntity owner) {
        if (!(owner instanceof Player player)) {
            return false;
        }
        try {
            return CuriosFinder.hasNamelessSet(player);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public static boolean wearsNetherSet(LivingEntity owner) {
        if (!(owner instanceof Player player)) {
            return false;
        }
        try {
            return CuriosFinder.hasNetherSet(player);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public static boolean wearsGrandSet(LivingEntity owner) {
        if (!(owner instanceof Player player)) {
            return false;
        }
        return RingServantGear.ownerHas(player, item(GRAND_TURBAN))
                && RingServantGear.ownerHas(player, item(GRAND_ROBE));
    }

    public static boolean wearsAbyssSet(LivingEntity owner) {
        if (!(owner instanceof Player player)) {
            return false;
        }
        try {
            return CuriosFinder.hasAbyssCrown(player) && CuriosFinder.hasAbyssRobes(player);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public static boolean wearsGeoSet(LivingEntity owner) {
        if (!(owner instanceof Player player)) {
            return false;
        }
        try {
            return CuriosFinder.hasAmethystNecklace(player) && CuriosFinder.hasGeoRobe(player);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    private static Item item(ResourceLocation id) {
        Item item = ForgeRegistries.ITEMS.getValue(id);
        return item == null ? Items.AIR : item;
    }

    public static Player ringScopedOwner(LivingEntity servant) {
        if (servant == null || !RingServantGear.ringScopeApplies(servant)) {
            return null;
        }
        return RingServantGear.ownerChainPlayer(servant);
    }

    public static Player namelessScopedOwner(LivingEntity servant) {
        Player owner = ringScopedOwner(servant);
        return owner != null && wearsNamelessSet(owner) ? owner : null;
    }

    public static Player abyssScopedOwner(LivingEntity servant) {
        Player owner = ringScopedOwner(servant);
        return owner != null && wearsAbyssSet(owner) ? owner : null;
    }

    public static Player geoScopedOwner(LivingEntity servant) {
        Player owner = ringScopedOwner(servant);
        return owner != null && wearsGeoSet(owner) ? owner : null;
    }

    public static Player namelessOrAbyssScopedOwner(LivingEntity servant) {
        Player owner = ringScopedOwner(servant);
        if (owner == null) {
            return null;
        }
        return wearsNamelessSet(owner) || wearsAbyssSet(owner) ? owner : null;
    }

    public static boolean netheriteSwap(LivingEntity servant) {
        if (!isNetheriteSwapEntity(servant)) {
            return false;
        }
        Player owner = ringScopedOwner(servant);
        return owner != null && wearsNetherSet(owner);
    }

    public static boolean forceUndeadWeapon(LivingEntity servant) {
        if (servant == null) {
            return false;
        }
        if (servant.getMobType() != MobType.UNDEAD) {
            return false;
        }
        if (!RingServantGear.acceptsRingArmor(servant)) {
            return false;
        }
        return namelessScopedOwner(servant) != null;
    }

    public static boolean drownedTrident(LivingEntity servant) {
        return pathIs(servant, DROWNED) && ringScopedOwner(servant) != null;
    }

    public static boolean ancientSwordExcluded(LivingEntity servant) {
        return namelessOrAbyssScopedOwner(servant) != null;
    }

    public static boolean draugrAxeForced(LivingEntity servant) {
        return anyPath(servant, CATACLYSM_DRAUGR, CATACLYSM_ROYAL_DRAUGR)
                && namelessScopedOwner(servant) != null;
    }

    public static float meleeDamageBonus(LivingEntity attacker) {
        Player owner = ringScopedOwner(attacker);
        if (owner == null) {
            return 0.0F;
        }
        if (wearsNamelessSet(owner)) {
            if (pathIs(attacker, VANGUARD)) {
                return 1.0F;
            }
            if (pathIs(attacker, BLACKGUARD)) {
                return 2.0F;
            }
        }
        if (wearsGrandSet(owner)) {
            if (pathIs(attacker, PIKER)) {
                return 1.0F;
            }
            if (pathIs(attacker, ROYALGUARD)) {
                return 2.0F;
            }
        }
        if (wearsGeoSet(owner) && anyPath(attacker, STONE_MINISTROSITY, REDSTONE_MINISTROSITY)) {
            return 1.0F;
        }
        return 0.0F;
    }

    public static boolean isOminousWolf(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (key == null) {
            return false;
        }
        String path = key.getPath();
        return path.contains("wolf") || path.contains("warg") || "black_beast".equals(path);
    }

    public static void applyIronTeethGeoHealth(LivingEntity entity) {
        if (entity == null || geoScopedOwner(entity) == null) {
            return;
        }
        AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null || maxHealth.getModifier(GEO_IRON_TEETH_HEALTH_UUID) != null) {
            return;
        }
        maxHealth.addPermanentModifier(new AttributeModifier(GEO_IRON_TEETH_HEALTH_UUID,
                "goety_supreme:ring_geo_iron_teeth", 5.0D, AttributeModifier.Operation.ADDITION));
    }
}
