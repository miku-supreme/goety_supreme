package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class SnowballFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue THROW_INTERVAL;
    public static ForgeConfigSpec.DoubleValue RATE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_SPEED;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_DAMAGE_BASE;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_SPEED_BASE;
    public static ForgeConfigSpec.DoubleValue VELOCITY_BONUS_PER_LEVEL;
    public static ForgeConfigSpec.DoubleValue INACCURACY;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("雪球聚晶：持续施法期间不断掷出与雪傀儡仆从同款的雪球。").push("snowball_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 2, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("持续施法的最长时间（刻），按住右键期间每隔投掷间隔掷出一发。")
                .defineInRange("cast_duration", 300, 20, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法结束后的冷却时间（刻）。")
                .defineInRange("cooldown", 20, 0, Integer.MAX_VALUE);
        THROW_INTERVAL = builder.comment("持续施法中两发雪球之间的基准间隔（刻）；实际间隔 = 本值 ÷ rate_multiplier。")
                .defineInRange("throw_interval", 15, 1, Integer.MAX_VALUE);
        RATE_MULTIPLIER = builder.comment("射速倍率（全局生效，不分魔杖）：射击间隔为基准的四分之一即填 4。")
                .defineInRange("rate_multiplier", 4.0D, 1.0D, Double.MAX_VALUE);
        SNOWBALL_DAMAGE = builder.comment("霜冻魔杖施法时聚晶雪球的基础伤害（雪傀儡仆从仍为 0.5，聚晶略强一档；"
                + "强效附魔每级再 +0.25；装在别的魔杖上时改用 snowball_damage_base）。")
                .defineInRange("snowball_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        SNOWBALL_SPEED = builder.comment("霜冻魔杖施法时的雪球初速（雪傀儡仆从同款基准为 1.6，聚晶略提速；"
                + "装在别的魔杖上时改用 snowball_speed_base）。")
                .defineInRange("snowball_speed", 1.8D, 0.2D, Double.MAX_VALUE);
        SNOWBALL_DAMAGE_BASE = builder.comment("非霜冻魔杖施法时的雪球基础伤害（仆从同款 0.5）。")
                .defineInRange("snowball_damage_base", 0.5D, 0.0D, Double.MAX_VALUE);
        SNOWBALL_SPEED_BASE = builder.comment("非霜冻魔杖施法时的雪球初速（仆从同款基准 1.6）。")
                .defineInRange("snowball_speed_base", 1.6D, 0.2D, Double.MAX_VALUE);
        VELOCITY_BONUS_PER_LEVEL = builder.comment("高速附魔每一级为雪球增加的飞行速度。")
                .defineInRange("velocity_bonus_per_level", 0.2D, 0.0D, Double.MAX_VALUE);
        INACCURACY = builder.comment("瞄准敌人投掷时的角度散布（度），越小越精准。")
                .defineInRange("inaccuracy", 0.35D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private SnowballFocusConfig() {
    }
}
