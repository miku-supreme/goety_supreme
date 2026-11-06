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

final class WarlordGreatLeap {

    private WarlordGreatLeap() {
    }

    static void tickGreatLeap(AbstractWitherWarlord warlord) {
        if (warlord.skillTick == 1) {
            warlord.leapTarget = warlord.pickGreatLeapTarget();
            if (warlord.leapTarget == null) {
                warlord.endSkill(40);
                return;
            }
            warlord.leaping = true;
            warlord.leapPhase = 0;
            warlord.getNavigation().stop();
            warlord.setNoGravity(true);
            warlord.setDeltaMovement(0.0D, AbstractWitherWarlord.GREAT_LEAP_RISE_SPEED, 0.0D);
            warlord.playSound(SoundEvents.RAVAGER_ATTACK, 1.6F, 0.7F);
        }
        if (!warlord.leaping) {
            if (warlord.skillTick >= AbstractWitherWarlord.GREAT_LEAP_END_TICK + 20) {
                warlord.endSkill(160);
            }
            return;
        }
        if (warlord.leapTarget == null || !warlord.leapTarget.isAlive()) {
            warlord.endSkill(60);
            return;
        }
        if (warlord.leapPhase < 2 && warlord.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME,
                    warlord.getX(), warlord.getY(1.0D), warlord.getZ(), 12, 0.55D, 0.95D, 0.55D, 0.03D);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    warlord.getX(), warlord.getY(1.7D), warlord.getZ(), 6, 0.45D, 0.7D, 0.45D, 0.02D);
        }
        if (warlord.leapPhase == 0) {
            if (warlord.skillTick < AbstractWitherWarlord.GREAT_LEAP_LOCK_TICKS) {
                double dx = warlord.leapTarget.getX() - warlord.getX();
                double dz = warlord.leapTarget.getZ() - warlord.getZ();
                double flat = Math.sqrt(dx * dx + dz * dz);
                if (flat > 0.001D) {
                    double pull = Math.min(0.4D, flat / AbstractWitherWarlord.GREAT_LEAP_LOCK_TICKS);
                    warlord.setDeltaMovement(dx / flat * pull, AbstractWitherWarlord.GREAT_LEAP_RISE_SPEED, dz / flat * pull);
                } else {
                    warlord.setDeltaMovement(0.0D, AbstractWitherWarlord.GREAT_LEAP_RISE_SPEED, 0.0D);
                }
            } else {
                warlord.leapPhase = 1;
                Vec3 dive = new Vec3(warlord.leapTarget.getX() - warlord.getX(),
                        warlord.leapTarget.getY() + 0.5D - warlord.getY(),
                        warlord.leapTarget.getZ() - warlord.getZ());
                if (dive.lengthSqr() < 1.0E-6D) {
                    dive = new Vec3(0.0D, -1.0D, 0.0D);
                }
                warlord.setDeltaMovement(dive.normalize().scale(AbstractWitherWarlord.GREAT_LEAP_DIVE_SPEED));
                warlord.playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.6F);
            }
        }
        if (warlord.leapPhase == 1) {
            Vec3 dive = warlord.getDeltaMovement();
            if (dive.lengthSqr() > 1.0E-6D) {
                warlord.setDeltaMovement(dive.normalize().scale(AbstractWitherWarlord.GREAT_LEAP_DIVE_SPEED));
            }
            if (warlord.onGround() || warlord.skillTick >= AbstractWitherWarlord.GREAT_LEAP_SLAM_TICK + 20) {
                warlord.leapPhase = 2;
                warlord.landLeapImpact();
            }
        }
        if (warlord.leapPhase == 2 && warlord.skillTick >= AbstractWitherWarlord.GREAT_LEAP_END_TICK) {
            warlord.endSkill(160);
        }
    }

    static void landLeapImpact(AbstractWitherWarlord warlord) {
        warlord.setNoGravity(false);
        warlord.setDeltaMovement(Vec3.ZERO);
        Vec3 at = warlord.position();
        if (warlord.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, at.x, at.y, at.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            warlord.spawnIgnisFlameBurst(server, at);
        }
        warlord.playSound(SoundEvents.GENERIC_EXPLODE, 2.0F, 0.7F);
        warlord.playSound(goetySound("hell_blast_impact"), 2.4F, 0.85F);
        AABB box = new AABB(at, at).inflate(AbstractWitherWarlord.GREAT_LEAP_IMPACT_RADIUS);
        for (LivingEntity victim : warlord.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (warlord.canAttack(victim) && victim != warlord) {
                warlord.dealWarlordDamage(victim, AbstractWitherWarlord.GREAT_LEAP_IMPACT_DAMAGE,
                        warlord.damageSources().explosion(warlord, warlord));
            }
        }
        warlord.spawnHellfireRing(at, 5.0D, 20, 8);
        warlord.raiseGroundBlocks(at, AbstractWitherWarlord.GREAT_LEAP_IMPACT_RADIUS, 0.28F);
    }

    static void spawnIgnisFlameBurst(AbstractWitherWarlord warlord, ServerLevel server, Vec3 center) {
        double[][] shells = {{1.2D, 0.8D, 40.0D}, {2.6D, 1.2D, 30.0D}, {4.2D, 1.6D, 24.0D}};
        for (double[] shell : shells) {
            double spread = shell[0];
            float speed = (float) shell[1];
            int count = (int) shell[2];
            server.sendParticles(ParticleTypes.FLAME, center.x, center.y + 0.6D, center.z,
                    count, spread, spread * 0.6D, spread, speed);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y + 0.8D, center.z,
                    count / 2, spread * 0.8D, spread * 0.5D, spread * 0.8D, speed * 0.8F);
        }
        server.sendParticles(ParticleTypes.LAVA,
                center.x, center.y + 0.4D, center.z, 20, 1.6D, 0.5D, 1.6D, 0.0D);
        server.sendParticles(ParticleTypes.LARGE_SMOKE,
                center.x, center.y + 1.0D, center.z, 24, 2.0D, 1.0D, 2.0D, 0.02D);
    }

    static void raiseGroundBlocks(AbstractWitherWarlord warlord, Vec3 center, double radius, float popUp) {
        if (!(warlord.level() instanceof ServerLevel server)) {
            return;
        }
        int r = Mth.ceil(radius);
        BlockPos origin = BlockPos.containing(center);
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                if ((double) (dx * dx + dz * dz) > radius * radius) {
                    continue;
                }
                if (warlord.getRandom().nextFloat() > 0.6F) {
                    continue;
                }
                BlockPos surface = null;
                for (int dy = 2; dy >= -4; dy--) {
                    BlockPos probe = origin.offset(dx, dy, dz);
                    BlockState state = server.getBlockState(probe);
                    if (!state.isAir() && state.getFluidState().isEmpty()) {
                        surface = probe;
                        break;
                    }
                }
                if (surface == null) {
                    continue;
                }
                BlockState state = server.getBlockState(surface);
                if (state.isAir() || state.hasBlockEntity() || !state.getFluidState().isEmpty()) {
                    continue;
                }
                if (!server.getBlockState(surface.above()).isAir()) {
                    continue;
                }
                ModFallingBlock falling = new ModFallingBlock(server,
                        Vec3.atLowerCornerOf(surface.above()), state,
                        popUp + warlord.getRandom().nextFloat() * 0.12F);
                server.addFreshEntity(falling);
            }
        }
        warlord.playSound(goetySound("wall_erupt"), 1.6F, 0.8F + warlord.getRandom().nextFloat() * 0.2F);
        warlord.playSound(goetySound("dirt_debris"), 1.6F, 0.8F + warlord.getRandom().nextFloat() * 0.2F);
        warlord.playSound(SoundEvents.GRAVEL_BREAK, 1.6F, 0.8F + warlord.getRandom().nextFloat() * 0.2F);
    }

    @Nullable
    static SoundEvent goetySound(String path) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    @Nullable
    static LivingEntity pickGreatLeapTarget(AbstractWitherWarlord warlord) {
        LivingEntity current = warlord.getTarget();
        if (warlord.isLeapTargetValid(current)) {
            return current;
        }
        return warlord.findHighestHealthTarget(AbstractWitherWarlord.GREAT_LEAP_LOCK_RANGE);
    }

    static boolean isLeapTargetValid(AbstractWitherWarlord warlord, @Nullable LivingEntity candidate) {
        return candidate != null && candidate != warlord && candidate.isAlive()
                && !warlord.isFriendlyTo(candidate)
                && warlord.distanceTo(candidate) <= AbstractWitherWarlord.GREAT_LEAP_LOCK_RANGE
                && warlord.hasLineOfSight(candidate);
    }

    @Nullable
    static LivingEntity findHighestHealthTarget(AbstractWitherWarlord warlord, double radius) {
        LivingEntity best = null;
        AABB box = warlord.getBoundingBox().inflate(radius);
        for (LivingEntity candidate : warlord.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!warlord.canAttack(candidate) || candidate == warlord || warlord.isFriendlyTo(candidate)) {
                continue;
            }
            if (!warlord.hasLineOfSight(candidate)) {
                continue;
            }
            if (best == null || candidate.getMaxHealth() > best.getMaxHealth()) {
                best = candidate;
            }
        }
        return best;
    }
}
