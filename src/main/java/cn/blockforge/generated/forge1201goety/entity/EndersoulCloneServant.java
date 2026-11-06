package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.EndersoulCloneServant;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;

public final class EndersoulCloneServant extends Summoned {
    private MutantEndermanServant cloner;

    private int revealedHits;

    public EndersoulCloneServant(EntityType<? extends EndersoulCloneServant> type, Level level) {
        super(type, level);
        setHostile(false);
        xpReward = 0;
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 96.0D);
    }

    @Override
    public void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
    }

    public boolean belongsTo(MutantEndermanServant servant) {
        return cloner == servant;
    }

    public void bindTo(MutantEndermanServant servant) {
        cloner = servant;
        if (servant.getTrueOwner() != null) {
            setTrueOwner(servant.getTrueOwner());
        } else {
            setOwnerId(servant.getOwnerId());
        }
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(servant.getMaxHealth());
        setHealth(servant.getHealth());
        setTarget(servant.getTarget());
        setPersistenceRequired();
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        return entity == this || entity == cloner
                || entity instanceof EndersoulCloneServant other && cloner != null && other.cloner == cloner
                || (cloner != null && cloner.isAlliedTo(entity))
                || super.isAlliedTo(entity);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        return entity != null && !isAlliedTo(entity) && super.canAttack(entity);
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target != null && canAttack(target) ? target : null);
    }

    @Override
    public void tick() {
        if (!level().isClientSide()) {
            if (cloner == null || !cloner.isAlive() || cloner.isNoAi() || cloner.level() != level()
                    || !cloner.isClone() || tickCount >= MutantEndermanServant.CLONE_ANIMATION.duration()) {
                dismiss();
                return;
            }
            setTarget(cloner.getTarget());
            setAggressive(getTarget() != null);
        }
        super.tick();
        if (!level().isClientSide() && getTarget() != null && tickCount % 10 == 0
                && (isInWaterOrRain() || !hasLineOfSight(getTarget()) || distanceToSqr(getTarget()) > 1024.0D)) {
            teleportAround(getTarget());
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!(entity instanceof LivingEntity target) || !canAttack(target)) {
            return false;
        }
        boolean hit = target.hurt(ModDamageSource.summonAttack(this, getTrueOwner()),
                (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (hit) {
            float bodyHeal = cloner != null && cloner.isFullyUpgraded() ? 3.0F : 2.0F;
            heal(2.0F);
            if (cloner != null && cloner.isAlive()) {
                cloner.heal(bodyHeal);
            }
            if (cloner != null) cloner.applyVoidTouched(target);
        }
        swing(InteractionHand.MAIN_HAND);
        if (!level().isClientSide() && random.nextInt(3) != 0) {
            teleportAround(target);
        }
        return hit;
    }

    private void teleportAround(LivingEntity target) {
        BlockPos landing = MutantEndermanServant.findLanding(this, target, 2, 12,
                target.blockPosition().getY(), 6);
        if (landing == null) {
            return;
        }
        Vec3 old = position();
        EntityUtil.sendParticlePacket(this, MutantEndermanServant.soulParticle(), 64);
        teleportTo(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D);
        level().playSound(null, old.x, old.y, old.z, SoundEvents.ENDERMAN_TELEPORT,
                getSoundSource(), 1.0F, 1.0F);
        playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        EntityUtil.sendParticlePacket(this, MutantEndermanServant.soulParticle(), 64);
        fallDistance = 0.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (level().isClientSide() || tickCount <= 10 || amount <= 0.0F || isInvulnerableTo(source)
                || (source.getEntity() != null && isAlliedTo(source.getEntity()))) {
            return false;
        }
        boolean hit = super.hurt(source, amount);
        if (hit) {
            int requiredHits = cloner != null && cloner.isFullyUpgraded() ? 2 : 1;
            if (++revealedHits >= requiredHits) {
                dismiss();
            }
        }
        return hit;
    }

    public void dismiss() {
        if (!isRemoved() && !level().isClientSide()) {
            EntityUtil.sendParticlePacket(this, MutantEndermanServant.soulParticle(), 256);
            discard();
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        dismiss();
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.55F;
    }

    @Override
    public boolean canUpdateMove() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean shouldDropLoot() {
        return false;
    }
}
