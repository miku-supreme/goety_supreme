package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.EndermanServantFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.spell.EndermanServantFocusSpell;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class EndermanServantFocusSpell extends SupremeSummonSpell {

    public static final SoundEvent CASTING_SOUND = SoundEvents.EVOKER_CAST_SPELL;

    @Override
    public int defaultSoulCost() {
        return EndermanServantFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return EndermanServantFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return EndermanServantFocusConfig.COOLDOWN.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return CASTING_SOUND;
    }

    @Override
    public int SummonDownDuration() {
        return EndermanServantFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.VOID;
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
        return entity -> entity instanceof EndermanServant;
    }

    @Override
    public int summonLimit() {
        return EndermanServantFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.PORTAL, caster.getX(), caster.getY(0.6D), caster.getZ(),
                16, 0.35D, 0.5D, 0.35D, 0.2D);
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = summonCount(caster, wandStack);
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            EndermanServant servant = ModEntityTypes.ENDERMAN_SERVANT.get().create(level);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.MOB_SUMMONED,
                    null, null);
            servant.setTrueOwner(caster);
            servant.setLimitedLife(lifespanFor(durationLevel));
            servant.setPersistenceRequired();
            buffSummon(caster, servant, potencyLevel);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.PORTAL, servant.getX(), servant.getY(1.0D), servant.getZ(),
                        24, 0.5D, 0.9D, 0.5D, 0.18D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.ENDERMAN_TELEPORT, net.minecraft.sounds.SoundSource.PLAYERS, 0.9F,
                        0.9F + servant.getRandom().nextFloat() * 0.2F);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
        }
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (rightStaff(wandStack)) {
            return SupremeFociConfig.randomSummonCount(caster,
                    EndermanServantFocusConfig.STAFF_SUMMON_COUNT_MIN,
                    EndermanServantFocusConfig.STAFF_SUMMON_COUNT_MAX);
        }
        return SupremeFociConfig.randomSummonCount(caster,
                EndermanServantFocusConfig.SUMMON_COUNT_MIN,
                EndermanServantFocusConfig.SUMMON_COUNT_MAX);
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
        long lifespan = EndermanServantFocusConfig.LIFESPAN_TICKS.get().longValue()
                + Math.max(0L, (long) durationLevel) * EndermanServantFocusConfig.DURATION_BONUS_TICKS.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private void setInitialTarget(EndermanServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 2, 0, 3) : candidate;
    }
}
