package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.WitherWarlordHelmetEffects;
import cn.blockforge.generated.goetysupreme.mixin.CuriosFinderNetherCrownMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherWarlordHelmetEffects {

    private static final int WINDOW_TICKS = 40;

    private static final float RAW_THRESHOLD = 60.0F;

    private static final int EXTRA_IFRAME_TICKS = 10;

    private static final int MAX_EXTRA_IFRAME_TICKS = 100;

    private static final Map<Player, Blessing> BLESSINGS = new WeakHashMap<>();

    private WitherWarlordHelmetEffects() {
    }

    public static boolean wearingHelmInHeadSlot(LivingEntity entity) {
        if (!(entity instanceof Player player) || !GeneratedMod.WITHER_WARLORD_HELMET.isPresent()) {
            return false;
        }
        return !CuriosFinder.findCurio(player, GeneratedMod.WITHER_WARLORD_HELMET.get()).isEmpty();
    }

    public static boolean countsAsNetherCrown(LivingEntity entity) {
        return wearingHelmInHeadSlot(entity);
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || !wearingHelmInHeadSlot(player)) {
            return;
        }
        Blessing blessing = BLESSINGS.get(player);
        if (blessing != null && blessing.extraIframes > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || !wearingHelmInHeadSlot(player)) {
            return;
        }
        Blessing blessing = BLESSINGS.computeIfAbsent(player, ignored -> new Blessing());
        long now = player.level().getGameTime();
        if (now - blessing.windowStart > WINDOW_TICKS) {
            blessing.windowStart = now;
            blessing.accumulated = 0.0F;
        }
        blessing.accumulated += event.getAmount();
        if (blessing.accumulated > RAW_THRESHOLD) {
            blessing.accumulated = 0.0F;
            blessing.windowStart = now;
            blessing.extraIframes = Math.min(MAX_EXTRA_IFRAME_TICKS,
                    blessing.extraIframes + EXTRA_IFRAME_TICKS);
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            Blessing blessing = BLESSINGS.get(player);
            if (blessing != null && blessing.extraIframes > 0) {
                blessing.extraIframes--;
            }
        }
    }

    private static final class Blessing {
        private long windowStart;
        private float accumulated;
        private int extraIframes;
    }
}
