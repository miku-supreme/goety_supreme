package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.spell.DroughtBlazeFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class DroughtBlazeServant extends AbstractJerotesServant implements RangedAttackMob {

    private static final float RANGED_RANGE = 20.0F;
    private static final int RANGED_INTERVAL = 30;

    private static final double FIREBALL_SPEED = 1.0D;

    private static final int SPELL_LEVEL = 1;

    private static final float FIRE_ABSORPTION_PERCENTAGE = 60.0F;

    private static final int FIRE_ABSORPTION_COOLDOWN = 5;

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID =
            SynchedEntityData.defineId(DroughtBlazeServant.class, EntityDataSerializers.BYTE);

    private int fireAbsorptionCooldown;
    private float lastAbsorbedDamage;

    private float allowedHeightOffset = 0.5F;
    private int nextHeightOffsetChangeTick = 100;

    public DroughtBlazeServant(EntityType<? extends DroughtBlazeServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner()
                && ServantFeedHeal.tryBlazeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    public boolean isCharged() {
        return (entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setCharged(boolean charged) {
        byte flags = entityData.get(DATA_FLAGS_ID);
        entityData.set(DATA_FLAGS_ID, charged ? (byte) (flags | 1) : (byte) (flags & ~1));
    }

    @Override
    public boolean isOnFire() {
        return isCharged();
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Charged", isCharged());
        tag.putInt("FireAbsorptionCooldown", fireAbsorptionCooldown);
        tag.putFloat("LastAbsorbedDamage", lastAbsorbedDamage);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setCharged(tag.getBoolean("Charged"));
        fireAbsorptionCooldown = tag.getInt("FireAbsorptionCooldown");
        lastAbsorbedDamage = tag.getFloat("LastAbsorbedDamage");
    }

        @Override
    public PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.DROUGHT_BLAZE;
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/elemental/drought_blaze.png";
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("drought_blaze_ambient", SoundEvents.BLAZE_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("drought_blaze_hurt", SoundEvents.BLAZE_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("drought_blaze_death", SoundEvents.BLAZE_DEATH);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new DroughtBlazeAttackGoal(this));
    }

    private static final class DroughtBlazeAttackGoal extends Goal {
        private final DroughtBlazeServant blaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        DroughtBlazeAttackGoal(DroughtBlazeServant blaze) {
            this.blaze = blaze;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = blaze.getTarget();
            return target != null && target.isAlive() && blaze.canAttack(target);
        }

        @Override
        public void start() {
            this.attackStep = 0;
        }

        @Override
        public void stop() {
            this.lastSeen = 0;
            this.blaze.setCharged(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            --this.attackTime;
            LivingEntity target = blaze.getTarget();
            if (target == null) {
                return;
            }
            boolean seen = blaze.getSensing().hasLineOfSight(target);
            this.lastSeen = seen ? 0 : ++this.lastSeen;
            double distance = blaze.distanceTo(target);
            double followRange = blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
            if (blaze.distanceToSqr(target) < 4.0D) {
                if (!seen) {
                    return;
                }
                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    blaze.doHurtTarget(target);
                }
                blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(),
                        target.getZ(), 1.0D);
            } else if (distance * distance < followRange * followRange && seen) {
                if (this.attackTime <= 0) {
                    ++this.attackStep;
                    if (this.attackStep == 1) {
                        this.attackTime = 25;
                        blaze.setCharged(true);
                    } else if (this.attackStep <= 4) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 30;
                        this.attackStep = 0;
                        blaze.setCharged(false);
                    }
                    if (this.attackStep == 2) {
                        float factor = Mth.clamp(
                                (float) Math.sqrt(target.distanceToSqr(blaze)) / 20.0F, 0.1F, 1.0F);
                        blaze.performRangedAttack(target, factor);
                    }
                }
                blaze.getLookControl().setLookAt(target, 10.0F, 10.0F);
            } else if (this.lastSeen < 5) {
                blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(),
                        target.getZ(), 1.0D);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        castFeedback("magic_drought_fireball", SoundEvents.BLAZE_SHOOT,
                "drought_fireball_display", net.minecraft.core.particles.ParticleTypes.FLAME);
        spawnFireball(target);
        startSpellBurst(burstCastingTicks(SPELL_LEVEL), target, this::spawnFireball);
    }

    private void spawnFireball(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        Vec3 dir = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D)
                .subtract(getX(), getY(0.9D), getZ()).normalize();
        double spread = (14.0D - level().getDifficulty().getId() * 4.0D) / 14.0D;
        Vec3 jitter = new Vec3(
                (random.nextDouble() - 0.5D) * spread * 0.2D,
                (random.nextDouble() - 0.5D) * spread * 0.2D,
                (random.nextDouble() - 0.5D) * spread * 0.2D);
        DroughtFireballServantEntity fireball = new DroughtFireballServantEntity(
                level(), this, dir.add(jitter).normalize().scale(FIREBALL_SPEED),
                6 + (SPELL_LEVEL - 1) * 3);
        fireball.setJerotesAcceleration();
        fireball.setDamageBonus(getSummonPotency());
        level().addFreshEntity(fireball);
        playSound(jerotesSound("drought_blaze_shoot", SoundEvents.BLAZE_SHOOT), 1.0F,
                1.0F / (random.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void customServerAiStep() {
        if (--nextHeightOffsetChangeTick <= 0) {
            nextHeightOffsetChangeTick = 100;
            allowedHeightOffset = (float) random.triangle(0.5D, 6.891D);
        }
        LivingEntity target = getTarget();
        if (target != null && target.getEyeY() > getEyeY() + allowedHeightOffset && canAttack(target)) {
            Vec3 motion = getDeltaMovement();
            setDeltaMovement(motion.x(), motion.y() + (0.3D - motion.y()) * 0.3D, motion.z());
            this.hasImpulse = true;
        }
        super.customServerAiStep();
        tickSpellBurst();
    }

    @Override
    public void aiStep() {
        if (!onGround() && getDeltaMovement().y() < 0.0D) {
            Vec3 motion = getDeltaMovement();
            setDeltaMovement(motion.x(), motion.y() * 0.6D, motion.z());
        }
        super.aiStep();
        if (level().isClientSide()) {
            if (random.nextInt(24) == 0 && !isSilent()) {
                level().playLocalSound(getX() + 0.5D, getY() + 0.5D, getZ() + 0.5D,
                        SoundEvents.FIRE_AMBIENT, getSoundSource(),
                        1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
            for (int i = 0; i < 2; i++) {
                level().addParticle(ParticleTypes.LARGE_SMOKE,
                        getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        if (fireAbsorptionCooldown > 0) {
            --fireAbsorptionCooldown;
        }
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0.0F && isFireDamage(source)) {
            float absorbed = amount * (FIRE_ABSORPTION_PERCENTAGE / 100.0F);
            if (!level().isClientSide() && absorbed > 0.0F
                    && (fireAbsorptionCooldown <= 0 || lastAbsorbedDamage < absorbed)) {
                heal(absorbed);
                fireAbsorptionCooldown = FIRE_ABSORPTION_COOLDOWN;
                lastAbsorbedDamage = absorbed;
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                            getRandomX(1.0D), getY() + getBbHeight() * 0.5D, getRandomZ(1.0D),
                            2, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                playSound(AbstractJerotesServant.jerotesCoreSound("magic_fire_absorption",
                        SoundEvents.FIRECHARGE_USE), 1.0F, 1.0F);
            }
        }
        return super.hurt(source, amount);
    }

    private static boolean isFireDamage(DamageSource source) {
        return source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.ON_FIRE);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return isFireDamage(source) || source.is(DamageTypeTags.IS_FALL)
                || super.isInvulnerableTo(source);
    }

    @Override
    public int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }
}
