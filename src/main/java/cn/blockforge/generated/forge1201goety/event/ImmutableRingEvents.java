package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.event.ImmutableRingEvents;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ImmutableRingEvents {

    private static final int RENEW_EVERY = 20;

    private static final int RENEW_TICKS = 300;

    private static final int LEVEL_I = 0;

    private static final int LEVEL_II = 1;

    private static final int NO_SUPPLEMENT = -1;

    private static final float HIGH_HEALTH_THRESHOLD = 100.0F;

    private static final double ARMOR_HIGH_EXEMPT = 4.0D;

    private static final double ARMOR_MID = 10.0D;

    private static final double ARMOR_MID_HIGH = 15.0D;

    private static final Map<LivingEntity, Integer> SCANNED = new WeakHashMap<>();

    private static final float SKELETON_BOW_DAMAGE_BONUS = 1.5F;

    @Nullable
    private static MobEffect cachedIronHide;

    private ImmutableRingEvents() {
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof IOwned)) {
            return;
        }
        if (entity.level().isClientSide()) {
            return;
        }
        if (entity.tickCount % RENEW_EVERY != 0) {
            return;
        }
        int level = scanOnce(entity);
        if (level == NO_SUPPLEMENT) {
            return;
        }
        if (!RingServantGear.ringScopeApplies(entity)) {
            return;
        }
        entity.addEffect(new MobEffectInstance(ironHide(), RENEW_TICKS, level,
                false, false, true));
    }

    private static int scanOnce(LivingEntity servant) {
        Integer cached = SCANNED.get(servant);
        if (cached != null) {
            return cached;
        }
        int level = ironHideAmplifier(servant);
        SCANNED.put(servant, level);
        return level;
    }

    public static int ironHideAmplifier(LivingEntity entity) {
        if (entity == null) {
            return NO_SUPPLEMENT;
        }
        if (OminousCompat.loaded() && RingSetBonuses.isOminousWolf(entity)) {
            return NO_SUPPLEMENT;
        }
        double armor = RingServantGear.baseArmor(entity);
        if (entity instanceof AbstractSoulReaper) {
            return LEVEL_I;
        }
        if (RingServantGear.isNecromancerServant(entity)) {
            return UniversalSoulJarItem.isAllowedNecromancer(entity) ? LEVEL_II : LEVEL_I;
        }
        if (!RingServantGear.isArmorIncapable(entity)) {
            return NO_SUPPLEMENT;
        }
        if (entity.getMaxHealth() < HIGH_HEALTH_THRESHOLD) {
            if (armor >= ARMOR_MID && armor < ARMOR_MID_HIGH) {
                return LEVEL_I;
            }
            if (armor < ARMOR_MID) {
                return LEVEL_II;
            }
            return NO_SUPPLEMENT;
        }
        if (armor >= ARMOR_HIGH_EXEMPT) {
            return NO_SUPPLEMENT;
        }
        return LEVEL_I;
    }

    @SubscribeEvent
    public static void onSkeletonArrowHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) {
            return;
        }
        Entity shooter = event.getSource().getEntity();
        if (!(shooter instanceof LivingEntity) && arrow.getOwner() instanceof LivingEntity owner) {
            shooter = owner;
        }
        if (!(shooter instanceof LivingEntity living)) {
            return;
        }
        boolean skeleton = RingServantGear.isSkeletonServant(living);
        boolean playerMob = living instanceof PlayerMobServant;
        if (!skeleton && !playerMob) {
            return;
        }
        RingServantGear.Ring ring = RingServantGear.ringOf(RingServantGear.ownerChainPlayer(living));
        boolean qualifies = ring == RingServantGear.Ring.IMMUTABLE
                || ring == RingServantGear.Ring.TEMPERED;
        if (!qualifies) {
            return;
        }
        event.setAmount(event.getAmount() + SKELETON_BOW_DAMAGE_BONUS);
    }

    private static MobEffect ironHide() {
        if (cachedIronHide == null) {
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                    ResourceLocation.fromNamespaceAndPath("goety", "iron_hide"));
            cachedIronHide = effect != null ? effect : MobEffects.DAMAGE_RESISTANCE;
        }
        return cachedIronHide;
    }
}
