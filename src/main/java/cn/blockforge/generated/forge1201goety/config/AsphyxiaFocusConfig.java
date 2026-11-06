package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;

public final class AsphyxiaFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("窒息聚晶：照宝石恶鳞的窒息控制，在自身周围降下窒息效果，"
                        + "令范围内不靠水呼吸的敌人持续吃溺水伤害。")
                .push("asphyxia_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量；对标 weakening_focus 的 soul_cost（100）。")
                .defineInRange("soul_cost", 100, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻）；对标 weakening_focus 的 cast_duration（0，即时施法）。")
                .defineInRange("cast_duration", 0, 0, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻）；对标 weakening_focus 的 cooldown（400，20 秒）。")
                .defineInRange("cooldown", 400, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级，默认 3（照 J 村 spell/OtherSpellList.java:986-988 的 "
                        + "AsphyxiaControl#getBaseSpellLevel()，魔法卷轴显示「3环」）；"
                        + "实际等级 = 基准 + 强效附魔 + 深渊系魔杖加成。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在深渊系魔杖（goety:abyss_staff，判定同 Goety 的 rightStaff）里施法时"
                        + "额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private AsphyxiaFocusConfig() {
    }
}
