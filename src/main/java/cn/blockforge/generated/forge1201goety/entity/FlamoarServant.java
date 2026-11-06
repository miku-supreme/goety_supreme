package cn.blockforge.generated.goetysupreme.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.ServantWanderGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.model.FlamoarServantModel;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class FlamoarServant extends AbstractJerotesServant {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();
    public final AnimationState attack4AnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANIM_TICK =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_ANIM =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ATTACK_USE =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_LADY =
            SynchedEntityData.defineId(FlamoarServant.class, EntityDataSerializers.BOOLEAN);

    private static final int ATTACK_START_TICK = 25;

    private static final int ATTACK_HIT_TICK = 15;
    private static final int ATTACK_HIT_TICK_USE5 = 13;

    private static final float FIRE_ABSORPTION_PERCENTAGE = 60.0F;

    public float thisTickRenderTime;
    public float lastTickRenderTime = 6.0F;
    public float attackAnimProgress;

    public FlamoarServant(EntityType<? extends FlamoarServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 40;
        setMaxUpStep(2.1F);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        setPathfindingMalus(BlockPathTypes.BLOCKED, 0.0F);
        setPathfindingMalus(BlockPathTypes.FENCE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0F);
        setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0.0F);
        setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.33D)
                .add(Attributes.FLYING_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ARMOR, 18.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 4.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/flamoar/flamoar.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.FLAMOAR;
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
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ServantWanderGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new ServantWanderGoal(this, 0.6D, 0.00001F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(net.minecraft.world.entity.EquipmentSlot.MAINHAND, "flamoar_scimitar");
        equipJerotes(net.minecraft.world.entity.EquipmentSlot.OFFHAND, "flamoar_scimitar");
        setDropChance(net.minecraft.world.entity.EquipmentSlot.MAINHAND, 0.0F);
        setDropChance(net.minecraft.world.entity.EquipmentSlot.OFFHAND, 0.0F);
    }

    private static final String SCIMITAR_PATH = "flamoar_scimitar";

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        LivingEntity owner = getTrueOwner();
        if (owner != null && owner != player) {
            return super.mobInteract(player, hand);
        }
        if (owner == player && ServantFeedHeal.tryBlazeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (owner == player && ServantFeedHeal.tryFireChargeFeed(this, player, held)) {
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        if (!isFlamoarScimitar(held)) {
            return super.mobInteract(player, hand);
        }
        EquipmentSlot slot = player.isShiftKeyDown() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
        if (!level().isClientSide()) {
            ItemStack old = getItemBySlot(slot).copy();
            setItemSlot(slot, held.copyWithCount(1));
            setDropChance(slot, 0.0F);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            if (!old.isEmpty() && !RingServantGear.isRingGear(old) && !player.addItem(old)) {
                player.drop(old, false);
            }
            playSound(SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    private static boolean isFlamoarScimitar(ItemStack stack) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && "jerotesvillage".equals(id.getNamespace())
                && SCIMITAR_PATH.equals(id.getPath());
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIM_STATE, 0);
        this.entityData.define(DATA_ANIM_TICK, 0);
        this.entityData.define(DATA_ATTACK_ANIM, 0);
        this.entityData.define(DATA_ATTACK_TICK, 0);
        this.entityData.define(DATA_ATTACK_USE, 0);
        this.entityData.define(DATA_LADY, false);
    }

    public int getAnimTick() {
        return this.entityData.get(DATA_ANIM_TICK);
    }

    public void setAnimTick(int ticks) {
        this.entityData.set(DATA_ANIM_TICK, ticks);
    }

    public int getAttackAnim() {
        return this.entityData.get(DATA_ATTACK_ANIM);
    }

    public void setAttackAnim(int value) {
        this.entityData.set(DATA_ATTACK_ANIM, value);
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAttackTick(int ticks) {
        this.entityData.set(DATA_ATTACK_TICK, ticks);
    }

    public int getAttackUse() {
        return this.entityData.get(DATA_ATTACK_USE);
    }

    public void setAttackUse(int use) {
        this.entityData.set(DATA_ATTACK_USE, use);
    }

    public boolean isLady() {
        return this.entityData.get(DATA_LADY);
    }

    public void setLady(boolean lady) {
        this.entityData.set(DATA_LADY, lady);
    }

    private void setAnimationId(int id) {
        this.entityData.set(DATA_ANIM_STATE, id);
    }

    private static int animationId(String animation) {
        return switch (animation) {
            case "attack1" -> 1;
            case "attack2" -> 2;
            case "attack3" -> 3;
            case "attack4" -> 4;
            case "dead" -> 5;
            default -> 0;
        };
    }

    private AnimationState stateForId(int id) {
        return switch (id) {
            case 1 -> this.attack1AnimationState;
            case 2 -> this.attack2AnimationState;
            case 3 -> this.attack3AnimationState;
            case 4 -> this.attack4AnimationState;
            case 5 -> this.deadAnimationState;
            default -> null;
        };
    }

    private List<AnimationState> allAnimations() {
        return List.of(this.attack1AnimationState, this.attack2AnimationState,
                this.attack3AnimationState, this.attack4AnimationState, this.deadAnimationState);
    }

    private void playAnimation(String animation, int ticks) {
        if (this.level().isClientSide) {
            return;
        }
        this.setAnimTick(ticks);
        this.setAnimationId(animationId(animation));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!DATA_ANIM_STATE.equals(key) || !this.level().isClientSide) {
            return;
        }
        AnimationState state = stateForId(this.entityData.get(DATA_ANIM_STATE));
        if (state == null) {
            allAnimations().forEach(AnimationState::stop);
            return;
        }
        state.start(this.tickCount);
        for (AnimationState other : allAnimations()) {
            if (other != state) {
                other.stop();
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.9F;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(DamageTypes.CRAMMING)
                || source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypeTags.IS_FIRE)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castFireAbsorption(source, amount, FIRE_ABSORPTION_PERCENTAGE);
        if (this.isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        if (source.getEntity() instanceof LivingEntity attacker && attacker.isOnFire()
                && !attacker.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            return super.hurt(source, amount / 3.0F);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        resetFallDistance();
    }

    public AABB getAttackBoundingBox() {
        AABB box = getBoundingBox();
        Entity vehicle = getVehicle();
        if (vehicle != null) {
            AABB other = vehicle.getBoundingBox();
            box = new AABB(Math.min(box.minX, other.minX), box.minY, Math.min(box.minZ, other.minZ),
                    Math.max(box.maxX, other.maxX), box.maxY, Math.max(box.maxZ, other.maxZ));
        }
        double margin = Math.sqrt(2.04F) - 0.6D;
        return box.inflate(margin, 0.0D, margin).inflate(0.5D, 0.5D, 0.5D);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return this.getAttackBoundingBox().intersects(target.getBoundingBox());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!this.level().isClientSide) {
            this.setAnimTick(20);
            this.setAttackTick(ATTACK_START_TICK);
            int roll = this.random.nextInt(40);
            if (roll > 30) {
                this.setAttackUse(1);
                this.playAnimation("attack1", 20);
            } else if (roll > 20) {
                this.setAttackUse(2);
                this.playAnimation("attack2", 20);
            } else if (roll > 10) {
                this.setAttackUse(2);
                this.playAnimation("attack3", 20);
            } else {
                this.setAttackUse(3);
                this.playAnimation("attack4", 20);
            }
        }
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);
        return true;
    }

    public boolean trueHurt() {
        if (!isSilent()) {
            playSound(jerotesSound("flamoar_attack", SoundEvents.RAVAGER_ATTACK), 2.0F, 1.0F);
            if (getAttackUse() == 1) {
                playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0F, 1.0F);
            }
            if (getAttackUse() == 2) {
                playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, 1.0F);
            }
            if (getAttackUse() == 3) {
                playSound(SoundEvents.PLAYER_ATTACK_CRIT, 2.0F, 1.0F);
            }
        }
        float damageMulti = 1.0F;
        float knockbackMulti = 1.0F;
        float reach = 1.5F;
        int use = getAttackUse();
        if (use == 1) {
            damageMulti = 1.25F;
            knockbackMulti = 1.25F;
        }
        if (use == 3) {
            damageMulti = 1.25F;
            reach = 1.55F;
        }
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        List<LivingEntity> victims = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getAttackBoundingBox().inflate(reach));
        for (LivingEntity victim : victims) {
            if (victim == this || !canAttack(victim) || isAlliedTo(victim)) {
                continue;
            }
            if (use != 3 && use != 4 && !hasLineOfSight(victim)) {
                continue;
            }
            if (damage <= 0.0F) {
                continue;
            }
            if (!victim.hurt(this.damageSources().mobAttack(this), damage * damageMulti)) {
                continue;
            }
            if (knockback > 0.0F) {
                victim.knockback(knockback * knockbackMulti,
                        Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F)),
                        -Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }
            this.setLastHurtMob(victim);
            victim.setRemainingFireTicks(16);
            hitParticles(victim);
        }
        if (use == 2) {
            dash(0.5F);
        }
        sweepAttackParticles();
        return true;
    }

    private void hitParticles(LivingEntity victim) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double dx = -Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F));
        double dz = Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F));
        for (int i = 0; i < 15; ++i) {
            double xs = this.getX() + dx + (this.random.nextFloat() - 0.5F) * 2.0F
                    - (this.getX() - victim.getX()) / 2.0D;
            double ys = this.getY(0.5D) + this.random.nextFloat() - 0.5D
                    - (this.getY() - victim.getY()) / 2.0D;
            double zs = this.getZ() + dz + (this.random.nextFloat() - 0.5F) * 2.0F
                    - (this.getZ() - victim.getZ()) / 2.0D;
            server.sendParticles(ParticleTypes.FLAME, xs, ys, zs, 0, dx, 0.0D, dz, 0.0D);
        }
    }

    private void rushAttack() {
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float fx = -Mth.sin(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float fy = -Mth.sin(pitch * ((float) Math.PI / 180.0F));
        float fz = Mth.cos(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float len = Mth.sqrt(fx * fx + fy * fy + fz * fz);
        float speed = 0.015F / len * 2.0F;
        this.addDeltaMovement(new Vec3(fx * speed, fy * speed, fz * speed));
    }

    private void dash(float strength) {
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float fx = -Mth.sin(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float fy = -Mth.sin(pitch * ((float) Math.PI / 180.0F));
        float fz = Mth.cos(yaw * ((float) Math.PI / 180.0F)) * Mth.cos(pitch * ((float) Math.PI / 180.0F));
        float len = Mth.sqrt(fx * fx + fy * fy + fz * fz);
        float factor = strength / len * 2.0F;
        if (!this.onGround()) {
            fy = 0.0F;
        }
        this.addDeltaMovement(new Vec3(fx * factor, fy * factor, fz * factor));
    }

    private void sweepAttackParticles() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double dx = -Mth.sin(this.getYRot() * ((float) Math.PI / 180.0F));
        double dz = Mth.cos(this.getYRot() * ((float) Math.PI / 180.0F));
        server.sendParticles(ParticleTypes.SWEEP_ATTACK,
                this.getX() + dx, this.getY(0.5D), this.getZ() + dz, 0, dx, 0.0D, dz, 0.0D);
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        setNoGravity(true);
        if (this.level().isClientSide) {
            this.lastTickRenderTime = this.thisTickRenderTime;
            this.thisTickRenderTime = 0.0F;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
            heal(3.0F);
        }
        if ((blockPosition().getY() < this.level().getMinBuildHeight() && getTarget() == null)
                || isInWallSelf()) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (0.3D - motion.y) * 0.3D, 0.0D));
        }
        LivingEntity target = getTarget();
        if (target != null && this.distanceTo(target) > 3.0F && getAttackTick() <= 0) {
            this.getLookControl().setLookAt(target, 360.0F, 360.0F);
            this.lookAt(target, 360.0F, 360.0F);
            rushAttack();
        }
        if (target != null && (!target.isAlive()
                || (target instanceof Player player && (player.isCreative() || player.isSpectator())))) {
            setTarget(null);
        }
        if (!this.level().isClientSide) {
            this.setAnimTick(Math.max(-1, getAnimTick() - 1));
        }
        if (getAnimTick() == 0 && !this.level().isClientSide) {
            this.setAnimationId(0);
        }
        this.clearFire();
        if (isAlive()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
        if (this.level().isClientSide) {
            for (int i = 0; i < 3; ++i) {
                this.level().addParticle(ParticleTypes.FLAME,
                        this.getRandomX(0.4D),
                        this.getY() + 0.15D + (2.0D * this.random.nextDouble() - 1.0D) * 0.6D,
                        this.getRandomZ(0.4D), 0.0D, 0.0D, 0.0D);
            }
            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.FLAME,
                        this.getRandomX(1.0D), this.getEyeY(), this.getRandomZ(1.0D),
                        0.0D, 0.0D, 0.0D);
            }
        }
        if (!this.level().isClientSide) {
            this.setAttackTick(Math.max(0, getAttackTick() - 1));
        }
        if (getAttackUse() != 5 && getAttackTick() == ATTACK_HIT_TICK && isAlive()) {
            trueHurt();
        }
        if (getAttackUse() == 5 && isAlive() && getAttackTick() == ATTACK_HIT_TICK_USE5) {
            trueHurt();
        }
        if (getAttackTick() > 0) {
            this.getNavigation().stop();
        }
        if (getTarget() != null && getAttackTick() <= 5) {
            if (!this.level().isClientSide) {
                this.setAttackAnim(Math.min(getAttackAnim() + 1, 10));
            }
        } else if (!this.level().isClientSide) {
            this.setAttackAnim(Math.max(getAttackAnim() - 2, 0));
        }
        if (getAttackTick() > 20 && getTarget() != null) {
            this.lookAt(getTarget(), 360.0F, 360.0F);
            this.getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
        }
    }

    public boolean isInWallSelf() {
        float halfWidth = getBbWidth() * 0.8F;
        AABB box = AABB.ofSize(getEyePosition(), halfWidth, 1.0E-6D, halfWidth);
        return BlockPos.betweenClosedStream(box).anyMatch(pos -> {
            BlockState state = this.level().getBlockState(pos);
            return !state.isAir()
                    && state.isSuffocating(this.level(), pos)
                    && Shapes.joinIsNotEmpty(
                            state.getCollisionShape(this.level(), pos)
                                    .move(pos.getX(), pos.getY(), pos.getZ()),
                            Shapes.create(box), BooleanOp.AND);
        });
    }

    @Override
    public void tickDeath() {
        if (this.deathTime <= 0 && !this.level().isClientSide) {
            this.setAnimTick(20);
            this.playAnimation("dead", 20);
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        if (spawnType != MobSpawnType.CONVERSION) {
            setLady(level.getRandom().nextFloat() > 0.8F);
        }
        return result;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AnimTick", getAnimTick());
        tag.putInt("AttackUse", getAttackUse());
        tag.putInt("AttackTick", getAttackTick());
        tag.putInt("AttackAnim", getAttackAnim());
        tag.putBoolean("IsLady", isLady());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAnimTick(tag.getInt("AnimTick"));
        setAttackUse(tag.getInt("AttackUse"));
        setAttackTick(tag.getInt("AttackTick"));
        setAttackAnim(tag.getInt("AttackAnim"));
        setLady(tag.getBoolean("IsLady"));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("flamoar_ambient", SoundEvents.BLAZE_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("flamoar_hurt", SoundEvents.BLAZE_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("flamoar_death", SoundEvents.BLAZE_DEATH);
    }

    public InteractionHand scimitarHand() {
        return InteractionHand.MAIN_HAND;
    }
}
