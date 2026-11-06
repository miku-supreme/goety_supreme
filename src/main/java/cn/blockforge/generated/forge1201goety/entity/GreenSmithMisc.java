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

/** 绿豆零散辅助：夜临、季风、传送。 */
public final class GreenSmithMisc {

    private GreenSmithMisc() {
    }

    public static void tickNightfall(AbstractGreenSmith smith) {
        if (smith.nightDone) {
            return;
        }
        if (smith.tickCount > 40) {
            smith.nightDone = true;
            return;
        }
        if (smith.tickCount % 5 != 0 || !smith.level().dimensionType().hasSkyLight()) {
            return;
        }
        smith.nightDone = true;
        if (smith.level() instanceof ServerLevel server && !server.isDay()) {
            return;
        }
        if (smith.level() instanceof ServerLevel server) {
            long dayTime = server.getDayTime();
            long day = Math.floorDiv(dayTime, 24000L);
            long timeOfDay = Math.floorMod(dayTime, 24000L);
            if (timeOfDay >= 18000L) {
                return;
            }
            server.setDayTime((day + 1L) * 24000L + 18000L);
            server.sendParticles(ParticleTypes.SMOKE, smith.getX(), smith.getY() + 1.0D, smith.getZ(),
                    40, 1.5D, 1.0D, 1.5D, 0.02D);
            smith.playGoetySound("lich_ambient", 1.2F, 0.6F);
        }
    }

    public static void castMonsoonCloud(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server) || !AbstractGreenSmith.ominousLoaded()) {
            return;
        }
        LivingEntity target = smith.getTarget();
        double x;
        double z;
        double referenceY;
        if (target != null && target.isAlive()) {
            x = target.getX();
            z = target.getZ();
            referenceY = target.getY();
        } else {
            Vec3 look = smith.getLookAngle();
            x = smith.getX() + look.x * 2.0D;
            z = smith.getZ() + look.z * 2.0D;
            referenceY = smith.getY();
        }
        double groundY = MinionGroundPlacement.groundFeetY(server, x, z, referenceY,
                MinionGroundPlacement.SKELETON_CLEARANCE);
        double y = Math.max(groundY, referenceY) + AbstractGreenSmith.CLOUD_HOVER_HEIGHT;
        MonsoonCloud cloud = new MonsoonCloud(server, smith,
                target != null && target.isAlive() ? target : smith);
        cloud.setExtraDamage(AbstractGreenSmith.CLOUD_EXTRA_DAMAGE + smith.soulJarDamageBonus());
        cloud.setRadius(AbstractGreenSmith.CLOUD_RADIUS
                + smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_CLOUD_RADIUS_PER_LEVEL);
        cloud.setLifeSpan(AbstractGreenSmith.CLOUD_LIFESPAN);
        cloud.setStaff(true);
        cloud.moveTo(x, y, z, smith.getYRot(), 0.0F);
        server.addFreshEntity(cloud);
        smith.playGoetySound("wind_blast", 1.4F, 0.7F);
        smith.playMonsoonStrikeFx(server, x, y, z);
    }

    public static void playMonsoonStrikeFx(AbstractGreenSmith smith, ServerLevel server, double x, double y, double z) {
        Vec3 tip = smith.chargeStaffTip();
        ModNetwork.sendToALL(new SThunderBoltPacket(
                tip, new Vec3(x, y, z), new ColorUtil(AbstractGreenSmith.GREEN_LIGHTNING_COLOR), 6));
        ModNetwork.sendToALL(new SThunderBoltPacket(
                new Vec3(x, y + 24.0D, z), new Vec3(x, y - AbstractGreenSmith.CLOUD_HOVER_HEIGHT, z),
                new ColorUtil(AbstractGreenSmith.GREEN_LIGHTNING_COLOR), 6));
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z,
                48, 3.0D, 0.6D, 3.0D, 0.18D);
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, tip.x, tip.y, tip.z,
                16, 0.3D, 0.4D, 0.3D, 0.08D);
        smith.playGoetySound("thunderbolt", 1.2F, 1.1F);
    }

    public static void tickMonsoonCharge(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (smith.castElapsed == 1) {
            smith.playGoetySound("prepare_spell", 1.2F, 0.75F);
        }
        Vec3 tip = smith.chargeStaffTip();
        if (smith.castElapsed % 3 == 0) {
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, tip.x, tip.y, tip.z,
                    6, 0.2D, 0.25D, 0.2D, 0.08D);
        }
        if (smith.castElapsed % 10 == 0) {
            ModNetwork.sendToALL(new SThunderBoltPacket(
                    new Vec3(tip.x, tip.y + 24.0D, tip.z), tip,
                    new ColorUtil(AbstractGreenSmith.GREEN_LIGHTNING_COLOR), 3));
        }
    }

    public static void tickDistanceTeleport(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (smith.teleportToTargetCooldown > 0 || smith.isCasting()) {
            return;
        }
        LivingEntity target = smith.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        double minSqr = AbstractGreenSmith.APPROACH_TELEPORT_MIN_DISTANCE * AbstractGreenSmith.APPROACH_TELEPORT_MIN_DISTANCE;
        if (smith.distanceToSqr(target) <= minSqr) {
            return;
        }
        if (smith.tryTeleportCloserToTarget(server, target)) {
            smith.teleportToTargetCooldown = AbstractGreenSmith.APPROACH_TELEPORT_COOLDOWN;
        }
    }

    public static boolean tryTeleportCloserToTarget(AbstractGreenSmith smith, ServerLevel server, LivingEntity target) {
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = smith.getRandom().nextDouble() * Math.PI * 2.0D;
            double distance = AbstractGreenSmith.APPROACH_TELEPORT_DISTANCE_MIN
                    + smith.getRandom().nextDouble()
                            * (AbstractGreenSmith.APPROACH_TELEPORT_DISTANCE_MAX - AbstractGreenSmith.APPROACH_TELEPORT_DISTANCE_MIN);
            int x = Mth.floor(target.getX() + Math.cos(angle) * distance);
            int z = Mth.floor(target.getZ() + Math.sin(angle) * distance);
            for (int dy = 8; dy >= -12; dy--) {
                int y = Mth.floor(target.getY()) + dy;
                BlockPos pos = new BlockPos(x, y, z);
                if (!smith.isSafeTeleportPos(server, pos)) {
                    continue;
                }
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.playGoetySound("lich_teleport_out", 1.0F, 0.9F);
                smith.getNavigation().stop();
                smith.fallDistance = 0.0F;
                smith.setDeltaMovement(Vec3.ZERO);
                smith.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.playGoetySound("lich_teleport_in", 1.0F, 0.9F);
                return true;
            }
        }
        return false;
    }

    public static boolean teleportAway(AbstractGreenSmith smith, ServerLevel server, int radius) {
        for (int attempt = 0; attempt < 48; attempt++) {
            double angle = smith.getRandom().nextDouble() * Math.PI * 2.0D;
            double distance = 4.0D + smith.getRandom().nextDouble() * Math.max(1, radius - 4);
            int x = Mth.floor(smith.getX() + Math.cos(angle) * distance);
            int z = Mth.floor(smith.getZ() + Math.sin(angle) * distance);
            for (int dy = 6; dy >= -6; dy--) {
                int y = Mth.floor(smith.getY()) + dy;
                BlockPos pos = new BlockPos(x, y, z);
                if (!server.isInWorldBounds(pos) || !server.getWorldBorder().isWithinBounds(pos)) {
                    continue;
                }
                BlockPos below = pos.below();
                if (server.getBlockState(below).getCollisionShape(server, below).isEmpty()) {
                    continue;
                }
                AABB bounds = smith.getBoundingBox().move(
                        pos.getX() + 0.5D - smith.getX(), pos.getY() - smith.getY(), pos.getZ() + 0.5D - smith.getZ());
                if (!server.noCollision(smith, bounds)) {
                    continue;
                }
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.playGoetySound("lich_teleport_out", 1.0F, 0.9F);
                smith.getNavigation().stop();
                smith.fallDistance = 0.0F;
                smith.setDeltaMovement(0.0D, 0.0D, 0.0D);
                smith.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.playGoetySound("lich_teleport_in", 1.0F, 0.9F);
                if (!smith.isNonChargeCast(smith.castAnimation)) {
                    smith.cancelCastingMove();
                }
                smith.noteTeleport();
                return true;
            }
        }
        return false;
    }

    public static void noteTeleport(AbstractGreenSmith smith) {
        smith.hitsSinceTeleport = 0;
        smith.forcedTeleportHits = AbstractGreenSmith.FORCED_TELEPORT_MIN_HITS
                + smith.getRandom().nextInt(AbstractGreenSmith.FORCED_TELEPORT_MAX_HITS - AbstractGreenSmith.FORCED_TELEPORT_MIN_HITS + 1);
    }

    public static int forcedTeleportHits(AbstractGreenSmith smith) {
        if (smith.forcedTeleportHits <= 0) {
            smith.noteTeleport();
        }
        return smith.forcedTeleportHits;
    }

    public static void teleportBackward(AbstractGreenSmith smith, ServerLevel server, double distance, boolean windBlast) {
        Vec3 look = smith.getLookAngle();
        for (int attempt = 0; attempt < 12; attempt++) {
            double dist = distance + attempt * 0.75D;
            int x = Mth.floor(smith.getX() - look.x * dist);
            int z = Mth.floor(smith.getZ() - look.z * dist);
            for (int dy = 4; dy >= -6; dy--) {
                int y = Mth.floor(smith.getY()) + dy;
                BlockPos pos = new BlockPos(x, y, z);
                if (!server.isInWorldBounds(pos) || !server.getWorldBorder().isWithinBounds(pos)) {
                    continue;
                }
                BlockPos below = pos.below();
                if (server.getBlockState(below).getCollisionShape(server, below).isEmpty()) {
                    continue;
                }
                AABB bounds = smith.getBoundingBox().move(
                        pos.getX() + 0.5D - smith.getX(), pos.getY() - smith.getY(), pos.getZ() + 0.5D - smith.getZ());
                if (!server.noCollision(smith, bounds)) {
                    continue;
                }
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.getNavigation().stop();
                smith.fallDistance = 0.0F;
                smith.setDeltaMovement(Vec3.ZERO);
                smith.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + 1.0D,
                        smith.getZ(), 24, 0.4D, 0.6D, 0.4D, 0.03D);
                smith.playGoetySound("lich_teleport_out", 1.0F, 0.9F);
                if (windBlast) {
                    WindBlastFx.cast(server, smith, smith.getX(), smith.getY() + 0.5D, smith.getZ(),
                            smith.windBlastRadius(3.0D), 0.6D);
                }
                if (smith.getHealth() <= smith.getMaxHealth() * 0.5F) {
                    smith.retreatHealTicks = AbstractGreenSmith.RETREAT_HEAL_WINDOW;
                }
                smith.noteTeleport();
                return;
            }
        }
    }

    public static boolean isSafeTeleportPos(AbstractGreenSmith smith, ServerLevel server, BlockPos pos) {
        if (!server.isInWorldBounds(pos) || !server.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }
        AABB bounds = smith.getBoundingBox().move(
                pos.getX() + 0.5D - smith.getX(), pos.getY() - smith.getY(), pos.getZ() + 0.5D - smith.getZ());
        if (!server.noCollision(smith, bounds)) {
            return false;
        }
        BlockPos below = pos.below();
        return !server.getBlockState(below).getCollisionShape(server, below).isEmpty()
                || server.getFluidState(pos).is(FluidTags.WATER);
    }

}