package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.WitherWarlordArmorMaterial;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;

public final class WitherWarlordArmorMaterial implements ArmorMaterial {

    public static final WitherWarlordArmorMaterial INSTANCE = new WitherWarlordArmorMaterial();

    public static final int HELMET_DEFENSE = 5;

    public static final float TOUGHNESS = 4.0F;

    private static final int DURABILITY_MULTIPLIER = 37;

    private WitherWarlordArmorMaterial() {
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 11 * DURABILITY_MULTIPLIER;
            case CHESTPLATE -> 16 * DURABILITY_MULTIPLIER;
            case LEGGINGS -> 15 * DURABILITY_MULTIPLIER;
            case BOOTS -> 13 * DURABILITY_MULTIPLIER;
        };
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> HELMET_DEFENSE;
            case CHESTPLATE -> 8;
            case LEGGINGS -> 6;
            case BOOTS -> 3;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }

    @Override
    public String getName() {
        return GeneratedMod.MOD_ID + ":wither_warlord";
    }

    @Override
    public float getToughness() {
        return TOUGHNESS;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }
}
