package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.ForgeRingArmorNormalizer;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;

public final class ForgeRingArmorNormalizer {

    private static final int TIER_IRON = 3;

    private ForgeRingArmorNormalizer() {
    }

    public static void normalize(Summoned servant) {
        int head = tier(servant.getItemBySlot(EquipmentSlot.HEAD));
        int chest = tier(servant.getItemBySlot(EquipmentSlot.CHEST));
        int legs = tier(servant.getItemBySlot(EquipmentSlot.LEGS));
        int feet = tier(servant.getItemBySlot(EquipmentSlot.FEET));
        if (head < 0 || chest < 0 || legs < 0 || feet < 0) {
            return;
        }

        int highest = Math.max(Math.max(head, chest), Math.max(legs, feet));
        int highestCount = 0;
        if (head == highest) {
            highestCount++;
        }
        if (chest == highest) {
            highestCount++;
        }
        if (legs == highest) {
            highestCount++;
        }
        if (feet == highest) {
            highestCount++;
        }

        int upper;
        int lower;
        if (highestCount >= 2) {
            upper = highest;
            lower = highest;
        } else {
            upper = Math.max(head, chest);
            lower = Math.max(legs, feet);
        }

        if (servant instanceof SecondRounderZombieServant
                || servant instanceof SecondRounderSkeletonServant) {
            upper = Math.max(upper, TIER_IRON);
        }

        equip(servant, EquipmentSlot.HEAD, upper);
        equip(servant, EquipmentSlot.CHEST, upper);
        equip(servant, EquipmentSlot.LEGS, lower);
        equip(servant, EquipmentSlot.FEET, lower);
    }

    public static boolean hasNormalizableArmor(Summoned servant) {
        return isArmorOrRingGear(servant.getItemBySlot(EquipmentSlot.HEAD))
                && isArmorOrRingGear(servant.getItemBySlot(EquipmentSlot.CHEST))
                && isArmorOrRingGear(servant.getItemBySlot(EquipmentSlot.LEGS))
                && isArmorOrRingGear(servant.getItemBySlot(EquipmentSlot.FEET));
    }

    private static boolean isArmorOrRingGear(ItemStack stack) {
        return tier(stack) >= 0 || RingServantGear.isRingGear(stack);
    }

    private static void equip(Summoned servant, EquipmentSlot slot, int tier) {
        Item item = Mob.getEquipmentForSlot(slot, tier);
        if (item != null) {
            servant.setItemSlot(slot, new ItemStack(item));
            servant.setDropChance(slot, 0.0F);
        }
    }

    private static int tier(ItemStack stack) {
        if (!(stack.getItem() instanceof ArmorItem armor)) {
            return -1;
        }
        if (armor.getMaterial() == ArmorMaterials.LEATHER) {
            return 0;
        }
        if (armor.getMaterial() == ArmorMaterials.GOLD) {
            return 1;
        }
        if (armor.getMaterial() == ArmorMaterials.CHAIN) {
            return 2;
        }
        if (armor.getMaterial() == ArmorMaterials.IRON) {
            return 3;
        }
        if (armor.getMaterial() == ArmorMaterials.DIAMOND) {
            return 4;
        }
        return -1;
    }
}
