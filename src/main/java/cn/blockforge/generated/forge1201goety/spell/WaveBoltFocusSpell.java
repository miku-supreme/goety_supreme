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
import cn.blockforge.generated.goetysupreme.config.WaveBoltFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.BulgeBulletServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.WaveBoltFocusSpell;

public final class WaveBoltFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return WaveBoltFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return WaveBoltFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return WaveBoltFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.ABYSS;
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
        int potency = potency(wandStack, stats);
        boolean staffBoost = staffBoost(wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "bulge_bullet_display", ParticleTypes.SPLASH,
                1 + potency + (staffBoost ? 1 : 0));
        int velocity = focusEnchantmentLevel(wandStack, ModEnchantments.VELOCITY.get());
        fire(level, caster, potency, staffBoost, velocity);
        int spellLevel = spellLevel(staffBoost);
        int shots = 3;
        if (shots > 1) {
            int interval = Math.max(1, WaveBoltFocusConfig.BURST_INTERVAL_TICKS.get());
            JerotesFocusBurst.scheduleExtraShots(shots, interval, caster,
                    () -> fire(level, caster, potency, staffBoost, velocity));
        }
    }

    private static int spellLevel(boolean staffBoost) {
        return 1 + (staffBoost ? 1 : 0);
    }

    private boolean staffBoost(ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && rightStaff(wandStack);
    }

    private int potency(ItemStack wandStack, SpellStat stats) {
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        return potency + focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
    }

    private void fire(ServerLevel level, LivingEntity caster, int potency, boolean staffBoost, int velocity) {
        JerotesFocusBurst.castSound(caster, "magic_bulge_bullet", SoundEvents.SNOWBALL_THROW);
        BulgeBulletServant bullet = new BulgeBulletServant(
                ModEntityTypes.BULGE_BULLET_SERVANT.get(), caster, level, Math.max(1, potency));
        bullet.setPos(caster.getX(), caster.getEyeY() - 0.2D, caster.getZ());
        int levelDamage = staffBoost ? 1 : 0;
        bullet.setDamageBonus((staffBoost ? WaveBoltFocusConfig.STAFF_DAMAGE_BONUS.get() : 0)
                + levelDamage);
        float speed = WaveBoltFocusConfig.PROJECTILE_SPEED.get().floatValue();
        speed += (float) (Math.max(0, velocity) * WaveBoltFocusConfig.VELOCITY_BONUS_PER_LEVEL.get());
        if (staffBoost) {
            speed *= WaveBoltFocusConfig.STAFF_SPEED_MULTIPLIER.get().floatValue();
        }
        float inaccuracy = WaveBoltFocusConfig.INACCURACY.get().floatValue();
        bullet.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0.0F, speed, inaccuracy);
        level.addFreshEntity(bullet);
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = com.Polarice3.Goety.api.items.magic.IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty() || enchantment == null) {
            return 0;
        }
        return focus.getEnchantmentLevel(enchantment);
    }
}
