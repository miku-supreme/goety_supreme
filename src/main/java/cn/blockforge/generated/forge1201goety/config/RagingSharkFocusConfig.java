package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.RagingSharkSummonSpell;

public final class RagingSharkFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("狂鲨聚晶：唯一作用就是召唤夺命五头鲨；数值对标猛击聚晶。",
                "手持深渊魔杖时有 50% 概率一次召唤两只。").push("raging_shark_focus");
        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻）。")
                .defineInRange("cast_duration", 20, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻）。")
                .defineInRange("cooldown", 600, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻）；0 关闭。")
                .defineInRange("summon_down_ticks", 150, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的夺命五头鲨上限。")
                .defineInRange("summon_limit", 8, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private RagingSharkFocusConfig() {
    }
}
