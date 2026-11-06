package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.api.items.IPersist;
import com.Polarice3.Goety.api.items.ISoulRepair;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.projectiles.ScytheSlash;
import com.Polarice3.Goety.common.entities.projectiles.VoidSlash;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.neutral.Wartling;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModTiers;
import com.Polarice3.Goety.common.items.equipment.BladeOfEnderItem;
import com.Polarice3.Goety.common.items.equipment.DarkScytheItem;
import com.Polarice3.Goety.common.items.equipment.DeathScytheItem;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.config.ItemConfig;
import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ItemHelper;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import com.mojang.datafixers.util.Pair;
import java.util.function.Supplier;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.extensions.IForgeItemStack;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;

import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobMeleeAttackGoal;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobEatMode;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantGoals;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;
import cn.blockforge.generated.goetysupreme.util.ServantMineAvoidance;

public final class PlayerMobServantGoals {
    public PlayerMobServantGoals() {}
    public static final class AdaptiveWeaponGoal extends PlayerMobMeleeAttackGoal {
        final PlayerMobServant servant;

        boolean jumpCritPending;
        int jumpCritHoldTicks;

        public static final int JUMP_CRIT_HOLD_LIMIT_TICKS = 30;

        public static final int JUMP_ATTACK_INTERVAL_TICKS = 25;
        int jumpAttackCooldown;

        public AdaptiveWeaponGoal(PlayerMobServant servant, double speed) {
            super(servant, speed, true);
            this.servant = servant;
        }

        @Override
        public boolean canUse() {
            return !servant.isPureWandCasterSetup()
                    && !(servant.getMainHandItem().getItem() instanceof BowItem)
                    && !(servant.getMainHandItem().getItem() instanceof CrossbowItem)
                    && !servant.isTridentRangedEngagement()
                    && !servant.isThrowEngagement()
                    && !servant.isEnderPearlEngagement()
                    && !holdsJerotesSpearLike(servant)
                    && servant.getTarget() != null
                    && servant.isValidCombatTargetForGoal(servant.getTarget())
                    && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = servant.getTarget();
            return !servant.isPureWandCasterSetup()
                    && !(servant.getMainHandItem().getItem() instanceof BowItem)
                    && !(servant.getMainHandItem().getItem() instanceof CrossbowItem)
                    && !servant.isTridentRangedEngagement()
                    && !servant.isThrowEngagement()
                    && !servant.isEnderPearlEngagement()
                    && !holdsJerotesSpearLike(servant)
                    && target != null
                    && target.isAlive()
                    && servant.isValidCombatTargetForGoal(target)
                    && super.canContinueToUse();
        }

        public static boolean holdsJerotesSpearLike(PlayerMobServant servant) {
            ItemStack main = servant.getMainHandItem();
            return (JerotesItems.isSpear(main) || JerotesItems.isPike(main))
                    && JerotesVillageCompat.isJerotesSpearGoalLoadable();
        }

        @Override
        public void start() {
            super.start();
            jumpCritPending = false;
            jumpCritHoldTicks = 0;
        }

        @Override
        public void stop() {
            super.stop();
            jumpCritPending = false;
            jumpCritHoldTicks = 0;
            servant.stopShieldGuard();
            servant.setCombatChaseSprint(false);
        }

        @Override
        public void tick() {
            if (jumpAttackCooldown > 0) {
                jumpAttackCooldown--;
            }
            super.tick();
            LivingEntity target = servant.getTarget();
            if (servant.canGuardWithShield(target)) {
                servant.startShieldGuard(target);
            } else {
                servant.stopShieldGuard();
            }
            servant.updateCombatCrouch(target);
            boolean approachSlow = servant.isCrouchGuarding()
                    || (servant.isDaggerSneaking() && target != null
                            && PlayerMobMeleeAttackGoal.gapDistanceSqr(servant, target)
                                    > servant.meleeAttackReachSqr());
            if (approachSlow) {
                servant.getNavigation().setSpeedModifier(
                        Math.max(0.05D, PlayerMobServantConfig.SHIELD_APPROACH_SPEED.get()));
            } else {
                servant.getNavigation().setSpeedModifier(1.0D);
            }
            boolean outOfMelee = target != null && target.isAlive() && !jumpCritPending
                    && PlayerMobMeleeAttackGoal.gapDistanceSqr(servant, target)
                    > servant.meleeAttackReachSqr();
            servant.setCombatChaseSprint(outOfMelee);
        }

        @Override
        public void checkAndPerformAttack(LivingEntity target, double distanceSqr) {
            boolean inMelee = distanceSqr <= getAttackReachSqr(target);
            if (jumpCritPending) {
                if (!inMelee && servant.onGround()) {
                    jumpCritPending = false;
                    return;
                }
                boolean falling = !servant.onGround() && servant.fallDistance > 0.0F;
                if (falling && inMelee) {
                    jumpCritPending = false;
                    performJumpHit(target);
                    return;
                }
                if (servant.onGround() || ++jumpCritHoldTicks > JUMP_CRIT_HOLD_LIMIT_TICKS) {
                    jumpCritPending = false;
                    if (inMelee) {
                        performJumpHit(target);
                    }
                }
                return;
            }
            if (inMelee && jumpAttackCooldown <= 0 && servant.shouldAttemptJumpAttack(target)) {
                jumpCritPending = true;
                jumpCritHoldTicks = 0;
                startJumpAttackCooldown();
                resetAttackCooldown();
                servant.jumpForCrit();
                return;
            }
            if (!inMelee || !isTimeToAttack()) {
                return;
            }
            performAttack(target);
        }

        public void startJumpAttackCooldown() {
            jumpAttackCooldown = JUMP_ATTACK_INTERVAL_TICKS;
        }

        public void performJumpHit(LivingEntity target) {
            servant.stopShieldGuard();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }

        public void performAttack(LivingEntity target) {
            resetAttackCooldown();
            servant.stopShieldGuard();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }
    }
    public static final class PlayerCommandPositionGoal extends Goal {
        final PlayerMobServant servant;
        final double speed;
        int pathDelay;

        public PlayerCommandPositionGoal(PlayerMobServant servant, double speed) {
            this.servant = servant;
            this.speed = speed;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return servant.getTarget() == null && servant.isCommanded() && !servant.isStaying()
                    && (servant.commandPos != null || servant.commandPosEntity != null);
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            pathDelay = 0;
        }

        @Override
        public void stop() {
            servant.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (--pathDelay > 0) {
                return;
            }
            if (servant.commandPosEntity != null && servant.commandPosEntity.isAlive()) {
                servant.getNavigation().moveTo(servant.commandPosEntity, speed);
            } else if (servant.commandPos != null) {
                servant.getNavigation().moveTo(servant.commandPos.getX() + 0.5D, servant.commandPos.getY(),
                        servant.commandPos.getZ() + 0.5D, speed);
            } else {
                servant.getNavigation().stop();
                return;
            }
            pathDelay = 10;
        }
    }
    public static final class PlayerMobTridentAttackGoal extends Goal {
        public static final double MAX_THROW_RANGE_SQR = 16.0D * 16.0D;

        public static final int AIM_WINDUP_TICKS = 10;
        final PlayerMobServant servant;
        int throwCooldown;
        int seeTime;
        int strafingTime = -1;
        boolean strafingClockwise;
        boolean strafingBackwards;

        public PlayerMobTridentAttackGoal(PlayerMobServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            return servant.isTridentRangedEngagement() && !servant.isThrowEngagement();
        }

        @Override
        public boolean canContinueToUse() {
            return servant.isTridentRangedEngagement() && !servant.isThrowEngagement();
        }

        @Override
        public void start() {
            throwCooldown = AIM_WINDUP_TICKS;
            seeTime = 0;
            strafingTime = -1;
            strafingClockwise = servant.getRandom().nextBoolean();
            strafingBackwards = false;
            servant.stopShieldGuard();
            servant.startUsingItem(InteractionHand.MAIN_HAND);
        }

        @Override
        public void stop() {
            if (servant.isUsingTridentAim()) {
                servant.stopUsingItem();
            }
            servant.stopShieldGuard();
            servant.getNavigation().stop();
            servant.setCombatChaseSprint(false);
            seeTime = 0;
            strafingTime = -1;
            strafingBackwards = false;
        }

        @Override
        public void tick() {
            LivingEntity target = servant.getTarget();
            if (target == null || !target.isAlive()) {
                return;
            }
            double distanceSqr = servant.distanceToSqr(target);
            boolean canSee = servant.hasLineOfSight(target);
            boolean sawTarget = seeTime > 0;
            if (canSee != sawTarget) {
                seeTime = 0;
            }
            seeTime += canSee ? 1 : -1;
            boolean withinThrow = distanceSqr <= MAX_THROW_RANGE_SQR && seeTime > 0;
            if (!withinThrow) {
                strafingTime = -1;
                servant.setCombatChaseSprint(true);
                servant.getNavigation().moveTo(target, 1.0D);
                servant.getLookControl().setLookAt(target, 10.0F, servant.getMaxHeadXRot());
                return;
            }
            servant.setCombatChaseSprint(false);
            servant.getNavigation().stop();
            if (strafingTime == -1) {
                if (seeTime >= 20) {
                    strafingTime = 0;
                }
            } else {
                strafingTime++;
                if (strafingTime >= 20) {
                    if (servant.getRandom().nextFloat() < 0.3F) {
                        strafingClockwise = !strafingClockwise;
                    }
                    if (servant.getRandom().nextFloat() < 0.3F) {
                        strafingBackwards = !strafingBackwards;
                    }
                    strafingTime = 0;
                }
            }
            if (strafingTime >= 0) {
                double meleeSqr = servant.tridentMeleeRangeSqr();
                if (distanceSqr < meleeSqr * 1.44D) {
                    strafingBackwards = true;
                } else if (distanceSqr > MAX_THROW_RANGE_SQR * 0.75D) {
                    strafingBackwards = false;
                }
                servant.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F,
                        strafingClockwise ? 0.5F : -0.5F);
                servant.lookAt(target, 30.0F, 30.0F);
            } else {
                servant.getLookControl().setLookAt(target, 10.0F, servant.getMaxHeadXRot());
            }
            if (!servant.isUsingTridentAim()) {
                servant.stopShieldGuard();
                servant.startUsingItem(InteractionHand.MAIN_HAND);
            }
            if (throwCooldown > 0) {
                throwCooldown--;
            } else if (SakuraGearCompat.isCrystalTrident(servant.getMainHandItem())) {
                if (servant.getTicksUsingItem() >= SakuraGearCompat.TRIDENT_CHARGE_TICKS) {
                    servant.releaseUsingItem();
                    servant.startUsingItem(InteractionHand.MAIN_HAND);
                    throwCooldown = Math.max(AIM_WINDUP_TICKS,
                            PlayerMobServantConfig.TRIDENT_THROW_INTERVAL.get());
                }
            } else {
                servant.throwTridentCopy(target);
                throwCooldown = Math.max(AIM_WINDUP_TICKS,
                        PlayerMobServantConfig.TRIDENT_THROW_INTERVAL.get());
            }
        }
    }
    public static final class PlayerMobFleeEatGoal extends Goal {

        public static final double ESCAPE_DISTANCE_SQR = 8.0D * 8.0D;

        public static final int MAX_FLEE_TICKS = 200;

        public static final int COOLDOWN_TICKS = 300;
        public static final int FLEE_REPATH_TICKS = 20;

        final PlayerMobServant servant;
        boolean eating;
        boolean done;
        int fleeTicks;
        int fleeRepath;

        int displacementSettle;

        int displacementDelay;
        @Nullable
        Vec3 fleePos;

        public PlayerMobFleeEatGoal(PlayerMobServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            if (servant.level().isClientSide || !servant.isEffectiveAi()
                    || servant.fleeEatCooldown > 0) {
                return false;
            }
            LivingEntity target = servant.getTarget();
            if (target == null || !target.isAlive()
                    || servant.getHealth() >= servant.getMaxHealth() * 0.5F) {
                return false;
            }
            if (servant.hasCastableHeal()) {
                return false;
            }
            if (servant.isCastingWand()) {
                return false;
            }
            return servant.hasFleeEatFood();
        }

        @Override
        public boolean canContinueToUse() {
            if (done) {
                return false;
            }
            if (servant.eatingBite) {
                LivingEntity target = servant.getTarget();
                return target == null || target.isAlive();
            }
            LivingEntity target = servant.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            return eating || (servant.getHealth() < servant.getMaxHealth() * 0.5F
                    && servant.hasFleeEatFood());
        }

        @Override
        public void start() {
            eating = false;
            done = false;
            fleeTicks = 0;
            fleeRepath = 0;
            fleePos = null;
            displacementSettle = 0;
            displacementDelay = 0;
            servant.fleeEatActive = true;
            servant.stopShieldGuard();
            servant.setCombatChaseSprint(true);
        }

        @Override
        public void stop() {
            servant.fleeEatActive = false;
            servant.setCombatChaseSprint(false);
            servant.getNavigation().stop();
            if (servant.eatingBite) {
                servant.cancelEatBite();
            }
            servant.fleeEatCooldown = COOLDOWN_TICKS;
            fleePos = null;
            eating = false;
            done = false;
            displacementSettle = 0;
            displacementDelay = 0;
        }

        @Override
        public void tick() {
            LivingEntity target = servant.getTarget();
            if (target != null) {
                servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (!eating && !servant.eatingBite && !done) {
                tickFlee(target);
                return;
            }
            tickEat();
        }

        public void tickFlee(LivingEntity target) {
            if (target == null) {
                done = true;
                return;
            }
            if (servant.distanceToSqr(target) >= ESCAPE_DISTANCE_SQR
                    || fleeTicks >= MAX_FLEE_TICKS) {
                eating = true;
                servant.getNavigation().stop();
                servant.setCombatChaseSprint(false);
                servant.startEatBite(PlayerMobEatMode.FLEE);
                return;
            }
            if (displacementSettle > 0) {
                displacementSettle--;
                servant.setCombatChaseSprint(true);
                return;
            }
            fleeTicks++;
            servant.setCombatChaseSprint(true);
            if (--displacementDelay <= 0) {
                Vec3 away = servant.position().subtract(target.position());
                away = new Vec3(away.x, 0.0D, away.z);
                if (away.lengthSqr() < 1.0E-4D) {
                    away = new Vec3(1.0D, 0.0D, 0.0D);
                }
                away = away.normalize();
                double distance = servant.distanceTo(target);
                double travel = Mth.clamp(14.0D - distance, 4.0D, 24.0D);
                int settle = PlayerMobWandSpellGoal.burstDisplacement(servant, away, travel);
                if (settle > 0) {
                    displacementSettle = settle;
                    displacementDelay = 40;
                    servant.getNavigation().stop();
                    servant.getMoveControl().strafe(0.0F, 0.0F);
                    return;
                }
                displacementDelay = 10;
            }
            if (fleePos == null || --fleeRepath <= 0) {
                fleePos = LandRandomPos.getPosAway(servant, 16, 7, target.position());
                fleeRepath = FLEE_REPATH_TICKS;
            }
            Vec3 dest = ServantMineAvoidance.safeDestination(servant, fleePos, 8.0D);
            if (dest != null) {
                servant.getNavigation().moveTo(dest.x, dest.y, dest.z, 1.2D);
                return;
            }
            Vec3 away = servant.position().subtract(target.position());
            if (away.lengthSqr() < 1.0E-4D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            }
            Vec3 dir = away.normalize().scale(8.0D);
            Vec3 fallback = ServantMineAvoidance.safeDestination(servant,
                    new Vec3(servant.getX() + dir.x, servant.getY(), servant.getZ() + dir.z), 8.0D);
            servant.getNavigation().moveTo(fallback.x, fallback.y, fallback.z, 1.2D);
        }

        public void tickEat() {
            if (servant.eatingBite) {
                servant.tickEatWalk(servant.getTarget());
                if (servant.isUsingItem()) {
                    if (!servant.isUsingFood()) {
                        servant.resumeEatBite();
                    }
                    return;
                }
                servant.finishOrResumeEatBite();
                return;
            }
            if (eating) {
                done = true;
            }
        }
    }
    public static final class PlayerMobEatGoal extends Goal {
        final PlayerMobServant servant;

        public PlayerMobEatGoal(PlayerMobServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            if (servant.isCastingWand() && !servant.eatingBite) {
                return false;
            }
            return servant.eatingBite || servant.canStartEatBite();
        }

        @Override
        public boolean canContinueToUse() {
            if (servant.eatingBite) {
                LivingEntity target = servant.getTarget();
                return target == null || !target.isAlive()
                        || servant.distanceToSqr(target) > 36.0D;
            }
            return servant.canStartEatBite();
        }

        @Override
        public void start() {
            servant.startEatBite();
        }

        @Override
        public void tick() {
            if (!servant.eatingBite) {
                servant.startEatBite();
                return;
            }
            servant.tickEatWalk(null);
            if (servant.isUsingItem()) {
                if (!servant.isUsingFood()) {
                    servant.resumeEatBite();
                }
                return;
            }
            servant.finishOrResumeEatBite();
        }

        @Override
        public void stop() {
            servant.cancelEatBite();
        }
    }
    public static final class PlayerMobPickupGoal extends Goal {
        public static final double SEARCH_RANGE = 14.0D;
        public static final double SEARCH_RANGE_SQR = SEARCH_RANGE * SEARCH_RANGE;
        public static final double PICKUP_REACH_SQR = 1.5D * 1.5D;
        public static final int GOAL_TIMEOUT_TICKS = 200;
        final PlayerMobServant servant;
        ItemEntity wanted;
        int searchDelay;
        int repathDelay;
        int timeout;

        public PlayerMobPickupGoal(PlayerMobServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!servant.level().isClientSide && servant.isEffectiveAi()
                    && Boolean.TRUE.equals(PlayerMobServantConfig.AUTO_PICKUP_ENABLED.get())
                    && servant.getTarget() == null && !servant.eatingBite && !servant.isUsingItem()
                    && servant.getNavigation().isDone()) {
                if (searchDelay > 0) {
                    searchDelay--;
                    return false;
                }
                ItemEntity found = findWanted();
                if (found == null) {
                    searchDelay = 20;
                    return false;
                }
                wanted = found;
                return true;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return servant.getTarget() == null && !servant.eatingBite && !servant.isUsingItem()
                    && timeout > 0 && wanted != null && !wanted.isRemoved()
                    && !wanted.getItem().isEmpty() && wantsItem(wanted.getItem());
        }

        @Override
        public void start() {
            if (wanted == null || wanted.isRemoved()) {
                wanted = findWanted();
            }
            timeout = GOAL_TIMEOUT_TICKS;
            repathDelay = 0;
        }

        @Override
        public void stop() {
            wanted = null;
            searchDelay = 30;
        }

        @Override
        public void tick() {
            if (searchDelay > 0) {
                searchDelay--;
            }
            timeout--;
            ItemEntity item = wanted;
            if (item == null || item.isRemoved() || item.getItem().isEmpty()) {
                wanted = searchDelay <= 0 ? findWanted() : null;
                if (wanted != null) {
                    timeout = GOAL_TIMEOUT_TICKS;
                }
                return;
            }
            double distanceSqr = servant.distanceToSqr(item);
            if (distanceSqr <= PICKUP_REACH_SQR) {
                pickUp(item);
                wanted = null;
                searchDelay = 20;
                return;
            }
            if (searchDelay > 0 || timeout <= 0) {
                return;
            }
            if (repathDelay-- <= 0 || servant.getNavigation().isDone()) {
                servant.getNavigation().moveTo(item, 1.0D);
                repathDelay = 10;
            }
        }

        public ItemEntity findWanted() {
            List<ItemEntity> items = servant.level().getEntitiesOfClass(ItemEntity.class,
                    servant.getBoundingBox().inflate(SEARCH_RANGE, SEARCH_RANGE * 0.6D, SEARCH_RANGE),
                    item -> !item.isRemoved() && item.getAge() > 10 && wantsItem(item.getItem()));
            ItemEntity bestUpgrade = null;
            double bestUpgradeDistance = Double.MAX_VALUE;
            ItemEntity bestAny = null;
            double bestAnyDistance = Double.MAX_VALUE;
            for (ItemEntity item : items) {
                double distance = servant.distanceToSqr(item);
                if (distance > SEARCH_RANGE_SQR) {
                    continue;
                }
                if (isUpgradeOrEmptySlotFill(item.getItem())
                        && distance < bestUpgradeDistance) {
                    bestUpgrade = item;
                    bestUpgradeDistance = distance;
                }
                if (distance < bestAnyDistance) {
                    bestAny = item;
                    bestAnyDistance = distance;
                }
            }
            return bestUpgrade != null ? bestUpgrade : bestAny;
        }

        public boolean isUpgradeOrEmptySlotFill(ItemStack stack) {
            EquipmentSlot armorSlot = armorSlotFor(stack);
            if (armorSlot != null) {
                ItemStack current = servant.getItemBySlot(armorSlot);
                return current.isEmpty()
                        || armorScore(stack, armorSlot) > armorScore(current, armorSlot) + 0.01D;
            }
            int weaponClass = weaponClassOf(stack);
            if (weaponClass >= 0) {
                if (servant.isWandCasterSetup()) {
                    return false;
                }
                ItemStack current = servant.getMainHandItem();
                if (current.isEmpty()) {
                    return true;
                }
                return weaponClassOf(current) == weaponClass
                        && weaponScore(stack) > weaponScore(current) + 0.01D;
            }
            return false;
        }

        public boolean wantsItem(ItemStack stack) {
            if (stack.isEmpty()) {
                return false;
            }
            if (stack.isEdible() && stack.getFoodProperties(servant) != null) {
                return servant.storageRoomFor(stack) > 0;
            }
            EquipmentSlot armorSlot = armorSlotFor(stack);
            if (armorSlot != null) {
                ItemStack current = servant.getItemBySlot(armorSlot);
                return current.isEmpty()
                        || armorScore(stack, armorSlot) > armorScore(current, armorSlot) + 0.01D
                        || servant.storageRoomFor(stack) > 0;
            }
            int weaponClass = weaponClassOf(stack);
            if (weaponClass >= 0) {
                ItemStack current = servant.getMainHandItem();
                if (current.isEmpty()) {
                    return true;
                }
                boolean sameClassUpgrade = weaponClassOf(current) == weaponClass
                        && weaponScore(stack) > weaponScore(current) + 0.01D;
                return sameClassUpgrade || servant.storageRoomFor(stack) > 0;
            }
            return false;
        }

        public void pickUp(ItemEntity item) {
            ItemStack stack = item.getItem();
            EquipmentSlot armorSlot = armorSlotFor(stack);
            EquipmentSlot gearSlot = armorSlot != null ? armorSlot
                    : (weaponClassOf(stack) >= 0 ? EquipmentSlot.MAINHAND : null);
            boolean equipDirectly = gearSlot != null && isUpgradeOrEmptySlotFill(stack);
            if (equipDirectly) {
                ItemStack current = servant.getItemBySlot(gearSlot);
                ItemStack one = stack.split(1);
                servant.setItemSlot(gearSlot, one);
                servant.markEquipDropOnDeath(gearSlot);
                if (!current.isEmpty() && !RingServantGear.isRingGear(current)) {
                    servant.spawnAtLocation(current.copy(), 0.0F);
                }
            } else {
                int transferable = Math.min(stack.getCount(), servant.storageRoomFor(stack));
                if (transferable <= 0) {
                    searchDelay = 100;
                    return;
                }
                ItemStack batch = stack.split(transferable);
                ItemStack remainder = batch.copy();
                if (!servant.addStackToStorage(batch)) {
                    int notStored = batch.getCount();
                    if (notStored > 0) {
                        stack.grow(notStored);
                    }
                    searchDelay = 100;
                }
                remainder = null;
            }
            if (stack.isEmpty()) {
                item.discard();
            } else {
                item.setItem(stack);
            }
            servant.playSound(SoundEvents.ITEM_PICKUP, 0.3F,
                    (servant.getRandom().nextFloat() - servant.getRandom().nextFloat()) * 0.7F + 1.0F);
        }

        public static EquipmentSlot armorSlotFor(ItemStack stack) {
            Equipable equipable = Equipable.get(stack);
            if (equipable != null && equipable.getEquipmentSlot().getType() == EquipmentSlot.Type.ARMOR) {
                return equipable.getEquipmentSlot();
            }
            return null;
        }

        public static int weaponClassOf(ItemStack stack) {
            if (stack.isEmpty()) {
                return -1;
            }
            Item item = stack.getItem();
            if (item instanceof BowItem) {
                return 1;
            }
            if (item instanceof CrossbowItem) {
                return 2;
            }
            if (PlayerMobServant.isTridentLike(stack)) {
                return 3;
            }
            return PlayerMobServant.isMeleeWeapon(stack) ? 0 : -1;
        }

        public static double weaponScore(ItemStack stack) {
            double damage = 0.0D;
            for (AttributeModifier modifier
                    : stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE)) {
                if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                    damage += modifier.getAmount();
                }
            }
            return damage + enchantTotal(stack);
        }

        public static double armorScore(ItemStack stack, EquipmentSlot slot) {
            double defense = stack.getItem() instanceof ArmorItem armor
                    ? armor.getDefense() : 0.0D;
            return defense + enchantTotal(stack) * 0.5D;
        }

        public static double enchantTotal(ItemStack stack) {
            double total = 0.0D;
            for (Integer level : stack.getAllEnchantments().values()) {
                total += level == null ? 0.0D : level;
            }
            return total;
        }
    }
}
