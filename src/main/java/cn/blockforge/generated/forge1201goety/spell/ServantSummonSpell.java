package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.NewFocusSummonConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.spell.ServantSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class ServantSummonSpell extends SupremeSummonSpell {

    public enum SoundProfile {
        BLAZING,
        MINISTROUS;

        public SoundEvent castingSound(LivingEntity caster) {
            if (this == BLAZING && CuriosFinder.hasUnholySet(caster)) {
                return ModSounds.APOSTLE_PREPARE_SUMMON.get();
            }
            return ModSounds.PREPARE_SUMMON.get();
        }

        public SoundEvent finaleSound() {
            return this == BLAZING
                    ? ModSounds.SUMMON_SPELL_FIERY.get()
                    : ModSounds.SUMMON_SPELL.get();
        }
    }

    public enum StaffCountMode {
        RANGE,
        CHANCE_DOUBLE
    }

    private static final float CHANCE_DOUBLE_PROBABILITY = 0.25F;

    private final NewFocusSummonConfig.Section settings;
    private final SpellType spellType;
    private final SoundProfile soundProfile;
    private final Function<ServerLevel, AbstractJerotesServant> factory;
    private final Predicate<LivingEntity> predicate;
    private final StaffCountMode staffCountMode;

    public ServantSummonSpell(NewFocusSummonConfig.Section settings, SpellType spellType,
            SoundProfile soundProfile,
            Function<ServerLevel, AbstractJerotesServant> factory, Predicate<LivingEntity> predicate) {
        this(settings, spellType, soundProfile, factory, predicate, StaffCountMode.RANGE);
    }

    public ServantSummonSpell(NewFocusSummonConfig.Section settings, SpellType spellType,
            SoundProfile soundProfile,
            Function<ServerLevel, AbstractJerotesServant> factory, Predicate<LivingEntity> predicate,
            StaffCountMode staffCountMode) {
        this.settings = settings;
        this.spellType = spellType;
        this.soundProfile = soundProfile;
        this.factory = factory;
        this.predicate = predicate;
        this.staffCountMode = staffCountMode;
    }

    @Override
    public SoundEvent CastingSound(LivingEntity caster) {
        return soundProfile.castingSound(caster);
    }

    @Override
    public SoundEvent loopSound(LivingEntity caster) {
        return null;
    }

    @Override
    public int defaultSoulCost() {
        return settings.soulCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return settings.castDuration.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return settings.cooldown.get();
    }

    @Override
    public int SummonDownDuration() {
        return settings.summonDownTicks.get();
    }

    @Override
    public SpellType getSpellType() {
        return spellType;
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
        return predicate;
    }

    @Override
    public int summonLimit() {
        return settings.summonLimit.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.SOUL, caster.getX(), caster.getY(0.5D), caster.getZ(),
                16, 0.45D, 0.6D, 0.45D, 0.02D);
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));
        int count = summonCount(caster, wandStack);

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            AbstractJerotesServant servant = factory.apply(level);
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
            servant.setYBodyRot(caster.yBodyRot);
            servant.setYHeadRot(caster.getYRot());
            servant.setYRot(caster.getYRot());
            servant.setXRot(0.0F);
            servant.setLimitedLife(lifespanFor(durationLevel));
            servant.setPersistenceRequired();
            buffSummon(caster, servant, potencyLevel);
            SummonSap(caster, servant);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.CLOUD, servant.getX(), servant.getY(1.0D),
                        servant.getZ(), 14, 0.45D, 0.75D, 0.45D, 0.06D);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
            playSound(level, caster, soundProfile.finaleSound());
        }
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (rightStaff(wandStack)) {
            if (staffCountMode == StaffCountMode.CHANCE_DOUBLE) {
                return caster.getRandom().nextFloat() < CHANCE_DOUBLE_PROBABILITY ? 2 : 1;
            }
            return SupremeFociConfig.randomSummonCount(caster,
                    settings.staffSummonCountMin, settings.staffSummonCountMax);
        }
        return 1;
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = settings.lifespanTicks.get().longValue()
                + Math.max(0L, (long) durationLevel) * settings.durationBonusTicks.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
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

    private void setInitialTarget(AbstractJerotesServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 3) : candidate;
    }
}
