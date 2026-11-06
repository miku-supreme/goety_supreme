package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.entity.EnderStowawayServant;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;

public class EnderStowawayServant extends AbstractJerotesServant {

    private static final String ELITE_NAME = "Elite";

    private static final float CHORUS_FRUIT_HEAL = 5.0F;

    private static final int DODGE_TELEPORT_COOLDOWN = 20;
    private static final int APPROACH_TELEPORT_COOLDOWN = 60;
    private static final double APPROACH_DISTANCE_SQR = 144.0D;

    private static final int HURT_CONVERGE_TICKS = 120;

    private static final EntityDataAccessor<Boolean> DATA_ELITE =
            SynchedEntityData.defineId(EnderStowawayServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> CREEPY =
            SynchedEntityData.defineId(EnderStowawayServant.class, EntityDataSerializers.BOOLEAN);

    private int lastStareSound = -400;

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID =
            UUID.fromString("020e0dfb-87ae-4653-9556-831010e291a0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(
            SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15D,
            AttributeModifier.Operation.ADDITION);

    private int hurtsTick;
    private int teleportCooldown;

    public EnderStowawayServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
        setMaxUpStep(1.0F);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/ender_stowaway.png";
    }

    public String eliteTexturePath() {
        return "textures/entity/ender_stowaway_elite.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.ENDER_STOWAWAY;
    }

    @Override
    public boolean opensDoors() {
        return false;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new TeleportTowardsTargetGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return stowawayAttackReachSqr(EnderStowawayServant.this, target);
            }
        });
    }

    private static double stowawayAttackReachSqr(EnderStowawayServant servant, LivingEntity target) {
        double reach = (servant.getBbWidth() + target.getBbWidth()) / 2.0D
                + (Math.sqrt(2.04D) - 0.6D) + 0.25D;
        return reach * reach;
    }

    @Override
    public BodyRotationControl createBodyControl() {
        return new GiantBodyRotationControl(this);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ELITE, false);
        entityData.define(CREEPY, false);
    }

    public boolean isElite() {
        return entityData.get(DATA_ELITE);
    }

    private void setElite(boolean elite) {
        entityData.set(DATA_ELITE, elite);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        setElite(name != null && ELITE_NAME.equals(ChatFormatting.stripFormatting(name.getString())));
    }

    @Override
    public void setTarget(LivingEntity target) {
        LivingEntity valid = target != null && canAttack(target) ? target : null;
        super.setTarget(valid);
        setCreepy(valid != null);
        updateAttackingSpeedBoost(valid != null);
    }

    private void updateAttackingSpeedBoost(boolean attacking) {
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }
        if (attacking) {
            if (!speed.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                speed.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        } else {
            speed.removeModifier(SPEED_MODIFIER_ATTACKING);
        }
    }

    public boolean isCreepy() {
        return entityData.get(CREEPY);
    }

    private void setCreepy(boolean creepy) {
        if (isCreepy() != creepy) {
            entityData.set(CREEPY, creepy);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (CREEPY.equals(accessor) && isCreepy() && level().isClientSide()) {
            playStareSound();
        }
    }

    private void playStareSound() {
        if (tickCount >= lastStareSound + 400) {
            lastStareSound = tickCount;
            if (!isSilent()) {
                level().playLocalSound(getX(), getEyeY(), getZ(), SoundEvents.ENDERMAN_STARE,
                        getSoundSource(), 2.5F, 1.0F, false);
            }
        }
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.55F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENDERMAN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (level().isClientSide()) {
            return super.hurt(source, amount);
        }
        if (source.getDirectEntity() instanceof ThrownPotion potion) {
            var effects = PotionUtils.getMobEffects(potion.getItem());
            if (PotionUtils.getPotion(potion.getItem()) == Potions.WATER && effects.isEmpty()
                    && servantTeleportRandomly()) {
                return false;
            }
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && (source.is(DamageTypeTags.IS_FALL)
                        || (isElite() && source.is(DamageTypeTags.IS_DROWNING))
                        || source.is(DamageTypes.CACTUS)
                        || source.is(DamageTypes.SWEET_BERRY_BUSH))
                && servantTeleportRandomly()) {
            return false;
        }
        float applied = isElite() ? amount * 0.5F : amount;
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && getTarget() != null && hurtsTick <= 0
                && applied < getHealth() * (isElite() ? 10.0F : 5.0F)) {
            float dodgeChance = isElite() ? 0.75F : 0.3F;
            if (random.nextFloat() < dodgeChance && servantTeleportRandomly()) {
                return false;
            }
        }
        boolean hurt = super.hurt(source, applied);
        if (hurt && applied > getHealth() / 3.0F) {
            hurtsTick = HURT_CONVERGE_TICKS;
        }
        return hurt;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide()) {
            float chance = isElite() ? 0.85F : 0.3F;
            if (random.nextFloat() < chance) {
                servantTeleportRandomly();
            }
        }
        return hit;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            spawnPortalParticles();
            return;
        }
        if (teleportCooldown > 0) {
            teleportCooldown--;
        }
        if (hurtsTick > 0) {
            hurtsTick -= isElite() ? 11 : 1;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner() && held.is(Items.CHORUS_FRUIT)
                && ServantFeedHeal.feed(this, player, held, CHORUS_FRUIT_HEAL)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private void spawnPortalParticles() {
        for (int i = 0; i < 2; i++) {
            level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D),
                    (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(),
                    (random.nextDouble() - 0.5D) * 2.0D);
        }
    }

    private boolean servantTeleportRandomly() {
        if (level().isClientSide() || !isAlive()) {
            return false;
        }
        double x = getX() + (random.nextDouble() - 0.5D) * 64.0D;
        double y = getY() + (double) (random.nextInt(64) - 32);
        double z = getZ() + (random.nextDouble() - 0.5D) * 64.0D;
        return servantTeleport(x, y, z);
    }

    public boolean servantTeleportNear(LivingEntity target) {
        if (!(level() instanceof ServerLevel server) || target == null || !target.isAlive()) {
            return false;
        }
        BlockPos landing = ServantTeleportHelper.findLanding(this, target, 2.0D, 4.0D,
                target.blockPosition().getY(), 3);
        if (landing == null) {
            return false;
        }
        boolean moved = teleportTo(server, landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D,
                Set.of(), getYRot(), getXRot());
        if (moved) {
            playTeleportSound();
        }
        return moved;
    }

    private boolean servantTeleport(double x, double y, double z) {
        if (level().isClientSide()) {
            return false;
        }
        boolean moved = randomTeleport(x, y, z, true);
        if (moved) {
            playTeleportSound();
        }
        return moved;
    }

    private void playTeleportSound() {
        playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    private static final class TeleportTowardsTargetGoal extends Goal {
        private final EnderStowawayServant servant;
        private LivingEntity target;

        TeleportTowardsTargetGoal(EnderStowawayServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            target = servant.getTarget();
            return target != null && target.isAlive() && !servant.isAlliedTo(target)
                    && servant.teleportCooldown <= 0
                    && servant.distanceToSqr(target) > APPROACH_DISTANCE_SQR;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            servant.teleportCooldown = servant.servantTeleportNear(target)
                    ? APPROACH_TELEPORT_COOLDOWN : DODGE_TELEPORT_COOLDOWN;
            target = null;
        }
    }

    private static final class GiantBodyRotationControl extends BodyRotationControl {
        private final Mob mob;
        private int headStableTime;
        private float lastStableYHeadRot;

        GiantBodyRotationControl(Mob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void clientTick() {
            if (this.mob.getX() - this.mob.xo != 0.0D || this.mob.getZ() - this.mob.zo != 0.0D) {
                this.mob.yBodyRot = this.mob.getYRot();
                this.rotateHeadTowardsLook();
                this.lastStableYHeadRot = this.mob.yHeadRot;
                this.headStableTime = 0;
            } else if (this.isNoTarget()) {
                if (Math.abs(this.mob.yHeadRot - this.lastStableYHeadRot) > 15.0F) {
                    this.headStableTime = 0;
                    this.lastStableYHeadRot = this.mob.yHeadRot;
                    this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot,
                            this.mob.getMaxHeadYRot());
                } else {
                    float limit = 75.0F;
                    ++this.headStableTime;
                    if (this.headStableTime > 10) {
                        limit = Math.max(1.0F - (float) (this.headStableTime - 10) / 10.0F, 0.0F) * 75.0F;
                    }
                    this.mob.yBodyRot = approach(this.mob.yHeadRot, this.mob.yBodyRot, limit);
                }
            }
        }

        private void rotateHeadTowardsLook() {
            this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, this.mob.getMaxHeadYRot());
        }

        private boolean isNoTarget() {
            return !(this.mob.getTarget() instanceof Mob);
        }

        private static float approach(float target, float current, float limit) {
            float delta = Mth.wrapDegrees(current - target);
            if (delta < -limit) {
                delta = -limit;
            } else if (delta >= limit) {
                delta = limit;
            }
            return target + delta * 0.55F;
        }
    }
}
