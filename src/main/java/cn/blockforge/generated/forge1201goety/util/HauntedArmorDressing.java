package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.undead.HauntedArmorServant;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.HauntedArmorDressing;
import cn.blockforge.generated.goetysupreme.spell.HauntedArmorSummonSpell;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;

public final class HauntedArmorDressing {

    private static final int BLADE_WEIGHT = 1;
    private static final int SPEAR_WEIGHT = 2;
    private static final int HAMMER_WEIGHT = 3;
    private static final int AXE_WEIGHT = 6;

    private static final int BOW_WEIGHT = 2;
    private static final int CROSSBOW_WEIGHT = 2;
    private static final int WEAPON_WEIGHT_TOTAL =
            BLADE_WEIGHT + SPEAR_WEIGHT + HAMMER_WEIGHT + AXE_WEIGHT
                    + BOW_WEIGHT + CROSSBOW_WEIGHT;

    private HauntedArmorDressing() {
    }

    public static void dressAsCursedKnight(HauntedArmorServant servant, LivingEntity owner) {
        dressAsCursedKnight(servant, owner, ItemStack.EMPTY);
    }

    public static void dressAsCursedKnight(HauntedArmorServant servant, LivingEntity owner,
            ItemStack wandStack) {
        Player wearer = curioWearer(owner) instanceof Player player ? player : null;
        boolean direct = servant.getTrueOwner() instanceof Player;
        boolean immutable = wearer != null
                && RingServantGear.ownerHas(wearer, GeneratedMod.IMMUTABLE_RING.get());
        boolean specialScope = direct || immutable;

        boolean namelessSet = specialScope && wearer != null && CuriosFinder.hasNamelessSet(wearer);
        String armor = namelessSet ? "cursed_paladin_" : "cursed_knight_";
        equip(servant, EquipmentSlot.HEAD, armor + "helmet");
        equip(servant, EquipmentSlot.CHEST, armor + "chestplate");
        equip(servant, EquipmentSlot.LEGS, armor + "leggings");
        equip(servant, EquipmentSlot.FEET, armor + "boots");

        if (RingServantGear.ringScopeApplies(servant)) {
            servant.setItemSlot(EquipmentSlot.MAINHAND, rollFallenWeapon(servant.getRandom()));
        } else {
            equip(servant, EquipmentSlot.MAINHAND, "fell_blade");
        }

        boolean namelessStaff = specialScope && isNamelessStaff(wandStack);
        servant.setItemSlot(EquipmentSlot.OFFHAND,
                new ItemStack(namelessStaff ? GeneratedMod.FELL_SHIELD.get() : Items.SHIELD));
    }

    private static ItemStack rollFallenWeapon(RandomSource random) {
        int roll = random.nextInt(WEAPON_WEIGHT_TOTAL);
        if (roll < BLADE_WEIGHT) {
            Item blade = goetyItem("fell_blade");
            return blade == null ? ItemStack.EMPTY : new ItemStack(blade);
        }
        if (roll < BLADE_WEIGHT + SPEAR_WEIGHT) {
            return new ItemStack(GeneratedMod.FELL_SPEAR.get());
        }
        if (roll < BLADE_WEIGHT + SPEAR_WEIGHT + HAMMER_WEIGHT) {
            return new ItemStack(GeneratedMod.FELL_HAMMER.get());
        }
        if (roll < BLADE_WEIGHT + SPEAR_WEIGHT + HAMMER_WEIGHT + AXE_WEIGHT) {
            return new ItemStack(GeneratedMod.FELL_AXE.get());
        }
        if (roll < BLADE_WEIGHT + SPEAR_WEIGHT + HAMMER_WEIGHT + AXE_WEIGHT + BOW_WEIGHT) {
            return new ItemStack(GeneratedMod.FELL_BOW.get());
        }
        return new ItemStack(GeneratedMod.FELL_CROSSBOW.get());
    }

    private static boolean isNamelessStaff(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals("nameless_staff");
    }

    public static LivingEntity curioWearer(LivingEntity entity) {
        LivingEntity current = entity;
        for (int i = 0; i < 8 && current != null; ++i) {
            if (current instanceof Player) {
                return current;
            }
            if (!(current instanceof IOwned owned)) {
                break;
            }
            LivingEntity next = owned.getTrueOwner();
            if (next == null || next == current) {
                break;
            }
            current = next;
        }
        return current;
    }

    private static void equip(HauntedArmorServant servant, EquipmentSlot slot, String goetyPath) {
        Item item = goetyItem(goetyPath);
        if (item != null) {
            servant.setItemSlot(slot, new ItemStack(item));
        }
    }

    private static Item goetyItem(String path) {
        return ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
    }
}
