package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.item.BlazingEmberItem;
import cn.blockforge.generated.goetysupreme.entity.EliteServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;

public class SerponFireApostle extends AbstractSerpon implements EliteServant {

    @Override
    public void dropAllDeathLoot(net.minecraft.world.damagesource.DamageSource source) {
        super.dropAllDeathLoot(source);
        dropBlazingEmber();
    }

    @Override
    public void onExecuted(Player player) {
        dropBlazingEmber();
        super.onExecuted(player);
    }

    private void dropBlazingEmber() {
        if (getTrueOwner() == null) {
            return;
        }
        ItemStack ember = new ItemStack(GeneratedMod.BLAZING_EMBER.get());
        try {
            ReviveServantItem.setOwnerName(getTrueOwner(), ember);
            ReviveServantItem.setSummon(this, ember);
        } catch (RuntimeException | LinkageError ignored) {
            ember = new ItemStack(GeneratedMod.BLAZING_EMBER.get());
        }
        OwnerBoundLoot.deliver(this, ember);
    }

    @Override
    public boolean acceptsEquipment(net.minecraft.world.item.ItemStack stack) {
        return isAnyJerotesItem(stack, "fire_apostle_helmet", "fire_apostle_chestplate",
                "fire_apostle_leggings", "fire_apostle_boots", "fire_apostle_sword");
    }

    @Override
    public void buildOffers(net.minecraft.world.item.trading.MerchantOffers offers) {
        ItemStack alloy = jerotesStack("serpon_alloy_ingot", 32);
        if (alloy.isEmpty()) {
            return;
        }
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                alloy.copy(), jerotesStack("fire_apostle_helmet", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                alloy.copy(), jerotesStack("fire_apostle_chestplate", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                alloy.copy(), jerotesStack("fire_apostle_leggings", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                alloy.copy(), jerotesStack("fire_apostle_boots", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                alloy.copy(), jerotesStack("fire_apostle_sword", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 16),
                jerotesStack("serpon_powerhouse_upgrade_smithing_template", 1), 12, 20, 0.0F),
                -1, false);
    }

    private static final double FOLLOW_RANGE = 32.0D;

    private static final float FIRE_ABSORPTION_PERCENTAGE = 60.0F;

    private static final int ATTACK_WINDUP_TICKS = 25;

    private static final EntityDataAccessor<Integer> DATA_ANIM_STATE =
            SynchedEntityData.defineId(SerponFireApostle.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ANIM_TICK =
            SynchedEntityData.defineId(SerponFireApostle.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attack1AnimationState = new AnimationState();
    public final AnimationState attack2AnimationState = new AnimationState();
    public final AnimationState attack3AnimationState = new AnimationState();
    public final AnimationState attack4AnimationState = new AnimationState();
    public final AnimationState attack5AnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();

    public float attackAnimProgress;

    public float thisTickRenderTime;
    public float lastTickRenderTime = 6.0F;

    private int attackTick;

    private int attackUse;

    private int animTick;

    public SerponFireApostle(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 100;
        setShieldLevel(3);
    }

    @Override
    public boolean supportsRangedWeapons() {
        return false;
    }

    @Override
    public boolean supportsSpellAttack() {
        return false;
    }

    public AABB getAttackBoundingBox() {
        Entity vehicle = getVehicle();
        AABB box;
        if (vehicle != null) {
            AABB vehicleBox = vehicle.getBoundingBox();
            AABB selfBox = getBoundingBox();
            box = new AABB(Math.min(selfBox.minX, vehicleBox.minX), selfBox.minY,
                    Math.min(selfBox.minZ, vehicleBox.minZ),
                    Math.max(selfBox.maxX, vehicleBox.maxX), selfBox.maxY,
                    Math.max(selfBox.maxZ, vehicleBox.maxZ));
        } else {
            box = getBoundingBox();
        }
        double horizontal = Math.sqrt(2.04D) - 0.6D;
        AABB inflated = box.inflate(horizontal, 0.0D, horizontal);
        if (!getMainHandItem().isEmpty()) {
            return inflated.inflate(0.85D, 0.85D, 0.85D);
        }
        return inflated.inflate(0.5D, 0.5D, 0.5D);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return getAttackBoundingBox().intersects(target.getBoundingBox());
    }

    @Override
    public double meleeReachSqr(LivingEntity target) {
        double extra = getMainHandItem().isEmpty() ? 0.5D : 0.85D;
        double reach = (getBbWidth() + target.getBbWidth()) / 2.0D
                + (Math.sqrt(2.04D) - 0.6D) + extra;
        return reach * reach;
    }

    @Override
    public boolean warnsBeforeExecution() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 0.55D)
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_fire_apostle.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_FIRE_APOSTLE;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponFireApostle.this.meleeReachSqr(target);
            }
        });
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "fire_apostle_helmet");
        equipJerotes(EquipmentSlot.CHEST, "fire_apostle_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "fire_apostle_leggings");
        equipJerotes(EquipmentSlot.FEET, "fire_apostle_boots");
        equipJerotes(EquipmentSlot.MAINHAND, "fire_apostle_sword");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    @Override
    public float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        castFireAbsorption(source, amount, FIRE_ABSORPTION_PERCENTAGE);
        return super.hurt(source, amount);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ANIM_STATE, 0);
        entityData.define(DATA_ANIM_TICK, 0);
    }

    public int getAnimState() {
        return entityData.get(DATA_ANIM_STATE);
    }

    public void setAnimState(int id) {
        entityData.set(DATA_ANIM_STATE, id);
    }

    private void setAnimTick(int ticks) {
        entityData.set(DATA_ANIM_TICK, Math.max(-1, ticks));
    }

    public int getAnimTick() {
        return entityData.get(DATA_ANIM_TICK);
    }

    private static int animationId(String name) {
        return switch (name) {
            case "attack1" -> 1;
            case "attack2" -> 2;
            case "attack3" -> 3;
            case "attack4" -> 4;
            case "attack5" -> 5;
            case "dead" -> 6;
            default -> 0;
        };
    }

    private void setAnimationState(String name) {
        setAnimTick(20);
        setAnimState(animationId(name));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!level().isClientSide() || !DATA_ANIM_STATE.equals(key)) {
            return;
        }
        AnimationState next = switch (getAnimState()) {
            case 1 -> attack1AnimationState;
            case 2 -> attack2AnimationState;
            case 3 -> attack3AnimationState;
            case 4 -> attack4AnimationState;
            case 5 -> attack5AnimationState;
            case 6 -> deadAnimationState;
            default -> null;
        };
        for (AnimationState state : new AnimationState[] {
                attack1AnimationState, attack2AnimationState, attack3AnimationState,
                attack4AnimationState, attack5AnimationState, deadAnimationState}) {
            if (state != next) {
                state.stop();
            }
        }
        if (next != null) {
            next.startIfStopped(tickCount);
        }
    }

    public float getAttackAnim() {
        return this.attackTick;
    }

    private void tickAnimation() {
        if (level().isClientSide()) {
            if (isAlive()) {
                idleAnimationState.startIfStopped(tickCount);
            }
            return;
        }
        setAnimTick(Math.max(-1, getAnimTick() - 1));
        if (getAnimTick() == 0) {
            setAnimState(0);
        }
    }

    @Override
    public void tickExtra() {
        if (level().isClientSide()) {
            this.lastTickRenderTime = this.thisTickRenderTime;
            this.thisTickRenderTime = 0.0F;
        }
        tickAnimation();
        if (attackTick > 0) {
            --attackTick;
            if (isAlive()) {
                if (attackUse != 5 && attackTick == 15) {
                    trueHurt();
                } else if (attackUse == 5 && attackTick == 13) {
                    trueHurt();
                }
            }
            if (attackTick > 0) {
                getNavigation().stop();
            }
            if (attackTick > 15 && getTarget() != null) {
                lookAt(getTarget(), 360.0F, 360.0F);
                getLookControl().setLookAt(getTarget(), 360.0F, 360.0F);
            }
            if (attackUse == 2 && attackTick <= 20 && attackTick > 15) {
                float yaw = getYRot() * ((float) Math.PI / 180.0F);
                float pitch = getXRot() * ((float) Math.PI / 180.0F);
                double dx = -Mth.sin(yaw) * Mth.cos(pitch);
                double dy = -Mth.sin(pitch);
                double dz = Mth.cos(yaw) * Mth.cos(pitch);
                double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
                if (length > 1.0E-4D) {
                    float accel = attackTick > 18 ? -0.05F : 0.15F;
                    double scale = accel / length * 2.0D;
                    setDeltaMovement(getDeltaMovement().add(dx * scale, onGround() ? dy * scale : 0.0D, dz * scale));
                }
            }
        }
    }

    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(0.0D, 0.0D, 0.0D);
    }

    @Override
    public void push(double x, double y, double z) {
        super.push(0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (attackTick > 0) {
            return false;
        }
        if (!level().isClientSide) {
            int roll = random.nextInt(100);
            if (roll > 85) {
                attackUse = 1;
            } else if (roll > 70) {
                attackUse = 2;
            } else if (roll > 40) {
                attackUse = 3;
            } else if (roll > 15) {
                attackUse = 4;
            } else {
                attackUse = 5;
            }
            attackTick = ATTACK_WINDUP_TICKS;
            setAnimationState("attack" + attackUse);
        }
        getNavigation().stop();
        setDeltaMovement(getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
        return true;
    }

    @Override
    public void tickDeath() {
        if (deathTime <= 0 && !level().isClientSide()) {
            setAnimationState("dead");
        }
        super.tickDeath();
    }

    private void trueHurt() {
        if (!isSilent()) {
            playSound(jerotesSound("serpon_attack", SoundEvents.PLAYER_ATTACK_STRONG), 2.0F, 1.0F);
            net.minecraft.sounds.SoundEvent extra = switch (attackUse) {
                case 1 -> SoundEvents.PLAYER_ATTACK_SWEEP;
                case 2, 4 -> SoundEvents.PLAYER_ATTACK_KNOCKBACK;
                case 3 -> SoundEvents.PLAYER_ATTACK_STRONG;
                default -> SoundEvents.PLAYER_ATTACK_CRIT;
            };
            playSound(extra, 2.0F, 1.0F);
        }
        float damageMulti = 1.0F;
        float knockbackMulti = 1.0F;
        float reach = 1.35F;
        if (attackUse == 1) {
            reach = 1.5F;
        } else if (attackUse == 2) {
            knockbackMulti = 1.5F;
        } else if (attackUse == 3) {
            damageMulti = 1.25F;
        } else if (attackUse == 4) {
            knockbackMulti = 1.15F;
            reach = 1.5F;
        } else {
            damageMulti = 1.25F;
            reach = 1.5F;
        }
        boolean hitAny = false;
        AABB area = getAttackBoundingBox().inflate(reach);
        for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (!canAttack(victim)) {
                continue;
            }
            victim.invulnerableTime = 0;
            victim.hurt(this.damageSources().mobAttack(this),
                    (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMulti);
            victim.setSecondsOnFire(16);
            victim.knockback(knockbackMulti, getX() - victim.getX(), getZ() - victim.getZ());
            hitAny = true;
            if (level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 15; ++i) {
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                            victim.getRandomX(1.0D), victim.getRandomY(), victim.getRandomZ(1.0D),
                            1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        if (hitAny) {
            swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            serponSweepParticles();
        }
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ApostleAttackTick", this.attackTick);
        tag.putInt("ApostleAttackUse", this.attackUse);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.attackTick = tag.getInt("ApostleAttackTick");
        this.attackUse = tag.getInt("ApostleAttackUse");
    }
}
