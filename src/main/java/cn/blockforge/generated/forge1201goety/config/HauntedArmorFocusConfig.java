package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.HauntedArmorSummonSpell;

public final class HauntedArmorFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;

    public static ForgeConfigSpec.IntValue CAST_DURATION;

    public static ForgeConfigSpec.IntValue COOLDOWN;

    public static ForgeConfigSpec.IntValue SUMMON_DOWN_TICKS;

    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("胄行聚晶：召唤 Goety 本体的缠魂仆胄（出生自带诅咒骑士一套 + 堕落之刃 + 盾牌）。"
                        + "数值、音效、召唤量全部对标本体先锋聚晶（VanguardSpell）：灵魂 24 / 前摇 180 / 冷却 100 / "
                        + "压制 300 / 上限 16；普通杖 1、死灵杖 2~7、无名杖 7 只成排。"
                        + "本题材不依赖 J 村，所以留在 focus.toml 顶层，不放进 jerotes_village 大节。"
                        + "修改后重启游戏生效。")
                .push("haunted_armor_focus");
        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，对标先锋聚晶，默认 24。")
                .defineInRange("soul_cost", 24, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），对标先锋聚晶，默认 180（9 秒）。")
                .defineInRange("cast_duration", 180, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），对标先锋聚晶，默认 100（5 秒）。")
                .defineInRange("cooldown", 100, 0, Integer.MAX_VALUE);
        SUMMON_DOWN_TICKS = builder.comment("召唤压制（Summon Down）持续时间（刻），对标先锋聚晶，默认 300（15 秒）；0 关闭。")
                .defineInRange("summon_down_ticks", 300, 0, Integer.MAX_VALUE);
        SUMMON_LIMIT = builder.comment("同一主人同时存在的缠魂仆胄上限，对标先锋聚晶，默认 16。")
                .defineInRange("summon_limit", 16, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    private HauntedArmorFocusConfig() {
    }
}
