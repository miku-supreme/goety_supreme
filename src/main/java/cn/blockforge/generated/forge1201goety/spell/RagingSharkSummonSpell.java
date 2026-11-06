package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.List;
import java.util.function.Predicate;
import cn.blockforge.generated.goetysupreme.config.RagingSharkFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.RagingSharkSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public class RagingSharkSummonSpell extends SupremeSummonSpell {

    public RagingSharkSummonSpell() {
    }

    @Override
    public int defaultSoulCost() {
        return RagingSharkFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return RagingSharkFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return RagingSharkFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return RagingSharkFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return ModSounds.WILD_PREPARE_SPELL.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.ABYSS;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        return List.of(ModEnchantments.POTENCY.get(), ModEnchantments.DURATION.get());
    }

    @Override
    public Predicate<LivingEntity> summonPredicate() {
        return entity -> entity instanceof DoomFiveHeadShark;
    }

    @Override
    public int summonLimit() {
        return RagingSharkFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int potency = Math.max(0, effectiveStats.getPotency());
        int duration = Math.max(1, effectiveStats.getDuration());
        if (WandUtil.enchantedFocus(caster)) {
            potency += Math.max(0, WandUtil.getPotencyLevel(caster));
            duration += Math.max(0, WandUtil.getLevels(ModEnchantments.DURATION.get(), caster)) + 1;
        }

        int count = 1;
        if (this.typeStaff(wandStack, SpellType.ABYSS) && caster.getRandom().nextBoolean()) {
            count = 2;
        }

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            DoomFiveHeadShark shark = ModEntityTypes.DOOM_FIVE_HEAD_SHARK.get().create(level);
            if (shark == null) {
                continue;
            }
            BlockPos spawnPos = caster.isInWater()
                    ? BlockFinder.SummonWaterRadius(caster.blockPosition(), caster, level, 3 + i, 8)
                    : BlockFinder.SummonRadius(caster.blockPosition(), shark, level, 3 + i, 8);
            if (spawnPos == null) {
                spawnPos = caster.blockPosition();
            }
            shark.setTrueOwner(caster);
            shark.moveTo(spawnPos, caster.getYRot(), 0.0F);
            if (!caster.isInWater()) {
                MobUtil.moveDownToGround(shark);
            }
            shark.setLimitedLife(MobUtil.getSummonLifespan(level) * duration);
            shark.setPersistenceRequired();
            shark.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            shark.setTrueOwner(caster);
            shark.setYBodyRot(caster.yBodyRot);
            shark.setYHeadRot(caster.getYRot());
            this.buffSummon(caster, shark, potency);
            this.setTarget(caster, shark);
            if (level.addFreshEntity(shark)) {
                ++spawned;
                this.summonAdvancement(caster, shark);
                GoetySummonFx.play(level, shark);
                level.sendParticles(ParticleTypes.SOUL, shark.getX(), shark.getY(0.6D), shark.getZ(),
                        16, 0.45D, 0.45D, 0.45D, 0.02D);
            } else {
                shark.discard();
            }
        }
        if (spawned > 0) {
            this.SummonDown(caster);
            this.playSound(level, caster, ModSounds.SUMMON_SPELL.get());
        }
    }
}
