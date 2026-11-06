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

/** 绿豆阴骑/风爆/无名束/战吼，从 AbstractGreenSmith 抽出的施法逻辑。 */
public final class GreenSmithSpells {

    private GreenSmithSpells() {
    }

    public static double windBlastRadius(AbstractGreenSmith smith, double base) {
        return base + smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_WIND_RADIUS_PER_LEVEL;
    }

    public static int rallyWeight(AbstractGreenSmith smith) {
        if (smith.rallyCooldown > 0) {
            return 0;
        }
        float max = smith.getMaxHealth();
        float fraction = max <= 0.0F ? 1.0F : Mth.clamp(smith.getHealth() / max, 0.0F, 1.0F);
        int weight;
        if (fraction > 0.5F) {
            weight = Math.round(Mth.lerp((1.0F - fraction) * 2.0F,
                    AbstractGreenSmith.WEIGHT_RALLY_HEALTHY, AbstractGreenSmith.WEIGHT_RALLY_AT_HALF));
        } else {
            weight = Math.round(Mth.lerp((0.5F - fraction) * 2.0F,
                    AbstractGreenSmith.WEIGHT_RALLY_AT_HALF, AbstractGreenSmith.WEIGHT_RALLY_NEAR_DEATH));
        }
        if (smith.retreatHealTicks > 0) {
            weight += AbstractGreenSmith.WEIGHT_RALLY_RETREAT_BONUS;
        }
        return weight;
    }

    public static void fireNamelessBolt(AbstractGreenSmith smith) {
        if (smith.isClone()) {
            return;
        }
        LivingEntity target = smith.getTarget();
        Vec3 dir;
        if (target != null && target.isAlive()) {
            dir = new Vec3(target.getX() - smith.getX(), target.getEyeY() - smith.getEyeY(),
                    target.getZ() - smith.getZ());
        } else {
            dir = smith.getLookAngle();
        }
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = smith.getLookAngle();
        }
        dir = dir.normalize();
        if (smith.level() instanceof ServerLevel server) {
            NecroBolt bolt = new NecroBolt(smith, dir.x, dir.y, dir.z, server);
            bolt.setPos(smith.getX() + dir.x * 0.9D, smith.getEyeY() - 0.2D + dir.y * 0.9D,
                    smith.getZ() + dir.z * 0.9D);
            bolt.setExtraDamage(smith.soulJarDamageBonus());
            server.addFreshEntity(bolt);
        }
        smith.playGoetySound("soul_bolt_cast", 1.0F, 0.8F);
    }

    public static void castRally(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        MobEffect shielded = GreenSmithFx.shielded();
        MobEffect ironHide = GreenSmithFx.ironHide();
        MobEffect rallying = GreenSmithFx.rallying();
        MobEffect necrosis = GoetyEffects.NECROSIS.get();
        int upgrades = smith.effectiveSoulJarUpgrades();
        int buffTicks = AbstractGreenSmith.RALLY_BUFF_TICKS + upgrades * AbstractGreenSmith.SOUL_JAR_RALLY_TICKS_PER_LEVEL;
        int shieldedLevel = upgrades >= AbstractGreenSmith.MAX_SOUL_JAR_UPGRADES ? 1 : 0;
        float rallyHeal = AbstractGreenSmith.SOUL_JAR_RALLY_HEAL_PER_LEVEL * upgrades;
        smith.addEffect(new MobEffectInstance(shielded, buffTicks, shieldedLevel, false, true));
        smith.addEffect(new MobEffectInstance(ironHide, buffTicks, AbstractGreenSmith.RALLY_IRON_HIDE_LEVEL, false, true));
        smith.addEffect(new MobEffectInstance(rallying, buffTicks, AbstractGreenSmith.RALLY_RALLYING_LEVEL, false, true));
        smith.addEffect(new MobEffectInstance(necrosis, AbstractGreenSmith.RALLY_NECROSIS_TICKS, 0, false, true));
        if (!smith.isAntiHealActive() && smith.getHealth() < smith.getMaxHealth()) {
            smith.heal(rollRallyHeal(smith, rallyHeal));
        }
        server.sendParticles(GreenSmithFx.heal(), smith.getX(), smith.getY() + 1.2D, smith.getZ(),
                20, 0.6D, 0.8D, 0.6D, 0.02D);
        for (LivingEntity ally : server.getEntitiesOfClass(LivingEntity.class,
                smith.getBoundingBox().inflate(AbstractGreenSmith.RALLY_RADIUS))) {
            if (ally == smith || !smith.isMyAlly(ally) || !(ally instanceof Summoned)) {
                continue;
            }
            ally.addEffect(new MobEffectInstance(ironHide, buffTicks, AbstractGreenSmith.RALLY_IRON_HIDE_LEVEL, false, true));
            if (smith.isMyNecromancerServant(ally)) {
                ally.addEffect(new MobEffectInstance(shielded, buffTicks, shieldedLevel, false, true));
                ally.addEffect(new MobEffectInstance(rallying, buffTicks, AbstractGreenSmith.RALLY_RALLYING_LEVEL, false, true));
            }
            if (ally.getMobType() == MobType.UNDEAD) {
                ally.addEffect(new MobEffectInstance(necrosis, AbstractGreenSmith.RALLY_NECROSIS_TICKS, 0, false, true));
            }
            GreenSmithSunImmunity.grant(ally, AbstractGreenSmith.RALLY_SUN_IMMUNE_TICKS);
            if (ally.getHealth() < ally.getMaxHealth()) {
                ally.heal(rollRallyHeal(smith, rallyHeal));
            }
            server.sendParticles(GreenSmithFx.heal(), ally.getX(),
                    ally.getY() + ally.getBbHeight() * 0.5D, ally.getZ(), 8, 0.4D, 0.5D, 0.4D, 0.02D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, ally.getX(),
                    ally.getY() + ally.getBbHeight() * 0.5D, ally.getZ(), 8, 0.4D, 0.5D, 0.4D, 0.02D);
        }
        smith.playGoetySound("wind_horn", 1.5F, 1.0F);
    }

    public static float rollRallyHeal(AbstractGreenSmith smith, float perLevelBonus) {
        return AbstractGreenSmith.RALLY_HEAL_MIN + smith.getRandom().nextFloat() * (AbstractGreenSmith.RALLY_HEAL_MAX - AbstractGreenSmith.RALLY_HEAL_MIN) + perLevelBonus;
    }

    public static void castWindBurst(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 center = smith.position().add(0.0D, 0.5D, 0.0D);
        WindBlastFx.cast(server, smith, center.x, center.y, center.z,
                smith.windBlastRadius(6.0D), 1.0D);
        smith.applyTremblingFocus(server);
        smith.teleportBackward(server, 6.0D, true);
        smith.abortCloneCombo();
        LivingEntity target = smith.getTarget();
        for (int i = 0; i < 6; i++) {
            AbstractGreenSmith clone = ModEntityTypes.GREEN_SMITH_SERVANT.get().create(server);
            if (clone == null) {
                continue;
            }
            double angle = Math.PI * 2.0D * i / 6.0D;
            double x = smith.getX() + Math.cos(angle) * GreenSmithClones.CLONE_SLOT_RADIUS;
            double z = smith.getZ() + Math.sin(angle) * GreenSmithClones.CLONE_SLOT_RADIUS;
            clone.setTrueOwner(smith);
            double cloneY = MinionGroundPlacement.groundFeetY(server, x, z, smith.getY(),
                    MinionGroundPlacement.SKELETON_CLEARANCE);
            clone.moveTo(x, cloneY, z, smith.getYRot(), 0.0F);
            clone.setLimitedLife(AbstractGreenSmith.CLONE_FALLBACK_LIFE);
            clone.getPersistentData().putBoolean(AbstractGreenSmith.CLONE_TAG, true);
            clone.cloneSlot = i;
            clone.setHealth(clone.getMaxHealth());
            if (target != null) {
                clone.setTarget(target);
            }
            server.addFreshEntity(clone);
            clone.setHealth(clone.getMaxHealth());
            smith.windClones.add(clone.getUUID());
            server.sendParticles(GreenSmithFx.necroFire(), x, cloneY + 1.0D, z,
                    12, 0.3D, 0.5D, 0.3D, 0.02D);
        }
        smith.cloneComboPending = !smith.windClones.isEmpty();
        smith.playGoetySound("wind_blast", 1.4F, 0.8F);
    }

}