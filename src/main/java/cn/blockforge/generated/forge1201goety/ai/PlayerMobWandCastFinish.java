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

final class PlayerMobWandCastFinish {
    static void completeCast(PlayerMobWandSpellGoal goal) {
        goal.completeCast(true);
    }

    static void completeCast(PlayerMobWandSpellGoal goal, boolean fireResult) {
        if (!(goal.mob.level() instanceof ServerLevel level)) {
            goal.resetCast();
            return;
        }
        ISpell spell = goal.castingSpell;
        boolean healInterludeCast = goal.mob.isHealInterlude() && spell != null
                && "SoulHealSpell".equals(spell.getClass().getSimpleName());
        boolean weakeningInterludeCast = goal.mob.isWeakeningInterlude() && spell != null
                && "WeakeningSpell".equals(spell.getClass().getSimpleName());
        Set<String> before = goal.beneficialEffectIds();
        LivingEntity master = goal.master();
        LivingEntity context = master != null ? master : goal.mob;
        boolean paid = true;
        if (!fireResult) {
            spell.stopSpell(level, goal.mob, goal.castingWand, goal.castingFocus, goal.castTicks, goal.castingStats);
        } else {
            int cost = Math.max(0, spell.soulCost(context, goal.castingWand));
            if (master instanceof Player player && !player.isCreative()) {
                paid = SEHelper.getSoulsAmount(player, cost);
                if (paid) {
                    SEHelper.decreaseSouls(player, cost);
                }
            }
            if (paid) {
                boolean barricade = "BarricadeSpell".equals(spell.getClass().getSimpleName());
                boolean sneakBranch = !barricade && goal.castingSneakBranch;
                boolean prevShift = (barricade || sneakBranch) && goal.mob.isShiftKeyDown();
                if (barricade) {
                    goal.mob.setShiftKeyDown(goal.mob.getRandom().nextBoolean());
                } else if (sneakBranch) {
                    goal.mob.setShiftKeyDown(true);
                }
                try {
                    if (spell instanceof ITouchSpell touchSpell && goal.castingTouchTarget != null
                            && goal.castingTouchTarget.isAlive()) {
                        touchSpell.touchResult(level, goal.mob, goal.castingTouchTarget, goal.castingWand, goal.castingStats);
                    } else {
                        spell.SpellResult(level, goal.mob, goal.castingWand, goal.castingStats);
                    }
                } finally {
                    if (barricade || sneakBranch) {
                        goal.mob.setShiftKeyDown(prevShift);
                    }
                }
                if (goal.castingInvasionCleanup && goal.surrenderedCleanupWanted()) {
                    goal.executeOwnInvasionServants();
                }
            } else {
                level.playSound(null, goal.mob.getX(), goal.mob.getY(0.5D), goal.mob.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.7F, 0.6F);
            }
        }
        goal.probeCastResult(spell, goal.castingWand, fireResult, paid, master, context);
        if (paid) {
            int cooldown = Math.max(0, spell.spellCooldown(context));
            if (cooldown > 0 && !goal.castingFocusId.isEmpty()) {
                goal.mob.addWandFocusCooldown(goal.castingFocusId, cooldown);
            }
            if (spell instanceof SummonSpell summon) {
                int summonDown = Math.max(20, summon.SummonDownDuration());
                goal.mob.setWandSummonCooldown(summonDown);
                if (!(spell instanceof SupremeSummonSpell)) {
                    GoetySummonFx.playAt(level, goal.mob.getX(), goal.mob.getY(), goal.mob.getZ());
                }
            }
            Set<String> gained = new HashSet<>(goal.beneficialEffectIds());
            gained.removeAll(before);
            if (!gained.isEmpty() && !goal.castingFocusId.isEmpty()) {
                goal.mob.rememberWandBuffEffects(goal.castingFocusId, gained);
            }
        }
        boolean wasDisplacement = goal.castingDisplacement;
        boolean wasReposition = goal.castingReposition;
        boolean partingEscape = goal.castingPartingShot && goal.partingShotThenEscape;
        if (wasDisplacement) {
            if (wasReposition) {
                goal.castsSinceReposition = 0;
                goal.nextRepositionCasts = goal.REPOSITION_MIN_CASTS + goal.mob.getRandom()
                        .nextInt(goal.REPOSITION_MAX_CASTS - goal.REPOSITION_MIN_CASTS + 1);
            }
        } else if (paid) {
            goal.castsSinceReposition++;
        }
        if (!wasDisplacement && paid && goal.castingRangedDamage) {
            goal.rangedDamageCasts++;
            if (goal.rangedDamageCasts >= goal.nextCloseRangeCasts) {
                goal.rangedDamageCasts = 0;
                goal.nextCloseRangeCasts = goal.CLOSE_RANGE_MIN_CASTS + goal.mob.getRandom()
                        .nextInt(goal.CLOSE_RANGE_MAX_CASTS - goal.CLOSE_RANGE_MIN_CASTS + 1);
                if (goal.mob.getRandom().nextFloat() < goal.CLOSE_RANGE_ASSAULT_CHANCE) {
                    goal.closeRangeAssault = true;
                    goal.closeRangeAssaultTicks = 0;
                }
            }
        }
        boolean wasCloseRangeAssault = goal.castingCloseRangeAssault;
        goal.resetCast();
        if (wasCloseRangeAssault) {
            goal.clearCloseRangeAssault();
        }
        if (healInterludeCast) {
            goal.mob.finishHealInterlude();
        }
        if (weakeningInterludeCast) {
            goal.mob.finishWeakeningInterlude();
        }
        if (goal.maneuverSettleOnComplete > 0) {
            goal.maneuverSettleTicks = goal.maneuverSettleOnComplete;
            goal.maneuverSettleOnComplete = 0;
        }
        goal.gapTicks = goal.MIN_GAP_TICKS + goal.mob.getRandom().nextInt(goal.MAX_GAP_TICKS - goal.MIN_GAP_TICKS + 1);
        goal.state = State.GAP;
        if (partingEscape) {
            goal.burstAwayFrom(goal.mob.nearestThreat(goal.mob.getTarget(), goal.THREAT_SCAN_RADIUS));
        }
    }

    static void interruptCast(PlayerMobWandSpellGoal goal) {
        goal.probeCastInterrupt();
        if (goal.state == State.WINDUP && goal.castingSpell != null && goal.castStarted
                && goal.mob.level() instanceof ServerLevel level) {
            goal.castingSpell.stopSpell(level, goal.mob, goal.castingWand, goal.castingFocus, goal.castTicks, goal.castingStats);
        }
        if (goal.castingCloseRangeAssault) {
            goal.clearCloseRangeAssault();
        }
        goal.maneuverSettleOnComplete = 0;
        goal.resetCast();
    }

    static void resetCast(PlayerMobWandSpellGoal goal) {
        goal.castingSpell = null;
        goal.castingStats = null;
        goal.castingFocus = ItemStack.EMPTY;
        goal.castingWand = ItemStack.EMPTY;
        goal.castingFocusId = "";
        goal.castingTouchTarget = null;
        goal.castingAimEntity = null;
        goal.castingAimYawOffset = 0.0D;
        goal.castingDisplacement = false;
        goal.castingReposition = false;
        goal.castingPartingShot = false;
        goal.castingRangedDamage = false;
        goal.castingCloseRangeAssault = false;
        goal.castingSneakBranch = false;
        goal.castingInvasionCleanup = false;
        goal.castingLoopSound = null;
        goal.castingLoopSoundTicks = 0;
        goal.partingShotThenEscape = false;
        goal.chargeDistanceCast = false;
        goal.longWindupCast = false;
        goal.castingCharging = null;
        goal.castingCastUp = 0;
        goal.channelTicks = 0;
        goal.channelShots = 0;
        goal.channelShotTimer = 0;
        goal.channelSoulCounter = 0;
        goal.mob.setWandLongWindup(false);
        goal.mob.setWandPartingShot(false);
        goal.mob.setWandSneakCast(false);
        goal.castTicks = 0;
        goal.castTotal = 0;
        goal.castStarted = false;
        goal.mob.setCastingHand(-1);
    }

}
