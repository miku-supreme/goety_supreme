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
import cn.blockforge.generated.goetysupreme.config.SedimentMissileFocusConfig;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;
import cn.blockforge.generated.goetysupreme.spell.SedimentMissileFocusSpell;

public final class SedimentMissileFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return SedimentMissileFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return SedimentMissileFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return SedimentMissileFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NECROMANCY;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.VELOCITY.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        boolean staffBoost = staffBoost(wandStack);
        int spellLevel = 1 + (staffBoost ? 1 : 0);
        int shots = 3;
        int potency = potency(wandStack, stats);
        JerotesFocusBurst.showCastDisplay(level, caster, "sediment_missile_display", ParticleTypes.SOUL,
                1 + potency);
        int velocity = focusEnchantmentLevel(wandStack, ModEnchantments.VELOCITY.get());
        fire(level, caster, potency, velocity);
        if (shots > 1) {
            JerotesFocusBurst.scheduleExtraShots(shots, burstInterval(), caster,
                    () -> fire(level, caster, potency, velocity));
        }
    }

    private int burstInterval() {
        return Math.max(1, SedimentMissileFocusConfig.BURST_INTERVAL_TICKS.get());
    }

    private boolean staffBoost(ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && rightStaff(wandStack);
    }

    private int potency(ItemStack wandStack, SpellStat stats) {
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        potency += staffBoost(wandStack) ? 1 : 0;
        return potency;
    }

    private void fire(ServerLevel level, LivingEntity caster, int potency, int velocity) {
        JerotesFocusBurst.castSound(caster, "magic_sediment_missile", SoundEvents.EVOKER_CAST_SPELL);
        SedimentMissileEntity missile = new SedimentMissileEntity(level, caster);
        missile.setPos(caster.getX(), caster.getEyeY() - 0.2D, caster.getZ());
        missile.setDamageBonus(potency);
        Vec3 look = caster.getLookAngle();
        float speed = SedimentMissileFocusConfig.PROJECTILE_SPEED.get().floatValue();
        speed += (float) (Math.max(0, velocity) * SedimentMissileFocusConfig.VELOCITY_BONUS_PER_LEVEL.get());
        float inaccuracy = SedimentMissileFocusConfig.INACCURACY.get().floatValue();
        missile.shoot(look.x, look.y, look.z, speed, inaccuracy);
        level.addFreshEntity(missile);
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = com.Polarice3.Goety.api.items.magic.IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty() || enchantment == null) {
            return 0;
        }
        return focus.getEnchantmentLevel(enchantment);
    }
}
