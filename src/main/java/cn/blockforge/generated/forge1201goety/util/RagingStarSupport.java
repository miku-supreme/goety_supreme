package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.mixin.GoetyScatterBombHomingMixin;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class RagingStarSupport {

    private static final String IFRAME_KEY = "GoetySupremeRagingStarIframe";

    public static final int INDEPENDENT_INVULNERABLE_TICKS = 10;

    public static final String NBT_KEY = "GoetySupremeRagingStar";

    public static final String ANTIAIR_HOMING = "GoetySupremeAntiairHoming";

    public static final String ANTIAIR_TARGET = "GoetySupremeAntiairTarget";

    public static final String ANTIAIR_EXPIRE = "GoetySupremeAntiairExpire";

    public static final int ANTIAIR_LIFESPAN_TICKS = 10 * 20;

    public static final String SPRAY_MINE = "GoetySupremeSprayMine";

    public static final String RAGING_CUBE_MINE = "GoetySupremeRagingCubeMine";

    public static final float DAMAGE_MULTIPLIER = 0.75F;

    public static final float ELEMENT_DAMAGE_MULTIPLIER = 0.5F;

    public static final int BUFF_DURATION = 600;

    public static final double ROAR_RADIUS = 32.0D;

    private RagingStarSupport() {
    }

    public static void tickStarParticles(LivingEntity entity) {
        if (!entity.level().isClientSide() || !(entity instanceof RagingStarServant servant)
                || !servant.isRagingStarred() || entity.getRandom().nextInt(2) != 0) {
            return;
        }
        entity.level().addParticle(ParticleTypes.FIREWORK,
                entity.getRandomX(0.7D), entity.getRandomY(), entity.getRandomZ(0.7D),
                entity.getRandom().nextGaussian() * 0.02D, 0.1D,
                entity.getRandom().nextGaussian() * 0.02D);
    }

    public static void tickStarProtection(LivingEntity entity) {
        if (entity instanceof RagingStarServant servant && servant.isRagingStarred()
                && entity.isOnFire()) {
            entity.clearFire();
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || !(entity instanceof RagingStarServant servant)
                || !servant.isRagingStarred()) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.WITHER)
                || source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        long now = entity.level().getGameTime();
        long last = entity.getPersistentData().getLong(IFRAME_KEY);
        if (now - last < INDEPENDENT_INVULNERABLE_TICKS) {
            event.setAmount(0.0F);
            return;
        }
        if (event.getAmount() > 0.0F) {
            entity.getPersistentData().putLong(IFRAME_KEY, now);
        }
    }

    public static boolean isElementReducedSource(DamageSource source) {
        return source.is(DamageTypeTags.IS_EXPLOSION)
                || source.is(DamageTypes.WITHER)
                || source.is(DamageTypeTags.IS_FIRE)
                || source.is(DamageTypes.MAGIC)
                || source.is(DamageTypes.INDIRECT_MAGIC)
                || source.is(DamageTypeTags.IS_DROWNING)
                || source.is(DamageTypeTags.IS_FREEZING)
                || source.is(DamageTypeTags.IS_FALL);
    }

    public static boolean isFriendlyUnit(LivingEntity self, LivingEntity other) {
        if (other == self) {
            return true;
        }
        LivingEntity owner = self instanceof OwnableEntity ownable ? ownable.getOwner() : null;
        if (owner != null && other == owner) {
            return true;
        }
        if (self.isAlliedTo(other) || other.isAlliedTo(self)) {
            return true;
        }
        if (owner != null) {
            if (other.isAlliedTo(owner) || owner.isAlliedTo(other)) {
                return true;
            }
            if (other instanceof OwnableEntity otherOwned && otherOwned.getOwner() == owner) {
                return true;
            }
        }
        return false;
    }

    private static final TagKey<EntityType<?>> GOETY_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "bosses"));
    private static final TagKey<EntityType<?>> GOETY_MINI_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "mini_bosses"));
    private static final TagKey<EntityType<?>> FORGE_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", "bosses"));

    public static boolean isBossLike(LivingEntity entity) {
        EntityType<?> type = entity.getType();
        return type.is(GOETY_BOSSES) || type.is(GOETY_MINI_BOSSES) || type.is(FORGE_BOSSES);
    }

    public static boolean isVanillaHarmful(MobEffectInstance instance) {
        if (instance == null || instance.getEffect() == null
                || instance.getEffect().isBeneficial()
                || instance.getEffect().isInstantenous()) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(instance.getEffect());
        return id != null && "minecraft".equals(id.getNamespace());
    }
}
