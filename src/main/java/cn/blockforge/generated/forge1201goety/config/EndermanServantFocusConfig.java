package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class EndermanServantFocusConfig {

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
        builder.comment("勿视聚晶：一次召出末影人仆从（手持虚空魔杖时更多），潜行施法改为召集。"
                        + "数值照 Goety 本体看守聚晶：灵魂 20、前摇 40、冷却 300、召唤压制 120。")
                .push("enderman_servant_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 20。")
                .defineInRange("soul_cost", 20, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 40（2 秒）。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 300（15 秒）。")
                .defineInRange("cooldown", 300, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），默认 120（6 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 120, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的末影人仆从上限，默认 12。")
                .defineInRange("summon_limit", 12, 1, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命（刻），默认 2000（100 秒）。")
                .defineInRange("lifespan_ticks", 100 * 20, 1, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻），默认 2400（120 秒）。")
                .defineInRange("duration_bonus_ticks", 120 * 20, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("普通施法召唤数量的下限。")
                .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("普通施法召唤数量的上限（含）。")
                .defineInRange("summon_count_max", 1, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("手持虚空魔杖施法时召唤数量的下限。")
                .defineInRange("staff_summon_count_min", 2, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("手持虚空魔杖施法时召唤数量的上限（含）。")
                .defineInRange("staff_summon_count_max", 3, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private EndermanServantFocusConfig() {
    }
}
