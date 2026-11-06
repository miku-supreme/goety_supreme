package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;

public final class ExecutionDamage {

    private static final float EXECUTION_THRESHOLD = 1.0E7F;

    private ExecutionDamage() {
    }

    public static boolean isExecution(DamageSource source, float amount) {
        return source.is(DamageTypes.GENERIC_KILL) || amount >= EXECUTION_THRESHOLD;
    }
}
