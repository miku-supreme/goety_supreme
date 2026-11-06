package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class AnimatedIronFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue DURATION_BONUS_TICKS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("活化聚晶：召唤活化二轮盔甲仆从。"
                        + "普通魔杖 1 个；地卜魔杖 2 个；同一主人的同时在场上限读 servants.toml"
                        + " 的活化二轮盔甲一项（默认 8）。消耗 20、前摇 100 刻、"
                        + "冷却 400 刻、召唤压制 200 刻。")
                .push("animated_iron_focus");
        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 20。")
                .defineInRange("soul_cost", 20, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 100（5 秒）。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 400（20 秒）。")
                .defineInRange("cooldown", 400, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），"
                        + "默认 200（10 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 200, 0, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命（刻），默认 1200（60 秒）。")
                .defineInRange("lifespan_ticks", 1200, 1, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                .defineInRange("duration_bonus_ticks", 2400, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private AnimatedIronFocusConfig() {
    }
}
