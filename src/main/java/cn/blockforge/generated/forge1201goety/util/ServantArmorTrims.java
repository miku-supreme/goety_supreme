package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.util.ServantArmorTrims;

public final class ServantArmorTrims {

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    private record TrimSpec(ResourceLocation pattern, ResourceLocation material) {
    }

    private static final Map<ResourceLocation, TrimSpec> TABLE = new LinkedHashMap<>();

    private static final ResourceLocation SENTRY = mc("sentry");
    private static final ResourceLocation VEX = mc("vex");
    private static final ResourceLocation COAST = mc("coast");
    private static final ResourceLocation RIB = mc("rib");
    private static final ResourceLocation DUNE = mc("dune");
    private static final ResourceLocation HOST = mc("host");
    private static final ResourceLocation WAYFINDER = mc("wayfinder");
    private static final ResourceLocation WILD = mc("wild");
    private static final ResourceLocation SNOUT = mc("snout");

    private static final ResourceLocation WARD = mc("ward");

    private static final ResourceLocation IRON = mc("iron");
    private static final ResourceLocation GOLD = mc("gold");
    private static final ResourceLocation EMERALD = mc("emerald");
    private static final ResourceLocation AMETHYST = mc("amethyst");
    private static final ResourceLocation COPPER = mc("copper");
    private static final ResourceLocation DIAMOND = mc("diamond");
    private static final ResourceLocation NETHERITE = mc("netherite");

    private static final ResourceLocation DARK = goety("dark");

    private static final ResourceLocation JADE = goety("jade");

    private static final TrimSpec WITHER_SKELETON = new TrimSpec(RIB, NETHERITE);

    private static final TrimSpec WITHER_MINION = new TrimSpec(RIB, AMETHYST);

    private static final TrimSpec WARD_DIAMOND = new TrimSpec(WARD, DIAMOND);

    static {
        put("goety", "skeleton_pillager", SENTRY, IRON);
        put("goety", "zombie_vindicator", VEX, EMERALD);
        put("goety", "drowned_servant", COAST, DIAMOND);
        put("goety", "sunken_skeleton_servant", RIB, AMETHYST);
        put("goetyawaken", "parched_servant", RIB, COPPER);
        put("goety_supreme", "second_rounder_skeleton_servant", RIB, COPPER);
        put("goety", "husk_servant", DUNE, GOLD);
        put("goety", "frozen_zombie_servant", HOST, DIAMOND);
        put("goety", "frayed_servant", WAYFINDER, DARK);
        put("goety", "zombie_servant", WAYFINDER, JADE);
        put("goety_supreme", "second_rounder_zombie_servant", WILD, COPPER);
        put("goety", "wither_skeleton_servant", RIB, NETHERITE);
        put("goety", "stray_servant", RIB, DIAMOND);
        put("goety", "mossy_skeleton_servant", RIB, EMERALD);
        put("goety", "skeleton_servant", RIB, IRON);
        put("goety", "rattled_servant", RIB, DARK);
        put("goety", "jungle_zombie_servant", WILD, EMERALD);
        put("goety", "zpiglin_servant", SNOUT, GOLD);
        put("goety", "zpiglin_brute_servant", SNOUT, GOLD);
    }

    private ServantArmorTrims() {
    }

    public static void apply(Summoned servant) {
        if (servant == null || servant.level().isClientSide()) {
            return;
        }
        if (!scopeApplies(servant)) {
            return;
        }
        TrimSpec spec = specFor(servant);
        if (spec == null) {
            return;
        }
        if (!(servant.level() instanceof ServerLevel server)) {
            return;
        }
        RegistryAccess access = server.registryAccess();
        Optional<Holder.Reference<TrimPattern>> pattern = access
                .registryOrThrow(Registries.TRIM_PATTERN)
                .getHolder(ResourceKey.create(Registries.TRIM_PATTERN, spec.pattern()));
        Optional<Holder.Reference<TrimMaterial>> material = access
                .registryOrThrow(Registries.TRIM_MATERIAL)
                .getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, spec.material()));
        if (pattern.isEmpty() || material.isEmpty()) {
            return;
        }
        ArmorTrim trim = new ArmorTrim(material.get(), pattern.get());
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = servant.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                ArmorTrim.setTrim(access, stack, trim);
            }
        }
    }

    public static void applyWitherSkeletonTrim(LivingEntity servant) {
        if (servant == null || !(servant.level() instanceof ServerLevel server)) {
            return;
        }
        applyTrimToArmor(server.registryAccess(), servant, WITHER_SKELETON, true);
    }

    public static void applyWitherMinionTrim(LivingEntity servant) {
        if (servant == null || !(servant.level() instanceof ServerLevel server)) {
            return;
        }
        applyTrimToArmor(server.registryAccess(), servant, WITHER_MINION, false);
    }

    public static void applyWardDiamondTrim(LivingEntity servant) {
        if (servant == null || !(servant.level() instanceof ServerLevel server)) {
            return;
        }
        applyTrimToArmor(server.registryAccess(), servant, WARD_DIAMOND, false);
    }

    private static void applyTrimToArmor(RegistryAccess access, LivingEntity servant, TrimSpec spec,
            boolean restrictStageMaterials) {
        Optional<Holder.Reference<TrimPattern>> pattern = access
                .registryOrThrow(Registries.TRIM_PATTERN)
                .getHolder(ResourceKey.create(Registries.TRIM_PATTERN, spec.pattern()));
        Optional<Holder.Reference<TrimMaterial>> material = access
                .registryOrThrow(Registries.TRIM_MATERIAL)
                .getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, spec.material()));
        if (pattern.isEmpty() || material.isEmpty()) {
            return;
        }
        ArmorTrim trim = new ArmorTrim(material.get(), pattern.get());
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = servant.getItemBySlot(slot);
            if (!(stack.getItem() instanceof ArmorItem armor)) {
                continue;
            }
            if (restrictStageMaterials && !isWitherTrimStageMaterial(armor.getMaterial())) {
                continue;
            }
            ArmorTrim.setTrim(access, stack, trim);
        }
    }

    private static boolean isWitherTrimStageMaterial(ArmorMaterial material) {
        return material == ArmorMaterials.LEATHER
                || material == ArmorMaterials.CHAIN
                || material == ArmorMaterials.IRON;
    }

    private static boolean scopeApplies(LivingEntity servant) {
        return RingServantGear.ringScopeApplies(servant);
    }

    private static TrimSpec specFor(LivingEntity servant) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(servant.getType());
        return key == null ? null : TABLE.get(key);
    }

    private static void put(String namespace, String path,
            ResourceLocation pattern, ResourceLocation material) {
        TABLE.put(ResourceLocation.fromNamespaceAndPath(namespace, path),
                new TrimSpec(pattern, material));
    }

    private static ResourceLocation mc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    private static ResourceLocation goety(String path) {
        return ResourceLocation.fromNamespaceAndPath("goety", path);
    }
}
