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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.AnimatedIronFocusConfig;
import cn.blockforge.generated.goetysupreme.config.ServantCapConfig;
import cn.blockforge.generated.goetysupreme.spell.AnimatedIronSummonSpell;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class AnimatedIronSummonSpell extends SupremeSummonSpell {

    @Override
    public int defaultSoulCost() {
        return AnimatedIronFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return AnimatedIronFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return AnimatedIronFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return AnimatedIronFocusConfig.SUMMON_DOWN_TICKS.get();
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
        return entity -> entity instanceof SecondRounderAnimatedArmorServant;
    }

    @Override
    public int summonLimit() {
        return ServantCapConfig.JEROTES_SECOND_ROUNDER_ANIMATED_ARMOR.get();
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = isGeoStaff(wandStack) ? 2 : 1;
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            SecondRounderAnimatedArmorServant servant =
                    ModEntityTypes.SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT.get().create(level);
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
            if (RingServantGear.casterWearsNewRing(caster)) {
                servant.equipGuaranteedShield();
            }
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
                        servant.getZ(), 16, 0.45D, 0.75D, 0.45D, 0.06D);
                level.sendParticles(ParticleTypes.END_ROD, servant.getX(), servant.getY(0.9D),
                        servant.getZ(), 10, 0.4D, 0.6D, 0.4D, 0.02D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.IRON_GOLEM_REPAIR, SoundSource.PLAYERS, 0.8F,
                        0.9F + servant.getRandom().nextFloat() * 0.2F);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
        }
    }

    private boolean isGeoStaff(ItemStack wandStack) {
        return wandStack != null && !wandStack.isEmpty() && wandStack.is(ModItems.GEO_STAFF.get());
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = AnimatedIronFocusConfig.LIFESPAN_TICKS.get().longValue()
                + Math.max(0L, (long) durationLevel) * AnimatedIronFocusConfig.DURATION_BONUS_TICKS.get();
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

    private void setInitialTarget(SecondRounderAnimatedArmorServant servant, LivingEntity caster) {
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
