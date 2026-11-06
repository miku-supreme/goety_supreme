package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;

public final class AbstractGreenSmithLookGoals {

    private AbstractGreenSmithLookGoals() {
    }

    public static final class CastGatedStrollGoal extends WaterAvoidingRandomStrollGoal {
        private final AbstractGreenSmith smith;

        public CastGatedStrollGoal(AbstractGreenSmith smith, double speed) {
            super(smith, speed);
            this.smith = smith;
        }

        @Override
        public boolean canUse() {
            return !this.smith.isCasting() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.smith.isCasting() && super.canContinueToUse();
        }
    }

    public static final class CastGatedLookAtPlayerGoal extends LookAtPlayerGoal {
        private final AbstractGreenSmith smith;

        public CastGatedLookAtPlayerGoal(AbstractGreenSmith smith) {
            super(smith, Player.class, 16.0F);
            this.smith = smith;
        }

        @Override
        public boolean canUse() {
            return !this.smith.isCasting() && super.canUse();
        }
    }

    public static final class CastGatedRandomLookGoal extends RandomLookAroundGoal {
        private final AbstractGreenSmith smith;

        public CastGatedRandomLookGoal(AbstractGreenSmith smith) {
            super(smith);
            this.smith = smith;
        }

        @Override
        public boolean canUse() {
            return !this.smith.isCasting() && super.canUse();
        }
    }
}
