package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.hostile.Wight;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.util.SoulReaperAmbush;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperFogClient;
import cn.blockforge.generated.goetysupreme.effect.SoulReaperOmenEffect;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class SoulReaperAmbush {

    public static final String DEFEATED_TAG = "GoetySupremeSoulReaperDefeated";

    public static final String SACRIFICE_TAG = "GoetySupremeSoulReaperSacrifice";

    private static final ResourceLocation WIGHT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wight");

    private static final List<Pending> PENDING = new ArrayList<>();

    private SoulReaperAmbush() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Mob mob)) {
            return;
        }
        if (!WIGHT_ID.equals(ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()))) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel server)) {
            return;
        }
        ServerPlayer player = nearestCandidate(server, mob.position());
        if (player == null || !canAmbush(server, player)) {
            return;
        }
        if (hasPendingFor(player.getUUID()) || hasActiveReaper(server, player)) {
            return;
        }
        double chance = ambushChance(player);
        if (player.getRandom().nextDouble() >= chance) {
            return;
        }
        beginRitual(server, player, mob.position(), mob.getUUID());
    }

    private static void beginRitual(ServerLevel level, ServerPlayer player, Vec3 origin,
            @Nullable UUID anchorId) {
        level.sendParticles(ParticleTypes.SOUL, origin.x, origin.y + 1.0D, origin.z,
                30, 0.6D, 0.8D, 0.6D, 0.02D);
        level.sendParticles(ParticleTypes.SMOKE, origin.x, origin.y + 1.0D, origin.z,
                24, 0.6D, 0.8D, 0.6D, 0.01D);
        level.playSound(null, BlockPos.containing(origin), SoundEvents.SOUL_ESCAPE,
                SoundSource.HOSTILE, 2.0F, 0.6F);

        int introTicks = SoulReaperConfig.SPAWN_RITUAL_TICKS.get();
        int seekTimeout = Math.max(introTicks, SoulReaperConfig.SPAWN_SEEK_TIMEOUT.get());
        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                GeneratedMod.SOUL_REAPER_OMEN.get(), introTicks, 0, false, false, false));

        if (anchorId != null && level.getEntity(anchorId) instanceof LivingEntity sacrifice) {
            sacrifice.getPersistentData().putBoolean(SACRIFICE_TAG, true);
        }

        PENDING.add(new Pending(level.dimension().location().toString(), anchorId,
                player.getUUID(), introTicks, seekTimeout, anchorId != null, origin));
    }

    @SubscribeEvent
    public static void onLivingDrops(net.minecraftforge.event.entity.living.LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Wight wight)) {
            return;
        }
        if (wight.getPersistentData().getBoolean(SACRIFICE_TAG)) {
            event.getDrops().clear();
            wight.getPersistentData().remove(SACRIFICE_TAG);
        }
    }

    public static boolean hasPending(ServerPlayer player) {
        return hasPendingFor(player.getUUID());
    }

    public static boolean forceAmbush(ServerLevel level, ServerPlayer player, @Nullable Mob anchor) {
        if (!player.isAlive() || player.isSpectator() || hasPendingFor(player.getUUID())) {
            return false;
        }
        beginRitual(level, player, anchor != null ? anchor.position() : player.position(),
                anchor != null ? anchor.getUUID() : null);
        return true;
    }

    @Nullable
    private static ServerPlayer nearestCandidate(ServerLevel level, Vec3 pos) {
        double radius = SoulReaperConfig.SPAWN_RADIUS.get();
        AABB area = new AABB(pos, pos).inflate(radius);
        ServerPlayer best = null;
        double bestDist = Double.MAX_VALUE;
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
            if (player.isSpectator() || !player.isAlive()) {
                continue;
            }
            if (com.Polarice3.Goety.utils.SEHelper.getSESouls(player)
                    <= SoulReaperConfig.SPAWN_SOUL_THRESHOLD.get()) {
                continue;
            }
            double dist = player.distanceToSqr(pos);
            if (dist < bestDist) {
                bestDist = dist;
                best = player;
            }
        }
        return best;
    }

    private static boolean canAmbush(ServerLevel level, ServerPlayer player) {
        if (player.getY() < 60.0D && !level.canSeeSky(player.blockPosition())) {
            return false;
        }
        if (level.getRaidAt(player.blockPosition()) != null) {
            return false;
        }
        double radius = SoulReaperConfig.SPAWN_BOSS_RADIUS.get();
        AABB area = player.getBoundingBox().inflate(radius);
        List<LivingEntity> bosses = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != player && e.isAlive() && RagingStarSupport.isBossLike(e));
        return bosses.isEmpty();
    }

    private static boolean hasPendingFor(UUID playerId) {
        for (Pending pending : PENDING) {
            if (pending.playerId.equals(playerId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasActiveReaper(ServerLevel level, ServerPlayer player) {
        AABB area = player.getBoundingBox().inflate(64.0D);
        return !level.getEntitiesOfClass(SoulReaper.class, area, Entity::isAlive).isEmpty();
    }

    private static double ambushChance(ServerPlayer player) {
        double chance = SoulReaperConfig.SPAWN_CHANCE.get();
        double bonus = SoulReaperConfig.SPAWN_CHANCE_BONUS.get();
        if (com.Polarice3.Goety.utils.SEHelper.getSESouls(player)
                > SoulReaperConfig.SPAWN_SOUL_HIGH_THRESHOLD.get()) {
            chance += bonus;
        }
        if (player.getPersistentData().getBoolean(DEFEATED_TAG)) {
            chance += bonus;
        }
        if (!player.level().isDay()) {
            chance += bonus;
        }
        return Math.min(0.55D, chance);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || PENDING.isEmpty()) {
            return;
        }
        Iterator<Pending> it = PENDING.iterator();
        while (it.hasNext()) {
            Pending pending = it.next();
            ServerLevel level = levelOf(pending.dimension);
            ServerPlayer player = playerOf(pending.playerId);
            if (level == null || player == null || !player.isAlive()) {
                it.remove();
                if (player != null) {
                    player.removeEffect(GeneratedMod.SOUL_REAPER_OMEN.get());
                }
                continue;
            }
            Entity wight = pending.wightId == null ? null : findEntity(level, pending.wightId);
            boolean wightAlive = wight != null && wight.isAlive();

            if (!pending.arriving && wightAlive) {
                boolean spotted = wight instanceof LivingEntity living && wightSpotted(player, living);
                if (spotted && !pending.seenOnce) {
                    pending.seenOnce = true;
                    pending.lastPos = wight.position();
                } else if (!pending.seenOnce) {
                    pending.lastPos = wight.position();
                }
            }
            ++pending.waited;

            if (!pending.arriving) {
                boolean shouldArrive = (pending.requireSight && pending.seenOnce)
                        || (!pending.requireSight && pending.waited >= pending.minTicks)
                        || (pending.wightId != null && !wightAlive && !pending.seenOnce)
                        || pending.waited >= pending.timeout;
                if (shouldArrive) {
                    beginArrival(level, player, pending, wight);
                }
            }
            if (pending.arriving && pending.waited >= pending.spawnAt) {
                it.remove();
                spawnReaper(level, player, pending);
            }
        }
    }

    private static void beginArrival(ServerLevel level, ServerPlayer player, Pending pending,
            @Nullable Entity wight) {
        pending.arriving = true;
        pending.spawnPos = pending.seenOnce && pending.lastPos != null
                ? pending.lastPos
                : airSpawn(level, player);

        if (wight instanceof LivingEntity living && living.isAlive()) {
            living.kill();
        }

        int arrivalTicks = Math.max(pending.minTicks,
                SoulReaperConfig.SPAWN_WIGHT_DEATH_TICKS.get());
        pending.spawnAt = pending.waited + Math.max(1, arrivalTicks);
        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                GeneratedMod.SOUL_REAPER_OMEN.get(), Math.max(1, arrivalTicks),
                SoulReaperOmenEffect.PHASE_ARRIVAL, false, false, false));

        if (pending.seenOnce) {
            Vec3 pos = pending.spawnPos;
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + 1.0D,
                    pos.z, 40, 0.6D, 0.9D, 0.6D, 0.03D);
            level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y + 1.0D,
                    pos.z, 24, 0.6D, 0.9D, 0.6D, 0.04D);
        }
        Vec3 pos = pending.spawnPos;
        level.playSound(null, BlockPos.containing(pos.x, pos.y, pos.z),
                SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 3.0F, 0.5F);
    }

    private static void spawnReaper(ServerLevel level, ServerPlayer player, Pending pending) {
        player.removeEffect(GeneratedMod.SOUL_REAPER_OMEN.get());
        Vec3 spawnPos = pending.spawnPos != null ? pending.spawnPos : airSpawn(level, player);

        SoulReaper reaper = new SoulReaper(ModEntityTypes.SOUL_REAPER.get(), level);
        reaper.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        reaper.setPersistenceRequired();
        level.addFreshEntity(reaper);
        reaper.setTarget(player);
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, spawnPos.x, spawnPos.y + 1.0D,
                spawnPos.z, 50, 0.8D, 1.0D, 0.8D, 0.04D);

        player.getPersistentData().putBoolean(DEFEATED_TAG, false);
    }

    private static boolean wightSpotted(ServerPlayer player, LivingEntity wight) {
        if (MobUtil.isDirectlyLooking(player, wight)) {
            return true;
        }
        if (wight.closerThan(player, 4.0D)) {
            return true;
        }
        return wight instanceof Wight goetyWight && goetyWight.isScreaming();
    }

    private static Vec3 airSpawn(ServerLevel level, ServerPlayer player) {
        double radius = SoulReaperConfig.SPAWN_RADIUS.get();
        for (int attempt = 0; attempt < 24; attempt++) {
            double angle = player.getRandom().nextDouble() * Math.PI * 2.0D;
            double distance = 8.0D + player.getRandom().nextDouble() * (radius - 8.0D);
            double x = player.getX() + Math.cos(angle) * distance;
            double z = player.getZ() + Math.sin(angle) * distance;
            BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.containing(x, player.getY(), z));
            double y = ground.getY() + SoulReaperConfig.SPAWN_HEIGHT.get();
            if (y < level.getMinBuildHeight() + 2 || y > level.getMaxBuildHeight() - 3) {
                continue;
            }
            return new Vec3(x, y, z);
        }
        return new Vec3(player.getX(), player.getY() + SoulReaperConfig.SPAWN_HEIGHT.get(),
                player.getZ());
    }

    @Nullable
    private static ServerLevel levelOf(String dimension) {
        net.minecraft.server.MinecraftServer server =
                net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return null;
        }
        for (ServerLevel level : server.getAllLevels()) {
            if (level.dimension().location().toString().equals(dimension)) {
                return level;
            }
        }
        return null;
    }

    @Nullable
    private static ServerPlayer playerOf(UUID id) {
        net.minecraft.server.MinecraftServer server =
                net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        return server == null ? null : server.getPlayerList().getPlayer(id);
    }

    @Nullable
    private static Entity findEntity(ServerLevel level, UUID id) {
        return level.getEntity(id);
    }

    private static final class Pending {

        private final String dimension;
        private final UUID wightId;
        private final UUID playerId;

        private final int minTicks;

        private final int timeout;

        private final boolean requireSight;

        private boolean seenOnce;

        private Vec3 lastPos;

        private boolean arriving;

        private int spawnAt;

        private Vec3 spawnPos;
        private int waited;

        private Pending(String dimension, UUID wightId, UUID playerId, int minTicks,
                int timeout, boolean requireSight, Vec3 origin) {
            this.dimension = dimension;
            this.wightId = wightId;
            this.playerId = playerId;
            this.minTicks = minTicks;
            this.timeout = timeout;
            this.requireSight = requireSight;
            this.lastPos = origin;
        }
    }
}
