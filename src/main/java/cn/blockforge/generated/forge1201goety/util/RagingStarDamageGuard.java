package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import com.Polarice3.Goety.config.AttributesConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.api.EntityDamageCapHolder;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithDamageGuard;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.util.RagingStarDamageGuard;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class RagingStarDamageGuard {

    public static final float MAX_HEALTH_CAP_RATIO = 0.25F;

    private RagingStarDamageGuard() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void capRagingStarHurt(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        if (!isRagingStarCreature(victim) || isPassThrough(event.getSource(), event.getAmount())) {
            return;
        }
        event.setAmount(capByMaxHealth(victim, event.getAmount()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void capDamage(LivingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        if (isPassThrough(source, event.getAmount())) {
            return;
        }
        float amount = event.getAmount();
        float capped = amount;
        if (isRagingStarCreature(victim)) {
            capped = capByMaxHealth(victim, capped);
        }
        if (isMobDealt(source)) {
            float own = ownDamageCap(victim);
            if (own > 0.0F && capped > own) {
                capped = own;
            }
        }
        if (capped < amount) {
            event.setAmount(capped);
        }
    }

    public static boolean isStarredServant(LivingEntity entity) {
        return entity instanceof RagingStarServant star && star.isRagingStarred();
    }

    public static boolean isRagingStarCreature(LivingEntity entity) {
        return isStarredServant(entity)
                && !(entity instanceof RedstoneMonstrosity)
                && !(entity instanceof WitherServant)
                && !(entity instanceof AbstractWitherWarlord);
    }

    public static boolean hasEntityDamageCap(LivingEntity entity) {
        return entity instanceof AbstractWitherWarlord                     // 黑大帅
                || entity instanceof AbstractGreenSmith                    // 绿豆
                || entity instanceof Ifrit                                 // 亲王
                || (entity instanceof WitherServant servant && servant.isStarEmbedded()) // 湮灭凋灵
                || (entity instanceof RedstoneMonstrosity && isStarredServant(entity))   // 强化红石巨兽
                || isRagingStarCreature(entity)                            // 狂暴之星生物
                || entity instanceof EntityDamageCapHolder;                // 完全体凋灵风暴
    }

    public static float ownDamageCap(LivingEntity entity) {
        if (entity instanceof AbstractWitherWarlord) {
            return AbstractWitherWarlord.damageCap();             // 黑大帅：mobs.toml（默认 25）
        }
        if (entity instanceof AbstractGreenSmith) {
            return AbstractGreenSmith.damageCap();                // 绿豆：mobs.toml（默认 20）
        }
        if (entity instanceof Ifrit) {
            return Ifrit.damageCap();                             // 亲王：mobs.toml（默认 20）
        }
        if (entity instanceof WitherServant servant && servant.isStarEmbedded()) {
            return (float) (double) WitherServantConfig.RAGING_MAX_DAMAGE_PER_HIT.get();
        }
        if (entity instanceof EntityDamageCapHolder holder) {
            return holder.entityDamageCap();
        }
        if (entity instanceof RedstoneMonstrosity && isStarredServant(entity)) {
            return (float) (double) AttributesConfig.RedstoneMonstrosityDamageCap.get();
        }
        if (isRagingStarCreature(entity)) {
            return entity.getMaxHealth() * MAX_HEALTH_CAP_RATIO;
        }
        return 0.0F;
    }

    public static boolean isMobDealt(DamageSource source) {
        Entity causing = source.getEntity();
        Entity direct = source.getDirectEntity();
        if (causing instanceof Player || direct instanceof Player) {
            return false;
        }
        return causing instanceof LivingEntity || direct instanceof LivingEntity;
    }

    private static boolean isPassThrough(DamageSource source, float amount) {
        return source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || ExecutionDamage.isExecution(source, amount);
    }

    private static float capByMaxHealth(LivingEntity victim, float amount) {
        float limit = victim.getMaxHealth() * MAX_HEALTH_CAP_RATIO;
        return amount > limit ? limit : amount;
    }
}
