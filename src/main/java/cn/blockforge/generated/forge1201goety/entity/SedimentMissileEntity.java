package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;

public class SedimentMissileEntity extends ThrowableItemProjectile {

    private static final float DAMAGE_BASE = 2.0F;
    private static final int DAMAGE_DICE_SIDES = 4;

    private static final int MAX_LIFE_TICKS = 60;

    private float damageBonus;

    private static final int TRAIL_MAX_POINTS = 6;

    private static final int TRAIL_MAX_LIFE = 6;

    private static final double TRAIL_Y_OFFSET = 0.1875D;

    private static final double HOMING_OLD_SPEED_SCALE = 0.5D;
    private static final double HOMING_ACCEL = 0.4D;

    private LivingEntity lockedTarget;
    private boolean homingLost;
    private double closestDistSqr = Double.MAX_VALUE;

    private final List<TrailPoint> trailPoints = new ArrayList<>();

    public List<TrailPoint> getTrailPoints() {
        return this.trailPoints;
    }

    public static final class TrailPoint {
        public final Vec3 prevPosition;
        public final Vec3 position;
        public final int createdAt;

        public TrailPoint(Vec3 prevPosition, Vec3 position, int createdAt) {
            this.prevPosition = prevPosition;
            this.position = position;
            this.createdAt = createdAt;
        }

        public Vec3 getPosition(float partialTicks) {
            return this.prevPosition.lerp(this.position, partialTicks);
        }
    }

    public SedimentMissileEntity(EntityType<? extends SedimentMissileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SedimentMissileEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.SEDIMENT_MISSILE.get(), shooter, level);
    }

    public void setLockedTarget(LivingEntity target) {
        this.lockedTarget = target;
    }

    public void setDamageBonus(float bonus) {
        this.damageBonus = Math.max(0.0F, bonus);
    }

    @Override
    public Item getDefaultItem() {
        ItemStack stack = AbstractSedimentServant.jerotesItem("sediment_missile");
        return stack.isEmpty() ? Items.AIR : stack.getItem();
    }

    @Override
    public float getGravity() {
        return 0.0F;
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (lockedTarget != null && target != lockedTarget) {
            return false;
        }
        return !(getOwner() instanceof AbstractSedimentServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            Vec3 prev = new Vec3(this.xOld, this.yOld, this.zOld).add(0.0D, TRAIL_Y_OFFSET, 0.0D);
            Vec3 current = this.position().add(0.0D, TRAIL_Y_OFFSET, 0.0D);
            this.trailPoints.add(new TrailPoint(prev, current, this.tickCount));
            this.trailPoints.removeIf(point -> this.tickCount - point.createdAt > TRAIL_MAX_LIFE);
            while (this.trailPoints.size() > TRAIL_MAX_POINTS) {
                this.trailPoints.remove(0);
            }
            return;
        }
        if (tickCount > MAX_LIFE_TICKS) {
            discard();
            return;
        }
        Entity owner = getOwner();
        LivingEntity locked = this.lockedTarget;
        boolean canHome = !this.homingLost && locked != null && locked.isAlive() && owner instanceof LivingEntity caster
                && !caster.hasEffect(MobEffects.BLINDNESS)
                && !(locked instanceof Player && tickCount >= MAX_LIFE_TICKS / 4);
        if (canHome) {
            double distSqr = this.distanceToSqr(locked);
            double threshold = locked.getBbWidth();
            if (this.closestDistSqr < Double.MAX_VALUE && distSqr > this.closestDistSqr
                    && this.closestDistSqr < threshold * threshold) {
                this.homingLost = true;
            }
            if (!this.homingLost) {
                this.closestDistSqr = Math.min(this.closestDistSqr, distSqr);
                Vec3 dir = locked.getEyePosition().subtract(getEyePosition()).normalize();
                setYRot((float) (Mth.atan2(dir.z, dir.x) * 180.0D / Math.PI) - 90.0F);
                setXRot((float) (-Mth.atan2(dir.y, Math.sqrt(dir.x * dir.x + dir.z * dir.z)) * 180.0D / Math.PI));
                setDeltaMovement(getDeltaMovement().scale(HOMING_OLD_SPEED_SCALE)
                        .add(dir.scale(HOMING_ACCEL)));
            }
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide) {
            return;
        }
        Entity owner = getOwner();
        if (result.getEntity() instanceof LivingEntity target) {
            if (owner == target || (owner != null && owner.isAlliedTo(target))) {
                return;
            }
            target.invulnerableTime = 0;
            target.hurt(JerotesMagicDamage.necrotic(this, owner),
                    DAMAGE_BASE + random.nextInt(DAMAGE_DICE_SIDES) + this.damageBonus);
            if (!isSilent()) {
                level().playSound(null, getX(), getY(), getZ(),
                        AbstractJerotesServant.jerotesCoreSound("spell", SoundEvents.WITCH_THROW),
                        SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
        level().broadcastEntityEvent(this, (byte) 3);
        if (level() instanceof ServerLevel server) {
            JerotesParticleFx.hitBall(server, getX(), getY(), getZ(), 0x005F98, 0x89C7CC);
        }
        discard();
    }

    @Override
    public void onHitBlock(net.minecraft.world.phys.BlockHitResult result) {
        super.onHitBlock(result);
        if (level().isClientSide) {
            return;
        }
        level().broadcastEntityEvent(this, (byte) 3);
        if (lockedTarget == null) {
            discard();
        }
    }
}
