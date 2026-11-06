package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;

public class SerponFlyingStrollGoal extends RandomStrollGoal {

    private final AbstractJerotesServant servant;

    public SerponFlyingStrollGoal(AbstractJerotesServant servant, double speed, int interval) {
        super(servant, speed, interval, false);
        this.servant = servant;
    }

    @Override
    public boolean canUse() {
        if (!servant.isNoGravity()) {
            return false;
        }
        if (servant.getTarget() != null || servant.isStaying() || servant.isCommanded()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public Vec3 getPosition() {
        RandomSource random = servant.getRandom();
        return new Vec3(
                servant.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F,
                servant.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F,
                servant.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F);
    }
}
