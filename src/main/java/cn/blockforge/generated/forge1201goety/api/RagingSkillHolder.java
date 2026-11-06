package cn.blockforge.generated.goetysupreme.api;

import cn.blockforge.generated.goetysupreme.util.RagingArmAim;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.mixin.GoetyRedstoneMonstrosityRagingMixin;

public interface RagingSkillHolder {

    int RAGING_SKILL_SMASH = 1;

    int RAGING_SKILL_SLAM = 2;

    int RAGING_SKILL_ROAR = 3;

    int RAGING_SKILL_SPRAY = 4;

    int RAGING_SKILL_CHARGE = 5;

    int RAGING_SKILL_SUMMON = 6;

    int RAGING_SKILL_ANTIAIR = 7;

    int RAGING_SKILL_WIPE = 8;

    int WIPE_GRAB_TICK = 12;

    int WIPE_TEAR_FROM = 16;

    int WIPE_TEAR_TO = 34;

    int[] WIPE_DAMAGE_TICKS = {18, 24, 30, 34};

    int WIPE_THROW_TICK = 39;

    double WIPE_LIFT_HEIGHT = 1.3D;

    public static double wipeHoldHeight(int tick) {
        if (tick <= WIPE_TEAR_TO) {
            return 0.35D;
        }
        if (tick >= WIPE_THROW_TICK) {
            return WIPE_LIFT_HEIGHT;
        }
        return 0.35D + (tick - WIPE_TEAR_TO)
                * ((WIPE_LIFT_HEIGHT - 0.35D) / (WIPE_THROW_TICK - WIPE_TEAR_TO));
    }

    int getRagingSkillId();

    int getRagingSkillTick();

    int getRagingGrabId();

    int getRagingAimTargetId();
}
