package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.ai.SakuraSpiritTeleportGoal;

public class SakuraSpiritMeleeGoal extends MeleeAttackGoal {
    private final PathfinderMob spirit;

    public SakuraSpiritMeleeGoal(PathfinderMob mob) {
        super(mob, 1.0D, false);
        this.spirit = mob;
    }

    @Override
    public boolean canUse() {
        return !holdsAtDistance() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !holdsAtDistance() && super.canContinueToUse();
    }

    private boolean holdsAtDistance() {
        if (!SakuraSpiritConfig.NEAR_DEATH_TELEPORT.get()) {
            return false;
        }
        LivingEntity target = this.spirit.getTarget();
        return target != null && target.isAlive()
                && this.spirit.getHealth() < this.spirit.getMaxHealth() * SakuraSpiritConfig.lowHealthRatio();
    }
}
