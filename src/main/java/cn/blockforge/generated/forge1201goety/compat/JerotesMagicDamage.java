package cn.blockforge.generated.goetysupreme.compat;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.entity.SerponFireballEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;

public final class JerotesMagicDamage {

    private static final ResourceKey<DamageType> JEROTES_FIRE_MAGIC =
            create("jerotes", "fire_magic");

    private static final ResourceKey<DamageType> JEROTES_BYPASSES_COOLDOWN_FIRE =
            create("jerotes", "bypasses_cooldown_fire");

    private static final ResourceKey<DamageType> JEROTES_BYPASSES_COOLDOWN_SHOOT =
            create("jerotes", "bypasses_cooldown_shoot");

    private static final ResourceKey<DamageType> JEROTES_FIRE_MELEE =
            create("jerotes", "fire_melee");

    private static final ResourceKey<DamageType> JEROTES_NECROTIC =
            create("jerotes", "necrotic");

    private static final ResourceKey<DamageType> JEROTES_CORROSION =
            create("jerotes", "corrosion");

    private static final ResourceKey<DamageType> JEROTES_FREEZE_MAGIC =
            create("jerotes", "freeze_magic");

    private static final ResourceKey<DamageType> JEROTES_FREEZE_MELEE =
            create("jerotes", "freeze_melee");

    private static final ResourceKey<DamageType> JEROTES_DROWN_MAGIC =
            create("jerotes", "drown_magic");

    private static final ResourceKey<DamageType> JEROTES_BYPASSES_COOLDOWN_DROWN =
            create("jerotes", "bypasses_cooldown_drown");

    private static final ResourceKey<DamageType> JEROTES_LIGHTNING_MAGIC =
            create("jerotes", "lightning_magic");

    private static final ResourceKey<DamageType> FALLBACK_FIRE_MAGIC =
            create(GeneratedMod.MOD_ID, "fire_magic");
    private static final ResourceKey<DamageType> FALLBACK_BYPASSES_COOLDOWN_FIRE =
            create(GeneratedMod.MOD_ID, "bypasses_cooldown_fire");
    private static final ResourceKey<DamageType> FALLBACK_BYPASSES_COOLDOWN_SHOOT =
            create(GeneratedMod.MOD_ID, "bypasses_cooldown_shoot");

    private JerotesMagicDamage() {
    }

    public static DamageSource fireMagic(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_FIRE_MAGIC, FALLBACK_FIRE_MAGIC, direct, causing);
    }

    public static DamageSource bypassesCooldownFire(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_BYPASSES_COOLDOWN_FIRE,
                FALLBACK_BYPASSES_COOLDOWN_FIRE, direct, causing);
    }

    public static DamageSource bypassesCooldownShoot(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_BYPASSES_COOLDOWN_SHOOT,
                FALLBACK_BYPASSES_COOLDOWN_SHOOT, direct, causing);
    }

    public static DamageSource fireMelee(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_FIRE_MELEE,
                net.minecraft.world.damagesource.DamageTypes.MOB_ATTACK, direct, causing);
    }

    public static DamageSource freezeMelee(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_FREEZE_MELEE,
                net.minecraft.world.damagesource.DamageTypes.MOB_ATTACK, direct, causing);
    }

    public static DamageSource necrotic(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_NECROTIC,
                net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, direct, causing);
    }

    public static DamageSource drownMagic(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_DROWN_MAGIC,
                net.minecraft.world.damagesource.DamageTypes.DROWN, direct, causing);
    }

    public static DamageSource corrosion(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_CORROSION,
                net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, direct, causing);
    }

    public static DamageSource freezeMagic(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_FREEZE_MAGIC,
                net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, direct, causing);
    }

    public static DamageSource bypassesCooldownDrown(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_BYPASSES_COOLDOWN_DROWN,
                net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, direct, causing);
    }

    public static DamageSource lightningMagic(Entity direct, Entity causing) {
        return source(direct.level(), JEROTES_LIGHTNING_MAGIC,
                net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, direct, causing);
    }

    private static DamageSource source(Level level, ResourceKey<DamageType> preferred,
            ResourceKey<DamageType> fallback, Entity direct, Entity causing) {
        var registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        Optional<Holder.Reference<DamageType>> jerotesHolder = registry.getHolder(preferred);
        Holder<DamageType> holder = jerotesHolder.isPresent()
                ? jerotesHolder.get()
                : registry.getHolderOrThrow(fallback);
        return new DamageSource(holder, direct, causing);
    }

    private static ResourceKey<DamageType> create(String namespace, String path) {
        return ResourceKey.create(Registries.DAMAGE_TYPE,
                ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
}
