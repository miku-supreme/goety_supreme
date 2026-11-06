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

public final class PlayerMobWandSpellGoal extends Goal {

    private static final double MELEE_REACH = 4.0D;
    private static final double GUARD_RANGE = 6.0D;
    private static final double FLEE_TRIGGER_RANGE = 10.0D;
    static final int MIN_GAP_TICKS = 10;
    static final int MAX_GAP_TICKS = 20;

    private static final double OWNER_APPROACH_DISTANCE = 16.0D;

    private static final double RETREAT_TRIGGER_DISTANCE = 6.0D;

    static final double WINDUP_BREAK_DISTANCE = 6.0D;

    static final int LONG_WINDUP_TICKS = 100;

    static final int LOOP_SOUND_REPEAT_TICKS = 130;

    static final Set<String> CHARGE_DISTANCE_SPELLS = Set.of("QuakingSpell");

    static final int MAX_CHANNEL_TICKS = 200;

    private static final double PARTING_SHOT_MAX_RANGE = 10.0D;

    private static final double APPROACH_MIN_REACH = PARTING_SHOT_MAX_RANGE;

    static final int CLOSE_RANGE_MIN_CASTS = 4;
    static final int CLOSE_RANGE_MAX_CASTS = 5;

    static final float CLOSE_RANGE_ASSAULT_CHANCE = 0.5F;

    private static final int CLOSE_RANGE_ASSAULT_TIMEOUT = 120;

    static final int DAMAGE_ROTATION_BREADTH = 3;

    static final double RETREAT_TARGET_DISTANCE = 14.0D;

    static final double THREAT_SCAN_RADIUS = 12.0D;

    private static final double ENGAGE_MAX_DISTANCE = 28.0D;

    private static final double ENGAGE_TARGET_DISTANCE = 20.0D;

    private static final double GEO_ENGAGE_TARGET_DISTANCE = 15.0D;

    static final float INVOKER_FANGS_SNEAK_CHANCE = 0.45F;

    static final double VOID_RUNE_SNEAK_RANGE = 6.0D;
    static final double TEETH_SNEAK_RANGE = 4.0D;

    static final double BATTLEFIELD_SNEAK_RANGE = 6.0D;

    static final float OVERGROWTH_SNEAK_CHANCE = 0.5F;

    static final double NECRO_SHIELD_UNDEAD_RADIUS = 10.0D;

    private static final double ENGAGE_RANGE_MARGIN = 4.0D;

    static final int REPOSITION_MIN_CASTS = 3;
    static final int REPOSITION_MAX_CASTS = 4;

    private static final double REPOSITION_MIN_TRAVEL = 8.0D;
    private static final double REPOSITION_MAX_TRAVEL = 14.0D;

    static final double MANEUVER_MAX_TRAVEL = 24.0D;

    private static final double CROWD_SCAN_RADIUS = 12.0D;
    private static final double CROWD_CLUSTER_RADIUS = 3.0D;
    private static final int CROWD_MIN_ENEMIES = 2;
    private static final double CROWD_MIN_TRAVEL = 2.0D;

    private static final double ALLY_SCAN_RADIUS = 12.0D;
    static final int FLEE_TICKS = 40;

    static final int STRAFE_FLIP_INTERVAL = 20;
    static final float STRAFE_FLIP_CHANCE = 0.3F;

    static final float STRAFE_SPEED = 0.5F;

    private static final int PROBE_DECISION_INTERVAL = 20;

    static final float WINDUP_STRAFE_SPEED = STRAFE_SPEED * 0.5F;

    final PlayerMobServant mob;

    State state = State.IDLE;
    int gapTicks;
    int fleeTicks;

    int maneuverSettleTicks;

    int maneuverSettleOnComplete;

    int castsSinceReposition;

    int nextRepositionCasts = REPOSITION_MIN_CASTS;

    boolean castingReposition;

    int rangedDamageCasts;

    int nextCloseRangeCasts = CLOSE_RANGE_MIN_CASTS;

    boolean closeRangeAssault;

    int closeRangeAssaultTicks;

    boolean castingCloseRangeAssault;

    boolean castingRangedDamage;

    final Map<String, Integer> damageFocusUseOrder = new HashMap<>();
    int damageFocusUseCounter;
    List<LivingEntity> ownedCache;
    int ownedCacheTick = -1000;

    String lastFocusId = "";

    boolean strafeClockwise;
    int strafeTime = -1;

    private int probeTicks;

    int castTicks;
    int castTotal;
    boolean castStarted;
    ISpell castingSpell;
    SpellStat castingStats;
    ItemStack castingFocus = ItemStack.EMPTY;
    ItemStack castingWand = ItemStack.EMPTY;

    boolean castingDisplacement;

    boolean castingSneakBranch;

    boolean castingInvasionCleanup;

    SoundEvent castingLoopSound;
    int castingLoopSoundTicks;

    boolean longWindupCast;

    boolean castingPartingShot;

    boolean partingShotThenEscape;

    boolean chargeDistanceCast;

    IChargingSpell castingCharging;

    int castingCastUp;

    int channelTicks;

    int channelShots;

    int channelShotTimer;

    int channelSoulCounter;

    LivingEntity castingTouchTarget;

    Entity castingAimEntity;

    double castingAimYawOffset;
    String castingFocusId = "";    InteractionHand castingHand = InteractionHand.MAIN_HAND;

    enum State {
        IDLE,
        WINDUP,
        GAP,
        FLEE
    }

    enum Layout {
        NONE,
        WAND_ONLY,
        MELEE_MAIN_WAND_OFF,
        RANGED_MAIN_WAND_OFF,
        SHIELD_MAIN_WAND_OFF,
        WAND_MAIN_SHIELD_OFF
    }

    enum Maneuver {
        NONE,
        APPROACH_OWNER,
        APPROACH_TARGET,
        APPROACH_RANGE,
        DASH_CROWD,
        RETREAT,
        REPOSITION
    }

    public PlayerMobWandSpellGoal(PlayerMobServant mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob.level().isClientSide) {
            return false;
        }
        if (!mob.hasArcaneScroll()) {
            return false;
        }
        Layout layout = resolveLayout();
        if (layout == Layout.NONE) {
            return false;
        }
        if (mob.escapePartingShotTarget() != null) {
            return true;
        }
        if (resolveManeuver(layout, mob.getTarget()) != Maneuver.NONE) {
            return true;
        }
        if (hasDispellableTornado(layout)) {
            return true;
        }
        if (isWandCaster(layout)) {
            LivingEntity target = mob.getTarget();
            return (target != null && target.isAlive()) || hasSupportFocus(wandOf(layout))
                    || hasSurrenderedCleanupFocus(wandOf(layout));
        }
        LivingEntity target = mob.getTarget();
        boolean engaged = target != null && target.isAlive();
        if (!engaged) {
            return hasSupportFocus(wandOf(layout)) || hasSurrenderedCleanupFocus(wandOf(layout));
        }
        if (layout == Layout.MELEE_MAIN_WAND_OFF) {
            if (mob.isHealInterlude()) {
                return true;
            }
            if (mob.isWeakeningInterlude()) {
                return true;
            }
            if (mob.isMeleeWandMode()) {
                return false;
            }
            if (PlayerMobWandCasting.isSupportOnlyCatalog(mob, wandOf(layout))) {
                return PlayerMobWandCasting.hasUsableSupportFocus(mob, wandOf(layout));
            }
            return mob.distanceTo(target) > MELEE_REACH && hasAnyFocus(wandOf(layout));
        }
        return hasAnyUsableFocus(wandOf(layout));
    }

    @Override
    public boolean canContinueToUse() {
        if (state == State.WINDUP || state == State.FLEE) {
            return true;
        }
        return canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        mob.setAggressive(true);
    }

    @Override
    public void stop() {
        interruptCast();
        mob.stopWandShield();
        mob.setCastingHand(-1);
        mob.getNavigation().stop();
        mob.getMoveControl().strafe(0.0F, 0.0F);
        state = State.IDLE;
        gapTicks = 0;
        fleeTicks = 0;
    }

    @Override
    public void tick() {
        if (maneuverSettleTicks > 0) {
            maneuverSettleTicks--;
        }
        if (state == State.IDLE && mob.escapePartingShotTarget() != null) {
            handleEscapePartingShot();
            return;
        }
        if (state == State.WINDUP) {
            tickWindup();
            return;
        }
        if (state == State.FLEE) {
            tickFlee();
            return;
        }
        if (state == State.GAP) {
            if (--gapTicks <= 0) {
                state = State.IDLE;
            } else if (wandDrivesMovement(resolveLayout()) && maneuverSettleTicks <= 0) {
                LivingEntity gapTarget = mob.getTarget();
                if (gapTarget != null && gapTarget.isAlive()
                        && mob.distanceTo(gapTarget) > engageMaxDistance(resolveLayout())) {
                    mob.setCombatChaseSprint(true);
                    mob.getNavigation().moveTo(gapTarget, 1.2D);
                } else if (!approachToCastRange(resolveLayout(), gapTarget)) {
                    tickStrafe(gapTarget);
                }
            } else if (maneuverSettleTicks > 0) {
                mob.getNavigation().stop();
                mob.getMoveControl().strafe(0.0F, 0.0F);
            }
            return;
        }

        LivingEntity target = mob.getTarget();
        Layout layout = resolveLayout();
        if (layout == Layout.NONE) {
            clearCloseRangeAssault();
            return;
        }
        if (target == null || !target.isAlive()) {
            clearCloseRangeAssault();
            if (tryManeuver(layout, null)) {
                return;
            }
            if (tryDispelTornado(layout)) {
                return;
            }
            trySupportCast(layout);
            return;
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        maybeProbeFocusDecision(target, layout);
        double distance = mob.distanceTo(target);
        tickCloseRangeAssault(layout, target);
        if (tryManeuver(layout, target)) {
            return;
        }
        if (distance > engageMaxDistance(layout)) {
            mob.setCombatChaseSprint(true);
            mob.getNavigation().moveTo(target, 1.2D);
            return;
        }
        if (layout == Layout.SHIELD_MAIN_WAND_OFF) {
            if (mob.getShieldDisableTicks() > 0 && distance <= FLEE_TRIGGER_RANGE) {
                beginFlee();
                return;
            }
            if (distance <= GUARD_RANGE) {
                holdShield(layout);
                return;
            }
            if (!castOne(target, layout)) {
                clearShield();
                if (!approachToCastRange(layout, target)) {
                    tickStrafe(target);
                }
            }
            return;
        }
        if (layout == Layout.WAND_MAIN_SHIELD_OFF) {
            if (mob.getShieldDisableTicks() > 0 && distance <= FLEE_TRIGGER_RANGE) {
                beginFlee();
                return;
            }
            if (castOne(target, layout)) {
                return;
            }
            if (distance <= GUARD_RANGE) {
                holdShield(layout);
            } else {
                clearShield();
                if (!approachToCastRange(layout, target)) {
                    tickStrafe(target);
                }
            }
            return;
        }
        if (!castOne(target, layout) && wandDrivesMovement(layout)) {
            if (!approachToCastRange(layout, target)) {
                tickStrafe(target);
            }
        }
    }

    private boolean castOne(LivingEntity target, Layout layout) {
        if (tryDispelTornado(layout)) {
            return true;
        }
        FocusEntry entry = pickFocus(target, layout);
        if (entry == null) {
            return false;
        }
        return beginCast(entry, layout);
    }

    private boolean approachToCastRange(Layout layout, @Nullable LivingEntity target) {
        if (target == null || !target.isAlive() || target == mob || mob.isMeleeWandMode()) {
            return false;
        }
        ItemStack wand = wandOf(layout);
        if (wand.isEmpty()) {
            return false;
        }
        double distance = mob.distanceTo(target);
        if (closeRangeAssault) {
            double reach = closeRangeAssaultReach(wand);
            if (reach <= 0.0D) {
                clearCloseRangeAssault();
                return false;
            }
            if (distance <= reach) {
                return false;
            }
            mob.setCombatChaseSprint(true);
            mob.getNavigation().moveTo(target, 1.2D);
            return true;
        }
        if (distance <= RETREAT_TRIGGER_DISTANCE) {
            return false;
        }
        double needed = 0.0D;
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (entry.isDisplacement() || entry.isUtility() || mob.isWandFocusOnCooldown(entry.itemId())
                    || !isDamageFocus(entry)) {
                continue;
            }
            double reach;
            if (entry.isSelfCenteredAoe()) {
                reach = PlayerMobWandCasting.focusRadius(mob, entry);
            } else if (entry.isRangedSpell() || entry.isConeSpell()) {
                reach = PlayerMobWandCasting.focusRange(mob, entry);
            } else {
                continue;
            }
            if (reach <= APPROACH_MIN_REACH || distance <= reach) {
                continue;
            }
            needed = Math.max(needed, reach);
        }
        if (needed <= 0.0D || distance <= needed) {
            return false;
        }
        mob.setCombatChaseSprint(true);
        mob.getNavigation().moveTo(target, 1.2D);
        return true;
    }

    private boolean tryDispelTornado(Layout layout) {
        if (mob.level().isClientSide) {
            return false;
        }
        FocusEntry wind = null;
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wandOf(layout))) {
            if (entry.isWindBlast() && !mob.isWandFocusOnCooldown(entry.itemId())) {
                wind = entry;
                break;
            }
        }
        if (wind == null) {
            return false;
        }
        Entity tornado = nearestHostileTornado(wind);
        if (tornado == null) {
            return false;
        }
        this.castingAimEntity = tornado;
        if (beginCast(wind, layout)) {
            return true;
        }
        this.castingAimEntity = null;
        return false;
    }

    private boolean hasDispellableTornado(Layout layout) {
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wandOf(layout))) {
            if (entry.isWindBlast() && !mob.isWandFocusOnCooldown(entry.itemId())
                    && nearestHostileTornado(entry) != null) {
                return true;
            }
        }
        return false;
    }

    private Entity nearestHostileTornado(FocusEntry wind) {
        double range = PlayerMobWandCasting.focusRange(mob, wind);
        if (range <= 0.0D) {
            range = 16.0D;
        }
        Entity best = null;
        double bestDist = Double.MAX_VALUE;
        for (AbstractCyclone cyclone : mob.level().getEntitiesOfClass(AbstractCyclone.class,
                mob.getBoundingBox().inflate(range))) {
            if (!cyclone.isAlive()) {
                continue;
            }
            LivingEntity trueOwner = cyclone.getTrueOwner();
            if (trueOwner != null && isAlly(trueOwner)) {
                continue;
            }
            double dist = mob.distanceTo(cyclone);
            if (dist <= range && dist < bestDist) {
                bestDist = dist;
                best = cyclone;
            }
        }
        return best;
    }

    private void trySupportCast(Layout layout) {
        FocusEntry entry = pickSupportFocus(layout);
        if (entry == null || !beginCast(entry, layout)) {
            clearShield();
        }
    }

    private Maneuver resolveManeuver(Layout layout, LivingEntity target) {
        if (mob.isWeakeningInterlude() || mob.isHealInterlude()) {
            return Maneuver.NONE;
        }
        ItemStack wand = wandOf(layout);
        if (wand.isEmpty() || !hasDisplacementFocus(wand)) {
            return Maneuver.NONE;
        }
        if (target == null || !target.isAlive() || !mob.isValidCombatTargetForGoal(target)) {
            if (!mob.isFollowing() || mob.isCommanded()) {
                return Maneuver.NONE;
            }
            if (ServantTeleportHelper.teleportTargetOwner(mob) != null) {
                return Maneuver.NONE;
            }
            LivingEntity owner = master();
            if (owner != null && owner != mob && owner.isAlive()
                    && mob.distanceTo(owner) > OWNER_APPROACH_DISTANCE) {
                return Maneuver.APPROACH_OWNER;
            }
            return Maneuver.NONE;
        }
        if (mob.isMeleeWandMode()) {
            if (hasDashFocus(wand) && crowdedPoint() != null) {
                return Maneuver.DASH_CROWD;
            }
            return mob.distanceTo(target) > MELEE_REACH ? Maneuver.APPROACH_TARGET : Maneuver.NONE;
        }
        if (closeRangeAssault && closeRangeAssaultReach(wand) >= mob.distanceTo(target)) {
            return Maneuver.NONE;
        }
        LivingEntity threat = mob.nearestThreat(target, THREAT_SCAN_RADIUS);
        if (threat != null && mob.distanceTo(threat) <= RETREAT_TRIGGER_DISTANCE) {
            return Maneuver.RETREAT;
        }
        if (mob.distanceTo(target) > engageMaxDistance(layout)) {
            return Maneuver.APPROACH_RANGE;
        }
        if (castsSinceReposition >= nextRepositionCasts) {
            return Maneuver.REPOSITION;
        }
        return Maneuver.NONE;
    }

    private boolean hasDisplacementFocus(ItemStack wand) {
        return pickDisplacementFocus(wand, null) != null;
    }

    private double engageMaxDistance(Layout layout) {
        double longest = PlayerMobWandCasting.longestCastReach(mob, wandOf(layout));
        return Math.max(ENGAGE_MAX_DISTANCE, longest + ENGAGE_RANGE_MARGIN);
    }

    private double engageTargetDistance(Layout layout) {
        return PlayerMobWandCasting.isGoetyItem(wandOf(layout), "geo_staff")
                ? GEO_ENGAGE_TARGET_DISTANCE
                : ENGAGE_TARGET_DISTANCE;
    }

    private boolean hasDashFocus(ItemStack wand) {
        return pickDisplacementFocus(wand, Maneuver.DASH_CROWD) != null;
    }

    private FocusEntry pickDisplacementFocus(ItemStack wand, Maneuver mode) {
        List<FocusEntry> candidates = displacementCandidates(mob, wand, mode);
        return candidates.isEmpty() ? null : pickRandomMatching(candidates, entry -> true);
    }

    public static List<FocusEntry> displacementCandidates(PlayerMobServant mob, ItemStack wand, Maneuver mode) {
        List<FocusEntry> candidates = new ArrayList<>();
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (!entry.isDisplacement()
                    || (mode == Maneuver.DASH_CROWD && !entry.isDashCharge())
                    || mob.isWandFocusOnCooldown(entry.itemId())) {
                continue;
            }
            if (entry.spell() instanceof EndermanHandFocusSpell
                    && !EndermanHandFocusSpell.teleportReadyFor(mob)) {
                continue;
            }
            candidates.add(entry);
        }
        return candidates;
    }

    public static FocusEntry pickAnyDisplacementFocus(PlayerMobServant mob, ItemStack wand) {
        List<FocusEntry> candidates = displacementCandidates(mob, wand, null);
        return candidates.isEmpty() ? null
                : candidates.get(mob.getRandom().nextInt(candidates.size()));
    }

    private Vec3 crowdedPoint() {
        List<LivingEntity> enemies = new ArrayList<>();
        for (LivingEntity entity : nearbyEntities(mob, CROWD_SCAN_RADIUS)) {
            if (entity != mob && entity.isAlive() && mob.isValidCombatTargetForGoal(entity)) {
                enemies.add(entity);
            }
        }
        if (enemies.size() < CROWD_MIN_ENEMIES) {
            return null;
        }
        double clusterSqr = CROWD_CLUSTER_RADIUS * CROWD_CLUSTER_RADIUS;
        Vec3 best = null;
        int bestCount = 0;
        for (LivingEntity candidate : enemies) {
            int count = 0;
            for (LivingEntity other : enemies) {
                if (other.distanceToSqr(candidate) <= clusterSqr) {
                    count++;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                best = candidate.position();
            }
        }
        if (best == null || bestCount < CROWD_MIN_ENEMIES) {
            return null;
        }
        double dx = best.x - mob.getX();
        double dz = best.z - mob.getZ();
        return Math.sqrt(dx * dx + dz * dz) < CROWD_MIN_TRAVEL ? null : best;
    }

    private boolean tryManeuver(Layout layout, LivingEntity target) {
        if (mob.isWeakeningInterlude() || mob.isHealInterlude()) {
            return false;
        }
        Maneuver mode = resolveManeuver(layout, target);
        if (mode == Maneuver.NONE) {
            return false;
        }
        if (mode == Maneuver.RETREAT
                && tryPartingShotCast(layout, mob.nearestThreat(target, THREAT_SCAN_RADIUS),
                        false, false)) {
            return true;
        }
        FocusEntry entry = pickDisplacementFocus(wandOf(layout), mode);
        if (entry == null) {
            return false;
        }
        aimManeuver(layout, mode, target, entry);
        maneuverSettleOnComplete = entry.isTeleportDisplacement() ? 6 : 20;
        castingReposition = mode == Maneuver.REPOSITION;
        boolean started = beginCast(entry, layout);
        if (!started) {
            maneuverSettleOnComplete = 0;
            castingReposition = false;
        }
        return started;
    }

    private static final double RETREAT_SIDE_MIN_DEGREES = 35.0D;
    private static final double RETREAT_SIDE_MAX_DEGREES = 60.0D;

    public static Vec3 sideBackAway(PlayerMobServant mob, @Nullable LivingEntity threat) {
        Vec3 away;
        if (threat != null) {
            Vec3 raw = new Vec3(mob.getX() - threat.getX(), 0.0D, mob.getZ() - threat.getZ());
            away = raw.lengthSqr() < 1.0E-4D ? randomHorizontal(mob) : raw.normalize();
        } else {
            away = randomHorizontal(mob);
        }
        double angle = Math.toRadians(RETREAT_SIDE_MIN_DEGREES
                + mob.getRandom().nextDouble() * (RETREAT_SIDE_MAX_DEGREES - RETREAT_SIDE_MIN_DEGREES));
        double side = mob.getRandom().nextBoolean() ? 1.0D : -1.0D;
        Vec3 dir = rotateHorizontal(away, side * angle);
        Vec3 mine = ServantMineAvoidance.nearestThreatMine(mob, ServantMineAvoidance.SCAN_RADIUS);
        if (mine != null && dir.dot(horizontalAwayFrom(mob, mine)) < 0.0D) {
            dir = rotateHorizontal(away, -side * angle);
        }
        return dir;
    }

    private static Vec3 randomHorizontal(PlayerMobServant mob) {
        double angle = mob.getRandom().nextDouble() * Math.PI * 2.0D;
        return new Vec3(Math.cos(angle), 0.0D, Math.sin(angle));
    }

    private static Vec3 rotateHorizontal(Vec3 dir, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vec3(dir.x * cos - dir.z * sin, 0.0D, dir.x * sin + dir.z * cos);
    }

    private static Vec3 horizontalAwayFrom(PlayerMobServant mob, Vec3 from) {
        Vec3 away = new Vec3(mob.getX() - from.x, 0.0D, mob.getZ() - from.z);
        return away.lengthSqr() < 1.0E-4D ? new Vec3(1.0D, 0.0D, 0.0D) : away.normalize();
    }

    private void aimManeuver(Layout layout, Maneuver mode, LivingEntity target, FocusEntry entry) {
        Vec3 here = mob.position();
        Vec3 dir;
        double travel;
        if (mode == Maneuver.RETREAT) {
            LivingEntity threat = mob.nearestThreat(target, THREAT_SCAN_RADIUS);
            dir = sideBackAway(mob, threat);
            if (threat == null) {
                travel = RETREAT_TARGET_DISTANCE;
            } else {
                double distance = mob.distanceTo(threat);
                travel = Mth.clamp(RETREAT_TARGET_DISTANCE - distance, 4.0D, MANEUVER_MAX_TRAVEL);
            }
        } else if (mode == Maneuver.REPOSITION) {
            dir = repositionDirection(target);
            travel = REPOSITION_MIN_TRAVEL
                    + mob.getRandom().nextDouble() * (REPOSITION_MAX_TRAVEL - REPOSITION_MIN_TRAVEL);
        } else {
            Vec3 dest;
            if (mode == Maneuver.APPROACH_OWNER) {
                dest = master() != null ? master().position() : here;
            } else if (mode == Maneuver.DASH_CROWD) {
                Vec3 crowd = crowdedPoint();
                dest = crowd != null ? crowd : (target != null ? target.position() : here);
            } else {
                dest = target != null ? target.position() : here;
            }
            Vec3 to = new Vec3(dest.x - here.x, 0.0D, dest.z - here.z);
            double horizontal = to.length();
            dir = horizontal < 1.0E-4D ? new Vec3(1.0D, 0.0D, 0.0D) : to.scale(1.0D / horizontal);
            double stop = mode == Maneuver.APPROACH_TARGET ? 2.0D
                    : mode == Maneuver.DASH_CROWD ? 0.5D
                    : mode == Maneuver.APPROACH_RANGE ? engageTargetDistance(layout) : 1.0D;
            travel = Mth.clamp(horizontal - stop, 1.5D, MANEUVER_MAX_TRAVEL);
        }
        applyAim(mob, dir, travel, entry.isTeleportDisplacement());
    }

    private Vec3 repositionDirection(LivingEntity target) {
        Vec3 dir;
        if (target != null) {
            Vec3 toTarget = new Vec3(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ());
            Vec3 base = toTarget.lengthSqr() < 1.0E-4D ? new Vec3(1.0D, 0.0D, 0.0D)
                    : toTarget.normalize();
            double angle = (mob.getRandom().nextBoolean() ? 1.0D : -1.0D)
                    * Math.toRadians(60.0D + mob.getRandom().nextDouble() * 60.0D);
            dir = new Vec3(base.x * Math.cos(angle) - base.z * Math.sin(angle), 0.0D,
                    base.x * Math.sin(angle) + base.z * Math.cos(angle));
        } else {
            double angle = mob.getRandom().nextDouble() * Math.PI * 2.0D;
            dir = new Vec3(Math.cos(angle), 0.0D, Math.sin(angle));
        }
        Vec3 mine = ServantMineAvoidance.nearestThreatMine(mob, ServantMineAvoidance.SCAN_RADIUS);
        if (mine != null && dir.dot(horizontalAway(mine)) < 0.0D) {
            dir = dir.scale(-1.0D);
        }
        return dir;
    }

    public static void applyAim(PlayerMobServant mob, Vec3 dir, double travel, boolean teleport) {
        Vec3 here = mob.position();
        Vec3 look;
        if (teleport) {
            Vec3 ground = new Vec3(here.x + dir.x * travel, here.y + 0.1D, here.z + dir.z * travel);
            look = ground.subtract(mob.getEyePosition());
        } else {
            look = new Vec3(dir.x, 0.0D, dir.z);
        }
        if (look.lengthSqr() < 1.0E-6D) {
            return;
        }
        look = look.normalize();
        float yaw = (float) (Mth.atan2(look.z, look.x) * (180.0D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(look.y,
                Math.sqrt(look.x * look.x + look.z * look.z)) * (180.0D / Math.PI)));
        mob.setYRot(yaw);
        mob.setXRot(pitch);
        mob.setYHeadRot(yaw);
        mob.yBodyRot = yaw;
        Vec3 aimPoint = mob.getEyePosition().add(look.scale(10.0D));
        mob.getLookControl().setLookAt(aimPoint.x, aimPoint.y, aimPoint.z, 360.0F, 360.0F);
    }

    public static ItemStack findCastingWand(PlayerMobServant mob) {
        if (GoetyStaffGuard.isGoetyStaff(mob.getMainHandItem())) {
            return mob.getMainHandItem();
        }
        if (GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            return mob.getOffhandItem();
        }
        return mob.meleeWandStack();
    }

    public static boolean canBurstDisplacement(PlayerMobServant mob) {
        if (!mob.hasArcaneScroll()) {
            return false;
        }
        ItemStack wand = findCastingWand(mob);
        if (wand.isEmpty()) {
            return false;
        }
        FocusEntry entry = pickAnyDisplacementFocus(mob, wand);
        if (entry == null) {
            return false;
        }
        LivingEntity master = masterOf(mob);
        if (master instanceof Player player && !player.isCreative()) {
            int cost = Math.max(0, entry.spell().soulCost(master, wand));
            return SEHelper.getSoulsAmount(player, cost);
        }
        return true;
    }

    public static int burstDisplacement(PlayerMobServant mob, Vec3 dir, double travel) {
        if (!mob.hasArcaneScroll() || !(mob.level() instanceof ServerLevel level)) {
            return 0;
        }
        ItemStack wand = findCastingWand(mob);
        if (wand.isEmpty()) {
            return 0;
        }
        FocusEntry entry = pickAnyDisplacementFocus(mob, wand);
        if (entry == null) {
            return 0;
        }
        applyAim(mob, dir, travel, entry.isTeleportDisplacement());
        return castNow(level, mob, wand, entry)
                ? (entry.isTeleportDisplacement() ? 6 : 20) : 0;
    }

    public static boolean canCastHealNow(PlayerMobServant mob) {
        if (!mob.hasArcaneScroll()) {
            return false;
        }
        ItemStack wand = findCastingWand(mob);
        if (wand.isEmpty()) {
            return false;
        }
        FocusEntry entry = pickReadyHealFocus(mob, wand);
        if (entry == null) {
            return false;
        }
        LivingEntity master = masterOf(mob);
        if (master instanceof Player player && !player.isCreative()) {
            int cost = Math.max(0, entry.spell().soulCost(master, wand));
            return SEHelper.getSoulsAmount(player, cost);
        }
        return true;
    }

    private static FocusEntry pickReadyHealFocus(PlayerMobServant mob, ItemStack wand) {
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (entry.isHeal() && !mob.isWandFocusOnCooldown(entry.itemId())
                    && PlayerMobWandCasting.healNeeded(mob, entry)) {
                return entry;
            }
        }
        return null;
    }

    private static boolean castNow(ServerLevel level, PlayerMobServant mob, ItemStack wand,
            FocusEntry entry) {
        ISpell spell = entry.spell();
        LivingEntity master = masterOf(mob);
        LivingEntity context = master != null ? master : mob;
        int cost = Math.max(0, spell.soulCost(context, wand));
        if (master instanceof Player player && !player.isCreative()) {
            if (!SEHelper.getSoulsAmount(player, cost)) {
                level.playSound(null, mob.getX(), mob.getY(0.5D), mob.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.7F, 0.6F);
                return false;
            }
            SEHelper.decreaseSouls(player, cost);
        }
        PlayerMobWandCasting.equipFocus(wand, entry);
        SpellStat stats = PlayerMobWandCasting.buildStats(spell, master);
        InteractionHand hand = GoetyStaffGuard.isGoetyStaff(mob.getMainHandItem())
                ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        SoundEvent sound = spell.CastingSound(mob);
        if (sound != null) {
            level.playSound(null, mob.getX(), mob.getY(0.5D), mob.getZ(), sound,
                    SoundSource.PLAYERS, spell.castingVolume(), spell.castingPitch());
        }
        spell.SpellResult(level, mob, wand, stats);
        int cooldown = Math.max(0, spell.spellCooldown(context));
        if (cooldown > 0) {
            mob.addWandFocusCooldown(entry.itemId(), cooldown);
        }
        mob.swing(hand);
        return true;
    }

    private static LivingEntity masterOf(PlayerMobServant mob) {
        LivingEntity owner = mob.getMasterOwner();
        return owner != null ? owner : mob.getTrueOwner();
    }

    private boolean tryPartingShotCast(Layout layout, @Nullable LivingEntity target, boolean thenEscape,
            boolean instantOnly) {
        if (!mob.isPartingShotReady() || target == null || !target.isAlive() || target == mob) {
            return false;
        }
        FocusEntry entry = pickPartingShotFocus(mob, target, instantOnly);
        if (entry == null) {
            return false;
        }
        this.castingAimEntity = target;
        this.partingShotThenEscape = thenEscape;
        this.castingPartingShot = true;
        this.maneuverSettleOnComplete = 0;
        mob.setWandPartingShot(true);
        if (!beginCast(entry, layout)) {
            this.castingPartingShot = false;
            this.partingShotThenEscape = false;
            this.castingAimEntity = null;
            mob.setWandPartingShot(false);
            return false;
        }
        mob.markPartingShotUsed();
        return true;
    }

    private void handleEscapePartingShot() {
        LivingEntity threat = mob.escapePartingShotTarget();
        boolean instantOnly = mob.escapePartingShotInstant();
        mob.clearForcedPartingShot();
        if (threat == null || !threat.isAlive()) {
            return;
        }
        Layout layout = resolveLayout();
        if (layout != Layout.NONE && tryPartingShotCast(layout, threat, true, instantOnly)) {
            return;
        }
        burstAwayFrom(threat);
    }

    void burstAwayFrom(LivingEntity threat) {
        Vec3 here = mob.position();
        Vec3 away = threat == null ? new Vec3(mob.getRandom().nextDouble() - 0.5D, 0.0D,
                mob.getRandom().nextDouble() - 0.5D)
                : new Vec3(here.x - threat.getX(), 0.0D, here.z - threat.getZ());
        Vec3 dir = away.lengthSqr() < 1.0E-4D ? new Vec3(1.0D, 0.0D, 0.0D) : away.normalize();
        int settle = burstDisplacement(mob, dir, RETREAT_TARGET_DISTANCE);
        if (settle > 0) {
            maneuverSettleTicks = settle;
        }
    }

    public static boolean hasPartingShotTarget(PlayerMobServant mob, @Nullable LivingEntity target,
            boolean instantOnly) {
        return mob.isPartingShotReady() && pickPartingShotFocus(mob, target, instantOnly) != null;
    }

    @Nullable
    private static FocusEntry pickPartingShotFocus(PlayerMobServant mob, @Nullable LivingEntity target,
            boolean instantOnly) {
        if (target == null || !target.isAlive() || target == mob) {
            return null;
        }
        ItemStack wand = findCastingWand(mob);
        if (wand.isEmpty()) {
            return null;
        }
        LivingEntity master = masterOf(mob);
        boolean freeCasting = !(master instanceof Player player) || player.isCreative();
        List<FocusEntry> candidates = new ArrayList<>();
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (!isPartingShotCandidate(mob, entry, target)) {
                continue;
            }
            if (instantOnly && entry.spell().defaultCastDuration() > 0) {
                continue;
            }
            if (!freeCasting) {
                int cost = Math.max(0, entry.spell().soulCost(master, wand));
                if (!SEHelper.getSoulsAmount((Player) master, cost)) {
                    continue;
                }
            }
            candidates.add(entry);
        }
        return candidates.isEmpty() ? null
                : candidates.get(mob.getRandom().nextInt(candidates.size()));
    }

    private static boolean isPartingShotCandidate(PlayerMobServant mob, FocusEntry entry,
            LivingEntity target) {
        if (entry.isDisplacement() || entry.isUtility() || mob.isWandFocusOnCooldown(entry.itemId())) {
            return false;
        }
        if (entry.spell() instanceof IChargingSpell) {
            return false;
        }
        if (entry.spell().defaultCastDuration() > LONG_WINDUP_TICKS) {
            return false;
        }
        if (entry.isSummon() || entry.isHeal() || entry.isCharge() || entry.isChill()
                || entry.isWeakening() || PlayerMobWandCasting.isBuffFocus(mob, entry)) {
            return false;
        }
        double distance = mob.distanceTo(target);
        if (entry.isCloseRangeSelfCentered() || entry.isSneakSelfCentered()
                || entry.isCloseRangeWindup()) {
            return distance <= PARTING_SHOT_MAX_RANGE;
        }
        double reach = entry.isSelfCenteredAoe()
                ? PlayerMobWandCasting.focusRadius(mob, entry)
                : PlayerMobWandCasting.focusRange(mob, entry);
        if (reach <= 0.0D) {
            return distance <= PARTING_SHOT_MAX_RANGE;
        }
        return reach <= PARTING_SHOT_MAX_RANGE && distance <= reach;
    }

    private double closeRangeDamageReach(FocusEntry entry) {
        if (entry.isDisplacement() || entry.isUtility() || mob.isWandFocusOnCooldown(entry.itemId())) {
            return -1.0D;
        }
        if (entry.spell() instanceof IChargingSpell) {
            return -1.0D;
        }
        if (entry.spell().defaultCastDuration() > LONG_WINDUP_TICKS) {
            return -1.0D;
        }
        if (entry.isSummon() || entry.isHeal() || entry.isCharge() || entry.isChill()
                || entry.isWeakening() || isBuffFocus(entry) || !isDamageFocus(entry)) {
            return -1.0D;
        }
        if (entry.spell().defaultCastDuration() <= 0 && !entry.isSneakSelfCentered()) {
            return -1.0D;
        }
        if (entry.isCloseRangeSelfCentered() || entry.isSneakSelfCentered()
                || entry.isCloseRangeWindup()) {
            return PARTING_SHOT_MAX_RANGE;
        }
        double reach = entry.isSelfCenteredAoe()
                ? PlayerMobWandCasting.focusRadius(mob, entry)
                : PlayerMobWandCasting.focusRange(mob, entry);
        if (reach <= 0.0D) {
            return PARTING_SHOT_MAX_RANGE;
        }
        return reach <= PARTING_SHOT_MAX_RANGE ? reach : -1.0D;
    }

    boolean isCloseRangeDamageFocus(FocusEntry entry) {
        return closeRangeDamageReach(entry) > 0.0D;
    }

    private double closeRangeAssaultReach(ItemStack wand) {
        double best = -1.0D;
        if (wand == null || wand.isEmpty()) {
            return best;
        }
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            double reach = closeRangeDamageReach(entry);
            if (reach > best) {
                best = reach;
            }
        }
        return best;
    }

    void clearCloseRangeAssault() {
        closeRangeAssault = false;
        closeRangeAssaultTicks = 0;
        castingCloseRangeAssault = false;
    }

    private void tickCloseRangeAssault(Layout layout, LivingEntity target) {
        if (!closeRangeAssault) {
            return;
        }
        if (target == null || !target.isAlive()) {
            clearCloseRangeAssault();
            return;
        }
        if (++closeRangeAssaultTicks > CLOSE_RANGE_ASSAULT_TIMEOUT
                || closeRangeAssaultReach(wandOf(layout)) <= 0.0D) {
            clearCloseRangeAssault();
        }
    }

    boolean isRangedDamageFocus(FocusEntry entry) {
        if (!isDamageFocus(entry) || entry.isDisplacement() || entry.isUtility()) {
            return false;
        }
        if (entry.isCloseRangeSelfCentered() || entry.isSneakSelfCentered()
                || entry.isCloseRangeWindup()) {
            return false;
        }
        if (entry.isSelfCenteredAoe()) {
            return PlayerMobWandCasting.focusRadius(mob, entry) > APPROACH_MIN_REACH;
        }
        if (entry.isRangedSpell() || entry.isConeSpell()) {
            double reach = PlayerMobWandCasting.focusRange(mob, entry);
            return reach <= 0.0D || reach > APPROACH_MIN_REACH;
        }
        return true;
    }

    FocusEntry pickFocus(LivingEntity target, Layout layout) { return PlayerMobWandFocusPicker.pickFocus(this, target, layout); }

    FocusEntry pickRandomMatching(List<FocusEntry> usable, java.util.function.Predicate<FocusEntry> predicate) { return PlayerMobWandFocusPicker.pickRandomMatching(this, usable, predicate); }

    FocusEntry pickDamageFocus(List<FocusEntry> usable, boolean wantAoe) { return PlayerMobWandFocusPicker.pickDamageFocus(this, usable, wantAoe); }

    int damageUseOrder(FocusEntry entry) { return PlayerMobWandFocusPicker.damageUseOrder(this, entry); }

    FocusEntry pickFrom(List<FocusEntry> candidates) { return PlayerMobWandFocusPicker.pickFrom(this, candidates); }

    FocusEntry pickSupportFocus(Layout layout) { return PlayerMobWandFocusPicker.pickSupportFocus(this, layout); }

    List<FocusEntry> usableFocuses(Layout layout, LivingEntity target) { return PlayerMobWandFocusPicker.usableFocuses(this, layout, target); }

    boolean isUsable(FocusEntry entry, LivingEntity target) { return PlayerMobWandFocusPicker.isUsable(this, entry, target); }

    boolean necroShieldUsable(LivingEntity target) { return PlayerMobWandFocusPicker.necroShieldUsable(this, target); }

    boolean necroShieldWantsConvert() { return PlayerMobWandFocusPicker.necroShieldWantsConvert(this); }

    boolean wildUndeadNearby(double radius) { return PlayerMobWandFocusPicker.wildUndeadNearby(this, radius); }

    static double sneakRange(FocusEntry entry) { return PlayerMobWandFocusPicker.sneakRange(entry); }

    boolean shouldSneakCast(ItemStack focus, ItemStack wand, ISpell spell) { return PlayerMobWandFocusPicker.shouldSneakCast(this, focus, wand, spell); }

    boolean invokerFangsSneakWorthwhile() { return PlayerMobWandFocusPicker.invokerFangsSneakWorthwhile(this); }

    boolean closeRangeForSneak(double range) { return PlayerMobWandFocusPicker.closeRangeForSneak(this, range); }

    private void maybeProbeFocusDecision(LivingEntity target, Layout layout) {
        if (!WandFocusProbe.isEnabled() || ++probeTicks < PROBE_DECISION_INTERVAL) {
            return;
        }
        probeTicks = 0;
        ItemStack wand = wandOf(layout);
        if (wand.isEmpty()) {
            return;
        }
        List<FocusEntry> watched = new ArrayList<>();
        List<String> usableOthers = new ArrayList<>();
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (WandFocusProbe.isWatched(entry.spell())) {
                watched.add(entry);
            } else if (isUsable(entry, target)) {
                usableOthers.add(entry.spell().getClass().getSimpleName());
            }
        }
        if (watched.isEmpty()) {
            return;
        }
        StringBuilder line = new StringBuilder("[决策] 仆从=").append(WandFocusProbe.entity(mob))
                .append(" 目标=").append(WandFocusProbe.entity(target))
                .append(" 距离=").append(WandFocusProbe.trim(mob.distanceTo(target)))
                .append(" 档位=").append(layout)
                .append(" 交战距离上限=").append(WandFocusProbe.trim(engageMaxDistance(layout)));
        for (FocusEntry entry : watched) {
            line.append(" || ").append(probeFocusLine(entry, target));
        }
        line.append(" || 本刻可放的其他聚晶=")
                .append(usableOthers.isEmpty() ? "（无）" : String.join(", ", usableOthers));
        WandFocusProbe.record(line.toString());
    }

    private String probeFocusLine(FocusEntry entry, LivingEntity target) {
        StringBuilder line = new StringBuilder();
        line.append(entry.itemId()).append('(').append(entry.spell().getClass().getSimpleName())
                .append(") cd=").append(mob.getWandFocusCooldown(entry.itemId()));
        if (entry.isSelfCenteredAoe()) {
            line.append(" 自圆心半径=").append(WandFocusProbe.trim(focusRadius(entry)));
        }
        if (entry.isRangedSpell() || entry.isConeSpell()) {
            line.append(" 射程=").append(WandFocusProbe.trim(PlayerMobWandCasting.focusRange(mob, entry)));
        }
        line.append(" 判定=").append(isUsable(entry, target) ? "可用" : probeBlockReason(entry, target));
        return line.toString();
    }

    private String probeBlockReason(FocusEntry entry, LivingEntity target) {
        if (entry.isDisplacement()) {
            return "位移聚晶(只走机动档)";
        }
        if (entry.isUtility()) {
            return "功能聚晶(战斗里不用)";
        }
        int cooldown = mob.getWandFocusCooldown(entry.itemId());
        if (cooldown > 0) {
            return "冷却中(剩" + cooldown + "刻)";
        }
        if (target != null && entry.isSelfCenteredAoe() && !inFocusRadius(entry, target)) {
            return "目标在自圆心半径外(距离" + WandFocusProbe.trim(mob.distanceTo(target))
                    + " > 半径" + WandFocusProbe.trim(focusRadius(entry)) + ")";
        }
        if (target != null && entry.isConeSpell() && !inConeRange(entry, target)) {
            return "扇形射程/朝向不符(射程"
                    + WandFocusProbe.trim(PlayerMobWandCasting.focusRange(mob, entry)) + ")";
        }
        if (target != null && entry.isRangedSpell() && !inFocusRange(entry, target)) {
            return "超出施法距离(距离" + WandFocusProbe.trim(mob.distanceTo(target))
                    + " > 射程" + WandFocusProbe.trim(PlayerMobWandCasting.focusRange(mob, entry)) + ")";
        }
        if (entry.isSummon()) {
            if (mob.getWandSummonCooldown() > 0) {
                return "召唤冷却中(剩" + mob.getWandSummonCooldown() + "刻)";
            }
            return summonBelowLimit(entry) ? "可用" : "已达召唤上限";
        }
        if (entry.isHeal()) {
            return healCondition(entry) ? "可用" : "不需要治疗";
        }
        if (entry.isChill()) {
            return chillCondition(entry, wandOf(resolveLayout())) ? "可用" : "不需要冷静";
        }
        if (entry.isCharge()) {
            return target == null && chargeTarget(entry) != null ? "可用" : "非战斗且无合法友方目标";
        }
        if (isBuffFocus(entry)) {
            return buffNeeded(entry) ? "可用" : "增益还在";
        }
        if (entry.isWeakening()) {
            return weakenNeeded(target) && inFocusRadius(entry, target)
                    ? "可用" : "目标已有/免疫弱化或不在半径内";
        }
        return "可用";
    }

    void probeCastStart(FocusEntry entry, ItemStack wand, @Nullable LivingEntity master,
            LivingEntity context) {
        if (castingSpell == null || !WandFocusProbe.isEnabled()) {
            return;
        }
        LivingEntity target = mob.getTarget();
        String guard = !longWindupCast ? "无"
                : (castingCharging != null ? "前" + LONG_WINDUP_TICKS + "刻" : "全程");
        WandFocusProbe.record("[起手] 仆从=" + WandFocusProbe.entity(mob)
                + " 法术=" + castingSpell.getClass().getSimpleName()
                + " 聚晶=" + entry.itemId()
                + " 前摇=" + castTotal + "刻(贴身打断保护=" + guard + ")"
                + " 灵魂消耗=" + Math.max(0, castingSpell.soulCost(context, wand))
                + " 主人灵魂=" + ownerSoulText(master)
                + " 目标=" + WandFocusProbe.entity(target)
                + " 目标距离=" + (target == null ? "-" : WandFocusProbe.trim(mob.distanceTo(target))));
    }

    void probeCastResult(@Nullable ISpell spell, ItemStack wand, boolean fireResult,
            boolean paid, @Nullable LivingEntity master, LivingEntity context) {
        if (spell == null || !WandFocusProbe.isEnabled() || !WandFocusProbe.isWatched(spell)) {
            return;
        }
        WandFocusProbe.record("[结算] 仆从=" + WandFocusProbe.entity(mob)
                + " 法术=" + spell.getClass().getSimpleName()
                + " 聚晶=" + castingFocusId
                + " 按结果结算=" + fireResult
                + " 付灵魂=" + paid
                + " 需要灵魂=" + Math.max(0, spell.soulCost(context, wand))
                + " 主人灵魂=" + ownerSoulText(master)
                + " 前摇=" + castTicks + "/" + castTotal
                + " 冷却=" + Math.max(0, spell.spellCooldown(context)) + "刻");
    }

    void probeCastInterrupt() {
        if (castingSpell == null || !WandFocusProbe.isEnabled()
                || !WandFocusProbe.isWatched(castingSpell)) {
            return;
        }
        WandFocusProbe.record("[中断] 仆从=" + WandFocusProbe.entity(mob)
                + " 法术=" + castingSpell.getClass().getSimpleName()
                + " 聚晶=" + castingFocusId
                + " 前摇=" + castTicks + "/" + castTotal
                + " 状态=" + state);
    }

    private String ownerSoulText(@Nullable LivingEntity master) {
        if (master instanceof Player player) {
            return String.valueOf(SEHelper.getSESouls(player));
        }
        return "非玩家主人(不扣灵魂)";
    }

    boolean isDamageFocus(FocusEntry entry) {
        return !entry.isSummon() && !entry.isHeal() && !entry.isChill()
                && !entry.isWeakening() && !entry.isCharge() && !entry.isDisplacement()
                && !isBuffFocus(entry) && !entry.isUtility() && !entry.isNecroShield();
    }

    boolean isBuffFocus(FocusEntry entry) {
        return PlayerMobWandCasting.isBuffFocus(mob, entry);
    }

    boolean healCondition(FocusEntry entry) {
        return PlayerMobWandCasting.healNeeded(mob, entry);
    }

    boolean chillCondition(FocusEntry entry, ItemStack wand) {
        if (buffNeeded(entry)) {
            return true;
        }
        if (wand != null && entry.spell().typeStaff(wand, SpellType.FROST)) {
            return countAlliesMissingChill() >= 2;
        }
        return false;
    }

    boolean weakenNeeded(LivingEntity target) {
        return PlayerMobWandCasting.weakenNeeded(target);
    }

    boolean inFocusRadius(FocusEntry entry, LivingEntity target) {
        if (target == null) {
            return false;
        }
        return mob.distanceTo(target) <= focusRadius(entry);
    }

    private double focusRadius(FocusEntry entry) {
        return PlayerMobWandCasting.focusRadius(mob, entry);
    }

    boolean inConeRange(FocusEntry entry, LivingEntity target) {
        if (target == null) {
            return false;
        }
        double range = PlayerMobWandCasting.focusRange(mob, entry);
        if (range <= 0.0D || mob.distanceTo(target) > range) {
            return false;
        }
        Vec3 look = mob.getViewVector(1.0F);
        Vec3 to = target.position().subtract(mob.position());
        return to.lengthSqr() < 1.0E-4D || look.dot(to.normalize()) > 0.0D;
    }

    boolean inFocusRange(FocusEntry entry, LivingEntity target) {
        if (target == null) {
            return false;
        }
        double range = PlayerMobWandCasting.focusRange(mob, entry);
        return range <= 0.0D || mob.distanceTo(target) <= range;
    }

    boolean buffNeeded(FocusEntry entry) {
        return PlayerMobWandCasting.buffNeeded(mob, entry);
    }

    boolean summonBelowLimit(FocusEntry entry) {
        return !(entry.spell() instanceof SummonSpell spell) || summonBelowLimit(spell);
    }

    boolean summonBelowLimit(SummonSpell spell) {
        LivingEntity master = master();
        int count = 0;
        for (LivingEntity entity : ownedNearby()) {
            if (!(entity instanceof IOwned owned) || !spell.summonPredicate().test(entity)
                    || !entity.isAlive()) {
                continue;
            }
            LivingEntity trueOwner = owned.getTrueOwner();
            if (trueOwner == mob || (master != null && trueOwner == master)) {
                count++;
            }
        }
        return count < spell.summonLimit();
    }

    boolean isInvasionSummonFocus(FocusEntry entry) {
        if (entry == null || !PlayerMobWandCasting.isNamespace(entry.stack(),
                PlayerMobWandCasting.INVASION_NS)) {
            return false;
        }
        if (entry.isSummon()) {
            return true;
        }
        String id = entry.itemId();
        String name = entry.spell() == null ? "" : entry.spell().getClass().getSimpleName();
        return id.contains("surrender") || id.contains("bone") || name.contains("Surrender")
                || name.contains("Bone");
    }

    private int ownInvasionServants() {
        int count = 0;
        for (LivingEntity entity : ownedNearby()) {
            if (entity != mob && ownsInvasionServant(entity)) {
                count++;
            }
        }
        return count;
    }

    private boolean ownsInvasionServant(LivingEntity entity) {
        if (entity == mob || !entity.isAlive() || !(entity instanceof IOwned owned)
                || owned.getTrueOwner() != mob) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return id != null && PlayerMobWandCasting.INVASION_NS.equals(id.getNamespace());
    }

    boolean isInvasionCleanupCast(ItemStack focus, ISpell spell) {
        return spell instanceof SummonSpell && PlayerMobWandCasting.isNamespace(focus,
                PlayerMobWandCasting.INVASION_NS) && surrenderedCleanupWanted();
    }

    boolean surrenderedCleanupWanted() {
        if (ownInvasionServants() <= 0) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        return target == null || !target.isAlive();
    }

    private boolean hasSurrenderedCleanupFocus(ItemStack wand) {
        if (wand == null || wand.isEmpty()) {
            return false;
        }
        for (FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (isInvasionSummonFocus(entry) && !mob.isWandFocusOnCooldown(entry.itemId())
                    && surrenderedCleanupWanted()) {
                return true;
            }
        }
        return false;
    }

    void executeOwnInvasionServants() {
        for (LivingEntity entity : new ArrayList<>(nearbyEntities(mob, 64.0D))) {
            if (ownsInvasionServant(entity)) {
                entity.kill();
            }
        }
    }

    int countEnemies(LivingEntity target) {
        if (target == null) {
            return 0;
        }
        int count = 0;
        for (LivingEntity entity : nearbyEntities(target, 8.0D)) {
            if (entity != mob && entity.isAlive() && mob.isValidCombatTargetForGoal(entity)) {
                count++;
            }
        }
        return count;
    }

    private int countAlliesMissingChill() {
        int count = 0;
        for (LivingEntity entity : nearbyEntities(mob, ALLY_SCAN_RADIUS)) {
            if (isAlly(entity) && !entity.hasEffect(GoetyEffects.CHILL_HIDE.get())) {
                count++;
            }
        }
        return count;
    }

    boolean isAlly(LivingEntity entity) {
        return entity != null && (entity == mob || mob.isAlliedTo(entity) || entity.isAlliedTo(mob));
    }

    static List<LivingEntity> nearbyEntities(LivingEntity center, double radius) {
        AABB box = center.getBoundingBox().inflate(radius);
        return center.level().getEntitiesOfClass(LivingEntity.class, box);
    }

    boolean beginCast(FocusEntry entry, Layout layout) { return PlayerMobWandCastBegin.beginCast(this, entry, layout); }

    LivingEntity resolveTouchTarget(FocusEntry entry) { return PlayerMobWandCastBegin.resolveTouchTarget(this, entry); }

    LivingEntity chargeTarget(FocusEntry entry) { return PlayerMobWandCastBegin.chargeTarget(this, entry); }

    boolean isChargeCandidate(LivingEntity entity) { return PlayerMobWandCastBegin.isChargeCandidate(this, entity); }

    double touchReach() { return PlayerMobWandCastBegin.touchReach(this); }

    boolean breakWindupForRetreat() { return PlayerMobWandCastBegin.breakWindupForRetreat(this); }

    void tickWindup() { PlayerMobWandCastBegin.tickWindup(this); }

    void playCastingLoopSound(ServerLevel level) { PlayerMobWandCastBegin.playCastingLoopSound(this, level); }

    boolean tickChargingChannel(ServerLevel level) { return PlayerMobWandCastBegin.tickChargingChannel(this, level); }

    boolean fireChargingShot(ServerLevel level) { return PlayerMobWandCastBegin.fireChargingShot(this, level); }

    void tickWindupStrafe() { PlayerMobWandCastBegin.tickWindupStrafe(this); }

    void completeCast() { PlayerMobWandCastFinish.completeCast(this); }

    void completeCast(boolean fireResult) { PlayerMobWandCastFinish.completeCast(this, fireResult); }

    void interruptCast() { PlayerMobWandCastFinish.interruptCast(this); }

    void resetCast() { PlayerMobWandCastFinish.resetCast(this); }

    Set<String> beneficialEffectIds() { return PlayerMobWandLocomotion.beneficialEffectIds(this); }

    void holdShield(Layout layout) { PlayerMobWandLocomotion.holdShield(this, layout); }

    void clearShield() { PlayerMobWandLocomotion.clearShield(this); }

    void beginFlee() { PlayerMobWandLocomotion.beginFlee(this); }

    void tickFlee() { PlayerMobWandLocomotion.tickFlee(this); }

    boolean isWandCaster(Layout layout) { return PlayerMobWandLocomotion.isWandCaster(this, layout); }

    boolean wandDrivesMovement(Layout layout) { return PlayerMobWandLocomotion.wandDrivesMovement(this, layout); }

    void tickStrafe(LivingEntity target) { PlayerMobWandLocomotion.tickStrafe(this, target); }

    Vec3 horizontalAway(Vec3 from) { return PlayerMobWandLocomotion.horizontalAway(this, from); }

    void faceDirection(Vec3 dir) { PlayerMobWandLocomotion.faceDirection(this, dir); }

    void snapLookAt(Entity target) { PlayerMobWandLocomotion.snapLookAt(this, target); }

    public static void snapLookAt(PlayerMobServant mob, Entity target) { PlayerMobWandLocomotion.snapLookAt(mob, target); }

    public static void snapLookAt(PlayerMobServant mob, Entity target, double yawOffsetDeg) { PlayerMobWandLocomotion.snapLookAt(mob, target, yawOffsetDeg); }

    void windupLookAt(Entity target) { PlayerMobWandLocomotion.windupLookAt(this, target); }

    double parallelStaffAimYawOffset(FocusEntry entry, ItemStack wand, Entity target) { return PlayerMobWandLocomotion.parallelStaffAimYawOffset(this, entry, wand, target); }

    LivingEntity castGearContext(LivingEntity master) { return PlayerMobWandLocomotion.castGearContext(this, master); }

    public static LivingEntity castGearContext(PlayerMobServant mob, LivingEntity master) { return PlayerMobWandLocomotion.castGearContext(mob, master); }

    static final Set<String> SELF_FOCUS_CAST_DURATION_SPELLS = Set.of(
            "QuakingSpell", "ThunderstormSpell", "VoidShockSpell");

    LivingEntity castDurationContext(ISpell spell, LivingEntity gearContext) { return PlayerMobWandLocomotion.castDurationContext(this, spell, gearContext); }

    ItemStack wandOf(Layout layout) { return PlayerMobWandLocomotion.wandOf(this, layout); }

    ItemStack otherHand(Layout layout) { return PlayerMobWandLocomotion.otherHand(this, layout); }

    InteractionHand wandHand(Layout layout) { return PlayerMobWandLocomotion.wandHand(this, layout); }

    Layout resolveLayout() { return PlayerMobWandLocomotion.resolveLayout(this); }

    boolean hasAnyUsableFocus(ItemStack wand) { return PlayerMobWandLocomotion.hasAnyUsableFocus(this, wand); }

    boolean hasAnyFocus(ItemStack wand) { return PlayerMobWandLocomotion.hasAnyFocus(this, wand); }

    List<LivingEntity> ownedNearby() { return PlayerMobWandLocomotion.ownedNearby(this); }

    boolean hasSupportFocus(ItemStack wand) { return PlayerMobWandLocomotion.hasSupportFocus(this, wand); }

    static boolean isRangedWeapon(ItemStack stack) { return PlayerMobWandLocomotion.isRangedWeapon(stack); }

    static boolean isMeleeWeapon(ItemStack stack) { return PlayerMobWandLocomotion.isMeleeWeapon(stack); }

    LivingEntity master() { return PlayerMobWandLocomotion.master(this); }
}
