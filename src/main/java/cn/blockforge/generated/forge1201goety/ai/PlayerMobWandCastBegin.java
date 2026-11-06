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

final class PlayerMobWandCastBegin {
    static boolean beginCast(PlayerMobWandSpellGoal goal, FocusEntry entry, Layout layout) {
        if (!goal.mob.hasArcaneScroll()) {
            return false;
        }
        ItemStack wand = goal.wandOf(layout);
        if (wand.isEmpty()) {
            return false;
        }
        goal.castingSpell = entry.spell();
        LivingEntity master = goal.master();
        goal.castingStats = PlayerMobWandCasting.buildStats(goal.castingSpell, master);
        goal.castingTouchTarget = null;
        if (goal.castingSpell instanceof ITouchSpell) {
            goal.castingTouchTarget = goal.resolveTouchTarget(entry);
            if (goal.castingTouchTarget == null) {
                goal.castingSpell = null;
                goal.castingStats = null;
                return false;
            }
        }
        goal.clearShield();
        PlayerMobWandCasting.equipFocus(wand, entry);
        goal.castingFocus = IWand.getFocus(wand).isEmpty() ? entry.stack().copy() : IWand.getFocus(wand).copy();
        goal.castingWand = wand;
        goal.castingFocusId = entry.itemId();
        goal.castingDisplacement = entry.isDisplacement();
        goal.chargeDistanceCast = goal.CHARGE_DISTANCE_SPELLS.contains(goal.castingSpell.getClass().getSimpleName());
        goal.castingRangedDamage = goal.isRangedDamageFocus(entry);
        goal.castingCloseRangeAssault = goal.closeRangeAssault && goal.isCloseRangeDamageFocus(entry);
        if (goal.isDamageFocus(entry)) {
            goal.damageFocusUseOrder.put(entry.itemId(), ++goal.damageFocusUseCounter);
        }
        goal.lastFocusId = goal.castingFocusId;
        goal.castingHand = goal.wandHand(layout);
        LivingEntity context = goal.castGearContext(master);
        int duration = Math.max(0, goal.castingSpell.castDuration(goal.castDurationContext(goal.castingSpell, context), wand));
        goal.castingCharging = goal.castingSpell instanceof IChargingSpell charging ? charging : null;
        if (goal.castingCharging != null) {
            int castUp = Math.max(0, goal.castingCharging.castUp(context, wand));
            goal.castingCastUp = castUp;
            goal.castTotal = castUp + goal.MAX_CHANNEL_TICKS;
            goal.longWindupCast = true;
            goal.channelTicks = 0;
            goal.channelShots = 0;
            goal.channelShotTimer = 0;
            goal.channelSoulCounter = 0;
        } else {
            goal.castingCastUp = 0;
            goal.castTotal = duration;
            goal.longWindupCast = Math.max(goal.castingSpell.defaultCastDuration(), duration)
                    > goal.LONG_WINDUP_TICKS;
        }
        goal.mob.setWandLongWindup(goal.longWindupCast);
        goal.probeCastStart(entry, wand, master, context);
        goal.castTicks = 0;
        goal.castStarted = false;
        goal.mob.getNavigation().stop();
        goal.mob.setCombatChaseSprint(false);
        Entity aim = goal.castingAimEntity != null && goal.castingAimEntity.isAlive()
                ? goal.castingAimEntity
                : (goal.castingDisplacement ? null : goal.mob.getTarget());
        goal.castingAimYawOffset = aim == null ? 0.0D
                : goal.parallelStaffAimYawOffset(entry, wand, aim);
        if (aim != null && aim.isAlive()) {
            goal.snapLookAt(goal.mob, aim, goal.castingAimYawOffset);
        }
        goal.castingSneakBranch = goal.shouldSneakCast(goal.castingFocus, goal.castingWand, goal.castingSpell);
        goal.castingInvasionCleanup = goal.isInvasionCleanupCast(goal.castingFocus, goal.castingSpell);
        goal.mob.setWandSneakCast(goal.castingSneakBranch);
        if (goal.castTotal <= 0) {
            InteractionHand hand = goal.castingHand;
            goal.completeCast();
            goal.mob.swing(hand);
        } else {
            goal.state = State.WINDUP;
            goal.mob.setCastingHand(goal.castingHand == InteractionHand.MAIN_HAND ? 0 : 1);
        }
        return true;
    }

    static LivingEntity resolveTouchTarget(PlayerMobWandSpellGoal goal, FocusEntry entry) {
        if (entry.isCharge()) {
            return goal.chargeTarget(entry);
        }
        LivingEntity target = goal.mob.getTarget();
        return target != null && target.isAlive() && goal.mob.isValidCombatTargetForGoal(target)
                ? target : null;
    }

    static LivingEntity chargeTarget(PlayerMobWandSpellGoal goal, FocusEntry entry) {
        if (!entry.isCharge() || goal.mob.level().isClientSide) {
            return null;
        }
        double reach = goal.touchReach();
        LivingEntity owner = goal.master();
        if (owner != null && goal.mob.distanceToSqr(owner) <= reach * reach && goal.isChargeCandidate(owner)) {
            return owner;
        }
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity entity : goal.nearbyEntities(goal.mob, reach)) {
            if (entity == goal.mob || !goal.isAlly(entity) || !goal.isChargeCandidate(entity)) {
                continue;
            }
            double dist = goal.mob.distanceToSqr(entity);
            if (dist < bestDist) {
                bestDist = dist;
                best = entity;
            }
        }
        if (best != null) {
            return best;
        }
        return goal.isChargeCandidate(goal.mob) ? goal.mob : null;
    }

    static boolean isChargeCandidate(PlayerMobWandSpellGoal goal, LivingEntity entity) {
        if (entity == null || !entity.isAlive() || GoetyEffects.CHARGED == null) {
            return false;
        }
        if (entity.hasEffect(GoetyEffects.CHARGED.get())) {
            return false;
        }
        return TargetImmunity.lands(entity, GoetyEffects.CHARGED.get());
    }

    static double touchReach(PlayerMobWandSpellGoal goal) {
        AttributeInstance reach = goal.mob.getAttribute(ForgeMod.ENTITY_REACH.get());
        double value = reach == null ? 3.0D : reach.getValue();
        return Math.max(1.0D, value);
    }

    static boolean breakWindupForRetreat(PlayerMobWandSpellGoal goal) {
        if (goal.castingDisplacement) {
            return false;
        }
        if (goal.castingPartingShot) {
            return false;
        }
        if (goal.chargeDistanceCast) {
            return false;
        }
        if (goal.longWindupCast) {
            boolean chargingGraceOver = goal.castingCharging != null && goal.castTicks >= goal.LONG_WINDUP_TICKS;
            if (!chargingGraceOver) {
                return false;
            }
        }
        LivingEntity threat = goal.mob.nearestThreat(goal.mob.getTarget(), goal.THREAT_SCAN_RADIUS);
        if (threat == null || goal.mob.distanceTo(threat) > goal.WINDUP_BREAK_DISTANCE) {
            return false;
        }
        if (!goal.canBurstDisplacement(goal.mob)) {
            return false;
        }
        Vec3 dir = goal.sideBackAway(goal.mob, threat);
        double travel = Mth.clamp(goal.RETREAT_TARGET_DISTANCE - goal.mob.distanceTo(threat),
                4.0D, goal.MANEUVER_MAX_TRAVEL);
        goal.interruptCast();
        goal.state = State.IDLE;
        goal.maneuverSettleTicks = Math.max(0, goal.burstDisplacement(goal.mob, dir, travel));
        return true;
    }

    static void tickWindup(PlayerMobWandSpellGoal goal) {
        if (goal.castingSpell == null || !(goal.mob.level() instanceof ServerLevel level)) {
            goal.state = State.IDLE;
            return;
        }
        goal.mob.getNavigation().stop();
        goal.tickWindupStrafe();
        if (goal.breakWindupForRetreat()) {
            return;
        }
        goal.castTicks++;
        if (!goal.castStarted) {
            goal.castStarted = true;
            var sound = goal.castingSpell.CastingSound(goal.mob);
            if (sound != null) {
                level.playSound(null, goal.mob.getX(), goal.mob.getY(0.5D), goal.mob.getZ(), sound,
                        SoundSource.PLAYERS, goal.castingSpell.castingVolume(), goal.castingSpell.castingPitch());
            }
            goal.castingLoopSound = goal.castingSpell.loopSound(goal.mob);
            if (goal.castingLoopSound != null) {
                goal.playCastingLoopSound(level);
            }
            goal.castingSpell.startSpell(level, goal.mob, goal.castingWand, goal.castingStats);
        } else if (goal.castingLoopSound != null && --goal.castingLoopSoundTicks <= 0) {
            goal.playCastingLoopSound(level);
        }
        if (goal.castTicks >= goal.castTotal) {
            goal.completeCast(goal.castingCharging == null);
            return;
        }
        goal.castingSpell.useSpell(level, goal.mob, goal.castingWand, goal.castTicks, goal.castingStats);
        goal.castingSpell.useParticle(level, goal.mob, goal.castingWand);
        if (goal.castingSpell instanceof IBreathingSpell breathing && goal.castingCharging != null
                && goal.castTicks > goal.castingCastUp) {
            breathing.showWandBreath(goal.mob, goal.castingWand, goal.castingStats);
        }
        if (goal.castingCharging != null && goal.tickChargingChannel(level)) {
            goal.completeCast(false);
        }
    }

    static void playCastingLoopSound(PlayerMobWandSpellGoal goal, ServerLevel level) {
        if (goal.castingLoopSound == null || goal.castingSpell == null) {
            return;
        }
        level.playSound(null, goal.mob.getX(), goal.mob.getY(0.5D), goal.mob.getZ(), goal.castingLoopSound,
                SoundSource.PLAYERS, goal.castingSpell.castingVolume(), goal.castingSpell.castingPitch());
        goal.castingLoopSoundTicks = goal.LOOP_SOUND_REPEAT_TICKS;
    }

    static boolean tickChargingChannel(PlayerMobWandSpellGoal goal, ServerLevel level) {
        if (goal.castTicks <= goal.castingCastUp) {
            return false;
        }
        goal.channelTicks++;
        goal.channelShotTimer++;
        LivingEntity gear = goal.castGearContext(goal.master());
        int interval = Math.max(0, goal.castingCharging.Cooldown(gear, goal.castingWand, goal.channelShots));
        if (interval <= 0 || goal.channelShotTimer >= interval) {
            goal.channelShotTimer = 0;
            if (!goal.fireChargingShot(level)) {
                return true;
            }
        }
        int limit = Math.max(0, goal.castingCharging.shotsNumber(gear, goal.castingWand));
        if (limit > 0 && goal.channelShots >= limit) {
            return true;
        }
        return goal.channelTicks >= goal.MAX_CHANNEL_TICKS;
    }

    static boolean fireChargingShot(PlayerMobWandSpellGoal goal, ServerLevel level) {
        LivingEntity master = goal.master();
        LivingEntity context = master != null ? master : goal.mob;
        int cost = Math.max(0, goal.castingSpell.soulCost(context, goal.castingWand));
        if (goal.castingCharging.everCharge() && ++goal.channelSoulCounter % 20 != 0) {
            cost = 0;
        }
        if (cost > 0 && master instanceof Player player && !player.isCreative()) {
            if (!SEHelper.getSoulsAmount(player, cost)) {
                level.playSound(null, goal.mob.getX(), goal.mob.getY(0.5D), goal.mob.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.7F, 0.6F);
                return false;
            }
            SEHelper.decreaseSouls(player, cost);
        }
        goal.castingSpell.SpellResult(level, goal.mob, goal.castingWand, goal.castingStats);
        goal.channelShots++;
        return true;
    }

    static void tickWindupStrafe(PlayerMobWandSpellGoal goal) {
        Entity target = goal.castingAimEntity != null && goal.castingAimEntity.isAlive()
                ? goal.castingAimEntity : goal.mob.getTarget();
        // 前摇与持续施法全程盯住目标：死亡激光/炎呼这类"从身前持续发射"的聚晶,
        // 若只在起步瞬间对准, 后续目标移动会让伤害大量落空（同 J 村聚晶问题）。
        if (target != null && target.isAlive()) {
            goal.windupLookAt(target);
        }
        if (goal.castingDisplacement) {
            goal.mob.getMoveControl().strafe(0.0F, 0.0F);
            return;
        }
        if (target == null || !target.isAlive()) {
            goal.mob.getMoveControl().strafe(0.0F, 0.0F);
            return;
        }
        Vec3 mine = ServantMineAvoidance.nearestThreatMine(goal.mob, ServantMineAvoidance.SCAN_RADIUS);
        if (mine != null) {
            goal.faceDirection(goal.horizontalAway(mine));
            boolean panic = goal.mob.position().distanceToSqr(mine)
                    <= ServantMineAvoidance.PANIC_RADIUS * ServantMineAvoidance.PANIC_RADIUS;
            goal.mob.getMoveControl().strafe(panic ? goal.WINDUP_STRAFE_SPEED : goal.WINDUP_STRAFE_SPEED * 0.5F, 0.0F);
            return;
        }
        goal.mob.getMoveControl().strafe(0.0F, goal.strafeClockwise ? goal.WINDUP_STRAFE_SPEED : -goal.WINDUP_STRAFE_SPEED);
    }

}
