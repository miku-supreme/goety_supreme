package cn.blockforge.generated.goetysupreme.config;

import java.util.List;
import net.minecraft.util.Mth;
import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;

public final class SoulReaperConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue HEALTH;
    public static final ForgeConfigSpec.DoubleValue ATTACK;
    public static final ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static final ForgeConfigSpec.DoubleValue HOSTILE_SOUL_FLYING_SPEED_PER_1000;

    public static final ForgeConfigSpec.DoubleValue HOSTILE_SOUL_DAMAGE_PER_1000;

    public static final ForgeConfigSpec.DoubleValue HOSTILE_SOUL_HEALTH_PER_1000;

    public static final ForgeConfigSpec.DoubleValue SPAWN_CHANCE;

    public static final ForgeConfigSpec.DoubleValue SPAWN_CHANCE_BONUS;

    public static final ForgeConfigSpec.IntValue SPAWN_SOUL_THRESHOLD;

    public static final ForgeConfigSpec.IntValue SPAWN_SOUL_HIGH_THRESHOLD;

    public static final ForgeConfigSpec.DoubleValue SPAWN_RADIUS;

    public static final ForgeConfigSpec.DoubleValue SPAWN_HEIGHT;

    public static final ForgeConfigSpec.DoubleValue SPAWN_BOSS_RADIUS;

    public static final ForgeConfigSpec.IntValue SPAWN_RITUAL_TICKS;

    public static final ForgeConfigSpec.IntValue SPAWN_WIGHT_DEATH_TICKS;

    public static final ForgeConfigSpec.IntValue SPAWN_SEEK_TIMEOUT;

    public static final ForgeConfigSpec.IntValue SOUL_STEAL;

    public static final ForgeConfigSpec.IntValue SAPPED_TICKS;

    public static final ForgeConfigSpec.DoubleValue BURST_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue BURST_RADIUS;

    public static final ForgeConfigSpec.DoubleValue SHOCKWAVE_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue SHOCKWAVE_RADIUS;
    public static final ForgeConfigSpec.IntValue SHOCKWAVE_WEAKNESS_TICKS;

    public static final ForgeConfigSpec.DoubleValue SLAM_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue SLAM_RADIUS;

    public static final ForgeConfigSpec.DoubleValue SLAM_SPLASH_DAMAGE;

    public static final ForgeConfigSpec.DoubleValue CONTROL_NEAR_RANGE;

    public static final ForgeConfigSpec.DoubleValue COFFIN_HEALTH;

    public static final ForgeConfigSpec.DoubleValue COFFIN_DRAIN_RADIUS;
    public static final ForgeConfigSpec.DoubleValue COFFIN_DRAIN_HEALTH;
    public static final ForgeConfigSpec.IntValue COFFIN_DRAIN_SOUL;

    public static final ForgeConfigSpec.DoubleValue COFFIN_DRAIN_BACK_RATIO;
    public static final ForgeConfigSpec.DoubleValue COFFIN_DRAIN_BACK_CAP;

    public static final ForgeConfigSpec.IntValue COFFIN_REVIVE_PAUSE_TICKS;

    public static final ForgeConfigSpec.DoubleValue SERVANT_SOUL_DAMAGE_PERCENT_PER_1000;

    public static final ForgeConfigSpec.DoubleValue SERVANT_LIFESTEAL_RATIO;

    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> SERVANT_HEALTH_BY_LEVEL;

    public static final ForgeConfigSpec.DoubleValue SERVANT_MOVEMENT_SPEED_PER_LEVEL;

    public static final ForgeConfigSpec.DoubleValue SERVANT_COFFIN_HEAL_CAP;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("渡魂之镰 / 摄魂棺配置：修改后重启游戏生效。",
                "渡魂之镰是「会飞的高级收割者」，摄魂棺是它操控的静止棺材实体。");

        builder.comment("渡魂之镰本体属性。").push("soul_reaper");
        HEALTH = builder.comment("最大生命值（225）。",
                        "注：仆从版的成长血量不看这一项，走 servant.health_by_level 的四档表。")
                .defineInRange("max_health", 225.0D, 1.0D, Double.MAX_VALUE);
        ATTACK = builder.comment("普通攻击伤害（15）。")
                .defineInRange("attack_damage", 15.0D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌半径（40 格）。")
                .defineInRange("follow_range", 40.0D, 1.0D, 256.0D);
        HOSTILE_SOUL_FLYING_SPEED_PER_1000 = builder.comment(
                        "灵魂充盈（敌对版）：索敌玩家的灵魂能量超出阈值后，每多 1000 点给飞行速度 +N（0.01）。")
                .defineInRange("soul_flying_speed_per_1000", 0.01D, 0.0D, 10.0D);
        HOSTILE_SOUL_DAMAGE_PER_1000 = builder.comment(
                        "灵魂充盈（敌对版）：超出阈值每 1000 点给全部招式 +N 伤害（1）。")
                .defineInRange("soul_damage_per_1000", 1.0D, 0.0D, 1000.0D);
        HOSTILE_SOUL_HEALTH_PER_1000 = builder.comment(
                        "灵魂充盈（敌对版）：超出阈值每 1000 点给最大生命值 +N（10）。")
                .defineInRange("soul_health_per_1000", 10.0D, 0.0D, 10000.0D);
        SOUL_STEAL = builder.comment("普通攻击偷取的灵魂能量（20）。")
                .defineInRange("soul_steal", 20, 0, 1000000);
        SAPPED_TICKS = builder.comment("普通攻击附加的侵蚀时长（刻，3 秒）。")
                .defineInRange("sapped_ticks", 60, 0, 100000);
        BURST_DAMAGE = builder.comment("灵魂大爆破伤害（20）。")
                .defineInRange("burst_damage", 20.0D, 0.0D, Double.MAX_VALUE);
        BURST_RADIUS = builder.comment("灵魂大爆破半径（8 格）。")
                .defineInRange("burst_radius", 8.0D, 0.0D, 64.0D);
        SHOCKWAVE_DAMAGE = builder.comment("操控棺材·冲击波伤害（15，魔法伤害）。")
                .defineInRange("shockwave_damage", 15.0D, 0.0D, Double.MAX_VALUE);
        SHOCKWAVE_RADIUS = builder.comment("操控棺材·冲击波半径（5 格）。")
                .defineInRange("shockwave_radius", 5.0D, 0.0D, 64.0D);
        SHOCKWAVE_WEAKNESS_TICKS = builder.comment("冲击波附带的衰弱时长（刻，5 秒）。")
                .defineInRange("shockwave_weakness_ticks", 100, 0, 100000);
        SLAM_DAMAGE = builder.comment("操控棺材·闪现下砸伤害（12）。")
                .defineInRange("slam_damage", 12.0D, 0.0D, Double.MAX_VALUE);
        SLAM_RADIUS = builder.comment("操控棺材·闪现下砸半径（3 格）。")
                .defineInRange("slam_radius", 3.0D, 0.0D, 64.0D);
        SLAM_SPLASH_DAMAGE = builder.comment("多目标时下砸附带的爆炸伤害（5）。")
                .defineInRange("slam_splash_damage", 5.0D, 0.0D, Double.MAX_VALUE);
        CONTROL_NEAR_RANGE = builder.comment("棺材距目标小于它就走冲击波，否则闪现下砸（5 格）。")
                .defineInRange("control_near_range", 5.0D, 0.0D, 64.0D);
        builder.pop();

        builder.comment("来袭生成：当玩家灵魂能量够高时，渡魂之镰有概率代替孤魂尸鬼生成。").push("ambush");
        SPAWN_CHANCE = builder.comment("基础概率（0.25 = 25%）。")
                .defineInRange("chance", 0.25D, 0.0D, 1.0D);
        SPAWN_CHANCE_BONUS = builder.comment("每满足一个加成条件追加的概率（0.10 = +10%）。")
                .defineInRange("chance_bonus", 0.10D, 0.0D, 1.0D);
        SPAWN_SOUL_THRESHOLD = builder.comment("触发来袭所需的最低灵魂能量（90000）。")
                .defineInRange("soul_threshold", 90000, 0, Integer.MAX_VALUE);
        SPAWN_SOUL_HIGH_THRESHOLD = builder.comment("额外加分的灵魂能量门槛（95000）。")
                .defineInRange("soul_high_threshold", 95000, 0, Integer.MAX_VALUE);
        SPAWN_RADIUS = builder.comment("来袭落点与判定半径（32 格）。")
                .defineInRange("radius", 32.0D, 1.0D, 256.0D);
        SPAWN_HEIGHT = builder.comment("空中落点的离地高度（2 格）。")
                .defineInRange("height", 2.0D, 0.0D, 64.0D);
        SPAWN_BOSS_RADIUS = builder.comment("判定「周围有大 boss」的半径（32 格）。")
                .defineInRange("boss_radius", 32.0D, 0.0D, 256.0D);
        SPAWN_RITUAL_TICKS = builder.comment("尸鬼特效 / 黑雾滤镜的时长（刻），也是渡魂之镰最早能现身的时间。")
                .defineInRange("ritual_ticks", 30, 1, 600);
        SPAWN_WIGHT_DEATH_TICKS = builder.comment(
                        "玩家看到尸鬼后，先让尸鬼走完死亡动画，再让渡魂之镰现身的等待刻数。",
                        "Goety 孤魂尸鬼的死亡动画是 60 刻（3 秒），默认按它取。")
                .defineInRange("wight_death_ticks", 60, 0, 600);
        SPAWN_SEEK_TIMEOUT = builder.comment("玩家看到尸鬼之前最多等多少刻；",
                        "超时不再取消来袭，而是补一段收尾黑雾后把渡魂之镰放到玩家周围 32 格的半空（默认）。")
                .defineInRange("seek_timeout", 600, 1, 24000);
        builder.pop();

        builder.comment("摄魂棺。").push("soul_coffin");
        COFFIN_HEALTH = builder.comment("血量（20）。")
                .defineInRange("max_health", 20.0D, 1.0D, Double.MAX_VALUE);
        COFFIN_DRAIN_RADIUS = builder.comment("摄取半径（12 格）。")
                .defineInRange("drain_radius", 12.0D, 0.0D, 64.0D);
        COFFIN_DRAIN_HEALTH = builder.comment("每秒摄取的生命值（4）。")
                .defineInRange("drain_health", 4.0D, 0.0D, Double.MAX_VALUE);
        COFFIN_DRAIN_SOUL = builder.comment("每秒摄取的灵魂能量（5）。")
                .defineInRange("drain_soul", 5, 0, 1000000);
        COFFIN_DRAIN_BACK_RATIO = builder.comment(
                        "敌对渡魂之镰：摄魂棺摄取到的血量返还给主人的比例（0.5 = 一半）。")
                .defineInRange("drain_back_ratio", 0.5D, 0.0D, 1.0D);
        COFFIN_DRAIN_BACK_CAP = builder.comment(
                        "上述返还每秒的上限（2 点/秒）。")
                .defineInRange("drain_back_cap", 2.0D, 0.0D, Double.MAX_VALUE);
        COFFIN_REVIVE_PAUSE_TICKS = builder.comment("复苏后停止摄取的时长（刻，5 秒）。")
                .defineInRange("revive_pause_ticks", 100, 0, 100000);
        builder.pop();

        builder.comment("渡魂之镰仆从版专属机制。").push("servant");
        SERVANT_SOUL_DAMAGE_PERCENT_PER_1000 = builder.comment(
                        "灵魂充盈：超出阈值的每 1000 点灵魂能量给每种攻击伤害 +N 比例（0.1 = 10%）。")
                .defineInRange("soul_damage_percent_per_1000", 0.10D, 0.0D, 100.0D);
        SERVANT_LIFESTEAL_RATIO = builder.comment("嗜血：近战每个敌人回复的面板伤害百分比（0.1 = 10%）。")
                .defineInRange("lifesteal_ratio", 0.10D, 0.0D, 10.0D);
        SERVANT_HEALTH_BY_LEVEL = builder.comment(
                        "成长：四档（0..3）最大生命值表，225 → 300 → 400 → 575（初始 225、满级 575）。",
                        "至少要有一项；等级超出表长时按最后一项算。")
                .defineList("health_by_level", List.of(225.0D, 300.0D, 400.0D, 575.0D),
                        value -> value instanceof Number number && number.doubleValue() > 0.0D);
        SERVANT_MOVEMENT_SPEED_PER_LEVEL = builder.comment("成长：每级额外移动速度（0.02）。")
                .defineInRange("movement_speed_per_level", 0.02D, 0.0D, 10.0D);
        SERVANT_COFFIN_HEAL_CAP = builder.comment(
                        "摄魂棺供给渡魂之镰仆从的血量上限基数（2 点/秒，每升一级再 +1 点/秒）。")
                .defineInRange("coffin_heal_cap", 2.0D, 0.0D, Double.MAX_VALUE);
        builder.pop();

        SPEC = builder.build();
    }

    public static double servantHealthForLevel(int level) {
        List<?> table = SERVANT_HEALTH_BY_LEVEL.get();
        if (table.isEmpty()) {
            return 225.0D;
        }
        int index = Mth.clamp(level, 0, table.size() - 1);
        Object value = table.get(index);
        return value instanceof Number number ? number.doubleValue() : 225.0D;
    }

    public static double coffinHealCapForLevel(int level) {
        return SERVANT_COFFIN_HEAL_CAP.get() + Math.max(0, level);
    }

    private SoulReaperConfig() {
    }
}
