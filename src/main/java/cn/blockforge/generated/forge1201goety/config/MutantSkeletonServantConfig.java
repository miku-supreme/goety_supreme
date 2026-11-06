package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MutantSkeletonServantConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static ForgeConfigSpec.IntValue SHOOT_INTERVAL_TICKS;
    public static ForgeConfigSpec.IntValue SHOOT_RANGE;
    public static ForgeConfigSpec.IntValue MULTI_SHOT_CHANCE_PERCENT;
    public static ForgeConfigSpec.IntValue MALEFIC_TRIPLE_SHOT_CHANCE_PERCENT;
    public static ForgeConfigSpec.BooleanValue ARROW_EFFECTS;
    public static ForgeConfigSpec.BooleanValue BONE_SWEEP;
    public static ForgeConfigSpec.BooleanValue CONSTRICT_RIBS;
    public static ForgeConfigSpec.BooleanValue SOUL_JAR_ENABLED;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_HEALTH;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_ARROW_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_MELEE_DAMAGE;
    public static ForgeConfigSpec.BooleanValue MALEFIC_HELM_ENABLED;
    public static ForgeConfigSpec.DoubleValue MALEFIC_INTERVAL_MULTIPLIER;
    public static ForgeConfigSpec.IntValue MALEFIC_VOLLEY_MULTIPLIER;
    public static ForgeConfigSpec.BooleanValue MALEFIC_IGNORE_INVULNERABILITY;
    public static ForgeConfigSpec.IntValue BONE_MAX_UPGRADES;
    public static ForgeConfigSpec.DoubleValue BONE_HEALTH;
    public static ForgeConfigSpec.DoubleValue BONE_MELEE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BONE_ARROW_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BONE_ARMOR;
    public static ForgeConfigSpec.DoubleValue BONE_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue FEED_BONE_HEAL;
    public static ForgeConfigSpec.DoubleValue FEED_BONE_BLOCK_MULTIPLIER;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("骷髅突变体仆从的属性与攻击配置。").push("mutant_skeleton_servant");

        MAX_HEALTH = builder.comment("最大生命值，与 Mutant Monsters 原版突变骷髅一致为 150。")
                .defineInRange("max_health", 150.0D, 1.0D, Double.MAX_VALUE);
        ATTACK_DAMAGE = builder.comment("基础攻击伤害（骨扫与缩肋的加成都以此为基础）。")
                .defineInRange("attack_damage", 3.0D, 0.0D, Double.MAX_VALUE);
        FEED_BONE_HEAL = builder.comment("主人手持骨头右击一次回复的生命值（与骷髅仆从同款喂食；设为 0 关闭）。")
                .defineInRange("feed_bone_heal", 2.0D, 0.0D, Double.MAX_VALUE);
        FEED_BONE_BLOCK_MULTIPLIER = builder.comment("骨块按骨头的多少倍回血（默认 4 倍）。")
                .defineInRange("feed_bone_block_multiplier", 4.0D, 0.0D, Double.MAX_VALUE);
        MOVEMENT_SPEED = builder.comment("移动速度。")
                .defineInRange("movement_speed", 0.27D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                .defineInRange("knockback_resistance", 0.75D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("护甲值。")
                .defineInRange("armor", 0.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌范围。")
                .defineInRange("follow_range", 48.0D, 1.0D, 2048.0D);

        SHOOT_INTERVAL_TICKS = builder.comment("两次远程拉弓起手之间的最短间隔（刻），仍需完成当前射击动画。")
                .defineInRange("shoot_interval_ticks", 30, 1, Integer.MAX_VALUE);
        SHOOT_RANGE = builder.comment("远程射击范围（格）。")
                .defineInRange("shoot_range", 16, 1, Integer.MAX_VALUE);
        MULTI_SHOT_CHANCE_PERCENT = builder.comment("每次远程攻击改放“万箭齐发”（MULTI_SHOT 动画、24 连射）的概率，单位百分比；0 关闭。")
                .defineInRange("multi_shot_chance_percent", 20, 0, Integer.MAX_VALUE);
        MALEFIC_TRIPLE_SHOT_CHANCE_PERCENT = builder.comment("装备恶骸骨盔后，普通拉弓射箭触发三连发的概率，单位百分比。")
                .defineInRange("malefic_triple_shot_chance_percent", 35, 0, Integer.MAX_VALUE);
        ARROW_EFFECTS = builder.comment("单发突变箭是否按原版概率附加中毒 / 饥饿 / 缓慢效果。")
                .define("arrow_effects", true);
        BONE_SWEEP = builder.comment("是否启用贴脸时的“骨浪横扫”（MELEE 动画，4 格扇形伤害并击飞）。")
                .define("bone_sweep", true);
        CONSTRICT_RIBS = builder.comment("是否启用“肋骨绞压”（CONSTRUCT_RIBS 动画，重创贴身目标并炸飞）。")
                .define("constrict_ribs", true);

        builder.comment("强化配置；enabled 控制是否接受新的强化，已有强化状态保留。修改后重启游戏生效。").push("upgrades");
        SOUL_JAR_ENABLED = builder.comment("允许给予普通灵魂罐。").define("soul_jar_enabled", true);
        SOUL_JAR_HEALTH = builder.comment("灵魂罐增加的最大生命值。").defineInRange("soul_jar_health", 25.0D, 0.0D, Double.MAX_VALUE);
        SOUL_JAR_ARROW_DAMAGE = builder.comment("灵魂罐箭矢额外魔法伤害（2 点）。").defineInRange("soul_jar_arrow_damage", 2.0D, 0.0D, Double.MAX_VALUE);
        SOUL_JAR_MELEE_DAMAGE = builder.comment("灵魂罐近战额外魔法伤害（2 点）。").defineInRange("soul_jar_melee_damage", 2.0D, 0.0D, Double.MAX_VALUE);
        MALEFIC_HELM_ENABLED = builder.comment("允许给予恶骸骨盔。").define("malefic_helm_enabled", true);
        MALEFIC_INTERVAL_MULTIPLIER = builder.comment("恶骸骨盔两次拉弓起手的周期倍率，默认 2/3，即缩短三分之一，取整到最近一刻；同步缩短远程动画，保证实际生效。").defineInRange("malefic_interval_multiplier", 2.0D / 3.0D, 0.0D, Double.MAX_VALUE);
        MALEFIC_VOLLEY_MULTIPLIER = builder.comment("恶骸骨盔散射箭数倍率。").defineInRange("malefic_volley_multiplier", 2, 1, Integer.MAX_VALUE);
        MALEFIC_IGNORE_INVULNERABILITY = builder.comment("恶骸骨盔普通射击忽略受击无敌帧。").define("malefic_ignore_invulnerability", true);
        BONE_MAX_UPGRADES = builder.comment("浓缩骨块最大强化次数，0 禁用新强化；已有次数不删除。").defineInRange("bone_max_upgrades", 5, 0, Integer.MAX_VALUE);
        BONE_HEALTH = builder.comment("每次浓缩骨块增加的最大生命值。").defineInRange("bone_health", 10.0D, 0.0D, Double.MAX_VALUE);
        BONE_MELEE_DAMAGE = builder.comment("每次浓缩骨块增加的近战攻击力（0.7）。").defineInRange("bone_melee_damage", 0.7D, 0.0D, Double.MAX_VALUE);
        BONE_ARROW_DAMAGE = builder.comment("已废弃：骨块不再单独加箭伤（保留键只为兼容旧配置，固定 0）。").defineInRange("bone_arrow_damage", 0.0D, 0.0D, Double.MAX_VALUE);
        BONE_ARMOR = builder.comment("每次浓缩骨块增加的护甲。").defineInRange("bone_armor", 2.0D, 0.0D, Double.MAX_VALUE);
        BONE_TOUGHNESS = builder.comment("每次浓缩骨块增加的护甲韧性（1.0）。").defineInRange("bone_toughness", 1.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
        builder.pop();
    }

    private MutantSkeletonServantConfig() {
    }
}
