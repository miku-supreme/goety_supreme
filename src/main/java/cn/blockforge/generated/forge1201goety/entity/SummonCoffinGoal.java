package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;

public final class SummonCoffinGoal extends Goal {

    private final AbstractSoulReaper mob;

    SummonCoffinGoal(AbstractSoulReaper mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        AbstractSoulReaper mob = this.mob;
        return !mob.isCasting() && mob.getTarget() != null && mob.getTarget().isAlive()
                && !mob.hasCoffin();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        this.mob.startCast(AbstractSoulReaper.CAST_SUMMON_COFFIN);
    }
}
