package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class SnowGolemServantConfig {

    public static ForgeConfigSpec.DoubleValue SNOWBALL_BASE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_POTENCY_BONUS;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_SPREAD;
    public static ForgeConfigSpec.DoubleValue SNOWBALL_SPREAD_FROST_SET;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("雪傀儡仆从的雪球伤害与精准度配置。").push("snow_golem_servant");

        SNOWBALL_BASE_DAMAGE = builder.comment("雪球命中的基础伤害（强效为 0 时；烈焰人仍按 3 倍承受）。")
                .defineInRange("snowball_base_damage", 0.5D, 0.0D, Double.MAX_VALUE);
        SNOWBALL_POTENCY_BONUS = builder.comment("霜雪聚晶强效附魔每一级为仆从雪球增加的额外伤害。")
                .defineInRange("snowball_potency_bonus", 0.25D, 0.0D, Double.MAX_VALUE);
        SNOWBALL_SPREAD = builder.comment("普通投掷的角度散布（度，原版雪傀儡为 12）。")
                .defineInRange("snowball_spread", 12.0D, 0.0D, Double.MAX_VALUE);
        SNOWBALL_SPREAD_FROST_SET = builder.comment("主人穿戴冰霜套时的投掷角度散布（度），越小越精准。")
                .defineInRange("snowball_spread_frost_set", 4.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private SnowGolemServantConfig() {
    }
}
