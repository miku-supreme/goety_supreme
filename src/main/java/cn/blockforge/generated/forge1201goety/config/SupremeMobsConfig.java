package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;

public final class SupremeMobsConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue WITHER_WARLORD_HEALTH;
    public static final ForgeConfigSpec.DoubleValue WITHER_WARLORD_ATTACK;
    public static final ForgeConfigSpec.DoubleValue WITHER_WARLORD_DAMAGE_CAP;

    public static final ForgeConfigSpec.DoubleValue GREEN_SMITH_HEALTH;
    public static final ForgeConfigSpec.DoubleValue GREEN_SMITH_ATTACK;
    public static final ForgeConfigSpec.DoubleValue GREEN_SMITH_DAMAGE_CAP;

    public static final ForgeConfigSpec.DoubleValue IFRIT_HEALTH;
    public static final ForgeConfigSpec.DoubleValue IFRIT_ATTACK;
    public static final ForgeConfigSpec.DoubleValue IFRIT_DAMAGE_CAP;

    public static final ForgeConfigSpec.DoubleValue REAPER_HEALTH;
    public static final ForgeConfigSpec.DoubleValue REAPER_ATTACK;

    public static final ForgeConfigSpec.DoubleValue WILD_MAGE_HEALTH;
    public static final ForgeConfigSpec.DoubleValue WILD_MAGE_ATTACK;

    public static final ForgeConfigSpec.DoubleValue PRISMARINE_SOLDIER_HEALTH;
    public static final ForgeConfigSpec.DoubleValue PRISMARINE_SOLDIER_ATTACK;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Goety Supreme 敌对生物配置：每个生物一个小节；修改后重启游戏生效。",
                "这里只放本模组自己加的敌对生物。仆从配置在同目录的 servants.toml，聚晶在同目录的 focus.toml。",
                "敌对版与仆从版共用基础属性，所以改血量 / 攻击力对两种形态同时生效。");

        builder.comment("凋灵督军「黑大帅」。").push("wither_warlord");
        WITHER_WARLORD_HEALTH = builder.comment("最大生命值。").defineInRange("max_health", 500.0D, 1.0D, Double.MAX_VALUE);
        WITHER_WARLORD_ATTACK = builder.comment("基础攻击力（军刀追加的攻击力另计）。")
                .defineInRange("attack_damage", 5.0D, 0.0D, Double.MAX_VALUE);
        WITHER_WARLORD_DAMAGE_CAP = builder.comment("单次受击的实体伤害上限（限伤）。0 表示不限。")
                .defineInRange("damage_cap", 25.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        builder.comment("绿豆死灵法师。").push("green_smith");
        GREEN_SMITH_HEALTH = builder.comment("最大生命值。").defineInRange("max_health", 360.0D, 1.0D, Double.MAX_VALUE);
        GREEN_SMITH_ATTACK = builder.comment("基础攻击力。").defineInRange("attack_damage", 12.0D, 0.0D, Double.MAX_VALUE);
        GREEN_SMITH_DAMAGE_CAP = builder.comment("单次受击的实体伤害上限（限伤）。0 表示不限。")
                .defineInRange("damage_cap", 20.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        builder.comment("炼狱亲王 伊芙利特。").push("ifrit");
        IFRIT_HEALTH = builder.comment("最大生命值。").defineInRange("max_health", 360.0D, 1.0D, Double.MAX_VALUE);
        IFRIT_ATTACK = builder.comment("基础攻击力。").defineInRange("attack_damage", 20.0D, 0.0D, Double.MAX_VALUE);
        IFRIT_DAMAGE_CAP = builder.comment("单次受击的实体伤害上限（限伤）。0 表示不限。")
                .defineInRange("damage_cap", 20.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        builder.comment("收割者死灵法师。").push("reaper_necromancer");
        REAPER_HEALTH = builder.comment("最大生命值。").defineInRange("max_health", 220.0D, 1.0D, Double.MAX_VALUE);
        REAPER_ATTACK = builder.comment("基础攻击力。").defineInRange("attack_damage", 8.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        builder.comment("荒野法师。").push("wild_mage");
        WILD_MAGE_HEALTH = builder.comment("最大生命值。").defineInRange("max_health", 220.0D, 1.0D, Double.MAX_VALUE);
        WILD_MAGE_ATTACK = builder.comment("基础攻击力。").defineInRange("attack_damage", 4.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        builder.comment("海晶石士兵。").push("prismarine_soldier");
        PRISMARINE_SOLDIER_HEALTH = builder.comment("最大生命值。")
                .defineInRange("max_health", 75.0D, 1.0D, Double.MAX_VALUE);
        PRISMARINE_SOLDIER_ATTACK = builder.comment("基础攻击力。")
                .defineInRange("attack_damage", 7.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        SPEC = builder.build();
    }

    private SupremeMobsConfig() {
    }
}
