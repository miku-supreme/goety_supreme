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

final class WarlordBlazing {

    private WarlordBlazing() {
    }

    static void tickBlazing(AbstractWitherWarlord warlord) {
        if (warlord.skillTick < AbstractWitherWarlord.BLAZING_PILLAR_DELAY_TICKS) {
            warlord.lockInPlace();
            return;
        }
        if (warlord.skillTick == AbstractWitherWarlord.BLAZING_PILLAR_DELAY_TICKS) {
            warlord.startBlazingRain();
            return;
        }
        if (warlord.blazingRainTicks > 0) {
            warlord.blazingRainTicks--;
            warlord.lockInPlace();
            if (warlord.blazingRainTicks % AbstractWitherWarlord.BLAZING_RAIN_INTERVAL == 0) {
                warlord.applyBlazingRainDamage();
            }
            if (warlord.blazingRainTicks <= 0) {
                warlord.endSkill(40);
            }
            return;
        }
        if (warlord.skillTick >= AbstractWitherWarlord.BLAZING_PILLAR_DELAY_TICKS + AbstractWitherWarlord.BLAZING_RAIN_TICKS + 20) {
            warlord.endSkill(40);
        }
    }

    static void startBlazingRain(AbstractWitherWarlord warlord) {
        warlord.blazingRainTicks = AbstractWitherWarlord.BLAZING_RAIN_TICKS;
        warlord.lockInPlace();
        warlord.spawnDenseHellfirePillars(warlord.position(), AbstractWitherWarlord.BLAZING_RADIUS, AbstractWitherWarlord.BLAZING_RAIN_TICKS);
        warlord.playSound(SoundEvents.BLAZE_SHOOT, 2.0F, 0.6F);
    }

    static void applyBlazingRainDamage(AbstractWitherWarlord warlord) {
        if (warlord.level().isClientSide()) {
            return;
        }
        MobEffect burnHex = AbstractWitherWarlord.goetyEffect("burn_hex");
        AABB box = warlord.getBoundingBox().inflate(AbstractWitherWarlord.BLAZING_RADIUS);
        for (LivingEntity victim : warlord.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim == warlord || !victim.isAlive() || !warlord.canAttack(victim)) {
                continue;
            }
            int invulnerable = victim.invulnerableTime;
            victim.invulnerableTime = 0;
            victim.hurt(ModDamageSource.hellfire(warlord, warlord), AbstractWitherWarlord.BLAZING_RAIN_DAMAGE);
            victim.invulnerableTime = invulnerable;
            if (burnHex != null) {
                victim.addEffect(new MobEffectInstance(burnHex, AbstractWitherWarlord.BLAZING_BURN_HEX_TICKS,
                        0, false, true), warlord);
            }
        }
    }

    static void lockInPlace(AbstractWitherWarlord warlord) {
        warlord.getNavigation().stop();
        warlord.setDeltaMovement(0.0D, warlord.getDeltaMovement().y, 0.0D);
    }

    static void spawnDenseHellfirePillars(AbstractWitherWarlord warlord, Vec3 center, double radius, int duration) {
        warlord.spawnGoetyHellfirePillar(center.x, center.y, center.z, duration, true);
        for (int ring = 1; ring <= AbstractWitherWarlord.BLAZING_PILLAR_RINGS; ring++) {
            double r = radius * ring / AbstractWitherWarlord.BLAZING_PILLAR_RINGS;
            int spokes = 6 * ring;
            for (int i = 0; i < spokes; i++) {
                double angle = Math.PI * 2.0D * i / spokes;
                warlord.spawnGoetyHellfirePillar(center.x + Math.cos(angle) * r, center.y,
                        center.z + Math.sin(angle) * r, duration, true);
            }
        }
    }

    private static final int HELLFIRE_THROW_TICK = 25;

    private static final double SABER_FORWARD = 1.35D;
    private static final double SABER_SIDE = 0.55D;
    private static final double SABER_HEIGHT = 1.95D;

    static void tickHellfireSlash(AbstractWitherWarlord warlord) {
        if (warlord.skillTick == HELLFIRE_THROW_TICK) {
            warlord.spawnBladeBeam();
        }
        if (warlord.skillTick >= 40) {
            warlord.endSkill(160);
        }
    }

    static void spawnBladeBeam(AbstractWitherWarlord warlord) {
        Vec3 look = warlord.getLookAngle();
        if (look.lengthSqr() < 1.0E-6D) {
            look = new Vec3(0.0D, 0.0D, 1.0D);
        }
        look = look.normalize();
        LivingEntity aim = warlord.getTarget();
        if (aim != null && (!aim.isAlive() || aim == warlord)) {
            aim = null;
        }
        Vec3 origin = warlord.saberOrigin(look);
        if (aim != null) {
            Vec3 toTarget = aim.getEyePosition().subtract(origin);
            if (toTarget.lengthSqr() > 1.0E-4D) {
                look = toTarget.normalize();
            }
        }
        WarlordHellfireSlash slash = new WarlordHellfireSlash(warlord.level(), warlord);
        slash.setPos(origin.x, origin.y, origin.z);
        slash.shoot(look.x, look.y, look.z, (float) WarlordHellfireSlash.SPEED, 0.0F);
        if (aim != null) {
            slash.setTrackingTarget(aim);
        }
        warlord.level().addFreshEntity(slash);
        warlord.playSound(SoundEvents.WITHER_SHOOT, 2.0F, 0.5F);
    }

    static Vec3 saberOrigin(AbstractWitherWarlord warlord, Vec3 look) {
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }
        forward = forward.normalize();
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        return warlord.position()
                .add(forward.scale(SABER_FORWARD))
                .add(right.scale(SABER_SIDE))
                .add(0.0D, SABER_HEIGHT, 0.0D);
    }
}
