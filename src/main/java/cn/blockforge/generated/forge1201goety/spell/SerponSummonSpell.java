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
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.SerponFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;
import cn.blockforge.generated.goetysupreme.entity.SerponIntruder;
import cn.blockforge.generated.goetysupreme.entity.SerponPioneer;
import cn.blockforge.generated.goetysupreme.entity.SerponRioter;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class SerponSummonSpell extends SupremeSummonSpell {

    public enum Kind {
        HEAD,
        CROWN,
        FLAME
    }

    public static final String JEROTES_VILLAGE_MOD_ID = JerotesVillageCompat.JEROTES_VILLAGE_MOD_ID;

    private final Kind kind;

    public SerponSummonSpell(Kind kind) {
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    private SerponFocusConfig.Section section() {
        return switch (kind) {
            case HEAD -> SerponFocusConfig.HEAD;
            case CROWN -> SerponFocusConfig.CROWN;
            case FLAME -> SerponFocusConfig.FLAME;
        };
    }

    @Override
    public int defaultSoulCost() {
        return section().soulCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return section().castDuration.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return section().cooldown.get();
    }

    @Override
    public int SummonDownDuration() {
        return section().summonDownTicks.get();
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
        return entity -> switch (kind) {
            case HEAD -> entity instanceof SerponPioneer || entity instanceof SerponColonialSoldier;
            case CROWN -> entity instanceof SerponRioter || entity instanceof SerponIntruder;
            case FLAME -> entity instanceof SerponFlameWizard;
        };
    }

    @Override
    public int summonLimit() {
        return section().summonLimit.get();
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

        boolean netherStaff = rightStaff(wandStack);
        List<AbstractSerpon> plan = summonPlan(caster, netherStaff);

        int spawned = 0;
        for (int i = 0; i < plan.size(); i++) {
            AbstractSerpon servant = plan.get(i);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i, plan.size());
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
            if (servant instanceof SerponColonialSoldier soldier
                    && RingServantGear.casterWearsNewRing(caster)) {
                soldier.equipGuaranteedShield();
            }
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

    private List<AbstractSerpon> summonPlan(LivingEntity caster, boolean netherStaff) {
        ServerLevel level = (ServerLevel) caster.level();
        List<AbstractSerpon> out = new ArrayList<>();
        boolean pickFirst = caster.getRandom().nextBoolean();
        switch (kind) {
            case HEAD -> {
                if (netherStaff) {
                    addPlan(out, level, ModEntityTypes.SERPON_PIONEER.get(), 2);
                    addPlan(out, level, ModEntityTypes.SERPON_COLONIAL_SOLDIER.get(), 2);
                } else {
                    addPlan(out, level, pickFirst
                            ? ModEntityTypes.SERPON_PIONEER.get()
                            : ModEntityTypes.SERPON_COLONIAL_SOLDIER.get(), 1);
                }
            }
            case CROWN -> {
                if (netherStaff) {
                    addPlan(out, level, ModEntityTypes.SERPON_RIOTER.get(), 1);
                    addPlan(out, level, ModEntityTypes.SERPON_INTRUDER.get(), 1);
                } else {
                    addPlan(out, level, pickFirst
                            ? ModEntityTypes.SERPON_RIOTER.get()
                            : ModEntityTypes.SERPON_INTRUDER.get(), 1);
                }
            }
            case FLAME -> addPlan(out, level, ModEntityTypes.SERPON_FLAME_WIZARD.get(),
                    netherStaff ? 2 : 1);
        }
        return out;
    }

    private void addPlan(List<AbstractSerpon> out, ServerLevel level,
            net.minecraft.world.entity.EntityType<? extends AbstractSerpon> type, int count) {
        for (int i = 0; i < count; i++) {
            AbstractSerpon servant = type.create(level);
            if (servant != null) {
                out.add(servant);
            }
        }
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = section().lifespanTicks.get().longValue()
                + Math.max(0L, (long) durationLevel) * section().durationBonusTicks.get();
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

    private void setInitialTarget(AbstractSerpon servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant,
            int index, int count) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 2) : candidate;
    }
}
