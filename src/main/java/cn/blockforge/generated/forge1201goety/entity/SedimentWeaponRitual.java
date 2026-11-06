package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.blocks.entities.DarkAltarBlockEntity;
import com.Polarice3.Goety.common.crafting.RitualRecipe;
import com.Polarice3.Goety.common.ritual.SummonRitual;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentWeaponRitual;

public class SedimentWeaponRitual extends SummonRitual {

    private ItemStack finishingWeapon = ItemStack.EMPTY;

    public SedimentWeaponRitual(RitualRecipe recipe) {
        super(recipe, true, true);
    }

    @Override
    public void finish(Level level, BlockPos pos, DarkAltarBlockEntity altar, Player player, ItemStack stack) {
        this.finishingWeapon = stack == null || stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        try {
            super.finish(level, pos, altar, player, stack);
        } finally {
            this.finishingWeapon = ItemStack.EMPTY;
        }
    }

    @Override
    public void initSummoned(LivingEntity entity, Level level, BlockPos pos, DarkAltarBlockEntity altar,
            Player player) {
        super.initSummoned(entity, level, pos, altar, player);
        if (!finishingWeapon.isEmpty() && entity instanceof Mob mob) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, finishingWeapon.copy());
            mob.setGuaranteedDrop(EquipmentSlot.MAINHAND);
        }
    }
}
