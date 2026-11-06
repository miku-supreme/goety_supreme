package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

final class OwnerTargetGoal extends TargetGoal {
    private final Scorchback servant;
    private LivingEntity owner;
    private LivingEntity candidate;
    private int timestamp;

    OwnerTargetGoal(Scorchback servant) {
        super(servant, false);
        this.servant = servant;
        this.setFlags(java.util.EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        this.owner = this.servant.getTrueOwner();
        if (this.owner == null) {
            return false;
        }
        int hurtMob = this.owner.getLastHurtMobTimestamp();
        int hurtBy = this.owner.getLastHurtByMobTimestamp();
        int newest = Math.max(hurtMob, hurtBy);
        if (newest == this.timestamp) {
            return false;
        }
        this.candidate = hurtMob >= hurtBy ? this.owner.getLastHurtMob() : this.owner.getLastHurtByMob();
        if (this.candidate == null || this.candidate == this.owner || !this.candidate.isAlive()
                || this.servant.isAlliedTo(this.candidate)
                || !this.canAttack(this.candidate, TargetingConditions.DEFAULT)) {
            return false;
        }
        this.timestamp = newest;
        return true;
    }

    @Override
    public void start() {
        this.servant.setTarget(this.candidate);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity current = this.servant.getTarget();
        if (current == null) {
            current = this.targetMob;
        }
        if (current == null || !this.servant.canAttack(current) || sameTeam(this.servant, current)) {
            return false;
        }
        if (this.servant.distanceToSqr(current) > Scorchback.OWNER_TARGET_LEASH * Scorchback.OWNER_TARGET_LEASH) {
            return false;
        }
        this.servant.setTarget(current);
        return true;
    }

    private static boolean sameTeam(LivingEntity mob, LivingEntity other) {
        var mine = mob.getTeam();
        return mine != null && other.getTeam() == mine;
    }
}
