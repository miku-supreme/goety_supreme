package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.config.HotWindFocusConfig;

public final class WhirlpoolFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("漩涡聚晶：照宝石恶鳞的漩涡，朝视线发吸水漩涡弹，命中结算溺水伤害并把目标拉扯向施法者。")
                .push("whirlpool_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量；对标 hot_wind_focus 的 soul_cost（32）。")
                .defineInRange("soul_cost", 32, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻）；对标 hot_wind_focus 的 cast_duration（100，5 秒）。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻）；对标 hot_wind_focus 的 cooldown（200，10 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级，默认 1（J 村 spell/OtherSpellList.java:338-340 的 "
                        + "Whirlpool#getBaseSpellLevel() 是 3，本聚晶取 1）；"
                        + "实际等级 = 基准 + 强效附魔 + 深渊系魔杖加成。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在深渊系魔杖（goety:abyss_staff，判定同 Goety 的 rightStaff）里施法时"
                        + "额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private WhirlpoolFocusConfig() {
    }
}
