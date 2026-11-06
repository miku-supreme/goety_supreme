package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.util.ScorchbackCombatMath;
import cn.blockforge.generated.goetysupreme.util.ScorchbackRainSockets;

public final class ScorchbackRainSockets {
    public static final int FIRST_TICK = ScorchbackCombatMath.RAIN_FIRST_TICK;
    public static final int LAST_TICK = ScorchbackCombatMath.RAIN_LAST_TICK;
    public static final int INTERVAL = ScorchbackCombatMath.RAIN_INTERVAL;
    private static final double[][] SOCKETS = {
        {-0.6740155D, 2.3602761D, -0.9662676D}, // 14 刻，第 1 根，front_l_body_spike
        {0.5755787D, 2.3363721D, -0.9873475D}, // 14 刻，第 2 根，front_r_body_spike
        {-0.7359112D, 2.3993444D, -0.0903659D}, // 19 刻，第 1 根，back_l_body_spike
        {0.5138931D, 2.4214391D, -0.0902073D}, // 19 刻，第 2 根，back_r_body_spike
        {0.0532485D, 2.2342968D, -0.6220256D}, // 24 刻，第 1 根，front_body_spike
        {-0.5611593D, 2.3969079D, -0.9677107D}, // 24 刻，第 2 根，front_l_body_spike
        {0.0126367D, 2.4548447D, -0.9671252D}, // 29 刻，第 1 根，front_r_body_spike
        {-1.2050493D, 2.1655301D, -0.1164919D}, // 29 刻，第 2 根，back_l_body_spike
        {0.2970776D, 2.4768024D, -0.1162202D}, // 34 刻，第 1 根，back_r_body_spike
        {-0.2997567D, 2.2216541D, -0.6216368D}, // 34 刻，第 2 根，front_body_spike
        {-0.5606521D, 2.3916435D, -0.9674392D}, // 39 刻，第 1 根，front_l_body_spike
        {0.6858406D, 2.2980647D, -0.9670155D}, // 39 刻，第 2 根，front_r_body_spike
        {-0.5231469D, 2.4578659D, -0.1166200D}, // 44 刻，第 1 根，back_l_body_spike
        {0.7215962D, 2.3433542D, -0.1161396D}, // 44 刻，第 2 根，back_r_body_spike
        {0.0274294D, 2.2334773D, -0.5889908D}, // 49 刻，第 1 根，front_body_spike
        {-0.5892358D, 2.3923313D, -0.9323958D}, // 49 刻，第 2 根，front_l_body_spike
        {0.5591246D, 2.3640148D, -0.9293129D}, // 54 刻，第 1 根，front_r_body_spike
        {-0.6906316D, 2.4149774D, -0.0775199D}, // 54 刻，第 2 根，back_l_body_spike
        {0.5326452D, 2.4221633D, -0.0744380D}, // 59 刻，第 1 根，back_r_body_spike
        {-0.0914147D, 2.2543127D, -0.5834894D}, // 59 刻，第 2 根，front_body_spike
    };

    public static Vec3 position(Scorchback entity, int volley, int shot) {
        double[] p = SOCKETS[Mth.clamp(volley * 2 + shot, 0, SOCKETS.length - 1)];
        double angle = entity.getActionYaw() * Mth.DEG_TO_RAD;
        double c = Math.cos(angle), s = Math.sin(angle);
        double size = entity.isBaby() ? 0.5D : 1.0D;
        return entity.position().add((p[0] * c - p[2] * s) * size, p[1] * size,
                (p[0] * s + p[2] * c) * size);
    }

    private ScorchbackRainSockets() { }
}
