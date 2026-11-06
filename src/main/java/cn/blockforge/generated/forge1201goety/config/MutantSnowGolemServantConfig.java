package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MutantSnowGolemServantConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;
    public static ForgeConfigSpec.DoubleValue SNOW_BLOCK_HEAL;

    public static ForgeConfigSpec.DoubleValue FULL_UPGRADE_ARMOR;

    public static ForgeConfigSpec.IntValue ICE_DAMAGE_DIRECT_MIN;
    public static ForgeConfigSpec.IntValue ICE_DAMAGE_DIRECT_MAX;
    public static ForgeConfigSpec.IntValue ICE_DAMAGE_AOE_MIN;
    public static ForgeConfigSpec.IntValue ICE_DAMAGE_AOE_MAX;
    public static ForgeConfigSpec.IntValue THROW_INTERVAL_TICKS;
    public static ForgeConfigSpec.IntValue THROW_RANGE;
    public static ForgeConfigSpec.BooleanValue ACTIVE_TARGETING;
    public static ForgeConfigSpec.BooleanValue AIM_CORRECTION;
    public static ForgeConfigSpec.DoubleValue THROW_INACCURACY;
    public static ForgeConfigSpec.DoubleValue DIRECT_THROW_RANGE;
    public static ForgeConfigSpec.DoubleValue DIRECT_THROW_SPEED;

    public static ForgeConfigSpec.BooleanValue SNOW_TRAIL;
    public static ForgeConfigSpec.BooleanValue FROST_NOVA_ENABLED;
    public static ForgeConfigSpec.DoubleValue FROST_NOVA_CHANCE;
    public static ForgeConfigSpec.BooleanValue SOUL_JAR_ENABLED;
    public static ForgeConfigSpec.DoubleValue SOUL_JAR_HEALTH;
    public static ForgeConfigSpec.DoubleValue SUMMON_CHANCE;
    public static ForgeConfigSpec.IntValue SUMMON_COOLDOWN_TICKS;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MIN;
    public static ForgeConfigSpec.IntValue SUMMON_COUNT_MAX;
    public static ForgeConfigSpec.IntValue SUMMON_CAP;
    public static ForgeConfigSpec.IntValue FROSTY_AURA_LEVEL;
    public static ForgeConfigSpec.BooleanValue CHILLING_ENABLED;
    public static ForgeConfigSpec.IntValue CHILL_HIDE_LEVEL;
    public static ForgeConfigSpec.IntValue ALLY_CHILL_HIDE_LEVEL;
    public static ForgeConfigSpec.IntValue ALLY_CHILL_HIDE_TICKS;
    public static ForgeConfigSpec.DoubleValue ALLY_CHILL_HIDE_RADIUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("雪傀儡突变体仆从的属性与投掷招式配置。").push("mutant_snow_golem_servant");

        MAX_HEALTH = builder.comment("最大生命值（Mutant Monsters 原版突变雪傀儡为 80）。")
                .defineInRange("max_health", 80.0D, 1.0D, Double.MAX_VALUE);
        MOVEMENT_SPEED = builder.comment("移动速度（原版 0.26）。")
                .defineInRange("movement_speed", 0.26D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                .defineInRange("knockback_resistance", 0.6D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("护甲值。")
                .defineInRange("armor", 0.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌距离（格）：锁定目标与保持追击的距离上限。")
                .defineInRange("follow_range", 35.0D, 1.0D, 2048.0D);
        SNOW_BLOCK_HEAL = builder.comment("主人手持雪块右击一次回复的生命值（掉血才吃；设为 0 关闭喂食）。")
                .defineInRange("snow_block_heal", 5.0D, 0.0D, Double.MAX_VALUE);
        FULL_UPGRADE_ARMOR = builder.comment("霜呼、灵魂罐、冷静三项强化全部到位（满级）后额外获得的护甲值。")
                .defineInRange("full_upgrade_armor", 5.0D, 0.0D, Double.MAX_VALUE);

        ICE_DAMAGE_DIRECT_MIN = builder.comment("冰块正中目标的整数伤害下限（4，直击 4~6 随机）。")
                .defineInRange("ice_damage_direct_min", 4, 0, Integer.MAX_VALUE);
        ICE_DAMAGE_DIRECT_MAX = builder.comment("冰块正中目标的整数伤害上限（6）。")
                .defineInRange("ice_damage_direct_max", 6, 0, Integer.MAX_VALUE);
        ICE_DAMAGE_AOE_MIN = builder.comment("冰块溅射的最小整数伤害（原版 4 + random，即 4-6）。")
                .defineInRange("ice_damage_aoe_min", 4, 0, Integer.MAX_VALUE);
        ICE_DAMAGE_AOE_MAX = builder.comment("冰块溅射的最大整数伤害。")
                .defineInRange("ice_damage_aoe_max", 6, 0, Integer.MAX_VALUE);
        THROW_INTERVAL_TICKS = builder.comment("两轮投掷之间的间隔刻数（原版 30）。")
                .defineInRange("throw_interval_ticks", 30, 5, Integer.MAX_VALUE);
        THROW_RANGE = builder.comment("投掷攻击的目标距离上限（格，默认 30）：敌人在该距离内且视线通畅才会扔冰块，"
                + "超出该距离时改为步行靠近。",
                "原版突变雪傀儡的 RangedAttackGoal 没有任何距离闸门，只要索敌到且视线通畅就照投；"
                + "这里是同一套口径，30 格基本等于索敌距离（follow_range 默认 35），"
                + "远距离的解算靠出手速度上限自动拉长弹道。")
                .defineInRange("throw_range", 30, 4, Integer.MAX_VALUE);
        ACTIVE_TARGETING = builder.comment("是否主动搜寻索敌距离（follow_range）内的敌对怪物；关闭后只保留护主与反击索敌。")
                .define("active_targeting", true);
        AIM_CORRECTION = builder.comment("是否按敌人当前位置解算投掷角度（考虑重力下坠与提前量）；"
                + "关闭则沿用原版固定抛物线，敌人贴脸或距离较远时容易掷空。")
                .define("aim_correction", true);
        THROW_INACCURACY = builder.comment("投掷角度的随机散布（度，原版固定为 1.0）。开启角度修正后建议保持较小值。")
                .defineInRange("throw_inaccuracy", 0.35D, 0.0D, Double.MAX_VALUE);
        DIRECT_THROW_RANGE = builder.comment("贴脸直扔距离（格，默认 5）：敌人在此距离以内时，冰块不再走抛物线解算，"
                + "而是直接朝目标躯干中心平射，避免贴脸时因抛物线过高或过平而掷空。")
                .defineInRange("direct_throw_range", 5.0D, 1.0D, Double.MAX_VALUE);
        DIRECT_THROW_SPEED = builder.comment("贴脸直扔初速度（块/刻）。越小弹道越平缓、越容易命中近处目标。")
                .defineInRange("direct_throw_speed", 1.8D, 0.5D, Double.MAX_VALUE);

        SNOW_TRAIL = builder.comment("是否在行走路径上积雪并冻住流水（仍需游戏规则 mobGriefing 开启）。")
                .define("snow_trail", true);

        builder.comment("强化配置；enabled 控制是否接受新的强化，已有强化状态保留。概率范围为 0 到 1；修改后重启生效。").push("upgrades");
        FROST_NOVA_ENABLED = builder.comment("允许给予霜冻新星聚晶。").define("frost_nova_enabled", true);
        FROST_NOVA_CHANCE = builder.comment("强化冰块命中触发霜冻新星的概率（0.75），法术数值沿用 Goety 配置。").defineInRange("frost_nova_chance", 0.75D, 0.0D, Double.MAX_VALUE);
        SOUL_JAR_ENABLED = builder.comment("允许给予流髑灵魂罐。").define("soul_jar_enabled", true);
        SOUL_JAR_HEALTH = builder.comment("流髑灵魂罐增加的最大生命值。").defineInRange("soul_jar_health", 40.0D, 0.0D, Double.MAX_VALUE);
        SUMMON_CHANCE = builder.comment("流髑灵魂罐将一次投掷改为召唤的概率。").defineInRange("summon_chance", 0.1D, 0.0D, Double.MAX_VALUE);
        SUMMON_COOLDOWN_TICKS = builder.comment("召唤冷却，20 刻为 1 秒。").defineInRange("summon_cooldown_ticks", 160, 0, Integer.MAX_VALUE);
        SUMMON_COUNT_MIN = builder.comment("每次召唤雪傀儡仆从的最少数量（3）。").defineInRange("summon_count_min", 3, 1, Integer.MAX_VALUE);
        SUMMON_COUNT_MAX = builder.comment("每次召唤雪傀儡仆从的最多数量（5）。").defineInRange("summon_count_max", 5, 1, Integer.MAX_VALUE);
        SUMMON_CAP = builder.comment("每只突变雪傀儡的召唤上限，0 禁止新召唤。").defineInRange("summon_cap", 6, 0, Integer.MAX_VALUE);
        FROSTY_AURA_LEVEL = builder.comment("流髑灵魂罐给予的凛寒气场（FROSTY_AURA）等级，默认 2 级。")
                .defineInRange("frosty_aura_level", 2, 1, Integer.MAX_VALUE);
        CHILLING_ENABLED = builder.comment("允许给予寒冷聚晶。").define("chilling_enabled", true);
        CHILL_HIDE_LEVEL = builder.comment("寒冷强化自身冰冷之躯等级。").defineInRange("chill_hide_level", 3, 1, Integer.MAX_VALUE);
        ALLY_CHILL_HIDE_LEVEL = builder.comment("给予附近冰霜仆从的冰冷之躯等级。").defineInRange("ally_chill_hide_level", 1, 1, Integer.MAX_VALUE);
        ALLY_CHILL_HIDE_TICKS = builder.comment("友军冰冷之躯持续时间，20 刻为 1 秒。").defineInRange("ally_chill_hide_ticks", 900, 1, Integer.MAX_VALUE);
        ALLY_CHILL_HIDE_RADIUS = builder.comment("给予友军冰冷之躯的半径，格（10）。").defineInRange("ally_chill_hide_radius", 10.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();
        builder.pop();
    }

    private MutantSnowGolemServantConfig() {
    }

    public static double randomBetween(ForgeConfigSpec.IntValue minimum, ForgeConfigSpec.IntValue maximum,
            net.minecraft.util.RandomSource random) {
        int min = minimum.get();
        int max = Math.max(min, maximum.get());
        return min + random.nextInt(max - min + 1);
    }
}
