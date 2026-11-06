package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.WarPythonSummonSpell;

public final class WarPythonFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue ROYAL_SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue LIFESPAN_TICKS;
    public static ForgeConfigSpec.IntValue DURATION_BONUS_TICKS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("战蟒聚晶：召唤战蟒 / 皇家战蟒。普通魔杖 1 只；下界魔杖 2 只；"
                        + "荒野魔杖 2~3 只；下界魔杖 + 下界套 / 不洁套则是 2 只皇家战蟒。"
                        + "数值对标猛击聚晶：消耗 16、前摇 20 刻、冷却 600 刻、召唤压制 150 刻。")
                .push("war_python_focus");
        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，对标猛击聚晶：16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），对标猛击聚晶：20。")
                .defineInRange("cast_duration", 20, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），对标猛击聚晶：600（30 秒）。")
                .defineInRange("cooldown", 600, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），"
                        + "对标猛击聚晶：150；0 关闭。")
                .defineInRange("summon_down_ticks", 150, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的战蟒（含皇家战蟒）上限，默认 8。")
                .defineInRange("summon_limit", 8, 1, Integer.MAX_VALUE);
        ROYAL_SUMMON_LIMIT = builder.comment("同一主人同时存在的皇家战蟒上限，默认 4；"
                        + "皇家战蟒同时占用上面的战蟒总名额。")
                .defineInRange("royal_summon_limit", 4, 1, Integer.MAX_VALUE);
        LIFESPAN_TICKS = builder.comment("普通召唤的仆从寿命（刻），默认 1200（60 秒）。")
                .defineInRange("lifespan_ticks", 1200, 1, Integer.MAX_VALUE);
        DURATION_BONUS_TICKS = builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                .defineInRange("duration_bonus_ticks", 2400, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private WarPythonFocusConfig() {
    }
}
