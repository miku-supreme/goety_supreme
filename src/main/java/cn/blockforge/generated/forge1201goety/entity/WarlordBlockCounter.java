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

final class WarlordBlockCounter {

    private WarlordBlockCounter() {
    }

    static void tickIntolerable(AbstractWitherWarlord warlord) {
        int phase = warlord.getBlockPhase();
        if (phase == AbstractWitherWarlord.BLOCK_PHASE_DASH) {
            warlord.tickDash();
            return;
        }
        if (phase == AbstractWitherWarlord.BLOCK_PHASE_END) {
            if (--warlord.blockEndTicks <= 0) {
                warlord.endSkill(40);
            }
            return;
        }
        if (warlord.skillTick == 1) {
            warlord.guardTicks = AbstractWitherWarlord.BLOCK_WINDOW_TICKS;
            warlord.guardHits = 0;
            warlord.blockEndTicks = 0;
            warlord.getNavigation().stop();
            warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_START);
            warlord.playSound(SoundEvents.SHIELD_BLOCK, 1.4F, 0.6F);
        } else if (phase == AbstractWitherWarlord.BLOCK_PHASE_START && warlord.skillTick >= AbstractWitherWarlord.BLOCK_START_TICKS) {
            warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_HOLD);
        }
        if (warlord.guardHits >= AbstractWitherWarlord.BLOCK_MAX_HITS) {
            warlord.resolveBlockCounter();
            return;
        }
        if (warlord.guardTicks > 0) {
            boolean waitingInvuln = warlord.warlordHurtCooldown > 0;
            if (!waitingInvuln) {
                warlord.guardTicks--;
            }
            warlord.lockInPlace();
            if (warlord.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.CRIT,
                        warlord.getX(), warlord.getY(1.2D), warlord.getZ(), 8, 0.7D, 0.9D, 0.7D, 0.05D);
            }
            if (warlord.guardTicks <= 0) {
                if (warlord.guardHits >= AbstractWitherWarlord.BLOCK_MAX_HITS) {
                    warlord.resolveBlockCounter();
                } else {
                    warlord.beginBlockEnd();
                }
            }
            return;
        }
        warlord.beginBlockEnd();
    }

    static void beginBlockEnd(AbstractWitherWarlord warlord) {
        warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_END);
        warlord.blockEndTicks = AbstractWitherWarlord.BLOCK_END_TICKS;
    }

    static void resolveBlockCounter(AbstractWitherWarlord warlord) {
        warlord.guardTicks = 0;
        warlord.guardHits = 0;
        warlord.intolerableCooldown = AbstractWitherWarlord.BLOCK_COOLDOWN_TICKS;
        warlord.blazingCooldown = AbstractWitherWarlord.BLOCK_COOLDOWN_TICKS;
        warlord.forcedBlock = false;
        boolean preferDash = warlord.isEnhanced()
                ? warlord.getRandom().nextInt(4) != 0
                : warlord.getRandom().nextBoolean();
        if (preferDash) {
            warlord.startBlockDash();
        } else {
            warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_NONE);
            warlord.beginSkill(AbstractWitherWarlord.SKILL_BLAZING);
        }
    }

    static void startBlockDash(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.lastHurtBy instanceof LivingEntity living && living.isAlive()
                ? living : warlord.getTarget();
        if (target == null || !target.isAlive()) {
            warlord.beginBlockEnd();
            return;
        }
        warlord.setBlockPhase(AbstractWitherWarlord.BLOCK_PHASE_DASH);
        warlord.guardTicks = 0;
        warlord.dashTarget = target;
        warlord.dashTicks = 0;
        warlord.dashHits = 0;
        warlord.faceTowards(target);
        warlord.getNavigation().stop();
        Vec3 away = warlord.position().subtract(target.position());
        Vec3 horizontal = new Vec3(away.x, 0.0D, away.z);
        if (horizontal.lengthSqr() < 1.0E-6D) {
            horizontal = new Vec3(0.0D, 0.0D, 1.0D);
        }
        horizontal = horizontal.normalize();
        warlord.setDeltaMovement(horizontal.x * 0.35D, 0.42D, horizontal.z * 0.35D);
        warlord.playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.6F);
        if (warlord.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME,
                    warlord.getX(), warlord.getY(1.0D), warlord.getZ(), 40, 0.6D, 0.8D, 0.6D, 0.12D);
            server.sendParticles(ParticleTypes.LAVA,
                    warlord.getX(), warlord.getY(0.6D), warlord.getZ(), 8, 0.5D, 0.3D, 0.5D, 0.0D);
        }
    }

    static void tickDash(AbstractWitherWarlord warlord) {
        if (warlord.getSkill() != AbstractWitherWarlord.SKILL_INTOLERABLE || warlord.getBlockPhase() != AbstractWitherWarlord.BLOCK_PHASE_DASH) {
            return;
        }
        LivingEntity target = warlord.dashTarget;
        if (target == null || !target.isAlive()) {
            warlord.endSkill(80);
            return;
        }
        if (warlord.level() instanceof ServerLevel fire) {
            fire.sendParticles(ParticleTypes.FLAME,
                    warlord.getX(), warlord.getY(0.9D), warlord.getZ(), 10, 0.42D, 0.55D, 0.42D, 0.05D);
            fire.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    warlord.getX(), warlord.getY(1.3D), warlord.getZ(), 6, 0.38D, 0.45D, 0.38D, 0.03D);
            fire.sendParticles(ParticleTypes.SMOKE,
                    warlord.getX(), warlord.getY(1.1D), warlord.getZ(), 5, 0.32D, 0.4D, 0.32D, 0.01D);
        }
        warlord.dashTicks++;
        if (warlord.dashHits < AbstractWitherWarlord.BLOCK_MAX_HITS) {
            double dist = warlord.distanceTo(target);
            if (dist > AbstractWitherWarlord.BLOCK_DASH_REACH) {
                warlord.faceTowards(target);
                warlord.getNavigation().stop();
                Vec3 dir = new Vec3(target.getX() - warlord.getX(), 0.0D, target.getZ() - warlord.getZ());
                if (dir.lengthSqr() > 1.0E-6D) {
                    dir = dir.normalize();
                    warlord.setDeltaMovement(dir.x * AbstractWitherWarlord.BLOCK_DASH_SPEED, warlord.getDeltaMovement().y,
                            dir.z * AbstractWitherWarlord.BLOCK_DASH_SPEED);
                }
                warlord.fallDistance = 0.0F;
            } else if (warlord.dashHits == 0) {
                warlord.setDeltaMovement(Vec3.ZERO);
                warlord.strikeDashTarget();
                warlord.dashTicks = 0;
            } else if (warlord.dashTicks >= AbstractWitherWarlord.BLOCK_DASH_HIT_GAP) {
                warlord.strikeDashTarget();
            }
        }
        if ((warlord.dashHits >= AbstractWitherWarlord.BLOCK_MAX_HITS && warlord.dashTicks >= AbstractWitherWarlord.BLOCK_DASH_HIT_GAP)
                || warlord.dashTicks >= AbstractWitherWarlord.BLOCK_DASH_TIMEOUT_TICKS) {
            warlord.endSkill(80);
        }
    }

    static void strikeDashTarget(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.dashTarget;
        if (target == null || !target.isAlive()) {
            return;
        }
        warlord.dashHits++;
        float damage = AbstractWitherWarlord.withMaxHealthBonus(AbstractWitherWarlord.BLOCK_DASH_DAMAGE, target, AbstractWitherWarlord.DASH_MAX_HEALTH_RATIO);
        warlord.dealWarlordDamage(target, damage, warlord.damageSources().mobAttack(warlord));
        if (warlord.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION,
                    target.getX(), target.getY(1.0D), target.getZ(), 2, 0.3D, 0.3D, 0.3D, 0.0D);
            server.sendParticles(ParticleTypes.FLAME,
                    target.getX(), target.getY(1.0D), target.getZ(), 24, 0.45D, 0.6D, 0.45D, 0.09D);
            server.sendParticles(ParticleTypes.LAVA,
                    target.getX(), target.getY(0.5D), target.getZ(), 4, 0.4D, 0.2D, 0.4D, 0.0D);
        }
    }
}
