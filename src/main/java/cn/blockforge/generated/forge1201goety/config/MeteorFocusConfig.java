package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MeteorFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;

    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static ForgeConfigSpec.DoubleValue SUMMON_DISTANCE;

    public static ForgeConfigSpec.DoubleValue AIM_RANGE;

    public static ForgeConfigSpec.DoubleValue RADIUS_BONUS_PER_LEVEL;

    public static ForgeConfigSpec.DoubleValue RANGE_BONUS_PER_LEVEL;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("陨石聚晶：照蛇龙执战者的陨石，在准星落点周围召唤陨石云砸下。"
                        + "伤害基准为 1（= 基准 × 技能等级）。")
                .push("meteor_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 12。")
                .defineInRange("soul_cost", 32, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 25。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 100（5 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级（伤害基准为 1，默认 1）。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每朵陨石的伤害基准（为 1）；"
                        + "实际伤害 = 基准 × 本次技能等级。")
                .defineInRange("base_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在下界系魔杖（判定同 Goety 的 rightStaff）"
                        + "里施法时额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        SUMMON_DISTANCE = builder.comment("陨石落点在准星落点周围的散布半径（格），默认 6。")
                .defineInRange("summon_distance", 6.0D, 0.0D, Double.MAX_VALUE);
        AIM_RANGE = builder.comment("准星落点最远取到施法者视线上的多少格，默认 20。")
                .defineInRange("aim_range", 20.0D, 4.0D, Double.MAX_VALUE);
        RADIUS_BONUS_PER_LEVEL = builder.comment("「半径」附魔每级给陨石爆炸半径加的格数，"
                        + "每级 0.5 格，默认 0.5。")
                .defineInRange("radius_bonus_per_level", 0.5D, 0.0D, Double.MAX_VALUE);
        RANGE_BONUS_PER_LEVEL = builder.comment("「范围」附魔每级给准星施法距离（aim_range）加的格数，"
                        + "默认 1.0。")
                .defineInRange("range_bonus_per_level", 1.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private MeteorFocusConfig() {
    }
}
