package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.IronTeethMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.model.IronTeethServantModel;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class IronTeethServant extends AbstractJerotesServant {

    private static final EntityDataAccessor<Integer> DATA_ATTACK_TICK =
            SynchedEntityData.defineId(IronTeethServant.class, EntityDataSerializers.INT);

    private static final TagKey<net.minecraft.world.item.Item> IRON_INGOT_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/iron"));

    public IronTeethServant(EntityType<? extends IronTeethServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/iron_teeth.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.IRON_TEETH;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new IronTeethMeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    public void equipDefaultGear(net.minecraft.util.RandomSource random) {
        RingSetBonuses.applyIronTeethGeoHealth(this);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK_TICK, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            setAttackTick(Math.max(-50, getAttackTick() - 1));
        }
    }

    public int getAttackTick() {
        return this.entityData.get(DATA_ATTACK_TICK);
    }

    public void setAttackTick(int tick) {
        this.entityData.set(DATA_ATTACK_TICK, tick);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (getAttackTick() > -50) {
            return false;
        }
        if (!this.level().isClientSide) {
            setAttackTick(10);
        }
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            if (!this.level().isClientSide) {
                playSound(jerotesSound("iron_teeth_attack", SoundEvents.IRON_GOLEM_ATTACK), 1.0F, 1.0F);
                if (target instanceof LivingEntity living) {
                    MobEffect bleeding = ForgeRegistries.MOB_EFFECTS.getValue(
                            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "bleeding"));
                    if (bleeding != null) {
                        living.addEffect(new MobEffectInstance(bleeding, 60, 0, false, false), this);
                    }
                }
            }
        }
        return hit;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackTick", getAttackTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAttackTick(tag.getInt("AttackTick"));
    }

    private static final float IRON_NUGGET_HEAL = 2.0F;

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean nugget = stack.is(Items.IRON_NUGGET);
        boolean ingot = stack.is(IRON_INGOT_TAG);
        if ((nugget || ingot) && this.getHealth() < this.getMaxHealth()) {
            if (!this.level().isClientSide) {
                ServantFeedHeal.feed(this, player, stack, ingot ? this.getMaxHealth() : IRON_NUGGET_HEAL);
                this.playSound(jerotesCoreSound("repair_mob", SoundEvents.IRON_GOLEM_REPAIR), 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL)
                || source.is(DamageTypeTags.IS_DROWNING)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || super.isInvulnerableTo(source);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || isJerotesEffect(effect, "deadly_poison")) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.25F;
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type == ForgeMod.WATER_TYPE.get() ? false : super.canDrownInFluidType(type);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return jerotesSound("iron_teeth_hurt", SoundEvents.IRON_GOLEM_HURT);
    }

    @Override
    public SoundEvent getDeathSound() {
        return jerotesSound("iron_teeth_death", SoundEvents.IRON_GOLEM_DEATH);
    }
}
