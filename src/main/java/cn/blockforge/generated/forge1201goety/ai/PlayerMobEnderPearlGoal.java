package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownEnderpearl;

public final class PlayerMobEnderPearlGoal extends Goal {

    private final PlayerMobServant mob;

    public PlayerMobEnderPearlGoal(PlayerMobServant mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.isEnderPearlEngagement();
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isEnderPearlEngagement();
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
        mob.setAggressive(false);
        mob.setCombatChaseSprint(false);
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        mob.throwEnderPearlAt(target);
    }
}
