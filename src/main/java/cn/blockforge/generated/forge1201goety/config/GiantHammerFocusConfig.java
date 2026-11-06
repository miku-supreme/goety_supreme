package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class GiantHammerFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue RANGE;
    public static ForgeConfigSpec.IntValue SPLIT_ANGLE;
    public static ForgeConfigSpec.DoubleValue WAVE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue WAVE_DAMAGE_VARIANCE;
    public static ForgeConfigSpec.DoubleValue POTENCY_DAMAGE_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("巨锤聚晶：地震波与突变生物（Mutant Monsters）巨人之锤完全同源。").push("giant_hammer_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 20, 0, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却时间（刻），默认与巨人之锤的 25 刻一致。")
                .defineInRange("cooldown", 25, 0, Integer.MAX_VALUE);
        RANGE = builder.comment("一道波的延伸距离（格），默认即原版巨人之锤的 8 格；"
                + "波仍从身前 1.5 格起步、每刻推进一格。")
                .defineInRange("range", 8.0D, 1.0D, Double.MAX_VALUE);
        SPLIT_ANGLE = builder.comment("地卜魔杖施法时地震波分裂为两道，左右两道各自偏离面朝方向的角度（度）；"
                + "装在别的魔杖上不分裂，只打出一道。")
                .defineInRange("split_angle", 22, 0, Integer.MAX_VALUE);
        WAVE_DAMAGE = builder.comment("地震波每一格对命中生物造成的基准伤害，默认 6 即巨人之锤原版下限；"
                + "注意这条对巨人之锤本体的地震波同样生效。")
                .defineInRange("wave_damage", 6.0D, 0.0D, Double.MAX_VALUE);
        WAVE_DAMAGE_VARIANCE = builder.comment("地震波伤害在其上额外浮动的随机上限：每格实际伤害 = wave_damage + 随机[0,"
                + "该值)，默认 2 即原版 6~8 的浮动。填 0 则固定为 wave_damage。")
                .defineInRange("wave_damage_variance", 2.0D, 0.0D, Double.MAX_VALUE);
        POTENCY_DAMAGE_BONUS = builder.comment("巨锤聚晶的强效附魔每一级为地震波每一格追加的伤害，默认 1"
                + "（同 Goety 本体波类法术的口径）。只作用于聚晶打出的波，巨人之锤本体的波不受影响。")
                .defineInRange("potency_damage_bonus", 1.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private GiantHammerFocusConfig() {
    }
}
