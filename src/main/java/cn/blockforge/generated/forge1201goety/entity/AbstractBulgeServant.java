package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.ai.AmphibiousGroundNavigation;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;

public abstract class AbstractBulgeServant extends AbstractJerotesServant implements RangedAttackMob {

    public static final int SHOOT_ANIM_TICKS = 20;

    private boolean wasCasting;

    public AbstractBulgeServant(EntityType<? extends AbstractBulgeServant> entityType, Level level) {
        super(entityType, level);
        setMaxUpStep(1.0F);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        AmphibiousGroundNavigation navigation = new AmphibiousGroundNavigation(this, level);
        navigation.setCanFloat(true);
        return navigation;
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.125F;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.noCollision(this);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
    }

    @Override
    public float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("bulge_ambient", SoundEvents.SQUID_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("bulge_hurt", SoundEvents.SQUID_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("bulge_death", SoundEvents.SQUID_DEATH);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != ForgeMod.WATER_TYPE.get() && super.canDrownInFluidType(type);
    }

    @Override
    public void travel(Vec3 movement) {
        if (isControlledByLocalInstance() && isInWater()) {
            moveRelative(getSpeed(), movement);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.8D));
        } else {
            super.travel(movement);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_DROWNING) || source.is(DamageTypeTags.IS_FALL)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        clearFire();
        Vec3 motion = getDeltaMovement();
        if (!onGround() && motion.y < 0.0D) {
            setDeltaMovement(motion.multiply(1.0D, 0.6D, 1.0D));
        }
        tickSpellBurst();
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public void equipDefaultGear(net.minecraft.util.RandomSource random) {
    }

    public void playShootAnim() {
        if (!level().isClientSide) {
            setCastTicks(currentAnimTicks());
        }
    }

    public int currentAnimTicks() {
        return SHOOT_ANIM_TICKS;
    }

    public abstract float rangedAttackRadius();

    public abstract int rangedAttackInterval();

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (level().isClientSide) {
            return;
        }
        performRangedAttackServer(target);
    }

    public void performRangedAttackServer(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        playShootAnim();
        fireBullet(target);
        startBulletBurst(target);
    }

    public abstract void fireBullet(LivingEntity target);

    public int spellLevel() {
        return 1;
    }

    public final void startBulletBurst(LivingEntity target) {
        startSpellBurst(burstCastingTicks(spellLevel()), target, this::fireBullet);
    }

    public static final class BulgeRangedAttackGoal extends RangedAttackGoal {
        private final AbstractBulgeServant servant;

        BulgeRangedAttackGoal(AbstractBulgeServant servant, double speed, int interval, float radius) {
            super(servant, speed, interval, radius);
            this.servant = servant;
        }

        @Override
        public void start() {
            super.start();
            servant.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            servant.setAggressive(false);
        }
    }

    public static final class BulgeFloatGoal extends Goal {
        private final AbstractBulgeServant servant;
        private final float speed;

        public BulgeFloatGoal(AbstractBulgeServant servant, float speed) {
            this.servant = servant;
            this.speed = speed;
            setFlags(EnumSet.of(Goal.Flag.JUMP));
            servant.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return servant.isInWaterOrFluid();
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (!canUse()) {
                return;
            }
            if (servant.getRandom().nextFloat() < 0.8F) {
                servant.getJumpControl().jump();
                servant.setDeltaMovement(servant.getDeltaMovement().add(0.0D, speed, 0.0D));
            }
            if (servant.getMobType() == MobType.WATER && servant.level().isClientSide) {
                for (int i = 0; i < 8; ++i) {
                    servant.level().addParticle(ParticleTypes.BUBBLE,
                            servant.getRandomX(0.5D), servant.getRandomY(), servant.getRandomZ(0.5D),
                            0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public boolean isInWaterOrFluid() {
        return isInFluidType();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (isAlive()) {
                idleAnimationState.startIfStopped(tickCount);
            }
            boolean casting = isCasting();
            if (casting && !wasCasting) {
                shootAnimationState.start(tickCount);
            }
            wasCasting = casting;
        }
    }

    public final net.minecraft.world.entity.AnimationState idleAnimationState =
            new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState shootAnimationState =
            new net.minecraft.world.entity.AnimationState();
}
