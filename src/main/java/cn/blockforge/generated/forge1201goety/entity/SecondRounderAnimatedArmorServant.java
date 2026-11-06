package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.JerotesAllyHelpGoal;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.spell.AnimatedIronSummonSpell;
import cn.blockforge.generated.goetysupreme.compat.JerotesBreakRules;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.util.ServantArmorTrims;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class SecondRounderAnimatedArmorServant extends AbstractJerotesServant {

    private static final TagKey<DamageType> JEROTES_IS_PSYCHIC = TagKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("jerotes", "is_psychic"));

    private static final double FOLLOW_RANGE = 32.0D;

    private static final EntityDataAccessor<Boolean> DATA_SITTING =
            SynchedEntityData.defineId(SecondRounderAnimatedArmorServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_SHIELD_LEVEL =
            SynchedEntityData.defineId(SecondRounderAnimatedArmorServant.class, EntityDataSerializers.INT);

    private int shieldCooldown;

    public SecondRounderAnimatedArmorServant(EntityType<? extends AbstractJerotesServant> entityType, Level level) {
        super(entityType, level);
        setCanPickUpLoot(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.MAX_HEALTH, 54.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/second_rounder_animated_armor.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SECOND_ROUNDER_ANIMATED_ARMOR;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Mob.class, 6.0F));
        this.targetSelector.addGoal(1, new JerotesAllyHelpGoal(this));
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new JerotesRangedWeaponGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                double reach = (getBbWidth() + target.getBbWidth()) / 2.0D
                        + (Math.sqrt(2.04D) - 0.6D) + 0.5D;
                return reach * reach;
            }
        });
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_SITTING, false);
        entityData.define(DATA_SHIELD_LEVEL, 1);
    }

    public int getShieldLevel() {
        return entityData.get(DATA_SHIELD_LEVEL);
    }

    public void setShieldLevel(int level) {
        entityData.set(DATA_SHIELD_LEVEL, Math.max(1, level));
    }

    public boolean isSitting() {
        return entityData.get(DATA_SITTING);
    }

    private void setSitting(boolean sitting) {
        entityData.set(DATA_SITTING, sitting);
    }

    public boolean isInSittingPose() {
        return isSitting();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            return;
        }
        tickSittingPose();
        tickShieldUse();
        tickEmptyArmorDestroy();
        if (!isInSittingPose() && getLightLevelDependentMagicValue() > 0.5F) {
            this.noActionTime = (int) ((double) this.noActionTime + 1.1D);
        }
        if (shieldCooldown > 0) {
            --shieldCooldown;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide() && amount > 0.0F && isAlive() && !isInvulnerableTo(source)
                && shieldCooldown <= 0 && isUsingItem() && isShield(getUseItem())
                && canBlockWithShield(source)) {
            if (source.getDirectEntity() instanceof LivingEntity attacker && attacker != this) {
                rollShieldBreak(attacker);
            }
            hurtCurrentlyUsedShield(amount);
            playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            return false;
        }
        return super.hurt(source, amount);
    }

    private boolean canBlockWithShield(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) {
            return false;
        }
        if (source.getDirectEntity() instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) {
            return false;
        }
        Vec3 sourcePos = source.getSourcePosition();
        if (sourcePos == null) {
            return false;
        }
        Vec3 facing = this.getViewVector(1.0F);
        Vec3 toSource = sourcePos.vectorTo(this.position());
        toSource = new Vec3(toSource.x, 0.0D, toSource.z);
        if (toSource.lengthSqr() < 1.0E-6D) {
            return false;
        }
        Vec3 flatFacing = new Vec3(facing.x, 0.0D, facing.z);
        return toSource.normalize().dot(flatFacing) < 0.0D;
    }

    private void rollShieldBreak(LivingEntity attacker) {
        if (level().isClientSide()) {
            return;
        }
        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.canDisableShield(getUseItem(), this, attacker) || attacker.canDisableShield()) {
            if (random.nextFloat() <= 1.0F - (float) (getShieldLevel() - 1) * 0.2F) {
                shieldCooldown = Math.max(shieldCooldown, 100);
                stopUsingItem();
            }
        }
        if (random.nextFloat() <= 0.15F - (float) (getShieldLevel() - 1) * 0.05F) {
            shieldCooldown = Math.max(shieldCooldown, 100);
            stopUsingItem();
        }
    }

    @Override
    public void blockUsingShield(LivingEntity attacker) {
        super.blockUsingShield(attacker);
        if (attacker != null) {
            rollShieldBreak(attacker);
        }
    }

    @Override
    public void hurtCurrentlyUsedShield(float amount) {
        if (!JerotesBreakRules.shieldCanBreak(level())) {
            return;
        }
        super.hurtCurrentlyUsedShield(amount);
    }

    @Override
    public void m_6472_(DamageSource source, float damage) {
        if (!JerotesBreakRules.armorCanBreak(level())) {
            return;
        }
        super.m_6472_(source, damage);
    }

    private void tickShieldUse() {
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive() || shieldCooldown > 0 || attackAnim > 0.0F) {
            stopShieldUse();
            return;
        }
        boolean mainShield = getMainHandItem().getItem() instanceof ShieldItem;
        boolean offShield = getOffhandItem().getItem() instanceof ShieldItem;
        boolean holdingRanged = JerotesRangedWeaponGoal.isRangedWeapon(getMainHandItem());
        boolean canTakeUse = !isUsingItem() || getUseItem().getUseAnimation() == UseAnim.BLOCK;
        if (offShield && !holdingRanged && canTakeUse) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
            return;
        }
        if (mainShield && !holdingRanged && canTakeUse) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.MAIN_HAND);
            }
            return;
        }
        if (!holdingRanged && canTakeUse && getOffhandItem().isEmpty()
                && isBlockingTwoHandedWeapon(getMainHandItem())) {
            if (!isUsingItem()) {
                startUsingItem(InteractionHand.MAIN_HAND);
            }
            return;
        }
        stopShieldUse();
    }

    private static boolean isBlockingTwoHandedWeapon(ItemStack stack) {
        return !stack.isEmpty() && !(stack.getItem() instanceof ShieldItem)
                && stack.getUseAnimation() == UseAnim.BLOCK;
    }

    private void stopShieldUse() {
        if (isUsingItem() && getUseItem().getUseAnimation() == UseAnim.BLOCK) {
            stopUsingItem();
        }
    }

    public void warlordBreakShield(int ticks) {
        if (level().isClientSide()) {
            return;
        }
        shieldCooldown = Math.max(shieldCooldown, Math.max(0, ticks));
        stopShieldUse();
    }

    private void tickSittingPose() {
        boolean sitting = getTarget() == null
                && (isStaying() || isCommanded())
                && getNavigation().isDone();
        if (sitting != isSitting()) {
            setSitting(sitting);
            if (sitting && level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(jerotesParticle("target", ParticleTypes.CRIT),
                        getX(), getY() + 0.1D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (sitting) {
            getNavigation().stop();
        }
    }

    private void tickEmptyArmorDestroy() {
        if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()
                && getItemBySlot(EquipmentSlot.CHEST).isEmpty()
                && getItemBySlot(EquipmentSlot.LEGS).isEmpty()
                && getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
            hurt(damageSources().generic(), 5.0F);
            if (isAlive() && getHealth() < 100.0F) {
                if (!isSilent()) {
                    playSound(SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F);
                }
                remove(Entity.RemovalReason.KILLED);
            }
        }
    }

    private static final TagKey<net.minecraft.world.item.Item> MELEE_WEAPONS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("goety_supreme", "melee_weapons"));

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        LivingEntity owner = getTrueOwner();
        if (owner != null && owner != player) {
            return super.mobInteract(player, hand);
        }
        if (owner == player && !held.isEmpty() && isAlive()) {
            float heal = held.is(Items.IRON_NUGGET) ? 3.0F
                    : (held.is(Items.IRON_INGOT) ? getMaxHealth() * 0.5F : 0.0F);
            if (heal > 0.0F && ServantFeedHeal.feed(this, player, held, heal)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        if (held.isEmpty()) {
            return super.mobInteract(player, hand);
        }
        EquipmentSlot slot = equipSlotFor(held);
        if (slot == null || !acceptsEquipment(held, slot)) {
            return super.mobInteract(player, hand);
        }
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

    private static EquipmentSlot equipSlotFor(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor) {
            return armor.getEquipmentSlot();
        }
        if (isShield(stack)) {
            return EquipmentSlot.OFFHAND;
        }
        return EquipmentSlot.MAINHAND;
    }

    private boolean acceptsEquipment(ItemStack stack, EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> isAnimatedIron(stack, "animated_iron_helmet");
            case CHEST -> isAnimatedIron(stack, "animated_iron_chestplate");
            case LEGS -> isAnimatedIron(stack, "animated_iron_leggings");
            case FEET -> isAnimatedIron(stack, "animated_iron_boots");
            case MAINHAND -> stack.is(MELEE_WEAPONS) || isSwordLike(stack) || isBowOrCrossbow(stack);
            case OFFHAND -> isShield(stack);
            default -> false;
        };
    }

    private static boolean isSwordLike(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    private static boolean isAnimatedIron(ItemStack stack, String path) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && "jerotesvillage".equals(id.getNamespace()) && path.equals(id.getPath());
    }

    private static boolean isBowOrCrossbow(ItemStack stack) {
        if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem) {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String path = id.getPath();
        return path.endsWith("bow") || path.endsWith("crossbow");
    }

    private static boolean isShield(ItemStack stack) {
        if (stack.getItem() instanceof ShieldItem) {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && id.getPath().contains("shield");
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        if (RingSetBonuses.geoScopedOwner(this) != null) {
            setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
            setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
            ServantArmorTrims.applyWardDiamondTrim(this);
        } else {
            boolean tempered = RingSetBonuses.ringScopedOwner(this) != null;
            setItemSlot(EquipmentSlot.MAINHAND,
                    new ItemStack(tempered ? Items.IRON_AXE : Items.IRON_SWORD));
            equipJerotesOrFallback(EquipmentSlot.HEAD, "animated_iron_helmet", Items.IRON_HELMET);
            equipJerotesOrFallback(EquipmentSlot.CHEST, "animated_iron_chestplate", Items.IRON_CHESTPLATE);
            equipJerotesOrFallback(EquipmentSlot.LEGS, "animated_iron_leggings", Items.IRON_LEGGINGS);
            equipJerotesOrFallback(EquipmentSlot.FEET, "animated_iron_boots", Items.IRON_BOOTS);
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    private void equipJerotesOrFallback(EquipmentSlot slot, String jerotesPath,
            net.minecraft.world.item.Item fallback) {
        ItemStack stack = jerotesItem(jerotesPath);
        setItemSlot(slot, stack.isEmpty() ? new ItemStack(fallback) : stack);
    }

    public void equipGuaranteedShield() {
        setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(JEROTES_IS_PSYCHIC)
                || source.is(DamageTypeTags.IS_DROWNING)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect == null) {
            return false;
        }
        return effect.getEffect() != MobEffects.POISON
                && effect.getEffect() != MobEffects.MOVEMENT_SLOWDOWN
                && effect.getEffect() != MobEffects.WEAKNESS
                && effect.getEffect() != MobEffects.BLINDNESS
                && !isJerotesEffect(effect, "deadly_poison")
                && !isJerotesEffect(effect, "hold_mob")
                && !isJerotesEffect(effect, "paralyzed")
                && super.canBeAffected(effect);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("second_rounder_animated_armor_hurt", SoundEvents.ARMOR_STAND_HIT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("second_rounder_animated_armor_death", SoundEvents.ARMOR_STAND_BREAK);
    }
}
