package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AncientSecondRounderArrowEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;

public abstract class AbstractSedimentServant extends AbstractJvillageInventoryServant {

    public static final TagKey<Item> GUARD_ARMOR = itemTag("sediment_guard_armor");

    public static final TagKey<Item> GUARD_MELEE = itemTag("sediment_guard_melee");

    public static final TagKey<Item> GUARD_HEAVY_ARMOR = itemTag("sediment_guard_heavy_armor");

    public static final TagKey<Item> GUARD_HEAVY_WEAPON = itemTag("sediment_guard_heavy_weapon");

    public static final TagKey<Item> LORD_NOBLE_ARMOR = itemTag("sediment_lord_noble_armor");

    public static final TagKey<Item> GUARD_SHIELD = itemTag("sediment_guard_shield");

    public static final TagKey<Item> GUARD_BOW = itemTag("sediment_guard_bow");

    public static final TagKey<Item> STABILIZER_DAGGER = itemTag("sediment_soul_stabilizer_dagger");

    private static final TagKey<DamageType> JEROTES_IS_WITHER = TagKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("jerotes", "is_wither"));

    private static final EntityDataAccessor<Integer> DATA_SHIELD_LEVEL =
            SynchedEntityData.defineId(AbstractSedimentServant.class, EntityDataSerializers.INT);

    private int angryTick;

    private boolean landNavigatorType = true;

    private int shieldCooldown;
    private boolean shieldCanUse = true;

    public AbstractSedimentServant(EntityType<? extends AbstractSedimentServant> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_SHIELD_LEVEL, 1);
    }

    public int getShieldLevel() {
        return entityData.get(DATA_SHIELD_LEVEL);
    }

    public void setShieldLevel(int level) {
        entityData.set(DATA_SHIELD_LEVEL, Math.max(1, level));
    }

    public boolean isAngryTick() {
        return this.angryTick > 0 && this.deathTime <= 0;
    }

    public int getAngryTick() {
        return this.angryTick;
    }

    public void setAngryTick(int ticks) {
        this.angryTick = Math.max(0, ticks);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            return;
        }
        if (deathTime == 0 && random.nextInt(900) == 1) {
            heal(3.0F);
        }
        if (angryTick > 0) {
            --angryTick;
            LivingEntity angryTarget = getTarget();
            if (level() instanceof ServerLevel serverLevel && isAngryTick()
                    && angryTarget != null && angryTarget.isAlive() && random.nextInt(20) < 10) {
                serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                        getRandomX(0.5D), getY() + getBbHeight() * 0.8D, getRandomZ(0.5D),
                        3, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (shieldCooldown > 0) {
            --shieldCooldown;
            shieldCanUse = false;
        } else {
            shieldCanUse = true;
        }
        tickAmphibious();
        tickShieldUse();
    }

    private void tickAmphibious() {
        if (isInWater() && landNavigatorType) {
            this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.2F, true);
            this.navigation = new AmphibiousPathNavigation(this, level());
            landNavigatorType = false;
        } else if (!isInWater() && !landNavigatorType) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            landNavigatorType = true;
        }
        if (tickCount % 10 != 0 || !isInWater() || !horizontalCollision) {
            return;
        }
        boolean shouldLeap = false;
        Path path = getNavigation().getPath();
        if (path != null && !getNavigation().isDone()) {
            BlockState targetState = level().getBlockState(path.getEndNode().asBlockPos());
            if (targetState.getFluidState().isEmpty()) {
                shouldLeap = true;
            }
        }
        LivingEntity target = getTarget();
        if (!shouldLeap && target != null && target.onGround()) {
            shouldLeap = true;
        }
        if (shouldLeap) {
            if (level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 18; i++) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH,
                            getRandomX(0.5D), getY() + 0.5D, getRandomZ(0.5D),
                            0, (random.nextFloat() - 0.5F) * 0.2D, 0.2D, (random.nextFloat() - 0.5F) * 0.2F, 0.5D);
                }
            }
            setDeltaMovement(getDeltaMovement().add(0.0D, 0.25D, 0.0D));
        }
    }

    private void tickShieldUse() {
        if (!canRaiseShield() || getTarget() == null || !getTarget().isAlive() || isCasting()
                || distanceToSqr(getTarget()) > 25.0D) {
            stopShieldUse();
            return;
        }
        if (getOffhandItem().getItem() instanceof ShieldItem) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
            return;
        }
        if (getOffhandItem().isEmpty() && attackAnim <= 0.0F
                && isBlockingTwoHandedWeapon(getMainHandItem())) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.MAIN_HAND);
            }
            return;
        }
        stopShieldUse();
    }

    private void stopShieldUse() {
        if (isUsingItem() && getUseItem().getUseAnimation() == UseAnim.BLOCK) {
            stopUsingItem();
        }
    }

    private static boolean isBlockingTwoHandedWeapon(ItemStack stack) {
        return !stack.isEmpty() && !(stack.getItem() instanceof ShieldItem)
                && stack.getUseAnimation() == UseAnim.BLOCK;
    }

    public boolean canRaiseShield() {
        return true;
    }

    public boolean holdsBow() {
        return getMainHandItem().getItem() instanceof BowItem;
    }

    public boolean holdsOtherRangedWeapon() {
        return !holdsBow()
                && (JerotesRangedWeaponGoal.isRangedWeapon(getMainHandItem())
                        || JerotesRangedWeaponGoal.isRangedWeapon(getOffhandItem()));
    }

    public boolean acceptsRangedWeaponGiving() {
        return true;
    }

    public <T extends AbstractSedimentServant & RangedAttackMob> void registerSedimentRangedGoals(T self) {
        boolean nativeBow = JerotesVillageCompat.isJerotesRangedGoalLoadable()
                && JvillageNativeGoals.attachBow(this.goalSelector, self);
        if (!nativeBow) {
            this.goalSelector.addGoal(1, new SedimentBowGoal<>(self, 1.0D, 20, 15.0F));
        }
        this.goalSelector.addGoal(1, new SedimentRangedWeaponGoal(this));
    }

    public static final class SedimentMeleeGoal extends MeleeAttackGoal {
        private final AbstractSedimentServant servant;

        SedimentMeleeGoal(AbstractSedimentServant servant, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(servant, speedModifier, followingTargetEvenIfNotSeen);
            this.servant = servant;
        }

        @Override
        public boolean canUse() {
            return !this.servant.holdsBow() && !this.servant.holdsOtherRangedWeapon() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.servant.holdsBow() && !this.servant.holdsOtherRangedWeapon()
                    && super.canContinueToUse();
        }
    }

    public static final class SedimentBowGoal<T extends AbstractSedimentServant & RangedAttackMob>
            extends RangedBowAttackGoal<T> {
        private final AbstractSedimentServant servant;

        SedimentBowGoal(T servant, double speedModifier, int attackInterval, float attackRadius) {
            super(servant, speedModifier, attackInterval, attackRadius);
            this.servant = servant;
        }

        @Override
        public boolean canUse() {
            return this.servant.holdsBow() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.servant.holdsBow() && super.canContinueToUse();
        }
    }

    public static final class SedimentRangedWeaponGoal extends JerotesRangedWeaponGoal {
        private final AbstractSedimentServant servant;

        SedimentRangedWeaponGoal(AbstractSedimentServant servant) {
            super(servant);
            this.servant = servant;
        }

        @Override
        public boolean canUse() {
            return !this.servant.holdsBow() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.servant.holdsBow() && super.canContinueToUse();
        }
    }

    public void shootAncientArrow(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        AncientSecondRounderArrowEntity arrow = new AncientSecondRounderArrowEntity(level(), this);
        arrow.setBaseDamage(arrow.getBaseDamage() + getSummonPotency());
        double dx = target.getX() - getX();
        double dy = target.getY(0.3333333333333333D) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, 1.6F, 12.0F - level().getDifficulty().getId() * 4.0F);
        playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        level().addFreshEntity(arrow);
    }

    @Override
    public void doPush(Entity entity) {
        if (!level().isClientSide() && deathTime == 0 && entity instanceof LivingEntity living
                && !(entity instanceof AbstractSedimentServant) && living.isAlive()
                && !(living instanceof Player player
                        && (player.isCreative() || player.isSpectator()))) {
            this.angryTick = 3600;
        }
        super.doPush(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide() && !isInvulnerableTo(source) && deathTime == 0) {
            AABB area = getBoundingBox().inflate(32.0D);
            for (AbstractSedimentServant other : level().getEntitiesOfClass(AbstractSedimentServant.class, area)) {
                if (other != null && other != this && sameMaster(other)) {
                    other.angryTick = 3600;
                }
            }
        }
        return super.hurt(source, amount);
    }

    private boolean sameMaster(AbstractSedimentServant other) {
        LivingEntity mine = getTrueOwner();
        LivingEntity theirs = other.getTrueOwner();
        return mine == theirs;
    }

    @Override
    public void blockUsingShield(LivingEntity attacker) {
        super.blockUsingShield(attacker);
        if (!level().isClientSide() && attacker != null) {
            if (isUsingItem() && getUseItem().getItem() instanceof ShieldItem) {
                playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + random.nextFloat() * 0.4F);
            } else if (isUsingItem()) {
                playSound(jerotesCoreSound("twohanded_block", SoundEvents.SHIELD_BLOCK),
                        1.0F, 0.8F + random.nextFloat() * 0.4F);
            }
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.canDisableShield(getUseItem(), this, attacker) || attacker.canDisableShield()) {
                disableShield();
            }
            disableShieldTry();
        }
    }

    public void disableShield() {
        if (random.nextFloat() <= 1.0F - (float) (getShieldLevel() - 1) * 0.2F) {
            this.shieldCooldown = Math.max(this.shieldCooldown, 100);
            this.shieldCanUse = false;
            stopUsingItem();
        }
    }

    public void disableShieldTry() {
        if (random.nextFloat() <= 0.15F - (float) (getShieldLevel() - 1) * 0.05F) {
            this.shieldCooldown = Math.max(this.shieldCooldown, 100);
            this.shieldCanUse = false;
            stopUsingItem();
        }
    }

    public void disableShieldBreak(int tick) {
        if (tick <= 0) {
            return;
        }
        this.shieldCooldown = Math.max(this.shieldCooldown, tick);
        this.shieldCanUse = false;
        stopUsingItem();
    }

    @Override
    public void hurtCurrentlyUsedShield(float amount) {
        if (!JerotesBreakRules.shieldCanBreak(level())) {
            return;
        }
        super.hurtCurrentlyUsedShield(amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide() && JerotesBreakRules.meleeCanBreak(level())) {
            ItemStack hand = getMainHandItem();
            if (!hand.isEmpty() && hand.isDamageableItem()) {
                hand.hurtAndBreak(1, this, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return hit;
    }

    @Override
    public void m_6472_(DamageSource source, float damage) {
        if (!JerotesBreakRules.armorCanBreak(level())) {
            return;
        }
        super.m_6472_(source, damage);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ShieldLevel", getShieldLevel());
        tag.putInt("AngryTick", this.angryTick);
        tag.putBoolean("LandNavigatorType", this.landNavigatorType);
        tag.putInt("ShieldCoolDown", this.shieldCooldown);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ShieldLevel")) {
            setShieldLevel(tag.getInt("ShieldLevel"));
        }
        this.angryTick = tag.getInt("AngryTick");
        this.landNavigatorType = !tag.contains("LandNavigatorType") || tag.getBoolean("LandNavigatorType");
        this.shieldCooldown = tag.getInt("ShieldCoolDown");
    }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, path));
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? 0.96F : 1.8F;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(JEROTES_IS_WITHER)
                || source.is(DamageTypes.WITHER)
                || source.is(DamageTypes.WITHER_SKULL)
                || super.isInvulnerableTo(source);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("resurrecter_ambient", SoundEvents.PILLAGER_AMBIENT);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("resurrecter_hurt", SoundEvents.PILLAGER_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("resurrecter_death", SoundEvents.PILLAGER_DEATH);
    }

    public EquipmentSlot replacementSlotFor(ItemStack held) {
        return null;
    }

    public static EquipmentSlot armorSlotFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Equipable equipable = Equipable.get(stack);
        if (equipable == null) {
            return null;
        }
        EquipmentSlot slot = equipable.getEquipmentSlot();
        return slot != null && slot.getType() == EquipmentSlot.Type.ARMOR ? slot : null;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        if (armorSlotFor(stack) != null) {
            return false;
        }
        return super.canHoldItem(stack);
    }

    public boolean isOwnedBy(Player player) {
        LivingEntity owner = getTrueOwner();
        for (int depth = 0; owner != null && depth < 16; depth++) {
            if (owner == player) {
                return true;
            }
            owner = owner instanceof AbstractJerotesServant servant ? servant.getTrueOwner()
                    : owner instanceof OwnableEntity ownable ? ownable.getOwner() : null;
        }
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        if (isOwnedBy(player) && isAlive()) {
            if (openServantInventory(player, hand)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
            ItemStack held = player.getItemInHand(hand);
            if (feedRottenFlesh(player, held)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
            if (GoetyStaffGuard.isGoetyStaff(held)) {
                return super.mobInteract(player, hand);
            }
            EquipmentSlot slot = replacementSlotFor(held);
            if (slot == null && armorSlotFor(held) != null) {
                slot = armorSlotFor(held);
            }
            if (slot == null && isGiveableWeapon(held)
                    && (acceptsRangedWeaponGiving()
                            || !JerotesRangedWeaponGoal.isRangedWeapon(held))) {
                slot = EquipmentSlot.MAINHAND;
            }
            if (slot != null) {
                if (!level().isClientSide()) {
                    ItemStack previous = getItemBySlot(slot).copy();
                    float previousDropChance = getEquipmentDropChance(slot);
                    setItemSlot(slot, held.copyWithCount(1));
                    setGuaranteedDrop(slot);
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                    RingServantGear.returnReplaced(player, previous, previousDropChance);
                    Equipable equipable = Equipable.get(held);
                    playSound(equipable == null
                                    ? SoundEvents.ARMOR_EQUIP_GENERIC : equipable.getEquipSound(),
                            1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    private boolean feedRottenFlesh(Player player, ItemStack held) {
        if (!held.is(net.minecraft.world.item.Items.ROTTEN_FLESH) || getHealth() >= getMaxHealth()) {
            return false;
        }
        if (!level().isClientSide()) {
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            heal(2.0F);
            playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            if (level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 7; ++i) {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            getRandomX(1.0D), getY() + 0.5D, getRandomZ(1.0D),
                            0, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D,
                            random.nextGaussian() * 0.02D, 0.5D);
                }
            }
        }
        return true;
    }
}
