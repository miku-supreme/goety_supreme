package cn.blockforge.generated.goetysupreme.util;

import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.util.MutantThrowStandIns;

public final class MutantThrowStandIns {
    private static final Map<ResourceKey<Level>, WeakReference<MutantSnowGolem>> CACHE =
            new ConcurrentHashMap<>();

    private MutantThrowStandIns() {
    }

    public static MutantSnowGolem forLevel(ServerLevel serverLevel) {
        WeakReference<MutantSnowGolem> cached = CACHE.get(serverLevel.dimension());
        if (cached != null) {
            MutantSnowGolem golem = cached.get();
            if (golem != null && !golem.isRemoved() && golem.level() == serverLevel) {
                return golem;
            }
        }
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_snow_golem"));
        if (type == null) {
            return null;
        }
        Object created = type.create(serverLevel);
        if (created instanceof MutantSnowGolem golem) {
            golem.setNoAi(true);
            golem.setPersistenceRequired();
            CACHE.put(serverLevel.dimension(), new WeakReference<>(golem));
            return golem;
        }
        return null;
    }
}
