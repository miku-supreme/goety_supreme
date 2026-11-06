package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.common.items.ModItems;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.SnowballFocusConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.spell.SnowballFocusSpell;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.util.SpellSnowball;

public final class SnowballFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return SnowballFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return SnowballFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return SnowballFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.FROST;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.VELOCITY.get());
        return enchantments;
    }

    @Override
    public void useSpell(ServerLevel level, LivingEntity caster, ItemStack wandStack, int castTime, SpellStat stats) {
        int base = Math.max(1, SnowballFocusConfig.THROW_INTERVAL.get());
        double rate = Math.max(1.0D, SnowballFocusConfig.RATE_MULTIPLIER.get());
        int interval = Math.max(1, (int) Math.round(base / rate));
        if ((castTime - 1) % interval == 0) {
            throwSnowball(level, caster, wandStack);
        }
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
    }

    private void throwSnowball(ServerLevel level, LivingEntity caster, ItemStack wandStack) {
        int potency = focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int velocity = focusEnchantmentLevel(wandStack, ModEnchantments.VELOCITY.get());
        boolean frostStaff = !wandStack.isEmpty() && wandStack.is(ModItems.FROST_STAFF.get());
        double speed = (frostStaff ? SnowballFocusConfig.SNOWBALL_SPEED.get()
                : SnowballFocusConfig.SNOWBALL_SPEED_BASE.get())
                + SnowballFocusConfig.VELOCITY_BONUS_PER_LEVEL.get() * velocity;
        float baseDamage = (frostStaff ? SnowballFocusConfig.SNOWBALL_DAMAGE
                : SnowballFocusConfig.SNOWBALL_DAMAGE_BASE).get().floatValue();
        float inaccuracy = SnowballFocusConfig.INACCURACY.get().floatValue();

        SpellSnowball snowball = new SpellSnowball(level, caster, potency, baseDamage);
        LivingEntity target = getTarget(caster, 24);
        if (target != null && target != caster && !SnowballVolley.isAlliedTo(caster, target)) {
            Vec3 from = snowball.position();
            Vec3 aimAt = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
            BallisticAim.Solution aim = BallisticAim.solveFast(from, aimAt,
                    SpellSnowball.GRAVITY, SpellSnowball.DRAG,
                    SpellSnowball.MIN_SPEED, Math.max(SpellSnowball.MIN_SPEED, speed));
            Vec3 shot = aim.velocity();
            snowball.shoot(shot.x, shot.y, shot.z, (float) shot.length(), inaccuracy);
        } else {
            Vec3 look = caster.getLookAngle();
            snowball.shoot(look.x, look.y, look.z, (float) speed, inaccuracy);
        }
        level.addFreshEntity(snowball);
        level.playSound(null, caster.getX(), caster.getY(0.7D), caster.getZ(),
                SoundEvents.SNOW_GOLEM_SHOOT, SoundSource.PLAYERS, 0.6F,
                0.4F / (caster.getRandom().nextFloat() * 0.4F + 0.8F));
        level.sendParticles(ParticleTypes.SNOWFLAKE, caster.getX() + lookX(caster), caster.getY(1.2D),
                caster.getZ() + lookZ(caster), 2, 0.05D, 0.05D, 0.05D, 0.0D);
    }

    private static double lookX(LivingEntity caster) {
        Vec3 look = caster.getLookAngle();
        return look.x * 0.5D;
    }

    private static double lookZ(LivingEntity caster) {
        Vec3 look = caster.getLookAngle();
        return look.z * 0.5D;
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)
                || enchantment == null) {
            return 0;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        return focus == null || focus.isEmpty() ? 0 : focus.getEnchantmentLevel(enchantment);
    }
}
