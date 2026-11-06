package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpirit;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritGear;

public final class SakuraSpiritGear {
    public static final List<Item> SWORDS = new ArrayList<>();

    private static final int[] WEAPON_TIER_WEIGHTS = {20, 50, 25, 5};
    private static final int[][] WEAPON_DAMAGE_RANGES = {
            {5, 6}, {7, 8}, {9, 11}, {12, 13}
    };

    public record ArmorSet(String searchText, boolean vanilla, Item helmet, Item chestplate,
            Item leggings, Item boots, int totalDefense, float totalToughness) {
    }

    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();

    private static final Map<EquipmentSlot, String[]> SLOT_SUFFIXES = new EnumMap<>(EquipmentSlot.class);

    static {
        SLOT_SUFFIXES.put(EquipmentSlot.HEAD, new String[] {"_helmet", "_hat"});
        SLOT_SUFFIXES.put(EquipmentSlot.CHEST, new String[] {"_chestplate", "_chest", "_tunic"});
        SLOT_SUFFIXES.put(EquipmentSlot.LEGS, new String[] {"_leggings", "_legs", "_pants"});
        SLOT_SUFFIXES.put(EquipmentSlot.FEET, new String[] {"_boots", "_boot", "_shoes", "_greaves"});
    }

    private static final String[] CONCEALMENT_IDS = {
            "sakura_sword:concealment",
            "taboo:concealment",
    };
    private static boolean concealmentResolved = false;
    private static Enchantment concealment = null;

    private SakuraSpiritGear() {
    }

    @Nullable
    public static Item randomWeapon(RandomSource random) {
        ensurePoolsBuilt();
        if (SWORDS.isEmpty()) {
            return null;
        }
        int roll = random.nextInt(100);
        int tier = 0;
        int threshold = 0;
        for (int i = 0; i < WEAPON_TIER_WEIGHTS.length; i++) {
            threshold += WEAPON_TIER_WEIGHTS[i];
            if (roll < threshold) {
                tier = i;
                break;
            }
        }
        List<Item> candidates = new ArrayList<>();
        for (Item item : SWORDS) {
            if (item instanceof SwordItem sword && inWeaponTier(sword, tier)) {
                candidates.add(item);
            }
        }
        return candidates.isEmpty() ? null : candidates.get(random.nextInt(candidates.size()));
    }

    private static boolean inWeaponTier(SwordItem sword, int tier) {
        float panelDamage = sword.getDamage() + 1.0F;
        return panelDamage >= WEAPON_DAMAGE_RANGES[tier][0]
                && panelDamage <= WEAPON_DAMAGE_RANGES[tier][1];
    }

    public static synchronized Enchantment concealment() {
        if (!concealmentResolved) {
            concealmentResolved = true;
            for (String id : CONCEALMENT_IDS) {
                ResourceLocation location = ResourceLocation.tryParse(id);
                if (location == null) {
                    continue;
                }
                Enchantment found = ForgeRegistries.ENCHANTMENTS.getValue(location);
                if (found != null) {
                    concealment = found;
                    break;
                }
            }
        }
        return concealment;
    }

    public static void enchantConcealment(ItemStack stack) {
        Enchantment enchantment = concealment();
        if (enchantment == null || stack.isEmpty()) {
            return;
        }
        try {
            stack.enchant(enchantment, 1);
        } catch (Exception ignored) {
        }
    }

    public static synchronized void ensurePoolsBuilt() {
        if (!SWORDS.isEmpty() || !ARMOR_SETS.isEmpty()) {
            return;
        }
        Map<String, EnumMap<EquipmentSlot, Item>> groups = new LinkedHashMap<>();
        Set<String> conflicted = new HashSet<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof SwordItem sword && sword.getDamage() + 1.0F >= 5.0F
                    && sword.getDamage() + 1.0F <= 13.0F) {
                SWORDS.add(item);
            }
            if (!(item instanceof ArmorItem armorItem)) {
                continue;
            }
            EquipmentSlot slot = armorItem.getType().getSlot();
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id == null || !SLOT_SUFFIXES.containsKey(slot)) {
                continue;
            }
            String stem = stemFor(id.getPath(), slot);
            if (stem == null) {
                continue;
            }
            String key = id.getNamespace() + ":" + stem;
            EnumMap<EquipmentSlot, Item> perSlot =
                    groups.computeIfAbsent(key, k -> new EnumMap<>(EquipmentSlot.class));
            Item previous = perSlot.putIfAbsent(slot, item);
            if (previous != null && previous != item) {
                conflicted.add(key);
            }
        }
        for (Map.Entry<String, EnumMap<EquipmentSlot, Item>> entry : groups.entrySet()) {
            if (conflicted.contains(entry.getKey())) {
                continue;
            }
            EnumMap<EquipmentSlot, Item> perSlot = entry.getValue();
            if (perSlot.size() != 4) {
                continue;
            }
            ArmorSet set = buildSet(entry.getKey(), perSlot);
            if (set != null) {
                ARMOR_SETS.add(set);
            }
        }
    }

    @Nullable
    private static String stemFor(String path, EquipmentSlot slot) {
        for (String suffix : SLOT_SUFFIXES.get(slot)) {
            if (path.endsWith(suffix) && path.length() > suffix.length()) {
                return path.substring(0, path.length() - suffix.length());
            }
        }
        return null;
    }

    @Nullable
    private static ArmorSet buildSet(String key, EnumMap<EquipmentSlot, Item> perSlot) {
        Item helmet = perSlot.get(EquipmentSlot.HEAD);
        Item chestplate = perSlot.get(EquipmentSlot.CHEST);
        Item leggings = perSlot.get(EquipmentSlot.LEGS);
        Item boots = perSlot.get(EquipmentSlot.FEET);
        if (!(helmet instanceof ArmorItem helmArmor) || !(chestplate instanceof ArmorItem chestArmor)
                || !(leggings instanceof ArmorItem legsArmor) || !(boots instanceof ArmorItem bootsArmor)) {
            return null;
        }
        int defense = helmArmor.getDefense() + chestArmor.getDefense()
                + legsArmor.getDefense() + bootsArmor.getDefense();
        float toughness = helmArmor.getToughness() + chestArmor.getToughness()
                + legsArmor.getToughness() + bootsArmor.getToughness();
        StringBuilder text = new StringBuilder(key.toLowerCase(Locale.ROOT));
        for (Item piece : List.of(helmet, chestplate, leggings, boots)) {
            text.append(' ').append(ForgeRegistries.ITEMS.getKey(piece));
        }
        try {
            text.append(' ').append(chestArmor.getMaterial().getName().toLowerCase(Locale.ROOT));
        } catch (Exception ignored) {
        }
        ResourceLocation first = ForgeRegistries.ITEMS.getKey(helmet);
        boolean vanilla = first != null && "minecraft".equals(first.getNamespace());
        return new ArmorSet(text.toString(), vanilla, helmet, chestplate, leggings, boots, defense, toughness);
    }

    public static float tierChance(int totalDefense) {
        if (totalDefense <= 7) {
            return 0.10F;
        }
        if (totalDefense < 12) {
            return 0.30F;
        }
        if (totalDefense <= 15) {
            return 0.50F;
        }
        if (totalDefense <= 20) {
            return 0.10F;
        }
        return 0.0F;
    }

    public static boolean isPinkish(ArmorSet set) {
        List<? extends String> keywords = SakuraSpiritConfig.PINK_ARMOR_KEYWORDS.get();
        if (keywords == null || set == null) {
            return false;
        }
        for (String keyword : keywords) {
            if (keyword != null && !keyword.isBlank()
                    && set.searchText().contains(keyword.trim().toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static ArmorSet rollArmorSet(RandomSource random) {
        ensurePoolsBuilt();
        if (ARMOR_SETS.isEmpty()) {
            return null;
        }
        List<ArmorSet> passed = new ArrayList<>();
        for (ArmorSet set : ARMOR_SETS) {
            if (!set.vanilla() && !isPinkish(set)) {
                continue;
            }
            float chance = tierChance(set.totalDefense());
            if (chance > 0.0F && random.nextFloat() < chance) {
                passed.add(set);
            }
        }
        if (passed.isEmpty()) {
            return null;
        }
        List<ArmorSet> pool = new ArrayList<>();
        for (ArmorSet set : passed) {
            if (isPinkish(set)) {
                pool.add(set);
            }
        }
        if (pool.isEmpty()) {
            pool = passed;
        }
        double totalWeight = 0.0D;
        for (ArmorSet set : pool) {
            totalWeight += 1.0D / (1.0D + Math.max(0.0F, set.totalToughness()));
        }
        double pick = random.nextDouble() * totalWeight;
        for (ArmorSet set : pool) {
            pick -= 1.0D / (1.0D + Math.max(0.0F, set.totalToughness()));
            if (pick < 0.0D) {
                return set;
            }
        }
        return pool.get(pool.size() - 1);
    }

    public static boolean canEquipSlot(EquipmentSlot slot) {
        return (slot != EquipmentSlot.LEGS && slot != EquipmentSlot.FEET)
                || ModList.get().isLoaded("sakura_sword");
    }

    public static void enforceLowerArmorRule(LivingEntity entity) {
        if (!canEquipSlot(EquipmentSlot.LEGS)) {
            entity.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
            entity.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        }
    }

    public static void equipScavengedArmor(LivingEntity entity, RandomSource random) {
        enforceLowerArmorRule(entity);
        ArmorSet set = rollArmorSet(random);
        if (set == null) {
            return;
        }
        entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(set.helmet()));
        entity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(set.chestplate()));
        if (!canEquipSlot(EquipmentSlot.LEGS)) {
            return;
        }
        ItemStack leggings = new ItemStack(set.leggings());
        enchantConcealment(leggings);
        entity.setItemSlot(EquipmentSlot.LEGS, leggings);
        ItemStack boots = new ItemStack(set.boots());
        enchantConcealment(boots);
        entity.setItemSlot(EquipmentSlot.FEET, boots);
    }

    public static void enchantByLookup(ItemStack stack, String enchantmentId, int level) {
        ResourceLocation id = ResourceLocation.tryParse(enchantmentId);
        if (id == null) {
            return;
        }
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(id);
        if (enchantment == null) {
            return;
        }
        try {
            stack.enchant(enchantment, level);
        } catch (Exception ignored) {
        }
    }

    public static void enchantRandomly(ItemStack stack, RandomSource random) {
        int rolls = 1 + random.nextInt(8);
        List<Enchantment> pool = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues());
        pool.remove(concealment());
        for (int i = 0; i < rolls && !pool.isEmpty(); i++) {
            Enchantment enchantment = pool.remove(random.nextInt(pool.size()));
            if (enchantment.canEnchant(stack)) {
                int level = 1 + random.nextInt(enchantment.getMaxLevel());
                try {
                    stack.enchant(enchantment, level);
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static boolean isHuntable(LivingEntity entity) {
        return !(entity instanceof ISakuraSpirit);
    }
}
