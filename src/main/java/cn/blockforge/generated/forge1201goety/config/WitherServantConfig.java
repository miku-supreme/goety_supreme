package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class WitherServantConfig {

    public static ForgeConfigSpec.BooleanValue BLOCK_DESTRUCTION_ENABLED;

    public static ForgeConfigSpec.DoubleValue RAGING_ARMOR;
    public static ForgeConfigSpec.DoubleValue RAGING_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue RAGING_KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue RAGING_REGEN_PER_SECOND;
    public static ForgeConfigSpec.DoubleValue RAGING_MAX_DAMAGE_PER_HIT;

    public static ForgeConfigSpec.DoubleValue ENHANCED_SKULL_CHANCE;
    public static ForgeConfigSpec.DoubleValue ENHANCED_SKULL_CHANCE_RAGING;
    public static ForgeConfigSpec.DoubleValue SKULL_EXPLOSION_POWER_RAGING;

    public static ForgeConfigSpec.BooleanValue MINIONS_ENABLED;
    public static ForgeConfigSpec.IntValue MINION_COUNT;

    public static ForgeConfigSpec.BooleanValue EXTINCTION_ENABLED;
    public static ForgeConfigSpec.IntValue EXTINCTION_CHARGE_SECONDS;
    public static ForgeConfigSpec.IntValue EXTINCTION_COOLDOWN_SECONDS;
    public static ForgeConfigSpec.IntValue EXTINCTION_RADIUS;
    public static ForgeConfigSpec.IntValue EXTINCTION_BOSS_TIER_MIN_HEALTH;
    public static ForgeConfigSpec.IntValue EXTINCTION_BOSS_DAMAGE;
    public static ForgeConfigSpec.IntValue EXTINCTION_BOSS_WITHER_SECONDS;
    public static ForgeConfigSpec.IntValue EXTINCTION_FALLBACK_DAMAGE;
    public static ForgeConfigSpec.IntValue EXTINCTION_PLAYER_DAMAGE;

    public static ForgeConfigSpec.BooleanValue RIDING_ENABLED;
    public static ForgeConfigSpec.DoubleValue RIDING_SPEED;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("凋灵仆从的行为与数值配置；修改后重启游戏生效。").push("wither_servant");

        BLOCK_DESTRUCTION_ENABLED = builder.comment("是否允许凋灵仆从破坏方块（冲撞熔穿、蓝色凋灵之首的爆炸拆块）。"
                + "默认关闭：仆从完全不破坏任何方块，只在配置开启且规则允许生物破坏时才会拆方块。")
                .define("block_destruction_enabled", false);

        RAGING_ARMOR = builder.comment("狂暴之星赋予的护甲值：只有被喂过狂暴之星（胸口嵌星）的凋灵仆从才有这项加成，"
                + "未镶星为 0；镶星后永久生效，与穿不穿凋灵护甲（狂暴状态）无关。")
                .defineInRange("raging_armor", 16.0D, 0.0D, Double.MAX_VALUE);
        RAGING_TOUGHNESS = builder.comment("狂暴之星赋予的护甲韧性：与护甲值同理，只随镶星存在，未镶星为 0。")
                .defineInRange("raging_toughness", 8.0D, 0.0D, Double.MAX_VALUE);
        RAGING_KNOCKBACK_RESISTANCE = builder.comment("狂暴形态的击退抗性。")
                .defineInRange("raging_knockback_resistance", 0.6D, 0.0D, Double.MAX_VALUE);
        RAGING_REGEN_PER_SECOND = builder.comment("每秒自动回复的生命值：常驻回血，同原版凋灵"
                + "（每 20 刻结算一次），与是否狂暴、是否索敌统统无关。")
                .defineInRange("raging_regen_per_second", 2.0D, 0.0D, Double.MAX_VALUE);
        RAGING_MAX_DAMAGE_PER_HIT = builder.comment("狂暴形态单次受到的伤害上限（限伤）。0 表示不限。")
                .defineInRange("raging_max_damage_per_hit", 50.0D, 0.0D, Double.MAX_VALUE);

        ENHANCED_SKULL_CHANCE = builder.comment("普通状态每次射击射出强化凋灵骷髅头的概率（双倍伤害、双倍飞行速度、"
                + "白色能量描边）。0.1 即 10%。")
                .defineInRange("enhanced_skull_chance", 0.1D, 0.0D, Double.MAX_VALUE);
        ENHANCED_SKULL_CHANCE_RAGING = builder.comment("狂暴状态下每次射击射出强化凋灵骷髅头的概率。0.35 即 35%。")
                .defineInRange("enhanced_skull_chance_raging", 0.35D, 0.0D, Double.MAX_VALUE);
        SKULL_EXPLOSION_POWER_RAGING = builder.comment("狂暴形态下普通凋灵之首砸地的爆炸威力（原版 1.0；"
                + "强化凋灵骷髅头固定 2.0，不受此项影响）。默认 1.5：狂暴后普通头的爆炸范围比平时大一半。")
                .defineInRange("skull_explosion_power_raging", 1.5D, 1.0D, Double.MAX_VALUE);

        MINIONS_ENABLED = builder.comment("凋灵骷髅仆从的召唤开关：仅在狂暴状态且生命值降至最大值一半或更低时触发一次，"
                + "召唤出的仆从以凋灵仆从本尊为主人。").define("minions_enabled", true);
        MINION_COUNT = builder.comment("狂暴状态触发时召唤的 Goety 凋灵骷髅仆从数量。")
                .defineInRange("minion_count", 4, 0, Integer.MAX_VALUE);

        EXTINCTION_ENABLED = builder.comment("是否启用狂暴形态的“凋零灭绝”技能。").define("extinction_enabled", true);
        EXTINCTION_CHARGE_SECONDS = builder.comment("凋零灭绝的蓄力时长（秒），蓄力期间释放大量烟雾并定身、"
                + "不释放冲撞技能、三头射击频率减半；蓄力音效只在开场播一次，蓄力中途丢失索敌不会打断灭绝。")
                .defineInRange("extinction_charge_seconds", 10, 1, Integer.MAX_VALUE);
        EXTINCTION_COOLDOWN_SECONDS = builder.comment("两次凋零灭绝之间的冷却（秒）。")
                .defineInRange("extinction_cooldown_seconds", 60, 5, Integer.MAX_VALUE);
        EXTINCTION_RADIUS = builder.comment("凋零灭绝的杀伤半径（格）。爆炸本身永远不破坏方块。")
                .defineInRange("extinction_radius", 64, 8, Integer.MAX_VALUE);
        EXTINCTION_BOSS_TIER_MIN_HEALTH = builder.comment("血量上限达到该值的生物按 Boss/小 Boss 规则处理：不直接击杀，"
                + "改为风暴凋灵之首同款强伤害。")
                .defineInRange("extinction_boss_tier_min_health", 200, 20, Integer.MAX_VALUE);
        EXTINCTION_BOSS_DAMAGE = builder.comment("对不能秒杀的目标造成的强伤害；非玩家首击就穿盾、使徒/焰魔等普通限伤"
                + "以及 Goety 自有的受击硬免疫，被下界减伤/护甲减掉的差额会补足，所以这一发稳定打满该数值；"
                + "不穿真无敌（isInvulnerable() 直接跳过）。")
                .defineInRange("extinction_boss_damage", 50, 0, Integer.MAX_VALUE);
        EXTINCTION_BOSS_WITHER_SECONDS = builder.comment("对 Boss/小 Boss/高血量生物附加凋零 III 的时长（秒）。")
                .defineInRange("extinction_boss_wither_seconds", 15, 0, Integer.MAX_VALUE);
        EXTINCTION_FALLBACK_DAMAGE = builder.comment("普通穿盾命中均被拒绝时的阶段免伤兜底伤害；不穿真无敌，不对玩家启用。")
                .defineInRange("extinction_fallback_damage", 30, 0, Integer.MAX_VALUE);
        EXTINCTION_PLAYER_DAMAGE = builder.comment("凋零灭绝对玩家只造成的普通伤害：一次、不穿普通限伤、"
                + "也不越过受击无敌帧，不附加凋零效果，就是一次很普通的伤害。")
                .defineInRange("extinction_player_damage", 20, 0, Integer.MAX_VALUE);

        RIDING_ENABLED = builder.comment("主人能否空手右击骑乘凋灵仆从（WASD 移动、长按空格持续上升、长按左 Ctrl 持续下降、按左 Shift 脱离；骑乘期间原版“潜行下马”已禁用，Ctrl 只做下降）。")
                .define("riding_enabled", true);
        RIDING_SPEED = builder.comment("骑乘时的基础飞行速度（格/刻）。").defineInRange("riding_speed", 0.55D, 0.1D, Double.MAX_VALUE);

        builder.pop();
    }

    public static boolean blockDestructionAllowed() {
        return Boolean.TRUE.equals(BLOCK_DESTRUCTION_ENABLED.get());
    }

    private WitherServantConfig() {
    }
}
