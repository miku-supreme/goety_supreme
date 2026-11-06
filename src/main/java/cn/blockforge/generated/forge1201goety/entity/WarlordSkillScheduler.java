package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.common.entities.util.FirePillar;
import com.Polarice3.Goety.common.entities.util.ModFallingBlock;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import cn.blockforge.generated.goetysupreme.util.RagingStarDamageGuard;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.renderer.WarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyWarlordHellfirePillarMixin;

final class WarlordSkillScheduler {

    private WarlordSkillScheduler() {
    }

    static void tickSkill(AbstractWitherWarlord warlord) {
        warlord.trackCombatIdle();
        warlord.rollSkillChances();
        warlord.tickForceTimers();
        int skill = warlord.getSkill();
        if (skill != AbstractWitherWarlord.SKILL_NONE) {
            warlord.skillTick++;
            if (warlord.skillTick >= AbstractWitherWarlord.SKILL_ABSOLUTE_TIMEOUT_TICKS) {
                warlord.endSkill(60);
                return;
            }
            switch (skill) {
                case AbstractWitherWarlord.SKILL_SLASH -> warlord.tickSlash();
                case AbstractWitherWarlord.SKILL_BACKSTEP -> warlord.tickBackstep();
                case AbstractWitherWarlord.SKILL_INTOLERABLE -> warlord.tickIntolerable();
                case AbstractWitherWarlord.SKILL_ELITE -> warlord.tickElite();
                case AbstractWitherWarlord.SKILL_CHARGE -> warlord.tickCharge();
                case AbstractWitherWarlord.SKILL_BLAZING -> warlord.tickBlazing();
                case AbstractWitherWarlord.SKILL_HELLFIRE_SLASH -> warlord.tickHellfireSlash();
                case AbstractWitherWarlord.SKILL_GREAT_LEAP -> warlord.tickGreatLeap();
                case AbstractWitherWarlord.SKILL_CATACLYSM -> warlord.tickCataclysm();
                default -> warlord.endSkill(40);
            }
            return;
        }
        if (warlord.isDrawAnimating()) {
            return;
        }
        if (warlord.skillActionGap > 0) {
            return;
        }
        if (warlord.forcedBackstep && warlord.backstepCooldown <= 0) {
            warlord.forcedBackstep = false;
            warlord.beginSkill(AbstractWitherWarlord.SKILL_BACKSTEP);
            return;
        }
        if (warlord.forcedBlock && warlord.intolerableCooldown <= 0) {
            warlord.forcedBlock = false;
            warlord.beginSkill(AbstractWitherWarlord.SKILL_INTOLERABLE);
            return;
        }
        if (warlord.eliteForceTicks == 0 && warlord.eliteCooldown <= 0) {
            LivingEntity forced = warlord.getTarget();
            if (forced != null && forced.isAlive()) {
                warlord.beginSkill(AbstractWitherWarlord.SKILL_ELITE);
                return;
            }
        }
        if (warlord.chargeCooldown <= 0 && warlord.tryTriggerCharge()) {
            return;
        }
        if (warlord.hellfireCooldown <= 0 && warlord.targetWithinRange(AbstractWitherWarlord.HELLFIRE_RANGE)
                && (warlord.queuedHellfire || warlord.hellfireIdleTicks >= AbstractWitherWarlord.HELLFIRE_FORCE_INTERVAL_TICKS)) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_HELLFIRE_SLASH);
            return;
        }
        if (warlord.slashIdleTicks >= AbstractWitherWarlord.SLASH_FORCE_INTERVAL_TICKS
                && warlord.targetInSlashRange()) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_SLASH);
            return;
        }
        LivingEntity leapCandidate = warlord.getTarget();
        if (leapCandidate != null && leapCandidate.isAlive()
                && warlord.greatLeapCooldown <= 0
                && warlord.distanceTo(leapCandidate) > AbstractWitherWarlord.GREAT_LEAP_MIN_DISTANCE
                && (warlord.leapIdleTicks >= AbstractWitherWarlord.GREAT_LEAP_FORCE_INTERVAL_TICKS
                        || warlord.getRandom().nextInt(AbstractWitherWarlord.GREAT_LEAP_CHANCE) == 0)) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_GREAT_LEAP);
            return;
        }
        if (warlord.queuedBackstep && warlord.backstepCooldown <= 0
                && warlord.targetWithinRange(AbstractWitherWarlord.BACKSTEP_TRIGGER_RANGE)) {
            warlord.queuedBackstep = false;
            warlord.beginSkill(AbstractWitherWarlord.SKILL_BACKSTEP);
            return;
        }
        int picked = warlord.pickWeightedSkill();
        if (picked != AbstractWitherWarlord.SKILL_NONE) {
            warlord.beginSkill(picked);
            return;
        }
        warlord.runCombatWatchdog();
    }

    static void trackCombatIdle(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        if (warlord.getSkill() != AbstractWitherWarlord.SKILL_NONE || target == null || !target.isAlive()
                || warlord.isDrawAnimating() || warlord.isSheathAnimating()) {
            warlord.combatIdleTicks = 0;
            return;
        }
        if (warlord.combatIdleTicks < AbstractWitherWarlord.COMBAT_IDLE_WATCHDOG_TICKS) {
            warlord.combatIdleTicks++;
        }
    }

    static void runCombatWatchdog(AbstractWitherWarlord warlord) {
        if (warlord.combatIdleTicks < AbstractWitherWarlord.COMBAT_IDLE_WATCHDOG_TICKS) {
            return;
        }
        LivingEntity target = warlord.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double dist = warlord.distanceTo(target);
        if (dist > AbstractWitherWarlord.COMBAT_ENGAGE_RANGE) {
            return;
        }
        if (warlord.slashCooldown <= 0 && warlord.targetInSlashRange()) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_SLASH);
            return;
        }
        if (dist > AbstractWitherWarlord.GREAT_LEAP_MIN_DISTANCE && warlord.greatLeapCooldown <= 0) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_GREAT_LEAP);
            return;
        }
        if (warlord.chargeCooldown <= 0) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_CHARGE);
            return;
        }
        if (dist <= 12.0D && warlord.hellfireCooldown <= 0) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_HELLFIRE_SLASH);
            return;
        }
        if (warlord.eliteCooldown <= 0) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_ELITE);
        }
    }

    static void rollSkillChances(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double dist = warlord.distanceTo(target);
        if (!warlord.queuedBackstep && dist <= AbstractWitherWarlord.BACKSTEP_TRIGGER_RANGE
                && warlord.getRandom().nextInt(AbstractWitherWarlord.BACKSTEP_CHANCE) == 0) {
            warlord.queuedBackstep = true;
        }
        if (!warlord.queuedHellfire && warlord.hellfireCooldown <= 0 && dist <= AbstractWitherWarlord.HELLFIRE_RANGE
                && warlord.getRandom().nextInt(AbstractWitherWarlord.HELLFIRE_CHANCE) == 0) {
            warlord.queuedHellfire = true;
        }
    }

    static void tickForceTimers(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double dist = warlord.distanceTo(target);
        if (warlord.slashIdleTicks < AbstractWitherWarlord.SLASH_FORCE_INTERVAL_TICKS && dist <= AbstractWitherWarlord.COMBAT_ENGAGE_RANGE) {
            warlord.slashIdleTicks++;
        }
        if (warlord.hellfireIdleTicks < AbstractWitherWarlord.HELLFIRE_FORCE_INTERVAL_TICKS && dist <= AbstractWitherWarlord.COMBAT_ENGAGE_RANGE) {
            warlord.hellfireIdleTicks++;
        }
        if (warlord.leapIdleTicks < AbstractWitherWarlord.GREAT_LEAP_FORCE_INTERVAL_TICKS && dist > AbstractWitherWarlord.GREAT_LEAP_MIN_DISTANCE) {
            warlord.leapIdleTicks++;
        }
    }

    static boolean targetWithinRange(AbstractWitherWarlord warlord, double range) {
        LivingEntity target = warlord.getTarget();
        return target != null && target.isAlive() && warlord.distanceTo(target) <= range;
    }

    static boolean targetInSlashRange(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        return target != null && target.isAlive()
                && warlord.distanceTo(target) <= AbstractWitherWarlord.SLASH_TRIGGER_RANGE
                && warlord.hasLineOfSight(target);
    }

    static void tickBladeState(AbstractWitherWarlord warlord) {
        int state = warlord.bladeState();
        LivingEntity target = warlord.getTarget();
        boolean hasTarget = target != null && target.isAlive();
        if (!hasTarget) {
            warlord.queuedBackstep = false;
            warlord.queuedHellfire = false;
            warlord.slashIdleTicks = 0;
            warlord.hellfireIdleTicks = 0;
            warlord.leapIdleTicks = 0;
            warlord.forcedBlock = false;
            warlord.burstDamage = 0.0F;
            if (state == 0) {
                warlord.noTargetTicks = 0;
                return;
            }
            if (state == 3) {
                if (++warlord.bladeStateTicks >= AbstractWitherWarlord.SHEATH_ANIMATION_TICKS) {
                    warlord.noTargetTicks = 0;
                    warlord.bladeStateTicks = 0;
                    warlord.setBladeState(0);
                    warlord.eliteForceTicks = -1;
                    warlord.chargeAfterElite = false;
                }
                return;
            }
            if (state == 1) {
                warlord.noTargetTicks = 0;
                warlord.bladeStateTicks = 0;
                warlord.setBladeState(3);
                return;
            }
            if (++warlord.noTargetTicks >= AbstractWitherWarlord.BLADE_SHEATH_DELAY_TICKS) {
                warlord.noTargetTicks = 0;
                warlord.bladeStateTicks = 0;
                warlord.setBladeState(3);
            }
            return;
        }
        warlord.noTargetTicks = 0;
        if (state == 3) {
            warlord.bladeStateTicks = 0;
            warlord.setBladeState(2);
            return;
        }
        if (state == 0) {
            if (warlord.getSkill() == AbstractWitherWarlord.SKILL_NONE) {
                warlord.faceTowards(target);
                warlord.drawLockYaw = warlord.getYRot();
                warlord.getNavigation().stop();
                warlord.bladeStateTicks = 0;
                warlord.setBladeState(1);
            }
        } else if (state == 1 && ++warlord.bladeStateTicks >= AbstractWitherWarlord.DRAW_ANIMATION_TICKS) {
            warlord.bladeStateTicks = 0;
            warlord.setBladeState(2);
            warlord.eliteForceTicks = AbstractWitherWarlord.ELITE_FIRST_FORCE_MIN
                    + warlord.getRandom().nextInt(AbstractWitherWarlord.ELITE_FIRST_FORCE_RANDOM);
        }
    }

    static void beginSkill(AbstractWitherWarlord warlord, int skill) {
        warlord.setSkill(skill);
        warlord.skillTick = 0;
        if (skill == AbstractWitherWarlord.SKILL_ELITE) {
            warlord.eliteCooldown = AbstractWitherWarlord.ELITE_COOLDOWN_TICKS;
            warlord.eliteForceTicks = AbstractWitherWarlord.ELITE_FORCE_PERIOD_TICKS;
            warlord.chargeAfterElite = true;
        }
        if (skill == AbstractWitherWarlord.SKILL_CHARGE) {
            warlord.chargeCooldown = AbstractWitherWarlord.CHARGE_COOLDOWN_TICKS;
        }
        if (skill == AbstractWitherWarlord.SKILL_SLASH) {
            warlord.slashIdleTicks = 0;
        }
        if (skill == AbstractWitherWarlord.SKILL_HELLFIRE_SLASH) {
            warlord.hellfireCooldown = AbstractWitherWarlord.HELLFIRE_COOLDOWN_TICKS;
            warlord.hellfireIdleTicks = 0;
            warlord.queuedHellfire = false;
        }
        if (skill == AbstractWitherWarlord.SKILL_GREAT_LEAP) {
            warlord.greatLeapCooldown = AbstractWitherWarlord.GREAT_LEAP_COOLDOWN_TICKS;
            warlord.leapIdleTicks = 0;
        }
        if (skill == AbstractWitherWarlord.SKILL_BLAZING) {
            warlord.blazingCooldown = AbstractWitherWarlord.BLAZING_COOLDOWN_TICKS;
            warlord.intolerableCooldown = Math.max(warlord.intolerableCooldown, AbstractWitherWarlord.BLOCK_COOLDOWN_TICKS);
            warlord.forcedBlock = false;
        }
        if (skill == AbstractWitherWarlord.SKILL_INTOLERABLE) {
            warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_START);
            warlord.intolerableCooldown = Math.max(warlord.intolerableCooldown, AbstractWitherWarlord.BLOCK_COOLDOWN_TICKS);
            warlord.forcedBlock = false;
        }
        warlord.guardTicks = 0;
        warlord.guardHits = 0;
        warlord.blockEndTicks = 0;
        warlord.blazingRainTicks = 0;
        warlord.leaping = false;
        warlord.leapPhase = 0;
        warlord.leapTarget = null;
        warlord.combatIdleTicks = 0;
        warlord.setNoGravity(false);
        warlord.playSound(SoundEvents.WITHER_AMBIENT, 1.6F, 0.7F);
    }

    static void endSkill(AbstractWitherWarlord warlord, int recovery) {
        switch (warlord.getSkill()) {
            case AbstractWitherWarlord.SKILL_SLASH -> warlord.slashCooldown = Math.max(warlord.slashCooldown, AbstractWitherWarlord.BASIC_ATTACK_COOLDOWN_TICKS);
            case AbstractWitherWarlord.SKILL_BACKSTEP -> warlord.backstepCooldown = Math.max(warlord.backstepCooldown, recovery);
            case AbstractWitherWarlord.SKILL_INTOLERABLE -> warlord.intolerableCooldown = Math.max(warlord.intolerableCooldown, recovery);
            case AbstractWitherWarlord.SKILL_ELITE -> warlord.eliteCooldown = Math.max(warlord.eliteCooldown, recovery);
            case AbstractWitherWarlord.SKILL_CHARGE -> warlord.chargeCooldown = Math.max(warlord.chargeCooldown, recovery);
            case AbstractWitherWarlord.SKILL_BLAZING -> warlord.blazingCooldown = Math.max(warlord.blazingCooldown, recovery);
            case AbstractWitherWarlord.SKILL_HELLFIRE_SLASH -> warlord.hellfireCooldown = Math.max(warlord.hellfireCooldown, recovery);
            case AbstractWitherWarlord.SKILL_GREAT_LEAP -> warlord.greatLeapCooldown = Math.max(warlord.greatLeapCooldown, recovery);
            default -> {
            }
        }
        warlord.setSkill(AbstractWitherWarlord.SKILL_NONE);
        warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_NONE);
        warlord.skillTick = 0;
        warlord.skillActionGap = AbstractWitherWarlord.SKILL_ACTION_GAP_TICKS;
        warlord.guardTicks = 0;
        warlord.guardHits = 0;
        warlord.blockEndTicks = 0;
        warlord.blazingRainTicks = 0;
        warlord.leaping = false;
        warlord.backstepDir = Vec3.ZERO;
        warlord.setNoGravity(false);
    }

    static int pickWeightedSkill(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        if (target == null || !target.isAlive()) {
            return AbstractWitherWarlord.SKILL_NONE;
        }
        double dist = warlord.distanceTo(target);
        int[] weights = new int[AbstractWitherWarlord.SKILL_CATACLYSM + 1];
        if (dist <= AbstractWitherWarlord.SLASH_TRIGGER_RANGE) {
            weights[AbstractWitherWarlord.SKILL_SLASH] = warlord.targetInSlashRange() ? 64 : 0;
            weights[AbstractWitherWarlord.SKILL_INTOLERABLE] = 22;
            weights[AbstractWitherWarlord.SKILL_ELITE] = 14;
        } else if (dist <= 20.0D) {
            weights[AbstractWitherWarlord.SKILL_ELITE] = 52;
            weights[AbstractWitherWarlord.SKILL_CHARGE] = 28;
        } else {
            return AbstractWitherWarlord.SKILL_NONE;
        }
        int total = 0;
        for (int skill = AbstractWitherWarlord.SKILL_SLASH; skill <= AbstractWitherWarlord.SKILL_CATACLYSM; skill++) {
            if (weights[skill] > 0 && warlord.skillCooldown(skill) > 0) {
                weights[skill] = 0;
            }
            total += weights[skill];
        }
        if (total <= 0) {
            return AbstractWitherWarlord.SKILL_NONE;
        }
        int roll = warlord.getRandom().nextInt(total);
        for (int skill = AbstractWitherWarlord.SKILL_SLASH; skill <= AbstractWitherWarlord.SKILL_CATACLYSM; skill++) {
            if (weights[skill] <= 0) {
                continue;
            }
            roll -= weights[skill];
            if (roll < 0) {
                return skill;
            }
        }
        return AbstractWitherWarlord.SKILL_NONE;
    }

    static int skillCooldown(AbstractWitherWarlord warlord, int skill) {
        return switch (skill) {
            case AbstractWitherWarlord.SKILL_SLASH -> warlord.slashCooldown;
            case AbstractWitherWarlord.SKILL_BACKSTEP -> warlord.backstepCooldown;
            case AbstractWitherWarlord.SKILL_INTOLERABLE -> warlord.intolerableCooldown;
            case AbstractWitherWarlord.SKILL_ELITE -> warlord.eliteCooldown;
            case AbstractWitherWarlord.SKILL_CHARGE -> warlord.chargeCooldown;
            case AbstractWitherWarlord.SKILL_BLAZING -> warlord.blazingCooldown;
            case AbstractWitherWarlord.SKILL_HELLFIRE_SLASH -> warlord.hellfireCooldown;
            case AbstractWitherWarlord.SKILL_GREAT_LEAP -> warlord.greatLeapCooldown;
            default -> 0;
        };
    }
}
