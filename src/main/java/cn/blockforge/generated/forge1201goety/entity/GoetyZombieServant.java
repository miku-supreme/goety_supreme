package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ai.NeutralZombieAttackGoal;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetyZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.compat.ServantTeleportHelper;

public class GoetyZombieServant extends ZombieServant implements RagingStarServant {

    private static final double BERSERK_MAX_HEALTH = 30.0D;
    private static final double BERSERK_ATTACK_BONUS = 3.0D;
    private static final double BERSERK_SPEED_MULTIPLIER = 0.1D;
    private static final double BERSERK_ARMOR_BONUS = 3.0D;

    private static final UUID BERSERK_HEALTH_UUID = UUID.fromString("a1f0d3c2-4b57-4e18-9c66-2d7b8e5a01f3");
    private static final UUID BERSERK_ATTACK_UUID = UUID.fromString("b2e1c4d3-5c68-4f29-8d77-3e8c9f6b12a4");
    private static final UUID BERSERK_SPEED_UUID = UUID.fromString("c3d2b5e4-6d79-4a3a-9e88-4f9d0a7c23b5");
    private static final UUID BERSERK_ARMOR_UUID = UUID.fromString("d4e3c6f5-7e8a-4b4b-8f99-5a0e1b8d34c6");

    private static final double STAR_MAX_HEALTH = 80.0D;
    private static final double STAR_ARMOR = 12.0D;
    private static final double STAR_ARMOR_TOUGHNESS = 6.0D;
    private static final double STAR_ATTACK_DAMAGE = 6.0D;
    private static final float STAR_SIZE_SCALE = 1.25F;

    private static final float BERSERK_SIZE_SCALE = 1.10F;
    private static final double STAR_REACH_BONUS = 1.0D;

    private static final double STAR_KNOCKBACK_RESISTANCE = 0.8D;

    private static final UUID STAR_HEALTH_UUID = UUID.fromString("f6a5e8b7-9a0c-4d6d-8b1b-7c2a3d0f56e8");
    private static final UUID STAR_OFFHAND_ATTACK_UUID = UUID.fromString("a7b6f9c8-0b1d-4e7e-9c2c-8d3b4e1a6709");
    private static final UUID STAR_KNOCKBACK_UUID = UUID.fromString("c3d5f7a9-2e4b-4c6d-9e8f-0a1b2c3d4e5f");

    private static final int ROAR_TOTAL = 700;
    private static final int ROAR_BUFF_TICK = 600;
    private static final int ROAR_SUMMON_TICK = 640;
    private static final double ROAR_TRIGGER_RANGE = 16.0D;

    private static final float ROAR_DAMAGE_PER_TICK = 0.1F;

    private static final int MAX_ROAR_MINIONS = 4;

    private static final EntityDataAccessor<Boolean> BERSERK =
            SynchedEntityData.defineId(GoetyZombieServant.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> STARRED =
            SynchedEntityData.defineId(GoetyZombieServant.class, EntityDataSerializers.BOOLEAN);

    private int roarTicks;

    public GoetyZombieServant(EntityType<? extends GoetyZombieServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return ZombieServant.setCustomAttributes();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BERSERK, false);
        entityData.define(STARRED, false);
    }

    @Override
    public void attackGoal() {
        goalSelector.addGoal(4, new StarredReachAttackGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        ServantTeleportHelper.tick(this);
        if (level().isClientSide()) {
            RagingStarSupport.tickStarParticles(this);
            return;
        }
        updateBerserkFromOwner();
        RagingStarSupport.tickStarProtection(this);
        tickRagingStar();
    }

    @Override
    public boolean isRagingStarred() {
        return entityData.hasItem(STARRED) && Boolean.TRUE.equals(entityData.get(STARRED));
    }

    @Override
    public void setRagingStarred(boolean starred) {
        if (starred == isRagingStarred()) {
            return;
        }
        entityData.set(STARRED, starred);
        if (level().isClientSide()) {
            return;
        }
        getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, starred);
        applyStarAttributes();
        updateOffhandWeaponDamage();
        if (starred) {
            refreshDimensions();
            setHealth(getMaxHealth());
        }
    }

    private void applyStarAttributes() {
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        if (health != null && health.getModifier(STAR_HEALTH_UUID) == null) {
            double delta = STAR_MAX_HEALTH - health.getBaseValue();
            if (Math.abs(delta) > 1.0E-4D) {
                health.addPermanentModifier(new AttributeModifier(STAR_HEALTH_UUID,
                        "Raging star health", delta, AttributeModifier.Operation.ADDITION));
            }
        }
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && Math.abs(attack.getBaseValue() - STAR_ATTACK_DAMAGE) > 1.0E-4D) {
            attack.setBaseValue(STAR_ATTACK_DAMAGE);
        }
        setBaseIfDifferent(Attributes.ARMOR, STAR_ARMOR);
        setBaseIfDifferent(Attributes.ARMOR_TOUGHNESS, STAR_ARMOR_TOUGHNESS);
        AttributeInstance knockback = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (knockback != null && knockback.getModifier(STAR_KNOCKBACK_UUID) == null) {
            knockback.addPermanentModifier(new AttributeModifier(STAR_KNOCKBACK_UUID,
                    "Raging star knockback resistance", STAR_KNOCKBACK_RESISTANCE,
                    AttributeModifier.Operation.ADDITION));
        }
    }

    private void setBaseIfDifferent(net.minecraft.world.entity.ai.attributes.Attribute attribute, double wanted) {
        AttributeInstance instance = getAttribute(attribute);
        if (instance != null && Math.abs(instance.getBaseValue() - wanted) > 1.0E-4D) {
            instance.setBaseValue(wanted);
        }
    }

    private void tickRagingStar() {
        if (!isRagingStarred()) {
            return;
        }
        if (tickCount % 20 == 0 && getHealth() < getMaxHealth()) {
            heal(1.0F);
        }
        if (tickCount % 20 == 1) {
            applyStarAttributes();
            updateOffhandWeaponDamage();
        }
        tickRoar();
    }

    private void tickRoar() {
        if (roarTicks > 0) {
            if (roarTicks > ROAR_BUFF_TICK) {
                getNavigation().stop();
                setXxa(0.0F);
                setZza(0.0F);
                setSprinting(false);
                setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
                setXRot(roarTicks > ROAR_SUMMON_TICK ? 35.0F : -50.0F);
                roarYellDamage();
            }
            if (roarTicks == ROAR_SUMMON_TICK) {
                playSound(GeneratedMod.ENGENDER_ZOMBIE_SPECIAL.get(), 4.0F, isBaby() ? 1.5F : 1.0F);
                summonRoarAllies();
            }
            if (roarTicks == ROAR_BUFF_TICK) {
                setXRot(0.0F);
                inspireAllies();
            }
            --roarTicks;
            return;
        }
        LivingEntity target = getTarget();
        if (target != null && target.isAlive()
                && distanceToSqr(target) <= ROAR_TRIGGER_RANGE * ROAR_TRIGGER_RANGE
                && hasLineOfSight(target)) {
            roarTicks = ROAR_TOTAL;
            playSound(SoundEvents.ZOMBIE_AMBIENT, 3.0F, isBaby() ? 0.9F : 0.6F);
        }
    }

    private void roarYellDamage() {
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(RagingStarSupport.ROAR_RADIUS))) {
            if (victim == this || !victim.isAlive() || RagingStarSupport.isFriendlyUnit(this, victim)) {
                continue;
            }
            victim.invulnerableTime = 0;
            victim.hurt(damageSources().magic(), ROAR_DAMAGE_PER_TICK);
        }
    }

    private void inspireAllies() {
        for (LivingEntity ally : level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(RagingStarSupport.ROAR_RADIUS))) {
            if (ally == this || !ally.isAlive() || !RagingStarSupport.isFriendlyUnit(this, ally)) {
                continue;
            }
            buffWithBannerEffects(ally);
        }
        buffWithBannerEffects(this);
        addEffect(new MobEffectInstance(GoetyEffects.SHIELDING.get(),
                RagingStarSupport.BUFF_DURATION, 1));
        addEffect(new MobEffectInstance(GoetyEffects.RALLYING.get(),
                RagingStarSupport.BUFF_DURATION, 0));
    }

    private static void buffWithBannerEffects(LivingEntity unit) {
        unit.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,
                RagingStarSupport.BUFF_DURATION, 0));
        unit.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                RagingStarSupport.BUFF_DURATION, 0));
        unit.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                RagingStarSupport.BUFF_DURATION, 0));
    }

    private void summonRoarAllies() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        int room = Math.max(0, MAX_ROAR_MINIONS - countMinions(server));
        int count = Math.min(2, room);
        for (int i = 0; i < count; ++i) {
            spawnMinion(server, getTarget());
        }
    }

    private void summonReinforcementOnHurt(LivingEntity attacker) {
        if (!(level() instanceof ServerLevel server) || countMinions(server) >= MAX_ROAR_MINIONS) {
            return;
        }
        spawnMinion(server, attacker);
    }

    private int countMinions(ServerLevel server) {
        return server.getEntitiesOfClass(GoetyZombieServant.class,
                getBoundingBox().inflate(32.0D), zombie -> zombie.getTrueOwner() == this).size();
    }

    private void spawnMinion(ServerLevel server, LivingEntity focus) {
        GoetyZombieServant minion = ModEntityTypes.GOETY_ZOMBIE_SERVANT.get().create(server);
        if (minion == null) {
            return;
        }
        double x = getX() + (getRandom().nextDouble() - 0.5D) * 3.0D;
        double z = getZ() + (getRandom().nextDouble() - 0.5D) * 3.0D;
        minion.moveTo(x, getY(), z, getRandom().nextFloat() * 360.0F, 0.0F);
        minion.setTrueOwner(this);
        minion.setNatural(false);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        minion.setHealth(minion.getMaxHealth());
        if (focus != null && focus != this && focus.isAlive()) {
            minion.setTarget(focus);
        }
        server.addFreshEntity(minion);
        GoetySummonFx.play(server, minion);
        server.sendParticles(ParticleTypes.POOF, minion.getX(), minion.getY(0.5D), minion.getZ(),
                12, 0.25D, 0.25D, 0.25D, 0.02D);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (isRagingStarred() && getTrueOwner() == player && player.isShiftKeyDown()
                && isOffhandWeapon(held)) {
            if (!level().isClientSide()) {
                ItemStack previous = getItemBySlot(EquipmentSlot.OFFHAND);
                setItemSlot(EquipmentSlot.OFFHAND, held.copyWithCount(1));
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                if (!previous.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(previous);
                }
                updateOffhandWeaponDamage();
                playSound(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    private static boolean isOffhandWeapon(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof TridentItem);
    }

    private void updateOffhandWeaponDamage() {
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack == null) {
            return;
        }
        if (attack.getModifier(STAR_OFFHAND_ATTACK_UUID) != null) {
            attack.removePermanentModifier(STAR_OFFHAND_ATTACK_UUID);
        }
        double bonus = isRagingStarred() ? offhandWeaponBonus() : 0.0D;
        if (bonus > 0.0D) {
            attack.addPermanentModifier(new AttributeModifier(STAR_OFFHAND_ATTACK_UUID,
                    "Raging star offhand weapon", bonus, AttributeModifier.Operation.ADDITION));
        }
    }

    private double offhandWeaponBonus() {
        double bonus = 0.0D;
        for (AttributeModifier modifier : getItemBySlot(EquipmentSlot.OFFHAND)
                .getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                bonus += modifier.getAmount();
            }
        }
        return bonus;
    }

    @Override
    public boolean isSunSensitive() {
        return !isRagingStarred() && super.isSunSensitive();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (isRagingStarred() && RagingStarSupport.isVanillaHarmful(effect)) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isRagingStarred() && amount > 0.0F) {
            amount *= RagingStarSupport.DAMAGE_MULTIPLIER;
            if (RagingStarSupport.isElementReducedSource(source)) {
                amount *= RagingStarSupport.ELEMENT_DAMAGE_MULTIPLIER;
            }
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt && isRagingStarred() && !level().isClientSide()) {
            LivingEntity focus = source.getEntity() instanceof LivingEntity attacker
                    ? attacker : getTarget();
            if (focus != null) {
                summonReinforcementOnHurt(focus);
            }
        }
        return hurt;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions base = super.getDimensions(pose);
        float scale = 1.0F;
        if (isRagingStarred()) {
            scale *= STAR_SIZE_SCALE;
        }
        if (hasBerserkTexture()) {
            scale *= BERSERK_SIZE_SCALE;
        }
        return scale == 1.0F ? base : base.scale(scale);
    }

    public static float starSizeScale() {
        return STAR_SIZE_SCALE;
    }

    public static float berserkSizeScale() {
        return BERSERK_SIZE_SCALE;
    }

    @Override
    public void tryKill(Player player) {
        if (isRagingStarred() && getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        super.tryKill(player);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean hitByPlayer) {
        super.dropCustomDeathLoot(source, looting, hitByPlayer);
        if (isRagingStarred()) {
            OwnerBoundLoot.deliver(this, new ItemStack(GeneratedMod.RAGING_STAR.get()));
        }
    }

    @Override
    public Component getName() {
        if (isRagingStarred() && !hasCustomName()) {
            return Component.translatable("entity.goety_supreme.corpse_dark_general");
        }
        return super.getName();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(RagingStarSupport.NBT_KEY, isRagingStarred());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        float savedHealth = tag.contains("Health") ? tag.getFloat("Health") : getMaxHealth();
        super.readAdditionalSaveData(tag);
        boolean starred = tag.getBoolean(RagingStarSupport.NBT_KEY);
        if (starred) {
            entityData.set(STARRED, true);
            getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, true);
            applyStarAttributes();
            updateOffhandWeaponDamage();
            setHealth(Math.min(savedHealth, getMaxHealth()));
        }
    }

    public boolean hasBerserkTexture() {
        return entityData.hasItem(BERSERK) && entityData.get(BERSERK);
    }

    private void updateBerserkFromOwner() {
        boolean shouldBerserk = MutantMonstersCompat.isLoaded()
                && getTrueOwner() instanceof MutantZombieServant mutantZombie
                && mutantZombie.isBerserk();
        if (shouldBerserk != hasBerserkTexture()) {
            entityData.set(BERSERK, shouldBerserk);
            refreshDimensions();
        }
        applyBerserkAttributes(shouldBerserk);
    }

    private void applyBerserkAttributes(boolean berserk) {
        AttributeInstance health = getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance speed = getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance armor = getAttribute(Attributes.ARMOR);
        if (!berserk) {
            removeModifier(health, BERSERK_HEALTH_UUID);
            removeModifier(attack, BERSERK_ATTACK_UUID);
            removeModifier(speed, BERSERK_SPEED_UUID);
            removeModifier(armor, BERSERK_ARMOR_UUID);
            return;
        }
        if (health != null && health.getModifier(BERSERK_HEALTH_UUID) == null) {
            double delta = BERSERK_MAX_HEALTH - health.getBaseValue();
            if (delta != 0.0D) {
                health.addPermanentModifier(new AttributeModifier(BERSERK_HEALTH_UUID,
                        "Berserk form health", delta, AttributeModifier.Operation.ADDITION));
            }
        }
        addModifier(attack, BERSERK_ATTACK_UUID, "Berserk form attack",
                BERSERK_ATTACK_BONUS, AttributeModifier.Operation.ADDITION);
        addModifier(speed, BERSERK_SPEED_UUID, "Berserk form speed",
                BERSERK_SPEED_MULTIPLIER, AttributeModifier.Operation.MULTIPLY_BASE);
        addModifier(armor, BERSERK_ARMOR_UUID, "Berserk form armor",
                BERSERK_ARMOR_BONUS, AttributeModifier.Operation.ADDITION);
    }

    private static void addModifier(AttributeInstance attribute, UUID id, String name, double amount,
            AttributeModifier.Operation operation) {
        if (attribute != null && attribute.getModifier(id) == null) {
            attribute.addPermanentModifier(new AttributeModifier(id, name, amount, operation));
        }
    }

    private static void removeModifier(AttributeInstance attribute, UUID id) {
        if (attribute != null && attribute.getModifier(id) != null) {
            attribute.removePermanentModifier(id);
        }
    }

    public void checkHostility() {
    }

    @Override
    public void setHostile(boolean hostile) {
    }

    @Override
    public boolean isHostile() {
        return false;
    }

    public static Class<? extends Summoned> servantBaseClass() {
        return ZombieServant.class;
    }

    public static final class StarredReachAttackGoal extends NeutralZombieAttackGoal {

        private final GoetyZombieServant zombie;

        public StarredReachAttackGoal(GoetyZombieServant zombie) {
            super(zombie, 1.0D, false);
            this.zombie = zombie;
        }

        @Override
        public double getAttackReachSqr(LivingEntity target) {
            double base = super.getAttackReachSqr(target);
            if (!zombie.isRagingStarred()) {
                return base;
            }
            double reach = Math.sqrt(base) + STAR_REACH_BONUS;
            return reach * reach;
        }
    }
}
