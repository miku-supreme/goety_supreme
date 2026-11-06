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

/** 绿豆分身连段：风爆分身、连段施法、治疗与收尾。 */
public final class GreenSmithClones {

    static final double CLONE_SLOT_RADIUS = 3.0D;

    private GreenSmithClones() {
    }

    public static void startCloneCombo(AbstractGreenSmith smith) {
        smith.cloneComboPending = false;
        smith.cloneHealClaimed = false;
        List<AbstractGreenSmith> clones = smith.livingWindClones();
        smith.windClones.clear();
        if (clones.isEmpty()) {
            return;
        }
        int[] pool = smith.cloneComboPool();
        int spell = pool[smith.getRandom().nextInt(pool.length)];
        int borrowSkill = smith.getRandom().nextInt(AbstractGreenSmith.BORROW_SKILLS);
        switch (spell) {
            case AbstractGreenSmith.SPELL_SUMMON -> {
                smith.summonCooldown = AbstractGreenSmith.SUMMON_COOLDOWN;
                smith.startCast(AbstractGreenSmith.CAST_SUMMON);
            }
            case AbstractGreenSmith.SPELL_BORROW -> {
                smith.borrowCooldown = AbstractGreenSmith.BORROW_COOLDOWN;
                smith.borrowedSkill = borrowSkill;
                smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_BORROW, false);
                smith.castImpact = smith.scaledCharge(AbstractGreenSmith.BORROW_CHARGE_TICKS);
            }
            case AbstractGreenSmith.SPELL_LASER -> {
                smith.laserCooldown = AbstractGreenSmith.LASER_COOLDOWN;
                smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_LASER_CHARGE, false);
                smith.castImpact = smith.scaledCharge(AbstractGreenSmith.LASER_CHARGE_TICKS);
            }
            case AbstractGreenSmith.SPELL_RALLY -> {
                smith.rallyCooldown = AbstractGreenSmith.RALLY_COOLDOWN;
                smith.startCast(AbstractGreenSmith.CAST_RALLY);
            }
            case AbstractGreenSmith.SPELL_CLOUD -> {
                smith.cloudCooldown = AbstractGreenSmith.CLOUD_COOLDOWN;
                smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_CLOUD, false);
            }
            default -> {
                return;
            }
        }
        int impact = smith.castImpact;
        for (AbstractGreenSmith clone : clones) {
            clone.beginCloneCombo(spell, borrowSkill, impact);
        }
    }

    public static void beginCloneCombo(AbstractGreenSmith smith, int spell, int borrowSkill, int impactTicks) {
        if (!smith.isClone() || smith.isCasting()) {
            return;
        }
        switch (spell) {
            case AbstractGreenSmith.SPELL_SUMMON -> smith.startCast(AbstractGreenSmith.CAST_SUMMON, AbstractGreenSmith.EFFECT_CLONE_SUMMON, true);
            case AbstractGreenSmith.SPELL_BORROW -> {
                smith.borrowedSkill = borrowSkill;
                smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_CLONE_BORROW, true);
            }
            case AbstractGreenSmith.SPELL_LASER -> smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_LASER_CHARGE, true);
            case AbstractGreenSmith.SPELL_RALLY -> smith.startCast(AbstractGreenSmith.CAST_RALLY, AbstractGreenSmith.EFFECT_CLONE_HEAL, true);
            case AbstractGreenSmith.SPELL_CLOUD -> smith.startCast(AbstractGreenSmith.CAST_CAST, AbstractGreenSmith.EFFECT_CLONE_CLOUD, true);
            default -> {
                return;
            }
        }
        smith.castImpact = Math.max(1, impactTicks);
    }

    public static List<AbstractGreenSmith> livingWindClones(AbstractGreenSmith smith) {
        List<AbstractGreenSmith> clones = new ArrayList<>();
        if (!(smith.level() instanceof ServerLevel server)) {
            return clones;
        }
        for (UUID id : smith.windClones) {
            if (server.getEntity(id) instanceof AbstractGreenSmith clone
                    && clone.isClone() && clone.isAlive() && !clone.isRemoved()) {
                clones.add(clone);
            }
        }
        return clones;
    }

    public static void tickWindClones(AbstractGreenSmith smith) {
        if (smith.windClones.isEmpty()) {
            return;
        }
        if (!(smith.level() instanceof ServerLevel server)) {
            smith.windClones.clear();
            return;
        }
        smith.windClones.removeIf(id -> !(server.getEntity(id) instanceof AbstractGreenSmith clone)
                || !clone.isAlive() || !clone.isClone());
        for (UUID id : smith.windClones) {
            if (server.getEntity(id) instanceof AbstractGreenSmith clone) {
                pinToSlot(smith, clone);
            }
        }
    }

    static void pinToSlot(AbstractGreenSmith master, AbstractGreenSmith clone) {
        double angle = Math.toRadians(clone.cloneSlot * AbstractGreenSmith.CLONE_LASER_STEP_DEGREES);
        double x = master.getX() + Math.cos(angle) * CLONE_SLOT_RADIUS;
        double z = master.getZ() + Math.sin(angle) * CLONE_SLOT_RADIUS;
        clone.getNavigation().stop();
        clone.setDeltaMovement(0.0D, 0.0D, 0.0D);
        clone.setPos(x, master.getY(), z);
        clone.fallDistance = 0.0F;
    }

    public static void abortCloneCombo(AbstractGreenSmith smith) {
        smith.cloneComboPending = false;
        if (smith.windClones.isEmpty()) {
            return;
        }
        if (smith.level() instanceof ServerLevel server) {
            for (UUID id : smith.windClones) {
                if (server.getEntity(id) instanceof AbstractGreenSmith clone
                        && clone.isClone() && !clone.isRemoved()) {
                    clone.dismissClone();
                }
            }
        }
        smith.windClones.clear();
    }

    public static void cloneSummonSkeleton(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        smith.summonSkeletonServant(server, smith.getX(), smith.getY(), smith.getZ());
        smith.playGoetySound("lich_laugh", 1.0F, 0.8F);
        smith.cloneDismissPending = true;
    }

    public static void cloneHealFocus(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        smith.sendSoulHealShockwave(server, smith.getX(), smith.getY(), smith.getZ());
        smith.sendSoulHealRings(server, smith);
        smith.playGoetySound("soul_heal", 1.2F, 1.0F);
        AbstractGreenSmith master = smith.master();
        if (master.isAlive() && master.claimCloneHeal()) {
            smith.applyCloneHealFocus(server, master);
        }
        smith.cloneDismissPending = true;
    }

    public static boolean claimCloneHeal(AbstractGreenSmith smith) {
        if (smith.cloneHealClaimed) {
            return false;
        }
        smith.cloneHealClaimed = true;
        return true;
    }

    public static void applyCloneHealFocus(AbstractGreenSmith smith, ServerLevel server, AbstractGreenSmith master) {
        float healAmount = AbstractGreenSmith.CLONE_HEAL_AMOUNT
                + master.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_CLONE_HEAL_PER_LEVEL;
        smith.healCloneTarget(server, master, healAmount);
        for (LivingEntity ally : server.getEntitiesOfClass(LivingEntity.class,
                master.getBoundingBox().inflate(AbstractGreenSmith.CLONE_HEAL_RADIUS))) {
            if (ally == master || !(ally instanceof Summoned) || !master.isMyAlly(ally)) {
                continue;
            }
            smith.healCloneTarget(server, ally, healAmount);
        }
    }

    public static void healCloneTarget(AbstractGreenSmith smith, ServerLevel server, LivingEntity target, float healAmount) {
        boolean blocked = target instanceof AbstractGreenSmith cloneSmith && cloneSmith.isAntiHealActive();
        if (!blocked && target.getHealth() < target.getMaxHealth()) {
            target.heal(healAmount);
        }
        smith.sendSoulHealRings(server, target);
    }

    public static void cloneBorrowedSkill(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        switch (smith.borrowedSkill) {
            case 0 -> smith.beginCloneBurst(AbstractGreenSmith.CLONE_BURST_STEAM);
            case 1 -> smith.beginCloneBurst(AbstractGreenSmith.CLONE_BURST_POISON);
            case 2 -> smith.cloneSnap();
            default -> {
                smith.cloneHauntedSkull(server);
                smith.cloneDismissPending = true;
            }
        }
    }

    public static void beginCloneBurst(AbstractGreenSmith smith, int kind) {
        smith.cloneBurstKind = kind;
        smith.cloneBurstRemaining = kind == AbstractGreenSmith.CLONE_BURST_STEAM ? AbstractGreenSmith.CLONE_STEAM_SHOTS : AbstractGreenSmith.CLONE_POISON_SHOTS;
        smith.cloneBurstTimer = 1;
        smith.faceCloneTarget();
    }

    public static void tickCloneBurst(AbstractGreenSmith smith) {
        if (smith.cloneBurstRemaining <= 0 || !(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (--smith.cloneBurstTimer > 0) {
            return;
        }
        smith.cloneBurstTimer = AbstractGreenSmith.CLONE_BURST_INTERVAL;
        smith.faceCloneTarget();
        if (smith.cloneBurstKind == AbstractGreenSmith.CLONE_BURST_STEAM) {
            smith.fireCloneSteamMissile(server);
        } else {
            smith.fireClonePoisonDart(server);
        }
        if (--smith.cloneBurstRemaining <= 0) {
            smith.cloneDismissPending = true;
        }
    }

    public static void faceCloneTarget(AbstractGreenSmith smith) {
        LivingEntity target = smith.getTarget();
        if (target != null && target.isAlive()) {
            float[] aim = smith.targetAngles(target);
            smith.setYRot(aim[0]);
            smith.setXRot(aim[1]);
            smith.yBodyRot = aim[0];
            smith.yHeadRot = aim[0];
            smith.setYHeadRot(aim[0]);
        }
    }

    public static void fireCloneSteamMissile(AbstractGreenSmith smith, ServerLevel server) {
        Vec3 dir = smith.cloneAimDirection();
        double spread = 0.03D;
        double dx = dir.x + (smith.getRandom().nextDouble() - 0.5D) * spread;
        double dy = dir.y + (smith.getRandom().nextDouble() - 0.5D) * spread;
        double dz = dir.z + (smith.getRandom().nextDouble() - 0.5D) * spread;
        Vec3 v = new Vec3(dx, dy, dz).normalize();
        float speedScale = smith.soulJarProjectileSpeedScale();
        SteamMissile missile = new SteamMissile(smith, v.x * speedScale, v.y * speedScale,
                v.z * speedScale, server);
        missile.setPos(smith.getX() + v.x * 0.6D, smith.getEyeY() - 0.2D, smith.getZ() + v.z * 0.6D);
        server.addFreshEntity(missile);
    }

    public static void fireClonePoisonDart(AbstractGreenSmith smith, ServerLevel server) {
        PoisonQuill quill = new PoisonQuill(server, smith);
        quill.setSpear(false, AbstractGreenSmith.POISON_DART_PIERCE);
        quill.setExtraDamage(AbstractGreenSmith.POISON_DART_EXTRA_BASE + smith.effectiveSoulJarDamageBonus());
        quill.setDuration(0);
        quill.shootFromRotation(smith, smith.getXRot(), smith.getYRot(), 0.0F,
                1.6F * smith.soulJarProjectileSpeedScale(), 1.0F);
        server.addFreshEntity(quill);
        smith.playGoetySound("poison_quill_vine_shoot", 1.0F, 1.0F);
    }

    public static Vec3 cloneAimDirection(AbstractGreenSmith smith) {
        LivingEntity target = smith.getTarget();
        Vec3 dir = target != null && target.isAlive()
                ? new Vec3(target.getX() - smith.getX(), target.getEyeY() - smith.getEyeY(),
                        target.getZ() - smith.getZ())
                : smith.getLookAngle();
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = smith.getLookAngle();
        }
        return dir.normalize();
    }

    public static void cloneSnap(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        smith.playSound(GeneratedMod.REAPER_SNAP.get(), 2.0F, 1.0F);
        AbstractGreenSmith master = smith.master();
        LivingEntity victim = smith.findSacrifice(server, master);
        if (victim != null) {
            double bx = victim.getX();
            double by = victim.getY() + victim.getBbHeight() * 0.5D;
            double bz = victim.getZ();
            float heal = victim.getMaxHealth() * 0.25F;
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, bx, by, bz,
                    18, 0.4D, 0.6D, 0.4D, 0.02D);
            victim.discard();
            if (master.isAlive() && !master.isAntiHealActive()) {
                master.heal(heal);
                server.sendParticles(ParticleTypes.HEART, master.getX(),
                        master.getY() + master.getBbHeight() * 0.8D, master.getZ(),
                        3, 0.3D, 0.3D, 0.3D, 0.0D);
            }
        }
        for (ReaperSpinEntity spin : server.getEntitiesOfClass(ReaperSpinEntity.class,
                smith.getBoundingBox().inflate(48.0D))) {
            if (spin.isOwnedBy(master)) {
                spin.detonate();
            }
        }
        smith.cloneDismissPending = true;
    }

    public static void cloneHauntedSkull(AbstractGreenSmith smith, ServerLevel server) {
        Vec3 base = smith.cloneAimDirection();
        double skullSpeed = smith.soulJarProjectileSpeedScale();
        HauntedSkullProjectile skull = new HauntedSkullProjectile(smith,
                base.x * skullSpeed, base.y * skullSpeed, base.z * skullSpeed, server);
        skull.setPos(smith.getX() + base.x * 0.8D, smith.getEyeY() + 0.4D, smith.getZ() + base.z * 0.8D);
        smith.configureSkull(skull);
        server.addFreshEntity(skull);
        server.sendParticles(ParticleTypes.WHITE_ASH, skull.getX(), skull.getY(), skull.getZ(),
                8, 0.2D, 0.2D, 0.2D, 0.02D);
        smith.playGoetySound("skull_lord_shoot", 1.3F, 1.0F);
    }

    public static void cloneCorruptLaser(AbstractGreenSmith smith, ServerLevel server) {
        float base = 0.0F;
        float yaw = base + smith.cloneSlot * AbstractGreenSmith.CLONE_LASER_STEP_DEGREES;
        smith.setYRot(yaw);
        smith.setXRot(0.0F);
        smith.yBodyRot = yaw;
        smith.yBodyRotO = yaw;
        smith.yHeadRot = yaw;
        smith.yHeadRotO = yaw;
        smith.laserTarget = null;
        smith.laserTicks = AbstractGreenSmith.CLONE_LASER_TICKS
                + smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_CLONE_LASER_TICKS_PER_LEVEL;
        smith.playGoetySound("corrupt_beam_loop", 1.2F, 0.75F);
        smith.spawnCorruptedBeam(server);
    }

    public static void cloneLightningOrb(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server) || !AbstractGreenSmith.ominousLoaded()) {
            return;
        }
        smith.faceCloneTarget();
        Vec3 dir = smith.cloneAimDirection();
        double spawnX = smith.getX() + dir.x * 0.5D;
        double spawnY = smith.getEyeY() - 0.2D;
        double spawnZ = smith.getZ() + dir.z * 0.5D;
        float spread = (float) Math.toRadians(AbstractGreenSmith.CLONE_ORB_SPREAD_DEGREES);
        float orbSpeed = smith.soulJarProjectileSpeedScale();
        for (int i = -1; i <= 1; ++i) {
            float rad = i * spread;
            float cos = Mth.cos(rad);
            float sin = Mth.sin(rad);
            double bx = dir.x * cos - dir.z * sin;
            double bz = dir.x * sin + dir.z * cos;
            double by = dir.y + Math.sqrt(bx * bx + bz * bz) * 0.1D;
            SurgingOrb orb = new SurgingOrb(spawnX, spawnY, spawnZ,
                    bx * orbSpeed, by * orbSpeed, bz * orbSpeed, server);
            orb.setOwner(smith);
            orb.setExtraDamage(AbstractGreenSmith.CLONE_ORB_EXTRA_DAMAGE + smith.soulJarDamageBonus());
            if (GoetyCompat.hasSurgingOrbOrange()) {
                orb.setOrange(false);
            }
            server.addFreshEntity(orb);
        }
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, spawnX, spawnY, spawnZ,
                18, 0.25D, 0.25D, 0.25D, 0.12D);
        smith.playGoetySound("shock_cast", 1.0F, 0.5F);
        smith.cloneDismissPending = true;
    }

    public static int[] cloneComboPool(AbstractGreenSmith smith) {
        if (!AbstractGreenSmith.ominousLoaded()) {
            return AbstractGreenSmith.CLONE_COMBO_SPELLS;
        }
        int[] pool = Arrays.copyOf(AbstractGreenSmith.CLONE_COMBO_SPELLS, AbstractGreenSmith.CLONE_COMBO_SPELLS.length + 1);
        pool[pool.length - 1] = AbstractGreenSmith.SPELL_CLOUD;
        return pool;
    }

    public static void dismissClone(AbstractGreenSmith smith) {
        smith.cloneDismissPending = false;
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(GreenSmithFx.lich(), smith.getX(), smith.getY() + smith.getBbHeight() * 0.5D,
                    smith.getZ(), 30, 0.5D, 0.8D, 0.5D, 0.03D);
            smith.playGoetySound("lich_teleport_out", 1.0F, 0.9F);
        }
        smith.discard();
    }

}