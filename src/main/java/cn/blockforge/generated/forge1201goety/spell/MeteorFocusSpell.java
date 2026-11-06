package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
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
import cn.blockforge.generated.goetysupreme.config.MeteorFocusConfig;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.spell.MeteorFocusSpell;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerpenperthMeteorEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;

public final class MeteorFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return MeteorFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return MeteorFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return MeteorFocusConfig.COOLDOWN.get();
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
        JerotesFocusBurst.castSound(caster, "magic_serpenperth_meteor", SoundEvents.GENERIC_EXPLODE);
        int spellLevel = spellLevel(stats, wandStack);
        JerotesFocusBurst.showCastDisplay(level, caster, "serpenperth_meteor_display", ParticleTypes.FLAME,
                spellLevel);
        float damage = (float) (MeteorFocusConfig.BASE_DAMAGE.get() * spellLevel);
        int countMin = Math.max(3, spellLevel);
        int countMax = Math.max(6, spellLevel * 2);
        int count = countMin + caster.getRandom().nextInt(countMax - countMin + 1);
        double radiusBonus = focusEnchantmentLevel(wandStack, ModEnchantments.RADIUS.get())
                * MeteorFocusConfig.RADIUS_BONUS_PER_LEVEL.get();
        double aimRange = MeteorFocusConfig.AIM_RANGE.get()
                + focusEnchantmentLevel(wandStack, ModEnchantments.RANGE.get())
                * MeteorFocusConfig.RANGE_BONUS_PER_LEVEL.get();
        Vec3 aim = aimPoint(level, caster, aimRange);
        double spread = MeteorFocusConfig.SUMMON_DISTANCE.get();
        int summonDistance = Math.max(1, (int) Math.round(spread));
        float summonHeight = caster.getBbHeight() + 15.0F;
        BlockPos basePos = BlockPos.containing(aim);
        for (int i = 0; i < count; ++i) {
            BlockPos shootPos = findSpawnPositionNear(level, basePos, Math.max(1, summonDistance / 3));
            Vec3 startPos = new Vec3(shootPos.getX() + 0.5D, shootPos.getY() + summonHeight,
                    shootPos.getZ() + 0.5D);
            BlockPos landing = findSpawnPositionNear(level, basePos, summonDistance);
            SerpenperthMeteorEntity cloud =
                    new SerpenperthMeteorEntity(ModEntityTypes.SERPENPERTH_METEOR.get(), level);
            cloud.setSpellLevelDamage(spellLevel);
            cloud.setDamageOverride(damage);
            cloud.setRadiusBonus(radiusBonus);
            cloud.setPos(landing.getX() + 0.5D, aim.y + 0.125D, landing.getZ() + 0.5D);
            cloud.setStartPos(startPos);
            cloud.setOwner(caster);
            cloud.setOrder(i);
            cloud.setNoBlockFire(caster.isShiftKeyDown());
            level.addFreshEntity(cloud);
        }
    }

    private static Vec3 aimPoint(ServerLevel level, LivingEntity caster, double range) {
        Vec3 start = caster.getEyePosition();
        Vec3 end = start.add(caster.getLookAngle().scale(range));
        BlockHitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster));
        return hit.getType() == HitResult.Type.MISS ? end : hit.getLocation();
    }

    private static BlockPos findSpawnPositionNear(ServerLevel level, BlockPos base, int n) {
        return SerpenperthMeteorEntity.findSpawnPositionNear(level, base, n);
    }

    private int spellLevel(SpellStat stats, ItemStack wandStack) {
        int level = Math.max(1, MeteorFocusConfig.BASE_LEVEL.get());
        int potency = stats == null ? 0 : Math.max(0, stats.getPotency());
        potency += focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        int bonus = staffBoost(wandStack) ? MeteorFocusConfig.STAFF_LEVEL_BONUS.get() : 0;
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
