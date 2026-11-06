package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.IBreathingSpell;
import com.Polarice3.Goety.api.magic.IChargingSpell;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.api.magic.ITouchSpell;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.projectiles.AbstractCyclone;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.SummonSpell;
import com.Polarice3.Goety.utils.SEHelper;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting.FocusEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import cn.blockforge.generated.goetysupreme.debug.WandFocusProbe;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocusSpell;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobStrafeMoveControl;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;
import cn.blockforge.generated.goetysupreme.util.ServantOwnerGear;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;
import cn.blockforge.generated.goetysupreme.util.TargetImmunity;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobFocusFireEscapeGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal.Layout;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal.Maneuver;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal.State;

final class PlayerMobWandFocusPicker {
    static FocusEntry pickFocus(PlayerMobWandSpellGoal goal, LivingEntity target, Layout layout) {
        List<FocusEntry> usable = goal.usableFocuses(layout, target);
        if (usable.isEmpty()) {
            return null;
        }
        if (goal.mob.isHealInterlude()) {
            return goal.pickRandomMatching(usable, FocusEntry::isHeal);
        }
        if (goal.mob.isWeakeningInterlude()) {
            return goal.pickRandomMatching(usable, FocusEntry::isWeakening);
        }
        FocusEntry selfHeal = goal.pickRandomMatching(usable,
                entry -> entry.isHeal() && PlayerMobWandCasting.selfHealNeeded(goal.mob));
        if (selfHeal != null) {
            return selfHeal;
        }
        FocusEntry allyHeal = goal.pickRandomMatching(usable,
                entry -> entry.isHeal() && PlayerMobWandCasting.allyHealNeeded(goal.mob, entry));
        if (allyHeal != null) {
            return allyHeal;
        }
        FocusEntry convertUndead = goal.pickRandomMatching(usable,
                entry -> entry.isNecroShield() && goal.necroShieldWantsConvert());
        if (convertUndead != null) {
            return convertUndead;
        }
        FocusEntry surrenderedCleanup = goal.pickRandomMatching(usable,
                entry -> goal.isInvasionSummonFocus(entry) && goal.surrenderedCleanupWanted());
        if (surrenderedCleanup != null) {
            return surrenderedCleanup;
        }
        FocusEntry summon = goal.pickRandomMatching(usable, FocusEntry::isSummon);
        if (summon != null) {
            return summon;
        }
        FocusEntry weaken = goal.pickRandomMatching(usable, FocusEntry::isWeakening);
        if (weaken != null) {
            return weaken;
        }
        FocusEntry buff = goal.pickRandomMatching(usable,
                entry -> !entry.isChill() && goal.isBuffFocus(entry));
        if (buff != null) {
            return buff;
        }
        FocusEntry necroShield = goal.pickRandomMatching(usable, FocusEntry::isNecroShield);
        if (necroShield != null) {
            return necroShield;
        }
        FocusEntry chill = goal.pickRandomMatching(usable, FocusEntry::isChill);
        if (chill != null) {
            return chill;
        }
        if (goal.closeRangeAssault) {
            FocusEntry sally = goal.pickRandomMatching(usable, goal::isCloseRangeDamageFocus);
            if (sally != null) {
                return sally;
            }
        }
        int enemies = goal.countEnemies(target);
        boolean wantAoe = enemies >= 3;
        return goal.pickDamageFocus(usable, wantAoe);
    }

    static FocusEntry pickRandomMatching(PlayerMobWandSpellGoal goal, List<FocusEntry> usable, java.util.function.Predicate<FocusEntry> predicate) {
        List<FocusEntry> candidates = new ArrayList<>();
        for (FocusEntry entry : usable) {
            if (predicate.test(entry)) {
                candidates.add(entry);
            }
        }
        return goal.pickFrom(candidates);
    }

    static FocusEntry pickDamageFocus(PlayerMobWandSpellGoal goal, List<FocusEntry> usable, boolean wantAoe) {
        List<FocusEntry> candidates = new ArrayList<>();
        for (FocusEntry entry : usable) {
            if (goal.isDamageFocus(entry)) {
                candidates.add(entry);
            }
        }
        if (candidates.isEmpty()) {
            return null;
        }
        candidates.sort(Comparator.comparingInt(goal::damageUseOrder));
        int breadth = Math.min(goal.DAMAGE_ROTATION_BREADTH, candidates.size());
        int cutoff = goal.damageUseOrder(candidates.get(breadth - 1));
        List<FocusEntry> tier = new ArrayList<>();
        for (FocusEntry entry : candidates) {
            if (goal.damageUseOrder(entry) <= cutoff) {
                tier.add(entry);
            }
        }
        List<FocusEntry> matched = new ArrayList<>();
        for (FocusEntry entry : tier) {
            if (entry.isAoe() == wantAoe) {
                matched.add(entry);
            }
        }
        return goal.pickFrom(matched.isEmpty() ? tier : matched);
    }

    static int damageUseOrder(PlayerMobWandSpellGoal goal, FocusEntry entry) {
        return goal.damageFocusUseOrder.getOrDefault(entry.itemId(), 0);
    }

    static FocusEntry pickFrom(PlayerMobWandSpellGoal goal, List<FocusEntry> candidates) {
        if (candidates.isEmpty()) {
            return null;
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        List<FocusEntry> withoutLast = new ArrayList<>(candidates.size());
        for (FocusEntry entry : candidates) {
            if (!entry.itemId().equals(goal.lastFocusId)) {
                withoutLast.add(entry);
            }
        }
        List<FocusEntry> pool = withoutLast.isEmpty() ? candidates : withoutLast;
        return pool.get(goal.mob.getRandom().nextInt(pool.size()));
    }

    static FocusEntry pickSupportFocus(PlayerMobWandSpellGoal goal, Layout layout) {
        List<FocusEntry> usable = goal.usableFocuses(layout, null);
        FocusEntry heal = goal.pickRandomMatching(usable,
                entry -> entry.isHeal() && PlayerMobWandCasting.healNeeded(goal.mob, entry));
        if (heal != null) {
            return heal;
        }
        FocusEntry charge = goal.pickRandomMatching(usable,
                entry -> entry.isCharge() && goal.chargeTarget(entry) != null);
        if (charge != null) {
            return charge;
        }
        return goal.pickRandomMatching(usable, FocusEntry::isChill);
    }

    static List<FocusEntry> usableFocuses(PlayerMobWandSpellGoal goal, Layout layout, LivingEntity target) {
        List<FocusEntry> usable = new ArrayList<>();
        for (FocusEntry entry : PlayerMobWandCasting.catalog(goal.mob, goal.wandOf(layout))) {
            if (goal.isUsable(entry, target)) {
                usable.add(entry);
            }
        }
        return usable;
    }

    static boolean isUsable(PlayerMobWandSpellGoal goal, FocusEntry entry, LivingEntity target) {
        if (entry.isDisplacement() || entry.isUtility() || goal.mob.isWandFocusOnCooldown(entry.itemId())) {
            return false;
        }
        if (entry.isNecroShield()) {
            return goal.necroShieldUsable(target);
        }
        if (target != null && entry.isSelfCenteredAoe() && !goal.inFocusRadius(entry, target)) {
            return false;
        }
        boolean sneakRingClose = target != null && entry.isSneakSelfCentered()
                && goal.sneakRange(entry) > 0.0D && goal.mob.distanceTo(target) <= goal.sneakRange(entry);
        if (target != null && entry.isConeSpell() && !sneakRingClose && !goal.inConeRange(entry, target)) {
            return false;
        }
        if (target != null && entry.isRangedSpell() && !goal.inFocusRange(entry, target)) {
            return false;
        }
        if (entry.isSummon()) {
            if (goal.isInvasionSummonFocus(entry) && goal.surrenderedCleanupWanted()) {
                return true;
            }
            if (goal.mob.getWandSummonCooldown() > 0) {
                return false;
            }
            return goal.summonBelowLimit(entry);
        }
        if (entry.isHeal()) {
            return goal.healCondition(entry);
        }
        if (entry.isChill()) {
            return goal.chillCondition(entry, goal.wandOf(goal.resolveLayout()));
        }
        if (entry.isCharge()) {
            return target == null && goal.chargeTarget(entry) != null;
        }
        if (goal.isBuffFocus(entry)) {
            return goal.buffNeeded(entry);
        }
        if (entry.isWeakening()) {
            return goal.weakenNeeded(target) && goal.inFocusRadius(entry, target);
        }
        return true;
    }

    static boolean necroShieldUsable(PlayerMobWandSpellGoal goal, LivingEntity target) {
        ItemStack wand = goal.findCastingWand(goal.mob);
        if (PlayerMobWandCasting.isGoetyItem(wand, "nameless_staff")
                && goal.wildUndeadNearby(goal.NECRO_SHIELD_UNDEAD_RADIUS)) {
            return true;
        }
        return target != null && target.isAlive() && goal.mob.isValidCombatTargetForGoal(target);
    }

    static boolean necroShieldWantsConvert(PlayerMobWandSpellGoal goal) {
        ItemStack wand = goal.findCastingWand(goal.mob);
        return PlayerMobWandCasting.isGoetyItem(wand, "nameless_staff")
                && goal.wildUndeadNearby(goal.NECRO_SHIELD_UNDEAD_RADIUS);
    }

    static boolean wildUndeadNearby(PlayerMobWandSpellGoal goal, double radius) {
        for (LivingEntity entity : goal.mob.level().getEntitiesOfClass(LivingEntity.class,
                goal.mob.getBoundingBox().inflate(radius))) {
            if (entity == goal.mob || !entity.isAlive() || entity.getMobType() != MobType.UNDEAD) {
                continue;
            }
            if (goal.mob.isAlliedTo(entity) || entity.isAlliedTo(goal.mob)) {
                continue;
            }
            if (goal.mob.distanceTo(entity) <= radius) {
                return true;
            }
        }
        return false;
    }

    static double sneakRange(FocusEntry entry) {
        String name = entry.spell().getClass().getSimpleName();
        return switch (name) {
            case "VoidRuneSpell" -> PlayerMobWandSpellGoal.VOID_RUNE_SNEAK_RANGE;
            case "TeethSpell" -> PlayerMobWandSpellGoal.TEETH_SNEAK_RANGE;
            case "BattlefieldSpell" -> PlayerMobWandSpellGoal.BATTLEFIELD_SNEAK_RANGE;
            default -> 0.0D;
        };
    }

    static boolean shouldSneakCast(PlayerMobWandSpellGoal goal, ItemStack focus, ItemStack wand, ISpell spell) {
        if (focus == null || focus.isEmpty() || spell == null) {
            return false;
        }
        if (PlayerMobWandCasting.isItem(focus, PlayerMobWandCasting.INVASION_NS,
                PlayerMobWandCasting.INVOKER_FANGS_FOCUS)) {
            return goal.invokerFangsSneakWorthwhile()
                    && goal.mob.getRandom().nextFloat() < goal.INVOKER_FANGS_SNEAK_CHANCE;
        }
        if (goal.isInvasionCleanupCast(focus, spell)) {
            return true;
        }
        String name = spell.getClass().getSimpleName();
        if ("VoidRuneSpell".equals(name)) {
            return goal.closeRangeForSneak(goal.VOID_RUNE_SNEAK_RANGE);
        }
        if ("TeethSpell".equals(name)) {
            return spell instanceof Spell goetySpell && goetySpell.rightStaff(wand)
                    && goal.closeRangeForSneak(goal.TEETH_SNEAK_RANGE);
        }
        if ("OvergrowthSpell".equals(name)) {
            return goal.mob.getRandom().nextFloat() < goal.OVERGROWTH_SNEAK_CHANCE;
        }
        if ("BattlefieldSpell".equals(name)) {
            return goal.closeRangeForSneak(goal.BATTLEFIELD_SNEAK_RANGE);
        }
        return false;
    }

    static boolean invokerFangsSneakWorthwhile(PlayerMobWandSpellGoal goal) {
        int count = 0;
        for (LivingEntity entity : goal.mob.level().getEntitiesOfClass(LivingEntity.class,
                goal.mob.getBoundingBox().inflate(16.0D))) {
            if (entity == goal.mob || !entity.isAlive() || !goal.mob.isValidCombatTargetForGoal(entity)) {
                continue;
            }
            if (goal.mob.distanceTo(entity) <= 16.0D && ++count >= 2) {
                return true;
            }
        }
        return false;
    }

    static boolean closeRangeForSneak(PlayerMobWandSpellGoal goal, double range) {
        LivingEntity target = goal.castingAimEntity instanceof LivingEntity living ? living : goal.mob.getTarget();
        return target != null && target.isAlive() && target != goal.mob && goal.mob.distanceTo(target) <= range;
    }

}
