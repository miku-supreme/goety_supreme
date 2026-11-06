package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.api.entities.ally.IServant;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public final class ServantWanderGoal extends RandomStrollGoal {
    private final PathfinderMob mob;
    private final IServant servant;
    private final float probability;

    private final BooleanSupplier allowed;

    public ServantWanderGoal(PathfinderMob mob, double speed, float probability) {
        this(mob, speed, probability, () -> true);
    }

    public ServantWanderGoal(PathfinderMob mob, double speed, float probability, BooleanSupplier allowed) {
        super(mob, speed, 120, false);
        this.mob = mob;
        this.servant = (IServant) Objects.requireNonNull(mob);
        this.probability = probability;
        this.allowed = Objects.requireNonNull(allowed);
    }

    @Override
    public boolean canUse() {
        if (!allowed.getAsBoolean() || mob.getTarget() != null || !super.canUse()) {
            return false;
        }
        if (servant.isStaying() || servant.isCommanded()) {
            return servant.getTrueOwner() == null;
        }
        return !(mob.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation);
    }

    @Override
    public boolean canContinueToUse() {
        return allowed.getAsBoolean() && super.canContinueToUse();
    }

    @Override
    @Nullable
    public Vec3 getPosition() {
        if (servant.isGuardingArea() && servant.getBoundPos() != null) {
            BlockPos bound = servant.getBoundPos();
            if (mob.distanceToSqr(Vec3.atBottomCenterOf(bound)) > (double) IServant.GUARDING_RANGE * IServant.GUARDING_RANGE) {
                return Vec3.atBottomCenterOf(bound);
            }
            BlockPos guardPos = bound.offset(
                    mob.getRandom().nextIntBetweenInclusive(-10, 10),
                    mob.getRandom().nextIntBetweenInclusive(-7, 7),
                    mob.getRandom().nextIntBetweenInclusive(-10, 10));
            BlockPos landPos = LandRandomPos.movePosUpOutOfSolid(mob, guardPos);
            return landPos == null ? Vec3.atBottomCenterOf(bound) : Vec3.atBottomCenterOf(landPos);
        }

        if (servant.getTrueOwner() != null && servant.isFollowing()) {
            BlockPos ownerPos = servant.getTrueOwner().blockPosition().offset(
                    mob.getRandom().nextIntBetweenInclusive(-10, 10),
                    mob.getRandom().nextIntBetweenInclusive(-7, 7),
                    mob.getRandom().nextIntBetweenInclusive(-10, 10));
            BlockPos landPos = LandRandomPos.movePosUpOutOfSolid(mob, ownerPos);
            if (landPos != null) {
                return Vec3.atBottomCenterOf(landPos);
            }
        }

        return mob.getRandom().nextFloat() >= probability
                ? LandRandomPos.getPos(mob, 10, 7)
                : super.getPosition();
    }
}
