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

final class WarlordCharge {

    private WarlordCharge() {
    }

    static boolean tryTriggerCharge(AbstractWitherWarlord warlord) {
        LivingEntity target = warlord.getTarget();
        if (target == null || !target.isAlive()) {
            warlord.chargeAfterElite = false;
            return false;
        }
        double dist = warlord.distanceTo(target);
        if (warlord.chargeAfterElite) {
            warlord.chargeAfterElite = false;
            boolean close = dist <= AbstractWitherWarlord.CHARGE_NEAR_DISTANCE;
            if (!close || warlord.getRandom().nextInt(AbstractWitherWarlord.CHARGE_NEAR_CHANCE) == 0) {
                warlord.beginSkill(AbstractWitherWarlord.SKILL_CHARGE);
                return true;
            }
            return false;
        }
        int denominator = warlord.getLivingMinions().isEmpty()
                ? AbstractWitherWarlord.CHARGE_WITHOUT_MINIONS_CHANCE
                : AbstractWitherWarlord.CHARGE_WITH_MINIONS_CHANCE;
        if (warlord.getRandom().nextInt(denominator) == 0) {
            warlord.beginSkill(AbstractWitherWarlord.SKILL_CHARGE);
            return true;
        }
        return false;
    }

    static void tickCharge(AbstractWitherWarlord warlord) {
        if (warlord.skillTick == 1) {
            warlord.playSound(SoundEvents.RAVAGER_ROAR, 2.2F, 0.7F);
        }
        if (warlord.skillTick == AbstractWitherWarlord.CHARGE_HORN_TICK) {
            warlord.playSound(GeneratedMod.WARLORD_CHARGE_HORN.get(), 2.0F, 1.0F);
            warlord.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                    AbstractWitherWarlord.CHARGE_SPEED_TICKS, 0, false, true));
            boolean any = false;
            for (LivingEntity minion : warlord.getLivingMinions()) {
                any = true;
                minion.setHealth(Math.min(minion.getMaxHealth(),
                        minion.getHealth() + minion.getMaxHealth() * 0.5F));
                minion.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                        AbstractWitherWarlord.CHARGE_SPEED_TICKS, 0, false, true));
                MobEffect rallied = AbstractWitherWarlord.goetyEffect("rallied");
                if (rallied != null) {
                    minion.addEffect(new MobEffectInstance(rallied, 200, 1, false, true));
                }
            }
            if (!any) {
                MobEffect rallied = AbstractWitherWarlord.goetyEffect("rallied");
                if (rallied != null) {
                    warlord.addEffect(new MobEffectInstance(rallied, 200, 0, false, true));
                }
            }
        }
        if (warlord.skillTick >= 30) {
            warlord.endSkill(120);
        }
    }
}
