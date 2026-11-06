package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;

public abstract class ServantOwnerTargetGoal<T extends Summoned> extends TargetGoal {

    public final T servant;
    public LivingEntity owner;
    public LivingEntity target;
    public int timestamp;

    public ServantOwnerTargetGoal(T servant) {
        super(servant, false);
        this.servant = servant;
        setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean validTarget(LivingEntity candidate) {
        return owner != null && candidate != null && candidate.isAlive() && candidate != owner
                && !servant.isAlliedTo(candidate)
                && canAttack(candidate, TargetingConditions.DEFAULT);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity current = servant.getTarget();
        if (current == null) {
            current = this.targetMob;
        }
        if (current == null) {
            return false;
        }
        if (!servant.canAttack(current) || sameTeam(servant, current)) {
            return false;
        }
        double follow = getFollowDistance();
        if (servant.distanceToSqr(current) > follow * follow) {
            return false;
        }
        if (this.mustSee && !servant.getSensing().hasLineOfSight(current)) {
            return false;
        }
        servant.setTarget(current);
        return true;
    }

    private static boolean sameTeam(LivingEntity mob, LivingEntity other) {
        var mine = mob.getTeam();
        return mine != null && other.getTeam() == mine;
    }

    @Override
    public void stop() {
        target = null;
        super.stop();
    }

    public static final class HurtBy<S extends Summoned> extends ServantOwnerTargetGoal<S> {
        public HurtBy(S servant) {
            super(servant);
        }

        @Override
        public boolean canUse() {
            owner = servant.getTrueOwner();
            target = owner == null ? null : owner.getLastHurtByMob();
            return owner != null && owner.getLastHurtByMobTimestamp() != timestamp && validTarget(target);
        }

        @Override
        public void start() {
            servant.setTarget(target);
            timestamp = owner.getLastHurtByMobTimestamp();
            super.start();
        }
    }

    public static final class Hurt<S extends Summoned> extends ServantOwnerTargetGoal<S> {
        public Hurt(S servant) {
            super(servant);
        }

        @Override
        public boolean canUse() {
            owner = servant.getTrueOwner();
            target = owner == null ? null : owner.getLastHurtMob();
            return owner != null && owner.getLastHurtMobTimestamp() != timestamp && validTarget(target);
        }

        @Override
        public void start() {
            servant.setTarget(target);
            timestamp = owner.getLastHurtMobTimestamp();
            super.start();
        }
    }
}
