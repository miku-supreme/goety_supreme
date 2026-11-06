package cn.blockforge.generated.goetysupreme.util;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorMaterial;

public enum PrismarineArmorMaterial implements ArmorMaterial {

    PRISMARINE("goety_supreme:prismarine", 33, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.1F,
            () -> Ingredient.of(Items.PRISMARINE_SHARD));

    private final String name;
    private final int durabilityMultiplier;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    PrismarineArmorMaterial(String name, int durabilityMultiplier, int enchantmentValue, SoundEvent equipSound,
            float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 11 * this.durabilityMultiplier;
            case CHESTPLATE -> 16 * this.durabilityMultiplier;
            case LEGGINGS -> 15 * this.durabilityMultiplier;
            case BOOTS -> 13 * this.durabilityMultiplier;
        };
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 3;
            case CHESTPLATE -> 8;
            case LEGGINGS -> 6;
            case BOOTS -> 3;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
