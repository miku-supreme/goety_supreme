package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class FrostSnowFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;

    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue LIFESPAN_RANDOM_TICKS;
    public static ForgeConfigSpec.IntValue DURATION_BONUS_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("霜雪聚晶：一次召出数只雪傀儡仆从（手持霜冻魔杖时更多），潜行施法改为召集。")
                .push("frost_snow_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 8, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 20（1 秒）。")
                .defineInRange("cast_duration", 20, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 60（3 秒）。")
                .defineInRange("cooldown", 60, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），默认 60（3 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 60, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的雪傀儡仆从上限（含其他来源），默认 32。")
                .defineInRange("summon_limit", 32, 1, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命基数（刻），默认 600（30 秒，对齐寒裔聚晶下限）。")
                .defineInRange("lifespan_ticks", 30 * 20, 1, Integer.MAX_VALUE);
        LIFESPAN_RANDOM_TICKS = builder.comment("每只雪傀儡在寿命基数上额外随机增加的刻数，"
                + "默认 1800（90 秒），即单只寿命 30~120 秒随机（对齐寒裔聚晶）。")
                .defineInRange("lifespan_random_ticks", 90 * 20, 0, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻），默认 2000（100 秒）。")
                .defineInRange("duration_bonus_ticks", 100 * 20, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("普通施法召唤数量的下限。")
                .defineInRange("summon_count_min", 2, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("普通施法召唤数量的上限（含）。")
                .defineInRange("summon_count_max", 3, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("手持霜冻魔杖施法时召唤数量的下限。")
                .defineInRange("staff_summon_count_min", 4, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("手持霜冻魔杖施法时召唤数量的上限（含）。")
                .defineInRange("staff_summon_count_max", 6, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private FrostSnowFocusConfig() {
    }
}
