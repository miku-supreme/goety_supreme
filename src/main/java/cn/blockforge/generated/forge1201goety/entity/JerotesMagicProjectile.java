package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import cn.blockforge.generated.goetysupreme.entity.JerotesMagicProjectile;

public abstract class JerotesMagicProjectile extends AbstractHurtingProjectile {

    private int life;

    public JerotesMagicProjectile(EntityType<? extends JerotesMagicProjectile> type, Level level) {
        super(type, level);
    }

    public JerotesMagicProjectile(EntityType<? extends JerotesMagicProjectile> type, LivingEntity owner, Level level) {
        super(type, owner, 0.0D, 0.0D, 0.0D, level);
    }

    public void setPower(Vec3 direction) {
        this.setPower(direction.x, direction.y, direction.z);
    }

    public void setPower(double dx, double dy, double dz) {
        double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (length != 0.0D) {
            this.xPower = dx / length * 0.1D;
            this.yPower = dy / length * 0.1D;
            this.zPower = dz / length * 0.1D;
        }
    }

    public int getLife() {
        return this.life;
    }

    public abstract int getMaxLife();

    public void lastBreak() {
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (!this.level().isClientSide() && (entity != null && entity.isRemoved() || !this.level().hasChunkAt(this.blockPosition()))) {
            this.discard();
            return;
        }
        this.baseTick();
        if (this.shouldBurn()) {
            this.setSecondsOnFire(1);
        }
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
            this.onHit(hitResult);
        }
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x;
        double d2 = this.getY() + vec3.y;
        double d3 = this.getZ() + vec3.z;
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);
        float inertia;
        if (this.isInWater()) {
            if (this.shouldBubble()) {
                for (int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.BUBBLE,
                            d - vec3.x * 0.25D, d2 - vec3.y * 0.25D, d3 - vec3.z * 0.25D,
                            vec3.x, vec3.y, vec3.z);
                }
            }
            inertia = this.getLiquidInertia();
        } else {
            inertia = this.getInertia();
        }
        this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double) inertia));
        if (this.shouldTail()) {
            ParticleOptions trailParticle = this.getTrailParticle();
            if (trailParticle != null) {
                this.level().addParticle(trailParticle, d, d2 + (double) (this.getBbHeight() / 2.0F), d3, 0.0D, 0.0D, 0.0D);
            }
        }
        this.setPos(d, d2, d3);
        if (this.life >= this.getMaxLife()) {
            this.lastBreak();
            this.discard();
        } else {
            ++this.life;
        }
    }

    @Override
    public boolean shouldBurn() {
        return false;
    }

    public boolean shouldBubble() {
        return true;
    }

    public boolean shouldTail() {
        return true;
    }

    @Override
    public float getInertia() {
        return 1.0F;
    }

    public float getLiquidInertia() {
        return 1.0F;
    }
}
