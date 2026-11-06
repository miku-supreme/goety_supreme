package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolemFireball;
import cn.blockforge.generated.goetysupreme.config.SupremeServantConfigs;

public final class GolemServantConfig {

    public static class Section {
        public final String sectionName;

        public final Item healItem;
        public final String sectionComment;
        public final double defHealth;
        public final double defAttack;
        public final double defSpeed;
        public final double defHealPercent;

        public Section(String sectionName, String sectionComment, Item healItem,
                double defHealth, double defAttack, double defSpeed, double defHealPercent) {
            this.sectionName = sectionName;
            this.sectionComment = sectionComment;
            this.healItem = healItem;
            this.defHealth = defHealth;
            this.defAttack = defAttack;
            this.defSpeed = defSpeed;
            this.defHealPercent = defHealPercent;
        }

        public String healComment() {
            return "主人手持回血物品右击时回血。";
        }

        public double defaultFireHeal() {
            return 0.0D;
        }

        public double defaultArmor() {
            return 0.0D;
        }

        public double defaultArmorToughness() {
            return 0.0D;
        }

        public void buildCommon(ForgeConfigSpec.Builder builder) {
            MAX_HEALTH = builder.comment("最大生命值。")
                    .defineInRange("max_health", defHealth, 1.0D, Double.MAX_VALUE);
            ATTACK_DAMAGE = builder.comment("基础攻击伤害（近战面板值）。")
                    .defineInRange("attack_damage", defAttack, 0.0D, Double.MAX_VALUE);
            MOVEMENT_SPEED = builder.comment("移动速度。")
                    .defineInRange("movement_speed", defSpeed, 0.0D, Double.MAX_VALUE);
            KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                    .defineInRange("knockback_resistance", 1.0D, 0.0D, Double.MAX_VALUE);
            ARMOR = builder.comment("护甲值。")
                    .defineInRange("armor", defaultArmor(), 0.0D, Double.MAX_VALUE);
            ARMOR_TOUGHNESS = builder.comment("护甲韧性。")
                    .defineInRange("armor_toughness", defaultArmorToughness(), 0.0D, Double.MAX_VALUE);
            FIRE_HEAL_PER_SECOND = builder.comment("踩在火焰类方块（火/灵魂火、点燃的营火、岩浆块）上时"
                            + "每秒额外回复的生命值；0 表示关闭。")
                    .defineInRange("fire_heal_per_second", defaultFireHeal(), 0.0D, Double.MAX_VALUE);
            FEED_HEAL_PERCENT = builder.comment(healComment()
                            + "按最大生命值的百分比回血，25 表示四分之一；设为 0 关闭喂食回血。")
                    .defineInRange("feed_heal_percent", defHealPercent, 0.0D, Double.MAX_VALUE);
        }

        public void build(ForgeConfigSpec.Builder builder) {
            builder.comment(sectionComment).push(sectionName);
            buildCommon(builder);
            builder.pop();
        }

        public boolean feedEnabled() {
            return healItem != Items.AIR && FEED_HEAL_PERCENT.get() > 0.0D;
        }

        public ForgeConfigSpec.DoubleValue MAX_HEALTH;
        public ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
        public ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
        public ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
        public ForgeConfigSpec.DoubleValue ARMOR;
        public ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
        public ForgeConfigSpec.DoubleValue FIRE_HEAL_PER_SECOND;
        public ForgeConfigSpec.DoubleValue FEED_HEAL_PERCENT;
    }

    public static final class FurnaceSection extends Section {
        public ForgeConfigSpec.DoubleValue COAL_BLOCK_HEALTH_BONUS;
        public ForgeConfigSpec.IntValue FURNACE_SCAN_HORIZONTAL;
        public ForgeConfigSpec.IntValue FURNACE_SCAN_VERTICAL;
        public ForgeConfigSpec.BooleanValue FIRE_BREATH_ENABLED;
        public ForgeConfigSpec.IntValue FIRE_BREATH_POTENCY;
        public ForgeConfigSpec.IntValue FIRE_BREATH_RANGE_LEVEL;
        public ForgeConfigSpec.DoubleValue FIRE_BREATH_RADIUS;
        public ForgeConfigSpec.IntValue FIRE_BREATH_DAMAGE_INTERVAL;
        public ForgeConfigSpec.DoubleValue FIRE_BREATH_DAMAGE;
        public ForgeConfigSpec.DoubleValue FIRE_BREATH_LAVA_DAMAGE;
        public ForgeConfigSpec.IntValue FIRE_BREATH_LAVA_INTERVAL_TICKS;
        public ForgeConfigSpec.DoubleValue FIRE_BREATH_FIREBALL_DAMAGE;
        public ForgeConfigSpec.IntValue FIRE_BREATH_BURNING;
        public ForgeConfigSpec.BooleanValue FIRE_BREATH_GUARANTEED_ENABLED;
        public ForgeConfigSpec.DoubleValue FIRE_BREATH_GUARANTEED_RANGE;
        public ForgeConfigSpec.IntValue FIRE_BREATH_GUARANTEED_COOLDOWN_TICKS;
        public ForgeConfigSpec.IntValue BURNING_PARTICLE_INTERVAL_TICKS;
        public ForgeConfigSpec.DoubleValue BURNING_PARTICLE_RADIUS;
        public ForgeConfigSpec.DoubleValue COAL_BLOCK_AURA_RADIUS;
        public ForgeConfigSpec.IntValue COAL_BLOCK_AURA_INTERVAL_TICKS;
        public ForgeConfigSpec.IntValue COAL_BLOCK_AURA_IGNITE_SECONDS;

        private FurnaceSection(String name, String comment, double health, double attack) {
            super(name, comment, Items.IRON_INGOT, health, attack, 0.25D, 25.0D);
        }

        @Override
        public String healComment() {
            return "主人手持铁锭右击时回血（与原版铁傀儡同款修理）；煤炭/木炭等燃料右击是燃烧，不回血。";
        }

        @Override
        public double defaultFireHeal() {
            return 1.0D;
        }

        @Override
        public double defaultArmor() {
            return 8.0D;
        }

        @Override
        public double defaultArmorToughness() {
            return 1.0D;
        }

        @Override
        public void build(ForgeConfigSpec.Builder builder) {
            builder.comment(sectionComment).push(sectionName);
            buildCommon(builder);
            COAL_BLOCK_HEALTH_BONUS = builder
                    .comment("燃烧煤炭块（燃料达到煤炭块档）时额外获得的最大生命值。")
                    .defineInRange("coal_block_health_bonus", 15.0D, 0.0D, Double.MAX_VALUE);
            FURNACE_SCAN_HORIZONTAL = builder
                    .comment("寻找可点燃熔炉的水平扫描半径（格）；默认 8。")
                    .defineInRange("furnace_scan_horizontal", 8, 1, Integer.MAX_VALUE);
            FURNACE_SCAN_VERTICAL = builder
                    .comment("寻找可点燃熔炉的竖直扫描半径（格）；默认 3。")
                    .defineInRange("furnace_scan_vertical", 3, 0, Integer.MAX_VALUE);

            builder.comment("炎呼（燃烧状态下对近身敌人的保底技能）。").push("fire_breath");
            FIRE_BREATH_ENABLED = builder.comment("允许炎呼技能。").define("enabled", true);
            FIRE_BREATH_POTENCY = builder.comment("等效强效等级；默认 2（即「强效 2」）。")
                    .defineInRange("potency", 2, 0, Integer.MAX_VALUE);
            FIRE_BREATH_RANGE_LEVEL = builder.comment("等效范围等级；默认 1（基础射程 8 格 + 1 = 有效距离 9 格）。")
                    .defineInRange("range_level", 1, 0, Integer.MAX_VALUE);
            FIRE_BREATH_RADIUS = builder.comment("炎呼命中判定的横向半径（格）：原版固定 3.0。")
                    .defineInRange("radius", 3.0D, 0.5D, Double.MAX_VALUE);
            FIRE_BREATH_DAMAGE_INTERVAL = builder.comment(
                    "喷吐期间每隔多少刻按原版炎呼法术结算一次；默认 10。",
                    "伤害走 Goety 原版 goety:fireBreath 伤害源与 LivingEntity.hurt，受原版无敌帧约束。")
                    .defineInRange("damage_interval_ticks", 10, 1, Integer.MAX_VALUE);
            FIRE_BREATH_DAMAGE = builder.comment(
                    "炎呼每一次伤害结算的固定点数（5）。",
                    "直接覆盖法术的 damage 字段，不再叠加 Goety 配置的基础伤害与强效等级。")
                    .defineInRange("damage", 5.0D, 0.0D, Double.MAX_VALUE);
            FIRE_BREATH_LAVA_DAMAGE = builder.comment(
                    "炎呼期间身体正前方一格额外结算的熔岩伤害点数（每 0.5 秒 5 点）。")
                    .defineInRange("lava_damage", 5.0D, 0.0D, Double.MAX_VALUE);
            FIRE_BREATH_LAVA_INTERVAL_TICKS = builder.comment(
                    "前方一格熔岩伤害的结算间隔（刻）；10 刻 = 每 0.5 秒一次，默认 10。")
                    .defineInRange("lava_interval_ticks", 10, 1, Integer.MAX_VALUE);
            FIRE_BREATH_FIREBALL_DAMAGE = builder.comment(
                    "炎呼期间每 0.75 秒射出的烈焰人火球直伤（6 点，无视无敌帧）。")
                    .defineInRange("fireball_damage", 6.0D, 0.0D, Double.MAX_VALUE);
            FIRE_BREATH_BURNING = builder.comment(
                    "命中后点燃目标的秒数 × burning；默认 1（即原版炎呼 burning 1，5 秒火）。")
                    .defineInRange("burning", 1, 0, Integer.MAX_VALUE);
            FIRE_BREATH_GUARANTEED_ENABLED = builder.comment(
                    "近距离炎呼触发：燃烧状态下非火免主目标进入 guaranteed_range 时必然起手，优先于近战；"
                            + "火免目标仅在主人穿下界或不洁长袍时，每次近战独立以 10% 概率待发炎呼。"
                            + "两种触发共用范围与冷却；默认开启。")
                    .define("guaranteed_enabled", true);
            FIRE_BREATH_GUARANTEED_RANGE = builder.comment("近距离必定炎呼的触发半径（格）；默认 6。")
                    .defineInRange("guaranteed_range", 6.0D, 1.0D, Double.MAX_VALUE);
            FIRE_BREATH_GUARANTEED_COOLDOWN_TICKS = builder.comment("近距离必定炎呼的冷却（刻）；20 刻为 1 秒，默认 300（15 秒）。")
                    .defineInRange("guaranteed_cooldown_ticks", 300, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.comment("燃烧状态的被动效果。").push("burning_passive");
            BURNING_PARTICLE_INTERVAL_TICKS = builder.comment("燃烧时在周围浮现火焰粒子的间隔（刻）；越大越稀疏，默认 30。")
                    .defineInRange("particle_interval_ticks", 30, 5, Integer.MAX_VALUE);
            BURNING_PARTICLE_RADIUS = builder.comment("燃烧时火焰粒子浮现的半径（格）；默认 1。")
                    .defineInRange("particle_radius", 1.0D, 0.1D, Double.MAX_VALUE);
            COAL_BLOCK_AURA_RADIUS = builder.comment("完全燃烧（煤炭块）状态点燃周围敌对生物的半径（格）；默认 1。")
                    .defineInRange("coal_block_aura_radius", 1.0D, 0.1D, Double.MAX_VALUE);
            COAL_BLOCK_AURA_INTERVAL_TICKS = builder.comment("完全燃烧状态下点燃判定的间隔（刻）；20 刻为 1 秒，默认 10（半秒）。")
                    .defineInRange("coal_block_aura_interval_ticks", 10, 2, Integer.MAX_VALUE);
            COAL_BLOCK_AURA_IGNITE_SECONDS = builder.comment("每次点燃敌对生物的秒数；默认 3。")
                    .defineInRange("coal_block_aura_ignite_seconds", 3, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.pop();
        }
    }

    public static final class MagmaSection extends Section {
        public ForgeConfigSpec.DoubleValue FIREBALL_DAMAGE;
        public ForgeConfigSpec.DoubleValue FIREBALL_EXPLOSION_RADIUS;
        public ForgeConfigSpec.DoubleValue RANGED_RANGE;
        public ForgeConfigSpec.DoubleValue MELEE_RANGE;
        public ForgeConfigSpec.DoubleValue CHASE_RANGE;
        public ForgeConfigSpec.IntValue FIREBALL_INTERVAL_TICKS;
        public ForgeConfigSpec.DoubleValue MELEE_FIRE_DAMAGE;
        public ForgeConfigSpec.IntValue MELEE_IGNITE_SECONDS;
        public ForgeConfigSpec.IntValue WATER_SLOWNESS_LEVEL;
        public ForgeConfigSpec.IntValue WATER_PETRIFY_SECONDS;
        public ForgeConfigSpec.IntValue STONE_RECOVERY_MIN_SECONDS;
        public ForgeConfigSpec.IntValue STONE_RECOVERY_MAX_SECONDS;
        public ForgeConfigSpec.DoubleValue STONE_DAMAGE_MULTIPLIER;
        public ForgeConfigSpec.DoubleValue LAVA_HEAL_PER_SECOND;

        private MagmaSection() {
            super("magma_golem", "岩浆傀儡的独立属性：140 血、6 点护甲、1 点护甲韧性、攻击力比铁傀儡高 2 点、移速略低于铁傀儡、"
                    + "免疫火焰；目标在 8 格外 24 格内站桩射恶魂同款烈焰弹（不破坏方块），8 格内近战附火焰伤害并点燃，"
                    + "24 格外的敌人会主动靠近到射程内再开火（索敌上限 32 格）；"
                    + "可在岩浆上行走并浸泡回血，踩在火上也会回血，浸水超时石化，重新浸泡熔岩恢复；修改后重启游戏生效。",
                    Blocks.MAGMA_BLOCK.asItem(), 140.0D, 17.0D, 0.24D, 25.0D);
        }

        @Override
        public String healComment() {
            return "主人手持岩浆块右击时回血；浸泡/接触熔岩时另按秒回血（默认每秒 4 点），踩在火上每秒回 1 点。";
        }

        @Override
        public double defaultFireHeal() {
            return 1.0D;
        }

        @Override
        public double defaultArmor() {
            return 6.0D;
        }

        @Override
        public double defaultArmorToughness() {
            return 1.0D;
        }

        @Override
        public void build(ForgeConfigSpec.Builder builder) {
            builder.comment(sectionComment).push(sectionName);
            buildCommon(builder);
            FIREBALL_DAMAGE = builder.comment("烈焰弹直击伤害；默认 15，"
                            + "普通烈焰弹与主人穿不洁套后升级出的狱火爆破共用这一项；"
                            + "爆炸半径另由 fireball_explosion_radius 控制，保持 3.0 不变。"
                            + "普通烈焰弹命中时直伤与爆炸都完整结算，默认配置下直击总伤约 48；"
                            + "不洁套的狱火爆破是 Goety 本体实体，仍按它自己的差值结算。")
                    .defineInRange("fireball_damage", 15.0D, 0.0D, Double.MAX_VALUE);
            FIREBALL_EXPLOSION_RADIUS = builder.comment("烈焰弹命中时的爆炸半径（格），0 关闭爆炸；默认 3.0，"
                            + "普通烈焰弹与主人穿不洁套后升级出的狱火爆破共用这一项，但两者的结算公式不同："
                            + "普通弹走原版 Explosion，衰减用 2 倍半径，3.0 在爆心是 43 点、实体伤害范围 6 格；"
                            + "狱火爆破走 Goety 的 SpellExplosion，衰减半径就是这一项，3.0 在爆心只有 22 点、"
                            + "范围 3 格（等于原版半径 1.5）。想让狱火爆破与原版 3.0 同威力，这一项要设 6.0。"
                            + "爆炸只伤人、不破坏方块。")
                    .defineInRange("fireball_explosion_radius", 3.0D, 0.0D, Double.MAX_VALUE);
            RANGED_RANGE = builder.comment("远程射程（格）：目标在近战射程之外、该距离之内时站桩射烈焰弹，默认 24。")
                    .defineInRange("ranged_range", 24.0D, 1.0D, Double.MAX_VALUE);
            MELEE_RANGE = builder.comment("近战射程（格）：目标在该距离内才追击并挥拳，默认 8。")
                    .defineInRange("melee_range", 8.0D, 1.0D, Double.MAX_VALUE);
            CHASE_RANGE = builder.comment("主动索敌距离（格）：远程射程之外的敌人也会被锁定，先主动靠到远程射程内再开火，默认 32。")
                    .defineInRange("chase_range", 32.0D, 1.0D, Double.MAX_VALUE);
            FIREBALL_INTERVAL_TICKS = builder.comment("烈焰弹射击间隔（刻）；默认 40，即 2 秒一发，"
                            + "动作长 50 刻、第 35 刻出手，所以实际周期是 50 刻（2.5 秒/发）。")
                    .defineInRange("fireball_interval_ticks", 40, 5, Integer.MAX_VALUE);
            MELEE_FIRE_DAMAGE = builder.comment("近战命中的额外火焰伤害（铁傀儡重击之上再烫这么多）。")
                    .defineInRange("melee_fire_damage", 2.0D, 0.0D, Double.MAX_VALUE);
            MELEE_IGNITE_SECONDS = builder.comment("近战命中后点燃目标的秒数。")
                    .defineInRange("melee_ignite_seconds", 10, 0, Integer.MAX_VALUE);
            WATER_SLOWNESS_LEVEL = builder.comment("浸水期间的缓慢等级（8 即缓慢 VIII）。")
                    .defineInRange("water_slowness_level", 8, 1, Integer.MAX_VALUE);
            WATER_PETRIFY_SECONDS = builder.comment("连续浸水多少秒后石化（失去移动与 AI、受到的伤害提高）。")
                    .defineInRange("water_petrify_seconds", 1, 0, Integer.MAX_VALUE);
            STONE_RECOVERY_MIN_SECONDS = builder.comment("重新浸泡熔岩恢复所需的最短秒数。")
                    .defineInRange("stone_recovery_min_seconds", 5, 0, Integer.MAX_VALUE);
            STONE_RECOVERY_MAX_SECONDS = builder.comment("重新浸泡熔岩恢复所需的最长秒数（实际取两者之间随机）。")
                    .defineInRange("stone_recovery_max_seconds", 15, 0, Integer.MAX_VALUE);
            STONE_DAMAGE_MULTIPLIER = builder.comment("石化状态下受到的伤害倍率（2.0 即额外 +100%）。")
                    .defineInRange("stone_damage_multiplier", 2.0D, 1.0D, Double.MAX_VALUE);
            LAVA_HEAL_PER_SECOND = builder.comment("浸泡/接触熔岩（含踩在岩浆面上）时"
                            + "每秒回复的生命值；0 表示关闭。")
                    .defineInRange("lava_heal_per_second", 4.0D, 0.0D, Double.MAX_VALUE);
            builder.pop();
        }
    }

    public static final Section IRON = new Section("iron_golem",
            "铁傀儡仆从的独立属性；修改后重启游戏生效。",
            Items.IRON_INGOT, 100.0D, 15.0D, 0.25D, 25.0D) {
        @Override
        public String healComment() {
            return "主人手持铁锭右击时回血（与原版铁傀儡同款修理）。";
        }
    };

    public static final FurnaceSection FURNACE = new FurnaceSection("furnace_golem",
            "熔炉傀儡的独立属性（含燃烧机制加成）：被喂煤炭时移速 +25%、被喂煤炭块时 +50%，"
                    + "攻击力随燃料档位提升；踩在火上每秒回 1 点血；修改后重启游戏生效。", 125.0D, 16.5D);

    public static final MagmaSection MAGMA = new MagmaSection();

    public static final class PrisonSection extends Section {
        public ForgeConfigSpec.DoubleValue CONVERT_CHANCE_PERCENT;
        public ForgeConfigSpec.DoubleValue CONVERT_HEALTH_PERCENT;
        public ForgeConfigSpec.DoubleValue CONVERT_MAX_HEALTH;
        public ForgeConfigSpec.BooleanValue CONVERT_REQUIRES_PRISON;
        public ForgeConfigSpec.BooleanValue TELEPORT_TO_PRISON;
        public ForgeConfigSpec.DoubleValue FEED_HEAL_AMOUNT;

        private PrisonSection(String name, String comment, double health, double attack,
                double speed) {
            super(name, comment, Items.AIR, health, attack, speed, 0.0D);
        }

        @Override
        public String healComment() {
            return "主人手持监狱钢块右击时按固定点数回血（默认 100）；钢块本身不是普通回血物品。";
        }

        @Override
        public double defaultArmor() {
            return 10.0D;
        }

        @Override
        public double defaultArmorToughness() {
            return 2.0D;
        }

        @Override
        public void build(ForgeConfigSpec.Builder builder) {
            builder.comment(sectionComment).push(sectionName);
            buildCommon(builder);
            FEED_HEAL_AMOUNT = builder.comment("主人手持监狱钢块右击时回复的生命值（固定值，默认 100）。")
                    .defineInRange("feed_heal_amount", 100.0D, 0.0D, Double.MAX_VALUE);
            CONVERT_CHANCE_PERCENT = builder.comment("击败敌人时将其收押转化为自己仆从的概率（百分比，默认 50）。")
                    .defineInRange("convert_chance_percent", 50.0D, 0.0D, Double.MAX_VALUE);
            CONVERT_MAX_HEALTH = builder.comment("可被收押敌人的最大生命值上限；血量高于该值（默认 60，即 30 颗心）"
                            + "的敌人免疫收押。首领与小首领（Goety bosses / mini_bosses、Forge bosses 标签）"
                            + "无论如何都不能被收押。")
                    .defineInRange("convert_max_health", 60.0D, 1.0D, Double.MAX_VALUE);
            CONVERT_HEALTH_PERCENT = builder.comment("转化后仆从保留的最大生命值百分比（默认 25，即四分之一血）。")
                    .defineInRange("convert_health_percent", 25.0D, 1.0D, Double.MAX_VALUE);
            CONVERT_REQUIRES_PRISON = builder.comment("收押转化是否要求傀儡已用 Goety 指路石绑定机动监狱"
                            + "（默认 true：没绑监狱的傀儡不转化任何敌人；可以绑多座，犯人会送进关押最少的那座）。")
                    .define("convert_requires_prison", true);
            TELEPORT_TO_PRISON = builder.comment("转化后是否把仆从传送到绑定机动监狱的牢房内腔并保持游荡"
                            + "（默认 true；关掉则仆从留在被收押的位置）。")
                    .define("teleport_to_prison", true);
            builder.pop();
        }
    }

    public static final PrisonSection PRISON = new PrisonSection("prison_golem",
            "监狱傀儡的独立属性：免疫缓慢与火焰、220 血、10 点护甲、2 点护甲韧性、移动速度与 Goety 红石傀儡一致（0.3）、"
                    + "体型为铁傀儡的 1.25 倍、攻击力 20，伤害浮动为攻击力的 3/4 至 5/4；"
                    + "击败敌人有概率将其收押为对应的 Goety 仆从：需先用 Goety 指路石把机动监狱"
                    + "绑定到傀儡身上（可以绑多座，收押的犯人送进关押人数最少的那座），收押成功的仆从归监狱傀儡的主人（建造它的玩家）、"
                    + "先传送到机动监狱牢房内腔并围绕监狱保持游荡，没绑定监狱则不转化；"
                    + "血量高于上限或属于首领/小首领的敌人无效；"
                    + "主人手持监狱钢块右击可回复 100 血；修改后重启游戏生效。",
            220.0D, 20.0D, 0.3D);

    public static class FrostSection extends Section {
        public ForgeConfigSpec.DoubleValue RANGED_DAMAGE;
        public ForgeConfigSpec.IntValue RANGED_INTERVAL_TICKS;
        public ForgeConfigSpec.DoubleValue RANGED_RANGE;
        public ForgeConfigSpec.DoubleValue MELEE_RANGE;
        public ForgeConfigSpec.DoubleValue CHASE_RANGE;

        private final double defArmorValue;
        private final double defToughnessValue;
        private final double defRangedDamage;
        private final double defRangedRange;

        public FrostSection(String name, String comment, double health, double attack, double speed,
                double armor, double toughness, double rangedDamage, double rangedRange) {
            super(name, comment, Items.AIR, health, attack, speed, 25.0D);
            this.defArmorValue = armor;
            this.defToughnessValue = toughness;
            this.defRangedDamage = rangedDamage;
            this.defRangedRange = rangedRange;
        }

        @Override
        public double defaultArmor() {
            return defArmorValue;
        }

        @Override
        public double defaultArmorToughness() {
            return defToughnessValue;
        }

        @Override
        public String healComment() {
            return "主人手持冰块或雪块右击时按最大生命值百分比回血（两种物品都行）；没有回血物品时恒为关闭。";
        }

        @Override
        public void build(ForgeConfigSpec.Builder builder) {
            builder.comment(sectionComment).push(sectionName);
            buildCommon(builder);
            buildRanged(builder);
            buildExtra(builder);
            builder.pop();
        }

        public void buildRanged(ForgeConfigSpec.Builder builder) {
            RANGED_DAMAGE = builder.comment("伤害型雪球的直击伤害（不再像原版雪球那样只伤烈焰人）。")
                    .defineInRange("ranged_damage", defRangedDamage, 0.0D, Double.MAX_VALUE);
            int rangedInterval = defaultRangedIntervalTicks();
            RANGED_INTERVAL_TICKS = builder.comment("两次完整远程出手动作之间的间隔（刻）：整段动作播完之后再等这么多刻"
                            + "才起下一次手，不含动作本身时长；默认 " + rangedInterval + "。")
                    .defineInRange("ranged_interval_ticks", rangedInterval, 10, Integer.MAX_VALUE);
            RANGED_RANGE = builder.comment("远程射程（格）：目标在该距离之外时改用雪球，默认 32。")
                    .defineInRange("ranged_range", defRangedRange, 1.0D, Double.MAX_VALUE);
            MELEE_RANGE = builder.comment("近战射程（格）：目标在该距离内才追击挥拳，默认 10。")
                    .defineInRange("melee_range", 10.0D, 1.0D, Double.MAX_VALUE);
            CHASE_RANGE = builder.comment("主动索敌距离（格，即 FOLLOW_RANGE 属性），默认 48。")
                    .defineInRange("chase_range", 48.0D, 1.0D, Double.MAX_VALUE);
        }

        public int defaultRangedIntervalTicks() {
            return 40;
        }

        public void buildExtra(ForgeConfigSpec.Builder builder) {
        }
    }

    public static final class IceSection extends FrostSection {
        public ForgeConfigSpec.DoubleValue MELT_DAMAGE_PER_SECOND;

        private IceSection() {
            super("ice_golem",
                    "冰傀儡的独立属性（Engender 复刻）：60 血、3 点护甲、移速 0.255、攻击力 10"
                            + "（近战伤害按铁傀儡公式浮动，实际 5～14）；近战命中附带 2 秒缓慢 I"
                            + "（主人穿齐冰霜套时改为冰冻）；"
                            + "目标在 10 格外、会飞或比它高 4 格以上时改用伤害型雪球（伤害 4、射程 32、2 秒一发，"
                            + "命中附带 5 秒缓慢 I，主人穿齐冰霜套时改为冰冻）；"
                            + "遇水或身处高温生物群系持续融化掉血；免疫摔落伤害；默认戴南瓜头、剪刀可剪下；"
                            + "主人手持冰块或雪块右击可回血；"
                            + "修改后重启游戏生效。",
                    60.0D, 10.0D, 0.255D, 3.0D, 0.0D, 4.0D, 32.0D);
        }

        @Override
        public void buildExtra(ForgeConfigSpec.Builder builder) {
            MELT_DAMAGE_PER_SECOND = builder.comment("遇水或身处高温生物群系时每秒受到的融化伤害（默认 1 点）。")
                    .defineInRange("melt_damage_per_second", 1.0D, 0.0D, Double.MAX_VALUE);
        }
    }

    public static final class SnowSection extends FrostSection {
        public ForgeConfigSpec.IntValue DEATH_COLLAPSE_TICKS;
        public ForgeConfigSpec.DoubleValue SNOW_BLOCK_AOE_DAMAGE;
        public ForgeConfigSpec.DoubleValue SLAM_RADIUS;
        public ForgeConfigSpec.DoubleValue SLAM_KNOCKBACK;
        public ForgeConfigSpec.DoubleValue SLAM_LAUNCH;
        public ForgeConfigSpec.DoubleValue SLAM_MAX_HEALTH_DAMAGE_PERCENT;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_CHANCE;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_HIGH_VALUE_CHANCE;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_SWARM_CHANCE;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_HIGH_HEALTH;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_WEAK_HEALTH;
        public ForgeConfigSpec.IntValue SNOWBALL_VOLLEY_SWARM_COUNT;
        public ForgeConfigSpec.IntValue SNOWBALL_VOLLEY_COOLDOWN_TICKS;
        public ForgeConfigSpec.IntValue SNOWBALL_VOLLEY_INTERVAL_TICKS;
        public ForgeConfigSpec.IntValue SNOWBALL_VOLLEY_MAX_TARGETS;
        public ForgeConfigSpec.IntValue SNOWBALL_VOLLEY_ROUNDS;
        public ForgeConfigSpec.DoubleValue SNOWBALL_VOLLEY_DAMAGE;
        public ForgeConfigSpec.BooleanValue FROST_BREATH_ENABLED;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_CLUSTER_RADIUS;
        public ForgeConfigSpec.IntValue FROST_BREATH_POTENCY;
        public ForgeConfigSpec.IntValue FROST_BREATH_RANGE_LEVEL;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_RADIUS;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_WIDTH_BONUS;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_DAMAGE;
        public ForgeConfigSpec.IntValue FROST_BREATH_SLOW_AMPLIFIER;
        public ForgeConfigSpec.IntValue FROST_BREATH_SLOW_TICKS;
        public ForgeConfigSpec.IntValue FROST_BREATH_DAMAGE_INTERVAL;
        public ForgeConfigSpec.BooleanValue FROST_BREATH_GUARANTEED_ENABLED;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_GUARANTEED_RANGE;
        public ForgeConfigSpec.IntValue FROST_BREATH_GUARANTEED_COOLDOWN_TICKS;
        public ForgeConfigSpec.BooleanValue FROST_BREATH_SNOWBALL_ENABLED;
        public ForgeConfigSpec.DoubleValue FROST_BREATH_SNOWBALL_DAMAGE;
        public ForgeConfigSpec.BooleanValue CHILLING_ENABLED;
        public ForgeConfigSpec.IntValue CHILL_HIDE_TICKS;
        public ForgeConfigSpec.IntValue CHILL_HIDE_LEVEL;
        public ForgeConfigSpec.IntValue CHILL_HIDE_ALLY_LEVEL;
        public ForgeConfigSpec.IntValue CHILL_HIDE_ALLY_TICKS;
        public ForgeConfigSpec.DoubleValue CHILL_HIDE_ALLY_RADIUS;
        public ForgeConfigSpec.IntValue CHILL_HIDE_COOLDOWN_TICKS;

        private SnowSection() {
            super("abominable_snow_golem",
                    "巨型雪傀儡的独立属性（Engender 复刻）：200 血、5 点护甲、移速 0.25、攻击力 20，"
                            + "体型为冰傀儡的 2 倍（2.5×5.36）；近战伤害按铁傀儡公式浮动（60% 保底 12 点 + 0~19 随机，实际 12～31）；"
                            + "近战是原地拍地板：双臂高举下砸，落地对身周范围造成同一档伤害并震出淡蓝色冲击波；"
                            + "近战射程默认 10 格（进入 10 格就停下砸地，不再追到贴脸）；"
                            + "近战命中附带 7.5 秒缓慢 II（主人穿齐冰霜套时改为冰冻）；"
                            + "目标在 10 格外、会飞或比它高 4 格以上时改扔突变雪傀儡同款方块弹体（贴图与音效换成雪块），"
                            + "直击伤害是原雪球的两倍、出手速度是原雪球的四分之三（固定 16），"
                            + "有效射程随之收窄到约 18 格、两次完整扔雪块动作之间间隔 10 刻"
                            + "（40 刻投掷动作原速播完后再空 10 刻，起手周期 2.5 秒一发），"
                            + "命中带 8 点溅射（半径 3.5 格，破裂时另有同尺的范围雪块爆炸粒子）并附带 5 秒缓慢 I（主人穿齐冰霜套时改为冰冻）；"
                            + "扔雪块时按概率追加一轮雪球弹幕（15 秒冷却，至多 8 个敌人、每个至多 16 发、"
                            + "单发伤害 2.0）：射程内只有一只敌人时，若它是高血量/首领则完全不触发，否则 30%；"
                            + "多只敌人时，目标单位是高血量/首领则降到 10%、否则 30%；"
                            + "多只敌人且其中最大生命值 ≤30 的杂兵达到 3 只时，目标不是高血量/首领则提到 50%、"
                            + "是则仍为 30%；"
                            + "死亡时有塌雪动画；免疫摔落伤害；没有 boss 血条；默认戴南瓜头、剪刀可剪下；"
                            + "主人手持冰块或雪块右击可回血；主人手持霜呼聚晶 / 冷静聚晶右击可分别解锁霜呼与寒庇技能；"
                            + "霜呼的触发判定距离为 4~10 格，只要附近有敌人（含单目标）就会考虑，拍地板动作挡着时先记下、动作一结束立刻喷；"
                             + "主目标进入 8 格内时必定起手一次霜呼（优先级高于近战、不掷概率），冷却 20 秒；"
                            + "霜呼期间身体周围缠绕冰风暴（每 0.5 秒 5 点范围伤害，判定半径 1.8 格），"
                            + "同时每 0.5 秒从嘴里甩出 4 发雪球（1 发朝目标 + 3 发散射，单发 2.0 且无视受击无敌帧）；"
                            + "雪球弹幕期间同样环绕这道冰风暴（数值一致）；"
                            + "修改后重启游戏生效。",
                    200.0D, 20.0D, 0.25D, 5.0D, 0.0D, 8.0D, 32.0D);
        }

        @Override
        public int defaultRangedIntervalTicks() {
            return 10;
        }

        @Override
        public void buildExtra(ForgeConfigSpec.Builder builder) {
            DEATH_COLLAPSE_TICKS = builder.comment("死亡时的塌雪动画刻数；默认 100。")
                    .defineInRange("death_collapse_ticks", 100, 0, Integer.MAX_VALUE);
            SNOW_BLOCK_AOE_DAMAGE = builder.comment("雪块弹体命中时对【周围其他】敌人的溅射伤害。默认 8 点。",
                            "被雪块直接砸中的那一只只吃直击伤害（= 基础雪球伤害 × 2），不再重复吃一遍溅射，",
                            "所以一次命中只会看到一个伤害数字。",
                            "溅射判定半径固定 3.5 格（与破裂时那圈范围雪块爆炸粒子同尺）。")
                    .defineInRange("snow_block_aoe_damage", 8.0D, 0.0D, Double.MAX_VALUE);

            builder.comment("拍地板：近战动作落地时的范围伤害与冲击波。").push("slam");
            SLAM_RADIUS = builder.comment("拍地板判定盒的【水平半宽】（格）：墓灵巨像是 7×7×7 方盒、半宽 3.5，",
                            "使用者要求巨型雪傀儡比墓灵巨像多 0.5 格，故默认 4.0。",
                            "形状是墓灵巨像式的正前方方盒（沿朝向中心前移 2 格），不再是纯圆形；",
                            "盒子竖直半高固定 3.5 格、中心在脚底。")
                    .defineInRange("radius", 4.0D, 1.0D, Double.MAX_VALUE);
            SLAM_KNOCKBACK = builder.comment(
                            "落地把敌人【水平】震开的击退强度（格/刻）；默认 1.3，与监狱傀儡下砸一致。",
                            "走 Goety 的 forcefulKnockBack 口径：击退抗性只打五折，抗性 1.0 的目标也推得动。")
                    .defineInRange("knockback", 1.3D, 0.0D, Double.MAX_VALUE);
            SLAM_LAUNCH = builder.comment(
                            "落地把敌人【竖直】上抛的初速上限（格/刻）；默认 0.68，约抛到 3 格高",
                            "（早期是 0.9，约 4 格）。竖直沿用 Goety 的 min(上限, 原竖直速度/2 + 击退强度)，",
                            "所以击退抗性高的目标会被折扣、抛得低一些；调大抛得更高。")
                    .defineInRange("launch", AbstractGolemServant.SLAM_LAUNCH, 0.0D, Double.MAX_VALUE);
            SLAM_MAX_HEALTH_DAMAGE_PERCENT = builder.comment(
                            "落地范围伤害在基础伤害之外，再按【目标最大生命值】的这个百分比追加（%）；",
                            "默认 4，即每个被打中的目标额外吃到自身最大生命值 4% 的伤害。")
                    .defineInRange("max_health_damage_percent", 4.0D, 0.0D, Double.MAX_VALUE);
            builder.pop();

            builder.comment("雪球弹幕：扔雪块时按概率追加的一轮速射。").push("snowball_volley");
            SNOWBALL_VOLLEY_CHANCE = builder.comment("每次扔雪块时触发弹幕的基础概率（0~1）；默认 0.3。"
                            + "射程内只有一只敌人且它是「高价值目标」时完全不触发，"
                            + "多只敌人时按 high_value_chance / swarm_chance 分档覆盖这个基础值。")
                    .defineInRange("chance", 0.3D, 0.0D, Double.MAX_VALUE);
            SNOWBALL_VOLLEY_HIGH_VALUE_CHANCE = builder.comment(
                            "射程内有多只敌人、且目标单位是「高价值目标」时的触发概率（0~1）；默认 0.1。",
                            "高价值 = 最大生命值大于 high_health_threshold，或挂有 Goety bosses / mini_bosses、",
                            "Forge bosses 标签。")
                    .defineInRange("high_value_chance", 0.1D, 0.0D, Double.MAX_VALUE);
            SNOWBALL_VOLLEY_SWARM_CHANCE = builder.comment(
                            "射程内有多只敌人、且其中脆皮杂兵达到 swarm_count 只时的触发概率（0~1）；默认 0.5。",
                            "仅当目标单位不是高价值目标时提到这个值；目标本身是高价值时仍用基础 chance。",
                            "这一档优先于 high_value_chance。")
                    .defineInRange("swarm_chance", 0.5D, 0.0D, Double.MAX_VALUE);
            SNOWBALL_VOLLEY_HIGH_HEALTH = builder.comment(
                            "「高价值目标」的最大生命值门槛：严格大于该值才计入；默认 50。")
                    .defineInRange("high_health_threshold", 50.0D, 0.0D, Double.MAX_VALUE);
            SNOWBALL_VOLLEY_WEAK_HEALTH = builder.comment(
                            "「脆皮杂兵」的最大生命值门槛：小于等于该值才计入；默认 30。")
                    .defineInRange("weak_health_threshold", 30.0D, 0.0D, Double.MAX_VALUE);
            SNOWBALL_VOLLEY_SWARM_COUNT = builder.comment(
                            "射程内脆皮杂兵达到多少只时按 swarm_chance 触发；默认 3。")
                    .defineInRange("swarm_count", 3, 1, Integer.MAX_VALUE);
            SNOWBALL_VOLLEY_COOLDOWN_TICKS = builder.comment("弹幕的冷却（刻）；20 刻为 1 秒，默认 300（15 秒）。")
                    .defineInRange("cooldown_ticks", 300, 0, Integer.MAX_VALUE);
            SNOWBALL_VOLLEY_INTERVAL_TICKS = builder.comment("弹幕两轮之间的间隔（刻）；默认 4，约等于玩家连扔雪球的速度。")
                    .defineInRange("interval_ticks", 4, 1, Integer.MAX_VALUE);
            SNOWBALL_VOLLEY_MAX_TARGETS = builder.comment("一轮弹幕至多锁定的周围敌人数；默认 8。")
                    .defineInRange("max_targets", 8, 1, Integer.MAX_VALUE);
            SNOWBALL_VOLLEY_ROUNDS = builder.comment("每个敌人至多挨到的雪球数；默认 16。")
                    .defineInRange("rounds", 16, 1, Integer.MAX_VALUE);
            SNOWBALL_VOLLEY_DAMAGE = builder.comment("弹幕里每发雪球的伤害；默认 2.0。",
                            "和霜呼嘴部的雪球一样无视受击无敌帧，但击退仍是每 0.5 秒一次。")
                    .defineInRange("damage", 2.0D, 0.0D, Double.MAX_VALUE);
            builder.pop();

            builder.comment("霜呼（需主人手持霜呼聚晶右击解锁）。").push("frost_breath");
            FROST_BREATH_ENABLED = builder.comment("允许给予霜呼聚晶。").define("enabled", true);
            FROST_BREATH_CLUSTER_RADIUS = builder.comment("统计周围敌人聚集方向的半径（格）；默认 16。")
                    .defineInRange("cluster_radius", 16.0D, 1.0D, Double.MAX_VALUE);
            FROST_BREATH_POTENCY = builder.comment("等效强效等级；默认 2（即「强效 2」）。")
                    .defineInRange("potency", 2, 0, Integer.MAX_VALUE);
            FROST_BREATH_RANGE_LEVEL = builder.comment("等效范围等级；默认 2（基础射程 8 格 + 2 = 有效距离 10 格）。")
                    .defineInRange("range_level", 2, 0, Integer.MAX_VALUE);
            FROST_BREATH_RADIUS = builder.comment("霜呼命中判定的横向半径（格）：原版固定 3.0，",
                            "喷吐偏窄，这里加宽到 4，让锥形能罩住更多敌人。")
                    .defineInRange("radius", 4.0D, 0.5D, Double.MAX_VALUE);
            FROST_BREATH_WIDTH_BONUS = builder.comment(
                            "霜呼在 radius 基础上再向左右各扩展的格数（+1）；",
                            "命中判定与视觉粒子同时使用这个加宽量。默认 1.0。")
                    .defineInRange("width_bonus", 1.0D, 0.0D, Double.MAX_VALUE);
            FROST_BREATH_DAMAGE = builder.comment(
                            "霜呼每一次伤害结算的固定点数（4）。",
                            "直接覆盖法术的 damage 字段，不再叠加 Goety 配置的基础伤害与强效等级。")
                    .defineInRange("damage", 4.0D, 0.0D, Double.MAX_VALUE);
            FROST_BREATH_SLOW_AMPLIFIER = builder.comment(
                            "霜呼命中后额外施加的缓慢等级；1 = 缓慢 II。")
                    .defineInRange("slow_amplifier", 1, 0, 255);
            FROST_BREATH_SLOW_TICKS = builder.comment(
                            "霜呼命中后额外施加的缓慢时长（刻）；100 刻 = 5 秒。")
                    .defineInRange("slow_ticks", 100, 0, Integer.MAX_VALUE);
            FROST_BREATH_DAMAGE_INTERVAL = builder.comment(
                            "喷吐期间每隔多少刻按原版霜呼法术结算一次；默认 10。",
                            "伤害走 Goety 原版 goety:frost_breath 伤害源与 LivingEntity.hurt，受原版无敌帧约束，",
                            "被无敌帧挡下的那一次不会生效——这是原版行为，调小这个值也不会让伤害变多。")
                    .defineInRange("damage_interval_ticks", 10, 1, Integer.MAX_VALUE);
            FROST_BREATH_GUARANTEED_ENABLED = builder.comment(
                            "近距离必定霜呼：主目标进入 guaranteed_range 范围内时必然起手一次霜呼，",
                            "不受下面的 chance / min_enemies / min_distance 限制，优先级高于近战；默认开启。")
                    .define("guaranteed_enabled", true);
            FROST_BREATH_GUARANTEED_RANGE = builder.comment("近距离必定霜呼的触发半径（格）；默认 8。")
                    .defineInRange("guaranteed_range", 8.0D, 1.0D, Double.MAX_VALUE);
            FROST_BREATH_GUARANTEED_COOLDOWN_TICKS = builder.comment("近距离必定霜呼的冷却（刻）；20 刻为 1 秒，默认 400（20 秒）。")
                    .defineInRange("guaranteed_cooldown_ticks", 400, 0, Integer.MAX_VALUE);
            FROST_BREATH_SNOWBALL_ENABLED = builder.comment(
                            "霜呼喷吐时从嘴里甩雪球：默认开启。",
                            "喷吐窗口（第 20~85 刻）里每 0.5 秒甩出 4 发：1 发朝当前喷吐目标、",
                            "另外 3 发随机散射；这 4 发无视目标的受击无敌帧（先把 invulnerableTime 清零再结算）。")
                    .define("snowball_enabled", true);
            FROST_BREATH_SNOWBALL_DAMAGE = builder.comment(
                            "霜呼嘴部雪球每发的伤害；默认 2.0（与雪球弹幕同档，可单独调）。")
                    .defineInRange("snowball_damage", 2.0D, 0.0D, Double.MAX_VALUE);
            builder.pop();

            builder.comment("寒庇（需主人手持冷静聚晶右击解锁）。").push("chilling");
            CHILLING_ENABLED = builder.comment("允许给予冷静聚晶。").define("enabled", true);
            CHILL_HIDE_TICKS = builder.comment("自身寒庇的持续刻数；默认 350（17.5 秒）。")
                    .defineInRange("self_ticks", 350, 1, Integer.MAX_VALUE);
            CHILL_HIDE_LEVEL = builder.comment("自身寒庇的等级；默认 4。")
                    .defineInRange("self_level", 4, 1, Integer.MAX_VALUE);
            CHILL_HIDE_ALLY_LEVEL = builder.comment("友方霜冻仆从拿到的寒庇等级；默认 1。")
                    .defineInRange("ally_level", 1, 1, Integer.MAX_VALUE);
            CHILL_HIDE_ALLY_TICKS = builder.comment("友方霜冻仆从寒庇的持续刻数；默认 350（17.5 秒）。")
                    .defineInRange("ally_ticks", 350, 1, Integer.MAX_VALUE);
            CHILL_HIDE_ALLY_RADIUS = builder.comment("给予友军寒庇的半径（格）；默认 12（2026-10-28：8 → 12，书页同口径）。")
                    .defineInRange("ally_radius", 12.0D, 0.0D, Double.MAX_VALUE);
            CHILL_HIDE_COOLDOWN_TICKS = builder.comment("寒庇的冷却（刻）；20 刻为 1 秒，默认 500（25 秒）。")
                    .defineInRange("cooldown_ticks", 500, 0, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static final IceSection ICE = new IceSection();

    public static final SnowSection SNOW = new SnowSection();

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        IRON.build(builder);
        FURNACE.build(builder);
        MAGMA.build(builder);
        PRISON.build(builder);
        ICE.build(builder);
        SNOW.build(builder);
    }

    private GolemServantConfig() {
    }
}
