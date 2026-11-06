package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class PikeRainFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;

    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static ForgeConfigSpec.IntValue PIKES_PER_LEVEL;

    public static ForgeConfigSpec.DoubleValue SPREAD;

    public static ForgeConfigSpec.DoubleValue AIM_RANGE;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("刺枪雨聚晶：照蛇龙执战者的刺枪雨，在准星落点上方落下蛇龙标枪。"
                        + "伤害基准为 1（= 基准 × 技能等级）。")
                .push("pike_rain_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 32。")
                .defineInRange("soul_cost", 32, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 100（5 秒）。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 200（10 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级（伤害基准为 1，默认 1）。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每根标枪的伤害基准（为 1）；"
                        + "实际伤害 = 基准 × 本次技能等级。")
                .defineInRange("base_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在下界系魔杖（判定同 Goety 的 rightStaff）"
                        + "里施法时额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        PIKES_PER_LEVEL = builder.comment("每点技能等级落下的标枪根数，默认 4。")
                .defineInRange("pikes_per_level", 4, 1, Integer.MAX_VALUE);
        SPREAD = builder.comment("标枪落点在准星落点周围的散布半径（格），默认 4。")
                .defineInRange("spread", 4.0D, 0.0D, Double.MAX_VALUE);
        AIM_RANGE = builder.comment("准星落点最远取到施法者视线上的多少格，默认 16。")
                .defineInRange("aim_range", 16.0D, 4.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private PikeRainFocusConfig() {
    }
}
