package cn.blockforge.generated.goetysupreme.config;

import java.util.List;
import net.minecraftforge.common.ForgeConfigSpec;

public final class SakuraSpiritConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static ForgeConfigSpec.DoubleValue SLASH_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SLASH_MIN_RANGE;
    public static ForgeConfigSpec.DoubleValue SLASH_MAX_RANGE;
    public static ForgeConfigSpec.DoubleValue SLASH_SELF_HEAL;
    public static ForgeConfigSpec.IntValue LOW_HEALTH_PERCENT;
    public static ForgeConfigSpec.DoubleValue FLEE_KEEP_DISTANCE;
    public static ForgeConfigSpec.DoubleValue REENGAGE_DISTANCE;

    public static ForgeConfigSpec.IntValue SUMMON_LIMIT;
    public static ForgeConfigSpec.IntValue SUMMON_LIFESPAN;
    public static ForgeConfigSpec.IntValue SUMMON_LIFESPAN_RANDOM;
    public static ForgeConfigSpec.IntValue SUMMON_DURATION_BONUS;
    public static ForgeConfigSpec.BooleanValue SCAVENGE_GEAR;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> PINK_ARMOR_KEYWORDS;
    public static ForgeConfigSpec.BooleanValue NEAR_DEATH_TELEPORT;
    public static ForgeConfigSpec.BooleanValue SLASH_VOLLEY;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("樱之灵的属性、索敌与招式配置（仆从与野生种通用）。").push("sakura_spirit");

        MAX_HEALTH = builder.comment("最大生命值。")
                .defineInRange("max_health", 15.0D, 1.0D, Double.MAX_VALUE);
        ATTACK_DAMAGE = builder.comment("近战攻击伤害。")
                .defineInRange("attack_damage", 3.0D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("护甲值。")
                .defineInRange("armor", 0.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                .defineInRange("knockback_resistance", 0.0D, 0.0D, Double.MAX_VALUE);
        MOVEMENT_SPEED = builder.comment("移动速度。")
                .defineInRange("movement_speed", 0.25D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌距离（格）：锁定目标与保持追击的距离上限。")
                .defineInRange("follow_range", 64.0D, 1.0D, 2048.0D);

        SLASH_DAMAGE = builder.comment("粉色斩击弹幕的固定单发伤害；保持 0 则沿用现行为"
                + "（跟随樱之灵的近战攻击伤害，含手中剑的加成）。")
                .defineInRange("slash_damage", 0.0D, 0.0D, Double.MAX_VALUE);
        SLASH_MIN_RANGE = builder.comment("斩击弹幕的最近使用距离（格）：贴身的连发判定与近战避让沿用原作风味。")
                .defineInRange("slash_min_range", 3.0D, 0.0D, Double.MAX_VALUE);
        SLASH_MAX_RANGE = builder.comment("斩击弹幕的最远使用距离（格），即“中距离”窗口的外沿。")
                .defineInRange("slash_max_range", 8.0D, 1.0D, Double.MAX_VALUE);
        SLASH_SELF_HEAL = builder.comment("斩击命中目标且自身血量低于 60% 时回复的生命值；设为 0 关闭。")
                .defineInRange("slash_self_heal", 2.0D, 0.0D, Double.MAX_VALUE);
        LOW_HEALTH_PERCENT = builder.comment("濒死阈值（血量百分比）：低于该值时濒死瞬移触发、斩击弹幕无视冷却连发。")
                .defineInRange("low_health_percent", 30, 1, Integer.MAX_VALUE);
        FLEE_KEEP_DISTANCE = builder.comment("低血量风筝时与敌人保持的距离（格）：血量低于濒死阈值后，"
                + "敌人靠得比这更近就再次瞬移走，落点也至少选在这条距离之外；距离更远时会缓步接近到这附近。")
                .defineInRange("flee_keep_distance", 6.0D, 1.0D, Double.MAX_VALUE);
        REENGAGE_DISTANCE = builder.comment("血量回升到濒死阈值以上后，与敌人相距超过该距离（格）时"
                + "瞬移回到敌人身边恢复进攻；设为 0 则回升后不追加瞬移靠近。")
                .defineInRange("reengage_distance", 12.0D, 0.0D, Double.MAX_VALUE);

        SUMMON_LIMIT = builder.comment("同一召唤者在 256 格内可维持的樱之灵仆从数量上限。")
                .defineInRange("summon_limit", 12, 1, Integer.MAX_VALUE);
        SUMMON_LIFESPAN = builder.comment("召唤仆从的存活时长基数（刻），默认 2000（100 秒）："
                + "每只樱之灵在基数上再随机加 0~summon_lifespan_random 刻。")
                .defineInRange("summon_lifespan", 100 * 20, 20, Integer.MAX_VALUE);
        SUMMON_LIFESPAN_RANDOM = builder.comment("每只樱之灵在寿命基数上额外随机增加的刻数，"
                + "默认 2000（100 秒），即单只寿命 100~200 秒随机。")
                .defineInRange("summon_lifespan_random", 100 * 20, 0, Integer.MAX_VALUE);
        SUMMON_DURATION_BONUS = builder.comment("持久附魔每一级额外增加的寿命（刻），默认 1320（66 秒）。")
                .defineInRange("summon_duration_bonus", 66 * 20, 0, Integer.MAX_VALUE);
        SCAVENGE_GEAR = builder.comment("樱之灵是否随机穿戴拾来的剑与盔甲并随机附魔：剑按面板伤害区间抽取（5-6:20%，"
                + "7-8:50%，9-11:25%，12-13:5%，4以下与14以上不抽）；盔甲按注册名归组成套后整套抽取——原版套按四件护甲值总和分档掷骰（≤7:10%，8-11:30%，"
                + "12-15:50%，16-20:10%，21+不抽），非原版套只有偏粉的能进池；同档内偏粉优先、"
                + "整套韧性越高抽中概率越低；全部落空则不穿盔甲。护腿与靴子按原作额外带一条匿饰"
                + "（匿饰附魔来自 sakura_sword 模组）。")
                .define("scavenge_gear", true);
        PINK_ARMOR_KEYWORDS = builder.comment("“颜色偏粉”盔甲套的判定关键词：套内任一物品的注册名"
                + "（命名空间+名字）或盔甲材质名包含其中任一词（小写匹配）即视为偏粉套。偏粉套在同档位里"
                + "优先被抽中，且是唯一允许进池的非原版盔甲；改完需重启生效。")
                .defineList("pink_armor_keywords",
                        List.of("pink", "cherry", "sakura", "blossom", "rose", "petal", "magenta", "fuchsia"),
                        each -> each instanceof String);
        NEAR_DEATH_TELEPORT = builder.comment("是否启用濒死风筝：血量低于阈值时不再近战贴身，"
                + "而是传送拉开并按 flee_keep_distance 与敌人保持距离；血量回升后按 reengage_distance 传送回身。")
                .define("near_death_teleport", true);
        SLASH_VOLLEY = builder.comment("是否启用中距离的粉色斩击弹幕。")
                .define("slash_volley", true);

        builder.pop();
    }

    public static float lowHealthRatio() {
        return Math.max(1, LOW_HEALTH_PERCENT.get()) / 100.0F;
    }

    public static double fleeKeepDistance() {
        return Math.max(1.0D, FLEE_KEEP_DISTANCE.get());
    }

    public static double reengageDistance() {
        return Math.max(0.0D, REENGAGE_DISTANCE.get());
    }

    public static float slashDamageOrZero() {
        return SLASH_DAMAGE.get().floatValue() > 0.0F ? SLASH_DAMAGE.get().floatValue() : 0.0F;
    }

    private SakuraSpiritConfig() {
    }
}
