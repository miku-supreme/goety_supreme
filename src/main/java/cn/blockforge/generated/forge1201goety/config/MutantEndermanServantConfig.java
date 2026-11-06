package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.GeneratedMod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantSnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeServantConfigs;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class MutantEndermanServantConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static ForgeConfigSpec.BooleanValue CLAW_ENABLED;
    public static ForgeConfigSpec.IntValue CLAW_REACH;

    public static ForgeConfigSpec.BooleanValue TELEPORT_ENABLED;
    public static ForgeConfigSpec.IntValue TELEPORT_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue TELEPORT_MAX_DISTANCE;
    public static ForgeConfigSpec.IntValue TELEPORT_COOLDOWN_SECONDS;

    public static ForgeConfigSpec.BooleanValue TELESMASH_ENABLED;
    public static ForgeConfigSpec.IntValue TELESMASH_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue TELESMASH_MAX_DISTANCE;
    public static ForgeConfigSpec.IntValue TELESMASH_DAMAGE;
    public static ForgeConfigSpec.IntValue TELESMASH_COOLDOWN_SECONDS;

    public static ForgeConfigSpec.BooleanValue SCREAM_ENABLED;
    public static ForgeConfigSpec.IntValue SCREAM_DAMAGE;
    public static ForgeConfigSpec.IntValue SCREAM_RADIUS;
    public static ForgeConfigSpec.IntValue SCREAM_COOLDOWN_SECONDS;
    public static ForgeConfigSpec.BooleanValue SCREAM_DEBUFFS;

    public static ForgeConfigSpec.BooleanValue BLOCK_THROW_ENABLED;
    public static ForgeConfigSpec.BooleanValue BLOCK_GRAB_ENABLED;
    public static ForgeConfigSpec.IntValue BLOCK_THROW_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue BLOCK_THROW_MAX_DISTANCE;
    public static ForgeConfigSpec.IntValue BLOCK_GRAB_COOLDOWN_SECONDS;
    public static ForgeConfigSpec.DoubleValue RUBBLE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue RUBBLE_AREA_DAMAGE;

    public static ForgeConfigSpec.BooleanValue CLONES_ENABLED;
    public static ForgeConfigSpec.IntValue CLONE_COUNT;
    public static ForgeConfigSpec.IntValue CLONE_COOLDOWN_SECONDS;

    public static ForgeConfigSpec.IntValue DEATH_FADE_TICKS;
    public static ForgeConfigSpec.BooleanValue CHORUS_HEAL_ENABLED;
    public static ForgeConfigSpec.DoubleValue CHORUS_HEAL_AMOUNT;
    public static ForgeConfigSpec.IntValue VOID_TOUCHED_SECONDS;
    public static ForgeConfigSpec.BooleanValue VOID_SHARD_ENABLED;
    public static ForgeConfigSpec.DoubleValue VOID_SHARD_HEALTH;
    public static ForgeConfigSpec.DoubleValue WATCHLING_TELEPORT_CHANCE;

    public static ForgeConfigSpec.IntValue WATCHLING_TELEPORT_COUNT;
    public static ForgeConfigSpec.DoubleValue WATCHLING_CLONE_CHANCE;
    public static ForgeConfigSpec.IntValue WATCHLING_CLONE_MIN;
    public static ForgeConfigSpec.IntValue WATCHLING_CLONE_MAX;
    public static ForgeConfigSpec.IntValue WATCHLING_CAP;
    public static ForgeConfigSpec.BooleanValue VOID_ECHO_ENABLED;
    public static ForgeConfigSpec.DoubleValue VOID_ECHO_HEALTH;
    public static ForgeConfigSpec.DoubleValue VOID_ECHO_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.IntValue VOID_ECHO_PROTECTION_TICKS;
    public static ForgeConfigSpec.DoubleValue VOID_TOUCHED_CHANCE;
    public static ForgeConfigSpec.BooleanValue VOID_EYE_ENABLED;
    public static ForgeConfigSpec.DoubleValue VOID_EYE_HEALTH;
    public static ForgeConfigSpec.IntValue VOID_EYE_SHIELDING_LEVEL;
    public static ForgeConfigSpec.IntValue VOID_EYE_RALLYING_LEVEL;
    public static ForgeConfigSpec.IntValue VOID_EYE_DEFLECTIVE_LEVEL;

    public static ForgeConfigSpec.DoubleValue FULL_UPGRADE_ARMOR;

    public static ForgeConfigSpec.DoubleValue FULL_UPGRADE_ARMOR_TOUGHNESS;

    public static ForgeConfigSpec.DoubleValue FULL_UPGRADE_DAMAGE_MULTIPLIER;

    public static ForgeConfigSpec.IntValue TELESMASH_LANDING_DAMAGE;

    public static ForgeConfigSpec.DoubleValue TELESMASH_AOE_RADIUS;

    public static ForgeConfigSpec.DoubleValue TELESMASH_FRONT_RADIUS;

    public static ForgeConfigSpec.DoubleValue TELESMASH_FRONT_HALF_ANGLE;

    public static ForgeConfigSpec.DoubleValue WATCHLING_ENDERMAN_CHANCE;
    private static ForgeConfigSpec.IntValue RESTORED_SKILLS_REVISION;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("末影人突变体仆从的属性与招式配置。").push("mutant_enderman_servant");

        MAX_HEALTH = builder.comment("最大生命值，与 Mutant Monsters 原版突变末影人一致为 200。")
                .defineInRange("max_health", 200.0D, 1.0D, Double.MAX_VALUE);
        ATTACK_DAMAGE = builder.comment("基础攻击伤害（爪击在原版还会按出手臂追加 1~3 点）。")
                .defineInRange("attack_damage", 10.0D, 0.0D, Double.MAX_VALUE);
        MOVEMENT_SPEED = builder.comment("移动速度，原版突变末影人为 0.3。")
                .defineInRange("movement_speed", 0.3D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，原版突变末影人为 1.0（完全免疫击退）。")
                .defineInRange("knockback_resistance", 1.0D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("护甲值。")
                .defineInRange("armor", 0.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌范围，原版突变末影人为 96。")
                .defineInRange("follow_range", 96.0D, 1.0D, 2048.0D);

        CLAW_ENABLED = builder.comment("是否启用近身爪击（MELEE 动画，第 3 刻结算 4 格扇形伤害并击飞）。")
                .define("claw_enabled", true);
        CLAW_REACH = builder.comment("爪击触发距离（格）。")
                .defineInRange("claw_reach", 4, 1, Integer.MAX_VALUE);

        TELEPORT_ENABLED = builder.comment("是否启用传送突袭（TELEPORT 动画：闪现到目标附近再近战）。")
                .define("teleport_enabled", true);
        TELEPORT_MIN_DISTANCE = builder.comment("目标距离至少多少格才会考虑传送拉近。")
                .defineInRange("teleport_min_distance", 8, 2, Integer.MAX_VALUE);
        TELEPORT_MAX_DISTANCE = builder.comment("目标距离超过多少格就不再追（走普通寻路）。")
                .defineInRange("teleport_max_distance", 32, 4, Integer.MAX_VALUE);
        TELEPORT_COOLDOWN_SECONDS = builder.comment("两次传送突袭之间的最短间隔（秒）。")
                .defineInRange("teleport_cooldown_seconds", 3, 1, Integer.MAX_VALUE);

        TELESMASH_ENABLED = builder.comment("是否启用天坠砸击：第 18 刻把目标传送到高处，"
                + "造成穿透伤害；起手附加缓慢和反胃。")
                .define("telesmash_enabled", true);
        TELESMASH_MIN_DISTANCE = builder.comment("天坠砸击的最近触发距离（格）；默认允许近战触发。")
                .defineInRange("telesmash_min_distance", 0, 0, Integer.MAX_VALUE);
        TELESMASH_MAX_DISTANCE = builder.comment("天坠砸击的最远触发距离（格）。")
                .defineInRange("telesmash_max_distance", 40, 4, Integer.MAX_VALUE);
        TELESMASH_DAMAGE = builder.comment("天坠砸击对正中目标的伤害，原版为 6（穿透护盾的伤害类型）。")
                .defineInRange("telesmash_damage", 9, 0, Integer.MAX_VALUE);
        TELESMASH_LANDING_DAMAGE = builder.comment("天坠砸击命中后，目标从高处落地时必定额外承受的摔落伤害（5）。"
                + "按摔落伤害类型结算，所以照常吃摔落保护 / 缓降等原版减免；0 关闭。")
                .defineInRange("telesmash_landing_damage", 5, 0, Integer.MAX_VALUE);
        TELESMASH_COOLDOWN_SECONDS = builder.comment("两次天坠砸击之间的最短间隔（秒）。")
                .defineInRange("telesmash_cooldown_seconds", 6, 1, Integer.MAX_VALUE);
        TELESMASH_AOE_RADIUS = builder.comment("天坠砸击的范围半径（格，范围招式）："
                + "主目标周围这个半径内的敌人会一起被掀上天、吃同一份伤害与落地摔伤。0 表示只打主目标。")
                .defineInRange("telesmash_aoe_radius", 4.0D, 0.0D, 32.0D);
        TELESMASH_FRONT_RADIUS = builder.comment("天坠砸击的第二个圆心（不止以目标为圆心，"
                + "自己身前较大范围的敌人也会被击飞）：以自己为圆心、朝当前索敌 / 视线方向的扇区内，"
                + "这个半径内的敌人一起被掀上天。0 关闭。")
                .defineInRange("telesmash_front_radius", 8.0D, 0.0D, 32.0D);
        TELESMASH_FRONT_HALF_ANGLE = builder.comment("天坠砸击身前扇区的左右半角（度）：默认 60，即身前 ±60°。")
                .defineInRange("telesmash_front_half_angle", 60.0D, 1.0D, 180.0D);

        SCREAM_ENABLED = builder.comment("是否启用尖啸：第 40 刻范围穿透伤害和减益，"
                + "后续持续震慑；淋雨时更容易触发。")
                .define("scream_enabled", true);
        SCREAM_DAMAGE = builder.comment("尖啸的范围伤害，原版为 4（穿透护盾的伤害类型）。")
                .defineInRange("scream_damage", 4, 0, Integer.MAX_VALUE);
        SCREAM_RADIUS = builder.comment("尖啸的水平作用半径（格），原版 20。")
                .defineInRange("scream_radius", 20, 4, Integer.MAX_VALUE);
        SCREAM_COOLDOWN_SECONDS = builder.comment("两次尖啸之间的最短间隔（秒）。")
                .defineInRange("scream_cooldown_seconds", 20, 5, Integer.MAX_VALUE);
        SCREAM_DEBUFFS = builder.comment("尖啸是否附加原版减益：缓慢IV 6秒、50%中毒、75%虚弱、"
                + "约67%饥饿、75%反胃。")
                .define("scream_debuffs", true);

        BLOCK_THROW_ENABLED = builder.comment("是否启用投掷方块（THROW 动画，弹体走 Mutant Monsters 原版的 "
                + "ThrowableBlock，观感与野生突变末影人完全一致）。")
                .define("block_throw_enabled", true);
        BLOCK_GRAB_ENABLED = builder.comment("是否允许仆从从世界里抓方块垫手（与原版一样需要 mobGriefing 开启；"
                + "关闭后只有空手近战/传送/尖啸，不会破坏地形）。")
                .define("block_grab_enabled", true);
        BLOCK_THROW_MIN_DISTANCE = builder.comment("投掷方块的最近距离（格），贴身时改用近战。")
                .defineInRange("block_throw_min_distance", 6, 1, Integer.MAX_VALUE);
        BLOCK_THROW_MAX_DISTANCE = builder.comment("投掷方块的最远距离（格）。")
                .defineInRange("block_throw_max_distance", 28, 4, Integer.MAX_VALUE);
        BLOCK_GRAB_COOLDOWN_SECONDS = builder.comment("抓方块判定间隔（秒），原版节奏约 10~14 秒。")
                .defineInRange("block_grab_cooldown_seconds", 12, 1, Integer.MAX_VALUE);
        RUBBLE_DAMAGE = builder.comment("碎块正中目标的伤害（原版按投掷者攻击伤害结算）。")
                .defineInRange("rubble_damage", 10.0D, 0.0D, Double.MAX_VALUE);
        RUBBLE_AREA_DAMAGE = builder.comment("碎块落点 2.5 格溅射伤害。")
                .defineInRange("rubble_area_damage", 6.0D, 0.0D, Double.MAX_VALUE);

        CLONES_ENABLED = builder.comment("是否启用分身术：持块时也能发动，本体变为末魂形态，并召唤有主人归属的分身。"
                + "技能结束、本体受击或死亡后清理，不产生野生分身。")
                .define("clones_enabled", true);
        CLONE_COUNT = builder.comment("每次分身术召唤的末魂分身数量。")
                .defineInRange("clone_count", 7, 1, Integer.MAX_VALUE);
        CLONE_COOLDOWN_SECONDS = builder.comment("分身术结束到下次发动的最短间隔（秒）。")
                .defineInRange("clone_cooldown_seconds", 30, 10, Integer.MAX_VALUE);

        DEATH_FADE_TICKS = builder.comment("死亡动画总时长，MM 原版为 280 刻，第 80 刻开始溶解。")
                .defineInRange("death_fade_ticks", 280, 20, Integer.MAX_VALUE);

        CHORUS_HEAL_ENABLED = builder.comment("是否允许主人手持紫颂果右击回血"
                + "（与 Goety 看守灵仆从同一套机制：消耗 1 个紫颂果、回固定血量并冒治疗粒子）。")
                .define("chorus_fruit_heal_enabled", true);
        CHORUS_HEAL_AMOUNT = builder.comment("每喂 1 个紫颂果回复的生命值，看守灵仆从为 5。")
                .defineInRange("chorus_fruit_heal_amount", 5.0D, 0.0D, Double.MAX_VALUE);

        VOID_TOUCHED_SECONDS = builder.comment("虚空回响强化：伤害成功命中后有 35% 概率施加虚空之蚀 I，持续秒数。")
                .defineInRange("void_touched_seconds", 5, 1, Integer.MAX_VALUE);

        RESTORED_SKILLS_REVISION = builder.comment("技能恢复配置迁移版本，自动维护。")
                .defineInRange("restored_skills_revision", 0, 0, Integer.MAX_VALUE);
        builder.comment("强化配置；enabled 控制是否接受新的强化，已有强化状态保留。概率范围为 0 到 1；修改后重启生效。").push("upgrades");
        VOID_SHARD_ENABLED = builder.comment("允许给予虚空碎片。").define("void_shard_enabled", true);
        VOID_SHARD_HEALTH = builder.comment("虚空碎片增加的最大生命值。").defineInRange("void_shard_health", 25.0D, 0.0D, Double.MAX_VALUE);
        WATCHLING_TELEPORT_CHANCE = builder.comment("瞬移召唤看守灵的概率（默认 50%）。").defineInRange("watchling_teleport_chance", 0.5D, 0.0D, 1.0D);
        WATCHLING_TELEPORT_COUNT = builder.comment("瞬移召唤成功时一次召出的数量（默认 2 个）。").defineInRange("watchling_teleport_count", 2, 1, Integer.MAX_VALUE);
        WATCHLING_CLONE_CHANCE = builder.comment("分身术召唤看守灵的概率（默认 100%：必定召唤）。").defineInRange("watchling_clone_chance", 1.0D, 0.0D, 1.0D);
        WATCHLING_CLONE_MIN = builder.comment("分身术召唤看守灵的最少数量（默认 3）。").defineInRange("watchling_clone_min", 3, 1, Integer.MAX_VALUE);
        WATCHLING_CLONE_MAX = builder.comment("分身术召唤看守灵的最多数量（默认 5）。").defineInRange("watchling_clone_max", 5, 1, Integer.MAX_VALUE);
        WATCHLING_ENDERMAN_CHANCE = builder.comment("每只召唤物改用末影人仆从的概率（33%）；"
                + "末影人仆从与看守灵共用同一份名额上限，一起算在 watchling_cap 里。")
                .defineInRange("watchling_enderman_chance", 0.33D, 0.0D, 1.0D);
        WATCHLING_CAP = builder.comment("每只突变末影人的看守灵上限（末影人仆从算在同一名额里），区块卸载不释放名额，0 禁止新召唤。").defineInRange("watchling_cap", 8, 0, Integer.MAX_VALUE);
        VOID_ECHO_ENABLED = builder.comment("允许给予虚空回响。").define("void_echo_enabled", true);
        VOID_ECHO_HEALTH = builder.comment("虚空回响增加的最大生命值。").defineInRange("void_echo_health", 50.0D, 0.0D, Double.MAX_VALUE);
        VOID_ECHO_DAMAGE_MULTIPLIER = builder.comment("虚空回响受到伤害的倍率。").defineInRange("void_echo_damage_multiplier", 0.85D, 0.0D, Double.MAX_VALUE);
        VOID_ECHO_PROTECTION_TICKS = builder.comment("虚空回响普通受击保护时长（0.75 秒 = 15 刻）。").defineInRange("void_echo_protection_ticks", 15, 0, Integer.MAX_VALUE);
        VOID_TOUCHED_CHANCE = builder.comment("命中施加虚空之蚀的概率（默认 40%）。").defineInRange("void_touched_chance", 0.40D, 0.0D, 1.0D);
        VOID_EYE_ENABLED = builder.comment("允许给予虚空之眼。").define("void_eye_enabled", true);
        VOID_EYE_HEALTH = builder.comment("虚空之眼增加的最大生命值。").defineInRange("void_eye_health", 25.0D, 0.0D, Double.MAX_VALUE);
        VOID_EYE_SHIELDING_LEVEL = builder.comment("虚空之眼护佑等级。").defineInRange("void_eye_shielding_level", 2, 1, Integer.MAX_VALUE);
        VOID_EYE_RALLYING_LEVEL = builder.comment("虚空之眼重振等级。").defineInRange("void_eye_rallying_level", 1, 1, Integer.MAX_VALUE);
        VOID_EYE_DEFLECTIVE_LEVEL = builder.comment("虚空之眼偏转等级。").defineInRange("void_eye_deflective_level", 3, 1, Integer.MAX_VALUE);
        FULL_UPGRADE_ARMOR = builder.comment("虚空碎片、虚空回响、虚空之眼三项全部到位（满级强化）后额外获得的护甲值（8）。")
                .defineInRange("full_upgrade_armor", 8.0D, 0.0D, Double.MAX_VALUE);
        FULL_UPGRADE_ARMOR_TOUGHNESS = builder.comment("三项全部到位（满级强化）后额外获得的护甲韧性（4）。")
                .defineInRange("full_upgrade_armor_toughness", 4.0D, 0.0D, Double.MAX_VALUE);
        FULL_UPGRADE_DAMAGE_MULTIPLIER = builder.comment("三项全部到位（满级强化）后所有伤害性招式的伤害倍率，默认 1.25（+25%）；"
                + "结算后向上取整，例如 1 × 1.25 = 1.25 → 2 点。1.0 表示不加成。")
                .defineInRange("full_upgrade_damage_multiplier", 1.25D, 1.0D, Double.MAX_VALUE);
        builder.pop();
        builder.pop();
    }

    @SubscribeEvent
    public static void migrateLegacyDefaults(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() != SupremeServantConfigs.SPEC) {
            return;
        }
        if (RESTORED_SKILLS_REVISION.get() == 0) {
            CLONES_ENABLED.set(true);
            if (DEATH_FADE_TICKS.get() == 120) {
                DEATH_FADE_TICKS.set(280);
            }
        }
        if (RESTORED_SKILLS_REVISION.get() < 2) {
            migrateDefault(TELEPORT_COOLDOWN_SECONDS, 5, 3);
            migrateDefault(TELESMASH_MIN_DISTANCE, 8, 0);
            migrateDefault(TELESMASH_COOLDOWN_SECONDS, 10, 6);
            migrateDefault(SCREAM_COOLDOWN_SECONDS, 40, 20);
            migrateDefault(CLONE_COOLDOWN_SECONDS, 90, 30);
            RESTORED_SKILLS_REVISION.set(2);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 3) {
            migrateDefault(GolemServantConfig.MAGMA.FIREBALL_INTERVAL_TICKS, 20, 40);
            RESTORED_SKILLS_REVISION.set(3);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 4) {
            migrateDefault(GolemServantConfig.ICE.ATTACK_DAMAGE, 5.0D, 10.0D);
            migrateDefault(GolemServantConfig.SNOW.ATTACK_DAMAGE, 10.0D, 20.0D);
            migrateDefault(GolemServantConfig.ICE.ARMOR, 20.0D, 0.0D);
            migrateDefault(GolemServantConfig.ICE.ARMOR_TOUGHNESS, 10.0D, 0.0D);
            migrateDefault(GolemServantConfig.SNOW.ARMOR, 25.0D, 0.0D);
            migrateDefault(GolemServantConfig.SNOW.ARMOR_TOUGHNESS, 15.0D, 0.0D);
            RESTORED_SKILLS_REVISION.set(4);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 5) {
            migrateDefault(GolemServantConfig.ICE.FEED_HEAL_PERCENT, 0.0D, 25.0D);
            migrateDefault(GolemServantConfig.SNOW.FEED_HEAL_PERCENT, 0.0D, 25.0D);
            RESTORED_SKILLS_REVISION.set(5);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 6) {
            migrateDefault(GolemServantConfig.SNOW.RANGED_INTERVAL_TICKS, 40, 10);
            RESTORED_SKILLS_REVISION.set(6);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 7) {
            migrateDefault(GolemServantConfig.SNOW.FROST_BREATH_RANGE_LEVEL, 1, 2);
            RESTORED_SKILLS_REVISION.set(7);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 8) {
            migrateDefault(GolemServantConfig.ICE.ARMOR, 0.0D, 3.0D);
            migrateDefault(GolemServantConfig.SNOW.ARMOR, 0.0D, 5.0D);
            migrateDefault(GolemServantConfig.MAGMA.ARMOR, 0.0D, 6.0D);
            migrateDefault(GolemServantConfig.MAGMA.ARMOR_TOUGHNESS, 0.0D, 1.0D);
            migrateDefault(GolemServantConfig.FURNACE.ARMOR, 0.0D, 8.0D);
            migrateDefault(GolemServantConfig.FURNACE.ARMOR_TOUGHNESS, 0.0D, 1.0D);
            migrateDefault(GolemServantConfig.PRISON.ARMOR, 0.0D, 10.0D);
            migrateDefault(GolemServantConfig.PRISON.ARMOR_TOUGHNESS, 0.0D, 2.0D);
            migrateDefault(MutantZombieServantConfig.ARMOR, 0.0D, 3.0D);
            RESTORED_SKILLS_REVISION.set(8);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 9) {
            migrateDefault(GolemServantConfig.SNOW.SNOW_BLOCK_AOE_DAMAGE, 4.0D, 8.0D);
            migrateDefault(MutantSnowGolemServantConfig.THROW_RANGE, 12, 30);
            migrateDefault(MutantSnowGolemServantConfig.FROSTY_AURA_LEVEL, 1, 2);
            RESTORED_SKILLS_REVISION.set(9);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 10) {
            migrateDefault(FULL_UPGRADE_ARMOR, 5.0D, 8.0D);
            migrateDefault(WATCHLING_TELEPORT_CHANCE, 0.2D, 0.5D);
            migrateDefault(WATCHLING_CLONE_CHANCE, 0.5D, 1.0D);
            migrateDefault(VOID_TOUCHED_CHANCE, 0.25D, 0.35D);
            RESTORED_SKILLS_REVISION.set(10);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 11) {
            migrateDefault(VOID_TOUCHED_CHANCE, 0.35D, 0.40D);
            migrateDefault(WATCHLING_CLONE_MIN, 2, 3);
            migrateDefault(WATCHLING_CLONE_MAX, 3, 5);
            RESTORED_SKILLS_REVISION.set(11);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 12) {
            migrateDefault(GolemServantConfig.MAGMA.FIREBALL_DAMAGE, 6.0D, 15.0D);
            RESTORED_SKILLS_REVISION.set(12);
            event.getConfig().save();
        }
        if (RESTORED_SKILLS_REVISION.get() < 13) {
            migrateDefault(MutantSnowGolemServantConfig.SUMMON_COUNT_MIN, 2, 3);
            migrateDefault(MutantSnowGolemServantConfig.SUMMON_COUNT_MAX, 3, 5);
            migrateDefault(MutantSnowGolemServantConfig.ALLY_CHILL_HIDE_RADIUS, 8.0D, 10.0D);
            RESTORED_SKILLS_REVISION.set(13);
            event.getConfig().save();
        }
    }

    private static void migrateDefault(ForgeConfigSpec.IntValue value, int oldDefault, int newDefault) {
        if (value.get() == oldDefault) {
            value.set(newDefault);
        }
    }

    private static void migrateDefault(ForgeConfigSpec.DoubleValue value, double oldDefault, double newDefault) {
        if (Math.abs(value.get() - oldDefault) < 1.0E-6D) {
            value.set(newDefault);
        }
    }

    private MutantEndermanServantConfig() {
    }
}
