package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import cn.blockforge.generated.goetysupreme.renderer.BoneParticleFx;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;

@OnlyIn(Dist.CLIENT)
public final class BoneParticleFx {

    private static final Map<Integer, Matrix4f> BASE_INVERSES = new HashMap<>();

    private static final Map<Long, Long> LAST_SPAWN = new HashMap<>();

    private static final long SPAWN_INTERVAL_NANOS = 40_000_000L;

    private BoneParticleFx() {
    }

    public static void captureFrameBase(int entityId, Matrix4f base) {
        BASE_INVERSES.put(entityId, new Matrix4f(base).invert());
    }

    public static void emit(LivingEntity entity, PoseStack poseStack, float partialTick, int slot,
            int count, double spread, double upward, ParticleOptions... particles) {
        if (count <= 0 || particles.length == 0) {
            return;
        }
        Matrix4f inverse = BASE_INVERSES.get(entity.getId());
        if (inverse == null) {
            return;
        }
        long now = System.nanoTime();
        long key = ((long) entity.getId() << 8) | (slot & 0xFFL);
        Long last = LAST_SPAWN.get(key);
        if (last != null && now - last < SPAWN_INTERVAL_NANOS) {
            return;
        }
        LAST_SPAWN.put(key, now);

        Vector4f viewPoint = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F).mul(poseStack.last().pose());
        Vector4f local = new Vector4f(viewPoint.x, viewPoint.y, viewPoint.z, 1.0F).mul(inverse);
        Vec3 pos = entity.getPosition(partialTick).add(local.x, local.y, local.z);

        for (int i = 0; i < count; i++) {
            ParticleOptions particle = particles[i % particles.length];
            double ox = (entity.getRandom().nextDouble() - 0.5D) * 2.0D * spread;
            double oy = (entity.getRandom().nextDouble() - 0.5D) * 2.0D * spread;
            double oz = (entity.getRandom().nextDouble() - 0.5D) * 2.0D * spread;
            entity.level().addParticle(particle, pos.x + ox, pos.y + oy, pos.z + oz,
                    0.0D, upward, 0.0D);
        }
        prune(now);
    }

    private static void prune(long now) {
        if (BASE_INVERSES.size() <= 64) {
            return;
        }
        Iterator<Map.Entry<Long, Long>> it = LAST_SPAWN.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Long> entry = it.next();
            if (now - entry.getValue() > 5_000_000_000L) {
                int entityId = (int) (entry.getKey() >> 8);
                BASE_INVERSES.remove(entityId);
                it.remove();
            }
        }
    }
}
