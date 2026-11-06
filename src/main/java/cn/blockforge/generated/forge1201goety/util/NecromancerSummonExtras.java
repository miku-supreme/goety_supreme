package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.undead.HauntedArmorServant;
import com.Polarice3.Goety.common.entities.ally.undead.PhantomServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.Polarice3.Goety.utils.SoundUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.util.HauntedArmorDressing;
import cn.blockforge.generated.goetysupreme.api.NecromancerFocusHolder;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerNativeCapMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyForgeRingArmorMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerNativeCapMixin;

public final class NecromancerSummonExtras {

    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();
    private static final String LOG = "[goety_supreme][extra-summon] ";

    private static final String FOCUS_HUNTING = "hunting_focus";
    private static final String FOCUS_PHANTASM = "phantasm_focus";
    private static final String FOCUS_MAULING = "mauling_focus";
    private static final String FOCUS_SLIMY = "slimy_focus";
    private static final String FOCUS_GHASTLY = "ghastly_focus";
    private static final String SPIDER_NEST = "spider_nest";

    private static final String FOCUS_SPIDER = "spider_focus";

    private static final String FOCUS_FROST_HUNTER = "urbhadhach_focus";

    private static final String FOCUS_SPOOKY = "spooky_focus";

    private static final String FOCUS_GUARDIAN = "guardian_focus";

    private static final String FOCUS_RAGING_SHARK = "raging_shark_focus";

    private static final String FOCUS_FLYING_FIREBALL = "flying_fireball_focus";

    private static final String FOCUS_HAUNT = "haunt_focus";

    private static final String FOCUS_REDSTONE = "redstone_focus";

    private static final String FOCUS_RIGID = "rigid_focus";

    private static final String FOCUS_POLEMISTIS = "polemistis_focus";

    private static final String FOCUS_KAKOURGOS = "kakourgos_focus";

    private static final String CATACLYSM_NAMESPACE = "goety_cataclysm";

    private static final String TWILIGHT_NAMESPACE = "goetytwilight";
    private static final String IRON_NAMESPACE = "goetyiron";

    private static final String FOCUS_ICE_CRYSTAL = "ice_crystal_focus";
    private static final String FOCUS_SNOW_GUARDIAN = "snow_guardian_focus";
    private static final String FOCUS_STABLE_ICE_CORE = "stable_ice_core_focus";
    private static final String FOCUS_ALPHA_WOLF = "alpha_wolf_focus";

    private static final String FOCUS_KING_SPIDER = "king_spider_focus";
    private static final String BEETLE_NEST = "beetle_nest";
    private static final String FOCUS_FIERY = "fiery_focus";

    private static final String FOCUS_VEXING = "vexing_focus";

    private static final ResourceLocation WITHERED_MANUSCRIPT =
            ResourceLocation.fromNamespaceAndPath("goety", "withered_manuscript");

    private static boolean isOwnFocus(String key) {
        return FOCUS_RAGING_SHARK.equals(key) || FOCUS_FLYING_FIREBALL.equals(key);
    }

    private static final ResourceLocation SKELETON_WOLF = goety("skeleton_wolf");
    private static final ResourceLocation PHANTOM_SERVANT = goety("phantom_servant");

    private static final int PHANTOM_NAMELESS_POTENCY = 2;

    private static final double PHANTOM_NAMELESS_HEIGHT = 3.0D;
    private static final ResourceLocation SPIDER_SERVANT = goety("spider_servant");
    private static final ResourceLocation WEB_SPIDER = goety("web_spider_servant");
    private static final ResourceLocation BLACK_WOLF = goety("black_wolf");
    private static final ResourceLocation ICY_SPIDER = goety("icy_spider_servant");
    private static final ResourceLocation WINTER_WOLF = goety("winter_wolf");
    private static final ResourceLocation GNASHER = goety("gnasher");
    private static final ResourceLocation SNAPPER = goety("snapper");

    private static final ResourceLocation POLAR_BEAR_SERVANT = goety("polar_bear_servant");

    private static final ResourceLocation GUARDIAN_SERVANT = goety("guardian_servant");

    private static final ResourceLocation DOOM_FIVE_HEAD_SHARK =
            goetySupreme("doom_five_head_shark");

    private static final ResourceLocation FLYING_FIREBALL_SERVANT =
            goetySupreme("flying_fireball_servant");
    private static final ResourceLocation SLIME_SERVANT = goety("slime_servant");
    private static final ResourceLocation TROPICAL_SLIME = goety("tropical_slime_servant");
    private static final ResourceLocation HELLHOUND = goety("hellhound");
    private static final ResourceLocation MAGMA_CUBE_SERVANT = goety("magma_cube_servant");
    private static final ResourceLocation MINI_GHAST = goety("mini_ghast");
    private static final ResourceLocation GHAST_SERVANT = goety("ghast_servant");
    private static final ResourceLocation MALGHAST = goety("malghast");

    private static final ResourceLocation CRIMSON_SPIDER = goetyominus("crimson_spider_servant");

    private static final ResourceLocation URBHADHACH_SERVANT = goetyominus("urbhadhach_servant");

    private static final ResourceLocation BORDER_WRAITH_SERVANT = goety("border_wraith_servant");

    private static final ResourceLocation HAUNT = goety("haunt");

    private static final ResourceLocation SECOND_ROUNDER_SPIDER =
            goetySupreme("second_rounder_spider_servant");
    private static final ResourceLocation SECOND_ROUND_WORLD =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "second_round_world");

    private static final ResourceLocation GRAVE_CUBE = goety("grave_cube");

    private static final ResourceLocation BEAR_SERVANT = goety("bear_servant");

    private static final ResourceLocation DREDEN_SERVANT = goetyominus("dreden_servant");

    private static final ResourceLocation SYMBIOCTO = cataclysm("symbiocto_servant");

    private static final ResourceLocation DROWNED_HOST = cataclysm("drowned_host_servant");

    private static final ResourceLocation URCHINKIN = cataclysm("urchinkin_servant");

    private static final ResourceLocation ICE_CRYSTAL_SERVANT = twilight("ice_crystal_servant");
    private static final ResourceLocation SNOW_GUARDIAN_SERVANT = twilight("snow_guardian_servant");
    private static final ResourceLocation STABLE_ICE_CORE_SERVANT =
            twilight("stable_ice_core_servant");
    private static final ResourceLocation TWILIGHT_WINTER_WOLF = twilight("winter_wolf_servant");
    private static final ResourceLocation MIST_WOLF_SERVANT = twilight("mist_wolf_servant");
    private static final ResourceLocation KING_SPIDER_SERVANT = twilight("king_spider_servant");
    private static final ResourceLocation TWILIGHT_WRAITH_SERVANT =
            twilight("twilight_wraith_servant");
    private static final ResourceLocation SLIME_BEETLE_SERVANT = twilight("slime_beetle_servant");
    private static final ResourceLocation PINCH_BEETLE_SERVANT = twilight("pinch_beetle_servant");
    private static final ResourceLocation CITADEL_KEEPER_SERVANT = iron("citadel_keeper_servant");

    private static final List<ResourceLocation> CAP_MOSSY = List.of(WEB_SPIDER, BLACK_WOLF);

    private static final List<ResourceLocation> CAP_CAIRN = List.of(ICY_SPIDER, WINTER_WOLF);

    private static final List<ResourceLocation> CAP_DROWNED_SNAPPER =
            List.of(SNAPPER, TROPICAL_SLIME);

    private static final List<ResourceLocation> CAP_WITHER_GHAST =
            List.of(MINI_GHAST, GHAST_SERVANT, MALGHAST);

    private static final List<ResourceLocation> CAP_WITHER_GHAST_ELITE =
            List.of(GHAST_SERVANT, MALGHAST);

    private static final List<ResourceLocation> CAP_WITHER_MAGMA = List.of(MAGMA_CUBE_SERVANT);

    private static final List<ResourceLocation> CAP_WITHER_SPIDER =
            List.of(HELLHOUND, CRIMSON_SPIDER, SECOND_ROUNDER_SPIDER);

    private static final List<ResourceLocation> CAP_MOSSY_SLIME = List.of(SLIME_SERVANT);

    private static final List<ResourceLocation> CAP_FROST_HUNTER = List.of(URBHADHACH_SERVANT);

    private static final List<ResourceLocation> CAP_BORDER_WRAITH = List.of(BORDER_WRAITH_SERVANT);

    private static final List<ResourceLocation> CAP_POLAR_BEAR = List.of(POLAR_BEAR_SERVANT);

    private static final List<ResourceLocation> CAP_DROWNED_SHARK = List.of(DOOM_FIVE_HEAD_SHARK);

    private static final List<ResourceLocation> CAP_DROWNED_GUARDIAN = List.of(GUARDIAN_SERVANT);

    private static final List<ResourceLocation> CAP_SUNKEN_SLIME = List.of(TROPICAL_SLIME);

    private static final List<ResourceLocation> CAP_WITHER_FLYING_FIREBALL =
            List.of(FLYING_FIREBALL_SERVANT);

    private static final List<ResourceLocation> CAP_TWILIGHT_ICE_CRYSTAL =
            List.of(ICE_CRYSTAL_SERVANT);
    private static final List<ResourceLocation> CAP_TWILIGHT_SNOW_GUARDIAN =
            List.of(SNOW_GUARDIAN_SERVANT);
    private static final List<ResourceLocation> CAP_TWILIGHT_STABLE_ICE_CORE =
            List.of(STABLE_ICE_CORE_SERVANT);
    private static final List<ResourceLocation> CAP_TWILIGHT_ALPHA_WOLF =
            List.of(TWILIGHT_WINTER_WOLF);

    private static final List<ResourceLocation> CAP_WOLF_CHIEF_FAMILY =
            List.of(MIST_WOLF_SERVANT, KING_SPIDER_SERVANT);
    private static final List<ResourceLocation> CAP_TWILIGHT_WRAITH =
            List.of(TWILIGHT_WRAITH_SERVANT);

    private static final List<ResourceLocation> CAP_DRAUGR_SNOW_GUARDIAN =
            List.of(SNOW_GUARDIAN_SERVANT);
    private static final List<ResourceLocation> CAP_MOSSY_BEETLE =
            List.of(SLIME_BEETLE_SERVANT, PINCH_BEETLE_SERVANT);
    private static final List<ResourceLocation> CAP_WITHER_CITADEL = List.of(CITADEL_KEEPER_SERVANT);

    private static final List<ResourceLocation> CAP_HAUNT = List.of(HAUNT);

    private static final List<ResourceLocation> CAP_REDSTONE = List.of(GRAVE_CUBE);

    private static final List<ResourceLocation> CAP_BEAR = List.of(BEAR_SERVANT);

    private static final List<ResourceLocation> CAP_DREDEN = List.of(DREDEN_SERVANT);

    private static final List<ResourceLocation> CAP_CATACLYSM_OCTO =
            List.of(SYMBIOCTO, DROWNED_HOST);

    private static final List<ResourceLocation> CAP_CATACLYSM_URCHIN = List.of(URCHINKIN);

    private static final Set<ResourceLocation> WITHER_FLYING_TYPES = Set.of(
            MINI_GHAST, GHAST_SERVANT, MALGHAST, FLYING_FIREBALL_SERVANT);

    private static final Set<ResourceLocation> WITHER_GHAST_TYPES = Set.of(
            MINI_GHAST, GHAST_SERVANT, MALGHAST);

    private static final double GHAST_MIN_HORIZONTAL_DISTANCE = 8.5D;

    private static final double GHAST_MAX_HORIZONTAL_DISTANCE = 12.0D;

    private static final double GHAST_MIN_VERTICAL_OFFSET = 3.0D;

    private static final int GHAST_SPAWN_ATTEMPTS = 12;

    private static final ResourceLocation CRYPT_SLIME_SERVANT = goety("crypt_slime_servant");

    private static final ResourceLocation BONE_SPIDER_SERVANT = goety("bone_spider_servant");

    private static final ResourceLocation HAUNTED_ARMOR_SERVANT = goety("haunted_armor_servant");
    private static final List<ResourceLocation> CAP_CRYPT_SLIME = List.of(CRYPT_SLIME_SERVANT);

    private static final List<ResourceLocation> CAP_CRYPT_SPIDER =
            List.of(BONE_SPIDER_SERVANT, ICY_SPIDER, SKELETON_WOLF, WINTER_WOLF);
    private static final List<ResourceLocation> CAP_CRYPT_WRAITH = List.of(BORDER_WRAITH_SERVANT);
    private static final List<ResourceLocation> CAP_CRYPT_ARMOR = List.of(HAUNTED_ARMOR_SERVANT);

    private static final Set<ResourceLocation> EXTRA_SUMMON_TYPES = Set.of(
            SKELETON_WOLF, PHANTOM_SERVANT, SPIDER_SERVANT, WEB_SPIDER, BLACK_WOLF,
            ICY_SPIDER, WINTER_WOLF, GNASHER, SNAPPER, SLIME_SERVANT,
            TROPICAL_SLIME, HELLHOUND, MAGMA_CUBE_SERVANT, MINI_GHAST, GHAST_SERVANT,
            MALGHAST, CRIMSON_SPIDER, URBHADHACH_SERVANT, BORDER_WRAITH_SERVANT,
            SECOND_ROUNDER_SPIDER, CRYPT_SLIME_SERVANT, BONE_SPIDER_SERVANT, HAUNTED_ARMOR_SERVANT,
            POLAR_BEAR_SERVANT, GUARDIAN_SERVANT, DOOM_FIVE_HEAD_SHARK, FLYING_FIREBALL_SERVANT,
            HAUNT, GRAVE_CUBE, BEAR_SERVANT, DREDEN_SERVANT, SYMBIOCTO, DROWNED_HOST,
            URCHINKIN, ICE_CRYSTAL_SERVANT, SNOW_GUARDIAN_SERVANT, STABLE_ICE_CORE_SERVANT,
            TWILIGHT_WINTER_WOLF, MIST_WOLF_SERVANT, KING_SPIDER_SERVANT, TWILIGHT_WRAITH_SERVANT,
            SLIME_BEETLE_SERVANT, PINCH_BEETLE_SERVANT, CITADEL_KEEPER_SERVANT);

    private NecromancerSummonExtras() {
    }

    public static boolean isExtraSummonType(EntityType<?> type) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(type);
        return key != null && EXTRA_SUMMON_TYPES.contains(key);
    }

    public static List<LivingEntity> excludeExtraSummons(List<LivingEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return entities;
        }
        List<LivingEntity> nativeOnly = new ArrayList<>(entities.size());
        for (LivingEntity entity : entities) {
            if (!isExtraSummonType(entity.getType())) {
                nativeOnly.add(entity);
            }
        }
        return nativeOnly;
    }

    public static List<LivingEntity> excludeExtraSummonsKeepingNative(List<LivingEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return entities;
        }
        List<LivingEntity> nativeOnly = new ArrayList<>(entities.size());
        for (LivingEntity entity : entities) {
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            if (key == null || !EXTRA_SUMMON_TYPES.contains(key)) {
                nativeOnly.add(entity);
                continue;
            }
            boolean direct = entity.getPersistentData().getBoolean(SummonCapUtil.DIRECT_SUMMON_TAG);
            if (!direct && !SummonCapUtil.isSlimeServant(key)) {
                nativeOnly.add(entity);
            }
        }
        return nativeOnly;
    }

    private static ResourceLocation goety(String path) {
        return ResourceLocation.fromNamespaceAndPath("goety", path);
    }

    private static ResourceLocation goetyominus(String path) {
        return ResourceLocation.fromNamespaceAndPath(OminousCompat.namespace(), path);
    }

    private static ResourceLocation goetySupreme(String path) {
        return ResourceLocation.fromNamespaceAndPath("goety_supreme", path);
    }

    private static ResourceLocation cataclysm(String path) {
        return ResourceLocation.fromNamespaceAndPath(CATACLYSM_NAMESPACE, path);
    }

    private static ResourceLocation twilight(String path) {
        return ResourceLocation.fromNamespaceAndPath(TWILIGHT_NAMESPACE, path);
    }

    private static ResourceLocation iron(String path) {
        return ResourceLocation.fromNamespaceAndPath(IRON_NAMESPACE, path);
    }

    private static boolean isSecondRoundWorld(AbstractNecromancer necro) {
        return necro.level().dimension().location().equals(SECOND_ROUND_WORLD);
    }

    private static boolean isAquatic(AbstractNecromancer necro) {
        if (necro.isInWater() || necro.isUnderWater()) {
            return true;
        }
        BlockPos center = necro.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-2, -1, -2),
                center.offset(2, 1, 2))) {
            if (necro.level().getFluidState(pos).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tryGrantFocus(AbstractNecromancer necro, Player player, ItemStack stack) {
        if (necro.level().isClientSide()) {
            return false;
        }
        if (necro.getTrueOwner() != player) {
            return false;
        }
        Variant variant = variantOf(necro);
        NecromancerFocusHolder sheetHolder = holder(necro);
        if (sheetHolder == null) {
            return false;
        }
        Item slotItem = variant == Variant.WITHER
                ? ForgeRegistries.ITEMS.getValue(WITHERED_MANUSCRIPT)
                : GeneratedMod.FORBIDDEN_CHEAT_SHEET.get();
        if (FocusSlotRules.tryFeedSlotItem(necro, player, stack, slotItem,
                sheetHolder.goetySupreme$cheatSheets(), refusalSound(necro),
                sheetHolder::goetySupreme$setCheatSheets)) {
            return true;
        }
        if (necro.getNecroLevel() < 1) {
            return false;
        }
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (itemId == null) {
            return false;
        }
        String namespace = itemId.getNamespace();
        String key = itemId.getPath();
        boolean ownNamespace = GeneratedMod.MOD_ID.equals(namespace);
        if (!ownNamespace && !"goety".equals(namespace)
                && !OminousCompat.isOminousNamespace(namespace)
                && !OminousCompat.isOminousPath(key)
                && !CATACLYSM_NAMESPACE.equals(namespace)
                && !TWILIGHT_NAMESPACE.equals(namespace)
                && !IRON_NAMESPACE.equals(namespace)) {
            return false;
        }
        if (ownNamespace && !isOwnFocus(key)) {
            return false;
        }
        if (!accepts(variant, key)) {
            return false;
        }
        Set<String> grants = grants(necro);
        if (grants == null) {
            return false;
        }
        if (grants.contains(key)) {
            return false;
        }
        int slots = FocusSlotRules.maxSlots(FocusSlotRules.NECROMANCER_BASE_SLOTS,
                sheetHolder.goetySupreme$cheatSheets());
        int usedSlots = grants.size() + (variant == Variant.CRYPT ? 1 : 0);
        if (usedSlots >= slots) {
            FocusSlotRules.refuse(necro, refusalSound(necro));
            return true;
        }
        grants.add(key);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        necro.playLaughSound();
        if (necro.level() instanceof ServerLevel level) {
            ServerParticleUtil.summonUndeadParticles(level, necro);
        }
        return true;
    }

    private static boolean accepts(Variant variant, String key) {
        return switch (variant) {
            case NORMAL -> FOCUS_HUNTING.equals(key) || FOCUS_PHANTASM.equals(key)
                    || SPIDER_NEST.equals(key) || FOCUS_HAUNT.equals(key)
                    || FOCUS_REDSTONE.equals(key)
                    || (FOCUS_VEXING.equals(key)
                        && net.minecraftforge.fml.ModList.get().isLoaded(TWILIGHT_NAMESPACE));
            case MOSSY -> FOCUS_HUNTING.equals(key) || SPIDER_NEST.equals(key)
                    || FOCUS_SLIMY.equals(key) || FOCUS_MAULING.equals(key)
                    || FOCUS_ALPHA_WOLF.equals(key) || FOCUS_KING_SPIDER.equals(key)
                    || BEETLE_NEST.equals(key);
            case CAIRN -> FOCUS_HUNTING.equals(key) || SPIDER_NEST.equals(key)
                    || FOCUS_MAULING.equals(key) || FOCUS_HAUNT.equals(key)
                    || FOCUS_RIGID.equals(key) || FOCUS_ICE_CRYSTAL.equals(key)
                    || FOCUS_SNOW_GUARDIAN.equals(key) || FOCUS_STABLE_ICE_CORE.equals(key)
                    || FOCUS_ALPHA_WOLF.equals(key);
            case DROWNED -> FOCUS_HUNTING.equals(key) || FOCUS_MAULING.equals(key)
                    || FOCUS_SLIMY.equals(key) || FOCUS_RAGING_SHARK.equals(key)
                    || FOCUS_GUARDIAN.equals(key) || FOCUS_POLEMISTIS.equals(key)
                    || FOCUS_KAKOURGOS.equals(key);
            case SUNKEN -> FOCUS_SLIMY.equals(key) || FOCUS_RAGING_SHARK.equals(key)
                    || FOCUS_POLEMISTIS.equals(key) || FOCUS_KAKOURGOS.equals(key);
            case WITHER -> FOCUS_HUNTING.equals(key) || FOCUS_SLIMY.equals(key)
                    || FOCUS_GHASTLY.equals(key) || FOCUS_SPIDER.equals(key)
                    || FOCUS_FLYING_FIREBALL.equals(key) || FOCUS_FIERY.equals(key);
            case DRAUGR -> FOCUS_FROST_HUNTER.equals(key) || FOCUS_SPOOKY.equals(key)
                    || FOCUS_MAULING.equals(key) || FOCUS_RIGID.equals(key)
                    || FOCUS_SNOW_GUARDIAN.equals(key);
            case CRYPT -> FOCUS_SLIMY.equals(key) || SPIDER_NEST.equals(key)
                    || FOCUS_SPOOKY.equals(key) || FOCUS_FROST_HUNTER.equals(key)
                    || FOCUS_MAULING.equals(key) || FOCUS_HAUNT.equals(key)
                    || FOCUS_RIGID.equals(key) || FOCUS_ICE_CRYSTAL.equals(key)
                    || FOCUS_SNOW_GUARDIAN.equals(key) || FOCUS_STABLE_ICE_CORE.equals(key)
                    || FOCUS_ALPHA_WOLF.equals(key);
        };
    }

    private static void afterSummon(AbstractNecromancer necro, ServerLevel level, boolean idleSummon) {
        NecromancerFocusHolder holder = holder(necro);
        boolean topUp = holder != null && holder.goetySupreme$extraTopUpPending();
        if (holder != null) {
            holder.goetySupreme$setExtraTopUpPending(false);
        }
        Variant variant = variantOf(necro);
        int grantCount = holder == null ? 0 : holder.goetySupreme$necromancerFocusGrants().size();
        LOGGER.info("{}enter necro={} variant={} level={} grants={} idle={} topUp={}",
                LOG, necro.getUUID(), variant, necro.getNecroLevel(), grantCount, idleSummon, topUp);
        if (variant == Variant.CRYPT) {
            Option inherent = new Option(HAUNTED_ARMOR_SERVANT, 2, 4, CAP_CRYPT_ARMOR, 0);
            int inherentOwned = countOwned(level, necro, inherent.capTypes);
            if (inherentOwned < inherent.cap) {
                float inherentRoll = necro.getRandom().nextFloat();
                if (topUp || inherentRoll < EXTRA_SUMMON_CHANCE) {
                    int inherentSpawned = spawnOption(level, necro, inherent, Variant.CRYPT, idleSummon);
                    LOGGER.info("{}crypt-inherent: owned={}/{} roll={} spawned={}",
                            LOG, inherentOwned, inherent.cap, inherentRoll, inherentSpawned);
                    if (inherentSpawned > 0) {
                        return;
                    }
                    LOGGER.info("{}crypt-inherent spawned=0 -> fall through to focus options", LOG);
                } else {
                    LOGGER.info("{}crypt-inherent missed: owned={}/{} roll={} chance={}",
                            LOG, inherentOwned, inherent.cap, inherentRoll, EXTRA_SUMMON_CHANCE);
                }
            } else {
                LOGGER.info("{}crypt-inherent full: owned={}/{}", LOG, inherentOwned, inherent.cap);
            }
        }
        if (necro.getNecroLevel() < 1) {
            LOGGER.info("{}skip: necroLevel<1 (level={})", LOG, necro.getNecroLevel());
            return;
        }
        Set<String> grants = grants(necro);
        if (grants == null || grants.isEmpty()) {
            LOGGER.info("{}skip: grants empty (holder={})", LOG, holder != null);
            return;
        }
        List<Option> options = optionsFor(necro, variant, grants);
        if (options.isEmpty()) {
            LOGGER.info("{}skip: optionsFor empty variant={} grants={} (entity missing / environment)",
                    LOG, variant, grants);
            return;
        }
        List<Option> spawnable = new ArrayList<>(options.size());
        for (Option option : options) {
            if (ForgeRegistries.ENTITY_TYPES.containsKey(option.type)
                    && roomLeft(level, necro, option) > 0) {
                spawnable.add(option);
            }
        }
        if (spawnable.isEmpty()) {
            for (Option option : options) {
                LOGGER.info("{}  option entity={} typePresent={} room={} cap={} owned={}",
                        LOG, option.type,
                        ForgeRegistries.ENTITY_TYPES.containsKey(option.type),
                        roomLeft(level, necro, option), option.cap,
                        countOwned(level, necro, option.capTypes));
            }
            LOGGER.info("{}skip: spawnable empty (all lines full or entity missing) options={}",
                    LOG, options.size());
            return;
        }
        int misses = holder == null ? 0 : holder.goetySupreme$extraSummonMisses();
        boolean wither = variant == Variant.WITHER;
        float chance = wither ? WITHER_EXTRA_SUMMON_CHANCE : EXTRA_SUMMON_CHANCE;
        int pityMisses = wither ? WITHER_EXTRA_SUMMON_PITY_MISSES : EXTRA_SUMMON_PITY_MISSES;
        boolean pity = misses >= pityMisses;
        boolean guaranteed = topUp;
        float roll = necro.getRandom().nextFloat();
        if (!guaranteed && !pity && roll >= chance) {
            if (holder != null) {
                holder.goetySupreme$setExtraSummonMisses(misses + 1);
            }
            LOGGER.info("{}miss: roll={} chance={} misses={}->{} pityAt={} pity={} guaranteed={}",
                    LOG, roll, chance, misses, misses + 1, pityMisses, pity, guaranteed);
            return;
        }
        LOGGER.info("{}trigger: variant={} roll={} chance={} misses={} pity={} topUp={} spawnable={}",
                LOG, variant, roll, chance, misses, pity, guaranteed, spawnable.size());
        List<Option> candidates = new ArrayList<>(spawnable);
        int spawned = 0;
        Option choice = null;
        while (!candidates.isEmpty()) {
            choice = pick(candidates, necro);
            spawned = spawnOption(level, necro, choice, variant, idleSummon);
            if (spawned > 0) {
                break;
            }
            LOGGER.info("{}spawnOption=0 option entity={} cap={} owned={} room={} -> retry ({} left)",
                    LOG, choice.type, choice.cap, countOwned(level, necro, choice.capTypes),
                    roomLeft(level, necro, choice), candidates.size() - 1);
            candidates.remove(choice);
        }
        if (spawned > 0) {
            if (holder != null) {
                holder.goetySupreme$setExtraSummonMisses(0);
            }
            LOGGER.info("{}success: entity={} spawned={} variant={} idle={}",
                    LOG, choice == null ? "?" : choice.type, spawned, variant, idleSummon);
        } else {
            LOGGER.info("{}spawnOption=0 for all {} candidates -> treat as not triggered",
                    LOG, spawnable.size());
        }
    }

    public static void afterIdleSummon(AbstractNecromancer necro, ServerLevel level) {
        afterSummon(necro, level, true);
    }

    public static void afterCombatSummon(AbstractNecromancer necro, ServerLevel level) {
        afterSummon(necro, level, false);
    }

    public static void markExtraTopUp(AbstractNecromancer necro) {
        NecromancerFocusHolder holder = holder(necro);
        if (holder != null) {
            holder.goetySupreme$setExtraTopUpPending(true);
        }
    }

    private static final float EXTRA_SUMMON_CHANCE = 0.5F;

    private static final int EXTRA_SUMMON_PITY_MISSES = 2;

    private static final float WITHER_EXTRA_SUMMON_CHANCE = 0.75F;

    private static final int WITHER_EXTRA_SUMMON_PITY_MISSES = 1;

    private static List<Option> optionsFor(AbstractNecromancer necro, Variant variant,
            Set<String> grants) {
        Set<String> accepted = new java.util.HashSet<>();
        for (String key : grants) {
            if (accepts(variant, key)) {
                accepted.add(key);
            }
        }
        grants = accepted;
        List<Option> options = new ArrayList<>();
        boolean hunting = grants.contains(FOCUS_HUNTING);
        boolean maxed = necro.getNecroLevel() >= AbstractNecromancer.MAX_LEVEL;
        switch (variant) {
            case NORMAL -> {
                if (grants.contains(SPIDER_NEST)) {
                    options.add(new Option(SPIDER_SERVANT, 2, 4, List.of(SPIDER_SERVANT), 0));
                }
                if (hunting) {
                    options.add(new Option(SKELETON_WOLF, 2, 2, List.of(SKELETON_WOLF), 0));
                }
                if (grants.contains(FOCUS_PHANTASM)) {
                    options.add(new Option(PHANTOM_SERVANT, 2, 2, List.of(PHANTOM_SERVANT), 0));
                }
                if (grants.contains(FOCUS_VEXING) && grants.contains(FOCUS_PHANTASM)) {
                    options.add(new Option(TWILIGHT_WRAITH_SERVANT, 2, 2,
                            CAP_TWILIGHT_WRAITH, 0));
                }
                if (grants.contains(FOCUS_HAUNT)) {
                    options.add(new Option(HAUNT, 3, 6, CAP_HAUNT, 0));
                }
                if (grants.contains(FOCUS_REDSTONE)) {
                    options.add(new Option(GRAVE_CUBE, 2, 4, CAP_REDSTONE, 0));
                }
            }
            case MOSSY -> {
                if (grants.contains(SPIDER_NEST)) {
                    options.add(new Option(WEB_SPIDER, 2, 4, CAP_MOSSY, 0));
                }
                if (hunting) {
                    options.add(new Option(BLACK_WOLF, 2, 4, CAP_MOSSY, 0));
                }
                if (grants.contains(FOCUS_SLIMY)) {
                    options.add(new Option(SLIME_SERVANT, 2, 2, CAP_MOSSY_SLIME, maxed ? 4 : 3));
                }
                if (grants.contains(FOCUS_MAULING)) {
                    options.add(new Option(BEAR_SERVANT, 1, 2, CAP_BEAR, 0));
                }
                boolean bothChiefs = grants.contains(FOCUS_ALPHA_WOLF)
                        && grants.contains(FOCUS_KING_SPIDER);
                int chiefCap = bothChiefs ? 3 : 2;
                if (grants.contains(FOCUS_ALPHA_WOLF)) {
                    options.add(new Option(MIST_WOLF_SERVANT, 2, chiefCap,
                            CAP_WOLF_CHIEF_FAMILY, 0));
                }
                if (grants.contains(FOCUS_KING_SPIDER)) {
                    options.add(new Option(KING_SPIDER_SERVANT, 2, chiefCap,
                            CAP_WOLF_CHIEF_FAMILY, 0));
                }
                if (grants.contains(BEETLE_NEST)) {
                    options.add(new Option(SLIME_BEETLE_SERVANT, 2, 2, CAP_MOSSY_BEETLE, 0));
                }
            }
            case CAIRN -> {
                if (grants.contains(SPIDER_NEST)) {
                    options.add(new Option(ICY_SPIDER, 2, 4, CAP_CAIRN, 0));
                }
                if (hunting) {
                    options.add(new Option(WINTER_WOLF, 2, 4, CAP_CAIRN, 0));
                }
                if (grants.contains(FOCUS_MAULING)) {
                    options.add(new Option(POLAR_BEAR_SERVANT, 1, 2, CAP_POLAR_BEAR, 0));
                }
                if (grants.contains(FOCUS_HAUNT)) {
                    options.add(new Option(HAUNT, 3, 6, CAP_HAUNT, 0));
                }
                if (grants.contains(FOCUS_RIGID)) {
                    options.add(new Option(DREDEN_SERVANT, 2, 2, CAP_DREDEN, 0));
                }
                if (grants.contains(FOCUS_ICE_CRYSTAL)) {
                    options.add(new Option(ICE_CRYSTAL_SERVANT, 2, 2,
                            CAP_TWILIGHT_ICE_CRYSTAL, 0));
                }
                if (grants.contains(FOCUS_SNOW_GUARDIAN)) {
                    options.add(new Option(SNOW_GUARDIAN_SERVANT, 2, 2,
                            CAP_TWILIGHT_SNOW_GUARDIAN, 0));
                }
                if (grants.contains(FOCUS_STABLE_ICE_CORE)) {
                    options.add(new Option(STABLE_ICE_CORE_SERVANT, 2, 2,
                            CAP_TWILIGHT_STABLE_ICE_CORE, 0));
                }
                if (grants.contains(FOCUS_ALPHA_WOLF)) {
                    options.add(new Option(TWILIGHT_WINTER_WOLF, 2, 2,
                            CAP_TWILIGHT_ALPHA_WOLF, 0));
                }
            }
            case DRAUGR -> {
                if (grants.contains(FOCUS_FROST_HUNTER)) {
                    options.add(new Option(URBHADHACH_SERVANT, 1, 2, CAP_FROST_HUNTER, 0));
                }
                if (grants.contains(FOCUS_SPOOKY)) {
                    options.add(new Option(BORDER_WRAITH_SERVANT, 2, 2, CAP_BORDER_WRAITH, 0));
                }
                if (grants.contains(FOCUS_MAULING)) {
                    options.add(new Option(POLAR_BEAR_SERVANT, 1, 2, CAP_POLAR_BEAR, 0));
                }
                if (grants.contains(FOCUS_RIGID)) {
                    options.add(new Option(DREDEN_SERVANT, 2, 2, CAP_DREDEN, 0));
                }
                if (grants.contains(FOCUS_SNOW_GUARDIAN)) {
                    options.add(new Option(SNOW_GUARDIAN_SERVANT, 2, 4,
                            CAP_DRAUGR_SNOW_GUARDIAN, 0));
                }
            }
            case DROWNED -> {
                boolean aquatic = isAquatic(necro);
                if (aquatic && grants.contains(FOCUS_MAULING)) {
                    options.add(new Option(GNASHER, 1, 2, List.of(GNASHER), 0, 20));
                }
                if (aquatic && hunting) {
                    options.add(new Option(SNAPPER, 2, 4, CAP_DROWNED_SNAPPER, 0, 40));
                }
                if (grants.contains(FOCUS_SLIMY)) {
                    options.add(new Option(TROPICAL_SLIME, 2, 4, CAP_DROWNED_SNAPPER,
                            maxed ? 4 : 3, 40));
                }
                if (aquatic && grants.contains(FOCUS_RAGING_SHARK)) {
                    options.add(new Option(DOOM_FIVE_HEAD_SHARK, 1, 1, CAP_DROWNED_SHARK, 0, 20));
                }
                if (grants.contains(FOCUS_GUARDIAN)) {
                    options.add(new Option(GUARDIAN_SERVANT, 2, 2, CAP_DROWNED_GUARDIAN, 0, 40));
                }
                if (grants.contains(FOCUS_POLEMISTIS)) {
                    options.add(new Option(SYMBIOCTO, 2, 4, CAP_CATACLYSM_OCTO, 0, 1,
                            List.of(SYMBIOCTO), 2));
                    options.add(new Option(DROWNED_HOST, 2, 4, CAP_CATACLYSM_OCTO, 0, 1,
                            List.of(DROWNED_HOST), 2));
                }
                if (grants.contains(FOCUS_KAKOURGOS)) {
                    options.add(new Option(URCHINKIN, 2, 4, CAP_CATACLYSM_URCHIN, 0));
                }
            }
            case SUNKEN -> {
                if (isAquatic(necro) && grants.contains(FOCUS_RAGING_SHARK)) {
                    options.add(new Option(DOOM_FIVE_HEAD_SHARK, 1, 1, CAP_DROWNED_SHARK, 0, 20));
                }
                if (grants.contains(FOCUS_SLIMY)) {
                    options.add(new Option(TROPICAL_SLIME, 2, 4, CAP_SUNKEN_SLIME,
                            maxed ? 4 : 3, 40));
                }
                if (grants.contains(FOCUS_POLEMISTIS)) {
                    options.add(new Option(SYMBIOCTO, 2, 4, CAP_CATACLYSM_OCTO, 0, 1,
                            List.of(SYMBIOCTO), 2));
                    options.add(new Option(DROWNED_HOST, 2, 4, CAP_CATACLYSM_OCTO, 0, 1,
                            List.of(DROWNED_HOST), 2));
                }
                if (grants.contains(FOCUS_KAKOURGOS)) {
                    options.add(new Option(URCHINKIN, 2, 4, CAP_CATACLYSM_URCHIN, 0));
                }
            }
            case WITHER -> {
                LivingEntity owner = necro.getTrueOwner();
                boolean nether = owner != null && CuriosFinder.hasNetherSet(owner);
                boolean unholy = owner != null && CuriosFinder.hasUnholySet(owner);
                if (hunting) {
                    options.add(new Option(HELLHOUND, 2, 4, CAP_WITHER_SPIDER, 0));
                }
                if (grants.contains(FOCUS_SPIDER)) {
                    ResourceLocation spider = isSecondRoundWorld(necro)
                            ? SECOND_ROUNDER_SPIDER
                            : CRIMSON_SPIDER;
                    options.add(new Option(spider, 2, 4, CAP_WITHER_SPIDER, 0));
                }
                if (grants.contains(FOCUS_SLIMY)) {
                    options.add(new Option(MAGMA_CUBE_SERVANT, 2, 2, CAP_WITHER_MAGMA,
                            maxed ? 4 : 0));
                }
                if (grants.contains(FOCUS_GHASTLY)) {
                    ResourceLocation ghast = unholy ? MALGHAST
                            : (nether ? GHAST_SERVANT : MINI_GHAST);
                    options.add(CAP_WITHER_GHAST_ELITE.contains(ghast)
                            ? new Option(ghast, 2, 4, CAP_WITHER_GHAST, 0, 1,
                                    CAP_WITHER_GHAST_ELITE, 2)
                            : new Option(ghast, 2, 4, CAP_WITHER_GHAST, 0));
                }
                if (grants.contains(FOCUS_FLYING_FIREBALL)) {
                    options.add(new Option(FLYING_FIREBALL_SERVANT, 2, 4,
                            CAP_WITHER_FLYING_FIREBALL, 0));
                }
                if (grants.contains(FOCUS_FIERY)) {
                    options.add(new Option(CITADEL_KEEPER_SERVANT, 1, 2,
                            CAP_WITHER_CITADEL, 0));
                }
            }
            case CRYPT -> {
                if (grants.contains(FOCUS_SLIMY)) {
                    options.add(new Option(CRYPT_SLIME_SERVANT, 2, 2, CAP_CRYPT_SLIME, 4));
                }
                if (grants.contains(SPIDER_NEST)) {
                    options.add(new Option(BONE_SPIDER_SERVANT, 2, 4, CAP_CRYPT_SPIDER, 0, 3));
                    options.add(new Option(ICY_SPIDER, 2, 4, CAP_CRYPT_SPIDER, 0, 1));
                }
                if (grants.contains(FOCUS_SPOOKY)) {
                    options.add(new Option(BORDER_WRAITH_SERVANT, 2, 2, CAP_CRYPT_WRAITH, 0));
                }
                if (grants.contains(FOCUS_FROST_HUNTER)) {
                    options.add(new Option(URBHADHACH_SERVANT, 1, 2, CAP_FROST_HUNTER, 0));
                }
                if (grants.contains(FOCUS_MAULING)) {
                    options.add(new Option(POLAR_BEAR_SERVANT, 1, 2, CAP_POLAR_BEAR, 0));
                }
                if (grants.contains(FOCUS_HAUNT)) {
                    options.add(new Option(HAUNT, 3, 6, CAP_HAUNT, 0));
                }
                if (grants.contains(FOCUS_RIGID)) {
                    options.add(new Option(DREDEN_SERVANT, 2, 2, CAP_DREDEN, 0));
                }
                if (grants.contains(FOCUS_ICE_CRYSTAL)) {
                    options.add(new Option(ICE_CRYSTAL_SERVANT, 2, 2,
                            CAP_TWILIGHT_ICE_CRYSTAL, 0));
                }
                if (grants.contains(FOCUS_SNOW_GUARDIAN)) {
                    options.add(new Option(SNOW_GUARDIAN_SERVANT, 2, 2,
                            CAP_TWILIGHT_SNOW_GUARDIAN, 0));
                }
                if (grants.contains(FOCUS_STABLE_ICE_CORE)) {
                    options.add(new Option(STABLE_ICE_CORE_SERVANT, 2, 2,
                            CAP_TWILIGHT_STABLE_ICE_CORE, 0));
                }
                if (grants.contains(FOCUS_ALPHA_WOLF)) {
                    options.add(new Option(TWILIGHT_WINTER_WOLF, 2, 2,
                            CAP_TWILIGHT_ALPHA_WOLF, 0));
                }
            }
        }
        List<Option> available = new ArrayList<>(options.size());
        for (Option option : options) {
            if (ForgeRegistries.ENTITY_TYPES.containsKey(option.type)) {
                available.add(option);
            }
        }
        return available;
    }

    public static boolean hasBelowCapExtra(AbstractNecromancer necro) {
        if (necro == null || !(necro.level() instanceof ServerLevel level)) {
            return false;
        }
        if (necro.getNecroLevel() < 1) {
            return false;
        }
        Variant variant = variantOf(necro);
        if (variant == Variant.CRYPT
                && ForgeRegistries.ENTITY_TYPES.containsKey(HAUNTED_ARMOR_SERVANT)) {
            Option inherent = new Option(HAUNTED_ARMOR_SERVANT, 2, 4, CAP_CRYPT_ARMOR, 0);
            if (roomLeftWithPending(level, necro, inherent) > 0) {
                return true;
            }
        }
        Set<String> grants = grants(necro);
        if (grants == null || grants.isEmpty()) {
            return false;
        }
        for (Option option : optionsFor(necro, variant, grants)) {
            if (!ForgeRegistries.ENTITY_TYPES.containsKey(option.type)) {
                continue;
            }
            if (roomLeftWithPending(level, necro, option) > 0) {
                return true;
            }
        }
        return false;
    }

    private static int roomLeftWithPending(ServerLevel level, AbstractNecromancer necro, Option option) {
        int room = roomLeft(level, necro, option);
        if (room <= 0) {
            return 0;
        }
        int free = room - ExtraSummonWindup.pendingFor(necro, option.capTypes);
        if (option.subCap > 0 && option.subCapTypes != null) {
            int subFree = option.subCap - countOwned(level, necro, option.subCapTypes)
                    - ExtraSummonWindup.pendingFor(necro, option.subCapTypes);
            free = Math.min(free, subFree);
        }
        return free;
    }

    private static Option pick(List<Option> options, AbstractNecromancer necro) {
        int total = 0;
        for (Option option : options) {
            total += Math.max(1, option.weight);
        }
        int roll = necro.getRandom().nextInt(total);
        for (Option option : options) {
            roll -= Math.max(1, option.weight);
            if (roll < 0) {
                return option;
            }
        }
        return options.get(options.size() - 1);
    }

    private static int randomizedCount(AbstractNecromancer necro, Variant variant, Option option) {
        if (option.type.equals(TWILIGHT_WINTER_WOLF) || option.type.equals(MIST_WOLF_SERVANT)
                || option.type.equals(KING_SPIDER_SERVANT)) {
            return 1 + necro.getRandom().nextInt(2);
        }
        if (variant == Variant.DRAUGR && option.type.equals(SNOW_GUARDIAN_SERVANT)) {
            return 2 + necro.getRandom().nextInt(3);
        }
        return option.count;
    }

    private static int spawnOption(ServerLevel level, AbstractNecromancer necro, Option option,
            Variant variant, boolean idleSummon) {
        int remaining = roomLeft(level, necro, option);
        if (remaining <= 0) {
            LOGGER.info("{}spawnOption abort: remaining=0 entity={} cap={} owned={}",
                    LOG, option.type, option.cap,
                    countOwned(level, necro, option.capTypes));
            return 0;
        }
        int rolled = randomizedCount(necro, variant, option);
        int count = Math.min(rolled, remaining);
        int spawned = 0;
        boolean ground = false;
        Mob first = null;
        for (int i = 0; i < count; ++i) {
            SpawnResult result = spawnOne(level, necro, option, variant, idleSummon, i, count);
            if (result.kind() != ExtraSummonWindup.Kind.FAILED) {
                ++spawned;
                ground |= result.kind() == ExtraSummonWindup.Kind.GROUND_CIRCLE;
                if (first == null) {
                    first = result.mob();
                }
            }
        }
        if (ground && first != null) {
            ExtraSummonWindup.playSound(first, necro);
        }
        LOGGER.info("{}spawnOption: entity={} requested={} remaining={} spawned={}",
                LOG, option.type, count, remaining, spawned);
        return spawned;
    }

    private static int roomLeft(ServerLevel level, AbstractNecromancer necro, Option option) {
        int remaining = option.cap - countOwned(level, necro, option.capTypes);
        if (option.subCap > 0 && option.subCapTypes != null) {
            remaining = Math.min(remaining,
                    option.subCap - countOwned(level, necro, option.subCapTypes));
        }
        return remaining;
    }

    private static int countOwned(ServerLevel level, AbstractNecromancer necro,
            List<ResourceLocation> types) {
        AABB box = necro.getBoundingBox().inflate(64.0D, 16.0D, 64.0D);
        return level.getEntitiesOfClass(Entity.class, box, entity -> {
            if (entity == necro) {
                return false;
            }
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            return types.contains(key) && SummonCapUtil.summonedBy(entity, necro)
                    && SummonCapUtil.countsTowardCap(entity, key);
        }).size();
    }

    private static SpawnResult spawnOne(ServerLevel level, AbstractNecromancer necro, Option option,
            Variant variant, boolean idleSummon, int copyIndex, int batchSize) {
        ResourceLocation actualType = option.type.equals(SLIME_BEETLE_SERVANT) && copyIndex == 1
                ? PINCH_BEETLE_SERVANT
                : option.type;
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(actualType);
        if (type == null) {
            LOGGER.info("{}spawnOne fail: entity type missing {}", LOG, actualType);
            return new SpawnResult(ExtraSummonWindup.Kind.FAILED, null);
        }
        Entity entity = type.create(level);
        if (!(entity instanceof Mob mob) || !(entity instanceof IOwned owned)) {
            LOGGER.info("{}spawnOne fail: create/IOwned entity={} class={}",
                    LOG, actualType, entity == null ? "null" : entity.getClass().getName());
            if (entity != null) {
                entity.discard();
            }
            return new SpawnResult(ExtraSummonWindup.Kind.FAILED, null);
        }
        SummonCapUtil.markDirectSummon(entity, necro);
        GreenSmithSoulJarGuard.inheritSummonTag(entity, necro);
        boolean aquatic = (variant == Variant.DROWNED || variant == Variant.SUNKEN)
                ? isAquatic(necro)
                : (necro.isInWater() || necro.isUnderWater());
        BlockPos pos = aquatic
                ? BlockFinder.SummonWaterRadius(necro.blockPosition(), necro, level)
                : BlockFinder.SummonRadius(necro.blockPosition(), mob, level);
        if (pos == null) {
            pos = necro.blockPosition();
        }
        LivingEntity master = idleSummon
                ? (necro.getTrueOwner() != null ? necro.getTrueOwner() : necro)
                : necro;
        owned.setTrueOwner(master);
        boolean noCircle = ExtraSummonWindup.isNoCircleType(option.type);
        boolean groundCircle = !noCircle;
        boolean flying = noCircle;
        boolean ghastExit = variant == Variant.WITHER && WITHER_GHAST_TYPES.contains(option.type);
        boolean phantomNameless = option.type.equals(PHANTOM_SERVANT) && ownerWearsNamelessSet(necro);
        double spawnX = pos.getX() + 0.5D;
        double spawnY = pos.getY();
        double spawnZ = pos.getZ() + 0.5D;
        if (ghastExit) {
            Vec3 exit = ghastExitPos(level, necro, mob);
            spawnX = exit.x;
            spawnY = exit.y;
            spawnZ = exit.z;
        } else if (phantomNameless) {
            spawnX = necro.getX();
            spawnY = necro.getY() + necro.getBbHeight() + PHANTOM_NAMELESS_HEIGHT;
            spawnZ = necro.getZ();
        } else if (flying) {
            spawnY = Math.max(spawnY, level.getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY()
                    + mob.getBbHeight() + 2.0D);
        }
        int batch = Math.max(1, batchSize);
        if (batch > 1) {
            double ringAngle = Math.PI * 2.0D * copyIndex / batch;
            spawnX += Math.cos(ringAngle);
            spawnZ += Math.sin(ringAngle);
        }
        mob.moveTo(spawnX, spawnY, spawnZ, necro.getYRot(), 0.0F);
        if (groundCircle && !aquatic) {
            MobUtil.moveDownToGround(mob);
        }
        if (Boolean.TRUE.equals(MobsConfig.NecromancerSummonsLife.get())) {
            owned.setLimitedLife(MobUtil.getSummonLifespan(level));
        }
        mob.finalizeSpawn(level, level.getCurrentDifficultyAt(necro.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        if (option.slimeSize > 0) {
            applySlimeSize(mob, option.slimeSize);
        }
        if (phantomNameless && mob instanceof PhantomServant phantom) {
            phantom.setPhantomSize(PHANTOM_NAMELESS_POTENCY);
        }
        if (mob instanceof HauntedArmorServant servant) {
            HauntedArmorDressing.dressAsCursedKnight(servant,
                    HauntedArmorDressing.curioWearer(necro));
        }
        Vec3 at = mob.position();
        ExtraSummonWindup.Kind kind = ExtraSummonWindup.summon(level, mob, at, necro);
        if (isSuspectedFlyer(option.type)) {
            LOGGER.info("{}spawnOne air-check entity={} flying={} noGravity={} spawnY={} kind={} at={}",
                    LOG, option.type, flying, mob.isNoGravity(), spawnY, kind, at);
        }
        if (kind == ExtraSummonWindup.Kind.FAILED) {
            LOGGER.info("{}spawnOne fail: SummonCircle addFreshEntity=false entity={} pos={},{},{}",
                    LOG, option.type, mob.getX(), mob.getY(), mob.getZ());
            return new SpawnResult(ExtraSummonWindup.Kind.FAILED, null);
        }
        SoundUtil.playNecromancerSummon(mob);
        switch (variant) {
            case MOSSY -> ServerParticleUtil.summonUndeadParticles(level, mob,
                    new ColorUtil(4209428), 4209428, 5983773);
            case WITHER -> ServerParticleUtil.summonUndeadParticles(level, mob,
                    new ColorUtil(16753408), 16753408, 0xFFFF6E);
            default -> ServerParticleUtil.summonUndeadParticles(level, mob);
        }
        return new SpawnResult(kind, mob);
    }

    private record SpawnResult(ExtraSummonWindup.Kind kind, Mob mob) {
    }

    private static boolean ownerWearsNamelessSet(AbstractNecromancer necro) {
        LivingEntity owner = necro.getTrueOwner();
        return owner != null && CuriosFinder.neutralNamelessSet(owner);
    }

    private static boolean isSuspectedFlyer(ResourceLocation type) {
        return type.equals(PHANTOM_SERVANT)
                || type.equals(HAUNT)
                || type.equals(DREDEN_SERVANT)
                || type.equals(MINI_GHAST)
                || type.equals(GHAST_SERVANT)
                || type.equals(MALGHAST)
                || type.equals(BORDER_WRAITH_SERVANT)
                || type.equals(FLYING_FIREBALL_SERVANT);
    }

    private static Vec3 ghastExitPos(ServerLevel level, AbstractNecromancer necro, Mob mob) {
        RandomSource random = necro.getRandom();
        Vec3 fallback = null;
        for (int attempt = 0; attempt < GHAST_SPAWN_ATTEMPTS; attempt++) {
            double angle = random.nextDouble() * (Math.PI * 2.0D);
            double distance = GHAST_MIN_HORIZONTAL_DISTANCE
                    + random.nextDouble() * (GHAST_MAX_HORIZONTAL_DISTANCE
                            - GHAST_MIN_HORIZONTAL_DISTANCE);
            double x = necro.getX() + Math.cos(angle) * distance;
            double z = necro.getZ() + Math.sin(angle) * distance;
            double ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.containing(x, necro.getY(), z)).getY();
            double y = Math.max(necro.getY() + GHAST_MIN_VERTICAL_OFFSET, ground + 2.0D);
            if (fallback == null) {
                fallback = new Vec3(x, y, z);
            }
            for (int lift = 0; lift <= 5; lift++) {
                Vec3 at = new Vec3(x, y + lift * 2.0D, z);
                if (level.noCollision(mob, mob.getDimensions(mob.getPose()).makeBoundingBox(at))) {
                    return at;
                }
            }
        }
        return fallback != null ? fallback : necro.position();
    }

    private static void applySlimeSize(Entity entity, int size) {
        try {
            Method method = entity.getClass().getMethod("setSize", int.class, boolean.class);
            method.invoke(entity, size, Boolean.TRUE);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
        }
    }

    public static boolean canStartSummon(AbstractNecromancer necro) {
        if (necro == null) {
            return false;
        }
        LivingEntity target = necro.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (necro.isSpellCasting()) {
            return false;
        }
        int cooldown = spellCooldown(necro);
        return cooldown != Integer.MIN_VALUE && cooldown <= 0;
    }

    private static Method spellCooldownMethod;
    private static boolean spellCooldownLookupFailed;

    private static int spellCooldown(AbstractNecromancer necro) {
        if (spellCooldownMethod == null && !spellCooldownLookupFailed) {
            for (Class<?> type = necro.getClass(); type != null; type = type.getSuperclass()) {
                try {
                    Method method = type.getDeclaredMethod("getSpellCooldown");
                    method.setAccessible(true);
                    spellCooldownMethod = method;
                    break;
                } catch (NoSuchMethodException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
                } catch (RuntimeException e) {
                    spellCooldownLookupFailed = true;
                    break;
                }
            }
            if (spellCooldownMethod == null) {
                spellCooldownLookupFailed = true;
            }
        }
        if (spellCooldownMethod == null) {
            return Integer.MIN_VALUE;
        }
        try {
            return (int) spellCooldownMethod.invoke(necro);
        } catch (ReflectiveOperationException | RuntimeException e) {
            return Integer.MIN_VALUE;
        }
    }

    public static boolean forceSpellCooldown(AbstractNecromancer necro, int ticks) {
        if (necro == null) {
            return false;
        }
        try {
            for (Class<?> type = necro.getClass(); type != null; type = type.getSuperclass()) {
                try {
                    Method method = type.getDeclaredMethod("setSpellCooldown", int.class);
                    method.setAccessible(true);
                    method.invoke(necro, ticks);
                    return true;
                } catch (NoSuchMethodException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
                }
            }
        } catch (ReflectiveOperationException | RuntimeException ignored) {
        }
        return false;
    }

    public static AbstractNecromancer outerNecromancer(Object goal) {
        if (goal == null) {
            return null;
        }
        for (Class<?> type = goal.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!AbstractNecromancer.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    if (field.get(goal) instanceof AbstractNecromancer necro) {
                        return necro;
                    }
                } catch (ReflectiveOperationException | RuntimeException ignored) {
                }
            }
        }
        return null;
    }

    public static boolean goalJustSummoned(Object goal) {
        return readInt(goal, "spellTime") == 0;
    }

    private static int readInt(Object target, String name) {
        if (target == null) {
            return Integer.MIN_VALUE;
        }
        for (Class<?> type = target.getClass(); type != null; type = type.getSuperclass()) {
            try {
                Field field = type.getDeclaredField(name);
                field.setAccessible(true);
                return field.getInt(target);
            } catch (NoSuchFieldException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            } catch (ReflectiveOperationException | RuntimeException e) {
                return Integer.MIN_VALUE;
            }
        }
        return Integer.MIN_VALUE;
    }

    private static net.minecraft.sounds.SoundEvent refusalSound(AbstractNecromancer necro) {
        switch (variantOf(necro)) {
            case DROWNED:
                return ModSounds.DROWNED_NECROMANCER_HURT.get();
            case MOSSY:
                return ModSounds.MOSSY_NECROMANCER_HURT.get();
            case WITHER:
                return ModSounds.WITHER_NECROMANCER_HURT.get();
            default:
                return ModSounds.NECROMANCER_HURT.get();
        }
    }

    private static Set<String> grants(AbstractNecromancer necro) {
        if (necro instanceof NecromancerFocusHolder holder) {
            return holder.goetySupreme$necromancerFocusGrants();
        }
        return null;
    }

    private static NecromancerFocusHolder holder(AbstractNecromancer necro) {
        return necro instanceof NecromancerFocusHolder focusHolder ? focusHolder : null;
    }

    private enum Variant {
        NORMAL, MOSSY, CAIRN, DROWNED, WITHER, DRAUGR, CRYPT, SUNKEN
    }

    private static Variant variantOf(AbstractNecromancer necro) {
        String name = necro.getClass().getName();
        if (name.contains("CryptNecromancer")) {
            return Variant.CRYPT;
        }
        if (name.contains("MossyNecromancer")) {
            return Variant.MOSSY;
        }
        if (name.contains("CairnNecromancer")) {
            return Variant.CAIRN;
        }
        if (name.contains("DrownedNecromancer")) {
            return Variant.DROWNED;
        }
        if (name.contains("SunkenNecromancer")) {
            return Variant.SUNKEN;
        }
        if (name.contains("WitherNecromancer")) {
            return Variant.WITHER;
        }
        if (name.contains("DraugrNecromancer")) {
            return Variant.DRAUGR;
        }
        return Variant.NORMAL;
    }

    private static final class Option {
        private final ResourceLocation type;
        private final int count;
        private final int cap;
        private final List<ResourceLocation> capTypes;
        private final int slimeSize;
        private int weight;

        private int subCap;

        private List<ResourceLocation> subCapTypes;

        private Option(ResourceLocation type, int count, int cap, List<ResourceLocation> capTypes,
                int slimeSize) {
            this(type, count, cap, capTypes, slimeSize, 1);
        }

        private Option(ResourceLocation type, int count, int cap, List<ResourceLocation> capTypes,
                int slimeSize, int weight) {
            this.type = type;
            this.count = count;
            this.cap = cap;
            this.capTypes = capTypes;
            this.slimeSize = slimeSize;
            this.weight = Math.max(1, weight);
        }

        private Option(ResourceLocation type, int count, int cap, List<ResourceLocation> capTypes,
                int slimeSize, int weight, List<ResourceLocation> subCapTypes, int subCap) {
            this(type, count, cap, capTypes, slimeSize, weight);
            this.subCapTypes = subCapTypes;
            this.subCap = subCap;
        }
    }
}
