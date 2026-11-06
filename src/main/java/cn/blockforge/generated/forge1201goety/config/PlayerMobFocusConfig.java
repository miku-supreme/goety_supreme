package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class PlayerMobFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;

    public static ForgeConfigSpec.IntValue COOLDOWN;

    public static ForgeConfigSpec.IntValue CAST_DURATION;

    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;

    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("幻影聚晶配置：能量消耗、冷却、前摇、召唤压制与召唤上限。",
                "仆从寿命固定为初始 66 秒，持久附魔每级额外增加 100 秒，不再提供配置项。")
                .push("player_mob_focus");
        SOUL_COST = builder.comment("每次施法召唤消耗的灵魂能量。")
                .defineInRange("soul_cost", 40, 0, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法完成后的冷却时间，单位为 tick；默认 6 秒（120 tick）。")
                .defineInRange("cooldown", 120, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇时间，单位为 tick；默认 2 秒（40 tick）。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的玩家生物幻影上限。")
                .defineInRange("summon_limit", 16, 1, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），"
                        + "对标小兽聚晶 100（5 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 100, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private PlayerMobFocusConfig() {
    }
}
