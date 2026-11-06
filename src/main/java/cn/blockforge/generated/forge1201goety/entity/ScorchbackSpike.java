package cn.blockforge.generated.goetysupreme.entity;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackSpike;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackSpikeRenderer;

public class ScorchbackSpike extends Entity {

    private static final int DEFAULT_LIFE = 400;

    private static final int DAMAGE_INTERVAL = 20;
    private static final float DAMAGE = 8.0F;

    private int life = DEFAULT_LIFE;
    private int damageTimer;
    @Nullable
    private UUID ownerId;

    public ScorchbackSpike(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public void setOwner(Scorchback owner) {
        this.ownerId = owner.getUUID();
    }

    @Override
    public void defineSynchedData() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.contains("ScorchbackSpikeLife") ? tag.getInt("ScorchbackSpikeLife") : DEFAULT_LIFE;
        if (tag.hasUUID("ScorchbackSpikeOwner")) {
            this.ownerId = tag.getUUID("ScorchbackSpikeOwner");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("ScorchbackSpikeLife", this.life);
        if (this.ownerId != null) {
            tag.putUUID("ScorchbackSpikeOwner", this.ownerId);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            return;
        }
        if (--this.life <= 0) {
            this.discard();
            return;
        }
        if (++this.damageTimer < DAMAGE_INTERVAL) {
            return;
        }
        this.damageTimer = 0;
        Scorchback owner = this.resolveOwner();
        if (owner == null) {
            return;
        }
        AABB area = this.getBoundingBox().inflate(0.9D, 0.6D, 0.9D);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (!owner.canAttackTarget(victim)) {
                continue;
            }
            Vec3 before = victim.getDeltaMovement();
            victim.hurt(this.damageSources().cactus(), DAMAGE);
            victim.setDeltaMovement(before.x, before.y, before.z);
            victim.hurtMarked = true;
        }
    }

    @Nullable
    private Scorchback resolveOwner() {
        if (this.ownerId == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        Entity entity = server.getEntity(this.ownerId);
        return entity instanceof Scorchback scorchback && scorchback.isAlive() ? scorchback : null;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        return false;
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    public boolean canRide(Entity entity) {
        return false;
    }
}
