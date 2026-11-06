package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.MalignasaurBombFocusConfig;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.spell.MalignasaurBombFocusSpell;
import cn.blockforge.generated.goetysupreme.util.MalignasaurFocusAim;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;

public final class MalignasaurBombFocusSpell extends Spell {

    @Override
    public int defaultSoulCost() {
        return MalignasaurBombFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return MalignasaurBombFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return MalignasaurBombFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.ABYSS;
    }

    @Override
    public SoundEvent CastingSound() {
        return ModSounds.ABYSS_PREPARE_SPELL.get();
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.RADIUS.get());
        enchantments.add(ModEnchantments.RANGE.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        int spellLevel = MalignasaurFocusAim.spellLevel(this, stats, wandStack,
                MalignasaurBombFocusConfig.BASE_LEVEL.get(),
                MalignasaurBombFocusConfig.STAFF_LEVEL_BONUS.get());
        JerotesFocusBurst.showCastDisplay(level, caster, "malignasaur_bomb_display",
                ParticleTypes.SOUL_FIRE_FLAME, spellLevel);
        LivingEntity target = caster;
        MalignasaurSpells.bomb(level, caster, target, spellLevel);
    }
}
