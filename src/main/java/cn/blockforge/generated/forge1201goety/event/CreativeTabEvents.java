package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.event.CreativeTabEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CreativeTabEvents {
    private static final ResourceLocation GOETY_FOCUS_TAB =
            ResourceLocation.fromNamespaceAndPath("goety", "goety_focus");

    private static final ResourceLocation GOETY_MAIN_TAB =
            ResourceLocation.fromNamespaceAndPath("goety", "goety");
    private static final ResourceLocation GOETY_BLOCK_TAB =
            ResourceLocation.fromNamespaceAndPath("goety", "goety_block");
    private static final ResourceLocation GOETY_SERVANT_TAB =
            ResourceLocation.fromNamespaceAndPath("goety", "goety_servants");

    public static boolean jerotesVillageLoaded() {
        return JerotesVillageCompat.isJerotesVillageLoaded();
    }

    public static boolean mutantMonstersLoaded() {
        return MutantMonstersCompat.isLoaded();
    }

    private CreativeTabEvents() {
    }

    @SubscribeEvent
    public static void addItems(BuildCreativeModeTabContentsEvent event) {
        ResourceLocation tabId = event.getTabKey().location();
        if (GOETY_BLOCK_TAB.equals(tabId)) {
            event.accept(AstralSetDropin.ASTRAL_WITHER_SKULL_ITEM);
            event.accept(AstralSetDropin.PRISON_STEEL_BLOCK_ITEM);
            event.accept(AstralSetDropin.GRAVE_SOUL_SAND_ITEM);
            event.accept(GeneratedMod.REINFORCED_BLAST_FURNACE);
            event.accept(GeneratedMod.PRISMARINE_CORE);
            event.accept(GeneratedMod.MOBILE_PRISON);
            if (jerotesVillageLoaded()) {
                event.accept(GeneratedMod.ORNATE_SEDIMENT_LORD_COFFIN);
            }
        } else if (GOETY_MAIN_TAB.equals(tabId)) {
            event.accept(GeneratedMod.ARCANE_SCROLL);
            event.accept(GeneratedMod.FORBIDDEN_CHEAT_SHEET);
        } else if (GOETY_FOCUS_TAB.equals(tabId)) {
            event.accept(GeneratedMod.PLAYER_MOB_FOCUS);
            event.accept(GeneratedMod.SAKURA_SPIRIT_FOCUS);
            event.accept(GeneratedMod.HAUNTED_ARMOR_FOCUS);
            event.accept(GeneratedMod.FROST_SNOW_FOCUS);
            event.accept(GeneratedMod.FROSTBORN_FOCUS);
            event.accept(GeneratedMod.RAGING_SHARK_FOCUS);
            event.accept(GeneratedMod.SPIN_SCYTHE_FOCUS);
            event.accept(GeneratedMod.GREEN_FIRE_SKY_FOCUS);
            event.accept(GeneratedMod.IRON_GUARD_FOCUS);
            event.accept(GeneratedMod.SNOWBALL_FOCUS);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.ICE_SHARD_FOCUS);
                event.accept(GeneratedMod.ENDERMAN_HAND_FOCUS);
            }
            event.accept(GeneratedMod.ENDERMAN_SERVANT_FOCUS);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.GIANT_HAMMER_FOCUS);
            }
            event.accept(GeneratedMod.SKELETON_MOUNT_FOCUS);
            event.accept(GeneratedMod.WITHER_SKELETON_MOUNT_FOCUS);
            if (jerotesVillageLoaded()) {
                event.accept(GeneratedMod.SEDIMENT_GUARD_FOCUS);
                event.accept(GeneratedMod.SEDIMENT_SOUL_STABILIZER_FOCUS);
                event.accept(GeneratedMod.HEAVY_ARMORED_SEDIMENT_GUARD_FOCUS);
                event.accept(GeneratedMod.BULGE_FOCUS);
                event.accept(GeneratedMod.DROUGHT_BLAZE_FOCUS);
                event.accept(GeneratedMod.WAVE_BOLT_FOCUS);
                event.accept(GeneratedMod.DROUGHT_MISSILE_FOCUS);
                event.accept(GeneratedMod.SEDIMENT_MISSILE_FOCUS);
                event.accept(GeneratedMod.SECOND_ROUNDER_ROTTING_FOCUS);
                event.accept(GeneratedMod.SECOND_ROUNDER_OSSEOUS_FOCUS);
                event.accept(GeneratedMod.SERPON_HEAD_FOCUS);
                event.accept(GeneratedMod.SERPON_CROWN_FOCUS);
                event.accept(GeneratedMod.SERPON_FLAME_FOCUS);
                event.accept(GeneratedMod.WAR_PYTHON_FOCUS);
                event.accept(GeneratedMod.SERPON_BLAZING_FOCUS);
                event.accept(GeneratedMod.CORROSIVER_BREATH_FOCUS);
                event.accept(GeneratedMod.ANIMATED_IRON_FOCUS);
                event.accept(GeneratedMod.CORROSIVE_ICE_SPIKE_FOCUS);
                event.accept(GeneratedMod.BITTER_COLD_WIND_FOCUS);
                event.accept(GeneratedMod.HOT_WIND_FOCUS);
                event.accept(GeneratedMod.METEOR_FOCUS);
                event.accept(GeneratedMod.PIKE_RAIN_FOCUS);
                event.accept(GeneratedMod.OCEAN_BREATH_FOCUS);
                event.accept(GeneratedMod.WHIRLPOOL_FOCUS);
                event.accept(GeneratedMod.ASPHYXIA_FOCUS);
                event.accept(GeneratedMod.MALIGNASAUR_BOMB_FOCUS);
                event.accept(GeneratedMod.IRON_TEETH_FOCUS);
                event.accept(GeneratedMod.FLYING_FIREBALL_FOCUS);
                event.accept(GeneratedMod.BLAZING_SPIRIT_FOCUS);
            }
        } else if (GOETY_SERVANT_TAB.equals(tabId)) {
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.MUTANT_ZOMBIE_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.BERSERK_ESSENCE);
            event.accept(GeneratedMod.BLAZING_EMBER);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.MUTANT_SNOW_GOLEM_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.SNOW_GOLEM_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.IRON_GOLEM_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.SKELETON_HORSE_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.WITHER_SKELETON_HORSE_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.ENDERMAN_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.VITALITY_CARVED_PUMPKIN);
            event.accept(GeneratedMod.VITALITY_PUMPKIN_LANTERN);
            event.accept(GeneratedMod.ENTWINED_LANTERN);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.MUTANT_SKELETON_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.CONCENTRATED_BONE_CHUNK);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.MUTANT_ENDERMAN_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.GOETY_ZOMBIE_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.GOETY_SKELETON_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.PLAYER_MOB_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.WITHER_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.NAMELESS_WITHER_SPAWN_EGG);
            event.accept(GeneratedMod.EVIL_STAR);
            event.accept(GeneratedMod.RAGING_STAR);
            event.accept(GeneratedMod.SAKURA_SPIRIT_SPAWN_EGG);
            event.accept(GeneratedMod.FURNACE_GOLEM_SPAWN_EGG);
            event.accept(GeneratedMod.MAGMA_GOLEM_SPAWN_EGG);
            event.accept(GeneratedMod.PRISON_GOLEM_SPAWN_EGG);
            event.accept(GeneratedMod.WITHER_WARLORD_SPAWN_EGG);
            event.accept(GeneratedMod.WITHER_WARLORD_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.INFERNAL_CREDENTIAL);
            event.accept(GeneratedMod.ICE_GOLEM_SERVANT_SPAWN_EGG);
            if (mutantMonstersLoaded()) {
                event.accept(GeneratedMod.ABOMINABLE_SNOW_GOLEM_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.PRISMARINE_SOLDIER_SPAWN_EGG);
            event.accept(GeneratedMod.WILD_MAGE_SPAWN_EGG);
            event.accept(GeneratedMod.WILD_MAGE_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.REAPER_NECROMANCER_SPAWN_EGG);
            event.accept(GeneratedMod.REAPER_NECROMANCER_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.GREEN_SMITH_SPAWN_EGG);
            event.accept(GeneratedMod.GREEN_SMITH_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.IFRIT_SPAWN_EGG);
            event.accept(GeneratedMod.SCORCHBACK_SPAWN_EGG);
            event.accept(GeneratedMod.SCORCHBACK_EGG);
            event.accept(GeneratedMod.CRYPT_NECROMANCER_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.WILD_MAGE_SOUL_JAR);
            event.accept(GeneratedMod.REAPER_SOUL_JAR);
            event.accept(GeneratedMod.GREEN_SMITH_SOUL_JAR);
            event.accept(GeneratedMod.UNIVERSAL_SOUL_JAR);
            event.accept(GeneratedMod.CRYPT_SOUL_JAR);
            event.accept(GeneratedMod.BROKEN_REAPER_SCYTHE);
            event.accept(GeneratedMod.SOUL_REAPER_SPAWN_EGG);
            event.accept(GeneratedMod.SOUL_REAPER_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.COFFIN_SHARD);
            event.accept(GeneratedMod.GHOST_NECROMANCER_HORN);
            event.accept(GeneratedMod.GREEN_APPLE);
            event.accept(GeneratedMod.TEMPERED_RING);
            event.accept(GeneratedMod.IMMUTABLE_RING);
            event.accept(GeneratedMod.OVERGROWTH_CORE);
            if (jerotesVillageLoaded()) {
                event.accept(GeneratedMod.SEDIMENT_GUARD_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SEDIMENT_SOUL_STABILIZER_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SEDIMENT_LORD_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.CORROSIVER_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.LIGHTNING_WORM_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.DRACOGNATHUS_REVIVAL_HORN);
                event.accept(GeneratedMod.FLAMOAR_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.PRIMORDIER_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.FROST_YETI_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.FLYING_FIREBALL_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.IRON_TEETH_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SEDIMENT_LORD_TOTEM);
                event.accept(GeneratedMod.DROUGHT_WILDFIRE_HELM);
                event.accept(GeneratedMod.SEA_WAVE_HELM);
                event.accept(GeneratedMod.BULGE_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SEA_WAVE_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.DROUGHT_WILDFIRE_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.DROUGHT_BLAZE_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SECOND_ROUNDER_ZOMBIE_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SECOND_ROUNDER_SKELETON_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SECOND_ROUNDER_SPIDER_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.ENDER_STOWAWAY_SERVANT_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_COLONIAL_SOLDIER_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_RIOTER_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_PIONEER_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_FIRE_APOSTLE_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_INTRUDER_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_FLAME_WIZARD_SPAWN_EGG);
                event.accept(GeneratedMod.WAR_PYTHON_SPAWN_EGG);
                event.accept(GeneratedMod.ROYAL_WAR_PYTHON_SPAWN_EGG);
                event.accept(GeneratedMod.SERPON_COMBAT_EXECUTER_SPAWN_EGG);
                event.accept(GeneratedMod.SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT_SPAWN_EGG);
            }
            event.accept(GeneratedMod.MUSIC_DISC_WITHER_THEME);
            event.accept(GeneratedMod.MUSIC_DISC_POLARGEIST);
            event.accept(GeneratedMod.MUSIC_DISC_CANT_LET_GO);
            event.accept(GeneratedMod.MUSIC_DISC_GREEN_SMITH_THEME);
            event.accept(GeneratedMod.MUSIC_DISC_WARLORD_THEME);
        } else if (CreativeModeTabs.COMBAT.location().equals(tabId)) {
            event.accept(GeneratedMod.PRISMARINE_SWORD);
            event.accept(GeneratedMod.PRISMARINE_AXE);
            event.accept(GeneratedMod.PRISMARINE_BOW);
            event.accept(GeneratedMod.PRISMARINE_ARROW);
            event.accept(GeneratedMod.PRISMARINE_HELMET);
            event.accept(GeneratedMod.PRISMARINE_CHESTPLATE);
            event.accept(GeneratedMod.PRISMARINE_LEGGINGS);
            event.accept(GeneratedMod.PRISMARINE_BOOTS);
            event.accept(GeneratedMod.WITHER_WARLORD_HELMET);
            event.accept(GeneratedMod.WITHERING_SABER);
            event.accept(GeneratedMod.HELLFIRE_SABER);
            event.accept(GeneratedMod.FELL_AXE);
            event.accept(GeneratedMod.FELL_SPEAR);
            event.accept(GeneratedMod.IRON_SPEAR);
            event.accept(GeneratedMod.DIAMOND_SPEAR);
            event.accept(GeneratedMod.NETHERITE_SPEAR);
            event.accept(GeneratedMod.DARK_SPEAR);
            event.accept(GeneratedMod.FELL_HAMMER);
            event.accept(GeneratedMod.FELL_SHIELD);
            event.accept(GeneratedMod.FELL_BOW);
            event.accept(GeneratedMod.FELL_CROSSBOW);
        } else if (CreativeModeTabs.SPAWN_EGGS.location().equals(tabId)) {
            event.accept(GeneratedMod.PRISMARINE_SOLDIER_SPAWN_EGG);
            event.accept(GeneratedMod.WILD_MAGE_SPAWN_EGG);
            event.accept(GeneratedMod.WILD_MAGE_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.REAPER_NECROMANCER_SPAWN_EGG);
            event.accept(GeneratedMod.REAPER_NECROMANCER_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.GREEN_SMITH_SPAWN_EGG);
            event.accept(GeneratedMod.GREEN_SMITH_SERVANT_SPAWN_EGG);
            event.accept(GeneratedMod.IFRIT_SPAWN_EGG);
            event.accept(GeneratedMod.SCORCHBACK_SPAWN_EGG);
            event.accept(GeneratedMod.SCORCHBACK_EGG);
            event.accept(GeneratedMod.SOUL_REAPER_SPAWN_EGG);
            event.accept(GeneratedMod.SOUL_REAPER_SERVANT_SPAWN_EGG);
        }
    }
}
