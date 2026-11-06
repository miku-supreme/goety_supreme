package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.SerponBlazingFocusConfig;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.spell.SerponBlazingFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;

public final class SerponBlazingFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return SerponBlazingFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return SerponBlazingFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return SerponBlazingFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NETHER;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.BURNING.get());
        enchantments.add(ModEnchantments.RADIUS.get());
        enchantments.add(ModEnchantments.RANGE.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "serpon_flames_display", ParticleTypes.FLAME, spellLevel);
        float angle = spellAngle(wandStack);
        double reach = reach(wandStack);
        breathe(level, caster, spellLevel, angle, reach);
        int shots = Math.max(1, SerponBlazingFocusConfig.BURST_SHOTS.get());
        if (shots > 1) {
            int interval = Math.max(1, SerponBlazingFocusConfig.BURST_INTERVAL_TICKS.get());
            JerotesFocusBurst.scheduleExtraShots(shots, interval, caster,
                    () -> breathe(level, caster, spellLevel, angle, reach));
        }
    }

    private void breathe(ServerLevel level, LivingEntity caster, int spellLevel, float angle, double reach) {
        JerotesFocusBurst.castSound(caster, "magic_serpon_flames", SoundEvents.BLAZE_SHOOT);
        SerponFlamesEntity flames = new SerponFlamesEntity(level, caster, spellLevel);
        flames.setSpellAngle(angle);
        flames.setReach(reach);
        level.addFreshEntity(flames);
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, SerponBlazingFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? SerponBlazingFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
        return Math.max(1, level + potency + bonus);
    }

    private float spellAngle(ItemStack wandStack) {
        double perLevel = SerponBlazingFocusConfig.RADIUS_ANGLE_PER_LEVEL.get();
        return (float) (SerponFlamesEntity.SPELL_ANGLE
                + focusEnchantmentLevel(wandStack, ModEnchantments.RADIUS.get()) * perLevel);
    }

    private double reach(ItemStack wandStack) {
        double perLevel = SerponBlazingFocusConfig.RANGE_DISTANCE_PER_LEVEL.get();
        return SerponFlamesEntity.REACH
                + focusEnchantmentLevel(wandStack, ModEnchantments.RANGE.get()) * perLevel;
    }

    private boolean staffBoost(ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && rightStaff(wandStack);
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = com.Polarice3.Goety.api.items.magic.IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty() || enchantment == null) {
            return 0;
        }
        return focus.getEnchantmentLevel(enchantment);
    }
}
