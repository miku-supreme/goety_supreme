package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.mixin.GoetyGuardianAttackRhythmMixin;

public final class GuardianServantConfig {

    public static ForgeConfigSpec.BooleanValue BURST_ENABLED;
    public static ForgeConfigSpec.IntValue BURST_INTERVAL;
    public static ForgeConfigSpec.IntValue BURST_SHOTS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("守卫者仆从与远古守卫者仆从的激光连射节奏。").push("guardian_servant");

        BURST_ENABLED = builder.comment("是否启用激光连射：第一束后不再脱锁重蓄，目标有效期间自动连续射出光束。")
                .define("burst_enabled", true);
        BURST_INTERVAL = builder.comment("连射时相邻两束激光的间隔（刻，20 刻=1 秒）；仅影响第一束之后的续射，"
                        + "第一束仍按原版完整蓄力（普通 10+80 刻、远古 10+60 刻）。")
                .defineInRange("burst_interval", 10, 1, Integer.MAX_VALUE);
        BURST_SHOTS = builder.comment("一轮连射的光束总发数（含第一束）；打完该发数后脱锁、重新索敌并完整蓄力再来一轮。"
                        + "0 表示只要锁定有效就无限连射。")
                .defineInRange("burst_shots", 0, 0, Integer.MAX_VALUE);

        builder.pop();
    }

    private GuardianServantConfig() {
    }
}
