package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.PrisonServantResolver;

public final class PrisonServantResolver {
    private static final Set<String> GENERIC_TOKENS = Set.of(
            "mod", "mods", "addon", "addons", "compat", "minecraft", "forge", "the", "of",
            "goety", "goetic", "supreme");
    private static Map<String, Set<String>> dependencies;
    private static Map<String, String> namespaceMods;
    private static Map<String, Set<String>> namespaceWords;

    private PrisonServantResolver() { }

    private static void loadModRelations() {
        if (dependencies != null) return;
        dependencies = new HashMap<>();
        namespaceMods = new HashMap<>();
        namespaceWords = new HashMap<>();
        for (IModInfo mod : ModList.get().getMods()) {
            Set<String> deps = new HashSet<>();
            for (IModInfo.ModVersion dependency : mod.getDependencies()) {
                deps.add(dependency.getModId());
            }
            dependencies.put(mod.getModId(), deps);
            namespaceMods.put(mod.getNamespace(), mod.getModId());
            namespaceMods.put(mod.getModId(), mod.getModId());
            Set<String> words = tokens(mod.getModId());
            words.addAll(tokens(mod.getNamespace()));
            words.addAll(tokens(mod.getDisplayName()));
            namespaceWords.put(mod.getModId(), words);
            namespaceWords.put(mod.getNamespace(), words);
        }
    }

    public static Set<String> tokens(String name) {
        String separated = name.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
        Set<String> result = new HashSet<>();
        for (String token : separated.split("[^a-z0-9]+")) {
            if (token.length() >= 3 && !GENERIC_TOKENS.contains(token)) result.add(token);
        }
        return result;
    }

    private static boolean overlaps(String left, String right) {
        Set<String> words = new HashSet<>(namespaceWords.getOrDefault(left, tokens(left)));
        words.retainAll(namespaceWords.getOrDefault(right, tokens(right)));
        return !words.isEmpty();
    }

    private static boolean dependsOn(String mod, String target) {
        return dependencies.getOrDefault(mod, Set.of()).contains(target);
    }

    private static boolean goetyFamily(String namespace) {
        String mod = namespaceMods.getOrDefault(namespace, namespace);
        return namespace.equals("goety") || namespace.equals(GeneratedMod.MOD_ID)
                || namespace.contains("goety") || namespace.contains("goetic")
                || dependsOn(mod, "goety") || dependsOn(mod, GeneratedMod.MOD_ID);
    }

    private static int namespaceScore(String source, String candidate) {
        String sourceMod = namespaceMods.getOrDefault(source, source);
        String candidateMod = namespaceMods.getOrDefault(candidate, candidate);
        if (candidateMod.equals(sourceMod)) return 400;
        if (!isBaseMod(sourceMod) && !isBaseMod(candidateMod)
                && (dependsOn(candidateMod, sourceMod) || dependsOn(sourceMod, candidateMod))) {
            return 300 + (overlaps(source, candidate) ? 10 : 0);
        }
        if (overlaps(source, candidate)) return 200;
        return 0;
    }

    private static boolean isBaseMod(String mod) {
        return mod.equals("goety") || mod.equals(GeneratedMod.MOD_ID)
                || mod.equals("minecraft") || mod.equals("forge");
    }

    private static int candidateScore(String source, String candidate, boolean exact, boolean named) {
        int relation = namespaceScore(source, candidate);
        int nameScore = exact ? 1000 : named ? 500 : 100;
        if ((exact || named) && relation > 0) nameScore += 10000;
        return nameScore + relation + (goetyFamily(candidate) ? 10 : 0);
    }

    public static EntityType<?> resolve(Level level, ResourceLocation victim, float maxHealth) {
        loadModRelations();
        String name = victim.getPath().replaceFirst("^hostile_", "");
        Set<String> words = tokens(name);
        record Candidate(EntityType<?> type, ResourceLocation id, int score, boolean healthCheck) { }
        List<Candidate> candidates = new ArrayList<>();
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (id == null || id.equals(victim)) continue;
            String path = id.getPath();
            int relation = namespaceScore(victim.getNamespace(), id.getNamespace());
            boolean exact = path.equals(name + "_servant");
            boolean named = path.endsWith("_" + name + "_servant");
            Set<String> candidateWords = tokens(path);
            boolean fuzzy = !words.isEmpty() && candidateWords.containsAll(words);
            if (!exact && !named && !(fuzzy && (goetyFamily(id.getNamespace()) || relation > 0))) continue;
            int score = candidateScore(victim.getNamespace(), id.getNamespace(), exact, named);
            candidates.add(new Candidate(type, id, score, !exact && !named));
        }
        candidates.sort(Comparator.comparingInt(Candidate::score).reversed()
                .thenComparing(candidate -> candidate.id().toString()));
        for (Candidate candidate : candidates) {
            Entity probe = null;
            try {
                probe = candidate.type().create(level);
                if (!(probe instanceof LivingEntity living) || !(probe instanceof IServant)
                        || !(probe instanceof IOwned)) continue;
                float health = living.getMaxHealth();
                if (!candidate.healthCheck() || health >= maxHealth * 0.7F && health <= maxHealth * 1.2F) {
                    return candidate.type();
                }
            } catch (RuntimeException | LinkageError ignored) {
            } finally {
                if (probe != null) probe.discard();
            }
        }
        return null;
    }
}
