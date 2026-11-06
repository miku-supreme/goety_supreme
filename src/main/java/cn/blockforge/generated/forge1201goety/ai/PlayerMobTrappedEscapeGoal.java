package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class PlayerMobTrappedEscapeGoal extends Goal {

    private final PlayerMobServant mob;

    private int settleTicks;

    public PlayerMobTrappedEscapeGoal(PlayerMobServant mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.isTrappedInHazard() && mob.isHazardEscapeReady();
    }

    @Override
    public boolean canContinueToUse() {
        return settleTicks > 0 || (mob.isTrappedInHazard() && mob.isHazardEscapeReady());
    }

    @Override
    public void start() {
        mob.setAggressive(true);
    }

    @Override
    public void stop() {
        settleTicks = 0;
        mob.setCombatChaseSprint(false);
    }

    @Override
    public void tick() {
        if (settleTicks > 0) {
            settleTicks--;
            mob.getNavigation().stop();
            mob.getMoveControl().strafe(0.0F, 0.0F);
            return;
        }
        settleTicks = mob.tryEscapeHazard();
    }
}
