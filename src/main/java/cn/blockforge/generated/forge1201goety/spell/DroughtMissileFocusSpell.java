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
import cn.blockforge.generated.goetysupreme.config.DroughtMissileFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.spell.DroughtMissileFocusSpell;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;

public final class DroughtMissileFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return DroughtMissileFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return DroughtMissileFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return DroughtMissileFocusConfig.COOLDOWN.get();
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
        enchantments.add(ModEnchantments.VELOCITY.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        boolean staffBoost = staffBoost(wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "drought_fireball_display", ParticleTypes.FLAME,
                spellLevel(staffBoost));
        int burnSeconds = burnSeconds(wandStack, stats);
        int velocity = focusEnchantmentLevel(wandStack, ModEnchantments.VELOCITY.get());
        fire(level, caster, burnSeconds, staffBoost, velocity);
        int spellLevel = spellLevel(staffBoost);
        int shots = 3;
        if (shots > 1) {
            int interval = Math.max(1, DroughtMissileFocusConfig.BURST_INTERVAL_TICKS.get());
            JerotesFocusBurst.scheduleExtraShots(shots, interval, caster,
                    () -> fire(level, caster, burnSeconds, staffBoost, velocity));
        }
    }

    private boolean staffBoost(ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && rightStaff(wandStack);
    }

    private static int spellLevel(boolean staffBoost) {
        return 1 + (staffBoost ? 1 : 0);
    }

    private int burnSeconds(ItemStack wandStack, SpellStat stats) {
        int base = Math.max(0, DroughtMissileFocusConfig.BURN_SECONDS.get());
        int burning = stats == null ? 0 : Math.max(0, stats.getBurning());
        burning += focusEnchantmentLevel(wandStack, ModEnchantments.BURNING.get());
        return base + burning * 3;
    }

    private void fire(ServerLevel level, LivingEntity caster, int burnSeconds, boolean staffBoost, int velocity) {
        JerotesFocusBurst.castSound(caster, "magic_drought_fireball", SoundEvents.BLAZE_SHOOT);
        Vec3 look = caster.getLookAngle();
        double spread = DroughtMissileFocusConfig.INACCURACY.get() * 0.01D;
        Vec3 jitter = new Vec3(
                (caster.getRandom().nextDouble() - 0.5D) * spread,
                (caster.getRandom().nextDouble() - 0.5D) * spread,
                (caster.getRandom().nextDouble() - 0.5D) * spread);
        double speed = DroughtMissileFocusConfig.PROJECTILE_SPEED.get();
        speed += Math.max(0, velocity) * DroughtMissileFocusConfig.VELOCITY_BONUS_PER_LEVEL.get();
        if (staffBoost) {
            speed *= DroughtMissileFocusConfig.STAFF_SPEED_MULTIPLIER.get();
        }
        DroughtFireballServantEntity fireball = new DroughtFireballServantEntity(
                level, caster, look.add(jitter).normalize().scale(speed), burnSeconds);
        int levelDamage = Math.max(0, spellLevel(staffBoost) - 1);
        fireball.setDamageBonus((staffBoost ? DroughtMissileFocusConfig.STAFF_DAMAGE_BONUS.get() : 0)
                + levelDamage);
        level.addFreshEntity(fireball);
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = com.Polarice3.Goety.api.items.magic.IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty() || enchantment == null) {
            return 0;
        }
        return focus.getEnchantmentLevel(enchantment);
    }
}
