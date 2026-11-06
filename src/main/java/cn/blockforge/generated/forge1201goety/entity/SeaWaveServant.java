package cn.blockforge.generated.goetysupreme.entity;

import java.util.Collections;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyFrostNovaHelper;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.IframeHit;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;

public class SeaWaveServant extends BulgeServant {

    private static final EntityDataAccessor<Integer> ANIM_STATE =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> IDLE_ANIM =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROLL_ANIM =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_USE =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHOOT_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WIND_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BULGE_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BULGE_USE_TICK =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TRIDENT_COUNT =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TRIDENT_ADD_COOLDOWN =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> TRIDENT_HEALTH =
            SynchedEntityData.defineId(SeaWaveServant.class, EntityDataSerializers.FLOAT);

    private static final int ANIM_NONE = 0;
    private static final int ANIM_SHOOT = 1;
    private static final int ANIM_WIND = 2;
    private static final int ANIM_BULGE = 3;
    private static final int ANIM_ATTACK1 = 4;
    private static final int ANIM_ATTACK2 = 5;
    private static final int ANIM_DEAD = 6;

    private static final int SPELL_LEVEL = 3;

    private static final int SUMMON_LIMIT = 6;

    private static final int SUMMON_PER_CAST = 3;

    private static final int BULGE_LIFESPAN_TICKS = 60 * 20;

    public float idleAnimProgress;
    public float rollAnimProgress;
    public float thisTickRenderTime;
    public float lastTickRenderTime;

    public final AnimationState idle1AnimationState = new AnimationState();
    public final AnimationState idle2AnimationState = new AnimationState();
    public final AnimationState windAnimationState = new AnimationState();
    public final AnimationState bulgeAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    public SeaWaveServant(EntityType<? extends SeaWaveServant> entityType, Level level) {
        super(entityType, level);
        this.spellLevelField = SPELL_LEVEL;
    }

    private int spellLevelField = SPELL_LEVEL;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SEA_WAVE;
    }

    @Override
    public float getStandingEyeHeight(net.minecraft.world.entity.Pose pose,
            net.minecraft.world.entity.EntityDimensions dimensions) {
        return 1.875F;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/elemental/seawave.png";
    }

    @Override
    public net.minecraft.sounds.SoundEvent getAmbientSound() {
        return jerotesSound("seawave_ambient", SoundEvents.DOLPHIN_AMBIENT);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("seawave_hurt", SoundEvents.DOLPHIN_HURT);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return jerotesSound("seawave_death", SoundEvents.DOLPHIN_DEATH);
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new BulgeFloatGoal(this, 0.15F));
        this.goalSelector.addGoal(2, new BigWitchAttackGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    public float rangedAttackRadius() {
        return 20.0F;
    }

    @Override
    public int rangedAttackInterval() {
        return 80;
    }

    @Override
    public int spellLevel() {
        return spellLevelField;
    }

    @Override
    public void travel(Vec3 movement) {
        super.travel(movement);
        if (specialAction() || getAttackTick() > 0) {
            setDeltaMovement(getDeltaMovement().multiply(0.005D, 1.0D, 0.005D));
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIM_STATE, ANIM_NONE);
        this.entityData.define(ANIM_TICK, 0);
        this.entityData.define(IDLE_ANIM, 0);
        this.entityData.define(ROLL_ANIM, 0);
        this.entityData.define(ATTACK_TICK, 0);
        this.entityData.define(ATTACK_USE, 0);
        this.entityData.define(SHOOT_TICK, 0);
        this.entityData.define(WIND_TICK, 0);
        this.entityData.define(BULGE_TICK, 400);
        this.entityData.define(BULGE_USE_TICK, 0);
        this.entityData.define(TRIDENT_COUNT, 4);
        this.entityData.define(TRIDENT_ADD_COOLDOWN, 0);
        this.entityData.define(TRIDENT_HEALTH, 10.0F);
    }

    public int getTridentCount() {
        return this.entityData.get(TRIDENT_COUNT);
    }

    public void setTridentCount(int n) {
        this.entityData.set(TRIDENT_COUNT, n);
    }

    public float getTridentHealth() {
        return this.entityData.get(TRIDENT_HEALTH);
    }

    public void setTridentHealth(float f) {
        this.entityData.set(TRIDENT_HEALTH, f);
    }

    public int getIdleAnim() {
        return this.entityData.get(IDLE_ANIM);
    }

    public void setIdleAnim(int n) {
        this.entityData.set(IDLE_ANIM, n);
    }

    public int getRollAnim() {
        return this.entityData.get(ROLL_ANIM);
    }

    public void setRollAnim(int n) {
        this.entityData.set(ROLL_ANIM, n);
    }

    public int getAttackTick() {
        return this.entityData.get(ATTACK_TICK);
    }

    public void setAttackTick(int n) {
        this.entityData.set(ATTACK_TICK, n);
    }

    public int getAttackUse() {
        return this.entityData.get(ATTACK_USE);
    }

    public void setAttackUse(int n) {
        this.entityData.set(ATTACK_USE, n);
    }

    public int getShootTick() {
        return this.entityData.get(SHOOT_TICK);
    }

    public void setShootTick(int n) {
        this.entityData.set(SHOOT_TICK, n);
    }

    public int getWindTick() {
        return this.entityData.get(WIND_TICK);
    }

    public void setWindTick(int n) {
        this.entityData.set(WIND_TICK, n);
    }

    public int getBulgeUseTick() {
        return this.entityData.get(BULGE_USE_TICK);
    }

    public void setBulgeUseTick(int n) {
        this.entityData.set(BULGE_USE_TICK, n);
    }

    public boolean specialAction() {
        return getShootTick() > 0 || getWindTick() > 0 || getBulgeUseTick() > 0;
    }

    private void setAnimationState(String animation) {
        int state = switch (animation) {
            case "shoot" -> ANIM_SHOOT;
            case "wind" -> ANIM_WIND;
            case "bulge" -> ANIM_BULGE;
            case "attack1" -> ANIM_ATTACK1;
            case "attack2" -> ANIM_ATTACK2;
            case "dead" -> ANIM_DEAD;
            default -> ANIM_NONE;
        };
        this.entityData.set(ANIM_STATE, state);
    }

    private void setAnimTick(int ticks) {
        this.entityData.set(ANIM_TICK, ticks);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (level().isClientSide && ANIM_STATE.equals(key)) {
            switch (this.entityData.get(ANIM_STATE)) {
                case ANIM_NONE -> stopAllAnimation();
                case ANIM_SHOOT -> {
                    shootAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(shootAnimationState);
                }
                case ANIM_WIND -> {
                    windAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(windAnimationState);
                }
                case ANIM_BULGE -> {
                    bulgeAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(bulgeAnimationState);
                }
                case ANIM_ATTACK1 -> {
                    attack1AnimationState.startIfStopped(tickCount);
                    stopMostAnimation(attack1AnimationState);
                }
                case ANIM_ATTACK2 -> {
                    attack2AnimationState.startIfStopped(tickCount);
                    stopMostAnimation(attack2AnimationState);
                }
                case ANIM_DEAD -> {
                    deadAnimationState.startIfStopped(tickCount);
                    stopMostAnimation(deadAnimationState);
                }
                default -> stopAllAnimation();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    private List<AnimationState> allActionAnimations() {
        return List.of(shootAnimationState, windAnimationState, bulgeAnimationState,
                attack1AnimationState, attack2AnimationState, deadAnimationState);
    }

    private void stopMostAnimation(AnimationState keep) {
        for (AnimationState state : allActionAnimations()) {
            if (state != keep) {
                state.stop();
            }
        }
    }

    private void stopAllAnimation() {
        for (AnimationState state : allActionAnimations()) {
            state.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (isAlive()) {
                idle1AnimationState.startIfStopped(tickCount);
                idle2AnimationState.startIfStopped(tickCount);
            }
            if (level().isClientSide) {
                lastTickRenderTime = thisTickRenderTime;
                thisTickRenderTime = 0.0F;
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpellLevel", spellLevelField);
        tag.putInt("IdleAnim", getIdleAnim());
        tag.putInt("AttackTick", getAttackTick());
        tag.putInt("AttackUse", getAttackUse());
        tag.putInt("ShootTick", getShootTick());
        tag.putInt("WindTick", getWindTick());
        tag.putInt("BulgeTick", this.entityData.get(BULGE_TICK));
        tag.putInt("BulgeUseTick", getBulgeUseTick());
        tag.putInt("TridentCount", getTridentCount());
        tag.putInt("TridentAddCooldown", this.entityData.get(TRIDENT_ADD_COOLDOWN));
        tag.putFloat("TridentHealth", getTridentHealth());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spellLevelField = tag.getInt("SpellLevel") == 0 ? SPELL_LEVEL : tag.getInt("SpellLevel");
        setIdleAnim(tag.getInt("IdleAnim"));
        setAttackTick(tag.getInt("AttackTick"));
        setAttackUse(tag.getInt("AttackUse"));
        setShootTick(tag.getInt("ShootTick"));
        setWindTick(tag.getInt("WindTick"));
        this.entityData.set(BULGE_TICK, tag.getInt("BulgeTick"));
        setBulgeUseTick(tag.getInt("BulgeUseTick"));
        setTridentCount(tag.getInt("TridentCount"));
        this.entityData.set(TRIDENT_ADD_COOLDOWN, tag.getInt("TridentAddCooldown"));
        setTridentHealth(tag.getFloat("TridentHealth"));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            return;
        }
        if (!isAggressive() && getShootTick() > 0) {
            setShootTick(0);
            shootAnimationState.stop();
            cancelSpellBurst();
        }
        if (getTarget() != null) {
            getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
            lookAt(getTarget(), 360.0F, 360.0F);
        }
        if (!specialAction() && getAttackTick() <= 0 && isAlive()) {
            setRollAnim(getRollAnim() + 1);
            setIdleAnim(Math.min(getIdleAnim() + 1, 10));
        } else {
            setIdleAnim(Math.max(getIdleAnim() - 1, 0));
        }
        if (random.nextInt(900) == 1 && isAlive()) {
            heal(3.0F);
        }
        if (random.nextInt(900) == 1 && isAlive()) {
            setTridentHealth(Math.min(10.0F, getTridentHealth() + 3.0F));
        }
        if (isAlive()) {
            LivingEntity target = getTarget();
            if (getAttackTick() <= -60 && target != null && isWithinMeleeAttackRange(target)
                    && getAttackBoundingBox().intersects(target.getBoundingBox())
                    && getSensing().hasLineOfSight(target)) {
                swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                doHurtTarget(target);
            }
            if (getAttackTick() == 10) {
                trueHurt();
            }
            if (inWaterColumn() && getAttackTick() > 5 && getAttackTick() <= 12
                    && getAttackUse() == 2 && level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 24; ++i) {
                    double angle = Math.PI * 2.0D * i / 24.0D;
                    double offsetX = Math.cos(angle) * 3.0D;
                    double offsetZ = Math.sin(angle) * 3.0D;
                    serverLevel.sendParticles(ParticleTypes.BUBBLE_POP, getX() + offsetX, getY() - 0.05D,
                            getZ() + offsetZ, 0, 0.0D, 0.5D, 0.0D, 1.0D);
                    serverLevel.sendParticles(ParticleTypes.BUBBLE, getX() + offsetX, getY() - 0.05D,
                            getZ() + offsetZ, 0, 0.0D, 0.5D, 0.0D, 1.0D);
                    serverLevel.sendParticles(ParticleTypes.BUBBLE_POP, getX() + offsetX, getY() - 0.05D,
                            getZ() + offsetZ, 0, 0.0D, -0.5D, 0.0D, 1.0D);
                    serverLevel.sendParticles(ParticleTypes.BUBBLE, getX() + offsetX, getY() - 0.05D,
                            getZ() + offsetZ, 0, 0.0D, -0.5D, 0.0D, 1.0D);
                }
            }
            if (getTridentCount() < 4 && this.entityData.get(TRIDENT_ADD_COOLDOWN) <= 0) {
                setTridentHealth(10.0F);
                setTridentCount(Math.min(4, getTridentCount() + 1));
                this.entityData.set(TRIDENT_ADD_COOLDOWN, 120);
                gemstoneBubbleBurst(20);
                playSound(jerotesSound("seawave_regenerat", SoundEvents.AMETHYST_BLOCK_CHIME));
            }
            if (getTridentCount() > 0 && getTridentHealth() <= 0.0F) {
                setTridentHealth(10.0F);
                setTridentCount(Math.max(0, getTridentCount() - 1));
                this.entityData.set(TRIDENT_ADD_COOLDOWN, 120);
                gemstoneBubbleBurst(20);
                playSound(jerotesSound("seawave_break", SoundEvents.AMETHYST_BLOCK_HIT));
            }
            if (getWindTick() == 20 && getTarget() != null) {
                castGemstoneWaves();
            }
            if (this.entityData.get(BULGE_TICK) >= 600 && isAggressive() && !specialAction()
                    && getAttackTick() <= 0) {
                int bulges = countGateBulges();
                int seawaves = countGateSeawaves();
                if (bulges <= seawaves * 4) {
                    setAnimTick(25);
                    setAnimationState("bulge");
                    this.entityData.set(BULGE_TICK, 0);
                    setBulgeUseTick(25);
                }
            }
            if (getBulgeUseTick() == 5) {
                summonBulges();
                if (!isSilent()) {
                    level().playSound(null, getX(), getY(), getZ(),
                            jerotesSound("seawave_bulge", SoundEvents.DOLPHIN_PLAY), getSoundSource(), 10.0F, 1.0F);
                }
            }
            setAttackTick(Math.max(-60, getAttackTick() - 1));
            this.entityData.set(TRIDENT_ADD_COOLDOWN, Math.max(0, this.entityData.get(TRIDENT_ADD_COOLDOWN) - 1));
            setShootTick(Math.max(0, getShootTick() - 1));
            setWindTick(Math.max(0, getWindTick() - 1));
            this.entityData.set(BULGE_TICK, Math.min(600, this.entityData.get(BULGE_TICK) + 1));
            setBulgeUseTick(Math.max(0, getBulgeUseTick() - 1));
        }
        int animTick = this.entityData.get(ANIM_TICK);
        if (animTick > 0) {
            animTick = Math.max(0, animTick - 1);
            this.entityData.set(ANIM_TICK, animTick);
        } else if (this.entityData.get(ANIM_STATE) != ANIM_DEAD && this.entityData.get(ANIM_STATE) != ANIM_NONE) {
            setAnimationState("");
        }
    }

    public AABB getAttackBoundingBox() {
        AABB aabb;
        Entity entity = getVehicle();
        if (entity != null) {
            AABB vehicleBox = entity.getBoundingBox();
            AABB self = getBoundingBox();
            aabb = new AABB(Math.min(self.minX, vehicleBox.minX), self.minY, Math.min(self.minZ, vehicleBox.minZ),
                    Math.max(self.maxX, vehicleBox.maxX), self.maxY, Math.max(self.maxZ, vehicleBox.maxZ));
        } else {
            aabb = getBoundingBox();
        }
        AABB grown = aabb.inflate(Math.sqrt(2.04F) - 0.6D, 0.0D, Math.sqrt(2.04F) - 0.6D);
        return grown.inflate(1.25D, 1.25D, 1.25D);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return getAttackBoundingBox().intersects(target.getBoundingBox());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (specialAction() || getAttackTick() > -60) {
            return false;
        }
        if (!level().isClientSide) {
            setAttackTick(30);
            setAnimTick(30);
            int attackRandom = random.nextInt(30);
            if (attackRandom > 15 || getTridentCount() <= 0) {
                setAnimationState("attack2");
                setAttackUse(2);
                if (!isSilent()) {
                    playSound(jerotesSound("seawave_shockwave", SoundEvents.GENERIC_EXPLODE), 5.0F, 1.0F);
                }
            } else {
                setAnimationState("attack1");
                setAttackUse(1);
            }
        }
        return true;
    }

    private void trueHurt() {
        float damageMulti = 1.0F;
        float knockbackMulti = 1.0F;
        float reach = 0.5F;
        if (getAttackUse() == 2) {
            knockbackMulti = 1.5F;
            reach = 1.25F;
        }
        if (getAttackUse() == 1) {
            knockbackMulti = 1.25F;
            if (!isSilent()) {
                playSound(jerotesSound("seawave_trident", SoundEvents.TRIDENT_THROW), 5.0F, 1.0F);
            }
        }
        float baseDamage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        for (LivingEntity hurt : level().getEntitiesOfClass(LivingEntity.class,
                getAttackBoundingBox().inflate(reach))) {
            if (hurt == null || distanceToSqr(hurt) > 128.0D) {
                continue;
            }
            if (!validTarget(hurt)) {
                continue;
            }
            if (!getSensing().hasLineOfSight(hurt) && (getAttackUse() != 2 || !inWaterColumn())) {
                continue;
            }
            if (hurt instanceof AbstractBulgeServant bulge
                    && bulge.getTarget() != this && getTarget() != bulge) {
                continue;
            }
            if (getAttackUse() == 2) {
                boolean bl = hurt.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(this, getTrueOwner()),
                        baseDamage * damageMulti);
                if (bl && sizeAllowsSpecialKnockback(hurt)) {
                    double kb = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                    double d3 = Math.max(0.0D, 1.0D - Math.max(kb, 1.0D)) + 0.3D;
                    knockAway(hurt, d3, knockbackMulti);
                }
                if (inWaterColumn() && sizeAllowsSpecialKnockback(hurt)) {
                    double kb = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                    double d3 = Math.max(0.0D, 1.0D - Math.max(kb, 1.0D)) + 0.2D;
                    knockAway(hurt, d3, knockbackMulti);
                }
                continue;
            }
            int count = 0;
            if (canSeeAngle(hurt.getEyePosition(), 0.0F, 45.0D)) {
                if (getTridentCount() >= 4) {
                    count++;
                }
                if (getTridentCount() >= 3) {
                    count++;
                }
            }
            if (canSeeAngle(hurt.getEyePosition(), 45.0F, 80.0D) && getTridentCount() >= 2) {
                count++;
            }
            if (canSeeAngle(hurt.getEyePosition(), -45.0F, 80.0D) && getTridentCount() >= 1) {
                count++;
            }
            if (count <= 0) {
                continue;
            }
            DamageSource tridentSource = GoetyFrostNovaHelper.extraDamageSource(
                    ModDamageSource.summonAttack(this, getTrueOwner()));
            for (int i = 0; i < count; i++) {
                IframeHit.hurt(hurt, tridentSource, baseDamage * damageMulti);
            }
        }
        if (getAttackUse() == 2) {
            for (LivingEntity shake : level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(12.0D))) {
                if (shake == null || shake == this || level().isClientSide) {
                    continue;
                }
                applyJerotesEffect(shake, "quake", 20, 0);
            }
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(jerotesParticle("seawave_shockwave", ParticleTypes.EXPLOSION),
                        getX(), getY() + 0.1D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            for (Boat boat : level().getEntitiesOfClass(Boat.class, getAttackBoundingBox().inflate(reach))) {
                if (boat == null || distanceToSqr(boat) > 128.0D) {
                    continue;
                }
                boat.hurt(damageSources().mobAttack(this), 30.0F);
            }
        } else {
            sweepAttack();
        }
    }

    private void knockAway(LivingEntity hurt, double d3, float knockbackMulti) {
        double factor = d3 / 1.5D * (0.7D + knockbackMulti / 3.0D);
        hurt.setOnGround(false);
        hurt.setDeltaMovement(hurt.getDeltaMovement().add(
                -(getX() - hurt.getX()) * factor,
                -(getY() - hurt.getY()) * factor,
                -(getZ() - hurt.getZ()) * factor));
        hurt.setDeltaMovement(hurt.getDeltaMovement().add(
                0.0D, (7.0F - distanceTo(hurt)) / 10.0F, 0.0D));
        hurt.hurtMarked = true;
    }

    private static boolean sizeAllowsSpecialKnockback(LivingEntity hurt) {
        return !mobSizeGiant(hurt);
    }

    private static boolean mobSizeFind(Entity entity, float average, float max) {
        double width = entity.getBoundingBox().getXsize();
        double height = entity.getBoundingBox().getYsize();
        return !(width >= (double) max || height >= (double) max
                || (width >= (double) average && height >= (double) average));
    }

    private static boolean mobSizeSmall(Entity entity) {
        return mobSizeFind(entity, 0.75F, 1.75F);
    }

    private static boolean mobSizeMedium(Entity entity) {
        return mobSizeFind(entity, 1.75F, 3.5F) && !mobSizeSmall(entity);
    }

    private static boolean mobSizeLarge(Entity entity) {
        return mobSizeFind(entity, 3.5F, 6.0F) && !mobSizeSmall(entity) && !mobSizeMedium(entity);
    }

    private static boolean mobSizeGiant(Entity entity) {
        return !mobSizeSmall(entity) && !mobSizeMedium(entity) && !mobSizeLarge(entity);
    }

    private boolean canSeeAngle(Vec3 targetPos, float addRotDeg, double angleThreshold) {
        double yaw = Math.toRadians(getYHeadRot() - addRotDeg);
        double lookX = -Math.sin(yaw);
        double lookZ = Math.cos(yaw);
        double dx = targetPos.x - getX();
        double dz = targetPos.z - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1.0E-4D) {
            return false;
        }
        double dot = (dx / horizontal) * lookX + (dz / horizontal) * lookZ;
        return dot > 1.0D - angleThreshold / 180.0D;
    }

    private void sweepAttack() {
        if (level() instanceof ServerLevel serverLevel) {
            double d = -net.minecraft.util.Mth.sin((float) Math.toRadians(getYRot()));
            double d2 = net.minecraft.util.Mth.cos((float) Math.toRadians(getYRot()));
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, getX() + d, getY(0.5D), getZ() + d2,
                    0, d, 0.0D, d2, 0.0D);
        }
    }

    @Override
    public void performRangedAttackServer(LivingEntity target) {
        if (specialAction() || getAttackTick() > 0) {
            return;
        }
        if (distanceTo(target) > 8.0F || random.nextFloat() > 0.5F || !getSensing().hasLineOfSight(target)) {
            setAnimTick(50);
            setAnimationState("shoot");
            setShootTick(50);
            fireBullet(target);
            startBulletBurst(target);
        } else {
            setAnimTick(20);
            setAnimationState("wind");
            setWindTick(30);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= getHealth() * 100.0F
                && getTridentCount() > 0) {
            if (!level().isClientSide) {
                setTridentHealth(getTridentHealth() - amount);
            }
            if (amount > 0.0F) {
                playSound(jerotesCoreSound("twohanded_block", SoundEvents.SHIELD_BLOCK));
            }
            amount = 0.0F;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tickDeath() {
        if (deathTime <= 0 && !level().isClientSide) {
            setAnimTick(20);
            setAnimationState("dead");
        }
        ++deathTime;
        if (deathTime >= 20 && !level().isClientSide && !isRemoved()) {
            level().broadcastEntityEvent(this, (byte) 60);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (!(getTrueOwner() instanceof Player player)) {
            return;
        }
        ItemStack helm = new ItemStack(GeneratedMod.SEA_WAVE_HELM.get());
        ReviveServantItem.setOwnerName(player, helm);
        ReviveServantItem.setSummon(this, helm);
        OwnerBoundLoot.deliver(this, helm, ParticleTypes.SPLASH);
    }

    @Override
    public void tryKill(Player player) {
        if (level().isClientSide()) {
            return;
        }
        if (getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    private void spellFeedback(String soundPath, SoundEvent fallbackSound,
            String particlePath, ParticleOptions fallbackParticle, int spellLevel) {
        if (!isSilent()) {
            playSound(jerotesSound(soundPath, fallbackSound), 1.0F, 1.0F);
        }
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(jerotesParticle(particlePath, fallbackParticle),
                    getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        showSpellLevelDisplay(spellLevel);
    }

    private void castGemstoneWaves() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        spellFeedback("magic_gemstone_waves", SoundEvents.CONDUIT_ACTIVATE,
                "gemstone_waves_display", ParticleTypes.BUBBLE, spellLevel());
        clearFire();
        int distance = 8;
        double angleReach = 30.0D;
        int count = spellLevel() * 5;
        for (int i = 0; i < count * 20; ++i) {
            double fx = (random.nextFloat() - 0.5F) * distance;
            double fy = (random.nextFloat() - 0.5F) * distance;
            double fz = (random.nextFloat() - 0.5F) * distance;
            Vec3 sample = new Vec3(getX() + fx, getEyeY() + fy / 2.0F, getRandomZ(0.8D) + fz);
            for (int retry = 0; retry < 6; ++retry) {
                fx = (random.nextFloat() - 0.5F) * distance;
                fy = (random.nextFloat() - 0.5F) * distance;
                fz = (random.nextFloat() - 0.5F) * distance;
                sample = new Vec3(getX() + fx, getEyeY() + fy / 2.0F, getRandomZ(0.8D) + fz);
                BlockState state = serverLevel.getBlockState(BlockPos.containing(sample));
                FluidState fluid = serverLevel.getFluidState(BlockPos.containing(sample));
                if (seesPos(sample) && (state.isAir() || !fluid.is(Fluids.WATER))
                        && canSeeAngle(sample, 0.0F, angleReach)) {
                    break;
                }
            }
            if (!seesPos(sample)) {
                continue;
            }
            BlockState state = serverLevel.getBlockState(BlockPos.containing(sample));
            FluidState fluid = serverLevel.getFluidState(BlockPos.containing(sample));
            if (!state.isAir() && fluid.is(Fluids.WATER)) {
                continue;
            }
            if (!canSeeAngle(sample, 0.0F, angleReach)) {
                continue;
            }
            float roll = random.nextFloat();
            if (roll > 0.5F) {
                serverLevel.sendParticles(jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE),
                        sample.x, sample.y, sample.z, 0, fx, fy, fz, 0.25F);
            } else if (roll < 0.25F) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE_POP,
                        sample.x, sample.y, sample.z, 0, fx, fy, fz, 0.25F);
            } else {
                serverLevel.sendParticles(ParticleTypes.FISHING,
                        sample.x, sample.y, sample.z, 0, fx, fy, fz, 0.25F);
            }
        }
        List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(distance, distance / 2.0F, distance));
        Collections.shuffle(list, new java.util.Random());
        for (LivingEntity victim : list) {
            if (count <= 0) {
                break;
            }
            if (victim == null || distanceTo(victim) > distance * 2.0F
                    || !canSeeAngle(victim.getPosition(0.5F), 0.0F, angleReach)
                    || !validTarget(victim) || !victim.hasLineOfSight(this)) {
                continue;
            }
            int dice = 3 + (3 - 3);
            float damage = rollDice(dice, 6) * (victim.isSensitiveToWater() ? 2.0F : 1.0F);
            if (victim.hurt(JerotesMagicDamage.drownMagic(this, null), damage)) {
                victim.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 9 * 20, 2), this);
                victim.clearFire();
                if (isInWater()) {
                    for (int i = 0; i < 5; ++i) {
                        serverLevel.sendParticles(jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE),
                                victim.getRandomX(0.8D), victim.getRandomY(), victim.getRandomZ(0.8D),
                                0, 0.0D, 0.0D, 0.0D, 0.0D);
                        serverLevel.sendParticles(jerotesParticle("gemstone_bubble_land", ParticleTypes.BUBBLE_POP),
                                victim.getRandomX(0.8D), victim.getRandomY(), victim.getRandomZ(0.8D),
                                0, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                }
                double d3 = Math.max(0.0D, 1.0D - victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                victim.push(-(getX() - victim.getX()) * 0.1D * d3,
                        -(getY() - victim.getY()) * 0.02D * d3,
                        -(getZ() - victim.getZ()) * 0.1D * d3);
            }
            --count;
        }
    }

    private int rollDice(int dice, int sides) {
        int sum = 0;
        for (int i = 0; i < dice; ++i) {
            sum += 1 + random.nextInt(sides);
        }
        return sum;
    }

    private void summonBulges() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        spellFeedback("magic_conjure_bulges", SoundEvents.CONDUIT_ACTIVATE,
                "conjure_bulges_display", ParticleTypes.BUBBLE, spellLevel());
        int existing = countOwnedLimitedBulges();
        int count = Math.min(SUMMON_PER_CAST, Math.max(0, SUMMON_LIMIT - existing));
        for (int i = 0; i < count; ++i) {
            BulgeServant bulge = ModEntityTypes.BULGE_SERVANT.get().create(serverLevel);
            if (bulge == null) {
                continue;
            }
            BlockPos summonPos = BlockFinder.SummonRadius(blockPosition(), bulge, serverLevel, 6, 9);
            if (summonPos == null) {
                summonPos = blockPosition();
            }
            bulge.moveTo(summonPos, getYRot(), 0.0F);
            bulge.setTrueOwner(this);
            bulge.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(summonPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            bulge.setTrueOwner(this);
            bulge.setLimitedLife(BULGE_LIFESPAN_TICKS);
            bulge.setPersistenceRequired();
            if (getTarget() != null && bulge.canAttack(getTarget())) {
                bulge.setTarget(getTarget());
            }
            if (serverLevel.addFreshEntity(bulge)) {
                GoetySummonFx.play(serverLevel, bulge);
                serverLevel.sendParticles(ParticleTypes.CLOUD, bulge.getX(), bulge.getY(0.5D), bulge.getZ(),
                        20, 0.4D, 0.4D, 0.4D, 0.02D);
                serverLevel.sendParticles(jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE),
                        bulge.getRandomX(0.5D), bulge.getRandomY(), bulge.getRandomZ(0.5D),
                        20, 0.0D, -0.25D, 0.0D, 0.125D);
            } else {
                bulge.discard();
            }
        }
    }

    private int countGateBulges() {
        int n = 0;
        for (BulgeServant bulge : level().getEntitiesOfClass(BulgeServant.class,
                getBoundingBox().inflate(64.0D))) {
            if (bulge instanceof SeaWaveServant || !gateKeeps(bulge)) {
                continue;
            }
            n++;
        }
        return n;
    }

    private int countGateSeawaves() {
        int n = 0;
        for (SeaWaveServant seawave : level().getEntitiesOfClass(SeaWaveServant.class,
                getBoundingBox().inflate(64.0D))) {
            if (!gateKeeps(seawave)) {
                continue;
            }
            n++;
        }
        return n;
    }

    private boolean gateKeeps(BulgeServant other) {
        if (getTarget() == other || other.getTarget() == this) {
            return false;
        }
        return (getTrueOwner() == null && other.getTrueOwner() == null) || other.isAlliedTo(this);
    }

    private int countOwnedLimitedBulges() {
        int n = 0;
        for (BulgeServant bulge : level().getEntitiesOfClass(BulgeServant.class,
                getBoundingBox().inflate(128.0D))) {
            if (bulge == this || bulge.getTrueOwner() != this || !bulge.isLimitedLife()) {
                continue;
            }
            n++;
        }
        return n;
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
    }

    private boolean inWaterColumn() {
        if (isInWater()) {
            return true;
        }
        BlockPos pos = blockPosition();
        for (int i = 1; i <= 5; ++i) {
            if (level().getFluidState(pos.below(i)).is(Fluids.WATER)) {
                return true;
            }
        }
        return false;
    }

    private void gemstoneBubbleBurst(int count) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE),
                    getRandomX(1.0D), getRandomY(), getRandomZ(1.0D), count,
                    0.0D, -0.25D, 0.0D, 0.125D);
        }
    }

    private boolean seesPos(Vec3 pos) {
        return level().clip(new ClipContext(getEyePosition(), pos, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
    }

    private boolean validTarget(LivingEntity candidate) {
        return candidate != null && candidate != this && candidate.isAlive()
                && candidate != getTrueOwner() && !isAlliedTo(candidate) && canAttack(candidate);
    }

    private void applyJerotesEffect(LivingEntity target, String path, int duration, int amplifier) {
        net.minecraft.world.effect.MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        if (effect == null) {
            effect = ForgeRegistries.MOB_EFFECTS.getValue(
                    ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        }
        if (effect == null) {
            effect = path.equals("quake") ? MobEffects.CONFUSION : null;
        }
        if (effect != null) {
            target.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false), this);
        }
    }

    private static final class BigWitchAttackGoal extends RangedAttackGoal {
        private final SeaWaveServant hag;
        private final double speedModifier = 0.8D;
        private final int attackIntervalMin = 80;
        private final float attackRadiusSqr = 20.0F * 20.0F;
        private int attackTime = -1;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        BigWitchAttackGoal(SeaWaveServant servant) {
            super(servant, 0.8D, 80, 20.0F);
            this.hag = servant;
            setFlags(java.util.EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public void start() {
            super.start();
            hag.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            hag.setAggressive(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = hag.getTarget();
            if (target == null) {
                return;
            }
            double d = hag.distanceToSqr(target.getX(), target.getY(), target.getZ());
            boolean hasLineOfSight = hag.getSensing().hasLineOfSight(target);
            boolean seen = seeTime > 0;
            if (hasLineOfSight != seen) {
                seeTime = 0;
            }
            seeTime = hasLineOfSight ? seeTime + 1 : seeTime - 1;
            if (d > attackRadiusSqr || seeTime < 20) {
                hag.getNavigation().moveTo(target, speedModifier);
                strafingTime = -1;
            } else {
                hag.getNavigation().stop();
                ++strafingTime;
            }
            if (strafingTime >= 20) {
                if (hag.getRandom().nextFloat() < 0.3F) {
                    strafingClockwise = !strafingClockwise;
                }
                if (hag.getRandom().nextFloat() < 0.3F) {
                    strafingBackwards = !strafingBackwards;
                }
                strafingTime = 0;
            }
            if (strafingTime > -1) {
                if (d > attackRadiusSqr * 0.75F) {
                    strafingBackwards = false;
                } else if (d < attackRadiusSqr * 0.25F) {
                    strafingBackwards = true;
                }
                hag.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F,
                        strafingClockwise ? 0.5F : -0.5F);
                Entity vehicle = hag.getControlledVehicle();
                if (vehicle instanceof Mob mob) {
                    mob.lookAt(target, 360.0F, 360.0F);
                }
                hag.lookAt(target, 360.0F, 360.0F);
            } else {
                hag.getLookControl().setLookAt(target, 360.0F, 360.0F);
            }
            if (--attackTime == 0) {
                if (!seen) {
                    return;
                }
                float f = (float) Math.sqrt(d) / attackRadiusSqr;
                hag.performRangedAttack(target, net.minecraft.util.Mth.clamp(f, 0.1F, 1.0F));
                attackTime = attackIntervalMin;
            } else if (attackTime < 0) {
                attackTime = attackIntervalMin;
            }
        }
    }
}
