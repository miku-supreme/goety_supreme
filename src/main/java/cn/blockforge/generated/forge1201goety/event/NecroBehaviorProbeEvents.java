package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.debug.NecroBehaviorProbe;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.NecroBehaviorProbeEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class NecroBehaviorProbeEvents {

    private NecroBehaviorProbeEvents() {
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!NecroBehaviorProbe.isEnabled()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            return;
        }
        NecroBehaviorProbe.tickEntity(entity);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!NecroBehaviorProbe.isEnabled() || event.getEntity().level().isClientSide()) {
            return;
        }
        DamageSource source = event.getSource();
        if (source == null) {
            return;
        }
        LivingEntity attacker = source.getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) {
            return;
        }
        if (!NecroBehaviorProbe.isTracked(attacker)) {
            return;
        }
        LivingEntity target = event.getEntity();
        NecroBehaviorProbe.record("[NECRO-ATTACK] by=" + NecroBehaviorProbe.entityTag(attacker)
                + " victim=" + NecroBehaviorProbe.entityTag(target)
                + " amount=" + NecroBehaviorProbe.fmt(event.getAmount())
                + " canceled=" + event.isCanceled()
                + " direct=" + NecroBehaviorProbe.entityTag(source.getDirectEntity())
                + " | " + NecroBehaviorProbe.brief(attacker));
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!NecroBehaviorProbe.isEnabled() || event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof Projectile projectile)) {
            return;
        }
        if (!(projectile.getOwner() instanceof LivingEntity owner)) {
            return;
        }
        if (!NecroBehaviorProbe.isTracked(owner)) {
            return;
        }
        NecroBehaviorProbe.record("[NECRO-PROJECTILE] owner=" + NecroBehaviorProbe.entityTag(owner)
                + " projectile=" + NecroBehaviorProbe.entityTag(projectile)
                + " pos=" + NecroBehaviorProbe.fmt(projectile.getX()) + ','
                + NecroBehaviorProbe.fmt(projectile.getY()) + ','
                + NecroBehaviorProbe.fmt(projectile.getZ()));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        NecroBehaviorProbe.expireIfNeeded();
    }
}
