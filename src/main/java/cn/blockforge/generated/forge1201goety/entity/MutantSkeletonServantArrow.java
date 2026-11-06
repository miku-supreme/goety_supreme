package cn.blockforge.generated.goetysupreme.entity;

import fuzs.mutantmonsters.world.entity.projectile.MutantArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cn.blockforge.generated.goetysupreme.config.MutantSkeletonServantConfig;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;

public final class MutantSkeletonServantArrow extends MutantArrow {
    public static final String SOUL_CHARGED_TAG = "GoetySupremeSoulCharged";
    public static final String IGNORE_INVULNERABILITY_TAG = "GoetySupremeIgnoreInvulnerability";
    public static final String GILDED_APPEARANCE_TAG = "GoetySupremeGildedAppearance";
    private static final String BONE_DAMAGE_TAG = "GoetySupremeBoneDamage";
    private static final EntityDataAccessor<Boolean> GILDED_APPEARANCE =
            SynchedEntityData.defineId(MutantSkeletonServantArrow.class, EntityDataSerializers.BOOLEAN);

    public MutantSkeletonServantArrow(EntityType<? extends MutantSkeletonServantArrow> type, Level level) {
        super(type, level);
    }

    public MutantSkeletonServantArrow(Level level, LivingEntity owner, boolean soulCharged,
            boolean ignoresInvulnerability, int boneUpgrades) {
        this(ModEntityTypes.MUTANT_SKELETON_ARROW.get(), level);
        setPos(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
        setOwner(owner);
        setGilded(snapshotGildedAppearance(owner));
        setNoGravity(true);
        CompoundTag data = getPersistentData();
        data.putBoolean(SOUL_CHARGED_TAG, soulCharged);
        data.putBoolean(IGNORE_INVULNERABILITY_TAG, ignoresInvulnerability);
        data.putDouble(BONE_DAMAGE_TAG, 0.0D);
    }

    private static boolean snapshotGildedAppearance(LivingEntity owner) {
        return owner instanceof MutantSkeletonServant servant && servant.hasHornedAppearance();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(GILDED_APPEARANCE, false);
    }

    public boolean isGilded() {
        return entityData.get(GILDED_APPEARANCE);
    }

    private void setGilded(boolean gilded) {
        entityData.set(GILDED_APPEARANCE, gilded);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(GILDED_APPEARANCE_TAG, isGilded());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setGilded(tag.getBoolean(GILDED_APPEARANCE_TAG));
    }

    public static void addBoneDamage(LivingHurtEvent event) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!isRemoved() && getPersistentData().getBoolean(SOUL_CHARGED_TAG)
                && level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, getX(), getY(), getZ(),
                    1, 0.02D, 0.02D, 0.02D, 0.0D);
        }
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof MutantSkeletonServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide() && getPersistentData().getBoolean(IGNORE_INVULNERABILITY_TAG)
                && result.getEntity() instanceof LivingEntity target) {
            target.invulnerableTime = 0;
        }
        super.onHitEntity(result);
    }

    @Override
    public void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (!level().isClientSide() && target.isAlive()
                && getPersistentData().getBoolean(SOUL_CHARGED_TAG)) {
            int invulnerable = target.invulnerableTime;
            target.invulnerableTime = 0;
            target.hurt(level().damageSources().indirectMagic(this, getOwner()),
                    MutantSkeletonServantConfig.SOUL_JAR_ARROW_DAMAGE.get().floatValue());
            target.invulnerableTime = Math.max(invulnerable, target.invulnerableTime);
        }
    }
}
