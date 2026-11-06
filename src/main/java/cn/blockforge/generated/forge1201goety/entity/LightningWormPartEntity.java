package cn.blockforge.generated.goetysupreme.entity;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;

public class LightningWormPartEntity extends LivingEntity {

    private static final EntityDataAccessor<Integer> BODY_INDEX =
            SynchedEntityData.defineId(LightningWormPartEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID =
            SynchedEntityData.defineId(LightningWormPartEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public final EntityDimensions multipartSize;
    public float radius = 1.1F;
    public float angleYaw = 180.0F * ((float) Math.PI / 180.0F);
    public float offsetY;
    public float damageMultiplier = 0.8F;
    private Entity cachedParent;

    public LightningWormPartEntity(EntityType<? extends LightningWormPartEntity> entityType, Level level) {
        super(entityType, level);
        this.multipartSize = entityType.getDimensions();
        this.noPhysics = true;
        setNoGravity(true);
    }

    public LightningWormPartEntity(EntityType<? extends LightningWormPartEntity> entityType,
            LivingEntity parent, float radius, float angleYaw, float offsetY) {
        this(entityType, parent.level());
        setParent(parent);
        this.radius = radius;
        this.angleYaw = (angleYaw + 90.0F) * ((float) Math.PI / 180.0F);
        this.offsetY = offsetY;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BODY_INDEX, 0);
        entityData.define(PARENT_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (getParentId() != null) {
            tag.putUUID("ParentUUID", getParentId());
        }
        tag.putInt("BodyIndex", getBodyIndex());
        tag.putFloat("AngleYaw", angleYaw);
        tag.putFloat("Radius", radius);
        tag.putFloat("OffsetY", offsetY);
        tag.putFloat("DamageMultiplier", damageMultiplier);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("ParentUUID")) {
            setParentId(tag.getUUID("ParentUUID"));
        }
        setBodyIndex(tag.getInt("BodyIndex"));
        this.angleYaw = tag.getFloat("AngleYaw");
        this.radius = tag.getFloat("Radius");
        this.offsetY = tag.getFloat("OffsetY");
        this.damageMultiplier = tag.getFloat("DamageMultiplier");
    }

    public UUID getParentId() {
        return entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(UUID uniqueId) {
        entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public void setParent(Entity parent) {
        setParentId(parent.getUUID());
        this.cachedParent = parent;
    }

    public Entity getParent() {
        UUID id = getParentId();
        if (id == null) {
            return null;
        }
        if (cachedParent != null && cachedParent.getUUID().equals(id) && !cachedParent.isRemoved()) {
            return cachedParent;
        }
        if (level() instanceof ServerLevel serverLevel) {
            this.cachedParent = serverLevel.getEntity(id);
        }
        return cachedParent;
    }

    public int getBodyIndex() {
        return entityData.get(BODY_INDEX);
    }

    public void setBodyIndex(int index) {
        entityData.set(BODY_INDEX, index);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (PARENT_UUID.equals(accessor) || BODY_INDEX.equals(accessor)) {
            refreshDimensions();
        }
        super.onSyncedDataUpdated(accessor);
    }

    public void setInitialPartPos(Entity parent) {
        setPos(parent.getX() + radius * Math.cos(parent.getYRot() * ((float) Math.PI / 180.0F) + angleYaw),
                parent.getY() + offsetY,
                parent.getZ() + radius * Math.sin(parent.getYRot() * ((float) Math.PI / 180.0F) + angleYaw));
    }

    public void forceResetPosition() {
        Entity parent = getParent();
        if (parent == null) {
            return;
        }
        double targetX = parent.getX() + radius * Math.cos(parent.getYRot() * ((float) Math.PI / 180.0F) + angleYaw);
        double targetY = parent.getY() + offsetY;
        double targetZ = parent.getZ() + radius * Math.sin(parent.getYRot() * ((float) Math.PI / 180.0F) + angleYaw);
        setPos(Mth.lerp(0.2D, getX(), targetX), Mth.lerp(0.2D, getY(), targetY), Mth.lerp(0.2D, getZ(), targetZ));
    }

    @Override
    public boolean canFreeze() {
        Entity parent = getParent();
        return (parent == null || parent.canFreeze()) && super.canFreeze();
    }

    @Override
    public boolean isFreezing() {
        Entity parent = getParent();
        return (parent == null || parent.isFreezing()) && super.isFreezing();
    }

    @Override
    public boolean isOnFire() {
        Entity parent = getParent();
        return (parent == null || parent.isOnFire()) && super.isOnFire();
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public ItemStack getPickResult() {
        Entity parent = getParent();
        return parent != null ? parent.getPickResult() : ItemStack.EMPTY;
    }

    @Override
    public void playBlockFallSound() {
    }

    @Override
    public boolean startRiding(Entity entity) {
        return false;
    }

    @Override
    public void handleInsidePortal(BlockPos pos) {
    }

    @Override
    public void tick() {
        this.setDeltaMovement(Vec3.ZERO);
        if (tickCount > 20) {
            if (tickCount % 120 == 0 && !level().isClientSide) {
                forceResetPosition();
                return;
            }
            Entity parent = getParent();
            refreshDimensions();
            if (level() instanceof ServerLevel && parent != null) {
                setNoGravity(true);
                setPos(parent.xo + radius * Math.cos(parent.yRotO * ((float) Math.PI / 180.0F) + angleYaw),
                        parent.yo + offsetY,
                        parent.zo + radius * Math.sin(parent.yRotO * ((float) Math.PI / 180.0F) + angleYaw));
                double dx = parent.getX() - getX();
                double dy = parent.getY() - getY();
                double dz = parent.getZ() - getZ();
                float pitch = -((float) (Mth.atan2(dy, Mth.sqrt((float) (dx * dx + dz * dz))) * 57.2957763671875D));
                setXRot(Mth.approachDegrees(getXRot(), pitch, 5.0F));
                hurtTime = 0;
                setYRot(parent.yRotO);
                yHeadRot = getYRot();
                yBodyRot = parent.yRotO;
                if (parent instanceof LivingEntity living && (living.hurtTime > 0 || living.deathTime > 0)) {
                    this.hurtTime = living.hurtTime;
                    this.deathTime = living.deathTime;
                }
                if (parent.isRemoved()) {
                    discard();
                }
            } else if (tickCount > 20 && level() instanceof ServerLevel) {
                discard();
            }
        }
        super.tick();
        transferEffectsAndTimers();
    }

    private void transferEffectsAndTimers() {
        if (level().isClientSide) {
            return;
        }
        if (!getActiveEffects().isEmpty() && getParent() instanceof LivingEntity parent) {
            for (MobEffectInstance effect : ImmutableList.copyOf(getActiveEffects())) {
                if (!parent.hasEffect(effect.getEffect())) {
                    parent.addEffect(new MobEffectInstance(effect));
                }
            }
            removeAllEffects();
        }
        if (getRemainingFireTicks() > 0 && getParent() instanceof LivingEntity parent
                && getRemainingFireTicks() > parent.getRemainingFireTicks()) {
            parent.setRemainingFireTicks(getRemainingFireTicks());
        }
        setRemainingFireTicks(0);
        if (getTicksFrozen() > 0 && getParent() instanceof LivingEntity parent
                && getTicksFrozen() > parent.getTicksFrozen()) {
            parent.setTicksFrozen(getTicksFrozen());
        }
        setTicksFrozen(0);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return super.hurt(source, amount);
        }
        Entity parent = getParent();
        return parent != null && parent.hurt(source, amount * damageMultiplier);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypeTags.IS_DROWNING)
                || source.is(DamageTypeTags.IS_FIRE)
                || source.is(DamageTypes.IN_WALL)
                || source.is(DamageTypes.FALLING_BLOCK)
                || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.CRAMMING)
                || source.is(DamageTypes.DRY_OUT)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        Entity parent = getParent();
        if (parent != null) {
            return parent == entity || parent.isAlliedTo(entity);
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity parent = getParent();
        return parent != null ? parent.interact(player, hand) : InteractionResult.PASS;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void onInsideBlock(BlockState state) {
    }

    @Override
    public void doPush(Entity entity) {
        Entity parent = getParent();
        if (entity.getType() != this.getType() && (parent == null || entity.getType() != parent.getType())) {
            super.doPush(entity);
        }
    }

    @Override
    public void pushEntities() {
    }

    @Override
    public int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    public void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        if (getParent() == null) {
            super.checkFallDamage(y, onGround, state, pos);
        }
    }
}
