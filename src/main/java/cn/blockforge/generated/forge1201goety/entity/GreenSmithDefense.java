package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.GatherTrailParticleOption;
import com.Polarice3.Goety.client.particles.RisingCircleParticleOption;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.client.particles.SoulShockwaveParticleOption;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.PhantomServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import com.Polarice3.Goety.common.entities.projectiles.MonsoonCloud;
import com.Polarice3.Goety.common.entities.projectiles.NecroBolt;
import com.Polarice3.Goety.common.entities.projectiles.PoisonQuill;
import com.Polarice3.Goety.common.entities.projectiles.SteamMissile;
import com.Polarice3.Goety.common.entities.projectiles.SurgingOrb;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.common.entities.util.TridentStorm;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.wild.BlossomSpell;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SThunderBoltPacket;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmithGoals;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.util.CastQueue;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithStaffFxLayer;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSunImmunity;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.renderer.WindBlastFx;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSoulJarMixin;
import cn.blockforge.generated.goetysupreme.mixin.ReviveServantItemSoulJarGuardMixin;

public final class GreenSmithDefense {

    private GreenSmithDefense() {
    }

    public static boolean hurt(AbstractGreenSmith smith, DamageSource source, float amount) {
        if (isInvulnerableTo(smith, source)) {
            return false;
        }
        if (smith.level().isClientSide) {
            return false;
        }
        if (ExecutionDamage.isExecution(source, amount)) {
            smith.hurtInvulnerable = 0;
            smith.invulnerableTime = 0;
            smith.hardKill = true;
            boolean dead = smith.superHurt(source, Float.MAX_VALUE);
            smith.hardKill = false;
            return dead;
        }
        boolean witherDamage = source.is(DamageTypes.WITHER);
        if (smith.hurtInvulnerable > 0 && !witherDamage
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        float capped = AbstractGreenSmith.applyDamageCap(amount);
        if (source.getEntity() == null && source.getDirectEntity() == null) {
            capped *= 0.5F;
        }
        if (source.is(DamageTypes.FREEZE)) {
            capped *= 0.5F;
        }
        float healthBefore = smith.getHealth();
        boolean hurt = smith.superHurt(source, amount);
        if (!hurt) {
            return false;
        }
        float actualDamage = Math.max(0.0F, healthBefore - smith.getHealth());
        if (witherDamage) {
            smith.antiHealTicks = AbstractGreenSmith.ANTI_HEAL_TICKS;
            smith.getEntityData().set(AbstractGreenSmith.DATA_ANTI_HEAL, smith.antiHealTicks);
        } else {
            smith.hurtInvulnerable = AbstractGreenSmith.HURT_INVULNERABLE_TICKS;
            smith.invulnerableTime = 0;
        }
        if (AbstractGreenSmith.warlordAttacker(source) != null) {
            smith.warlordHateDamage += capped;
        }
        if (smith.isCasting()) {
            smith.castDamageTaken += actualDamage;
        }
        if (!isClone(smith) && !smith.isFieldActive() && smith.castAnimation != AbstractGreenSmith.CAST_FIELD) {
            if (actualDamage >= AbstractGreenSmith.TELEPORT_DAMAGE || smith.inHazard()) {
                if (smith.level() instanceof ServerLevel server) {
                    smith.teleportAway(server, 10);
                }
            }
            if (++smith.hitsSinceTeleport >= smith.forcedTeleportHits()) {
                if (smith.level() instanceof ServerLevel server) {
                    smith.teleportAway(server, 10);
                }
            }
            smith.tickCastInterrupt();
        }
        return true;
    }

    public static boolean isInvulnerableTo(AbstractGreenSmith smith, DamageSource source) {
        if (source.is(DamageTypeTags.IS_FALL)) {
            return true;
        }
        if (source.is(DamageTypes.DROWN) || source.is(DamageTypes.FLY_INTO_WALL)
                || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.SWEET_BERRY_BUSH)) {
            return true;
        }
        if (source.getMsgId().contains("dehydrat")) {
            return true;
        }
        return smith.superIsInvulnerableTo(source);
    }

    public static boolean canBeAffected(AbstractGreenSmith smith, MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || effect.getEffect() == MobEffects.WEAKNESS
                || effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
            return false;
        }
        return smith.superCanBeAffected(effect);
    }

    public static boolean causeFallDamage(AbstractGreenSmith smith, float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    public static boolean canBreatheUnderwater(AbstractGreenSmith smith) {
        return true;
    }

    public static MobType getMobType(AbstractGreenSmith smith) {
        return MobType.UNDEAD;
    }

    public static void actuallyHurt(AbstractGreenSmith smith, DamageSource source, float amount) {
        if (smith.hardKill) {
            smith.superActuallyHurt(source, amount);
            return;
        }
        float multiplier = 1.0F;
        if (source.getEntity() == null && source.getDirectEntity() == null) {
            multiplier *= 0.5F;
        }
        if (source.is(DamageTypes.FREEZE)) {
            multiplier *= 0.5F;
        }
        if (smith.isFieldActive()) {
            multiplier *= Math.max(0.05F,
                    0.2F - smith.soulJarUpgrades * AbstractGreenSmith.SOUL_JAR_FIELD_REDUCTION_PER_LEVEL);
        }
        SupremeBossHurt.hurt(smith, source, amount, AbstractGreenSmith.damageCap(), multiplier);
    }

    public static void tryKill(AbstractGreenSmith smith, Player player) {
        if (smith.level().isClientSide()) {
            return;
        }
        if (smith.getKillChance() <= 0) {
            smith.warnKill(player);
            return;
        }
        hurt(smith, ModDamageSource.getDamageSource(smith.level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    public static void dropCustomDeathLoot(AbstractGreenSmith smith, DamageSource source, int looting, boolean recentlyHit) {
        smith.superDropCustomDeathLoot(source, looting, recentlyHit);
        dropGreenSmithLoot(smith, looting);
    }

    public static void dropGreenSmithLoot(AbstractGreenSmith smith, int looting) {
        int bonus = Math.max(0, looting);
        dropGoetyItem(smith, "ectoplasm", smith.getRandom().nextInt(5 + bonus));
        dropGoetyItem(smith, "grave_dust", smith.getRandom().nextInt(5 + bonus));
        smith.spawnAtLocation(new ItemStack(Items.BONE, smith.getRandom().nextInt(6 + bonus)));
        smith.spawnAtLocation(new ItemStack(Items.EMERALD, smith.getRandom().nextInt(3 + bonus)));
    }

    public static void dropGoetyItem(AbstractGreenSmith smith, String path, int count) {
        if (count <= 0) {
            return;
        }
        net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
        if (item != null) {
            smith.spawnAtLocation(new ItemStack(item, count));
        }
    }

    public static boolean isClone(AbstractGreenSmith smith) {
        return smith.getPersistentData().getBoolean(AbstractGreenSmith.CLONE_TAG);
    }

    public static void playGoetySound(AbstractGreenSmith smith, String path, float volume, float pitch) {
        SoundEvent sound = GreenSmithFx.sound(path);
        if (sound != null) {
            smith.playSound(sound, volume, pitch);
        }
    }

    public static LivingEntity getMasterOwner(AbstractGreenSmith smith) {
        return smith.getTrueOwner() == null ? smith : smith.superGetMasterOwner();
    }

    public static boolean isMyAlly(AbstractGreenSmith smith, LivingEntity other) {
        if (other == smith) {
            return true;
        }
        if (other instanceof AbstractGreenSmith) {
            return true;
        }
        if (ServantFaction.isSameFaction(smith, other)) {
            return true;
        }
        LivingEntity owner = smith.getTrueOwner();
        if (owner != null) {
            if (other == owner) {
                return true;
            }
            if (other instanceof OwnableEntity ownable && ownable.getOwner() == owner) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlliedTo(AbstractGreenSmith smith, Entity other) {
        if (other == null) {
            return false;
        }
        if (other instanceof LivingEntity living && isMyAlly(smith, living)) {
            return true;
        }
        return smith.superIsAlliedTo(other);
    }

    public static net.minecraft.world.entity.SpawnGroupData finalizeSpawn(AbstractGreenSmith smith, ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType,
            @Nullable net.minecraft.world.entity.SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        smith.setPersistenceRequired();
        return smith.superFinalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    public static EntityDimensions getDimensions(AbstractGreenSmith smith, Pose pose) {
        return smith.superGetDimensions(pose).scale(smith.getSoulJarSizeScale());
    }

    public static SoundEvent getAmbientSound(AbstractGreenSmith smith) {
        SoundEvent sound = GreenSmithFx.sound("lich_ambient");
        return sound != null ? sound : SoundEvents.WITHER_AMBIENT;
    }

    public static SoundEvent getHurtSound(AbstractGreenSmith smith, DamageSource source) {
        SoundEvent sound = GreenSmithFx.sound("lich_hurt");
        return sound != null ? sound : SoundEvents.WITHER_HURT;
    }

    public static SoundEvent getDeathSound(AbstractGreenSmith smith) {
        SoundEvent sound = GreenSmithFx.sound("lich_death");
        return sound != null ? sound : SoundEvents.WITHER_DEATH;
    }

    public static void playStepSound(AbstractGreenSmith smith, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        SoundEvent step = GreenSmithFx.sound("necromancer_step");
        if (step != null) {
            smith.playSound(step, 0.15F, 1.0F);
        }
    }

    public static int getAmbientSoundInterval(AbstractGreenSmith smith) {
        return 200;
    }

    public static void playAmbientSound(AbstractGreenSmith smith) {
        SoundEvent sound = getAmbientSound(smith);
        if (sound != null) {
            smith.playSound(sound, 1.0F, 0.85F);
        }
    }

    public static void die(AbstractGreenSmith smith, DamageSource cause) {
        if (!smith.level().isClientSide) {
            smith.fieldTicks = 0;
            smith.getEntityData().set(AbstractGreenSmith.DATA_FIELD_ACTIVE, false);
            smith.laserTicks = 0;
            smith.laserTarget = null;
            smith.clearCorruptedBeam();
            smith.setCastAnimation(AbstractGreenSmith.CAST_NONE, 0);
            smith.castElapsed = 0;
            smith.castQueue.clear();
            smith.abortCloneCombo();
        }
        smith.superDie(cause);
    }

    public static void tickDeath(AbstractGreenSmith smith) {
        smith.castQueue.clear();
        ++smith.deathTime;
        if (smith.deathTime >= AbstractGreenSmith.DEATH_ANIMATION_TICKS && !smith.level().isClientSide && !smith.isRemoved()) {
            smith.level().broadcastEntityEvent(smith, (byte) 60);
            remove(smith, Entity.RemovalReason.KILLED);
        }
    }

    public static int getFieldTicks(AbstractGreenSmith smith) {
        return smith.fieldTicks;
    }

    public static void remove(AbstractGreenSmith smith, Entity.RemovalReason reason) {
        smith.castQueue.clear();
        smith.clearCorruptedBeam();
        smith.resetStaffFx();
        smith.superRemove(reason);
    }

}
