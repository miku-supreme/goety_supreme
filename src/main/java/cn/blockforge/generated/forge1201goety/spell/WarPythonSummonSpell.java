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
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.WarPythonFocusConfig;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;
import cn.blockforge.generated.goetysupreme.spell.WarPythonSummonSpell;

public final class WarPythonSummonSpell extends SupremeSummonSpell {

    public static final String JEROTES_VILLAGE_MOD_ID = JerotesVillageCompat.JEROTES_VILLAGE_MOD_ID;

    @Override
    public int defaultSoulCost() {
        return WarPythonFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return WarPythonFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return WarPythonFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return WarPythonFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SoundEvent CastingSound(LivingEntity caster) {
        if (CuriosFinder.hasUnholySet(caster)) {
            return ModSounds.APOSTLE_PREPARE_SUMMON.get();
        }
        return ModSounds.PREPARE_SUMMON.get();
    }

    @Override
    public SoundEvent loopSound(LivingEntity caster) {
        return null;
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
        return entity -> entity instanceof WarPythonServant;
    }

    @Override
    public int summonLimit() {
        return Math.max(8, WarPythonFocusConfig.SUMMON_LIMIT.get());
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

        boolean royal = isRoyalPlan(caster, wandStack);
        int count = summonCount(caster, wandStack);
        int total = countOwned(level, caster, WarPythonServant.class);
        int royals = royal ? countOwned(level, caster, RoyalWarPythonServant.class) : 0;
        int allowed = Math.max(8, WarPythonFocusConfig.SUMMON_LIMIT.get()) - total;
        if (royal) {
            allowed = Math.min(allowed,
                    Math.max(4, WarPythonFocusConfig.ROYAL_SUMMON_LIMIT.get()) - royals);
        }
        if (allowed <= 0) {
            return;
        }
        count = Math.min(count, allowed);

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            WarPythonServant servant = createServant(level, royal);
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
            servant.setSummonPotency(potencyLevel);
            buffSummon(caster, servant, potencyLevel);
            SummonSap(caster, servant);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.FLAME, servant.getX(), servant.getY(1.1D),
                        servant.getZ(), 16, 0.4D, 0.6D, 0.4D, 0.02D);
                level.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY(0.9D),
                        servant.getZ(), 10, 0.45D, 0.7D, 0.45D, 0.02D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.6F,
                        0.9F + servant.getRandom().nextFloat() * 0.2F);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                    SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.9F,
                    0.85F + caster.getRandom().nextFloat() * 0.2F);
            SummonDown(caster);
        }
    }

    private WarPythonServant createServant(ServerLevel level, boolean royal) {
        return royal
                ? ModEntityTypes.ROYAL_WAR_PYTHON_SERVANT.get().create(level)
                : ModEntityTypes.WAR_PYTHON_SERVANT.get().create(level);
    }

    private boolean isRoyalPlan(LivingEntity caster, ItemStack wandStack) {
        boolean netherSet = CuriosFinder.hasNetherSet(caster) || CuriosFinder.hasUnholySet(caster);
        return netherSet && isNetherStaff(wandStack);
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (isRoyalPlan(caster, wandStack)) {
            return 2;
        }
        if (isNetherStaff(wandStack)) {
            return 2;
        }
        if (isWildStaff(wandStack)) {
            return 2 + caster.getRandom().nextInt(2);
        }
        return 1;
    }

    private boolean isNetherStaff(ItemStack wandStack) {
        return rightStaff(wandStack) || isGoetyItem(wandStack, "nether_staff");
    }

    private boolean isWildStaff(ItemStack wandStack) {
        return isGoetyItem(wandStack, "wild_staff");
    }

    private static boolean isGoetyItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals(path);
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = WarPythonFocusConfig.LIFESPAN_TICKS.get().longValue()
                + Math.max(0L, (long) durationLevel) * WarPythonFocusConfig.DURATION_BONUS_TICKS.get();
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

    private void setInitialTarget(WarPythonServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof net.minecraft.world.entity.Mob mob
                ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, WarPythonServant servant,
            int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 2) : candidate;
    }

    private <T extends WarPythonServant> int countOwned(ServerLevel level, LivingEntity caster,
            Class<T> type) {
        return level.getEntitiesOfClass(type, caster.getBoundingBox().inflate(256.0D),
                servant -> servant.isAlive() && servant.getTrueOwner() == caster).size();
    }
}
