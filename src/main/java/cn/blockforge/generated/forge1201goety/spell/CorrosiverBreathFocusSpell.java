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
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.CorrosiverBreathFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.spell.CorrosiverBreathFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;

public final class CorrosiverBreathFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return CorrosiverBreathFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return CorrosiverBreathFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return CorrosiverBreathFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.FROST;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        JerotesFocusBurst.castSound(caster, "magic_corrosiver_breath", SoundEvents.RAVAGER_ROAR);
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "corrosiver_breath_display", ParticleTypes.SOUL_FIRE_FLAME,
                spellLevel);
        breathe(level, caster, spellLevel);
        if (staffBoost(wandStack)) {
            int shots = Math.max(1, CorrosiverBreathFocusConfig.STAFF_BURST_SHOTS.get());
            int interval = Math.max(1, CorrosiverBreathFocusConfig.BURST_INTERVAL_TICKS.get());
            JerotesFocusBurst.scheduleExtraShots(shots, interval, caster,
                    () -> breathe(level, caster, spellLevel));
        }
    }

    private void breathe(ServerLevel level, LivingEntity caster, int spellLevel) {
        Vec3 look = caster.getLookAngle();
        CorrosiverBreathAreaServantEntity area = new CorrosiverBreathAreaServantEntity(level, caster);
        area.setPos(caster.getX() + look.x * 2.25D,
                caster.getY() + caster.getBbHeight() * 0.25D + 0.125D,
                caster.getZ() + look.z * 2.25D);
        area.setSpellLevel(spellLevel);
        area.setDamageOverride((float) (CorrosiverBreathFocusConfig.BASE_DAMAGE.get() * spellLevel));
        area.setYRot(caster.getYRot());
        level.addFreshEntity(area);
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, CorrosiverBreathFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? CorrosiverBreathFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
        return Math.max(1, level + potency + bonus);
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
