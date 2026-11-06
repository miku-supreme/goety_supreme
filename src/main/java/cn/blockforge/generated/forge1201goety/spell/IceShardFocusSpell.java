package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.IceShardFocusConfig;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.spell.IceShardFocusSpell;
import cn.blockforge.generated.goetysupreme.util.MutantThrowStandIns;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.block.SpellIceShardBlock;

public final class IceShardFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return IceShardFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return 0;
    }

    @Override
    public int defaultSpellCooldown() {
        return IceShardFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.FROST;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.RADIUS.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        MutantSnowGolem standIn = MutantThrowStandIns.forLevel(level);
        if (standIn == null) {
            return;
        }
        int potency = focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int radius = focusEnchantmentLevel(wandStack, ModEnchantments.RADIUS.get());
        boolean frostStaff = !wandStack.isEmpty() && wandStack.is(ModItems.FROST_STAFF.get());
        standIn.setPos(caster.getX(), caster.getY(), caster.getZ());
        standIn.setYRot(caster.getYRot());
        standIn.yHeadRot = caster.getYHeadRot();
        standIn.yBodyRot = caster.getYRot();

        SpellIceShardBlock ice = new SpellIceShardBlock(standIn, caster, potency, radius, frostStaff);
        float inaccuracy = IceShardFocusConfig.INACCURACY.get().floatValue();
        double speed = (frostStaff ? IceShardFocusConfig.THROW_SPEED
                : IceShardFocusConfig.THROW_SPEED_BASE).get();
        double aimMax = (frostStaff ? IceShardFocusConfig.AIM_MAX_SPEED
                : IceShardFocusConfig.AIM_MAX_SPEED_BASE).get();
        LivingEntity target = getTarget(caster, 24);
        if (target != null && target != caster && !SnowballVolley.isAlliedTo(caster, target)) {
            Vec3 from = ice.position();
            Vec3 aimAt = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
            BallisticAim.Solution solution = frostStaff
                    ? BallisticAim.solveFast(from, aimAt, 0.06D, 0.99D, 0.5D, aimMax)
                    : BallisticAim.solve(from, aimAt, 0.06D, 0.99D, 0.5D, aimMax);
            solution = frostStaff
                    ? BallisticAim.solveFast(from,
                            aimAt.add(BallisticAim.leadOffset(target.getDeltaMovement(), solution.ticks() * 0.8D)),
                            0.06D, 0.99D, 0.5D, aimMax)
                    : BallisticAim.solve(from,
                            aimAt.add(BallisticAim.leadOffset(target.getDeltaMovement(), solution.ticks() * 0.8D)),
                            0.06D, 0.99D, 0.5D, aimMax);
            Vec3 velocity = solution.velocity();
            ice.shoot(velocity.x, velocity.y, velocity.z, (float) velocity.length(), inaccuracy);
        } else {
            Vec3 look = caster.getLookAngle();
            ice.shoot(look.x, look.y + 0.35D, look.z, (float) speed, 1.0F);
        }
        level.addFreshEntity(ice);
        level.sendParticles(ParticleTypes.SNOWFLAKE, caster.getX(), caster.getY(1.2D), caster.getZ(),
                6, 0.25D, 0.35D, 0.25D, 0.01D);
        level.playSound(null, caster.getX(), caster.getY(0.7D), caster.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0F,
                0.6F + caster.getRandom().nextFloat() * 0.3F);
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
