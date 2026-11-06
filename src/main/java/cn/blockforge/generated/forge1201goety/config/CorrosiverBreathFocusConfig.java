package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CorrosiverBreathFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;

    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;
    public static ForgeConfigSpec.IntValue BURST_INTERVAL_TICKS;

    public static ForgeConfigSpec.IntValue STAFF_BURST_SHOTS;

    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("蚀龙吐息聚晶：从施法者身上喷出蚀龙兽的锥形腐蚀吐息，"
                        + "起手一口，施法期间按 burst_interval_ticks 连喷。")
                .push("corrosiver_breath_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 8。")
                .defineInRange("soul_cost", 32, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 20（1 秒）；也是连喷窗口的长度。")
                .defineInRange("cast_duration", 100, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 60（3 秒）。")
                .defineInRange("cooldown", 200, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("吐息的基准技能等级（伤害基准为 1，默认 1）。"
                        + "腐蚀 / 虚弱 / 缓慢时长 = (6+(等级−1)×3) 秒。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每口吐息的伤害基准（为 1）；"
                        + "实际伤害 = 基准 × 本次技能等级。")
                .defineInRange("base_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        BURST_INTERVAL_TICKS = builder.comment("前摇结束后连射补口的间隔（刻），默认 10。")
                .defineInRange("burst_interval_ticks", 10, 1, Integer.MAX_VALUE);
        STAFF_BURST_SHOTS = builder.comment("装在对应强化法杖（冰霜系魔杖，判定同 Goety 的 rightStaff）"
                        + "里施法时，前摇结束后的连射总数（含收尾那一发），默认 3；"
                        + "没拿对法杖就只放收尾那 1 发。")
                .defineInRange("staff_burst_shots", 3, 1, Integer.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在冰霜系魔杖（判定同 Goety 的 rightStaff）"
                        + "里施法时每口额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private CorrosiverBreathFocusConfig() {
    }
}
