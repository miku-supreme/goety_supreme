package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.projectiles.Lavaball;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

final class IfritMeteors {

    private static final double BLAST_RADIUS = 2.2D;
    private static final float BLAST_DAMAGE = 10.0F;
    private static final float DIRECT_DAMAGE = 5.0F;
    private static final float HEAL_PER_HIT = 2.0F;
    private static final double FALL_SPEED = 0.9D;
    private static final int MAX_LIFE = 200;

    private IfritMeteors() {
    }

    static void spawn(Ifrit mob, double x, double y, double z) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        Lavaball meteor = new Lavaball(server, x, y, z, 0.0D, -0.06D, 0.0D);
        meteor.setOwner(mob);
        meteor.setUpgraded(true);
        meteor.setDangerous(false);
        meteor.setDamage(0.0F);
        meteor.setExtraDamage(0.0F);
        meteor.setExplosionPower(0.0F);
        meteor.setFiery(0);
        meteor.setDeltaMovement(0.0D, -FALL_SPEED, 0.0D);
        if (server.addFreshEntity(meteor)) {
            mob.activeMeteors.add(meteor);
        }
        server.sendParticles(ParticleTypes.FLAME, x, y, z, 10, 0.3D, 0.3D, 0.3D, 0.03D);
        mob.playSound(SoundEvents.BLAZE_SHOOT, 1.2F, 0.7F);
    }

    static void discard(Ifrit mob) {
        for (Lavaball meteor : mob.activeMeteors) {
            if (meteor.isAlive()) {
                meteor.discard();
            }
        }
        mob.activeMeteors.clear();
    }

    static void tick(Ifrit mob) {
        if (mob.activeMeteors.isEmpty()) {
            return;
        }
        Iterator<Lavaball> iterator = mob.activeMeteors.iterator();
        while (iterator.hasNext()) {
            Lavaball meteor = iterator.next();
            boolean landed = meteor.isRemoved() || !meteor.isAlive();
            boolean expired = meteor.tickCount > MAX_LIFE;
            if (!landed && !expired) {
                continue;
            }
            iterator.remove();
            if (expired) {
                meteor.discard();
            }
            resolve(mob, meteor);
        }
    }

    private static void resolve(Ifrit mob, Lavaball meteor) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 at = meteor.position();
        AABB box = new AABB(
                at.x - BLAST_RADIUS, at.y - BLAST_RADIUS, at.z - BLAST_RADIUS,
                at.x + BLAST_RADIUS, at.y + BLAST_RADIUS, at.z + BLAST_RADIUS);
        List<LivingEntity> victims = server.getEntitiesOfClass(LivingEntity.class, box);
        LivingEntity direct = null;
        double closest = Double.MAX_VALUE;
        int hitCount = 0;
        for (LivingEntity victim : victims) {
            if (!IfritCombat.canHit(mob, victim) || victim.distanceToSqr(at) > BLAST_RADIUS * BLAST_RADIUS) {
                continue;
            }
            if (IfritCombat.hitVictim(mob, victim, BLAST_DAMAGE)) {
                hitCount++;
            }
            double distance = victim.distanceToSqr(at);
            if (distance < closest) {
                closest = distance;
                direct = victim;
            }
        }
        if (direct != null && direct.isAlive()) {
            IfritCombat.hitVictim(mob, direct, DIRECT_DAMAGE);
        }
        heal(mob, hitCount);
        server.sendParticles(ParticleTypes.EXPLOSION, at.x, at.y, at.z,
                1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(ParticleTypes.FLAME, at.x, at.y, at.z,
                24, BLAST_RADIUS * 0.35D, 0.4D, BLAST_RADIUS * 0.35D, 0.05D);
        server.sendParticles(ParticleTypes.LAVA, at.x, at.y, at.z,
                8, 0.4D, 0.2D, 0.4D, 0.0D);
        mob.playSound(SoundEvents.GENERIC_EXPLODE, 1.2F, 1.1F);
    }

    static void heal(Ifrit mob, int hits) {
        if (hits <= 0) {
            return;
        }
        float amount = HEAL_PER_HIT * hits * (mob.isClone() ? 0.5F : 1.0F);
        if (mob.isClone()) {
            Ifrit owner = mob.resolveCloneOwner();
            if (owner != null && owner.isAlive()) {
                owner.heal(amount);
            }
        } else {
            mob.heal(amount);
        }
    }
}
