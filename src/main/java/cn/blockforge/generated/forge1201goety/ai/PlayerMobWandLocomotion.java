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

final class PlayerMobWandLocomotion {
    static Set<String> beneficialEffectIds(PlayerMobWandSpellGoal goal) {
        Set<String> ids = new HashSet<>();
        for (MobEffectInstance instance : goal.mob.getActiveEffects()) {
            MobEffect effect = instance.getEffect();
            if (effect.getCategory() != MobEffectCategory.BENEFICIAL) {
                continue;
            }
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(effect);
            if (key != null) {
                ids.add(key.toString());
            }
        }
        return ids;
    }

    static void holdShield(PlayerMobWandSpellGoal goal, Layout layout) {
        if (goal.state == State.WINDUP) {
            return;
        }
        ItemStack other = goal.otherHand(layout);
        if (!(other.getItem() instanceof ShieldItem)) {
            return;
        }
        goal.mob.startWandShield(layout == Layout.SHIELD_MAIN_WAND_OFF
                ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    static void clearShield(PlayerMobWandSpellGoal goal) {
        goal.mob.stopWandShield();
    }

    static void beginFlee(PlayerMobWandSpellGoal goal) {
        goal.clearShield();
        goal.state = State.FLEE;
        goal.fleeTicks = goal.FLEE_TICKS;
    }

    static void tickFlee(PlayerMobWandSpellGoal goal) {
        LivingEntity target = goal.mob.getTarget();
        if (--goal.fleeTicks <= 0 || target == null || !target.isAlive()) {
            goal.state = State.IDLE;
            goal.mob.setCombatChaseSprint(false);
            return;
        }
        LivingEntity threat = goal.mob.nearestThreat(target, goal.THREAT_SCAN_RADIUS);
        var dir = goal.sideBackAway(goal.mob, threat != null ? threat : target).scale(8.0D);
        Vec3 dest = ServantMineAvoidance.safeDestination(goal.mob,
                new Vec3(goal.mob.getX() + dir.x, goal.mob.getY(), goal.mob.getZ() + dir.z), 8.0D);
        if (dest == null) {
            dest = goal.mob.position().add(dir);
        }
        goal.mob.getNavigation().moveTo(dest.x, dest.y, dest.z, 1.25D);
        goal.mob.setCombatChaseSprint(true);
    }

    static boolean isWandCaster(PlayerMobWandSpellGoal goal, Layout layout) {
        return layout == Layout.WAND_ONLY
                || layout == Layout.WAND_MAIN_SHIELD_OFF
                || layout == Layout.SHIELD_MAIN_WAND_OFF;
    }

    static boolean wandDrivesMovement(PlayerMobWandSpellGoal goal, Layout layout) {
        return layout != Layout.NONE;
    }

    static void tickStrafe(PlayerMobWandSpellGoal goal, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            goal.mob.getNavigation().stop();
            return;
        }
        goal.mob.getNavigation().stop();
        goal.mob.setCombatChaseSprint(false);
        goal.strafeTime++;
        if (goal.strafeTime >= goal.STRAFE_FLIP_INTERVAL) {
            if (goal.mob.getRandom().nextFloat() < goal.STRAFE_FLIP_CHANCE) {
                goal.strafeClockwise = !goal.strafeClockwise;
            }
            goal.strafeTime = 0;
        }
        Vec3 mine = ServantMineAvoidance.nearestThreatMine(goal.mob, ServantMineAvoidance.SCAN_RADIUS);
        if (mine != null) {
            goal.faceDirection(goal.horizontalAway(mine));
            boolean panic = goal.mob.position().distanceToSqr(mine)
                    <= ServantMineAvoidance.PANIC_RADIUS * ServantMineAvoidance.PANIC_RADIUS;
            goal.mob.getMoveControl().strafe(panic ? 1.0F : 0.6F, 0.0F);
            return;
        }
        goal.mob.lookAt(target, 30.0F, 30.0F);
        goal.mob.getMoveControl().strafe(0.0F, goal.strafeClockwise ? goal.STRAFE_SPEED : -goal.STRAFE_SPEED);
    }

    static Vec3 horizontalAway(PlayerMobWandSpellGoal goal, Vec3 from) {
        Vec3 away = new Vec3(goal.mob.getX() - from.x, 0.0D, goal.mob.getZ() - from.z);
        return away.lengthSqr() < 1.0E-4D ? new Vec3(1.0D, 0.0D, 0.0D) : away.normalize();
    }

    static void faceDirection(PlayerMobWandSpellGoal goal, Vec3 dir) {
        float yaw = (float) (Mth.atan2(dir.z, dir.x) * (180.0D / Math.PI)) - 90.0F;
        goal.mob.setYRot(yaw);
        goal.mob.setYHeadRot(yaw);
        goal.mob.yBodyRot = yaw;
        goal.mob.getLookControl().setLookAt(goal.mob.getX() + dir.x * 5.0D, goal.mob.getEyeY(),
                goal.mob.getZ() + dir.z * 5.0D, 360.0F, 360.0F);
    }

    static void snapLookAt(PlayerMobWandSpellGoal goal, Entity target) {
        goal.snapLookAt(goal.mob, target, 0.0D);
    }

    static void snapLookAt(PlayerMobServant mob, Entity target) {
        PlayerMobWandSpellGoal.snapLookAt(mob, target, 0.0D);
    }

    static void snapLookAt(PlayerMobServant mob, Entity target, double yawOffsetDeg) {
        double dx = target.getX() - mob.getX();
        double dy = target.getEyeY() - mob.getEyeY();
        double dz = target.getZ() - mob.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F
                + (float) yawOffsetDeg;
        float pitch = (float) (-(Mth.atan2(dy, horizontal) * (180.0D / Math.PI)));
        mob.setYRot(yaw);
        mob.setXRot(pitch);
        mob.setYHeadRot(yaw);
        mob.yBodyRot = yaw;
        if (yawOffsetDeg == 0.0D) {
            mob.getLookControl().setLookAt(target, 360.0F, 360.0F);
            return;
        }
        double yawRad = Math.toRadians(yaw);
        double reach = Math.max(1.0D, horizontal);
        mob.getLookControl().setLookAt(mob.getX() - Math.sin(yawRad) * reach, target.getEyeY(),
                mob.getZ() + Math.cos(yawRad) * reach, 360.0F, 360.0F);
    }

    static void windupLookAt(PlayerMobWandSpellGoal goal, Entity target) {
        if (goal.castingAimYawOffset == 0.0D) {
            goal.mob.lookAt(target, 30.0F, 30.0F);
            goal.mob.setYRot(goal.mob.getYHeadRot());
            goal.mob.yBodyRot = goal.mob.getYHeadRot();
            return;
        }
        double dx = target.getX() - goal.mob.getX();
        double dz = target.getZ() - goal.mob.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F
                + (float) goal.castingAimYawOffset;
        double yawRad = Math.toRadians(yaw);
        double reach = Math.max(1.0D, horizontal);
        goal.mob.getLookControl().setLookAt(goal.mob.getX() - Math.sin(yawRad) * reach, target.getEyeY(),
                goal.mob.getZ() + Math.cos(yawRad) * reach, 30.0F, 30.0F);
        goal.mob.setYRot(goal.mob.getYHeadRot());
        goal.mob.yBodyRot = goal.mob.getYHeadRot();
    }

    static double parallelStaffAimYawOffset(PlayerMobWandSpellGoal goal, FocusEntry entry, ItemStack wand, Entity target) {
        if (entry == null || wand == null || wand.isEmpty() || target == null) {
            return 0.0D;
        }
        String name = entry.spell().getClass().getSimpleName();
        if ("VoidRuneSpell".equals(name)) {
            if (!entry.spell().typeStaff(wand, SpellType.VOID)) {
                return 0.0D;
            }
            return 90.0D;
        }
        if (!"LightningSpearSpell".equals(name) || !entry.spell().typeStaff(wand, SpellType.STORM)) {
            return 0.0D;
        }
        double dx = target.getX() - goal.mob.getX();
        double dz = target.getZ() - goal.mob.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        if (distance < 1.0E-4D) {
            return 0.0D;
        }
        double delta = Math.asin(Mth.clamp(0.5D / distance, -1.0D, 1.0D));
        return Math.toDegrees(Mth.clamp(delta, -Math.toRadians(25.0D), Math.toRadians(25.0D)));
    }

    static LivingEntity castGearContext(PlayerMobWandSpellGoal goal, LivingEntity master) {
        return goal.castGearContext(goal.mob, master);
    }

    static LivingEntity castGearContext(PlayerMobServant mob, LivingEntity master) {
        if (master != null) {
            return master;
        }
        LivingEntity topOwner = ServantOwnerGear.topOwner(mob);
        return topOwner != null ? topOwner : mob;
    }

    static LivingEntity castDurationContext(PlayerMobWandSpellGoal goal, ISpell spell, LivingEntity gearContext) {
        if (gearContext != goal.mob
                && goal.SELF_FOCUS_CAST_DURATION_SPELLS.contains(spell.getClass().getSimpleName())) {
            return goal.mob;
        }
        return gearContext;
    }

    static ItemStack wandOf(PlayerMobWandSpellGoal goal, Layout layout) {
        return switch (layout) {
            case MELEE_MAIN_WAND_OFF, RANGED_MAIN_WAND_OFF, SHIELD_MAIN_WAND_OFF -> goal.mob.getOffhandItem();
            case WAND_MAIN_SHIELD_OFF -> goal.mob.getMainHandItem();
            case WAND_ONLY -> goal.mob.getMainHandItem().getItem() instanceof IWand
                    ? goal.mob.getMainHandItem() : goal.mob.getOffhandItem();
            case NONE -> ItemStack.EMPTY;
        };
    }

    static ItemStack otherHand(PlayerMobWandSpellGoal goal, Layout layout) {
        return switch (layout) {
            case MELEE_MAIN_WAND_OFF, RANGED_MAIN_WAND_OFF, SHIELD_MAIN_WAND_OFF -> goal.mob.getMainHandItem();
            case WAND_MAIN_SHIELD_OFF -> goal.mob.getOffhandItem();
            case WAND_ONLY -> goal.mob.getMainHandItem().getItem() instanceof IWand
                    ? goal.mob.getOffhandItem() : goal.mob.getMainHandItem();
            case NONE -> ItemStack.EMPTY;
        };
    }

    static InteractionHand wandHand(PlayerMobWandSpellGoal goal, Layout layout) {
        return layout == Layout.WAND_MAIN_SHIELD_OFF ? InteractionHand.MAIN_HAND
                : layout == Layout.WAND_ONLY
                        ? (goal.mob.getMainHandItem().getItem() instanceof IWand
                                ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND)
                        : InteractionHand.OFF_HAND;
    }

    static Layout resolveLayout(PlayerMobWandSpellGoal goal) {
        ItemStack main = goal.mob.getMainHandItem();
        ItemStack off = goal.mob.getOffhandItem();
        boolean mainWand = main.getItem() instanceof IWand;
        boolean offWand = off.getItem() instanceof IWand;
        if (!mainWand && !offWand) {
            return Layout.NONE;
        }
        if (mainWand) {
            return off.getItem() instanceof ShieldItem ? Layout.WAND_MAIN_SHIELD_OFF : Layout.WAND_ONLY;
        }
        if (main.getItem() instanceof ShieldItem) {
            return Layout.SHIELD_MAIN_WAND_OFF;
        }
        if (goal.isRangedWeapon(main)) {
            return Layout.RANGED_MAIN_WAND_OFF;
        }
        if (goal.isMeleeWeapon(main)) {
            return Layout.MELEE_MAIN_WAND_OFF;
        }
        return Layout.WAND_ONLY;
    }

    static boolean hasAnyUsableFocus(PlayerMobWandSpellGoal goal, ItemStack wand) {
        for (FocusEntry entry : PlayerMobWandCasting.catalog(goal.mob, wand)) {
            if (goal.isUsable(entry, goal.mob.getTarget())) {
                return true;
            }
        }
        return false;
    }

    static boolean hasAnyFocus(PlayerMobWandSpellGoal goal, ItemStack wand) {
        return !PlayerMobWandCasting.catalog(goal.mob, wand).isEmpty();
    }

    static List<LivingEntity> ownedNearby(PlayerMobWandSpellGoal goal) {
        if (goal.mob.tickCount - goal.ownedCacheTick > 10 || goal.ownedCache == null) {
            goal.ownedCacheTick = goal.mob.tickCount;
            goal.ownedCache = new ArrayList<>();
            for (LivingEntity entity : goal.nearbyEntities(goal.mob, 64.0D)) {
                if (entity instanceof IOwned && entity.isAlive()) {
                    goal.ownedCache.add(entity);
                }
            }
        }
        return goal.ownedCache;
    }

    static boolean hasSupportFocus(PlayerMobWandSpellGoal goal, ItemStack wand) {
        for (FocusEntry entry : PlayerMobWandCasting.catalog(goal.mob, wand)) {
            if (entry.isHeal() && !goal.mob.isWandFocusOnCooldown(entry.itemId())
                    && PlayerMobWandCasting.healNeeded(goal.mob, entry)) {
                return true;
            }
            if (entry.isChill() && goal.chillCondition(entry, wand)) {
                return true;
            }
            if (entry.isCharge() && !goal.mob.isWandFocusOnCooldown(entry.itemId())
                    && goal.chargeTarget(entry) != null) {
                return true;
            }
        }
        return false;
    }

    static boolean isRangedWeapon(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof ProjectileWeaponItem || PlayerMobServant.isTridentLike(stack));
    }

    static boolean isMeleeWeapon(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem() instanceof ProjectileWeaponItem
                || PlayerMobServant.isTridentLike(stack)) {
            return false;
        }
        return stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE);
    }

    static LivingEntity master(PlayerMobWandSpellGoal goal) {
        LivingEntity owner = goal.mob.getMasterOwner();
        return owner != null ? owner : goal.mob.getTrueOwner();
    }

}
