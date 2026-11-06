package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;

final class IfritCombat {

    static final double KNOCKBACK_SCALE = 0.45D;
    static final int QUAKE_TICKS = 20;
    static final float COMBO4_SLAM_HEALTH_RATIO = 0.10F;
    static final float COMBO4_HEAL_PER_TARGET = 10.0F;

    private static final double SLAM_DIRECT_RADIUS = 4.0D;
    private static final float HELLFIRE_SLAM = 10.0F;
    private static final float DMG_COMBO4_SLAM = 20.0F;
    private static final int STUN_TICKS = 30;
    private static final float HELLFIRE_PHASE_TWO_BONUS = 1.0F;

    private IfritCombat() {
    }

    static int hitDashSweep(Ifrit mob, double radius, float damage) {
        List<LivingEntity> victims = mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(radius, 4.0D, radius));
        int hits = 0;
        for (LivingEntity victim : victims) {
            if (!canHit(mob, victim) || mob.dashHitVictims.contains(victim)) {
                continue;
            }
            double dx = victim.getX() - mob.getX();
            double dz = victim.getZ() - mob.getZ();
            if (dx * dx + dz * dz > radius * radius) {
                continue;
            }
            mob.dashHitVictims.add(victim);
            if (!hitVictim(mob, victim, damage)) {
                mob.dashHitVictims.remove(victim);
                continue;
            }
            Vec3 push = new Vec3(dx, 0.0D, dz);
            if (push.lengthSqr() < 1.0E-4D) {
                push = mob.getLookAngle();
            }
            push = push.normalize().scale(0.9D);
            victim.push(push.x * KNOCKBACK_SCALE, 0.55D * KNOCKBACK_SCALE,
                    push.z * KNOCKBACK_SCALE);
            hits++;
        }
        if (hits > 0) {
            IfritSounds.playImpactSound(mob);
        }
        return hits;
    }

    static int hitCone(Ifrit mob, double radius, float damage, double minDot, double knockUp) {
        Vec3 look = mob.getLookAngle();
        Vec3 origin = mob.position();
        AABB box = mob.getBoundingBox().inflate(radius, 2.5D, radius);
        List<LivingEntity> victims = mob.level().getEntitiesOfClass(LivingEntity.class, box);
        int hits = 0;
        for (LivingEntity victim : victims) {
            if (!canHit(mob, victim) || victim.distanceTo(mob) > radius + 1.0D) {
                continue;
            }
            Vec3 to = victim.position().subtract(origin);
            if (to.lengthSqr() > 1.0E-4D) {
                Vec3 dir = to.normalize();
                if (dir.dot(look) < minDot) {
                    continue;
                }
            }
            if (!hitVictim(mob, victim, damage)) {
                continue;
            }
            Vec3 push = victim.position().subtract(origin).normalize().scale(0.7D);
            victim.push(push.x * KNOCKBACK_SCALE, knockUp * KNOCKBACK_SCALE,
                    push.z * KNOCKBACK_SCALE);
            if (mob.getTarget() == victim) {
                victim.setLastHurtByMob(mob);
            }
            hits++;
        }
        mob.burst(ParticleTypes.FLAME, 18, 0.05D);
        if (hits > 0) {
            IfritSounds.playImpactSound(mob);
        }
        return hits;
    }

    static int hitAoe(Ifrit mob, double radius, float damage) {
        AABB box = mob.getBoundingBox().inflate(radius, 3.0D, radius);
        List<LivingEntity> victims = mob.level().getEntitiesOfClass(LivingEntity.class, box);
        int hits = 0;
        for (LivingEntity victim : victims) {
            if (!canHit(mob, victim) || victim.distanceTo(mob) > radius + 1.0D) {
                continue;
            }
            if (!hitVictim(mob, victim, damage)) {
                continue;
            }
            Vec3 push = victim.position().subtract(mob.position()).normalize().scale(1.1D);
            victim.push(push.x * KNOCKBACK_SCALE, 0.75D * KNOCKBACK_SCALE,
                    push.z * KNOCKBACK_SCALE);
            hits++;
        }
        if (hits > 0) {
            IfritSounds.playImpactSound(mob);
        }
        return hits;
    }

    static int hitSideSectors(Ifrit mob, double radius, float damage) {
        Vec3 look = mob.getLookAngle();
        Vec3 origin = mob.position();
        AABB box = mob.getBoundingBox().inflate(radius, 2.5D, radius);
        List<LivingEntity> victims = mob.level().getEntitiesOfClass(LivingEntity.class, box);
        int hits = 0;
        for (LivingEntity victim : victims) {
            if (!canHit(mob, victim) || victim.distanceTo(mob) > radius + 1.0D) {
                continue;
            }
            Vec3 to = victim.position().subtract(origin);
            if (to.lengthSqr() < 1.0E-4D) {
                continue;
            }
            Vec3 dir = to.normalize();
            double front = dir.dot(look);
            double side = dir.dot(new Vec3(-look.z, 0.0D, look.x));
            if (Math.abs(side) < Math.abs(front)) {
                continue;
            }
            if (!hitVictim(mob, victim, damage)) {
                continue;
            }
            Vec3 push = new Vec3(-look.z, 0.0D, look.x).scale(Math.signum(side) * 1.2D);
            victim.push(push.x * KNOCKBACK_SCALE, 0.5D * KNOCKBACK_SCALE,
                    push.z * KNOCKBACK_SCALE);
            hits++;
        }
        mob.burst(ParticleTypes.FLAME, 28, 0.08D);
        if (hits > 0) {
            IfritSounds.playImpactSound(mob);
        }
        return hits;
    }

    static void slamShockwave(Ifrit mob) {
        Vec3 look = mob.getLookAngle().multiply(1.0D, 0.0D, 1.0D);
        if (look.lengthSqr() < 1.0E-4D) {
            look = new Vec3(0.0D, 0.0D, 1.0D);
        }
        mob.quakeDir = look.normalize();
        mob.quakeOriginX = mob.getX();
        mob.quakeOriginZ = mob.getZ();
        mob.quakeRefY = mob.getY();
        mob.quakeAge = 0;
        mob.quakeTicks = QUAKE_TICKS;
        mob.quakeHits.clear();
        mob.burst(ParticleTypes.EXPLOSION_EMITTER, 1, 0.0D);
        mob.playSound(SoundEvents.GENERIC_EXPLODE, 1.8F, 0.6F);
        mob.playSound(SoundEvents.ANVIL_LAND, 1.4F, 0.7F);
        slamImpact(mob);
    }

    static void slamImpact(Ifrit mob) {
        if (!(mob.level() instanceof ServerLevel server)) {
            return;
        }
        mob.shieldBreakStrike = true;
        mob.hellfireBonus = HELLFIRE_SLAM;
        try {
            slamImpactHits(mob, server);
        } finally {
            mob.shieldBreakStrike = false;
            mob.hellfireBonus = 0.0F;
        }
    }

    private static void slamImpactHits(Ifrit mob, ServerLevel server) {
        Vec3 dir = mob.quakeDir.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 0.0D, 1.0D) : mob.quakeDir;
        double cx = mob.quakeOriginX + dir.x;
        double cz = mob.quakeOriginZ + dir.z;
        AABB box = new AABB(cx - SLAM_DIRECT_RADIUS, mob.quakeRefY - 4.0D, cz - SLAM_DIRECT_RADIUS,
                cx + SLAM_DIRECT_RADIUS, mob.quakeRefY + 5.0D, cz + SLAM_DIRECT_RADIUS);
        boolean hit = false;
        int direct = 0;
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, box)) {
            if (!canHit(mob, victim) || !mob.quakeHits.add(victim.getId())) {
                continue;
            }
            if (!hitVictim(mob, victim,
                    DMG_COMBO4_SLAM + victim.getMaxHealth() * COMBO4_SLAM_HEALTH_RATIO)) {
                mob.quakeHits.remove(victim.getId());
                continue;
            }
            stun(mob, victim);
            victim.push(dir.x * 0.8D * KNOCKBACK_SCALE, 0.5D * KNOCKBACK_SCALE,
                    dir.z * 0.8D * KNOCKBACK_SCALE);
            direct++;
            hit = true;
        }
        healFromTargets(mob, direct, COMBO4_HEAL_PER_TARGET);
        if (hit) {
            IfritSounds.playImpactSound(mob);
        }
    }

    static void stun(Ifrit mob, LivingEntity victim) {
        victim.addEffect(new MobEffectInstance(GoetyEffects.STUNNED.get(), STUN_TICKS, 0,
                false, true), mob);
    }

    static boolean canHit(Ifrit mob, LivingEntity victim) {
        if (victim == mob || victim instanceof Ifrit || !victim.isAlive()) {
            return false;
        }
        if (victim instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return false;
        }
        return !victim.isAlliedTo(mob);
    }

    static boolean hitVictim(Ifrit mob, LivingEntity victim, float damage) {
        float amount = damage * (mob.isClone() ? 0.5F : 1.0F);
        if (isFrostEnemy(victim)) {
            amount *= 2.0F;
        }
        DamageSource source;
        if (mob.shieldBreakStrike) {
            WarlordShieldBreak.breakGuard(victim);
            source = WarlordShieldBreak.piercingSource(mob);
        } else {
            source = mob.damageSources().mobAttack(mob);
        }
        boolean hurt = victim.hurt(source, amount);
        if (hurt) {
            victim.setSecondsOnFire(5);
            victim.setLastHurtByMob(mob);
            if (mob.hellfireBonus > 0.0F) {
                applyHellfireBonus(mob, victim, mob.hellfireBonus + hellfirePhaseBonus(mob));
            }
        }
        return hurt;
    }

    private static void applyHellfireBonus(Ifrit mob, LivingEntity victim, float amount) {
        float scaled = amount * (mob.isClone() ? 0.5F : 1.0F);
        int invulnerable = victim.invulnerableTime;
        victim.invulnerableTime = 0;
        victim.hurt(ModDamageSource.hellfire(mob, mob), scaled);
        victim.invulnerableTime = invulnerable;
    }

    private static float hellfirePhaseBonus(Ifrit mob) {
        return mob.isPhaseTwo() ? HELLFIRE_PHASE_TWO_BONUS : 0.0F;
    }

    static int healFromTargets(Ifrit mob, int hits, float perTarget) {
        if (hits > 0) {
            float amount = perTarget * hits * (mob.isClone() ? 0.5F : 1.0F);
            if (mob.isClone()) {
                Ifrit owner = mob.resolveCloneOwner();
                if (owner != null && owner.isAlive()) {
                    owner.heal(amount);
                }
            } else {
                mob.heal(amount);
            }
        }
        return hits;
    }

    private static boolean isFrostEnemy(LivingEntity entity) {
        if (entity instanceof SnowGolem || entity instanceof Stray
                || entity instanceof AbstractFrostGolemServant) {
            return true;
        }
        String type = entity.getType().toString().toLowerCase();
        return type.contains("snow") || type.contains("frost") || type.contains("ice");
    }
}
