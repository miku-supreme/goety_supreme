package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class IronGuardFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;

    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue DURATION_BONUS_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("铁卫聚晶：一次召出铁傀儡仆从（手持地卜魔杖时更多），潜行施法改为召集。")
                .push("iron_guard_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 64, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 160（8 秒）。")
                .defineInRange("cast_duration", 160, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 960（48 秒）。")
                .defineInRange("cooldown", 960, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），"
                        + "对标腾跃聚晶 300（15 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 300, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的铁傀儡仆从上限（模具搭的同款一并计入），默认 4。")
                .defineInRange("summon_limit", 4, 1, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命（刻），默认 2000（100 秒）。")
                .defineInRange("lifespan_ticks", 100 * 20, 1, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻），默认 2400（120 秒）。")
                .defineInRange("duration_bonus_ticks", 120 * 20, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("普通施法召唤数量的下限。")
                .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("普通施法召唤数量的上限（含）。")
                .defineInRange("summon_count_max", 1, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("手持地卜魔杖施法时召唤数量的下限。")
                .defineInRange("staff_summon_count_min", 2, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("手持地卜魔杖施法时召唤数量的上限（含）。")
                .defineInRange("staff_summon_count_max", 2, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private IronGuardFocusConfig() {
    }
}
