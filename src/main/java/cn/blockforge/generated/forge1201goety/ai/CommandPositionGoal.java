package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.api.entities.ally.IServant;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class CommandPositionGoal extends Goal {

    private static final int RECALC_INTERVAL = 10;

    private final PathfinderMob mob;
    private final IServant servant;
    private final double speed;
    private int timeToRecalcPath;

    public CommandPositionGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.servant = (IServant) Objects.requireNonNull(mob);
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() == null && this.servant.isCommanded() && !this.servant.isStaying();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath > 0) {
            return;
        }
        this.timeToRecalcPath = RECALC_INTERVAL;
        LivingEntity entity = this.servant.getCommandPosEntity();
        if (entity != null) {
            this.mob.getNavigation().moveTo(entity, this.speed);
            return;
        }
        BlockPos pos = this.servant.getCommandPos();
        if (pos != null) {
            this.mob.getNavigation().moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, this.speed);
        }
    }
}
