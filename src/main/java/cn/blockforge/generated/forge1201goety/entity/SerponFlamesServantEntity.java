package cn.blockforge.generated.goetysupreme.entity;

import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesServantEntity;

public class SerponFlamesServantEntity extends AbstractHurtingProjectile {

    private static final int MAX_LIFE = 20;

    private static final int USE_LIFE = 10;

    private static final double REACH = 9.0D;

    private static final double HALF_ANGLE_COS = Math.cos(Math.toRadians(22.5D / 2.0D));
    private static final int DICE_SIDES = 6;
    private static final float DICE_DIVISOR = 30.0F;

    private int life;

    private int spellLevelDamage = 3;

    private int spellLevelFireTime = 12;

    public SerponFlamesServantEntity(EntityType<? extends SerponFlamesServantEntity> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
        this.noPhysics = true;
        this.setDeltaMovement(Vec3.ZERO);
    }

    public SerponFlamesServantEntity(Level level, LivingEntity caster, int spellLevelDamage, int fireSeconds) {
        super(ModEntityTypes.SERPON_FLAMES_SERVANT.get(), caster, 0.0D, 0.0D, 0.0D, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = fireSeconds;
        Vec3 eye = caster.getEyePosition();
        Vec3 look = caster.getViewVector(1.0F).normalize();
        setPos(eye.x + look.x * 1.2D, eye.y - 0.2D + look.y * 1.2D, eye.z + look.z * 1.2D);
        setDeltaMovement(Vec3.ZERO);
        setYRot(caster.getYRot());
        setXRot(caster.getXRot());
        setOwner(caster);
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return false;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
    }

    @Override
    public ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public float getInertia() {
        return 1.0F;
    }

    @Override
    public void tick() {
        Entity owner = getOwner();
        if (!level().isClientSide() && (owner == null || !owner.isAlive())) {
            discard();
            return;
        }
        if (!level().isClientSide() && owner != null) {
            Vec3 eye = owner.getEyePosition();
            setPos(eye.x + getDeltaMovement().x, eye.y - 0.2D, eye.z + getDeltaMovement().z);
            setDeltaMovement(Vec3.ZERO);
        }
        ++this.life;
        if (this.life > MAX_LIFE) {
            discard();
            return;
        }
        if (this.life == 3 && !level().isClientSide() && level() instanceof ServerLevel server
                && getOwner() instanceof LivingEntity boltCaster) {
            JerotesParticleFx.sprayBoltFan(server, boltCaster, (float) REACH, 22.5F,
                    0xFF8114, 0xFFE2CB, 0xFF1C14);
        }
        if (this.life == USE_LIFE && !level().isClientSide()) {
            resolveCone();
        }
        super.tick();
    }

    private void resolveCone() {
        if (!(getOwner() instanceof LivingEntity caster) || !caster.isAlive()) {
            return;
        }
        Vec3 look = caster.getViewVector(1.0F).normalize();
        Vec3 origin = new Vec3(caster.getX(), caster.getY(1.2D), caster.getZ());
        List<LivingEntity> candidates = level().getEntitiesOfClass(LivingEntity.class,
                caster.getBoundingBox().inflate(REACH, 1.5D, REACH));
        for (LivingEntity hurt : candidates) {
            if (hurt == caster || !caster.canAttack(hurt) || !hurt.isAlive()) {
                continue;
            }
            Vec3 to = hurt.position().add(0.0D, hurt.getBbHeight() * 0.5D, 0.0D).subtract(origin);
            if (to.lengthSqr() > REACH * REACH) {
                continue;
            }
            if (to.normalize().dot(look) < HALF_ANGLE_COS) {
                continue;
            }
            if (!caster.hasLineOfSight(hurt)) {
                continue;
            }
            hurt.invulnerableTime = 0;
            float damage = rollDamage();
            if (!hurt.hurt(JerotesMagicDamage.bypassesCooldownFire(this, caster), damage)) {
                hurt.hurt(level().damageSources().indirectMagic(this, caster), damage);
            } else {
                hurt.setSecondsOnFire(spellLevelFireTime);
            }
        }
        spawnConeParticles(caster, look, origin);
    }

    private float rollDamage() {
        int count = Math.max(1, 6 + (spellLevelDamage - 3));
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += 1 + random.nextInt(DICE_SIDES);
        }
        return sum / DICE_DIVISOR;
    }

    private void spawnConeParticles(LivingEntity caster, Vec3 look, Vec3 origin) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        ParticleOptions serpon = AbstractJerotesServant.jerotesCoreParticle("serpon_flames", ParticleTypes.FLAME);
        Vec3 mouth = origin.add(look.scale(1.2D));
        serverLevel.sendParticles(serpon, mouth.x, mouth.y, mouth.z, 14, 0.28D, 0.22D, 0.28D, 0.04D);
        for (double dist = 1.5D; dist <= REACH; dist += 1.0D) {
            double spread = 0.15D + dist * 0.12D;
            Vec3 at = origin.add(look.scale(dist));
            serverLevel.sendParticles(serpon, at.x, at.y, at.z, 6, spread, spread, spread, 0.03D);
            serverLevel.sendParticles(ParticleTypes.FLAME, at.x, at.y, at.z,
                    4, spread, spread, spread, 0.05D);
        }
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, mouth.x, mouth.y, mouth.z,
                6, 0.25D, 0.2D, 0.25D, 0.02D);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SerponFlamesLife", this.life);
        tag.putInt("SpellLevelDamage", this.spellLevelDamage);
        tag.putInt("SpellLevelFireTime", this.spellLevelFireTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.life = tag.getInt("SerponFlamesLife");
        this.spellLevelDamage = tag.getInt("SpellLevelDamage");
        this.spellLevelFireTime = tag.getInt("SpellLevelFireTime");
    }
}
