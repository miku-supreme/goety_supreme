package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.util.EntityUtil;
import fuzs.mutantmonsters.world.entity.EndersoulFragment;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.EndersoulFragmentServant;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;

public final class EndersoulFragmentServant extends EndersoulFragment {
    private static final EntityDataAccessor<Optional<UUID>> SOURCE_OWNER =
            SynchedEntityData.defineId(EndersoulFragmentServant.class, EntityDataSerializers.OPTIONAL_UUID);

    private MutantEndermanServant source;
    private UUID sourceOwnerId;
    private int remainingTicks;

    private boolean collected;

    public EndersoulFragmentServant(EntityType<? extends EndersoulFragmentServant> type, Level level) {
        super(type, level);
        setTamed(true);
        remainingTicks = 20 + random.nextInt(20);
    }

    @Override
    public void defineSynchedData() {
        super.m_8097_();
        entityData.define(SOURCE_OWNER, Optional.empty());
    }

    public void bindTo(MutantEndermanServant servant) {
        source = servant;
        UUID owner = servant.getOwnerId();
        if (owner == null && servant.getTrueOwner() != null) {
            owner = servant.getTrueOwner().getUUID();
        }
        setSourceOwnerId(owner);
    }

    private void setSourceOwnerId(UUID owner) {
        sourceOwnerId = owner;
        entityData.set(SOURCE_OWNER, Optional.ofNullable(owner));
    }

    private UUID getSourceOwnerId() {
        if (sourceOwnerId != null) {
            return sourceOwnerId;
        }
        if (entityData.hasItem(SOURCE_OWNER)) {
            sourceOwnerId = entityData.get(SOURCE_OWNER).orElse(null);
        }
        return sourceOwnerId;
    }

    private boolean isSourceOwner(Player player) {
        return player != null && !player.isSpectator() && getSourceOwnerId() != null
                && getSourceOwnerId().equals(player.getUUID());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.m_7380_(tag);
        UUID owner = getSourceOwnerId();
        if (owner != null) {
            tag.putUUID("SourceOwner", owner);
        }
        tag.putBoolean("Collected", collected);
        tag.putInt("RemainingTicks", Math.max(1, remainingTicks));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.hasUUID("SourceOwner")) {
            setSourceOwnerId(tag.getUUID("SourceOwner"));
        }
        collected = tag.getBoolean("Collected");
        setTamed(true);
        int savedTicks = tag.getInt("RemainingTicks");
        if (savedTicks > 0) {
            remainingTicks = savedTicks;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && !collected && getOwner() == null && --remainingTicks <= 0) {
            burst();
        }
    }

    private void burst() {
        if (isRemoved() || collected || level().isClientSide()) {
            return;
        }
        EntityUtil.sendParticlePacket(this, MutantEndermanServant.soulParticle(), 64);
        playSound(SoundEvents.GENERIC_EXPLODE, 0.5F, 1.8F);
        if (source != null) {
            for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(5.0D), entity -> entity != source && entity.isAlive()
                            && !entity.isSpectator() && !source.isAlliedTo(entity)
                            && !(entity instanceof Player player && player.getAbilities().invulnerable))) {
                source.hurtWithVoidEffect(victim, ModDamageSource.summonAttack(source, source.getTrueOwner()), 1.0F);
                Vec3 outward = victim.position().subtract(position()).normalize().scale(0.6D);
                victim.setDeltaMovement(outward.x, 0.2D, outward.z);
                victim.hurtMarked = true;
                EntityUtil.sendPlayerVelocityPacket(victim);
            }
        }
        discard();
    }

    @Override
    public boolean hurt(DamageSource damage, float amount) {
        if (isInvulnerableTo(damage) || amount <= 0.0F
                || source != null && damage.getEntity() != null && source.isAlliedTo(damage.getEntity())) {
            return false;
        }
        burst();
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!isSourceOwner(player)) {
            return InteractionResult.PASS;
        }
        InteractionResult result = super.interact(player, hand);
        if (!level().isClientSide() && result.consumesAction()) {
            collected = true;
            setTamed(true);
        }
        return result;
    }
}
