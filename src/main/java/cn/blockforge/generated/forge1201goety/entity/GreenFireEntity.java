package cn.blockforge.generated.goetysupreme.entity;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import com.Polarice3.Goety.client.particles.MagicSmokeParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.renderer.GreenFireRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.ServantRobeFireDamage;

public class GreenFireEntity extends Entity {

    private static final int LIFETIME = 200;

    private static final int DAMAGE_INTERVAL = 20;

    private static final float DAMAGE = 1.5F;

    private static final int SMOKE_FROM = 0x8CFF4D;
    private static final int SMOKE_TO = 0x1B5E20;

    private int life;

    @Nullable
    private UUID ownerUuid;

    public GreenFireEntity(EntityType<? extends GreenFireEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.ownerUuid = owner == null ? null : owner.getUUID();
    }

    @Nullable
    private LivingEntity resolveOwner() {
        if (this.ownerUuid == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        return server.getEntity(this.ownerUuid) instanceof LivingEntity living ? living : null;
    }

    @Override
    public void defineSynchedData() {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Life", this.life);
        if (this.ownerUuid != null) {
            tag.putUUID("OwnerUuid", this.ownerUuid);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("Life");
        this.ownerUuid = tag.hasUUID("OwnerUuid") ? tag.getUUID("OwnerUuid") : null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.spawnParticles();
            return;
        }
        if (++this.life > LIFETIME) {
            this.discard();
            return;
        }
        if (this.life % DAMAGE_INTERVAL == 0) {
            this.burn();
        }
    }

    private void spawnParticles() {
        ParticleOptions particle = new MagicSmokeParticleOption(
                SMOKE_FROM, SMOKE_TO, 10 + this.random.nextInt(10), 0.2F, 0.0F);
        for (int i = 0; i < 2; i++) {
            double x = this.getX() + (this.random.nextDouble() - 0.5D) * 0.7D;
            double y = this.getY() + this.random.nextDouble() * 1.4D;
            double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 0.7D;
            this.level().addParticle(particle, x, y, z, 0.0D, 0.01D, 0.0D);
        }
    }

    public float getAnimationProgress(float partialTick) {
        float age = this.tickCount + partialTick;
        float grow = Mth.clamp(age / 4.0F, 0.0F, 1.0F);
        float fade = Mth.clamp((LIFETIME - age) / 10.0F, 0.0F, 1.0F);
        return Math.min(grow, fade);
    }

    private void burn() {
        AABB area = new AABB(this.getX() - 0.7D, this.getY() - 0.2D, this.getZ() - 0.7D,
                this.getX() + 0.7D, this.getY() + 1.6D, this.getZ() + 0.7D);
        List<LivingEntity> victims = this.level().getEntitiesOfClass(LivingEntity.class, area);
        LivingEntity owner = this.resolveOwner();
        for (LivingEntity victim : victims) {
            if (victim.getMobType() == MobType.UNDEAD) {
                continue;
            }
            if (owner != null && (victim == owner
                    || owner.isAlliedTo(victim) || victim.isAlliedTo(owner))) {
                continue;
            }
            DamageSource fire = ServantRobeFireDamage.upgrade(owner, this.damageSources().inFire());
            if (fire.is(DamageTypeTags.IS_FIRE)
                    && (victim.hasEffect(MobEffects.FIRE_RESISTANCE) || victim.fireImmune())) {
                continue;
            }
            victim.hurt(fire, DAMAGE);
            victim.setSecondsOnFire(2);
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(
                    GreenSmithFx.necroFire(),
                    this.getX(), this.getY() + 0.3D, this.getZ(), 6, 0.3D, 0.2D, 0.3D, 0.01D);
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4096.0D;
    }
}
