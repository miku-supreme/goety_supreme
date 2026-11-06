package cn.blockforge.generated.goetysupreme.config;

import cn.blockforge.generated.goetysupreme.GeneratedMod;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.renderer.PlayerMobServantRenderer;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;

public final class PlayerMobServantConfig {
    public static final String DEFAULT_SKIN = "goety_supreme:textures/entity/player_mob_servant.png";
    public static final String PLAYER_MOBS_SKIN_NAMESPACE = "player_mobs";

    public static final List<String> DEFAULT_SPAWN_ITEMS = List.of(
            "minecraft:bow-90",
            "minecraft:crossbow-10",
            "minecraft:stone_sword-64",
            "minecraft:iron_sword-20",
            "minecraft:golden_sword-10",
            "minecraft:diamond_sword-5",
            "minecraft:netherite_sword-1");

    public static final List<String> DEFAULT_SPAWN_ITEMS_OFFHAND = List.of(
            "minecraft:shield-1",
            "minecraft:air-4");

    public static final List<String> DEFAULT_MOB_NAMES = List.of(
            "mireyni", "mikumiku39super", "rozaliya1", "0_uvu_0", "doilly", "xi_55",
            "baicha_yoyo", "rotten_oranges", "nekogina", "canchen0", "asdgsdfdgf", "miiik3s", "mwvvw",
            "kumuras", "miku39super", "simulink_1", "simulink__1", "lyaei", "duaa0", "asilari", "teauuud",
            "bunnyinfinity", "lunity99", "lopeeed", "cookiewi", "fentjia", "teapoot_", "lcandyl", "mxntsunndae",
            "ynsc", "pdppd91", "bakadz", "mikanunu", "miel_bun", "qoocies", "cookitos", "xin_tiris");

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue MELEE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue MELEE_ATTACK_RANGE;

    public static ForgeConfigSpec.DoubleValue MELEE_KEEP_DISTANCE_MIN;
    public static ForgeConfigSpec.DoubleValue MELEE_KEEP_DISTANCE_MAX;
    public static ForgeConfigSpec.DoubleValue BOW_DAMAGE;
    public static ForgeConfigSpec.DoubleValue ARMOR;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue FOLLOW_RANGE;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> MOB_NAMES;
    public static ForgeConfigSpec.ConfigValue<String> SKIN;
    public static ForgeConfigSpec.EnumValue<ModelType> MODEL;
    public static ForgeConfigSpec.BooleanValue SLIM_ARMS;
    public static ForgeConfigSpec.BooleanValue COPY_OWNER_SKIN;

    public static ForgeConfigSpec.EnumValue<SkinLayerMode> THREE_D_SKIN_LAYERS;

    public static ForgeConfigSpec.BooleanValue FORCE_ITEMS_SPAWN;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SPAWN_ITEMS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SPAWN_ITEMS_OFFHAND;
    public static ForgeConfigSpec.EnumValue<Difficulty> OFFHAND_SPAWN_DIFFICULTY;
    public static ForgeConfigSpec.DoubleValue OFFHAND_SPAWN_CHANCE;

    public static ForgeConfigSpec.BooleanValue SHIELD_ENABLED;
    public static ForgeConfigSpec.DoubleValue SHIELD_APPROACH_RANGE;
    public static ForgeConfigSpec.DoubleValue SHIELD_GUARD_RANGE;
    public static ForgeConfigSpec.DoubleValue SHIELD_APPROACH_SPEED;

    public static ForgeConfigSpec.DoubleValue SNEAK_SPEED_MULTIPLIER;
    public static ForgeConfigSpec.IntValue SHIELD_BREAK_DISABLE_TICKS;
    public static ForgeConfigSpec.IntValue SHIELD_BLOCK_LIMIT;
    public static ForgeConfigSpec.IntValue SHIELD_CROUCH_BLOCK_LIMIT;
    public static ForgeConfigSpec.IntValue SHIELD_BLOCK_DISABLE_TICKS;
    public static ForgeConfigSpec.IntValue SHIELD_TOGGLE_WINDOW_TICKS;
    public static ForgeConfigSpec.IntValue SHIELD_TOGGLE_LIMIT;
    public static ForgeConfigSpec.IntValue SHIELD_CROUCH_FORCE_STAND_BLOCKS;
    public static ForgeConfigSpec.IntValue FORCED_STAND_TICKS;

    public static ForgeConfigSpec.DoubleValue TRIDENT_MELEE_RANGE;
    public static ForgeConfigSpec.IntValue TRIDENT_THROW_INTERVAL;
    public static ForgeConfigSpec.DoubleValue TRIDENT_THROW_DAMAGE;

    public static ForgeConfigSpec.DoubleValue ENDER_PEARL_MIN_DISTANCE;
    public static ForgeConfigSpec.DoubleValue ENDER_PEARL_DAMAGE;
    public static ForgeConfigSpec.IntValue ENDER_PEARL_THROW_INTERVAL;

    public static ForgeConfigSpec.DoubleValue EAT_RANGE;

    public static ForgeConfigSpec.DoubleValue PREBUFF_EAT_RANGE;
    public static ForgeConfigSpec.BooleanValue AUTO_PICKUP_ENABLED;
    public static ForgeConfigSpec.IntValue STARTER_BREAD_COUNT;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("player_mob_servant 的属性、伤害、皮肤和主副手装备配置。",
                "皮肤使用完整 ResourceLocation，例如 goety_supreme:textures/entity/player_mob_servant.png。",
                "资源包可覆盖该 ResourceLocation 对应的 assets/<命名空间>/textures/... 文件。")
                .push("player_mob_servant");

        MAX_HEALTH = builder.comment("最大生命值。")
                .defineInRange("max_health", 20.0D, 1.0D, Double.MAX_VALUE);
        MELEE_DAMAGE = builder.comment("基础近战攻击伤害；主手武器和附魔仍可提供额外加成。")
                .defineInRange("melee_damage", 3.0D, 0.0D, Double.MAX_VALUE);
        MELEE_ATTACK_RANGE = builder.comment(
                        "近战够手距离（格）：玩家生物判定能打到目标所需的中心距离；默认 2 格。",
                        "原版按体型宽度计算，玩家体型的怪物大约只有 1.4 格，所以经常要贴脸才出手。")
                .defineInRange("melee_attack_range", 2.0D, 0.5D, 64.0D);
        MELEE_KEEP_DISTANCE_MIN = builder.comment(
                        "近战 AI 控距下沿（格，包围盒外缘间隙）：贴得比这个还近就往后退，默认 1 格。",
                        "只在近战档接管时生效，且不会超过 melee_attack_range。")
                .defineInRange("melee_keep_distance_min", 1.0D, 0.0D, 64.0D);
        MELEE_KEEP_DISTANCE_MAX = builder.comment(
                        "近战 AI 控距上沿（格，包围盒外缘间隙）：进到这条线以内就不再向目标推进，默认 1.5 格。",
                        "够手距离是 melee_attack_range（默认 2 格），所以停在带宽里仍然打得到目标。")
                .defineInRange("melee_keep_distance_max", 1.5D, 0.0D, 64.0D);
        BOW_DAMAGE = builder.comment("弓箭和弩箭的基础箭矢伤害；箭矢速度、暴击和目标防御仍会参与最终结算。")
                .defineInRange("bow_damage", 2.0D, 0.0D, Double.MAX_VALUE);
        ARMOR = builder.comment("基础护甲值。")
                .defineInRange("armor", 0.0D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = builder.comment("击退抗性，范围为 0.0 至 1.0。")
                .defineInRange("knockback_resistance", 0.0D, 0.0D, Double.MAX_VALUE);
        ARMOR_TOUGHNESS = builder.comment("基础护甲韧性值。")
                .defineInRange("armor_toughness", 0.0D, 0.0D, Double.MAX_VALUE);
        MOVEMENT_SPEED = builder.comment("移动速度。")
                .defineInRange("movement_speed", 0.25D, 0.0D, Double.MAX_VALUE);
        FOLLOW_RANGE = builder.comment("索敌距离（格）：锁定目标与保持追击的距离上限。默认 36，"
                        + "配合 PlayerMobWandSpellGoal 的「超过 28 格用位移聚晶靠近到 20 格」。")
                .defineInRange("follow_range", 36.0D, 1.0D, 2048.0D);

        MOB_NAMES = builder.comment("Player Mobs 兼容名称列表；生成时随机选择一个名称，并从 assets/player_mobs/skins/<名称>.png 读取对应皮肤。",
                        "该目录是 Player Mobs 模组的官方约定；作为容错，同名贴图放在",
                        "assets/player_mobs/textures/skins/ 或 assets/player_mobs/textures/entity/skins/，" +
                        "以及本模组命名空间的对应位置，也能被认到（见 mobSkinCandidates）。")
                .defineListAllowEmpty("Mob Names", DEFAULT_MOB_NAMES, PlayerMobServantConfig::isMobName);
        SKIN = builder.comment("默认皮肤资源位置；实体拥有列表中的皮肤名且对应 PNG 存在时优先使用皮肤名对应贴图。",
                "资源包可覆盖该 ResourceLocation 对应的 assets/<命名空间>/textures/... 文件。")
                .define("skin", DEFAULT_SKIN, PlayerMobServantConfig::isResourceLocation);
        MODEL = builder.comment("玩家模型：DEFAULT 使用 Steve 手臂，SLIM 使用 Alex 手臂。")
                .defineEnum("model", ModelType.DEFAULT);
        SLIM_ARMS = builder.comment("是否使用 Alex 风格的纤细手臂；显式 model=SLIM 时优先。")
                .define("slim_arms", false);
        COPY_OWNER_SKIN = builder.comment("是否优先使用主人当前的玩家皮肤；关闭后始终使用上面的皮肤资源。")
                .define("copy_owner_skin", false);
        THREE_D_SKIN_LAYERS = builder.comment(
                        "第二层皮肤（帽子 / 外套 / 左右袖 / 左右裤腿）要不要做成 3D 立体层。",
                        "原版这六件外层只向外鼓 0.25 像素（帽子 0.5），几乎贴在里层上；",
                        "3D 皮肤层模组（3dskinlayers）会把玩家的这层推到 1 像素外，形成明显的立体厚度。",
                        "AUTO：装了 3dskinlayers 才跟着做，没装就保持原版薄层，主人的外观与仆从一致；",
                        "ON：无论装没装都做成立体层；OFF：永远保持原版薄层。")
                .defineEnum("three_d_skin_layers", SkinLayerMode.AUTO);

        FORCE_ITEMS_SPAWN = builder.comment("是否强制让玩家生物生成时持有主手物品；关闭时会按难度进行生成概率判定。")
                .define("Force Items Spawn", false);
        SPAWN_ITEMS = builder.comment(
                        "玩家生物可以生成的主手物品列表。",
                        "默认大致为：弓 40%、弩 10%、剑 50%，各类剑再按自身权重分配。",
                        "是否生成主手物品还会先进行一次独立概率判定。",
                        "格式为 \"namespace:id-weight\"，例如 \"minecraft:iron_sword-20\"。")
                .defineListAllowEmpty("Spawn Items", DEFAULT_SPAWN_ITEMS, PlayerMobServantConfig::isWeightedItemEntry);
        SPAWN_ITEMS_OFFHAND = builder.comment(
                        "玩家生物可以生成的副手物品列表。",
                        "副手物品默认只在困难难度及以上生成。",
                        "如果主手是弓或其他 ProjectileWeaponItem，则不会生成副手物品。",
                        "格式为 \"namespace:id-weight\"；minecraft:air 代表空副手。")
                .defineListAllowEmpty("Spawn Items Offhand", DEFAULT_SPAWN_ITEMS_OFFHAND,
                        PlayerMobServantConfig::isWeightedItemEntry);
        OFFHAND_SPAWN_DIFFICULTY = builder.comment("允许玩家生物生成副手物品的最低难度。")
                .defineEnum("Offhand Spawn Difficulty", Difficulty.HARD);
        OFFHAND_SPAWN_CHANCE = builder.comment(
                        "副手物品生成概率；设为 -1 可禁用副手物品生成。",
                        "该概率只在主手物品已经生成、难度达到上面要求且主手不是弓类武器时判定。")
                .defineInRange("Offhand Spawn Chance", 0.5D, -1.0D, Double.MAX_VALUE);

        SHIELD_ENABLED = builder.comment("是否启用主手近战武器加副手盾牌时的自动举盾行为。")
                .define("shield_enabled", true);
        SHIELD_APPROACH_RANGE = builder.comment(
                        "目标进入此距离（格）后，仆从才会举盾潜行靠近；默认 3 格。",
                        "此距离内格挡几乎不被击退，且连续格挡上限提高到潜行档（见 shield_crouch_block_limit）。",
                        "注意：本项键名从 shield_approach_range 改为 shield_crouch_approach_range——",
                        "早期版本默认值是 6 格，旧 servants.toml 里残留的 6.0 不会再被读到，潜行档一律按新的 3 格生效。")
                .defineInRange("shield_crouch_approach_range", 3.0D, 0.0D, Double.MAX_VALUE);
        SHIELD_GUARD_RANGE = builder.comment(
                        "举盾警戒距离（格）：只要索敌到敌人（任意距离）即可举盾；"
                        + "此值仅作参考，超出接近距离时不会潜行靠近。")
                .defineInRange("shield_guard_range", 32.0D, 0.0D, Double.MAX_VALUE);
        SHIELD_APPROACH_SPEED = builder.comment("举盾潜行接近目标时的移动速度倍率。")
                .defineInRange("shield_approach_speed", 0.45D, 0.05D, Double.MAX_VALUE);
        SNEAK_SPEED_MULTIPLIER = builder.comment(
                        "潜行（举盾蹲档 / 匕首潜行档 / 战斗潜行）时移动速度的倍率。",
                        "原版玩家潜行是 0.3；J 村原生怪物调用 setShiftKeyDown 不附带任何减速，潜行速度等于正常速度。",
                        "玩家生物仆从采用玩家口径，默认 0.3。")
                .defineInRange("sneak_speed_multiplier", 0.3D, 0.05D, 1.0D);
        SHIELD_BREAK_DISABLE_TICKS = builder.comment("受到可破盾伤害后，盾牌被禁用的时间（tick）。")
                .defineInRange("shield_break_disable_ticks", 100, 0, Integer.MAX_VALUE);
        SHIELD_BLOCK_LIMIT = builder.comment("正常举盾连续成功格挡多少次伤害后暂时禁用盾牌；默认值 5 表示第六次格挡触发。")
                .defineInRange("shield_block_limit", 5, 0, Integer.MAX_VALUE);
        SHIELD_CROUCH_BLOCK_LIMIT = builder.comment(
                        "潜行举盾（接近距离内）连续格挡上限；与正常档共享同一个计数，"
                        + "默认 8 表示潜行档第 9 次格挡触发破盾冷却。")
                .defineInRange("shield_crouch_block_limit", 8, 1, Integer.MAX_VALUE);
        SHIELD_BLOCK_DISABLE_TICKS = builder.comment("连续格挡达到上限后，盾牌被禁用的时间（tick）。")
                .defineInRange("shield_block_disable_ticks", 40, 0, Integer.MAX_VALUE);

        SHIELD_TOGGLE_WINDOW_TICKS = builder.comment("反滥用窗口（tick，20 tick = 1 秒）：在此窗口内 举盾<->潜行 切换次数超过 "
                        + "shield_toggle_limit 时，强制切换成站立举盾。")
                .defineInRange("shield_toggle_window_ticks", 40, 1, Integer.MAX_VALUE);
        SHIELD_TOGGLE_LIMIT = builder.comment("反滥用：2 秒窗口内 举盾<->潜行 连续切换次数上限（超过即触发强制站立举盾）。"
                        + "默认 5 表示第 6 次切换触发。")
                .defineInRange("shield_toggle_limit", 5, 1, Integer.MAX_VALUE);
        SHIELD_CROUCH_FORCE_STAND_BLOCKS = builder.comment(
                        "反滥用：潜行举盾状态下连续成功格挡且未进行任何一次近战攻击达到此次数时，"
                        + "强制切换成站立举盾状态，防止利用潜行档位无限格挡。默认 4 表示第 4 次格挡触发。")
                .defineInRange("shield_crouch_force_stand_blocks", 4, 1, Integer.MAX_VALUE);
        FORCED_STAND_TICKS = builder.comment("触发反滥用后，强制站立举盾的持续时间（tick，20 tick = 1 秒）；"
                        + "期间目标即使在接近距离内也保持站立举盾，不再潜行靠近。")
                .defineInRange("shield_forced_stand_ticks", 60, 20, Integer.MAX_VALUE);

        TRIDENT_MELEE_RANGE = builder.comment("主手持三叉戟时：目标大于此距离（格）投掷三叉戟，之内近战接管（含举盾）。")
                .defineInRange("trident_melee_range", 6.0D, 1.0D, Double.MAX_VALUE);
        TRIDENT_THROW_INTERVAL = builder.comment("三叉戟投掷间隔（tick）。")
                .defineInRange("trident_throw_interval", 20, 5, Integer.MAX_VALUE);
        TRIDENT_THROW_DAMAGE = builder.comment(
                        "三叉戟投掷命中的基础伤害；默认 8.0 即原版投掷三叉戟的固定值，",
                        "三叉戟自身对目标属类的加成（打击/亡灵杀手/水下速掘）在基础值之上照常生效；",
                        "6 格内的三叉戟近战仍是属性结算（基础近战伤害 + 武器值），不受此项影响。")
                .defineInRange("trident_throw_damage", 8.0D, 0.0D, Double.MAX_VALUE);

        ENDER_PEARL_MIN_DISTANCE = builder.comment(
                        "末影珍珠传送接近的触发距离（格）：主手不是远程武器、副手或背包有末影珍珠时，",
                        "目标超过此距离才会掷珍珠传送到敌人身边，默认 12 格。")
                .defineInRange("ender_pearl_min_distance", 12.0D, 1.0D, Double.MAX_VALUE);
        ENDER_PEARL_DAMAGE = builder.comment(
                        "末影珍珠传送后结算的摔落伤害；默认 5.0，与原版玩家掷珍珠一致。",
                        "设为 0 可关闭这次扣血。")
                .defineInRange("ender_pearl_damage", 5.0D, 0.0D, Double.MAX_VALUE);
        ENDER_PEARL_THROW_INTERVAL = builder.comment(
                        "两次末影珍珠投掷之间的最短间隔（tick，20 tick = 1 秒）；默认 60。",
                        "写一段冷却是为了掷完直接让位近战，不会原地连掷。")
                .defineInRange("ender_pearl_throw_interval", 60, 5, Integer.MAX_VALUE);

        EAT_RANGE = builder.comment("血不满时主动进食的索敌判定距离（格）：无目标或目标超出此距离才会进食。")
                .defineInRange("eat_range", 18.0D, 1.0D, Double.MAX_VALUE);
        PREBUFF_EAT_RANGE = builder.comment(
                        "发现敌人、还没贴进近战圈时，为了补食物 buff 而停下进食的索敌距离（格）。",
                        "敌人在此距离内、身上又缺少该食物能提供的正面效果时，先吃完再冲；",
                        "已经有的 buff 不会重复吃。默认 16。")
                .defineInRange("prebuff_eat_range", 16.0D, 1.0D, Double.MAX_VALUE);
        AUTO_PICKUP_ENABLED = builder.comment("是否允许仆从在非战斗状态下主动拾取地上的食物、武器与盔甲。")
                .define("auto_pickup_enabled", true);
        STARTER_BREAD_COUNT = builder.comment("召唤时自带的捆绑面包数量（0 关闭；这些面包不会在死亡时掉落）。")
                .defineInRange("starter_bread_count", 3, 0, Integer.MAX_VALUE);

        builder.pop();
    }

    private PlayerMobServantConfig() {
    }

    public static List<String> mobNames() {
        List<String> names = new ArrayList<>();
        List<? extends String> configured = MOB_NAMES.get();
        if (configured == null) {
            return names;
        }
        for (String value : configured) {
            String name = trim(value);
            if (isMobName(name)) {
                names.add(name);
            }
        }
        return names;
    }

    public static List<ResourceLocation> mobSkinCandidates(String name) {
        String configured = trim(name);
        if (!isMobName(configured)) {
            return List.of();
        }
        LinkedHashSet<ResourceLocation> candidates = new LinkedHashSet<>();
        for (String namespace : List.of(PLAYER_MOBS_SKIN_NAMESPACE, GeneratedMod.MOD_ID)) {
            candidates.add(ResourceLocation.fromNamespaceAndPath(namespace, "skins/" + configured + ".png"));
            candidates.add(ResourceLocation.fromNamespaceAndPath(namespace, "textures/skins/" + configured + ".png"));
            candidates.add(ResourceLocation.fromNamespaceAndPath(namespace,
                    "textures/entity/skins/" + configured + ".png"));
        }
        return List.copyOf(candidates);
    }

    public static ResourceLocation mobSkinLocation(String name) {
        List<ResourceLocation> candidates = mobSkinCandidates(name);
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    public static String matchMobName(String text) {
        String value = trim(text);
        if (value.isEmpty()) {
            return null;
        }
        List<String> names = mobNames();
        if (names.isEmpty()) {
            return null;
        }
        for (String name : names) {
            if (name.equalsIgnoreCase(value)) {
                return name;
            }
        }
        for (String token : value.split("[^0-9A-Za-z_]+")) {
            if (token.isEmpty()) {
                continue;
            }
            for (String name : names) {
                if (name.equalsIgnoreCase(token)) {
                    return name;
                }
            }
        }
        String best = null;
        int bestStart = Integer.MAX_VALUE;
        String lower = value.toLowerCase(Locale.ROOT);
        for (String name : names) {
            String needle = name.toLowerCase(Locale.ROOT);
            int index = lower.indexOf(needle);
            while (index >= 0) {
                if (isWordBoundary(value, index, name.length()) && index < bestStart) {
                    best = name;
                    bestStart = index;
                }
                index = lower.indexOf(needle, index + 1);
            }
        }
        return best;
    }

    private static boolean isWordBoundary(String text, int index, int length) {
        if (index > 0 && isNameChar(text.charAt(index - 1))) {
            return false;
        }
        int end = index + length;
        return end >= text.length() || !isNameChar(text.charAt(end));
    }

    private static boolean isNameChar(char value) {
        return (value >= '0' && value <= '9') || (value >= 'a' && value <= 'z')
                || (value >= 'A' && value <= 'Z') || value == '_';
    }

    public static String skin() {
        String configured = trim(SKIN.get());
        return isResourceLocation(configured) ? configured : DEFAULT_SKIN;
    }

    public static ResourceLocation skinLocation() {
        ResourceLocation location = ResourceLocation.tryParse(skin());
        return location == null ? ResourceLocation.parse(DEFAULT_SKIN) : normalizeTextureLocation(location);
    }

    private static ResourceLocation normalizeTextureLocation(ResourceLocation location) {
        String path = location.getPath();
        if (!path.startsWith("textures/")) {
            path = "textures/" + path;
        }
        if (!path.endsWith(".png")) {
            path += ".png";
        }
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path);
    }

    public static boolean useSlimArms() {
        return MODEL.get() == ModelType.SLIM || SLIM_ARMS.get();
    }

    public static boolean use3DSkinLayers() {
        SkinLayerMode mode = THREE_D_SKIN_LAYERS.get();
        if (mode == SkinLayerMode.ON) {
            return true;
        }
        if (mode == SkinLayerMode.OFF) {
            return false;
        }
        return is3DSkinLayersLoaded();
    }

    private static boolean is3DSkinLayersLoaded() {
        AtomicBoolean loaded = new AtomicBoolean();
        ModList.get().forEachModContainer((id, container) -> {
            String lower = id.toLowerCase(Locale.ROOT);
            if (lower.contains("skinlayer") || lower.contains("skin_layer") || lower.contains("3dskin")) {
                loaded.set(true);
            }
        });
        return loaded.get();
    }

    public static List<? extends String> mainhandItems() {
        return listOrEmpty(SPAWN_ITEMS.get());
    }

    public static List<? extends String> offhandItems() {
        return listOrEmpty(SPAWN_ITEMS_OFFHAND.get());
    }

    public static boolean isResourceLocation(Object value) {
        return value instanceof String string && isResourceLocation(trim(string));
    }

    public static boolean isMobName(Object value) {
        if (!(value instanceof String string)) {
            return false;
        }
        String name = trim(string);
        return !name.isEmpty() && !name.equals(".") && !name.equals("..")
                && name.length() <= 64 && name.indexOf('/') < 0 && name.indexOf('\\') < 0
                && ResourceLocation.isValidPath(name);
    }

    public static boolean isWeightedItemEntry(Object value) {
        return value instanceof String string && parseWeightedItem(string) != null;
    }

    public static WeightedItem parseWeightedItem(String value) {
        String entry = trim(value);
        if (entry.isEmpty()) {
            return null;
        }

        String itemId = entry;
        int weight = 1;
        int atSeparator = entry.lastIndexOf('@');
        int dashSeparator = entry.lastIndexOf('-');
        int separator = atSeparator > 0 ? atSeparator : -1;
        if (separator < 0 && dashSeparator > 0 && isPositiveInteger(entry.substring(dashSeparator + 1))) {
            separator = dashSeparator;
        }
        if (separator > 0 && separator < entry.length() - 1) {
            itemId = entry.substring(0, separator).trim();
            try {
                weight = Integer.parseInt(entry.substring(separator + 1).trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        return weight > 0 && isResourceLocation(itemId) ? new WeightedItem(itemId, weight) : null;
    }

    private static boolean isPositiveInteger(String value) {
        try {
            return Integer.parseInt(value.trim()) > 0;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    private static List<? extends String> listOrEmpty(List<? extends String> values) {
        return values == null ? List.of() : values;
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static boolean isResourceLocation(String value) {
        if (value.isEmpty()) {
            return false;
        }
        int separator = value.indexOf(':');
        if (separator == 0 || separator != value.lastIndexOf(':')) {
            return false;
        }
        String namespace = separator < 0 ? "minecraft" : value.substring(0, separator);
        String path = separator < 0 ? value : value.substring(separator + 1);
        return !path.isEmpty() && ResourceLocation.isValidNamespace(namespace)
                && ResourceLocation.isValidPath(path);
    }

    public record WeightedItem(String itemId, int weight) {
    }

    public enum ModelType {
        DEFAULT,
        SLIM
    }

    public enum SkinLayerMode {

        AUTO,

        ON,

        OFF
    }
}
