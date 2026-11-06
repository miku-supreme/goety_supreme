package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;

public final class SedimentMissileFocusConfig {

    public static ForgeConfigSpec.IntValue SOUL_COST;
    public static ForgeConfigSpec.IntValue CAST_DURATION;
    public static ForgeConfigSpec.IntValue COOLDOWN;
    public static ForgeConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static ForgeConfigSpec.DoubleValue INACCURACY;

    public static ForgeConfigSpec.IntValue BURST_INTERVAL_TICKS;

    public static ForgeConfigSpec.DoubleValue VELOCITY_BONUS_PER_LEVEL;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("沉降飞弹聚晶：射出 J 村定灵人的沉降法术弹，照 J 村 SedimentMissile 每 5 刻一枚。")
                .push("sediment_missile_focus");

        SOUL_COST = builder.comment("一次施法消耗的灵魂能量，默认 16。")
                .defineInRange("soul_cost", 16, 0, Integer.MAX_VALUE);
        CAST_DURATION = builder.comment("施法前摇（刻），默认 40。")
                .defineInRange("cast_duration", 40, 1, Integer.MAX_VALUE);
        COOLDOWN = builder.comment("施法后的冷却（刻），默认 40（2 秒）。")
                .defineInRange("cooldown", 40, 0, Integer.MAX_VALUE);
        PROJECTILE_SPEED = builder.comment("沉降法术弹的出手速度，默认 1.4。")
                .defineInRange("projectile_speed", 1.4D, 0.2D, Double.MAX_VALUE);
        INACCURACY = builder.comment("出手散布（度），默认 1.0，越小越准。")
                .defineInRange("inaccuracy", 1.0D, 0.0D, Double.MAX_VALUE);
        BURST_INTERVAL_TICKS = builder.comment("连射补射的间隔（刻），默认 5（定灵人每 5 刻一枚）。")
                .defineInRange("burst_interval_ticks", 5, 1, Integer.MAX_VALUE);
        VELOCITY_BONUS_PER_LEVEL = builder.comment("「高速」附魔每一级给沉降法术弹增加的飞行速度，默认 0.2。")
                .defineInRange("velocity_bonus_per_level", 0.2D, 0.0D, Double.MAX_VALUE);
        builder.pop();
    }

    private SedimentMissileFocusConfig() {
    }
}
