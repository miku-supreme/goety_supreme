package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithDamageGuard;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorSetBonus;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class PrismarineArmorSetBonus {

    private static final float DAMAGE_CUT_PER_PIECE = 0.10F;

    private static final int FULL_SET_PIECES = 4;

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private PrismarineArmorSetBonus() {
    }

    public static int prismarinePieces(LivingEntity entity) {
        int pieces = 0;
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isPrismarineArmor(entity.getItemBySlot(slot))) {
                pieces++;
            }
        }
        return pieces;
    }

    private static boolean isPrismarineArmor(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return stack.is(GeneratedMod.PRISMARINE_HELMET.get())
                || stack.is(GeneratedMod.PRISMARINE_CHESTPLATE.get())
                || stack.is(GeneratedMod.PRISMARINE_LEGGINGS.get())
                || stack.is(GeneratedMod.PRISMARINE_BOOTS.get());
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        if (!event.getSource().is(DamageTypeTags.IS_DROWNING)) {
            return;
        }
        if (prismarinePieces(event.getEntity()) >= FULL_SET_PIECES) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (event.getAmount() <= 0.0F) {
            return;
        }
        DamageSource source = event.getSource();
        boolean magic = source.is(DamageTypeTags.WITCH_RESISTANT_TO);
        if (!magic && !PrismarineSoldier.isLightningDamage(source)) {
            return;
        }
        int pieces = prismarinePieces(event.getEntity());
        if (pieces <= 0) {
            return;
        }
        event.setAmount(event.getAmount() * (1.0F - DAMAGE_CUT_PER_PIECE * pieces));
    }
}
