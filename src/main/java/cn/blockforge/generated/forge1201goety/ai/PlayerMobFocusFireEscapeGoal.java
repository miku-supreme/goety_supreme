package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;

public final class PlayerMobFocusFireEscapeGoal extends Goal {

    private static final double THREAT_RADIUS = 12.0D;

    private static final int MAX_FLEE_TICKS = 60;

    private static final int FLEE_REPATH_TICKS = 20;

    private static final int DISPLACEMENT_DELAY_TICKS = 40;

    private static final double DISPLACEMENT_TRAVEL = 14.0D;

    private final PlayerMobServant mob;

    private int settleTicks;

    private boolean displaced;
    private int fleeTicks;
    private int fleeRepath;
    private int displacementDelay;

    private boolean yieldForPartingShot;

    private boolean panicEscape;
    @Nullable
    private Vec3 fleePos;

    public PlayerMobFocusFireEscapeGoal(PlayerMobServant mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (mob.level().isClientSide || !mob.isEffectiveAi()) {
            return false;
        }
        if (mob.isUnderPanicEscape()) {
            return true;
        }
        if (mob.isCastingPartingShot()) {
            return false;
        }
        if (mob.escapePartingShotTarget() != null) {
            return false;
        }
        return mob.isFocusFireEscapeReady() && mob.isUnderFocusFire();
    }

    @Override
    public boolean canContinueToUse() {
        if (yieldForPartingShot) {
            return false;
        }
        return settleTicks > 0 || (fleeTicks < MAX_FLEE_TICKS && !displaced);
    }

    @Override
    public void start() {
        panicEscape = mob.isUnderPanicEscape();
        mob.setAggressive(true);
        mob.markFocusFireEscaped();
        settleTicks = 0;
        displaced = false;
        fleeTicks = 0;
        fleeRepath = 0;
        displacementDelay = 0;
        yieldForPartingShot = false;
        fleePos = null;
        if (panicEscape) {
            mob.clearForcedPartingShot();
        }
        mob.setCombatChaseSprint(true);
    }

    @Override
    public void stop() {
        settleTicks = 0;
        displaced = false;
        fleeTicks = 0;
        fleePos = null;
        yieldForPartingShot = false;
        panicEscape = false;
        mob.setCombatChaseSprint(false);
        mob.getNavigation().stop();
        mob.getMoveControl().strafe(0.0F, 0.0F);
    }

    @Override
    public void tick() {
        if (settleTicks > 0) {
            settleTicks--;
            mob.getNavigation().stop();
            mob.getMoveControl().strafe(0.0F, 0.0F);
            return;
        }
        fleeTicks++;
        LivingEntity threat = mob.nearestThreat(mob.getTarget(), THREAT_RADIUS);
        if (threat != null) {
            mob.getLookControl().setLookAt(threat, 30.0F, 30.0F);
        }
        if (--displacementDelay <= 0) {
            boolean panic = panicEscape || mob.isUnderPanicEscape();
            if (threat != null && PlayerMobWandSpellGoal.hasPartingShotTarget(mob, threat, panic)) {
                mob.requestEscapePartingShot(threat, panic);
                yieldForPartingShot = true;
                return;
            }
            if (PlayerMobWandSpellGoal.canBurstDisplacement(mob)) {
                int settle = PlayerMobWandSpellGoal.burstDisplacement(mob,
                        awayFrom(threat), DISPLACEMENT_TRAVEL);
                if (settle > 0) {
                    displaced = true;
                    settleTicks = settle;
                    displacementDelay = DISPLACEMENT_DELAY_TICKS;
                    mob.setCombatChaseSprint(true);
                    return;
                }
            }
            displacementDelay = 10;
        }
        if (fleePos == null || --fleeRepath <= 0) {
            fleePos = LandRandomPos.getPosAway(mob, 16, 7,
                    threat != null ? threat.position() : mob.position());
            fleeRepath = FLEE_REPATH_TICKS;
        }
        Vec3 dest = ServantMineAvoidance.safeDestination(mob, fleePos, 8.0D);
        if (dest == null) {
            Vec3 dir = awayFrom(threat).scale(8.0D);
            dest = ServantMineAvoidance.safeDestination(mob,
                    new Vec3(mob.getX() + dir.x, mob.getY(), mob.getZ() + dir.z), 8.0D);
        }
        if (dest != null) {
            mob.setCombatChaseSprint(true);
            mob.getNavigation().moveTo(dest.x, dest.y, dest.z, 1.25D);
        }
    }

    private Vec3 awayFrom(@Nullable LivingEntity threat) {
        return PlayerMobWandSpellGoal.sideBackAway(mob, threat);
    }
}
