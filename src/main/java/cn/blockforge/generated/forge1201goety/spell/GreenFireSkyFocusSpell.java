package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.spell.GreenFireSkyFocusSpell;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
public final class GreenFireSkyFocusSpell extends Spell {

    private static final Set<UUID> ACTIVE = new HashSet<>();

    public static final int CAST_TICKS = 400;
    public static final int FIRST_STRIKE_DELAY = 20;
    public static final int NAMELESS_STRIKE_INTERVAL = 10;
    public static final int OTHER_STRIKE_INTERVAL = 20;

    @Override
    public int defaultSoulCost() { return 64; }

    @Override
    public int defaultCastDuration() { return CAST_TICKS; }

    @Override
    public int defaultSpellCooldown() { return 300; }

    @Override
    public SpellType getSpellType() { return SpellType.NECROMANCY; }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> list = new ArrayList<>();
        list.add(ModEnchantments.POTENCY.get());
        list.add(ModEnchantments.RADIUS.get());
        list.add(ModEnchantments.DURATION.get());
        return list;
    }

    @Override
    public void useSpell(ServerLevel level, LivingEntity caster, ItemStack staff, int castTime, SpellStat stats) {
        boolean nameless = staff != null && staff.is(
                com.Polarice3.Goety.common.items.ModItems.NAMELESS_STAFF.get());
        int interval = nameless ? NAMELESS_STRIKE_INTERVAL : OTHER_STRIKE_INTERVAL;
        com.mojang.logging.LogUtils.getLogger().info(
                "[skyfocus] castTick={} nameless={} interval={}", castTime, nameless, interval);
        if (castTime == 0) {
            ACTIVE.add(caster.getUUID());
            level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                    com.Polarice3.Goety.init.ModSounds.PREPARE_SUMMON.get(),
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (castTime >= FIRST_STRIKE_DELAY && (castTime - FIRST_STRIKE_DELAY) % 5 == 0) {
            AbstractGreenSmith.skyFocusShield(level, caster);
        }
        if (castTime >= FIRST_STRIKE_DELAY && (castTime - FIRST_STRIKE_DELAY) % interval == 0) {
            Vec3 strike = AbstractGreenSmith.skyFocusStrike(level, caster, 10.0D);
            int strikes = (castTime - FIRST_STRIKE_DELAY) / interval;
            if (strikes > 0 && strikes % 2 == 0) {
                AbstractGreenSmith.skyFocusSummonSkeleton(level, caster,
                        strike.x, strike.y, strike.z);
            }
        }
    }

    @Override
    public void stopSpell(ServerLevel level, LivingEntity caster, ItemStack staff, ItemStack focus,
            int castTime, SpellStat stats) {
        ACTIVE.remove(caster.getUUID());
        com.mojang.logging.LogUtils.getLogger().info("[skyfocus] stop castTick={} removed={}", castTime,
                !ACTIVE.contains(caster.getUUID()));
    }

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void onHurt(net.minecraftforge.event.entity.living.LivingHurtEvent event) {
        if (!ACTIVE.contains(event.getEntity().getUUID())) {
            return;
        }
        if (event.getSource().is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        event.setAmount(event.getAmount() * 0.2F);
        com.mojang.logging.LogUtils.getLogger().info(
                "[skyfocus] 80% reduction amount={} source={}",
                event.getAmount(), event.getSource().getMsgId());
    }
}
