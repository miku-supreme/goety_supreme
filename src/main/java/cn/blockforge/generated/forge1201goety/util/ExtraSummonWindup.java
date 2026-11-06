package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.entities.neutral.DrownedNecromancer;
import com.Polarice3.Goety.common.entities.util.SummonCircle;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.Polarice3.Goety.utils.SoundUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.mixin.SummonCircleRendererSpiritTextureMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ExtraSummonWindup {

    public static final int TICKS = 20;

    private static final Set<ResourceLocation> NO_CIRCLE_TYPES = Set.of(
            goety("phantom_servant"),
            goety("mini_ghast"),
            goety("ghast_servant"),
            goety("malghast"),
            goetySupreme("flying_fireball_servant"));

    public static boolean isNoCircleType(ResourceLocation type) {
        return type != null && NO_CIRCLE_TYPES.contains(type);
    }

    private static ResourceLocation goety(String path) {
        return ResourceLocation.fromNamespaceAndPath("goety", path);
    }

    private static ResourceLocation goetySupreme(String path) {
        return ResourceLocation.fromNamespaceAndPath("goety_supreme", path);
    }

    private static final List<PendingCap> PENDING_CAP = new ArrayList<>();

    private ExtraSummonWindup() {
    }

    public enum Kind {

        GROUND_CIRCLE,

        AIR_IMMEDIATE,

        FAILED
    }

    private record PendingCap(java.util.UUID summoner, ResourceLocation type, long dueTick) {
    }

    public static int pendingFor(LivingEntity summoner, Collection<ResourceLocation> types) {
        if (summoner == null || types == null || types.isEmpty()) {
            return 0;
        }
        net.minecraft.server.MinecraftServer server = summoner.level().getServer();
        if (server == null) {
            return 0;
        }
        long now = server.getTickCount();
        int count = 0;
        for (PendingCap pending : PENDING_CAP) {
            if (pending.dueTick() <= now) {
                continue;
            }
            if (pending.summoner().equals(summoner.getUUID()) && types.contains(pending.type())) {
                ++count;
            }
        }
        return count;
    }

    private static void trackPending(LivingEntity summoner, Entity entity, long dueTick) {
        if (summoner == null || entity == null) {
            return;
        }
        ResourceLocation type = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (type != null) {
            PENDING_CAP.add(new PendingCap(summoner.getUUID(), type, dueTick));
        }
    }

    public static Kind summon(ServerLevel level, Entity entity, Vec3 at, LivingEntity circleOwner) {
        ResourceLocation type = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (isNoCircleType(type)) {
            entity.setPos(at.x, at.y, at.z);
            if (!level.addFreshEntity(entity)) {
                return Kind.FAILED;
            }
            ServerParticleUtil.circularParticles(level, ModParticleTypes.SUMMON.get(),
                    at.x, at.y + 0.1D, at.z, 0.9F);
            playSound(entity, circleOwner);
            return Kind.AIR_IMMEDIATE;
        }
        long due = level.getServer().getTickCount() + TICKS;
        SummonCircle circle = new SummonCircle(level, at, entity, false, true, circleOwner);
        circle.setNoGravity(true);
        circle.noParticles = true;
        if (!level.addFreshEntity(circle)) {
            return Kind.FAILED;
        }
        trackPending(circleOwner, entity, due);
        return Kind.GROUND_CIRCLE;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        long now = event.getServer().getTickCount();
        PENDING_CAP.removeIf(pending -> pending.dueTick() <= now);
    }

    public static void playSound(Entity summoned, LivingEntity summoner) {
        if (summoner == null) {
            return;
        }
        Entity armSource = summoned != null ? summoned : summoner;
        armSource.playSound(ModSounds.GRAVE_GOLEM_ARM.get(), 1.0F, 1.0F);
        if (summoner instanceof DrownedNecromancer) {
            summoner.level().playSound(null, summoner.getX(), summoner.getY(), summoner.getZ(),
                    ModSounds.DROWNED_NECROMANCER_SUMMON.get(), summoner.getSoundSource(),
                    1.4F, 1.0F);
            return;
        }
        if (summoner instanceof AbstractReaperNecromancer) {
            summoner.playSound(ModSounds.NECROMANCER_SUMMON.get(), 1.2F, 0.7F);
            return;
        }
        if (summoner instanceof AbstractGreenSmith) {
            summoner.playSound(ModSounds.NECROMANCER_SUMMON.get(), 1.4F, 0.7F);
            return;
        }
        if (summoned != null) {
            SoundUtil.playNecromancerSummon(summoned);
        }
    }
}
