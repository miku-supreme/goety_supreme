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

import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

/** 绿豆死法名册的纯选型：小型首领、死法三人组、献祭目标。 */
public final class GreenSmithNecromancerRoster {

    private GreenSmithNecromancerRoster() {
    }

    public static void summonFocusHaunts(AbstractGreenSmith smith, ServerLevel server, Vec3 look, Vec3 right) {
        if (!OminousCompat.loaded()) {
            return;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(AbstractGreenSmith.HAUNT_ENTITY_ID);
        if (type == null) {
            return;
        }
        int count = Math.min(AbstractGreenSmith.HAUNT_FOCUS_BATCH, AbstractGreenSmith.HAUNT_FOCUS_CAP - smith.countHaunts(server));
        Vec3 back = new Vec3(look.x, 0.0D, look.z);
        back = back.lengthSqr() < 1.0E-4D ? new Vec3(0.0D, 0.0D, 1.0D) : back.normalize();
        for (int index = 0; index < count; ++index) {
            double side = index - (count - 1) / 2.0D;
            double x = smith.getX() - back.x * 1.0D + right.x * side;
            double z = smith.getZ() - back.z * 1.0D + right.z * side;
            double y = smith.getY();
            Entity entity = type.create(server);
            if (!(entity instanceof Summoned haunt)) {
                if (entity != null) {
                    entity.discard();
                }
                continue;
            }
            haunt.setTrueOwner(smith.summonOwner());
            haunt.moveTo(x, y, z, smith.getYRot(), 0.0F);
            haunt.finalizeSpawn(server, server.getCurrentDifficultyAt(haunt.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            haunt.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS
                    + smith.soulJarLifespanBonusTicks());
            haunt.setPersistenceRequired();
            smith.applySoulJarSummonBuffs(haunt);
            SummonCapUtil.markDirectSummon(haunt, smith);
            ExtraSummonWindup.Kind kind = ExtraSummonWindup.summon(server, haunt,
                    new Vec3(x, y, z), smith);
            if (kind == ExtraSummonWindup.Kind.FAILED) {
                continue;
            }
            if (kind == ExtraSummonWindup.Kind.GROUND_CIRCLE) {
                ExtraSummonWindup.playSound(haunt, smith);
            }
            GoetySummonFx.play(server, haunt);
        }
    }


    public static void summonFocusWraithsOrDruids(AbstractGreenSmith smith, ServerLevel server, Vec3 right) {
        if (smith.spookyFocusGiven
                && smith.pruneFamiliars(server, smith.spookyFamiliars) < AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
            summonFocusWraiths(smith, server, right);
            return;
        }
        if (smith.boneDruidFocusGiven
                && smith.pruneFamiliars(server, smith.boneDruidFamiliars) < AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
            summonFocusDruids(smith, server, right);
        }
    }

    public static void summonFocusDruids(AbstractGreenSmith smith, ServerLevel server, Vec3 right) {
        int alive = smith.pruneFamiliars(server, smith.boneDruidFamiliars);
        double[] sides = {-1.0D, 1.0D};
        for (double side : sides) {
            if (alive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                return;
            }
            double x = smith.getX() + right.x * side;
            double z = smith.getZ() + right.z * side;
            if (smith.summonFamiliar(server, AbstractGreenSmith.SKELETON_DRUID_ID, x, smith.getY(), z,
                    smith.boneDruidFamiliars)) {
                ++alive;
            }
        }
    }

    public static void summonFocusWraiths(AbstractGreenSmith smith, ServerLevel server, Vec3 right) {
        int alive = smith.pruneFamiliars(server, smith.spookyFamiliars);
        double[] sides = {-1.0D, 1.0D};
        for (double side : sides) {
            if (alive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                return;
            }
            double x = smith.getX() + right.x * side;
            double z = smith.getZ() + right.z * side;
            if (smith.summonFamiliar(server, AbstractGreenSmith.WRAITH_SERVANT_ID, x, smith.getY(), z,
                    smith.spookyFamiliars)) {
                ++alive;
            }
        }
    }

    public static void summonFocusReapers(AbstractGreenSmith smith, ServerLevel server, Vec3 right, List<Summoned> necromancers) {
        boolean atReapers = smith.reapingFocusGiven && !necromancers.isEmpty();
        boolean useCube = smith.redstoneFocusGiven
                && ForgeRegistries.ENTITY_TYPES.containsKey(AbstractGreenSmith.GRAVE_CUBE_ID)
                && (!smith.reapingFocusGiven || smith.getRandom().nextBoolean());
        if (!atReapers && !useCube) {
            return;
        }
        int alive = smith.pruneFamiliars(server, smith.reaperFamiliars);
        int cubeAlive = smith.pruneFamiliars(server, smith.redstoneFamiliars);
        double centerX;
        double centerY;
        double centerZ;
        if (atReapers) {
            Summoned first = necromancers.get(0);
            Summoned last = necromancers.get(necromancers.size() - 1);
            centerX = (first.getX() + last.getX()) * 0.5D;
            centerY = first.getY();
            centerZ = (first.getZ() + last.getZ()) * 0.5D;
        } else {
            centerX = smith.getX();
            centerY = smith.getY();
            centerZ = smith.getZ();
        }
        double[] sides = {-1.0D, 1.0D};
        for (double side : sides) {
            double x = centerX + right.x * side * 1.0D;
            double z = centerZ + right.z * side * 1.0D;
            if (useCube) {
                if (cubeAlive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                    return;
                }
                if (smith.summonFamiliar(server, AbstractGreenSmith.GRAVE_CUBE_ID, x, centerY, z,
                        smith.redstoneFamiliars)) {
                    ++cubeAlive;
                }
            } else {
                if (alive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                    return;
                }
                if (smith.summonFamiliar(server, AbstractGreenSmith.REAPER_SERVANT_ID, x, centerY, z,
                        smith.reaperFamiliars)) {
                    ++alive;
                }
            }
        }
    }

    public static void summonFocusPhantoms(AbstractGreenSmith smith, ServerLevel server, Vec3 right) {
        boolean useVex = smith.vexFocusGiven
                && ForgeRegistries.ENTITY_TYPES.containsKey(AbstractGreenSmith.TWILIGHT_WRAITH_ID)
                && (!smith.phantasmFocusGiven || smith.getRandom().nextBoolean());
        if (!useVex && !smith.phantasmFocusGiven) {
            return;
        }
        int alive = smith.pruneFamiliars(server, smith.phantomFamiliars);
        int vexAlive = smith.pruneFamiliars(server, smith.vexFamiliars);
        double[] sides = {-1.0D, 1.0D};
        for (double side : sides) {
            double x = smith.getX() + right.x * side;
            double z = smith.getZ() + right.z * side;
            double y = smith.getY() + smith.getBbHeight() + AbstractGreenSmith.PHANTOM_SPAWN_HEIGHT;
            if (useVex) {
                if (vexAlive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                    return;
                }
                if (smith.summonFamiliar(server, AbstractGreenSmith.TWILIGHT_WRAITH_ID, x, y, z,
                        smith.vexFamiliars)) {
                    ++vexAlive;
                }
            } else {
                if (alive >= AbstractGreenSmith.MAX_FOCUS_FAMILIARS) {
                    return;
                }
                if (smith.summonPhantom(server, x, y, z, AbstractGreenSmith.PHANTOM_POTENCY, smith.phantomFamiliars)) {
                    ++alive;
                }
            }
        }
    }


    public static void castSummonNecromancers(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        smith.tickNecromancerRoster();
        boolean directSummon = smith.directSummonToMaster;
        boolean hasSmallBoss = false;
        boolean hasNormalNecromancer = false;
        if (!directSummon) {
            for (UUID id : smith.necromancers) {
                if (server.getEntity(id) instanceof LivingEntity living && living.isAlive()
                        && AbstractGreenSmith.isNecromancerLike(living)) {
                    if (AbstractGreenSmith.isSmallBossNecromancer(living)) {
                        hasSmallBoss = true;
                    } else {
                        hasNormalNecromancer = true;
                    }
                }
            }
        }
        boolean pityReady = smith.smallBossPity >= 3;
        boolean pityDeferred = false;
        ResourceLocation chosenSmallBoss = smith.pickSmallBossType();
        ResourceLocation smallBossType = null;
        if (chosenSmallBoss != null) {
            if (directSummon) {
                if (pityReady) {
                    if (smith.ownerHasRoomForSmallBoss(server, chosenSmallBoss)) {
                        smallBossType = chosenSmallBoss;
                    } else {
                        pityDeferred = true;
                    }
                } else if (smith.getRandom().nextFloat() < AbstractGreenSmith.SMALL_BOSS_SUMMON_CHANCE
                        && smith.ownerHasRoomForSmallBoss(server, chosenSmallBoss)) {
                    smallBossType = chosenSmallBoss;
                }
            } else if (pityReady) {
                if (!hasSmallBoss && !hasNormalNecromancer) {
                    smallBossType = chosenSmallBoss;
                } else {
                    pityDeferred = true;
                }
            } else if (!hasSmallBoss && !hasNormalNecromancer
                    && smith.getRandom().nextFloat() < AbstractGreenSmith.SMALL_BOSS_SUMMON_CHANCE) {
                smallBossType = chosenSmallBoss;
            }
        }
        ResourceLocation[] backRowBase = {
                AbstractGreenSmith.CAIRN_NECROMANCER_SERVANT_ID, AbstractGreenSmith.MOSSY_NECROMANCER_SERVANT_ID, AbstractGreenSmith.NECROMANCER_SERVANT_ID};
        ResourceLocation[][] ranks = {
                {AbstractGreenSmith.BLACKGUARD_SERVANT_ID, AbstractGreenSmith.BLACKGUARD_SERVANT_ID, AbstractGreenSmith.BLACKGUARD_SERVANT_ID},
                {AbstractGreenSmith.VANGUARD_SERVANT_ID, AbstractGreenSmith.VANGUARD_SERVANT_ID, AbstractGreenSmith.VANGUARD_SERVANT_ID},
                smith.pickNecromancerTrio(backRowBase),
        };
        ResourceLocation[][] baseRanks = {ranks[0], ranks[1], backRowBase};
        String[] categories = {"blackguard", "vanguard", "necromancer"};
        int[] caps = {AbstractGreenSmith.MAX_BLACKGUARDS, AbstractGreenSmith.MAX_VANGUARDS, AbstractGreenSmith.MAX_NECROMANCERS};
        if (hasSmallBoss) {
            caps[2] = 0;
        } else if (smallBossType != null) {
            ranks[2] = new ResourceLocation[]{smallBossType, smallBossType, smallBossType};
            baseRanks[2] = ranks[2];
            caps[2] = 1;
        }
        Vec3 look = smith.getLookAngle();
        Vec3 right = new Vec3(-look.z, 0.0D, look.x).normalize();
        boolean anySpawned = false;
        boolean smallBossSpawned = false;
        List<Summoned> summonedNecromancers = new ArrayList<>();
        for (int row = 0; row < ranks.length; row++) {
            int slots = Math.max(0, Math.min(3, caps[row] - smith.countServantsOf(server, categories[row])));
            for (int i = -1; i <= 1 && slots > 0; i++) {
                ResourceLocation servantId = ranks[row][i + 1];
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(servantId);
                if (type == null) {
                    continue;
                }
                Entity entity = type.create(server);
                if (!(entity instanceof Summoned minion)) {
                    continue;
                }
                double forward = 2.0D + (ranks.length - 1 - row) * 2.0D;
                double side = i * 2.0D;
                double x = smith.getX() + look.x * forward + right.x * side;
                double z = smith.getZ() + look.z * forward + right.z * side;
                minion.setTrueOwner(smith.summonOwner());
                double y = MinionGroundPlacement.groundFeetY(server, x, z, smith.getY(),
                        MinionGroundPlacement.SKELETON_CLEARANCE);
                minion.moveTo(x, y, z, smith.getYRot(), 0.0F);
                minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                        MobSpawnType.MOB_SUMMONED, null, null);
                minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS
                        + smith.soulJarLifespanBonusTicks());
                minion.setPersistenceRequired();
                smith.applySoulJarSummonBuffs(minion);
                AbstractGreenSmith.markGreenSmithNecromancer(minion, smith);
                if (minion instanceof AbstractNecromancer necromancer) {
                    ResourceLocation minionId = ForgeRegistries.ENTITY_TYPES.getKey(minion.getType());
                    if (AbstractGreenSmith.isSunkenNecromancer(minionId)) {
                        EntityType<?> drowned = ForgeRegistries.ENTITY_TYPES.getValue(AbstractGreenSmith.DROWNED_SERVANT_ID);
                        if (drowned != null) {
                            necromancer.addSummon(drowned);
                        }
                    } else if (AbstractGreenSmith.isBoneFocusNecromancer(baseRanks[row][i + 1])) {
                        necromancer.addSummon(ModEntityType.SKELETON_SERVANT.get());
                    } else if (AbstractGreenSmith.CAIRN_NECROMANCER_SERVANT_ID.equals(minionId)) {
                        necromancer.addSummon(ModEntityType.ZOMBIE_SERVANT.get());
                    }
                }
                if (server.addFreshEntity(minion)) {
                    smith.necromancers.add(minion.getUUID());
                    GoetySummonFx.play(server, minion);
                    if (row == ranks.length - 1) {
                        summonedNecromancers.add(minion);
                        if (smallBossType != null) {
                            smallBossSpawned = true;
                        }
                    }
                    --slots;
                    anySpawned = true;
                    server.sendParticles(GreenSmithFx.necroFire(), x, y + 0.4D, z,
                            16, 0.4D, 0.5D, 0.4D, 0.01D);
                }
            }
        }
        if (smith.spookyFocusGiven || smith.boneDruidFocusGiven) {
            summonFocusWraithsOrDruids(smith, server, right);
        }
        if (smith.reapingFocusGiven || smith.redstoneFocusGiven) {
            smith.summonFocusReapers(server, right, summonedNecromancers);
        }
        if (smith.phantasmFocusGiven || smith.vexFocusGiven) {
            summonFocusPhantoms(smith, server, right);
        }
        if (smith.hauntFocusGiven) {
            summonFocusHaunts(smith, server, look, right);
        }
        if (smallBossSpawned) {
            smith.smallBossPity = 0;
        } else if (!pityDeferred) {
            smith.smallBossPity = Math.min(3, smith.smallBossPity + 1);
        }
        smith.playGoetySound("necromancer_summon", 1.4F, 0.7F);
        if (anySpawned) {
            smith.playGoetySound("lich_laugh", 1.2F, 0.7F);
            smith.playGoetySound("lich_teleport_in", 1.0F, 0.75F);
        }
    }


    public static ResourceLocation pickSmallBossType(AbstractGreenSmith smith) {
        if (smith.smallBossSoulJarTypes.isEmpty()) {
            return null;
        }
        return smith.smallBossSoulJarTypes.get(smith.getRandom().nextInt(smith.smallBossSoulJarTypes.size()));
    }

    public static ResourceLocation[] pickNecromancerTrio(AbstractGreenSmith smith, ResourceLocation[] base) {
        ResourceLocation[] trio = base.clone();
        Set<ResourceLocation> used = new LinkedHashSet<>(Arrays.asList(base));
        ResourceLocation draugr = smith.availableDraugrServant();
        ResourceLocation sunken = smith.availableSunkenServant();
        for (int i = 0; i < trio.length; i++) {
            ResourceLocation pick = null;
            if (draugr != null && smith.getRandom().nextFloat() < AbstractGreenSmith.DRAUGR_REPLACEMENT_CHANCE) {
                pick = draugr;
            } else if (sunken != null && smith.getRandom().nextFloat() < AbstractGreenSmith.OMINOUS_REPLACEMENT_CHANCE) {
                pick = sunken;
            }
            if (pick == null || used.contains(pick)) {
                continue;
            }
            used.remove(trio[i]);
            trio[i] = pick;
            used.add(pick);
        }
        return trio;
    }

    public static LivingEntity findSacrifice(AbstractGreenSmith smith, ServerLevel server, AbstractGreenSmith master) {
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (Summoned minion : server.getEntitiesOfClass(Summoned.class,
                smith.getBoundingBox().inflate(48.0D))) {
            if (minion == smith || minion == master || !minion.isAlive()
                    || minion instanceof AbstractGreenSmith
                    || minion.getTrueOwner() != master) {
                continue;
            }
            double dist = smith.distanceToSqr(minion);
            if (dist < bestDist) {
                bestDist = dist;
                best = minion;
            }
        }
        return best;
    }
}
