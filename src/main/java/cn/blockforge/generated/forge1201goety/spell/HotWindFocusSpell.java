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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.HotWindFocusConfig;
import cn.blockforge.generated.goetysupreme.spell.HotWindFocusSpell;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponSiroccoEntity;

public final class HotWindFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return HotWindFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return HotWindFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return HotWindFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NETHER;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.RADIUS.get());
        enchantments.add(ModEnchantments.RANGE.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        JerotesFocusBurst.castSound(caster, "magic_serpon_sirocco", SoundEvents.BLAZE_SHOOT);
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "serpon_sirocco_display", ParticleTypes.CRIT, spellLevel);
        float damage = (float) (HotWindFocusConfig.BASE_DAMAGE.get() * spellLevel);
        double radiusBonus = radiusBonus(wandStack);
        int lifeBonus = lifeBonus(wandStack);
        volley(level, caster, spellLevel, damage, radiusBonus, lifeBonus);
        if (staffBoost(wandStack)) {
            int shots = Math.max(1, HotWindFocusConfig.STAFF_BURST_SHOTS.get());
            int interval = Math.max(1, HotWindFocusConfig.STAFF_BURST_INTERVAL_TICKS.get());
            JerotesFocusBurst.scheduleExtraShots(shots, interval, caster,
                    () -> volley(level, caster, spellLevel, damage, radiusBonus, lifeBonus));
        }
    }

    @Override
    public void useSpell(ServerLevel level, LivingEntity caster, ItemStack wandStack, int castTime, SpellStat stats) {
        spawnChargeParticles(level, caster);
    }

    private void spawnChargeParticles(ServerLevel level, LivingEntity caster) {
        double cx = caster.getX();
        double cy = caster.getY(0.5D);
        double cz = caster.getZ();
        double radius = 3.0D;
        for (int i = 0; i < 40; ++i) {
            double angle = caster.getRandom().nextDouble() * Math.PI * 2.0D;
            double inclination = caster.getRandom().nextDouble() * Math.PI;
            double px = cx + radius * Math.sin(inclination) * Math.cos(angle);
            double py = cy + radius * Math.cos(inclination);
            double pz = cz + radius * Math.sin(inclination) * Math.sin(angle);
            double dx = cx - px;
            double dy = cy - py;
            double dz = cz - pz;
            double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (len < 1.0E-4D) {
                len = 1.0D;
            }
            level.sendParticles(ParticleTypes.FLAME, px, py, pz, 0,
                    dx / len * 0.15D, dy / len * 0.15D, dz / len * 0.15D, 0.0D);
        }
    }

    private void volley(ServerLevel level, LivingEntity caster, int spellLevel, float damage,
            double radiusBonus, int lifeBonus) {
        int count = Math.max(1, spellLevel);
        float distance = 12.0F;
        for (int i = 0; i < count; ++i) {
            SerponSiroccoEntity sirocco = new SerponSiroccoEntity(level, caster, spellLevel);
            sirocco.setPos(caster.getX(), caster.getY(0.7D) - sirocco.getBbHeight() / 2.5D, caster.getZ());
            sirocco.setDamageOverride(damage);
            sirocco.setRadiusBonus(radiusBonus);
            sirocco.setLifeBonus(lifeBonus);
            sirocco.shootFromRotation(caster, caster.getXRot(),
                    caster.getYRot() - (count - 1) * distance / 2.0F + i * distance,
                    0.0F, 0.45F, 0.45F);
            level.addFreshEntity(sirocco);
        }
        level.playSound(null, caster.getX(), caster.getY(0.7D), caster.getZ(),
                SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 10.0F, 1.0F);
    }

    private double radiusBonus(ItemStack wandStack) {
        return focusEnchantmentLevel(wandStack, ModEnchantments.RADIUS.get())
                * HotWindFocusConfig.RADIUS_BONUS_PER_LEVEL.get();
    }

    private int lifeBonus(ItemStack wandStack) {
        return focusEnchantmentLevel(wandStack, ModEnchantments.RANGE.get())
                * Math.max(0, HotWindFocusConfig.RANGE_TICKS_PER_LEVEL.get());
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, HotWindFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? HotWindFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
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
