package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.MalignasaurFocusAim;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;

public final class MalignasaurFocusAim {

    private static final double AIM_RANGE = 24.0D;

    private static final double AIM_RANGE_PER_LEVEL = 2.0D;

    private static final double TARGET_RADIUS = 3.0D;

    private static final double TARGET_RADIUS_PER_LEVEL = 1.0D;

    private MalignasaurFocusAim() {
    }

    public static LivingEntity target(ServerLevel level, LivingEntity caster, ItemStack wandStack) {
        double range = AIM_RANGE + enchantmentLevel(wandStack, ModEnchantments.RANGE.get())
                * AIM_RANGE_PER_LEVEL;
        Vec3 start = caster.getEyePosition();
        Vec3 end = start.add(caster.getLookAngle().scale(range));
        BlockHitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster));
        Vec3 aim = hit.getType() == HitResult.Type.MISS ? end : hit.getLocation();
        double radius = Math.max(0.5D, TARGET_RADIUS + enchantmentLevel(wandStack,
                ModEnchantments.RADIUS.get()) * TARGET_RADIUS_PER_LEVEL);
        LivingEntity best = null;
        double bestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class,
                new AABB(aim, aim).inflate(radius))) {
            if (candidate == caster || !candidate.isAlive() || candidate.isSpectator()
                    || !caster.canAttack(candidate)) {
                continue;
            }
            double distance = candidate.distanceToSqr(aim);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best == null ? caster : best;
    }

    public static int spellLevel(Spell spell, SpellStat stats, ItemStack wandStack, int baseLevel,
            int staffLevelBonus) {
        int level = Math.max(1, baseLevel);
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += enchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(spell, wandStack) ? Math.max(0, staffLevelBonus) : 0;
        return Math.max(1, level + potency + bonus);
    }

    public static boolean staffBoost(Spell spell, ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && spell.rightStaff(wandStack);
    }

    public static int enchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        if (wandStack == null || wandStack.isEmpty() || enchantment == null) {
            return 0;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty()) {
            return 0;
        }
        return Math.max(0, focus.getEnchantmentLevel(enchantment));
    }
}
