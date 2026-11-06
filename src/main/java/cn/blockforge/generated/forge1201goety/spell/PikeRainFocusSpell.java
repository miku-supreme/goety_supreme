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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.PikeRainFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.spell.PikeRainFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;

public final class PikeRainFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return PikeRainFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return PikeRainFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return PikeRainFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NETHER;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        JerotesFocusBurst.castSound(caster, "serpon_combat_executer_javelin", SoundEvents.TRIDENT_THROW);
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "rain_of_serpon_javelin_display", ParticleTypes.CRIT,
                spellLevel);
        float damage = (float) (PikeRainFocusConfig.BASE_DAMAGE.get() * spellLevel);
        int count = Math.max(1, spellLevel) * PikeRainFocusConfig.PIKES_PER_LEVEL.get();
        Vec3 aim = aimPoint(level, caster);
        double spread = PikeRainFocusConfig.SPREAD.get();
        ItemStack pikeStack = AbstractJerotesServant.jerotesItem("serpon_javelin");
        net.minecraft.core.particles.ParticleOptions javelinParticle =
                AbstractJerotesServant.jerotesParticle("serpon_javelin", ParticleTypes.CRIT);
        net.minecraft.core.particles.ParticleOptions javelinDownParticle =
                AbstractJerotesServant.jerotesParticle("serpon_javelin_down", ParticleTypes.CRIT);
        for (int i = 0; i < count; ++i) {
            double x = aim.x + (caster.getRandom().nextDouble() - 0.5D) * spread * 2.0D;
            double z = aim.z + (caster.getRandom().nextDouble() - 0.5D) * spread * 2.0D;
            double y = aim.y + 12.0D;
            SerponJavelinEntity javelin = new SerponJavelinEntity(level, caster, pikeStack);
            javelin.setDamageOverride(damage);
            javelin.setNoBlockFire(caster.isShiftKeyDown());
            javelin.setPos(x, y, z);
            javelin.setDeltaMovement((caster.getRandom().nextDouble() - 0.5D) * 0.05D, -0.75D,
                    (caster.getRandom().nextDouble() - 0.5D) * 0.05D);
            level.addFreshEntity(javelin);
            level.sendParticles(javelinParticle, x, javelin.getY(1.5D), z, 0,
                    0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(javelinDownParticle, x, javelin.getY(1.5D), z, 0,
                    0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static Vec3 aimPoint(ServerLevel level, LivingEntity caster) {
        Vec3 start = caster.getEyePosition();
        Vec3 end = start.add(caster.getLookAngle().scale(PikeRainFocusConfig.AIM_RANGE.get()));
        BlockHitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster));
        return hit.getType() == HitResult.Type.MISS ? end : hit.getLocation();
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, PikeRainFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? PikeRainFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
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
