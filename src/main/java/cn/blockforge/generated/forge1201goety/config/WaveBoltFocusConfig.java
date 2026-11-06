package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.BulgeBulletServant;

public final class WaveBoltFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static ForgeConfigSpec.DoubleValue INACCURACY;
    public static ForgeConfigSpec.IntValue BURST_INTERVAL_TICKS;

    public static ForgeConfigSpec.IntValue STAFF_DAMAGE_BONUS;
    public static ForgeConfigSpec.DoubleValue STAFF_SPEED_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue VELOCITY_BONUS_PER_LEVEL;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("澜弹聚晶：射出 J 村波涛人的波涛弹，照 J 村 BulgeBullet 每 5 刻连射。")
                .push("wave_bolt_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 40。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 40（2 秒）。")
                .defineInRange("cooldown", 40, 0, Integer.MAX_VALUE);
        PROJECTILE_SPEED = builder.comment("波涛弹的出手速度，默认 1.0（与波涛人仆从同款）。")
                .defineInRange("projectile_speed", 1.0D, 0.2D, Double.MAX_VALUE);
        INACCURACY = builder.comment("出手散布（度），默认 1.0，越小越准。")
                .defineInRange("inaccuracy", 1.0D, 0.0D, Double.MAX_VALUE);
        BURST_INTERVAL_TICKS = builder.comment("前摇结束后连射补射的间隔（刻），默认 5（J 村弹体每 5 刻一发的节奏）。")
                .defineInRange("burst_interval_ticks", JerotesFocusBurst.CASTING_INTERVAL_TICKS,
                        1, Integer.MAX_VALUE);
        STAFF_DAMAGE_BONUS = builder.comment("装在对应魔杖（本系深渊杖，判定同 Goety 的 rightStaff）里施法时，"
                        + "每发波涛弹额外增加的伤害，默认 1。")
                .defineInRange("staff_damage_bonus", 1, 0, Integer.MAX_VALUE);
        STAFF_SPEED_MULTIPLIER = builder.comment("装在对应魔杖（本系深渊杖）里施法时的飞行速度倍率，"
                        + "默认 1.1（即 +10%）。")
                .defineInRange("staff_speed_multiplier", 1.1D, 0.1D, Double.MAX_VALUE);
        VELOCITY_BONUS_PER_LEVEL = builder.comment("「高速」附魔每一级给波涛弹增加的飞行速度，默认 0.2。")
                .defineInRange("velocity_bonus_per_level", 0.2D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private WaveBoltFocusConfig() {
    }
}
