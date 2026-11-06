package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BitterColdWindFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;

    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue DURATION_BONUS_TICKS;

    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;

    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("寒风聚晶：冰霜仪式产物，召唤一只苦寒风（爬墙、群伤旋风、命中冻 220 刻）；"
                + "数值全部对标 Goety 的收割聚晶。")
                .push("bitter_cold_wind_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量；照收割聚晶 24。")
                .defineInRange("soul_cost", 24, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻）；照收割聚晶 60（3 秒）。")
                .defineInRange("cast_duration", 60, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻）；照收割聚晶 100（5 秒）。")
                .defineInRange("cooldown", 100, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻）；照收割聚晶 300（15 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 300, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的苦寒风上限；照收割聚晶 8。")
                .defineInRange("summon_limit", 8, 1, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命（刻），默认 2000（100 秒）。")
                .defineInRange("lifespan_ticks", 100 * 20, 1, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻），默认 2400（120 秒）。")
                .defineInRange("duration_bonus_ticks", 120 * 20, 0, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment(
                "手持冰霜魔杖（Goety rightStaff，即霜冻魔杖一类）施法时一次召唤的苦寒风数量下限；2。")
                .defineInRange("staff_summon_count_min", 2, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment(
                "手持冰霜魔杖（Goety rightStaff，即霜冻魔杖一类）施法时一次召唤的苦寒风数量上限；4。")
                .defineInRange("staff_summon_count_max", 4, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private BitterColdWindFocusConfig() {
    }
}
