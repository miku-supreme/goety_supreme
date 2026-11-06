package cn.blockforge.generated.goetysupreme.ai;

import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;

public class JerotesAllyHelpGoal extends Goal {

    private static final double HELP_RADIUS = 32.0D;

    private static final int SEARCH_INTERVAL = 10;

    private final Mob mob;
    private LivingEntity attackTarget;

    public JerotesAllyHelpGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null) {
            return false;
        }
        if (SEARCH_INTERVAL > 1 && mob.getRandom().nextInt(SEARCH_INTERVAL) != 0) {
            return false;
        }
        List<LivingEntity> allies = mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(HELP_RADIUS), entity -> entity != mob && mob.isAlliedTo(entity));
        for (LivingEntity ally : allies) {
            LivingEntity attacker = ally.getLastHurtByMob();
            if (!isHelpable(attacker) && ally instanceof Mob allyMob) {
                attacker = allyMob.getTarget();
            }
            if (isHelpable(attacker)) {
                this.attackTarget = attacker;
                return true;
            }
        }
        return false;
    }

    private boolean isHelpable(LivingEntity candidate) {
        return candidate != null && candidate != mob && candidate.isAlive()
                && !mob.isAlliedTo(candidate) && mob.canAttack(candidate);
    }

    @Override
    public void start() {
        mob.setTarget(attackTarget);
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && !mob.isAlliedTo(target);
    }
}
