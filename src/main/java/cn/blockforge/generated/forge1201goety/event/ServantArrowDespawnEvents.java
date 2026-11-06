package cn.blockforge.generated.goetysupreme.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.event.ServantArrowDespawnEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ServantArrowDespawnEvents {

    public static final String SERVANT_ARROW_TAG = "goety_supreme_servant_arrow";

    private static final double MAX_FLIGHT_DISTANCE = 100.0D;
    private static final int GROUND_DESPAWN_TICKS = 200;

    private static final double STATIONARY_DISTANCE_SQR = 1.0E-12D;

    private static final Map<AbstractArrow, FlightState> TRACKED = new ConcurrentHashMap<>();

    private ServantArrowDespawnEvents() {
    }

    private static final class FlightState {
        private double lastX;
        private double lastY;
        private double lastZ;
        private double distance;
        private int stationaryTicks;
    }

    private static boolean isServantArrow(AbstractArrow arrow) {
        return arrow.getPersistentData().getBoolean(SERVANT_ARROW_TAG)
                || arrow.getOwner() instanceof PlayerMobServant;
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof net.minecraft.server.level.ServerLevel)) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof AbstractArrow arrow && isServantArrow(arrow)) {
            FlightState state = new FlightState();
            state.lastX = arrow.getX();
            state.lastY = arrow.getY();
            state.lastZ = arrow.getZ();
            TRACKED.put(arrow, state);
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof AbstractArrow) {
            TRACKED.remove(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        TRACKED.clear();
    }

    @SubscribeEvent
    public static void onServerTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || TRACKED.isEmpty()) {
            return;
        }
        for (Map.Entry<AbstractArrow, FlightState> entry : TRACKED.entrySet()) {
            AbstractArrow arrow = entry.getKey();
            if (arrow.isRemoved() || !isServantArrow(arrow)) {
                TRACKED.remove(arrow);
                continue;
            }
            FlightState state = entry.getValue();
            double x = arrow.getX();
            double y = arrow.getY();
            double z = arrow.getZ();
            double dx = x - state.lastX;
            double dy = y - state.lastY;
            double dz = z - state.lastZ;
            double movedSqr = dx * dx + dy * dy + dz * dz;
            state.distance += Math.sqrt(movedSqr);
            state.lastX = x;
            state.lastY = y;
            state.lastZ = z;

            if (state.distance >= MAX_FLIGHT_DISTANCE) {
                TRACKED.remove(arrow);
                arrow.discard();
                continue;
            }
            if (movedSqr <= STATIONARY_DISTANCE_SQR) {
                if (++state.stationaryTicks >= GROUND_DESPAWN_TICKS) {
                    TRACKED.remove(arrow);
                    arrow.discard();
                }
            } else {
                state.stationaryTicks = 0;
            }
        }
    }
}
