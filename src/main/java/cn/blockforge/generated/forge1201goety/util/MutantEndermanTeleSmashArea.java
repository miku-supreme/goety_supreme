package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import fuzs.mutantmonsters.util.EntityUtil;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.util.MutantEndermanTeleSmashArea;
import cn.blockforge.generated.goetysupreme.mixin.MutantEndermanTeleSmashMixin;

public final class MutantEndermanTeleSmashArea {

    private MutantEndermanTeleSmashArea() {
    }

    public static List<LivingEntity> victims(LivingEntity caster, LivingEntity primary,
            Predicate<LivingEntity> attackable) {
        List<LivingEntity> victims = new ArrayList<>();
        if (primary == null || !(caster.level() instanceof ServerLevel server) || !primary.isAlive()) {
            return victims;
        }
        victims.add(primary);
        double radius = MutantEndermanServantConfig.TELESMASH_AOE_RADIUS.get();
        double centerX = primary.getX();
        double centerY = primary.getY();
        double centerZ = primary.getZ();
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class,
                primary.getBoundingBox().inflate(radius, 4.0D, radius))) {
            if (victim == primary || !victim.isAlive() || !attackable.test(victim)) {
                continue;
            }
            double dx = victim.getX() - centerX;
            double dy = victim.getY() - centerY;
            double dz = victim.getZ() - centerZ;
            if (dx * dx + dy * dy + dz * dz > radius * radius) {
                continue;
            }
            victims.add(victim);
        }
        double frontRadius = MutantEndermanServantConfig.TELESMASH_FRONT_RADIUS.get();
        double halfAngle = MutantEndermanServantConfig.TELESMASH_FRONT_HALF_ANGLE.get();
        if (frontRadius > 0.0D) {
            List<LivingEntity> inFront = new ArrayList<>(server.getEntitiesOfClass(LivingEntity.class,
                    caster.getBoundingBox().inflate(frontRadius, 8.0D, frontRadius)));
            inFront.sort(java.util.Comparator.comparingDouble(caster::distanceToSqr));
            for (LivingEntity victim : inFront) {
                if (victim == caster || !victim.isAlive() || !attackable.test(victim)) {
                    continue;
                }
                double dx = caster.getX() - victim.getX();
                double dz = caster.getZ() - victim.getZ();
                if (dx * dx + dz * dz > frontRadius * frontRadius) {
                    continue;
                }
                if (EntityUtil.getHeadAngle(caster, dx, dz) > halfAngle) {
                    continue;
                }
                if (!victims.contains(victim)) {
                    victims.add(victim);
                }
            }
        }
        return victims;
    }

    public static boolean lift(ServerLevel server, LivingEntity victim, RandomSource random) {
        for (int attempt = 0; attempt < 16; attempt++) {
            double x = victim.getX() + (random.nextDouble() - 0.5D) * 14.0D;
            double y = Math.min(victim.getY() + random.nextDouble()
                    + (victim instanceof Player ? 13.0D : 7.0D), server.getMaxBuildHeight() - victim.getBbHeight());
            double z = victim.getZ() + (random.nextDouble() - 0.5D) * 14.0D;
            BlockPos pos = BlockPos.containing(x, y, z);
            AABB box = victim.getBoundingBox().move(x - victim.getX(), y - victim.getY(), z - victim.getZ());
            if (!server.hasChunkAt(pos) || !server.getWorldBorder().isWithinBounds(box)
                    || !server.noCollision(victim, box) || server.containsAnyLiquid(box)) {
                continue;
            }
            victim.stopRiding();
            EntityUtil.stunRavager(victim);
            EntityUtil.sendParticlePacket(victim, soulParticle(), 256);
            victim.teleportTo(x, y, z);
            EntityUtil.sendParticlePacket(victim, soulParticle(), 256);
            server.playSound(null, x, y, z, SoundEvents.ENDERMAN_TELEPORT, victim.getSoundSource(),
                    1.2F, 0.9F + random.nextFloat() * 0.2F);
            return true;
        }
        return false;
    }

    public static ParticleOptions soulParticle() {
        Object particle = BuiltInRegistries.PARTICLE_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "endersoul"));
        return particle instanceof SimpleParticleType type ? type : ParticleTypes.PORTAL;
    }
}
