package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpiritTeleporter;

public class SakuraSpiritTeleportGoal extends Goal {

    private static final int FLEE_COOLDOWN = 20;

    private static final int FAIL_COOLDOWN = 20;

    private static final double PANIC_FACTOR = 0.75D;

    private static final double APPROACH_GAP = 2.0D;

    private final Mob mob;
    private int cooldown;
    private boolean returnPending;

    public SakuraSpiritTeleportGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (!SakuraSpiritConfig.NEAR_DEATH_TELEPORT.get()) {
            return false;
        }
        if (!(this.mob instanceof ISakuraSpiritTeleporter)) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return this.returnPending || isLowHealth();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        if (this.cooldown > 0) {
            this.cooldown--;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()
                || !(this.mob instanceof ISakuraSpiritTeleporter teleporter)) {
            return;
        }
        double keep = SakuraSpiritConfig.fleeKeepDistance();
        double dist = this.mob.distanceTo(target);
        if (isLowHealth()) {
            this.returnPending = true;
            if (dist < keep * PANIC_FACTOR) {
                this.mob.getNavigation().stop();
                if (this.cooldown <= 0) {
                    this.cooldown = teleporter.tryTeleportAwayFrom(target, keep)
                            ? FLEE_COOLDOWN : FAIL_COOLDOWN;
                }
            } else if (dist > keep + APPROACH_GAP) {
                approachToRing(target, keep);
            } else {
                this.mob.getNavigation().stop();
            }
        } else {
            double reengage = SakuraSpiritConfig.reengageDistance();
            if (reengage > 0.0D && dist > reengage) {
                if (this.cooldown <= 0) {
                    if (teleporter.tryTeleportNearTo(target, keep + 2.0D, keep + 8.0D)) {
                        this.returnPending = false;
                        this.cooldown = FLEE_COOLDOWN;
                    } else {
                        this.cooldown = FAIL_COOLDOWN;
                    }
                }
            } else {
                this.returnPending = false;
            }
        }
    }

    @Override
    public void stop() {
        this.returnPending = false;
    }

    private void approachToRing(LivingEntity target, double keep) {
        double dx = target.getX() - this.mob.getX();
        double dz = target.getZ() - this.mob.getZ();
        double flat = Math.sqrt(dx * dx + dz * dz);
        if (flat < 1.0E-3D) {
            this.mob.getNavigation().stop();
            return;
        }
        double step = flat - keep;
        this.mob.getNavigation().moveTo(this.mob.getX() + dx / flat * step,
                this.mob.getY(), this.mob.getZ() + dz / flat * step, 1.0D);
    }

    private boolean isLowHealth() {
        return this.mob.getHealth() < this.mob.getMaxHealth() * SakuraSpiritConfig.lowHealthRatio();
    }
}
