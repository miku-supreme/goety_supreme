package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.IronGuardFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.spell.IronGuardFocusSpell;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class IronGuardFocusSpell extends SupremeSummonSpell {

    @Override
    public int defaultSoulCost() {
        return IronGuardFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return IronGuardFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return IronGuardFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return IronGuardFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.GEOMANCY;
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
        return entity -> entity instanceof IronGolemServant;
    }

    @Override
    public int summonLimit() {
        return IronGuardFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.CLOUD, caster.getX(), caster.getY(0.5D), caster.getZ(),
                14, 0.45D, 0.6D, 0.45D, 0.05D);
    }

    private static ParticleOptions godRayParticle() {
        ParticleType<?> type = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath("goety", "god_ray"));
        return type instanceof ParticleOptions options ? options : ParticleTypes.END_ROD;
    }

    private static void sendGodRay(ServerLevel level, double x, double y, double z) {
        ParticleOptions godRay = godRayParticle();
        int rgb = ChatFormatting.GOLD.getColor() == null ? 0xFFAA00 : ChatFormatting.GOLD.getColor();
        double r = ((rgb >> 16) & 0xFF) / 255.0D;
        double g = ((rgb >> 8) & 0xFF) / 255.0D;
        double b = (rgb & 0xFF) / 255.0D;
        for (ServerPlayer player : level.getPlayers(p -> true)) {
            level.sendParticles(player, godRay, true, x, y, z, 0, r, g, b, 1.0D);
        }
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
            IronGolemServant servant = ModEntityTypes.IRON_GOLEM_SERVANT.get().create(level);
            if (servant == null) {
                continue;
            }            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            servant.setLimitedLife(lifespanFor(durationLevel));
            servant.setPersistenceRequired();
            buffSummon(caster, servant, potencyLevel);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                sendGodRay(level, servant.getX(), servant.getY(), servant.getZ());
                level.sendParticles(ParticleTypes.CLOUD, servant.getX(), servant.getY(1.2D),
                        servant.getZ(), 16, 0.5D, 0.8D, 0.5D, 0.06D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.IRON_GOLEM_REPAIR, net.minecraft.sounds.SoundSource.PLAYERS, 0.9F,
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
        if (wandStack != null && !wandStack.isEmpty() && wandStack.is(ModItems.GEO_STAFF.get())) {
            return SupremeFociConfig.randomSummonCount(caster,
                    IronGuardFocusConfig.STAFF_SUMMON_COUNT_MIN,
                    IronGuardFocusConfig.STAFF_SUMMON_COUNT_MAX);
        }
        return SupremeFociConfig.randomSummonCount(caster,
                IronGuardFocusConfig.SUMMON_COUNT_MIN,
                IronGuardFocusConfig.SUMMON_COUNT_MAX);
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
        long lifespan = IronGuardFocusConfig.LIFESPAN_TICKS.get().longValue()
                + Math.max(0L, (long) durationLevel) * IronGuardFocusConfig.DURATION_BONUS_TICKS.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private void setInitialTarget(IronGolemServant servant, LivingEntity caster) {
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
