package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.BitterColdWindFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.spell.BitterColdWindFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class BitterColdWindFocusSpell extends SupremeSummonSpell {

    @Override
    public int defaultSoulCost() {
        return BitterColdWindFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return BitterColdWindFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return BitterColdWindFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return BitterColdWindFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return ModSounds.FROST_PREPARE_SPELL.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.FROST;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.DURATION.get());
        return enchantments;
    }

    @Override
    public Predicate<LivingEntity> summonPredicate() {
        return entity -> entity instanceof BitterColdWindServant;
    }

    @Override
    public int summonLimit() {
        return BitterColdWindFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.SNOWFLAKE, caster.getX(), caster.getY(0.5D), caster.getZ(),
                16, 0.45D, 0.6D, 0.45D, 0.02D);
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));
        int count = summonCount(caster, wandStack);

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            BitterColdWindServant wind = ModEntityTypes.BITTER_COLD_WIND_SERVANT.get().create(level);
            if (wind == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, wind, i);
            wind.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(wind);
            wind.setTrueOwner(caster);
            wind.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            wind.setTrueOwner(caster);
            wind.setLimitedLife(lifespanFor(durationLevel));
            wind.setPersistenceRequired();
            buffSummon(caster, wind, potencyLevel);
            setInitialTarget(wind, caster);

            if (level.addFreshEntity(wind)) {
                ++spawned;
                summonAdvancement(caster, wind);
                GoetySummonFx.play(level, wind);
                wind.playSummonParticles();
                level.playSound(null, wind.getX(), wind.getY(0.7D), wind.getZ(),
                        SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 0.8F,
                        1.2F + wind.getRandom().nextFloat() * 0.3F);
            } else {
                wind.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
            this.playSound(level, caster, ModSounds.SUMMON_SPELL.get());
        }
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (wandStack != null && !wandStack.isEmpty() && rightStaff(wandStack)) {
            return SupremeFociConfig.randomSummonCount(caster,
                    BitterColdWindFocusConfig.STAFF_SUMMON_COUNT_MIN,
                    BitterColdWindFocusConfig.STAFF_SUMMON_COUNT_MAX);
        }
        return 1;
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = focusStack(wandStack);
        return focus.isEmpty() || enchantment == null ? 0 : focus.getEnchantmentLevel(enchantment);
    }

    private ItemStack focusStack(ItemStack wandStack) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)) {
            return ItemStack.EMPTY;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        return focus == null ? ItemStack.EMPTY : focus;
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = BitterColdWindFocusConfig.LIFESPAN_TICKS.get().longValue()
                + Math.max(0L, (long) durationLevel) * BitterColdWindFocusConfig.DURATION_BONUS_TICKS.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private void setInitialTarget(BitterColdWindServant wind, LivingEntity caster) {
        LivingEntity target = caster instanceof net.minecraft.world.entity.Mob mob
                ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !wind.isAlliedTo(target)) {
            wind.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, BitterColdWindServant wind,
            int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, wind, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 3) : candidate;
    }
}
