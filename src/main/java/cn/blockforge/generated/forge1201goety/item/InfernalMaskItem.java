package cn.blockforge.generated.goetysupreme.item;

import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;

public class InfernalMaskItem extends net.minecraft.world.item.ArmorItem
        implements top.theillusivec4.curios.api.type.capability.ICurioItem {
    private static final java.util.UUID KNOCKBACK_UUID =
            java.util.UUID.fromString("6d3a2f7e-6d2b-4f7e-9d4f-2b0f6a9c1e11");
    private static final net.minecraft.world.item.ArmorMaterial MATERIAL =
            new net.minecraft.world.item.ArmorMaterial() {
                public int getDurabilityForType(net.minecraft.world.item.ArmorItem.Type type) { return 407; }
                public int getDefenseForType(net.minecraft.world.item.ArmorItem.Type type) {
                    return type == net.minecraft.world.item.ArmorItem.Type.HELMET ? 3 : 0;
                }
                public int getEnchantmentValue() { return 0; }
                public net.minecraft.sounds.SoundEvent getEquipSound() {
                    return net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_GENERIC;
                }
                public net.minecraft.world.item.crafting.Ingredient getRepairIngredient() {
                    return net.minecraft.world.item.crafting.Ingredient.EMPTY;
                }
                public String getName() { return "goety_supreme:infernal_mask"; }
                public float getToughness() { return 2.0F; }
                public float getKnockbackResistance() { return 0.0F; }
            };

    public InfernalMaskItem() {
        super(MATERIAL, net.minecraft.world.item.ArmorItem.Type.HELMET,
                new net.minecraft.world.item.Item.Properties().stacksTo(1));
    }

    public boolean canEquip(top.theillusivec4.curios.api.SlotContext context,
            net.minecraft.world.item.ItemStack stack) {
        return "head".equals(context.identifier());
    }

    @Override
    public com.google.common.collect.Multimap<net.minecraft.world.entity.ai.attributes.Attribute,
            net.minecraft.world.entity.ai.attributes.AttributeModifier> getDefaultAttributeModifiers(
                    net.minecraft.world.entity.EquipmentSlot slot) {
        var builder = com.google.common.collect.ImmutableMultimap
                .<net.minecraft.world.entity.ai.attributes.Attribute,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier>builder();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == net.minecraft.world.entity.EquipmentSlot.HEAD) {
            builder.put(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE,
                    new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                            KNOCKBACK_UUID, "Infernal mask knockback", 0.1D,
                            net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public String getArmorTexture(net.minecraft.world.item.ItemStack stack,
            net.minecraft.world.entity.Entity entity, net.minecraft.world.entity.EquipmentSlot slot, String type) {
        return "goety_supreme:textures/models/armor/infernal_mask_layer_1.png";
    }

    public com.google.common.collect.Multimap<net.minecraft.world.entity.ai.attributes.Attribute,
            net.minecraft.world.entity.ai.attributes.AttributeModifier> getAttributeModifiers(
                    top.theillusivec4.curios.api.SlotContext context, java.util.UUID uuid,
                    net.minecraft.world.item.ItemStack stack) {
        return com.google.common.collect.ImmutableMultimap.of();
    }

    public void onUnequip(top.theillusivec4.curios.api.SlotContext context,
            net.minecraft.world.item.ItemStack newStack, net.minecraft.world.item.ItemStack oldStack) {
        if (!context.entity().level().isClientSide
                && context.entity() instanceof net.minecraft.world.entity.player.Player player) {
            IfritClone.discardFor(player);
        }
    }

    public static boolean isWorn(net.minecraft.world.entity.LivingEntity entity) {
        if (entity == null) return false;
        if (entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD).is(mask())) return true;
        return top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(entity)
                .map(handler -> {
                    var head = handler.getCurios().get("head");
                    if (head != null) {
                        for (int i = 0; i < head.getStacks().getSlots(); i++) {
                            if (head.getStacks().getStackInSlot(i).is(mask())) return true;
                        }
                    }
                    return handler.findFirstCurio(stack -> stack.is(mask())).isPresent();
                })
                .orElse(false);
    }

    private static net.minecraft.world.item.Item mask() {
        return net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("goety_supreme", "infernal_mask"));
    }
}
