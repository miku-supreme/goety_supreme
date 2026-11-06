package cn.blockforge.generated.goetysupreme.compat;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.MutantPotionServantCompatibility;

public final class MutantPotionServantCompatibility {
    private static final TagKey<EntityType<?>> ZOMBIE_SERVANTS = servantTag("zombie_servants");
    private static final TagKey<EntityType<?>> SKELETON_SERVANTS = servantTag("skeleton_servants");

    private static final ResourceLocation MUTANT_ENDERMAN = mutant("mutant_enderman");
    private static final ResourceLocation MUTANT_SNOW_GOLEM = mutant("mutant_snow_golem");
    private static final ResourceLocation MUTANT_ZOMBIE = mutant("mutant_zombie");
    private static final ResourceLocation MUTANT_SKELETON = mutant("mutant_skeleton");

    private MutantPotionServantCompatibility() {
    }

    @Nullable
    public static EntityType<?> mutantFor(LivingEntity source) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(source.getType());
        if (id == null || !isOwnedServant(source)) {
            return null;
        }
        if (isEndermanServant(id)) {
            return entityType(MUTANT_ENDERMAN);
        }
        if (isSnowGolemServant(id)) {
            return entityType(MUTANT_SNOW_GOLEM);
        }
        if (source.getType().is(ZOMBIE_SERVANTS) || isSecondRounder(id, "second_rounder_zombie_servant")) {
            return entityType(MUTANT_ZOMBIE);
        }
        if ((source.getType().is(SKELETON_SERVANTS)
                || isSecondRounder(id, "second_rounder_skeleton_servant")) && !isWitherSkeleton(id)) {
            return entityType(MUTANT_SKELETON);
        }
        return null;
    }

    public static boolean isCompatibleSource(LivingEntity source) {
        return mutantFor(source) != null;
    }

    private static boolean isOwnedServant(LivingEntity source) {
        return source instanceof com.Polarice3.Goety.api.entities.IOwned;
    }

    private static boolean isEndermanServant(ResourceLocation id) {
        return is(id, GeneratedMod.MOD_ID, "enderman_servant")
                || is(id, GeneratedMod.MOD_ID, "ender_stowaway_servant")
                || is(id, "goetyawaken", "enderman_servant");
    }

    private static boolean isSnowGolemServant(ResourceLocation id) {
        return is(id, GeneratedMod.MOD_ID, "snow_golem_servant")
                || is(id, "goety", "snow_golem_servant");
    }

    private static boolean isSecondRounder(ResourceLocation id, String path) {
        return is(id, GeneratedMod.MOD_ID, path);
    }

    private static boolean isWitherSkeleton(ResourceLocation id) {
        return is(id, "goety", "wither_skeleton_servant")
                || is(id, "goety", "wither_necromancer_servant");
    }

    private static boolean is(ResourceLocation id, String namespace, String path) {
        return namespace.equals(id.getNamespace()) && path.equals(id.getPath());
    }

    private static TagKey<EntityType<?>> servantTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    private static ResourceLocation mutant(String path) {
        return ResourceLocation.fromNamespaceAndPath("mutantmonsters", path);
    }

    @Nullable
    private static EntityType<?> entityType(ResourceLocation id) {
        return ForgeRegistries.ENTITY_TYPES.getValue(id);
    }
}
