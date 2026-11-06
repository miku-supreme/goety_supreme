package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.SummonSpell;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public abstract class SupremeSummonSpell extends SummonSpell {

    public abstract void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats);

    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
    }

    public void castReleaseSound(ServerLevel level, LivingEntity caster) {
    }

    @Override
    public void commonResultHit(ServerLevel level, LivingEntity caster) {
        gatherHitImpl(level, caster);
        summonGatherParticles(level, caster);
    }

    public void gatherHitImpl(ServerLevel level, LivingEntity caster) {
        super.commonResultHit(level, caster);
    }

    @Override
    public final void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        castReleaseSound(level, caster);
        commonResult(level, caster);
        if (!isShifting(caster)) {
            summon(level, caster, wandStack, stats);
        }
    }
}
