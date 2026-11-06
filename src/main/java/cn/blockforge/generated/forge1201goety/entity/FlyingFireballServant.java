package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballFireLayer;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;

public class FlyingFireballServant extends AbstractJerotesServant {

    public FlyingFireballServant(EntityType<? extends FlyingFireballServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FLYING_SPEED, 1.25D)
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/flying_fireball.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.FLYING_FIREBALL;
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        return navigation;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    public void equipDefaultGear(net.minecraft.util.RandomSource random) {
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ServantWanderGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 1.0D, 0.00001F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            if (!this.level().isClientSide) {
                this.playSound(SoundEvents.FIRE_AMBIENT, 1.0F, 1.0F);
            }
            target.setSecondsOnFire(14);
        }
        return hit;
    }

    private static final float FIRE_ABSORPTION_PERCENTAGE = 60.0F;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.FLAME,
                    this.getRandomX(0.5D), this.getY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.FLAME,
                    this.getX(), this.getEyeY(), this.getZ(), 0.0D, 0.04D, 0.0D);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castFireAbsorption(source, amount, FIRE_ABSORPTION_PERCENTAGE);
        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FALL)
                || source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public float getVoicePitch() {
        return 1.2F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.FIRE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FIRE_EXTINGUISH;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.FIRE_EXTINGUISH;
    }
}
