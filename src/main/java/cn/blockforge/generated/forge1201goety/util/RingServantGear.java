package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.ForgeRingArmorNormalizer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.ImmutableRingEvents;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.util.ServantArmorTrims;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.mixin.CuriosFinderForgeRingMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyIllagerRingArmorMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyIllagerTrainArmorMixin;
import cn.blockforge.generated.goetysupreme.mixin.MobRingGearLockMixin;

public final class RingServantGear {

    private static final int TIER_CHAIN = 2;
    private static final int TIER_IRON = 3;
    private static final int TIER_DIAMOND = 4;
    private static final int TIER_NETHERITE = 5;

    private static final Item[] RING_MELEE_WEAPONS = {
        Items.STONE_SWORD, Items.STONE_AXE,
        Items.IRON_SWORD, Items.IRON_AXE,
        Items.DIAMOND_SWORD, Items.DIAMOND_AXE,
        Items.NETHERITE_SWORD, Items.NETHERITE_AXE
    };

    private static final int IRON_AXE_INDEX = 3;

    private static final float NETHERITE_VS_DIAMOND = 0.5F;

    public static final String RING_GEAR_TAG = "GoetySupremeRingGear";

    private static final ResourceLocation FORGE_RING =
            ResourceLocation.fromNamespaceAndPath("goety", "ring_of_the_forge");

    private static final TagKey<EntityType<?>> GOETY_SKELETON_SERVANTS = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("goety", "skeleton_servants"));

    private static final ResourceLocation WITHER_SKELETON_SERVANT =
            ResourceLocation.fromNamespaceAndPath("goety", "wither_skeleton_servant");

    private static final ResourceLocation VANGUARD_SERVANT =
            ResourceLocation.fromNamespaceAndPath("goety", "vanguard_servant");

    private static final EquipmentSlot[] ARMOR_SLOTS = {
        EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private RingServantGear() {
    }

    public enum Ring {
        NONE,

        FORGE,

        TEMPERED,

        IMMUTABLE
    }

    public static Player ringOwner(Summoned servant) {
        return servant == null ? null : ownerChainPlayer(servant);
    }

    public static Player ownerChainPlayer(LivingEntity servant) {
        LivingEntity owner = servant instanceof IOwned owned ? owned.getTrueOwner() : null;
        int guard = 0;
        while (owner != null && guard++ < 16) {
            if (owner instanceof Player player) {
                return player;
            }
            if (owner instanceof IOwned owned) {
                LivingEntity next = owned.getTrueOwner();
                if (next == owner) {
                    return null;
                }
                owner = next;
            } else {
                return null;
            }
        }
        return null;
    }

    public static boolean isSkeletonServant(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (VANGUARD_SERVANT.equals(key)) {
            return false;
        }
        if (entity.getType().is(GOETY_SKELETON_SERVANTS)) {
            return true;
        }
        if (key == null) {
            return false;
        }
        String path = key.getPath();
        return path.contains("skeleton") || path.equals("stray_servant") || path.equals("rattled_servant");
    }

    public static boolean isWitherSkeletonServant(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        return WITHER_SKELETON_SERVANT.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    public static boolean isIllagerServant(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
            if (type.getName().contains(".entities.ally.illager.")) {
                return true;
            }
        }
        return false;
    }

    public static boolean acceptsRingArmor(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (!isIllagerServant(entity)) {
            return true;
        }
        try {
            Object result = entity.getClass().getMethod("canWearArmor").invoke(entity);
            return !(result instanceof Boolean allowed) || allowed;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return true;
        }
    }

    public static boolean isArmorIncapable(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof AbstractNecromancer) {
            return true;
        }
        if (wearsAnyArmor(entity) || isNativeArmorWearer(entity)) {
            return false;
        }
        if (!acceptsRingArmor(entity)) {
            return true;
        }
        if (wearsRingArmor(entity)) {
            return false;
        }
        return !canWearArmorReflect(entity);
    }

    public static boolean wearsAnyArmor(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        for (EquipmentSlot slot : new EquipmentSlot[] {
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET }) {
            if (!entity.getItemBySlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static final ResourceLocation HAUNTED_ARMOR_SERVANT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "haunted_armor_servant");

    private static boolean isNativeArmorWearer(LivingEntity entity) {
        if (entity instanceof SecondRounderAnimatedArmorServant) {
            return true;
        }
        return HAUNTED_ARMOR_SERVANT_ID.equals(
                ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
    }

    public static boolean wearsRingArmor(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isRingGear(entity.getItemBySlot(slot))) {
                return true;
            }
        }
        return false;
    }

    public static double baseArmor(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        net.minecraft.world.entity.ai.attributes.AttributeInstance armor =
                entity.getAttribute(Attributes.ARMOR);
        return armor == null ? 0.0D : armor.getBaseValue();
    }

    private static boolean canWearArmorReflect(LivingEntity entity) {
        try {
            Object result = entity.getClass().getMethod("canWearArmor").invoke(entity);
            return result instanceof Boolean allowed && allowed;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return false;
        }
    }

    public static Ring ringOf(Player owner) {
        if (owner == null) {
            return Ring.NONE;
        }
        if (ownerHas(owner, GeneratedMod.IMMUTABLE_RING.get())) {
            return Ring.IMMUTABLE;
        }
        if (ownerHas(owner, GeneratedMod.TEMPERED_RING.get())) {
            return Ring.TEMPERED;
        }
        if (ownerHas(owner, forgeRingItem())) {
            return Ring.FORGE;
        }
        return Ring.NONE;
    }

    public static Ring ringOf(Summoned servant) {
        return ringOf(ringOwner(servant));
    }

    public static Ring chainRing(LivingEntity servant) {
        return ringOf(ownerChainPlayer(servant));
    }

    public static boolean ringScopeApplies(LivingEntity servant) {
        if (servant == null) {
            return false;
        }
        Ring ring = chainRing(servant);
        if (!handles(ring)) {
            return false;
        }
        if (ring == Ring.IMMUTABLE) {
            return true;
        }
        return servant instanceof IOwned owned && owned.getTrueOwner() instanceof Player;
    }

    public static boolean casterWearsNewRing(LivingEntity caster) {
        if (caster == null) {
            return false;
        }
        if (caster instanceof Player player) {
            return handles(ringOf(player));
        }
        return chainRing(caster) == Ring.IMMUTABLE;
    }

    public static Item forgeRingItem() {
        return ForgeRegistries.ITEMS.getValue(FORGE_RING);
    }

    public static boolean ownerHas(Player owner, Item item) {
        if (item == null || item == Items.AIR) {
            return false;
        }
        try {
            return CuriosFinder.hasCurio(owner, item);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public static boolean newRingCountsAsForgeRing(LivingEntity entity, Item asked) {
        if (entity == null || asked == null) {
            return false;
        }
        Item forge = forgeRingItem();
        if (forge == null || asked != forge) {
            return false;
        }
        if (!(entity instanceof Player player)) {
            return false;
        }
        return ownerHas(player, GeneratedMod.IMMUTABLE_RING.get())
                || ownerHas(player, GeneratedMod.TEMPERED_RING.get());
    }

    public static boolean handles(Ring ring) {
        return ring == Ring.TEMPERED || ring == Ring.IMMUTABLE;
    }

    public static void markRingGear(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            stack.getOrCreateTag().putBoolean(RING_GEAR_TAG, true);
        }
    }

    public static boolean isRingGear(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.hasTag()
                && stack.getTag().getBoolean(RING_GEAR_TAG);
    }

    public static void returnReplaced(Player player, ItemStack previous, float previousDropChance) {
        if (player == null || previous == null || previous.isEmpty()) {
            return;
        }
        if (isRingGear(previous)) {
            return;
        }
        if (previousDropChance > 0.0F) {
            player.getInventory().placeItemBackInInventory(previous);
        }
    }

    public static void rollRingGear(Summoned servant, RandomSource random) {
        if (servant == null || random == null) {
            return;
        }
        Ring ring = ringOf(servant);
        if (!handles(ring)) {
            return;
        }
        if (servant instanceof SkeletonHorseServant horse) {
            if (!horse.hasNetheriteArmor() && horse.getHorseArmor().isEmpty()) {
                ItemStack armor = rollHorseArmor(servant, random);
                if (!armor.isEmpty()) {
                    horse.setHorseArmor(armor);
                }
            }
            return;
        }
        if (!acceptsRingArmor(servant)) {
            return;
        }
        float p = luckBonus(ringOwner(servant));
        boolean netheriteSwap = RingSetBonuses.netheriteSwap(servant);
        int tier = rollRingArmorTier(random, p, netheriteSwap);
        equip(servant, EquipmentSlot.HEAD, tier);
        equip(servant, EquipmentSlot.CHEST, tier);
        equip(servant, EquipmentSlot.LEGS, tier);
        equip(servant, EquipmentSlot.FEET, tier);
        ServantArmorTrims.apply(servant);
        if (!isIllagerServant(servant)) {
            rollRingWeapon(servant, random, p, netheriteSwap);
        }
    }

    public static void rollRingArmorOnly(Summoned servant, RandomSource random) {
        if (servant == null || random == null) {
            return;
        }
        Ring ring = ringOf(servant);
        if (!handles(ring)) {
            return;
        }
        if (!acceptsRingArmor(servant)) {
            return;
        }
        int tier = rollRingArmorTier(random, luckBonus(ringOwner(servant)),
                RingSetBonuses.netheriteSwap(servant));
        equip(servant, EquipmentSlot.HEAD, tier);
        equip(servant, EquipmentSlot.CHEST, tier);
        equip(servant, EquipmentSlot.LEGS, tier);
        equip(servant, EquipmentSlot.FEET, tier);
        ServantArmorTrims.apply(servant);
    }

    public static boolean isNecromancerServant(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof AbstractNecromancer
                || entity instanceof AbstractWildMage
                || entity instanceof AbstractGreenSmith
                || entity instanceof AbstractReaperNecromancer
                || entity instanceof AbstractSoulReaper) {
            return true;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key != null && key.getPath().contains("necromancer");
    }

    public static float luckBonus(Player owner) {
        if (owner == null) {
            return 0.095F;
        }
        float bonus = 0.095F + 0.05F * (float) owner.getAttributeValue(Attributes.LUCK);
        return Math.min(bonus, 0.5F);
    }

    private static float[] tierShares(float p) {
        float chain = (1.0F - p) * (1.0F - p) * (1.0F - p);
        float iron = 3.0F * p * (1.0F - p) * (1.0F - p);
        float diamond = Math.max(0.0F, 1.0F - chain - iron);
        float netherite = diamond * NETHERITE_VS_DIAMOND;
        float fromChain = Math.min(chain, netherite);
        float fromIron = netherite - fromChain;
        return new float[] {chain - fromChain, iron - fromIron, diamond, netherite};
    }

    private static float[] witherSkeletonTierShares(float[] shares) {
        return new float[] {shares[0], shares[1], shares[3], shares[2]};
    }

    private static int rollRingArmorTier(RandomSource random, float p, boolean witherSkeletonSwap) {
        float[] shares = tierShares(p);
        if (witherSkeletonSwap) {
            shares = witherSkeletonTierShares(shares);
        }
        float chain = shares[0];
        float iron = chain + shares[1];
        float diamond = iron + shares[2];
        float roll = random.nextFloat();
        if (roll < chain) {
            return TIER_CHAIN;
        }
        if (roll < iron) {
            return TIER_IRON;
        }
        if (roll < diamond) {
            return TIER_DIAMOND;
        }
        return TIER_NETHERITE;
    }

    private static void rollRingWeapon(Summoned servant, RandomSource random, float p,
            boolean netheriteSwap) {
        ItemStack held = servant.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!held.isEmpty()) {
            return;
        }
        boolean force = RingSetBonuses.forceUndeadWeapon(servant);
        boolean trident = RingSetBonuses.drownedTrident(servant);
        Item weapon = rollRingMeleeWeaponItem(random, p, netheriteSwap, force, trident);
        if (weapon == null) {
            return;
        }
        ItemStack stack = new ItemStack(weapon);
        markRingGear(stack);
        servant.setItemSlot(EquipmentSlot.MAINHAND, stack);
        servant.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    public static ItemStack rollRingMeleeWeapon(RandomSource random, Player owner) {
        if (random == null || !handles(ringOf(owner))) {
            return ItemStack.EMPTY;
        }
        Item weapon = rollRingMeleeWeaponItem(random, luckBonus(owner), false, false, false);
        if (weapon == null) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(weapon);
        markRingGear(stack);
        return stack;
    }

    private static Item rollRingMeleeWeaponItem(RandomSource random, float p, boolean netheriteSwap,
            boolean force, boolean trident) {
        float[] shares = tierShares(p);
        if (netheriteSwap) {
            shares = witherSkeletonTierShares(shares);
        }
        float[] weights = new float[RING_MELEE_WEAPONS.length];
        float total = 0.0F;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = shares[i / 2] * (i % 2 == 0 ? 0.5F : 0.25F);
            total += weights[i];
        }
        if (force && total > 0.0F) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] /= total;
            }
        }
        float roll = random.nextFloat();
        float threshold = 0.0F;
        for (int i = 0; i < weights.length; i++) {
            threshold += weights[i];
            if (roll < threshold) {
                return trident && i == IRON_AXE_INDEX ? Items.TRIDENT : RING_MELEE_WEAPONS[i];
            }
        }
        return force ? RING_MELEE_WEAPONS[RING_MELEE_WEAPONS.length - 1] : null;
    }

    public static ItemStack rollHorseArmor(Summoned servant, RandomSource random) {
        if (servant == null || random == null) {
            return ItemStack.EMPTY;
        }
        Ring ring = ringOf(servant);
        if (!handles(ring) || !ringScopeApplies(servant)) {
            return ItemStack.EMPTY;
        }
        int tier = rollRingArmorTier(random, luckBonus(ringOwner(servant)),
                RingSetBonuses.netheriteSwap(servant));
        Item item = switch (tier) {
            case TIER_CHAIN -> Items.IRON_HORSE_ARMOR;
            case TIER_IRON -> Items.GOLDEN_HORSE_ARMOR;
            default -> Items.DIAMOND_HORSE_ARMOR;
        };
        ItemStack stack = new ItemStack(item);
        markRingGear(stack);
        return stack;
    }

    private static void equip(Summoned servant, EquipmentSlot slot, int tier) {
        Item item = tier == TIER_NETHERITE ? netheriteForSlot(slot) : Mob.getEquipmentForSlot(slot, tier);
        if (item != null) {
            ItemStack stack = new ItemStack(item);
            markRingGear(stack);
            servant.setItemSlot(slot, stack);
            servant.setDropChance(slot, 0.0F);
        }
    }

    private static Item netheriteForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> Items.NETHERITE_HELMET;
            case CHEST -> Items.NETHERITE_CHESTPLATE;
            case LEGS -> Items.NETHERITE_LEGGINGS;
            case FEET -> Items.NETHERITE_BOOTS;
            default -> null;
        };
    }

    public static void applyWitherSkeletonWeapon(Summoned servant, RandomSource random) {
        if (servant == null || random == null || !handles(ringOf(servant))) {
            return;
        }
        ItemStack held = servant.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!held.is(Items.STONE_SWORD)) {
            return;
        }
        float[] shares = tierShares(luckBonus(ringOwner(servant)));
        if (RingSetBonuses.netheriteSwap(servant)) {
            shares = witherSkeletonTierShares(shares);
        }
        float diamondShare = shares[2];
        float netheriteShare = shares[3];
        float ironUnit = (shares[0] + shares[1]) / 3.0F;
        float diamondUnit = diamondShare / 3.0F;
        float netheriteUnit = netheriteShare / 3.0F;
        float ironSword = 2.0F * ironUnit;
        float ironAxe = ironUnit;
        float diamondSword = 2.0F * diamondUnit;
        float diamondAxe = diamondUnit;
        float netheriteSword = 2.0F * netheriteUnit;
        float netheriteAxe = netheriteUnit;
        float roll = random.nextFloat();
        Item weapon;
        if (roll < ironSword) {
            weapon = Items.IRON_SWORD;
        } else if (roll < ironSword + ironAxe) {
            weapon = Items.IRON_AXE;
        } else if (roll < ironSword + ironAxe + diamondSword) {
            weapon = Items.DIAMOND_SWORD;
        } else if (roll < ironSword + ironAxe + diamondSword + diamondAxe) {
            weapon = Items.DIAMOND_AXE;
        } else if (roll < ironSword + ironAxe + diamondSword + diamondAxe + netheriteSword) {
            weapon = Items.NETHERITE_SWORD;
        } else {
            weapon = Items.NETHERITE_AXE;
        }
        ItemStack stack = new ItemStack(weapon);
        markRingGear(stack);
        servant.setItemSlot(EquipmentSlot.MAINHAND, stack);
        servant.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }
}
