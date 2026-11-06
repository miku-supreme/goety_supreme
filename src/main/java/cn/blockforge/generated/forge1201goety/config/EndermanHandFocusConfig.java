package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class EndermanHandFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.DoubleValue PICK_RANGE;
    public static ForgeConfigSpec.IntValue TELEPORT_COOLDOWN;
    public static ForgeConfigSpec.DoubleValue TELEPORT_RANGE;
    public static ForgeConfigSpec.IntValue VOID_ECLIPSE_DURATION;
    public static ForgeConfigSpec.DoubleValue BLOCK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BLOCK_AREA_DAMAGE;
    public static ForgeConfigSpec.DoubleValue POTENCY_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("影移聚晶：右键举块/投掷（无冷却），蹲下右键瞬移到准心所在位置。").push("enderman_hand_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量。")
                .defineInRange("soul_cost", 1, 0, Integer.MAX_VALUE);
        PICK_RANGE = builder.comment("举起方块的最大距离（格）。")
                .defineInRange("pick_range", 6.0D, 1.0D, Double.MAX_VALUE);
        TELEPORT_COOLDOWN = builder.comment("瞬移的冷却时间（刻）。")
                .defineInRange("teleport_cooldown", 40, 0, Integer.MAX_VALUE);
        TELEPORT_RANGE = builder.comment("瞬移的最大距离（格）。无距离限制：32 区块 = 512 格，"
                        + "对已加载区块内任意可见落点都能命中。")
                .defineInRange("teleport_range", 512.0D, 4.0D, Double.MAX_VALUE);
        VOID_ECLIPSE_DURATION = builder.comment("投掷的方块命中时附加虚空之蚀（Goety）的持续刻数，0 为不附加。")
                .defineInRange("void_eclipse_duration", 100, 0, Integer.MAX_VALUE);
        BLOCK_DAMAGE = builder.comment("举起方块掷中目标的直接伤害（原版突变末影人投掷为 10；"
                + "强效附魔加成单独见 potency_bonus）。")
                .defineInRange("block_damage", 10.0D, 0.0D, Double.MAX_VALUE);
        BLOCK_AREA_DAMAGE = builder.comment("方块落点 2 格范围内的溅射伤害，0 为不溅射。")
                .defineInRange("block_area_damage", 6.0D, 0.0D, Double.MAX_VALUE);
        POTENCY_BONUS = builder.comment("强效附魔每一级为投掷方块增加的额外伤害（作用于直接伤害）。")
                .defineInRange("potency_bonus", 1.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private EndermanHandFocusConfig() {
    }
}
