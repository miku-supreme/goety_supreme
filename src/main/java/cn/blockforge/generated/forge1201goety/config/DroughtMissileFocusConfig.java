package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;

public final class DroughtMissileFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static ForgeConfigSpec.DoubleValue INACCURACY;
    public static ForgeConfigSpec.IntValue BURN_SECONDS;
    public static ForgeConfigSpec.IntValue BURST_INTERVAL_TICKS;

    public static ForgeConfigSpec.IntValue STAFF_DAMAGE_BONUS;
    public static ForgeConfigSpec.DoubleValue STAFF_SPEED_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue VELOCITY_BONUS_PER_LEVEL;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("焦燃飞弹聚晶：射出 J 村旱火野火的旱火火球，照 J 村 DroughtFireball 每 5 刻连射。")
                .push("drought_missile_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 40。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 40（2 秒）。")
                .defineInRange("cooldown", 40, 0, Integer.MAX_VALUE);
        PROJECTILE_SPEED = builder.comment("旱火火球的出手速度，默认 0.9。注意：旱火野火 / 旱火烈焰人\n"
                        + "那两只射的火球已按 J 村对齐成 1.0 起步 + 每刻 0.1 加速；聚晶这一发保持\n"
                        + "0.9 的射手感，需要更快可以在这里调。")
                .defineInRange("projectile_speed", 0.9D, 0.2D, Double.MAX_VALUE);
        INACCURACY = builder.comment("出手散布（度），默认 1.0，越小越准。")
                .defineInRange("inaccuracy", 1.0D, 0.0D, Double.MAX_VALUE);
        BURN_SECONDS = builder.comment("命中后点燃目标的秒数，默认 6（J 村 1 级法术的口径）；"
                        + "伤害固定为 J 村的 2~9 点，不随强效附魔变化。")
                .defineInRange("burn_seconds", 6, 0, Integer.MAX_VALUE);
        BURST_INTERVAL_TICKS = builder.comment("前摇结束后连射补射的间隔（刻），默认 5（J 村弹体每 5 刻一发的节奏）。")
                .defineInRange("burst_interval_ticks", JerotesFocusBurst.CASTING_INTERVAL_TICKS,
                        1, Integer.MAX_VALUE);
        STAFF_DAMAGE_BONUS = builder.comment("装在对应魔杖（本系下界杖，判定同 Goety 的 rightStaff）里施法时，"
                        + "每发旱火火球额外增加的伤害，默认 1。")
                .defineInRange("staff_damage_bonus", 1, 0, Integer.MAX_VALUE);
        STAFF_SPEED_MULTIPLIER = builder.comment("装在对应魔杖（本系下界杖）里施法时的飞行速度倍率，"
                        + "默认 1.1（即 +10%）。")
                .defineInRange("staff_speed_multiplier", 1.1D, 0.1D, Double.MAX_VALUE);
        VELOCITY_BONUS_PER_LEVEL = builder.comment("「高速」附魔每一级给旱火火球增加的飞行速度，默认 0.2。")
                .defineInRange("velocity_bonus_per_level", 0.2D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private DroughtMissileFocusConfig() {
    }
}
