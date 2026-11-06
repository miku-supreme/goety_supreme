package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;

public final class SpinScytheFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BASE_SPEED;
    public static ForgeConfigSpec.IntValue OUTBOUND_TICKS;
    public static ForgeConfigSpec.IntValue PLAIN_STAFF_COUNT;
    public static ForgeConfigSpec.IntValue NECRO_STAFF_COUNT;
    public static ForgeConfigSpec.IntValue NAMELESS_STAFF_COUNT;
    public static ForgeConfigSpec.DoubleValue VELOCITY_PER_LEVEL;
    public static ForgeConfigSpec.IntValue RANGE_TICKS_PER_LEVEL;
    public static ForgeConfigSpec.DoubleValue RADIUS_PER_LEVEL;

    public static ForgeConfigSpec.DoubleValue SPREAD_ANGLE;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("夺魂聚晶：以收割聚晶为模板的攻击聚晶，扔出收割者死灵法师同款的旋转飞镰。"
                        + "死灵魔杖扔 2 把、无名魔杖扔 3 把、其余魔杖 1 把。")
                .push("spin_scythe_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 30（1.5 秒）。")
                .defineInRange("cast_duration", 30, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 100（5 秒）。")
                .defineInRange("cooldown", 100, 0, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每把飞镰的基础伤害（同收割者死法飞镰的 4 点）；"
                        + "强效附魔每级 +1。")
                .defineInRange("base_damage", 4.0D, 0.0D, Double.MAX_VALUE);
        BASE_SPEED = builder.comment("飞镰飞行速度（格/刻，同收割者死法）；"
                        + "高速附魔每级 +5%。")
                .defineInRange("base_speed", 0.15D, 0.01D, Double.MAX_VALUE);
        OUTBOUND_TICKS = builder.comment("出程时长（刻）：飞满这么久没撞墙就掉头返程，默认 60。")
                .defineInRange("outbound_ticks", 60, 1, Integer.MAX_VALUE);
        PLAIN_STAFF_COUNT = builder.comment("普通魔杖（含空手）一次扔出的飞镰把数，默认 1。")
                .defineInRange("plain_staff_count", 1, 1, Integer.MAX_VALUE);
        NECRO_STAFF_COUNT = builder.comment("死灵魔杖（goety:necro_staff）一次扔出的飞镰把数，默认 2。")
                .defineInRange("necro_staff_count", 2, 1, Integer.MAX_VALUE);
        NAMELESS_STAFF_COUNT = builder.comment("无名魔杖（goety:nameless_staff）一次扔出的飞镰把数，默认 3。")
                .defineInRange("nameless_staff_count", 3, 1, Integer.MAX_VALUE);
        VELOCITY_PER_LEVEL = builder.comment("高速附魔每级给飞镰飞行速度的加成比例，默认 0.05（+5%）。")
                .defineInRange("velocity_per_level", 0.05D, 0.0D, Double.MAX_VALUE);
        RANGE_TICKS_PER_LEVEL = builder.comment("范围附魔每级给飞镰出程时长追加的刻数，默认 40"
                        + "（基准速度下约 +6 格最大飞行距离）。")
                .defineInRange("range_ticks_per_level", 40, 0, Integer.MAX_VALUE);
        RADIUS_PER_LEVEL = builder.comment("半径附魔每级给飞镰横向宽度（判定盒与渲染一起）的加成比例，"
                        + "默认 0.05（+5%）。")
                .defineInRange("radius_per_level", 0.05D, 0.0D, Double.MAX_VALUE);
        SPREAD_ANGLE = builder.comment("多把飞镰之间的散射角（弧度），默认 0.22（照收割者借招的扇形）。")
                .defineInRange("spread_angle", 0.22D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private SpinScytheFocusConfig() {
    }
}