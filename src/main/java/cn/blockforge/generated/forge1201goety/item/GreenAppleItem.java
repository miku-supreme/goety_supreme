package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.debug.GreenApplePoolCommand;
import cn.blockforge.generated.goetysupreme.item.GreenAppleItem;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public class GreenAppleItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int COOLDOWN_TICKS = 15 * 20;

    public static final int DURABILITY = 5;

    private static final int NUTRITION = 2;
    private static final float SATURATION_MOD = 1.0F;

    private static final float HEALTH_COST = 10.0F;

    private static final int SUMMON_COUNT = 4;

    private static final float SERVANT_CHANCE = 0.25F;

    private static final long FULL_MOON_NIGHT_TICKS = 18000L;

    private static final TagKey<EntityType<?>> GOETY_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "bosses"));
    private static final TagKey<EntityType<?>> GOETY_MINI_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "mini_bosses"));
    private static final TagKey<EntityType<?>> FORGE_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", "bosses"));

    private static final TagKey<EntityType<?>> GOETY_NECROMANCERS =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "necromancers"));

    private static final TagKey<EntityType<?>> WILD_UNDEAD =
            TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath("goety_supreme", "green_apple_summons"));

    public static final Set<String> KNOWN_BOSS_IDS = Set.of(
            "goetyawaken:nameless_one",
            "goetyawaken:awakened_nameless_one",
            "goety:nameless_one",
            "goetynamelessoneaddon:nameless_one",
            "aquamirae:captain_cornelia",
            "goetyominous:urbhadhach",
            "hmag:giant_mummy");

    public static final String[] BOSS_KEYWORDS = {
            "boss",
            "nameless",
            "apostle",
            "warlord",
            "monstrosity",
            "lich",
            "titan",
            "avatar",
            "deity",
            "overlord",
            "archfiend",
            "ancient_one"
    };

    private static final int NECROMANCER_WEIGHT = 12;
    private static final int UNDEAD_WEIGHT = 3;

    private static final float MAX_UNDEAD_HEALTH = 100.0F;

    private static final Set<String> UNDEAD_IDS = Set.of(
            "minecraft:wither",
            "minecraft:skeleton",
            "minecraft:stray",
            "minecraft:wither_skeleton",
            "minecraft:skeleton_horse",
            "minecraft:zombie",
            "minecraft:husk",
            "minecraft:drowned",
            "minecraft:zombie_villager",
            "minecraft:zombified_piglin",
            "minecraft:zombie_horse",
            "minecraft:zoglin",
            "minecraft:phantom");

    private static final Set<String> GOETY_UNDEAD_IDS = Set.of(
            "goety:necromancer",
            "goety:cairn_necromancer",
            "goety:mossy_necromancer",
            "goety:frayed",
            "goety:rattled");

    private static final Map<EntityType<?>, Boolean> PROBE_CACHE = new HashMap<>();

    private static int probeRuns;
    private static long probeNanos;
    private static int probeFailures;

    private static final Map<String, String> SERVANT_ALIASES = Map.of(
            "minecraft:zombified_piglin", "goety:zpiglin_servant");

    private static final FoodProperties FOOD = new FoodProperties.Builder()
            .nutrition(NUTRITION)
            .saturationMod(SATURATION_MOD)
            .alwaysEat()
            .build();

    public GreenAppleItem() {
        super(new Item.Properties().food(FOOD).durability(DURABILITY).rarity(Rarity.RARE));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return super.finishUsingItem(stack, level, entity);
        }
        if (!(level instanceof ServerLevel server)) {
            return stack;
        }
        player.getFoodData().eat(NUTRITION, SATURATION_MOD);
        setFullMoonNight(server);
        int invulnerable = player.invulnerableTime;
        player.invulnerableTime = 0;
        player.hurt(player.damageSources().magic(), HEALTH_COST);
        player.invulnerableTime = Math.max(invulnerable, player.invulnerableTime);
        summonUndead(server, player);
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        return stack;
    }

    private static void setFullMoonNight(ServerLevel server) {
        long day = Math.floorDiv(server.getDayTime(), 24000L);
        long offset = Math.floorMod(day, 8L);
        long fullMoonDay = offset <= 4L ? day - offset : day + (8L - offset);
        server.setDayTime(fullMoonDay * 24000L + FULL_MOON_NIGHT_TICKS);
    }

    private static void summonUndead(ServerLevel server, Player player) {
        List<EntityType<?>> pool = undeadPool(server);
        if (pool.isEmpty()) {
            LOGGER.info("[GreenApple] {} 吃下青苹果，但候选池是空的（没有任何合格的野生亡灵）。",
                    player.getName().getString());
            return;
        }
        LOGGER.info("[GreenApple] {} 吃下青苹果，候选池 {} 种（亡灵探针 {}）；池内清单（DEBUG 级）：{}",
                player.getName().getString(), pool.size(), probeStats(),
                pool.size() <= 32 ? poolIds(pool) : "…");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[GreenApple] 候选池全量 {} 种：{}", pool.size(), poolIds(pool));
        }
        RandomSource random = player.getRandom();
        List<EntityType<?>> picked = pickDistinct(pool, random, SUMMON_COUNT);
        ServantIndex servantIndex = ServantIndex.build();
        List<String> actuallySummoned = new ArrayList<>();
        for (EntityType<?> hostile : picked) {
            boolean preferServant = random.nextFloat() < SERVANT_CHANCE;
            Mob mob = createUndead(server, spawnType(pool, hostile, preferServant, random, servantIndex),
                    preferServant, servantIndex);
            if (mob == null) {
                continue;
            }
            BlockPos spawnPos = BlockFinder.SummonRadius(player.blockPosition(), mob, server, 1, 4);
            if (spawnPos == null) {
                spawnPos = player.blockPosition().offset(1, 0, 1);
            }
            mob.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D,
                    player.getYRot(), 0.0F);
            MobUtil.moveDownToGround(mob);
            bindServant(mob, player);
            mob.finalizeSpawn(server, server.getCurrentDifficultyAt(mob.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            bindServant(mob, player);
            boolean servant = isServantType(mob.getType());
            if (servant) {
                mob.setPersistenceRequired();
            }
            if (server.addFreshEntity(mob)) {
                ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType());
                actuallySummoned.add((id == null ? mob.getType().toString() : id.toString())
                        + (servant ? "（仆从）" : "（野生敌对）"));
                server.sendParticles(GreenSmithFx.necroFire(), mob.getX(),
                        mob.getY(0.5D), mob.getZ(), 14, 0.4D, 0.6D, 0.4D, 0.01D);
                server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, mob.getX(),
                        mob.getY(0.7D), mob.getZ(), 8, 0.3D, 0.5D, 0.3D, 0.01D);
            } else {
                mob.discard();
            }
        }
        LOGGER.info("[GreenApple] 实际落地 {} 只：{}", actuallySummoned.size(), actuallySummoned);
        server.playSound(null, player.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS,
                1.1F, 0.6F);
    }

    private static List<String> poolIds(List<EntityType<?>> pool) {
        List<String> ids = new ArrayList<>(pool.size());
        for (EntityType<?> type : pool) {
            ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
            ids.add(id == null ? type.toString() : id.toString());
        }
        return ids;
    }

    public static String excludedWithoutProbe(EntityType<?> type) {
        String reason = excludedOutsideBlacklist(type);
        if (reason != null) {
            return reason;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id != null && KNOWN_BOSS_IDS.contains(id.toString())) {
            return "点名黑名单（别家首领，标签里没有它）";
        }
        return null;
    }

    public static String excludedOutsideBlacklist(EntityType<?> type) {
        if (type.is(GOETY_BOSSES)) {
            return "goety:bosses 标签";
        }
        if (type.is(GOETY_MINI_BOSSES)) {
            return "goety:mini_bosses 标签";
        }
        if (type.is(FORGE_BOSSES)) {
            return "forge:bosses 标签";
        }
        if (isServantType(type)) {
            return "仆从实体类型（注册名是 _servant 仆从版，不是野生亡灵）";
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id == null) {
            return "没有注册名（模组没注册好）";
        }
        String keyword = bossKeyword(id.getPath());
        if (keyword != null) {
            return "注册名含首领关键词「" + keyword + "」";
        }
        return null;
    }

    public static String bossKeyword(String path) {
        String lower = path.toLowerCase(Locale.ROOT);
        for (String keyword : BOSS_KEYWORDS) {
            if (lower.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }

    public static List<EntityType<?>> undeadPool(ServerLevel server) {
        List<EntityType<?>> pool = new ArrayList<>();
        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES.getValues()) {
            if (excludedWithoutProbe(type) != null) {
                continue;
            }
            if (type.getCategory() != MobCategory.MONSTER) {
                continue;
            }
            if (!isLowHealth(type)) {
                continue;
            }
            if (!isUndeadType(server, type)) {
                continue;
            }
            pool.add(type);
        }
        return pool;
    }

    public static boolean isUndeadType(EntityType<?> type) {
        if (type.getCategory() != MobCategory.MONSTER) {
            return false;
        }
        if (type.is(WILD_UNDEAD)) {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id == null) {
            return false;
        }
        String key = id.toString();
        return UNDEAD_IDS.contains(key) || GOETY_UNDEAD_IDS.contains(key);
    }

    public static boolean isUndeadType(ServerLevel level, EntityType<?> type) {
        if (isUndeadType(type)) {
            return true;
        }
        return level != null && probeUndead(level, type);
    }

    private static boolean probeUndead(ServerLevel level, EntityType<?> type) {
        Boolean cached = PROBE_CACHE.get(type);
        if (cached != null) {
            return cached;
        }
        boolean undead = false;
        long started = System.nanoTime();
        Entity probe = null;
        try {
            probe = type.create(level);
            undead = probe instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD;
        } catch (Throwable thrown) {
            probeFailures++;
            LOGGER.debug("[GreenApple] 探针失败（按非亡灵处理）：{}", type, thrown);
        } finally {
            if (probe != null) {
                probe.discard();
            }
            probeRuns++;
            probeNanos += System.nanoTime() - started;
            PROBE_CACHE.put(type, undead);
        }
        return undead;
    }

    public static String probeStats() {
        return probeRuns + " 次 / " + (probeNanos / 1_000_000L) + " ms / 失败 " + probeFailures
                + " 次（结果已按类型缓存，" + PROBE_CACHE.size() + " 个类型）";
    }

    public static boolean wildUndeadTagLoaded() {
        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES.getValues()) {
            if (type.is(WILD_UNDEAD)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLowHealth(EntityType<?> type) {
        Float health = maxHealthOf(type);
        return health != null && health < MAX_UNDEAD_HEALTH;
    }

    @SuppressWarnings("unchecked")
    public static Float maxHealthOf(EntityType<?> type) {
        if (!DefaultAttributes.hasSupplier(type)) {
            return null;
        }
        AttributeSupplier supplier =
                DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) type);
        if (!supplier.hasAttribute(Attributes.MAX_HEALTH)) {
            return null;
        }
        return (float) supplier.getValue(Attributes.MAX_HEALTH);
    }

    public static List<String> debugReport(ServerLevel server) {
        List<String> lines = new ArrayList<>();
        List<EntityType<?>> pool = undeadPool(server);
        Map<String, Integer> buckets = new LinkedHashMap<>();
        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES.getValues()) {
            String reason = excludedWithoutProbe(type);
            if (reason != null) {
                buckets.merge(reason, 1, Integer::sum);
                continue;
            }
            if (type.getCategory() != MobCategory.MONSTER) {
                buckets.merge("不是敌对怪物分类", 1, Integer::sum);
                continue;
            }
            if (!isUndeadType(server, type)) {
                buckets.merge("不是亡灵（标签 / 兜底名单都没有它，探针读出的 getMobType() 也不是 UNDEAD）",
                        1, Integer::sum);
                continue;
            }
            Float health = maxHealthOf(type);
            if (health == null) {
                buckets.merge("读不到默认属性（非生物 / 模组没注册属性）", 1, Integer::sum);
                continue;
            }
            if (health >= MAX_UNDEAD_HEALTH) {
                buckets.merge("血量 ≥ " + MAX_UNDEAD_HEALTH + "（可能小首领 / 精英）", 1, Integer::sum);
                continue;
            }
            buckets.merge("✅ 进池（会被召唤）", 1, Integer::sum);
        }

        lines.add("青苹果召唤池诊断（候选池 " + pool.size() + " 种亡灵，每次抽 "
                + SUMMON_COUNT + " 种，25% 换成仆从版）");
        for (Map.Entry<String, Integer> entry : buckets.entrySet()) {
            lines.add("  " + entry.getKey() + " ×" + entry.getValue());
        }
        lines.add("亡灵判定探针：" + probeStats());

        lines.add("本模组依赖的实体标签规模：");
        Map<TagKey<EntityType<?>>, Integer> tagSizes = new LinkedHashMap<>();
        for (TagKey<EntityType<?>> tag : List.of(WILD_UNDEAD, GOETY_BOSSES, GOETY_MINI_BOSSES,
                FORGE_BOSSES)) {
            tagSizes.put(tag, tagSize(server, tag));
        }
        for (Map.Entry<TagKey<EntityType<?>>, Integer> entry : tagSizes.entrySet()) {
            if (entry.getValue() == 0) {
                String meaning = entry.getKey() == WILD_UNDEAD
                        ? "青苹果会退回内置兜底名单（原版 + Goety 的野生亡灵），别的附属加进标签的不会生效"
                        : "青苹果对应的那条排除规则现在等于失效";
                lines.add("  ⚠ " + entry.getKey().location() + " 命中 0 个实体——标签为空或者加载失败了"
                        + "（去 latest.log 搜 “Couldn't load tag " + entry.getKey().location() + "”）。"
                        + meaning);
            }
        }
        StringBuilder sizes = new StringBuilder("  ");
        for (Map.Entry<TagKey<EntityType<?>>, Integer> entry : tagSizes.entrySet()) {
            sizes.append(entry.getKey().location()).append('=')
                    .append(entry.getValue()).append("，");
        }
        lines.add(sizes.toString());
        lines.add(wildUndeadTagLoaded()
                ? "亡灵池判定：green_apple_summons 标签已加载；标签之外的实体还会走一次 getMobType() 探针"
                : "亡灵池判定：green_apple_summons 标签是空的（多半是标签里引用了本版本不存在的 id，"
                        + "整条被 TagLoader 丢弃）——现在由内置名单 + getMobType() 探针顶着");

        lines.add("点名黑名单现状（这些是标签拦不住的别家首领）：");
        for (String idText : KNOWN_BOSS_IDS) {
            EntityType<?> type = entityType(idText);
            if (type == null) {
                lines.add("  " + idText + "：本包没注册");
                continue;
            }
            String other = excludedOutsideBlacklist(type);
            Float health = maxHealthOf(type);
            boolean wouldEnter = other == null && type.getCategory() == MobCategory.MONSTER
                    && isUndeadType(server, type) && health != null && health < MAX_UNDEAD_HEALTH;
            if (wouldEnter) {
                lines.add("  " + idText + "：！已注册，而且没有黑名单就会进池（分类是敌对怪物、判成亡灵、"
                        + "血量 " + health + " < " + MAX_UNDEAD_HEALTH + "）——现在靠黑名单拦着");
            } else {
                lines.add("  " + idText + "：已注册，另有规则拦住（" + other + "）");
            }
        }

        if (!pool.isEmpty()) {
            List<String> ids = poolIds(pool);
            lines.add("池内清单（最多列 40 个，共 " + ids.size() + "）：");
            lines.add("  " + ids.subList(0, Math.min(40, ids.size())));
        }
        return lines;
    }

    public static String explain(ServerLevel server, EntityType<?> type) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        String reason = excludedWithoutProbe(type);
        if (reason != null) {
            return id + " → 被排除：" + reason;
        }
        if (type.getCategory() != MobCategory.MONSTER) {
            return id + " → 被排除：不是敌对怪物分类（" + type.getCategory() + "），不会是亡灵";
        }
        if (!isUndeadType(server, type)) {
            return id + " → 被排除：不在 goety_supreme:green_apple_summons 标签、不在内置名单，"
                    + "探针实例化后 getMobType() 也不是 UNDEAD";
        }
        Float health = maxHealthOf(type);
        if (health == null) {
            return id + " → 被排除：读不到默认属性（不是生物，或这个模组没按 Forge 常规注册属性），"
                    + "无法确认血量";
        }
        if (health >= MAX_UNDEAD_HEALTH) {
            return id + " → 被排除：默认最大生命 " + health + " ≥ " + MAX_UNDEAD_HEALTH
                    + "（超过野生亡灵血线）";
        }
        return id + " → 会进池、可能被召唤（分类敌对怪物 + 判成亡灵 + 血量 " + health + " < "
                + MAX_UNDEAD_HEALTH + "，且不撞首领标签 / 黑名单 / 关键词）";
    }

    public static int tagSize(ServerLevel server, TagKey<EntityType<?>> tag) {
        int count = 0;
        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES.getValues()) {
            if (type.is(tag)) {
                count++;
            }
        }
        return count;
    }

    public static EntityType<?> entityType(String idText) {
        ResourceLocation id = ResourceLocation.tryParse(idText.trim());
        return id == null ? null : ForgeRegistries.ENTITY_TYPES.getValue(id);
    }

    public static boolean isServantType(EntityType<?> type) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id != null) {
            return id.getPath().endsWith(SUFFIX_SERVANT);
        }
        Class<?> base = type.getBaseClass();
        return base != null && base != Entity.class && IOwned.class.isAssignableFrom(base)
                && base.getSimpleName().endsWith("Servant");
    }

    private static final String SUFFIX_SERVANT = "_servant";

    private static final class ServantIndex {

        private final Map<String, EntityType<?>> byBase;

        private ServantIndex(Map<String, EntityType<?>> byBase) {
            this.byBase = byBase;
        }

        static ServantIndex build() {
            Map<String, EntityType<?>> map = new HashMap<>();
            for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES.getValues()) {
                if (!isServantType(type)) {
                    continue;
                }
                ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
                if (id == null) {
                    continue;
                }
                String path = id.getPath();
                String base = path.endsWith(SUFFIX_SERVANT)
                        ? path.substring(0, path.length() - SUFFIX_SERVANT.length())
                        : path;
                if (base.isEmpty()) {
                    continue;
                }
                map.putIfAbsent(id.getNamespace() + ":" + base, type);
                if ("goety".equals(id.getNamespace())) {
                    map.putIfAbsent("goety:" + base, type);
                }
            }
            return new ServantIndex(map);
        }

        EntityType<?> find(ResourceLocation hostileId) {
            EntityType<?> sameNamespace = byBase.get(hostileId.getNamespace() + ":" + hostileId.getPath());
            if (sameNamespace != null) {
                return sameNamespace;
            }
            return byBase.get("goety:" + hostileId.getPath());
        }
    }

    private static EntityType<?> findServantType(EntityType<?> hostile, ServantIndex index) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(hostile);
        if (id == null) {
            return null;
        }
        EntityType<?> byTag = index.find(id);
        if (byTag != null) {
            return byTag;
        }
        String alias = SERVANT_ALIASES.get(id.toString());
        if (alias != null) {
            EntityType<?> named = ForgeRegistries.ENTITY_TYPES.getValue(
                    ResourceLocation.tryParse(alias));
            if (named != null && isServantType(named)) {
                return named;
            }
        }
        EntityType<?> servant = ForgeRegistries.ENTITY_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "_servant"));
        if (servant == null || !isServantType(servant)) {
            servant = ForgeRegistries.ENTITY_TYPES.getValue(
                    ResourceLocation.fromNamespaceAndPath("goety", id.getPath() + "_servant"));
        }
        return servant != null && isServantType(servant) ? servant : null;
    }

    private static EntityType<?> spawnType(List<EntityType<?>> pool, EntityType<?> hostile,
            boolean preferServant, RandomSource random, ServantIndex index) {
        if (!preferServant || findServantType(hostile, index) != null) {
            return hostile;
        }
        List<EntityType<?>> capable = new ArrayList<>();
        for (EntityType<?> type : pool) {
            if (findServantType(type, index) != null) {
                capable.add(type);
            }
        }
        if (capable.isEmpty()) {
            return hostile;
        }
        return capable.get(random.nextInt(capable.size()));
    }

    private static Mob createUndead(ServerLevel server, EntityType<?> hostile, boolean preferServant,
            ServantIndex index) {
        if (preferServant) {
            EntityType<?> servantType = findServantType(hostile, index);
            if (servantType != null) {
                Entity created = servantType.create(server);
                if (created instanceof Mob mob && isServantType(mob.getType())) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("[GreenApple] 25% 命中：{} → 仆从版 {}",
                                ForgeRegistries.ENTITY_TYPES.getKey(hostile),
                                ForgeRegistries.ENTITY_TYPES.getKey(servantType));
                    }
                    return mob;
                }
                if (created != null) {
                    LOGGER.debug("[GreenApple] {} 名字是仆从版但不是 _servant 类型，按野怪处理",
                            ForgeRegistries.ENTITY_TYPES.getKey(servantType));
                    created.discard();
                }
            }
        }
        Entity created = hostile.create(server);
        if (created instanceof Mob mob) {
            return mob;
        }
        if (created != null) {
            created.discard();
        }
        return null;
    }

    private static void bindServant(Mob mob, Player player) {
        if (!isServantType(mob.getType())) {
            return;
        }
        if (!(mob instanceof IOwned owned)) {
            return;
        }
        owned.setTrueOwner(player);
        owned.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
    }

    private static List<EntityType<?>> pickDistinct(List<EntityType<?>> pool, RandomSource random,
            int count) {
        List<EntityType<?>> remaining = new ArrayList<>(pool);
        List<EntityType<?>> picked = new ArrayList<>();
        while (picked.size() < count && !remaining.isEmpty()) {
            int total = 0;
            for (EntityType<?> type : remaining) {
                total += weightOf(type);
            }
            int roll = random.nextInt(Math.max(1, total));
            int accumulated = 0;
            EntityType<?> chosen = remaining.get(remaining.size() - 1);
            for (EntityType<?> type : remaining) {
                accumulated += weightOf(type);
                if (roll < accumulated) {
                    chosen = type;
                    break;
                }
            }
            picked.add(chosen);
            remaining.remove(chosen);
        }
        return picked;
    }

    private static int weightOf(EntityType<?> type) {
        if (type.is(GOETY_NECROMANCERS)) {
            return NECROMANCER_WEIGHT;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        return id != null && id.getPath().contains("necromancer")
                ? NECROMANCER_WEIGHT : UNDEAD_WEIGHT;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copyWithCount(1);
    }
}
