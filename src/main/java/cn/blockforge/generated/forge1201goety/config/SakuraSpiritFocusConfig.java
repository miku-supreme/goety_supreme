package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;

public final class SakuraSpiritFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_DURATION;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("樱之灵聚晶：一次召出数只樱之灵仆从（拿对法杖时更多），潜行施法改为召回。")
                .push("sakura_guardian_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 80, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 78。")
                .defineInRange("cast_duration", 78, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 200（10 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_DURATION = builder.comment("召唤后进入召唤衰弱（Summon Down）的持续刻数。")
                .defineInRange("summon_down_duration", 600, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("普通施法召唤数量的下限。")
                .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("普通施法召唤数量的上限（含）。")
                .defineInRange("summon_count_max", 2, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("拿对法杖（rightStaff）施法时召唤数量的下限。")
                .defineInRange("staff_summon_count_min", 3, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("拿对法杖（rightStaff）施法时召唤数量的上限（含）。")
                .defineInRange("staff_summon_count_max", 4, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private SakuraSpiritFocusConfig() {
    }
}
