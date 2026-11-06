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

/** 绿豆灵魂罐赠予与属性刷新，从 AbstractGreenSmith 抽出的辅助逻辑。 */
public final class GreenSmithSoulJarHelper {

    private GreenSmithSoulJarHelper() {
    }

    public static void applyDraugrSoulJar(AbstractGreenSmith smith, Player player, ItemStack stack) {
        smith.draugrSoulJarGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.6F);
        smith.playGoetySound("lich_laugh", 1.0F, 0.8F);
    }

    public static void applySmallBossSoulJar(AbstractGreenSmith smith, Player player, ItemStack stack, LivingEntity stored) {
        ResourceLocation type = ForgeRegistries.ENTITY_TYPES.getKey(stored.getType());
        if (type == null || !smith.addSmallBossType(type)) {
            return;
        }
        if (smith.smallBossSoulJarTypes.size() == 1) {
            smith.smallBossPity = 0;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    24, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, smith.getX(), smith.getY(1.0D),
                    smith.getZ(), 16, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.5F);
        smith.playGoetySound("lich_laugh", 1.0F, 0.6F);
    }

    public static void applySoulJarUpgrade(AbstractGreenSmith smith, Player player, ItemStack stack) {
        ++smith.soulJarUpgrades;
        smith.setSoulJarUpgrades(smith.soulJarUpgrades);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        smith.refreshSoulJarAttributes();
        smith.refreshDimensions();
        smith.setHealth(smith.getMaxHealth());
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.8F);
        smith.playGoetySound("lich_laugh", 1.0F, 0.8F);
    }

    public static void refreshSoulJarAttributes(AbstractGreenSmith smith) {
        AttributeInstance health = smith.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            int level = Mth.clamp(smith.soulJarUpgrades, 0, AbstractGreenSmith.MAX_SOUL_JAR_UPGRADES);
            health.setBaseValue(SupremeMobsConfig.GREEN_SMITH_HEALTH.get() * AbstractGreenSmith.SOUL_JAR_HEALTH_FACTOR[level]);
        }
    }

    public static float soulJarDamageBonus(AbstractGreenSmith smith) {
        return smith.soulJarUpgrades * AbstractGreenSmith.SOUL_JAR_DAMAGE_BONUS;
    }

    public static float effectiveSoulJarDamageBonus(AbstractGreenSmith smith) {
        return smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_DAMAGE_BONUS;
    }

    public static int effectiveSoulJarUpgrades(AbstractGreenSmith smith) {
        if (smith.isClone()) {
            AbstractGreenSmith master = smith.master();
            return master == smith ? smith.soulJarUpgrades : master.soulJarUpgrades;
        }
        return smith.soulJarUpgrades;
    }

    public static int soulJarLifespanBonusTicks(AbstractGreenSmith smith) {
        return smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_LIFESPAN_BONUS_TICKS;
    }

    public static void applySoulJarSummonBuffs(AbstractGreenSmith smith, Summoned minion) {
        if (smith.effectiveSoulJarUpgrades() < AbstractGreenSmith.MAX_SOUL_JAR_UPGRADES) {
            return;
        }
        if (AbstractGreenSmith.isSkeletonServant(minion)) {
            AttributeInstance attack = minion.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attack != null) {
                attack.setBaseValue(attack.getBaseValue() + 1.0D);
            }
            return;
        }
        minion.addEffect(new MobEffectInstance(GreenSmithFx.buff(),
                MobEffectInstance.INFINITE_DURATION, 0, false, false));
    }

    public static ResourceLocation pickSmallBossType(AbstractGreenSmith smith) {
        return GreenSmithNecromancerRoster.pickSmallBossType(smith);
    }

    public static boolean ownerHasRoomForSmallBoss(AbstractGreenSmith smith, ServerLevel server, ResourceLocation typeId) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(typeId);
        LivingEntity owner = smith.summonOwner();
        return type != null && owner instanceof Player player
                && ServantLimits.hasRoom(server, player, type);
    }

}