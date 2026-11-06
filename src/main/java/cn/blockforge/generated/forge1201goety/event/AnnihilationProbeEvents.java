package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbe;
import cn.blockforge.generated.goetysupreme.event.AnnihilationProbeEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class AnnihilationProbeEvents {

    private AnnihilationProbeEvents() {
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        log("ATTACK", event.getEntity(), event.getSource(), event.getAmount(), event.isCanceled());
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        log("HURT", event.getEntity(), event.getSource(), event.getAmount(), event.isCanceled());
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        log("DAMAGE", event.getEntity(), event.getSource(), event.getAmount(), event.isCanceled());
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!AnnihilationProbe.isEnabled()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            return;
        }
        if (!entity.isAlive()) {
            AnnihilationProbe.forget(entity.getUUID());
            return;
        }
        AnnihilationProbe.watchGates(entity);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        AnnihilationProbe.expireIfNeeded();
    }

    private static void log(String stage, LivingEntity victim, DamageSource source, float amount, boolean canceled) {
        if (!AnnihilationProbe.isEnabled() || victim.level().isClientSide()) {
            return;
        }
        if (!AnnihilationProbe.isRelevant(victim, source)) {
            return;
        }
        AnnihilationProbe.record("[" + stage + "] " + AnnihilationProbe.entity(victim)
                + " amount=" + amount + " canceled=" + canceled
                + " " + AnnihilationProbe.damageType(source)
                + " " + AnnihilationProbe.chain(source)
                + " " + AnnihilationProbe.state(victim)
                + " | " + AnnihilationProbe.goetyGates(victim));
    }
}
