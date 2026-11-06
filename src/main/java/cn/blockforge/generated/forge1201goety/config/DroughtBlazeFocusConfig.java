package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DroughtBlazeFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("焦炎聚晶：换色版的燃烧聚晶，召唤 J 村的旱火烈焰人。"
                        + "默认数值对齐本体燃烧聚晶（普通施法召 1 只、手持本系下界杖召 2~5 只；"
                        + "周身上限 16；灵魂 16；冷却 10 秒）。")
                .push("drought_blaze_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16（=燃烧聚晶）。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 100（5 秒，=燃烧聚晶）。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 200（10 秒，=燃烧聚晶）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("玩家周身可存在的旱火烈焰人上限，默认 16（=燃烧聚晶的 blazeLimit）。")
                .defineInRange("summon_limit", 16, 1, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder
                .comment("连发压制（Summon Down）持续时间（刻），默认 400（=燃烧聚晶）；0 关闭。")
                .defineInRange("summon_down_ticks", 400, 0, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder
                .comment("仆从寿命（刻），默认 1200（60 秒，与其它 J 村仆从同一口径）；"
                        + "持久附魔按等级乘算。")
                .defineInRange("lifespan_ticks", 60 * 20, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("施法召唤数量的下限（燃烧聚晶口径：固定 1）。")
                .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("施法召唤数量的上限（含，燃烧聚晶口径：固定 1）。")
                .defineInRange("summon_count_max", 1, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("手持本系下界杖施法时召唤数量的下限，默认 2（=燃烧聚晶）。")
                .defineInRange("staff_summon_count_min", 2, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("手持本系下界杖施法时召唤数量的上限（含），默认 5"
                        + "（燃烧聚晶 rightStaff 分支是 2 + rand(4)，即 2~5）。")
                .defineInRange("staff_summon_count_max", 5, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private DroughtBlazeFocusConfig() {
    }
}
