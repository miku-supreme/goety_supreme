package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** 绿豆腐化激光，从 AbstractGreenSmith 抽出的施法/光束/锁定逻辑。 */
public final class GreenSmithLaserSkill {

    private GreenSmithLaserSkill() {
    }

    public static void beginLaserFire(AbstractGreenSmith smith) {
        smith.castEffect = AbstractGreenSmith.EFFECT_LASER_FIRE;
        smith.setCastAnimation(AbstractGreenSmith.CAST_FORWARD, AbstractGreenSmith.CAST_LENGTHS[AbstractGreenSmith.CAST_FORWARD]);
        smith.castImpact = AbstractGreenSmith.LASER_FIRE_DELAY;
        smith.castApplied = false;
        if (smith.level() instanceof ServerLevel server) {
            Vec3 eye = smith.getEyePosition();
            Vec3 look = smith.getLookAngle();
            server.sendParticles(ParticleTypes.WITCH, eye.x + look.x, eye.y + look.y, eye.z + look.z,
                    30, 0.5D, 0.5D, 0.5D, 0.04D);
            smith.playGoetySound("corrupt_beam_start", 1.4F, 0.8F);
        }
    }

    public static void castCorruptLaser(AbstractGreenSmith smith) {
        if (smith.isClone()) {
            if (smith.level() instanceof ServerLevel server) {
                smith.cloneCorruptLaser(server);
            }
            return;
        }
        LivingEntity target = smith.getTarget();
        smith.laserTarget = target != null ? target.getUUID() : null;
        if (target != null && target.isAlive()) {
            float yawOffset = (smith.getRandom().nextBoolean() ? 1.0F : -1.0F)
                    * (AbstractGreenSmith.LASER_START_YAW_MIN
                            + smith.getRandom().nextFloat() * (AbstractGreenSmith.LASER_START_YAW_MAX - AbstractGreenSmith.LASER_START_YAW_MIN));
            float pitchOffset = (smith.getRandom().nextFloat() * 2.0F - 1.0F) * AbstractGreenSmith.LASER_START_PITCH_OFFSET;
            smith.aimBeside(target, yawOffset, pitchOffset);
        }
        smith.laserTicks = Math.max(1, smith.castLength - smith.castElapsed);
        if (smith.level() instanceof ServerLevel server) {
            smith.playGoetySound("corrupt_beam_loop", 1.5F, 0.7F);
            server.sendParticles(ParticleTypes.WITCH, smith.getX(), smith.getEyeY(), smith.getZ(),
                    24, 0.6D, 0.6D, 0.6D, 0.02D);
            spawnCorruptedBeam(smith, server);
        }
    }

    public static void spawnCorruptedBeam(AbstractGreenSmith smith, ServerLevel server) {
        if (smith.laserBeam != null && smith.laserBeam.isAlive()) {
            return;
        }
        GreenSmithLaserBeam beam = new GreenSmithLaserBeam(ModEntityTypes.GREEN_SMITH_LASER.get(), server);
        Vec3 look = smith.getLookAngle();
        beam.setPos(smith.getX() + look.x / 2.0D, smith.getEyeY() - 0.2D, smith.getZ() + look.z / 2.0D);
        beam.setYRot(smith.getYRot());
        beam.setXRot(smith.getXRot());
        beam.setOwner(smith);
        beam.setItemBase(false);
        beam.setExtraDamage(smith.soulJarDamageBonus());
        server.addFreshEntity(beam);
        smith.laserBeam = beam;
    }

    public static void tickLaser(AbstractGreenSmith smith) {
        if (smith.laserTicks <= 0) {
            return;
        }
        --smith.laserTicks;
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (smith.isClone()) {
            if (smith.laserBeam != null && !smith.laserBeam.isAlive()) {
                smith.laserBeam = null;
            }
            if (smith.laserTicks <= 0) {
                clearCorruptedBeam(smith);
                smith.dismissClone();
            }
            return;
        }
        LivingEntity locked = smith.laserTarget != null && server.getEntity(smith.laserTarget) instanceof LivingEntity living
                ? living : null;
        if (locked != null && locked.isAlive()) {
            smith.aimAt(locked, AbstractGreenSmith.LASER_TRACK_TURN_DEGREES);
        }
        if (smith.laserBeam != null && !smith.laserBeam.isAlive()) {
            smith.laserBeam = null;
        }
        if (smith.laserTicks <= 0) {
            smith.laserTarget = null;
            clearCorruptedBeam(smith);
        }
    }

    public static void clearCorruptedBeam(AbstractGreenSmith smith) {
        if (smith.laserBeam != null) {
            smith.laserBeam.discard();
            smith.laserBeam = null;
        }
    }

}
