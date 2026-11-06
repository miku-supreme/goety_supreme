package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class FrostbornFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue LIFESPAN_RANDOM_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue STAFF_SUMMON_COUNT_MAX;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("寒裔聚晶：数值对齐诡厄巫法本体的霜裔聚晶（一次召 1 只冰傀儡，"
                        + "手持霜冻魔杖时 50% 概率多召 1 只；周身上限 4；灵魂 24；冷却 60 秒）。")
                .push("frostborn_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 24（=霜裔聚晶）。")
                .defineInRange("soul_cost", 24, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 20（1 秒，=霜裔聚晶）。")
                .defineInRange("cast_duration", 20, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 1200（60 秒，=霜裔聚晶）。")
                .defineInRange("cooldown", 1200, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("玩家周身可存在的冰傀儡仆从上限，默认 4"
                        + "（本体霜裔聚晶为 2，按需求放宽；模具搭建的冰傀儡仆从同样计入）。")
                .defineInRange("summon_limit", 4, 1, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder
                .comment("连发压制（Summon Down）持续时间（刻），默认 300（=霜裔聚晶）；0 关闭。")
                .defineInRange("summon_down_ticks", 300, 0, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("仆从寿命基数（刻），默认 600（30 秒，=霜裔聚晶下限）。")
                .defineInRange("lifespan_ticks", 30 * 20, 1, Integer.MAX_VALUE);
        LIFESPAN_RANDOM_TICKS = builder
                .comment("每只在基数上再随机加 0~此值的寿命（刻），默认 1800，即单只 30~120 秒随机（=霜裔聚晶）。")
                .defineInRange("lifespan_random_ticks", 90 * 20, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("普通施法召唤数量的下限（霜裔聚晶口径：固定 1）。")
                .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("普通施法召唤数量的上限（含，霜裔聚晶口径：固定 1）。")
                .defineInRange("summon_count_max", 1, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MIN = builder.comment("手持霜冻魔杖施法时召唤数量的下限（50% 概率多召 1 只）。")
                .defineInRange("staff_summon_count_min", 1, 1, Integer.MAX_VALUE);
        STAFF_SUMMON_COUNT_MAX = builder.comment("手持霜冻魔杖施法时召唤数量的上限（含）。")
                .defineInRange("staff_summon_count_max", 2, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private FrostbornFocusConfig() {
    }
}
