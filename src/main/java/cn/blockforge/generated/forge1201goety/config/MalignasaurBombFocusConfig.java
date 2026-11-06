package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;

public final class MalignasaurBombFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("恶鳞炸弹聚晶：照宝石恶鳞的恶鳞炸弹，在自身周围 12 格内铺下 48~64 颗水雷，"
                        + "踩上去的敌人会被炸成一片气泡。")
                .push("malignasaur_bomb_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量；对标 abyssal_mine_focus 的 soul_cost（16）。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻）；对标 abyssal_mine_focus 的 cast_duration（70，3.5 秒）。")
                .defineInRange("cast_duration", 70, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻）；对标 abyssal_mine_focus 的 cooldown（800，40 秒）。")
                .defineInRange("cooldown", 800, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级，默认 4（照 J 村 spell/OtherSpellList.java:1714-1716 的 "
                        + "MalignasaurBomb#getBaseSpellLevel()，魔法卷轴显示「4环」）；"
                        + "实际等级 = 基准 + 强效附魔 + 深渊系魔杖加成。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在深渊系魔杖（goety:abyss_staff，判定同 Goety 的 rightStaff）里施法时"
                        + "额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private MalignasaurBombFocusConfig() {
    }
}
