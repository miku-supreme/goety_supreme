package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CorrosiveIceSpikeFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.IntValue BASE_LEVEL;

    public static ForgeConfigSpec.DoubleValue BASE_DAMAGE;
    public static ForgeConfigSpec.IntValue STAFF_LEVEL_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("腐蚀冰锥聚晶：照蚀龙兽的腐蚀冰锥，朝视线扇形喷出冰锥，"
                        + "命中造成冰冻伤害并挂腐蚀。伤害基准为 1（= 基准 × 技能等级）。")
                .push("corrosive_ice_spike_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 8。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 20（1 秒）。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 40（2 秒）。")
                .defineInRange("cooldown", 40, 0, Integer.MAX_VALUE);
        BASE_LEVEL = builder.comment("基准技能等级（伤害基准为 1，默认 1）。")
                .defineInRange("base_level", 1, 1, Integer.MAX_VALUE);
        BASE_DAMAGE = builder.comment("每枚冰锥的伤害基准（为 1）；"
                        + "实际伤害 = 基准 × 本次技能等级。")
                .defineInRange("base_damage", 1.0D, 0.0D, Double.MAX_VALUE);
        STAFF_LEVEL_BONUS = builder.comment("装在冰霜系魔杖（判定同 Goety 的 rightStaff）"
                        + "里施法时额外增加的技能等级，默认 1。")
                .defineInRange("staff_level_bonus", 1, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private CorrosiveIceSpikeFocusConfig() {
    }
}
