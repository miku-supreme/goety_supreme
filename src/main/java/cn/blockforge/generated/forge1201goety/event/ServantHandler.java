package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.util.ServantRobeFireDamage;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.util.SpellSnowball;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ServantHandler {

    public static final String SNOWBALL_HEAL_TAG = "GoetySupremeSnowballHeal";

    private ServantHandler() {
    }

    @SubscribeEvent
    public static void onSnowballPassThrough(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Snowball snowball)
                || !(event.getRayTraceResult() instanceof EntityHitResult hitResult)) {
            return;
        }
        Entity hit = hitResult.getEntity();
        boolean mutantLoaded = MutantMonstersCompat.isLoaded();
        if (snowball.getOwner() instanceof SnowGolemServant owner && hit instanceof LivingEntity living) {
            if (owner.isAlliedTo(living)) {
                if (!snowball.level().isClientSide()) {
                    if (mutantLoaded && living instanceof MutantSnowGolemServant mutant) mutant.feedSnowball();
                    else if (living instanceof SnowGolemServant) living.heal(1.0F);
                }
                event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
            } else {
                if (!snowball.level().isClientSide()) {
                    living.hurt(snowball.damageSources().thrown(snowball, owner),
                            SnowballVolley.damage(owner.getSnowballPotency(), living));
                    owner.applySnowballEffect(living);
                    snowball.level().broadcastEntityEvent(snowball, (byte) 3);
                    snowball.discard();
                }
                event.setImpactResult(ProjectileImpactEvent.ImpactResult.STOP_AT_CURRENT_NO_DAMAGE);
            }
            return;
        }
        if (snowball instanceof SpellSnowball spellBall && hit instanceof LivingEntity living) {
            LivingEntity thrower = snowball.getOwner() instanceof LivingEntity entityOwner ? entityOwner : null;
            if (thrower == null) {
                return;
            }
            if (SnowballVolley.isAlliedTo(thrower, living)) {
                if (!snowball.level().isClientSide() && mutantLoaded
                        && living instanceof MutantSnowGolemServant mutant) {
                    mutant.feedSnowball();
                }
                event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
            } else {
                if (!snowball.level().isClientSide()) {
                    living.hurt(snowball.damageSources().thrown(snowball, thrower),
                            SnowballVolley.damage(spellBall.getBaseDamage(),
                                    spellBall.getPotency(), living));
                    SnowballVolley.applyEffect(thrower, spellBall.getPotency(), living);
                    snowball.level().broadcastEntityEvent(snowball, (byte) 3);
                    snowball.discard();
                }
                event.setImpactResult(ProjectileImpactEvent.ImpactResult.STOP_AT_CURRENT_NO_DAMAGE);
            }
            return;
        }
        if (mutantLoaded && hit instanceof MutantSnowGolemServant servant) {
            if (!snowball.level().isClientSide()) {
                net.minecraft.nbt.CompoundTag data = snowball.getPersistentData();
                float heal = data.contains(SNOWBALL_HEAL_TAG) ? data.getFloat(SNOWBALL_HEAL_TAG) : 1.0F;
                servant.feedSnowball(heal);
            }
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        }
    }

    @SubscribeEvent
    public static void onServantTridentImpact(LivingHurtEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ThrownTrident)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof PlayerMobServant servant)) {
            return;
        }
        float configured = PlayerMobServantConfig.TRIDENT_THROW_DAMAGE.get().floatValue()
                + servant.getSummonPotency() * PlayerMobServant.POTENCY_RANGED_DAMAGE_BONUS;
        event.setAmount(Math.max(0.0F, event.getAmount() - 8.0F + configured));
    }

    public static boolean isServantOf(Mob mob, Player player) {
        if (!(mob instanceof IOwned owned) || player == null) {
            return false;
        }
        return player.equals(owned.getTrueOwner());
    }

    @SubscribeEvent
    public static void onWarlordEliteArrowImpact(LivingHurtEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof WitherSkeletonServant skeleton)) {
            return;
        }
        if (!(skeleton.getTrueOwner() instanceof AbstractWitherWarlord)) {
            return;
        }
        event.setAmount(event.getAmount() + WARLORD_ELITE_ARROW_BONUS);
    }

    private static final float WARLORD_ELITE_ARROW_BONUS = 3.0F;

    @SubscribeEvent
    public static void onServantFireAttack(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        if (!ServantRobeFireDamage.isPlainFire(source)) {
            return;
        }
        if (!(source.getEntity() instanceof LivingEntity servant) || !(servant instanceof IOwned)) {
            return;
        }
        DamageSource upgraded = ServantRobeFireDamage.upgrade(servant, source);
        if (upgraded == source) {
            return;
        }
        LivingEntity victim = event.getEntity();
        event.setCanceled(true);
        boolean landed = victim.hurt(upgraded, event.getAmount());
        if (landed && ServantRobeFireDamage.isFireBreath(source)) {
            victim.setSecondsOnFire(ServantRobeFireDamage.fireBreathIgniteSeconds());
        }
    }
}
