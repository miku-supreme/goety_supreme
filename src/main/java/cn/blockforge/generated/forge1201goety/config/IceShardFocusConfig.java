package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class IceShardFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue ICE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue ICE_DAMAGE_MAX;
    public static ForgeConfigSpec.DoubleValue POTENCY_BONUS;
    public static ForgeConfigSpec.DoubleValue FROST_STAFF_DAMAGE_BONUS;
    public static ForgeConfigSpec.DoubleValue THROW_SPEED;
    public static ForgeConfigSpec.DoubleValue AIM_MAX_SPEED;
    public static ForgeConfigSpec.DoubleValue THROW_SPEED_BASE;
    public static ForgeConfigSpec.DoubleValue AIM_MAX_SPEED_BASE;
    public static ForgeConfigSpec.DoubleValue INACCURACY;
    public static ForgeConfigSpec.IntValue EFFECT_DURATION;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("碎冰聚晶：瞬发掷出与突变雪傀儡同款的冰块。").push("ice_shard_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 20, 0, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却时间（刻）。")
                .defineInRange("cooldown", 20, 0, Integer.MAX_VALUE);
        ICE_DAMAGE = builder.comment("冰块基础伤害的下限（正中与溅射相同）。实际伤害在 ice_damage ~ ice_damage_max 间随机。")
                .defineInRange("ice_damage", 4.0D, 0.0D, Double.MAX_VALUE);
        ICE_DAMAGE_MAX = builder.comment("冰块基础伤害的上限。默认 4~6，对标突变雪傀儡的冰块。")
                .defineInRange("ice_damage_max", 6.0D, 0.0D, Double.MAX_VALUE);
        POTENCY_BONUS = builder.comment("强效附魔每一级为冰块增加的额外伤害。")
                .defineInRange("potency_bonus", 1.0D, 0.0D, Double.MAX_VALUE);
        FROST_STAFF_DAMAGE_BONUS = builder.comment("霜冻魔杖施法时，冰块额外增加的溅射与直接伤害。")
                .defineInRange("frost_staff_damage_bonus", 2.0D, 0.0D, Double.MAX_VALUE);
        THROW_SPEED = builder.comment("霜冻魔杖施法时的冰块初速（无目标直射出手速度，突变雪傀儡同款基准 0.9，聚晶已提速；"
                + "装在别的魔杖上时改用 throw_speed_base）。")
                .defineInRange("throw_speed", 1.5D, 0.2D, Double.MAX_VALUE);
        AIM_MAX_SPEED = builder.comment("霜冻魔杖施法时瞄准敌人弹道解算允许的最大初速（越快越平；"
                + "装在别的魔杖上时改用 aim_max_speed_base 并回退为省力抛物线）。")
                .defineInRange("aim_max_speed", 2.2D, 0.5D, Double.MAX_VALUE);
        THROW_SPEED_BASE = builder.comment("非霜冻魔杖施法时的冰块直射初速（突变雪傀儡同款 0.9）。")
                .defineInRange("throw_speed_base", 0.9D, 0.2D, Double.MAX_VALUE);
        AIM_MAX_SPEED_BASE = builder.comment("非霜冻魔杖施法时瞄准弹道解算的最大初速（提速前旧值 1.3，走省力抛物线）。")
                .defineInRange("aim_max_speed_base", 1.3D, 0.5D, Double.MAX_VALUE);
        INACCURACY = builder.comment("瞄准敌人投掷时的角度散布（度）。")
                .defineInRange("inaccuracy", 0.35D, 0.0D, Double.MAX_VALUE);
        EFFECT_DURATION = builder.comment("命中给予缓慢 / 冰冻（施法者穿冰霜套时）的持续刻数。")
                .defineInRange("effect_duration", 100, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private IceShardFocusConfig() {
    }
}
