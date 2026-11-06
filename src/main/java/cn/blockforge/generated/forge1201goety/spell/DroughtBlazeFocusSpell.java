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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.DroughtBlazeFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.spell.DroughtBlazeFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class DroughtBlazeFocusSpell extends SupremeSummonSpell {

    @Override
    public int defaultSoulCost() {
        return DroughtBlazeFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return DroughtBlazeFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return DroughtBlazeFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return DroughtBlazeFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NETHER;
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
        return entity -> entity instanceof DroughtBlazeServant;
    }

    @Override
    public int summonLimit() {
        return DroughtBlazeFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.FLAME, caster.getX(), caster.getY(0.5D), caster.getZ(),
                16, 0.45D, 0.6D, 0.45D, 0.02D);
    }

    @Override
    public void castReleaseSound(ServerLevel level, LivingEntity caster) {
        JerotesFocusBurst.castSound(caster, "magic_conjure_drought_blazes", SoundEvents.BLAZE_SHOOT);
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = summonCount(caster, wandStack);
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationMultiplier = Math.max(1, effectiveStats.getDuration()
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get())));

        for (int i = 0; i < count; i++) {
            DroughtBlazeServant servant = ModEntityTypes.DROUGHT_BLAZE_SERVANT.get().create(level);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            servant.setLimitedLife(DroughtBlazeFocusConfig.LIFESPAN_TICKS.get()
                    * Math.max(1, durationMultiplier));
            servant.setPersistenceRequired();
            servant.setSummonPotency(potencyLevel);
            buffSummon(caster, servant, potencyLevel);
            SummonSap(caster, servant);
            setTarget(caster, servant);

            if (level.addFreshEntity(servant)) {
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.FLAME, servant.getX(), servant.getY(1.0D),
                        servant.getZ(), 16, 0.3D, 0.5D, 0.3D, 0.02D);
                level.sendParticles(ParticleTypes.LARGE_SMOKE, servant.getX(), servant.getY(0.8D),
                        servant.getZ(), 8, 0.3D, 0.4D, 0.3D, 0.01D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.7F,
                        0.9F + servant.getRandom().nextFloat() * 0.2F);
            } else {
                servant.discard();
            }
        }
        SummonDown(caster);
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (rightStaff(wandStack)) {
            return SupremeFociConfig.randomSummonCount(caster,
                    DroughtBlazeFocusConfig.STAFF_SUMMON_COUNT_MIN,
                    DroughtBlazeFocusConfig.STAFF_SUMMON_COUNT_MAX);
        }
        return SupremeFociConfig.randomSummonCount(caster,
                DroughtBlazeFocusConfig.SUMMON_COUNT_MIN,
                DroughtBlazeFocusConfig.SUMMON_COUNT_MAX);
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

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 2) : candidate;
    }
}
