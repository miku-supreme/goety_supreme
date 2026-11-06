package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.CorrosiveIceSpikeFocusConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.spell.CorrosiveIceSpikeFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveIceSpikeServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class CorrosiveIceSpikeFocusSpell extends Spell {

    private static final double SHOOT_SPEED = 1.5D;

    private static final double MIN_SHOOT_SPEED = 0.5D;

    private static final Vec3 RIGHT_AXIS = new Vec3(0.0D, 1.0D, 0.0D);

    @Override
    public int defaultSoulCost() {
        return CorrosiveIceSpikeFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return CorrosiveIceSpikeFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return CorrosiveIceSpikeFocusConfig.COOLDOWN.get();
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
        JerotesFocusBurst.castSound(caster, "magic_corrosive_ice_spike", SoundEvents.PLAYER_HURT_FREEZE);
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "corrosive_ice_spike_display", ParticleTypes.SNOWFLAKE,
                spellLevel);
        float damage = (float) (CorrosiveIceSpikeFocusConfig.BASE_DAMAGE.get() * spellLevel);
        int count = Math.max(1, spellLevel);
        Vec3 look = caster.getLookAngle();
        Vec3 aim = servantAim(caster);
        for (int i = 0; i < count; ++i) {
            double spread = -(count - 1) * 5.0D / 2.0D + i * 5.0D;
            Vec3 dir = aim != null ? spreadAround(aim, spread) : JerotesFocusBurst.rotateY(look, spread);
            CorrosiveIceSpikeServantEntity spike = new CorrosiveIceSpikeServantEntity(level, caster);
            spike.setPos(caster.getX() + dir.x, caster.getEyeY() - 0.2D, caster.getZ() + dir.z);
            spike.setSpellLevel(spellLevel);
            spike.setDamageOverride(damage);
            spike.setNoGravity(false);
            spike.shoot(dir.x, dir.y, dir.z, 1.5F, 0.0F);
            level.addFreshEntity(spike);
        }
    }

    @Nullable
    private Vec3 servantAim(LivingEntity caster) {
        if (!(caster instanceof PlayerMobServant servant)) {
            return null;
        }
        LivingEntity target = servant.getTarget();
        if (target == null || target == caster || !target.isAlive()
                || servant.isAlliedTo(target) || target.isAlliedTo(servant)) {
            return null;
        }
        Vec3 from = new Vec3(caster.getX(), caster.getEyeY() - 0.2D, caster.getZ());
        Vec3 center = target.getBoundingBox().getCenter();
        BallisticAim.Solution first = BallisticAim.solveFast(from, center,
                CorrosiveIceSpikeServantEntity.GRAVITY, CorrosiveIceSpikeServantEntity.DRAG,
                MIN_SHOOT_SPEED, SHOOT_SPEED);
        BallisticAim.Solution solved = BallisticAim.solveFast(from,
                center.add(BallisticAim.leadOffset(target.getDeltaMovement(), first.ticks())),
                CorrosiveIceSpikeServantEntity.GRAVITY, CorrosiveIceSpikeServantEntity.DRAG,
                MIN_SHOOT_SPEED, SHOOT_SPEED);
        Vec3 velocity = solved.velocity();
        return velocity.lengthSqr() < 1.0E-6D ? null : velocity.normalize();
    }

    private static Vec3 spreadAround(Vec3 dir, double degrees) {
        double radians = Math.toRadians(degrees);
        Vec3 left = RIGHT_AXIS.cross(dir);
        if (left.lengthSqr() < 1.0E-6D) {
            return dir;
        }
        left = left.normalize();
        return dir.scale(Math.cos(radians)).add(left.scale(Math.sin(radians))).normalize();
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, CorrosiveIceSpikeFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? CorrosiveIceSpikeFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
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
