package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;

final class PlayerMobServantFood {

    private final PlayerMobServant mob;

    PlayerMobServantFood(PlayerMobServant mob) {
        this.mob = mob;
    }

    void initServantSurvival() {
        if (this.mob.level().isClientSide || this.mob.survivalInitialized) {
            return;
        }
        this.mob.survivalInitialized = true;
        this.mob.servantFood.setFoodLevel(20);
        this.mob.servantFood.setSaturation(4.0F);
        if (!this.mob.starterFoodGiven) {
            this.mob.starterFoodGiven = true;
            int count = Math.max(0, PlayerMobServantConfig.STARTER_BREAD_COUNT.get());
            ItemStack template = new ItemStack(Items.BREAD);
            template.getOrCreateTag().putBoolean(PlayerMobServant.NO_DROP_TAG, true);
            for (int i = 0; i < this.mob.servantStorage.getContainerSize() && count > 0; i++) {
                if (this.mob.servantStorage.getItem(i).isEmpty()) {
                    int put = Math.min(count, template.getMaxStackSize());
                    ItemStack bread = template.copyWithCount(put);
                    this.mob.servantStorage.setItem(i, bread);
                    count -= put;
                }
            }
        }
        this.mob.lastExhaustX = this.mob.getX();
        this.mob.lastExhaustZ = this.mob.getZ();
        this.mob.syncServantFoodData();
    }

    void tickServantSurvival() {
        if (this.mob.level().isClientSide) {
            return;
        }
        if (!this.mob.survivalInitialized) {
            this.initServantSurvival();
        }
        if (this.mob.eatRestartCooldown > 0) {
            this.mob.eatRestartCooldown--;
        }
        double dx = this.mob.getX() - this.mob.lastExhaustX;
        double dz = this.mob.getZ() - this.mob.lastExhaustZ;
        this.mob.lastExhaustX = this.mob.getX();
        this.mob.lastExhaustZ = this.mob.getZ();
        double moved = Math.sqrt(dx * dx + dz * dz);
        if (moved > 0.0D && this.mob.tickCount > 1 && !this.mob.isPassenger()
                && this.mob.getVehicle() == null) {
            float factor = this.mob.isSprinting() ? 0.1F : (this.mob.isSwimming() ? 0.017F : 0.01F);
            this.mob.servantFood.addExhaustion((float) moved * factor);
        }
        if (this.mob.servantFood.getExhaustionLevel() > 4.0F) {
            this.mob.servantFood.setExhaustion(this.mob.servantFood.getExhaustionLevel() - 4.0F);
            if (this.mob.servantFood.getSaturationLevel() > 0.0F) {
                this.mob.servantFood.setSaturation(
                        Math.max(this.mob.servantFood.getSaturationLevel() - 1.0F, 0.0F));
            } else if (this.mob.level().getDifficulty() != Difficulty.PEACEFUL) {
                this.mob.servantFood.setFoodLevel(
                        Math.max(this.mob.servantFood.getFoodLevel() - 1, 0));
            }
        }
        boolean naturalRegen = this.mob.level().getGameRules()
                .getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (naturalRegen && this.mob.servantFood.getSaturationLevel() > 0.0F
                && this.servantIsHurt() && this.mob.servantFood.getFoodLevel() >= 20) {
            if (this.mob.hungerRegenTick < 10) {
                ++this.mob.hungerRegenTick;
            }
            if (this.mob.hungerRegenTick >= 10) {
                float saturationUsed = Math.min(this.mob.servantFood.getSaturationLevel(), 6.0F);
                this.mob.heal(saturationUsed / 6.0F);
                this.mob.servantFood.addExhaustion(saturationUsed);
                this.mob.hungerRegenTick = 0;
            }
        } else if (naturalRegen && this.mob.servantFood.getFoodLevel() >= 18
                && this.servantIsHurt()) {
            if (this.mob.hungerRegenTick < 80) {
                ++this.mob.hungerRegenTick;
            }
            if (this.mob.hungerRegenTick >= 80) {
                this.mob.heal(1.0F);
                this.mob.servantFood.addExhaustion(6.0F);
                this.mob.hungerRegenTick = 0;
            }
        } else if (this.mob.hungerRegenTick > 0) {
            --this.mob.hungerRegenTick;
        }
        this.mob.syncServantFoodData();
    }

    boolean servantIsHurt() {
        return this.mob.getHealth() < this.mob.getMaxHealth();
    }

    double eatRangeSqr() {
        double range = Math.max(1.0D, PlayerMobServantConfig.EAT_RANGE.get());
        return range * range;
    }

    boolean isUsingFood() {
        return this.mob.isUsingItem() && this.mob.getUsedItemHand() == InteractionHand.MAIN_HAND
                && !this.mob.getUseItem().isEmpty() && this.mob.getUseItem().isEdible();
    }

    void keepEyesOnTargetWhileEating() {
        if (!this.mob.eatingBite && !this.isUsingFood()) {
            return;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()
                || !this.mob.isValidCombatTargetForGoal(target)) {
            return;
        }
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    private static final float FULL_GAP_TOLERANCE = 2.0F;
    private static final int FULL_HUNGRY_GAP = 10;
    private static final float STARVING_SATURATION = 2.0F;
    private static final int EAT_COOLDOWN_TICKS = 100;
    private static final double EAT_WALK_SPEED = 0.25D;
    private static final int EAT_WALK_REPATH_TICKS = 20;
    private static final double EAT_WALK_FOLLOW_STOP_SQR = 9.0D;

    public boolean canStartEatBite() {
        if (mob.level().isClientSide || !mob.isEffectiveAi() || mob.eatingBite || mob.eatRestartCooldown > 0) {
            mob.pendingPlayerMobEatMode = null;
            return false;
        }
        LivingEntity target = mob.getTarget();
        boolean engaged = target != null && target.isAlive();
        if (engaged && PlayerMobMeleeAttackGoal.gapDistanceSqr(mob, target) <= mob.meleeAttackReachSqr()) {
            mob.pendingPlayerMobEatMode = null;
            return false;
        }
        int slot = findBestFoodSlot();
        boolean needsFood = mob.servantFood.needsFood()
                || (servantIsHurt() && mob.servantFood.getSaturationLevel() < mob.servantFood.getFoodLevel());
        boolean normalWindow = !engaged || mob.distanceToSqr(target) > eatRangeSqr();
        if (normalWindow && needsFood && slot >= 0) {
            if (mob.hasCastableHeal()) {
                mob.pendingPlayerMobEatMode = null;
                return false;
            }
            mob.pendingPlayerMobEatMode = currentPlayerMobEatMode();
            return true;
        }
        if (mob.servantFood.getSaturationLevel() < STARVING_SATURATION
                && findBestFoodSlot(PlayerMobEatMode.HURT) >= 0) {
            if (mob.hasCastableHeal()) {
                mob.pendingPlayerMobEatMode = null;
                return false;
            }
            mob.pendingPlayerMobEatMode = PlayerMobEatMode.HURT;
            return true;
        }
        if (engaged && mob.distanceToSqr(target) <= prebuffRangeSqr() && slot >= 0
                && foodEffectWouldHelp(mob.servantStorage.getItem(slot).getFoodProperties(mob))) {
            mob.pendingPlayerMobEatMode = currentPlayerMobEatMode();
            return true;
        }
        mob.pendingPlayerMobEatMode = null;
        return false;
    }

    private double prebuffRangeSqr() {
        double range = Math.max(1.0D, PlayerMobServantConfig.PREBUFF_EAT_RANGE.get());
        return range * range;
    }

    private PlayerMobEatMode currentPlayerMobEatMode() {
        if (mob.fleeEatActive) {
            return PlayerMobEatMode.FLEE;
        }
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive() && mob.isValidCombatTargetForGoal(target)) {
            return PlayerMobEatMode.COMBAT_BUFF;
        }
        if (servantIsHurt()) {
            return mob.getHealth() >= mob.getMaxHealth() * 0.8F ? PlayerMobEatMode.HURT_HIGH : PlayerMobEatMode.HURT;
        }
        return PlayerMobEatMode.FULL;
    }

    public int findBestFoodSlot() {
        return findBestFoodSlot(currentPlayerMobEatMode());
    }

    private int findBestFoodSlot(PlayerMobEatMode mode) {
        int gap = Math.max(0, 20 - mob.servantFood.getFoodLevel());
        int best = -1;
        float bestScore = -Float.MAX_VALUE;
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            ItemStack stack = mob.servantStorage.getItem(i);
            if (stack.isEmpty() || !stack.isEdible()) {
                continue;
            }
            FoodProperties properties = stack.getFoodProperties(mob);
            if (properties == null) {
                continue;
            }
            boolean beneficial = hasBeneficialEffect(properties);
            boolean defensive = beneficial && hasDefensiveEffect(properties);
            boolean missingBuff = beneficial && foodEffectWouldHelp(properties);
            int nutrition = properties.getNutrition();
            float restore = nutrition * properties.getSaturationModifier() * 2.0F + nutrition;
            float score;
            switch (mode) {
                case FLEE -> {
                    if (defensive) {
                        score = 4000.0F + foodSaturationValue(properties);
                    } else if (!beneficial) {
                        score = 2000.0F + foodSaturationValue(properties);
                    } else {
                        score = 100.0F + restore;
                    }
                }
                case COMBAT_BUFF -> {
                    if (!missingBuff) {
                        continue;
                    }
                    score = 2500.0F + restore;
                }
                case HURT_HIGH -> {
                    if (!beneficial) {
                        score = 3000.0F + foodSaturationValue(properties);
                    } else if (missingBuff) {
                        score = 1000.0F + foodSaturationValue(properties);
                    } else {
                        continue;
                    }
                }
                case HURT -> {
                    score = (defensive ? 3000.0F : 0.0F) + (beneficial ? 900.0F : 0.0F) + restore;
                }
                case FULL -> {
                    if (beneficial) {
                        continue;
                    }
                    float value = foodSaturationValue(properties);
                    float diff = Math.abs((float) gap - value);
                    score = diff <= FULL_GAP_TOLERANCE ? 1000.0F - diff : -diff;
                }
                default -> {
                    continue;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                best = i;
            }
        }
        if (mode == PlayerMobEatMode.FULL && best >= 0 && bestScore < 500.0F && gap < FULL_HUNGRY_GAP) {
            return -1;
        }
        return best;
    }

    private static float foodSaturationValue(FoodProperties properties) {
        return properties.getNutrition() * properties.getSaturationModifier() * 2.0F;
    }

    public boolean hasFleeEatFood() {
        return findBestFoodSlot(PlayerMobEatMode.FLEE) >= 0;
    }

    private static boolean hasBeneficialEffect(FoodProperties properties) {
        for (Pair<MobEffectInstance, Float> pair : properties.getEffects()) {
            try {
                MobEffectInstance effect = pair.getFirst();
                if (effect != null && effect.getEffect().getCategory() == MobEffectCategory.BENEFICIAL) {
                    return true;
                }
            } catch (RuntimeException ignored) {
            }
        }
        return false;
    }

    private static boolean hasDefensiveEffect(FoodProperties properties) {
        for (Pair<MobEffectInstance, Float> pair : properties.getEffects()) {
            try {
                MobEffectInstance effect = pair.getFirst();
                if (effect == null) {
                    continue;
                }
                MobEffect type = effect.getEffect();
                if (type == MobEffects.REGENERATION || type == MobEffects.DAMAGE_RESISTANCE
                        || type == MobEffects.ABSORPTION || type == MobEffects.HEALTH_BOOST) {
                    return true;
                }
            } catch (RuntimeException ignored) {
            }
        }
        return false;
    }

    private boolean foodEffectWouldHelp(FoodProperties properties) {
        if (properties == null) {
            return false;
        }
        for (Pair<MobEffectInstance, Float> pair : properties.getEffects()) {
            try {
                MobEffectInstance effect = pair.getFirst();
                if (effect == null || effect.getEffect().getCategory() != MobEffectCategory.BENEFICIAL) {
                    continue;
                }
                MobEffectInstance current = mob.getEffect(effect.getEffect());
                if (current == null || current.getAmplifier() < effect.getAmplifier()) {
                    return true;
                }
            } catch (RuntimeException ignored) {
            }
        }
        return false;
    }

    public void startEatBite() {
        startEatBite(mob.pendingPlayerMobEatMode != null ? mob.pendingPlayerMobEatMode : currentPlayerMobEatMode());
    }

    public void startEatBite(PlayerMobEatMode mode) {
        if (mob.eatingBite || isEatCooldownActive()) {
            return;
        }
        int slot = findBestFoodSlot(mode);
        if (slot < 0) {
            mob.eatRestartCooldown = 60;
            return;
        }
        ItemStack food = mob.servantStorage.removeItem(slot, 1);
        if (food.isEmpty()) {
            mob.eatRestartCooldown = 60;
            return;
        }
        FoodProperties properties = food.getFoodProperties(mob);
        mob.eatPendingNutrition = properties == null ? 0 : properties.getNutrition();
        mob.eatPendingSaturationMod = properties == null ? 0.0F : properties.getSaturationModifier();
        if (mob.isUsingItem()) {
            mob.stopUsingItem();
        }
        mob.stopShieldGuard();
        mob.eatWeaponStash = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        mob.setItemSlot(EquipmentSlot.MAINHAND, food);
        mob.startUsingItem(InteractionHand.MAIN_HAND);
        mob.eatingBite = true;
        mob.eatWalkRepath = 0;
        mob.eatWalkDest = null;
    }

    public void tickEatWalk(@Nullable LivingEntity threat) {
        if (!mob.eatingBite || mob.level().isClientSide) {
            mob.eatWalkRepath = 0;
            mob.eatWalkDest = null;
            return;
        }
        if (mob.eatWalkRepath > 0) {
            mob.eatWalkRepath--;
        }
        if (mob.eatWalkRepath > 0 && mob.eatWalkDest != null && !mob.getNavigation().isDone()) {
            return;
        }
        mob.eatWalkRepath = EAT_WALK_REPATH_TICKS;
        mob.eatWalkDest = eatWalkDestination(threat);
        if (mob.eatWalkDest == null) {
            mob.getNavigation().stop();
            return;
        }
        mob.getNavigation().moveTo(mob.eatWalkDest.x, mob.eatWalkDest.y, mob.eatWalkDest.z, EAT_WALK_SPEED);
    }

    @Nullable
    private Vec3 eatWalkDestination(@Nullable LivingEntity threat) {
        if (threat != null && threat.isAlive()) {
            Vec3 away = mob.position().subtract(threat.position());
            away = new Vec3(away.x, 0.0D, away.z);
            if (away.lengthSqr() < 1.0E-4D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            }
            Vec3 candidate = mob.position().add(away.normalize().scale(6.0D));
            return ServantMineAvoidance.safeDestination(mob,
                    new Vec3(candidate.x, mob.getY(), candidate.z), 6.0D);
        }
        LivingEntity owner = mob.getTrueOwner();
        if (owner == null) {
            owner = mob.getMasterOwner();
        }
        if (owner == null || !owner.isAlive() || mob.distanceToSqr(owner) <= EAT_WALK_FOLLOW_STOP_SQR) {
            return null;
        }
        return ServantMineAvoidance.safeDestination(mob, owner.position(), 4.0D);
    }

    public void resumeEatBite() {
        ItemStack hand = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (hand.isEmpty() || !hand.isEdible()) {
            return;
        }
        if (mob.isUsingItem() && !isUsingFood()) {
            mob.stopUsingItem();
        }
        if (!mob.isUsingItem()) {
            mob.startUsingItem(InteractionHand.MAIN_HAND);
        }
    }

    public void finishOrResumeEatBite() {
        if (!mob.eatingBite) {
            return;
        }
        ItemStack hand = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (hand.isEmpty()) {
            completeEatBite();
        } else if (hand.isEdible()) {
            resumeEatBite();
        } else {
            mob.eatingBite = false;
            restoreEatWeapon();
            startEatCooldown();
        }
    }

    private void completeEatBite() {
        mob.eatingBite = false;
        if (mob.isUsingItem()) {
            mob.stopUsingItem();
        }
        mob.servantFood.eat(mob.eatPendingNutrition, mob.eatPendingSaturationMod);
        mob.syncServantFoodData();
        restoreEatWeapon();
        startEatCooldown();
    }

    private void startEatCooldown() {
        mob.eatRestartCooldown = Math.max(mob.eatRestartCooldown, EAT_COOLDOWN_TICKS);
    }

    private boolean isEatCooldownActive() {
        return mob.eatRestartCooldown > 0;
    }

    public void cancelEatBite() {
        if (!mob.eatingBite) {
            return;
        }
        mob.eatingBite = false;
        if (mob.isUsingItem()) {
            mob.stopUsingItem();
        }
        ItemStack hand = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!hand.isEmpty() && hand.isEdible()) {
            mob.addStackToStorage(hand.copy());
        }
        restoreEatWeapon();
        startEatCooldown();
    }

    void restoreEatWeapon() {
        mob.setItemSlot(EquipmentSlot.MAINHAND, mob.eatWeaponStash);
        mob.eatWeaponStash = ItemStack.EMPTY;
    }
}
