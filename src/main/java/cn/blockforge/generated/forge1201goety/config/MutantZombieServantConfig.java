package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MutantZombieServantConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue ROTTEN_FLESH_HEAL;
    public static ForgeConfigSpec.IntValue SLAM_GROUND_DAMAGE_FIRST_MIN;
    public static ForgeConfigSpec.IntValue SLAM_GROUND_DAMAGE_FIRST_MAX;
    public static ForgeConfigSpec.IntValue SLAM_GROUND_DAMAGE_WAVE_MIN;
    public static ForgeConfigSpec.IntValue SLAM_GROUND_DAMAGE_WAVE_MAX;

    public static ForgeConfigSpec.BooleanValue MAINHAND_WEAPON_DAMAGE_ENABLED;

    public static ForgeConfigSpec.BooleanValue OFFHAND_WEAPON_SHOCKWAVE_ENABLED;

    public static ForgeConfigSpec.BooleanValue MAINHAND_WEAPON_ENCHANTMENT_ENABLED;

    public static ForgeConfigSpec.BooleanValue OFFHAND_WEAPON_ENCHANTMENT_ENABLED;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static ForgeConfigSpec.BooleanValue SUMMON_ZOMBIE_SERVANTS;
    public static ForgeConfigSpec.IntValue SUMMON_SERVANT_COOLDOWN_SECONDS;
    public static ForgeConfigSpec.IntValue SUMMON_SERVANT_COUNT;
    public static ForgeConfigSpec.IntValue SUMMON_SERVANT_CAP;

    public static ForgeConfigSpec.DoubleValue SUMMON_SERVANT_KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue WILD_ROAR_SUMMON_SERVANTS;

    public static ForgeConfigSpec.BooleanValue FIRST_ROAR_GUARANTEE;
    public static ForgeConfigSpec.IntValue FIRST_ROAR_MIN_SECONDS;
    public static ForgeConfigSpec.IntValue FIRST_ROAR_MAX_SECONDS;

    public static ForgeConfigSpec.IntValue RESURRECTION_LIVES;
    public static ForgeConfigSpec.IntValue HOWLING_SOUL_EFFECT_SECONDS;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_ROAR_CHANCE_PERCENT;
    public static ForgeConfigSpec.BooleanValue HOWLING_SOUL_ENABLED;
    public static ForgeConfigSpec.DoubleValue HOWLING_SOUL_HEALTH;
    public static ForgeConfigSpec.DoubleValue HOWLING_SOUL_ROAR_DAMAGE;
    public static ForgeConfigSpec.IntValue HOWLING_SOUL_STUN_TICKS;
    public static ForgeConfigSpec.IntValue HOWLING_SOUL_WANE_TICKS;
    public static ForgeConfigSpec.BooleanValue SOUL_JAR_ENABLED;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_HEALTH;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.IntValue SOUL_JAR_BUFF_SECONDS;
    public static ForgeConfigSpec.BooleanValue BERSERK_ENABLED;
    public static ForgeConfigSpec.IntValue BERSERK_DURATION_SECONDS;
    public static ForgeConfigSpec.DoubleValue BERSERK_HEALTH;
    public static ForgeConfigSpec.DoubleValue BERSERK_ARMOR;
    public static ForgeConfigSpec.DoubleValue BERSERK_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue BERSERK_ATTACK_BONUS;

    public static ForgeConfigSpec.DoubleValue BERSERK_SPEED_BONUS;
    public static ForgeConfigSpec.DoubleValue BERSERK_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue BERSERK_SLAM_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue BERSERK_SLAM_RANGE;
    public static ForgeConfigSpec.IntValue BERSERK_EXTRA_LIVES;
    public static ForgeConfigSpec.BooleanValue BERSERK_IMMEDIATE_ROAR;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("僵尸突变体仆从的属性与攻击伤害配置。").push("mutant_zombie_servant");

        MAX_HEALTH = builder.comment("最大生命值。")
                .defineInRange("max_health", 150.0D, 1.0D, Double.MAX_VALUE);
        ATTACK_DAMAGE = builder.comment("基础近战攻击伤害，同时作为投掷招式伤害。")
                .defineInRange("attack_damage", 12.0D, 0.0D, Double.MAX_VALUE);
        ROTTEN_FLESH_HEAL = builder.comment("主人手持腐肉右击一次回复的生命值（与僵尸仆从同款喂食；设为 0 关闭）。")
                .defineInRange("rotten_flesh_heal", 2.0D, 0.0D, Double.MAX_VALUE);
        SLAM_GROUND_DAMAGE_FIRST_MIN = builder.comment("震地首段的最小整数伤害。")
                .defineInRange("slam_ground_damage_first_min", 9, 0, Integer.MAX_VALUE);
        SLAM_GROUND_DAMAGE_FIRST_MAX = builder.comment("震地首段的最大整数伤害。")
                .defineInRange("slam_ground_damage_first_max", 12, 0, Integer.MAX_VALUE);
        SLAM_GROUND_DAMAGE_WAVE_MIN = builder.comment("后续震地波的最小整数伤害。")
                .defineInRange("slam_ground_damage_wave_min", 6, 0, Integer.MAX_VALUE);
        SLAM_GROUND_DAMAGE_WAVE_MAX = builder.comment("后续震地波的最大整数伤害。")
                .defineInRange("slam_ground_damage_wave_max", 8, 0, Integer.MAX_VALUE);
        MAINHAND_WEAPON_DAMAGE_ENABLED = builder.comment(
                        "主人给的主手武器是否把它自己的攻击力加成算进扑击伤害（只有扑击跟攻击力有关，震地波 / 咆哮震波不吃主手加成）。",
                        "原版 Mob#doHurtTarget 不会把手中武器的属性攻击力算进 ATTACK_DAMAGE，这里补上。")
                .define("mainhand_weapon_damage_enabled", true);
        OFFHAND_WEAPON_SHOCKWAVE_ENABLED = builder.comment(
                        "主人给的副手武器是否把它自己的攻击力加成算进冲击波伤害：",
                        "震地波吃全额加成，咆哮震波只吃四分之一加成。")
                .define("offhand_weapon_shockwave_enabled", true);
        MAINHAND_WEAPON_ENCHANTMENT_ENABLED = builder.comment(
                        "主人给的主手武器附魔是否在扑击上生效：锋利 / 亡灵杀手 / 节肢杀手 / 穿刺按目标类型加伤，",
                        "火焰附加点燃目标，击退加强推出距离。独立于上一条攻击力开关，不影响震地波与咆哮。")
                .define("mainhand_weapon_enchantment_enabled", true);
        OFFHAND_WEAPON_ENCHANTMENT_ENABLED = builder.comment(
                        "主人给的副手武器附魔是否在震地波上生效（加伤 / 火焰附加 / 击退，同主手口径）。",
                        "只作用于震地波，咆哮震波不吃任何附魔。")
                .define("offhand_weapon_enchantment_enabled", true);
        MOVEMENT_SPEED = builder.comment("移动速度。")
                .defineInRange("movement_speed", 0.26D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                .defineInRange("knockback_resistance", 1.0D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("护甲值。")
                .defineInRange("armor", 3.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌距离（格）：锁定目标与保持追击的距离上限。")
                .defineInRange("follow_range", 35.0D, 1.0D, 2048.0D);

        SUMMON_ZOMBIE_SERVANTS = builder.comment("是否启用突变僵尸仆从的“召唤僵尸仆从”技能（沿用原版咆哮破土动作）。")
                .define("summon_zombie_servants", true);
        SUMMON_SERVANT_COOLDOWN_SECONDS = builder.comment("技能冷却：两次咆哮召唤之间的最短间隔（秒）。")
                .defineInRange("summon_servant_cooldown_seconds", 20, 0, Integer.MAX_VALUE);
        SUMMON_SERVANT_COUNT = builder.comment("单次咆哮召唤的僵尸仆从数量（1-6，破土点不足时按实际生成；原版一次咆哮最多预约 6 个破土点）。")
                .defineInRange("summon_servant_count", 6, 1, Integer.MAX_VALUE);
        SUMMON_SERVANT_CAP = builder.comment("同一主人在附近同时存活的僵尸仆从总数上限（咆哮召唤共用）。")
                .defineInRange("summon_servant_cap", 12, 0, Integer.MAX_VALUE);
        SUMMON_SERVANT_KNOCKBACK_RESISTANCE = builder.comment("咆哮召唤出的僵尸仆从额外获得的击退抗性，"
                        + "0.4（叠加在 Goety 自身配置之上）。")
                .defineInRange("summon_servant_knockback_resistance", 0.4D, 0.0D, Double.MAX_VALUE);
        WILD_ROAR_SUMMON_SERVANTS = builder.comment("野生突变僵尸的召唤技能是否改为本模组的僵尸仆从（关闭则保留原版普通僵尸）。")
                .define("wild_roar_summon_servants", true);

        FIRST_ROAR_GUARANTEE = builder.comment("是否在锁定敌人后的若干秒内必定使用一次“召唤僵尸”"
                + "（野生突变僵尸与突变僵尸仆从通用），保底用掉之后回到原本的低概率随机触发。")
                .define("first_roar_guarantee", true);
        FIRST_ROAR_MIN_SECONDS = builder.comment("保底召唤最早出现的延迟（秒，从锁定敌人时开始计时）。")
                .defineInRange("first_roar_min_seconds", 5, 0, Integer.MAX_VALUE);
        FIRST_ROAR_MAX_SECONDS = builder.comment("保底召唤最迟出现的延迟（秒，实际时刻取最小值与最大值之间随机）。")
                .defineInRange("first_roar_max_seconds", 10, 0, Integer.MAX_VALUE);

        RESURRECTION_LIVES = builder.comment("复活技能：倒下后还能重新爬起来的重数，与 Mutant Monsters 原版突变僵尸一致为 3；"
                + "倒地期间原色趴地 7 秒，爬起来时恢复最大生命的 1/4。设为 0 关闭技能，仆从会像普通生物一样直接死亡。"
                + "倒地期间被点燃（打火石可补火）则剥夺全部剩余重数，进入 5 秒渐隐的真正死亡。")
                .defineInRange("resurrection_lives", 3, 0, Integer.MAX_VALUE);

        HOWLING_SOUL_EFFECT_SECONDS = builder.comment("咆哮之魂强化：成功命中后附加诅咒 I 和末日 I 的时长（秒，默认 15 秒）。")
                .defineInRange("howling_soul_effect_seconds", 15, 1, Integer.MAX_VALUE);
        SOUL_JAR_ROAR_CHANCE_PERCENT = builder.comment("普通灵魂罐强化：每次随机咆哮判定的触发百分比。未强化为 2.2，强化默认 8.8；仍受召唤冷却限制。")
                .defineInRange("soul_jar_roar_chance_percent", 8.8D, 0.0D, Double.MAX_VALUE);

        builder.comment("强化配置；enabled 控制是否接受新的强化，已有强化状态保留。修改后重启游戏生效。").push("upgrades");
        HOWLING_SOUL_ENABLED = builder.comment("允许给予咆哮之魂。").define("howling_soul_enabled", true);
        HOWLING_SOUL_HEALTH = builder.comment("咆哮之魂增加的最大生命值。").defineInRange("howling_soul_health", 25.0D, 0.0D, Double.MAX_VALUE);
        HOWLING_SOUL_ROAR_DAMAGE = builder.comment("咆哮之魂额外咆哮伤害。").defineInRange("howling_soul_roar_damage", 2.0D, 0.0D, Double.MAX_VALUE);
        HOWLING_SOUL_STUN_TICKS = builder.comment("强化咆哮震撼时长，20 刻为 1 秒（1.5 秒 = 30）。").defineInRange("howling_soul_stun_ticks", 30, 0, Integer.MAX_VALUE);
        HOWLING_SOUL_WANE_TICKS = builder.comment("强化咆哮 Goety 衰弱时长，20 刻为 1 秒（5 秒 = 100）。").defineInRange("howling_soul_wane_ticks", 100, 0, Integer.MAX_VALUE);
        SOUL_JAR_ENABLED = builder.comment("允许给予普通灵魂罐。").define("soul_jar_enabled", true);
        SOUL_JAR_HEALTH = builder.comment("灵魂罐增加的最大生命值。").defineInRange("soul_jar_health", 25.0D, 0.0D, Double.MAX_VALUE);
        SOUL_JAR_DAMAGE_MULTIPLIER = builder.comment("灵魂罐咆哮期间受到伤害的倍率。").defineInRange("soul_jar_damage_multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        SOUL_JAR_BUFF_SECONDS = builder.comment("灵魂罐咆哮给予友军的增益时长，秒。").defineInRange("soul_jar_buff_seconds", 30, 1, Integer.MAX_VALUE);
        BERSERK_ENABLED = builder.comment("允许给予狂暴精粹。").define("berserk_enabled", true);
        BERSERK_DURATION_SECONDS = builder.comment("狂暴持续时长，秒，重复给予刷新时长。").defineInRange("berserk_duration_seconds", 120, 1, Integer.MAX_VALUE);
        BERSERK_HEALTH = builder.comment("狂暴临时最大生命加成。").defineInRange("berserk_health", 100.0D, 0.0D, Double.MAX_VALUE);
        BERSERK_ARMOR = builder.comment("狂暴护甲加成。").defineInRange("berserk_armor", 12.0D, 0.0D, Double.MAX_VALUE);
        BERSERK_TOUGHNESS = builder.comment("狂暴护甲韧性加成，6 点。").defineInRange("berserk_toughness", 6.0D, 0.0D, Double.MAX_VALUE);
        BERSERK_ATTACK_BONUS = builder.comment("狂暴攻击加成比例，0.30 表示增加 30%。").defineInRange("berserk_attack_bonus", 0.30D, 0.0D, Double.MAX_VALUE);
        BERSERK_SPEED_BONUS = builder.comment("狂暴移动速度加成比例，0.10 表示提高 10%。").defineInRange("berserk_speed_bonus", 0.10D, 0.0D, Double.MAX_VALUE);
        BERSERK_DAMAGE_MULTIPLIER = builder.comment("狂暴受到伤害的倍率。").defineInRange("berserk_damage_multiplier", 0.9D, 0.0D, Double.MAX_VALUE);
        BERSERK_SLAM_MULTIPLIER = builder.comment("狂暴震地伤害倍率。").defineInRange("berserk_slam_multiplier", 1.5D, 0.0D, Double.MAX_VALUE);
        BERSERK_SLAM_RANGE = builder.comment("狂暴震地波距离，格。").defineInRange("berserk_slam_range", 10.0D, 1.0D, Double.MAX_VALUE);
        BERSERK_EXTRA_LIVES = builder.comment("每次给予狂暴精粹增加的复活机会。").defineInRange("berserk_extra_lives", 1, 0, Integer.MAX_VALUE);
        BERSERK_IMMEDIATE_ROAR = builder.comment("给予狂暴精粹后立即咆哮。").define("berserk_immediate_roar", true);
        builder.pop();
        builder.pop();
    }

    private MutantZombieServantConfig() {
    }

    public static double randomBetween(ForgeConfigSpec.IntValue minimum, ForgeConfigSpec.IntValue maximum,
            net.minecraft.util.RandomSource random) {
        int min = minimum.get();
        int max = Math.max(min, maximum.get());
        return min + random.nextInt(max - min + 1);
    }

    public static int firstRoarDelayTicks(net.minecraft.util.RandomSource random) {
        int min = Math.max(0, FIRST_ROAR_MIN_SECONDS.get()) * 20;
        int max = Math.max(0, FIRST_ROAR_MAX_SECONDS.get()) * 20;
        int hi = Math.max(min, max);
        return min + (hi > min ? random.nextInt(hi - min + 1) : 0);
    }
}
