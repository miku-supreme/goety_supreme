package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;

public final class SerponBlazingFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;
    public static ForgeConfigSpec.IntValue BURST_INTERVAL_TICKS;

    public static ForgeConfigSpec.IntValue BURST_SHOTS;

    public static ForgeConfigSpec.DoubleValue RADIUS_ANGLE_PER_LEVEL;

    public static ForgeConfigSpec.DoubleValue RANGE_DISTANCE_PER_LEVEL;

    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("炽焰聚晶：从施法者身上喷出蛇龙火焰巫师的锥形火舌，"
                        + "照 J 村 SerponFlames 每 2 刻一口、共 30 口。")
                .push("serpon_blazing_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 59（照 J 村 SerponFlames 的 getCastingTicks()）。")
                .defineInRange("cast_duration", 59, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 200（10 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("喷火的基准技能等级（默认 4，= 火焰巫师）。"
                        + "骰数 = 6+(等级−3)×6，点燃秒数 = 6+(等级−1)×3。")
                .defineInRange("base_level", 4, 1, Integer.MAX_VALUE);
        BURST_INTERVAL_TICKS = builder.comment("前摇结束后连喷补口的间隔（刻），默认 2（J 村 n % 2 == 0）。")
                .defineInRange("burst_interval_ticks", 2, 1, Integer.MAX_VALUE);
        BURST_SHOTS = builder.comment("前摇结束后的连喷总数（含起手那一口），默认 30。"
                        + "J 村 59 刻内 n=58,56,...,2 共 29 次补口 + 起手 1 口。")
                .defineInRange("burst_shots", 30, 1, Integer.MAX_VALUE);
        RADIUS_ANGLE_PER_LEVEL = builder.comment("聚晶上「半径」附魔每一级给喷吐<b>半角</b>增加的角度（度），默认 5。"
                        + "基础半角 22.5°（全角 45°），所以「半径 I」把全角扩到 55°。")
                .defineInRange("radius_angle_per_level", 5.0D, 0.0D, 90.0D);
        RANGE_DISTANCE_PER_LEVEL = builder.comment("聚晶上「范围」附魔每一级给喷吐射程增加的格数，"
                        + "为每级 1 格，默认 1；基础射程 9 格。")
                .defineInRange("range_distance_per_level", 1.0D, 0.0D, Double.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在对应魔杖（本系下界杖，判定同 Goety 的 rightStaff）"
                        + "里施法时每口额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private SerponBlazingFocusConfig() {
    }
}
