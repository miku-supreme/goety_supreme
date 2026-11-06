package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;

import net.minecraftforge.common.ForgeConfigSpec;

public final class HotWindFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;

    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;

    public static ForgeConfigSpec.IntValue STAFF_BURST_SHOTS;

    public static ForgeConfigSpec.IntValue STAFF_BURST_INTERVAL_TICKS;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static ForgeConfigSpec.DoubleValue RADIUS_BONUS_PER_LEVEL;

    public static ForgeConfigSpec.IntValue RANGE_TICKS_PER_LEVEL;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("热风聚晶：照蛇龙执战者的热风，朝视线扇形放出热风弹体，"
                        + "沿途推开敌人并造成火焰 / 魔法伤害。伤害基准为 1（= 基准 × 技能等级）。")
                .push("hot_wind_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 10。")
                .defineInRange("soul_cost", 32, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），5 秒 = 100 刻，默认 100。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 60（3 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级（伤害基准为 1，默认 1）。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每发热风的伤害基准（为 1）；"
                        + "实际伤害 = 基准 × 本次技能等级。")
                .defineInRange("base_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        STAFF_BURST_SHOTS = builder.comment("装在下界系强化法杖（判定同 Goety 的 rightStaff）里施法时，"
                        + "前摇结束后的连射总数（含收尾那一发），默认 3（2~3 发）；"
                        + "没拿对法杖就只放收尾那 1 发。")
                .defineInRange("staff_burst_shots", 3, 1, Integer.MAX_VALUE);
        STAFF_BURST_INTERVAL_TICKS = builder.comment("连射补发的间隔（刻），默认 5。")
                .defineInRange("staff_burst_interval_ticks", JerotesFocusBurst.CASTING_INTERVAL_TICKS,
                        1, Integer.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在下界系魔杖（判定同 Goety 的 rightStaff）"
                        + "里施法时额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        RADIUS_BONUS_PER_LEVEL = builder.comment("「半径」附魔每级给热风弹体加的判定半径（格），"
                        + "每级 1 格，默认 1.0。")
                .defineInRange("radius_bonus_per_level", 1.0D, 0.0D, Double.MAX_VALUE);
        RANGE_TICKS_PER_LEVEL = builder.comment("「范围」附魔每级给热风弹体延长的存活（刻），"
                        + "即飞得更远；默认 2。")
                .defineInRange("range_ticks_per_level", 2, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private HotWindFocusConfig() {
    }
}
